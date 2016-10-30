package cn.itcast.erp.biz.impl;

import java.util.Date;

import cn.itcast.erp.biz.IReturnordersBiz;
import cn.itcast.erp.dao.IReturnordersDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.entity.Returnorderdetail;
import cn.itcast.erp.entity.Returnorders;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.exception.ErpException;

/**
 * 退货订单业务逻辑类
 */
public class ReturnordersBiz extends BaseBiz<Returnorders> implements IReturnordersBiz {

	private IReturnordersDao returnordersDao;

	public void setReturnordersDao(IReturnordersDao returnordersDao) {
		this.returnordersDao = returnordersDao;
		setBaseDao(returnordersDao);
	}

	private IStoredetailDao storedetailDao;

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	/**
	 * 添加退货订单
	 */
	public void add(Returnorders returnorders) {
		try {
			// 退货订单创建日期
			returnorders.setCreatetime(new Date());
			// 状态:{"0":未审核,"1":已审核,"3":已结束}
			returnorders.setState("0");
			// 总金额
			double money = 0;
			for (Returnorderdetail returnorderdetail : returnorders.getReturnorderdetails()) {
				money += returnorderdetail.getMoney();
				// 退货订单明细状态:{"0":未出库,"1":已出库}
				returnorderdetail.setState("0");
				// 根据商品编号查询仓库库存
				Storedetail storedetail = storedetailDao.findByGoodsuuid(returnorderdetail.getGoodsuuid());
				if (storedetail == null) {
					throw new ErpException("库存不足");
				}
				// 退货订单明细:仓库编号
				returnorderdetail.setStoreuuid(storedetail.getStoreuuid());
			}
			returnorders.setTotalmoney(money);

			returnordersDao.add(returnorders);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 采购退货审核
	 * @param uuid
	 * @param empUuid
	 */
	public void doCheck(Long uuid, Long empUuid) {
		Returnorders returnorders = returnordersDao.get(uuid);
		// 审核时间
		returnorders.setChecktime(new Date());
		// 审核人
		returnorders.setChecker(empUuid);
		// 状态:{"1":已审核}
		returnorders.setState("1");
	}
	/**
	 * 销售退货订单审核
	 * @param uuid
	 * @param empUuid
	 */
	public void doReturnCheck(Long uuid,Long empUuid){
		//获取订单
		Returnorders returnOrders=returnordersDao.get(uuid);
		//审核时间
		returnOrders.setChecktime(new Date());
		//审核人
		returnOrders.setChecker(empUuid);
		//修改状态
		returnOrders.setState("1");
	}

}
