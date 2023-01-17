def call() {
  pipeline {
       agent any
       stages {

		    stage ("Sonar Quality Gate status Checks") {
              steps {
                 script {
                    timeout(time: 1, unit: "HOURS") {
                        def qg = waitForQualityGate()
                        if (qg.status != "OK") {
                            error "Aborting the pipeline due to Quality Gate failed: ${qg.status}"
                        } else {
				echo "Quality gate passed with result: ${qg.status}"
			}
                    }
                }
            }
        }
       }
  }
    
}
