package cn.itcast.erp.biz;

import cn.itcast.erp.entity.Returnorderdetail;

public interface IReturnorderdetailBiz extends IBaseBiz<Returnorderdetail> {
	/**
	 * 销售退货入库
	 * @param id
	 * @param uuid
	 * @param storeUuid
	 */
	public void doInStore(Long id, Long uuid, Long storeUuid);
	/*
	 * 采购退货出库
	 */
	public void doOutStore(Long uuid,Long empUuid,Long storeUuid);
}
