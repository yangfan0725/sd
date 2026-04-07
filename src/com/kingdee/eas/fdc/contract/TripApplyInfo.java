package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class TripApplyInfo extends AbstractTripApplyInfo implements Serializable 
{
    public TripApplyInfo()
    {
        super();
    }
    protected TripApplyInfo(String pkField)
    {
        super(pkField);
    }
}