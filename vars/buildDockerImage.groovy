#!/usr/bin/env groovy

def call(String imageName){
    echo "Building Docker image..."
    withCredentials([usernamePassword(credentialsId: "dockerhubID", usernameVariable: "santu458", passwordVariable: "Vinayaka@459")]){
        sh "docker build -t $imageName ."
        sh "echo $password | docker login -u $username --password-stdin"
        sh "docker push $imageName"
    }
}
