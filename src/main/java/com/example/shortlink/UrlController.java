package com.example.shortlink;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class UrlController {
	@Autowired
	private UrlRepository repository;
	
	@GetMapping("/urls")
	public String listView(Model m) {
		m.addAttribute("urls", repository.findAll());
		
		return "list";
	}
	
	@GetMapping("/create_url")
	public String createUrlGet() {
		return "create";
	}
	
	@PostMapping("/create_url")
	public String createUrlPost(urlshort url) {
		repository.save(url);
		url.setShortUrl("urls/"+url.getId().toString());
		url.setCount(0);
		repository.save(url);
		
		return "redirect:/urls";
	}
	
	@GetMapping("/urls/{id}") 
	public String linkUrl(@PathVariable("id") Integer id) {
		Optional<urlshort> url = repository.findById(id);
		String shortlink;
		
		if(url.isPresent()) {
			shortlink = url.get().getOriginalUrl();
			url.get().setCount(url.get().getCount()+1);
			
			repository.save(url.get());
		}
		else {
			shortlink = "/urls";
		}
		
		return "redirect:" + shortlink;
	}
	
	@GetMapping("/urls/{id}/modify")
	public String modifyUrlGet(@PathVariable("id") Integer id, Model m) {
		Optional<urlshort> url = repository.findById(id);
		
		m.addAttribute("url", url.isPresent() ? url.get() : null);
		
		return "modify";
	}
	
	@PutMapping("/urls/{id}")
	public String modifyUrlPut(@PathVariable("id") Integer id, urlshort url) {
		if(repository.existsById(id)) {
			url.setShortUrl("urls/"+id);
			url.setCount(0);
			
			repository.save(url);
		}
		
		return "redirect:/urls";
	}
	
	@GetMapping("/urls/{id}/delete")
	public String modifyUrlGet(@PathVariable("id") Integer id) {
		if(repository.existsById(id)) {
			repository.deleteById(id);
		}
		
		return "redirect:/urls";
	}
}
