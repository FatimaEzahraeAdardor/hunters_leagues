pipeline {
    agent any

    environment {
        SONARQUBE_URL = 'http://localhost:9000'
        SONAR_TOKEN = credentials('sonar-token')  // Ensure you are using the correct credential ID for the SonarQube token
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

        stage('SonarQube Analysis') {
            steps {
                script {
                    withSonarQubeEnv('SonarQube') {
                        echo "Running SonarQube Analysis..."
                        sh '''
                            mvn clean verify sonar:sonar -Dsonar.projectKey=hunters_league -Dsonar.projectName="hunters_league" -Dsonar.host.url=$SONARQUBE_URL -Dsonar.login=$SONAR_TOKEN
                        '''
                    }
                }
            }
        }

        // Optionally, you can add more stages, e.g., for waiting for the quality gate
    }
}
