def sonarServerUrl = hudson.plugins.sonar.SonarInstallation.get(SONAR_SERVER).serverUrl;
def sonarDatabaseUrl = hudson.plugins.sonar.SonarInstallation.get(SONAR_SERVER).databaseUrl;
def sonarDatabaseLogin = hudson.plugins.sonar.SonarInstallation.get(SONAR_SERVER).databaseLogin;
def sonarDatabasePassword = hudson.plugins.sonar.SonarInstallation.get(SONAR_SERVER).databasePassword;

def mvn(GString args) {
    sh "${tool 'Maven 3'}/bin/mvn ${args}"
}

def mvnCleanVerify(ignoreTestFailures = true) {
    mvn "-B ${ignoreTestFailures?:'-Dmaven.test.failure.ignore'} clean verify"
}

def sonarAnalyse() {
    mvn "sonar:sonar -Dsonar.jdbc.url='${sonarDatabaseUrl}' -Dsonar.host.url=${sonarServerUrl} -Dsonar.jdbc.username=${sonarDatabaseLogin} -Dsonar.jdbc.password=${sonarDatabasePassword}"
}
