#!/usr/bin/env groovy

	def uploadtoNexus() {
		nexusArtifactUploader artifacts: [[artifactId: 'myweb', classifier: '', file: 'target/myweb-1.0.0-SNAPSHOT.war', type: 'war']], credentialsId: 'nexus3', groupId: 'in.javahome', nexusUrl: '192.168.145.131:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'Jenkinstest', version: '1.0.0-SNAPSHOT'
	}
