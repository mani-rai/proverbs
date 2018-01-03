<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Probers - Sample Project</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css" />
    </head>

    <body>
        <div id="layout-container">
            <div id="header-container">
                <div id="brand-container">
                    <h1><a href="./"><spring:message code="label.app-name" /></a></h1>
                </div>
                <div id="nav-bar-container">
                    <ul>
                        <li><a href="#"><spring:message code="label.about_us" /></a></li>
                        <li><a href="#"><spring:message code="label.contact" /></a></li>
                    </ul>
                </div>
                <div id="translation-option-container">
                    <ul>
                        <li><a href="?lang=en"><spring:message code="label.english" /></a></li>
                        <li><a href="?lang=tr"><spring:message code="label.turkish" /></a></li>
                    </ul>
                </div>
            </div>
            <div id="content-container">
                <c:forEach items="${proverbs}" var="proverb">
                    <div class="proverb-container">
                        <a href="<c:out value="${proverb.id}"/>">
                            <h3><c:out value="${proverb.phrase}"/></h3>
                        </a>
                        <div class="proverb-tags-container">
                            <b><spring:message code="label.tags"/>:</b>
                            <c:forEach items="${proverb.tags}" var="tag">
                                <a href="#"><c:out value="${tag.name}"/></a>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <div id="footer-container">
                <spring:message code="label.copyright"/>
            </div>
        </div>
    </body>
</html>
