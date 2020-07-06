package com.itheima.serviceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.itheima.domain.User;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;
	
	@Override
	public User findByUserName(String username) {
		return userMapper.findByUserName(username);
	}

}
