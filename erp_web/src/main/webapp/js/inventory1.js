$(function(){
	$('#grid').datagrid({
		url:'inventory_listByPage.action',
		columns:[[
			{field:'uuid',title:'编号',width:100},
		    {field:'goodsuuid',title:'商品',width:100,formatter:function(value,row,index){		  		    			  		    	
  		    	return ajax('goods_get.action?id=', value,'goods_'+index,'t.name' );		  		    	
  		    }},
  		    {field:'storeuuid',title:'仓库',width:100,formatter:function(value,row,index){		  		    			  		    	
  		    	return ajax('store_get.action?id=', value,'store_'+index,'t.name' );		  		    	
  		    }},
  		    {field:'num',title:'数量',width:100},
  		    {field:'type',title:'登记类型',width:100,formatter:function(value){
  		    	if(value=='1'){
  		    		return "盘盈";
  		    	}
  		    	if(value=='2'){
  		    		return "盘亏";
  		    	}
  		    }},
  		    {field:'createtime',title:'登记日期',width:100,formatter:function(value){
  		    	return new Date(value).Format("yyyy-MM-dd");
  		    }},
  		    {field:'checktime',title:'审核日期',width:100,formatter:function(value){
  		    	return new Date(value).Format("yyyy-MM-dd");
  		    }},
  		    {field:'creater',title:'登记人',width:100,formatter:function(value,row,index){		  		    			  		    	
  		    	return ajax('emp_get.action?id=', value,'emp_'+index,'t.name');		  		    	
  		    }},
  		    {field:'checker',title:'审核人',width:100,formatter:function(value,row,index){		  		    			  		    	
  		    	return ajax('emp_get.action?id=', value,'emp_'+index,'t.name');		  		    	
  		    }},
  		    {field:'state',title:'审核状态',width:100,formatter:function(value){
  		    	if(value=='0'){
  		    		return "未审核";
  		    	}
  		    	if(value=='1'){
  		    		return "已审核";
  		    	}
  		    }},
  		    {field:'remark',title:'备注',width:100},
		]],
		singleSelect:true,
		pagination:true,
		toolbar: [{
			iconCls: 'icon-add',
			text:'增加',
			handler: function(){				
				$('#editWindow').window('open');
				$('#editForm').form('clear');
			}
		}]
	});
	$('#btnSave').bind('click',function(){
		var formdata = getFormData("editForm");
		$.ajax({
			url:'inventory_add.action',
			dataType:'json',
			type:'post',
			data:formdata,
			success:function(value){
				if(value.success){
					$('#editWindow').window('close');
					$('#grid').datagrid('reload');//刷新表格
				}
				$.messager.alert('提示',value.message);
			}
		});
	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData('searchForm');		
		$('#grid').datagrid('load',formdata);		
	});
})



