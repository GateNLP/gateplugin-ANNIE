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
                    findbugs defaultEncoding: '', excludePattern: '', failedNewAll: '0', healthy: '', includePattern: '', pattern: '', unHealthy: '', unstableNewAll: '0', useStableBuildAsReference: true
                }
            }
        stage('Deploy') {
            steps {
                sh 'mvn -e source:jar javadoc:jar deploy'
            }
        }
    }
}
