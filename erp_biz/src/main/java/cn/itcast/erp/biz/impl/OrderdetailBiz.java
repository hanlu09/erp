package cn.itcast.erp.biz.impl;
import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.dao.IOrderdetailDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.entity.Storeoper;
import cn.itcast.erp.exception.ErpException;
/**
 * 订单明细业务逻辑类
 * @author Administrator
 *
 */
public class OrderdetailBiz extends BaseBiz<Orderdetail> implements IOrderdetailBiz {

	private IOrderdetailDao orderdetailDao;
	
	public void setOrderdetailDao(IOrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
		setBaseDao(orderdetailDao);
	}
	
	private IStoredetailDao storedetailDao;
	
	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	private IStoreoperDao storeoperDao;
	
	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}

	/**
	 * 采购入库
	 * @param uuid 订单明细ID
	 * @param empUuid 员工ID
	 * @param storeUuid 仓库ID
	 */
	@RequiresPermissions("采购入库")
	public void doInStore(Long uuid,Long empUuid,Long storeUuid){
		
		//1.修改订单明细的状态等字段
		Orderdetail orderdetail = orderdetailDao.get(uuid);
		orderdetail.setEndtime(new Date());//入库时间
		orderdetail.setEnder(empUuid);//入库操作人		
		orderdetail.setStoreuuid(storeUuid);//仓库ID
		orderdetail.setState("1");//订单明细状态  1:已入库
		//2.修改库存数量
		
		//2.1 根据仓库ID和商品ID查询  商品仓库库存表  ，
		Storedetail sd=new Storedetail();//构建查询条件
		sd.setStoreuuid(storeUuid);//仓库ID
		sd.setGoodsuuid(orderdetail.getGoodsuuid());//商品ID
		List<Storedetail> storedetailList = storedetailDao.getList(sd, null, null);//按条件查询
		//如果没有则增加库存记录
		if(storedetailList.size()==0){
			sd.setNum(orderdetail.getNum());//库存为订单明细的商品数量
			storedetailDao.add(sd);
		}else{//如果存在记录则在原有库存数量基础上增加库存
			Storedetail storedetail = storedetailList.get(0);
			//在原有库存基础上增加订单明细中的商品数量
			storedetail.setNum(storedetail.getNum()+orderdetail.getNum());
		}
				
		//3.增加库存变动记录
		Storeoper storeoper=new Storeoper();
		storeoper.setEmpuuid(empUuid);//员工ID
		storeoper.setGoodsuuid(orderdetail.getGoodsuuid());//商品ID
		storeoper.setNum(orderdetail.getNum());//操作数量
		storeoper.setOpertime(orderdetail.getEndtime());//操作时间
		storeoper.setStoreuuid(storeUuid);//仓库ID
		storeoper.setType("1");//类型  1：入库  2：出库
		storeoperDao.add(storeoper);//增加记录
		
		//4.当该订单明细的所属订单的所有订单明细均已入库，则修改订单的状态为3 （已入库）
		
		//查询该订单明细的所属订单的订单明细的状态为0的个数  如果个数为0  代表所有订单明细均已入库
		Orders orders = orderdetail.getOrders();//得到该订单明细的所属订单
		Orderdetail od=new Orderdetail();
		od.setState("0");//状态为0  未入库
		od.setOrders(orders);//该订单明细的所属订单
		long count = orderdetailDao.getCount(od, null, null);
		
		if(count==0){
			//修改订单主表的状态等信息			
			orders.setEndtime(orderdetail.getEndtime());//操作时间
			orders.setEnder(empUuid);
			orders.setState("3");//订单状态 3：已入库			
		}
		
	}
	

	/**
	 * 销售出库
	 * @param uuid 订单明细ID
	 * @param empUuid 员工ID
	 * @param storeUuid 仓库ID
	 */
	@RequiresPermissions("销售出库")
	public void doOutStore(Long uuid,Long empUuid,Long storeUuid){
		
		//1.修改订单明细的状态等字段
		Orderdetail orderdetail = orderdetailDao.get(uuid);
		orderdetail.setEndtime(new Date());//出库时间
		orderdetail.setEnder(empUuid);//出库操作人		
		orderdetail.setStoreuuid(storeUuid);//仓库ID
		orderdetail.setState("1");//订单明细状态  1:已出库  
		//2.修改库存数量
		
		//2.1 根据仓库ID和商品ID查询  商品仓库库存表  ，
		Storedetail sd=new Storedetail();//构建查询条件
		sd.setStoreuuid(storeUuid);//仓库ID
		sd.setGoodsuuid(orderdetail.getGoodsuuid());//商品ID
		List<Storedetail> storedetailList = storedetailDao.getList(sd, null, null);//按条件查询
		//如果没有则？？？
		if(storedetailList.size()==0){
			throw new ErpException("库存不足");			
		}else{//如果存在记录则在原有库存数量基础上减少库存
			Storedetail storedetail = storedetailList.get(0);
			//在原有库存基础上增加订单明细中的商品数量
			storedetail.setNum(storedetail.getNum()-orderdetail.getNum());
			if(storedetail.getNum()<0){
				throw new ErpException("库存不足");
			}
		}
				
		//3.增加库存变动记录
		Storeoper storeoper=new Storeoper();
		storeoper.setEmpuuid(empUuid);//员工ID
		storeoper.setGoodsuuid(orderdetail.getGoodsuuid());//商品ID
		storeoper.setNum(orderdetail.getNum());//操作数量
		storeoper.setOpertime(orderdetail.getEndtime());//操作时间
		storeoper.setStoreuuid(storeUuid);//仓库ID
		storeoper.setType("2");//类型  1：入库  2：出库
		storeoperDao.add(storeoper);//增加记录
		
		//4.当该订单明细的所属订单的所有订单明细均已出库，则修改订单的状态为3 （已出库）
		
		//查询该订单明细的所属订单的订单明细的状态为0的个数  如果个数为0  代表所有订单明细均已入库
		Orders orders = orderdetail.getOrders();//得到该订单明细的所属订单
		Orderdetail od=new Orderdetail();
		od.setState("0");//状态为0  未出库
		od.setOrders(orders);//该订单明细的所属订单
		long count = orderdetailDao.getCount(od, null, null);
		
		if(count==0){
			//修改订单主表的状态等信息			
			orders.setEndtime(orderdetail.getEndtime());//操作时间
			orders.setEnder(empUuid);
			orders.setState("3");//订单状态 3：已出库			
		}
		
	}
	
}
