echo set CATALINA_HOME
set CATALINA_HOME=c:\opt\apache-tomcat-7

echo stop tomcat
call c:\opt\apache-tomcat-7\bin\shutdown.bat

echo build application
call c:\opt\ant\bin\ant -buildfile c:\opt\github\TheHunt\build.xml

echo start tomcat
call c:\opt\apache-tomcat-7\bin\startup.bat

echo done (%DATE% %TIME%)

