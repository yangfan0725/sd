package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class TripCostInfo extends AbstractTripCostInfo implements Serializable 
{
    public TripCostInfo()
    {
        super();
    }
    protected TripCostInfo(String pkField)
    {
        super(pkField);
    }
}