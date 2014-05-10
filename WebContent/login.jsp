<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="css/basic.css" id="thecss">
        <title>The Hunt Project</title>
    </head>
    <body>
        <center>
        <form name="loginform" action="/TheHunt/login" method="post">
        <table width="80%">
            <tr>
                <td height="50" valign="middle" colspan="2"><b>The Hunt Project</b></td>
            </tr> 
            <tr>
                <td height="20" width="50%" valign="middle">Login</td>
                <td height="20" width="50%" valign="middle"><font color="red">${EMSG}</font><font color="green">${MSG}</font></td>            
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">User Name:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="username" id="username" /></td>
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Password:</td>
                <td height="20" width="50%" valign="middle"><input type="password" name="password" id="password" /></td>
            </tr>
            <tr>
                <td height="60" valign="middle" colspan="2"><input type="submit" value="Login" /></td>
            </tr>
            <tr>
                <td height="50" valign="middle" colspan="2">Don't have an account?  <a href="/TheHunt/accountgateway?a=createnewaccount">Click here</a></td>            
            </tr>
        </table>
        </form>
        </center>
    </body>
</html>