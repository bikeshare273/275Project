package quiz.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;



@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping("/api/v1/*")
public class QuizAppController extends WebMvcConfigurerAdapter {	

	
	
}
