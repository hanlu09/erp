package cn.itcast.erp.biz.impl;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
import cn.itcast.erp.exception.ErpException;
import redis.clients.jedis.Jedis;
/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private IEmpDao empDao;
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		setBaseDao(empDao);
	}
	
	private IRoleDao roleDao;
	
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	/*private Jedis jedis;
	
	public void setJedis(Jedis jedis) {
		this.jedis = jedis;
	}*/

	/**
	 * 增加员工
	 */
	public void add(Emp emp){
		
		//参数1:source 源密码
		//参数2:salt  盐     （增加破解难度）
		//参数3:hashIterations   散列次数      加密（ 加密（源字符串））  增加破解难度
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String pwd=emp.getUsername()+sdf.format(emp.getBirthday());//初始密码为登陆名+出生年月日字符串yyyyMMdd
		
		Md5Hash md5=new Md5Hash(pwd, emp.getUsername(), 2);
		emp.setPwd(md5.toString());
		
		empDao.add(emp);
	}

	/**
	 * 根据用户名和密码查询员工
	 */
	public Emp findByUsernameAndPwd(String username, String pwd) {
		
		return empDao.findByUsernameAndPwd(username, pwd);
	}

	/**
	 * 读取用户角色
	 * @param uuid
	 * @return
	 */
	public List<Tree>  readEmpRoles(Long uuid){
		//获得该员工的角色列表
		List<Role> roles = empDao.get(uuid).getRoles();		
		List<Tree> trees=new ArrayList();		
		List<Role> roleList = roleDao.getList(null, null, null);
		for(Role role:roleList){			
			Tree tree=new Tree();
			tree.setId(String.valueOf( role.getUuid()));
			tree.setText(role.getName());//角色名称
			if(roles.contains(role)){
				tree.setChecked(true);
			}
			trees.add(tree);
		}
		return trees;
	}
	
	/**
	 * 更新用户角色
	 * @param uuid
	 * @param checkedStr
	 */
	public void updateEmpRoles(Long uuid,String checkedStr){
		
		Emp emp = empDao.get(uuid);//获得当前员工对象
		emp.setRoles(new ArrayList());//清空原有的角色
		String[] roleIds = checkedStr.split(",");//获取角色ID集合
				
		for(String roleId:roleIds){			
			Role role = roleDao.get(Long.valueOf(roleId));
			emp.getRoles().add(role);
		}
		
		/*清除 redis中的缓存
		try {
			jedis.del("menuList_"+uuid);
			System.out.println("清除 redis中的缓存");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	
	/**
	 * 修改密码
	 * @param uuid
	 * @param oldPwd
	 * @param newPwd
	 */
	public void updatePwd(Long uuid,String oldPwd,String newPwd){
		
		Emp emp = empDao.get(uuid);		
		//判断该员工输入的密码是否正确
		Md5Hash md5_1=new Md5Hash(oldPwd, emp.getUsername(), 2);
		if(!md5_1.toString().equals(emp.getPwd())){			
			throw new ErpException("原密码错误");
		}		
		//保存新密码
		Md5Hash md5_2 =new Md5Hash(newPwd, emp.getUsername(), 2);
		emp.setPwd(md5_2.toString());		
	}
	
	
	/**
	 * 管理员重置密码
	 * @param uuid
	 * @param newPwd
	 */
	public void updatePwd_reset(Long uuid,String newPwd){
		
		Emp emp = empDao.get(uuid);			
		//保存新密码
		Md5Hash md5_2 =new Md5Hash(newPwd, emp.getUsername(), 2);
		emp.setPwd(md5_2.toString());	
	}
	
	
}
