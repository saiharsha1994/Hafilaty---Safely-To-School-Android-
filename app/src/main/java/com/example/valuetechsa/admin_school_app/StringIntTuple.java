package com.example.valuetechsa.admin_school_app;

/**
 * Created by ValueTechSA on 27-03-2017.
 */
public class StringIntTuple{
    public final Double intValue;
    public final String stringValue;
    public StringIntTuple(Double intValue, String stringValue){
        this.intValue = intValue;
        this.stringValue = stringValue;
    }
    public String toString(){
        return "(" + this.intValue + "," + this.stringValue + ")";
    }

}
