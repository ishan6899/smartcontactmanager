package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.dao.UserRepository;
import com.smart.entites.User;
import com.smart.helper.Message;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/")
	public String home(Model model){
	model.addAttribute("title","Home - Smart Contact Manager");
	return ("home");
	}
	
	
	@GetMapping("/about")
	public String about(Model model){
	model.addAttribute("title","About - Smart Contact Manager");
	return ("about");
	}
	
	@GetMapping("/signup")
	public String signup(Model model) {
	model.addAttribute("title","Register - Smart Contact Manager");
	model.addAttribute("user",new User());
	return "signup";
	}	
	
	@PostMapping("/do-register")
	public String registerUser( @Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value="agreement",defaultValue = "false")boolean agreement,Model model, HttpSession session) {
	
		try {
		
		
		if(!agreement)
		{
			System.out.println("Not agreed");
			throw new Exception("You havent agreed to terms and conditions");
		}
		
		if(result1.hasErrors())
		{
			System.out.println(result1.toString());
			model.addAttribute("user",user);	
			return "signup";
		}
		
		
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setImageUrl("default.png");
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User result =this.userRepository.save(user);
		
		
		session.setAttribute("message", new Message("Succesfully Registered","alert-success"));

		
		
		
	System.out.println(result);
	return "signup";

//	System.out.println(user);
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user",user);
			session.setAttribute("message", new Message("Something went wrong"+ e.getMessage(),"alert-danger"));
			return "signup";

			
			// TODO: handle exception
		}
		
	}
	

@GetMapping("/signin")
public String customLogin(Model model)

{
	model.addAttribute("title","Login Page");
	return "login";
}
	
	
	
}



