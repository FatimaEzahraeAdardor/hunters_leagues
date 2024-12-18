pipeline {
    agent any

    environment {
        SONARQUBE_URL = 'hhttp://host.docker.internal:9000/'
        SONAR_TOKEN = "sqa_2452c6b30074ed8668feec957f243900378ea17d"  // Ensure you are using the correct credential ID for the SonarQube token
    }

    stages {
        stage('Clean Workspace') {
            steps {
                deleteDir()  // Clean the workspace before checking out the repo
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/FatimaEzahraeAdardor/hunters_leagues'
            }
        }

        stage('Check Maven Version') {
            steps {
                sh 'mvn --version' // Check Maven version to verify if it's installed correctly
            }
        }

       stage('SonarQube Analysis') {
           tools {
               maven 'Maven'
           }
           steps {
               script {
                   withSonarQubeEnv('SonarQube') {
                       echo "Running SonarQube Analysis..."
                       sh '''
                           mvn clean package sonar:sonar \
                           -Dsonar.projectKey=hunters_league \
                           -Dsonar.host.url=$SONARQUBE_URL \
                           -Dsonar.login=$SONAR_TOKEN
                       '''
                   }
               }
           }
       }
    }
}
