//------------------
// HELPERS

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
    junit '**/target/surefire-reports/TEST-*.xml'
}

//------------------
stage 'Code Quality'

node {
    parallel(
            'sonarqube': {
                def host = 'http://localhost:9000'
                mvn "sonar:sonar -Dsonar.host.url=$host"
            },
            'owasp-dependency-check': {
                def version = '1.4.2'
                mvn "mvn org.owasp:dependency-check-maven:$version:check"
                publishHTML target: [
                        reportDir  : 'target',
                        reportFiles: 'dependency-check-report.html',
                        reportName : 'OWASP Dependency Check'
                ]
            }
    )
}

//------------------

/*
timeout(time:5, unit:'DAYS') {
    input message: 'Approve?'
}
*/
