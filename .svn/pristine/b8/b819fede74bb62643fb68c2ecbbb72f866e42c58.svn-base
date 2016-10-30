package cn.itcast.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;

/**
 * 角色Action 
 * @author Administrator
 *
 */
public class RoleAction extends BaseAction<Role> {

	private IRoleBiz roleBiz;
	
	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		setBaseBiz(roleBiz);
	}
	
	/**
	 * 读取角色菜单
	 */
	public void readRoleMenus(){
		
		List<Tree> trees = roleBiz.readRoleMenus(getId());
		String jsonString = JSON.toJSONString(trees, true);
		write(jsonString);		
	}
	
	private String checkedStr;//前端传递过来的菜单ID集合
		
	public String getCheckedStr() {
		return checkedStr;
	}

	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}

	/**
	 * 更新角色菜单
	 */
	public void updateRoleMenus(){
		
		try {
			roleBiz.updateRoleMenus(getId(), checkedStr);
			write(ajaxReturn(true, "更新角色权限成功"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			write(ajaxReturn(false, "更新角色权限失败"));
		}
		
	}
	
}
