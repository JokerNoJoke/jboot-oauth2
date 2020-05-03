pipeline {
    agent any

    stages {
        stage('Pull') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/JokerNoJoke/jboot-oauth2.git'
            }
        }
        stage('Build') {
            steps {
                // Run Maven on a Unix agent
                sh "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    echo 'Build success'
                }
            }
        }
        stage('Deploy development') {
            when {
                not {
                    branch 'production'
                }
            }
            steps {
                if (env.BRANCH_NAME == 'master') {
                    echo 'I only execute on the master branch'
                } else {
                    echo 'I execute elsewhere'
                }
            }
        }
        stage('Deploy production') {
            when {
                branch 'production'
            }
            steps {
                echo 'Deploy production success'
            }
        }
    }

    post {
        always {
            echo 'always'
        }
        changed {
            echo 'changed'
        }
        failure {
            echo 'failure'
        }
        success {
            echo 'success'
        }
        unstable {
            echo '只有当前流水线或阶段的完成状态为"unstable"，才允许在 部分运行该步骤, 通常由于测试失败,代码违规等造成。通常web UI是黄色'
        }
        aborted {
            echo '只有当前流水线或阶段的完成状态为"aborted"，才允许在 部分运行该步骤, 通常由于流水线被手动的aborted。通常web UI是灰色'
        }
    }
}
