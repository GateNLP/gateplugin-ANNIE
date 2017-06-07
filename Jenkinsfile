pipeline {
    agent any
    triggers {
        upstream(upstreamProjects: "gate-top", threshold: hudson.model.Result.SUCCESS)
    }
    tools { 
        maven 'Maven 3.3.9' 
        jdk 'JDK1.8' 
    }
    stages {
        stage ('Build') {
            steps {
                sh 'mvn -e clean install' 
            }
        }
        stage('Document') {
            steps {
                sh 'mvn -e site'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                    jacoco exclusionPattern: '**/gate/gui/**'
                    findbugs excludePattern: '**/gate/resources/**,**/gate/jape/parser/**', failedNewAll: '0', pattern: '**/findbugsXml.xml', unstableNewAll: '0', useStableBuildAsReference: true
                    warnings canResolveRelativePaths: false, consoleParsers: [[parserName: 'Java Compiler (javac)'], [parserName: 'JavaDoc Tool']], defaultEncoding: 'UTF-8', excludePattern: "**/test/**,**/gate/jape/parser/**", failedNewAll: '0', unstableNewAll: '0', useStableBuildAsReference: true
                }
                success {
                    step([$class: 'JavadocArchiver', javadocDir: 'target/site/apidocs', keepAll: false])
                }
            }
        }
        stage('Deploy') {
            when{
                branch 'master'
            }
            steps {
                sh 'mvn -e source:jar javadoc:jar deploy'
            }
        }
    }
}
