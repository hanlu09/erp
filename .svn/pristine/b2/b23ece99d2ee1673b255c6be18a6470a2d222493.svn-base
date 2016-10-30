package cn.itcast.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
import redis.clients.jedis.Jedis;
/**
 * 角色业务逻辑类
 * @author Administrator
 *
 */
public class RoleBiz extends BaseBiz<Role> implements IRoleBiz {

	private IRoleDao roleDao;
	
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		setBaseDao(roleDao);
	}

	private IMenuDao menuDao;
		
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	/*private Jedis jedis;
	
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}*/

	/**
	 * 读取角色权限
	 * @param uuid
	 * @return
	 */
	public List<Tree> readRoleMenus(Long uuid){
		//得到该角色的菜单集合
		List<Menu> menus = roleDao.get(uuid).getMenus();		
		List<Tree> trees=new ArrayList();//tree集合
		Menu menu0 = menuDao.get("0");//获取根菜单		
		for(Menu menu1:menu0.getMenus()){//循环一级菜单			
			Tree tree1=new Tree();
			tree1.setId(menu1.getMenuid());//一级菜单ID
			tree1.setText(menu1.getMenuname());//一级菜单名称						
			for(Menu menu2:menu1.getMenus()){//循环二级菜单				
				Tree tree2=new Tree();
				tree2.setId(menu2.getMenuid());//二级菜单ID
				tree2.setText(menu2.getMenuname());//二级菜单名称					
				//判断当前的菜单是否在该角色的菜单集合中出现
				if(menus.contains(menu2)){
					tree2.setChecked(true);//选中节点
				}				
				tree1.getChildren().add(tree2);//将二级菜单挂到一级菜单下
			}			
			trees.add(tree1);//将一级菜单添加到tree集合中
		}
		return trees;
	}
	
	/**
	 * 更新角色权限
	 * @param uuid
	 * @param checkedStr
	 */
	public void updateRoleMenus(Long uuid,String checkedStr){
		
		Role role = roleDao.get(uuid);//得到当前角色		
		role.setMenus(new ArrayList());//清空中间表原有的该角色的菜单数据		
		String[] menuIds = checkedStr.split(",");//根据逗号拆分为菜单ID		
		for(String menuId:menuIds)
		{
			Menu menu = menuDao.get(menuId);//根据菜单ID得到菜单
			role.getMenus().add(menu);//向中间表添加数据
		}
		
		
		/*清除拥有该角色的员工的菜单缓存		
		try {
			for(Emp emp:role.getEmps() ){			
				jedis.del("menuList_"+emp.getUuid());
				System.out.println("清除缓存menuList_"+emp.getUuid());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		
	}
	
}
