package cn.itcast.erp.dao;

import java.util.Date;
import java.util.List;

/**
 * 报表Dao
 * @author Administrator
 *
 */
public interface IReportDao {

	/**
	 * 销售统计报表
	 * @return
	 */
	public List orderReport();
	
	
	/**
	 * 销售统计报表(按日期)
	 */
	public List orderReport(Date date1,Date date2);
	
	/**
	 * 销售退货统计报表
	 * @return list
	 */
	public List returnOrderReport();
	
	/**
	 * 销售退货统计报表(按日期)
	 */
	public List returnOrderReport(Date date1,Date date2);
	/**
	 * 按年获取月份销售统计
	 */
	public List saleReport(String year);
	/**
	 * 获取年份销售统计
	 */
	public List getYears();
}
