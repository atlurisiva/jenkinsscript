pipeline {
    agent any
    stages {
        stage('contionus Download') {
            agent { label 'master'}
            steps 
            
            {
                script {
                    try {
                        git 'https://github.com/atlurisiva/jenkinsscript.git'}
                    catch(Exception e1) {
                        mail bcc: '', body: 'contionous download is failed', cc: '', from: '', replyTo: '', subject: 'contionus download is failed', to: 'atluri1988@gmail.com'
                    }
                }
                
            }
        stage('contionus build') {
            steps { sh 'mvn package'}
        }
        stage('continous deplpyment') {
            steps {deploy adapters: [tomcat9(credentialsId: '1b5f1a4c-ac6c-4d4f-9327-9e8d0432741d', path: '', url: 'http://172.31.93.240:8080')], contextPath: 'testapp123', war: '**/*.war'}
        }
        stage('contionus testing') {
            steps {
                git 'https://github.com/atlurisiva/FunctionalTestingoriginal.git'
                sh 'java -jar /var/lib/jenkins/workspace/declarative/testing.jar'
            }
         }
    }
    post {
        success {
                    input 'waiting for approval '
                    deploy adapters: [tomcat9(credentialsId: '1b5f1a4c-ac6c-4d4f-9327-9e8d0432741d', path: '', url: 'http://172.31.89.67:8080')], contextPath: 'prodapp123', war: '**/*.war'
                }
        failure {
            mail bcc: '', body: 'jenkins script failed ', cc: '', from: '', replyTo: '', subject: 'jenkins script failed', to: 'atluri1988@gmail.com'
        }
    }   
}