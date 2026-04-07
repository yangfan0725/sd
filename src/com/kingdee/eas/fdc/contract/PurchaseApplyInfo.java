package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PurchaseApplyInfo extends AbstractPurchaseApplyInfo implements Serializable 
{
    public PurchaseApplyInfo()
    {
        super();
    }
    protected PurchaseApplyInfo(String pkField)
    {
        super(pkField);
    }
}