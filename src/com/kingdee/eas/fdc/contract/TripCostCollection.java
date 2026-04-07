package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TripCostCollection extends AbstractObjectCollection 
{
    public TripCostCollection()
    {
        super(TripCostInfo.class);
    }
    public boolean add(TripCostInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TripCostCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TripCostInfo item)
    {
        return removeObject(item);
    }
    public TripCostInfo get(int index)
    {
        return(TripCostInfo)getObject(index);
    }
    public TripCostInfo get(Object key)
    {
        return(TripCostInfo)getObject(key);
    }
    public void set(int index, TripCostInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TripCostInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TripCostInfo item)
    {
        return super.indexOf(item);
    }
}