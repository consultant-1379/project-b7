package com.ericsson.projectb7.pojo;

public class EnmProjectDetails {
    private String cxpName;
    private String cxpNumber;
    private String rpmNampe;
    private String cnaName;

    public EnmProjectDetails(String cxpName, String cxpNumber, String rpmNampe, String cnaName) {
        this.cxpName = cxpName;
        this.cxpNumber = cxpNumber;
        this.rpmNampe = rpmNampe;
        this.cnaName = cnaName;
    }

    public String getCxpName() {
        return cxpName;
    }

    public void setCxpName(String cxpName) {
        this.cxpName = cxpName;
    }

    public String getCxpNumber() {
        return cxpNumber;
    }

    public void setCxpNumber(String cxpNumber) {
        this.cxpNumber = cxpNumber;
    }

    public String getRpmNampe() {
        return rpmNampe;
    }

    public void setRpmNampe(String rpmNampe) {
        this.rpmNampe = rpmNampe;
    }

    public String getCnaName() {
        return cnaName;
    }

    public void setCnaName(String cnaName) {
        this.cnaName = cnaName;
    }
}


