package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class FundTransferInfo extends AbstractFundTransferInfo implements Serializable 
{
    public FundTransferInfo()
    {
        super();
    }
    protected FundTransferInfo(String pkField)
    {
        super(pkField);
    }
}