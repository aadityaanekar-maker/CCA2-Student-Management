# Student Management System – CCA 2

A small Java 17 + Spring Boot application prepared as a CI/CD demonstration starter.

## Run locally
```bash
mvn clean test package
java -jar target/student-management-1.0.0.jar
```
Open http://localhost:8080. Student API: `GET /api/students`; add a student with `POST /api/students`.

## CI/CD architecture
GitHub -> Jenkins -> Maven build/test -> Docker image -> Docker Hub -> Minikube/Kubernetes.

## Repository contents
- `Dockerfile`: builds and runs the Spring Boot application.
- `Jenkinsfile`: declarative CI/CD pipeline.
- `k8s/deployment.yaml`: Kubernetes Deployment.
- `k8s/service.yaml`: NodePort Service.

## Before running Jenkins
1. Replace `DOCKERHUB_USERNAME` in `Jenkinsfile` with your Docker Hub username.
2. Add a Jenkins username/password credential with ID `dockerhub-creds` (Docker Hub username + access token).
3. Ensure Jenkins can run `docker`, `kubectl`, and access the configured Minikube cluster.
4. Configure the Jenkins Pipeline job to use this GitHub repository.

## Demonstrating a second run
Change the visible version text in `StudentController.java`, commit and push. Run the Jenkins job again. It publishes a new build-number tag and updates the Kubernetes Deployment.
