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
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                    jacoco exclusionPattern: '**/gate/gui/**'
                }
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn -e source:jar javadoc:jar deploy'
            }
        }
    }
}
