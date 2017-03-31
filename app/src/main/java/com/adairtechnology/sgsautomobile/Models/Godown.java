package com.adairtechnology.sgsautomobile.Models;

/**
 * Created by Android-Team1 on 1/8/2017.
 */

public class Godown {

    private String gcode;
    private String partyName;
    private String partyBillNo;
    private String partyDate;


    public String getGcode() {
        return gcode;
    }
   public void setGcode(String gcode) {
        this.gcode = gcode;
    }


    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyBillNo() {
        return partyBillNo;
    }

    public void setPartyBillNo(String partyBillNo) {
        this.partyBillNo = partyBillNo;
    }

    public String getPartyDate() {
        return partyDate;
    }

    public void setPartyDate(String partyDate) {
        this.partyDate = partyDate;
    }
}