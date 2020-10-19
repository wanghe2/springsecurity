package com.wang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class LoginController {
	@RequestMapping("login")
	public String getPage() {
		System.err.println(".........登录页面----freemark...............");
		return "loginPage";
	}
}
