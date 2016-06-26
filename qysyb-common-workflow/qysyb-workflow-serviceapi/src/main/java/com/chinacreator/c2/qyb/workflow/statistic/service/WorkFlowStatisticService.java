package com.chinacreator.c2.qyb.workflow.statistic.service;

import java.util.List;
import java.util.Map;

public interface WorkFlowStatisticService {
	/**
	 * 
	 * @param serviceType
	 * @param offset
	 * @param limit
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map> getWorkCompleteStatus(String serviceType ,int offset,int limit,long startDate,long endDate );
	/**
	 * 
	 * @param ClassId  ZC FW QQBM
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Map> getWorkClassStatistic(String ClassId ,long startDate,long endDate);
	/**
	 * 
	 * @param serviceType
	 * @param priority
	 * @param offset
	 * @param limit
	 * @param startDate
	 * @param endDate
	 * @return
	 */

	List<Map<String, Object>> getWorkHandleStatus(String serviceType, String priId, int offset,
			int limit, long startDate, long endDate);
	/**
	 * 
	 * @param serviceType
	 * @param priId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	int getWorkHandleStatusNum(String serviceType, String priId,long startDate, long endDate);
}
