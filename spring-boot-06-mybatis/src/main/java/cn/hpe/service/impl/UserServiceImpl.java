package cn.hpe.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hpe.mapper.UserMapper;
import cn.hpe.pojo.User;
import cn.hpe.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<User> queryByName(String name) {
		return userMapper.queryByName(name);
	}

	@Override
	public List<User> queryAll() {
		return userMapper.queryAll();
	}
	
	

}
