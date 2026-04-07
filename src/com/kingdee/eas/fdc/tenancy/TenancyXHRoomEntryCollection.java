package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyXHRoomEntryCollection extends AbstractObjectCollection 
{
    public TenancyXHRoomEntryCollection()
    {
        super(TenancyXHRoomEntryInfo.class);
    }
    public boolean add(TenancyXHRoomEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyXHRoomEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyXHRoomEntryInfo item)
    {
        return removeObject(item);
    }
    public TenancyXHRoomEntryInfo get(int index)
    {
        return(TenancyXHRoomEntryInfo)getObject(index);
    }
    public TenancyXHRoomEntryInfo get(Object key)
    {
        return(TenancyXHRoomEntryInfo)getObject(key);
    }
    public void set(int index, TenancyXHRoomEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyXHRoomEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyXHRoomEntryInfo item)
    {
        return super.indexOf(item);
    }
}