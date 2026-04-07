package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractPurchaseDocumentInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractPurchaseDocumentInfo()
    {
        this("id");
    }
    protected AbstractPurchaseDocumentInfo(String pkField)
    {
        super(pkField);
    }
    /**
     * Object: 招标文件 's 工程项目 property 
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
     * Object: 招标文件 's 采购类别 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getInviteType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("inviteType");
    }
    public void setInviteType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("inviteType", item);
    }
    /**
     * Object: 招标文件 's 采购方式 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWFTypeInfo getPurchaseMode()
    {
        return (com.kingdee.eas.fdc.contract.ContractWFTypeInfo)get("purchaseMode");
    }
    public void setPurchaseMode(com.kingdee.eas.fdc.contract.ContractWFTypeInfo item)
    {
        put("purchaseMode", item);
    }
    /**
     * Object: 招标文件 's 需求部门 property 
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
     * Object: 招标文件 's 责任人 property 
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
     * Object:招标文件's 招标文件内容property 
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
     * Object:招标文件's 采购归属property 
     */
    public com.kingdee.eas.fdc.contract.PurchaseBelongEnum getBelong()
    {
        return com.kingdee.eas.fdc.contract.PurchaseBelongEnum.getEnum(getString("belong"));
    }
    public void setBelong(com.kingdee.eas.fdc.contract.PurchaseBelongEnum item)
    {
		if (item != null) {
        setString("belong", item.getValue());
		}
    }
    /**
     * Object:招标文件's 采购方式property 
     */
    public com.kingdee.eas.fdc.contract.PurchaseTypeEnum getPurchaseType()
    {
        return com.kingdee.eas.fdc.contract.PurchaseTypeEnum.getEnum(getString("purchaseType"));
    }
    public void setPurchaseType(com.kingdee.eas.fdc.contract.PurchaseTypeEnum item)
    {
		if (item != null) {
        setString("purchaseType", item.getValue());
		}
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("A3FE5577");
    }
}