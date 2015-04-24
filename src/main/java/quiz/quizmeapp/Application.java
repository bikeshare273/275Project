package quiz.quizmeapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import quiz.controller.QuizAppController;



@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application{

    public static void main(String[] args) {
    	SpringApplication.run(QuizAppController.class, args);
    }
    
}
