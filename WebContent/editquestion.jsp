<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>

<jsp:useBean id="Question" class="hunt.beans.Question" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="css/basic.css" id="thecss">
        <title>The Hunt Project</title>
    </head>
    <body>
        <center>
        <form name="frm" action="/TheHunt/questiongateway" method="post">
        <c:if test="${QUESTION.getId() == 0}">
        <input type="hidden" name="a" id="a" value="createquestion" />
        </c:if>
        <c:if test="${QUESTION.getId() > 0}">
        <input type="hidden" name="a" id="a" value="editquestion" />
        </c:if>        
        <input type="hidden" name="questionid" id="questionid" value="${QUESTION.getId()}" />
        <input type="hidden" name="locationid" id="locationid" value="${QUESTION.getLocationId()}" />
        <table width="80%">
            <tr>
                <td height="70" width="50%" valign="middle"><b>The Hunt Project</b></td>
                <td height="70" width="50%" valign="middle" align="right">
                    <a href="/TheHunt/locationgateway?a=loadlocation&locationid=${QUESTION.getLocationId()}">Back to Location</a>&nbsp;
                    <a href="/TheHunt/logout">Logout</a>
                </td>
            </tr>        
            <tr>
                <td height="20" width="50%" valign="middle">Question Detail</td>
                <td height="20" width="50%" valign="middle"><font color="red">${EMSG}</font><font color="green">${MSG}</font></td>            
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Question:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="question" id="question" value="${QUESTION.getQuestion()}" /></td>
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Answer:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="answer" id="answer" value="${QUESTION.getAnswer()}" /></td>
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Points:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="points" id="points" value="${QUESTION.getPoints()}" /></td>
            </tr>            
            <tr>
                <td height="20" width="50%" valign="middle">Order:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="questionorder" id="questionorder" value="${QUESTION.getQuestionOrder()}" /></td>
            </tr>            
            <tr>
                <td height="50" valign="middle" colspan="2">
                    <input type="submit" value="Submit" />
                </td>
            </tr>
        </table>
        </form>
        </center>
    </body>
</html>