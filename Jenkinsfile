//------------------
// TOOLS

def mvn(GString args) {
    sh "${tool 'Maven 3'}/bin/mvn ${args}"
}



//------------------
// PIPELINE

stage 'Build'

node {
    checkout scm
    mvn "-B ${ignoreTestFailures?:'-Dmaven.test.failure.ignore'} clean verify"

    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    //step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/TEST-*.xml'])
    junit '**/target/surefire-reports/TEST-*.xml'
}

//------------------
stage 'Code Quality'

node {
    parallel(
        'sonarqube': {
            mvn "sonar:sonar -Dsonar.host.url=${env.SONAR_HOST_URL}"
        }
    )
}


//------------------

/*
timeout(time:5, unit:'DAYS') {
    input message: 'Approve?'
}
*/
