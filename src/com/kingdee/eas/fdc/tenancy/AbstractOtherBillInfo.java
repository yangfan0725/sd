package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractOtherBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractOtherBillInfo()
    {
        this("id");
    }
    protected AbstractOtherBillInfo(String pkField)
    {
        super(pkField);
        put("payEntry", new com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection());
        put("entry", new com.kingdee.eas.fdc.tenancy.OtherBillEntryCollection());
    }
    /**
     * Object: K其他合同 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object: K其他合同 's 款项明细 property 
     */
    public com.kingdee.eas.fdc.tenancy.OtherBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.OtherBillEntryCollection)get("entry");
    }
    /**
     * Object: K其他合同 's 其他合同应付明细 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection getPayEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection)get("payEntry");
    }
    /**
     * Object:K其他合同's 收取周期property 
     */
    public int getLeaseTime()
    {
        return getInt("leaseTime");
    }
    public void setLeaseTime(int item)
    {
        setInt("leaseTime", item);
    }
    /**
     * Object:K其他合同's 开始日期property 
     */
    public java.util.Date getStartDate()
    {
        return getDate("startDate");
    }
    public void setStartDate(java.util.Date item)
    {
        setDate("startDate", item);
    }
    /**
     * Object:K其他合同's 结束日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    /**
     * Object: K其他合同 's 部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("dept");
    }
    public void setDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("dept", item);
    }
    /**
     * Object:K其他合同's 手工合同号property 
     */
    public String getContractNo()
    {
        return getString("contractNo");
    }
    public void setContractNo(String item)
    {
        setString("contractNo", item);
    }
    /**
     * Object:K其他合同's 保证金信息property 
     */
    public String getDes()
    {
        return getString("des");
    }
    public void setDes(String item)
    {
        setString("des", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("73402BAE");
    }
}