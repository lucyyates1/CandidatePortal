package tech.geek.CandidatePortal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@SpringBootApplication
@ComponentScan(basePackages="tech.geek.CandidatePortal")
@EntityScan(basePackages="tech.geek.CandidatePortal.entity")
@EnableJpaRepositories(basePackages="tech.geek.CandidatePortal.repo")
public class CandidatePortalApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(CandidatePortalApplication.class, args);
	}
	@Bean
	public ViewResolver internalResourceViewResolver() {
		InternalResourceViewResolver bean = new InternalResourceViewResolver();
		bean.setViewClass(JstlView.class);
		bean.setPrefix("/jsp/");
		bean.setSuffix(".jsp");
		return bean;
	}

	//For Multipart Files (Resumes and Cover Letter File Uploads)
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		return multipartResolver;
	}
}

