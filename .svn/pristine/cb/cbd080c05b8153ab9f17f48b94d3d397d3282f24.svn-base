package cn.itcast.erp.biz;
import java.util.List;

import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
/**
 * 角色业务逻辑层接口
 * @author Administrator
 *
 */
public interface IRoleBiz extends IBaseBiz<Role>{
	
	
	/**
	 * 根据角色查找菜单树
	 * @param uuid
	 * @return
	 */
	public List<Tree> readRoleMenus(Long uuid);
	
	/**
	 * 更新角色权限
	 * @param uuid
	 * @param checkedStr
	 */
	public void updateRoleMenus(Long uuid,String checkedStr);
	
}

