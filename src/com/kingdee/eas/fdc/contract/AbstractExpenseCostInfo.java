package com.kingdee.eas.fdc.contract;

import java.io.Serializable;
import com.kingdee.bos.dao.AbstractObjectValue;
import java.util.Locale;
import com.kingdee.util.TypeConversionUtils;
import com.kingdee.bos.util.BOSObjectType;


public class AbstractExpenseCostInfo extends com.kingdee.eas.fdc.basedata.FDCBillInfo implements Serializable 
{
    public AbstractExpenseCostInfo()
    {
        this("id");
    }
    protected AbstractExpenseCostInfo(String pkField)
    {
        super(pkField);
        put("entry", new com.kingdee.eas.fdc.contract.ExpenseCostEntryCollection());
    }
    /**
     * Object: 费用报销 's 工程项目 property 
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
     * Object: 费用报销 's 合同类型 property 
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
     * Object:费用报销's 费用类型property 
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
     * Object:费用报销's 备注内容property 
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
     * Object: 费用报销 's 费用申请 property 
     */
    public com.kingdee.eas.fdc.contract.ExpenseApplyInfo getExpenseApply()
    {
        return (com.kingdee.eas.fdc.contract.ExpenseApplyInfo)get("expenseApply");
    }
    public void setExpenseApply(com.kingdee.eas.fdc.contract.ExpenseApplyInfo item)
    {
        put("expenseApply", item);
    }
    /**
     * Object: 费用报销 's 报销人 property 
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
     * Object: 费用报销 's 报销部门 property 
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
     * Object: 费用报销 's 分录 property 
     */
    public com.kingdee.eas.fdc.contract.ExpenseCostEntryCollection getEntry()
    {
        return (com.kingdee.eas.fdc.contract.ExpenseCostEntryCollection)get("entry");
    }
    /**
     * Object: 费用报销 's 收款单位 property 
     */
    public com.kingdee.eas.basedata.master.cssp.SupplierInfo getSupplier()
    {
        return (com.kingdee.eas.basedata.master.cssp.SupplierInfo)get("supplier");
    }
    public void setSupplier(com.kingdee.eas.basedata.master.cssp.SupplierInfo item)
    {
        put("supplier", item);
    }
    /**
     * Object: 费用报销 's 费用流程类型 property 
     */
    public com.kingdee.eas.fdc.contract.ContractWFTypeInfo getContractWFType()
    {
        return (com.kingdee.eas.fdc.contract.ContractWFTypeInfo)get("contractWFType");
    }
    public void setContractWFType(com.kingdee.eas.fdc.contract.ContractWFTypeInfo item)
    {
        put("contractWFType", item);
    }
    /**
     * Object: 费用报销 's 收款银行 property 
     */
    public com.kingdee.eas.fdc.contract.BankNumInfo getBankNum()
    {
        return (com.kingdee.eas.fdc.contract.BankNumInfo)get("bankNum");
    }
    public void setBankNum(com.kingdee.eas.fdc.contract.BankNumInfo item)
    {
        put("bankNum", item);
    }
    /**
     * Object:费用报销's 收款账号property 
     */
    public String getAccountNumber()
    {
        return getString("accountNumber");
    }
    public void setAccountNumber(String item)
    {
        setString("accountNumber", item);
    }
    /**
     * Object:费用报销's 收款银行property 
     */
    public String getReceiveBank()
    {
        return getString("receiveBank");
    }
    public void setReceiveBank(String item)
    {
        setString("receiveBank", item);
    }
    public BOSObjectType getBOSType()
    {
        return new BOSObjectType("9A285B2A");
    }
}