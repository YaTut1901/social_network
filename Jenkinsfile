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
  }
}
