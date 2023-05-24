package br.com.vistoriaOnline.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value={"/logged/home", "/logged/home/"})
public class HomeController {
	
	@GetMapping
	public String home() {
		return "home";
	}
	
}
