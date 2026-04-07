/**
 * output package name
 */
package com.kingdee.eas.fdc.contract;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class BusinessTypeEnum extends StringEnum
{
    public static final String GW_VALUE = "GW";//alias=公务招待
    public static final String SW_VALUE = "SW";//alias=商务招待
    public static final String PT_VALUE = "PT";//alias=普通招待

    public static final BusinessTypeEnum GW = new BusinessTypeEnum("GW", GW_VALUE);
    public static final BusinessTypeEnum SW = new BusinessTypeEnum("SW", SW_VALUE);
    public static final BusinessTypeEnum PT = new BusinessTypeEnum("PT", PT_VALUE);

    /**
     * construct function
     * @param String businessTypeEnum
     */
    private BusinessTypeEnum(String name, String businessTypeEnum)
    {
        super(name, businessTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static BusinessTypeEnum getEnum(String businessTypeEnum)
    {
        return (BusinessTypeEnum)getEnum(BusinessTypeEnum.class, businessTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(BusinessTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(BusinessTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(BusinessTypeEnum.class);
    }
}