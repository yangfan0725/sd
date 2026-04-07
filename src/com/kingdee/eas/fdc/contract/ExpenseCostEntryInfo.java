package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ExpenseCostEntryInfo extends AbstractExpenseCostEntryInfo implements Serializable 
{
    public ExpenseCostEntryInfo()
    {
        super();
    }
    protected ExpenseCostEntryInfo(String pkField)
    {
        super(pkField);
    }
}