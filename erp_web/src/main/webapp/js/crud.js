
var method="";//保存提交的方法名称 
$(function(){
	
	//判断listParam是否定义,如果没有订单，则给出初始值
	if(typeof(listParam)=='undefined'){
		listParam='';
	}
	if(typeof(saveParam)=='undefined'){
		saveParam='';
	}
	
	//表格数据初始化
	$('#grid').datagrid({
		url:name+'_listByPage.action'+listParam,
		columns:columns,
		singleSelect:true,
		pagination:true,
		toolbar: [{
			iconCls: 'icon-add',
			text:'增加',
			handler: function(){				
				$('#editWindow').window('open');
				$('#editForm').form('clear');
				method="add";
			}
		}]

	});
	
	//条件查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData('searchForm');		
		$('#grid').datagrid('load',formdata);		
	});
	
	//保存
	$('#btnSave').bind('click',function(){
		//验证当前表单中是否全都通过验证....
		if($('#editForm').form('validate')==false){
			return ;
		}
				
		var formdata=getFormData('editForm');		
		
		$.ajax({
			url:name+'_'+method+'.action'+saveParam,
			data:formdata,
			dataType:'json',
			type:'post',
			success:function(value){
				
				if(value.success){
					$('#editWindow').window('close');
					$('#grid').datagrid('reload');
				}
				$.messager.alert('提示',value.message);				
			}
			
		});
		
		
	});
	
	
});

/**
 * 删除 
 */
function dele(id){
	
	$.messager.confirm('提示','确定要删除此记录吗？',function(r){
		if(r)
		{
			$.ajax({
				url:name+'_delete.action?id='+id,
				dataType:'json',
				success:function(value){
					if(value.success){
						$('#grid').datagrid('reload');
					}
					$.messager.alert('提示',value.message);
				}
			});		
		}	
	});	
}

/**
 * 编辑
 */
function edit(id){
	
	$('#editWindow').window('open');
	$('#editForm').form('clear');
	$('#editForm').form('load',name+'_get.action?id='+id);	
	method="update";
}