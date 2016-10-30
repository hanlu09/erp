package cn.itcast.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.entity.Menu;
import redis.clients.jedis.Jedis;
/**
 * 菜单业务逻辑类
 * @author Administrator
 *
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

	private IMenuDao menuDao;
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		setBaseDao(menuDao);
	}
	
	/*private Jedis jedis;
	
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}*/

	/**
	 * 根据员工ID查询菜单集合
	 */
	public List<Menu> getMenusByEmpuuid(Long empUuid) {
		
		/*从缓存中提取数据，如果缓冲中有数据则返回缓存中的数据
		String menuListJson = jedis.get("menuList_"+empUuid);
		if(menuListJson!=null){
			System.out.println("从缓存中提取员工菜单集合");
			return JSON.parseArray(menuListJson, Menu.class);
						
		}else//如果缓存中没有数据，则从数据库中提取数据，并且存入缓存
		{
			System.out.println("从数据库中提取员工菜单集合放入缓存");
			List<Menu> menus = menuDao.getMenusByEmpuuid(empUuid);
			jedis.set("menuList_"+empUuid, JSON.toJSONString(menus));
			return menus;
		}
		*/
		return menuDao.getMenusByEmpuuid(empUuid);
	}
	
	/**
	 * 克隆菜单
	 * @param menu
	 * @return
	 */
	private Menu createMenu(Menu menu){
		Menu m=new Menu();
		m.setMenuid(menu.getMenuid());
		m.setMenuname(menu.getMenuname());
		m.setIcon(menu.getIcon());
		m.setUrl(menu.getUrl());
		m.setMenus(new ArrayList());
		return m;		
	}

	/**
	 * 根据员工ID查询菜单树
	 * @param empUuid
	 * @return
	 */
	public Menu readMenuByEmpuuid(Long empUuid){
		//查询该员工的拥有的菜单集合
		List<Menu> menus = menuDao.getMenusByEmpuuid(empUuid);
		Menu menu0 = menuDao.get("0");
		Menu m0=createMenu(menu0);//克隆根菜单		
		for(Menu menu1:menu0.getMenus()){			
			Menu m1=createMenu(menu1);//克隆一级菜单			
			for(Menu menu2:menu1.getMenus()){					
				if(menus.contains(menu2)){
					Menu m2=createMenu(menu2);//克隆二级菜单
					m1.getMenus().add(m2);//将二级菜单挂到一级菜单下
				}
			}
			if(m1.getMenus().size()>0){//当新的一级菜单下有二级菜单
				m0.getMenus().add(m1);//将一级菜单挂到根菜单下
			}
		}		
		return m0;
	}
	
	
}
