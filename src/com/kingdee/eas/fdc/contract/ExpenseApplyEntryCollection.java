package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpenseApplyEntryCollection extends AbstractObjectCollection 
{
    public ExpenseApplyEntryCollection()
    {
        super(ExpenseApplyEntryInfo.class);
    }
    public boolean add(ExpenseApplyEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpenseApplyEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpenseApplyEntryInfo item)
    {
        return removeObject(item);
    }
    public ExpenseApplyEntryInfo get(int index)
    {
        return(ExpenseApplyEntryInfo)getObject(index);
    }
    public ExpenseApplyEntryInfo get(Object key)
    {
        return(ExpenseApplyEntryInfo)getObject(key);
    }
    public void set(int index, ExpenseApplyEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpenseApplyEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpenseApplyEntryInfo item)
    {
        return super.indexOf(item);
    }
}