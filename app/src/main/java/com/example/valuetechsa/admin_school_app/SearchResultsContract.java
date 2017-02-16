package com.example.valuetechsa.admin_school_app;

/**
 * Created by ValueTechSA on 18-01-2017.
 */
public class SearchResultsContract {
    private String vendorname;
    private String vendoremail;
    private String busdrivernumber;
    private String expiredate;
    private int contractrowposition;

    public String getVendorname() {
        return vendorname;
    }

    public void setVendorname(String vendorname) {
        this.vendorname = vendorname;
    }

    public String getVendoremail() {
        return vendoremail;
    }

    public void setVendoremail(String vendoremail) {
        this.vendoremail = vendoremail;
    }

    public String getBusdrivernumber() {
        return busdrivernumber;
    }

    public void setBusdrivernumber(String busdrivernumber) {
        this.busdrivernumber = busdrivernumber;
    }

    public String getExpiredate() {
        return expiredate;
    }

    public void setExpiredate(String expiredate) {
        this.expiredate = expiredate;
    }

    public int getContractrowposition() {
        return contractrowposition;
    }

    public void setContractrowposition(int contractrowposition) {
        this.contractrowposition = contractrowposition;
    }
}
