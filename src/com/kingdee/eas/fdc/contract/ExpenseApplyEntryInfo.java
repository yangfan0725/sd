package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ExpenseApplyEntryInfo extends AbstractExpenseApplyEntryInfo implements Serializable 
{
    public ExpenseApplyEntryInfo()
    {
        super();
    }
    protected ExpenseApplyEntryInfo(String pkField)
    {
        super(pkField);
    }
}