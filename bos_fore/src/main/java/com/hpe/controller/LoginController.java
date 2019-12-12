package com.hpe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.alibaba.fastjson.JSON;
import com.hpe.domain.Customer;

@Controller
@RequestMapping("login")
public class LoginController {

	@RequestMapping("Login")
	@ResponseBody
	public String Login(HttpServletRequest request, HttpServletResponse response,String password,String telephone) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 根据电话号码获取账号密码
		List<Customer> list = (List<Customer>) WebClient
				.create("http://localhost:9081/crm-web/service/customerService/getCustomer?telephone=" + telephone)
				.type(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		String pwd = list.get(0).getPassword();
		// 判断密码是否正确
		if (password.equals(pwd)) {
			// 密码正确
			map.put("success", true);
		} else {
			map.put("msg", "账号密码错误，请重新登录");
			map.put("success", false);
		}
		String json = JSON.toJSONString(map);
		return json;
	}

}
