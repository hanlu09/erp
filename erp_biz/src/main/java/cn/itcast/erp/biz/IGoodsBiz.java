package cn.itcast.erp.biz;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import cn.itcast.erp.entity.Goods;
/**
 * 商品业务逻辑层接口
 * @author Administrator
 *
 */
public interface IGoodsBiz extends IBaseBiz<Goods>{
	/**
	 * 导入数据
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void doImportData(File file) throws FileNotFoundException, IOException;
	
}

