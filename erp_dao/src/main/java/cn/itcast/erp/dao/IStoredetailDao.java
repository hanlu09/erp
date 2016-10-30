package cn.itcast.erp.dao;

import java.util.List;

import cn.itcast.erp.entity.StoreAlert;
import cn.itcast.erp.entity.Storedetail;
/**
 * 仓库库存数据访问接口
 * @author Administrator
 *
 */
public interface IStoredetailDao extends IBaseDao<Storedetail>{
	
	/**
	 * 获取库存预警列表
	 * @return
	 */
	public List<StoreAlert> getStoreAlertList();

	/**
	 * 根据商品编号查询仓库库存实体
	 * @param goodsuuid
	 * @return
	 */
	public Storedetail findByGoodsuuid(Long goodsuuid);
}
