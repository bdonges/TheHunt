<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>

<jsp:directive.page import="java.util.List" />
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
        <table width="70%">
            <tr><td height="50" valign="middle" colspan="2">The Hunt Project</td></tr> 
            <tr><td height="50" valign="middle" colspan="2">Hunts</td></tr>
            <tr>
                <td height="50" valign="middle" colspan="8">
                    ${EMSG}<br/><br/>
                    ${MSG}              
                </td>
            </tr>            
        </table>
        </center>
	</body>
</html>