package cn.itcast.erp.biz.impl;

import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

import cn.itcast.erp.biz.IReportBiz;
import cn.itcast.erp.dao.IReportDao;
import cn.itcast.erp.entity.Report;
/**
 * 报表业务逻辑层
 * @author Administrator
 *
 */
public class ReportBiz implements IReportBiz {

	private IReportDao reportDao;
		
	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}


	/**
	 * 销售统计表
	 */
	public List orderReport(Date date1,Date date2) {
		
		if(date1==null || date2==null){
			return reportDao.orderReport();
		}else
		{
			return reportDao.orderReport(date1, date2);
		}
	}
	
	/**
	 * 销售退货统计报表(按日期)
	 */
	public List returnOrderReport(Date date1,Date date2) {
		if (date1 == null || date2 == null) {
			return reportDao.returnOrderReport();
		}
		return reportDao.returnOrderReport(date1, date2);
	}
	
	/**
	 * 销售统计图
	 * @param outputStream
	 * @param date1
	 * @param date2
	 * @throws IOException
	 */
	public void orderChart(OutputStream outputStream,Date date1,Date date2) throws IOException{
		
		//获取销售统计数据
		List<Report> list = orderReport(date1,date2);
		//构建饼图数据集
		DefaultPieDataset dataSet=new DefaultPieDataset();
		for(Report report:list){			
			dataSet.setValue(report.getName(), report.getMoney());
		}
		createPieChart(outputStream, dataSet, "销售统计图");
	}
	
	/**
	 * 销售退货统计图
	 * @param outputStream
	 * @param date1
	 * @param date2
	 * @throws IOException
	 */
	public void returnOrderChart(OutputStream outputStream, Date date1,Date date2) throws IOException {
		//获取销售退货统计数据
		List<Report> reports = returnOrderReport(date1,date2);
		//获取销售退货统计饼图的数据集
		DefaultPieDataset dataset = new DefaultPieDataset();
		//将统计数据放入到数据集当中
		for (Report report : reports) {
			dataset.setValue(report.getName(), report.getMoney());
		}
		
		createPieChart(outputStream, dataset, "销售退货统计图");
		
	}
	
	/**
	 * 导出销售统计表
	 * @param outputStream
	 * @param date1
	 * @param date2
	 * @throws IOException
	 */
	public void orderExcel(OutputStream outputStream,Date date1,Date date2) throws IOException{
		
		//创建新的工作簿对象
		HSSFWorkbook book=new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = book.createSheet();
		//创建标题行
		HSSFRow titleRow = sheet.createRow(0);
		titleRow.createCell(0).setCellValue("商品类别");
		titleRow.createCell(1).setCellValue("销售额");
		//获取报表数据
		List<Report> list = orderReport(date1,date2);
		
		//向excel写入报表数据
		for(int i=0;i<list.size();i++){		
			Report report = list.get(i);
			HSSFRow row = sheet.createRow(i+1);
			row.createCell(0).setCellValue(report.getName());
			row.createCell(1).setCellValue(report.getMoney());			
		}
				
		//输出到输出流
		book.write(outputStream);
		
		
	}
	
	/**
	 * 导出销售退货统计表
	 * 
	 * @param outputStream
	 * @param date1
	 * @param date2
	 * @throws IOException
	 */
	@SuppressWarnings("deprecation")
	public void returnOrderExcel(OutputStream outputStream, Date date1, Date date2) throws IOException {

		// 创建新的工作簿对象
		HSSFWorkbook book = new HSSFWorkbook();
		// 创建工作表
		HSSFSheet sheet = book.createSheet();
		//创建总标题行
		{
			//创建标题行
			HSSFRow titleRow = sheet.createRow(8);
			HSSFCell titleCell = titleRow.createCell(6);
			titleCell.setCellValue("退货销售统计表");
			sheet.addMergedRegion(new CellRangeAddress(
					8, // 起始行
					8, // 结束行
					6, // 其实列
					7  // 结束列
				));
			//创建工作表样式
			HSSFCellStyle cellStyle = book.createCellStyle();
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 设置单元格水平方向对其方式
	        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 设置单元格垂直方向对其方式
	        titleCell.setCellStyle(cellStyle);
		}
		
		// 创建字段标题
		HSSFRow fieldRow = sheet.createRow(9);
		//titleRow.createCell(0).setCellValue("商品类别");
		HSSFCell cell_10 = fieldRow.createCell(6);
		cell_10.setCellValue("商品类别");
		HSSFCell cell_11 = fieldRow.createCell(7);
		cell_11.setCellValue("销售额");
		//创建工作表样式 (字段)
		HSSFCellStyle cellStyle = book.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 设置单元格水平方向对其方式
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 设置单元格垂直方向对其方式
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); // 底部边框
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex()); // 底部边框颜色
		
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);  // 左边边框
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex()); // 左边边框颜色
		        
		cellStyle.setBorderRight(CellStyle.BORDER_THIN); // 右边边框
		cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());  // 右边边框颜色
		 
		cellStyle.setBorderTop(CellStyle.BORDER_THIN); // 上边边框
		cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());  // 上边边框颜色
        cell_10.setCellStyle(cellStyle);
        cell_11.setCellStyle(cellStyle);
		// 获取报表数据
		List<Report> list = returnOrderReport(date1, date2);

		// 向excel写入报表数据
		for (int i = 0; i < list.size(); i++) {
			Report report = list.get(i);
			HSSFRow row = sheet.createRow(i + 10);
			HSSFCell tempCell_0 = row.createCell(6);
			tempCell_0.setCellValue(report.getName());
			HSSFCell tempCell_1 = row.createCell(7);
			tempCell_1.setCellValue(report.getMoney());
			//创建工作表样式
			HSSFCellStyle temp_CellStyle = book.createCellStyle();
			temp_CellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);  // 设置单元格水平方向对其方式
			temp_CellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 设置单元格垂直方向对其方式
			//隔行换色
			if ((i + 2)%2 == 0 ) {
				temp_CellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex()); // 前景色
				temp_CellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND); //前景色
				tempCell_0.setCellStyle(temp_CellStyle);
				tempCell_1.setCellStyle(temp_CellStyle);
				//tempCell_0.setCellStyle(cellStyle);//设置黑框
				//tempCell_1.setCellStyle(cellStyle);//设置黑框
			}else {
				temp_CellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex()); // 前景色
				temp_CellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND); //前景色
				tempCell_0.setCellStyle(temp_CellStyle);
				tempCell_1.setCellStyle(temp_CellStyle);
				//tempCell_0.setCellStyle(cellStyle);//设置黑框
				//tempCell_1.setCellStyle(cellStyle);//设置黑框
			}
		}

		// 输出到输出流
		book.write(outputStream);

	}

	
	/**
	 * 创建饼图
	 * 
	 * @param outputStream
	 * @param dataSet
	 * @param title
	 * @throws IOException
	 */
	private void createPieChart(OutputStream outputStream, DefaultPieDataset dataSet, String title) throws IOException {

		// JFreeChart chart=ChartFactory.createPieChart("", dataSet, true,
		// false, false);
		// 获取3D饼图实例
		JFreeChart chart = ChartFactory.createPieChart3D("", dataSet, true, false, false);

		
		// 设置标题
		chart.setTitle(new TextTitle(title, new Font("黑体", Font.BOLD, 14)));
		// 获取饼图绘图区
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(new Font("黑体", Font.BOLD, 12));// 设置绘图区的标签字体
		//设置百分比
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}",new DecimalFormat("0.0"),new DecimalFormat("0.0%")));
		
		// 设置开始角度
		plot.setStartAngle(270.0D);
		// 设置透明度
		plot.setForegroundAlpha(0.5F);

		// 获取图例对象
		LegendTitle legend = chart.getLegend();
		legend.setItemFont(new Font("黑体", Font.BOLD, 10));// 设置图例项字体

		// 将图表输出到输出流
		ChartUtilities.writeChartAsPNG(outputStream, chart, 400, 300);

	}

	
	/**
	 * 按年获取月份统计销售报表
	 */
	public List saleReport(String year){
		List<Report> list = reportDao.saleReport(year);
		//向月中添加'月份'
		for (Report report : list) {
			if (report.getName().substring(0, 1).equals("0")) {
				report.setName(report.getName().substring(1, 2)+"月份");
			}else {
				report.setName(report.getName()+"月份");
			}
		}
		//新建一个list存入需要返回的list,list中存入12个月,月份没有销售额,设为0
		List<Report> newlist = new ArrayList<Report>();
		//向新list中存入应有值
		for (int month=1;month<13;month++) {
			Report report = new Report(month+"月份", 0);
			for (Report reportSrc : list) {
				if (report.getName().equals(reportSrc.getName())) {
					report.setMoney(reportSrc.getMoney());
				}
			}
			newlist.add(report);
		}
		return newlist;
	}
	/**
	 * 获取年份s
	 */
	public List getYears(){
		List<Report> list = reportDao.getYears();
		//向年中添加年
		for (Report report : list) {
			report.setName(report.getName()+"年");
		}
		return list;
	}
	/**
	 * 生成柱状图
	 * @throws IOException 
	 */
	public void saleChart(OutputStream ops,String year) throws IOException{
		DefaultCategoryDataset dataset= new DefaultCategoryDataset();
		List<Report> list = saleReport(year);
		for (Report report : list) {
			dataset.setValue(report.getMoney(),"销售额",report.getName());
		}
		JFreeChart chart = ChartFactory.createBarChart(year+"年销售趋势分析图", "月份", "月销售额(元)", dataset, PlotOrientation.VERTICAL, true, false, false);
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer render = new BarRenderer();
		render.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		render.setBaseItemLabelsVisible(true);
		render.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12,TextAnchor.BASELINE_CENTER));
		render.setItemLabelAnchorOffset(10D);
		plot.setRenderer(render);
		ChartUtilities.writeChartAsPNG(ops, chart, 700, 500);
	}
	/**
	 * 年度销售统计导出到excel
	 * @throws IOException 
	 */
	public void saleExcel(OutputStream out,String year) throws IOException{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		HSSFRow titlerow = sheet.createRow(0);
		
		
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);  
		//cell.setCellStyle(cellStyle);
		HSSFCell cell0 = titlerow.createCell(0);
		HSSFCell cell1 = titlerow.createCell(1);
		cell0.setCellStyle(cellStyle);
		cell1.setCellStyle(cellStyle);
		cell0.setCellValue("月份");
		cell1.setCellValue("销售额");
		List<Report> list = saleReport(year);
		int number = 1;
		for (Report report : list) {
			HSSFRow row = sheet.createRow(number);
			row.createCell(0).setCellValue(report.getName());
			row.createCell(1).setCellValue(report.getMoney());
			number++;
		}
		workbook.write(out);
	}
}
