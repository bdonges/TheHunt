<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>

<jsp:directive.page import="java.util.Vector" />
<jsp:directive.page import="hunt.beans.Hunt" />
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
        <table width="80%">
            <tr>
                <td height="50" width="50%" valign="middle"><b>The Hunt Project</b></td>
                <td height="50" width="50%" valign="middle" align="right"><a href="/TheHunt/logout">Logout</a></td>
            </tr>        
            <tr>
                <td height="50" width="50%" valign="middle">Welcome back ${ACCOUNT.getFirstName()}</td>
                <td height="50" width="50%" valign="middle" align="right"><a href="/TheHunt/accountgateway?a=loadaccount&accountid=${ACCOUNT.getId()}">Edit Account Settings</a></td>
            </tr>
            <tr>
                <td height="50" width="50%" valign="middle">Hunts</td>
                <td height="50" width="50%" valign="middle" align="right"><a href="/TheHunt/huntgateway?a=createnewhunt&accountid=${ACCOUNT.getId()}">Create Hunt</a></td>
            </tr>

            <c:if test="${ACCOUNT.getHunts().size() == 0}">
            <tr><td height="50" valign="middle" colspan="2"><i>No Hunts Found</i></td></tr>
            </c:if>

            <c:if test="${ACCOUNT.getHunts().size() > 0}">
                <c:forEach var="hunt" items="${ACCOUNT.getHunts()}">
                <tr>
                    <td height="20" width="50%" valign="middle">
                        <a href="/TheHunt/huntgateway?a=loadhunt&accountid=${ACCOUNT.getId()}&huntid=${hunt.getId()}">${hunt.getName()}</a>
                    </td>
                    <td height="20" width="50%" valign="middle" align="right">
                        <a href="/TheHunt/huntgateway?a=starthunt&accountid=${ACCOUNT.getId()}&huntid=${hunt.getId()}">Start</a>&nbsp;
                        <a href="/TheHunt/huntgateway?a=deletehunt&accountid=${ACCOUNT.getId()}&huntid=${hunt.getId()}">Delete</a>
                    </td>                    
                </tr>
                </c:forEach>
            </c:if>    
                    
                    
        </table>
        </center>
	</body>
</html>