package com.wang.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/static")
public class StaticController {
	@RequestMapping("/freeone")
	public String viewpage() {
		return "free1";
	}
}
