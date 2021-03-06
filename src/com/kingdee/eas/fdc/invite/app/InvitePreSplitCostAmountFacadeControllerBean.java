package com.kingdee.eas.fdc.invite.app;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.aimcost.HappenDataGetter;
import com.kingdee.eas.fdc.aimcost.HappenDataInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.contract.web.ContractWebHelper;
import com.kingdee.eas.fdc.invite.InvitePreSplitEntryCollection;
import com.kingdee.eas.fdc.invite.InvitePreSplitEntryInfo;
import com.kingdee.jdbc.rowset.IRowSet;

public class InvitePreSplitCostAmountFacadeControllerBean extends AbstractInvitePreSplitCostAmountFacadeControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.app.InvitePreSplitCostAmountFacadeControllerBean");
   
    protected Map _getCostAccRelDatas(Context ctx, Set costAcctCols)throws BOSException, EASBizException
    {
    	Map prjId_CostAccount = new HashMap();
    	Iterator costAcctColsIter = costAcctCols.iterator();
    	Set prjIdSet = new HashSet();
    	
    	InvitePreSplitEntryCollection splitEntryCols = new InvitePreSplitEntryCollection();
    	while(costAcctColsIter.hasNext())
    	{
    		CostAccountInfo costAcctInfo = (CostAccountInfo)(costAcctColsIter.next());
    		
    		String prjId = costAcctInfo.getCurProject().getId().toString();
    		
    		if(!prjId_CostAccount.containsKey(prjId))
    		{
    			Set tmpSet = new HashSet();
    			tmpSet.add(costAcctInfo);
    			
    			prjId_CostAccount.put(prjId, tmpSet);
    			prjIdSet.add(prjId);
    			
    		}
    		else
    		{
    			((Set)prjId_CostAccount.get(prjId)).add(costAcctInfo);
    		}
    	}
    	
    	Iterator prjIdSetIter = prjIdSet.iterator();
    	while(prjIdSetIter.hasNext())
    	{
    		String prjId = (String)prjIdSetIter.next();
    		
//    		final FullDynamicCostMap fullDynamicCostMap = FDCCostRptFacadeFactory.getLocalInstance(ctx).getFullDynamicCost(prjId, null);
//    		
//    		//????????
//    		Map aimCostMap = fullDynamicCostMap.getAimCostMap();
//    		
//    		//????????
//    		Map dyCostMap = fullDynamicCostMap.getDynamicCostMapp();
//    		
//    		//??????????????
//    		HappenDataGetter happenGetter = fullDynamicCostMap.getHappenDataGetter();
    		
    		Set tmpCostAccts = (Set)prjId_CostAccount.get(prjId);
    		
    		Set tmpCostAcctsId = new HashSet();
    		Iterator costIdIter = tmpCostAccts.iterator();
    		while(costIdIter.hasNext())
    		{
    			CostAccountInfo tmpCostInfo = (CostAccountInfo)costIdIter.next();
    			tmpCostAcctsId.add(tmpCostInfo.getId().toString());
    		}
    		
    		//??????????????
    		Map splitedCostTotal = getSplitedCostTotal(tmpCostAccts, ctx);
    		
    		//????????
    		Map costAccountMap = getCostAccountMap(tmpCostAccts, ctx);
    		
    		Map costAllInfoMap = ContractWebHelper.getCostInfo(ctx, tmpCostAcctsId);
    		
    		InvitePreSplitEntryFactory(tmpCostAccts, costAllInfoMap, splitedCostTotal, splitEntryCols);
    		
//    		InvitePreSplitEntryFactory(tmpCostAccts, costAccountMap, aimCostMap, dyCostMap, happenGetter, splitedCostTotal, splitEntryCols);
    	}
    	
    	Map entryDatas = new HashMap();
    	entryDatas.put("INVITE_PRESPLIT_ENTRY", splitEntryCols);
    	
        return entryDatas;
    }
    private Map getCostAccountMap(Set paramCostAccts, Context ctx)
    {
    	Map costAccountMap = new HashMap();
    	
    	Iterator paramCostAcctsIter = paramCostAccts.iterator();
    	while(paramCostAcctsIter.hasNext())
    	{
    		CostAccountInfo tmpAcctInfo = (CostAccountInfo)paramCostAcctsIter.next();
    		costAccountMap.put(tmpAcctInfo.getId().toString(), tmpAcctInfo);
    	}

    	return costAccountMap;
    }
    private Map getSplitedCostTotal(Set paramCostAccts, Context ctx) throws BOSException
    {
    	Set costAcctId = new HashSet();
    	Iterator paramCostAcctsIter = paramCostAccts.iterator();
    	while(paramCostAcctsIter.hasNext())
    	{
    		CostAccountInfo tmpAcctInfo = (CostAccountInfo)paramCostAcctsIter.next();
    		costAcctId.add(tmpAcctInfo.getId().toString());
    	}
    	
    	Map splitedCostTotal = new HashMap();
		try {
			
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			
			builder.appendSql("select ent.fcostamountid as costAmountId,  sum(ent.fpresplitamount) as preSplitAmount from t_inv_invitepresplitentry ent ");
			builder.appendSql(" left outer join T_FDC_CostAccount acc on ent.fcostamountid = acc.fid ");
			builder.appendSql(" left outer join t_inv_invitepresplit slit on ent.fparentid = slit.fid ");
			
			builder.appendSql(" where ");
			builder.appendParam(" slit.fiscontractsplit ", Boolean.FALSE);
			builder.appendSql(" and ");
			builder.appendParam(" ent.FCostAmountID ", costAcctId.toArray());

			builder.appendSql(" group by ent.fcostamountid");
			
			IRowSet rowSet = builder.executeQuery();
			while (rowSet.next()) {
				splitedCostTotal.put(rowSet.getString("costAmountId"), rowSet.getBigDecimal("preSplitAmount"));
			}

		} catch (SQLException e) {
			throw new BOSException(e);
		}
		
		return splitedCostTotal;
    }
    
    private void InvitePreSplitEntryFactory(Set paramCostAcctSet, Map paramCostAllInfoMap, Map paramSplitCostTotal, InvitePreSplitEntryCollection paramSplitEntryCols)
    {
    	Iterator paramCostAcctSetIter = paramCostAcctSet.iterator();
    	
    	while(paramCostAcctSetIter.hasNext())
    	{
    		CostAccountInfo tmpCostAcctInfo = (CostAccountInfo)(paramCostAcctSetIter.next());
    		
    		String costAcctId = tmpCostAcctInfo.getId().toString();
    		
    		InvitePreSplitEntryInfo tmpEntryInfo = new InvitePreSplitEntryInfo();
    		
    		tmpEntryInfo.setCurProject(tmpCostAcctInfo.getCurProject());
    		tmpEntryInfo.setCostAccount(tmpCostAcctInfo);
    
    		//????????
    		tmpEntryInfo.setAimCost((BigDecimal)paramCostAllInfoMap.get(costAcctId+"_aim"));
    		
    		//????????
    		tmpEntryInfo.setDynamicCost((BigDecimal)paramCostAllInfoMap.get(costAcctId+"_dyn"));
    		
    		//????????????
    		tmpEntryInfo.setOccuredCost((BigDecimal)paramCostAllInfoMap.get(costAcctId+"_happen"));
    		
    		//??????????????
    		BigDecimal splitedCostTotal = (BigDecimal)paramSplitCostTotal.get(costAcctId);
    		if(splitedCostTotal == null)
    		{
    			splitedCostTotal = new BigDecimal("0");
    		}
    		tmpEntryInfo.setSplitedCost(splitedCostTotal);
    		
    		paramSplitEntryCols.add(tmpEntryInfo);
    	}
    }
    
    private void InvitePreSplitEntryFactory(Set paramCostAcctSet, Map paramCostAccts, Map paramAimCost, Map paramDynCost, 
    		HappenDataGetter paramHappenCost, Map paramSplitCostTotal, InvitePreSplitEntryCollection paramSplitEntryCols)
    {
    	Iterator paramCostAcctSetIter = paramCostAcctSet.iterator();
    	
    	while(paramCostAcctSetIter.hasNext())
    	{
    		CostAccountInfo tmpCostAcctInfo = (CostAccountInfo)(paramCostAcctSetIter.next());
    		
    		String costAcctId = tmpCostAcctInfo.getId().toString();
    		
    		InvitePreSplitEntryInfo tmpEntryInfo = new InvitePreSplitEntryInfo();
    		
    		tmpEntryInfo.setCurProject(tmpCostAcctInfo.getCurProject());
    		tmpEntryInfo.setCostAccount(tmpCostAcctInfo);
    		
    		//????????
    		BigDecimal aimAmount = (BigDecimal)paramAimCost.get(costAcctId);
    		if(aimAmount == null)
    		{
    			aimAmount = new BigDecimal("0");
    		}
    		tmpEntryInfo.setAimCost(aimAmount);
    		
    		//????????
    		BigDecimal mapDynAmount = (BigDecimal)paramDynCost.get(costAcctId);
    		if(mapDynAmount == null)
    		{
    			mapDynAmount = new BigDecimal("0");
    		}
    		BigDecimal dynAmount = aimAmount.add(mapDynAmount);
    		tmpEntryInfo.setDynamicCost(dynAmount);
    		
    		//????????????
//    		HappenDataInfo info = (HappenDataInfo)paramHappenCost.get(costAcctId);
    		HappenDataInfo info = paramHappenCost.getHappenInfo(costAcctId);
    		
    		if(info != null)
    		{
    			BigDecimal happenAmount = (BigDecimal)info.getAmount();
    			tmpEntryInfo.setOccuredCost(happenAmount);
    		}
    		else
    		{
    			tmpEntryInfo.setOccuredCost(new BigDecimal("0"));
    		}
    		
    		//??????????????
    		BigDecimal splitedCostTotal = (BigDecimal)paramSplitCostTotal.get(costAcctId);
    		if(splitedCostTotal == null)
    		{
    			splitedCostTotal = new BigDecimal("0");
    		}
    		tmpEntryInfo.setSplitedCost(splitedCostTotal);
    		
    		paramSplitEntryCols.add(tmpEntryInfo);
    	}
    }
    
}