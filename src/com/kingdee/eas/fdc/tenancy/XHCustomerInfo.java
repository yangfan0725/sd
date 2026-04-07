package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;

public class XHCustomerInfo extends AbstractXHCustomerInfo implements Serializable 
{
    public XHCustomerInfo()
    {
        super();
    }
    protected XHCustomerInfo(String pkField)
    {
        super(pkField);
    }
}