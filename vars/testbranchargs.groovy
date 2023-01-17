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
			stage ("UploadToNexus") {
				steps {
					script {
						UploadtoNexus()
					}
				}
				
			}
						
		}
	
	}
	def UploadtoNexus() {
		nexusArtifactUploader artifacts: [[artifactId: 'myweb', classifier: '', file: 'target/myweb-1.0.0-SNAPSHOT.war', type: 'war']], credentialsId: 'nexus3', groupId: 'in.javahome', nexusUrl: '192.168.145.131:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'Jenkinstest', version: '1.0.0-SNAPSHOT'
	}
}
