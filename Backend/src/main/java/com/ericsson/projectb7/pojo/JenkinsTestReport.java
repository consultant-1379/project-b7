package com.ericsson.projectb7.pojo;

import java.util.Date;

public class JenkinsTestReport {
    private String projectName;
    private String cxpNumber;
    private int testsPassed;
    private int testsFailed;
    private int testsSkipped;
    private double successRate;
    private int buildNumber;
    private long buildDateTimestamp;
    private String buildReleaseType;
    private double failureRate;

    public double getFailureRate() {
        return failureRate;
    }

    public void setFailureRate(double failureRate) {
        this.failureRate = failureRate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCxpNumber() {
        return cxpNumber;
    }

    public void setCxpNumber(String cxpNumber) {
        this.cxpNumber = cxpNumber;
    }

    public int getTestsPassed() {
        return testsPassed;
    }

    public void setTestsPassed(int testsPassed) {
        this.testsPassed = testsPassed;
    }

    public int getTestsFailed() {
        return testsFailed;
    }

    public void setTestsFailed(int testsFailed) {
        this.testsFailed = testsFailed;
    }

    public int getTestsSkipped() {
        return testsSkipped;
    }

    public void setTestsSkipped(int testsSkipped) {
        this.testsSkipped = testsSkipped;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    public long getBuildDateTimestamp() {
        return buildDateTimestamp;
    }

    public void setBuildDateTimestamp(long buildDateTimestamp) {
        this.buildDateTimestamp = buildDateTimestamp;
    }

    public String getBuildReleaseType() {
        return buildReleaseType;
    }

    public void setBuildReleaseType(String buildReleaseType) {
        this.buildReleaseType = buildReleaseType;
    }
}
