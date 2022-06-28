pipeline {
    agent any
        stages
        {
            stage ('contionous download')
            {
                steps {
                    script {
                        try {
                            git 'https://github.com/atlurisiva/jenkinsscript.git'
                        }
                        catch(Exception e1)
                        {
                            mail bcc: '', body: 'Unable to download', cc: '', from: '', replyTo: '', subject: 'jenkins ci-cd', to: 'atluri1988@gmail.com'
                            exit(1)
                        }
                    }
                }
            }
            stage ('contionus build')
            {
                steps {
                    script {
                        try {
                            sh 'mvn package'
                        }
                        catch(Exception e2)
                        {
                             mail bcc: '', body: 'unable to create artifact', cc: '', from: '', replyTo: '', subject: 'jenkins ci-cd', to: 'atluri1988@gmail.com'
                            exit(1)  
                        }
                    }
                }
            }
            stage ('contionus deployment')
            {
                steps{
                    script{
                        try{
                             deploy adapters: [tomcat9(credentialsId: '1b5f1a4c-ac6c-4d4f-9327-9e8d0432741d', path: '', url: 'http://172.31.93.240:8080')], contextPath: 'testapp1', war: '**/*.war'
                        }
                        catch(Exception e3)
                        {
                            mail bcc: '', body: 'unable to deploy into qa server', cc: '', from: '', replyTo: '', subject: 'jenkins ci-cd', to: 'atluri1988@gmail.com'
                            exit(1)
                        }
                    }
                }
            }
            stage ('contionus testing') 
            {
                steps{
                    script{
                        try{
                            git 'https://github.com/atlurisiva/FunctionalTestingoriginal.git'
                            sh 'java -jar /var/lib/jenkins/workspace/script/testing.jar'
                        }
                        catch(Exception e4)
                        {
                            mail bcc: '', body: 'unable to test', cc: '', from: '', replyTo: '', subject: 'jenkins ci-cd', to: 'atluri1988@gmail.com'
                            exit(1)
                        }
                    }
                }
            }
            stage ('contiouns deployment')
            {
                steps {
                    script {
                        try {
                            deploy adapters: [tomcat9(credentialsId: '1b5f1a4c-ac6c-4d4f-9327-9e8d0432741d', path: '', url: 'http://172.31.89.67:8080')], contextPath: 'prodapp1', war: '**/*.war'
                        }
                        catch(Exception e5)
                        {
                             mail bcc: '', body: 'unable to deploy', cc: '', from: '', replyTo: '', subject: 'jenkins ci-cd', to: 'atluri1988@gmail.com'
                            exit(1) 
                        }
                    }
                }
            }
        }
}