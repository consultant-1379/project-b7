package com.ericsson.projectb7.pojo;

public class ProjectLastBuildInformation {
    private String projectName;
    private String buildReleaseType;
    private int lastBuildNumber;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getBuildReleaseType() {
        return buildReleaseType;
    }

    public void setBuildReleaseType(String buildReleaseType) {
        this.buildReleaseType = buildReleaseType;
    }

    public int getLastBuildNumber() {
        return lastBuildNumber;
    }

    public void setLastBuildNumber(int lastBuildNumber) {
        this.lastBuildNumber = lastBuildNumber;
    }
}
