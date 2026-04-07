package com.kingdee.eas.fdc.contract;

import java.io.Serializable;

public class ExpenseCostInfo extends AbstractExpenseCostInfo implements Serializable 
{
    public ExpenseCostInfo()
    {
        super();
    }
    protected ExpenseCostInfo(String pkField)
    {
        super(pkField);
    }
}