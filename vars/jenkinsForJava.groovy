def call(String repoUrl) {
  pipeline {
       agent any
       tools {
           maven 'maven1'
           jdk 'openjdk 17.0.2'
           
       }
       stages {
 
           stage("Checkout Code") {
               steps {
                   git branch: 'main',
                       url: "${repoUrl}"
               }
           }

       }
   }
}
