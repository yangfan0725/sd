package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class PurchaseDocumentCollection extends AbstractObjectCollection 
{
    public PurchaseDocumentCollection()
    {
        super(PurchaseDocumentInfo.class);
    }
    public boolean add(PurchaseDocumentInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(PurchaseDocumentCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(PurchaseDocumentInfo item)
    {
        return removeObject(item);
    }
    public PurchaseDocumentInfo get(int index)
    {
        return(PurchaseDocumentInfo)getObject(index);
    }
    public PurchaseDocumentInfo get(Object key)
    {
        return(PurchaseDocumentInfo)getObject(key);
    }
    public void set(int index, PurchaseDocumentInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(PurchaseDocumentInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(PurchaseDocumentInfo item)
    {
        return super.indexOf(item);
    }
}