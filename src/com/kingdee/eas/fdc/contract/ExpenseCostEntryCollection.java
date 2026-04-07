package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpenseCostEntryCollection extends AbstractObjectCollection 
{
    public ExpenseCostEntryCollection()
    {
        super(ExpenseCostEntryInfo.class);
    }
    public boolean add(ExpenseCostEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpenseCostEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpenseCostEntryInfo item)
    {
        return removeObject(item);
    }
    public ExpenseCostEntryInfo get(int index)
    {
        return(ExpenseCostEntryInfo)getObject(index);
    }
    public ExpenseCostEntryInfo get(Object key)
    {
        return(ExpenseCostEntryInfo)getObject(key);
    }
    public void set(int index, ExpenseCostEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpenseCostEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpenseCostEntryInfo item)
    {
        return super.indexOf(item);
    }
}