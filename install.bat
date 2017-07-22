echo.

cd ./ssm-parent
call mvn clean install

cd ../ssm-common-base
call mvn clean install

cd ../ssm-common-core
call mvn clean install

cd ../ssm-common-web
call mvn clean install

cd ../ssm-sys-api
call mvn clean install

cd ../ssm-sys-core
call mvn clean install

cd ../ssm-sys-web
call mvn clean install

cd ../ssm-act-api
call mvn clean install

cd ../ssm-act-core
call mvn clean install

cd ../ssm-act-web
call mvn clean install

cd ../ssm-final
call mvn clean install

echo. & pause