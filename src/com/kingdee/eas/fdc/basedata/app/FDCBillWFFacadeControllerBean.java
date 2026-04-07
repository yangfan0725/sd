/*jadclipse*/package com.kingdee.eas.fdc.basedata.app;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.base.multiapprove.ApproveResult;
import com.kingdee.eas.base.multiapprove.MultiApproveInfo;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.util.app.ContextUtil;
import com.kingdee.eas.util.app.DbUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.LocaleUtils;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import org.apache.log4j.Logger;
public class FDCBillWFFacadeControllerBean extends AbstractFDCBillWFFacadeControllerBean
{
            public FDCBillWFFacadeControllerBean()
            {
            }
            protected void _setWFAuditNotPrint(Context ctx, BOSUuid billId, BOSUuid auditorId)
                throws BOSException, EASBizException
            {








/*  35*/        FDCBillWFAuditInfoInfo info = new FDCBillWFAuditInfoInfo();
/*  36*/        info.setId(BOSUuid.create(info.getBOSType()));
/*  37*/        info.setBillId(String.valueOf(billId));
/*  38*/        info.setAuditorId(String.valueOf(auditorId));
/*  39*/        info.setInfoType("NOTPRINT");
/*  40*/        FDCBillWFAuditInfoFactory.getLocalInstance(ctx).addnew(info);
            }
            protected void _setWFAuditOrgInfo(Context ctx, BOSUuid billId, BOSUuid auditorId, String org)
                throws BOSException, EASBizException
            {/*  44*/        FDCBillWFAuditInfoInfo info = new FDCBillWFAuditInfoInfo();
/*  45*/        info.setId(BOSUuid.create(info.getBOSType()));
/*  46*/        info.setBillId(String.valueOf(billId));
/*  47*/        info.setAuditorId(String.valueOf(auditorId));
/*  48*/        info.setInfoType("ORGINFO");
/*  49*/        info.setOrgInfo(org);
/*  50*/        FDCBillWFAuditInfoFactory.getLocalInstance(ctx).addnew(info);
            }
            protected List _getWFAuditResultForPrint(Context ctx, String billId)
                throws BOSException, EASBizException
            {















/*  70*/        StringBuffer sql = new StringBuffer();
/*  71*/        String fnameloc = (new StringBuilder("FName_")).append(LocaleUtils.getLocaleString(ctx.getLocale())).toString();
/*  72*/        String fopinionloc = (new StringBuilder("FOpinion_")).append(LocaleUtils.getLocaleString(ctx.getLocale())).toString();
/*  73*/        sql.append((new StringBuilder("select t_org_ctrlunit.")).append(fnameloc).append(" as ").append("ctrlUnitName").append(",").append(" T_ORG_Admin.").append(fnameloc).append(" as ").append("adminUnitName").append(",").append(" t_pm_user.").append(fnameloc).append(" as ").append("peronName").append(",").append(" T_ORG_POSITION.").append(fnameloc).append(" as ").append("auditPosition").append(",").append(" t_pm_user.fid as auditorId,").append(" T_BAS_MultiApprove.").append(fopinionloc).append(" as ").append("opinion").append(",").append(" T_BAS_MultiApprove.fcreatetime as ").append("createTime").append(",").append(" T_BAS_MultiApprove.fassignmentid ,").append(" t_wfr_assigndetail.factdefID ,").append(" t_wfr_assigndetail.FACTDEFNAME_").append(LocaleUtils.getLocaleString(ctx.getLocale())).append(" as ").append("auditNodeName").append(",").append(" T_BAS_MultiApprove.FHandlerOption as ").append("handlerOpinion").append(",").append(" T_BAS_MultiApprove.FHandlerContent as ").append("handlerContent").append(",").append(" T_bas_multiapprove.fispass     as ").append("isPass").append(" \r\n").toString());












/*  86*/        sql.append(" from \r\n");
/*  87*/        sql.append(" T_BAS_MultiApprove \r\n");
/*  88*/        sql.append(" left outer join t_pm_user on T_BAS_MultiApprove.fcreatorid=t_pm_user.fid \r\n");
/*  89*/        sql.append(" left outer join t_org_positionmember  on t_pm_user.fpersonid=t_org_positionmember.fpersonid and  t_org_positionmember.fisprimary=1 \r\n");
/*  90*/        sql.append(" left outer join T_ORG_POSITION on T_ORG_POSITION.fid=t_org_positionmember.fpositionid \r\n");
/*  91*/        sql.append(" left outer join T_ORG_Admin on T_ORG_Admin.fid=T_ORG_POSITION.fadminorgunitid \r\n");
/*  92*/        sql.append(" left outer join t_org_ctrlunit on t_org_ctrlunit.fid=t_org_positionmember.fcontrolUnitid \r\n");
/*  93*/        sql.append(" left outer join t_wfr_assigndetail on t_wfr_assigndetail.fassignID=T_BAS_MultiApprove.fassignmentid \r\n");
				sql.append(" left outer join T_WFR_ActInst act on act.FACTINSTID=t_wfr_assigndetail.FACTINSTID ");
/*  94*/        sql.append(" where T_BAS_MultiApprove.fbillid=? ");
				IEnactmentService service = EnactmentServiceFactory.createEnactService(ctx);
				ProcessInstInfo processInstInfo = null;
				ProcessInstInfo procInsts[] = service.getProcessInstanceByHoldedObjectId(billId);
				int t = 0;
				for(int n = procInsts.length; t < n; t++)
					 if(procInsts[t].getState().startsWith("open"))
						 processInstInfo = procInsts[t];
				if(processInstInfo == null){
					 procInsts = service.getAllProcessInstancesByBizobjId(billId);
					 if(procInsts!=null){
						 if(procInsts.length==1){
							 processInstInfo=procInsts[0];
						 }else{
							 Timestamp date=null;
							 for(int k=0;k<procInsts.length;k++){
								 if(procInsts[k].getState().equals("closed.completed")){
									 if(date==null||procInsts[k].getCompleteTime().after(date)){
										 date=procInsts[k].getCompleteTime();
										 processInstInfo=procInsts[k];
									 }
								 }
							 }
						 }
					 }
				}
				if(processInstInfo!=null){
					 sql.append(" and act.FPROCINSTID ='"+processInstInfo.getProcInstId()+"' ");
				}
				sql.append(" order by T_BAS_MultiApprove.fcreatetime asc \r\n");


/*  97*/        IRowSet rowset = DbUtil.executeQuery(ctx, sql.toString(), new Object[] {/*  97*/            billId
                });







/* 106*/        StringBuffer sqlWfInfo = new StringBuffer();
/* 107*/        sqlWfInfo.append("select wf.fbillid,wf.fauditorid,wf.finfotype,ad.factdefID from t_fdc_fdcbillwfauditinfo wf inner join t_bas_multiapprove ma on (wf.fbillid = ma.fbillid and wf.fauditorid=ma.fcreatorid) inner join t_wfr_assigndetail ad on ad.fassignID=ma.fassignmentid where wf.fbillid=?");


/* 110*/        IRowSet rowsetWfInfo = DbUtil.executeQuery(ctx, sqlWfInfo.toString(), new Object[] {/* 110*/            billId
                });/* 111*/        Map noprintMap = new HashMap();
/* 112*/        Map orginfoMap = new HashMap();
/* 113*/        List resultList = new ArrayList();


/* 116*/        try
                {
/* <-MISALIGNED-> */ /* 116*/            for(int i = 0; rowsetWfInfo != null && i < rowsetWfInfo.size(); i++)
                    {
/* <-MISALIGNED-> */ /* 117*/                rowsetWfInfo.next();
/* <-MISALIGNED-> */ /* 118*/                String infoType = rowsetWfInfo.getString("finfotype");
/* <-MISALIGNED-> */ /* 119*/                if(infoType.equalsIgnoreCase("NOTPRINT"))
/* <-MISALIGNED-> */ /* 120*/                    noprintMap.put(rowsetWfInfo.getString("fauditorid"), Boolean.TRUE);
/* <-MISALIGNED-> */ /* 122*/                else
/* <-MISALIGNED-> */ /* 122*/                if(infoType.equalsIgnoreCase("ORGINFO"))
/* <-MISALIGNED-> */ /* 123*/                    orginfoMap.put(rowsetWfInfo.getString("factdefID"), Boolean.FALSE);
                    }




















/* 148*/            List tempList = new ArrayList();
/* 149*/            Map factdefIdMap = new HashMap();






/* 156*/            Map auditInfosMap = new HashMap();
/* 157*/            boolean isDuplicatePrint = false;
/* 158*/            isDuplicatePrint = FDCUtils.getDefaultFDCParamByKey(ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), "FDC012_WFIsDuplicateNotPrint");

/* 160*/            while(rowset != null && rowset.next()) 
                    {
/* <-MISALIGNED-> */ /* 160*/                String key = rowset.getString("auditorId");
/* <-MISALIGNED-> */ /* 161*/                String key2 = rowset.getString("factdefID");



/* 167*/                if(orginfoMap.containsKey(key2) && !((Boolean)orginfoMap.get(key2)).booleanValue())
                        {/* 168*/                    resultList.add(tempList);
/* 169*/                    tempList = new ArrayList();
/* 170*/                    orginfoMap.put(key2, Boolean.TRUE);
                        }




/* 176*/                if(factdefIdMap.containsKey(key2))
/* 177*/                    tempList = (ArrayList)factdefIdMap.get(key2);

/* 179*/                else/* 179*/                    factdefIdMap.put(key2, tempList);





/* 185*/                Map auditInfoMap = null;
/* 186*/                if(!auditInfosMap.containsKey(key))
/* 187*/                    auditInfoMap = new HashMap();


/* 190*/                else/* 190*/                if(!isDuplicatePrint)
/* 191*/                    auditInfoMap = (Map)auditInfosMap.get(key);

/* 193*/                else/* 193*/                    auditInfoMap = new HashMap();


/* 196*/                auditInfoMap.put("id", rowset.getString("auditorId"));
/* 197*/                auditInfoMap.put("ctrlUnitName", rowset.getString("ctrlUnitName"));
/* 198*/                auditInfoMap.put("adminUnitName", rowset.getString("adminUnitName"));
/* 199*/                auditInfoMap.put("opinion", rowset.getString("opinion"));
/* 200*/                auditInfoMap.put("peronName", rowset.getString("peronName"));
/* 201*/                auditInfoMap.put("createTime", rowset.getString("createTime"));
/* 202*/                auditInfoMap.put("auditPosition", rowset.getString("auditPosition"));
/* 203*/                auditInfoMap.put("auditNodeName", rowset.getString("auditNodeName"));
/* 204*/                String passResult = ApproveResult.getEnum(rowset.getString("isPass")).getAlias();
/* 205*/                auditInfoMap.put("isPass", passResult);
/* 206*/                auditInfoMap.put("handlerOpinion", String.valueOf(rowset.getInt("handlerOpinion")));
/* 207*/                auditInfoMap.put("handlerContent", rowset.getString("handlerContent"));



/* 211*/                if(!noprintMap.containsKey(key))


/* 214*/                    if(!auditInfosMap.containsKey(key))
                            {/* 215*/                        tempList.add(auditInfoMap);
/* 216*/                        auditInfosMap.put(key, auditInfoMap);
                            } else

/* 219*/                    if(isDuplicatePrint)
                            {/* 220*/                        tempList.add(auditInfoMap);
/* 221*/                        auditInfosMap.put(key, auditInfoMap);
                            }



/* 226*/                if(!rowset.getString("isPass").equalsIgnoreCase("true") && FDCUtils.getDefaultFDCParamByKey(ctx, ContextUtil.getCurrentFIUnit(ctx).getId().toString(), "FDC011_WFisPassIsFalseNotPrint"))
                        {





/* 233*/                    resultList = new ArrayList();
/* 234*/                    tempList = new ArrayList();
/* 235*/                    auditInfosMap = new HashMap();
                        }
                    }


/* 240*/            resultList.add(tempList);
                }/* 241*/        catch(SQLException e)
                {
/* 243*/            e.printStackTrace();
                }

/* 246*/        return resultList;
            }
            protected Map _getWFBillLastAuditorAndTime(Context ctx, Set billIds)
                throws BOSException, EASBizException
            {/* 250*/        Map result = new HashMap();

/* 252*/        FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
/* 253*/        String lang = ctx.getLocale().getLanguage();
/* 254*/        builder.appendSql((new StringBuilder("select t.fbillid,t.fcreatetime,creator.fid as creatorid,creator.fname_")).append(lang).append(" as creatorname from (( \n").toString());
/* 255*/        builder.appendSql("select t1.fbillid,t1.fcreateTime,t1.fcreatorId from T_BAS_MULTIAPPROVEHST t1  \n");
/* 256*/        builder.appendSql("inner join (select fbillid,max(fcreatetime) as fcreatetime from T_BAS_MULTIAPPROVEHST  where ");
/* 257*/        builder.appendParam("fbillid", billIds.toArray());
/* 258*/        builder.appendSql(" group by fbillid) t2 on t1.fbillid=t2.fbillid and t1.fcreatetime=t2.fcreatetime \n");
/* 259*/        builder.appendSql(") \n");
/* 260*/        builder.appendSql("union all \n");
/* 261*/        builder.appendSql("(select t1.fbillid,t1.fcreateTime,t1.fcreatorId from T_BAS_MULTIAPPROVE t1  \n");
/* 262*/        builder.appendSql("inner join (select fbillid,max(fcreatetime) as fcreatetime from T_BAS_MULTIAPPROVE  where ");
/* 263*/        builder.appendParam("fbillid", billIds.toArray());
/* 264*/        builder.appendSql(" group by fbillid) t2 on t1.fbillid=t2.fbillid and t1.fcreatetime=t2.fcreatetime ");
/* 265*/        builder.appendSql("))t  \n");
/* 266*/        builder.appendSql(" inner join t_pm_user creator on creator.fid=t.fcreatorid");
/* 267*/        IRowSet rowSet = builder.executeQuery();


/* 270*/        try
                {
/* <-MISALIGNED-> */ /* 270*/            while(rowSet.next()) 
                    {
/* <-MISALIGNED-> */ /* 270*/                MultiApproveInfo info = new MultiApproveInfo();
/* <-MISALIGNED-> */ /* 271*/                info.setCreateTime(rowSet.getTimestamp("fcreatetime"));
/* <-MISALIGNED-> */ /* 272*/                UserInfo creator = new UserInfo();
/* <-MISALIGNED-> */ /* 274*/                creator.setName(rowSet.getString("creatorname"));
/* <-MISALIGNED-> */ /* 275*/                info.setCreator(creator);
/* <-MISALIGNED-> */ /* 276*/                result.put(rowSet.getString("fbillid"), info);
                    }
                }
/* <-MISALIGNED-> */ /* 278*/        catch(SQLException e)
                {
/* <-MISALIGNED-> */ /* 279*/            throw new BOSException(e);
                }





















/* 307*/        return result;
            }
            private static Logger logger = Logger.getLogger("com.kingdee.eas.fdc.basedata.app.FDCBillWFFacadeControllerBean");
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_basedata_server.jar
	Total time: 42 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/