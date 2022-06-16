node('master') {
    stage('continous download')
    {
        git 'https://github.com/atlurisiva/jenkinsscript.git'
    }
    stage('continous build'){
        sh 'mvn package'
    }
    stage('continous Deployment')
    {
        deploy adapters: [tomcat9(credentialsId: '1b5f1a4c-ac6c-4d4f-9327-9e8d0432741d', path: '', url: 'http://172.31.93.240:8080')], contextPath: 'testapp1', war: '**/*.war'
        
    }
    stage('continous testing')
    {
        git 'https://github.com/atlurisiva/FunctionalTestingoriginal.git'
        sh 'java -jar /var/lib/jenkins/workspace/script/testing.jar'
    }
    stage('contionus delivery') {
        input 'waiting for approval from delivery manager'
        deploy adapters: [tomcat9(credentialsId: '1b5f1a4c-ac6c-4d4f-9327-9e8d0432741d', path: '', url: 'http://172.31.89.67:8080')], contextPath: 'prodapp1', war: '**/*.war'
    }

}
