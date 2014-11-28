<%-- 
    Document   : list
    Created on : Nov 25, 2014, 3:01:11 PM
    Author     : Matej Bordáč
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message var="title" key="album.list.title"/>
<my:layout title="${title}">
    <jsp:attribute name="body">
        
        <p><fmt:message key="album.list.all"/></p>
        
        <form method="get" action="${pageContext.request.contextPath}/album/new">
            <input type="submit" value="<fmt:message key='album.list.add'/>">
        </form>
        
        <table class="basic">
            <tr>
                <th></th>
                <th><fmt:message key="album.title"/></th>
                <th><fmt:message key="album.musician.name"/></th>
                <th><fmt:message key="album.releaseDate"/></th>
                <th></th>
                <th></th>
            </tr>
            <c:forEach items="${albums}" var="album">
                <tr>
                    
                    <td><img src="${album.albumArt}" height="32" width="32" onerror="this.src='${pageContext.request.contextPath}/resources/images/default-album.png'"></td>
                    
                    <td><a href="${pageContext.request.contextPath}/album/${album.id}"><c:out value="${album.title}"/></a></td>
                    <td><a href="${pageContext.request.contextPath}/musician/${album.musician.id}"><c:out value="${album.musician.name}"/></a></td>
                    <td><c:out value="${album.dateOfRelease}"/></td>
                    <td>
                        <form method="get" action="${pageContext.request.contextPath}/album/update/${album.id}">
                            <input type="submit" value="<fmt:message key='album.edit.button'/>">
                        </form>
                    </td>
                    <td>
                        <form method="post" action="${pageContext.request.contextPath}/album/delete/${album.id}">
                            <input type="submit" value="<fmt:message key='album.delete.button'/>">
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
                <hr>
                <p><i><fmt:message key='album.deletewarning'/></i></p>
        
    </jsp:attribute>
</my:layout>