package com.kingdee.eas.fdc.tenancy;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractInvoiceBillInfo extends com.kingdee.eas.fdc.tenancy.TenBillBaseInfo implements Serializable 
{
    public AbstractInvoiceBillInfo()
    {
        this("id");
    }
    protected AbstractInvoiceBillInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.tenancy.InvoiceBillEntryCollection());
    }
    /**
     * Object: K票据管理 's 租赁合同 property 
     */
    public com.kingdee.eas.fdc.tenancy.TenancyBillInfo getTenancyBill()
    {
        return (com.kingdee.eas.fdc.tenancy.TenancyBillInfo)get("tenancyBill");
    }
    public void setTenancyBill(com.kingdee.eas.fdc.tenancy.TenancyBillInfo item)
    {
        put("tenancyBill", item);
    }
    /**
     * Object: K票据管理 's 分录 property 
     */
    public com.kingdee.eas.fdc.tenancy.InvoiceBillEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.tenancy.InvoiceBillEntryCollection)get("entry");
    }
    /**
     * Object:K票据管理's 票据类型property 
     */
    public com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum getType()
    {
        return com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum.getEnum(getString("type"));
    }
    public void setType(com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum item)
    {
		if (item != null) {
        setString("type", item.getValue());
		}
    }
    /**
     * Object:K票据管理's 是否生成凭证property 
     */
    public boolean isFiVouchered()
    {
        return getBoolean("fiVouchered");
    }
    public void setFiVouchered(boolean item)
    {
        setBoolean("fiVouchered", item);
    }
    /**
     * Object:K票据管理's 票据金额property 
     */
    public java.math.BigDecimal getAmount()
    {
        return getBigDecimal("amount");
    }
    public void setAmount(java.math.BigDecimal item)
    {
        setBigDecimal("amount", item);
    }
    /**
     * Object: K票据管理 's 客户 property 
     */
    public com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo getFdcCustomer()
    {
        return (com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo)get("fdcCustomer");
    }
    public void setFdcCustomer(com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo item)
    {
        put("fdcCustomer", item);
    }
    /**
     * Object:K票据管理's 销售编号property 
     */
    public String getSaleNumber()
    {
        return getString("saleNumber");
    }
    public void setSaleNumber(String item)
    {
        setString("saleNumber", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("F345578B");
    }
}