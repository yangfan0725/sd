package com.kingdee.eas.fdc.schedule;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractTaskTypeInfo extends com.kingdee.eas.framework.TreeBaseInfo implements Serializable 
{
    public AbstractTaskTypeInfo()
    {
        this("id");
    }
    protected AbstractTaskTypeInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object:任务类型's 是否启用property 
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
     * Object: 任务类型 's 父节点 property 
     */
    public com.kingdee.eas.fdc.schedule.TaskTypeInfo getParent()
    {
        return (com.kingdee.eas.fdc.schedule.TaskTypeInfo)get("parent");
    }
    public void setParent(com.kingdee.eas.fdc.schedule.TaskTypeInfo item)
    {
        put("parent", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("137FE435");
    }
}