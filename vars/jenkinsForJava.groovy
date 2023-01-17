def call(String repoUrl) {
  pipeline {
       agent any
       tools {
           maven 'maven1'
   
           
       }
       stages {
           stage("Tools initialization") {
               steps {
                   sh "mvn --version"
                   sh "java -version"
               }
           }
           stage("Checkout Code") {
               steps {
                   git branch: 'main',
                       url: "${repoUrl}"
               }
           }
           stage("Cleaning workspace") {
               steps {
                   sh "mvn clean"
               }
           }

           stage("Packing Application") {
               steps {
                   sh "mvn package -DskipTests"
               }
           }
       }
   }
}
