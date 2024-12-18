pipeline {
   agent {
           docker {
               image 'maven:3.8.8-eclipse-temurin-17'
               args '-v /var/run/docker.sock:/var/run/docker.sock'
           }
       }
    environment {
        SONAR_PROJECT_KEY = "hunters_league"
        SONAR_TOKEN = "sqa_2452c6b30074ed8668feec957f243900378ea17d" // Use Jenkins credentials for security
        SONAR_HOST_URL = "http://host.docker.internal:9000"
    }

    stages {
        stage('Install Tools') {
            steps {
                script {
                    echo "Installing jq..."
                    sh '''
                    apt-get update && apt-get install -y jq apt-transport-https ca-certificates curl gnupg-agent software-properties-common
                                        curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
                                        add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
                                        apt-get update && apt-get install -y docker-ce-cli
                    '''
                }
            }
        }

        stage('Checkout Code') {
            steps {
                script {
                    echo "Checking out code from GitHub..."
                    checkout([$class: 'GitSCM',
                        branches: [[name: '*/main']],
                        userRemoteConfigs: [[
                            url: 'https://github.com/FatimaEzahraeAdardor/hunters_leagues'
                        ]]
                    ])
                }
            }
        }

        stage('Build and SonarQube Analysis') {
            steps {
                echo "Running Maven build and SonarQube analysis..."
                sh """
                mvn clean package sonar:sonar \
                  -Dsonar.projectKey=$SONAR_PROJECT_KEY \
                  -Dsonar.host.url=$SONAR_HOST_URL \
                  -Dsonar.login=$SONAR_TOKEN
                """
            }
        }

        stage('Quality Gate Check') {
            steps {
                script {
                    echo "Checking SonarQube Quality Gate..."
                    def qualityGate = sh(
                        script: """
                        curl -s -u "$SONAR_TOKEN:" \
                        "$SONAR_HOST_URL/api/qualitygates/project_status?projectKey=$SONAR_PROJECT_KEY" \
                        | jq -r '.projectStatus.status'
                        """,
                        returnStdout: true
                    ).trim()
                    if (qualityGate != "OK") {
                        error "Quality Gate failed! Stopping the build."
                    }
                    echo "Quality Gate passed! Proceeding..."
                }
            }
        }
         stage('Build Docker Image') {
            steps {
                script {
                    echo "Building Docker Image..."
                    sh 'docker build -t springboot-app .'
                }
            }
        }
        stage('Deploy Docker Container') {
            steps {
                script {
                    echo "Deploying Docker container..."
                    sh """
                    docker stop springboot-app-container || true
                    docker rm springboot-app-container || true
                    docker run -d -p 8080:8080 --name springboot-app-container springboot-app
                    """
                }
            }
        }
    }
}