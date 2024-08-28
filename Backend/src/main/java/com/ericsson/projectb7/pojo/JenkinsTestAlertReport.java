package com.ericsson.projectb7.pojo;

public class JenkinsTestAlertReport {
    private String projectName;
    private String cxpNumber;
    private int buildNumber;
    private long buildDateTimestamp;
    private String buildReleaseType;
    private String cnaName;

    public JenkinsTestAlertReport(String projectName, String cxpNumber, int buildNumber, long buildDateTimestamp, String buildReleaseType, String cnaName) {
        this.projectName = projectName;
        this.cxpNumber = cxpNumber;
        this.buildNumber = buildNumber;
        this.buildDateTimestamp = buildDateTimestamp;
        this.buildReleaseType = buildReleaseType;
        this.cnaName = cnaName;
    }

    public JenkinsTestAlertReport() {

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

    public String getCnaName() {
        return cnaName;
    }

    public void setCnaName(String cnaName) {
        this.cnaName = cnaName;
    }
}
