<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>

<jsp:useBean id="Account" class="hunt.beans.Account" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="css/basic.css" id="thecss">
        <title>The Hunt Project</title>
    </head>
    <body>
        <center>
        <form name="frm" action="/TheHunt/accountgateway" method="post">
        <c:if test="${ACCOUNT.getId() == 0}">
        <input type="hidden" name="a" id="a" value="createaccount" />
        </c:if>
        <c:if test="${ACCOUNT.getId() > 0}">
        <input type="hidden" name="a" id="a" value="editaccount" />
        </c:if>        
        <input type="hidden" name="accountid" id="accountid" value="${ACCOUNT.getId()}" />
        <table width="70%">
            <tr>
                <td height="50" width="30%" valign="middle">The Hunt Project</td>
                <td height="50" width="70%" valign="middle"><a href="/TheHunt/logout">logout</a></td>
            </tr>        
            <tr>
                <td height="40" width="30%" valign="middle">Account Detail</td>
                <td height="40" width="70%" valign="middle"><font color="red">${EMSG}</font><font color="green">${MSG}</font></td>            
            </tr>
            <tr>
                <td height="40" width="30%" valign="middle">First Name:</td>
                <td height="40" width="70%" valign="middle"><input type="text" name="firstname" id="firstname" value="${ACCOUNT.getFirstName()}" /></td>
            </tr>
            <tr>
                <td height="40" width="30%" valign="middle">Last Name:</td>
                <td height="40" width="70%" valign="middle"><input type="text" name="lastname" id="lastname" value="${ACCOUNT.getLastName()}" /></td>
            </tr>
            <tr>
                <td height="40" width="30%" valign="middle">Phone Number:</td>
                <td height="40" width="70%" valign="middle"><input type="text" name="phone" id="phone" value="${ACCOUNT.getPhone()}" /></td>
            </tr>            
            <tr>
                <td height="40" width="30%" valign="middle">Email:</td>
                <td height="40" width="70%" valign="middle"><input type="text" name="email" id="email" value="${ACCOUNT.getEmail()}" /></td>
            </tr>            
            <tr>
                <td height="40" width="30%" valign="middle">User Name:</td>
                <td height="40" width="70%" valign="middle"><input type="text" name="username" id="username" value="${ACCOUNT.getUsername()}" /></td>
            </tr>
            <tr>
                <td height="40" width="30%" valign="middle">Password:</td>
                <td height="40" width="70%" valign="middle"><input type="password" name="password" id="password" value="${ACCOUNT.getPassword()}" /></td>
            </tr>
            <tr>
                <td height="60" valign="middle" colspan="2">
                    <input type="submit" value="Submit" />
                    <a href="/TheHunt/index.jsp'">Cancel</a>
                </td>
            </tr>
        </table>
        </form>
        </center>
    </body>
</html>