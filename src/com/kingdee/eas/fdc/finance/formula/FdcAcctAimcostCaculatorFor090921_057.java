package com.kingdee.eas.fdc.finance.formula;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.common.variant.Variant;
import com.kingdee.bos.ctrl.excel.model.struct.IMethodBatchQuery;
import com.kingdee.bos.ctrl.excel.model.struct.Parameter;
import com.kingdee.bos.ctrl.excel.model.util.SortedParameterArray;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fi.newrpt.formula.ICalculateContextProvider;
import com.kingdee.eas.fi.newrpt.formula.ICalculator;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

public class FdcAcctAimcostCaculatorFor090921_057 implements ICalculator, IMethodBatchQuery 
{
	private Context ServerCtx = null;
	
	private String fdcCompanyNumber = null ;
	private boolean displayAllCompany = false;
	private String prjNumber = null ;
	private String acctLongNumber = null ;
	private String beginPeriod = null;
	private String endPeriod = null;
	
	private ICalculateContextProvider context;
	
	public FdcAcctAimcostCaculatorFor090921_057()
	{
		
	}
	public Context getServerCtx()
	{
		return ServerCtx;
	}
	
	public void setServerCtx(Context serverCtx)
	{
		ServerCtx = serverCtx;
	}
	
	public void initCalculateContext(ICalculateContextProvider context)
	{
		this.context = context;
		this.ServerCtx = this.context.getServerContext();
	}
	
	public boolean batchQuery(Map methods)
	{
		SortedParameterArray params = (SortedParameterArray) methods.get("fdc_acct_aimcost_filterByDate");
		if (params != null)
		{
			for(int i=0;i<params.size();i++)
			{
			Parameter param = params.getParameter(i);
			Object[] obj = param.getArgs();
			
			fdcCompanyNumber = (String) ((Variant) obj[0]).getValue();
			prjNumber =(String) ((Variant) obj[2]).getValue();
			acctLongNumber = (String) ((Variant) obj[3]).getValue();
			beginPeriod = (String) ((Variant) obj[4]).getValue();
			endPeriod = (String) ((Variant) obj[5]).getValue();
		
		try
		{
			BigDecimal amount = this.fdc_acct_aimcost_filterByDate(fdcCompanyNumber, false,prjNumber,acctLongNumber, beginPeriod,endPeriod);
			params.getParameter(i).setValue(amount);
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		}
	}
		
		return true;
	}
	/**
	 * ????????--????????????????
	 * @param fdcCompanyNumber ????????
	 * @param prjNumber        ??????????
	 * @param acctLongNumber   ??????????????
	 * @param month            ????
	 * @return DynCostSnapShot????aimCost
	 * @throws ParseException 
	 * @throws BOSException 
	 * @throws SQLException 
	 */
	public BigDecimal fdc_acct_aimcost_filterByDate(String fdcCompanyNumber,boolean displayAllCompany,String prjNumber,String acctLongNumber,String beginPeriod,String endPeriod) throws ParseException, BOSException, SQLException
	{
		FDCSQLBuilder builder = new FDCSQLBuilder() ;
		
		/**
		 * ??????????????
		 */
		prjNumber = prjNumber.replace('.','!');
		acctLongNumber = acctLongNumber.replace('!','.');
		
		//????????????????????????????????????????????????????????????
		Date endDate = null ;
		java.text.DateFormat df2 = new java.text.SimpleDateFormat("yyyy-MM-dd");
		
		endDate = df2.parse(endPeriod);
		
		endDate = FDCDateHelper.getLastDayOfMonth2(endDate);
		
		/**
		 * ????T_ORG_CostCenter????number??fdcCompanyNumber,????FCostCenterId
		 * ??????T_FDC_CurProject??FCostCenterId????????????????FlongNumber??prjNumber
		 * ????????????????????fid????????????????????????????????????ID
		 */
		builder.appendSql("select curProject.fid from T_FDC_CurProject curProject ");
		builder.appendSql(" inner join T_ORG_BaseUnit costCenter on curProject.FFullOrgUnit = costCenter.fid ");
		builder.appendSql(" where costCenter.FLongNumber = ? ");
		builder.addParam(fdcCompanyNumber);
		builder.appendSql(" and ");
		builder.appendSql(" curProject.FLongNumber = ? ");
		builder.addParam(prjNumber);
		
		/**
		 * ????????????ID????List??????????????????????????????????????
		 */
		List curProjectID = new ArrayList();
		
		IRowSet rowSet = builder.executeQuery(ServerCtx);
		
		while((rowSet != null) && (rowSet.next()))
		{
			curProjectID.add(rowSet.getString("fid"));
		}
		
		//????builder????sql????
		builder.clear();
		
		/**
		 * ??????????????????????ID??T_FDC_CostAccount????????????ID????????
		 * ??????????????????????????????????????????????????ID
		 */
		BigDecimal result = FDCHelper.ZERO ;
		if(curProjectID != null && curProjectID.size() == 1)
		{
			String projectID = curProjectID.get(0).toString() ;
				builder.clear();
				builder.appendSql(" select sum( FAimCostAmt ) as FAimCostAmt  from T_AIM_DynCostSnapShot ");
				builder.appendSql(" where fprojectid=? and charindex(?||'.',fcostacctlgnumber||'.')=1 and FSnapShotDate=? ");
//				builder.appendSql(" where fprojectid=? and charindex(?||'.',fcostacctlgnumber||'.')=1 and FSnapShotDate=? and FSavedType =?");
				builder.addParam(projectID);
				builder.addParam(acctLongNumber);
				builder.addParam( endDate );
//				builder.addParam(CostMonthlySaveTypeEnum.AUTOSAVE_VALUE);
//			}
			
			IRowSet rowSetTemp = builder.executeQuery(ServerCtx);
			
			while((rowSetTemp != null) && (rowSetTemp.next()))
			{
				result = rowSetTemp.getBigDecimal("FAimCostAmt");
			}
			
		
		}
		
		if(result == null)
		{
			result = FDCHelper.ZERO ;
		}
		return result;
	}
}
