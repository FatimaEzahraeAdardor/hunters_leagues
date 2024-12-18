pipeline {
    agent any

    tools {
        maven 'Maven' // Replace 'Maven' with the actual name of your Maven installation in Jenkins
    }

    environment {
        DOCKER_IMAGE = 'hunters_league'
        DOCKER_TAG = "${BUILD_NUMBER}"
        SONAR_TOKEN = credentials('sonar-token')
        MAVEN_OPTS = '-Dmaven.test.failure.ignore=true'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    deleteDir() // Clean workspace
                    echo "Cloning Git repository..."
                    sh '''
                        git clone -b v04 https://github.com/FatimaEzahraeAdardor/hunters_leagues .
                        echo "Repository cloned successfully."
                    '''
                }
            }
        }

        stage('Environment Check') {
            steps {
                sh '''
                    echo "Git version:"
                    git --version
                    echo "Current Git branch:"
                    git branch --show-current
                    echo "Git status:"
                    git status
                    echo "Java version:"
                    java -version
                    echo "Maven version:"
                    mvn --version
                    echo "Working directory contents:"
                    pwd
                    ls -la
                '''
            }
        }

        stage('Build') {
            steps {
                sh '''
                    mvn clean package -DskipTests --batch-mode --errors
                '''
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test --batch-mode'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    jacoco(
                        execPattern: '**/target/*.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java'
                    )
                }
            }
        }

        stage('Code Quality Analysis') {
            steps {
                sh '''
                    mvn sonar:sonar \
                        -Dsonar.projectKey=hunters_league \
                         -Dsonar.projectName='hunters_league' \
                         -Dsonar.host.url=http://localhost:9000 \
                         -Dsonar.token=sqp_10d9bf4bf0f27b8aeb8053f77bf9b38a87aa7aa0
                '''

            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")
                    docker.build("${DOCKER_IMAGE}:latest")
                }
            }
        }

        stage('Manual Approval') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    input message: 'Deploy to production?', ok: 'Proceed'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}").run('-p 8081:8080')
                }
            }
        }
    }

    post {
        always {
            // cleanWs() should be called within the 'node' context, so it goes in the pipeline stages.
            echo "Cleaning workspace..."
            cleanWs()
        }
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline execution failed!'
        }
    }
}
