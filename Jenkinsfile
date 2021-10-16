pipeline {
  agent any
  tools {
    maven 'MVN'
    jdk 'JAVA'
  }

  stages {
    stage("Prebuild checks") {
      steps {
        sh "java -version"
        sh "mvn -version"
      }
    }
    
    stage("Build") {
      steps{
        sh "mvn clean package -DskipTests"
      }
    }
    
    stage("Test") {
      steps{
        sh "mvn clean install"
      }
    }
  }
  
  post {
    always {
      cleanWs()
    }
  }
}
