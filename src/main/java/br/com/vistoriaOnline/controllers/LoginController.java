package br.com.vistoriaOnline.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@GetMapping
	@RequestMapping("/login")
	public String login() {
		return "index";
	}
	
	@GetMapping(value = {"/login", "/login/"})
	public String login(Authentication authentication, @RequestParam(required = false, defaultValue = "false") String error) {
		
		if(authentication != null && authentication.isAuthenticated())
			return "redirect:/logged/home/";
		
		return "index";
	}
	
}
