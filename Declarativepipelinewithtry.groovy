pipeline {
    agent { label: master}
    stages
    {
        stage('continous download')
        {
            steps {
                 git 'https://github.com/atlurisiva/jenkinsscript.git'
            }
        }
        stage('contionous build')
        {
            steps {
                sh 'mvn package'
            }
        }
        stage('contionus deployment')
        {
            steps {
                deploy adapters: [tomcat9(credentialsId: '1b5f1a4c-ac6c-4d4f-9327-9e8d0432741d', path: '', url: 'http://172.31.93.240:8080')], contextPath: 'testapp1', war: '**/*.war'
            }
        }
        stage('contionus testing')
        {
            steps {
                        git 'https://github.com/atlurisiva/FunctionalTestingoriginal.git'
                        sh 'java -jar /var/lib/jenkins/workspace/script/testing.jar'
            }
        }

    }
    post {
        success 
        {
            mail bcc: '', body: '', cc: '', from: '', replyTo: '', subject: 'jenkins ci-cd approval', to: 'atluri1988@gmail.com'
            input message: 'waiting for approval', submitter: 'ram'
            deploy adapters: [tomcat9(credentialsId: '1b5f1a4c-ac6c-4d4f-9327-9e8d0432741d', path: '', url: 'http://172.31.89.67:8080')], contextPath: 'prodapp1', war: '**/*.war'
        }
        failure {
            mail bcc: '', body: '', cc: '', from: '', replyTo: '', subject: 'jenkins ci-cd failed', to: 'atluri1988@gmail.com'
        }
    }
}