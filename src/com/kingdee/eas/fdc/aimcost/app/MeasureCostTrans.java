package com.kingdee.eas.fdc.aimcost.app;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.basedata.assistant.IMeasureUnit;
import com.kingdee.eas.basedata.assistant.MeasureUnitCollection;
import com.kingdee.eas.basedata.assistant.MeasureUnitFactory;
import com.kingdee.eas.basedata.assistant.MeasureUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.fdc.aimcost.MeasureCostFactory;
import com.kingdee.eas.fdc.aimcost.MeasureCostInfo;
import com.kingdee.eas.fdc.aimcost.MeasureEntryCollection;
import com.kingdee.eas.fdc.aimcost.MeasureEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexEntryInfo;
import com.kingdee.eas.fdc.aimcost.PlanIndexInfo;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CostAccountTypeEnum;
import com.kingdee.eas.fdc.basedata.CurProjProductEntriesCollection;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ICostAccount;
import com.kingdee.eas.fdc.basedata.IProductType;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.tools.datatask.AbstractDataTransmission;
import com.kingdee.eas.tools.datatask.core.TaskExternalException;
import com.kingdee.eas.tools.datatask.runtime.DataToken;
import com.kingdee.eas.util.ResourceBase;
import com.kingdee.util.StringUtils;

/**
 * ????????????????????
 */
public class MeasureCostTrans extends AbstractDataTransmission {
	
	private boolean isFirstRun = true;
	//????????
	private final String KEY_ACCT_NUMBER="FAcctNumber";
	//????????
	private final String KEY_ACCT_NAME="FAcctName";
	//????????
	private final String KEY_ENTRY_NAME="FEntryName";
	//????????????
	private final String KEY_INDEX_NAME="FIndexName";
	//??????????
	private final String KEY_INDEX_VALUE="FIndexValue";
	//????????
	private final String KEY_COEFFICIENT_NAME="FCoefficientName";
	//??????
	private final String KEY_COEFFICIENT="FCoefficient";
	//??????
	private final String KEY_WORKLOAD="FWorkload";
	//????
	private final String KEY_UNIT="FUnit";
	//????
	private final String KEY_PRICE="FPrice";
	//??????????
	private final String KEY_COSTAMOUNT="FCostAmount";
	//????????
	private final String KEY_ADJUST_COEFFICIENT="FAdjustCoefficient";
	//????????
	private final String KEY_ADJUST_AMT="FAdjustAmt";
	//????
	private final String KEY_AMOUNT="FAmount";
	//????????
	private final String KEY_BUILDPART="FBuildPart";
	//????????
	private final String KEY_SELLPART="FSellPart";
	//????????
	private final String KEY_PROGRAM="FProgram";
	//????
	private final String KEY_DESC="FDesc";
	//????????
	private final String KEY_CHANGE_REASON="FChangeReason";
	//????
	private final String KEY_PRODUCT_NUMBER="FProductTypeNumber";

	protected ICoreBase getController(Context ctx) throws TaskExternalException {
		try {
			return MeasureCostFactory.getLocalInstance(ctx);
		} catch (BOSException e) {
			throw new TaskExternalException("BOSException: getLocalInstance", e);
		}
	}

	public CoreBaseInfo transmit(Hashtable hsData, Context ctx)
			throws TaskExternalException {
		MeasureCostInfo info = (MeasureCostInfo)getContextParameter("editData");
		CurProjectInfo curProjectInfo =(CurProjectInfo)getContextParameter("project");
		
		// ????
		MeasureEntryInfo entryInfo = null;
		MeasureEntryCollection entryCollection = info.getCostEntry();
		entryInfo = new MeasureEntryInfo();
		// ????????
		entryInfo.setHead(info);
		
		// ????????
		CostAccountInfo costAccountInfo = null;
		try {
			Object data = ((DataToken) hsData.get(KEY_ACCT_NUMBER)).data;
			if (data != null && data.toString().length() > 0) {
				String str = data.toString();
				if (str != null && str.length() > 0) {
					ICostAccount ica = CostAccountFactory.getLocalInstance(ctx);
					CostAccountCollection collection = ica.getCostAccountCollection(getLNFilter(str, info.getOrgUnit(), info.getProject()));
					if (collection != null && collection.size() > 0) {
						costAccountInfo = collection.get(0);
						if (costAccountInfo != null) {
							if (!costAccountInfo.isIsEnabled()) {
								// ????????????????
								throw new TaskExternalException(getResource(ctx, "Import_CostAccount_DisEnabled"));
							} else if (!costAccountInfo.isIsLeaf()) {
								// ??????????????????,????????????????????
								throw new TaskExternalException(getResource(ctx, "Import_CostAccount_IsNotLeaf"));
							}else if(costAccountInfo.getType()== CostAccountTypeEnum.MAIN){
								if(hsData.get(KEY_PRODUCT_NUMBER)!=null&&StringUtils.isEmpty(hsData.get(KEY_PRODUCT_NUMBER).toString())){
									throw new TaskExternalException("????????????????!");
								}
							}
							entryInfo.setCostAccount(costAccountInfo);
						} else {
							// ??????????????
							throw new TaskExternalException(getResource(ctx, "Import_CostAccount_NotExist"));
						}
					}
				} else {
					// ????????????????????
					throw new TaskExternalException(getResource(ctx, "Import_CostAccount_Null"));
				}
			}
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}

		Object acctName = ((DataToken) hsData.get(KEY_ACCT_NAME)).data;
		if (acctName != null && acctName.toString().length() > 0) {
			if(acctName.toString().length()>80){
				throw new TaskExternalException("????????????!");
			}
		}else{
			throw new TaskExternalException("????????????????!");
		}
		if (curProjectInfo != null) {// ????????????????????????????????????
			// ????????(????????)
			ProductTypeInfo productTypeInfo = null;
			try {
				Object data = ((DataToken) hsData.get(KEY_PRODUCT_NUMBER)).data;
				if (data != null && data.toString().length() > 0) {
					String str = data.toString();
					if (str != null && str.length() > 0) {
						IProductType ipt = ProductTypeFactory.getLocalInstance(ctx);
						ProductTypeCollection collection = ipt.getProductTypeCollection(getNFilter(str));
						if (collection != null && collection.size() > 0) {
							productTypeInfo = collection.get(0);
							if (productTypeInfo != null) {
								if (!productTypeInfo.isIsEnabled()) {
									// ????????????????
									throw new TaskExternalException(getResource(ctx, "Import_ProductType_DisEnabled"));
								} else {
									if (curProjectInfo.getCurProjProductEntries() != null) {
										CurProjProductEntriesCollection cppec = curProjectInfo.getCurProjProductEntries();
										boolean isAccObj = true;//????????????????"????????????"
										boolean flag = false;
										
										for(int i = 0 ;i<cppec.size();i++){											
											if(productTypeInfo.getId().toString().equals(cppec.get(i).getProductType().getId().toString())){
												if(cppec.get(i).isIsAccObj()){													
													flag = true;
													entryInfo.setProduct(productTypeInfo);
													break;
												}else{
													isAccObj = false;
													break;
												}
											}											
										}
										if(!isAccObj){//??????????????????????????????????????????,????????
											throw new TaskExternalException(getResource(ctx, "Import_ProductType_IsAccObj"));
										}else{
											if(!flag){//??????????????????????????????????????????,????????
												throw new TaskExternalException(getResource(ctx, "Import_CostAmount_ProjectHasNotProductType"));
											}
										}

									} else {
										// ????????????????????????????????,????????????
									}

								}
							}
						} else {
							// ??????????????
							throw new TaskExternalException("????????+"+curProjectInfo.getName()+"????????????");
						}
					}
				} else if(entryInfo.getCostAccount().getType()==CostAccountTypeEnum.MAIN){
					// ????????????????
					throw new TaskExternalException(getResource(ctx, "Import_ProductType_Null"));
				}
			} catch (BOSException e) {
				throw new TaskExternalException(e.getMessage(), e.getCause());
			}
		}
		
		entryCollection.add(entryInfo);
		createEntry(ctx, entryInfo, hsData);

		return info;
	}

	private void createEntry(Context ctx, MeasureEntryInfo info, Hashtable hsData) throws TaskExternalException {
		MeasureCostInfo costInfo = (MeasureCostInfo)getContextParameter("editData");
		PlanIndexInfo indexInfo = (PlanIndexInfo)costInfo.get("PlanIndex");
		//????????
		if(hsData.get(KEY_ENTRY_NAME)!=null){
			String entryName = (String) ((DataToken) hsData.get(KEY_ENTRY_NAME)).data;
			info.setEntryName(entryName);
		}
		//????????
		if(hsData.get(KEY_INDEX_NAME)!=null){
			Object data = ((DataToken) hsData.get("FIndexName")).data;
			Item item = getItemByValue(data.toString().trim(),info.getCostAccount().getType());
			if(item==null){
				throw new TaskExternalException("????????????????????!");
			}
			info.setIndexName(item.name);
			info.setSimpleName(item.key);
			
			//??????????
			if(info.getProduct()!=null&&indexInfo.getEntrys()!=null){
				boolean isProduct = false;
				for(int i=0;i<indexInfo.getEntrys().size();i++){
					if(isProduct){
						continue;
					}
					PlanIndexEntryInfo indexEntry = indexInfo.getEntrys().get(i);
					if(indexEntry.getProduct().getId().toString().equals(info.getProduct().getId().toString())){
						info.setIndexValue(indexEntry.getBigDecimal(item.key));
						isProduct=true;
					}
				}
			}else{
				info.setIndexValue(indexInfo.getBigDecimal(item.key));
			}
		}else{
			throw new TaskExternalException("????????????????????!");
		}
		
		//????
		if(hsData.get(KEY_COEFFICIENT_NAME)!=null){
			String coefficientName = (String) ((DataToken) hsData.get(KEY_COEFFICIENT_NAME)).data;
			if(coefficientName.length()>80){
				throw new TaskExternalException(coefficientName+" ????????????????!");
			}
			info.setCoefficientName(coefficientName);
		}
		
		//??????
		if(hsData.get(KEY_COEFFICIENT)!=null){
			info.setCoefficient(getBigDecimal(ctx, hsData, KEY_COEFFICIENT, false));
		}
		
		//??????
		info.setWorkload(getBigDecimal(ctx, hsData, KEY_WORKLOAD, false));
		
		//????
		info.setPrice(getBigDecimal(ctx, hsData, KEY_PRICE, false));
		
		//????????
		try {
			Object data = ((DataToken) hsData.get("FUnit")).data;
			if (data != null && data.toString().length() > 0) {
				String str = data.toString();
				if (str != null && str.length() > 0) {
					IMeasureUnit imu = MeasureUnitFactory.getLocalInstance(ctx);
					MeasureUnitCollection collection = imu.getMeasureUnitCollection(getMUFilter(str));
					
					if (collection != null && collection.size() > 0) {
						MeasureUnitInfo measureUnitInfo = collection.get(0);
						if (measureUnitInfo != null) {
							if (measureUnitInfo.isIsDisabled()) {
								throw new TaskExternalException(getResource(ctx, "????????  "+measureUnitInfo.getName()+"??????????"));
							} else {
								info.setUnit(measureUnitInfo);
							}
						}
					} else {
						//??????
						throw new TaskExternalException("???????? "+str+" ??????");
					}
				}
			} else {
				//????????
			}
			
		} catch (BOSException e) {
			throw new TaskExternalException(e.getMessage(), e.getCause());
		}
		
		//????????????(??????????)
		info.setCostAmount(getBigDecimal(ctx, hsData, KEY_COSTAMOUNT,true));
		
		//??????????????????????????
		BigDecimal price = info.getPrice();
		BigDecimal workload = info.getWorkload();
		if (price == null) {
			price = FDCHelper.ZERO;
		}
		if (workload == null) {
			workload = FDCHelper.ZERO;
		}

		if (price.compareTo(FDCHelper.ZERO) == 0
				&& workload.compareTo(FDCHelper.ZERO) == 0) {
			info.setPrice(null);
			info.setWorkload(null);
		} else {

			BigDecimal sumPrice = price.multiply(workload).setScale(2,
					BigDecimal.ROUND_HALF_UP);
			info.setCostAmount(sumPrice);
		}
		
		//??????
		info.setAdjustCoefficient(getBigDecimal(ctx, hsData, KEY_ADJUST_COEFFICIENT, false));

		//??????
		info.setAdjustAmt(getBigDecimal(ctx, hsData, KEY_ADJUST_AMT, false));
		
		//????
		info.setAmount(getBigDecimal(ctx, hsData, KEY_AMOUNT, true));
		
		//????????
		//????????
		//????????
		if(hsData.get(KEY_PROGRAM)!=null){
			String program = (String) ((DataToken) hsData.get(KEY_PROGRAM)).data;
			if(program.length()>255){
				throw new TaskExternalException("????????????!");
			}
			info.setProgram(program);
		}
		//????
		if(hsData.get(KEY_DESC)!=null){
			String desc = (String) ((DataToken) hsData.get(KEY_DESC)).data;
			if(desc.length()>1200){
				throw new TaskExternalException("????????!");
			}
			info.setDesc(desc);
		}
		//????????
		if(hsData.get(KEY_CHANGE_REASON)!=null){
			String changeReason = (String) ((DataToken) hsData.get("FChangeReason")).data;
			if(changeReason.length()>1200){
				throw new TaskExternalException("????????????!");
			}
			info.setChangeReason(changeReason);
		}
		
		/*//??????\????\????????????????????
		//1		?????????????????????????????????????????? ?????????????????????? ??????????????????????????
		//2??   ???????????????????????????????????????????? ?????????????????????? ????????????????????????????????????????????
		//3??   ????????????????????????????????????????
		//4??   ??????????????????????????????????????????????????????????????????????????????????????????????????????????????
		//5??   ????????????????????????????????????????????????????????????????????????????????????

		if(info.getWorkload()!=null){
			if(info.getPrice()!=null){
				if(info.getCostAmount()!=null){
					if(round(info.getWorkload().multiply(info.getPrice()),2).compareTo(info.getCostAmount())==0){//????
						//????1
					}else{//????
						info.setPrice(null);
						info.setWorkload(null);
						//????2
					}
				}
			}else{
				//????5
				info.setWorkload(null);
			}
		}else{
			if(info.getPrice()!=null){
				//????5
				info.setPrice(null);
			}else{
				//????3
			}
		}*/
		
	}

	private BigDecimal getBigDecimal(Context ctx, Hashtable hsData, String key, boolean isRequered) throws TaskExternalException {
		BigDecimal value;
		try {
			Object o = ((DataToken) hsData.get(key)).data;
			if (o != null && o.toString().trim().length() > 0) {
				value = new BigDecimal(o.toString());
				if (value != null) {
					return value;
				}
			}else {
				// ??????,????????
				if(isRequered){
					throw new TaskExternalException(getResource(ctx, "Import_CostAmount_Null"));
				}
			}
		} catch (NumberFormatException nex) {
			// ????????????????????
			throw new TaskExternalException(getResource(ctx, "Import_CostAmount_PriceFormatError"));
		}
		return null;
	}
	
	public void submit(CoreBaseInfo coreBaseInfo, Context ctx)
			throws TaskExternalException {
		//TODO ????????
		
		
		super.submit(coreBaseInfo, ctx);
	}
	
	
	private static String resource = "com.kingdee.eas.fdc.costdb.CostDBResource";
	public static String getResource(Context ctx, String key) {
		return ResourceBase.getString(resource, key, ctx.getLocale());
	}	
	/**   
     *   ??????????????????????????????   
     *   @param   v   ??????????????????   
     *   @param   scale   ????????????????   
     *   @return   ????????????????   
     */   
    public static BigDecimal round(BigDecimal   v,int   scale){   
           if(scale<0){   
                   throw   new   IllegalArgumentException(   
                           "The   scale   must   be   a   positive   integer   or   zero");   
           }   
           BigDecimal   b   =   v;
           BigDecimal   one   =   new   BigDecimal("1");   

           return   b.divide(one,scale,BigDecimal.ROUND_HALF_UP);  
    }
    // ????????EntityViewInfo
	private EntityViewInfo getATFilter(String number) {
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("number", number, CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}
    // ????????EntityViewInfo
	private EntityViewInfo getMUFilter(String name) {
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}
   
    // ??????????????EntityViewInfo
    private EntityViewInfo getNFilter(String name) {

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", name, CompareType.EQUALS));
		viewInfo.setFilter(filter);
		return viewInfo;
	}
   
    // ????????????????EntityViewInfo
    private EntityViewInfo getLNFilter(String longNumber, FullOrgUnitInfo fullOrgUnitInfo, CurProjectInfo curProjectInfo) {
	   
	   EntityViewInfo viewInfo = new EntityViewInfo();
	   FilterInfo filter = new FilterInfo();
	   filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber.replace('.', '!'), CompareType.EQUALS));
	   if (curProjectInfo != null) {
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", curProjectInfo.getId().toString(), CompareType.EQUALS));
	   }
	   viewInfo.setFilter(filter);
	   return viewInfo;
	}
    
    public Item getItemByValue(String value,CostAccountTypeEnum enumType){
    	if(CostAccountTypeEnum.MAIN==enumType){
    		for(int i=0;i<Item.PRODUCTITEMS.length;i++){
    			Item item = Item.PRODUCTITEMS[i];
    			if(value.equals(item.name)){
    				return item;
    			}
    		}
    	}else{
    		for(int i=0;i<Item.SIXITEMS.length;i++){
    			Item item = Item.SIXITEMS[i];
    			if(value.equals(item.name)){
    				return item;
    			}
    		}
    	}
    	
    	return null;
    }
    public static class Item{
		String key=null;
		String name=null;
		String productId=null;
		BigDecimal sellArea=null;
		boolean isCustom=false;
		Item(String key,String name) {
			this.key=key;
			this.name=name;
		}
		private Item(ApportionTypeInfo info) {
			this.key=info.getId().toString();
			this.name=info.getName();
			isCustom=true;
		}
		private static Map hashMap=null;
		public static Item getCustomItem(ApportionTypeInfo info){
			if(hashMap==null){
				hashMap=new HashMap();
			}
			Item item=(Item)hashMap.get(info.getId());
			if(item==null){
				item=new Item(info);
			}
			hashMap.put(info.getId(), item);
			return item;
			
		}
		public boolean isCustomIndex(){
			return isCustom;
		}
		public String toString() {
			return name;
		}
		
		public int hashCode() {
			return super.hashCode();
		}
		
		/**
		 * ????????????????
		 * TODO ????????????
		 */
		public static Item [] SIXITEMS=new Item[]{
			new Item("empty",	""),	
			new Item("totalContainArea",	"??????????"),	
			new Item("buildArea",	"????????????"),	
			new Item("totalBuildArea",	"??????????"),	
			new Item("buildContainArea",	"??????????????"),
//			new Item("buildDensity",	"????????	"),
//			new Item("greenAreaRate",	"??????"),	
			new Item("cubageRateArea",	"????????????"),	
//			new Item("cubageRate",	"??????"),	
			new Item("totalRoadArea",	"????????????"),	
			new Item("totalGreenArea",	"????????????	"),
			new Item("pitchRoad",	"??????????????"),
			new Item("concreteRoad",	"??????????????????????"),	
			new Item("hardRoad",	"??????????????"),
			new Item("hardSquare",	"????????????	"),
			new Item("hardManRoad",	"??????????????"),
			new Item("importPubGreenArea",	"????????????	"),
			new Item("houseGreenArea",	"????????????	"),
			new Item("privateGarden",	"????????????	"),
			new Item("warterViewArea",	"????????"),
			new Item("viewArea",	"????????")//,
//			new Item("doors",	"????")
			
		};
		
		/**
		 * ????????????
		 */
		public static Item [] PRODUCTITEMS=new Item[]{
				new Item("empty",	""),
				new Item("containArea",	"????????"),	
				new Item("buildArea",	"????????"),	
				new Item("sellArea",	"????????"),	
				new Item("cubageRate",	"??????"),	
				new Item("productRate",	"????????"),	
				new Item("unitArea",	"????????????"),	
				new Item("units",	"??????"),	
				new Item("doors",	"????"),
		};
		
		/**
		 * ??????????????????????
		 */
		public static Item [] SPLITITEMS = new Item[] { 
				new Item("man", "????????"), 
				new Item("buildArea", "????????"), 
				new Item("sellArea", "????????"),
				new Item("containArea", "????????"),
				new Item("cubageRate", "??????"), 
				new Item("productRate", "????????"), 
				new Item("unitArea", "????????????"), 
				new Item("units", "??????"),
				new Item("doors", "????	"),

		};

	}
	
}
