var g_index;//主表格的索引
var g_index2;//子表格的索引
$(function(){
	
	$('#grid').datagrid({
		url:url,
		columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'createtime',title:'生成日期',width:100,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd');
		  		    }},
		  		    {field:'checktime',title:'检查日期',width:100,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd');
		  		    }},
		  		    {field:'starttime',title:'开始日期',width:100,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd');
		  		    }},
		  		    {field:'endtime',title:'结束日期',width:100,formatter:function(value){
		  		    	return new Date(value).Format('yyyy-MM-dd');
		  		    }},
		  		    {field:'type',title:'订单类型',width:100,formatter:function(value){
		  		    	return type[value];
		  		    }},
		  		    {field:'creater',title:'下单员',width:100,formatter:function(value,row,index){		  		    			  		    	
		  		    	return ajax('emp_get.action?id=', value,'creater_'+index,'t.name' );		  		    	
		  		    }},
		  		    {field:'checker',title:'审查员',width:100,formatter:function(value,row,index){		  		    			  		    	
		  		    	return ajax('emp_get.action?id=', value,'checker_'+index,'t.name' );		  		    	
		  		    }},
		  		    {field:'starter',title:'采购员',width:100,formatter:function(value,row,index){		  		    			  		    	
		  		    	return ajax('emp_get.action?id=', value,'starter_'+index,'t.name' );		  		    	
		  		    }},
		  		    {field:'ender',title:'库管员',width:100,formatter:function(value,row,index){		  		    			  		    	
		  		    	return ajax('emp_get.action?id=', value,'ender_'+index,'t.name' );		  		    	
		  		    }},
		  		    {field:'supplieruuid',title:'供应商',width:100,formatter:function(value,row,index){		  		    			  		    	
		  		    	return ajax('supplier_get.action?id=', value,'supplier_'+index,'t.name' );		  		    	
		  		    }},
		  		    {field:'totalmoney',title:'总金额',width:100},
		  		    {field:'state',title:'订单状态',width:100,formatter:function(value){
		  		    	return state[value];
		  		    }},
		  		    operation
		]],
		singleSelect:true,
		pagination:true,
		view:detailview,
		detailFormatter:function(index,row){
			return "<table id='ddv_"+index+"'></table>";
		},
		onExpandRow:function(index,row){
			$('#ddv_'+index).datagrid({
				data:row.orderdetails ,
				columns:[[
				  		    {field:'uuid',title:'编号',width:100},
				  		    {field:'goodsuuid',title:'商品编号',width:100},
				  		    {field:'goodsname',title:'商品名称',width:100},
				  		    {field:'price',title:'价格',width:100},
				  		    {field:'num',title:'数量',width:100},
				  		    {field:'money',title:'金额',width:100},
				  		    {field:'endtime',title:'结束日期',width:100},
				  		    {field:'ender',title:'库管员',width:100},
				  		    {field:'storeuuid',title:'仓库编号',width:100},
				  		    {field:'state',title:'状态',width:100}		  		
				 ]],
				 singleSelect:true,
				 onDblClickRow:function(rowIndex,rowData){
					 				
					 $('#orderWindow').window('open');
					 $('#goodsuuid').html(rowData.goodsuuid);//商品编号
					 $('#goodsname').html(rowData.goodsname);//商品名称
					 $('#num').html(rowData.num);//商品数量
					 
					 $('#uuid').val(rowData.uuid);//订单明细ID
					 
					 //主表格索引为展开行的索引
					 g_index=index;
					 //子表格索引为子表格双击行的索引
					 g_index2=rowIndex;
					 
					 if(Request['type']=='1'){
						 $('#outDiv').hide();//隐藏出库按钮
					 }
					 if(Request['type']=='2'){
						 $('#inDiv').hide();//隐藏入库按钮
					 }
					 
				 },
				 loadFilter:function(data){ 
					 var value={total:0,rows:[]};					 
					 for(var i=0;i<data.length;i++){
						 if(isFilter && data[i].state=='0' || isFilter==false){
							 value.rows.push(data[i]);//push是JS的向集合(数组)添加元素的方法
						 }						 
					 }					 
					 return value;					 
				 }
			});
			//修复明细行高度
			
			$('#grid').datagrid('fixDetailRowHeight',index);
			
		}
		
	});
	
	//查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData("searchForm");
		$('#grid').datagrid('load',formdata);		
	});
	
	
})

/**
 * 审核
 * @param id
 */
function doCheck(id){
	doLogic('确定要审核吗？','orders_doCheck.action?id='+id);	
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

/**
 * 采购入库
 */
function doInStore(){	
	doUrlStore('orderdetail_doInStore.action');	
}

/**
 * 销售出库
 */
function doOutStore(){	
	doUrlStore('orderdetail_doOutStore.action');	
}

function doUrlStore(url){
	var formdata=getFormData("orderForm");
	$.ajax({
		url:url,
		dataType:'json',
		type:'post',
		data:formdata,
		success:function(value){
			if(value.success){				
				$('#orderWindow').window('close');//关闭窗口
				//修改本地数据的状态为1
				$('#ddv_'+g_index).datagrid('getRows')[g_index2].state='1';
				//移除子表格的行
				$('#ddv_'+g_index).datagrid('deleteRow',g_index2);	
				//判断当子表格的记录全部移除，重新加载数据
				if($('#ddv_'+g_index).datagrid('getRows').length==0){
					$('#grid').datagrid('reload');//重新加载
				}				
			}			
			$.messager.alert('提示',value.message);
		}		
	});		
}
