<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jstl/fmt" %>

<jsp:directive.page import="hunt.beans.Question" />
<jsp:useBean id="Location" class="hunt.beans.Location" scope="session" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <link rel="stylesheet" type="text/css" href="css/basic.css" id="thecss">
        <title>The Hunt Project</title>
    </head>
    <body>
        <center>
        <form name="frm" action="/TheHunt/locationgateway" method="post">
        <c:if test="${LOCATION.getId() == 0}">
        <input type="hidden" name="a" id="a" value="createlocation" />
        </c:if>
        <c:if test="${LOCATION.getId() > 0}">
        <input type="hidden" name="a" id="a" value="editlocation" />
        </c:if>        
        <input type="hidden" name="locationid" id="locationid" value="${LOCATION.getId()}" />
        <input type="hidden" name="huntid" id="huntid" value="${LOCATION.getHuntId()}" />
        <table width="80%">
            <tr>
                <td height="70" width="50%" valign="middle"><b>The Hunt Project</b></td>
                <td height="70" width="50%" valign="middle" align="right">
                    <a href="/TheHunt/huntgateway?a=loadhunt&huntid=${LOCATION.getHuntId()}">Back to Hunt</a>&nbsp;
                    <a href="/TheHunt/logout">Logout</a>
                </td>
            </tr>        
            <tr>
                <td height="20" width="50%" valign="middle">Location Detail</td>
                <td height="20" width="50%" valign="middle"><font color="red">${EMSG}</font><font color="green">${MSG}</font></td>            
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Name:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="name" id="name" value="${LOCATION.getName()}" /></td>
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Code:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="code" id="code" value="${LOCATION.getCode()}" /></td>
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Key:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="key" id="key" value="${LOCATION.getKey()}" /></td>
            </tr>            
            <tr>
                <td height="20" width="50%" valign="middle">Address:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="address" id="address" value="${LOCATION.getAddress()}" /></td>
            </tr>            
            <tr>
                <td height="20" width="50%" valign="middle">Phone Number:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="phone" id="phone" value="${LOCATION.getPhoneNumber()}" /></td>
            </tr>
                                    <tr>
                <td height="20" width="50%" valign="middle">Special Location Id:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="speciallocationid" id="speciallocationid" value="${LOCATION.getSpecialLocationId()}" /></td>
            </tr>
            <tr>
                <td height="20" width="50%" valign="middle">Has Special:</td>
                <td height="20" width="50%" valign="middle"><input type="text" name="address" id="address" value="${LOCATION.getHasSpecial()}" /></td>
            </tr>            
            
            <tr>
                <td height="50" valign="middle" colspan="2">
                    <input type="submit" value="Submit" />
                </td>
            </tr>
            <!-- end location section -->
            
             <c:if test="${LOCATION.getId() > 0}">
            
            <tr><td height="40" width="100%" valign="middle" colspan="2"><hr/></td></tr>
            
            <!-- start question section -->
            <tr>
                <td height="20" width="50%" valign="middle">Questions</td>
                <td height="20" width="50%" valign="middle" align="right"><a href="/TheHunt/questiongateway?a=createnewquestion&locationid=${LOCATION.getId()}&huntid=${LOCATION.getHuntId()}">Add Question</a></td>
            </tr>
            <c:if test="${LOCATION.getQuestions().size() == 0}">
            <tr><td height="20" valign="middle" colspan="2"><i>No Questions Found</i></td></tr>
            </c:if>
            <c:if test="${LOCATION.getQuestions().size() > 0}">
                <c:forEach var="question" items="${LOCATION.getQuestions()}">
                <tr>
                    <td height="20" width="50%" valign="middle">
                        ${question.getQuestionOrder()}.&nbsp;<a href="/TheHunt/questiongateway?a=loadquestion&huntid=${LOCATION.getHuntId()}&locationid=${LOCATION.getId()}&questionid=${question.getId()}">${question.getQuestion()}</a>&nbsp;(${question.getPoints()})
                    </td>
                    <td height="20" width="50%" valign="middle" align="right">
                        <a href="/TheHunt/questiongateway?a=deletequestion&huntid=${LOCATION.getHuntId()}&locationid=${LOCATION.getId()}&questionid=${question.getId()}">Delete</a>
                    </td>
                </tr>
                </c:forEach>
            </c:if>              
            <!-- end question section -->    
            
            </c:if>        
            
        </table>
        </form>
        </center>
    </body>
</html>