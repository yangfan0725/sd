package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FundTransferCollection extends AbstractObjectCollection 
{
    public FundTransferCollection()
    {
        super(FundTransferInfo.class);
    }
    public boolean add(FundTransferInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FundTransferCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FundTransferInfo item)
    {
        return removeObject(item);
    }
    public FundTransferInfo get(int index)
    {
        return(FundTransferInfo)getObject(index);
    }
    public FundTransferInfo get(Object key)
    {
        return(FundTransferInfo)getObject(key);
    }
    public void set(int index, FundTransferInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FundTransferInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FundTransferInfo item)
    {
        return super.indexOf(item);
    }
}