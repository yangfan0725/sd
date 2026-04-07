package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ContractMDeveloperEntryInfo extends AbstractContractMDeveloperEntryInfo implements Serializable 
{
    public ContractMDeveloperEntryInfo()
    {
        super();
    }
    protected ContractMDeveloperEntryInfo(String pkField)
    {
        super(pkField);
    }
}