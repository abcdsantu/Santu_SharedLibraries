#!/usr/bin/env groovy

def call(String imageName){
    echo "Building Docker image..."
    withCredentials([usernamePassword(credentialsId: "dockerhubID", usernameVariable: "username", passwordVariable: "password")]){
        sh "docker build -t $imageName ."
        sh "docker tag $imageName $imageName':V$BUILD_NUMBER'"
        sh "echo $password | docker login -u $username --password-stdin"
        sh "docker push $imageName':V$BUILD_NUMBER'"
    }
}
