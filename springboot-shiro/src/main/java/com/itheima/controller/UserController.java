package com.itheima.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itheima.service.UserService;

@Controller
public class UserController {
	
	@Resource
	private UserService userService;
	
	/**
	 *测试方法 
	 * */
	@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		System.out.println("userController");
		return "ok";
	}
	
	/**
	 * 测试thymeleaf
	 * @return
	 */
	@RequestMapping("/testThymeleaf")
	public String testThymeleaf(Model model) {
		//把数据存入model
		model.addAttribute("name","黑马程序员");
		//返回test.html
		return "test";
	}
	/**
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return "/User/add";
	}
	/**
	 * @return
	 */
	@RequestMapping("/update")
	public String update() {
		return "/User/update";
	}
	/**
	 * 默认跳转路径
	 * @return
	 */
	/*@RequestMapping("/login")
	public String login() {
		return "login";
	}*/
	
	/**
	 * @return
	 */
	@RequestMapping("/toLogin")
	public String toLogin() {
		return "toLogin";
	}
	
	/**
	 * 登陆逻辑处理
	 */
	@RequestMapping("/userLogin")
	public String userLogin(String username,String password,Model model) {
		/**
		 * 使用shiro 编写认证操作
		 */
		//1.获取Subject
		Subject subject = SecurityUtils.getSubject();
		//2.封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		//3.执行登陆方法
		try {
			subject.login(token);
			return "redirect:/testThymeleaf";
		} catch (UnknownAccountException e) {
			//登陆失败：用户名不存在
			model.addAttribute("msg", "用户名不存在");
			return "toLogin";
			
		} catch (IncorrectCredentialsException e) {
			//登陆失败：密码错误
			model.addAttribute("msg", "密码错误");
			return "toLogin";
		}
	}
}
