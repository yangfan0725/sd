package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.dao.AbstractObjectCollection;
import com.kingdee.bos.dao.IObjectPK;

public class ContractMDeveloperEntryCollection extends AbstractObjectCollection 
{
    public ContractMDeveloperEntryCollection()
    {
        super(ContractMDeveloperEntryInfo.class);
    }
    public boolean add(ContractMDeveloperEntryInfo item)
    {
        return addObject(item);
    }
    public boolean addCollection(ContractMDeveloperEntryCollection item)
    {
        return addObjectCollection(item);
    }
    public boolean remove(ContractMDeveloperEntryInfo item)
    {
        return removeObject(item);
    }
    public ContractMDeveloperEntryInfo get(int index)
    {
        return(ContractMDeveloperEntryInfo)getObject(index);
    }
    public ContractMDeveloperEntryInfo get(Object key)
    {
        return(ContractMDeveloperEntryInfo)getObject(key);
    }
    public void set(int index, ContractMDeveloperEntryInfo item)
    {
        setObject(index, item);
    }
    public boolean contains(ContractMDeveloperEntryInfo item)
    {
        return containsObject(item);
    }
    public boolean contains(Object key)
    {
        return containsKey(key);
    }
    public int indexOf(ContractMDeveloperEntryInfo item)
    {
        return super.indexOf(item);
    }
}