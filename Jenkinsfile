pipeline {
    agent any
    tools { 
        maven 'Maven 3.3.9' 
        jdk 'JDK1.8' 
    }
    stages {
        stage ('Build') {
            steps {
                sh 'mvn -e install' 
            }
        }
        stage('Document') {
            steps {
                sh 'mvn -e site'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                    jacoco exclusionPattern: '**/gate/gui/**'
                    findbugs excludePattern: '**/gate/resources/**,**/gate/jape/parser/**', failedNewAll: '0', pattern: '**/findbugsXml.xml', unstableNewAll: '0', useStableBuildAsReference: true
                    step([$class: 'JavadocArchiver', javadocDir: 'target/site/apidocs', keepAll: false])
                    warnings canResolveRelativePaths: false, consoleParsers: [[parserName: 'Java Compiler (javac)'], [parserName: 'JavaDoc Tool']], defaultEncoding: 'UTF-8', failedNewAll: '0', unstableNewAll: '0', useStableBuildAsReference: true
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
