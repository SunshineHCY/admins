<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户管理</title>
<c:if test="${fn:contains(sessionInfo.resourceList, '/userController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/userController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/userController/grantPage')}">
	<script type="text/javascript">
		$.canGrant = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/userController/editPwdPage')}">
	<script type="text/javascript">
		$.canEditPwd = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${ctx}/userController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'name',
			sortOrder : 'asc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			frozenColumns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				checkbox : true
			}, {
				field : 'name',
				title : '登录名称',
				width : 80,
				sortable : true
			} ] ],
			columns : [ [ {
				field : 'pwd',
				title : '密码',
				width : 60,
				formatter : function(value, row, index) {
					return '******';
				}
			}, {
				field : 'createdatetime',
				title : '创建时间',
				width : 150,
				sortable : true
			}, {
				field : 'modifydatetime',
				title : '最后修改时间',
				width : 150,
				sortable : true
			}, {
				field : 'roleIds',
				title : '所属角色ID',
				width : 150,
				hidden : true
			}, {
				field : 'roleNames',
				title : '所属角色名称',
				width : 150
			}, {
				field : 'action',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {					
					var str = '';
					if(row.name!='admin'){
						if ($.canEdit) {
							str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${ctx}/style/images/extjs_icons/pencil.png');
						}
						str += '&nbsp;';
						if ($.canGrant) {
							str += $.formatString('<img onclick="grantFun(\'{0}\');" src="{1}" title="授权"/>', row.id, '${ctx}/style/images/extjs_icons/key.png');
						}
						str += '&nbsp;';
						if ($.canDelete) {
							str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${ctx}/style/images/extjs_icons/cancel.png');
						}
						str += '&nbsp;';
						if ($.canEditPwd) {
							str += $.formatString('<img onclick="editPwdFun(\'{0}\');" src="{1}" title="修改密码"/>', row.id, '${ctx}/style/images/extjs_icons/lock/lock_edit.png');
						}
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function(data) {
				$('#searchForm table').show();
				parent.$.messager.progress('close');
				for (var i = 0; i < data.rows.length; i++) {
				    if (data.rows[i].name =='admin') {
				    	//dataGrid.datagrid('checkRow', i);
				      //禁用checkbox
				      $(".datagrid-row[datagrid-row-index=" + i + "] input[type='checkbox']")[0].disabled = true;
				    }
				  }

			},
			onRowContextMenu : function(e, rowIndex, rowData) {
				e.preventDefault();
				$(this).datagrid('unselectAll');
				$(this).datagrid('selectRow', rowIndex);
				$('#menu').menu('show', {
					left : e.pageX,
					top : e.pageY
				});
			}
		});
	});

	function editPwdFun(id) {
		parent.$.modalDialog({
			title : '编辑用户密码',
			width : 500,
			height : 300,
			href : '${ctx}/userController/editPwdPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function deleteFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前用户？', function(b) {
			if (b) {
				var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
				if (currentUserId != id) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					$.post('${ctx}/userController/delete', {
						id : id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							dataGrid.datagrid('reload');
						}
						parent.$.messager.progress('close');
					}, 'JSON');
				} else {
					parent.$.messager.show({
						title : '提示',
						msg : '不可以删除自己！'
					});
				}
			}
		});
	}

	function batchDeleteFun() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			parent.$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					parent.$.messager.progress({
						title : '提示',
						text : '数据处理中，请稍后....'
					});
					var currentUserId = '${sessionInfo.id}';/*当前登录用户的ID*/
					var flag = false;
					for ( var i = 0; i < rows.length; i++) {
						if (currentUserId != rows[i].id) {
							ids.push(rows[i].id);
						} else {
							flag = true;
						}
					}
					$.getJSON('${ctx}/userController/batchDelete', {
						ids : ids.join(',')
					}, function(result) {
						if (result.success) {
							dataGrid.datagrid('load');
							dataGrid.datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
						}
						if (flag) {
							parent.$.messager.show({
								title : '提示',
								msg : '不可以删除自己！'
							});
						} else {
							parent.$.messager.alert('提示', result.msg, 'info');
						}
						parent.$.messager.progress('close');
					});
				}
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要删除的记录！'
			});
		}
	}

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑用户',
			width : 500,
			height : 300,
			href : '${ctx}/userController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加用户',
			width : 500,
			height : 300,
			href : '${ctx}/userController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function batchGrantFun() {
		var rows = dataGrid.datagrid('getChecked');
		var ids = [];
		if (rows.length > 0) {
			for ( var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
			}
			parent.$.modalDialog({
				title : '用户授权',
				width : 500,
				height : 300,
				href : '${ctx}/userController/grantPage?ids=' + ids.join(','),
				buttons : [ {
					text : '授权',
					handler : function() {
						parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#form');
						f.submit();
					}
				} ]
			});
		} else {
			parent.$.messager.show({
				title : '提示',
				msg : '请勾选要授权的记录！'
			});
		}
	}

	function grantFun(id) {
		parent.$.modalDialog({
			title : '用户授权',
			width : 500,
			height : 300,
			href : '${ctx}/userController/grantPage?ids=' + id,
			buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为授权成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 130px; overflow: hidden;">
			<form id="searchForm">
				<table style="width: 600px;" cellpadding="2" cellspacing="1" class="formtable">
					<tr>
					<td align="left" width="15%" nowrap>
						<label>登录名：</label></td>
					<td >
						<input name="name"  class="inputxt" placeholder="可以模糊查询登录名"   style="width: 150px;" /></td>
					</tr>
					<tr>
					<td align="left" width="15%" nowrap>
						<label >创建时间：</label></td>
						<td style="font-size: 15px;"><input  name="createdatetimeStart" class="inputxt" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px;"/>至<input  name="createdatetimeEnd" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px;" /></td>
					</tr>
					<tr>
						<td align="left" width="15%" nowrap>
							<label>最后修改时间：</label></td>
						<td style="font-size: 15px;"><input  name="modifydatetimeStart"  class="inputxt" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px;"/>至<input  name="modifydatetimeEnd" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px;"/></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/userController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'pencil_add'">添加</a>
		</c:if>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/userController/grantPage')}">
			<a onclick="batchGrantFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'tux'">批量授权</a>
		</c:if>
		<%-- <c:if test="${fn:contains(sessionInfo.resourceList, '/userController/batchDelete')}">
			<a onclick="batchDeleteFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'delete'">批量删除</a>
		</c:if> --%>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
	</div>

	<div id="menu" class="easyui-menu" style="width: 120px; display: none;">
		<div onclick="addFun();" data-options="iconCls:'pencil_add'">增加</div>
		<div onclick="deleteFun();" data-options="iconCls:'pencil_delete'">删除</div>
		<div onclick="editFun();" data-options="iconCls:'pencil'">编辑</div>
	</div>
</body>
</html>