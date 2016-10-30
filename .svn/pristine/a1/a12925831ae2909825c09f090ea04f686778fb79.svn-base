package cn.itcast.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;
import cn.itcast.erp.exception.ErpException;

/**
 * 员工Action 
 * @author Administrator
 *
 */
public class EmpAction extends BaseAction<Emp> {

	private IEmpBiz empBiz;
	
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		setBaseBiz(empBiz);
	}
	
	/**
	 * 读取员工角色
	 */
	public void readEmpRoles(){
		List<Tree> trees = empBiz.readEmpRoles(getId());
		String jsonString = JSON.toJSONString(trees);
		write(jsonString);
	}
	
	private String checkedStr;//角色ID串
		
	public String getCheckedStr() {
		return checkedStr;
	}

	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}

	/**
	 * 更新员工角色
	 */
	public void updateEmpRoles(){
		
		try {
			empBiz.updateEmpRoles(getId(), checkedStr);
			write(ajaxReturn(true, "更新用户角色成功"));
		} catch (Exception e) {
			e.printStackTrace();
			write(ajaxReturn(false, "更新用户角色失败"));
		}
		
	}
	
	private String oldPwd;//原密码
	private String newPwd;//新密码
		
	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	/**
	 * 修改密码 
	 */
	public void updatePwd(){		
		try {
			empBiz.updatePwd(getUser().getUuid(), oldPwd, newPwd);
			write(ajaxReturn(true, "修改密码成功"));
		} catch (ErpException e) {
			e.printStackTrace();
			write(ajaxReturn(false, e.getMessage()));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			write(ajaxReturn(false, "修改密码失败"));
		}
	}
	
	/**
	 * 管理员重置密码 
	 */
	public void updatePwd_reset(){		
		try {
			empBiz.updatePwd_reset(getId(), newPwd);
			write(ajaxReturn(true, "修改密码成功"));
		} catch (Exception e) {
			e.printStackTrace();
			write(ajaxReturn(false, "修改密码失败"));
		}
	}
	
	
}
