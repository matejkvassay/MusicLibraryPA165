<%-- 
    Document   : detail
    Created on : 27.11.2014, 23:05:43
    Author     : Horak
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<my:layout title="${song.title} - details">
    <jsp:attribute name="body">
        <div id="detail">
            <h1>${song.title}</h1>
             <sec:authorize access="hasAnyRole('ADMIN','USER')">
                <form method="get" action="${pageContext.request.contextPath}/song/update/${song.id}">
                    <input type="submit" value="<fmt:message key='song.list.edit'/>">
                </form>
             </sec:authorize>
            <br>
            
            <b><fmt:message key="song.detail.bitrate"/>: </b><c:out value="${song.bitrate}"/><br>
            <b><fmt:message key="song.detail.genre"/>: </b>
                <a href="${pageContext.request.contextPath}/genre/${genre.id}"><c:out value="${genre.name}"/></a>
                <br>
            <b><fmt:message key="song.detail.musicianName"/>: </b>
                <a href="${pageContext.request.contextPath}/musician/${musician.id}"><c:out value="${musician.name}"/></a>
                <br>
            <b><fmt:message key="song.detail.album"/>: </b>
                <a href="${pageContext.request.contextPath}/album/${album.id}"><c:out value="${album.title}"/></a>
                <br>
            <b><fmt:message key="song.detail.positionInAlbum"/>: </b><c:out value="${song.positionInAlbum}"/><br>
            <b><fmt:message key="song.detail.commentary"/>: </b><c:out value="${song.commentary}"/><br>
            
        </div>
    </jsp:attribute>
</my:layout>
