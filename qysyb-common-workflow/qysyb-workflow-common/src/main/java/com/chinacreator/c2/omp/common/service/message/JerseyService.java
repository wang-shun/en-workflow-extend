package com.chinacreator.c2.omp.common.service.message;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/*import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;*/

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chinacreator.c2.omp.common.util.CommonUtil;

@Service
public class JerseyService {

	private Logger logger = Logger.getLogger(JerseyService.class);

	private static volatile JerseyService instance;

	public static JerseyService getInstance() {
		if (instance == null)
			instance = new JerseyService();
		return instance;
	}

	public JerseyService() {
	}

	public boolean execute(String url, String jsonBody) {/*

		logger.info("开始执行服务:" + url  + "-内容体:"
				+ jsonBody);
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(url);

		try {

			Response response = target
					.request()
					.header("token", CommonUtil.randomToken())
					.buildPost(
							Entity.entity(jsonBody, MediaType.APPLICATION_JSON))
					.invoke();

			logger.info("服务执行结束:" + url);
			logger.info("服务执行结束:" + response.toString());
			if (response.getStatusInfo().getStatusCode() == 200)
				return true;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		return false;
	*/	return false;
		}

	public static void main(String args[]) {/*
//		JerseyService service = new JerseyService();
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("msg", "this is a test in monit backend!");
//		service.execute("http://172.16.25.195:8080/alarm-log/api/log/add",
//				params);
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://www.iever.cn:8080/battery/api/batteryinfo/getaddress/5454");
		Response response = target.request().buildGet().invoke();
		String r = response.readEntity(String.class);
		System.out.println(r);
		System.out.println(response);
	*/}

}
