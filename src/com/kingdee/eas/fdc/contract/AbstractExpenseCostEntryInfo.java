package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExpenseCostEntryInfo extends com.kingdee.eas.framework.CoreBillEntryBaseInfo implements Serializable 
{
    public AbstractExpenseCostEntryInfo()
    {
        this("id");
    }
    protected AbstractExpenseCostEntryInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 分录 's 单据头 property 
     */
    public com.kingdee.eas.fdc.contract.ExpenseCostInfo getParent()
    {
        return (com.kingdee.eas.fdc.contract.ExpenseCostInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.contract.ExpenseCostInfo item)
    {
        put("parent", item);
    }
    /**
     * Object:分录's 详细信息property 
     */
    public String getDetail()
    {
        return getString("detail");
    }
    public void setDetail(String item)
    {
        setString("detail", item);
    }
    /**
     * Object:分录's 内容property 
     */
    public String getContent()
    {
        return getString("content");
    }
    public void setContent(String item)
    {
        setString("content", item);
    }
    /**
     * Object:分录's 描述property 
     */
    public String getDesc()
    {
        return getString("desc");
    }
    public void setDesc(String item)
    {
        setString("desc", item);
    }
    /**
     * Object:分录's 表行Keyproperty 
     */
    public String getRowKey()
    {
        return getString("rowKey");
    }
    public void setRowKey(String item)
    {
        setString("rowKey", item);
    }
    /**
     * Object:分录's 数据类型property 
     */
    public com.kingdee.eas.fdc.basedata.DataTypeEnum getDataType()
    {
        return com.kingdee.eas.fdc.basedata.DataTypeEnum.getEnum(getInt("dataType"));
    }
    public void setDataType(com.kingdee.eas.fdc.basedata.DataTypeEnum item)
    {
		if (item != null) {
        setInt("dataType", item.getValue());
		}
    }
    /**
     * Object:分录's 详细信息定义IDproperty 
     */
    public String getDetailDefID()
    {
        return getString("detailDefID");
    }
    public void setDetailDefID(String item)
    {
        setString("detailDefID", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("13842768");
    }
}