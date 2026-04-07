package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class XHTenancyBillInfo extends AbstractXHTenancyBillInfo implements Serializable 
{
    public XHTenancyBillInfo()
    {
        super();
    }
    protected XHTenancyBillInfo(String pkField)
    {
        super(pkField);
    }
}