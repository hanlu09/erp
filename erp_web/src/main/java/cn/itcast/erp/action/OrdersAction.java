package cn.itcast.erp.action;
import java.util.List;

import org.apache.shiro.authz.UnauthorizedException;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.exception.ErpException;

/**
 * 订单Action 
 * @author Administrator
 *
 */
public class OrdersAction extends BaseAction<Orders> {

	private IOrdersBiz ordersBiz;
	
	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		setBaseBiz(ordersBiz);
	}
	
	
	private String json;//json字符串（订单明细）
	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	/**
	 * 增加
	 */
	public void add(){
		Emp emp= getUser();//当前登陆用户
		if(emp==null){
			write(ajaxReturn(false, "当前用户未登陆"));
			return ;
		}		
		try {
			Orders orders = getT();//得到前端提交的订单主表数据			
			orders.setCreater(emp.getUuid());//当前登陆人为创建人			
			//将订单明细的json字符串转换为订单明细的集合
			List<Orderdetail> orderdetailList = JSON.parseArray(json, Orderdetail.class);
			//将订单明细添加到订单实体
			orders.setOrderdetails(orderdetailList);			
			ordersBiz.add(orders);
			write(ajaxReturn(true, "增加订单成功"));
		} catch (ErpException e) {			
			e.printStackTrace();
			write(ajaxReturn(false, e.getMessage()));
		}catch (Exception e) {			
			e.printStackTrace();
			write(ajaxReturn(false, "增加订单失败"));
		}
	}
	
	/**
	 * 采购审核
	 */
	public void doCheck(){
		Emp emp = getUser();
		if(emp==null){
			write(ajaxReturn(false, "当前用户未登陆"));
			return ;
		}
		
		try {
			ordersBiz.doCheck(getId(), emp.getUuid());
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
	
	
	/**
	 * 采购确认
	 */
	public void doStart(){
		Emp emp = getUser();
		if(emp==null){
			write(ajaxReturn(false, "当前用户未登陆"));
			return ;
		}
		
		try {
			ordersBiz.doStart(getId(), emp.getUuid());
			write(ajaxReturn(true, "确认成功"));
		} catch (Exception e) {
			e.printStackTrace();
			write(ajaxReturn(false, "确认失败"));
		}
		
	}
	
}
