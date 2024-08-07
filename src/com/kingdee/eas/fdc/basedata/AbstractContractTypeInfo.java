package com.kingdee.eas.fdc.basedata;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractContractTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractContractTypeInfo()
    {
        this("id");
    }
    protected AbstractContractTypeInfo(String pkField)
    {
        super(pkField);
        put("contractWFTypeEntry", new com.kingdee.eas.fdc.basedata.ContractWFEntryCollection());
        put("entry", new com.kingdee.eas.fdc.basedata.PayContentTypeEntryCollection());
        put("inviteTypeEntry", new com.kingdee.eas.fdc.basedata.ContractInviteTypeEntryCollection());
    }
    /**
     * Object:合同类型's 启用或禁用状态property 
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
     * Object:合同类型's 是否成本拆分项property 
     */
    public boolean isIsCost()
    {
        return getBoolean("isCost");
    }
    public void setIsCost(boolean item)
    {
        setBoolean("isCost", item);
    }
    /**
     * Object:合同类型's 付款比例property 
     */
    public java.math.BigDecimal getPayScale()
    {
        return getBigDecimal("payScale");
    }
    public void setPayScale(java.math.BigDecimal item)
    {
        setBigDecimal("payScale", item);
    }
    /**
     * Object: 合同类型 's 父结点 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("parent", item);
    }
    /**
     * Object: 合同类型 's 责任部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDutyOrgUnit()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("dutyOrgUnit");
    }
    public void setDutyOrgUnit(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("dutyOrgUnit", item);
    }
    /**
     * Object:合同类型's 印花税率property 
     */
    public java.math.BigDecimal getStampTaxRate()
    {
        return getBigDecimal("stampTaxRate");
    }
    public void setStampTaxRate(java.math.BigDecimal item)
    {
        setBigDecimal("stampTaxRate", item);
    }
    /**
     * Object:合同类型's 长编码property 
     */
    public String getForSupportLongnumberCoding()
    {
        return getString("forSupportLongnumberCoding");
    }
    public void setForSupportLongnumberCoding(String item)
    {
        setString("forSupportLongnumberCoding", item);
    }
    /**
     * Object: 合同类型 's 付款事项分录 property 
     */
    public com.kingdee.eas.fdc.basedata.PayContentTypeEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.basedata.PayContentTypeEntryCollection)get("entry");
    }
    /**
     * Object:合同类型's 审批流程发起组织property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum getOrgType()
    {
        return com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.getEnum(getString("orgType"));
    }
    public void setOrgType(com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum item)
    {
		if (item != null) {
        setString("orgType", item.getValue());
		}
    }
    /**
     * Object: 合同类型 's 合同流程类型分录 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractWFEntryCollection getContractWFTypeEntry()
    {
        return (com.kingdee.eas.fdc.basedata.ContractWFEntryCollection)get("contractWFTypeEntry");
    }
    /**
     * Object: 合同类型 's 采购类别分录 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractInviteTypeEntryCollection getInviteTypeEntry()
    {
        return (com.kingdee.eas.fdc.basedata.ContractInviteTypeEntryCollection)get("inviteTypeEntry");
    }
    /**
     * Object:合同类型's 是否受控于成本科目控制property 
     */
    public boolean isIsAccountView()
    {
        return getBoolean("isAccountView");
    }
    public void setIsAccountView(boolean item)
    {
        setBoolean("isAccountView", item);
    }
    /**
     * Object:合同类型's 是否只允许单次付款申请property 
     */
    public boolean isSinglePayment()
    {
        return getBoolean("singlePayment");
    }
    public void setSinglePayment(boolean item)
    {
        setBoolean("singlePayment", item);
    }
    /**
     * Object:合同类型's 是否受控于中标审批单property 
     */
    public boolean isIsTA()
    {
        return getBoolean("isTA");
    }
    public void setIsTA(boolean item)
    {
        setBoolean("isTA", item);
    }
    /**
     * Object:合同类型's 是否必须录入合同事项发生明细property 
     */
    public boolean isIsMarket()
    {
        return getBoolean("isMarket");
    }
    public void setIsMarket(boolean item)
    {
        setBoolean("isMarket", item);
    }
    /**
     * Object:合同类型's 是否web端合约规划控制property 
     */
    public boolean isIsWebPC()
    {
        return getBoolean("isWebPC");
    }
    public void setIsWebPC(boolean item)
    {
        setBoolean("isWebPC", item);
    }
    /**
     * Object:合同类型's 是否收款类property 
     */
    public boolean isIsReceive()
    {
        return getBoolean("isReceive");
    }
    public void setIsReceive(boolean item)
    {
        setBoolean("isReceive", item);
    }
    /**
     * Object:合同类型's 是否关联收入类合同property 
     */
    public boolean isIsRelateReceive()
    {
        return getBoolean("isRelateReceive");
    }
    public void setIsRelateReceive(boolean item)
    {
        setBoolean("isRelateReceive", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B371775E");
    }
}