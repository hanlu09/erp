$(function(){
	
	$('#grid').datagrid({
		url:url,
		columns:
			[[{field:'uuid',title:'编号',width:100},
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
  		    	return ajax('emp_get.action?id=', value,'emp_'+index,'t.name' );		  		    	
  		    }},
  		    {field:'checker',title:'审核人',width:100,formatter:function(value,row,index){		  		    			  		    	
  		    	return  ajax('emp_get.action?id=', value,'emp_'+index,'t.name' );		  		    	
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
  		  	{field:'-',title:'操作',width:200,formatter:function(value,row,index){
  		  			if(row.state=='0'){
		    		return "<a href='#' onclick='doCheck("+row.uuid+")'>审核</a>";
  		  			}
		    	}}
		]],
		singleSelect:true,
		pagination:true,
		detailFormatter:function(index,row){
			return "<table id='ddv_"+index+"'></table>";
		}
	});
	//条件查询
	$('#btnSearch').bind('click',function(){
		alert("aaa");
		var formdata=getFormData('searchForm');		
		$('#grid').datagrid('load',formdata);		
	});
})


	


/**
 * 审核
 * @param id
 */
function doCheck(id){
	doLogic('确定要审核吗？','inventory_doCheck.action?id='+id);	
	}
/**
 * 确认
 * @param id
 */
function doStart(id){
	doLogic('确定要确认吗？','orders_doStart.action?id='+id);	
}

function doLogic(message,url){
	
	$.messager.confirm('提示',message,function(r){
		if(r){
			$.ajax({
				url:url,
				dataType:'json',
				success:function(value){
					//如果成功
					if(value.success){
						$('#grid').datagrid('reload');//刷新表格
					}			
					$.messager.alert('提示',value.message);
				}
			});			
		}		
	});
	
}


