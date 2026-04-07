package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class PurchaseDocumentInfo extends AbstractPurchaseDocumentInfo implements Serializable 
{
    public PurchaseDocumentInfo()
    {
        super();
    }
    protected PurchaseDocumentInfo(String pkField)
    {
        super(pkField);
    }
}