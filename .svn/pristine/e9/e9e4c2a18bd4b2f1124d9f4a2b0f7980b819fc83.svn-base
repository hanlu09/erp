$(function(){
	
	$('#grid').datagrid({
		url:'storedetail_listByPage.action',
		columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'storeuuid',title:'仓库',width:100,formatter:function(value,row,index){
		  		    	return ajax('store_get.action?id=',value,'store_'+index,'t.name');		  		    	
		  		    }},
		  		    {field:'goodsuuid',title:'商品编号',width:100,formatter:function(value,row,index){
		  		    	return ajax('goods_get.action?id=',value,'goods_'+index,'t.name');		  		    	
		  		    }},
		  		    {field:'num',title:'数量',width:100}	    
			]],
	    singleSelect:true,
	    pagination:true	,
	    onDblClickRow:function(rowIndex,rowData){
	    	
	    	$('#storeoperWindow').window('open');
	    	//读取仓库名称
	    	ajax('store_get.action?id=',rowData.storeuuid,'storename','t.name');
	    	//读取商品名称
	    	ajax('goods_get.action?id=',rowData.goodsuuid,'goodsname','t.name');
	    	//显示数量
	    	$('#num').html(rowData.num);
	    	//库存变动记录筛选
	    	$('#storeoper_grid').datagrid('load',{'t1.storeuuid':rowData.storeuuid,'t1.goodsuuid':rowData.goodsuuid});
	    	
	    }
	});
	
	//查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData('searchForm');
		$('#grid').datagrid('load',formdata);		
	});
	

	$('#storeoper_grid').datagrid({
		url:'storeoper_listByPage.action',
		columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'empuuid',title:'员工编号',width:100,formatter:function(value,row,index){
		  		    	return ajax('emp_get.action?id=',value,'emp_'+index,'t.name');
		  		    }},
		  		    {field:'opertime',title:'操作时间',width:140,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd hh:mm:ss');
		  		    }},
		  		    {field:'storeuuid',title:'仓库编号',width:100,formatter:function(value,row,index){
		  		    	return ajax('store_get.action?id=',value,'store2_'+index,'t.name');
		  		    }},
		  		    {field:'goodsuuid',title:'商品编号',width:100,formatter:function(value,row,index){
		  		    	return ajax('goods_get.action?id=',value,'goods2_'+index,'t.name');
		  		    }},
		  		    {field:'num',title:'数量',width:100},
		  		    {field:'type',title:'类型',width:100,formatter:function(value){
		  		    	return storetype[value];
		  		    }}	    
				]],
		singleSelect:true,
		pagination:true	
		
	});
	
})