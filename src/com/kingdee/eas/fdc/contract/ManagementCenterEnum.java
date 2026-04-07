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
public class ManagementCenterEnum extends StringEnum
{
    public static final String SND01_VALUE = "SND01";//alias=苏高科本部
    public static final String SND02_VALUE = "SND02";//alias=环保产业园管理中心
    public static final String SND03_VALUE = "SND03";//alias=创新中心管理中心
    public static final String SND04_VALUE = "SND04";//alias=高新智泰产业园管理中心
    public static final String SND05_VALUE = "SND05";//alias=狮山总部经济产业园管理中心
    public static final String SND06_VALUE = "SND06";//alias=智汇高科管理中心
    public static final String SND07_VALUE = "SND07";//alias=高新智造港管理中心
    public static final String SND08_VALUE = "SND08";//alias=常熟智能制造创新园管理中心

    public static final ManagementCenterEnum SND01 = new ManagementCenterEnum("SND01", SND01_VALUE);
    public static final ManagementCenterEnum SND02 = new ManagementCenterEnum("SND02", SND02_VALUE);
    public static final ManagementCenterEnum SND03 = new ManagementCenterEnum("SND03", SND03_VALUE);
    public static final ManagementCenterEnum SND04 = new ManagementCenterEnum("SND04", SND04_VALUE);
    public static final ManagementCenterEnum SND05 = new ManagementCenterEnum("SND05", SND05_VALUE);
    public static final ManagementCenterEnum SND06 = new ManagementCenterEnum("SND06", SND06_VALUE);
    public static final ManagementCenterEnum SND07 = new ManagementCenterEnum("SND07", SND07_VALUE);
    public static final ManagementCenterEnum SND08 = new ManagementCenterEnum("SND08", SND08_VALUE);

    /**
     * construct function
     * @param String managementCenterEnum
     */
    private ManagementCenterEnum(String name, String managementCenterEnum)
    {
        super(name, managementCenterEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ManagementCenterEnum getEnum(String managementCenterEnum)
    {
        return (ManagementCenterEnum)getEnum(ManagementCenterEnum.class, managementCenterEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ManagementCenterEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ManagementCenterEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ManagementCenterEnum.class);
    }
}