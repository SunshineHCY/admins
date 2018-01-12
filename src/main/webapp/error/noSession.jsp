<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%-- ${msg}--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../common/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
var Common = {
		// 判断是否连接超时
		isAjaxSessiontimeout:function(data){
			if(null != data && 2 == data.code){
				//alert("会话已经超时，请重新登录！");
				window.location.href ='${ctx }"/userController/login';
				return true;
			}
			return false;
		}
	};
</script>
