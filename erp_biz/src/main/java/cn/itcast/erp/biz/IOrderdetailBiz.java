package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Orderdetail;
/**
 * 订单明细业务逻辑层接口
 * @author Administrator
 *
 */
public interface IOrderdetailBiz extends IBaseBiz<Orderdetail>{
	
	/**
	 * 采购入库
	 * @param uuid 订单明细ID
	 * @param empUuid 员工ID
	 * @param storeUuid 仓库ID
	 */
	public void doInStore(Long uuid,Long empUuid,Long storeUuid);
	

	/**
	 * 销售出库
	 * @param uuid 订单明细ID
	 * @param empUuid 员工ID
	 * @param storeUuid 仓库ID
	 */
	public void doOutStore(Long uuid,Long empUuid,Long storeUuid);
}

