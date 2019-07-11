package com.example.shortlink;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/urls")
public interface UrlRepository extends CrudRepository<urlshort, Integer> {

}
