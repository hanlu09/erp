var isEditingRowIndex;//当前编辑行的索引
$(function(){
	
	$('#grid').datagrid({
		columns:[[
		  		    {field:'goodsuuid',title:'商品编号',width:100,editor:{type:'numberbox',options:{
		  		    	disabled:true
		  		    }}},
		  		    {field:'goodsname',title:'商品名称',width:100,editor:{type:'combobox',options:{
		  		    	url:'goods_list.action',
		  		    	valueField:'name',
		  		    	textField:'name',
		  		    	onSelect:function(record){		  		    
		  		    		//获取价格编辑框对象
		  		    		var priceEdt=$('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'price'});
		  		    		
		  		    		if(Request['type']=='1'){//如果是采购申请
		  		    			$(priceEdt.target).val(record.inprice);//给价格编辑框赋值：采购价
		  		    		}
		  		    		if(Request['type']=='2'){//如果是销售订单录入
		  		    			$(priceEdt.target).val(record.outprice);//给价格编辑框赋值：销售价
		  		    		}
		  		    		//获取商品编号编辑框
		  		    		var goodsuuidEdt=$('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'goodsuuid'});
		  		    		$(goodsuuidEdt.target).val(record.uuid);//商品编号
		  		    		cal();//计算
		  		    		sum();//合计数计算
		  		    	}
		  		    }}},
		  		    {field:'price',title:'价格',width:100,editor:{type:'numberbox',options:{
		  		    	precision:2
		  		    }}},
		  		    {field:'num',title:'数量',width:100,editor:'numberbox'},
		  		    {field:'money',title:'金额',width:100,editor:{type:'numberbox',options:{
		  		    	precision:2,
		  		    	disabled:true
		  		    }}},
		  		    {field:'-',title:'操作',width:100,formatter:function(value,row,index){
		  		    	return "<a href='#' onClick='deleteRow("+index+")'>删除</a>";
		  		    }}
		  		 ]],
		 singleSelect:true,
		 toolbar: [{
				iconCls: 'icon-add',
				text:'增加',
				handler: function(){				
					//新增一行
					$('#grid').datagrid('appendRow',{'num':0,'money':0});
					//alert('上次编辑的行：'+isEditingRowIndex);
					//关闭上一次编辑的行
					$('#grid').datagrid('endEdit',isEditingRowIndex);
					
					//获取当前表格最后一行的索引
					isEditingRowIndex=$('#grid').datagrid('getRows').length-1;
					//alert('本次编辑的行：'+isEditingRowIndex);
					//开启编辑行
					$('#grid').datagrid('beginEdit',isEditingRowIndex);
					bindGridEvent();//绑定表格事件
				}
		  }],
		  onClickRow:function(rowIndex,rowData){
			  //alert('上次编辑的行：'+isEditingRowIndex);
			  //关闭上一次编辑的行
			  $('#grid').datagrid('endEdit',isEditingRowIndex);
			  //让当前编辑行为点击行的索引
			  isEditingRowIndex=rowIndex;
			  //alert('本次编辑的行：'+isEditingRowIndex);
			  //开启编辑行
			  $('#grid').datagrid('beginEdit',isEditingRowIndex);
			  
			  bindGridEvent();//绑定表格事件
		  }
		
	});
	
	
	//供应商下拉列表
	$('#supplier').combogrid({
		url:'supplier_list.action?t1.type='+Request['type'],
		columns:[[
		  		    {field:'uuid',title:'编号',width:100},
		  		    {field:'name',title:'名称',width:100},
		  		    {field:'address',title:'地址',width:100},
		  		    {field:'contact',title:'联系人',width:100},
		  		    {field:'tele',title:'电话',width:100},
		  		    {field:'email',title:'EMAIL',width:100}
				]],
		idField:'uuid',
		textField:'name',
		width:400,
		panelWidth:720,
		mode:'remote'
	});
	
	//保存
	$('#btnSave').bind('click',function(){
		//获得表单数据
		var formdata= getFormData("orderForm");
		//结束编辑
		$('#grid').datagrid('endEdit',isEditingRowIndex);
		//将表格数据转换为json追加到变量中				
		formdata['json']=JSON.stringify( $('#grid').datagrid('getRows'));
				
		$.ajax({
			url:'orders_add.action?t.type='+Request['type'],
			dataType:'json',
			type:'post',
			data:formdata,
			success:function(value){
				
				if(value.success){
					//清空表格(向表格加载空数据)
					$('#grid').datagrid('loadData',{total:0,rows:[]});
					//清空合计
					$('#sum').html("0.00");
				}
				$.messager.alert('提示',value.message);
				
			}
			
		});
		
	});
	
});

/**
 * 计算金额
 */
function cal(){
	//获取价格编辑框对象
	var priceEdt=$('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'price'});
	//得到价格
	var price= $(priceEdt.target).val();	
	//获取数量编辑框对象
	var numEdt=$('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'num'});
	//得到数量
	var num= $(numEdt.target).val();
	
	
	//计算金额
	var money=(price*num).toFixed(2);//toFixed是四舍五入的方法
	//获取金额编辑框对象
	var moneyEdt=$('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'money'});
	//给金额编辑框赋值
	$(moneyEdt.target).val(money);
	
	//给金额的单元格赋值
	$('#grid').datagrid('getRows')[isEditingRowIndex].money=money;
	
}

/**
 * 绑定表格内编辑框事件
 */
function bindGridEvent(){
	//获取价格编辑框对象
	var priceEdt=$('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'price'});
	//绑定价格编辑框键盘敲击事件
	$(priceEdt.target).bind('keyup',function(){
		cal();//计算
		sum();//合计数计算
	});
	
	//获取数量编辑框对象
	var numEdt=$('#grid').datagrid('getEditor',{index:isEditingRowIndex,field:'num'});
	//绑定数量编辑框键盘敲击事件
	$(numEdt.target).bind('keyup',function(){
		cal();//计算
		sum();//合计数计算
	});
	
}

/**
 * 移除行
 * @param index
 */
function deleteRow(index){
	//结束编辑行
	$('#grid').datagrid('endEdit',isEditingRowIndex);
	//删除一行
	$('#grid').datagrid('deleteRow',index);
	
	//获取表格数据
	var data=$('#grid').datagrid('getData');
	//向表格加载数据
	$('#grid').datagrid('loadData',data);
	
	sum();
}

/**
 * 合计数统计
 */
function sum(){
	//获取当前表格所有的行
	var rows= $('#grid').datagrid('getRows');
	var money=0;//金额合计
	for(var i=0;i<rows.length;i++){
		money+= parseFloat( rows[i].money );//parseFloat是将字符串转换为浮点类型
	}
	
	$('#sum').html(money.toFixed(2));
}

