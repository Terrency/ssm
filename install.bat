echo.

cd ./ssm-parent
call mvn clean install -Dmaven.test.skip=true

cd ../ssm-common-base
call mvn clean install -Dmaven.test.skip=true

cd ../ssm-common-core
call mvn clean install -Dmaven.test.skip=true

cd ../ssm-common-web
call mvn clean install -Dmaven.test.skip=true

cd ../ssm-sys-api
call mvn clean install -Dmaven.test.skip=true

cd ../ssm-sys-core
call mvn clean install -Dmaven.test.skip=true

cd ../ssm-sys-web
call mvn clean install -Dmaven.test.skip=true

cd ../ssm-act-api
call mvn clean install -Dmaven.test.skip=true

cd ../ssm-act-core
call mvn clean install -Dmaven.test.skip=true

cd ../ssm-act-web
call mvn clean install -Dmaven.test.skip=true

cd ../ssm-final
call mvn clean install -Dmaven.test.skip=true

echo. & pause