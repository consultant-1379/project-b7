package com.ericsson.projectb7.pojo;

public class CnaDetails {
    private String cnaName;
    private String cnaNumber;

    public CnaDetails(String cnaName, String cnaNumber) {
        this.cnaName = cnaName;
        this.cnaNumber = cnaNumber;
    }

    public String getCnaName() {
        return cnaName;
    }

    public void setCnaName(String cnaName) {
        this.cnaName = cnaName;
    }

    public String getCnaNumber() {
        return cnaNumber;
    }

    public void setCnaNumber(String cnaNumber) {
        this.cnaNumber = cnaNumber;
    }
}