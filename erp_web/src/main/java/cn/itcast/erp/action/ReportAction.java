package cn.itcast.erp.action;

import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IReportBiz;

/**
 * 报表Action
 * @author Administrator
 *
 */
public class ReportAction {

	private IReportBiz reportBiz;
	private String year;
	
	public void setYear(String year) {
		this.year = year;
	}
	public String getYear() {
		if (this.year==null) {
			this.year="";
		}
		return year;
	}
	public void setReportBiz(IReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}

	
	private Date date1;//开始日期
	private Date date2;//截止日期
		
	public Date getDate1() {
		return date1;
	}
	public void setDate1(Date date1) {
		this.date1 = date1;
	}
	public Date getDate2() {
		return date2;
	}
	public void setDate2(Date date2) {
		this.date2 = date2;
	}


	/**
	 * 销售统计报表
	 */
	public void orderReport(){
		
		List list = reportBiz.orderReport(date1, date2);
		String jsonString = JSON.toJSONString(list);
		write(jsonString);		
	}
	/**
	 * 销售退货统计报表
	 */
	public void returnOrderReport() {
		
		
		List returnOrderReportList = reportBiz.returnOrderReport(date1, date2);
		String jsonString = JSON.toJSONString(returnOrderReportList);
		write(jsonString);
	}
	/**
	 * 销售统计图
	 * @throws IOException 
	 */
	public void orderChart() throws IOException{
		
		
		HttpServletResponse response = ServletActionContext.getResponse();
		//得到输出流对象
		ServletOutputStream outputStream = response.getOutputStream();
		
		reportBiz.orderChart(outputStream, date1, date2);
		
	}
	/**
	 * 销售退货统计图
	 */
	public void returnOrderChart() {
		
		try {
			
			//获取response对象
			HttpServletResponse response = ServletActionContext.getResponse();
			//获取输出流
			ServletOutputStream outputStream = response.getOutputStream();
			//调用返回饼图的方法
			reportBiz.returnOrderChart(outputStream, date1, date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 销售报表导出
	 * @throws IOException
	 */
	public void orderExcel() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		//文件名称
		String filename=new String("销售统计表.xls".getBytes(),"ISO-8859-1");
		//指定头信息
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		ServletOutputStream outputStream = response.getOutputStream();
		reportBiz.orderExcel(outputStream, date1, date2);
	}
	/**
	 * 销售退货报表导出
	 * @throws IOException
	 */
	public void returnOrderExcel() throws IOException{
		HttpServletResponse response = ServletActionContext.getResponse();
		//文件名称
		String filename=new String("销售退货统计表.xls".getBytes(),"ISO-8859-1");
		//指定头信息
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		ServletOutputStream outputStream = response.getOutputStream();
		reportBiz.returnOrderExcel(outputStream, date1, date2);
	}
	
	/**
	 * 输出字符串
	 * @param jsonString
	 */
	public void write(String jsonString){

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");		
		try {
			response.getWriter().print(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	/**
	 * 销售统计报表
	 */
	public void saleReport(){
		//刷新页面返回空
		if (year==null) {
			return ;
		}
		List sale = reportBiz.saleReport(getYear().substring(0, 4));
		String jsonString = JSON.toJSONString(sale);
		write(jsonString);
	}
	/**
	 * 生成销售统计柱状图
	 */
	public void saleChart() throws IOException{
		//刷新页面返回空
		if (year==null) {
			return ;
		}
		ServletOutputStream ops = ServletActionContext.getResponse().getOutputStream();
		reportBiz.saleChart(ops, getYear().substring(0, 4));
	}
	/**
	 * 获取年份统计
	 */
	public void saleYears(){
		List years = reportBiz.getYears();
		String jsonString = JSON.toJSONString(years);
		write(jsonString);
	}
	/**
	 * 导出到excel
	 * @throws IOException 
	 */
	public void saleExcel() throws IOException{
		//刷新页面返回空
		if (year==null) {
			return ;
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		ServletOutputStream out = response.getOutputStream();
		String filename=new String((getYear().substring(0, 4)+"年销售统计报表.xls").getBytes(), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachement;filename="+filename);
		
		reportBiz.saleExcel(out, getYear().substring(0, 4));
	}
}
