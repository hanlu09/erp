package cn.itcast.erp.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
/**
 * 自定义授权过滤器----
 * @author Administrator
 *
 */
public class MyFilter extends AuthorizationFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
		
		
		Subject subject=getSubject(request, response);
		//获取权限名称列表 
		String[] perms= (String[])mappedValue;//得到的是shiro配置文件中perms[]中的内容
		if(perms==null || perms.length==0){
			return true;
		}		
		//遍历权限
		for(String p:perms){			
			if(subject.isPermitted(p)){
				return true;
			}			
		}		
		return false;
	}

}
