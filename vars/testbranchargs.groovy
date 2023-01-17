def call(String repoUrl) {
	def args = [
        
        branche: '',
        url: ''
    ]
	pipeline {
		agent any
		stages {
			stage ("Git Checkout") {
				steps {
					checkout([
					    $class: 'GitSCM',
						branches: [[name:  stageParams.branch ]],
						userRemoteConfigs: [[ url: stageParams.url ]]
						])			
				}
			}
			stage ("Maven Build") {
			
				steps {
					sh "mvn clean package -DskipTests=true"
				}
			}
			
			stage ("Sonar Publish") {
				steps {
					withSonarQubeEnv("sonar") {
						sh "mvn sonar:sonar"
					}
				}
			}
			
			stage ("Sonar Quality Gate status Checks") {
				steps {
					script {
						timeout(time: 1, unit: "HOURS") {
							def qg = waitForQualityGate()
							if (qg.status != "OK") {
								error "Aborting the pipeline due to Quality Gate failed: ${qg.status}"
							}
						}
					}
				}
			}
			
						
		}
	
	}
	
}
