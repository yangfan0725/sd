package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExpenseApplyInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractExpenseApplyInfo()
    {
        this("id");
    }
    protected AbstractExpenseApplyInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ExpenseApplyEntryCollection());
    }
    /**
     * Object: 费用申请 's 工程项目 property 
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
     * Object: 费用申请 's 合同类型 property 
     */
    public com.kingdee.eas.fdc.basedata.ContractTypeInfo getContractType()
    {
        return (com.kingdee.eas.fdc.basedata.ContractTypeInfo)get("contractType");
    }
    public void setContractType(com.kingdee.eas.fdc.basedata.ContractTypeInfo item)
    {
        put("contractType", item);
    }
    /**
     * Object:费用申请's 费用类型property 
     */
    public com.kingdee.eas.fdc.contract.ExpenseTypeEnum getExpenseType()
    {
        return com.kingdee.eas.fdc.contract.ExpenseTypeEnum.getEnum(getString("expenseType"));
    }
    public void setExpenseType(com.kingdee.eas.fdc.contract.ExpenseTypeEnum item)
    {
		if (item != null) {
        setString("expenseType", item.getValue());
		}
    }
    /**
     * Object:费用申请's 备注内容property 
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
     * Object: 费用申请 's 申请人 property 
     */
    public com.kingdee.eas.basedata.person.PersonInfo getPerson()
    {
        return (com.kingdee.eas.basedata.person.PersonInfo)get("person");
    }
    public void setPerson(com.kingdee.eas.basedata.person.PersonInfo item)
    {
        put("person", item);
    }
    /**
     * Object: 费用申请 's 申请部门 property 
     */
    public com.kingdee.eas.basedata.org.AdminOrgUnitInfo getDept()
    {
        return (com.kingdee.eas.basedata.org.AdminOrgUnitInfo)get("dept");
    }
    public void setDept(com.kingdee.eas.basedata.org.AdminOrgUnitInfo item)
    {
        put("dept", item);
    }
    /**
     * Object: 费用申请 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.ExpenseApplyEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ExpenseApplyEntryCollection)get("entry");
    }
    /**
     * Object: 费用申请 's 费用流程类型 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWFTypeInfo getContractWFType()
    {
        return (com.kingdee.eas.fdc.contract.ContractWFTypeInfo)get("contractWFType");
    }
    public void setContractWFType(com.kingdee.eas.fdc.contract.ContractWFTypeInfo item)
    {
        put("contractWFType", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("AAC743B1");
    }
}