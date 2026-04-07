package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class XHCustomerCollection extends AbstractObjectCollection 
{
    public XHCustomerCollection()
    {
        super(XHCustomerInfo.class);
    }
    public boolean add(XHCustomerInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(XHCustomerCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(XHCustomerInfo item)
    {
        return removeObject(item);
    }
    public XHCustomerInfo get(int index)
    {
        return(XHCustomerInfo)getObject(index);
    }
    public XHCustomerInfo get(Object key)
    {
        return(XHCustomerInfo)getObject(key);
    }
    public void set(int index, XHCustomerInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(XHCustomerInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(XHCustomerInfo item)
    {
        return super.indexOf(item);
    }
}