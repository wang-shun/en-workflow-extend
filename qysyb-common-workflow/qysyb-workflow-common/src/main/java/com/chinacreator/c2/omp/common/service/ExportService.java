package com.chinacreator.c2.omp.common.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import org.springframework.stereotype.Service;

import com.chinacreator.c2.config.ConfigManager;

import sun.misc.BASE64Decoder;
/**
 * 导出公共类
 * @author tao.wang1
 *
 */
@Service
public class ExportService {
	/**
	 * 
	 *@title 根据base64数据生成图片
	 *@param fileName 文件名称
	 *@param imgUrl
	 *@param filePath
	 *@return  String
	 *@date 2015年12月30日
	 *@author tao.wang1
	 */
	public String base64TOpictrue(String fileName,String imgUrl,String filePath) {
		if(null==filePath||filePath.isEmpty()){
			filePath = ConfigManager.getInstance().getConfig("c2.dirfile.rootFolder.export");//文件保存路径
		}
		//图片位置
		String pictruePath=filePath+fileName+".png";
        //对字节数组字符串进行Base64解码并生成图片
        if (imgUrl == null) //图像数据为空
            return "";
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            String[] url = imgUrl.split(",");
            String u = url[1];
            //Base64解码
            byte[] buffer = new BASE64Decoder().decodeBuffer(u);
            //生成图片
            OutputStream out = new FileOutputStream(new File(pictruePath));    
            out.write(buffer);
            out.flush();
            out.close();
            return pictruePath;  
        } 
        catch (Exception e) 
        {
            return "";
        }
    }	
}
