<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>

<jsp:directive.page import="hunt.beans.Player" />
<jsp:useBean id="Team" class="hunt.beans.Team" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="css/basic.css" id="thecss">
        <title>The Hunt Project</title>
    </head>
    <body>
        <center>
        <form name="frm" action="/TheHunt/teamgateway" method="post">
        <c:if test="${TEAM.getId() == 0}">
        <input type="hidden" name="a" id="a" value="createteam" />
        </c:if>
        <c:if test="${TEAM.getId() > 0}">
        <input type="hidden" name="a" id="a" value="editteam" />
        </c:if>        
        <input type="hidden" name="teamid" id="teamid" value="${TEAM.getId()}" />
        <input type="hidden" name="huntid" id="huntid" value="${TEAM.getHuntId()}" />
        <table width="60%">
            <tr>
                <td height="50" width="40%" valign="middle">The Hunt Project</td>
                <td height="50" width="60%" valign="middle"><a href="/TheHunt/logout">logout</a></td>
            </tr>        
            <tr>
                <td height="40" width="40%" valign="middle">Team Detail</td>
                <td height="40" width="60%" valign="middle"><font color="red">${EMSG}</font><font color="green">${MSG}</font></td>            
            </tr>
            <tr>
                <td height="40" width="40%" valign="middle">Name:</td>
                <td height="40" width="60%" valign="middle"><input type="text" name="name" id="name" value="${TEAM.getName()}" /></td>
            </tr>
            <tr>
                <td height="40" width="40%" valign="middle">Password:</td>
                <td height="40" width="60%" valign="middle"><input type="password" name="password" id="password" value="${TEAM.getPassword()}"/></td>
            </tr>            
            <tr>
                <td height="60" valign="middle" colspan="2">
                    <input type="submit" value="Submit" />
                </td>
            </tr>
            <!-- end hunt edit section -->

            <c:if test="${TEAM.getId() > 0}">
            
            <tr><td height="40" width="100%" valign="middle"><hr/></td></tr>
            
            <!-- start player section -->
            <tr>
                <td height="50" width="40%" valign="middle">Players</td>
                <td height="50" width="60%" valign="middle"><a href="/TheHunt/playergateway?a=addplayer&huntid=${HUNT.getId()}">Add Team</a></td>
            </tr>
            <c:if test="${TEAM.getPlayers().size() == 0}">
            <tr><td height="50" valign="middle" colspan="2"><i>No Players Found</i></td></tr>
            </c:if>
            <c:if test="${TEAM.getPlayers().size() > 0}">
                <c:forEach var="player" items="${TEAM.getPlayers()}">
                <tr>
                    <td height="20" width="40%" valign="middle">
                        <a href="/TheHunt/playergateway?a=loadplayer&huntid=${HUNT.getId()}&teamid=${TEAM.getId()}&playerid=">${player.getFirstName()} ${player.getLastName()}</a>
                    </td>
                    <td height="20" width="40%" valign="middle" align="right">
                        <a href="/TheHunt/playergateway?a=deleteplayer&huntid=${HUNT.getId()}&teamid=${TEAM.getId()}&playerid=">Delete</a>
                    </td>
                </tr>
                </c:forEach>
            </c:if>                
            <!-- end player section -->
            
            </c:if>
            
        </table>
        </form>
        </center>
    </body>
</html>