<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%-- 页面国际化 --%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- 页面国际化 --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%-- js国际化 --%>
<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%-- java代码 --%>
<%
    String contextPath = request.getContextPath();
    Locale locale = LocaleContextHolder.getLocale();
%>
<html>
    <script src="<%=contextPath%>/commons/jquery-3.6.0.min.js" type="text/javascript"></script>
    <script src="<%=contextPath%>/commons/jquery.i18n.properties.js" type="text/javascript"></script>
    <script type="text/javascript">
        var ROOT = '<%=contextPath%>';
        var CURRENT_LANG = '<%=locale%>';

        //初始化 i18n 插件
        $.i18n.properties({
            path: ROOT + '/i18n/',      //这里表示访问路径
            name: ['zero', 'aurora'],   //文件名前缀，单个 'zero'
            language: CURRENT_LANG,     //当前环境语言 例如 en_US
            mode: 'map'                 //默认值，both单个翻译文件，map多组翻译文件
        });

        //初始化 i18n 函数
        function i18n(_key) {
            try {
                return $.i18n.prop(_key);
            } catch (e) {
                return _key;
            }
        }

        //打印当前语言
        console.log(CURRENT_LANG);
    </script>
</html>
