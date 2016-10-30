var isEditingRowIndex;//当前编辑行的索引
var g_index;//主表格的索引
var g_index2;//子表格的索引
$(function() {
	//原订单相关信息
	$('#searchgrid').datagrid({
		url:url,
		columns:[[
		  		    {field:'uuid',title:'订单编号',width:180},
		  		    {field:'supplieruuid',title:'供应商',width:180,formatter:function(value,row,index){		  		    			  		    	
		  		    	return ajax('supplier_get.action?id=', value,'supplier_'+index,'t.name' );		  		    	
		  		    }},
		  		    {field:'totalmoney',title:'总金额',width:180}
		]],
		singleSelect:true,
		pagination:true,
		view:detailview,
		detailFormatter:function(index,row){
			return "<table style='width:540px' id='ddv_"+index+"'></table>";
		},
		onExpandRow:function(index,row){
			$('#ddv_'+index).datagrid({
				data:row.orderdetails ,
				columns:[[
				  		    {field:'goodsname',title:'商品名称',width:100},
				  		    {field:'price',title:'价格',width:100},
				  		    {field:'num',title:'数量',width:100},
				  		    {field:'money',title:'金额',width:100},
				  		    {field:'storeuuid',title:'仓库编号',width:100},
				 ]],
				 singleSelect:true,
			});
			//修复明细行高度
			$('#searchgrid').datagrid('fixDetailRowHeight',index);
		}
	});
	
	//库存
	$('#storedetailgrid').datagrid({
		url:'storedetail_listByPage.action',
		columns:[[
		          {field:'storeuuid',title:'仓库',width:180,formatter:function(value,row,index){		  		    			  		    	
		        	  return ajax('store_get.action?id=', value,'storeuuid_'+index,'t.name' );		  		    	
		          }},
		          {field:'goodsuuid',title:'商品',width:180,formatter:function(value,row,index){		  		    			  		    	
		        	  return ajax('goods_get.action?id=', value,'goodsuuid_'+index,'t.name' );		  		    	
		          }},
		          {field:'num',title:'库存',width:180}
		]],
		singleSelect:true,
		pagination:true
	});
	
	//原订单查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData("searchForm");
		$('#searchgrid').datagrid('load',formdata);		
	});
	
	//库存查询
	$('#btnStoreSearch').bind('click',function(){
		var formdata=getFormData("storedetailForm");
		$('#storedetailgrid').datagrid('load',formdata);		
	});

	//退货登记数据表格
	$('#grid').datagrid({
		columns:[[
			{field:'goodsuuid',title:'商品编号',width:100,
				editor:{
					type:'numberbox',
					options:{disabled:true}
				}
			},
			{field:'goodsname',title:'商品名称',width:100,
				editor:{
					type:'combobox',
					options:{
						url:'goods_list.action',
						valueField:'name',
						textField:'name',
						onSelect:function(rowData){
							var goodsuuidEdt = $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'goodsuuid'});
							$(goodsuuidEdt.target).val(rowData.uuid);
							
							var priceEdt = $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'price'});
							//采购退货订单
							if(Request['type'] == '1') {
								$(priceEdt.target).val(rowData.inprice);//采购价格
							}
							//销售退货订单
							if(Request['type'] == '2') {
								$(priceEdt.target).val(rowData.outprice);//销售价格
							}
							cal();
							sum();
							
							//获取数量编辑框
							var numEdt = $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'num'});
							//光标定位
							$(numEdt.target).focus();
						},
						editable:false
					}
				}
			},
			{field:'price',title:'价格',width:100,
				editor:{
					type:'numberbox',
					options:{precision:2,disabled:true}
				}
			},
			{field:'num',title:'数量',width:100,editor:'numberbox'},
			{field:'money',title:'金额',width:100,
				editor:{
					type:'numberbox',
					options:{precision:2,disabled:true}
				}
			},
			{field:'-',title:'操作',width:100,formatter:function(value,row,index){
				return "<a href='#' onClick='deleteRow("+index+")'>删除</a>";
			}}
		]],
		singleSelect:true,
		toolbar:[{
			iconCls:'icon-add',
			text:'增加',
			handler:function() {
				//增加新的一行
				$('#grid').datagrid('appendRow',{'number':0,'money':0});
				//关闭上一次编辑行
				$('#grid').datagrid('endEdit',isEditingRowIndex);
				//取得当前表格最后一行的索引
				isEditingRowIndex = $('#grid').datagrid('getRows').length-1;
				//开启编辑行
				$('#grid').datagrid('beginEdit',isEditingRowIndex);
				//绑定键盘录入事件
				bindGridEvent();
			}
		}],
		onClickRow:function(rowIndex,rowData) {
			//关闭上一次编辑行
			$('#grid').datagrid('endEdit',isEditingRowIndex);
			//获取点击行的索引
			isEditingRowIndex = rowIndex;
			//开启编辑行
			$('#grid').datagrid('beginEdit',rowIndex);
			//绑定键盘录入事件
			bindGridEvent();
			
			//获取数量编辑框
			var numEdt = $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'num'});
			//光标定位
			$(numEdt.target).focus();
		}
	});
	
	//供应商
	$('#supplier').combogrid({
		url:'supplier_list.action?t1.type='+Request['type'],
		idField:'uuid',
		textField:'name',
		columns:[[
          {field:'uuid',title:'ID',width:100},
          {field:'name',title:'名称',width:100},
          {field:'address',title:'地址',width:100},
          {field:'contact',title:'联系人',width:100},
          {field:'tele',title:'电话',width:100}
        ]],
        panelWidth:600,
        mode:'remote'
	});
	//订单编号
	$('#orders').combobox({
		url:'orders_list.action?t1.state=3&t1.type='+Request['type'],
		valueField:'uuid',
		textField:'uuid'
	});
	
	/**
	 * 提交订单
	 */
	$('#btnSave').bind('click',function(){
		//结束编辑框
		$('#grid').datagrid('endEdit',isEditingRowIndex);
		//获取供应商
		var data = getFormData('supplierForm');
		//获取表格数据
		var rows = $('#grid').datagrid('getRows');
		//向data追加属性json,值为表格数据转换的json字符串
		data['json'] = JSON.stringify(rows);
		
		$.ajax({
			url:'returnorders_add.action?t.type='+Request['type'],
			type:'post',
			data:data,
			dataType:'json',
			success:function(value){
				if(value.success){
					//清空表格(向表格中加载空数据)
					$('#grid').datagrid('loadData',{total:0,rows:[]});
					//清空总金额
					$('#sum').html("0.00");
				}
				$.messager.alert("提示",value.message);
			}
		});
	});
})

/**
 * 计算金额
 */
function cal() {
	//获取价格编辑框
	var priceEdt = $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'price'});
	//获取价格编辑框的值
	var price = $(priceEdt.target).val();
	//获取数量编辑框
	var numEdt = $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'num'});
	//获取数量编辑框的值
	var num = $(numEdt.target).val();
	//计算金额
	var money = (price * num).toFixed(2);
	//获取金额编辑框
	var moneyEdt = $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'money'});
	//对金额编辑框赋值
	$(moneyEdt.target).val(money);
	$('#grid').datagrid('getRows')[isEditingRowIndex].money = money;
}

/**
 * 绑定表格内的键盘录入事件
 */
function bindGridEvent() {
	//获取价格编辑框
	var priceEdt = $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'price'});
	//绑定价格编辑框的键盘录入事件
	$(priceEdt.target).bind("keyup",function(){
		cal();
		sum();
	});
	//获取数量编辑框
	var numEdt = $('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'num'});
	//绑定数量编辑框的键盘录入事件
	$(numEdt.target).bind("keyup",function(){
		cal();
		sum();
	});
}

/**
 * 删除一行
 */
function deleteRow(rowIndex) {
	//关闭上一次编辑行
	$('#grid').datagrid('endEdit',isEditingRowIndex);
	//删除一行
	$('#grid').datagrid('deleteRow',rowIndex);
	//获取数据
	var data = $('#grid').datagrid('getData');
	//加载数据
	$('#grid').datagrid('loadData',data);
	//删除后重新计算总金额
	sum();
}

/**
 * 计算总金额
 */
function sum() {
	var money = 0;
	var rows = $('#grid').datagrid('getRows');
	for(var i = 0; i < rows.length; i++) {
		money += parseFloat(rows[i].money);
	}
	$('#sum').html("总金额:" + money.toFixed(2));
}
