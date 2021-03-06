package com.kingdee.eas.fdc.finance.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import java.util.Calendar;

import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;

import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherEntryFactory;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherInfo;
import com.kingdee.eas.fdc.finance.ProjectMonthPlanGatherFactory;
import com.kingdee.eas.fdc.finance.VersionTypeEnum;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.fdc.basedata.app.FDCBillControllerBean;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.fdc.finance.OrgUnitMonthPlanGatherCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCBillCollection;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.util.NumericExceptionSubItem;

public class OrgUnitMonthPlanGatherControllerBean extends AbstractOrgUnitMonthPlanGatherControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.finance.app.OrgUnitMonthPlanGatherControllerBean");
    
    protected void _audit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._audit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("orgUnit.id");
		sel.add("version");
		sel.add("bizDate");
		sel.add("versionType");
		OrgUnitMonthPlanGatherInfo info =this.getOrgUnitMonthPlanGatherInfo(ctx, new ObjectUuidPK(billId), sel);
		String orgUnitId=info.getOrgUnit().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(info.getBizDate());
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_FNC_OrgUnitMonthPlanGather set FIsLatest = 1 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_FNC_OrgUnitMonthPlanGather set FIsLatest = 0 where fOrgUnitid = '");
		sql.append(orgUnitId).append("'");
		sql.append("and FVersion = ").append(version);
		sql.append("and year(fbizDate) = ").append(year);
		sql.append("and month(fbizDate) = ").append(month);
		sql.append("and fversionType = '").append(info.getVersionType().getValue()).append("'");
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
    }
    protected void _unAudit(Context ctx, BOSUuid billId) throws BOSException, EASBizException {
		super._unAudit(ctx, billId);
		SelectorItemCollection sel=new SelectorItemCollection();
		sel.add("orgUnit.id");
		sel.add("version");
		sel.add("isLatest");
		sel.add("bizDate");
		sel.add("versionType");
		OrgUnitMonthPlanGatherInfo info =this.getOrgUnitMonthPlanGatherInfo(ctx, new ObjectUuidPK(billId), sel);
		if(!info.isIsLatest()){
			throw new EASBizException(new NumericExceptionSubItem("100","??????????????????????"));
		}
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",info.getOrgUnit().getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("versionType",info.getVersionType().getValue()));
		filter.getFilterItems().add(new FilterItemInfo("bizDate",info.getBizDate()));
		filter.getFilterItems().add(new FilterItemInfo("version",info.getVersion(),CompareType.GREATER));
		if(OrgUnitMonthPlanGatherFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","????????????????????????????????"));
		}
		if(info.getVersionType().equals(VersionTypeEnum.REPORT)){
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("orgUnit.id",info.getOrgUnit().getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("bizDate",info.getBizDate()));
			filter.getFilterItems().add(new FilterItemInfo("versionType",VersionTypeEnum.EXECUTE_VALUE));
			if(OrgUnitMonthPlanGatherFactory.getLocalInstance(ctx).exists(filter)){
				throw new EASBizException(new NumericExceptionSubItem("100","??????????????????????????????????"));
			}
		}
		String orgUnitId = info.getOrgUnit().getId().toString();
		int version = info.getVersion();
		version = version-1;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(info.getBizDate());
		int year=cal.get(Calendar.YEAR);
		int month=cal.get(Calendar.MONTH)+1;
		
		FDCSQLBuilder fdcSB = new FDCSQLBuilder(ctx);
		fdcSB.setBatchType(FDCSQLBuilder.STATEMENT_TYPE);
		
		StringBuffer sql = new StringBuffer();
		sql.append("update T_FNC_OrgUnitMonthPlanGather set FIsLatest = 0 where fid = '").append(billId.toString()).append("'");
		fdcSB.addBatch(sql.toString());
		
		sql.setLength(0);
		sql.append("update T_FNC_OrgUnitMonthPlanGather set FIsLatest = 1 where fOrgUnitId = '");
		sql.append(orgUnitId).append("'");
		sql.append("and FVersion = ").append(version);
		sql.append("and year(fbizDate) = ").append(year);
		sql.append("and month(fbizDate) = ").append(month);
		sql.append("and fversionType = '").append(info.getVersionType().getValue()).append("'");
		fdcSB.addBatch(sql.toString());
		fdcSB.executeBatch();
	}
    protected boolean isUseName() {
		return false;
	}
    protected void _delete(Context ctx, IObjectPK pk) throws BOSException,EASBizException {
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("srcId",pk.toString()));
		if(OrgUnitMonthPlanGatherEntryFactory.getLocalInstance(ctx).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","??????????????????????????????"));
		}
		super._delete(ctx, pk);
	}
}