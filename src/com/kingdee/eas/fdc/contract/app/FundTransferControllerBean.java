package com.kingdee.eas.fdc.contract.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.sql.Timestamp;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import java.math.BigDecimal;

import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.contract.FundTransferInfo;
import com.kingdee.eas.fdc.contract.TripCostInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.fdc.contract.FundTransferCollection;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.CasRecPayBillTypeEnum;
import com.kingdee.eas.fi.cas.IPaymentBillType;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillTypeCollection;
import com.kingdee.eas.fi.cas.PaymentBillTypeFactory;
import com.kingdee.eas.fi.cas.PaymentBillTypeInfo;
import com.kingdee.eas.fi.cas.SettlementStatusEnum;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class FundTransferControllerBean extends AbstractFundTransferControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.contract.app.FundTransferControllerBean");
    
protected void checkNameDup(Context ctx, FDCBillInfo billInfo)throws BOSException, EASBizException{
    	
    	
    }
protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
EASBizException {
// TODO Auto-generated method stub
	super._audit(ctx, billId);
	SelectorItemCollection sic=new SelectorItemCollection();
	sic.add("*");
	sic.add("transferBank.*");
	sic.add("curProject.fullOrgUnit.id");
	
	FundTransferInfo tripInfo=this.getFundTransferInfo(ctx, new ObjectUuidPK(billId),sic);
	PaymentBillInfo payment=new PaymentBillInfo();
	PaymentBillTypeInfo typeInfo = null;
	PaymentBillTypeCollection coll =null;
	EntityViewInfo ev = new EntityViewInfo();
	FilterInfo filter = new FilterInfo();
	ev.setFilter(filter);
	filter.getFilterItems().add(
		new FilterItemInfo("number", "999",
				CompareType.EQUALS));
	IPaymentBillType iPaymentBillType;
	try {
	iPaymentBillType = PaymentBillTypeFactory.getLocalInstance(ctx);			
	coll = iPaymentBillType.getPaymentBillTypeCollection(ev);
	} catch (BOSException e) {
	logger.error(e.getMessage()+"得到出纳系统的默认的收款类型!");
	}
	
	if (coll != null && !coll.isEmpty()) {
	typeInfo = coll.get(0);
	}
	
	
	payment.setPayBillType(typeInfo);
	payment.setSourceType(SourceTypeEnum.CASH);
	payment.setSourceSysType(SourceTypeEnum.CASH);
	//公司
	payment.setCompany(CompanyOrgUnitFactory.getLocalInstance(ctx).getCompanyOrgUnitInfo(new ObjectUuidPK(tripInfo.getCurProject().getFullOrgUnit().getId())));
	//业务日期
	payment.setBizDate(tripInfo.getBizDate());
	//汇率
	//收款金额
	payment.setActPayAmt(tripInfo.getAmount());
	
	payment.setCreator(tripInfo.getCreator());
	payment.setCreateTime(new Timestamp(System.currentTimeMillis()));
	payment.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
	payment.setLastUpdateUser(tripInfo.getLastUpdateUser());
	payment.setCU(tripInfo.getCU());
	
	payment.setNumber(getNumber(ctx,payment,tripInfo));
	
	payment.setActPayLocAmt(tripInfo.getAmount());
	payment.setCurrency(CurrencyFactory.getLocalInstance(ctx).getCurrencyCollection("select * from where name='人民币'").get(0));
	payment.setExchangeRate(new BigDecimal(1));
	
	/**
	* 设置非空字段
	*/
	payment.setPaymentBillType(CasRecPayBillTypeEnum.RealType);
	payment.setIsExchanged(false);
	payment.setIsInitializeBill(false);
	payment.setIsImport(false);
	payment.setFiVouchered(false);
	payment.setSettlementStatus(SettlementStatusEnum.UNSUBMIT);
	payment.setIsAppointVoucher(false);
	payment.setIsCoopBuild(false);
	//receivingBillInfo.setSourceType(SourceTypeEnum.CASH);
	
	//单据状态
	payment.setBillStatus(BillStatusEnum.SAVE);
	//原始单据id
	payment.setSourceBillId(tripInfo.getId().toString());
	payment.setPayeeName(tripInfo.getAccountCompany());
	if(tripInfo.getAccountCompany()!=null&&!tripInfo.getAccountCompany().trim().equals("")){
		payment.setUsage("资金划转，"+tripInfo.getAccountCompany()+"，金额"+tripInfo.getAmount()+"元；");
	}else{
		payment.setUsage("资金划转，金额"+tripInfo.getAmount()+"元；");
	}
	if(tripInfo.getTransferBank()!=null){
		payment.setPayeeBank(tripInfo.getTransferBank().getName());
	}
	payment.setPayeeAccountBank(tripInfo.getTransferAccount());
	
	SelectorItemCollection sels = new SelectorItemCollection();
	sels.add("asstActGpDt.asstActType.*");
	sels.add("asstActGpDt.*");
	
	
	PaymentBillEntryInfo payBillEntryInfo = new PaymentBillEntryInfo();
	payBillEntryInfo.setActualAmt(tripInfo.getAmount());
	if(tripInfo.getAccountCompany()!=null&&!tripInfo.getAccountCompany().trim().equals("")){
		payBillEntryInfo.setRemark("资金划转，"+tripInfo.getAccountCompany()+"，金额"+tripInfo.getAmount()+"元；");
	}else{
		payBillEntryInfo.setRemark("资金划转，金额"+tripInfo.getAmount()+"元；");
	}
	
	payBillEntryInfo.setSeq(0);
	payBillEntryInfo.setCostCenter(CostCenterOrgUnitFactory.getLocalInstance(ctx).getCostCenterOrgUnitInfo(new ObjectUuidPK(tripInfo.getCurProject().getFullOrgUnit().getId())));
	payment.getEntries().add(payBillEntryInfo);
	
	PaymentBillFactory.getLocalInstance(ctx).save(payment);
	}
	@Override
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
	EASBizException {
	// TODO Auto-generated method stub
	super._unAudit(ctx, billId);
	if(PaymentBillFactory.getLocalInstance(ctx).exists("select id from where sourceBillId='"+billId+"'")){
	throw new EASBizException(new NumericExceptionSubItem("101","已生成出纳付款单，请先删除出纳付款单后再进行反审批操作！"));
	}
	}
	private String getNumber(Context ctx,PaymentBillInfo  receivingBillInfo,FundTransferInfo revGatherInfo) throws BOSException, EASBizException
	{		
	ICodingRuleManager iCodingRuleManager = null;
	if(ctx!=null)	iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	else 	iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
	
	OrgUnitInfo orgUnit = null;
	if(ctx!=null) {
	orgUnit = ContextUtil.getCurrentSaleUnit(ctx);
	if(orgUnit==null) orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
	}else {
	orgUnit = SysContext.getSysContext().getCurrentSaleUnit();
	if(orgUnit==null) orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
	}
	
	String retNumber = iCodingRuleManager.getNumber(receivingBillInfo, orgUnit.getId().toString());
	if(retNumber!=null && !"".equals(retNumber)){
	receivingBillInfo.setNumber(retNumber);
	}else
	{
	retNumber = revGatherInfo.getNumber()+"_toPayment";
	}
	
	return retNumber;
}
}