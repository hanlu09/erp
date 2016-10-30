package cn.itcast.erp.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import cn.itcast.erp.entity.Emp;

	/*
	 * 日志类
	 */
@Component("logger")
@Aspect//定义切面
public class Logger {
	
	//定义切入点
	@Pointcut("execution(* cn.itcast.erp.action.LoginAction.checkUser())")
	private void print1(){}
	
	@Pointcut("execution(* cn.itcast.erp.action.LoginAction.loginOut())")
	private void print2(){}
	
	//定义登录通知
	@After("print1()")
	public void log1() {
		if(getUser() != null) {
			String message = getCurrentTime() + "：" +getUser().getName()+ "用户登录";
			//记录日志
			save(message);
			System.out.println(getCurrentTime() + "：" +getUser().getName()+ "用户登录"); 
		}
	}
	//定义退出通知
	@Before("print2()")
	public void log2() {
		if(getUser() != null) {
			//记录日志
			String message = getCurrentTime() + "：" +getUser().getName()+ "用户正常退出";
			save(message);
			System.out.println(getCurrentTime() + "：" +getUser().getName()+ "用户退出");
		}
	}
	/*
	 * 获取当前时间
	 */
	public String getCurrentTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	/**
	 * 获取当前登陆用户
	 * @return
	 */
	public Emp getUser(){		
		Subject subject = org.apache.shiro.SecurityUtils.getSubject();
		return (Emp)subject.getPrincipal();		
	}
	/*
	 * 在日志文件中记录信息
	 */
	public void save(String message){
		//定义日志文件的位置，不存在就创建
		String path = ServletActionContext.getServletContext().getRealPath("/WEB-INF/logger");
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
		//定义日志文件名
		File newFile = new File(file,"logger.txt");
		//定义变量记录日志文件是否已经存在
		boolean flag = false;
		if(newFile.exists()) {
			flag = true;
		}
		FileOutputStream fos = null;
		try {
			//定义输出流，开启追加功能不覆盖源文件
			fos = new FileOutputStream(newFile,true);
			//判断日志文件是否已经存在
			if(flag) {
				//如果已经存在，输入信息前加换行
				fos.write("\r\n".getBytes());
			}
			fos.write(message.getBytes());
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
