package com.kingdee.eas.fdc.finance.client;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillCollection;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillFactory;
import com.kingdee.eas.fdc.contract.DeductOfPayReqBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public class PaymentBillDataProvider extends FDCBillDataProvider {
	static public final String[] col = new String[] { "id", "bookedDate",
			"number", "useDepartment.name", "period", "payDate", "dayaccount",
			"feeType.name", "recProvince", "recCity",
			"payerAccountBank.bankAccountNumber", "payeeType.name",
			"isDifferPlace", "payerBank.name", "payee", "actFdcPayeeName.name",
			"payerAccount.name", "payeeBank", "currency.name", "bizType.name",
			"payeeAccountBank", "exchangeRate", "isEmergency", "amount",
			"accessoryAmt", "localAmt", "capitalAmount", "paymentProportion",
			"description", "completePrjAmt", "usage", "fpItem.name", "conceit",
			"summary", "settlementType.name", "settlementNumber",
			"payeeArea.name", "creator.number", "creator.name", "createTime",
			"auditor.number", "auditor.name", "auditDate", "accountant.number",
			"accountant.name", "cashier.number", "cashier.name",
			"projNameWithoutOrg", "curProject.displayName", "contractPrice",
			"contractName", "latestPrice", "changeAmt", "settleAmt",
			"payedAmt", "payTimes", "lstPrjAllPaidAmt", "lstPrjAllReqAmt",
			"projectPriceInContract", "prjAllReqAmt", "prjAllPaidAmt",
			"lstGuerdonPaidAmt", "lstGuerdonReqAmt", "guerdonAmt",
			"allGuerdonReqAmt", "allGuerdonPaidAmt", "lstCompensationPaidAmt",
			"lstCompensationReqAmt", "compensationAmt",
			"allCompensationReqAmt", "allCompensationPaidAmt", "deductType",
			"lstDeductPaidAmt", "lstDeductReqAmt", "deductAmt",
			"allDeductReqAmt", "allDeductPaidAmt", "lstAMatlAllPaidAmt",
			"lstAMatlAllReqAmt", "payPartAMatlAmt", "payPartAMatlAllReqAmt",
			"allPartAMatlAllPaidAmt", "lstRealPaidAmt", "lstRealReqAmt",
			"curPaid", "allRealReqAmt", "allRealPaidAmt", "balance",
			"lstDeductSumPaidAmt", "lstDeductSumReqAmt", "deductSumAmt",
			"allDeductSumReqAmt", "allDeductSumPaidAmt", "curPlannedPayment",
			"curBackPay", "curReqPercent", "allReqPercent", "imageSchedule", "contractNo"};
	public static String printStringHelper(Object o) {
		if (o == null)
			return "";
		else if(o instanceof BigDecimal){
			if(FDCHelper.ZERO.compareTo(FDCHelper.toBigDecimal(o))==0)
				return "";
			else
				return String.valueOf(((BigDecimal)o).setScale(2,BigDecimal.ROUND_HALF_UP));
		}
			return String.valueOf(o);
	}

	public static String printStringHelper(boolean o) {
		return o ? "??" : "??";
	}
	

	private PaymentBillInfo fdcBill = null;
	private PayRequestBillInfo payReqInfo = null;
	private CurProjectInfo curProjectInfo = null;
	private HashMap bindCellMap =null;
	public PaymentBillDataProvider(PaymentBillInfo editData,HashMap bindCellMap,CurProjectInfo curProjectInfo,IMetaDataPK mainQuery) {
		super(editData.getId().toString(), mainQuery);
		fdcBill = editData;
		payReqInfo = (PayRequestBillInfo)editData.get("payReqInfo");
		this.bindCellMap = bindCellMap;
		this.curProjectInfo = curProjectInfo;
	}
	
	private void insert(IRowSet drs,DeductOfPayReqBillInfo info,Map deductTypeSum) throws Exception{
		
		/**************************************************????start**************************************************/
		//???????? 
		drs.updateString("contractNo", fdcBill.getContractNo());
		//????????
		drs.updateString("bookedDate",printStringHelper(payReqInfo.getBookedDate()));
		//??????????
		drs.updateString("number",fdcBill.getNumber());
		//????????
		drs.updateString("useDepartment.name",fdcBill.getUseDepartment()!=null?fdcBill.getUseDepartment().getName():null);
		//????????
		if(payReqInfo.getPeriod()!=null)
		drs.updateString("period",payReqInfo.getPeriod().getPeriodYear()+"??"+payReqInfo.getPeriod().getPeriodNumber()+"??");
		//????????
		drs.updateString("payDate",printStringHelper(fdcBill.getPayDate()));
		//??????????
		drs.updateString("dayaccount",printStringHelper(fdcBill.getDayaccount()));
		//????????
		drs.updateString("feeType.name",fdcBill.getFeeType()!=null?fdcBill.getFeeType().getName():null);
		//????????
		drs.updateString("recProvince",fdcBill.getRecProvince());
		//????????/??
		drs.updateString("recCity",fdcBill.getRecCity());
		//????????
		drs.updateString("payerAccountBank.bankAccountNumber",fdcBill.getPayerAccountBank()!=null?fdcBill.getPayerAccountBank().getBankAccountNumber():null);
		//??????????
		drs.updateString("payeeType.name",fdcBill.getPayeeType()!=null?fdcBill.getPayeeType().getName():null);
		//????????
		drs.updateString("isDifferPlace",printStringHelper(fdcBill.getIsDifferPlace()));
		//????????
		drs.updateString("payerBank.name",fdcBill.getPayerBank()!=null?fdcBill.getPayerBank().getName():null);
		//??????????
		drs.updateString("payee",fdcBill.getPayeeName());
		//????????????????
		drs.updateString("actFdcPayeeName.name",fdcBill.getActFdcPayeeName()!=null?fdcBill.getActFdcPayeeName().getName():null);
		//????????
		drs.updateString("payerAccount.name",fdcBill.getPayerAccount()!=null?fdcBill.getPayerAccount().getName():null);
		//????????
		drs.updateString("payeeBank",fdcBill.getPayeeBank());
		//????
		drs.updateString("currency.name",fdcBill.getCurrency()!=null?fdcBill.getCurrency().getName():null);
		//????????
		drs.updateString("bizType.name",fdcBill.getBizType()!=null?fdcBill.getBizType().getName():null);
		//????????
		drs.updateString("payeeAccountBank",fdcBill.getPayeeAccountBank());
		//????
		drs.updateString("exchangeRate",printStringHelper(fdcBill.getExchangeRate()));
		//????????
		drs.updateString("isEmergency",printStringHelper(fdcBill.getIsEmergency()));
		//????????
		drs.updateString("amount",printStringHelper(fdcBill.getAmount()));
		//??????
		drs.updateString("accessoryAmt",String.valueOf(fdcBill.getAccessoryAmt()));
		//????????
		drs.updateString("localAmt",printStringHelper(fdcBill.getLocalAmt()));
		//????????
		drs.updateString("capitalAmount",fdcBill.getCapitalAmount());
		//??????????(%)
		drs.updateString("paymentProportion",printStringHelper(payReqInfo.getPaymentProportion()));
		//????
		drs.updateString("description",fdcBill.getDescription());
		//????????????
		drs.updateString("completePrjAmt",printStringHelper(payReqInfo.getCompletePrjAmt()));
		//????
		drs.updateString("usage",fdcBill.getUsage());
		//????????
		drs.updateString("fpItem.name",fdcBill.getFpItem()!=null?fdcBill.getFpItem().getName():null);
		//????????
		drs.updateString("conceit",fdcBill.getConceit());
		//????
		drs.updateString("summary",fdcBill.getSummary());
		//????????
		drs.updateString("settlementType.name",fdcBill.getSettlementType()!=null?fdcBill.getSettlementType().getName():null);
		//??????
		drs.updateString("settlementNumber",fdcBill.getSettlementNumber());
		//??????
		drs.updateString("creator.number",fdcBill.getCreator()!=null?fdcBill.getCreator().getNumber():null);
		//??????
		drs.updateString("creator.name",fdcBill.getCreator()!=null?fdcBill.getCreator().getName():null);
		//????????
		drs.updateString("createTime",printStringHelper(fdcBill.getCreateTime()));
		//??????
		drs.updateString("auditor.number",fdcBill.getAuditor()!=null?fdcBill.getAuditor().getNumber():null);
		//??????
		drs.updateString("auditor.name",fdcBill.getAuditor()!=null?fdcBill.getAuditor().getName():null);
		//????????
		drs.updateString("auditDate",printStringHelper(fdcBill.getAuditDate()));
		//????????
		drs.updateString("accountant.number",fdcBill.getAccountant()!=null?fdcBill.getAccountant().getNumber():null);
		//????????
		drs.updateString("accountant.name",fdcBill.getAccountant()!=null?fdcBill.getAccountant().getName():null);
		//????????
		drs.updateString("cashier.number",fdcBill.getCashier()!=null?fdcBill.getCashier().getNumber():null);
		//????????
		drs.updateString("cashier.number",fdcBill.getCashier()!=null?fdcBill.getCashier().getNumber():null);
		/**************************************************????start**************************************************/
		
		
		
		/**************************************************??????????????start**************************************************/
		BigDecimal sumLstDeductPaid = (BigDecimal)deductTypeSum.get("sumLstDeductPaid");
		BigDecimal sumLstDeductReq =  (BigDecimal)deductTypeSum.get("sumLstDeductReq");
		BigDecimal sumDeduct =  (BigDecimal)deductTypeSum.get("sumDeduct");
		BigDecimal sumAllDeductReq =  (BigDecimal)deductTypeSum.get("sumAllDeductReq");
		BigDecimal sumAllDeductPaid = (BigDecimal)deductTypeSum.get("sumAllDeductPaid");
		
//		 ????????????????????????drs.updateString(key,value) key
		// ????????????????????????????????Value??????????????????????
		String orgName=((CurProjectInfo)fdcBill.getCurProject()).getFullOrgUnit().getName();
		String curProjectName = curProjectInfo.getDisplayName();		
		//??????????????????????????
		//2008-07-22
		String projNameWithoutOrg = curProjectName.replace('_', '\\');
		curProjectName = orgName+"\\"+curProjectName.replace('_', '\\');
		drs.updateString("curProject.displayName", curProjectName);
		drs.updateString("projNameWithoutOrg", projNameWithoutOrg);
		drs.updateString("contractName",payReqInfo.getContractName());
		drs.updateString("contractPrice", printStringHelper(payReqInfo.getContractPrice()));
		drs.updateString("latestPrice",printStringHelper(fdcBill.getLatestPrice()));
		drs.updateString("changeAmt",printStringHelper(fdcBill.getChangeAmt()));
		drs.updateString("settleAmt",printStringHelper(fdcBill.getSettleAmt()));
		drs.updateString("payedAmt", printStringHelper(fdcBill.getPayedAmt()));
		drs.updateString("payTimes", String.valueOf(fdcBill.getPayTimes()));
		
		drs.updateString("lstPrjAllPaidAmt", printStringHelper(fdcBill
				.getLstPrjAllPaidAmt()));
		drs.updateString("lstPrjAllReqAmt", printStringHelper(fdcBill
				.getLstPrjAllReqAmt()));
		drs.updateString("projectPriceInContract",
				printStringHelper(fdcBill.getProjectPriceInContract()));
		drs.updateString("prjAllReqAmt", printStringHelper(fdcBill
				.getPrjAllReqAmt()));
		//?????????? ???????????????? = ???????????????????? +????
		BigDecimal prjAllPaidAmt = (fdcBill.getLstPrjAllPaidAmt()!=null?fdcBill.getLstPrjAllPaidAmt():FDCHelper.ZERO);
		prjAllPaidAmt = FDCHelper.add(prjAllPaidAmt,fdcBill.getProjectPriceInContract());
		drs.updateString("prjAllPaidAmt", printStringHelper(prjAllPaidAmt));
		// ???? ????????????????
		BigDecimal lstGuerdonPaidAmt = FDCHelper.toBigDecimal(((ICell)( bindCellMap.get("lstGuerdonPaidAmt"))).getValue());				
		drs.updateString("lstGuerdonPaidAmt",printStringHelper(lstGuerdonPaidAmt));
		// ???? ????????????????
		BigDecimal lstGuerdonReqAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstGuerdonReqAmt"))).getValue());
		drs.updateString("lstGuerdonReqAmt", printStringHelper(lstGuerdonReqAmt));
		// ???? ????????				
		BigDecimal guerdonAmt = FDCHelper.toBigDecimal(((ICell)( bindCellMap.get("guerdonAmt"))).getValue());
		guerdonAmt = guerdonAmt!= null?guerdonAmt: FDCHelper.ZERO;
		drs.updateString("guerdonAmt", printStringHelper(guerdonAmt));
		// ???? ???????????????? = ???? ???????????????? + ???? ????????
		// ???? ???????????????? = ???? ???????????????? + ????
		BigDecimal allGuerdonReqAmt = guerdonAmt.add(lstGuerdonReqAmt!= null ? lstGuerdonReqAmt : FDCHelper.ZERO);
		BigDecimal allGuerdonPaidAmt = guerdonAmt.add(lstGuerdonPaidAmt!= null ?lstGuerdonPaidAmt: FDCHelper.ZERO);
		drs.updateString("allGuerdonReqAmt",printStringHelper(allGuerdonReqAmt));
		drs.updateString("allGuerdonPaidAmt",printStringHelper(allGuerdonPaidAmt));
		
		// ???? ????????????????
		BigDecimal lstCompensationPaidAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstCompensationPaidAmt"))).getValue());
		drs.updateString("lstCompensationPaidAmt",printStringHelper(lstCompensationPaidAmt));
		
		// ???? ????????????????
		BigDecimal lstCompensationReqAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("lstCompensationReqAmt"))).getValue());
		drs.updateString("lstCompensationReqAmt", printStringHelper(lstCompensationReqAmt));
		// ???? ????????
		BigDecimal compensationAmt = FDCHelper.toBigDecimal(( (ICell)( bindCellMap.get("compensationAmt"))).getValue());
		compensationAmt = compensationAmt != null? compensationAmt: FDCHelper.ZERO;
		drs.updateString("compensationAmt", printStringHelper(compensationAmt));
		// ???? ???????????????? = ???? ???????????????? + ???? ????????
		// ???? ???????????????? = ???? ???????????????? +????				
		BigDecimal allCompensationReqAmt = compensationAmt.add(lstCompensationReqAmt!= null 
												? lstCompensationReqAmt: FDCHelper.ZERO);
		BigDecimal allCompensationPaidAmt = compensationAmt.add(lstCompensationPaidAmt != null 
												? lstCompensationPaidAmt: FDCHelper.ZERO);
		drs.updateString("allCompensationReqAmt",	printStringHelper(allCompensationReqAmt));
		drs.updateString("allCompensationPaidAmt",printStringHelper(allCompensationPaidAmt));
		
		//??????
		//????????				
		if(info!=null){
			drs.updateString("deductType",info==null?"":info.getDeductType().getName());
			BigDecimal lstDeductPaidAmt = info.getAllPaidAmt()==null?FDCHelper.ZERO:info.getAllPaidAmt();
			BigDecimal lstDeductReqAmt  = info.getAllReqAmt()==null?FDCHelper.ZERO:info.getAllReqAmt();
			BigDecimal deductAmt        = info.getAmount()==null?FDCHelper.ZERO:info.getAmount();
			BigDecimal allDeductReqAmt  = lstDeductReqAmt.add(deductAmt);
			BigDecimal allDeductPaidAmt  = lstDeductPaidAmt.add(deductAmt);
			
			drs.updateString("lstDeductPaidAmt",printStringHelper(lstDeductPaidAmt));
			drs.updateString("lstDeductReqAmt",printStringHelper(lstDeductReqAmt));
			drs.updateString("deductAmt",printStringHelper(deductAmt));
			drs.updateString("allDeductReqAmt",printStringHelper(allDeductReqAmt));
			drs.updateString("allDeductPaidAmt",	printStringHelper(allDeductPaidAmt));
			
			
			//????????
			sumLstDeductPaid = sumLstDeductPaid.add(lstDeductPaidAmt);
			sumLstDeductReq = sumLstDeductReq.add(lstDeductReqAmt);
			sumDeduct = sumDeduct.add(deductAmt);
			sumAllDeductReq = sumAllDeductReq.add(allDeductReqAmt);
			sumAllDeductPaid = sumAllDeductPaid.add(allDeductPaidAmt);
			
			deductTypeSum.put("sumLstDeductPaid",sumLstDeductPaid);
			deductTypeSum.put("sumLstDeductReq",sumLstDeductReq);
			deductTypeSum.put("sumDeduct",sumDeduct);
			deductTypeSum.put("sumAllDeductReq",sumAllDeductReq);
			deductTypeSum.put("sumAllDeductPaid",sumAllDeductPaid);
		}
		
		drs.updateString("lstDeductSumPaidAmt",printStringHelper(sumLstDeductPaid));
		drs.updateString("lstDeductSumReqAmt",printStringHelper(sumLstDeductReq));
		drs.updateString("deductSumAmt",printStringHelper(sumDeduct));
		drs.updateString("allDeductSumReqAmt",printStringHelper(sumAllDeductReq));
		drs.updateString("allDeductSumPaidAmt",printStringHelper(sumAllDeductPaid));					
		
		//???? ????????????????
		drs.updateString("lstAMatlAllPaidAmt",printStringHelper(fdcBill.getLstAMatlAllPaidAmt()));
		//???? ????????????????
		drs.updateString("lstAMatlAllReqAmt",printStringHelper(fdcBill.getLstAMatlAllReqAmt()));
		//???? ????????
		drs.updateString("payPartAMatlAmt",printStringHelper(fdcBill.getPayPartAMatlAmt()));
//		BigDecimal AMatlAllReqAmt = FDCHelper.add(fdcBill.getLstAMatlAllReqAmt(),fdcBill.getPayPartAMatlAmt());
		//???? ????????????????
		drs.updateString("payPartAMatlAllReqAmt",printStringHelper(fdcBill.getPayPartAMatlAllReqAmt()));
		//?????? ???????????????? = ?????? ???????????????? +????
		BigDecimal allPartAMatlAllPaidAmt = (fdcBill.getLstAMatlAllPaidAmt()!=null?fdcBill
				.getLstAMatlAllPaidAmt():FDCHelper.ZERO);
		// ?????? ????????????????
				//.add(fdcBill.getPayPartAMatlAmt()!=null?fdcBill.getPayPartAMatlAmt()
						//:FDCHelper.ZERO);// ?????? ????????
		allPartAMatlAllPaidAmt = FDCHelper.add(allPartAMatlAllPaidAmt,fdcBill.getPayPartAMatlAmt());
		drs.updateString("allPartAMatlAllPaidAmt",printStringHelper(allPartAMatlAllPaidAmt));
		
		//?????? ???????? = ?????????? + ???? - ?????? - ???????? - ??????
		BigDecimal projectPriceInContract = fdcBill.getProjectPriceInContract()!=null?fdcBill.getProjectPriceInContract():FDCHelper.ZERO;
//		BigDecimal tempCurPaid = fdcBill.getCurPaid()!= null ? fdcBill.getCurPaid(): FDCHelper.ZERO;
		BigDecimal tempCurPaid = projectPriceInContract.add(guerdonAmt)//????
		.subtract(compensationAmt)//????
		.subtract(sumDeduct)//????
		.subtract(fdcBill.getPayPartAMatlAmt() != null ? fdcBill
						.getPayPartAMatlAmt() : FDCHelper.ZERO);//??????
		drs.updateString("curPaid", printStringHelper(tempCurPaid));
		
		//?????? ???????????????? = ?????????? + ???? - ?????? - ???????? - ??????
		BigDecimal lstRealPaidAmt = FDCHelper.toBigDecimal(fdcBill.getLstPrjAllPaidAmt());
		lstRealPaidAmt = lstRealPaidAmt.add(lstGuerdonPaidAmt)//????
				.subtract(lstCompensationPaidAmt)//????
				.subtract(sumLstDeductPaid)//????
				.subtract(fdcBill.getLstAMatlAllPaidAmt() != null ? fdcBill
								.getLstAMatlAllPaidAmt()
								: FDCHelper.ZERO);//??????
		drs.updateString("lstRealPaidAmt",printStringHelper(lstRealPaidAmt));
		
		//?????? ???????????????? = ?????????? + ???? - ?????? - ???????? - ??????
		BigDecimal lstRealReqAmt = FDCHelper.toBigDecimal(fdcBill.getLstPrjAllReqAmt());
		lstRealReqAmt = lstRealReqAmt.add(lstGuerdonReqAmt)//????
				.subtract(lstCompensationReqAmt)//????
				.subtract(sumLstDeductReq)//????
				.subtract(fdcBill.getLstAMatlAllReqAmt() != null ? fdcBill
								.getLstAMatlAllReqAmt()
								: FDCHelper.ZERO);//??????
		drs.updateString("lstRealReqAmt",printStringHelper(lstRealReqAmt));
		
		//?????? ???????????????? = ?????????? + ???? - ?????? - ???????? - ??????
		BigDecimal prjAllReqAmt = fdcBill.getPrjAllReqAmt()!=null?fdcBill.getPrjAllReqAmt():FDCHelper.ZERO;
		BigDecimal allRealReqAmt = prjAllReqAmt.add(allGuerdonReqAmt)//????
				.subtract(	allCompensationReqAmt)//????
				.subtract(sumAllDeductReq)//????
				.subtract(fdcBill.getPayPartAMatlAllReqAmt() != null ? fdcBill
								.getPayPartAMatlAllReqAmt()
								: FDCHelper.ZERO);//??????
		drs.updateString("allRealReqAmt",printStringHelper(allRealReqAmt));
		
		// ?????? ???????????????? = ?????????? + ???? - ?????? - ???????? - ??????
//		BigDecimal prjAllReqAmt =  fdcBill.getPrjAllReqAmt();
		BigDecimal allRealPaidAmt = prjAllReqAmt
				.add(allGuerdonPaidAmt)//????
				.subtract(allCompensationPaidAmt)//????
				.subtract(sumAllDeductPaid)//????
				.subtract(allPartAMatlAllPaidAmt);//??????
		drs.updateString("allRealPaidAmt",printStringHelper(allRealPaidAmt));
		
		// ???? = ???????? - ?????????? ????????????????
		BigDecimal balance = (fdcBill
		.getLatestPrice() == null ? FDCHelper.ZERO : fdcBill.getLatestPrice())				
		.subtract(prjAllPaidAmt);//?????????? ????????????????
		
		drs.updateString("balance",printStringHelper(balance));
		// ????????% = ?????? ???????? / ????????
		BigDecimal tempCurReqPercent = fdcBill.getLatestPrice() == null
				|| fdcBill.getLatestPrice().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO
				: tempCurPaid.divide(fdcBill.getLatestPrice(), 4,
						BigDecimal.ROUND_HALF_UP);
//		BigDecimal tempCurReqPercent = fdcBill.getCurReqPercent()!=null?
//				fdcBill.getCurReqPercent():FDCHelper.ZERO;
		drs.updateString("curReqPercent",
				printStringHelper((tempCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		
		// ????????% = ?????? ???????????????? / ????????
		BigDecimal tempAllCurReqPercent = fdcBill.getLatestPrice() == null
				|| fdcBill.getLatestPrice().compareTo(FDCHelper.ZERO) == 0 ? FDCHelper.ZERO
				: allRealReqAmt.divide(fdcBill.getLatestPrice(), 4,
						BigDecimal.ROUND_HALF_UP);
		
		drs.updateString("allReqPercent",
				printStringHelper((tempAllCurReqPercent.multiply(FDCConstants.ONE_HUNDRED)).setScale(2)));
		// ///
		drs.updateString("imageSchedule", printStringHelper(fdcBill
				.getImageSchedule()));
		/**************************************************??????????????end**************************************************/
	}

	public IRowSet getMainBillRowSet(BOSQueryDataSource ds) {
		DynamicRowSet drs = null;
		try {
			drs = new DynamicRowSet(col.length);
			for (int i = 0; i < col.length; i++) {
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = col[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			drs.beforeFirst();
			
			//??????????
			DeductOfPayReqBillCollection c = null;
			DeductOfPayReqBillInfo info = null;
			
			if(fdcBill.getId()!=null){
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				FilterItemCollection items = filter.getFilterItems();
				items.add(new FilterItemInfo("payRequestBill.id", payReqInfo.getId().toString()));
				view.setFilter(filter);
				SorterItemInfo sorterItemInfo = new SorterItemInfo("deductType.number");
				sorterItemInfo.setSortType(SortType.ASCEND);
				view.getSorter().add(sorterItemInfo);
				view.getSelector().add("deductType.number");
				view.getSelector().add("deductType.name");
				view.getSelector().add("*");
				c = DeductOfPayReqBillFactory.getRemoteInstance().getDeductOfPayReqBillCollection(view);
			}
			if(c!=null){
				Map deductTypeSum = new HashMap();
				deductTypeSum.put("sumLstDeductPaid",FDCHelper.ZERO);
				deductTypeSum.put("sumLstDeductReq",FDCHelper.ZERO);
				deductTypeSum.put("sumDeduct",FDCHelper.ZERO);
				deductTypeSum.put("sumAllDeductReq",FDCHelper.ZERO);
				deductTypeSum.put("sumAllDeductPaid",FDCHelper.ZERO);
				for(int i=0;i<c.size();i++){
					info = c.get(i);
					drs.moveToInsertRow();
					insert( drs,info ,deductTypeSum);
					drs.insertRow();
				}
			}
			drs.beforeFirst();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		return drs;
	}


}
