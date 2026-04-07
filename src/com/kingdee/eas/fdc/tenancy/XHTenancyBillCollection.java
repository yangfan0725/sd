package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class XHTenancyBillCollection extends AbstractObjectCollection 
{
    public XHTenancyBillCollection()
    {
        super(XHTenancyBillInfo.class);
    }
    public boolean add(XHTenancyBillInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(XHTenancyBillCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(XHTenancyBillInfo item)
    {
        return removeObject(item);
    }
    public XHTenancyBillInfo get(int index)
    {
        return(XHTenancyBillInfo)getObject(index);
    }
    public XHTenancyBillInfo get(Object key)
    {
        return(XHTenancyBillInfo)getObject(key);
    }
    public void set(int index, XHTenancyBillInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(XHTenancyBillInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(XHTenancyBillInfo item)
    {
        return super.indexOf(item);
    }
}