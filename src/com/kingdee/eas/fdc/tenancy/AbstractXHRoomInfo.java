package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractXHRoomInfo extends com.kingdee.eas.fdc.basedata.FDCDataBaseInfo implements Serializable 
{
    public AbstractXHRoomInfo()
    {
        this("id");
    }
    protected AbstractXHRoomInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 星瀚房间 's 项目 property 
     */
    public com.kingdee.eas.fdc.sellhouse.SellProjectInfo getSellProject()
    {
        return (com.kingdee.eas.fdc.sellhouse.SellProjectInfo)get("sellProject");
    }
    public void setSellProject(com.kingdee.eas.fdc.sellhouse.SellProjectInfo item)
    {
        put("sellProject", item);
    }
    /**
     * Object:星瀚房间's 面积property 
     */
    public java.math.BigDecimal getArea()
    {
        return getBigDecimal("area");
    }
    public void setArea(java.math.BigDecimal item)
    {
        setBigDecimal("area", item);
    }
    /**
     * Object:星瀚房间's 是否已售property 
     */
    public com.kingdee.eas.fdc.contract.app.YesOrNoEnum getIsSale()
    {
        return com.kingdee.eas.fdc.contract.app.YesOrNoEnum.getEnum(getString("isSale"));
    }
    public void setIsSale(com.kingdee.eas.fdc.contract.app.YesOrNoEnum item)
    {
		if (item != null) {
        setString("isSale", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("09862AB4");
    }
}