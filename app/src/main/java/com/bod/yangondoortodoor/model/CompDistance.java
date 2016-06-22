package com.bod.yangondoortodoor.model;

import java.util.Comparator;

/**
 * Created by DELL on 1/18/2016.
 */
public class CompDistance implements Comparator<LocObj>
{
    @Override
    public int compare(LocObj arg0, LocObj arg1) {
        return (int)arg0.distance - (int)arg1.distance;
    }

}
