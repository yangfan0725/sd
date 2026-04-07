package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractXHTenancyBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractXHTenancyBillInfo()
    {
        this("id");
    }
    protected AbstractXHTenancyBillInfo(String pkField)
    {
        super(pkField);
        put("payListEntry", new com.kingdee.eas.fdc.tenancy.TenancyXHRoomPayListEntryCollection());
        put("xhRoomEntry", new com.kingdee.eas.fdc.tenancy.TenancyXHRoomEntryCollection());
    }
    /**
     * Object:星瀚租赁合同's 合同名称property 
     */
    public String getTenancyName()
    {
        return getString("tenancyName");
    }
    public void setTenancyName(String item)
    {
        setString("tenancyName", item);
    }
    /**
     * Object:星瀚租赁合同's 合同类型property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum getTenancyType()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum.getEnum(getString("tenancyType"));
    }
    public void setTenancyType(com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum item)
    {
		if (item != null) {
        setString("tenancyType", item.getValue());
		}
    }
    /**
     * Object:星瀚租赁合同's 起始日期property 
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
     * Object:星瀚租赁合同's 结束日期property 
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
     * Object:星瀚租赁合同's 租期property 
     */
    public java.math.BigDecimal getLeaseCount()
    {
        return getBigDecimal("leaseCount");
    }
    public void setLeaseCount(java.math.BigDecimal item)
    {
        setBigDecimal("leaseCount", item);
    }
    /**
     * Object:星瀚租赁合同's 合同状态property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum getTenancyState()
    {
        return com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum.getEnum(getString("tenancyState"));
    }
    public void setTenancyState(com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum item)
    {
		if (item != null) {
        setString("tenancyState", item.getValue());
		}
    }
    /**
     * Object:星瀚租赁合同's 免租信息property 
     */
    public String getFreeRemark()
    {
        return getString("freeRemark");
    }
    public void setFreeRemark(String item)
    {
        setString("freeRemark", item);
    }
    /**
     * Object:星瀚租赁合同's 递增信息property 
     */
    public String getIncreasedRemark()
    {
        return getString("increasedRemark");
    }
    public void setIncreasedRemark(String item)
    {
        setString("increasedRemark", item);
    }
    /**
     * Object:星瀚租赁合同's 租赁日期property 
     */
    public java.util.Date getTenancyDate()
    {
        return getDate("tenancyDate");
    }
    public void setTenancyDate(java.util.Date item)
    {
        setDate("tenancyDate", item);
    }
    /**
     * Object: 星瀚租赁合同 's 房间 property 
     */
    public com.kingdee.eas.fdc.sellhouse.RoomInfo getRoom()
    {
        return (com.kingdee.eas.fdc.sellhouse.RoomInfo)get("room");
    }
    public void setRoom(com.kingdee.eas.fdc.sellhouse.RoomInfo item)
    {
        put("room", item);
    }
    /**
     * Object: 星瀚租赁合同 's 客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("customer");
    }
    public void setCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("customer", item);
    }
    /**
     * Object: 星瀚租赁合同 's 星瀚客户 property 
     */
    public com.kingdee.eas.fdc.tenancy.XHCustomerInfo getXhCustomer()
    {
        return (com.kingdee.eas.fdc.tenancy.XHCustomerInfo)get("xhCustomer");
    }
    public void setXhCustomer(com.kingdee.eas.fdc.tenancy.XHCustomerInfo item)
    {
        put("xhCustomer", item);
    }
    /**
     * Object: 星瀚租赁合同 's 星瀚房间 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyXHRoomEntryCollection getXhRoomEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyXHRoomEntryCollection)get("xhRoomEntry");
    }
    /**
     * Object: 星瀚租赁合同 's 付款计划分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyXHRoomPayListEntryCollection getPayListEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyXHRoomPayListEntryCollection)get("payListEntry");
    }
    /**
     * Object: 星瀚租赁合同 's 租赁合同 property 
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
     * Object:星瀚租赁合同's 退租日期property 
     */
    public java.util.Date getQuitRoomDate()
    {
        return getDate("quitRoomDate");
    }
    public void setQuitRoomDate(java.util.Date item)
    {
        setDate("quitRoomDate", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("E628832E");
    }
}