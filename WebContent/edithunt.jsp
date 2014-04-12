<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>

<jsp:directive.page import="hunt.beans.Team" />
<jsp:directive.page import="hunt.beans.Location" />
<jsp:useBean id="Hunt" class="hunt.beans.Hunt" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="css/basic.css" id="thecss">
        <title>The Hunt Project</title>
    </head>
    <body>
        <center>
        <form name="frm" action="/TheHunt/huntgateway" method="post">
        <c:if test="${HUNT.getId() == 0}">
        <input type="hidden" name="a" id="a" value="createhunt" />
        </c:if>
        <c:if test="${HUNT.getId() > 0}">
        <input type="hidden" name="a" id="a" value="edithunt" />
        </c:if>        
        <input type="hidden" name="huntid" id="huntid" value="${HUNT.getId()}" />
        <input type="hidden" name="accountid" id="accountid" value="${HUNT.getAccountId()}" />
        <table width="60%">
            <tr>
                <td height="50" width="40%" valign="middle">The Hunt Project</td>
                <td height="50" width="60%" valign="middle"><a href="/TheHunt/logout">logout</a></td>
            </tr>        
            <tr>
                <td height="40" width="40%" valign="middle">Hunt Detail</td>
                <td height="40" width="60%" valign="middle"><font color="red">${EMSG}</font><font color="green">${MSG}</font></td>            
            </tr>
            <tr>
                <td height="40" width="40%" valign="middle">Name:</td>
                <td height="40" width="60%" valign="middle"><input type="text" name="name" id="name" value="${HUNT.getName()}" /></td>
            </tr>
            <tr>
                <td height="40" width="40%" valign="middle">Run Date:</td>
                <td height="40" width="60%" valign="middle"><input type="date" name="rundate"></td>
            </tr>            
            <tr>
                <td height="60" valign="middle" colspan="2">
                    <input type="submit" value="Submit" />
                </td>
            </tr>
            <!-- end hunt edit section -->

            <c:if test="${HUNT.getId() > 0}">
            
            <tr><td height="40" width="100%" valign="middle"><hr/></td></tr>
            
            <!-- start team section -->
            <tr>
                <td height="50" width="40%" valign="middle">Teams</td>
                <td height="50" width="60%" valign="middle"><a href="/TheHunt/teamgateway?a=createnewteam&huntid=${HUNT.getId()}">Add Team</a></td>
            </tr>
            <c:if test="${HUNT.getTeams().size() == 0}">
            <tr><td height="50" valign="middle" colspan="2"><i>No Teams Found</i></td></tr>
            </c:if>
            <c:if test="${HUNT.getTeams().size() > 0}">
                <c:forEach var="team" items="${HUNT.getTeams()}">
                <tr><td height="50" valign="middle" colspan="2"><a href="/TheHunt/teamgateway?a=loadteam&huntid=${HUNT.getId()}&teamid=${team.getId()}">${team.getName()}</a></td></tr>
                </c:forEach>
            </c:if>                
            <!-- end team section -->
            
            <tr><td height="40" width="100%" valign="middle"><hr/></td></tr>
            
            <!-- start location section -->
            <tr>
                <td height="50" width="40%" valign="middle">Locations</td>
                <td height="50" width="60%" valign="middle"><a href="/TheHunt/locationgateway?a=addlocation&huntid=${HUNT.getId()}">Add Location</a></td>
            </tr>
            <c:if test="${HUNT.getTeams().size() == 0}">
            <tr><td height="50" valign="middle" colspan="2"><i>No Teams Found</i></td></tr>
            </c:if>
            <c:if test="${HUNT.getLocations().size() > 0}">
                <c:forEach var="location" items="${HUNT.getLocations()}">
                <tr><td height="50" valign="middle" colspan="2"><a href="/TheHunt/locationgateway?a=loadlocation&huntid=${HUNT.getId()}&locationid=${location.getId()}">${team.getName()}</a></td></tr>
                </c:forEach>
            </c:if>              
            <!-- end location section -->
            
            </c:if>
            
        </table>
        </form>
        </center>
    </body>
</html>