package com.kingdee.eas.fdc.finance.app;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserCollection;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitCollection;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostSplitStateEnum;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.util.FDCTransmissionHelper;
import com.kingdee.eas.fdc.contract.ContractBillCollection;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryCollection;
import com.kingdee.eas.fdc.contract.ContractCostSplitEntryInfo;
import com.kingdee.eas.fdc.contract.ContractCostSplitFactory;
import com.kingdee.eas.fdc.contract.ContractCostSplitInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitCollection;
import com.kingdee.eas.fdc.finance.PaymentSplitEntryInfo;
import com.kingdee.eas.fdc.finance.PaymentSplitFactory;
import com.kingdee.eas.fdc.finance.PaymentSplitInfo;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillCollection;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillFactory;
import com.kingdee.eas.fdc.finance.WorkLoadConfirmBillInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;

/**
 * 
 * @(#)							
 * ??????		????????????????????????????????		 	
 * ??????		????????????????
 *		
 * @author		xiaochong
 * @version		EAS7.0		
 * @createDate	2011-6-15	 
 * @see
 */
public class WorkLoadSplitTransmission extends AbstractDataTransmission {
	
	private static final String resource = "com.kingdee.eas.fdc.finance.FinanceTransmissionResource";
	// ??????????????????????????????????????????????
	private Map arrMap = new HashMap();
	// ??????????????
	private Map infoMap = new HashMap();
	// ????????????????????*??????????
	private Set costAccountSet = new HashSet();
	// ????????????
	private Map stateMap = new HashMap();
	// ????????????
	private Map splitStateMap = new HashMap();
	// ??????????????
	PaymentSplitInfo paymentSplitInfo = null;
	// ????????
	FullOrgUnitInfo orgUnitInfo = null;
	// ????????????
	CurProjectInfo curProjectInfo = null;
	// ????????
	ContractBillInfo contractBillInfo = null;
	// ????????????
	WorkLoadConfirmBillInfo workLoadConfirmBillInfo = null;
	// ??????
	UserInfo creatorInfo = null;
	// ??????
	UserInfo auditorInfo = null;

	// ??????????
	BigDecimal workLoadConfirmBillWorkLoadDecimal = null;
	
	/** ???????????????? */
	private Map contractSplitAccountMap = new HashMap();
	
//	 public void submit(CoreBaseInfo coreBaseInfo, Context ctx) throws TaskExternalException {
//		
//		 try {
//			if (coreBaseInfo == null)
//				return;
//			if (coreBaseInfo.getId() == null || !getController(ctx).exists(new ObjectUuidPK(coreBaseInfo.getId())))
//				getController(ctx).save(coreBaseInfo);
//			else
//				getController(ctx).update(new ObjectUuidPK(coreBaseInfo.getId()), coreBaseInfo);
//		} catch (Exception ex) {
//			throw new TaskExternalException(ex.getMessage(), ex.getCause());
//		}
//	}
//	 

	/** ?????????????? */
	public void initStateMap(Context ctx) {
		stateMap.put(getResource(ctx, "AUDITTED"), "4AUDITTED");
		stateMap.put(getResource(ctx, "SAVED"), "1SAVED");
	}

	/** ?????????????? */
	public void initSplitStateMap(Context ctx) {
		splitStateMap.put(getResource(ctx, "ALLSPLIT"), "3ALLSPLIT");
		splitStateMap.put(getResource(ctx, "NOSPLIT"), "1NOSPLIT");
	}
	
	/** ???????????? */
	private void setArrMap(Hashtable hsdata, Context ctx) throws TaskExternalException {
		for (int i = 0; i < hsdata.size(); i++) {
			Hashtable lineData = (Hashtable) hsdata.get(new Integer(i));

			String workLoadConfirmBillNumber = getString(lineData, "FWorkLoadConfirmBill_number");				// ????????????????
			String entrysCostAccountLongNumber = getString(lineData, "FEntrysCostAccount_longNumber");			// ????????????????
			String entrysAmount = getString(lineData, "FEntrys_amount");										// ????????????

			// ????????????????????
			FDCTransmissionHelper.isFitLength(getResource(ctx, "WorkLoadConfirmBillNumberIsNull"), workLoadConfirmBillNumber);
			// ????????????????????
			FDCTransmissionHelper.isFitLength(getResource(ctx, "CostAccountLongNumberIsNull"), entrysCostAccountLongNumber);
			// ????????????????????
			FDCTransmissionHelper.isFitLength(getResource(ctx, "AmountIsNull"), entrysAmount);
			
			if(!costAccountSet.add(workLoadConfirmBillNumber.trim()+entrysCostAccountLongNumber.trim())){
				FDCTransmissionHelper.isThrow(getResource(ctx, "EntryIsRepaet"));
			}

			if (arrMap.get(workLoadConfirmBillNumber.trim()) == null) {
				arrMap.put(workLoadConfirmBillNumber.trim(), new ArrayList());
			}

			BigDecimal decimal = this.str2BigDecimal(ctx, entrysAmount);
			((ArrayList) arrMap.get(workLoadConfirmBillNumber.trim())).add(decimal);
		}
	}
	
	
	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return PaymentSplitFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e);
		}
	}
	

	// ??????????????????
	public int getSubmitType() {
		return SUBMITMULTIRECTYPE;
	}
	
	// ????????????
	public boolean isSameBlock(Hashtable firstData, Hashtable currentData){
		return firstData != null && currentData != null ;
	}
	

	/** ???????? */
	public CoreBaseInfo transmit(Hashtable hsdata, Context ctx)
			throws TaskExternalException {
		initStateMap(ctx);
		initSplitStateMap(ctx);
		setArrMap(hsdata, ctx);
		
		return innerTransform(hsdata, ctx);
	}
	

	/** ???????????????? */
	protected CoreBaseInfo innerTransform(Hashtable hsdata, Context ctx) throws TaskExternalException {
		try {
			for (int i = 0; i < hsdata.size(); i++) {
				Hashtable lineData = (Hashtable) hsdata.get(new Integer(i));
				paymentSplitInfo = innerTransformHead(lineData, ctx,i);
				PaymentSplitEntryInfo entry = innerTransformEntry(lineData, ctx,i);
				int seq = paymentSplitInfo.getEntrys().size() + 1;
				entry.setSeq(seq);
				entry.setParent(paymentSplitInfo);
				paymentSplitInfo.getEntrys().add(entry);
			}

			// ????infoMAP
			Collection coll = infoMap.values();
			Iterator it = coll.iterator();
			while (it.hasNext()) {
				PaymentSplitInfo info = (PaymentSplitInfo) it.next();
				
				if (info.getId() == null || !getController(ctx).exists(new ObjectUuidPK(info.getId())))
					getController(ctx).save(info);
				else
					getController(ctx).update(new ObjectUuidPK(info.getId()), info);
			}
		} catch (TaskExternalException e) {
			paymentSplitInfo = null;
			throw e;
		} catch (EASBizException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		
		return null;
	}
	

	/** ??????*???????????? */
	private PaymentSplitInfo innerTransformHead(Hashtable hsdata, Context ctx,int t) throws TaskExternalException {
		
		String curProjectCostCenterLongNumber = getString(hsdata, "FCurProjectCostCenter_longNumber"); 			// ??????????
		String curProjectLongNumber = getString(hsdata, "FCurProject_longNumber"); 								// ????????????
//		String curProjectName = getString(hsdata, "FCurProject_name_l2"); 										// ????????????
		String contractBillNumber = getString(hsdata, "FContractBill_number");									// ????????
//		String contractBillName = getString(hsdata, "FContractBill_name");										// ????????
		String workLoadConfirmBillNumber = getString(hsdata, "FWorkLoadConfirmBill_number");					// ????????????????
//		String workLoadConfirmBillWorkLoad = getString(hsdata, "FWorkLoadConfirmBill_workLoad");				// ??????????
		String creatorNumber = getString(hsdata, "FCreator_number");											// ??????*
		String createTime = getString(hsdata, "FCreateTime");													// ????????*
		String auditorNumber = getString(hsdata, "FAuditor_number");											// ??????*
		String auditTime = getString(hsdata, "FAuditTime");														// ????????*
		String splitState = getString(hsdata, "FSplitState");													// ????????*
		String state = getString(hsdata, "FState");																// ????????*
		
		
		
		// ????????
		FDCTransmissionHelper.isFitLength(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "CurProjectLongNumberNotNull"), curProjectLongNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "ContractBillCodingNumberNotNull"), contractBillNumber);
		//FDCTransmissionHelper.isFitLength(getResource(ctx, "WorkLoadConfirmBillNumberIsNull"), workLoadConfirmBillNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "CreatorNumberIsNull"), creatorNumber);
		FDCTransmissionHelper.isFitLength(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "CreateTimeIsNull"), createTime);
		
		
		// ????????????
		Date createDate = FDCTransmissionHelper.checkDateFormat(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+createTime, getResource(ctx, "DateFormatIsError"));

		CostSplitStateEnum splitStateEnum = getSplitState((String) splitStateMap.get(splitState));
		FDCBillStateEnum stateEnum = getState((String) stateMap.get(state));
		
		Date auditDate = null;
		if (stateEnum.equals(FDCBillStateEnum.AUDITTED)) {
			FDCTransmissionHelper.isFitLength(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "AuditorNumberIsNull"), auditorNumber);
			FDCTransmissionHelper.isFitLength(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "AuditTimeIsNull"), auditTime);
			auditDate = FDCTransmissionHelper.checkDateFormat(auditTime, getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "DateFormatIsError"));
		}
		 
		/** ?????????????????????????????? */
		if (infoMap.get(workLoadConfirmBillNumber) != null) {
			return (PaymentSplitInfo) infoMap.get(workLoadConfirmBillNumber);
		}
		
		
		try{

			EntityViewInfo viewInfo = new EntityViewInfo();
		    FilterInfo filter = new FilterInfo();
		    filter.getFilterItems().add(new FilterItemInfo("number",workLoadConfirmBillNumber));
		    filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
		    
		    viewInfo.setFilter(filter);
			// ??????????????????????????
			WorkLoadConfirmBillCollection workLoadConfirmBillColl = WorkLoadConfirmBillFactory .getLocalInstance(ctx)
						.getWorkLoadConfirmBillCollection(viewInfo);
			if (!isExist(workLoadConfirmBillColl)) {
				FDCTransmissionHelper.isThrow(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "WorkLoadConfirmBillIsNotExist"));
				// FDCTransmissionHelper.isThrow("","????????????????????????????????????????");
			}
			workLoadConfirmBillInfo = workLoadConfirmBillColl.get(0);
			// ??????????????????????????????????
			workLoadConfirmBillWorkLoadDecimal = workLoadConfirmBillInfo.getWorkLoad();
			// ??????????????????????????????????????
			String workLoadConfirmBillId=getUid(workLoadConfirmBillInfo);
			PaymentSplitCollection paymentSplitColl = PaymentSplitFactory.getLocalInstance(ctx)
						.getPaymentSplitCollection(getEntityViewInfoInstance("workLoadConfirmBill", workLoadConfirmBillId));
			
			if (isExist(paymentSplitColl)) {
				 FDCTransmissionHelper.isThrow(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "WorkLoadHasSplit"));
				// return paymentSplitInfo = paymentSplitColl.get(0);
			}
			
			/// ????????????????????????????????
			ArrayList list=(ArrayList)arrMap.get(workLoadConfirmBillNumber.trim());
			BigDecimal d=new BigDecimal(0);
			for(int i=0;i<list.size();i++){
				d=d.add((BigDecimal)list.get(i));
			}
			if(d.compareTo(workLoadConfirmBillWorkLoadDecimal)!=0){
				FDCTransmissionHelper.isThrow(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "AmountIsNotEqualWorkLoadConfirmBillWorkLoad"));
			}
			
			
			String id = null;
			
			
			//??????????????????????//????????????????????????????????
			id = getUid(workLoadConfirmBillInfo.getCurProject());
			
			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(ctx)
										.getCurProjectCollection(getEntityViewInfoInstance("id", id));
			
			if (!isExist(curProjectColl)||!curProjectColl.get(0).getLongNumber().equalsIgnoreCase(curProjectLongNumber.trim().replace('.', '!'))) {
				FDCTransmissionHelper.isThrow(curProjectLongNumber, getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "CurProjectIsNotFound"));
			}
			curProjectInfo = curProjectColl.get(0);
			
			//??????????????
			CostCenterOrgUnitInfo costCenterOrgUnit = curProjectInfo.getCostCenter();
			
			if (!StringUtils.isEmpty(curProjectCostCenterLongNumber)) {
				id = getUid(costCenterOrgUnit);
				
				CostCenterOrgUnitCollection costCenterOrgUnitColl = CostCenterOrgUnitFactory
						.getLocalInstance(ctx).getCostCenterOrgUnitCollection(getEntityViewInfoInstance("id", id));
				
				if (!isExist(costCenterOrgUnitColl)
						|| !costCenterOrgUnitColl.get(0).getLongNumber()
								.equalsIgnoreCase(curProjectCostCenterLongNumber.trim().replace('.', '!'))) {
					FDCTransmissionHelper.isThrow(curProjectCostCenterLongNumber, getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "CostOrgLongNumberNotFound"));
				}
				costCenterOrgUnit = costCenterOrgUnitColl.get(0);
			}
			orgUnitInfo = costCenterOrgUnit.castToFullOrgUnitInfo();
			
			// ????????????????????
			getContractSplitAccountMap(curProjectInfo.getId().toString(), ctx, contractSplitAccountMap);
			
			// ??????????????????//????????????????????????
			id = getUid(workLoadConfirmBillInfo.getContractBill());
			ContractBillCollection contractBillColl = ContractBillFactory
					.getLocalInstance(ctx).getContractBillCollection(getEntityViewInfoInstance("id", id));
			
			if (!isExist(contractBillColl)||!contractBillColl.get(0).getNumber().equalsIgnoreCase(contractBillNumber.trim())) {
				FDCTransmissionHelper.isThrow(contractBillNumber, getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "ContractBillIsNotExist"));
			}
			contractBillInfo = contractBillColl.get(0);
			if(contractBillInfo.getSplitState().getValue().equals(CostSplitStateEnum.NOSPLIT_VALUE))
			{
				FDCTransmissionHelper.isThrow(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "ContractNotSplit"));
				// FDCTransmissionHelper.isThrow("","??????????????????????????");
			}		
			
			// ????????????????????
			UserCollection userColl = UserFactory.getLocalInstance(ctx)
					.getUserCollection(getEntityViewInfoInstance("number", creatorNumber));
			if (!isExist(userColl)) {
				FDCTransmissionHelper.isThrow(creatorNumber, getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "CreatorIsNotExist"));
			}
			creatorInfo = userColl.get(0);

			
			if (stateEnum.equals(FDCBillStateEnum.AUDITTED)) {
				// ????????????????????
				userColl = UserFactory.getLocalInstance(ctx).getUserCollection(getEntityViewInfoInstance("number", auditorNumber));
				if (!isExist(userColl)) {
					FDCTransmissionHelper.isThrow(auditorNumber,getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "AuditorIsNotExist"));
				}
				auditorInfo = userColl.get(0);
			}
			
		}catch (BOSException e) {
			e.printStackTrace();
		}
		
		paymentSplitInfo = new PaymentSplitInfo();
		
		paymentSplitInfo.setOrgUnit(orgUnitInfo);
		paymentSplitInfo.setCurProject(curProjectInfo);
		paymentSplitInfo.setWorkLoadConfirmBill(workLoadConfirmBillInfo);
		paymentSplitInfo.setAmount(workLoadConfirmBillWorkLoadDecimal);
		paymentSplitInfo.setCompletePrjAmt(workLoadConfirmBillWorkLoadDecimal);
		paymentSplitInfo.setContractBill(contractBillInfo);
		paymentSplitInfo.setCreator(creatorInfo);
		paymentSplitInfo.setCreateTime(new Timestamp(createDate.getTime()));
		paymentSplitInfo.setAuditor(auditorInfo);
		paymentSplitInfo.setAuditTime(auditDate);
		paymentSplitInfo.setSplitState(splitStateEnum);
		paymentSplitInfo.setState(stateEnum);
		paymentSplitInfo.setIsWorkLoadBill(true);
		
		infoMap.put(workLoadConfirmBillNumber, paymentSplitInfo);
		
		return paymentSplitInfo;
	}
	
	/** ????*???????????? */
	private PaymentSplitEntryInfo innerTransformEntry(Hashtable hsdata, Context ctx,int t) throws TaskExternalException {
		
		String entrysCostAccountCurProjectLongNumber = getString(hsdata, "FEntrysCostAccountCurProject_longNumber");	// ????????????????????
//		String entrysCostAccountCurProjectName = getString(hsdata, "FEntrysCostAccountCurProject_name_l2");				// ????????????????
		String entrysCostAccountLongNumber = getString(hsdata, "FEntrysCostAccount_longNumber");						// ????????????????
		entrysCostAccountLongNumber = entrysCostAccountLongNumber.replace('!', '.');
//		String entrysCostAccountName = getString(hsdata, "FEntrysCostAccount_name_l2");									// ????????????
		String entrysAmount = getString(hsdata, "FEntrys_amount");														// ????????????
		
		
		// ????????
		FDCTransmissionHelper.isFitLength(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "CostAccountCurProjectLongNumberIsNull"), entrysCostAccountCurProjectLongNumber);
		//FDCTransmissionHelper.isFitLength(getResource(ctx, "CostAccountLongNumberIsNull"), entrysCostAccountLongNumber);	
		//FDCTransmissionHelper.isFitLength(getResource(ctx, "AmountIsNull"), entrysAmount);	
		
		// ??entrysAmount??????BigDecimal??????????BigDecimal????????????????????
		BigDecimal amountDecimal = this.str2BigDecimal(ctx, entrysAmount);
		
		
		CostAccountInfo costAccountInfo=null;
		try {
			
			EntityViewInfo viewInfo = new EntityViewInfo();
		    FilterInfo filter = new FilterInfo();
		    filter.getFilterItems().add(new FilterItemInfo("curProject.id",curProjectInfo.getId().toString()));
		    filter.getFilterItems().add(new FilterItemInfo("codingnumber",entrysCostAccountLongNumber));
		    viewInfo.setFilter(filter);
			
			// ??????????????????????
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(ctx).getCostAccountCollection(viewInfo);
			if (!isExist(costAccountColl)) {
				 FDCTransmissionHelper.isThrow(entrysCostAccountLongNumber,getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "CostAccountIsNotExist"));
			}
			costAccountInfo = costAccountColl.get(0);
			
			
			//????????????????
			String id = getUid(costAccountInfo.getCurProject());
			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectCollection(getEntityViewInfoInstance("id", id));
			if (!isExist(curProjectColl) || !curProjectColl.get(0).getLongNumber().equalsIgnoreCase(entrysCostAccountCurProjectLongNumber.replace('.', '!'))) {
				FDCTransmissionHelper.isThrow(entrysCostAccountCurProjectLongNumber,getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "CostAccountCurProjectIsNotExist"));
			}
			
			// ??????????????????????????????????????
			HashMap contractSplitAccountMapTemp = (HashMap)contractSplitAccountMap.get(curProjectInfo.getId().toString() + contractBillInfo.getId().toString());
			if (contractSplitAccountMapTemp == null) {
				FDCTransmissionHelper.isThrow(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "PaymentSplit_Import_ContractSplitNotExist"));
			} else {
				ContractCostSplitEntryInfo  contractCostSplitEntryInfo = (ContractCostSplitEntryInfo)contractSplitAccountMapTemp.get(costAccountInfo.getId().toString());
				if (contractCostSplitEntryInfo == null) {
					FDCTransmissionHelper.isThrow(getResource(ctx, "Import_No")+t+getResource(ctx, "Import_Line")+getResource(ctx, "ContractSplitNotExist"));
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		
		PaymentSplitEntryInfo info=new PaymentSplitEntryInfo();
		info.setCostAccount(costAccountInfo);
		info.setAmount(amountDecimal);
		info.setIsLeaf(true);
		
		return info;
	}
	
	/** ?????????????? */
	private String getString(Hashtable hsdata, Object key) {
		return (String) ((DataToken) hsdata.get(key)).data;
	}
	
	/** ???????????????????????? */
	private EntityViewInfo getEntityViewInfoInstance(String property, String value) {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo(property, value, CompareType.EQUALS));
		EntityViewInfo view = new EntityViewInfo();
		view.setFilter(filter);

		return view;
	}
	
	/** ?????????????????????????????????? */
	private FDCBillStateEnum getState(String state) {
		FDCBillStateEnum stateEnum = null;
		if (state == null) state = "";

		if (FDCBillStateEnum.SAVED_VALUE.equalsIgnoreCase(state)) {
			stateEnum = FDCBillStateEnum.SAVED;
		} else {//????????????
			stateEnum = FDCBillStateEnum.AUDITTED;
		}

		return stateEnum;
	}

	/** ?????????????????????????????????? */
	private CostSplitStateEnum getSplitState(String splitState) {
		CostSplitStateEnum splitStateEnum = null;
		if (splitState == null) splitState = "";

		if (CostSplitStateEnum.NOSPLIT_VALUE.equalsIgnoreCase(splitState.trim())) {
			splitStateEnum = CostSplitStateEnum.NOSPLIT;
		} else {
			splitStateEnum = CostSplitStateEnum.ALLSPLIT;
		}

		return splitStateEnum;
	}
	
	/** ???????????? */
	private boolean isExist(IObjectCollection collection) {
		return (collection == null || collection.size() == 0) ? false : true;
	}
	
	/** ????Info??????ID?????? */
	private String getUid(CoreBaseInfo info) {
		BOSUuid uId = (info != null) ? info.getId() : null;
		return (uId != null) ? uId.toString() : "";
	}
	
	/** ?????? to BigDecimal???? */
	private BigDecimal str2BigDecimal(Context ctx, String amount) throws TaskExternalException {
		BigDecimal decimal = null;
		try {
			decimal = new BigDecimal(amount.trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			FDCTransmissionHelper.isThrow(amount, getResource(ctx, "NumberFormatError"));
		}
		return decimal;
	}

	/**
	 * ????????????
	 * @author ??????
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
	
	private void getContractSplitAccountMap(String curProjectID, Context ctx, Map contractSplitAccountMap) throws TaskExternalException{
		try {
			Map contractCostSplitEntryMap = new HashMap();
			ContractCostSplitInfo contractCostSplitInfo = null;
			ContractCostSplitEntryInfo contractCostSplitEntryInfo = null;
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectID));
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(filter);
			ContractCostSplitCollection info = ContractCostSplitFactory.getLocalInstance(ctx).getContractCostSplitCollection(view);
			for (int i = 0; i < info.size(); i++) {
				contractCostSplitInfo = info.get(i);
				ContractCostSplitEntryCollection contractCostSplitEntrys = contractCostSplitInfo.getEntrys();
				for (int j = 0; j < contractCostSplitEntrys.size(); j++) {
					contractCostSplitEntryInfo = contractCostSplitEntrys.get(j);
					contractCostSplitEntryMap.put(contractCostSplitEntryInfo.getCostAccount().getId().toString(), contractCostSplitEntryInfo);
				}
				contractSplitAccountMap.put(curProjectID + contractCostSplitInfo.getContractBill().getId().toString(), contractCostSplitEntryMap);
			}
			return ;
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		} catch (Exception e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
	}
}
