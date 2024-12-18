pipeline {
    agent any

    environment {
        SONARQUBE_URL = 'http://localhost:9000'  // Make sure this is correct based on where SonarQube is running
        SONAR_TOKEN = credentials('sonar-token')  // Ensure the correct credential ID for the SonarQube token
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
                maven 'Maven'  // Ensure Maven is configured in Jenkins tools settings
            }
            steps {
                script {
                    // Inspect the workspace to check the location of .scannerwork folder
                    echo "Listing .scannerwork directory"
                    sh 'ls -R .scannerwork || echo "No .scannerwork directory found"'

                    // Run SonarQube analysis
                    withSonarQubeEnv('SonarQube') {
                        echo "Running SonarQube Analysis..."
                        sh '''
                            mvn clean verify sonar:sonar \
                            -Dsonar.projectKey=hunters_league \
                            -Dsonar.projectName="hunters_league" \
                            -Dsonar.host.url=$SONARQUBE_URL \
                            -Dsonar.login=$SONAR_TOKEN \
                            -X  // Enable debug logging for more detailed output
                        '''
                    }
                }
            }
        }

        stage('Post Analysis') {
            steps {
                script {
                    // Wait for the SonarQube analysis to complete
                    echo "Waiting for SonarQube analysis results..."
                    timeout(time: 1, unit: 'HOURS') {
                        waitForQualityGate()  // Wait for the analysis to complete before proceeding
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Cleaning up after the pipeline execution"
        }
        success {
            echo "SonarQube analysis completed successfully."
        }
        failure {
            echo "SonarQube analysis failed. Check the logs for details."
        }
    }
}
