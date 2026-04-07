package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyXHRoomPayListEntryInfo extends com.kingdee.eas.fdc.basecrm.RevListInfo implements Serializable 
{
    public AbstractTenancyXHRoomPayListEntryInfo()
    {
        this("id");
    }
    protected AbstractTenancyXHRoomPayListEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 房间付款计划分录 's 头 property 
     */
    public com.kingdee.eas.fdc.tenancy.XHTenancyBillInfo getHead()
    {
        return (com.kingdee.eas.fdc.tenancy.XHTenancyBillInfo)get("head");
    }
    public void setHead(com.kingdee.eas.fdc.tenancy.XHTenancyBillInfo item)
    {
        put("head", item);
    }
    /**
     * Object:房间付款计划分录's 租期序号property 
     */
    public int getLeaseSeq()
    {
        return getInt("leaseSeq");
    }
    public void setLeaseSeq(int item)
    {
        setInt("leaseSeq", item);
    }
    /**
     * Object:房间付款计划分录's 开始日期property 
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
     * Object:房间付款计划分录's 结束日期property 
     */
    public java.util.Date getEndDate()
    {
        return getDate("endDate");
    }
    public void setEndDate(java.util.Date item)
    {
        setDate("endDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("BD7F53AE");
    }
}