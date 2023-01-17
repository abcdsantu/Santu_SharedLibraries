

def call() {
   withSonarQubeEnv("sonar") 
          sh "mvn sonar:sonar"
}

