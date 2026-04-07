package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractMDeveloperEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractContractMDeveloperEntryInfo()
    {
        this("id");
    }
    protected AbstractContractMDeveloperEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 多甲方协议 's 头 property 
     */
    public com.kingdee.eas.fdc.contract.ContractBillInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ContractBillInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ContractBillInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 多甲方协议 's 甲方 property 
     */
    public com.kingdee.eas.fdc.basedata.LandDeveloperInfo getLandDeveloper()
    {
        return (com.kingdee.eas.fdc.basedata.LandDeveloperInfo)get("landDeveloper");
    }
    public void setLandDeveloper(com.kingdee.eas.fdc.basedata.LandDeveloperInfo item)
    {
        put("landDeveloper", item);
    }
    /**
     * Object:多甲方协议's 管理中心property 
     */
    public com.kingdee.eas.fdc.contract.ManagementCenterEnum getCenter()
    {
        return com.kingdee.eas.fdc.contract.ManagementCenterEnum.getEnum(getString("center"));
    }
    public void setCenter(com.kingdee.eas.fdc.contract.ManagementCenterEnum item)
    {
		if (item != null) {
        setString("center", item.getValue());
		}
    }
    /**
     * Object:多甲方协议's 分摊金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object:多甲方协议's 备注说明property 
     */
    public String getRemark()
    {
        return getString("remark");
    }
    public void setRemark(String item)
    {
        setString("remark", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("C745D488");
    }
}