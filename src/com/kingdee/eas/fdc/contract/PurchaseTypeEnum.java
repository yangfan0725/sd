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
public class PurchaseTypeEnum extends StringEnum
{
    public static final String GKZB_VALUE = "GKZB";//alias=公开招标
    public static final String YQZB_VALUE = "YQZB";//alias=邀请招标
    public static final String JZXTP_VALUE = "JZXTP";//alias=竞争性谈判
    public static final String JZXCS_VALUE = "JZXCS";//alias=竞争性磋商
    public static final String XJ_VALUE = "XJ";//alias=询价
    public static final String DYLYCG_VALUE = "DYLYCG";//alias=单一来源采购
    public static final String ZJCG_VALUE = "ZJCG";//alias=直接采购

    public static final PurchaseTypeEnum GKZB = new PurchaseTypeEnum("GKZB", GKZB_VALUE);
    public static final PurchaseTypeEnum YQZB = new PurchaseTypeEnum("YQZB", YQZB_VALUE);
    public static final PurchaseTypeEnum JZXTP = new PurchaseTypeEnum("JZXTP", JZXTP_VALUE);
    public static final PurchaseTypeEnum JZXCS = new PurchaseTypeEnum("JZXCS", JZXCS_VALUE);
    public static final PurchaseTypeEnum XJ = new PurchaseTypeEnum("XJ", XJ_VALUE);
    public static final PurchaseTypeEnum DYLYCG = new PurchaseTypeEnum("DYLYCG", DYLYCG_VALUE);
    public static final PurchaseTypeEnum ZJCG = new PurchaseTypeEnum("ZJCG", ZJCG_VALUE);

    /**
     * construct function
     * @param String purchaseTypeEnum
     */
    private PurchaseTypeEnum(String name, String purchaseTypeEnum)
    {
        super(name, purchaseTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PurchaseTypeEnum getEnum(String purchaseTypeEnum)
    {
        return (PurchaseTypeEnum)getEnum(PurchaseTypeEnum.class, purchaseTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PurchaseTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PurchaseTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PurchaseTypeEnum.class);
    }
}