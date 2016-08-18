//------------------
// TOOLS

def mvn(GString args) {
    sh "${tool 'Maven 3'}/bin/mvn ${args}"
}



//------------------
// PIPELINE

stage 'Build'

def ignoreTestFailures = true

node {
    checkout scm
    mvn "-B ${if (ignoreTestFailures) '-Dmaven.test.failure.ignore'} clean verify"

    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    //step([$class: 'JUnitResultArchiver', testResults: 'target/surefire-reports/TEST-*.xml'])
    junit '**/target/surefire-reports/TEST-*.xml'
}

//------------------
stage 'Code Quality'

node {
    parallel(
        'sonarqube': {
            def sonarqubeScannerHome = tool name: 'Sonar', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
            sh "${sonarqubeScannerHome}/bin/sonar-scanner -e -Dsonar.host.url=http://localhost:9000"
        }
    )
}


//------------------

/*
timeout(time:5, unit:'DAYS') {
    input message: 'Approve?'
}
*/
