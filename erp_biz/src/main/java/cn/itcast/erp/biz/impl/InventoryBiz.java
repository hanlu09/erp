package cn.itcast.erp.biz.impl;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.web.filter.authz.PortFilter;

import cn.itcast.erp.biz.IInventoryBiz;

import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.IInventoryDao;
import cn.itcast.erp.entity.Orders;

import cn.itcast.erp.dao.IInventoryDao;

import cn.itcast.erp.entity.Inventory;

/**
 * 盘盈盘亏审核
 * @author 兴
 *
 */

public class InventoryBiz extends BaseBiz<Inventory> implements IInventoryBiz {
	private IInventoryDao inventoryDao;


	public void setInventoryDao(IInventoryDao c) {
		this.inventoryDao = c;
		setBaseDao(c);
	}

	/**
	 * 添加盘赢盘亏action
	 */
	public void add(Inventory inventory){
		inventory.setState("0");
		inventoryDao.add(inventory);
	}
	
	
	/**
	 * 盘盈盘亏审核
	 */
	public void doCheck(Long uuid,Long empUuid){		
		Inventory inventory = inventoryDao.get(uuid);
		inventory.setChecktime(new Date());//审核时间
		inventory.setChecker(empUuid);//审核人 
		inventory.setState("1");//状态 1：已审核
		inventoryDao.add(inventory);
	}

}
