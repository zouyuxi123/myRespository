package cn.hpe.service;

import java.util.List;

import cn.hpe.pojo.User;

public interface UserService {

	public List<User> queryByName(String name);
	
	public List<User> queryAll();

}
