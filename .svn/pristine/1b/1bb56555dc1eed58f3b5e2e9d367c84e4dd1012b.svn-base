package cn.itcast.erp.dao.impl;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.entity.StoreAlert;
import cn.itcast.erp.entity.Storedetail;
/**
 * 仓库库存数据访问类
 * @author Administrator
 *
 */
public class StoredetailDao extends BaseDao<Storedetail> implements IStoredetailDao {

	
	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Storedetail storedetail1,Storedetail storedetail2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Storedetail.class);
		if(storedetail1!=null){
			
			if(storedetail1.getStoreuuid()!=null){
				dc.add(Restrictions.eq("storeuuid", storedetail1.getStoreuuid()));
			}
			if(storedetail1.getGoodsuuid()!=null){
				dc.add(Restrictions.eq("goodsuuid", storedetail1.getGoodsuuid()));
			}
			
		}		
		return dc;
	}
	
	/**
	 * 获取库存预警列表
	 * @return
	 */
	public List<StoreAlert> getStoreAlertList(){
		String hql="from StoreAlert where storenum<outnum";
		return (List<StoreAlert>) getHibernateTemplate().find(hql);		
	}
	
	/**
	 * 根据商品编号查询仓库库存实体
	 * @param goodsuuid
	 * @return
	 */
	public Storedetail findByGoodsuuid(Long goodsuuid) {
		List<Storedetail> list = (List<Storedetail>) getHibernateTemplate().find("from Storedetail where goodsuuid=?", goodsuuid);
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
}

