var id;//全局变量：角色ID
var clickRow=function(rowIndex, rowData){
	
	id=rowData.uuid;	
	
	$('#tree').tree({
		url:'role_readRoleMenus.action?id='+id,
		animate:true,
		checkbox:true
	});	
	
}


//保存
function save(){
	//提取选中的节点
	var nodes= $('#tree').tree('getChecked');//得到选中的节点集合
	var checkedStr="";
	for(var i=0;i<nodes.length;i++){
		if(i>0){
			checkedStr+=",";
		}
		checkedStr+=nodes[i].id;
	}
	//提交给后端
	$.ajax({
		url:'role_updateRoleMenus.action',
		dataType:'json',
		type:'post',
		data:{id:id ,checkedStr:checkedStr},
		success:function(value){
			$.messager.alert('提示',value.message);
		}		
	});
	
}