pipeline {
    agent any
    environment {
        IMAGE_NAME = 'DOCKERHUB_USERNAME/cca2-student-management'
        REGISTRY_CREDENTIALS = 'dockerhub-creds'
    }
    stages {
        stage('Checkout') {
            steps { checkout scm }
        }
        stage('Build and Test') {
            steps { sh 'mvn -B clean test package' }
        }
        stage('Docker Build') {
            steps { sh 'docker build -t $IMAGE_NAME:$BUILD_NUMBER -t $IMAGE_NAME:latest .' }
        }
        stage('Push Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${REGISTRY_CREDENTIALS}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh '''
                      echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                      docker push $IMAGE_NAME:$BUILD_NUMBER
                      docker push $IMAGE_NAME:latest
                      docker logout
                    '''
                }
            }
        }
        stage('Deploy to Minikube') {
            steps {
                sh '''
                  sed "s|IMAGE_PLACEHOLDER|$IMAGE_NAME:$BUILD_NUMBER|g" k8s/deployment.yaml | kubectl apply -f -
                  kubectl apply -f k8s/service.yaml
                  kubectl rollout status deployment/cca2-student-management --timeout=180s
                '''
            }
        }
        stage('Verify') {
            steps {
                sh 'kubectl get pods,deployments,services'
            }
        }
    }
    post { always { echo 'CCA 2 pipeline finished. Check stage results above.' } }
}
