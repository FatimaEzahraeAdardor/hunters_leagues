
   pipeline {
        agent any

        environment {
            SONARQUBE_URL = 'http://localhost:9000'
            SONAR_TOKEN = credentials('sonar-token')  // Make sure to use your credential for SonarQube token
        }

        stages {
            stage('Checkout') {
                steps {
                  git branch: 'main', url: 'https://github.com/FatimaEzahraeAdardor/hunters_leagues'
                }
            }

            stage('SonarQube Analysis') {
                steps {
                    script {
                        withSonarQubeEnv('SonarQube') {
                            sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=hunters_league -Dsonar.projectName="hunters_league" -Dsonar.host.url=$SONARQUBE_URL -Dsonar.login=$SONAR_TOKEN'
                        }
                    }
                }
            }

            stage('Quality Gate') {
                steps {
                    script {
                        // Wait for the Quality Gate result and fail if not passed
                        waitForQualityGate abortPipeline: true
                    }
                }
            }
        }
    }
