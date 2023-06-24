pipeline {
    agent any

    stages {
        stage('clone') {
            steps {
                // Get code from the master branch of a Github repository
                git branch: 'main', url: 'https://github.com/ChikoHakles/mini-project-library.git'
                echo "clone done"
            }
        }
        stage('Build') {
            steps {
                // To run Maven on a Windows agent, use
                bat "mvn clean package"
                echo "build done"
            }
        }
        stage('Deploy') {
            steps {
                // To run Maven on a Windows agent, use
                bat "move target\\library.war C:\\xampp\\tomcat\\webapps"
                echo "deploy done"
            }
        }
    }
}