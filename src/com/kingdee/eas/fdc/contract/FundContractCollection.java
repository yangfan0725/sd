package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class FundContractCollection extends AbstractObjectCollection 
{
    public FundContractCollection()
    {
        super(FundContractInfo.class);
    }
    public boolean add(FundContractInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(FundContractCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(FundContractInfo item)
    {
        return removeObject(item);
    }
    public FundContractInfo get(int index)
    {
        return(FundContractInfo)getObject(index);
    }
    public FundContractInfo get(Object key)
    {
        return(FundContractInfo)getObject(key);
    }
    public void set(int index, FundContractInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(FundContractInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(FundContractInfo item)
    {
        return super.indexOf(item);
    }
}