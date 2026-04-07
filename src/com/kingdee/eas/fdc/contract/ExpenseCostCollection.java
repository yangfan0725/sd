package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ExpenseCostCollection extends AbstractObjectCollection 
{
    public ExpenseCostCollection()
    {
        super(ExpenseCostInfo.class);
    }
    public boolean add(ExpenseCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ExpenseCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ExpenseCostInfo item)
    {
        return removeObject(item);
    }
    public ExpenseCostInfo get(int index)
    {
        return(ExpenseCostInfo)getObject(index);
    }
    public ExpenseCostInfo get(Object key)
    {
        return(ExpenseCostInfo)getObject(key);
    }
    public void set(int index, ExpenseCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ExpenseCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ExpenseCostInfo item)
    {
        return super.indexOf(item);
    }
}