package cn.itcast.erp.biz.impl;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import cn.itcast.erp.biz.IGoodsBiz;
import cn.itcast.erp.dao.IGoodsDao;
import cn.itcast.erp.dao.IGoodstypeDao;
import cn.itcast.erp.entity.Goods;
import cn.itcast.erp.entity.Goodstype;
/**
 * 商品业务逻辑类
 * @author Administrator
 *
 */
public class GoodsBiz extends BaseBiz<Goods> implements IGoodsBiz {

	private IGoodsDao goodsDao;
	
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
		setBaseDao(goodsDao);
	}
	private IGoodstypeDao goodstypeDao;
	
	public void setGoodstypeDao(IGoodstypeDao goodstypeDao) {
		this.goodstypeDao = goodstypeDao;
	}

	/**
	 * 导入数据
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void doImportData(File file) throws FileNotFoundException, IOException{
		//从输入流中读取到工作簿
		HSSFWorkbook book=new HSSFWorkbook(new FileInputStream(file));
		//读取第一个工作表
		HSSFSheet sheet = book.getSheetAt(0);
		//得到最后一行
		int lastRowNum = sheet.getLastRowNum();
		
		for(int i=1;i<=lastRowNum;i++){
			HSSFRow row = sheet.getRow(i);
			String name = row.getCell(0).getStringCellValue();//商品名称
			String origin = row.getCell(1).getStringCellValue();//产地
			String producer = row.getCell(2).getStringCellValue();//厂家
			String unit = row.getCell(3).getStringCellValue();//计量单位
			double inprice = row.getCell(4).getNumericCellValue();//采购价格
			double outprice = row.getCell(5).getNumericCellValue();//销售价格
			String type = row.getCell(6).getStringCellValue();//类型
			
			Goods goods=new Goods();
			goods.setName(name);
			
			//查询商品名称是否存在
			List<Goods> goodsList = goodsDao.getList(goods, null, null);
			if(goodsList.size()>0){
				goods=goodsList.get(0);										
			}		
			
			goods.setOrigin(origin);
			goods.setProducer(producer);
			goods.setUnit(unit);
			goods.setInprice(inprice);
			goods.setOutprice(outprice);
			//goods.setGoodstype(goodstype);
			
			//商品类型
			Goodstype goodstype=new Goodstype();
			goodstype.setName(type);
			List<Goodstype> list = goodstypeDao.getList(goodstype, null, null);
			if(list.size()>0){
				goods.setGoodstype(list.get(0));				
			}else
			{
				goodstypeDao.add(goodstype);
				goods.setGoodstype(goodstype);
			}
			
			if(goodsList.size()==0){
				add(goods);
			}
		}
		
	}

	
}
