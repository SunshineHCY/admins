<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../common/taglib.jsp"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<meta charset="utf-8" />
<link rel="shortcut icon"
	href="https://cdn.paicaifu.com/wealth/img/favicon.ico"
	type="image/x-icon" />
<title>行业智慧资讯平台</title>
<link rel="stylesheet" type="text/css" href="${ctx }/style/style2.0.css">
<style type="text/css">
ul li {
	font-size: 30px;
	color: #2ec0f6;
}

.tyg-div {
	z-index: -1000;
	float: left;
	position: absolute;
	left: 5%;
	top: 20%;
}

.tyg-p {
	font-size: 14px;
	font-family: 'microsoft yahei';
	position: absolute;
	top: 135px;
	left: 60px;
}

.tyg-div-denglv {
	z-index: 1000;
	float: right;
	position: absolute;
	right: 3%;
	top: 10%;
}

.tyg-div-form {
	background-color: #23305a;
	width: 300px;
	height: auto;
	margin: 120px auto 0 auto;
	color: #2ec0f6;
}

.tyg-div-form form {
	padding: 10px;
}

.tyg-div-form form input{
	width: 270px;
	height: 30px;
	margin: 25px 10px 0px 0px;
}

.tyg-div-form form button {
	cursor: pointer;
	width: 270px;
	height: 44px;
	margin-top: 25px;
	padding: 0;
	background: #2ec0f6;
	-moz-border-radius: 6px;
	-webkit-border-radius: 6px;
	border-radius: 6px;
	border: 1px solid #2ec0f6;
	-moz-box-shadow: 0 15px 30px 0 rgba(255, 255, 255, .25) inset, 0 2px 7px
		0 rgba(0, 0, 0, .2);
	-webkit-box-shadow: 0 15px 30px 0 rgba(255, 255, 255, .25) inset, 0 2px
		7px 0 rgba(0, 0, 0, .2);
	box-shadow: 0 15px 30px 0 rgba(255, 255, 255, .25) inset, 0 2px 7px 0
		rgba(0, 0, 0, .2);
	font-family: 'PT Sans', Helvetica, Arial, sans-serif;
	font-size: 14px;
	font-weight: 700;
	color: #fff;
	text-shadow: 0 1px 2px rgba(0, 0, 0, .1);
	-o-transition: all .2s;
	-moz-transition: all .2s;
	-webkit-transition: all .2s;
	-ms-transition: all .2s;
}

.z-err {
	font-size: 14px;
	color: #EF6C6B;
	margin: 5px auto;
}
</style>
<body>
	<div class="tyg-div">
		<ul>
			<li>让</li>&nbsp;
			<li><div style="margin-left: 20px;">数</div></li>&nbsp;
			<li><div style="margin-left: 40px;">据</div></li>&nbsp;
			<li><div style="margin-left: 60px;">改</div></li>&nbsp;
			<li><div style="margin-left: 80px;">变</div></li>&nbsp;
			<li><div style="margin-left: 100px;">生</div></li>&nbsp;
			<li><div style="margin-left: 120px;">活</div></li>
		</ul>
	</div>
	<div id="contPar" class="contPar">
		<div id="page1" style="z-index: 1;">
			<div class="title0">
				行业智慧资讯平台<img src="https://cdn.paicaifu.com/wealth/img/favicon.ico" />
			</div>
			<div class="title1">股市、财富、金融、公共安全、大数据</div>
			<div class="imgGroug">
				<ul>
					<img alt="" class="img0 png" src="${ctx }/style/images/page1_0.png">
					<img alt="" class="img1 png" src="${ctx }/style/images/page1_1.png">
					<img alt="" class="img2 png" src="${ctx }/style/images/page1_2.png">
				</ul>
			</div>
			<img alt="" class="img3 png" src="${ctx }/style/images/page1_3.jpg">
		</div>
	</div>
	<div class="tyg-div-denglv">
		<div class="tyg-div-form">
			 <form  id="loginForm"  method="post" >
            <h3>登录<span style="font-size:16px;display:inline-block;padding-left:10px">欢迎访问  财富资讯</span></h2>
            <div class="body-content ">
               <!--  <p>jeeadmin欢迎您！</p> -->
               <div style="margin:5px 0px;">
				<input  name="name" id="name"  type="text" placeholder="请输入用户名" class="easyui-validatebox span2" data-options="required:true"/>
			</div>
			<div style="margin:5px 0px;">
		<input name="pwd"  id="pwd" type="password"  placeholder="请输入密码" class="easyui-validatebox span2" data-options="required:true">
			</div>
              
               <p id="err" class="z-err">${err}</p> 
				<button type="button" style="margin-top: 10px;"
					onclick="javascript:login();">登录 <i class="fa fa-chevron-circle-right"></i></button>
            </div>
        </form>
			
		</div>
	</div>


	<script type="text/javascript" src="${ctx }/jslib/com.js"></script>


	<script type="text/javascript">
		//点击图片刷新验证码
		function login() {
			$("#err").html("");
			var name = $("#name").val();
			if ("" == name) {
				$("#errDiv").show();
				$("#err").html("用户名不能为空");
				$("#name").focus();
				return;
			}
			var pwd = $("#pwd").val();
			if ("" == pwd) {
				$("#err").html("密码不能为空");
				$("#pwd").focus();
				return;
			}
			var data = "name=" + name + "&pwd=" + pwd;
			$.post('${ctx}/userController/login',
							$('#loginForm').serialize(),
							function(result) {
								if (result.success) {
									window.location.href = '${ctx}/manage/Application.do';
								} else {
									$("#err").html(result.msg);
									return;
								}
								parent.$.messager.progress('close');
							}, "JSON");
		}

		$(function() {
			$("#loginForm").keydown(function(e) {
				var e = e || event, keycode = e.which || e.keyCode;
				if (keycode == 13) {
					login();
				}
			})
		});

		jQuery(function($) {
			$(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			});
		});
	</script>
	<!--[if IE 6]>
<script language="javascript" type="text/javascript" src="./script/ie6_png.js"></script>
<script language="javascript" type="text/javascript">
DD_belatedPNG.fix(".png");
</script>
<![endif]-->


</body>
</html>