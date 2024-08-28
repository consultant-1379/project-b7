package com.ericsson.projectb7.jenkins;

import com.ericsson.projectb7.dao.EnmProjectListDao;
import com.ericsson.projectb7.dao.JenkinsTestReportDao;
import com.ericsson.projectb7.pojo.JenkinsTestReport;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;

import java.io.IOException;
import java.util.Date;
import java.time.Instant;
import java.util.Map;
import java.util.List;

import com.offbytwo.jenkins.model.*;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class JenkinsClient {


    @Autowired
    @Qualifier("jenkinsServerConfiguration")
    private JenkinsServer jenkinsServer;

    @Autowired
    private EnmProjectListDao enmProjectListDao;

    @Autowired
    private JenkinsTestReportDao jenkinsTestReportDao;

    public String getCxpNumber(String jobName){
        Map<String, String> rpmCxpNumber = enmProjectListDao.getRpmCxpNumberInfo();
        String tempcxpNumber = rpmCxpNumber.get(jobName);
        return  tempcxpNumber == null ? "" : tempcxpNumber;
    }

    public void initializeJenkinsServerData() throws IOException {

        Map<String, Job> jobs = jenkinsServer.getJobs();
        for (String jobName : jobs.keySet()) {
            List<Build> builds = jobs.get(jobName).details().getBuilds();

            for (Build build : builds) {
                JenkinsTestReport report = new JenkinsTestReport();

                String buildReleaseType = jobName;
                String jobRpmName = jobName;

                if(jobName.contains("_")) {
                    buildReleaseType = jobName.substring(jobName.lastIndexOf("_") + 1);
                    jobRpmName = jobName.substring(0, jobName.lastIndexOf("_"));
                    report.setCxpNumber(getCxpNumber(jobRpmName));
                }else{
                    report.setCxpNumber(getCxpNumber(jobName));
                }
                
                report.setBuildReleaseType(buildReleaseType);

                report.setProjectName(jobRpmName);

                report.setBuildNumber(build.getNumber());

                BuildWithDetails buildWDetails = build.details();

                report.setBuildDateTimestamp(buildWDetails.getTimestamp());

                TestResult result= null;
                try {
                    result = build.getTestResult();

                }catch (HttpResponseException hre){
                    System.err.println("Catched an exception while fetching test count for job:"+jobName);
                    continue;
                }

                int passCount = result.getPassCount();
                report.setTestsPassed(passCount);

                int failCount = result.getFailCount();
                report.setTestsFailed(failCount);

                int skipCount = result.getSkipCount();
                report.setTestsSkipped(skipCount);

                double totalTestCount = 1.0*(passCount + failCount + skipCount);
                if (totalTestCount == 0.0)
                    totalTestCount = 1.0;

                report.setSuccessRate((((double)passCount) /totalTestCount));

                report.setFailureRate((((double)failCount) /totalTestCount));

                jenkinsTestReportDao.addJenkinsReports(report);
            }
        }

    }

}
