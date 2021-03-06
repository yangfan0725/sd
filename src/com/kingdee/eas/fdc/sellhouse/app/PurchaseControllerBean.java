package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillInfo;
import com.kingdee.eas.fdc.basecrm.RevListTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillCollection;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillFactory;
import com.kingdee.eas.fdc.sellhouse.FDCReceiveBillInfo;
import com.kingdee.eas.fdc.sellhouse.FunctionSetting;
import com.kingdee.eas.fdc.sellhouse.GenFdcTrasfBillFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseElsePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryFactory;
import com.kingdee.eas.fdc.sellhouse.PurchasePayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiptTypeStateEnum;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryCollection;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryFactory;
import com.kingdee.eas.fdc.sellhouse.ReceiveDistillCommisionEntryInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SellHouseException;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseCollection;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseStateEnum;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberFactory;
import com.kingdee.eas.fdc.tenancy.MarketingUnitMemberInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectCollection;
import com.kingdee.eas.fdc.tenancy.MarketingUnitSellProjectFactory;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.util.NumericExceptionSubItem;

public class PurchaseControllerBean extends AbstractPurchaseControllerBean {
	private static final String AUDITED = "1";
	private static final String UNAUDITED = "0";
	private static Logger logger = Logger
			.getLogger("com.kingdee.eas.fdc.sellhouse.app.PurchaseControllerBean");
	//???????????????? ???? ?????????????????? eric_wang 2010.08.12,?????????????? 2010.09.08
//	private RoomDisplaySetting setting = null;
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException,
			EASBizException {
		PurchaseInfo purchase = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(pk);
		RoomInfo info  = RoomFactory.getLocalInstance(ctx).getRoomInfo(new ObjectUuidPK(purchase.getRoom().getId().toString()));
		SheRoomStateFactory.setRoomOnShowState(ctx, info, purchase, false, false);
		super._delete(ctx, pk);
	}
	//??????????????????????????????????????????????????????????????????????
	public boolean isEnabledIsActGathering(Context ctx,PurchaseInfo purInfo){
		//by zgy  ????????????
		RoomDisplaySetting setting1 = new RoomDisplaySetting(ctx);
		HashMap functionSetMap = (HashMap)setting1.getFunctionSetMap();
		FunctionSetting funcSet = (FunctionSetting)functionSetMap.get(purInfo.getSellProject().getId().toString());
		/**
		 * ??????????????????????funcSet????????????????????????????
		 * new update by renliang at 2011-3-17
		 */
		
		if(funcSet!=null && funcSet.getIsActGathering()!=null){
			return funcSet.getIsActGathering().booleanValue();
		}
		return false;
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {		
		PurchaseInfo purchase = (PurchaseInfo) model;
		if (purchase.getPayType() == null) {
			throw new SellHouseException(SellHouseException.NOPAYTYPE);
		}
		PurchaseCustomerInfoCollection customerInfos = purchase
				.getCustomerInfo();
		for (int i = 0; i < customerInfos.size(); i++) {
			PurchaseCustomerInfoInfo customerInfoInfo = customerInfos.get(i);
			FDCCustomerInfo customer = customerInfoInfo.getCustomer();
			if (customer != null) {
				//---????????????????????????????????????????update(model)
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("postalcode");
				sels.add("phone");
				sels.add("certificateName");
				sels.add("certificateNumber");
				sels.add("mailAddress");
				sels.add("description");
				
				/***
				 * ???????? by xiaoao_liu
				 * at 2010-9-19
				 */
				sels.add("sex");
				FDCCustomerFactory.getLocalInstance(ctx).updatePartial(customer, sels);
//				FDCCustomerFactory.getLocalInstance(ctx).update(new ObjectUuidPK(customer.getId()), customer);
				//-------
				FDCCustomerFactory.getLocalInstance(ctx).addToSysCustomer(customer.getId().toString());
			}
		}
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) purchase
				.getSincerityPurchase();
		if (sin != null) {
			if (!sin.getSincerityState().equals(
					SincerityPurchaseStateEnum.ToPurchase)) {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sincerityState");
				sin.setSincerityState(SincerityPurchaseStateEnum.ToPurchase);
				SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
						sin, sels);
				RoomInfo room = sin.getRoom();
				if(room!=null)
				{
					SincerityPurchaseCollection sincerColl = SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseCollection("where room.id='"+room.getId().toString()+"' and " +
							" sincerityState='ArrangeNum' ");
					if(sincerColl!=null && sincerColl.size()>0)
					{
						for(int a=0;a<sincerColl.size();a++)
						{
							SincerityPurchaseInfo info = sincerColl.get(a);
							info.setSincerityState(SincerityPurchaseStateEnum.Invalid);
							SelectorItemCollection sec = new SelectorItemCollection();
							sec.add("sincerityState");
							SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
									info, sec);
						}
						
					}
				}
			}
		}
		//??purchaseState??????????????
		purchase.setPurchaseState(PurchaseStateEnum.PurchaseApply);
		IObjectPK objectPK = super._submit(ctx, purchase);
		if (purchase.getRoom().getSellState().equals(
				RoomSellStateEnum.PrePurchase) || purchase.getRoom().getSellState().equals(
						RoomSellStateEnum.Purchase)) {
			// ???????????????? ????????????????????????
			RoomStateFactory.setRoomSellState(ctx,
					null, purchase.getId().toString());
		}
		//????????????????????????????   by zgy 
		if(!isEnabledIsActGathering(ctx,purchase)){
			RoomInfo room = purchase.getRoom();
			SheRoomStateFactory.setRoomPurState(ctx, room, purchase, null);
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			purchase.setToPurchaseDate(purchase.getPurchaseDate());
			purUpdateSel.add("toPurchaseDate");
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();
			room.setToPurchaseDate(purchase.getPurchaseDate());
			roomUpdateSel.add("toPurchaseDate");
			RoomFactory.getLocalInstance(ctx).updatePartial(room, roomUpdateSel);
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, purUpdateSel);
			//????????????
			PurchasePayListEntryCollection entrys = PurchasePayListEntryFactory
					.getLocalInstance(ctx).getPurchasePayListEntryCollection(
							"select *,moneyDefine.moneyType where head.id='"+purchase.getId()+"' order by seq");
			purchase.getPayListEntry().clear();
			purchase.getPayListEntry().addCollection(entrys);				
			boolean isStillHasPrecont = false;  //????????????????????
			for (int i = 0; i < entrys.size(); i++) {
				PurchasePayListEntryInfo entry = entrys.get(i);
				if(entry.getAllRemainAmount().compareTo(new BigDecimal(0))>0){
					if(entry.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)){ 
						isStillHasPrecont = true;
						break;
					}
				}
			}
					//????????????????????????????????????????????????????
			if (isStillHasPrecont) {
				counteractPurchasePlan(ctx, purchase);
			}
			
			//????????????????????????????
			PurchasePayListEntryInfo firstEntryInfo = null;
			BigDecimal allRemainAmount = new BigDecimal(0);	//???????????? 
			PurchasePayListEntryCollection payListColl = purchase.getPayListEntry(); 
			CRMHelper.sortCollection(payListColl, "seq", true);
			for(int i=0;i<payListColl.size();i++) {
				PurchasePayListEntryInfo entryInfo = payListColl.get(i); 
				if(firstEntryInfo==null && !entryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))  {
					firstEntryInfo = entryInfo;
				}
				allRemainAmount = allRemainAmount.add(entryInfo.getAllRemainAmount());
			}
			if(firstEntryInfo!=null) {
				boolean isFirstFinish = false;
				if(firstEntryInfo.getActRevAmount().compareTo(new BigDecimal(0))>0) {
					if(firstEntryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.EarnestMoney)) { //????????????????????????
						if(firstEntryInfo.getActRevAmount().compareTo(firstEntryInfo.getAppAmount())>=0) 
							isFirstFinish = true;
					}else if(firstEntryInfo.getAllRemainAmount().compareTo(firstEntryInfo.getAppAmount())>=0){
						isFirstFinish = true;
					}
				}
				if(isFirstFinish){ //?????????????? ??????			
					String sql = "update T_SHE_Room set FReceiptTypeState ='FirstRe' where fid = '"+room.getId().toString()+"'";
					DbUtil.execute(ctx, sql);
				}else{  //??????????????   ??????
					if(allRemainAmount.compareTo(new BigDecimal(0))>0) {	//????????????
						String sql = "update T_SHE_Room set FReceiptTypeState = null where fid = '"+room.getId().toString()+"'";
						DbUtil.execute(ctx, sql);
					}else{	
						String sql = "update T_SHE_Room set FReceiptTypeState = null where fid = '"+room.getId().toString()+"'";
						DbUtil.execute(ctx, sql);
					}
				}
			}
		}	
		return objectPK;
	}

	protected IObjectPK _save(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		PurchaseInfo purchase = (PurchaseInfo) model;
		PurchaseCustomerInfoCollection customerInfos = purchase
				.getCustomerInfo();
		for (int i = 0; i < customerInfos.size(); i++) {
			PurchaseCustomerInfoInfo customerInfoInfo = customerInfos.get(i);
			FDCCustomerInfo customer = customerInfoInfo.getCustomer();
			if (customer != null) {
				FDCCustomerFactory.getLocalInstance(ctx).update(
						new ObjectUuidPK(customer.getId()), customer);
				FDCCustomerFactory.getLocalInstance(ctx).addToSysCustomer(customer.getId().toString());
			}
		}
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) purchase
				.getSincerityPurchase();
		if (sin != null) {
			if (!sin.getSincerityState().equals(
					SincerityPurchaseStateEnum.ToPurchase)) {				
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sincerityState");
				sin.setSincerityState(SincerityPurchaseStateEnum.ToPurchase);
				SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
						sin, sels);
				RoomInfo room = sin.getRoom();
				if(room!=null)
				{
					SincerityPurchaseCollection sincerColl = SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseCollection("where room.id='"+room.getId().toString()+"' and " +
							" sincerityState='ArrangeNum' ");
					if(sincerColl!=null && sincerColl.size()>0)
					{
						for(int a=0;a<sincerColl.size();a++)
						{
							SincerityPurchaseInfo info = sincerColl.get(a);
							info.setSincerityState(SincerityPurchaseStateEnum.Invalid);
							SelectorItemCollection sec = new SelectorItemCollection();
							sec.add("sincerityState");
							SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
									info, sec);
						}
						
					}
				}
			}
		}
		IObjectPK object = super._save(ctx, purchase);
		if (purchase.getRoom().getSellState().equals(
				RoomSellStateEnum.PrePurchase)) {
			RoomStateFactory.setRoomSellState(ctx,
					null, purchase.getId().toString());
		}
		purchase.setPurchaseState(PurchaseStateEnum.PrePurchaseApply);
//		//??????????????????????????????????????????????????????????????????????????????????
//		//????????????????????????????????????????????????????????????????
//		//????????????????????????????????????????????????????????????????
//		//????????????SourceFunction????????????????????????????????????????????????????????????????
		String papr = purchase.getSourceFunction();
		if(papr!=null&&papr.equals("false")){
			SheRoomStateFactory.setRoomPrePurState(ctx, purchase.getRoom(), purchase, null);
		}
		return object;

	}
	
	/**
	 * ????????????????????????????????
	 * */
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		PurchaseInfo purchase = (PurchaseInfo) model;
		purchase.setPurchaseState(PurchaseStateEnum.PrePurchaseApply);
		super._save(ctx, pk, model);
	}

	protected boolean isUseName() {
		return false;
	}

	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("isAfterAudit");
		sels.add("room.*");
		sels.add("payListEntry.*");
		sels.add("payListEntry.moneyDefine.*");
		
		PurchaseInfo purchase = (PurchaseInfo) this.getValue(ctx,new ObjectUuidPK(billId), sels);
		if(!purchase.getPurchaseState().equals(PurchaseStateEnum.PurchaseAudit))	{
			throw new ContractException(new NumericExceptionSubItem("100","????????????????????????????????????"));
		}
		
		RoomSignContractCollection signs = RoomSignContractFactory.getLocalInstance(ctx)
				.getRoomSignContractCollection("where purchase.id ='"+purchase.getId()+"' and state!='"+FDCBillStateEnum.INVALID_VALUE+"'");
		if (signs.size() > 0)	throw new ContractException(new NumericExceptionSubItem("100","????????????,????????????"));
	
		if(PurchaseChangeFactory.getLocalInstance(ctx).exists("where purchase.id = '"+purchase.getId()+"'"))
			throw new ContractException(new NumericExceptionSubItem("100","????????????????????????????????????????"));
		
/*		??????????????????????????????
 		if(PurchasePayListEntryFactory.getLocalInstance(ctx).exists("where head.id='"+purchase.getId()+"' and actRevAmount >0 "))
			throw new ContractException(new NumericExceptionSubItem("100","????????????????????????????????????????????????????????????"));
		if(PurchaseElsePayListEntryFactory.getLocalInstance(ctx).exists("where head.id='"+purchase.getId()+"' and actRevAmount >0 "))
			throw new ContractException(new NumericExceptionSubItem("100","????????????????????????????????????????????????????????????"));

*		???????????????????????????????????????????? xin_wang 2010.09.21
 */
 		if(PurchasePayListEntryFactory.getLocalInstance(ctx).exists("where head.id='"+purchase.getId()+"' and actRevAmount >0 ")
 				||PurchaseElsePayListEntryFactory.getLocalInstance(ctx).exists("where head.id='"+purchase.getId()+"' and actRevAmount >0 ")){
 			//update??????????????????????????????????????????true
 			SelectorItemCollection coll = new SelectorItemCollection();
 			coll.add("isAfterAudit");
 			purchase.setIsAfterAudit(true);
 			_updatePartial(ctx, purchase, coll);
 		}
			
		
			

		//????????????????????
		purchase.setPurchaseState(PurchaseStateEnum.PurchaseApply);
		SelectorItemCollection purUpdateSels = new SelectorItemCollection();
		purUpdateSels.add("purchaseState");
		_updatePartial(ctx, purchase, purUpdateSels);

		
		//---??????????????????????????????????????????????,?????????? _audit() ????????????  zhicheng_jin
		String setAudited = "update T_SHE_PurchaseCustomerInfo set FNumber=? where FHeadID=?";
		Object[] params = new Object[]{UNAUDITED, billId.toString()};
		DbUtil.execute(ctx, setAudited, params);
		
		super._unAudit(ctx, billId);
	}
	
	
	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
//		setting = new RoomDisplaySetting(ctx);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("room.*");
		PurchaseInfo purchase = (PurchaseInfo) this.getValue(ctx,new ObjectUuidPK(billId), sels);
		
		if(!PurchaseStateEnum.PurchaseAuditing.equals(purchase.getPurchaseState())){
			if(!PurchaseStateEnum.PurchaseApply.equals(purchase.getPurchaseState())){
				throw new ContractException(new NumericExceptionSubItem("100","??????????????????????????????????"));
			}
		}
		
		//????????????<????????????> eric_wang 2010.08.12 ???????????????? 2010.09.08
//		if(setting.getIsPreToOtherMoneyMap()!=null&&setting.getIsPreToOtherMoneyMap().get("isPreToOtherMoney")!=null&&(((Boolean)setting.getIsPreToOtherMoneyMap().get("isPreToOtherMoney")).booleanValue())){
				PurchasePayListEntryCollection entrys = PurchasePayListEntryFactory
				.getLocalInstance(ctx).getPurchasePayListEntryCollection(
						"select *,moneyDefine.moneyType where head.id='"+purchase.getId()+"' order by seq");
				purchase.getPayListEntry().clear();
				purchase.getPayListEntry().addCollection(entrys);
				
				boolean isHasRevMoney = false;		//??????
				boolean isStillHasPrecont = false;  //????????????????????
				for (int i = 0; i < entrys.size(); i++) {
					PurchasePayListEntryInfo entry = entrys.get(i);
					if(entry.getAllRemainAmount().compareTo(new BigDecimal(0))>0){
						isHasRevMoney = true;
						if(entry.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)){ 
							isStillHasPrecont = true;
							break;
						}
					}
				}
				//????????????????????????????????????????????????????
				if (isStillHasPrecont) {
					counteractPurchasePlan(ctx, purchase);
				}
			
				//??????????????????????????????????????
				//???????????????????? ???????????????? ????????????????????
				boolean isFullEast = false;
				PurchasePayListEntryInfo firstEntry = null;
				for(int i=0;i<entrys.size();i++) {
					if(!entrys.get(i).getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)) { 
						firstEntry = entrys.get(i);
						break;
					}
				}
				if(firstEntry!=null) {
					BigDecimal apAmount = firstEntry.getApAmount();
					if (firstEntry.getAllRemainAmount().compareTo(apAmount) >= 0)		isFullEast = true;
				}
				RoomSellStateEnum sellState = purchase.getRoom().getSellState();
				if (isFullEast) {   //??????????????????????????????/???? --> ????
					if(sellState.equals(RoomSellStateEnum.OnShow) || sellState.equals(RoomSellStateEnum.PrePurchase)) {
						SheRoomStateFactory.setRoomPurState(ctx, purchase.getRoom(), purchase, null);
					}
				}else if(isHasRevMoney) { //??????????  ?????????????? --> ????
					if(sellState.equals(RoomSellStateEnum.OnShow))	{ 
						SheRoomStateFactory.setRoomPrePurState(ctx, purchase.getRoom(), purchase, null);
					}			
				}
//		}
		
		//????????????????????
		purchase.setPurchaseState(PurchaseStateEnum.PurchaseAudit);
		SelectorItemCollection purUpateSels = new SelectorItemCollection();
		purUpateSels.add("purchaseState");
		_updatePartial(ctx, purchase, purUpateSels);
		
		//-------????????????????????????????,????????????????????????????????????????????
		String setAudited = "update T_SHE_PurchaseCustomerInfo set FNumber=? where FHeadID=?";
		Object[] params = new Object[]{AUDITED, billId.toString()};
		DbUtil.execute(ctx, setAudited, params);
		//-------------------
		
		super._audit(ctx, billId);
	}


	/**
	 * ????????????????????????????????????????????????????
	 * TODO ????????????????????????????
	 */
	private void counteractPurchasePlan(Context ctx, PurchaseInfo purchase)	throws BOSException, EASBizException {
		Date lastPreRevDate = null; //????????????????????????
		CoreBaseCollection coreBaseColl = new CoreBaseCollection();
		PurchasePayListEntryCollection preEntryColl = new PurchasePayListEntryCollection(); //????????????
		PurchasePayListEntryCollection payEntryColl = new PurchasePayListEntryCollection(); //??????????
		
		for(int i=0;i<purchase.getPayListEntry().size();i++) {
			PurchasePayListEntryInfo eyInfo = purchase.getPayListEntry().get(i);
			MoneyDefineInfo moDeInfo = eyInfo.getMoneyDefine();  //????????????????????
			if(moDeInfo!=null && moDeInfo.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)) {	
				preEntryColl.add(eyInfo);
				if(eyInfo.getActRevDate()!=null && (lastPreRevDate==null || eyInfo.getActRevDate().after(lastPreRevDate)))
					lastPreRevDate = eyInfo.getActRevDate();
			}
			else payEntryColl.add(eyInfo);
			coreBaseColl.add(eyInfo);
		}
		if(lastPreRevDate==null) lastPreRevDate = CRMHelper.getServerDate2(ctx); 

		if(preEntryColl.size()==0 || payEntryColl.size()==0) return;	//??????????????????????????????????????????????
		
		//????????????????????????????????
		FDCReceivingBillInfo fdcRevBillInfo = GenFdcTrasfBillFactory.genTrasfBill(ctx, preEntryColl.get(0).getId().toString()); 
		if(fdcRevBillInfo==null) return;
		
		for(int i=0;i<preEntryColl.size();i++) { //????????????????????????????
			PurchasePayListEntryInfo preEntryInfo = preEntryColl.get(i);
			BigDecimal preLeftAmount =  preEntryInfo.getAllRemainAmount(); //????????
			if(preLeftAmount.compareTo(FDCHelper.ZERO)<=0) continue;

			for(int j=0;j<payEntryColl.size();j++) {
				PurchasePayListEntryInfo payEntryInfo = payEntryColl.get(j);
				BigDecimal payAppAmount = payEntryInfo.getAppAmount();
				BigDecimal unPayAmount = payAppAmount.subtract(payEntryInfo.getAllRemainAmount());  //????????
				if(unPayAmount.compareTo(FDCHelper.ZERO)<=0) continue;  
				
				//?????? ??????oldEntry -x???? ,??????newEntry +x????
				if(preLeftAmount.compareTo(unPayAmount)>=0) {
					preEntryInfo.setHasTransferredAmount(preEntryInfo.getHasTransferredAmount().add(unPayAmount));
					payEntryInfo.setActRevAmount(payEntryInfo.getActRevAmount().add(unPayAmount));
					preLeftAmount = preLeftAmount.subtract(unPayAmount);
					
					GenFdcTrasfBillFactory.setTrasfEntry(ctx,fdcRevBillInfo,unPayAmount,null
										,payEntryInfo.getMoneyDefine(),null,payEntryInfo.getId().toString(),RevListTypeEnum.purchaseRev
										,preEntryInfo.getMoneyDefine(),preEntryInfo.getId().toString(),RevListTypeEnum.purchaseRev);
					
				}else{
					preEntryInfo.setHasTransferredAmount(preEntryInfo.getHasTransferredAmount().add(preLeftAmount));
					payEntryInfo.setActRevAmount(payEntryInfo.getActRevAmount().add(preLeftAmount));
					
					GenFdcTrasfBillFactory.setTrasfEntry(ctx,fdcRevBillInfo,preLeftAmount,null
											,payEntryInfo.getMoneyDefine(),null,payEntryInfo.getId().toString(),RevListTypeEnum.purchaseRev
											,preEntryInfo.getMoneyDefine(),preEntryInfo.getId().toString(),RevListTypeEnum.purchaseRev);
					
					preLeftAmount = new BigDecimal(0);
				}
				//payEntryInfo.setActRevDate(lastPreRevDate);
				
				if(preLeftAmount.compareTo(new BigDecimal(0))<=0) break;
			}
			
			if(preLeftAmount.compareTo(new BigDecimal(0))>0) {	//????????????????????????????????????????????????
				preEntryInfo.setLimitAmount(preEntryInfo.getLimitAmount().add(preLeftAmount));
				preEntryInfo.setIsRemainCanRefundment(true);
			}
		}
				
		GenFdcTrasfBillFactory.submitNewTrasfBill(ctx, fdcRevBillInfo, "com.kingdee.eas.fdc.sellhouse.app.SheRevNoHandle");
	}

	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		PurchaseInfo billInfo = ((PurchaseInfo) model);
		PurchaseStateEnum purchaseState = billInfo.getPurchaseState();
		if (purchaseState.equals(
				PurchaseStateEnum.ChangeRoomBlankOut)
				|| purchaseState.equals(
						PurchaseStateEnum.QuitRoomBlankOut)
				|| purchaseState.equals(
						PurchaseStateEnum.NoPayBlankOut)
				|| purchaseState.equals(
						PurchaseStateEnum.ManualBlankOut)
				|| purchaseState.equals(
						PurchaseStateEnum.AdjustBlankOut)) {
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("number", billInfo.getNumber()));
		
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.ChangeRoomBlankOut, CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.QuitRoomBlankOut, CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.NoPayBlankOut, CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.AdjustBlankOut, CompareType.NOTEQUALS));
		
		filter.getFilterItems().add(
				new FilterItemInfo("purchaseState",
						PurchaseStateEnum.ManualBlankOut, CompareType.NOTEQUALS));
		
		filter.getFilterItems()
				.add(
						new FilterItemInfo("orgUnit.id", billInfo.getOrgUnit()
								.getId()));
		
		if (billInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", billInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new ContractException(ContractException.NUMBER_DUP);
		}
	}

	// ????????
	protected void setPropsForBill(Context ctx, FDCBillInfo fDCBillInfo)
			throws EASBizException, BOSException {
		if (fDCBillInfo.getOrgUnit() == null) {
			FullOrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx)
					.castToFullOrgUnitInfo();
			fDCBillInfo.setOrgUnit(orgUnit);
		}
	}

	protected String getCurrentOrgId(Context ctx) {
		SaleOrgUnitInfo org = ContextUtil.getCurrentSaleUnit(ctx);
		String curOrgId = org.getId().toString();
		return curOrgId;
	}

	
	/**
	 * ????????
	 * */
	protected void _checkPrePurchase(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		for(int i=0; i<pks.length; i++){
			PurchaseInfo purchase = (PurchaseInfo) getValue(ctx, pks[i]);
			purchase.setPurchaseState(PurchaseStateEnum.PrePurchaseCheck);
			purchase.setPrePurchaseAuditor(ContextUtil.getCurrentUserInfo(ctx));
			purchase.setPrePurchaseAuditDate(new Date());
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("purchaseState");
			sels.add("prePurchaseAuditor");
			sels.add("prePurchaseAuditDate");
			_updatePartial(ctx, purchase, sels);
		}
	}

	/**
	 * ??????????
	 * */
	protected void _uncheckPrePurchase(Context ctx, IObjectPK[] pks) throws BOSException, EASBizException {
		for(int i=0; i<pks.length; i++){
			PurchaseInfo purchase = (PurchaseInfo) getValue(ctx, pks[i]);
			purchase.setPurchaseState(PurchaseStateEnum.PrePurchaseApply);
			purchase.setPrePurchaseAuditor(ContextUtil.getCurrentUserInfo(ctx));
			purchase.setPrePurchaseAuditDate(new Date());
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("purchaseState");
			sels.add("prePurchaseAuditor");
			sels.add("prePurchaseAuditDate");
			_updatePartial(ctx, purchase, sels);
		}
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		PurchaseInfo billInfo = new PurchaseInfo();
		billInfo.setId(billId);
		billInfo.setPurchaseState(PurchaseStateEnum.PurchaseAuditing);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("purchaseState");
		_updatePartial(ctx, billInfo, selector);
		
		super._setAudittingStatus(ctx, billId);
	}
	/**
	 * ??????????????????????????????????
	 * by renliang at 2010-11-17
	 */
	protected void _setSubmitStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		PurchaseInfo billInfo = new PurchaseInfo();
		billInfo.setId(billId);
		billInfo.setPurchaseState(PurchaseStateEnum.PurchaseApply);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("purchaseState");
		_updatePartial(ctx, billInfo, selector);
		
		super._setSubmitStatus(ctx, billId);
	}
	
	protected void _purDistillUpdate(Context ctx) throws BOSException {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("salesman.*");
		sel.add("sellProject");
		sel.add("room.sellState");
		sel.add("distillCommisionEntry.*");
		filterInfo.getFilterItems().add(new FilterItemInfo("state", "4AUDITTED"));
		view.setSelector(sel);
		view.setFilter(filterInfo);
		//?????????????????????? 
		PurchaseCollection purColl = PurchaseFactory.getLocalInstance(ctx).getPurchaseCollection(view);
		for(int j=0;j<purColl.size();j++)
		{
			PurchaseInfo purInfo = purColl.get(j);
			RoomInfo roomInfo = purInfo.getRoom();
			//????????????????????????????
			if(RoomSellStateEnum.Purchase.equals(roomInfo.getSellState()) ||
					RoomSellStateEnum.Sign.equals(roomInfo.getSellState()))
			{
				PurDistillCommisionEntryCollection purComColl = purInfo.getDistillCommisionEntry();
				if(purInfo.getSalesman()==null)
				{
					continue;
				}
				if(purInfo.getSellProject()==null)
				{
					continue;
				}
				UserInfo salesMan = purInfo.getSalesman();
				SellProjectInfo project = purInfo.getSellProject();
				//??????????????????????????????????????????????????????????????????????????????????????????????????????
				MarketingUnitInfo marketInfo = getMarketingUnit(ctx,salesMan,project,new Date(),true);
				int i=0;					
				//??????????????????????
				CoreBaseCollection purCommisionColl = new CoreBaseCollection(); 
				//??????????????????????????????????
				if(purComColl.size()>0)
				{				
					//????????????????????????????????????????????????????????????????
					//????????????????????????????????????????????????????????
					FilterInfo filter = new FilterInfo();
					filter.getFilterItems().add(
							new FilterItemInfo("head.id",purInfo.getId().toString()));
					try {
						PurDistillCommisionEntryFactory.getLocalInstance(ctx).delete(filter);
					} catch (EASBizException e) {
						e.printStackTrace();
					}
				}
				//????????????????????????????????????????????????????????????????????
				if(marketInfo==null)
				{
					continue;
				}
				//????????????????????????????????????
				setPurDisCommisionEntry(ctx, marketInfo, salesMan, purCommisionColl, purInfo, i);				
				
				//??????????????????????????????????????????????????????????????????????????????????????????????
				//??????????????????????????????????????????????????????????????????????????????????????????????
				//????????????????????????????????????????????????????????????????????????????????????????????????
				//????????????????
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().
				add(new FilterItemInfo("purchase.id",purInfo.getId().toString()));
				ev.setFilter(filter);
				
				ev.getSelector().add("*");
				ev.getSelector().add("distillCommisionEntry.*");
				FDCReceiveBillCollection fdcRecColl = FDCReceiveBillFactory.getLocalInstance(ctx).getFDCReceiveBillCollection(ev);
				for(int k=0;k<fdcRecColl.size();k++)
				{
					FDCReceiveBillInfo recInfo = fdcRecColl.get(k);
					EntityViewInfo ev2 = new EntityViewInfo();
					FilterInfo filter2 = new FilterInfo();
					filter2.getFilterItems().
					add(new FilterItemInfo("fdcreceivebill.id",recInfo.getId().toString()));
					filter2.getFilterItems().
					add(new FilterItemInfo("billStatus","12"));
					filter2.getFilterItems().
					add(new FilterItemInfo("billStatus","14"));
					filter2.setMaskString("#0 and (#1 or #2)");
					ev2.setFilter(filter2);						
/*			
 * ??????????????????????????		
 * 			ReceivingBillCollection receivingBillColl = ReceivingBillFactory.getLocalInstance(ctx).getReceivingBillCollection(ev2);
				
					//????????????????????????????????
					if(receivingBillColl.size()==0)
					{
						continue;
					}
					ReceivingBillInfo receivingBillInfo =receivingBillColl.get(0);
					if(BillStatusEnum.AUDITED.equals(receivingBillInfo.getBillStatus()) ||
						BillStatusEnum.RECED.equals(receivingBillInfo.getBillStatus()))
					{
						ReceiveDistillCommisionEntryCollection recComColl = recInfo.getDistillCommisionEntry();
						if(recComColl.size()>0)
						{
							FilterInfo filter3 = new FilterInfo();
							filter3.getFilterItems().add(
									new FilterItemInfo("head.id",recInfo.getId().toString()));
							try {
								ReceiveDistillCommisionEntryFactory.getLocalInstance(ctx).delete(filter3);
							} catch (EASBizException e) {
								e.printStackTrace();
							}						
						}							
						setRecDisCommisionEntry(ctx, recInfo, marketInfo, purInfo, recComColl);						
					}		*/			
				}
			}
		}
	}
	
	//??????????????????????????????????????
	private static void setRecDisCommisionEntry(Context ctx,FDCReceiveBillInfo recInfo,MarketingUnitInfo marketInfo,PurchaseInfo purInfo,ReceiveDistillCommisionEntryCollection recComColl) throws BOSException
	{
		PurchaseInfo purchaseInfo = recInfo.getPurchase();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("distillCommisionEntry.*");
		try {
			purInfo = PurchaseFactory.getLocalInstance(ctx).getPurchaseInfo(new ObjectUuidPK(purchaseInfo.getId()), sels);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		PurDistillCommisionEntryCollection purComEntryColl = purInfo.getDistillCommisionEntry();
		if(purComEntryColl.size()>0)
		{
			CoreBaseCollection recComEntryColl = new CoreBaseCollection();
			PurDistillCommisionEntryCollection purchaseComColl = purInfo.getDistillCommisionEntry();
			//ReceiveDistillCommisionEntryCollection recComColl = new ReceiveDistillCommisionEntryCollection();
			for(int m=0;m<purchaseComColl.size();m++)
			{
				PurDistillCommisionEntryInfo purComInfo = purchaseComColl.get(m);
				ReceiveDistillCommisionEntryInfo recComInfo = new ReceiveDistillCommisionEntryInfo();
				recComInfo.setSharePercent(purComInfo.getSharePercent());
				recComInfo.setTakePercentage(purComInfo.getTakePercentage());
				recComInfo.setDistillCommisionLevel(purComInfo.getDistillCommisionLevel());
				recComInfo.setIsAchieveCommision(purComInfo.isIsAchieveCommision());
				recComInfo.setUser(purComInfo.getUser());
				if(purComInfo.getDistillCommisionLevel()==0 && purComInfo.getMarketUnit()==null)
				{
					recComInfo.setMarketUnit(marketInfo);
				}else
				{
					recComInfo.setMarketUnit(purComInfo.getMarketUnit());
				}							
				recComInfo.setHead(recInfo);
				recComEntryColl.add(recComInfo);
			}
			try {
				ReceiveDistillCommisionEntryFactory.getLocalInstance(ctx).addnew(recComEntryColl);
			} catch (EASBizException e) {
				e.printStackTrace();
			}
		}
	}
	
	//??????????????????
	private static void setPurDisCommisionEntry(Context ctx,MarketingUnitInfo marketInfo,UserInfo salesMan,CoreBaseCollection purCommisionColl,PurchaseInfo purInfo,int i) throws BOSException
	{
		PurDistillCommisionEntryInfo purCommisionInfo = new PurDistillCommisionEntryInfo();
		purCommisionInfo.setUser(salesMan);
		//??????????0.??????????????????????
		purCommisionInfo.setSharePercent(new BigDecimal(0));
		//??????????????????????????????
		purCommisionInfo.setTakePercentage(new BigDecimal(100));
		//??????????????????????????????????????????????
		purCommisionInfo.setDistillCommisionLevel(i);
		//????????????,????????????????????,????????????????????????,
		//?????????????????????????????????????? 2009-11-09
		purCommisionInfo.setMarketUnit(marketInfo);
		//????????????,????????false
		purCommisionInfo.setIsAchieveCommision(false);
		purCommisionInfo.setHead(purInfo);
		purCommisionColl.add(purCommisionInfo);
		//????????????????????????????????????????????????????????????????????????????????????????
		//????????????????????????????????????????????????????????????????????????????????????????????
		List dutyList = getDutyPerson(ctx,marketInfo,new Date());
		if(dutyList.size()>0)
		{
			i++;
		}
		for(int k=0;k<dutyList.size();k++)
		{
			PurDistillCommisionEntryInfo purCommisionInfo2 = new PurDistillCommisionEntryInfo();
			MarketingUnitMemberInfo markMemberInfo = (MarketingUnitMemberInfo)dutyList.get(k);
			purCommisionInfo2.setUser(markMemberInfo.getMember());
			purCommisionInfo2.setSharePercent(new BigDecimal(0));
			purCommisionInfo2.setTakePercentage(markMemberInfo.getTakePercentage());
			purCommisionInfo2.setDistillCommisionLevel(i);
			purCommisionInfo2.setMarketUnit(markMemberInfo.getHead());
			purCommisionInfo2.setIsAchieveCommision(false);
			purCommisionInfo2.setHead(purInfo);
			purCommisionColl.add(purCommisionInfo2);
		}
		try {
			//??????????????????????????????????????????????????????????????????????????????						
			setDistillCommision(ctx,purCommisionColl,marketInfo,i,purInfo,new Date());	
			PurDistillCommisionEntryFactory.getLocalInstance(ctx).addnew(purCommisionColl);
		} catch (EASBizException e) {
			e.printStackTrace();
		}
	}
	
	//??????????????????????????????????????????????????????????????????????????????????????????????????????
	private static MarketingUnitInfo getMarketingUnit(Context ctx,UserInfo salesMan,SellProjectInfo project,Date toPurchaseDate,boolean isDate) throws BOSException
	{	
		Set marketIDSet = new HashSet();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();	
		view.getSelector().add("head.*");
		filter.getFilterItems().
		add(new FilterItemInfo("member.id",salesMan.getId().toString()));
		//		filter.getFilterItems()
		//		.add(new FilterItemInfo("sellProject.sellProject.id", project.getId().toString()));
		filter.getFilterItems()
		.add(new FilterItemInfo("isSellFunction",Boolean.TRUE));
		if(isDate)
		{
			//??????????????????????????????????????????
			filter.getFilterItems()
			.add(new FilterItemInfo("accessionDate",toPurchaseDate,CompareType.LESS_EQUALS));
			filter.getFilterItems()
			.add(new FilterItemInfo("dimissionDate",null));
			filter.getFilterItems().
			add(new FilterItemInfo("dimissionDate",toPurchaseDate,CompareType.GREATER_EQUALS));
			filter.setMaskString("#0 and #1 and #2 and (#3 or #4)");
		}
		
		view.setFilter(filter);

		MarketingUnitMemberCollection marketMemberColl = MarketingUnitMemberFactory.getLocalInstance(ctx).getMarketingUnitMemberCollection(view);
		for(int i=0;i<marketMemberColl.size();i++)
		{
			MarketingUnitInfo marketInfo = marketMemberColl.get(i).getHead();
			marketIDSet.add(marketInfo.getId().toString());
		}

		EntityViewInfo view2 = new EntityViewInfo();
		FilterInfo filter2 = new FilterInfo();
		view2.getSelector().add("head.*");
		view2.getSelector().add("head.member.*");
		filter2.getFilterItems().
		add(new FilterItemInfo("head.id",marketIDSet,CompareType.INCLUDE));
		filter2.getFilterItems()
		.add(new FilterItemInfo("sellProject.id", project.getId().toString()));
		view2.setFilter(filter2);
		MarketingUnitSellProjectCollection marketProjectColl = MarketingUnitSellProjectFactory.getLocalInstance(ctx).getMarketingUnitSellProjectCollection(view2);
		//		MarketingUnitCollection marketColl=MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitCollection(view);
		MarketingUnitInfo marketInfo = null;
		if(marketProjectColl.size()!=0)
		{
			marketInfo = new MarketingUnitInfo();
			marketInfo = marketProjectColl.get(0).getHead();
		}
		return marketInfo;
	}
	
	//????????????????????????????true????????????
	private static List getDutyPerson(Context ctx,MarketingUnitInfo marketInfo,Date toPurchaseDate) throws BOSException
	{
		List dutyList = new ArrayList();
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.getSelector().add("*");
		view.getSelector().add("member.*");
		view.getSelector().add("head.*");
		filter.getFilterItems().
		add(new FilterItemInfo("head.id",marketInfo.getId().toString()));
		filter.getFilterItems().
		add(new FilterItemInfo("isDuty",Boolean.TRUE));//????????????
		filter.getFilterItems().
		add(new FilterItemInfo("isSharePercent",Boolean.TRUE));//????????
		filter.getFilterItems()
		.add(new FilterItemInfo("accessionDate",toPurchaseDate,CompareType.LESS_EQUALS));//????????????????????????
		filter.getFilterItems().
		add(new FilterItemInfo("dimissionDate",null));//????????????????????????????????????????????????????
		filter.getFilterItems().
		add(new FilterItemInfo("dimissionDate",toPurchaseDate,CompareType.GREATER_EQUALS));
		filter.setMaskString("#0 and #1 and #2 and #3 and (#4 or #5)");
		view.setFilter(filter);

		MarketingUnitMemberCollection markMemberColl = MarketingUnitMemberFactory.
		getLocalInstance(ctx).getMarketingUnitMemberCollection(view);
		for(int i=0;i<markMemberColl.size();i++)
		{
			MarketingUnitMemberInfo markMemberInfo = markMemberColl.get(i);
			dutyList.add(markMemberInfo);
		}
		return dutyList;
	}
	
	//????????????????????????????????????????????????????????????????????????????????????????
	private static void setDistillCommision(Context ctx,CoreBaseCollection purCommisionColl,MarketingUnitInfo marketInfo,int i,PurchaseInfo purchaseInfo,Date toPurchaseDate) throws BOSException, EASBizException
	{
		if(marketInfo.getParent()==null)
		{
			return;
		}else
		{
			MarketingUnitInfo marketUnitInfo = marketInfo.getParent();
			SelectorItemCollection sels = new SelectorItemCollection();
			sels.add("*");
			sels.add("member.*");
			marketUnitInfo = MarketingUnitFactory.getLocalInstance(ctx).getMarketingUnitInfo(new ObjectUuidPK(marketUnitInfo.getId()), sels);
			List dutyList = getDutyPerson(ctx, marketUnitInfo,toPurchaseDate);
			if(dutyList.size()>0)
			{
				i++;
			}
			for(int k=0;k<dutyList.size();k++)
			{
				PurDistillCommisionEntryInfo purCommisionInfo2 = new PurDistillCommisionEntryInfo();
				MarketingUnitMemberInfo markMemberInfo = (MarketingUnitMemberInfo)dutyList.get(k);
				purCommisionInfo2.setUser(markMemberInfo.getMember());
				purCommisionInfo2.setSharePercent(new BigDecimal(0));
				purCommisionInfo2.setTakePercentage(markMemberInfo.getTakePercentage());
				purCommisionInfo2.setDistillCommisionLevel(i);
				purCommisionInfo2.setMarketUnit(markMemberInfo.getHead());
				purCommisionInfo2.setIsAchieveCommision(false);
				purCommisionInfo2.setHead(purchaseInfo);
				purCommisionColl.add(purCommisionInfo2);
			}
			setDistillCommision(ctx,purCommisionColl,marketUnitInfo,i,purchaseInfo,toPurchaseDate);
		}


	}
	
	protected void _submitPrePurchase(Context ctx, IObjectValue model) throws BOSException
	{
		// TODO ????????????????
		
	}

	protected void _auditPrePurchase(Context ctx, BOSUuid billId) throws BOSException
	{
		IObjectPK [] pk =  new ObjectUuidPK[1];
		pk[0] =  new ObjectUuidPK(billId);
		
		try
		{
			PurchaseFactory.getLocalInstance(ctx).checkPrePurchase(pk);
		} catch (EASBizException e)
		{
			throw new BOSException(e);
		}		
	}

	protected void _receiveBill(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		// TODO Auto-generated method stub
		
	}

	protected void _purAddMarket(Context ctx) throws BOSException,
			EASBizException {	
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add("salesman");
		sel.add("sellProject");
		sel.add("marketUnit");
		view.setSelector(sel);
		//???????????????? 
		PurchaseCollection purColl = PurchaseFactory.getLocalInstance(ctx).getPurchaseCollection(view);
		for(int i=0;i<purColl.size();i++)
		{
			PurchaseInfo purInfo = purColl.get(i);
			if(purInfo.getMarketUnit()==null)
			{
				if(purInfo.getSalesman()==null)
				{
					continue;
				}
				if(purInfo.getSellProject()==null)
				{
					continue;
				}
				MarketingUnitInfo marketInfo = getMarketingUnit(ctx,purInfo.getSalesman(),purInfo.getSellProject(),new Date(),false);
				if(marketInfo==null)
				{
					continue;
				}
				purInfo.setMarketUnit(marketInfo);
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("marketUnit");
				PurchaseFactory.getLocalInstance(ctx).updatePartial(purInfo, sels);
			}
		}
	}
	
	/**
	 * ??????????????????????????
	 */
	protected void _immediacySave(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		PurchaseInfo purchase = (PurchaseInfo) model;
		if (purchase.getId() == null
				|| !this._exists(ctx, new ObjectUuidPK(purchase.getId()))
				|| purchase.getNumber() == null
				|| purchase.getNumber().length() == 0) {
			handleIntermitNumber(ctx, purchase);
		}
		if (purchase.getPayType() == null) {
			throw new SellHouseException(SellHouseException.NOPAYTYPE);
		}
		PurchaseCustomerInfoCollection customerInfos = purchase
				.getCustomerInfo();
		for (int i = 0; i < customerInfos.size(); i++) {
			PurchaseCustomerInfoInfo customerInfoInfo = customerInfos.get(i);
			FDCCustomerInfo customer = customerInfoInfo.getCustomer();
			if (customer != null) {
				//---????????????????????????????????????????update(model)
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("postalcode");
				sels.add("phone");
				sels.add("certificateName");
				sels.add("certificateNumber");
				sels.add("mailAddress");
				sels.add("description");
				
				/***
				 * ???????? by xiaoao_liu
				 * at 2010-9-19
				 */
				sels.add("sex");
				FDCCustomerFactory.getLocalInstance(ctx).updatePartial(customer, sels);
//				FDCCustomerFactory.getLocalInstance(ctx).update(new ObjectUuidPK(customer.getId()), customer);
				//-------
				FDCCustomerFactory.getLocalInstance(ctx).addToSysCustomer(customer.getId().toString());
			}
		}
		SincerityPurchaseInfo sin = (SincerityPurchaseInfo) purchase
				.getSincerityPurchase();
		if (sin != null) {
			if (!sin.getSincerityState().equals(
					SincerityPurchaseStateEnum.ToPurchase)) {
				SelectorItemCollection sels = new SelectorItemCollection();
				sels.add("sincerityState");
				sin.setSincerityState(SincerityPurchaseStateEnum.ToPurchase);
				SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
						sin, sels);
				RoomInfo room = sin.getRoom();
				if(room!=null)
				{
					SincerityPurchaseCollection sincerColl = SincerityPurchaseFactory.getLocalInstance(ctx).getSincerityPurchaseCollection("where room.id='"+room.getId().toString()+"' and " +
							" sincerityState='ArrangeNum' ");
					if(sincerColl!=null && sincerColl.size()>0)
					{
						for(int a=0;a<sincerColl.size();a++)
						{
							SincerityPurchaseInfo info = sincerColl.get(a);
							info.setSincerityState(SincerityPurchaseStateEnum.Invalid);
							SelectorItemCollection sec = new SelectorItemCollection();
							sec.add("sincerityState");
							SincerityPurchaseFactory.getLocalInstance(ctx).updatePartial(
									info, sec);
						}
						
					}
				}
			}
		}
		//??purchaseState??????????????
		purchase.setPurchaseState(PurchaseStateEnum.PurchaseApply);
		EntityViewInfo purView=new EntityViewInfo();
		FilterInfo purFilter=new FilterInfo();
		purFilter.getFilterItems().add(new FilterItemInfo("id",purchase.getId().toString()));
		purView.setFilter(purFilter);
		PurchaseCollection purColl = PurchaseFactory.getLocalInstance(ctx)
			.getPurchaseCollection(purView);
		if(purColl!=null && purColl.size()>0){
			PurchaseFactory.getLocalInstance(ctx)
					.update(new ObjectUuidPK(purchase.getId().toString()), purchase);
		}else{
			PurchaseFactory.getLocalInstance(ctx).addnew(purchase);
		}
		
		if (purchase.getRoom().getSellState().equals(
				RoomSellStateEnum.PrePurchase)) {
			// ????????????????
			RoomStateFactory.setRoomSellState(ctx,
					null, purchase.getId().toString());
		}
		//????????????????????????????   by zgy 
		if(!isEnabledIsActGathering(ctx,purchase)){
			RoomInfo room = purchase.getRoom();
			SheRoomStateFactory.setRoomPurState(ctx, room, purchase, null);
			SelectorItemCollection purUpdateSel = new SelectorItemCollection();
			purchase.setToPurchaseDate(purchase.getPurchaseDate());
			purUpdateSel.add("toPurchaseDate");
			SelectorItemCollection roomUpdateSel = new SelectorItemCollection();
			room.setToPurchaseDate(purchase.getPurchaseDate());
			roomUpdateSel.add("toPurchaseDate");
			RoomFactory.getLocalInstance(ctx).updatePartial(room, roomUpdateSel);
			PurchaseFactory.getLocalInstance(ctx).updatePartial(purchase, purUpdateSel);
			//????????????
			PurchasePayListEntryCollection entrys = PurchasePayListEntryFactory
					.getLocalInstance(ctx).getPurchasePayListEntryCollection(
							"select *,moneyDefine.moneyType where head.id='"+purchase.getId()+"' order by seq");
			purchase.getPayListEntry().clear();
			purchase.getPayListEntry().addCollection(entrys);				
			boolean isStillHasPrecont = false;  //????????????????????
			for (int i = 0; i < entrys.size(); i++) {
				PurchasePayListEntryInfo entry = entrys.get(i);
				if(entry.getAllRemainAmount().compareTo(new BigDecimal(0))>0){
					if(entry.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)){ 
						isStillHasPrecont = true;
						break;
					}
				}
			}
					//????????????????????????????????????????????????????
			if (isStillHasPrecont) {
				counteractPurchasePlan(ctx, purchase);
			}
			
			//????????????????????????????
			PurchasePayListEntryInfo firstEntryInfo = null;
			BigDecimal allRemainAmount = new BigDecimal(0);	//???????????? 
			PurchasePayListEntryCollection payListColl = purchase.getPayListEntry(); 
			CRMHelper.sortCollection(payListColl, "seq", true);
			for(int i=0;i<payListColl.size();i++) {
				PurchasePayListEntryInfo entryInfo = payListColl.get(i); 
				if(firstEntryInfo==null && !entryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.PreconcertMoney))  {
					firstEntryInfo = entryInfo;
				}
				allRemainAmount = allRemainAmount.add(entryInfo.getAllRemainAmount());
			}
			if(firstEntryInfo!=null) {
				boolean isFirstFinish = false;
				if(firstEntryInfo.getActRevAmount().compareTo(new BigDecimal(0))>0) {
					if(firstEntryInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.EarnestMoney)) { //????????????????????????
						if(firstEntryInfo.getActRevAmount().compareTo(firstEntryInfo.getAppAmount())>=0) 
							isFirstFinish = true;
					}else if(firstEntryInfo.getAllRemainAmount().compareTo(firstEntryInfo.getAppAmount())>=0){
						isFirstFinish = true;
					}
				}
				if(isFirstFinish){ //?????????????? ??????			
					String sql = "update T_SHE_Room set FReceiptTypeState ='FirstRe' where fid = '"+room.getId().toString()+"'";
					DbUtil.execute(ctx, sql);
				}else{  //??????????????   ??????
					if(allRemainAmount.compareTo(new BigDecimal(0))>0) {	//????????????
						String sql = "update T_SHE_Room set FReceiptTypeState = null where fid = '"+room.getId().toString()+"'";
						DbUtil.execute(ctx, sql);
					}else{	
						String sql = "update T_SHE_Room set FReceiptTypeState = null where fid = '"+room.getId().toString()+"'";
						DbUtil.execute(ctx, sql);
					}
				}
			}
		}	
	}
}