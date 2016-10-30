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
		$('#chart').attr('src','report_returnOrderChart.action?date1='+formdata['date1'] +'&date2='+formdata['date2']);
	});
	
	//导出
	$('#btnExport').bind('click',function(){
		var formdata=getFormData('searchForm');
		window.open('report_returnOrderExcel.action?date1='+formdata['date1'] +'&date2='+formdata['date2']);
	});
	
})