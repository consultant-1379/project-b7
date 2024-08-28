package com.ericsson.projectb7.jenkins;

import com.ericsson.projectb7.dao.EnmProjectListDao;
import com.ericsson.projectb7.dao.JenkinsTestReportDao;
import com.ericsson.projectb7.pojo.ExcelRecord;
import com.ericsson.projectb7.pojo.JenkinsTestAlertReport;
import com.ericsson.projectb7.pojo.JenkinsTestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JenkinsService {

    @Autowired
    private JenkinsClient client;

    @Autowired
    private JenkinsTestReportDao testReport;

    @Autowired
    private EnmProjectListDao enmProjectListDao;

    public List<JenkinsTestReport> getTestReportsWithinTimePeriod(long startDateMillisec, long endDateMillisec){
        return testReport.getAllEnmProjectsTestReport(startDateMillisec, endDateMillisec);
    }

   public List<JenkinsTestReport> getJenkinsTestReportHighestFailureRate(double threshold) {
    return testReport.getJenkinsTestReportFailureRate(threshold);
    }

    public List<JenkinsTestReport> getProductJenkinsTestReportHighestFailureRate(double threshold, String product) {
        return  testReport.getJenkinsProductTestReportFailureRate(threshold, product);
    }

    public List<JenkinsTestReport> getCnaJenkinsTestReportHighestFailureRate(double threshold, String cnaName) {
        List<ExcelRecord> projectDetails = enmProjectListDao.getProjectDetails(cnaName);
        List<String> projectRpm = projectDetails.stream()
                .map(record ->
                        !record.getRpm().isEmpty() ? record.getRpm().substring(record.getRpm().lastIndexOf("/")+1) :"")
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toList());

        List<JenkinsTestReport> jenkinsTestReportList = new ArrayList<>();

        for (String projectName: projectRpm){
            jenkinsTestReportList.addAll(testReport.getJenkinsProductTestReportFailureRate(threshold, projectName));
        }

        return  jenkinsTestReportList;
    }

    public List<JenkinsTestAlertReport> getTestAlertsThatRanBefore(int noOfDays) {

        LocalDate currentDate = LocalDate.now();
        LocalDate alertDate = currentDate.minusDays((long)noOfDays);
//        long dateAsStamp = alertDate.toEpochSecond(Loc);

        Instant instant = alertDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        long timeInMillis = instant.toEpochMilli();System.out.println(timeInMillis);

        List<JenkinsTestAlertReport> jenkinsTestAlertReports =  testReport.getProjectsThatNeverRanBefore(timeInMillis);
        return jenkinsTestAlertReports.stream().map(element -> {
            element.setCnaName(enmProjectListDao.getCxpNumberCnaInfoDetails().getOrDefault(element.getCxpNumber(), ""));
            return element;
        }).collect(Collectors.toList());
    }

    public List<JenkinsTestReport> getCnaTestReportWithInTimePeriod(String cnaName, long startDatemillisec, long endDatemillis) {
        List<ExcelRecord> projectDetails = enmProjectListDao.getProjectDetails(cnaName);
        List<String> projectRpm = projectDetails.stream()
                .map(record ->
                        !record.getRpm().isEmpty() ? record.getRpm().substring(record.getRpm().lastIndexOf("/")+1) :"")
                .filter(string -> !string.isEmpty())
                .collect(Collectors.toList());
        List<JenkinsTestReport> jenkinsTestReportList = new ArrayList<>();
        for (String projectName: projectRpm){
            jenkinsTestReportList.addAll(testReport.getEnmProjectTestReportWithInRange(projectName, startDatemillisec, endDatemillis));
        }

        return jenkinsTestReportList;
    }

    public List<JenkinsTestReport> getProductTestReportsWithInTimePeriod(String productName, long startDatemillisec, long endDatemillis) {
        List<JenkinsTestReport> jenkinsTestReports =  testReport.getEnmProjectTestReportWithInRange(productName, startDatemillisec, endDatemillis);
        return jenkinsTestReports;
    }
}
