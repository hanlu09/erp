package cn.itcast.erp.realm;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;
/**
 * 自定义 realm
 * @author Administrator
 *
 */
public class ErpRealm extends AuthorizingRealm {

	private IEmpBiz empBiz;
	
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}

	private IMenuBiz menuBiz;
		
	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
	}

	/**
	 * 授权  给当前用户赋予权限
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		Emp emp= (Emp) arg0.getPrimaryPrincipal();
		//得到当前用户的菜单列表
		List<Menu> menus = menuBiz.getMenusByEmpuuid(emp.getUuid());
		
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		for(Menu menu :menus){
			info.addStringPermission(menu.getMenuname());//向当前用户授权
		}
		
		return info;
	}

	/**
	 * 认证
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
		UsernamePasswordToken token=(UsernamePasswordToken)arg0;//获取令牌
		String username = token.getUsername();//得到用户名
		String pwd=new String(token.getPassword());//得到密码 
		
		Emp emp = empBiz.findByUsernameAndPwd(username, pwd);//判断用户名密码是否正确
		if(emp==null){
			return null;
		}		
		//参数1：principal  主角对象
		//参数2：credentials 密码
		//参数3：realmName  固定写法 getName()
		return new SimpleAuthenticationInfo(emp, emp.getPwd(), getName());
	}

}
