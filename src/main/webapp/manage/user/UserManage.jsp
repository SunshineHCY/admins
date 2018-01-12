<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息管理</title>

<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}/userMgr/list',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			//idField : 'umIdN',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			//sortName : 'name',
			//sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			singleSelect : true,
			frozenColumns : [ [ {} ] ],
			nowrap : false,
			columns : [ [ {
				field : 'userId',
				title : '用户ID',
				width : 60
			},{
				field : 'uiNickname',
				title : '用户昵称',
				width : 60
			},{
				field : 'uiRegMobile',
				title : '注册手机号',
				width : 60
			},{
				field : 'inserttime',
				title : '注册时间',	
				width : 60,
				formatter : function(value, rec, index) {
					return (value)?value.substr(0,10):'-';
				}
			},{
				field : 'uiTerminalType',
				title : '注册终端',
				width : 60,
				formatter : function(value, rec, index) {
					if(value== '1'){
						return 'Android';
					}else{
						return 'IOS';
					}
				}
			},{
				field : 'uiGoldcoinNum',
				title : '持有金币数',
				width : 60
			}] ],
			toolbar : '#toolbar', 	
			onLoadSuccess : function(data) {
				console.log(data);
				 if (data.rows&&data.rows.length>=0) {
					$('#totalCount').text(data.summary.totalCount);
				}else{
					$('#totalCount').text('0');
				}
				$('#usermanageForm table').show();
				parent.$.messager.progress('close'); 
			},
			onRowContextMenu : function(e, rowIndex, rowData) {}
		});
	});

	function serchUsermanage() {
		dataGrid.datagrid('load', $.serializeObject($('#usermanageForm')));
	}
	function cleanFuna() {
		$('#usermanageForm input').val('');
		$('#usermanageForm select ').val('');
		dataGrid.datagrid('load', {});
	}
	
	function exportExcel() {
		var userId = $("input[name='userId']").val();
		var nickName = $("input[name='nickName']").val();
		var mobile = $("input[name='mobile']").val();
		var beginDate = $("input[name='beginDate']").val();
		var endDate = $("input[name='endDate']").val();
		var terminalType = $("input[name='terminalType']").val();
		
		$.ajax({
			type : "post",
			url :'${ctx}/userMgr/export',
			data : {
				'userId':userId,
				'nickName':nickName,
				'mobile':mobile,
				'beginDate':beginDate,
				'endDate':endDate,
				'terminalType':terminalType
			},
			dataType : "text",
			success : function(result) {
				console.log(result);
				var totalRec = parseInt(result.replace('"',''));
				if(totalRec==0){
					$.messager.alert('提示', '记录数为0，无需下载', 'info');
					return;
				}else if(totalRec>50000){
					$.messager.alert('提示', '超过下载限制，最多五万条！', 'info');
					return;
				}else{
					window.open('${ctx}/userMgr/export?userId='+userId+'&nickName='+nickName+'&mobile='+mobile+'&beginDate='
							+beginDate+'&endDate='+endDate+'&terminalType='+terminalType);
				}
			},
			error : function() {
				$.messager.alert('提示', '服务器遇到错误，请稍候重试', 'error');
			}
		});
	} 
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 140px; overflow: hidden;">
			<form id="usermanageForm">
				<table  style="width: 600px;" cellpadding="1" cellspacing="1" class="formtable">
					<tr>
						<td align="right" width="15%" nowrap><label>用户ID：</label></td>
						<td><input name= "userId" style="width: 150px;"/></td>
						<td align="right" width="15%" nowrap><label>用户昵称：</label></td>
						<td><input name= "nickName" style="width: 150px;"/></td>
						<td align="right" width="15%" nowrap><label>注册手机号：</label></td>
						<td><input name= "mobile" style="width: 150px;"/></td>
					</tr>
					<tr>
						<td align="right" width="15%" nowrap><label >注册日期：</label></td>
						<td colspan="3">
							<input  name="beginDate" class="inputxt" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px;"/>
						至
							<input name="endDate" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px;"/>					
						</td>
						<td align="right" width="15%" nowrap><label>终端类型：</label></td>
						<td>
							<select name="terminalType" style="width: 100px;">
							    <option value="" selected="selected">全部</option>
								<option value="1" >Android</option>
								<option value="2" >IOS</option>
							</select>
						</td>	
					</tr>
					<tr>
						<td colspan="2" style="font-size: 15px;">合计：<span id="totalCount">0</span>个用户</td> 
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="serchUsermanage();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFuna();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/userMgr/export')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bullet_arrow_down'" onclick="exportExcel();">下载</a>
		</c:if>
	</div>
</body>
</html>