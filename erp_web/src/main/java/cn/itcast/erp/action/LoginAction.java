package cn.itcast.erp.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.itheima.soft.biz.impl.ISoftctrService;

import cn.itcast.erp.entity.Emp;

/**
 * 登陆action
 * @author Administrator
 *
 */
public class LoginAction {
	
	private ISoftctrService softctrService;//软件认证系统服务
	
	public void setSoftctrService(ISoftctrService softctrService) {
		this.softctrService = softctrService;
	}
	
	private String username;//登陆名
	private String pwd;//密码
	
	
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	/**
	 * 登陆 
	 */
	public void checkUser(){
		
		//0.密码加密
		Md5Hash md5=new Md5Hash(pwd, username, 2);
		
		//1.创建令牌(用户名密码的封装)
		UsernamePasswordToken token=new UsernamePasswordToken(username, md5.toString());
		//2.得到Subject(主题,  代表当前用户以及当前用户的一些行为 登陆   )
		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
		
		//3.执行认证		
		try {
			subject.login(token);
			write(ajaxReturn(true, "登陆成功"));
		
		} catch (AuthenticationException e) {
			e.printStackTrace();
			write(ajaxReturn(false, "用户名或密码错误"));
		}
		
		
	}
	
	/**
	 * 退出登陆
	 */
	public void loginOut(){
		Subject subject=org.apache.shiro.SecurityUtils.getSubject();
		subject.logout();//退出登陆
	}
	
	/**
	 * 显示当前登陆人姓名
	 */
	public void showName(){
		Subject subject=org.apache.shiro.SecurityUtils.getSubject();
		Emp emp = (Emp) subject.getPrincipal();		
		if(emp!=null){
			write(ajaxReturn(true, emp.getName()));			
		}else{			
			write(ajaxReturn(false, ""));
		}		
	}
	
	/**
	 * 输出字符串
	 * @param jsonString
	 */
	public void write(String jsonString){

		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");		
		try {
			response.getWriter().print(jsonString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	/**
	 * 标准结构返回体
	 * @param success
	 * @param message
	 * @return
	 */
	public String ajaxReturn(boolean success,String message){
		
		Map map=new HashMap();
		map.put("success", success);
		map.put("message", message);
		return JSON.toJSONString(map);		
	}
	
}
