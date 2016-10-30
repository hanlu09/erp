package cn.itcast.erp.biz;

import cn.itcast.erp.entity.Inventory;

/**
 * 盘盈盘亏审核
 * @author 兴
 *
 */

public interface IInventoryBiz extends IBaseBiz<Inventory> {
	
	/**
	 * 添加盘赢盘亏action
	 */
	public void add(Inventory inventory);
	
	
	public void doCheck(Long uuid,Long empUuid);

}
