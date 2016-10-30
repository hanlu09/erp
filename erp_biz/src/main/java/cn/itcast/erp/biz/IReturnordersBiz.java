package cn.itcast.erp.biz;

import cn.itcast.erp.entity.Returnorders;

/**
 * 退货订单业务逻辑层接口
 */
public interface IReturnordersBiz extends IBaseBiz<Returnorders>{

	/**
	 * 添加退货订单
	 */
	public void add(Returnorders returnorders);
	
	/**
	 * 采购退货审核
	 * @param uuid
	 * @param empUuid
	 */
	public void doCheck(Long uuid, Long empUuid);
	/**
	 * 销售退货订单审核
	 * @param uuid
	 * @param empUuid
	 */
	public void doReturnCheck(Long uuid,Long empUuid);
}
