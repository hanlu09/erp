package cn.itcast.erp.action;

import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IReturnordersBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Returnorderdetail;
import cn.itcast.erp.entity.Returnorders;
import cn.itcast.erp.exception.ErpException;

public class ReturnordersAction extends BaseAction<Returnorders> {

	private IReturnordersBiz returnordersBiz;

	public void setReturnordersBiz(IReturnordersBiz returnordersBiz) {
		this.returnordersBiz = returnordersBiz;
		setBaseBiz(returnordersBiz);
	}

	private String json;// json字符串(退单明细)

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	/**
	 * 采购退货登记
	 */
	public void add() {
		Emp emp = getUser();
		if (emp == null) {
			write(ajaxReturn(false, "当前用户未登录"));
			return;
		}

		try {
			// 获取前端提交的退货订单主表数据
			Returnorders returnorders = getT();
			// 当前登录用户为创建人
			returnorders.setCreater(emp.getUuid());
			List<Returnorderdetail> returnorderdetails = JSON.parseArray(json, Returnorderdetail.class);
			returnorders.setReturnorderdetails(returnorderdetails);
			returnordersBiz.add(returnorders);
			write(ajaxReturn(true, "退货订单添加成功"));
		} catch (ErpException e) {
			e.printStackTrace();
			write(ajaxReturn(false, e.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();
			write(ajaxReturn(false, "退货订单添加失败"));
		}
	}

	/**
	 * 采购退货审核
	 */
	public void doCheck() {
		Emp emp = getUser();
		if (emp == null) {
			write(ajaxReturn(false, "当前用户未登录"));
			return;
		}

		try {
			returnordersBiz.doCheck(getId(), emp.getUuid());
			write(ajaxReturn(true, "采购审核成功"));
		} catch (Exception e) {
			e.printStackTrace();
			write(ajaxReturn(false, "采购审核失败"));
		}
	}
	/**
	 * 销售退货审核
	 */
	public void doReturnCheck(){
		
		try {
			returnordersBiz.doReturnCheck(getId(), getUser().getUuid());
			write(ajaxReturn(true, "订单审核成功"));
		} catch (Exception e) {
			e.printStackTrace();
			write(ajaxReturn(false, "订单审核失败"));
		}
	}
}
