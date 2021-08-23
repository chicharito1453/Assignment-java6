package com.dinh.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dinh.entity.Authority;
import com.dinh.service.AuthorityService;

@CrossOrigin("*")
@RestController
public class AuthorityRestController {

	@Autowired
	AuthorityService authService;
	
	@GetMapping("/rest/authorities")
	List<Authority>findAll(@RequestParam("admin")Optional<Boolean>admin){
		if(admin.orElse(false)) {
			return authService.findAuthoritiesOfAdministrators();
		}
		return authService.findAll();
	}
	
	@PostMapping("/rest/authorities")
	public Authority post(@RequestBody Authority auth) {
		return authService.create(auth);
	}
	
	@DeleteMapping("/rest/authorities/{id}")
	public void delete(@PathVariable("id")Integer id) {
		authService.delete(id);
	}
	
}
