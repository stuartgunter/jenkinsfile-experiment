//------------------
// TOOLS

def mvn(GString args) {
    sh "${tool 'Maven 3'}/bin/mvn ${args}"
}

def mvnCleanVerify(ignoreTestFailures = true) {
    mvn "-B ${ignoreTestFailures?:'-Dmaven.test.failure.ignore'} clean verify"
}

def sonarAnalyse() {
    mvn "sonar:sonar -Dsonar.host.url=${env.SONAR_HOST_URL}"
}




//------------------
// PIPELINE

stage 'Build'

node {
    checkout scm
    tools.mvnCleanVerify()

    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    //step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/TEST-*.xml'])
    junit '**/target/surefire-reports/TEST-*.xml'
}

//------------------
stage 'Code Quality'

node {
    parallel(
        'sonarqube': {
            tools.sonarAnalyse()
        }
    )
}


//------------------

/*
timeout(time:5, unit:'DAYS') {
    input message: 'Approve?'
}
*/
