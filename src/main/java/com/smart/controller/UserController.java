package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.smart.dao.UserRepository;
import com.smart.entites.Contact;
import com.smart.entites.User;

import java.security.*;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	//Method for adding common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		
		String userName=principal.getName();
		
		User user = userRepository.getUserByUserName(userName);
		
		model.addAttribute("user", user);
		
		
	}
	
	
	
	
	//Dashboard home page
	@GetMapping("/index")
	public String dashboard(Model model, Principal principal)
	{
		
		return "normal/user_dashboard";
	}
	
	//add contact
	@GetMapping("add-contact")
	public String openAddContactForm(Model model)
	{
		model.addAttribute("title","Add Contact");
		model.addAttribute("contact", new Contact());
		
		return "normal/add_contact_form";
	}
	
	//proccesing add contat form
	
	@PostMapping("process-contact")
	public String processContact(@ModelAttribute Contact contact,Principal principal) {
		
		
		System.out.println(contact);
		
		return "normal/add_contact_form";
	}
	
	
	
	
	
}
