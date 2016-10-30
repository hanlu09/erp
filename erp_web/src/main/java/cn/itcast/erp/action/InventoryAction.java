package cn.itcast.erp.action;

import org.apache.shiro.authz.UnauthorizedException;

import cn.itcast.erp.biz.IInventoryBiz;
import cn.itcast.erp.entity.Emp;

import cn.itcast.erp.biz.IInventoryBiz;
import cn.itcast.erp.entity.Emp;

import cn.itcast.erp.entity.Inventory;

public class InventoryAction extends BaseAction<Inventory>{
	
	private IInventoryBiz inventoryBiz;

	public void setInventoryBiz(IInventoryBiz inventoryBiz) {
		this.inventoryBiz = inventoryBiz;
		setBaseBiz(inventoryBiz);
	}

	
	/**
	 * 添加盘赢盘亏action
	 */
	public void add(){
		try {
			Emp emp = getUser();
			Inventory inventory = getT();
			inventory.setCreater(emp.getUuid());
			inventoryBiz.add(inventory);
			write(ajaxReturn(true, "添加成功"));
		} catch (Exception e) {
			e.printStackTrace();
			write(ajaxReturn(false, "添加失败"));
		}
	}
	
	
	
	//盘赢盘亏审核 action
	public void doCheck(){
		
		Emp emp = getUser();
			try {
				inventoryBiz.doCheck(getId(), emp.getUuid());
				write(ajaxReturn(true, "审核成功"));
			}catch(UnauthorizedException e){
				e.printStackTrace();
				write(ajaxReturn(false, "当前用户不具有审核权限"));			
			}
			catch (Exception e) {
				e.printStackTrace();
				write(ajaxReturn(false, "审核失败"));
			}
	}



}
