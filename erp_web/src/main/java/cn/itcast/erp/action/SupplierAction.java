package cn.itcast.erp.action;
import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.entity.Supplier;

/**
 * 供应商Action 
 * @author Administrator
 *
 */
public class SupplierAction extends BaseAction<Supplier> {

	private ISupplierBiz supplierBiz;
	
	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		setBaseBiz(supplierBiz);
	}
	
	
	
	/**
	 * 查询供应商列表
	 */
	public void list(){
		Supplier supplier=getT1();
		if(supplier==null){
			supplier=new Supplier();
			setT1(supplier);					
		}		
		supplier.setName(getQ());		
		super.list();
	}
	
	
}
