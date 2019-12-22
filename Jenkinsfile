pipeline {
  agent {
      label 'maven'
  }
   
  triggers {
        pollSCM('*/5 * * * *')
    }
  
  options { disableConcurrentBuilds() }
  
  stages {
    stage('Build App') {
      steps {
        sh "mvn install"
      }
    }
    stage('Create Image Builder') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector("bc", "springbootapp").exists();
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.newBuild("--name=springbootapp","--image-stream=openjdk18-openshift:1.1", "--binary")
          }
        }
      }
    }
    stage('Build Image') {
      steps {
        script {
          openshift.withCluster() {
            openshift.selector("bc", "springbootapp").startBuild("--from-file=target/spring-example-0.0.1-SNAPSHOT.jar", "--wait")
          }
        }
      }
    }
    stage('Promote to UAT') {
      steps {
        script {
          openshift.withCluster() {
            openshift.tag("springbootapp:latest", "springbootapp:uat")
          }
        }
      }
    }
    stage('Create UAT') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector('dc', 'springbootapp-uat').exists()
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.newApp("springbootapp:latest", "--name=springbootapp-uat").narrow('svc').expose()
          }
        }
      }
    }
    stage('Promote PROD') {
      steps {
        script {
          openshift.withCluster() {
            openshift.tag("springbootapp:uat", "springbootapp:prod")
          }
        }
      }
    }
    stage('Create PROD') {
      when {
        expression {
          openshift.withCluster() {
            return !openshift.selector('dc', 'springbootapp-prod').exists()
          }
        }
      }
      steps {
        script {
          openshift.withCluster() {
            openshift.newApp("springbootapp:prod", "--name=springbootapp-prod").narrow('svc').expose()
          }
        }
      }
    }
  }
}
