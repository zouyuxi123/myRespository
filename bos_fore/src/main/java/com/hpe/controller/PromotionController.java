package com.hpe.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hpe.domain.Promotion;

@Controller
@RequestMapping("promotionl")
public class PromotionController {
	@RequestMapping(value = "listByPage", produces = "text/json;charset=utf-8")
	@ResponseBody
	public String listByPage(String pageIndex, String pageSize) {
		
		// 获取宣传任务
		List<Promotion> list = (List<Promotion>) WebClient
				.create("http://localhost:9080/bos-web/service/promotionService/list")
				.type(MediaType.APPLICATION_JSON)
				.getCollection(Promotion.class);
		// 遍历集合
		System.out.println("list");
		for (Promotion promotion : list) {
			promotion.setTitleImg("http://localhost:9080" + promotion.getTitleImg());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		map.put("list", list);
		String json = JSON.toJSONString(map);
		return json;
	}

}
