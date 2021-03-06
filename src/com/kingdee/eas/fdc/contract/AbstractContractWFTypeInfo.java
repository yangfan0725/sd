package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractWFTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractContractWFTypeInfo()
    {
        this("id");
    }
    protected AbstractContractWFTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:合同流程类型's 启用或禁用状态property 
     */
    public boolean isIsEnabled()
    {
        return getBoolean("isEnabled");
    }
    public void setIsEnabled(boolean item)
    {
        setBoolean("isEnabled", item);
    }
    /**
     * Object: 合同流程类型 's 父节点 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWFTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractWFTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractWFTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:合同流程类型's oa流程模板idproperty 
     */
    public String getOaTId()
    {
        return getString("oaTId");
    }
    public void setOaTId(String item)
    {
        setString("oaTId", item);
    }
    /**
     * Object:合同流程类型's oa流程模板id（补充合同）property 
     */
    public String getOaSTId()
    {
        return getString("oaSTId");
    }
    public void setOaSTId(String item)
    {
        setString("oaSTId", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("8B1F0936");
    }
}