package cn.itcast.erp.action;
import java.io.File;
import java.io.IOException;

import cn.itcast.erp.biz.IGoodsBiz;
import cn.itcast.erp.entity.Goods;

/**
 * 商品Action 
 * @author Administrator
 *
 */
public class GoodsAction extends BaseAction<Goods> {

	private IGoodsBiz goodsBiz;
	
	public void setGoodsBiz(IGoodsBiz goodsBiz) {
		this.goodsBiz = goodsBiz;
		setBaseBiz(goodsBiz);
	}
	
	private File file;
	private String fileContentType; // 文件的内容类型  
	private String fileFileName; // 上传文件名  
		
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileContentType() {
		return fileContentType;
	}
	
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String upload(){		
		try {
			goodsBiz.doImportData(file);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "list";
	}
}
