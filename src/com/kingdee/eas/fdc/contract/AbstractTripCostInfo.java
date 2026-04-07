package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTripCostInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractTripCostInfo()
    {
        this("id");
    }
    protected AbstractTripCostInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 出差报销 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object:出差报销's 出差事由property 
     */
    public String getPurpose()
    {
        return getString("purpose");
    }
    public void setPurpose(String item)
    {
        setString("purpose", item);
    }
    /**
     * Object:出差报销's 出差日期property 
     */
    public java.util.Date getFromDate()
    {
        return getDate("fromDate");
    }
    public void setFromDate(java.util.Date item)
    {
        setDate("fromDate", item);
    }
    /**
     * Object:出差报销's 出差日期property 
     */
    public java.util.Date getToDate()
    {
        return getDate("toDate");
    }
    public void setToDate(java.util.Date item)
    {
        setDate("toDate", item);
    }
    /**
     * Object:出差报销's 出差天数property 
     */
    public int getDay()
    {
        return getInt("day");
    }
    public void setDay(int item)
    {
        setInt("day", item);
    }
    /**
     * Object:出差报销's 出差地点property 
     */
    public String getPlace()
    {
        return getString("place");
    }
    public void setPlace(String item)
    {
        setString("place", item);
    }
    /**
     * Object:出差报销's 交通工具property 
     */
    public String getTransport()
    {
        return getString("transport");
    }
    public void setTransport(String item)
    {
        setString("transport", item);
    }
    /**
     * Object: 出差报销 's 出差申请 property 
     */
    public com.kingdee.eas.fdc.contract.TripApplyInfo getTripApply()
    {
        return (com.kingdee.eas.fdc.contract.TripApplyInfo)get("tripApply");
    }
    public void setTripApply(com.kingdee.eas.fdc.contract.TripApplyInfo item)
    {
        put("tripApply", item);
    }
    /**
     * Object: 出差报销 's 报销人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object: 出差报销 's 报销部门 property 
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
     * Object: 出差报销 's 出差流程类型 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWFTypeInfo getContractWFType()
    {
        return (com.kingdee.eas.fdc.contract.ContractWFTypeInfo)get("contractWFType");
    }
    public void setContractWFType(com.kingdee.eas.fdc.contract.ContractWFTypeInfo item)
    {
        put("contractWFType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("CBB614CD");
    }
}