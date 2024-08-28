package com.ericsson.projectb7.dao;

import com.ericsson.projectb7.pojo.JenkinsTestAlertReport;
import com.ericsson.projectb7.pojo.JenkinsTestReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JenkinsTestReportDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String GET_LAST_BUILD_RELEASE_NUMBER = "SELECT LAST_BUILD_NUMBER FROM PROJECT_LAST_BUILD_NUMBER WHERE PROJECT_NAME = ? AND BUILD_RELEASE_TYPE = ?";

    private static final String INSERT_JENKINS_REPORT = "INSERT IGNORE INTO JENKINS_TEST_REPORT (PROJECT_NAME, CXP_NUMBER, TESTS_PASSED, TESTS_FAILED," +
            " TESTS_SKIPPED, SUCCESS_RATE , FAILURE_RATE ,BUILD_NUMBER ,BUILD_TIME_STAMP ,BUILD_RELEASE_TYPE) VALUES (? , ? , ? , ?, ? , ?, ?, ? , ? , ?)";

    private static final String SELECT_ALL_JENKINS_REPORT_QUERY = "SELECT PROJECT_NAME, CXP_NUMBER, TESTS_PASSED, TESTS_FAILED, TESTS_SKIPPED, SUCCESS_RATE , FAILURE_RATE ," +
            "BUILD_NUMBER ,BUILD_TIME_STAMP ,BUILD_RELEASE_TYPE FROM JENKINS_TEST_REPORT";

    private static final String SELECT_JENKINS_REPORT_WITHIN_RANGE_QUERY = "SELECT PROJECT_NAME, CXP_NUMBER, TESTS_PASSED, TESTS_FAILED, TESTS_SKIPPED, SUCCESS_RATE , FAILURE_RATE ," +
            "BUILD_NUMBER ,BUILD_TIME_STAMP ,BUILD_RELEASE_TYPE FROM JENKINS_TEST_REPORT WHERE BUILD_TIME_STAMP BETWEEN ? and ?";

    private static final String SELECT_PRODUCT_JENKINS_REPORT_QUERY = "SELECT PROJECT_NAME, CXP_NUMBER, TESTS_PASSED, TESTS_FAILED, TESTS_SKIPPED, SUCCESS_RATE , FAILURE_RATE ," +
            "BUILD_NUMBER ,BUILD_TIME_STAMP ,BUILD_RELEASE_TYPE FROM JENKINS_TEST_REPORT WHERE PROJECT_NAME = ?";

    private static final String SELECT_PRODUCT_JENKINS_REPORT_WITHIIN_RANGE_QUERY = "SELECT PROJECT_NAME, CXP_NUMBER, TESTS_PASSED, TESTS_FAILED, TESTS_SKIPPED, SUCCESS_RATE , FAILURE_RATE ," +
            "BUILD_NUMBER ,BUILD_TIME_STAMP ,BUILD_RELEASE_TYPE FROM JENKINS_TEST_REPORT WHERE CXP_NUMBER = ? AND BUILD_TIME_STAMP BETWEEN ? and ?";

    private static final String SELECT_JENKINS_REPORT_HIGHEST_FAILURE_RATE = "SELECT PROJECT_NAME, CXP_NUMBER, TESTS_PASSED, TESTS_FAILED, TESTS_SKIPPED, SUCCESS_RATE , FAILURE_RATE ," +
            "BUILD_NUMBER ,BUILD_TIME_STAMP ,BUILD_RELEASE_TYPE FROM JENKINS_TEST_REPORT WHERE FAILURE_RATE >= ?";

    private static final String SELECT_PRODUCT_JENKINS_REPORT_HIGHEST_FAILURE_RATE = "SELECT PROJECT_NAME, CXP_NUMBER, TESTS_PASSED, TESTS_FAILED, TESTS_SKIPPED, SUCCESS_RATE , FAILURE_RATE ," +
            "BUILD_NUMBER ,BUILD_TIME_STAMP ,BUILD_RELEASE_TYPE FROM JENKINS_TEST_REPORT WHERE FAILURE_RATE >= ? AND PROJECT_NAME = ?";

    private static final String LIST_PROJECTS_NEVER_RAN_UNTIL_NUMBER_OF_DAYS = "SELECT JK1.PROJECT_NAME, JK1.BUILD_RELEASE_TYPE, JK1.BUILD_NUMBER,JK1.CXP_NUMBER, JK1.BUILD_TIME_STAMP FROM ( SELECT PROJECT_NAME, MAX(BUILD_NUMBER) AS MAX_BUILD_NUMBER, BUILD_RELEASE_TYPE FROM JENKINS_TEST_REPORT GROUP BY PROJECT_NAME, BUILD_RELEASE_TYPE, CXP_NUMBER ) AS X INNER JOIN JENKINS_TEST_REPORT AS JK1 ON JK1.PROJECT_NAME = X.PROJECT_NAME AND JK1.BUILD_NUMBER = X.MAX_BUILD_NUMBER AND JK1.BUILD_RELEASE_TYPE = X.BUILD_RELEASE_TYPE WHERE JK1.BUILD_TIME_STAMP <= ?";

    public int getLastBuildNumber(String projectName, String buildReleaseType){
       return jdbcTemplate.queryForObject(GET_LAST_BUILD_RELEASE_NUMBER, new Object[]{projectName, buildReleaseType}, Integer.class);
    }

    public int addJenkinsReports(JenkinsTestReport jenkinsTestReport){
        return jdbcTemplate.update(INSERT_JENKINS_REPORT, new Object[]{jenkinsTestReport.getProjectName(), jenkinsTestReport.getCxpNumber(), jenkinsTestReport.getTestsPassed(),
        jenkinsTestReport.getTestsFailed(), jenkinsTestReport.getTestsSkipped(), jenkinsTestReport.getSuccessRate(), jenkinsTestReport.getFailureRate(),
        jenkinsTestReport.getBuildNumber(), jenkinsTestReport.getBuildDateTimestamp(), jenkinsTestReport.getBuildReleaseType()});
    }
// TODO: Method for listing all ENM projects with test results pass,failed -- Completed

    public List<JenkinsTestReport> getAllEnmProjectsTestReport(){
        return jdbcTemplate.query(SELECT_ALL_JENKINS_REPORT_QUERY, new RowMapper<JenkinsTestReport>() {
            @Override
            public JenkinsTestReport mapRow(ResultSet resultSet, int i) throws SQLException {
                JenkinsTestReport jenkinsTestReport = new JenkinsTestReport();
                jenkinsTestReport.setProjectName(resultSet.getString("PROJECT_NAME"));
                jenkinsTestReport.setCxpNumber(resultSet.getString("CXP_NUMBER"));
                jenkinsTestReport.setTestsPassed(resultSet.getInt("TESTS_PASSED"));
                jenkinsTestReport.setTestsFailed(resultSet.getInt("TESTS_FAILED"));
                jenkinsTestReport.setTestsSkipped(resultSet.getInt("TESTS_SKIPPED"));
                jenkinsTestReport.setSuccessRate(resultSet.getDouble("SUCCESS_RATE"));
                jenkinsTestReport.setFailureRate(resultSet.getDouble("FAILURE_RATE"));
                jenkinsTestReport.setBuildNumber(resultSet.getInt("BUILD_NUMBER"));
                jenkinsTestReport.setBuildDateTimestamp(resultSet.getLong("BUILD_TIME_STAMP"));
                jenkinsTestReport.setBuildReleaseType(resultSet.getString("BUILD_RELEASE_TYPE"));
                return jenkinsTestReport;
            }
        });
    }

    public List<JenkinsTestReport> getEnmProjectTestReport(String projectName){
        return jdbcTemplate.query(SELECT_PRODUCT_JENKINS_REPORT_QUERY, new Object[]{projectName}, new RowMapper<JenkinsTestReport>() {
            @Override
            public JenkinsTestReport mapRow(ResultSet resultSet, int i) throws SQLException {
                JenkinsTestReport jenkinsTestReport = new JenkinsTestReport();

                jenkinsTestReport.setProjectName(resultSet.getString("PROJECT_NAME"));
                jenkinsTestReport.setCxpNumber(resultSet.getString("CXP_NUMBER"));
                jenkinsTestReport.setTestsPassed(resultSet.getInt("TESTS_PASSED"));
                jenkinsTestReport.setTestsFailed(resultSet.getInt("TESTS_FAILED"));
                jenkinsTestReport.setTestsSkipped(resultSet.getInt("TESTS_SKIPPED"));
                jenkinsTestReport.setSuccessRate(resultSet.getDouble("SUCCESS_RATE"));
                jenkinsTestReport.setFailureRate(resultSet.getDouble("FAILURE_RATE"));
                jenkinsTestReport.setBuildNumber(resultSet.getInt("BUILD_NUMBER"));
                jenkinsTestReport.setBuildDateTimestamp(resultSet.getLong("BUILD_TIME_STAMP"));
                jenkinsTestReport.setBuildReleaseType(resultSet.getString("BUILD_RELEASE_TYPE"));
                return jenkinsTestReport;
            }
        });
    }
//    TODO: Method for returning test cases which has highest failure rate: return records based on failure rate information

    public List<JenkinsTestReport> getJenkinsTestReportFailureRate(double threshold) {
        return jdbcTemplate.query(SELECT_JENKINS_REPORT_HIGHEST_FAILURE_RATE, new Object[]{threshold},  new RowMapper<JenkinsTestReport>() {
            @Override
            public JenkinsTestReport mapRow(ResultSet resultSet, int i) throws SQLException {
                JenkinsTestReport jenkinsTestReport = new JenkinsTestReport();

                jenkinsTestReport.setProjectName(resultSet.getString("PROJECT_NAME"));
                jenkinsTestReport.setCxpNumber(resultSet.getString("CXP_NUMBER"));
                jenkinsTestReport.setTestsPassed(resultSet.getInt("TESTS_PASSED"));
                jenkinsTestReport.setTestsFailed(resultSet.getInt("TESTS_FAILED"));
                jenkinsTestReport.setTestsSkipped(resultSet.getInt("TESTS_SKIPPED"));
                jenkinsTestReport.setSuccessRate(resultSet.getDouble("SUCCESS_RATE"));
                jenkinsTestReport.setFailureRate(resultSet.getDouble("FAILURE_RATE"));
                jenkinsTestReport.setBuildNumber(resultSet.getInt("BUILD_NUMBER"));
                jenkinsTestReport.setBuildDateTimestamp(resultSet.getLong("BUILD_TIME_STAMP"));
                jenkinsTestReport.setBuildReleaseType(resultSet.getString("BUILD_RELEASE_TYPE"));
                return jenkinsTestReport;
            }
        });
    }

    public List<JenkinsTestReport> getJenkinsProductTestReportFailureRate(double threshold, String product) {
        return jdbcTemplate.query(SELECT_PRODUCT_JENKINS_REPORT_HIGHEST_FAILURE_RATE, new Object[]{threshold, product},  new RowMapper<JenkinsTestReport>() {
            @Override
            public JenkinsTestReport mapRow(ResultSet resultSet, int i) throws SQLException {
                JenkinsTestReport jenkinsTestReport = new JenkinsTestReport();
                jenkinsTestReport.setProjectName(resultSet.getString("PROJECT_NAME"));
                jenkinsTestReport.setCxpNumber(resultSet.getString("CXP_NUMBER"));
                jenkinsTestReport.setTestsPassed(resultSet.getInt("TESTS_PASSED"));
                jenkinsTestReport.setTestsFailed(resultSet.getInt("TESTS_FAILED"));
                jenkinsTestReport.setTestsSkipped(resultSet.getInt("TESTS_SKIPPED"));
                jenkinsTestReport.setSuccessRate(resultSet.getDouble("SUCCESS_RATE"));
                jenkinsTestReport.setFailureRate(resultSet.getDouble("FAILURE_RATE"));
                jenkinsTestReport.setBuildNumber(resultSet.getInt("BUILD_NUMBER"));
                jenkinsTestReport.setBuildDateTimestamp(resultSet.getLong("BUILD_TIME_STAMP"));
                jenkinsTestReport.setBuildReleaseType(resultSet.getString("BUILD_RELEASE_TYPE"));
                return jenkinsTestReport;
            }
        });
    }

    public List<JenkinsTestAlertReport> getProjectsThatNeverRanBefore(long noOfDaysInMilliSec) {
        return jdbcTemplate.query(LIST_PROJECTS_NEVER_RAN_UNTIL_NUMBER_OF_DAYS, new Object[]{noOfDaysInMilliSec}, new RowMapper<JenkinsTestAlertReport>() {
            @Override
            public JenkinsTestAlertReport mapRow(ResultSet resultSet, int i) throws SQLException {
                JenkinsTestAlertReport jenkinsTestReport = new JenkinsTestAlertReport();
                jenkinsTestReport.setProjectName(resultSet.getString("PROJECT_NAME"));
                jenkinsTestReport.setCxpNumber(resultSet.getString("CXP_NUMBER"));
                jenkinsTestReport.setBuildNumber(resultSet.getInt("BUILD_NUMBER"));
                jenkinsTestReport.setBuildDateTimestamp(resultSet.getLong("BUILD_TIME_STAMP"));
                jenkinsTestReport.setBuildReleaseType(resultSet.getString("BUILD_RELEASE_TYPE"));
                return jenkinsTestReport;
            }
        });
    }

    public List<JenkinsTestReport> getAllEnmProjectsTestReport(long startDateMillisec, long endDateMillisec) {
        return jdbcTemplate.query(SELECT_JENKINS_REPORT_WITHIN_RANGE_QUERY, new Object[]{startDateMillisec, endDateMillisec}, new RowMapper<JenkinsTestReport>() {
            @Override
            public JenkinsTestReport mapRow(ResultSet resultSet, int i) throws SQLException {
                JenkinsTestReport jenkinsTestReport = new JenkinsTestReport();

                jenkinsTestReport.setProjectName(resultSet.getString("PROJECT_NAME"));
                jenkinsTestReport.setCxpNumber(resultSet.getString("CXP_NUMBER"));
                jenkinsTestReport.setTestsPassed(resultSet.getInt("TESTS_PASSED"));
                jenkinsTestReport.setTestsFailed(resultSet.getInt("TESTS_FAILED"));
                jenkinsTestReport.setTestsSkipped(resultSet.getInt("TESTS_SKIPPED"));
                jenkinsTestReport.setSuccessRate(resultSet.getDouble("SUCCESS_RATE"));
                jenkinsTestReport.setFailureRate(resultSet.getDouble("FAILURE_RATE"));
                jenkinsTestReport.setBuildNumber(resultSet.getInt("BUILD_NUMBER"));
                jenkinsTestReport.setBuildDateTimestamp(resultSet.getLong("BUILD_TIME_STAMP"));
                jenkinsTestReport.setBuildReleaseType(resultSet.getString("BUILD_RELEASE_TYPE"));
                return jenkinsTestReport;
            }
        });
    }

    public List<JenkinsTestReport> getEnmProjectTestReportWithInRange(String projectName, long startDatemillisec, long endDatemillis) {
        return jdbcTemplate.query(SELECT_PRODUCT_JENKINS_REPORT_WITHIIN_RANGE_QUERY, new Object[]{projectName, startDatemillisec, endDatemillis}, new RowMapper<JenkinsTestReport>() {
            @Override
            public JenkinsTestReport mapRow(ResultSet resultSet, int i) throws SQLException {
                JenkinsTestReport jenkinsTestReport = new JenkinsTestReport();

                jenkinsTestReport.setProjectName(resultSet.getString("PROJECT_NAME"));
                jenkinsTestReport.setCxpNumber(resultSet.getString("CXP_NUMBER"));
                jenkinsTestReport.setTestsPassed(resultSet.getInt("TESTS_PASSED"));
                jenkinsTestReport.setTestsFailed(resultSet.getInt("TESTS_FAILED"));
                jenkinsTestReport.setTestsSkipped(resultSet.getInt("TESTS_SKIPPED"));
                jenkinsTestReport.setSuccessRate(resultSet.getDouble("SUCCESS_RATE"));
                jenkinsTestReport.setFailureRate(resultSet.getDouble("FAILURE_RATE"));
                jenkinsTestReport.setBuildNumber(resultSet.getInt("BUILD_NUMBER"));
                jenkinsTestReport.setBuildDateTimestamp(resultSet.getLong("BUILD_TIME_STAMP"));
                jenkinsTestReport.setBuildReleaseType(resultSet.getString("BUILD_RELEASE_TYPE"));
                return jenkinsTestReport;
            }
        });
    }
//  TODO: alerts: method should return build that didn't ran more than 7 days.
}
