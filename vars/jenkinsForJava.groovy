def call(String repoUrl) {
  pipeline {
       agent any
       tools {
           maven 'maven1'
           jdk 'java'
           
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
