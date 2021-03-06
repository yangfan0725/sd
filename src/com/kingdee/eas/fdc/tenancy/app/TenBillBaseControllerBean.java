package com.kingdee.eas.fdc.tenancy.app;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CtrlUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.finance.app.ProjectPeriodStatusUtil;
import com.kingdee.eas.fdc.tenancy.TenBillBaseInfo;
import com.kingdee.eas.fdc.tenancy.TenancyException;
import com.kingdee.eas.fi.gl.GlUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

public class TenBillBaseControllerBean extends AbstractTenBillBaseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.tenancy.app.TenBillBaseControllerBean");
    //??????????????
    protected String _getLogInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
        TenBillBaseInfo info = (TenBillBaseInfo)super._getValue(ctx,pk);
        String retValue = "";
        if(info.getNumber()!= null)
        {
            retValue = info.getNumber();
            if(info.getName()!=null){
            	retValue = retValue + " " + info.getName();
            }
        }
        return retValue;
    }

    protected IObjectPK _addnew(Context ctx , IObjectValue model) throws BOSException , EASBizException {
    	TenBillBaseInfo tenBillBaseInfo = ((TenBillBaseInfo) model);
    	
		//??????????????????
		trimName(tenBillBaseInfo);
		
		//??????????????????
		setProps(ctx, tenBillBaseInfo);
		
		//????????
		dealAmount(ctx, tenBillBaseInfo);
		
    	return super._addnew(ctx,model);
	}
    
    //????
	protected void _save(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		TenBillBaseInfo tenBillBaseInfo = ((TenBillBaseInfo) model);

		if(tenBillBaseInfo.getState() == null)
			tenBillBaseInfo.setState(FDCBillStateEnum.SAVED);

//		handleIntermitNumber(ctx,fDCBillInfo);//????????
		//??????????????????
		setPropsForBill(ctx, tenBillBaseInfo);

		//????????
		if (tenBillBaseInfo.getId() == null ||
				!this._exists(ctx, new ObjectUuidPK(tenBillBaseInfo.getId()))) {
			handleIntermitNumber(ctx, tenBillBaseInfo);
		}
		 
		//????????
		checkBill(ctx, model);

		//??????????????????
		trimName(tenBillBaseInfo);		 
		
		super._save(ctx, pk, tenBillBaseInfo);
	}
	
	//????
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		TenBillBaseInfo tenBillBaseInfo = ((TenBillBaseInfo) model);
		
		if(tenBillBaseInfo.getState() == null){
			tenBillBaseInfo.setState(FDCBillStateEnum.SAVED);
		}
//		handleIntermitNumber(ctx,fDCBillInfo);//????????
		
		//??????????????????
		setPropsForBill(ctx, tenBillBaseInfo);
	
		//????????
		 if (tenBillBaseInfo.getId() == null ||
				!this._exists(ctx, new ObjectUuidPK(tenBillBaseInfo.getId()))||
				tenBillBaseInfo.getNumber() == null || tenBillBaseInfo.getNumber().length()==0) {
			handleIntermitNumber(ctx, tenBillBaseInfo);
		}
		 
		//????????
		checkBill(ctx, model);
		
		//??????????????????
		trimName(tenBillBaseInfo);
		
		return super._save(ctx, tenBillBaseInfo);
	}

	//????
	protected void _submit(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		TenBillBaseInfo tenBillBaseInfo = ((TenBillBaseInfo) model);
		tenBillBaseInfo.setState(FDCBillStateEnum.SUBMITTED);
//		handleIntermitNumber(ctx,FDCBillInfo);//????????
		
		//??????????????????
		setPropsForBill(ctx, tenBillBaseInfo);
		
		//????????
		 if (tenBillBaseInfo.getId() == null ||
				!this._exists(ctx, new ObjectUuidPK(tenBillBaseInfo.getId()))) {
			handleIntermitNumber(ctx, tenBillBaseInfo);
		}
		 
		//????????
		checkBill(ctx, model);
		
		//??????????????????
		trimName(tenBillBaseInfo);				 
			
		super._submit(ctx, pk, tenBillBaseInfo);
	}

	//????
	protected IObjectPK _submit(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {
		TenBillBaseInfo tenBillBaseInfo = ((TenBillBaseInfo) model);
		tenBillBaseInfo.setState(FDCBillStateEnum.SUBMITTED);
//		handleIntermitNumber(ctx,FDCBillInfo);//????????
		
		//??????????????????
		setPropsForBill(ctx, tenBillBaseInfo);
		
		//????????
		 if (tenBillBaseInfo.getId() == null ||
				!this._exists(ctx, new ObjectUuidPK(tenBillBaseInfo.getId()))) {
			handleIntermitNumber(ctx, tenBillBaseInfo);
		}
		 
		//????????
		checkBill(ctx, model);
		
		//??????????????????
		trimName(tenBillBaseInfo);
		
		return super._submit(ctx, tenBillBaseInfo);
	}

	//??????????????????????????????????????????????????????????????????????????????????
	protected void setPropsForBill(Context ctx, TenBillBaseInfo tenBillBaseInfo) throws EASBizException, BOSException {
		if(tenBillBaseInfo.getOrgUnit() == null) {
			FullOrgUnitInfo orgUnit = ContextUtil.getCurrentSaleUnit(ctx).castToFullOrgUnitInfo();
			tenBillBaseInfo.setOrgUnit(orgUnit);
		}
		if(tenBillBaseInfo.getCU() == null) {
			CtrlUnitInfo currentCtrlUnit = ContextUtil.getCurrentCtrlUnit(ctx);
			tenBillBaseInfo.setCU(currentCtrlUnit);
		}		
	}
	
	//????????
	protected void dealAmount(Context ctx, TenBillBaseInfo tenBillBaseInfo) throws EASBizException, BOSException {
//		if(tenBillBaseInfo.getOriginalAmount()==null ){
//			tenBillBaseInfo.setOriginalAmount(tenBillBaseInfo.getAmount());
//		}
	}
	
	/*protected void _addnew(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);
		handleIntermitNumber(ctx,fDCBillInfo);//????????
		setPropsForBill(ctx, fDCBillInfo);

		checkBill(ctx, model);

		trimName(fDCBillInfo);
		
		super._addnew(ctx, pk, fDCBillInfo);
	}

	protected IObjectPK _addnew(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		FDCBillInfo fDCBillInfo = ((FDCBillInfo) model);
		handleIntermitNumber(ctx,fDCBillInfo);//????????
		setPropsForBill(ctx, fDCBillInfo);

		checkBill(ctx, model);
		
		trimName(fDCBillInfo);
		
		return super._addnew(ctx, fDCBillInfo);
	}*/

	/**
	 * ??????????????????
	 * @param fDCBillInfo
	 */
	protected void trimName(TenBillBaseInfo tenBillBaseInfo) {
		if(tenBillBaseInfo.getName() != null) {
			tenBillBaseInfo.setName(tenBillBaseInfo.getName().trim());
			
			if(tenBillBaseInfo.getName().length()>40) {
				tenBillBaseInfo.setName(tenBillBaseInfo.getName().substring(0,40));
			}
		}

	}

	protected void _update(Context ctx, IObjectPK pk, IObjectValue model)
			throws BOSException, EASBizException {
		//????????,????????????,????????submit,??submit??????
//		checkBill(ctx, model);
//		
//		trimName((FDCBillInfo)model);
		super._update(ctx, pk, model);
	}

	/**
	 * ??????????????
	 */
	protected void _audit(Context ctx, List idList) throws BOSException,
			EASBizException {

		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();

			audit(ctx, BOSUuid.read(id));
		}

	}

	/**
	 * ????????????
	 */
	protected void _unAudit(Context ctx, List idList) throws BOSException,
			EASBizException {
		for (Iterator iter = idList.iterator(); iter.hasNext();) {
			String id = (String) iter.next();

			unAudit(ctx, BOSUuid.read(id));

		}

	}
	
	/**
	 * 
	 * ??????????????????
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd ??????????2006-8-24
	 *               <p>
	 */
	protected void checkNameDup(Context ctx, TenBillBaseInfo tenBillBaseInfo)
			throws BOSException, EASBizException {
		if(!isUseName()) return;
		
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("name", tenBillBaseInfo.getName()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
		filter.getFilterItems()
				.add(new FilterItemInfo("orgUnit.id", tenBillBaseInfo.getOrgUnit()
								.getId()));
		if (tenBillBaseInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", tenBillBaseInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new TenancyException(TenancyException.NAME_DUP);
		}
	}

	/**
	 * 
	 * ??????????????????
	 * 
	 * @param ctx
	 * @param billInfo
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd ??????????2006-8-24
	 *               <p>
	 */
	private void checkNumberDup(Context ctx, TenBillBaseInfo tenBillBaseInfo)
			throws BOSException, EASBizException {
		if(!isUseNumber()) return;
		FilterInfo filter = new FilterInfo();

		filter.getFilterItems().add(
				new FilterItemInfo("number", tenBillBaseInfo.getNumber()));
		filter.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID,CompareType.NOTEQUALS));		
		filter.getFilterItems()
				.add(new FilterItemInfo("orgUnit.id", tenBillBaseInfo.getOrgUnit().getId()));
		if (tenBillBaseInfo.getId() != null) {
			filter.getFilterItems().add(
					new FilterItemInfo("id", tenBillBaseInfo.getId().toString(),
							CompareType.NOTEQUALS));
		}

		if (_exists(ctx, filter)) {
			throw new TenancyException(TenancyException.NUMBER_DUP);
		}
	}

	/**
	 * 
	 * ??????????????
	 * 
	 * @param ctx
	 * @param model
	 * @throws BOSException
	 * @throws EASBizException
	 * @author:liupd ??????????2006-10-13
	 *               <p>
	 */
	protected void checkBill(Context ctx, IObjectValue model)
			throws BOSException, EASBizException {

		TenBillBaseInfo tenBillBaseInfo = ((TenBillBaseInfo) model);

		checkNumberDup(ctx, tenBillBaseInfo);

		checkNameDup(ctx, tenBillBaseInfo);

	}
	
	//??????????????
	private void checkBillForAudit(Context ctx, BOSUuid billId,TenBillBaseInfo tenBillBaseInfo)throws BOSException, EASBizException {
		
	}
	
	//????????????????
	private void checkBillForUnAudit(Context ctx, BOSUuid billId,TenBillBaseInfo tenBillBaseInfo)throws BOSException, EASBizException {
		
	}

	protected void _audit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		
		TenBillBaseInfo tenBillBaseInfo = new TenBillBaseInfo();
		tenBillBaseInfo.setId(billId);
		tenBillBaseInfo.setState(FDCBillStateEnum.AUDITTED);
		tenBillBaseInfo.setAuditor(ContextUtil.getCurrentUserInfo(ctx));
		tenBillBaseInfo.setAuditTime(new Date());
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");
		
		//??????????????
		checkBillForAudit( ctx,billId,tenBillBaseInfo);

		_updatePartial(ctx, tenBillBaseInfo, selector);
	}

	protected void _setAudittingStatus(Context ctx, BOSUuid billId)
			throws BOSException, EASBizException {
		
		setBillStatus(ctx, billId, FDCBillStateEnum.AUDITTING);

	}

	//??????
	protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException,
			EASBizException {
		TenBillBaseInfo tenBillBaseInfo = new TenBillBaseInfo();

		tenBillBaseInfo.setId(billId);
		tenBillBaseInfo.setState(FDCBillStateEnum.SUBMITTED);
		tenBillBaseInfo.setAuditor(null);
		tenBillBaseInfo.setAuditTime(null);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		selector.add("auditor");
		selector.add("auditTime");

		//??????????????
		checkBillForUnAudit( ctx,billId,tenBillBaseInfo);
		
		_updatePartial(ctx, tenBillBaseInfo, selector);
	}

	
	/**
	 * 
	 * ??????????????
	 * @return
	 * @author:liupd
	 * ??????????2006-10-17 <p>
	 */
	protected boolean isCost() {
		return true;
	}
	
	/**
	 * 
	 * ??????????????????????
	 * @return
	 * @author:liupd
	 * ??????????2006-10-17 <p>
	 */
	protected boolean isUseName() {
		return true;
	}
	
	/**
	 * 
	 * ??????????????????????
	 * @return
	 * @author:liupd
	 * ??????????2006-10-17 <p>
	 */
	protected boolean isUseNumber() {
		return true;
	}
	
	
	/**
	 * ????
	 * @author sxhong  		Date 2006-11-25
	 */
	protected void handleIntermitNumber(Context ctx, TenBillBaseInfo info) throws BOSException, CodingRuleException, EASBizException
	{
//		String orgId = ContextUtil.getCurrentCostUnit(ctx).getId().toString();
//		String bindingProperty = "contractType.number";
//		// ????????????????????????????????????????????????
//		if (orgId == null || orgId.trim().length() == 0) {			
//			orgId = OrgConstants.DEF_CU_ID;
//		}
//		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
//		
//		// ??ID??????????????????
//		if (orgId != null && orgId.trim().length() > 0
//				&& iCodingRuleManager.isExist(info, orgId, bindingProperty)) {			
//			// ??????????"??????????"
//			if (iCodingRuleManager.isUseIntermitNumber(info, orgId, bindingProperty)) {
//				String numberTemp = iCodingRuleManager.getNumber(info, orgId, bindingProperty, "");
//				info.setNumber(numberTemp);				
//			} else if (iCodingRuleManager.isAddView(info, orgId, bindingProperty)){				
//				// ??????????????????,????????????????
//				if (iCodingRuleManager.isModifiable(info, orgId, bindingProperty)) {					
//					iCodingRuleManager.checkModifiedNumber(info, orgId,info.getNumber(), bindingProperty);
//				}				
//			} else {
//				// ??????????,??????????,????????,????????number??,????????number
//				String numberTemp = iCodingRuleManager.getNumber(info,orgId, bindingProperty, "");
//				info.setNumber(numberTemp);
//			}
//		}		
				
		
		//??????????????????????????????,????????????????
		if(info.getNumber() != null && info.getNumber().length() > 0) return;
		
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
		
		//??????????????????
		String costUnitId= info.getOrgUnit().getId().toString();
			//ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
		
       if(StringUtils.isEmpty(costUnitId)){
    	   return;
       }
       boolean isExist = true;
       if (!iCodingRuleManager.isExist(info, costUnitId)){
    	   costUnitId = ContextUtil.getCurrentOrgUnit(ctx).getId().toString();
      		if (!iCodingRuleManager.isExist(info, costUnitId)){
      			isExist = false; 
           }
      }
       if(isExist){
    	   // ??????????????????????????????????,??????????????
           if (iCodingRuleManager.isUseIntermitNumber(info, costUnitId) || !iCodingRuleManager.isAddView(info, costUnitId))
           // ??????orgId??????1????orgId??????????????????????????????????????
           {
               // ????????????????????????????????????????????????????????????????
               String number = iCodingRuleManager.getNumber(info,costUnitId);
               info.setNumber(number);
           }
       }
	}

	/**
	 * ????????????(??????????????????????)
	 */
	protected void _setSubmitStatus(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		setBillStatus(ctx, billId, FDCBillStateEnum.SUBMITTED);
		
	}

	private void setBillStatus(Context ctx, BOSUuid billId, FDCBillStateEnum state) throws BOSException, EASBizException {
		TenBillBaseInfo tenBillBaseInfo = new TenBillBaseInfo();

		tenBillBaseInfo.setId(billId);
		tenBillBaseInfo.setState(state);
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");

		_updatePartial(ctx, tenBillBaseInfo, selector);
	}
	
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
	
		recycleNumber(ctx, pk);
		
		super._delete(ctx, pk);
		
	}

	protected void _delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException {

		for (int i = 0; i < arrayPK.length; i++) {
			recycleNumber(ctx, arrayPK[i]);
		}
		
		super._delete(ctx, arrayPK);

	}
	
	/**
	 * ????Number??????????????????????????????????
	 * @param ctx
	 * @param pk
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws CodingRuleException
	 */
	protected void recycleNumber(Context ctx, IObjectPK pk) throws BOSException, EASBizException, CodingRuleException {
		TenBillBaseInfo tenBillBaseInfo = (TenBillBaseInfo)_getValue(ctx, pk);
		SaleOrgUnitInfo currentSaleUnit = ContextUtil.getCurrentSaleUnit(ctx);
		String curOrgId = currentSaleUnit.getId().toString();
		if(tenBillBaseInfo.getNumber()!=null&&tenBillBaseInfo.getNumber().length()>0){
			ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getLocalInstance(ctx);
	        if (iCodingRuleManager.isExist(tenBillBaseInfo, curOrgId) && iCodingRuleManager.isUseIntermitNumber(tenBillBaseInfo, curOrgId)) {
	            iCodingRuleManager.recycleNumber(tenBillBaseInfo, curOrgId, tenBillBaseInfo.getNumber());
	        }
		}
	}

	/**
	 * ????
	 */
	protected void _cancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		setBillStatus(ctx, BOSUuid.read(pk.toString()), FDCBillStateEnum.INVALID);
	}

	/**
	 * ????
	 */
	protected void _cancelCancel(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		setBillStatus(ctx, BOSUuid.read(pk.toString()), FDCBillStateEnum.AUDITTED);
		
	}
	
	/**
	 * 
	 * ??????????????
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.app.AbstractFDCBillControllerBean#_audit(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected void _cancel(Context ctx, IObjectPK[] pkArray) throws BOSException,
			EASBizException {

		for (int i = 0; i < pkArray.length; i++) {
			
			_cancel(ctx, pkArray[i]);

		}

	}
	
	/**
	 * 
	 * ??????????????
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.app.AbstractFDCBillControllerBean#_audit(com.kingdee.bos.Context,
	 *      java.util.List)
	 */
	protected void _cancelCancel(Context ctx, IObjectPK[] pkArray) throws BOSException,
			EASBizException {

		for (int i = 0; i < pkArray.length; i++) {

			_cancelCancel(ctx, pkArray[i]);

		}

	}

	//??????????????????????????????
	protected Map _fetchInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
		Map initMap = new HashMap();
		
		OrgUnitInfo orgUnit = ContextUtil.getCurrentOrgUnit(ctx);
		
		//????????
		String projectId = (String) paramMap.get("projectId");
		
		CurProjectInfo curProjectInfo = null;
		initProject( ctx,  paramMap, initMap);
		if(initMap.get(FDCConstants.FDC_INIT_PROJECT)!=null){
			curProjectInfo = (CurProjectInfo)initMap.get(FDCConstants.FDC_INIT_PROJECT);
			projectId = curProjectInfo.getId().toString();
			paramMap.put("projectId",projectId);
			initMap.put("projectId",projectId);
		}
		
		//??????????????????
		String orgUnitId = null;
		if(curProjectInfo!=null &&  curProjectInfo.getCostCenter()!=null){
			orgUnitId = curProjectInfo.getCostCenter().getId().toString();
		}else{
			orgUnitId = orgUnit.getId().toString();
		}
		
		//????????????		
		FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getLocalInstance(ctx)
			.getFullOrgUnitInfo(new ObjectUuidPK(orgUnitId));
		
		initMap.put(FDCConstants.FDC_INIT_ORGUNIT,orgUnitInfo);
		
		String comId = null;
		if(curProjectInfo!=null){
			comId = curProjectInfo.getFullOrgUnit().getId().toString();
		}else{
			comId = ContextUtil.getCurrentFIUnit(ctx).getId().toString();
		}
		
		//????????
		CompanyOrgUnitInfo company =GlUtils.getCurrentCompany(ctx,comId,null,false);		
		initMap.put(FDCConstants.FDC_INIT_COMPANY,company);
		
		//??????
		initMap.put(FDCConstants.FDC_INIT_CURRENCY,company.getBaseCurrency());
		
		//????????
		if(projectId!=null ){
			if( paramMap.get("isCost")==null){
				initPeriod( ctx, projectId, curProjectInfo,comId, initMap,true );
			}else{
				initPeriod( ctx, projectId,curProjectInfo,comId,  initMap,false );
			}
		}
	
		return initMap;
	}
	
	//??????????????
	protected void  initProject(Context ctx, Map paramMap,Map initMap) throws EASBizException, BOSException{
		String projectId = (String) paramMap.get("projectId");
		CurProjectInfo curProjectInfo = null;		
		if(projectId != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add("isLeaf");
			curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId),sic);
//			if(!curProjectInfo.isIsLeaf()){
//				projectId = curProjectInfo.getId().toString();
//				paramMap.put("projectId",projectId);
//			}
		}
		if(( projectId == null ||  !curProjectInfo.isIsLeaf() )&& paramMap.get("contractBillId")!=null) {
			//????????
			String contractBillId = (String)paramMap.get("contractBillId");
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add("*");
			selector.add("curProject.name");
			selector.add("curProject.number");
			selector.add("curProject.longNumber");
			selector.add("curProject.displayName");
			selector.add("curProject.parent.id");
			selector.add("curProject.fullOrgUnit.name");
			selector.add("curProject.CU.name");
			selector.add("curProject.CU.number");
			selector.add("currency.number");
			selector.add("currency.name");
			selector.add("respDept.number");
			selector.add("respDept.name");
			selector.add("partB.number");
			selector.add("partB.name");
			
			BOSObjectType  contractType=new ContractBillInfo().getBOSType();
			BOSObjectType  noTextContractType=new ContractWithoutTextInfo().getBOSType();
			if(BOSUuid.read(contractBillId).getType().equals(contractType)){
				ContractBillInfo contractBill = ContractBillFactory.getLocalInstance(ctx).
				getContractBillInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				//????????
				projectId = (String) contractBill.getCurProject().getId().toString();
			}else if(BOSUuid.read(contractBillId).getType().equals(noTextContractType)){
				ContractWithoutTextInfo contractBill = ContractWithoutTextFactory.getLocalInstance(ctx).
				getContractWithoutTextInfo(new ObjectUuidPK(BOSUuid.read(contractBillId)),selector);
				//initMap.put(FDCConstants.FDC_INIT_CONTRACT,contractBill);
				
				//????????
				projectId = (String) contractBill.getCurProject().getId().toString();
			}			
		}
		
		if(projectId != null) {
			curProjectInfo = CurProjectFactory.getLocalInstance(ctx).getCurProjectInfo(new ObjectUuidPK(projectId));
		}
		initMap.put(FDCConstants.FDC_INIT_PROJECT,curProjectInfo);
	}
	
	//??????????
	protected void  initPeriod(Context ctx,String projectId, CurProjectInfo curProjectInfo,String comId,Map initMap,boolean isCost ) throws EASBizException, BOSException{
		
		//????
		Date bookedDate = new Date();
		
		//??????????????????
		boolean isInCore = FDCUtils.IsInCorporation( ctx, comId);
		
		if(isInCore){
			//??????????????
			boolean isFreeze = FDCUtils.isFreeze(ctx,projectId,isCost);
			initMap.put(FDCConstants.FDC_INIT_ISFREEZE,Boolean.valueOf(isFreeze));
			
			//????
			ProjectPeriodStatusUtil._save(ctx,new ObjectUuidPK(projectId),curProjectInfo);
			PeriodInfo bookedPeriod = FDCUtils.getCurrentPeriod(ctx,projectId,isCost);
			
			if(bookedPeriod!=null){
				initMap.put(FDCConstants.FDC_INIT_BOOKEDPERIOD,bookedPeriod);		
				
				PeriodInfo curPeriod = null;
				if(isFreeze){
					curPeriod = PeriodUtils.getNextPeriodInfo(ctx,bookedPeriod);
				}else{
					curPeriod = bookedPeriod;
				}
				
				initMap.put(FDCConstants.FDC_INIT_PERIOD,curPeriod);
				
	
				if(bookedDate.before(curPeriod.getBeginDate())){
					bookedDate = curPeriod.getBeginDate();
				}
				if(bookedDate.after(curPeriod.getEndDate())){
					bookedDate = curPeriod.getEndDate();
				}		
	
			}
		}else{
			//????
			PeriodInfo bookedPeriod = PeriodUtils.getPeriodInfo(ctx ,bookedDate ,new ObjectUuidPK(comId));
			initMap.put(FDCConstants.FDC_INIT_PERIOD,bookedPeriod);
			initMap.put(FDCConstants.FDC_INIT_BOOKEDPERIOD,bookedPeriod);		
		}
		
		initMap.put(FDCConstants.FDC_INIT_DATE,bookedDate);
	}
	
	//????????????????????????
	protected Map _fetchFilterInitData(Context ctx, Map paramMap) throws BOSException, EASBizException {
	
		return null;
	}

	//????????????
	protected boolean _checkCanSubmit(Context ctx, String id) throws BOSException, EASBizException {

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add("state");
		if(_exists(ctx, new ObjectUuidPK(id))){
			TenBillBaseInfo tenBillBaseInfo = (TenBillBaseInfo)this.getValue(ctx,new ObjectUuidPK(id),selector);
			if(FDCBillStateEnum.AUDITTED.equals(tenBillBaseInfo.getState())
					|| FDCBillStateEnum.AUDITTING.equals(tenBillBaseInfo.getState())){
				return false;	
			}else{
				return true;
			}
		}
		
		return true;
	}

	//??????????????????,??????????????????????????????
	protected void setProps (Context ctx, TenBillBaseInfo tenBillBaseInfo) throws EASBizException, BOSException {

		if(tenBillBaseInfo.get("curProject")!=null && (tenBillBaseInfo.getOrgUnit() == null ||tenBillBaseInfo.getCU() == null )) {
			CurProjectInfo projectInfo =(CurProjectInfo)tenBillBaseInfo.get("curProject");
			if( projectInfo.getCostCenter()==null || projectInfo.getCU()==null){
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("CU.id");
				sic.add("costCenter.id");				
				projectInfo = CurProjectFactory.getLocalInstance(ctx)
					.getCurProjectInfo(new ObjectUuidPK(projectInfo.getId().toString()),sic);
			}
			if(tenBillBaseInfo.getOrgUnit() == null && projectInfo.getCostCenter()!=null) {
				FullOrgUnitInfo orgUnit = projectInfo.getCostCenter().castToFullOrgUnitInfo();
				tenBillBaseInfo.setOrgUnit(orgUnit);
			}
			if(tenBillBaseInfo.getCU() == null) {
				CtrlUnitInfo currentCtrlUnit = projectInfo.getCU();
				tenBillBaseInfo.setCU(currentCtrlUnit);
			}
		}		
	}
	
}