<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/header.jsp" %>
<html>
    <head>
        <meta charset="utf-8" />
        <title>JSP国际化页面</title>
    </head>
    <body>
        <h2>欢迎您 <span style="color: blueviolet;">${userNamer}</span> 老师！</h2>
        <p>
            <a href="?lang=zh_CN">中文</a>
            <a href="?lang=en_US">English</a>
        </p>
        <input type="button" onclick="testI18N('ty.zll')" value="js国际化测试"/>
        <h3>spring标签组：</h3>
        <h4 style="color: red;"><spring:message code="ty.zll"/></h4>
        <h4 style="color: red;"><spring:message code="aurora.title"/></h4>
        <h3>jstl标签组：</h3>
        <h4 style="color: red;"><fmt:message key="ty.test"/></h4>
        <h4 style="color: red;"><fmt:message key="aurora.title"/></h4>
    </body>
    <script src="<%=contextPath%>/js/demo.js" type="text/javascript"></script>
</html>