<%-- 
    Document   : detail
    Created on : Nov 26, 2014, 7:37:50 PM
    Author     : Matej Kvassay
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:layout title="${genre.name} - details">
    <jsp:attribute name="body">
        <div id="detail">
            <h1><c:out value="${genre.name}"/></h1>
             <sec:authorize access="hasAnyRole('ADMIN','USER')">
                <form method="get" action="${pageContext.request.contextPath}/genre/update/${genre.id}">
                    <input type="submit" value="<fmt:message key='genre.edit.button'/>">
                </form>
             </sec:authorize>
            <br>
            
            <b><fmt:message key="genre.detail.description"/>: </b>
            ${genre.description}<br>

            <b><fmt:message key="genre.detail.songs"/>:</b>
            <table class="tracklist">
                     <tr>
                         <th><fmt:message key="genre.detail.musician"/></th>
                         <th><fmt:message key="genre.detail.song"/></th>
                         
                     </tr>
                     <c:forEach items="${songs}" var="song">
                         <tr>
                             <td>
                                 <a href="${pageContext.request.contextPath}/musician/${song.musician.id}"><c:out value="${song.musician.name}"/></a>
                             </td>
                             <td>
                                 <a href="${pageContext.request.contextPath}/song/${song.id}"><c:out value="${song.title}"/></a>
                             </td>
                         </tr>
                     </c:forEach>
             </table>
        </div>
    </jsp:attribute>
</my:layout>
