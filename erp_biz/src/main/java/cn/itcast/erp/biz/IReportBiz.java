package cn.itcast.erp.biz;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

/**
 * 报表业务逻辑接口
 * @author Administrator
 *
 */
public interface IReportBiz {

	/**
	 * 销售统计报表
	 * @return
	 */
	public List orderReport(Date date1,Date date2);
	
	/**
	 * 销售统计图
	 * @param outputStream
	 * @param date1
	 * @param date2
	 * @throws IOException
	 */
	public void orderChart(OutputStream outputStream,Date date1,Date date2) throws IOException;
	
	
	/**
	 * 导出销售统计表
	 * @param outputStream
	 * @param date1
	 * @param date2
	 * @throws IOException
	 */
	public void orderExcel(OutputStream outputStream,Date date1,Date date2) throws IOException;
	




	/**
	 * 销售退货统计报表(按日期)
	 */
	public List returnOrderReport(Date date1,Date date2);
	/**
	 * 销售退货统计图
	 * @param outputStream
	 * @param date1
	 * @param date2
	 * @throws IOException
	 */
	public void returnOrderChart(OutputStream outputStream, Date date1,Date date2) throws IOException;
	/**
	 * 销售退货统计报表
	 * @param outputStream
	 * @param date1
	 * @param date2
	 * @throws IOException
	 */
	public void returnOrderExcel(OutputStream outputStream,Date date1,Date date2) throws IOException;
	
	public List saleReport(String year);
	/**
	 * 生成柱状图
	 * @throws IOException 
	 */
	public void saleChart(OutputStream ops,String year) throws IOException;
	/**
	 * 获取年份统计
	 */
	public List getYears();
	/**
	 * 导出到excel
	 * @throws IOException 
	 */
	public void saleExcel(OutputStream out,String year) throws IOException;

}
