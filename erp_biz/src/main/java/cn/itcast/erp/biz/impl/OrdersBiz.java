package cn.itcast.erp.biz.impl;
import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.exception.ErpException;
/**
 * 订单业务逻辑类
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;
	
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		setBaseDao(ordersDao);
	}
	
	/**
	 * 增加
	 */
	public void add(Orders orders){
		
		if(!orders.getType().equals("1") && !orders.getType().equals("2") ){
			throw new ErpException("参数非法");
		}
		
		//授权控制
		Subject subject=org.apache.shiro.SecurityUtils.getSubject();
		
		//采购订单
		if(orders.getType().equals("1")){
			if(!subject.isPermitted("采购申请")){
				throw new ErpException("当前用户没有采购申请权限");
			}			
		}
		//销售订单
		if(orders.getType().equals("2")){
			if(!subject.isPermitted("销售订单录入")){
				throw new ErpException("当前用户没有销售订单录入权限");
			}	
		}
		
		orders.setCreatetime(new Date());//创建日期
		//orders.setType("1");//订单类型  1：采购订单  2：销售订单
		orders.setState("0");//订单状态 0：未审核  1：已审核  2：已确认 3：已入库
		
		double money=0;//总金额
		for(Orderdetail orderdetail:orders.getOrderdetails())
		{
			orderdetail.setState("0");//订单明细状态  0：未入库  1：已入库
			money+=orderdetail.getMoney();			
		}
		orders.setTotalmoney(money);//总金额
		
		ordersDao.add(orders);
	}

	/**
	 * 采购审核
	 * @param uuid
	 */
	@RequiresPermissions("采购审核")
	public void doCheck(Long uuid,Long empUuid){		
		Orders orders = ordersDao.get(uuid);
		orders.setChecktime(new Date());//审核时间
		orders.setChecker(empUuid);//审核人 
		orders.setState("1");//状态 1：已审核			
	}
	
	/**
	 * 采购确认
	 * @param uuid
	 * @param empUuid
	 */
	@RequiresPermissions("采购确认")
	public void doStart(Long uuid,Long empUuid){
		Orders orders=ordersDao.get(uuid);
		orders.setStarttime(new Date());//确认时间
		orders.setStarter(empUuid);//确认人
		orders.setState("2");//状态2：已确认
	}
	
}
