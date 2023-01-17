#!/usr/bin/env groovy

def call(){
    echo "Packaging war file..."
    sh "mvn clean package -Dmaven.test.skip=true"
}
