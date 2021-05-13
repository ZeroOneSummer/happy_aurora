/*工厂函数*/
$(function () {
    $("#form_table p").click(function () {
       alert("Yes, I am a mermaid.")
    });

    $("input[type=button]").click(function () {
        var url = getContextPath("/user/loginOut");
        $.get(url);
        location.reload();
    })
});

/*获取上下文*/
function getContextPath(url) {
    var contextPath = document.location.href;
    var index = contextPath.lastIndexOf("/");
    contextPath = contextPath.substr(0, index);
    return url ? contextPath.concat(url.trim()) : contextPath;
}