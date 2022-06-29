<!DOCTYPE html>
<%@ page language="java" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
    <head>
        <meta charset="utf-8" />
        <title>JSP页面</title>
    </head>
    <body>
        <h2 style="color: red;"><spring:message code="ty.zll"/></h2>
        <p>
            <a href="?lang=zh_CN">中文</a>
            <a href="?lang=en_US">English</a>
        </p>
    </body>
</html>