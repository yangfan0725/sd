/**
 * 
 */
package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.Hashtable;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectCollection;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.util.StringUtils;

/**
 * @(#)							
 * ??????		????????????????????????????????		 	
 * ??????		
 *		
 * @description ??????????????????_??????????
 * @author		??????
 * @version		EAS7.0		
 * @createDate	2011-7-14	 
 * @see						
 */
public class AIMProductAimCostImport extends AbstractDataTransmission {
	
	private static String resource = "com.kingdee.eas.fdc.aimcost.AIMProductAimCostImportResource";
	
	/** ?????????????????? */
	private AIMAimCostProductSplitEntryInfo aimCostProductSplitEntryInfo = null;
	/** ?????????? */
	private AimCostInfo aimCostInfo = null;
	/** ???????????? */
	private CostEntryInfo costEntry = null;
	/** ???????? */
	private CurProjectInfo curProject = null;
	/** ???????? */
	private CostAccountInfo costAccount = null;
	/** ???????? */
	private ProductTypeInfo productTypeInfo = null;
	/** ????????????????????????<????> */
	private CostCenterOrgUnitInfo costCenterOrgUnit =null;
	
	/**
	 * @description		
	 * @author			??????	
	 * @createDate		2011-7-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.AbstractDataTransmission#getController(com.kingdee.bos.Context)					
	 */
	protected ICoreBase getController(Context context) throws TaskExternalException {
		ICoreBase factory = null;
		try {
			factory = AIMAimCostProductSplitEntryFactory.getLocalInstance(context);
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage());
		}
		return factory;
	}

	/**
	 * @description		
	 * @author			??????			
	 * @createDate		2011-7-14
	 * @param	
	 * @return					
	 *	
	 * @version			EAS1.0
	 * @see	
	 * (non-Javadoc)
	 * @see com.kingdee.eas.tools.datatask.IDataTransmission#transmit(java.util.Hashtable, com.kingdee.bos.Context)					
	 */
	public CoreBaseInfo transmit(Hashtable hashtable, Context context) throws TaskExternalException {
		
		aimCostProductSplitEntryInfo = new AIMAimCostProductSplitEntryInfo();
		
		/**
		 * ????Excel????????????
		 */
		// ????????
		String costCenterOrgUnitNumber = ((String) ((DataToken) hashtable.get("orgUnin")).data).trim();
		// ????????????
		String curProjectLongnumber = ((String) ((DataToken) hashtable.get("curPorjectNumber")).data).trim();
		// ????????????
		String curProjectName = ((String) ((DataToken) hashtable.get("curProjectName")).data).trim();
		// ????????????
		String costAccountNumber = ((String) ((DataToken) hashtable.get("costNumber")).data).trim();
		costAccountNumber = costAccountNumber.replace('!', '.');
		// ????????????
		String costAccountName = ((String) ((DataToken) hashtable.get("costName")).data).trim();
		// ????
		String amount = ((String) ((DataToken) hashtable.get("amount")).data).trim();
		// ????
		String choice = ((String) ((DataToken) hashtable.get("isChoose")).data).trim();
		String isChoose = null;
		// ????????
		String fProductTypeLongnumber = ((String) ((DataToken) hashtable.get("productNumber")).data).trim();
		// ????_????
		String fProductTypeAim = ((String) ((DataToken) hashtable.get("productAim")).data).trim();
		// ??????????????????
		BigDecimal aimCostAmount = null;
		
		/**
		 * ??????????Excel????????????????????????????
		 */
		// ????????????
		if (StringUtils.isEmpty(curProjectLongnumber)) {
			// throw new TaskExternalException("??????????????????????");
			throw new TaskExternalException(getResource(context, "PrjLongNumberNotNull"));
		}
		// ????????????
		if (StringUtils.isEmpty(costAccountNumber)) {
			// throw new TaskExternalException("??????????????????????");
			throw new TaskExternalException(getResource(context, "AcctLongNumberNotNull"));
		}
		// ????????
		if (StringUtils.isEmpty(fProductTypeLongnumber)) {
			// throw new TaskExternalException("??????????????????");
			throw new TaskExternalException(getResource(context, "ProductTypeLongNumberNotNull"));
		}
		// ????_????
		if (StringUtils.isEmpty(fProductTypeAim)) {
			// throw new TaskExternalException("????_??????????????");
			throw new TaskExternalException(getResource(context, "ProductTypeAimNotNull"));
		}
		/**
		 * ??????????Excel??????????????????????????????
		 */
		// ????
		if (!StringUtils.isEmpty(amount)) {
			// ????????????????????????????????????????????????????????????????????????
			if (!amount.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
				// throw new TaskExternalException("??????????????,????????????????");
				throw new TaskExternalException(getResource(context, "AmountNotNumber"));
			}
		}
		// ????_????
		if (!fProductTypeAim.matches("^([1-9]\\d{0,15}\\.\\d{0,4})|(0\\.\\d{0,4})||([1-9]\\d{0,15})||0$")) {
			//throw new TaskExternalException("????_??????????????,????????????????");
			throw new TaskExternalException(getResource(context, "ProductTypeAimNotNumber"));
		}
		
		/**
		 * ??????????Excel????????????????????????????????????
		 */
		try {
			// ????????????
			FilterInfo filter1 = new FilterInfo();
			filter1.getFilterItems().add(new FilterItemInfo("longnumber", curProjectLongnumber.replace('.', '!')));
			EntityViewInfo view1 = new EntityViewInfo();
			view1.setFilter(filter1);
			CurProjectCollection curProjectColl = CurProjectFactory.getLocalInstance(context).getCurProjectCollection(view1);
			if (curProjectColl.size() > 0) {
				curProject = curProjectColl.get(0);
			} else {
				// throw new TaskExternalException("????????????????????????????????????????");
				// ????????????????????????????????????????????????????????
				throw new TaskExternalException(curProjectLongnumber + getResource(context, "NumberNotFound"));
			}
			// ????????????
			FilterInfo filter2 = new FilterInfo();
			filter2.getFilterItems().add(new FilterItemInfo("codingnumber", costAccountNumber, CompareType.EQUALS));
			filter2.getFilterItems().add(new FilterItemInfo("curproject", curProject.getId().toString(), CompareType.EQUALS));
			EntityViewInfo view2 = new EntityViewInfo();
			view2.setFilter(filter2);
			CostAccountCollection costAccountColl = CostAccountFactory.getLocalInstance(context).getCostAccountCollection(view2);
			if (costAccountColl.size() > 0) {
				costAccount = costAccountColl.get(0);
			} else {
				// throw new TaskExternalException("????????????????????????????????????????");
				// ????????????????????????????????????????????????????????????????
				throw new TaskExternalException(costAccountNumber + getResource(context, "AcctLongNumberNotFound"));
			}
			// ???????? fProductTypeLongnumber <TODO ????????????????????????????????????????????????????????????????????????????????????????????????????????>
			FilterInfo filter3 = new FilterInfo();
			filter3.getFilterItems().add(new FilterItemInfo("number", fProductTypeLongnumber));
			EntityViewInfo view3 = new EntityViewInfo();
			view3.setFilter(filter3);
			ProductTypeCollection productTypeColl = ProductTypeFactory.getLocalInstance(context).getProductTypeCollection(view3);
			if (productTypeColl.size() > 0) {
				productTypeInfo = productTypeColl.get(0);
			} else {
				// throw new TaskExternalException("????????????????????????????????????");
				throw new TaskExternalException(fProductTypeLongnumber + getResource(context, "ProductTypeLongNumberNotFound"));
			}
//			// ????????
//			AimProductTypeGetter getter = new AimProductTypeGetter(curProject.getId().toString(), ProjectStageEnum.AIMCOST);
//			Map prodcutMap = getter.getSortedProductMap();
//			Set set = prodcutMap.keySet();
//			for (Iterator pIter = set.iterator(); pIter.hasNext();) {
//				if (fProductTypeLongnumber.equals(pIter.next())) {
//					productTypeInfo = (ProductTypeInfo) prodcutMap.get(pIter.next());
//				}
//			}
//			if (productTypeInfo == null) {
//				throw new TaskExternalException(fProductTypeLongnumber + getResource(context, "ProductTypeLongNumberNotFound"));
//			}
			// ???? choice
			if (choice.equals(getResource(context, "NotChoose"))) { 
				// ????????????"??"??????????false
				isChoose = null;
			} else {
				// ????????????"??"??????????true??????????????????????ture????????????????????true
				isChoose = "isChoose";
			}
			
			// ??????????????????????
			EntityViewInfo aimView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			aimView.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId", curProject.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isLastVersion", new Integer(1)));
			/********??????????????????FDC_PARAM_AIMCOSTAUDIT??????????????????????	-by neo********/
			try {
				if (context != null){
					if(FDCUtils.getDefaultFDCParamByKey(context, 
							ContextUtil.getCurrentFIUnit(context).getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT)){
						filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
					}
				}else{
					if(FDCUtils.getDefaultFDCParamByKey(null, 
							SysContext.getSysContext().getCurrentFIUnit().getId().toString(), FDCConstants.FDC_PARAM_AIMCOSTAUDIT))
						filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED));
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			}
			aimView.getSelector().add("costEntry.*");
			aimView.getSelector().add("costEntry.costAccount.id");
			aimView.getSelector().add(new SelectorItemInfo("costEntry.product.id"));
			aimView.getSelector().add(new SelectorItemInfo("costEntry.product.name"));
			aimView.getSelector().add(new SelectorItemInfo("costEntry.product.number"));

			AimCostCollection aimCostCollection = AimCostFactory.getLocalInstance(context).getAimCostCollection(aimView);

			if (aimCostCollection.size() >= 1) {
				aimCostInfo = aimCostCollection.get(0);
			}  else {
				// throw new TaskExternalException("??????????????????????????????????");
				throw new TaskExternalException(getResource(context, "AimCostNotFound"));
			}
			
			// ????????????????????
			CostEntryCollection costEntrys = aimCostInfo.getCostEntry();
			CostEntryInfo costEntrytemp = null;
			if (costEntrys.size()>0) {
//				ProductTypeInfo productTypeTemp = null;
				CostAccountInfo costAccountTemp = null;
				for (int i = 0; i < costEntrys.size(); i++)
				{
					costEntrytemp = costEntrys.get(i);
//					productTypeTemp = costEntrytemp.getProduct();
					costAccountTemp = costEntrytemp.getCostAccount();
					if (costAccountTemp != null) {
						if (productTypeInfo.getId().toString().equals("") && costAccountTemp.getId().toString().equals(costAccount.getId().toString())) {
							costEntry = costEntrys.get(i);
							break;
						}
//					} else {
//						if (productTypeTemp == null && costAccountTemp != null) {
//							if (costAccountTemp.getId().toString().equals(costAccount.getId().toString())) {
//								costEntry = costEntrys.get(i);
//								break;
//							}
//						}
					}
				}
			}
			if (costEntry == null) {				
				// throw new TaskExternalException("????????????????????????????????????");
				throw new TaskExternalException(getResource(context, "CostEntryNotFound"));				
			}
			
			// ??????????????????
			aimCostAmount = costEntry.getCostAmount();
			
			/**
			 * ????????????????????????????????<aimCostProductSplitEntryInfo>??????
			 */
			aimCostProductSplitEntryInfo.setProduct(productTypeInfo);

			SelectorItemCollection appSelector=new SelectorItemCollection();
			appSelector.add("id");
			appSelector.add("targetType.id");
			ApportionTypeInfo apport = new ApportionTypeInfo();
			// ??????????????????
			apport.setId(BOSUuid.read(ApportionTypeInfo.appointType));
			apport = ApportionTypeFactory.getLocalInstance(context).getApportionTypeInfo(new ObjectUuidPK(apport.getId()),appSelector);
			aimCostProductSplitEntryInfo.setApportionType(apport);
			aimCostProductSplitEntryInfo.setSplitAmount(FDCNumberHelper.toBigDecimal(fProductTypeAim));
			aimCostProductSplitEntryInfo.setDirectAmount(FDCNumberHelper.toBigDecimal(fProductTypeAim));
			aimCostProductSplitEntryInfo.setCostEntryId(costEntry.getId().toString());
			aimCostProductSplitEntryInfo.setDescription(isChoose);	

		} catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
		
		return aimCostProductSplitEntryInfo;
	}

	/**
	 * ????????????
	 * @author ??????
	 */
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}
}
