<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript" src="${ctx}/jslib/extBrowser.js" charset="utf-8"></script>
<link rel="shortcut icon" href="https://cdn.paicaifu.com/wealth/img/favicon.ico" type="image/x-icon" />
<!-- 引入my97日期时间控件 -->
<script type="text/javascript" src="${ctx}/jslib/My97DatePicker4.8b3/My97DatePicker/WdatePicker.js" charset="utf-8"></script>

<!-- 引入kindEditor插件 -->
<link rel="stylesheet" href="${ctx}/jslib/kindeditor-4.1.7/themes/default/default.css">
<script type="text/javascript" src="${ctx}/jslib/kindeditor-4.1.7/kindeditor-all-min.js" charset="utf-8"></script>

<!-- 引入jQuery -->
<script src="${ctx}/jslib/jquery-1.8.3.js" type="text/javascript" charset="utf-8"></script>

<!-- 引入bootstrap样式 -->
<link href="${ctx}/jslib/bootstrap-2.3.1/css/bootstrap.min.css" rel="stylesheet" media="screen">
<!-- <script charset="utf-8" src="${ctx}/jslib/bootstrap-2.3.1/js/bootstrap.min.js" charset="utf-8"></script> -->

<!-- 引入EasyUI -->
<link id="easyuiTheme" rel="stylesheet" href="${ctx}/jslib/jquery-easyui-1.3.3/themes/<c:out value="${cookie.easyuiThemeName.value}" default="bootstrap"/>/easyui.css" type="text/css">
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui-1.3.3/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>
<!-- 修复EasyUI1.3.3中layout组件的BUG -->
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui-1.3.3/plugins/jquery.layout.js" charset="utf-8"></script>

<!-- 引入EasyUI Portal插件 -->
<link rel="stylesheet" href="${ctx}/jslib/jquery-easyui-portal/portal.css" type="text/css">
<script type="text/javascript" src="${ctx}/jslib/jquery-easyui-portal/jquery.portal.js" charset="utf-8"></script>

<!-- 扩展EasyUI -->
<script type="text/javascript" src="${ctx}/jslib/extEasyUI.js?v=20130516" charset="utf-8"></script>

<!-- 扩展EasyUI Icon -->
<link rel="stylesheet" href="${ctx}/style/extEasyUIIcon.css?v=20130510" type="text/css">

<!-- 扩展jQuery -->
<script type="text/javascript" src="${ctx}/jslib/extJquery.js?v=20130506" charset="utf-8"></script>

	