package com.chinacreator.c2.omp.common.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.chinacreator.asp.sysmgmt.common.CommonTreeNode;
import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.dao.mybatis.enhance.Order;
import com.chinacreator.c2.dao.mybatis.enhance.Sortable;
import com.chinacreator.c2.omp.common.AdministrativeDivision;
import com.chinacreator.c2.omp.common.exception.ServiceException;
import com.chinacreator.c2.web.ds.TreeContentProvider;
import com.chinacreator.c2.web.ds.TreeContext;
import com.chinacreator.c2.web.ds.TreeNode;
/**
 * @author bing.huang
 *全国行政区域树
 * 2015-6-26
 */
public class AdministrativeDivisionService implements TreeContentProvider{
	/**
	 * 构造树节点
	 */
	@Override
	public TreeNode[] getElements(TreeContext context) {
		List<CommonTreeNode> list = new ArrayList<CommonTreeNode>();
		if (null != context)
		{
        
			
			//查询行政区域信息
			Dao<AdministrativeDivision> dao = DaoFactory.create(AdministrativeDivision.class);
			 Sortable sortable=new Sortable(); 
			List orderList = new ArrayList();
			Order order = new Order();
			order.setOrderProperty("CODE");
			order.setOrderDirection("ASC");
			orderList.add(order);
			sortable.setOrders(orderList);
			List<AdministrativeDivision> divisionInfo= dao.selectAll(sortable);
			CommonTreeNode serviceTypeTreeNode=null;
			for(AdministrativeDivision dcode: divisionInfo){
				serviceTypeTreeNode = new CommonTreeNode();				
				serviceTypeTreeNode.setId(dcode.getCode());
				serviceTypeTreeNode.setShowName(dcode.getName());
				serviceTypeTreeNode.setName(dcode.getName()+"("+dcode.getCode()+")");
				serviceTypeTreeNode.setPid(dcode.getParentCode());
				//判断是否存在子节点
				//serviceTypeTreeNode.setParent(this.existsChild(dcode.getCode()));					
				
				list.add(serviceTypeTreeNode);
			}
		
	}
		return (TreeNode[])list.toArray(new CommonTreeNode[list.size()]);
	}
	
	  /**
     * 判定是否存在子类
     * @param typeId
     * @return
     */
    public boolean existsChild(String code) throws ServiceException{
    	Dao<AdministrativeDivision> dao = DaoFactory.create(AdministrativeDivision.class);
    	SqlSession session = dao.getSession();
    	List<AdministrativeDivision>  divisionList=new ArrayList<AdministrativeDivision>();
    	boolean falg=true;
    	try {
			
    		//调用自定义sql获得查询结果
    		divisionList=session.selectList("com.chinacreator.c2.omp.common.AdministrativeDivisionMapper.existsChild",code); 
    		if(divisionList.size()==0){
    			falg=false;
    		}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
    	return falg;
   	
    }    
    
    private void recursionMonitorCategories(AdministrativeDivision ad,
			List<AdministrativeDivision> results) {
		results.add(ad);
		List<AdministrativeDivision> tmp = getMonitorCategoriesByParentId(ad.getCode());
		for (AdministrativeDivision ca : tmp) {
			recursionMonitorCategories(ca, results);
		}
	}
    
    //获得当前节点的所有子节点（包括自己）
    public List<AdministrativeDivision> getMonitorCategoriesByIdCascade(
			String adId) {
		List<AdministrativeDivision> results = new ArrayList<AdministrativeDivision>();
		List<AdministrativeDivision> children = getMonitorCategoriesByParentId(adId);

		AdministrativeDivision self = getMonitorCategoryById(adId);
		if (self != null)
			results.add(self);

		for (AdministrativeDivision category : children) {
			recursionMonitorCategories(category, results);
		}
		return results;
	}
    
    public List<AdministrativeDivision> getMonitorCategoriesByParentId(String parentId) {
    	Dao<AdministrativeDivision> dao = DaoFactory.create(AdministrativeDivision.class);
    	AdministrativeDivision condition = new AdministrativeDivision();
		condition.setParentCode(parentId);
		return dao.select(condition);
	}

    
    public AdministrativeDivision getMonitorCategoryById(String adId) {
    	Dao<AdministrativeDivision> dao = DaoFactory.create(AdministrativeDivision.class);
    	AdministrativeDivision condition = new AdministrativeDivision();
		condition.setCode(adId);
		AdministrativeDivision mc = dao.selectOne(condition);
		return mc;
	}
    
}
