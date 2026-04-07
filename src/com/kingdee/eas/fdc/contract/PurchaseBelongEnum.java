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
public class PurchaseBelongEnum extends StringEnum
{
    public static final String GCL_VALUE = "GCL";//alias=工程类
    public static final String HWL_VALUE = "HWL";//alias=货物类
    public static final String FWL_VALUE = "FWL";//alias=服务类

    public static final PurchaseBelongEnum GCL = new PurchaseBelongEnum("GCL", GCL_VALUE);
    public static final PurchaseBelongEnum HWL = new PurchaseBelongEnum("HWL", HWL_VALUE);
    public static final PurchaseBelongEnum FWL = new PurchaseBelongEnum("FWL", FWL_VALUE);

    /**
     * construct function
     * @param String purchaseBelongEnum
     */
    private PurchaseBelongEnum(String name, String purchaseBelongEnum)
    {
        super(name, purchaseBelongEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static PurchaseBelongEnum getEnum(String purchaseBelongEnum)
    {
        return (PurchaseBelongEnum)getEnum(PurchaseBelongEnum.class, purchaseBelongEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(PurchaseBelongEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(PurchaseBelongEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(PurchaseBelongEnum.class);
    }
}