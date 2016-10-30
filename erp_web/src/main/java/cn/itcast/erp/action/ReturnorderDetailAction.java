package cn.itcast.erp.action;

import cn.itcast.erp.biz.IReturnorderdetailBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Returnorderdetail;
import cn.itcast.erp.exception.ErpException;

public class ReturnorderDetailAction extends BaseAction<Returnorderdetail> {
	
	private IReturnorderdetailBiz returnorderdetailBiz;
	
	public void setReturnorderdetailBiz(IReturnorderdetailBiz returnorderdetailBiz) {
		this.returnorderdetailBiz = returnorderdetailBiz;
		setBaseBiz(returnorderdetailBiz);
	}

	private Long storeUuid;//仓库ID
	
	public Long getStoreUuid() {
		return storeUuid;
	}
	public void setStoreUuid(Long storeUuid) {
		this.storeUuid = storeUuid;
	}


	/**
	 * 销售退货入库
	 */
	public void doInStore(){
		Emp emp = getUser();
		if(emp==null){
			write(ajaxReturn(false, "当前用户未登陆"));
			return ;
		}
				
		try {
			returnorderdetailBiz.doInStore(getId(), emp.getUuid(), storeUuid);
			write(ajaxReturn(true, "入库成功"));
		} catch (Exception e) {
			e.printStackTrace();
			write(ajaxReturn(false, "入库失败"));
		}
	}
	/*
	 * 采购退货出库
	 */
	public void doOutStore() {
		Emp emp = getUser();
		if(emp==null){
			write(ajaxReturn(false, "当前用户未登陆"));
			return ;
		}
				
		try {
			returnorderdetailBiz.doOutStore(getId(), emp.getUuid(), storeUuid);
			write(ajaxReturn(true, "出库成功"));
		}catch(ErpException e) {
			e.printStackTrace();
			write(ajaxReturn(false, e.getMessage()));
		}catch (Exception e) {
			e.printStackTrace();
			write(ajaxReturn(false, "出库失败"));
		}
	}
}
