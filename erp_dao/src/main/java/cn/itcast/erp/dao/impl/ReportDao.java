package cn.itcast.erp.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.erp.dao.IReportDao;
/**
 * 报表Dao
 * @author Administrator
 *
 */
public class ReportDao extends HibernateDaoSupport implements IReportDao {

	/**
	 * 销售统计报表
	 */
	public List orderReport() {
		String hql="select new cn.itcast.erp.entity.Report(t.name,sum(d.money)) "
				+ "from Goodstype t ,Goods g ,Orders o,Orderdetail d "
				+ "where g.goodstype=t and d.orders=o and d.goodsuuid=g.uuid "
				+ "and o.type='2' group by t.name";
		
		return getHibernateTemplate().find(hql);
	}
	
	/**
	 * 销售统计报表(按日期)
	 */
	public List orderReport(Date date1,Date date2) {
		String hql="select new cn.itcast.erp.entity.Report(t.name,sum(d.money)) "
				+ "from Goodstype t ,Goods g ,Orders o,Orderdetail d "
				+ "where g.goodstype=t and d.orders=o and d.goodsuuid=g.uuid "
				+ "and o.createtime>=? and o.createtime<=? "
				+ "and o.type='2' group by t.name";
		
		return getHibernateTemplate().find(hql,date1,date2);
	}

	/**
	 * 通过年份获取月统销售计报表
	 */
	public List saleReport(String year){
		String queryString = "select new cn.itcast.erp.entity.Report(to_char(createtime,'MM'),sum(totalmoney)) "
				+ "from Orders "
				+ "where ?=to_char(createtime,'yyyy') and type='2' group by to_char(createtime,'MM') order by to_char(createtime,'MM')";
		return getHibernateTemplate().find(queryString, year);
	}
	/**
	 * 获取年份销售统计报表
	 */
	public List getYears(){
		String queryString = "select new cn.itcast.erp.entity.Report(to_char(createtime,'yyyy'),sum(totalmoney)) "
				+ "from Orders "
				+ "where  type='2' group by to_char(createtime,'yyyy') order by to_char(createtime,'yyyy')";
		return getHibernateTemplate().find(queryString);
	}

	/**
	 * 销售退货统计报表
	 */
	public List returnOrderReport() {
		String hql = "select new cn.itcast.erp.entity.Report(ge.name,sum(rl.money)) "
				+ "from Goodstype ge,Returnorderdetail rl,Goods gs,Returnorders rs  "
				+ "where ge = gs.goodstype and rl.returnorders = rs "
				+ "and rl.goodsuuid = gs.uuid and rs.type = '2'  "
				+ "group by ge.name ";
		
		return getHibernateTemplate().find(hql);
	}
	/**
	 * 销售退货统计报表(按日期)
	 */
	public List returnOrderReport(Date date1,Date date2) {
		
		String hql = "select new cn.itcast.erp.entity.Report(ge.name,sum(rl.money)) "
				+ "from Goodstype ge,Returnorderdetail rl,Goods gs,Returnorders rs  "
				+ "where ge = gs.goodstype and rl.returnorders = rs "
				+ "and rl.goodsuuid = gs.uuid and rs.type = '2'  "
				+ "and rs.createtime > ? and rs.createtime < ? "
				+ "group by ge.name ";
	
	 return getHibernateTemplate().find(hql, date1,date2);
	}


}
