package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ExpenseApplyInfo extends AbstractExpenseApplyInfo implements Serializable 
{
    public ExpenseApplyInfo()
    {
        super();
    }
    protected ExpenseApplyInfo(String pkField)
    {
        super(pkField);
    }
}