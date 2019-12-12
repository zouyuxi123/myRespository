package com.hpe.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.hpe.Utils.utils;
import com.hpe.domain.Customer;

@Controller
public class CustomerController {

	@Resource
	private RedisTemplate redisTemplate;

	@RequestMapping("sendCode")
	@ResponseBody
	public String sendCode(String telephone) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 生成四位数验证码
		String verificationCode = String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", // 域对象
				"LTAI4FiNDqQgEGe5Eq8DxGvt", // 您的AccessKey ID
				"Yddsmaa5vxmm3rsmjv0HiAjbW5qA7J");// 您的AccessKey Secret
		IAcsClient client = new DefaultAcsClient(profile);
		CommonRequest request = new CommonRequest();

		// 发送方式
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("RegionId", "cn-hangzhou");
		// 手机号码
		request.putQueryParameter("PhoneNumbers", telephone);
		// 模板标签
		request.putQueryParameter("SignName", "菜狗驿站");
		// 短信模板
		request.putQueryParameter("TemplateCode", "SMS_178770557");
		// 你的验证码为:${code}
		request.putQueryParameter("TemplateParam", "{\"code\":\"" + verificationCode + "\"}");
		try {
			CommonResponse response = client.getCommonResponse(request);

			map.put("success", true);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
		}

		String json = JSON.toJSONString(map);
		return json;
	}

	// 保存客户信息
	@RequestMapping("saveCustomer")
	@ResponseBody
	public String saveCustomer(String telephone, String password, String checkCode, String inputemail) {
		// 创建一个客户对象
		Customer customer = new Customer();
		// 添加信息
		customer.setTelephone(telephone);
		customer.setEmail(inputemail);
		customer.setPassword(password);

		Map<String, Object> map = new HashMap<String, Object>();

		// 向服务器发送请求
		try {
			WebClient.create("http://localhost:9081/crm-web/service/customerService/saveCustomer")
					.type(MediaType.APPLICATION_JSON).post(customer);
			// 激活码 唯一字符串
			String activeCode = UUID.randomUUID().toString();
			// 发送电子邮件
			utils.sendEmail(telephone, inputemail, activeCode);
			// 将激活码存放到数据库(只保存48小时)
			redisTemplate.opsForValue().set(telephone, activeCode, 48, TimeUnit.HOURS);
			map.put("success", true);
		} catch (Exception e) {

			e.printStackTrace();
			map.put("success", false);
		}

		String json = JSON.toJSONString(map);
		return json;

	}

	@RequestMapping("activeCode")
	public void activeCode(HttpServletResponse response, String telephone, String activeCode) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		// 取出激活码
		String code = redisTemplate.opsForValue().get(telephone).toString();
		// 判断激活码是否有效

		if (code == null || !code.equals(activeCode)) {
			// 激活码无效
			response.getWriter().write("无效的激活码");
			return;
		}
		// 激活用户

		WebClient.create(
				"http://localhost:9081/crm-web/service/customerService/updateTypeByTelephone?telephone=" + telephone)
				.put(null);
		response.getWriter().write("激活成功");
		// 激活成功后删除数据库激活码
		redisTemplate.delete(telephone);

	}

}
