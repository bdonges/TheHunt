<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>

<jsp:useBean id="Player" class="hunt.beans.Player" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="css/basic.css" id="thecss">
        <title>The Hunt Project</title>
    </head>
    <body>
        <center>
        <form name="frm" action="/TheHunt/playergateway" method="post">
        <c:if test="${PLAYER.getId() == 0}">
        <input type="hidden" name="a" id="a" value="createplayer" />
        </c:if>
        <c:if test="${PLAYER.getId() > 0}">
        <input type="hidden" name="a" id="a" value="editplayer" />
        </c:if>        
        <input type="hidden" name="playerid" id="playerid" value="${PLAYER.getId()}" />
        <input type="hidden" name="teamid" id="teamid" value="${PLAYER.getTeamId()}" />
        <table width="60%">
            <tr>
                <td height="50" width="40%" valign="middle">The Hunt Project</td>
                <td height="50" width="60%" valign="middle">
                    <a href="/TheHunt/teamgateway?a=loadteam&teamid=${PLAYER.getTeamId()}">Back to Team</a>&nbsp;
                    <a href="/TheHunt/logout">logout</a>
                </td>
            </tr>        
            <tr>
                <td height="40" width="40%" valign="middle">Player Detail</td>
                <td height="40" width="60%" valign="middle"><font color="red">${EMSG}</font><font color="green">${MSG}</font></td>            
            </tr>
            <tr>
                <td height="40" width="40%" valign="middle">First Name:</td>
                <td height="40" width="60%" valign="middle"><input type="text" name="firstname" id="firstname" value="${PLAYER.getFirstName()}" /></td>
            </tr>
            <tr>
                <td height="40" width="40%" valign="middle">Last Name:</td>
                <td height="40" width="60%" valign="middle"><input type="text" name="lastname" id="lastname" value="${PLAYER.getLastName()}" /></td>
            </tr>
            <tr>
                <td height="40" width="40%" valign="middle">Phone Number:</td>
                <td height="40" width="60%" valign="middle"><input type="text" name="phone" id="phone" value="${PLAYER.getPhoneNumber()}" /></td>
            </tr>            
            <tr>
                <td height="40" width="40%" valign="middle">Email:</td>
                <td height="40" width="60%" valign="middle"><input type="text" name="email" id="email" value="${PLAYER.getEmail()}" /></td>
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