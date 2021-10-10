pipeline {
  agent {
    docker {
      image "maven:3.8.2-jdk-11"
    }
  }

  stages {
    stage("Prebuild checks") {
      steps {
        sh "java -version"
        sh "mvn -version"
      }
    }
    stage("Build") {
      steps {
        sh "mvn clean install"
      }
    }
  }
}
