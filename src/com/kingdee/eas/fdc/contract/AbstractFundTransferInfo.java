package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractFundTransferInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractFundTransferInfo()
    {
        this("id");
    }
    protected AbstractFundTransferInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 资金划转 's 工程项目 property 
     */
    public com.kingdee.eas.fdc.basedata.CurProjectInfo getCurProject()
    {
        return (com.kingdee.eas.fdc.basedata.CurProjectInfo)get("curProject");
    }
    public void setCurProject(com.kingdee.eas.fdc.basedata.CurProjectInfo item)
    {
        put("curProject", item);
    }
    /**
     * Object: 资金划转 's 申请部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getNeedDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("needDept");
    }
    public void setNeedDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("needDept", item);
    }
    /**
     * Object: 资金划转 's 经办人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getRespPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("respPerson");
    }
    public void setRespPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("respPerson", item);
    }
    /**
     * Object:资金划转's 用途property 
     */
    public String getPurpose()
    {
        return getString("purpose");
    }
    public void setPurpose(String item)
    {
        setString("purpose", item);
    }
    /**
     * Object:资金划转's 转出方式property 
     */
    public com.kingdee.eas.fdc.contract.TransferTypeEnum getTransferType()
    {
        return com.kingdee.eas.fdc.contract.TransferTypeEnum.getEnum(getString("transferType"));
    }
    public void setTransferType(com.kingdee.eas.fdc.contract.TransferTypeEnum item)
    {
		if (item != null) {
        setString("transferType", item.getValue());
		}
    }
    /**
     * Object: 资金划转 's 银行账号 property 
     */
    public com.kingdee.eas.basedata.assistant.AccountBankInfo getAccountBank()
    {
        return (com.kingdee.eas.basedata.assistant.AccountBankInfo)get("accountBank");
    }
    public void setAccountBank(com.kingdee.eas.basedata.assistant.AccountBankInfo item)
    {
        put("accountBank", item);
    }
    /**
     * Object:资金划转's 银行账号property 
     */
    public String getTransferAccount()
    {
        return getString("transferAccount");
    }
    public void setTransferAccount(String item)
    {
        setString("transferAccount", item);
    }
    /**
     * Object:资金划转's 账号名称property 
     */
    public String getAccountName()
    {
        return getString("accountName");
    }
    public void setAccountName(String item)
    {
        setString("accountName", item);
    }
    /**
     * Object:资金划转's 收款单位property 
     */
    public String getAccountCompany()
    {
        return getString("accountCompany");
    }
    public void setAccountCompany(String item)
    {
        setString("accountCompany", item);
    }
    /**
     * Object: 资金划转 's 开户银行 property 
     */
    public com.kingdee.eas.fdc.contract.BankNumInfo getTransferBank()
    {
        return (com.kingdee.eas.fdc.contract.BankNumInfo)get("transferBank");
    }
    public void setTransferBank(com.kingdee.eas.fdc.contract.BankNumInfo item)
    {
        put("transferBank", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("B0D6AB8B");
    }
}