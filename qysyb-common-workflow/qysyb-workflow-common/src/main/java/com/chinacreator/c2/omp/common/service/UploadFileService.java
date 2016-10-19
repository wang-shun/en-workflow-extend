
package com.chinacreator.c2.omp.common.service;

import java.io.FileNotFoundException;
import java.util.List;



import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Page;
import com.chinacreator.c2.dao.mybatis.enhance.Pageable;
import com.chinacreator.c2.fs.FileServer;
import com.chinacreator.c2.fs.exception.FileNotExsitException;
import com.chinacreator.c2.ioc.ApplicationContextManager;
import com.chinacreator.c2.omp.common.UploadFile;

/**
 * 
 * 上传文件服务类
 * @author qiao.li
 * @version 1.0
 * @date 2015-5-29
 */

@Service
public class UploadFileService {
	private FileServer dirFileServer;

	/**
	 * 插入记录
	 * @param file
	 */
	public void addUploadFile(UploadFile file){
		Dao dao = DaoFactory.create(UploadFile.class);
		dao.insert(file);
	}
	/**
	 * 查询数据库中是否有指定记录
	 * @param businesstype 
	 * @param name
	 * @param businesskey
	 * @param taskid
	 * @return  有则返回记录
	 */
	public UploadFile existFileWithNameBusinessKeyTaskId(String businesstype,String name,String businesskey,String taskid){
		Dao dao = DaoFactory.create(UploadFile.class);
		UploadFile file = new UploadFile();
		file.setFileName(name);
		file.setBusinessType(businesstype);
		file.setBusinessKey(businesskey);
		//事件工单需要taskid区分环节
		if(businesstype.equals("FLOW_SJGD")){
			file.setTaskId(taskid);
		}
		List<UploadFile> files = dao.select(file);
		if(files == null||files.size()==0){
			return null;	
		}	
		else{
			return files.get(0);
		}
	}
	/**
	 * 删除指定记录
	 * @param file
	 */
	public int deleteFile(UploadFile file){
		Dao dao = DaoFactory.create(UploadFile.class);
		return dao.delete(file);
	}
	/**
	 * 更新文件
	 * @param file 带主键属性file
	 */
	public void updateFile(UploadFile file){
		Dao dao = DaoFactory.create(UploadFile.class);
		dao.update(file);
	}
 	/**
 	 * 获取指定id 和  key 的记录条数
 	 * @param businessType 请必填
 	 * @param businesskey 请必填
 	 * @param taskid
 	 * @return
 	 */
 	public List<UploadFile> getFiles(String businessType,String businessKey,String businessKey2){
 		Dao<UploadFile> dao = DaoFactory.create(UploadFile.class);
 		UploadFile file = new UploadFile();
 		file.setBusinessType(businessType);
 		file.setBusinessKey(businessKey);
 		file.setTaskId(businessKey2);
 		return dao.select(file);
 	}
 	
	/**
	 * 获取指定id 和  key 的记录条数
	 * @param businessType 请必填
	 * @param businesskey 请必填
	 * @param taskid
	 * @return
	 */
	public int getTotal(String businessType,String businesskey,String taskid){
		Dao dao = DaoFactory.create(UploadFile.class);
		UploadFile file = new UploadFile();
		file.setBusinessKey(businesskey);
		file.setTaskId(taskid);
		return dao.count(file);
	}
	/**
	 *获取分页数据
	 *@param businessType 请必填
	 * @param businesskey 请必填
	 * @param taskid
	 * @param pageable
	 * @return
	 */
	public Page getFilesPage(String businessType,String businesskey,String taskid,Pageable pageable){
		if(businessType == null || businessType == "undefined" || businessType == ""
				||businesskey == null || businesskey == "undefined" || businesskey == ""){
			return new Page(pageable, null);
		}
		Dao dao = DaoFactory.create(UploadFile.class);
		UploadFile file = new UploadFile();
		file.setBusinessType(businessType);
		file.setBusinessKey(businesskey);
		file.setTaskId(taskid);
		return dao.selectByPage(file, pageable, null, true);
	}
	
    //编程方式获取spring目录存储bean
    private FileServer getDirFileServer() {
        if (dirFileServer == null) {
            dirFileServer = ApplicationContextManager.getContext().getBean(
                    "dirFileServer", FileServer.class);
        }
        return dirFileServer;
    }
	
	/**
	 * 文件处理器处理文件删除
	 */
	@Transactional 
	public boolean processDelete(String path, Map<String, Object> arg1)
			throws Exception {
        try{
        	Dao<UploadFile> dao = DaoFactory.create(UploadFile.class);
            FileServer server = this.getDirFileServer();
            if(server.delete(path,true)){
            	UploadFile uf = new UploadFile();
            	uf.setFilePath(path);
            	UploadFile con = dao.selectOne(uf);
            	int r = dao.delete(con);
            	if(r==0){
            		throw new RuntimeException();  //回滚
            	}
            	return true;
            }else if(existFileWithPath(path)){
            	UploadFile uf = new UploadFile();
            	uf.setFilePath(path);
            	
            }
            
        }catch(FileNotFoundException e){
            throw new FileNotExsitException(path);        
        }
        return false;
	}
	/**
	 * 查看数据库中是否有此path记录文件
	 * @param path
	 * @return
	 */
	public boolean existFileWithPath(String path){
		Dao dao = DaoFactory.create(UploadFile.class);
		UploadFile file = new UploadFile();
		file.setFilePath(path);
		List<UploadFile> files = dao.select(file);
		if(files == null||files.size()==0){
			return false;	
		}	
		else{
			return true;
		}
	}
}
