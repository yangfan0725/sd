package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class XHRoomCollection extends AbstractObjectCollection 
{
    public XHRoomCollection()
    {
        super(XHRoomInfo.class);
    }
    public boolean add(XHRoomInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(XHRoomCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(XHRoomInfo item)
    {
        return removeObject(item);
    }
    public XHRoomInfo get(int index)
    {
        return(XHRoomInfo)getObject(index);
    }
    public XHRoomInfo get(Object key)
    {
        return(XHRoomInfo)getObject(key);
    }
    public void set(int index, XHRoomInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(XHRoomInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(XHRoomInfo item)
    {
        return super.indexOf(item);
    }
}