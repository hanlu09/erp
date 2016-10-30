package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Orders;
/**
 * 订单业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrdersBiz extends IBaseBiz<Orders>{
	
	/**
	 * 采购审核
	 * @param uuid
	 */
	public void doCheck(Long uuid,Long empUuid);
	
	/**
	 * 采购确认
	 * @param uuid
	 * @param empUuid
	 */
	public void doStart(Long uuid,Long empUuid);
}

