package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpenseApplyCollection extends AbstractObjectCollection 
{
    public ExpenseApplyCollection()
    {
        super(ExpenseApplyInfo.class);
    }
    public boolean add(ExpenseApplyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpenseApplyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpenseApplyInfo item)
    {
        return removeObject(item);
    }
    public ExpenseApplyInfo get(int index)
    {
        return(ExpenseApplyInfo)getObject(index);
    }
    public ExpenseApplyInfo get(Object key)
    {
        return(ExpenseApplyInfo)getObject(key);
    }
    public void set(int index, ExpenseApplyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpenseApplyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpenseApplyInfo item)
    {
        return super.indexOf(item);
    }
}