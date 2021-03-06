package com.kingdee.eas.fdc.sellhouse.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.SQLDataException;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeFactory;
import com.kingdee.eas.fdc.sellhouse.BuildingInfo;
import com.kingdee.eas.fdc.sellhouse.BuildingUnitInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeCollection;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseChangeInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseStateEnum;
import com.kingdee.eas.fdc.sellhouse.QuitRoomCollection;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomFactory;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractCollection;
import com.kingdee.eas.fdc.sellhouse.RoomSignContractFactory;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SubareaInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitFactory;
import com.kingdee.jdbc.rowset.IRowSet;

public class ProductTypeSellStateFacadeControllerBean extends AbstractProductTypeSellStateFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.sellhouse.app.ProductTypeSellStateFacadeControllerBean");
    
    /**
     * paramMap
     * ??????
     * selectNode        = ????????????????????????????????????????
     * IncludeAttachment = ??????????????????????????????????????
     * BuildArea         = ??????????????????????????????????????????????
     * BeginDate         = ????????
     * EndDate           = ????????
     * IncludeOrder      = ?????????????? ????????????????????????
     */
    protected Map _getProductSellDate(Context ctx, Map paramMap)throws BOSException, EASBizException
    {
    	Map result = new HashMap();
    	
    	/**
    	 * ??????????????????????
    	 */
    	ProductTypeCollection proTypeCols = getProductTypeCols(ctx);
    	if(proTypeCols == null)
    		proTypeCols = new ProductTypeCollection();
    	
    	result.put("ProductType", proTypeCols);
    	
    	try {
    		Map tmpPrdToModel = new HashMap();
    		//??????????????????
			result.put("RptTotalSumData", getTotalRptDate(ctx, paramMap, tmpPrdToModel));
			
			//????????????????????
			result.put("PrductToModel", tmpPrdToModel);
			
			//????????????????????
			result.put("SelledRoomData", getSelledRoomData(ctx, paramMap));
			
			//????????????????????
			result.put("UnselledRoomData", getUnselledRoomData(ctx, paramMap));
			
			//????????????????????????????
			result.put("SumGathering", getSumGathering(ctx, paramMap));
			
			//??????????????????????????
			result.put("SubAmtData", getSubAmtData(ctx, paramMap));
			
			//??????????????????????????????????????????????
			result = getPeriodNoGatheringData(ctx, paramMap,result);
			
			//????????????????????????
//			result.put("PeriodGatheringData", getPeriodGatheringData(ctx, paramMap, validPurchaseIds));
			
		} catch (SQLException e) {
			throw new SQLDataException(e);
		}
    	
        return result;
    }
    /////////////////////////////////////////////????????????-begin///////////////////////////////////////////////////////////
    
    /**
     * ????????????
     */
    /**
     * ??????Sql????????????????????
     */
    private static void appendDateFilter(FDCSQLBuilder builder, String filterName, Date beginDate, Date endDate)
    {
		if (beginDate != null)
		{
			builder.appendSql(" and " + filterName + " >= ? ");
			builder.addParam(FDCDateHelper.getSqlDate(beginDate));
		}
		if (endDate != null)
		{
			builder.appendSql(" and " + filterName + " < ? ");
			builder.addParam(FDCDateHelper.getNextDay(endDate));
		}
    }
    /**
     * ????????????????????????????????????????????????????null;
     * @param ctx
     * @return
     * @throws BOSException
     */
    protected ProductTypeCollection getProductTypeCols(Context ctx) throws BOSException
    {
    	//??????????????????????
    	EntityViewInfo viewType = new EntityViewInfo();
    	viewType.getSelector().add("id");
    	viewType.getSelector().add("number");
    	viewType.getSelector().add("name");
    	viewType.getSelector().add("isEnabled");
    	viewType.getSelector().add("description");
    	
    	FilterInfo filterType = new FilterInfo();
    	filterType.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
    	
    	SorterItemCollection sorterType = new SorterItemCollection();
    	sorterType.add(new SorterItemInfo("number"));
    	viewType.setSorter(sorterType);
    	
    	ProductTypeCollection cols = ProductTypeFactory.getLocalInstance(ctx).getProductTypeCollection(viewType);
    	
    	return cols;
    }
    
    protected static void appendSelectedNodeFilter(FDCSQLBuilder builder, Map paramMap,Context ctx) throws EASBizException, BOSException
    {
    	/**
    	 * ????????????????
    	 * ????????????????????????????????????????????????????????????
    	 */
    	DefaultKingdeeTreeNode selNode = (DefaultKingdeeTreeNode)paramMap.get("selectNode");
    	//????????????????
    	if(selNode.getUserObject() instanceof BuildingUnitInfo)
    	{
    		BuildingUnitInfo unitInfo = (BuildingUnitInfo)selNode.getUserObject();
    		String unitId = unitInfo.getId().toString();
    		builder.appendSql("left join t_she_buildingunit unit on room.fbuildunitid = unit.fid \n");
    		builder.appendSql("where unit.fid = '" + unitId + "'");
    	}
    	else if(selNode.getUserObject() instanceof BuildingInfo)
    	{
    		BuildingInfo buildInfo = (BuildingInfo)selNode.getUserObject();
    		String buildId = buildInfo.getId().toString();
    		builder.appendSql("left join t_she_building building on building.fid = room.fbuildingid \n");
    		builder.appendSql("where building.fid = '" + buildId + "'");
    	}
    	else if(selNode.getUserObject() instanceof SubareaInfo)
    	{
    		SubareaInfo subAreaInfo = (SubareaInfo)selNode.getUserObject();
    		String subAreaId = subAreaInfo.getId().toString();
    		builder.appendSql("left join t_she_building building on building.fid = room.fbuildingid \n");
    		builder.appendSql("left join t_she_subarea subArea on subArea.Fid = building.fsubareaid \n");
    		builder.appendSql("where subArea.Fid = '" + subAreaId + "'");
    	}
    	else if(selNode.getUserObject() instanceof SellProjectInfo)
    	{
    		SellProjectInfo sellPrjInfo = (SellProjectInfo)selNode.getUserObject();
    		String selPrjId = sellPrjInfo.getId().toString();
    		builder.appendSql("left join t_she_building building on building.fid = room.fbuildingid \n");
    		builder.appendSql("left join t_she_sellproject selPrj on selPrj.Fid = building.fsellprojectid \n");
    		builder.appendSql("where selPrj.Fid = '" + selPrjId + "'");
    	}
    	else if(selNode.getUserObject() instanceof FullOrgUnitInfo)
    	{
    		//??????????????????????????????????????????????????????????????????????????????????????
    		UserInfo user = (UserInfo)paramMap.get("userInfo");
    		Set projectSet = MarketingUnitFactory.getLocalInstance(ctx).getPermitSellProjectIdSet(user);
    		
    		/*????????????id??????????????????????????????????????????????????????
    		 * update by ye_liu
    		 * 2011-04-26 
    		 */
    		if(projectSet==null || projectSet.size()<=0){
    			projectSet.add("nullnullnull");
    		}
    		
//    		FullOrgUnitInfo orgInfo = (FullOrgUnitInfo)selNode.getUserObject();
//    		String orgLongNumber = orgInfo.getLongNumber() + '%';
    		builder.appendSql("left join t_she_building building on building.fid = room.fbuildingid \n");
    		builder.appendSql("left join t_she_sellproject selPrj on selPrj.Fid = building.fsellprojectid \n");
//    		builder.appendSql("inner join t_org_baseunit orgUnit on orgUnit.Fid = selPrj.Forgunitid \n");
//    		builder.appendSql("where orgUnit.Flongnumber like '" + orgLongNumber + "'");
    		builder.appendParam(" where selPrj.fid", projectSet.toArray());
    	}
    }
    /////////////////////////////////////////////????????????-end///////////////////////////////////////////////////////////
    
    /////////////////////////////////////////////??????-begin///////////////////////////////////////////////////////////
    /**
     * ????????????????????????????????
     * @throws EASBizException 
     */
    private Map getTotalRptDate(Context ctx, Map paramMap, Map roomModelMap) throws BOSException, SQLException, EASBizException
    {
    	Map result = new HashMap();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	
    	/**
    	 * ??????????sql??????????????????????????????????????????????????????????
    	 */
    	builder.appendSql("select prdType.Fid as prdTypeId, prdType.Fname_L2 as prdTypeName, rmodel.fid as rmodelId, \n");
    	builder.appendSql("rmodel.fname_l2 as rmodelName, count(room.fid) as sumCount, \n");
    	builder.appendSql("sum(room.fbuildingarea) as sumBuildingArea, \n");
    	builder.appendSql("sum(room.froomarea) as sumRoomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) as sumActualBuildingArea, \n");
    	builder.appendSql("sum(room.factualroomarea) as sunActualRoomArea, \n");
//    	builder.appendSql("sum(case when room.factualbuildingarea is null then room.fbuildingarea else room.factualbuildingarea end) as sumBuildArea,\n");
//    	builder.appendSql("sum(case when room.factualroomarea is null then room.factualroomarea else room.froomarea end) as sumRoomArea,  \n");
    	builder.appendSql("sum(room.fstandardtotalamount) as sumTotAmt \n");
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	/**
    	 * ????????????????
    	 * ????????????????????????????????????????????????????????????
    	 */
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	
    	/**
    	 * ????????????????
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	/**
    	 * ????????????????????????
    	 */
    	builder.appendSql("group by prdType.Fid, prdType.Fname_L2, rmodel.fid, rmodel.fname_l2 \n");
    	builder.appendSql("order by prdType.Fid, rmodel.fid asc\n");
    	
    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	
    	/**
    	 * ????????
    	 */
    	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
    	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
    	
    	/**
    	 * key = ???????? + ????
    	 */
    	while(rowSet.next())
    	{
    		String prdTypeId = rowSet.getString("prdTypeId");
    		String rmodelId = rowSet.getString("rmodelId");
    		
    		if(roomModelMap.containsKey(prdTypeId))
    		{
    			ArrayList array = (ArrayList)roomModelMap.get(prdTypeId);
    			array.add(rmodelId);
    		}
    		else
    		{
    			ArrayList array = new ArrayList();
    			array.add(rmodelId);
    			roomModelMap.put(prdTypeId, array);
    		}
    		String tmpKey = prdTypeId + rmodelId;
    		
    		Object[] tmpArray = new Object[4];
    		tmpArray[0] = rowSet.getString("rmodelName");    //????????
    		tmpArray[1] = rowSet.getBigDecimal("sumCount");  //??????
    		tmpArray[2] = this.getArea(isBuildArea, isPreArea, rowSet);//?? ????
//    		tmpArray[2] = isBuildArea.booleanValue()? rowSet.getBigDecimal("sumBuildArea"):rowSet.getBigDecimal("sumRoomArea"); //??????
    		tmpArray[3] = rowSet.getBigDecimal("sumTotAmt"); //??????
    		
    		result.put(tmpKey, tmpArray);
    		
    	}
    	
    	return result;
    }
    /////////////////////////////////////////////??????-end/////////////////////////////////////////////////////////////
  
   
    ///////////////////////////////////////////////????????-begin/////////////////////////////////////////////////////////
    /**
     * ??????????????????????
     * @throws BOSException 
     * @throws SQLException 
     * @throws EASBizException 
     */
    private Map getSelledRoomData(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Map selledRoomData = new HashMap();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdType.Fid as prdTypeId, prdType.Fname_L2 as prdTypeName, rmodel.fid as rmodelId, \n");
    	builder.appendSql("rmodel.fname_l2 as rmodelName, count(room.fid) as sumCount, \n");
    	builder.appendSql("sum(room.fbuildingarea) as sumBuildingArea, \n");
    	builder.appendSql("sum(room.froomarea) as sumRoomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) as sumActualBuildingArea, \n");
    	builder.appendSql("sum(room.factualroomarea) as sunActualRoomArea, \n");
//    	builder.appendSql("sum(case when room.factualbuildingarea is null then room.fbuildingarea else room.factualbuildingarea end) as sumBuildArea, \n");
//    	
//    	builder.appendSql("sum(case when room.factualroomarea is null then room.factualroomarea else room.froomarea end) as sumRoomArea, \n");
    	builder.appendSql("sum(room.fstandardtotalamount) as sumTotAmt, \n");
    	builder.appendSql("sum(room.fbasebuildingprice) as baseBuidAmt, sum(room.fbaseroomprice) as baseRoomAmt, sum(room.fdealtotalamount) as dealSumAmt,\n");
    	
    	builder.appendSql("sum(((case when room.fbasebuildingprice is null then 0 else room.fbasebuildingprice end)*(case when room.factualBuildingArea is null then 0 else room.factualBuildingArea end))) as buildActAmt,\n");
    	builder.appendSql("sum(((case when room.fbasebuildingprice is null then 0 else room.fbasebuildingprice end)*(case when room.fbuildingArea is null then 0 else room.fbuildingArea end))) as buildAmt,\n");
    	builder.appendSql("sum(((case when room.fbaseroomprice is null then 0 else room.fbaseroomprice end)*(case when room.froomArea is null then 0 else room.froomArea end))) as roomAmt,\n");
    	builder.appendSql("sum(((case when room.fbaseroomprice is null then 0 else room.fbaseroomprice end)*(case when room.factualRoomArea is null then 0 else room.factualRoomArea end))) as roomActAmt\n");

//    	builder.appendSql("sum(isnull(room.fbasebuildingprice,0)*isnull(room.buildingArea,0)) as buildAmt,sum(isnull(room.fbasebuildingprice,0)*isnull(room.actualBuildingArea,0)) as buildActAmt,\n");
//    	builder.appendSql("sum(isnull(room.fbaseroomprice,0)*isnull(room.roomArea,0)) as roomAmt,sum(isnull(room.fbaseroomprice,0)*isnull(room.actualRoomArea,0)) as roomActAmt,\n");
    	
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid  \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	/**
    	 * ????????????????
    	 * ????????????????????????????????????????????????????????????
    	 */
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	
    	/**
    	 * ????????????????
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	/**
    	 * ????????????????
    	 */
    	Boolean includePreOrder = (Boolean)(paramMap.get("IncludeOrder"));
    	if(includePreOrder.booleanValue())
    	{
    		builder.appendSql("\n and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fsellstate in ('Purchase', 'Sign') \n");
    	}
    	
    	builder.appendSql("group by prdType.Fid, prdType.Fname_L2, rmodel.fid, rmodel.fname_l2 \n");
    	builder.appendSql("order by prdType.Fid, rmodel.fid asc \n");
    	
    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	/**
    	 * ????????
    	 */
    	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
    	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
    	
    	/**
    	 * key = ???????? + ????
    	 */
    	while(rowSet.next())
    	{
    		String prdTypeId = rowSet.getString("prdTypeId");
    		String rmodelId = rowSet.getString("rmodelId");
    		String tmpKey = prdTypeId + rmodelId;
    		
    		Object[] tmpArray = new Object[6];
    		tmpArray[0] = rowSet.getBigDecimal("sumCount");  //??????
    		tmpArray[1] = this.getArea(isBuildArea, isPreArea, rowSet);
//    		tmpArray[1] = isBuildArea.booleanValue()? rowSet.getBigDecimal("sumBuildArea"):rowSet.getBigDecimal("sumRoomArea"); //??????
    		tmpArray[2] = rowSet.getBigDecimal("sumTotAmt"); //??????
    		tmpArray[3] = isBuildArea.booleanValue()? rowSet.getBigDecimal("baseBuidAmt"):rowSet.getBigDecimal("baseRoomAmt");  //????????
    		tmpArray[4] = rowSet.getBigDecimal("dealSumAmt"); //????????
//    		????????????????????????
    		tmpArray[5] = this.getbaseprice(isBuildArea, isPreArea, rowSet);  //????????
    		
    		
    		selledRoomData.put(tmpKey, tmpArray);
    	}
    	
    	return selledRoomData;
    }
    ///////////////////////////////////////////////????????-end///////////////////////////////////////////////////////////
    
    
    ///////////////////////////////////////////////????????-begin/////////////////////////////////////////////////////////
    /**
     * ??????????????????????
     * @throws BOSException 
     * @throws SQLException 
     * @throws EASBizException 
     */
    private Map getUnselledRoomData(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Map unselledRoomData = new HashMap();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdType.Fid as prdTypeId, prdType.Fname_L2 as prdTypeName, rmodel.fid as rmodelId, \n");
    	builder.appendSql("rmodel.fname_l2 as rmodelName, count(room.fid) as sumCount, \n");
    	builder.appendSql("sum(room.fbuildingarea) as sumBuildingArea, \n");
    	builder.appendSql("sum(room.froomarea) as sumRoomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) as sumActualBuildingArea, \n");
    	builder.appendSql("sum(room.factualroomarea) as sunActualRoomArea, \n");
//    	builder.appendSql("sum(case when room.factualbuildingarea is null then room.fbuildingarea else room.factualbuildingarea end) as sumBuildArea, \n");
//    	
//    	builder.appendSql("sum(case when room.factualroomarea is null then room.factualroomarea else room.froomarea end) as sumRoomArea, \n");
    	builder.appendSql("sum(room.fstandardtotalamount) as sumTotAmt \n");
    	
    	builder.appendSql("from t_she_room room \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid  \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	/**
    	 * ????????????????
    	 * ????????????????????????????????????????????????????????????
    	 */
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	
    	/**
    	 * ????????????????
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	/**
    	 * ????????????????
    	 */
    	Boolean includePreOrder = (Boolean)(paramMap.get("IncludeOrder"));
    	if(includePreOrder.booleanValue())
    	{
    		builder.appendSql("\n and room.fsellstate in ('Init', 'Onshow', 'KeepDown') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fsellstate in ('Init', 'Onshow', 'KeepDown', 'PrePurchase') \n");
    	}
    	
    	builder.appendSql("group by prdType.Fid, prdType.Fname_L2, rmodel.fid, rmodel.fname_l2 \n");
    	builder.appendSql("order by prdType.Fid, rmodel.fid asc \n");
    	
//    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	/**
    	 * ????????
    	 */
    	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
    	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
    	
    	/**
    	 * key = ???????? + ????
    	 */
    	while(rowSet.next())
    	{
    		String prdTypeId = rowSet.getString("prdTypeId");
    		String rmodelId = rowSet.getString("rmodelId");
    		String tmpKey = prdTypeId + rmodelId;
    		
    		Object[] tmpArray = new Object[5];
    		tmpArray[0] = rowSet.getBigDecimal("sumCount");  //??????
    		tmpArray[1] = this.getArea(isBuildArea, isPreArea, rowSet);
//    		tmpArray[1] = isBuildArea.booleanValue()? rowSet.getBigDecimal("sumBuildArea"):rowSet.getBigDecimal("sumRoomArea"); //??????
    		tmpArray[2] = rowSet.getBigDecimal("sumTotAmt"); //????????
    		
    		unselledRoomData.put(tmpKey, tmpArray);
    	}
    	
    	return unselledRoomData;
    }
      
    ///////////////////////////////////////////////????????-end///////////////////////////////////////////////////////////
    
    /**
	 * ????????????????????????????
	 */
    private Object getArea(Boolean isBuildArea,Boolean isPreArea,IRowSet rowSet) throws SQLException
    {
    	//??????
		//??????????????????????????????????????????????????
		if(isBuildArea.booleanValue() && isPreArea.booleanValue())
		{
			return rowSet.getBigDecimal("sumBuildingArea");
			//??????????????????????????????????????????????????
		}else if(isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("sumActualBuildingArea");
			//??????????????????????????????????????????????????
		}else if(!isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("sunActualRoomArea");
			//??????????????????????????????????????????????????
		}else
		{
			return rowSet.getBigDecimal("sumRoomArea");
		}
    }
    /**
	 * ????????????????????????
	 */
    private Object getbaseprice(Boolean isBuildArea,Boolean isPreArea,IRowSet rowSet) throws SQLException
    {
    	//??????
		//??????????????????????????????????????????
		if(isBuildArea.booleanValue() && isPreArea.booleanValue())
		{
			return rowSet.getBigDecimal("buildAmt");
			//????????????????????????????????????????
		}else if(isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("buildActAmt");
			//??????????????????????????????????????????
		}else if(!isBuildArea.booleanValue() && !isPreArea.booleanValue())
		{
			return  rowSet.getBigDecimal("roomActAmt");
			//??????????????????????????????????????????
		}else
		{
			return rowSet.getBigDecimal("roomAmt");
		}
    }
    
    ///////////////////////////////////////////////????????-begin/////////////////////////////////////////////////////////
    private Map getSumGathering(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Map sumGathering = new HashMap();
    	
    	//??????????????ID
    	Set selledRoomIds = getSelledRoomIdSet(ctx, paramMap);
    	
    	if(selledRoomIds == null || selledRoomIds.size() == 0)
    		return sumGathering;
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdTypeId as prdTypeId, rmodelId as rmodelId,sum(actamount) actamount, \n");
    	builder.appendSql("sum(transAmount) transAmount, sum(refundAmount) refundAmount,sum(justAmount) justAmount \n");
    	builder.appendSql("from ( \n");
    	
    	//????????????????????????????????
    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
//    	builder.appendSql("sum(payLists.FactRevamount-payLists.Fhastransferredamount-payLists.Fhasrefundmentamount-payLists.Fhasadjustedamount) as gatheringAmt \n");
    	builder.appendSql("sum(payLists.factrevamount) actamount, \n");
    	builder.appendSql("sum(payLists.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(payLists.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(payLists.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_purchasepaylistentry payLists \n");
    	//builder.appendSql("inner join t_she_moneydefine monDefine on payLists.Fmoneydefineid = monDefine.Fid \n");
    	builder.appendSql("left join t_she_purchase purchase on  payLists.Fheadid = purchase.fid \n");
    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //????????????????????????????????
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	builder.appendSql("where \n");
    	builder.appendParam("room.fid", selledRoomIds.toArray());
    	builder.appendSql("\n group by prdType.Fid, rmodel.fid \n");
    	
//    	builder.appendSql("union \n");
//    	
//    	//????????????????????????????????????????
//    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
////    	builder.appendSql("sum(elsePayLists.FactRevamount-elsePayLists.Fhastransferredamount-elsePayLists.Fhasrefundmentamount-elsePayLists.Fhasadjustedamount) as gatheringAmt \n");
//    	builder.appendSql("sum(elsePayLists.factrevamount) actamount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhastransferredamount) transAmount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhasrefundmentamount) refundAmount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhasadjustedamount) justAmount \n");
//    	builder.appendSql("from t_she_purchaseelsepaylistentry elsePayLists \n");
//    	//builder.appendSql("inner join t_she_moneydefine monDefine on elsePayLists.Fmoneydefineid = monDefine.Fid \n");
//    	builder.appendSql("left join t_she_purchase purchase on  elsePayLists.Fheadid = purchase.fid \n");
//    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //????????????????????????????????
//    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
//    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
//    	builder.appendSql("where \n");
//    	builder.appendParam("room.fid", selledRoomIds.toArray());
//    	builder.appendSql("\n group by prdType.Fid, rmodel.fid \n");
    	
    	builder.appendSql(") PayListSum \n");
    	
    	builder.appendSql("group by prdTypeId, rmodelId \n");
    	builder.appendSql("order by prdTypeId, rmodelId asc \n");
    	
//    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	
    	while(rowSet.next())
    	{
    		String tmpKey = rowSet.getString("prdTypeId") + rowSet.getString("rmodelId");
    		//????????
     		BigDecimal revamount = rowSet.getBigDecimal("actamount")==null?new BigDecimal(0):rowSet.getBigDecimal("actamount");
     		//????????
     		BigDecimal transAmount = rowSet.getBigDecimal("transAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("transAmount");
     		//????????
     		BigDecimal refundAmount = rowSet.getBigDecimal("refundAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("refundAmount");
     		//????????
     		BigDecimal justAmount = rowSet.getBigDecimal("justAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("justAmount");
     		//????????=????????-????????-????????-????????
     		BigDecimal actamount = revamount.subtract(transAmount).subtract(refundAmount).subtract(justAmount);
    		sumGathering.put(tmpKey,actamount);
    	}
    	
    	return sumGathering;
    }
    
    private Set getSelledRoomIdSet(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Set roomIds = new HashSet();
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select distinct room.fid as roomId \n");
    	builder.appendSql("from t_she_room room \n");
    	
    	/**
    	 * ????????????????
    	 * ????????????????????????????????????????????????????????????
    	 */
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	
    	/**
    	 * ????????????????
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    	/**
    	 * ????????????????
    	 */
    	Boolean includePreOrder = (Boolean)(paramMap.get("IncludeOrder"));
    	if(includePreOrder.booleanValue())
    	{
    		builder.appendSql("\n and room.fsellstate in ('PrePurchase', 'Purchase', 'Sign') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fsellstate in ('Purchase', 'Sign') \n");
    	}
//    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	
    	while(rowSet.next())
    	{
    		roomIds.add(rowSet.getString("roomId"));
    	}
    	
    	return roomIds;
    }
    ///////////////////////////////////////////////????????-end///////////////////////////////////////////////////////////
    
    
    ///////////////////////////////////////////////????????-begin///////////////////////////////////////////////////////////
    /**
     * selectNode        = ????????????????????????????????????????
	 * IncludeAttachment = ??????????????????????????????????????
	 * BuildArea         = ??????????????????????????????????????????????
	 * BeginDate         = ????????
	 * EndDate           = ????????
	 * IncludeOrder      = ?????????????? ????????????????????????
	 * IsAuditDate       = ????????????????????????????????????????????????????????????????????
     * @throws EASBizException 
     */
    public static Map getValidPurchaseIdSet(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Map purchaseMap = new HashMap();
    	Set purchaseIds = new HashSet();
//    	Set purchaseCompensateSet = new HashSet();
    	
//    	Boolean isAuditDate = (Boolean)paramMap.get("IsAuditDate");
    	Boolean isShowAll = (Boolean)paramMap.get("isShowAll");
    	Date beginDate = new Date();
    	Date endDate = new Date();
    	if(isShowAll==null)
    	{
    		beginDate = (Date)paramMap.get("BeginDate");
       	    endDate = (Date)paramMap.get("EndDate");
    	}else
    	{
    		if(isShowAll.booleanValue())
        	{
    			java.text.SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
    			try {
					beginDate = df.parse("19000101");
					endDate = df.parse("29000101");
				} catch (ParseException e) {
					e.printStackTrace();
				};				
//				endDate = FDCDateHelper.getServerTimeStamp();

        	}else
        	{
        		 beginDate = (Date)paramMap.get("BeginDate");
            	 endDate = (Date)paramMap.get("EndDate");
        	}
    	}
   	
    	Boolean includeOrder = (Boolean)paramMap.get("IncludeOrder");
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select roomId, purchaseId, purchaseState, busDate \n");
    	builder.appendSql("from (  \n");   	
    	
    	/**
    	 * ??????????
    	 */
    	builder.appendSql("select purchase.froomid as roomId, purchase.fid as purchaseId,  \n");
    	builder.appendSql("purchase.fpurchasestate as purchaseState, \n");
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("purchase.ftosaledate as busDate \n");
    	}
    	else
    	{
    		builder.appendSql("purchase.ftopurchasedate as busDate \n");
    	}
    	builder.appendSql("from t_she_purchase purchase \n");
    	builder.appendSql("left join t_she_room room on room.fid = purchase.froomid \n");
    	//????????????
    	appendSelectedNodeFilter(builder, paramMap,ctx);
    	if(includeOrder.booleanValue())
    	{
    		builder.appendSql("and purchase.fpurchasestate in ('PrePurchaseCheck', 'PurchaseApply', 'PurchaseAuditing', 'PurchaseAudit', 'PurchaseChange','QuitRoomBlankOut','ChangeRoomBlankOut')");
    	}
    	else
    	{
    		builder.appendSql("and purchase.fpurchasestate in ('PurchaseApply', 'PurchaseAuditing', 'PurchaseAudit', 'PurchaseChange','QuitRoomBlankOut','ChangeRoomBlankOut')");
    	}
    	builder.appendSql("and purchase.ftosaledate is not null \n");
    	if(includeOrder.booleanValue())
    	{
    		appendDateFilter(builder, "purchase.ftosaledate", beginDate, endDate);
    	}
    	else
    	{
    		appendDateFilter(builder, "purchase.ftopurchasedate", beginDate, endDate);
    	}
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('Attachment','NoAttachment') \n");
    	}
    	else
    	{
    		builder.appendSql("\n and room.fhouseproperty in ('NoAttachment') \n");
    	}
    	
    
    	builder.appendSql(") blankTable \n");
    	builder.appendSql("order by roomId, purchaseId asc \n");
    
    	logger.info(builder.getTestSql());
    	IRowSet rowSet = builder.executeQuery(ctx);
    	
    	/**
    	 * ??????????????????????????
    	 * key = roomId
    	 * 1 = ??????ID
    	 * 2 = ??????????
    	 * 3 = ????????????????
    	 */
    	Map roomPurchaseMap = new HashMap();
    	while(rowSet.next())
    	{
    		//roomId, purchaseId, purchaseState, busDate
    		//??????????????????????????????????????????????key??????????????????????ID??????????
    		String tmpKey = rowSet.getString("purchaseId");
    		String purchaseId = rowSet.getString("purchaseId");
    		String purchaseState = rowSet.getString("purchaseState");
    		Date  busDate = rowSet.getDate("busDate");
    		
//    		if(roomPurchaseMap.containsKey(tmpKey))
//    		{
//    			Object[] tmpArray = (Object[])roomPurchaseMap.get(tmpKey);
//    			Date tmpBusDate = (Date)tmpArray[2];
//    			if(busDate.after(tmpBusDate))
//    			{
//    				tmpArray[0] = purchaseId;
//    				tmpArray[1] = purchaseState;
//    				tmpArray[2] = busDate;
//    			}
//    		}
//    		else
//    		{
    			Object[] array = new Object[3];
    			array[0] = purchaseId;    //??????ID
    			array[1] = purchaseState; //??????????
    			array[2] = busDate;       //????????????????
    			
    			roomPurchaseMap.put(tmpKey, array);
//    		}
    	}
    	
    	Set tmpKeySet = roomPurchaseMap.keySet();
    	Iterator keyIter = tmpKeySet.iterator();  	
    	/**
    	 * ????????????????
    	 */
    	while(keyIter.hasNext())
    	{
    		String tmpStr = (String)keyIter.next();
    		Object[] tmpArray = (Object[]) roomPurchaseMap.get(tmpStr);
    		purchaseIds.add(tmpArray[0]);
    		if(includeOrder.booleanValue())
    		{
    			String purchaseState = String.valueOf(tmpArray[1]);
    			//'PrePurchaseCheck', 'PurchaseAudit', 'PurchaseChange'
    			if(purchaseState.equals("PrePurchaseCheck") || 
    					purchaseState.equals("PurchaseAudit") ||
    					purchaseState.equals("PurchaseChange") ||
    					purchaseState.equals("QuitRoomBlankOut") ||
    					purchaseState.equals("ChangeRoomBlankOut"))
    			{
    				//????????????????????????????????????????????????????????????????????????????????????????????????
    		    	//????????????????????????????
    				//????????????????????????????
//    				if(!vifyQuitAndChange(ctx, (String)tmpArray[0], beginDate, endDate))
//    				{
    					purchaseIds.add(tmpArray[0]);
//    				}   
    			}
    		}
    		else
    		{
    			String purchaseState = String.valueOf(tmpArray[1]);
    			//'PurchaseAudit', 'PurchaseChange'
    			if( purchaseState.equals("PurchaseAudit") ||
    					purchaseState.equals("PurchaseChange") ||
    					purchaseState.equals("QuitRoomBlankOut") ||
    					purchaseState.equals("ChangeRoomBlankOut"))
    			{
    				//????????????????????????????????????????????????????????????????????????????????????????????????
    		    	//????????????????????????????
    				//??????????????????
//    				if(!vifyQuitAndChange(ctx, (String)tmpArray[0], beginDate, endDate))
//    				{
    					purchaseIds.add(tmpArray[0]);
//    				} 
    			}
    		}
    	}   
    	//????????????????????????????
    	if(purchaseIds.size()>0)
    	{
    		//??????????????????????????????????????????????????????????????????????????
        	builder = new FDCSQLBuilder(ctx);
        	builder.appendSql("select pur.fid purchaseID ");
        	builder.appendSql("from t_she_purchase pur ");
        	builder.appendSql("left join t_she_quitroom quitroom on quitroom.fpurchaseid = pur.fid ");
        	builder.appendSql("where quitroom.fstate='4AUDITTED' ");
        	appendDateFilter(builder, "quitroom.Faudittime", beginDate, endDate);
        	builder.appendSql(" and ");
        	builder.appendParam("pur.fid", purchaseIds.toArray());
        	rowSet = builder.executeQuery(ctx);
        	while(rowSet.next())
        	{
        		String purchaseID = rowSet.getString("purchaseID");
        		purchaseIds.remove(purchaseID);
        	}
        	//??????????????????????????????????????????????????purchaseIds????????????????????????????
        	if(purchaseIds.size()>0)
        	{
        		//??????????????????????????????????????????????????????????????????????????
            	builder = new FDCSQLBuilder(ctx);
            	builder.appendSql("select pur.fid purchaseID ");
            	builder.appendSql("from t_she_purchase pur ");
            	builder.appendSql("left join t_she_changeroom change on change.FOldPurchaseID = pur.fid ");
            	builder.appendSql("where change.fstate='4AUDITTED' ");
            	appendDateFilter(builder, "change.Faudittime", beginDate, endDate);
            	builder.appendSql(" and "); 
            	builder.appendParam("pur.fid", purchaseIds.toArray());
            	rowSet = builder.executeQuery(ctx);
            	while(rowSet.next())
            	{
            		String purchaseID = rowSet.getString("purchaseID");
            		purchaseIds.remove(purchaseID);
            	}
        	}      	
    	}    	
    	
    	purchaseMap.put("purchase", purchaseIds);
    	return purchaseMap;
    }
    
    //????????????????????????????????????????????????
    private static boolean vifyQuitAndChange(Context ctx,String purchaseID,Date beginDate, Date endDate) throws BOSException
    {
    	boolean boo = false;
    	EntityViewInfo view = new EntityViewInfo();
    	EntityViewInfo view1 = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	view.getSelector().add("quitDate");
    	view1.getSelector().add("changeDate");
    	filter.getFilterItems().add(new FilterItemInfo("purchase.id",purchaseID));
    	filter.getFilterItems().add(new FilterItemInfo("state","4AUDITTED"));
    	view.setFilter(filter);
    	view1.setFilter(filter);
    	QuitRoomCollection quitRoomColl = QuitRoomFactory.getLocalInstance(ctx).getQuitRoomCollection(view);
    	
    	PurchaseChangeCollection purChangeColl = PurchaseChangeFactory.getLocalInstance(ctx).getPurchaseChangeCollection(view1);
    	
    	//????????????????????????????????????????????????????
    	if(quitRoomColl.size()==0 && purChangeColl.size()==0)
    	{
    		boo = false;
    	}else
    	{
    		//????????????????????????????????????????????????
    		if(quitRoomColl.size()>0)
    		{
    			QuitRoomInfo quitRoom = quitRoomColl.get(0);
    			Date quitDate = quitRoom.getQuitDate();
    			//??????????????????????????????????????????????????????
    			if(quitDate.after(beginDate) && quitDate.before(endDate))
    			{
    				boo = true;
    			}else
    			{
    				boo = false;
    			}
    		}
    		if(purChangeColl.size()>0)
    		{
    			PurchaseChangeInfo purChangeInfo = purChangeColl.get(0);
    			Date purChangeDate = purChangeInfo.getChangeDate();
    			//??????????????????????????????????????????????????????
    			if(purChangeDate.after(beginDate) && purChangeDate.before(endDate))
    			{
    				boo = true;
    			}else 
    			{
    				boo = false;
    			}
    		}
    	}
    	return boo;
    }
    
    /**
     * ??????????
     * @param ctx
     * @param paramMap
     * @param validPurchaseIds
     * @return
     * @throws BOSException
     * @throws SQLException
     * @throws EASBizException 
     */
    protected Map getPeriodNoGatheringData(Context ctx, Map paramMap, Map result) throws BOSException, SQLException, EASBizException
    {   	
    	Map PeriodNoGatheringDataMap = new HashMap();
    	Map PeriodGatheringDataMap = new HashMap();
    	Map perCheckMap = new HashMap();
    	Map purchaseMap = new HashMap();
//    	purchaseMap = getValidPurchaseIdSet(ctx, paramMap);
    	String sql = getValidPurchaseIdSetNew(ctx,paramMap);
//    	Set validPurchaseIds = (Set)purchaseMap.get("purchase");
    	//????????????????????ID(??????????????????????????????????????????????)
//    	Set purchaseCompensateSet = (Set)purchaseMap.get("purchaseCompensate");
//    	if(validPurchaseIds.size() <= 0)
//    	{
//    		return result;
//    	}
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdTypeId,rmodelId,state,sum(roomNum) roomNum,sum(sumBuildingArea) sumBuildingArea, \n");
    	builder.appendSql("sum(sumRoomArea) sumRoomArea,sum(sumActualBuildingArea) sumActualBuildingArea, \n");
    	builder.appendSql("sum(sunActualRoomArea) sunActualRoomArea,sum(compentAmount) compentAmount, \n");
    	builder.appendSql("sum(dealAmt) dealAmt,sum(standAmt) standAmt, \n");
    	builder.appendSql("sum(contractAmt) contractAmt,sum(purchaseAmt) purchaseAmt \n");
    	builder.appendSql("from (");
    	builder.appendSql("select prdType.Fid as prdTypeId, rmodel.fid as rmodelId,purchase.fpurchasestate state,count(purchase.froomid) as roomNum, \n");
    	builder.appendSql("sum(room.fbuildingarea) as sumBuildingArea, \n");
    	builder.appendSql("sum(room.froomarea) as sumRoomArea, \n");
    	builder.appendSql("sum(room.factualbuildingarea) as sumActualBuildingArea, \n");
    	builder.appendSql("sum(room.factualroomarea) as sunActualRoomArea, \n");
    	builder.appendSql("sum(areaCompent.FCompensateAmount) as compentAmount, \n");
//    	builder.appendSql("sum(case when room.factualbuildingarea is null then room.fbuildingarea else room.factualbuildingarea end) as buildArea, \n");
//    	builder.appendSql("sum(case when room.factualroomarea is null then room.froomarea else room.factualroomarea end) as roomArea, \n");
    	builder.appendSql("sum(purchase.fdealamount ) as dealAmt, sum(purchase.fstandardtotalamount) as standAmt,  \n");
    	builder.appendSql("sum(purchase.fcontracttotalamount) as contractAmt,  \n");
    	builder.appendSql("sum(case when purchase.fpurchasestate in ('PurchaseApply', 'PurchaseAuditing', 'PurchaseAudit', 'PurchaseChange') ");
    	builder.appendSql("then purchase.fdealamount end) as purchaseAmt \n");
    	
    	builder.appendSql("from t_she_purchase purchase \n");
    	builder.appendSql("left join t_she_room room on room.fid = purchase.froomid \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	builder.appendSql("left join t_she_RoomAreaCompensate areaCompent on room.fid = areaCompent.froomid \n");
//		builder.appendSql("left join T_SHE_AreaCompensateRevList comRevList on areaCompent.fid = comRevList.fheadid  \n");
		builder.appendSql("\n where ");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
    	builder.appendSql("purchase.fid in (" + sql + ")");
    	builder.appendSql("\n group by prdType.Fid, rmodel.fid,purchase.fpurchasestate \n");
    	/**
    	 * ????????????????
    	 */
    	Boolean includeAttach = (Boolean)(paramMap.get("IncludeAttachment"));
    	if(includeAttach.booleanValue())
    	{
    		builder.appendSql("union \n");
    		builder.appendSql("select prdType.Fid as prdTypeId, rmodel.fid as rmodelId,purchase.fpurchasestate state,count(purchase.froomid) as roomNum, \n");
        	builder.appendSql("sum(room.fbuildingarea) as sumBuildingArea, \n");
        	builder.appendSql("sum(room.froomarea) as sumRoomArea, \n");
        	builder.appendSql("sum(room.factualbuildingarea) as sumActualBuildingArea, \n");
        	builder.appendSql("sum(room.factualroomarea) as sunActualRoomArea, \n");
        	builder.appendSql("sum(areaCompent.FCompensateAmount) as compentAmount, \n");
//        	builder.appendSql("sum(case when room.factualbuildingarea is null then room.fbuildingarea else room.factualbuildingarea end) as buildArea, \n");
//        	builder.appendSql("sum(case when room.factualroomarea is null then room.froomarea else room.factualroomarea end) as roomArea, \n");
        	builder.appendSql("sum(purchase.fdealamount ) as dealAmt, sum(purchase.fstandardtotalamount) as standAmt,  \n");
        	builder.appendSql("sum(purchase.fcontracttotalamount) as contractAmt,  \n");
        	builder.appendSql("sum(case when purchase.fpurchasestate in ('PurchaseApply', 'PurchaseAuditing', 'PurchaseAudit', 'PurchaseChange') ");
        	builder.appendSql("then purchase.fdealamount end) as purchaseAmt \n");
        	builder.appendSql("from T_SHE_PurchaseRoomAttachEntry attach \n");
        	builder.appendSql("left join T_SHE_RoomAttachmentEntry roomAttach on attach.FAttachmentEntryID = roomAttach.fid \n");
        	builder.appendSql("left join t_she_purchase purchase on attach.fheadid = purchase.fid \n");
        	builder.appendSql("left join t_she_room room on roomAttach.froomid = room.fid \n");
        	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
        	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
        	builder.appendSql("left join t_she_RoomAreaCompensate areaCompent on room.fid = areaCompent.froomid \n");
//    		builder.appendSql("left join T_SHE_AreaCompensateRevList comRevList on areaCompent.fid = comRevList.fheadid  \n");
        	builder.appendSql("\n where ");
//        	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
        	builder.appendSql("purchase.fid in (" + sql + ")");
        	builder.appendSql("\n group by prdType.Fid, rmodel.fid,purchase.fpurchasestate \n");      	
    	}
    	builder.appendSql(") aa");
    	builder.appendSql("\n group by prdTypeId, rmodelId,state ");
    	
    	logger.info(builder.getTestSql());
    	
    	/**
    	 * ????????
    	 */
    	Boolean isBuildArea = (Boolean)paramMap.get("BuildArea");
    	Boolean isPreArea = (Boolean)paramMap.get("PreArea");
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		String tmpPrdTypeId = rowSet.getString("prdTypeId");
    		String tmpRmodelId = rowSet.getString("rmodelId");
    		String state = rowSet.getString("state");
    		String tmpKey = tmpPrdTypeId + tmpRmodelId;
    		BigDecimal compentAmount = rowSet.getBigDecimal("compentAmount");
    		compentAmount = compentAmount==null?new BigDecimal(0):compentAmount;
    		if(PurchaseStateEnum.PREPURCHASECHECK_VALUE.equals(state))
    		{
    			//????????????????????????????????????????????
    			if(PeriodNoGatheringDataMap.get(tmpKey)!=null)
    			{
    				Object[] obj = (Object[])PeriodNoGatheringDataMap.get(tmpKey);
    				BigDecimal roomNum = (BigDecimal)rowSet.getBigDecimal("roomNum");
    				obj[0] = getBigDecimal(obj[0]).add(CRMHelper.getBigDecimal(roomNum));
    				BigDecimal area = (BigDecimal)getArea(isBuildArea, isPreArea, rowSet);
    				obj[1] = getBigDecimal(obj[1]).add(CRMHelper.getBigDecimal(area));
    				BigDecimal standAmt = (BigDecimal)rowSet.getBigDecimal("standAmt");
    				obj[3] = getBigDecimal(obj[3]).add(CRMHelper.getBigDecimal(standAmt));
    			}else
    			{
    				Object[] tmpArray = new Object[9];
            		tmpArray[0] = rowSet.getBigDecimal("roomNum"); //????????
            		tmpArray[1] = this.getArea(isBuildArea, isPreArea, rowSet);
            		tmpArray[2] = FDCHelper.ZERO; //????????
            		tmpArray[3] = rowSet.getBigDecimal("standAmt"); //????????
            		tmpArray[4] = FDCHelper.ZERO; //????????
            		tmpArray[5] = FDCHelper.ZERO; //????????
            		tmpArray[6] = FDCHelper.ZERO; //????????=????????+??????
            		tmpArray[8] = FDCHelper.ZERO;//????????
            		PeriodNoGatheringDataMap.put(tmpKey, tmpArray);
    			}
    		}else
    		{
    			if(PeriodNoGatheringDataMap.get(tmpKey)!=null)
        		{
        			Object[] obj = (Object[])PeriodNoGatheringDataMap.get(tmpKey);
        			BigDecimal roomNum = (BigDecimal)rowSet.getBigDecimal("roomNum");
    				obj[0] = getBigDecimal(obj[0]).add(CRMHelper.getBigDecimal(roomNum));
    				BigDecimal area = (BigDecimal)getArea(isBuildArea, isPreArea, rowSet);
    				obj[1] = getBigDecimal(obj[1]).add(CRMHelper.getBigDecimal(area));
    				BigDecimal dealAmt = (BigDecimal)rowSet.getBigDecimal("dealAmt");
    				obj[2] = getBigDecimal(obj[2]).add(CRMHelper.getBigDecimal(dealAmt));
    				BigDecimal standAmt = (BigDecimal)rowSet.getBigDecimal("standAmt");
    				obj[3] = getBigDecimal(obj[3]).add(CRMHelper.getBigDecimal(standAmt));
    				BigDecimal contractAmt = (BigDecimal)rowSet.getBigDecimal("contractAmt");
    				obj[4] = getBigDecimal(obj[4]).add(CRMHelper.getBigDecimal(contractAmt));
    				BigDecimal purchaseAmt = (BigDecimal)rowSet.getBigDecimal("purchaseAmt");
    				obj[5] = getBigDecimal(obj[5]).add(CRMHelper.getBigDecimal(purchaseAmt));
        			BigDecimal contract = (BigDecimal)obj[4];
        			obj[6] = CRMHelper.getBigDecimal(contract).add(compentAmount);
        			BigDecimal youhui = getBigDecimal(obj[3]).subtract(getBigDecimal(obj[2]));
        			obj[8] = getBigDecimal(obj[8]).add(CRMHelper.getBigDecimal(youhui));
        			PeriodNoGatheringDataMap.put(tmpKey, obj);
        		}else
        		{
        			Object[] tmpArray = new Object[9];
            		tmpArray[0] = rowSet.getBigDecimal("roomNum"); //????????
            		tmpArray[1] = this.getArea(isBuildArea, isPreArea, rowSet);
            		BigDecimal dealAmt  = rowSet.getBigDecimal("dealAmt"); //????????
            		dealAmt = dealAmt==null?FDCHelper.ZERO:dealAmt;
            	    tmpArray[2] = dealAmt;
            		BigDecimal standAmt = rowSet.getBigDecimal("standAmt"); //????????
            		standAmt =  standAmt==null?FDCHelper.ZERO:standAmt;
            		tmpArray[3] = standAmt;
            		tmpArray[4] = rowSet.getBigDecimal("contractAmt"); //????????
            		tmpArray[5] = rowSet.getBigDecimal("purchaseAmt"); //????????
            		BigDecimal contract = rowSet.getBigDecimal("contractAmt");
            		contract = contract==null?new BigDecimal(0):contract;
            		tmpArray[6] = contract.add(compentAmount); //????????=????????+??????
            		tmpArray[8] = standAmt.subtract(dealAmt);
            		PeriodNoGatheringDataMap.put(tmpKey, tmpArray);
        		}
    		}    		   		 	   		
    	}
    	
    	//??????????????
    	builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdType.Fid as prdTypeId, rmodel.fid as rmodelId,sum(purchase.fcontracttotalamount) as contractAmt \n");
    	builder.appendSql("from t_she_purchase purchase \n");
    	builder.appendSql("left join t_she_room room on room.fid = purchase.froomid \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
		builder.appendSql("left join T_SHE_RoomSignContract roomSign on roomSign.fpurchaseid = purchase.fid  \n");
		//????????????????????
		builder.appendSql("where (roomSign.fstate='4AUDITTED' or roomSign.fstate='2SUBMITTED') ");
		builder.appendSql(" and ");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
    	builder.appendSql("purchase.fid in (" + sql + ")");
    	builder.appendSql("\n group by prdType.Fid, rmodel.fid");
    	    	
    	rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		String tmpPrdTypeId = rowSet.getString("prdTypeId");
    		String tmpRmodelId = rowSet.getString("rmodelId");
    		String tmpKey = tmpPrdTypeId + tmpRmodelId;
    		if(PeriodNoGatheringDataMap.get(tmpKey)!=null)
    		{
    			Object[] obj = (Object[])PeriodNoGatheringDataMap.get(tmpKey);
    			obj[7] = rowSet.getBigDecimal("contractAmt");
    			PeriodNoGatheringDataMap.put(tmpKey, obj);
    		}
    	}
    	
    	//??????????????????????????????????????????????????????????????
    	builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdTypeId as prdTypeId, rmodelId as rmodelId,sum(actamount) actamount, \n");
    	builder.appendSql("sum(transAmount) transAmount, sum(refundAmount) refundAmount,sum(justAmount) justAmount \n");
    	builder.appendSql("from ( \n");
    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
    	builder.appendSql("sum(payLists.factrevamount) actamount, \n");
    	builder.appendSql("sum(payLists.Fhastransferredamount) transAmount, \n");
    	builder.appendSql("sum(payLists.Fhasrefundmentamount) refundAmount, \n");
    	builder.appendSql("sum(payLists.Fhasadjustedamount) justAmount \n");
    	builder.appendSql("from t_she_purchasepaylistentry payLists \n");
    	//builder.appendSql("inner join t_she_moneydefine monDefine on payLists.Fmoneydefineid = monDefine.Fid \n");
    	builder.appendSql("left join t_she_purchase purchase on  payLists.Fheadid = purchase.fid \n");
    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //????????????????????????????????
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	builder.appendSql("where \n");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
    	builder.appendSql("purchase.fid in (" + sql + ")");
    	builder.appendSql("group by prdType.Fid, rmodel.fid \n");
    	
//    	builder.appendSql("union \n");
//    	
//    	//????????????????????????????????????????
//    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
////    	builder.appendSql("sum(elsePayLists.FactRevAmount-elsePayLists.Fhastransferredamount-elsePayLists.Fhasrefundmentamount-elsePayLists.Fhasadjustedamount) as gatheringAmt \n");
//    	builder.appendSql("sum(elsePayLists.factrevamount) actamount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhastransferredamount) transAmount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhasrefundmentamount) refundAmount, \n");
//    	builder.appendSql("sum(elsePayLists.Fhasadjustedamount) justAmount \n");
//    	builder.appendSql("from t_she_purchaseelsepaylistentry elsePayLists \n");
//    	//builder.appendSql("inner join t_she_moneydefine monDefine on elsePayLists.Fmoneydefineid = monDefine.Fid \n");
//    	builder.appendSql("left join t_she_purchase purchase on  elsePayLists.Fheadid = purchase.fid \n");
//    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //????????????????????????????????
//    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
//    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
//    	builder.appendSql("where \n");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
//    	builder.appendSql("group by prdType.Fid, rmodel.fid \n");
    	
    	builder.appendSql(") PayListSum \n");
    	builder.appendSql("group by prdTypeId, rmodelId \n");
    	builder.appendSql("order by prdTypeId, rmodelId asc \n");
    	
//    	logger.info(builder.getTestSql());
    	
    	rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		String tmpKey = rowSet.getString("prdTypeId") + rowSet.getString("rmodelId");
    		//????????
     		BigDecimal revamount = rowSet.getBigDecimal("actamount")==null?new BigDecimal(0):rowSet.getBigDecimal("actamount");
     		//????????
     		BigDecimal transAmount = rowSet.getBigDecimal("transAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("transAmount");
     		//????????
     		BigDecimal refundAmount = rowSet.getBigDecimal("refundAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("refundAmount");
     		//????????
     		BigDecimal justAmount = rowSet.getBigDecimal("justAmount")==null?new BigDecimal(0):rowSet.getBigDecimal("justAmount");
     		//????????=????????-????????-????????-????????
     		BigDecimal actamount = revamount.subtract(transAmount).subtract(refundAmount).subtract(justAmount);
    		PeriodGatheringDataMap.put(tmpKey,actamount);
    	}
    	result.put("PeriodNoGatheringData", PeriodNoGatheringDataMap);
    	result.put("PeriodGatheringData", PeriodGatheringDataMap);   	
    	return result;
    }
    
    /**
     * ????????????????????????????
     * @param ctx
     * @param paramMap
     * @param validPurchaseIds
     * @return
     * @throws BOSException
     * @throws SQLException
     * @throws EASBizException 
     */
    protected Map getPeriodGatheringData(Context ctx, Map paramMap, Set validPurchaseIds) throws BOSException, SQLException, EASBizException
    {
    	Map result = new HashMap();
    	Map purchaseMap = new HashMap();
//    	purchaseMap = getValidPurchaseIdSet(ctx, paramMap);
//    	validPurchaseIds = (Set)purchaseMap.get("purchase");
    	String sql = getValidPurchaseIdSetNew(ctx,paramMap);
    	if(validPurchaseIds.size() <= 0)
    	{
    		return result;
    	}
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdTypeId as prdTypeId, rmodelId as rmodelId, sum(gatheringAmt) as gathAmt \n");
    	builder.appendSql("from ( \n");
    	
    	//????????????????????????????????
    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
    	builder.appendSql("sum(payLists.FactRevAmount-payLists.Fhastransferredamount-payLists.Fhasrefundmentamount-payLists.Fhasadjustedamount) as gatheringAmt \n");
    	builder.appendSql("from t_she_purchasepaylistentry payLists \n");
    	//builder.appendSql("inner join t_she_moneydefine monDefine on payLists.Fmoneydefineid = monDefine.Fid \n");
    	builder.appendSql("left join t_she_purchase purchase on  payLists.Fheadid = purchase.fid \n");
    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //????????????????????????????????
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	
    	builder.appendSql("where \n");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
		builder.appendSql("purchase.fid in (" + sql + ")");
    	builder.appendSql("group by prdType.Fid, rmodel.fid \n");
    	
    	builder.appendSql("union \n");
    	
    	//????????????????????????????????????????
    	builder.appendSql("select  prdType.Fid as prdTypeId, rmodel.fid as rmodelId,  \n");
    	builder.appendSql("sum(elsePayLists.FactRevAmount-elsePayLists.Fhastransferredamount-elsePayLists.Fhasrefundmentamount-elsePayLists.Fhasadjustedamount) as gatheringAmt \n");
    	builder.appendSql("from t_she_purchaseelsepaylistentry elsePayLists \n");
    	//builder.appendSql("inner join t_she_moneydefine monDefine on elsePayLists.Fmoneydefineid = monDefine.Fid \n");
    	builder.appendSql("left join t_she_purchase purchase on  elsePayLists.Fheadid = purchase.fid \n");
    	builder.appendSql("left join t_she_room room on room.flastpurchaseid = purchase.fid \n"); //????????????????????????????????
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	builder.appendSql("where \n");
//    	builder.appendParam("purchase.fid", validPurchaseIds.toArray());
    	builder.appendSql("purchase.fid in (" + sql + ")");
    	builder.appendSql("group by prdType.Fid, rmodel.fid \n");
    	
    	builder.appendSql(") PayListSum \n");
    	builder.appendSql("group by prdTypeId, rmodelId \n");
    	builder.appendSql("order by prdTypeId, rmodelId asc \n");
    	
//    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	while(rowSet.next())
    	{
    		String tmpKey = rowSet.getString("prdTypeId") + rowSet.getString("rmodelId");
    		result.put(tmpKey, rowSet.getBigDecimal("gathAmt"));
    	}
    	
    	return result;
    }
    /**
     * ????????????????????????????????????????????????????????????
     * @param ctx
     * @param paramMap
     * @param validPurchaseIds
     * @return
     * @throws SQLException 
     * @throws BOSException 
     * @throws EASBizException 
     */
    protected Map getSubAmtData(Context ctx, Map paramMap) throws BOSException, SQLException, EASBizException
    {
    	Map result = new HashMap();
    	
    	//??????????????ID
    	Set selledRoomIds = getSelledRoomIdSet(ctx, paramMap);
    	if(selledRoomIds == null || selledRoomIds.size() == 0)
    	{
    		return result;
    	}
    	
    	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
    	builder.appendSql("select prdType.Fid as prdTypeId, rmodel.fid as rmodelId, sum(areaComp.FCompensateAmount) as compAmt \n");
    	builder.appendSql("from T_SHE_RoomAreaCompensate areaComp \n");
//    	builder.appendSql("left join T_SHE_AreaCompensateRevList comRevList on areaComp.fid = comRevList.fheadid  \n");
    	builder.appendSql("left join t_she_room room on room.fid = areaComp.Froomid \n");
    	builder.appendSql("left join t_fdc_producttype prdType on prdType.Fid = room.fproducttypeid \n");
    	builder.appendSql("left join t_she_roommodel rmodel on rmodel.fid = room.froommodelid \n");
    	builder.appendSql("where areaComp.Fcompensatestate in ('COMAUDITTED', 'COMRECEIVED') and \n");
    	builder.appendParam("room.fid", selledRoomIds.toArray());
//    	builder.appendSql("\n and room.FSellState = 'Sign'\n");
    	builder.appendSql("group by prdType.Fid, rmodel.fid");
    	
    	logger.info(builder.getTestSql());
    	
    	IRowSet rowSet = builder.executeQuery(ctx);
    	
    	while(rowSet.next())
    	{
    		String tmpKey = rowSet.getString("prdTypeId") + rowSet.getString("rmodelId");
    		result.put(tmpKey, rowSet.getBigDecimal("compAmt"));
    	}
    	
    	return result;
    }
    
    public static BigDecimal getBigDecimal(Object obj){
    	if(obj==null)
    	{
    		return FDCHelper.ZERO;
    	}else
    	{
    		if(obj instanceof BigDecimal)
    		{
    			return (BigDecimal)obj;
    		}else
    		{
    			return FDCHelper.ZERO;
    		}
    	}
	}
    
    ///////////////////////////////////////////////????????-end/////////////////////////////////////////////////////////////

	/**
	 * selectNode = ????????????????????????????????????????<br>
	 * IncludeAttachment = ??????????????????????????????????????<br>
	 * BuildArea = ??????????????????????????????????????????????<br>
	 * BeginDate = ????????<br>
	 * EndDate = ????????<br>
	 * IncludeOrder = ?????????????? ????????????????????????<br>
	 * IsAuditDate = ????????????????????????????????????????????????????????????????????<br>
	 * 
	 * ??????????id??????????2000???????????????????????????????????????????? in select ????<br>
	 * 
	 * @see <code>getValidPurchaseIdSet(Context ctx, Map paramMap)</code><br>
	 * 
	 * @author jiyu_guan<br>
	 * @throws EASBizException
	 */
	public static String getValidPurchaseIdSetNew(Context ctx, Map paramMap)
			throws BOSException, SQLException, EASBizException {

		FDCSQLBuilder sql = new FDCSQLBuilder();
		sql.appendSql(" select pch.FID ");
		sql.appendSql(" from t_she_purchase as pch ");
		sql.appendSql(" left join t_she_room room on room.fid = pch.FRoomId ");
		sql
				.appendSql(" left join t_she_quitroom qr on qr.fpurchaseid = pch.fid ");
		sql
				.appendSql(" left join t_she_changeroom cr on cr.FOldPurchaseID = pch.fid ");
		// ????????????
		appendSelectedNodeFilter(sql, paramMap, ctx);
		Boolean all = (Boolean) paramMap.get("isShowAll");
		boolean isShowAll = true;
		if (all == null || !all.booleanValue()) {
			isShowAll = false;
		}
//		boolean isShowAll = ((Boolean) paramMap.get("isShowAll"))
//				.booleanValue();
		boolean includeOrder = ((Boolean) paramMap.get("IncludeOrder"))
				.booleanValue();
		final DateFormat FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = (Date) paramMap.get("BeginDate");
		Date endDate = (Date) paramMap.get("EndDate");
		String strBg;
		
		// ????????????
		if (isShowAll || beginDate == null) {
			strBg = "1900-01-01";
		} else {
			strBg = FORMAT_DAY.format(beginDate);
		}
		String strEd;
		if (isShowAll || beginDate == null) {
			strEd = "2999-12-31";
		} else {
			strEd = FORMAT_DAY.format(endDate);
		}

		// ????????????
		if (includeOrder) {
			sql.appendSql(" and pch.FTosaleDate >= {ts '");
			sql.appendSql(strBg);
			sql.appendSql("'} ");
			sql.appendSql(" and pch.FTosaleDate < {ts '");
			sql.appendSql(strEd);
			sql.appendSql("'} ");
		}
		// ??????????
		else {
			sql.appendSql(" and pch.FTopurchaseDate >= {ts '");
			sql.appendSql(strBg);
			sql.appendSql("'} ");
			sql.appendSql(" and pch.FTopurchaseDate < {ts '");
			sql.appendSql(strEd);
			sql.appendSql("'} ");
		}
		// ????????????
		if (includeOrder) {
			sql
					.appendSql(" and pch.fpurchasestate in ('PurchaseApply', 'PurchaseAuditing','PrePurchaseCheck', 'PurchaseAudit', 'PurchaseChange','QuitRoomBlankOut','ChangeRoomBlankOut') ");
		} else {
			sql
					.appendSql(" and pch.fpurchasestate in ('PurchaseApply', 'PurchaseAuditing','PurchaseAudit', 'PurchaseChange','QuitRoomBlankOut','ChangeRoomBlankOut') ");
		}
		sql.appendSql("and pch.ftosaledate is not null \n");
		// ????????????????
		boolean includeAttach = ((Boolean) (paramMap.get("IncludeAttachment")))
				.booleanValue();
		if (includeAttach) {
			sql
					.appendSql(" and room.FHouseproperty in ('Attachment','NoAttachment') ");
		} else {
			sql.appendSql(" and room.FHouseproperty = 'NoAttachment' ");
		}
		// ??????????????????????????????????????????????
		sql.appendSql(" and (qr.FID is null or qr.FState <> '4AUDITTED' ");
		sql.appendSql(" or qr.FAudittime < {ts '");
		sql.appendSql(strBg);
		sql.appendSql("'} or qr.FAudittime > {ts '");
		sql.appendSql(strEd);
		sql.appendSql("'}) ");
		// ??????????????????????????????????????????????????????????????????????????
		sql.appendSql(" and (cr.FID is null or cr.FState <> '4AUDITTED' ");
		sql.appendSql(" or cr.FAudittime < {ts '");
		sql.appendSql(strBg);
		sql.appendSql("'} or cr.FAudittime > {ts '");
		sql.appendSql(strEd);
		sql.appendSql("'}) ");
		
		return sql.getTestSql();
	}
    
}