package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class FundContractInfo extends AbstractFundContractInfo implements Serializable 
{
    public FundContractInfo()
    {
        super();
    }
    protected FundContractInfo(String pkField)
    {
        super(pkField);
    }
}