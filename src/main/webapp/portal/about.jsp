<%@ page language="java" pageEncoding="UTF-8"%>
<!-- <ul>
	<li>SyPro，是一个EasyUI整站示例系统(主要演示了权限控制)</li>
	<li>前台由EasyUI1.3.2编写，后台是JAVA编写，应用框架spring mvc+hibernate4+maven</li>
	<li>目前已经过测试通过的数据库有MySql5、Oracle10g、SqlServer2005</li>
	<li>Web容器测试通过的有Jetty和Tomcat7</li>
	<li>要求JDK1.6+</li>
	<li>优化了所有页面，使页面更加流畅，尽量减少了页面的请求</li>
	<li>Tab内使用iframe方式引入目标页面，但实现了全屏遮罩的效果</li>
	<li>权限控制更加严格</li>
	<li>稍微美化了一下页面，本人不会美工，审美较差，凑合看吧</li>
	<li>EasyUI升级到了1.3.3版本(需要大家测试兼容性)</li>
</ul>
 -->
<div style="margin-top:14px;">
	<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎进入资讯后台管理系统</p>
	 <style>
.lifangti {
	transform: rotateX(-33.5deg) rotateY(45deg);
	transform-origin: 50px 0px;
	transform-style: preserve-3d;
	-webkit-transform: rotateX(-33.5deg) rotateY(45deg);
	-webkit-transform-origin: 50px 0px;
	-webkit-transform-style: preserve-3d;
	-moz-transform: rotateX(-33.5deg) rotateY(45deg);
	-moz-transform-origin: 50px 0px;
	top: 50%;
	left: 50%;
	margin: -100px 0 0 -50px;
	position: absolute;
	display: block;
	animation: xuanzhuan 5s infinite ease;
	-moz-animation: xuanzhuan 5s infinite ease; /* Firefox */
	-webkit-animation: xuanzhuan 5s infinite ease; /* Safari ºÍ Chrome */
	-o-animation: xuanzhuan 5s infinite ease; /* Opera */
}

	.lifangti > .bgabox {
		width: 100px;
		height: 100px;
		position: absolute;
		opacity: 0.6;
	}

.leftm {
	background-color: #ff6a00;
	transform: rotateY(90deg) translateZ(-50PX);
	-moz-transform: rotateY(90deg) translateZ(-50PX);
	-webkit-transform: rotateY(90deg) translateZ(-50PX);
}

.rightm {
	background-color: #6faed9;
	transform: rotateY(90deg) translateZ(50px);
	-moz-transform: rotateY(90deg) translateZ(50px);
	-webkit-transform: rotateY(90deg) translateZ(50px);
}

.topm {
	transform: rotateX(90deg) translateZ(50px);
	-moz-transform: rotateX(90deg) translateZ(50px);
	-webkit-transform: rotateX(90deg) translateZ(50px);
	background-color: #ff0000;
	opacity: 1;
}

.bottomm {
	transform: rotateX(90deg) translateZ(-50px);
	-moz-transform: rotateX(90deg) translateZ(-50px);
	-webkit-transform: rotateX(90deg) translateZ(-50px);
	background-color: #52d538;
}

.beform {
	background-color: #b12d9e;
	transform: translateZ(50px);
	-moz-transform: translateZ(50px);
	-webkit-transform: translateZ(50px);
}

.afterm {
	background-color: #138fc6;
	transform: translateZ(-50px);
	-moz-transform: translateZ(-50px);
	-webkit-transform: translateZ(-50px);
}

@keyframes xuanzhuan {
	from {
		transform: rotateX(-33.5deg) rotateY(45deg);
		-webkit-transform: rotateX(-33.5deg) rotateY(45deg);
		-moz-transform: rotateX(-33.5deg) rotateY(45deg);
	}

	to {
		transform: rotateX(-33.5deg) rotateY(765deg);
		-webkit-transform: rotateX(-33.5deg) rotateY(765deg);
		-moz-transform: rotateX(-33.5deg) rotateY(765deg);
	}
}

.lifangtimin {
	transform-origin: 25px 0px;
	transform-style: preserve-3d;
	-webkit-transform-origin: 25px 0px;
	top: 50%;
	margin: -50px 0 0 -25px;
	position: absolute;
	display: block;
	transform: translateX(50px) translateY(75px) rotateY(0deg);
	-webkit-transform: translateX(50px) translateY(75px) rotateY(0deg);
	-moz-transform: translateX(50px) translateY(75px) rotateY(0deg);
	animation: xuanzhuanm 5s infinite ease;
	-moz-animation: xuanzhuanm 5s infinite ease; /* Firefox */
	-webkit-animation: xuanzhuanm 5s infinite ease; /* Safari ºÍ Chrome */
	-o-animation: xuanzhuanm 5s infinite ease; /* Opera */
}

	.lifangtimin > div {
		width: 50px;
		height: 50px;
		position: absolute;
		opacity: 1;
	}

.leftmm {
	background-color: #ff6a00;
	transform: rotateY(90deg) translateZ(-25px);
	-webkit-transform: rotateY(90deg) translateZ(-25px);
	-moz-transform: rotateY(90deg) translateZ(-25px);
}

.rightmm {
	background-color: #6faed9;
	transform: rotateY(90deg) translateZ(25px);
	-webkit-transform: rotateY(90deg) translateZ(25px);
	-moz-transform: rotateY(90deg) translateZ(25px);
}

.topmm {
	transform: rotateX(90deg) translateZ(25px);
	-webkit-transform: rotateX(90deg) translateZ(25px);
	-moz-transform: rotateX(90deg) translateZ(25px);
	background-color: #ff0000;
	opacity: 1;
}

.bottommm {
	transform: rotateX(90deg) translateZ(-25px);
	-webkit-transform: rotateX(90deg) translateZ(-25px);
	-moz-transform: rotateX(90deg) translateZ(-25px);
	background-color: #52d538;
}

.beformm {
	background-color: #b12d9e;
	transform: translateZ(25px);
	-webkit-transform: translateZ(25px);
	-moz-transform: translateZ(25px);
}

.aftermm {
	background-color: #138fc6;
	transform: translateZ(-25px);
	-webkit-transform: translateZ(-25px);
	-moz-transform: translateZ(-25px);
}

@keyframes xuanzhuanm {
	from {
		transform: translateX(50px) translateY(75px) rotateY(0deg);
		-webkit-transform: translateX(50px) translateY(75px) rotateY(0deg);
		-moz-transform: translateX(50px) translateY(75px) rotateY(0deg);
	}

	to {
		transform: translateX(50px) translateY(75px) rotateY(-1080deg);
		-webkit-transform: translateX(50px) translateY(75px) rotateY(-1080deg);
		-moz-transform: translateX(50px) translateY(75px) rotateY(-1080deg);
	}
}
</style>
    <div class="wrapper wrapper-content">
        <div class="row">
            <div class="lifangti">
	<div class="beform bgabox"></div>
	<div class="afterm bgabox"></div>
	<div class="leftm bgabox"></div>
	<div class="rightm bgabox"></div>
	<div class="topm bgabox"></div>
	<div class="bottomm bgabox"></div>
	
	<div class="lifangtimin">
		<div class="beformm"></div>
		<div class="aftermm"></div>
		<div class="leftmm"></div>
		<div class="rightmm"></div>
		<div class="topmm"></div>
		<div class="bottommm"></div>
	</div>
	
</div>
        </div>
    </div>
</div>
</ul>
 