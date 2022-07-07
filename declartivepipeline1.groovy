pipeline {
    agent any
    stages {
        stage('contionus Download') {
            agent { label 'master'}
            steps {
                git 'https://github.com/atlurisiva/jenkinsscript.git'
            }

        }
        stage('contionus build') {
            steps {
                sh 'mvn package'
            }
        }
        stage('continous deplpyment') {
            steps {
        
            deploy adapters: [tomcat9(credentialsId: '1b5f1a4c-ac6c-4d4f-9327-9e8d0432741d', path: '', url: 'https://172.31.93.240:8080')], contextPath: 'qaapp1', war: '**/*.war'
            }
        }
        stage('contionus testing') {
            steps {
                git 'https://github.com/atlurisiva/FunctionalTestingoriginal.git'
                sh 'java -jar /var/lib/jenkins/workspace/declartive/testing.jar'
            }
         }
        stage('contionous delivery') {
            steps {
                input 'waiting for approval '
                deploy adapters: [tomcat9(credentialsId: '1b5f1a4c-ac6c-4d4f-9327-9e8d0432741d', path: '', url: 'https://172.31.89.67:8080')], contextPath: 'prodapp1', war: '**/*.war'
            }
        }

    
}
}