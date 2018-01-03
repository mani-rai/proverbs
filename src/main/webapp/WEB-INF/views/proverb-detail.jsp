<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Proverbs - Sample Project</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css" />
        <script src="${pageContext.request.contextPath}/resources/proverbs.js" type="text/javascript"></script>
    </head>
    <body>
        <!--Including Facebook JS SDK-->
        <div id="fb-root"></div>
        <script>(function (d, s, id) {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id))
                    return;
                js = d.createElement(s);
                js.id = id;

                // reading cookie for language.
                var language = 'en_US';
                if (getCookie("proverbs-app") == 'tr') {
                    language = 'tr_TR';
                }

                js.src = 'https://connect.facebook.net/' + language + '/sdk.js#xfbml=1&version=v2.11';
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));</script>

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
                <div class="proverb-container">
                    <h3><c:out value="${proverb.phrase}"/></h3>
                    <div class="meaning-container">
                        <b><spring:message code="label.meaning"/>:</b>
                        <p>${proverb.meaning}</p>
                    </div>
                    <div class="proverb-tags-container">
                        <b><spring:message code="label.tags"/>:</b>
                        <c:forEach items="${proverb.tags}" var="tag">
                            <a href="#">${tag.name}</a>
                        </c:forEach>
                    </div>
                    <div class="socia-btn-container">
                        <b><spring:message code="label.share"/>:</b>
                        </br>
                        <div class="fb-like" data-href="https://developers.facebook.com/docs/plugins/" data-width="70" data-layout="button" data-action="like" data-size="small" data-show-faces="false" data-share="false"></div>
                    </div>
                    <div id="comments-container">
                        <b><spring:message code="label.comments"/>:</b>
                        <c:forEach items="${proverb.comments}" var="comment">
                            <div class="comment-container">
                                <p class="comment">${comment.comment}</p>
                                <div class="commenter-container">${comment.name}</div>
                            </div>
                        </c:forEach>
                    </div>
                    <div id="comment-form-container">
                        <form id="commentForm">
                            <input type="hidden" id="proverbId" value="<c:out value="${proverb.id}"/>"/>
                            <table>
                                <tr>
                                    <td><spring:message code="label.comment"/>:</td>
                                    <td><textarea id="comment"></textarea></td>
                                </tr>
                                <tr>
                                    <td><spring:message code="label.name"/>:</td>
                                    <td><input id="name"/></td>
                                </tr>
                                <tr>
                                    <td><spring:message code="label.email"/>:</td>
                                    <td><input id="email"/></td>
                                </tr>
                                <tr>
                                    <td colspan="2"><input type="button" id="submitBtn" onclick="postComment()" value="<spring:message code="label.post_comment"/>"/></td>
                                </tr>
                            </table>
                        </form>
                    </div> 
                </div>
            </div>
        </div>
        <div id="footer-container">
            <spring:message code="label.copyright"/>
        </div>
    </div>
</body>
</html>
