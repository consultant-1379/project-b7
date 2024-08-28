package com.ericsson.projectb7;

import com.ericsson.projectb7.dao.EnmProjectListDao;
import com.ericsson.projectb7.jenkins.JenkinsClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class ProjectB7Application {

	public static void main(String[] args) throws IOException {
		ApplicationContext applicationContext  = SpringApplication.run(ProjectB7Application.class, args);

		EnmProjectListDao enmReportDao = applicationContext.getBean(EnmProjectListDao.class);
		enmReportDao.initializeEnmProjects();
		System.out.println("Successfully initialized ENM Project details..");

		JenkinsClient jenkinsClientBean = applicationContext.getBean(JenkinsClient.class);
		jenkinsClientBean.initializeJenkinsServerData();
		System.out.println("Successfully initialized Jenkins server data..");
	}

}
