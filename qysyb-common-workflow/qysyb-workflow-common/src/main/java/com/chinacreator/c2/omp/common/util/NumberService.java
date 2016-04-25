package com.chinacreator.c2.omp.common.util;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.stereotype.Service;




import com.chinacreator.c2.dao.Dao;
import com.chinacreator.c2.dao.DaoFactory;
import com.chinacreator.c2.omp.common.Sequence;
@Service
public class NumberService {
	final static int YYYYNNNNN = 1;
	Dao daoseq;
	/**
	 * 针对指定实体生成yyyy-xxxxx 样式编码 如2015-00001  还需要在实体对应map中插入模糊sql查询语句参考 ServiceCatalog实体。
	 * @param cls  实体class名，com.chinacreator.c2.kcomp.servicemanagement.ServiceClass
	 * @param method  编号在实体中对应属性方法名 如 getServiceClassNo
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */

	public String autoNumWithyear(String cls,String method) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{	
		if(daoseq==null){
			daoseq = DaoFactory.create(Sequence.class);
		} 
		Sequence seq = new Sequence();
		seq.setName(cls);
		Sequence seqsel = (Sequence) daoseq.selectOne(seq);
		if(seqsel==null||seqsel.getCurvalue()==null){
			String no = init(YYYYNNNNN);
			seq.setCurvalue(no);
			if(seqsel==null){
				daoseq.insert(seq);
			}else{
				daoseq.update(seq);
			}			
			return no;
		}else{
			String no = nextValue(YYYYNNNNN,seqsel.getCurvalue());
			seqsel.setCurvalue(no);
			daoseq.update(seqsel);
			return no;
		}
		
//		Class<?> cls1 = Class.forName(cls);

//		Dao dao = DaoFactory.create(cls1);
//		String matchstr = year+"-_____";
//		Map<String,String> map = new HashMap<String,String>();
//		map.put("matchstr", matchstr);
//		List list = dao.getSession().selectList(cls+"Mapper.selectLikeAll", map);//需要优化
//		if(list.isEmpty())
//			return year+"-00001";
//		ArrayList<String> array = new ArrayList<String>();
//		int maxnum = 0;
//		for(Object sc:list){
//			String s = (String) cls1.getMethod(method).invoke(sc);
//			String numstr =  s.substring(s.length()-5,s.length());
//			int num = Integer.valueOf(numstr);
//			if(num>maxnum)
//				maxnum = num;
//		}
//		maxnum++;
//		String maxstr = maxnum>=10000?(String.valueOf((maxnum/10000)%10)):"0"+(maxnum>=1000?(String.valueOf((maxnum/1000)%10)):"0")+(maxnum>=100?(String.valueOf((maxnum/100)%10)):"0")+(maxnum>=10?(String.valueOf((maxnum/10)%10)):"0")+(String.valueOf(maxnum%10));
//		//System.out.println("liqiaomark-----"+list.toString());
//		return year+"-"+maxstr;
	}

	private String nextValue(int type, String curvalue) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		if(type==YYYYNNNNN){
			String s[] = curvalue.split("-");
			if(s.length==2){
				String yearcurs = s[0];
				String numcurs = s[1];
				int maxnum = Integer.valueOf(numcurs);
				int yearcuri = Integer.valueOf(yearcurs);
				if(yearcuri!=year)
					return init(type);
				maxnum++;
				String maxstr = maxnum>=10000?(String.valueOf((maxnum/10000)%10)):"0"
					+(maxnum>=1000?(String.valueOf((maxnum/1000)%10)):"0")
					+(maxnum>=100?(String.valueOf((maxnum/100)%10)):"0")
					+(maxnum>=10?(String.valueOf((maxnum/10)%10)):"0")+(String.valueOf(maxnum%10));
				return yearcurs+"-"+maxstr;
			}
		}
		return "0000";		
	}

	private String init(int type) {

		if(type==YYYYNNNNN){
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			String year = formatter.format(new Date());
			return year+"-00001";
		}
		return "0000";
	}
	/**
	 * 编码 前缀为指定前缀  后缀为当前时间戳
	 * @param prefix
	 * @return
	 */
	public String numberWithTimestamp(String prefix){
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date date = new Date();
		return prefix+sf.format(date);
	}
}
