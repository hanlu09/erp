package cn.itcast.erp.dao;

import org.hibernate.criterion.DetachedCriteria;

import cn.itcast.erp.entity.Returnorders;

/**
 * 退货订单数据访问接口
 * @author zjgzzd
 */
public interface IReturnordersDao extends IBaseDao<Returnorders>{
	/**
	 * 构建查询条件
	 */
	public DetachedCriteria getDetachedCriteria(Returnorders returnOrders1,Returnorders returnOrders2,Object param);
	
}
