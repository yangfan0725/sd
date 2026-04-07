package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class TenancyXHRoomPayListEntryCollection extends AbstractObjectCollection 
{
    public TenancyXHRoomPayListEntryCollection()
    {
        super(TenancyXHRoomPayListEntryInfo.class);
    }
    public boolean add(TenancyXHRoomPayListEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(TenancyXHRoomPayListEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(TenancyXHRoomPayListEntryInfo item)
    {
        return removeObject(item);
    }
    public TenancyXHRoomPayListEntryInfo get(int index)
    {
        return(TenancyXHRoomPayListEntryInfo)getObject(index);
    }
    public TenancyXHRoomPayListEntryInfo get(Object key)
    {
        return(TenancyXHRoomPayListEntryInfo)getObject(key);
    }
    public void set(int index, TenancyXHRoomPayListEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(TenancyXHRoomPayListEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(TenancyXHRoomPayListEntryInfo item)
    {
        return super.indexOf(item);
    }
}