package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TripApplyCollection extends AbstractObjectCollection 
{
    public TripApplyCollection()
    {
        super(TripApplyInfo.class);
    }
    public boolean add(TripApplyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TripApplyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TripApplyInfo item)
    {
        return removeObject(item);
    }
    public TripApplyInfo get(int index)
    {
        return(TripApplyInfo)getObject(index);
    }
    public TripApplyInfo get(Object key)
    {
        return(TripApplyInfo)getObject(key);
    }
    public void set(int index, TripApplyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TripApplyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TripApplyInfo item)
    {
        return super.indexOf(item);
    }
}