package cn.itcast.erp.dao.impl;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.dao.IReturnorderdetailDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Returnorderdetail;

/**
 * 退货订单明细数据访问类
 */
public class ReturnorderdetailDao extends BaseDao<Returnorderdetail> implements IReturnorderdetailDao {

	/**
	 * 构建查询条件
	 */
	public DetachedCriteria getDetachedCriteria(Returnorderdetail returnorderdetail1,
			Returnorderdetail returnorderdetail2, Object param) {
		DetachedCriteria dc = DetachedCriteria.forClass(Returnorderdetail.class);
		if (returnorderdetail1 != null) {
			if (returnorderdetail1.getGoodsname() != null && returnorderdetail1.getGoodsname().trim().length() > 0) {
				dc.add(Restrictions.like("goodsname", returnorderdetail1.getGoodsname(), MatchMode.ANYWHERE));
			}
			if (returnorderdetail1.getState() != null && returnorderdetail1.getState().trim().length() > 0) {
				dc.add(Restrictions.like("state", returnorderdetail1.getState(), MatchMode.ANYWHERE));
			}
			if (returnorderdetail1.getReturnorders() != null && returnorderdetail1.getReturnorders().getUuid() != null) {
				dc.add(Restrictions.eq("returnorders", returnorderdetail1.getReturnorders()));
			}
		}
		return dc;
	}

}

