$(function(){
	
	$('#grid').datagrid({
		url:url,
		columns:columns,
		singleSelect:true
	});
	
	//查询
	$('#btnSearch').bind('click',function(){
		var formdata=getFormData('searchForm');
		$('#grid').datagrid('load',formdata);	
		//刷新统计图
		$('#chart').attr('src','report_orderChart.action?date1='+formdata['date1'] +'&date2='+formdata['date2']);
		
	});
	
	//导出
	$('#btnExport').bind('click',function(){
		var formdata=getFormData('searchForm');
		window.open('report_orderExcel.action?date1='+formdata['date1'] +'&date2='+formdata['date2']);
	});
	//年统计查询
	$("#saleSearch").bind('click',function(){
		
		var formdata = getFormData('saleForm');
		$('#grid').datagrid('load',formdata);
		//刷新图
		$("#saleChart").attr('src','report_saleChart.action?year='+formdata['year']);
	});
	//销售导出
	$('#saleExport').bind('click',function(){
		var formdata = getFormData('saleForm');
		window.open('report_saleExcel.action?year='+formdata['year']);
	});
})