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
        <table width="80%">
            <tr>
                <td height="50" width="50%" valign="middle"><b>The Hunt Project</b></td>
                <td height="50" width="50%" valign="middle" align="right">
                    <c:if test="${ACCOUNT.getId() > 0}">
                    <a href="/TheHunt/accountgateway?a=mainaccount&accountid=${ACCOUNT.getId()}">Back to Main</a>&nbsp;
                    <a href="/TheHunt/logout">Logout</a>
                    </c:if>
                </td>
            </tr>        
            <tr>
                <td height="20" width="50%" valign="middle">Account Detail</td>
                <td height="20" width="50%" valign="middle"><font color="red">${EMSG}</font><font color="green">${MSG}</font></td>            
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">First Name:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="firstname" id="firstname" value="${ACCOUNT.getFirstName()}" /></td>
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Last Name:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="lastname" id="lastname" value="${ACCOUNT.getLastName()}" /></td>
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Phone Number:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="phone" id="phone" value="${ACCOUNT.getPhone()}" /></td>
            </tr>            
            <tr>
                <td height="20" width="50%" valign="middle">Email:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="email" id="email" value="${ACCOUNT.getEmail()}" /></td>
            </tr>            
            <tr>
                <td height="20" width="50%" valign="middle">User Name:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="username" id="username" value="${ACCOUNT.getUsername()}" /></td>
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Password:</td>
                <td height="20" width="50%" valign="middle"><input type="password" name="password" id="password" value="${ACCOUNT.getPassword()}" /></td>
            </tr>
            <tr>
                <td height="60" valign="middle" colspan="2">
                    <input type="submit" value="Submit" />
                </td>
            </tr>
        </table>
        </form>
        </center>
    </body>
</html>