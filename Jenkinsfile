pipeline {
  agent {
    docker {
      image "maven:3.8.2-jdk-11"
    }
  }
  
  stages {
    stage("Build") {
      steps {
        sh "java -version"
        sh "mvn -version"
      }
    }
  }
  
  post {
    always {
      cleanWs()
    }
  }
}
