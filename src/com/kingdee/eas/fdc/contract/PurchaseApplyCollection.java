package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseApplyCollection extends AbstractObjectCollection 
{
    public PurchaseApplyCollection()
    {
        super(PurchaseApplyInfo.class);
    }
    public boolean add(PurchaseApplyInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseApplyCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseApplyInfo item)
    {
        return removeObject(item);
    }
    public PurchaseApplyInfo get(int index)
    {
        return(PurchaseApplyInfo)getObject(index);
    }
    public PurchaseApplyInfo get(Object key)
    {
        return(PurchaseApplyInfo)getObject(key);
    }
    public void set(int index, PurchaseApplyInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseApplyInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseApplyInfo item)
    {
        return super.indexOf(item);
    }
}