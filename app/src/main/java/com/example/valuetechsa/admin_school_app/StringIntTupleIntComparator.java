package com.example.valuetechsa.admin_school_app;

/**
 * Created by ValueTechSA on 27-03-2017.
 */
import java.util.Comparator;


public class StringIntTupleIntComparator implements Comparator<StringIntTuple> {

    @Override
    public int compare(StringIntTuple a,
                       StringIntTuple b) {
        return (a.intValue).compareTo(b.intValue);
    }

}
