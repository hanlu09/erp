var g_index;//主表格索引
var g_index2;//子表格索引

$(function() {
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
			{field:'endtime',title:'结束日期',width:100,formatter:function(value){
				return new Date(value).Format('yyyy-MM-dd');
			}},
			{field:'creater',title:'下单员',width:100,formatter:function(value,row,index){
				return ajax('emp_get.action?id=', value, 'creater_'+index, 't.name');
			}},
			{field:'checker',title:'审查员',width:100,formatter:function(value,row,index){
				return ajax('emp_get.action?id=', value, 'checker_'+index, 't.name');
			}},
			{field:'ender',title:'结束人',width:100,formatter:function(value,row,index){
				return ajax('emp_get.action?id=', value, 'ender_'+index, 't.name');
			}},
			{field:'supplieruuid',title:'供应商ID',width:100,formatter:function(value,row,index){
				return ajax('supplier_get.action?id=', value, 'supplier_'+index, 't.name');
			}},
			{field:'totalmoney',title:'总金额',width:100},
			{field:'state',title:'订单状态',width:100,formatter:function(value){
				return returnstate[value];
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
				data:row.returnorderdetails,
				columns:[[
					{field:'uuid',title:'编号',width:100},
					{field:'goodsuuid',title:'商品编号',width:100},
					{field:'goodsname',title:'商品名称',width:100},
					{field:'price',title:'价格',width:100},
					{field:'num',title:'数量',width:100},
					{field:'money',title:'金额',width:100},
					{field:'endtime',title:'结束日期',width:100,formatter:function(value){
						return new Date(value).Format("yyyy-MM-dd");
					}},
					{field:'ender',title:'结束人',width:100,formatter:function(value,row,index){
						return ajax('emp_get.action?id=', value, 'emp_'+index, 't.name');
					}},
					{field:'storeuuid',title:'仓库编号',width:100,formatter:function(value,row,index){
						return ajax('store_get.action?id=', value, 'store_'+index, 't.name');
					}},
					{field:'state',title:'状态',width:100,formatter:function(value){
						if(row.type == "1") {
							if(value == "0"){
								return "未出库";
							}
							if(value == "1"){
								return "已出库";
							}
							return "";
							
						}else {
							if(value == "0"){
								return "未入库";
							}
							if(value == "1"){
								return "已入库";
							}
							return "";
						}
						
					}}
				]],
				singleSelect:true,
				onDblClickRow:function(rowIndex,rowData){
					$('#orderWindow').window('open');
					$('#uuid').val(rowData.uuid);
					$('#goodsuuid').html(rowData.goodsuuid);
					$('#goodsname').html(rowData.goodsname);
					$('#num').html(rowData.num);
					
					//主表格索引为展开行的索引
					g_index = index;
					//子表格索引为子表格双击行的索引
					g_index2 = rowIndex;
					
					if(Request['type'] == '2') {
						$('#outDiv').hide();
					}
					if(Request['type'] == '1') {
						$('#inDiv').hide();
					}
				},
				loadFilter:function(data) {
					var value = {total:0,rows:[]};
					for(var i=0;i<data.length;i++) {
						if(isFilter && data[i].state == '0' || isFilter == false) {
							value.rows.push(data[i]);
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
		var formdata = getFormData("searchForm");
		$('#grid').datagrid('load',formdata);
	});
})

//销售退货订单审核
function returnCheck(id){
	doLogic("确定要审核吗?",'returnorders_doReturnCheck?id='+id);
}

/**
 * 审核
 * @param id
 */
function doCheck(id) {
	doLogic('确定要审核吗?','returnorders_doCheck.action?id='+id);
}

/**
 * 确认
 * @param id
 */
//function doStart(id) {
//	doLogic('确定要确认吗?','orders_doStart.action?id='+id);
//}

function doLogic(message,url) {
	$.messager.confirm('提示',message,function(r){
		if(r){
			$.ajax({
				url:url,
				dataType:'json',
				success:function(value) {
					if(value.success) {
						//刷新表格
						$('#grid').datagrid('reload');
					}
					$.messager.alert('提示',value.message);
				}
			});
		}
	});
}

/**
 * 采购退货出库
 */
function doOutStore(){	
	doUrlStore('returnorderdetail_doOutStore.action');	
}

/**
 * 销售退货入库
 */
function doInStore(){	
	doUrlStore('returnorderdetail_doInStore.action');	
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
