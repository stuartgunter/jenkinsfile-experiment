//------------------
stage 'Build'

def tools

node {
    tools = load 'tools.groovy'

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
