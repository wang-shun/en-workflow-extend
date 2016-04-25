package com.chinacreator.c2.omp.common.exception;

import com.chinacreator.c2.exception.C2RuntimeException;



/**
 * @author tao.wang
 *
 * 日期：2015-5-20
 *文件名称：自定义异常类
 */
public class ServiceException extends C2RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6417318522868648169L;

	public ServiceException(String errNum) {
		super(errNum);
	}

	public ServiceException(String errNum,Throwable cause) {
		super(errNum, cause);
	}
}
