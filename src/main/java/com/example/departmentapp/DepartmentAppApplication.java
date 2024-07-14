package com.example.departmentapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DepartmentAppApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ac = SpringApplication.run(DepartmentAppApplication.class, args);

		// String[] allBeanNames = ac.getBeanDefinitionNames();
		// for (String beanName : allBeanNames) {
		// 	System.out.println(beanName);
		// }
	}
}
