package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTenancyXHRoomEntryInfo extends com.kingdee.eas.framework.CoreBaseInfo implements Serializable 
{
    public AbstractTenancyXHRoomEntryInfo()
    {
        this("id");
    }
    protected AbstractTenancyXHRoomEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 星瀚房间分录 's 头 property 
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
     * Object: 星瀚房间分录 's 星瀚房间 property 
     */
    public com.kingdee.eas.fdc.tenancy.XHRoomInfo getXhRoom()
    {
        return (com.kingdee.eas.fdc.tenancy.XHRoomInfo)get("xhRoom");
    }
    public void setXhRoom(com.kingdee.eas.fdc.tenancy.XHRoomInfo item)
    {
        put("xhRoom", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("804539B0");
    }
}