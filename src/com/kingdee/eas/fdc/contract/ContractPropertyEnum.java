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
public class ContractPropertyEnum extends StringEnum
{
    public static final String DIRECT_VALUE = "DIRECT";//alias=直接合同
    public static final String THREE_PARTY_VALUE = "THREE_PARTY";//alias=三方合同
    public static final String SUPPLY_VALUE = "SUPPLY";//alias=补充合同
    public static final String STRATEGY_VALUE = "STRATEGY";//alias=多甲方协议
    public static final String AGREEMENT_VALUE = "AGREEMENT";//alias=集采协议

    public static final ContractPropertyEnum DIRECT = new ContractPropertyEnum("DIRECT", DIRECT_VALUE);
    public static final ContractPropertyEnum THREE_PARTY = new ContractPropertyEnum("THREE_PARTY", THREE_PARTY_VALUE);
    public static final ContractPropertyEnum SUPPLY = new ContractPropertyEnum("SUPPLY", SUPPLY_VALUE);
    public static final ContractPropertyEnum STRATEGY = new ContractPropertyEnum("STRATEGY", STRATEGY_VALUE);
    public static final ContractPropertyEnum AGREEMENT = new ContractPropertyEnum("AGREEMENT", AGREEMENT_VALUE);

    /**
     * construct function
     * @param String contractPropertyEnum
     */
    private ContractPropertyEnum(String name, String contractPropertyEnum)
    {
        super(name, contractPropertyEnum);
    }
    
    /**
     * getEnum function
     * @param String arguments
     */
    public static ContractPropertyEnum getEnum(String contractPropertyEnum)
    {
        return (ContractPropertyEnum)getEnum(ContractPropertyEnum.class, contractPropertyEnum);
    }

    /**
     * getEnumMap function
     */
    public static Map getEnumMap()
    {
        return getEnumMap(ContractPropertyEnum.class);
    }

    /**
     * getEnumList function
     */
    public static List getEnumList()
    {
         return getEnumList(ContractPropertyEnum.class);
    }
    
    /**
     * getIterator function
     */
    public static Iterator iterator()
    {
         return iterator(ContractPropertyEnum.class);
    }
}