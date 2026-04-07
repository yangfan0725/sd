/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy;

import java.util.Map;
import java.util.List;
import java.util.Iterator;
import com.kingdee.util.enums.StringEnum;

/**
 * output class name
 */
public class OtherBillTypeEnum extends StringEnum
{
    public static final String SF_VALUE = "SF";//alias=水费合同
    public static final String DF_VALUE = "DF";//alias=电费合同
    public static final String SD_VALUE = "SD";//alias=水电合同
    public static final String WY_VALUE = "WY";//alias=物业合同
    public static final String SB_VALUE = "SB";//alias=设备租赁合同
    public static final String LD_VALUE = "LD";//alias=绿地占用合同
    public static final String CD_VALUE = "CD";//alias=场地租赁合同
    public static final String ZF_VALUE = "ZF";//alias=政府补贴合同

    public static final OtherBillTypeEnum SF = new OtherBillTypeEnum("SF", SF_VALUE);
    public static final OtherBillTypeEnum DF = new OtherBillTypeEnum("DF", DF_VALUE);
    public static final OtherBillTypeEnum SD = new OtherBillTypeEnum("SD", SD_VALUE);
    public static final OtherBillTypeEnum WY = new OtherBillTypeEnum("WY", WY_VALUE);
    public static final OtherBillTypeEnum SB = new OtherBillTypeEnum("SB", SB_VALUE);
    public static final OtherBillTypeEnum LD = new OtherBillTypeEnum("LD", LD_VALUE);
    public static final OtherBillTypeEnum CD = new OtherBillTypeEnum("CD", CD_VALUE);
    public static final OtherBillTypeEnum ZF = new OtherBillTypeEnum("ZF", ZF_VALUE);

    /**
     * construct function
     * @param String otherBillTypeEnum
     */
    private OtherBillTypeEnum(String name, String otherBillTypeEnum)
    {
        super(name, otherBillTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static OtherBillTypeEnum getEnum(String otherBillTypeEnum)
    {
        return (OtherBillTypeEnum)getEnum(OtherBillTypeEnum.class, otherBillTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(OtherBillTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(OtherBillTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(OtherBillTypeEnum.class);
    }
}