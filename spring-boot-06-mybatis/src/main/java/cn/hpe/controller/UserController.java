package cn.hpe.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.hpe.pojo.User;
import cn.hpe.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("query/{name}")
	public List<User> queryByName(@PathVariable("name")String name){
		return userService.queryByName(name);
	}
	
	@RequestMapping("query2")
	public List<User> queryAll(){
		return userService.queryAll();
	}
	
	

}
