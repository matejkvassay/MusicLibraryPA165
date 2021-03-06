<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<fmt:message var="title" key="album.search.title"/>
<my:layout title="${title}">
    <jsp:attribute name="body">
        
        <p id='listTitle'><fmt:message key="search.result.for"/><c:out value=" '${q}'"/></p>
        
        <c:if test="${albums.isEmpty()}">
            <span><fmt:message key="album.search.none" /></span>
        </c:if>
        
        <c:if test="${!albums.isEmpty()}">
            <table class="basic">
                <tr>
                    <th></th>
                    <th><fmt:message key="album.title"/></th>
                    <th><fmt:message key="album.musician.name"/></th>
                    <th><fmt:message key="album.releaseDate"/></th>
                     <sec:authorize access="hasAnyRole('ADMIN','USER')">
                        <th></th>
                        <th></th>
                     </sec:authorize>
                </tr>
                <c:forEach items="${albums}" var="album">
                    <tr>
                        <td><img src="${album.albumArt}" height="32" width="32" onerror="this.src='${pageContext.request.contextPath}/resources/images/default-album.png'"></td>
                        <td><a href="${pageContext.request.contextPath}/album/${album.id}"><c:out value="${album.title}"/></a></td>
                        <td><a href="${pageContext.request.contextPath}/musician/${album.musician.id}"><c:out value="${album.musician.name}"/></a></td>
                        <td><c:out value="${album.dateOfRelease}"/></td>
                         <sec:authorize access="hasAnyRole('ADMIN','USER')">
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
                         </sec:authorize>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
        <hr>
        <sec:authorize access="hasAnyRole('ADMIN','USER')">
            <p><i><fmt:message key='album.deletewarning'/></i></p>
        </sec:authorize>
    </jsp:attribute>
</my:layout>