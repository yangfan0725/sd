package com.kingdee.eas.fdc.finance.app.voucher;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.master.account.AccountViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.BeforeAccountViewInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountWithAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.ICostAccountWithAccount;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.CompensationOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillEntryFactory;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.GuerdonOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAConfmOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.PartAOfPayReqBillFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentNoCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitException;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;

abstract class AbstractFDCVoucherEntryCreator implements IFDCVoucherEntryCreator{
	static final String PARTAOFBILLS = "partAOfBills_";
	protected Context ctx=null;
	static final String DEDUCTBILLS = "deductBills_";
	static final String PARTACONFIRMBILLS = "partAConfirmBills_";
	static final String COMPENSATIONBILLS = "compensationBills_";
	static final String GUERDONBILLS = "guerdonBills_";
	/***
	 * ????????????????????
	 */
	protected BeforeAccountViewInfo beforeAccountViewInfo = null;
	/***
	 * ????????????
	 */
	protected String financial = "";
	/***
	 * ??????????????????????
	 */
	protected boolean financialExtend = false;
/*	*//***
	 * ????????????????bills
	 *//*
	protected Map hasSynchBills = null;

*/	/***
	 * ??????????????????????
	 * ??????????
	 * ????????????????????????????????
	 * [??????????????????????????]
	 */
	protected Map costAccountWithAccountMap = null; 
	/***
	 * ??????????????????Map
	 */
//	protected Map deductAccountMap = null; 
/*	*//***
	 * ????????????????????????idSet
	 *//*
	protected Set needUpdatePaymentSplitSet = null;
	*//***
	 * ??????????????????????????idSet
	 *//*
	protected Set needUpdatePaymentNoCostSplitSet = null;*/

	public AbstractFDCVoucherEntryCreator(Context ctx){
		this.ctx=ctx;
	}
	protected Context getCtx(){
		return ctx;
	}
	private IFDCCostVoucherHandler costHandler=null;
	private IFDCPayVoucherHandler payHandler=null;
	public void setCostHandler(IFDCCostVoucherHandler costHandler){
		this.costHandler=costHandler;
	}
	public void setPayHandler(IFDCPayVoucherHandler payHandler){
		this.payHandler=payHandler;
	}
	private IFDCCostVoucherHandler getCostHandler(){
		return this.costHandler;
	}
	
	private IFDCPayVoucherHandler getPayHandler(){
		return this.payHandler;
	}
	
	public void createEntrys(Map param) throws BOSException, EASBizException{
		Map dataMap = init(param);
		IObjectCollection costEntrys=createCostEntrys(dataMap);
		IObjectCollection payEntry=createPayEntrys(dataMap);
		save(dataMap,costEntrys,payEntry);
		afterCreateEntrys(param);
	}

	public void afterCreateEntrys(Map param){
		//????????
		if(param!=null){
			param.clear();
		}
		//????????
		clearCache();
	}
	
	private final static String CACHEKEY="AbstractFDCVoucherEntryCreator_cache";
	public Map getCacheMap(){
		Context ctx=getCtx();
		if(ctx==null){
			throw new NullPointerException("ctx can't be null");
		}
		
		if(ctx.get(CACHEKEY)==null){
			ctx.put(CACHEKEY, new HashMap());
		}
		return (Map)ctx.get(CACHEKEY);
	}
	public void clearCache(){
		Context ctx=getCtx();
		if(ctx!=null&&ctx.get(CACHEKEY)!=null){
			ctx.remove(CACHEKEY);
		}
	}
	/**
	 * ??????????,
	 * ????????????????????????????????????????,
	 * ????????????????????????????????????,
	 * ????????????????????????????????????????????????????
	 * ????????????????????????????????
	 * @param param ??????????????????????????????
	 * @throws BOSException 
	 */
	protected Map init(Map param) throws BOSException{
		Map dataMap=new HashMap();
		dataMap.putAll(param);
		//??????????????????????????????????????????????????????????????,????????????????????,
		//??????????????????????????????
		
		/***
		 * ??????????
		 */
		financial = (String)param.get("financial");
		if(param.get("financialExtend") instanceof Boolean)
			financialExtend = ((Boolean)param.get("financialExtend")).booleanValue();
		else
			financialExtend = false;
		/***
		 * ????????????bills
		 */
//		hasSynchBills = (Map)param.get("hasSynchBills");
		Map paymentBills = (HashMap)dataMap.get("paymentBills");
//		dataMap.put("paymentBills",paymentBills);
		Set paymentBillIds = paymentBills.keySet();
		if(!isWorkLoadVoucher(param)){
			initDeductBills(dataMap, paymentBillIds);
			initPartAConfirmBills(dataMap,paymentBillIds);
			initCompensationBills(dataMap, paymentBillIds);
			initGuerdonBills(dataMap, paymentBillIds);
		}
		beforeAccountViewInfo = (BeforeAccountViewInfo)dataMap.get("beforeAccountViewInfo");
		
//		deductAccountMap = (Map)param.get("deductAccountMap");
		costAccountWithAccountMap = (Map)param.get("costAccountWithAccountMap");
		initSplitInfos(dataMap, paymentBillIds);
		
		//????????????????
		boolean isSimpleInvoice = false;
		boolean isInvoiceOffset = false;
		boolean isPaymentSplit = false;
		if(param.get("simpleInvoice") instanceof Boolean)
			isSimpleInvoice = ((Boolean)param.get("simpleInvoice")).booleanValue();
		if(param.get("isPaymentSplit") instanceof Boolean)
			isPaymentSplit = ((Boolean)param.get("isPaymentSplit")).booleanValue();
		if(param.get("invoiceOffset") instanceof Boolean)
			isInvoiceOffset = ((Boolean)param.get("invoiceOffset")).booleanValue();
		
		if(isSimpleInvoice){
			if(isPaymentSplit){
				initSplitBillInfos(dataMap, paymentBillIds);
			}else if(!isPaymentSplit&&isInvoiceOffset){
				initBillInfos(dataMap, paymentBillIds);
			}
		}
		initNeedUpdateSplitSet(dataMap);
		return dataMap;
	}
	
	protected void initSplitBillInfos(Map dataMap, Set paymentBillIds) throws BOSException {
		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		Map contractIds = new HashMap();
		Map payedAmtMap = new HashMap();
		Map invoiceAmtMap = new HashMap();
		
		//1.????????????????ID
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", paymentBillIds, CompareType.INCLUDE));
		view.getSelector().add("isConWithoutText");
		view.getSelector().add("contractBill");
		view.getSelector().add("conWithoutText");
		view.getSelector().add("createTime");
		view.getSelector().add("paymentBill");
		view.getSelector().add("paymentBill.createTime");
		SorterItemInfo sort = new SorterItemInfo("paymentBill.createTime");
		sort.setSortType(SortType.ASCEND);
		view.getSorter().add(sort);
		
		PaymentSplitCollection splits = PaymentSplitFactory.getLocalInstance(ctx).getPaymentSplitCollection(view);
		//????????????,????????????????
		String lastID = "";
		if(splits!=null&&splits.get(0)!=null&&splits.get(0).getPaymentBill()!=null&&splits.get(0).getPaymentBill().getId()!=null){
			lastID = splits.get(0).getPaymentBill().getId().toString();
		}
		for(Iterator iter=splits.iterator();iter.hasNext();){
			PaymentSplitInfo split = (PaymentSplitInfo)iter.next();
			boolean isNoText = split.isIsConWithoutText();
			if(isNoText){
				contractIds.put(split.getConWithoutText().getId().toString(), Boolean.TRUE);
			}else{
				contractIds.put(split.getContractBill().getId().toString(), Boolean.TRUE);
			}
		}
		
		PaymentNoCostSplitCollection noCostSplits = PaymentNoCostSplitFactory.getLocalInstance(ctx).getPaymentNoCostSplitCollection(view);
		//????????????
		String lastID2 = "";
		if(splits!=null&&noCostSplits.get(0)!=null&&noCostSplits.get(0).getPaymentBill()!=null&&noCostSplits.get(0).getPaymentBill().getId()!=null){
			lastID2 = noCostSplits.get(0).getPaymentBill().getId().toString();
		}
		for(Iterator iter=noCostSplits.iterator();iter.hasNext();){
			PaymentNoCostSplitInfo split = (PaymentNoCostSplitInfo)iter.next();
			boolean isNoText = split.isIsConWithoutText();
			if(isNoText){
				contractIds.put(split.getConWithoutText().getId().toString(), Boolean.TRUE);
			}else{
				contractIds.put(split.getContractBill().getId().toString(), Boolean.TRUE);
			}
		}
		
		//2.????????????????
		builder.appendSql("select fcontractbillid,fcurprojectid,faccountid,sum(fpayedAmt) fpayedAmt,sum(finvoiceAmt) finvoiceAmt from ( \n");
		builder.appendSql("select isnull(split.fcontractbillid,split.fconwithouttextid) as fcontractbillid ,acct.fcurproject as fcurprojectid,entry.fcostaccountid as faccountid,entry.fpayedamt as fpayedAmt,entry.finvoiceAmt as finvoiceAmt from  t_fnc_paymentSplitEntry entry \n");
		builder.appendSql(" inner join t_fnc_paymentSplit split on entry.fparentid=split.fid \n");
		builder.appendSql(" inner join t_cas_paymentBill bill on bill.fid=split.fpaymentbillid \n");	
		builder.appendSql(" inner join t_fdc_costaccount acct on acct.fid=entry.fcostaccountid \n");
		builder.appendSql(" where ( \n");
		builder.appendParam("split.fcontractbillid",contractIds.keySet().toArray());
		builder.appendSql(" or ");
		builder.appendParam("split.fconwithouttextid", contractIds.keySet().toArray());
		builder.appendSql(" )");
		builder.appendSql(" and split.fpaymentbillid not in ( \n");
		builder.appendParam(paymentBillIds.toArray());
		builder.appendSql(" )");
		builder.appendSql(" and entry.fcostaccountid is not null \n");
		builder.appendSql(" and entry.fisleaf=1 \n");
		builder.appendSql(" and split.fisinvalid=0 \n");
		builder.appendSql(" and split.fcreatetime < (select fcreatetime from t_fnc_paymentsplit where fpaymentbillid=?) \n");
		builder.addParam(lastID);
		
		builder.appendSql(" union all \n");
		
		builder.appendSql("select isnull(split.fcontractbillid,split.fconwithouttextid) as fcontractbillid,entry.fcurprojectid as fcurprojectid,entry.faccountviewid as faccountid,entry.fpayedamt as fpayedAmt,entry.finvoiceAmt as finvoiceAmt  from  t_fnc_paymentNoCostSplitEntry entry \n");
		builder.appendSql(" inner join t_fnc_paymentNoCostSplit split on entry.fparentid=split.fid \n");
		builder.appendSql(" inner join t_cas_paymentBill bill on bill.fid=split.fpaymentbillid \n");
		builder.appendSql(" where ( \n");
		builder.appendParam("split.fcontractbillid",contractIds.keySet().toArray());
		builder.appendSql(" or ");
		builder.appendParam("split.fconwithouttextid", contractIds.keySet().toArray());
		builder.appendSql(" )");
		builder.appendSql(" and split.fpaymentbillid not in ( \n");
		builder.appendParam(paymentBillIds.toArray());
		builder.appendSql(" )");
		builder.appendSql(" and entry.faccountviewid is not null \n");
		builder.appendSql(" and entry.fisleaf=1 \n");
		builder.appendSql(" and split.fisinvalid=0 \n");
		builder.appendSql(" and split.fcreatetime < (select fcreatetime from t_fnc_paymentNoCostSplit where fpaymentbillid=?) \n");//???????????? 
		builder.addParam(lastID2);
		
		builder.appendSql(" )t group by t.fcontractbillid,t.fcurprojectid,t.faccountid \n");
		IRowSet rs = builder.executeQuery();
		try {
			while(rs.next()){
				String key = rs.getString("fcontractbillid")+rs.getString("fcurprojectid")+rs.getString("faccountid");
				payedAmtMap.put(key, rs.getBigDecimal("fpayedAmt"));
				invoiceAmtMap.put(key, rs.getBigDecimal("finvoiceAmt"));
			}
		} catch (SQLException e) {
			throw new BOSException(e);
		} 
		dataMap.put("payedAmtMap", payedAmtMap);
		dataMap.put("invoiceAmtMap", invoiceAmtMap);
	}
	
	/**
	 * ??????????
	 * 		1.????????
	 * 		2.????????????????
	 * 		3.????????
	 * 		4.????????????????,????1,2,3????????????????????????????????????
	 * 
	 * 
	 * ??   ??????????????????????????????????????????????  
	 */
	private void initBillInfos(Map dataMap, Set paymentBillIds) throws BOSException {

		//????????????????????????????????
		Map invoiceMap = new HashMap();
		//????????????????????????????????ID
		Set voucheredReqIdsSet = new HashSet();

		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
		builder.appendSql("select distinct(fcontractbillid) from t_cas_paymentbill where ");
		builder.appendParam("fid", paymentBillIds.toArray());
		IRowSet rs = builder.executeQuery();
		Set conIds = new HashSet();
		try{
			while(rs.next()){
				conIds.add(rs.getString("fcontractbillid"));
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		builder.clear();
		builder.appendSql("select r.fid,r.finvoiceamt,r.fcontractid from t_con_payrequestbill r ");
		builder.appendSql("inner join t_cas_paymentbill p on p.ffdcpayreqid=r.fid ");
		builder.appendSql("where ");
		builder.appendParam("p.fcontractbillid", conIds.toArray());
		builder.appendSql(" and p.ffivouchered=1 ");
		rs = builder.executeQuery();
		try {
			while(rs.next()){
				String key = rs.getString("fcontractid");
				if(invoiceMap.containsKey(key)){
					BigDecimal amount = (BigDecimal)invoiceMap.get(key);
					amount = FDCHelper.add(amount,rs.getBigDecimal("finvoiceamt"));
					invoiceMap.put(key, amount);
				}else{
					invoiceMap.put(key, rs.getBigDecimal("finvoiceamt"));
				}
				voucheredReqIdsSet.add(rs.getString("fid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//????????????????????????
		builder.clear();
		builder.appendSql("select r.fid,r.finvoiceamt,r.fcontractid from t_con_payrequestbill r ");
		builder.appendSql("inner join t_cas_paymentbill p on p.ffdcpayreqid=r.fid ");
		builder.appendSql("where ");
		builder.appendParam("p.fid", paymentBillIds.toArray());
		rs=builder.executeQuery();
		try{
			while(rs.next()){
				String key = rs.getString("fid");
				if(voucheredReqIdsSet.contains(key)){
					continue;//??????????????????
				}
				if(invoiceMap.containsKey(key)){
					BigDecimal amount = (BigDecimal)invoiceMap.get(key);
					amount = FDCHelper.add(amount,rs.getBigDecimal("finvoiceamt"));
				}else{
					invoiceMap.put(key, rs.getBigDecimal("finvoiceamt"));
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		EntityViewInfo view;
		FilterInfo filter;
		view = new EntityViewInfo();
		filter = new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("id", paymentBillIds, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("contractBillId", conIds, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("fivouchered", Boolean.TRUE));
		filter.setMaskString("#0 or (#1 and #2)");//
		view.getSelector().add("id");
		view.getSelector().add("localAmt");
		view.getSelector().add("exchangeRate");
		view.getSelector().add("curProject");
		view.getSelector().add("contractBillId");
		view.getSelector().add("fdcPayReqID");
		view.getSelector().add("fivouchered");
		view.getSelector().add("actFdcPayeeName");
		SorterItemInfo sorter = new SorterItemInfo("createTime");
    	sorter.setSortType(SortType.ASCEND);
    	view.getSorter().add(sorter);
		IObjectCollection colls  = PaymentBillFactory.getLocalInstance(ctx).getCollection(view);
		
		
		Map totalMap = new HashMap();//TODO ????????
		Object[] obj= new Object[colls.size()];
		int index =0;
		for(Iterator iter=colls.iterator();iter.hasNext();){
			PaymentBillInfo info = (PaymentBillInfo)iter.next();
			String payReqId = info.getFdcPayReqID();
			String conId = info.getContractBillId();
			
//			BigDecimal totalPay = FDCHelper.ZERO;// ????????????????????????????
			BigDecimal invoiceAmt = (BigDecimal)invoiceMap.get(payReqId);
			
			//????????=?????????????????????????? + ????????????????????
			info.put("totalInvoice", FDCHelper.add(totalMap.get(conId+"inv"), invoiceMap.get(conId)));
			//????????
			info.put("invoiceAmt", invoiceAmt);
			//????????????????????(????????????????????????????),??????????????????????????????
			totalMap.put(conId+"inv",FDCHelper.add(invoiceAmt,totalMap.get(conId+"inv")));
			
			
			//????????????????
			info.put("totalPay", totalMap.get(conId+"pay"));
			totalMap.put(conId+"pay",FDCHelper.add(info.getLocalAmt(),totalMap.get(conId+"pay")));
			
			if(info.isFiVouchered()){
				obj[index]=info;
				index++;
//				colls.removeObject(info);
			}
		}
		for(int i=0;i<obj.length;i++){
			Object o = obj[i];
			if(o!=null){
				colls.removeObject((IObjectValue)o);
			}
		}
		dataMap.put("payInfos", colls);
		
	}
	
	private void initNeedUpdateSplitSet(Map dataMap){
		Set needUpdatePaymentSplitSet = new HashSet();
		Set needUpdatePaymentNoCostSplitSet = new HashSet();
		IObjectCollection paySplitInfos=(IObjectCollection)dataMap.get("paySplitInfos");
		for(Iterator iter=paySplitInfos.iterator();iter.hasNext();){
			IObjectValue splitInfo=(IObjectValue)iter.next();
			if(splitInfo instanceof PaymentSplitInfo){
				PaymentSplitInfo paymentSplitInfo = (PaymentSplitInfo)splitInfo;
				needUpdatePaymentSplitSet.add(paymentSplitInfo.getId().toString());
			}
			else if(splitInfo instanceof PaymentNoCostSplitInfo){
				PaymentNoCostSplitInfo paymentNoCostSplitInfo = (PaymentNoCostSplitInfo)splitInfo;
				needUpdatePaymentNoCostSplitSet.add(paymentNoCostSplitInfo.getId().toString());
			}
		}
		dataMap.put("needUpdatePaymentSplitSet", needUpdatePaymentSplitSet);
		dataMap.put("needUpdatePaymentNoCostSplitSet", needUpdatePaymentNoCostSplitSet);
	}
	/**
	 * 
	 * ????????????
	 * put(paySplitInfos)
	 * ????????????????????
	 * ??????????????????????
	 * ??????????????????ID????????????????????
	 * ????????????????????????
	 * ????instanceof
	 * 
	 * //TODO ??????????????????????		
	 * @param dataMap
	 * @param paymentBillIds
	 * @throws BOSException
	 */
	private void initSplitInfos(Map dataMap, Set paymentBillIds) throws BOSException {
		if(isWorkLoadVoucher(dataMap)){
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("workLoadConfirmBill.id", paymentBillIds, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			view.setFilter(filter);

			// isCost
			view.getSelector().add("*");
			view.getSelector().add("entrys.*");
			view.getSelector().add("entrys.product.*");
			view.getSelector().add("entrys.accountView.*");
			view.getSelector().add("workLoadConfirmBill.hasSettled");
			view.getSelector().add("workLoadConfirmBill.contractBill.id");
			view.getSelector().add("workLoadConfirmBill.contractBill.partB.id");
			view.getSelector().add("workLoadConfirmBill.id");
			view.getSelector().add("entrys.costAccount.id");
			view.getSelector().add("entrys.costAccount.number");
			view.getSelector().add("entrys.costAccount.longNumber");
			view.getSelector().add("entrys.costAccount.flag");
			view.getSelector().add("entrys.costAccount.curProject.id");
			view.getSelector().add("entrys.costAccount.curProject.name");
			

			IObjectCollection paymentSplitInfos = PaymentSplitFactory.getLocalInstance(ctx).getCollection(view);
			dataMap.put("paySplitInfos", paymentSplitInfos);
		} else {
			EntityViewInfo view;
			FilterInfo filter;
			view = new EntityViewInfo();
			filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("paymentBill.id", paymentBillIds, CompareType.INCLUDE));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE, CompareType.NOTEQUALS));
			view.setFilter(filter);

			// isCost
			view.getSelector().add("*");
			view.getSelector().add("entrys.*");
			view.getSelector().add("entrys.product.*");
			view.getSelector().add("entrys.accountView.*");
			view.getSelector().add("paymentBill.id");
			view.getSelector().add("paymentBill.actPayAmt");
			view.getSelector().add("paymentBill.payerAccount.id");
			view.getSelector().add("paymentBill.actFdcPayeeName.id");
			view.getSelector().add("paymentBill.fdcPayType.payType.id");
			view.getSelector().add("paymentBill.actPayLocAmt");
			view.getSelector().add("paymentBill.prjPayEntry.advance");
			view.getSelector().add("paymentBill.prjPayEntry.locAdvance");
			view.getSelector().add("paymentBill.contractBillId");
			view.getSelector().add("paymentBill.exchangeRate");
			view.getSelector().add("paymentBill.curProject.id");
			view.getSelector().add("entrys.costAccount.id");
			view.getSelector().add("entrys.costAccount.number");
			view.getSelector().add("entrys.costAccount.longNumber");
			view.getSelector().add("entrys.costAccount.flag");
			view.getSelector().add("entrys.costAccount.curProject.id");
			view.getSelector().add("entrys.costAccount.curProject.name");

			IObjectCollection paymentSplitInfos = PaymentSplitFactory.getLocalInstance(ctx).getCollection(view);

			// notCost
			view.getSelector().clear();
			view.getSelector().add("*");
			view.getSelector().add("entrys.*");
			view.getSelector().add("entrys.product.*");
			view.getSelector().add("entrys.accountView.*");
			view.getSelector().add("entrys.curProject.id");
			view.getSelector().add("paymentBill.id");
			view.getSelector().add("paymentBill.actPayAmt");
			view.getSelector().add("paymentBill.payerAccount.id");
			view.getSelector().add("paymentBill.actFdcPayeeName.id");
			view.getSelector().add("paymentBill.fdcPayType.payType.id");
			view.getSelector().add("paymentBill.actPayLocAmt");
			view.getSelector().add("paymentBill.exchangeRate");
			view.getSelector().add("paymentBill.prjPayEntry.advance");
			view.getSelector().add("paymentBill.prjPayEntry.locAdvance");
			view.getSelector().add("paymentBill.contractBillId");
			view.getSelector().add("paymentBill.curProject.id");
			view.getSelector().add("entrys.curProject.*");

			IObjectCollection paymentNoSplitInfos = PaymentNoCostSplitFactory.getLocalInstance(ctx).getCollection(view);
			paymentSplitInfos.addObjectCollection(paymentNoSplitInfos);
			dataMap.put("paySplitInfos", paymentSplitInfos);
		}
	}
	/***
	 * ????????????????
	 * put(compensationBills)
	 */
	private void initCompensationBills(Map dataMap, Set paymentBillIds) throws BOSException {
		EntityViewInfo view;
		FilterInfo filter;
		
		view = new EntityViewInfo();
		view.getSelector().add("compensation.*");
		view.getSelector().add("paymentBill.actFdcPayeeName.id");
		view.getSelector().add("paymentbill.id");
		view.getSelector().add("paymentbill.curProject.id");
		view.getSelector().add("paymentbill.exchangeRate");
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id",paymentBillIds,CompareType.INCLUDE));
		//???????????????????????????????????????????????????????????? by zhiyuan_tang 2010-05-31
//		filter.getFilterItems().add(new FilterItemInfo("hasPaid",Boolean.TRUE));
		view.setFilter(filter);
		CompensationOfPayReqBillCollection compensationBills = CompensationOfPayReqBillFactory.getLocalInstance(ctx).getCompensationOfPayReqBillCollection(view);
		dataMap.put(COMPENSATIONBILLS,compensationBills);
		/****
		 * ????????????????
		 * ????????????????????????????????????????????
		 * ????????????????????????????????????????????????Map,??????????????
		 * ??????????????
		 * dataMap.put(COMPENSATIONBILLS,compensationBills);
		 * ??????????????????????????????????
		 * ????????????????????????????????
		 */
//		for(Iterator it=compensationBills.iterator();it.hasNext();){
//			CompensationOfPayReqBillInfo compensationOfPayInfo = (CompensationOfPayReqBillInfo)it.next();
//			String key = COMPENSATIONBILLS + compensationOfPayInfo.getPaymentBill().getId().toString();
//			IObjectCollection coll = null;
//			if (dataMap.containsKey(key)) {
//				coll = (IObjectCollection) dataMap.get(key);
//				coll.addObject(compensationOfPayInfo);
//			} else {
//				coll = new CompensationOfPayReqBillCollection();
//				coll.addObject(compensationOfPayInfo);
//				dataMap.put(key, coll);
//			}
//		}
	}
	/***
	 * ????????????????
	 * put(guerdonBills)
	 */
	private void initGuerdonBills(Map dataMap, Set paymentBillIds) throws BOSException {
		EntityViewInfo view;
		FilterInfo filter;
		
		view = new EntityViewInfo();
		view.getSelector().add("guerdon.*");
		view.getSelector().add("paymentBill.actFdcPayeeName.id");
		view.getSelector().add("paymentbill.id");
		view.getSelector().add("paymentbill.curProject.id");
		view.getSelector().add("paymentbill.exchangeRate");
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id",paymentBillIds,CompareType.INCLUDE));
//		filter.getFilterItems().add(new FilterItemInfo("hasPaid",Boolean.TRUE));
		view.setFilter(filter);
		GuerdonOfPayReqBillCollection guerdonBills = GuerdonOfPayReqBillFactory.getLocalInstance(ctx).getGuerdonOfPayReqBillCollection(view);
		dataMap.put(GUERDONBILLS,guerdonBills);
	}
	
	/***
	 * ????????????????????
	 * 
	 * put(deductBills)
	 */	
	private void initDeductBills(Map dataMap, Set paymentBillIds) throws BOSException {
		EntityViewInfo view;
		FilterInfo filter;
			
		view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("deductBillEntry.*");
		view.getSelector().add("deductBillEntry.deductType.*");
		view.getSelector().add("deductBillEntry.deductUnit.id");
		view.getSelector().add("parent.paymentbill.actFdcPayeeName.id");
		view.getSelector().add("parent.paymentbill.id");
		view.getSelector().add("parent.paymentbill.curProject.id");
		view.getSelector().add("paymentbill.exchangeRate");
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("parent.paymentBill.id",paymentBillIds,CompareType.INCLUDE));
//		filter.getFilterItems().add(new FilterItemInfo("parent.hasPaid",Boolean.TRUE));
		view.setFilter(filter);
		DeductOfPayReqBillEntryCollection deductBills = DeductOfPayReqBillEntryFactory.getLocalInstance(ctx).getDeductOfPayReqBillEntryCollection(view);
		
		
		dataMap.put(DEDUCTBILLS,deductBills);
		
		view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("amount");
		view.getSelector().add("originalAmount");
		view.getSelector().add("deductBill.entrys.*");
		view.getSelector().add("deductBill.entrys.deductType.*");
		view.getSelector().add("deductBill.entrys.deductUnit.id");
		view.getSelector().add("paymentbill.actFdcPayeeName.id");
		view.getSelector().add("paymentbill.id");
		view.getSelector().add("paymentbill.contractBillId");
		view.getSelector().add("paymentbill.curProject.id");
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id",paymentBillIds,CompareType.INCLUDE));
//		filter.getFilterItems().add(new FilterItemInfo("hasPaid",Boolean.TRUE));
		view.setFilter(filter);
		PartAOfPayReqBillCollection partABills = PartAOfPayReqBillFactory.getLocalInstance(ctx).getPartAOfPayReqBillCollection(view);
		dataMap.put(PARTAOFBILLS,partABills);
		/****
		 * ????????????????
		 * ????????????????????????????????????????????
		 * ????????????????????????????????????????????????Map
		 * ??????????????
		 * ??????????????
		 * dataMap.put(DEDUCTBILLS,deductBills);
		 * ??????????????????????????????????
		 * ????????????????????????????????
		 */
//		for(Iterator it=deductBills.iterator();it.hasNext();){
//			DeductOfPayReqBillEntryInfo info = (DeductOfPayReqBillEntryInfo)it.next();
//			String key = DEDUCTBILLS + info.getParent().getPaymentBill().getId().toString();
//			IObjectCollection coll = null;
//			if (dataMap.containsKey(key)) {
//				coll = (IObjectCollection) dataMap.get("key");
//				coll.addObject(info);
//			} else {
//				coll = new DeductOfPayReqBillEntryCollection();
//				coll.addObject(info);
//				dataMap.put(key, coll);
//			}
//		}
	}
	/***
	 * ??????????????????????????
	 * ????????????????????????
	 * put(deductBills)
	 */	
	private void initPartAConfirmBills(Map dataMap, Set paymentBillIds) throws BOSException {
		EntityViewInfo view;
		FilterInfo filter;
			
		view = new EntityViewInfo();
		view.getSelector().add("id");
		view.getSelector().add("amount");
		view.getSelector().add("paymentbill.actFdcPayeeName.id");
		view.getSelector().add("paymentbill.id");
		view.getSelector().add("paymentbill.curProject.id");
		view.getSelector().add("paymentbill.exchangeRate");
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("paymentBill.id",paymentBillIds,CompareType.INCLUDE));
//		filter.getFilterItems().add(new FilterItemInfo("hasPaid",Boolean.TRUE));
		view.setFilter(filter);
		PartAConfmOfPayReqBillCollection partABills = PartAConfmOfPayReqBillFactory.getLocalInstance(ctx).getPartAConfmOfPayReqBillCollection(view);
		
		dataMap.put(PARTACONFIRMBILLS,partABills);
		
	}
	
	protected  IObjectCollection createCostEntrys(Map dataMap) throws EASBizException, BOSException {
		return getCostHandler().createCostEntrys(dataMap);
	}
	
	
	
	
	protected IObjectCollection createPayEntrys(Map dataMap) throws BOSException, EASBizException{
		return getPayHandler().createPayEntrys(dataMap);
	}
	
	
	
	

	
	/**
	 * ????????sql????????
	 * ????
	 * @param costEntrys
	 * @param payEntrys
	 * @throws BOSException 
	 */
	private void save(Map param,IObjectCollection costEntrys,IObjectCollection payEntrys) throws BOSException{
		//????:????????????????????????????????costHandler??save???????? //TODO ????????????????????????????
		getCostHandler().save(param, costEntrys);
		getPayHandler().save(param, payEntrys);
	}
	
	
	/****
	 * ??????????????????????????????????
	 * ??????????
	 * ????????
	 * ??????costAccountWithAccountMap????
	 * key = ????????ID_????????ID
	 * @param costAcc
	 * @param iCostAccountWithAccount
	 * @param costAccountWithAccountColl
	 * @param costAccountWithAccountInfo
	 * @param iCostAccount
	 * @param selector
	 * @param view
	 * @return
	 * @throws EASBizException
	 * @throws BOSException
	 * @deprecated
	 * @see costAccountWithAccountMap.get(????????ID_????????ID)
	 */
	protected String getAccountForCostAccount(CostAccountInfo costAcc, ICostAccountWithAccount iCostAccountWithAccount, CostAccountWithAccountCollection costAccountWithAccountColl, CostAccountWithAccountInfo costAccountWithAccountInfo, ICostAccount iCostAccount, SelectorItemCollection selector, EntityViewInfo view) throws EASBizException, BOSException {
		if (!iCostAccountWithAccount.exists(view.getFilter())) {
			if (costAcc.getLevel() == 1) {
				throw new PaymentSplitException(
						PaymentSplitException.NO_ACCOUNT);
			} else {
				if (costAcc.getParent().getId() != null) {
					costAcc = iCostAccount.getCostAccountInfo(
							new ObjectUuidPK(costAcc.getParent().getId()),
							selector);
					return getAccountForCostAccount(costAcc, iCostAccountWithAccount,
							costAccountWithAccountColl, costAccountWithAccountInfo,iCostAccount,selector,view);
				} else {
					throw new PaymentSplitException(
							PaymentSplitException.ACCOUNT_WRONG);
				}
			}
		}
		costAccountWithAccountColl = iCostAccountWithAccount
				.getCostAccountWithAccountCollection(view);
		if (costAccountWithAccountColl.size() == 1) {
			costAccountWithAccountInfo = costAccountWithAccountColl.get(0);
			if (costAccountWithAccountInfo.getAccount() != null) {
				return costAccountWithAccountInfo.getAccount().getId().toString();
			}
		}
		return null;//can not arrive 
	}

	/**
	 * ????????????????????????????
	 * @param key ????????ID_????????ID 
	 * @param paymentBillInfo
	 * @param isProgress ??????????????
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	public AccountViewInfo getAccountView(CostAccountInfo costAccount,PaymentBillInfo paymentBillInfo,boolean isProgress,boolean isConWithoutText) throws BOSException, EASBizException{
		String key = costAccount.getCurProject().getId().toString() ;
		key += "_" + costAccount.getId().toString();
		String msg = costAccount.getCurProject().getName().toString();
		int flag = costAccount.getFlag();
		if(FDCConstants.FDC_PARAM_SIMPLEFINACIAL.equals(financial)){
			/******
			 * ????????,??????(??????????????????????????createCostEntry??????) ????????????????????????
			 */
			return getAccountWithCostAccount(costAccount);
		}
		if(isConWithoutText){
			return getProgressAccountView(costAccount);
		}
		if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.progressID)){
			return getProgressAccountView(costAccount);
		}
		if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.settlementID)){
			if(isProgress)
				return getFirstSettleAccountView(costAccount);
			else
				return getSettleAccountView(costAccount);
		}
		if(paymentBillInfo.getFdcPayType().getPayType().getId().toString().equals(PaymentTypeInfo.keepID)){
			return getGrtAccountView(costAccount);
		}
		return null;
	}
	
	/**
	 * ??????????????????????????????????
	 * ??????????????????????
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	public AccountViewInfo getInvoiceAccountView(CostAccountInfo costAccount) throws EASBizException{
		String key = costAccount.getCurProject().getId().toString();
		key += "_" + costAccount.getId().toString();
		if(costAccountWithAccountMap.get(key)!=null&&((CostAccountWithAccountInfo)costAccountWithAccountMap.get(key)).getInvoiceAccount()!=null){
			return ((CostAccountWithAccountInfo)costAccountWithAccountMap.get(key)).getInvoiceAccount();
		}else{
			throw new PaymentSplitException(
					PaymentSplitException.HASCOSTACCOUNTNOTMAPINVOICEACCOUNT,new Object[]{costAccount.getCurProject().getName(),costAccount.getLongNumber().replace('!','.')});
		}
	}
	
	/**
	 * ??????????????????????????????????????????
	 * @param key
	 * @return
	 * @throws EASBizException 
	 */
	public AccountViewInfo getAccountWithCostAccount(CostAccountInfo costAccountInfo) throws EASBizException {
		String key = costAccountInfo.getCurProject().getId().toString();
		key += "_" + costAccountInfo.getId().toString();
		if(costAccountWithAccountMap.get(key)!=null&&((CostAccountWithAccountInfo)costAccountWithAccountMap.get(key)).getAccount()!=null)
			return ((CostAccountWithAccountInfo)costAccountWithAccountMap.get(key)).getAccount();
		else
			throw new PaymentSplitException(
					PaymentSplitException.HASCOSTACCOUNTNOTMAPACCOUNT,new Object[]{costAccountInfo.getCurProject().getName(),costAccountInfo.getLongNumber().replace('!','.')});
	}
	
	/**
	 * @param param
	 */
	private boolean isWorkLoadVoucher(Map param){
		return param.get("isWorkLoadVoucher")!=null;
	}
	
}
