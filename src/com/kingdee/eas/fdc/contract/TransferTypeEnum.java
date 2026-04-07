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
public class TransferTypeEnum extends StringEnum
{
    public static final String ONE_VALUE = "ONE";//alias=µ¥±Ê
    public static final String MANY_VALUE = "MANY";//alias=¶à±Ê

    public static final TransferTypeEnum ONE = new TransferTypeEnum("ONE", ONE_VALUE);
    public static final TransferTypeEnum MANY = new TransferTypeEnum("MANY", MANY_VALUE);

    /**
     * construct function
     * @param String transferTypeEnum
     */
    private TransferTypeEnum(String name, String transferTypeEnum)
    {
        super(name, transferTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static TransferTypeEnum getEnum(String transferTypeEnum)
    {
        return (TransferTypeEnum)getEnum(TransferTypeEnum.class, transferTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(TransferTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(TransferTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(TransferTypeEnum.class);
    }
}