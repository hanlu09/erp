package cn.itcast.erp.biz;
import java.util.List;

import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;
/**
 * 员工业务逻辑层接口
 * @author Administrator
 *
 */
public interface IEmpBiz extends IBaseBiz<Emp>{
	
	/**
	 * 根据用户名和密码查询员工实体
	 * @param username
	 * @param pwd
	 * @return
	 */
	public Emp findByUsernameAndPwd(String username,String pwd);
	
	/**
	 * 读取用户角色
	 * @param uuid
	 * @return
	 */
	public List<Tree>  readEmpRoles(Long uuid);
	
	
	/**
	 * 更新用户角色
	 * @param uuid
	 * @param checkedStr
	 */
	public void updateEmpRoles(Long uuid,String checkedStr);
	
	
	/**
	 * 修改密码
	 * @param uuid
	 * @param oldPwd
	 * @param newPwd
	 */
	public void updatePwd(Long uuid,String oldPwd,String newPwd);
	
	/**
	 * 管理员重置密码
	 * @param uuid
	 * @param newPwd
	 */
	public void updatePwd_reset(Long uuid,String newPwd);
}

