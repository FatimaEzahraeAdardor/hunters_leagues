pipeline {
    agent any

    environment {
        SONAR_PROJECT_KEY = "hunters_league"
        SONAR_TOKEN = credentials('sonar-token') // Use Jenkins credentials for security
        SONAR_HOST_URL sqa_2452c6b30074ed8668feec957f243900378ea17d= "http://host.docker.internal:9000"
    }

    stages {
        stage('Install Tools') {
            steps {
                script {
                    echo "Installing jq..."
                    sh '''
                    apt-get update && apt-get install -y jq
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
    }
}