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
public class ExpenseTypeEnum extends StringEnum
{
    public static final String ZD_VALUE = "ZD";//alias=招待
    public static final String PX_VALUE = "PX";//alias=培训
    public static final String GDZCCG_VALUE = "GDZCCG";//alias=固定资产采购
    public static final String BGYPCG_VALUE = "BGYPCG";//alias=办公用品采购
    public static final String DIRECT_VALUE = "DIRECT";//alias=直接报销
    public static final String CL_VALUE = "CL";//alias=车辆
    public static final String OTHER_VALUE = "OTHER";//alias=其他
    public static final String LS_VALUE = "LS";//alias=历史合同付款申请

    public static final ExpenseTypeEnum ZD = new ExpenseTypeEnum("ZD", ZD_VALUE);
    public static final ExpenseTypeEnum PX = new ExpenseTypeEnum("PX", PX_VALUE);
    public static final ExpenseTypeEnum GDZCCG = new ExpenseTypeEnum("GDZCCG", GDZCCG_VALUE);
    public static final ExpenseTypeEnum BGYPCG = new ExpenseTypeEnum("BGYPCG", BGYPCG_VALUE);
    public static final ExpenseTypeEnum DIRECT = new ExpenseTypeEnum("DIRECT", DIRECT_VALUE);
    public static final ExpenseTypeEnum CL = new ExpenseTypeEnum("CL", CL_VALUE);
    public static final ExpenseTypeEnum OTHER = new ExpenseTypeEnum("OTHER", OTHER_VALUE);
    public static final ExpenseTypeEnum LS = new ExpenseTypeEnum("LS", LS_VALUE);

    /**
     * construct function
     * @param String expenseTypeEnum
     */
    private ExpenseTypeEnum(String name, String expenseTypeEnum)
    {
        super(name, expenseTypeEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ExpenseTypeEnum getEnum(String expenseTypeEnum)
    {
        return (ExpenseTypeEnum)getEnum(ExpenseTypeEnum.class, expenseTypeEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ExpenseTypeEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ExpenseTypeEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ExpenseTypeEnum.class);
    }
}