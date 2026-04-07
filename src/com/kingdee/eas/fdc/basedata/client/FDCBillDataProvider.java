package com.kingdee.eas.fdc.basedata.client;

import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.ctrl.kdf.data.datasource.DSParam;
import com.kingdee.bos.ctrl.kdf.data.impl.BOSQueryDelegate;
import com.kingdee.bos.ctrl.kdf.expr.Variant;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.eas.cp.pem.web.utils.DateTimeFormatter;
import com.kingdee.eas.fdc.basedata.FDCBillWFAuditUtil;
import com.kingdee.eas.fdc.basedata.FDCBillWFFacadeFactory;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.jdbc.rowset.impl.ColInfo;
import com.kingdee.jdbc.rowset.impl.DynamicRowSet;

public abstract class FDCBillDataProvider implements BOSQueryDelegate {

	public String billId = "";
	public List auditAllList = null;
	public IMetaDataPK mainQuery=null;
	public FDCBillDataProvider(String billId,IMetaDataPK mainQuery) {
		this.billId = billId;
		this.mainQuery = mainQuery;
	}
	public IRowSet execute(BOSQueryDataSource ds) {
		// TODO 自动生成方法存根
		Variant paramVal = null;
        ArrayList ps = ds.getParams();
        IRowSet iRowSet = null;
        if (ps.size() > 0)
        {
            DSParam param = (DSParam)ps.get(0); 
            paramVal = param.getValue();	
        }
        
        if ("MAINBILL".equals(ds.getID().toUpperCase()))//假设主数据源名称为mainbill
        {
            //返加主数据源的结果集
        	iRowSet = getMainBillRowSet(ds);
        }
        else if(ds.getID().toUpperCase().startsWith("AUDITINFO")) 
        {
            //返回参数值为paramVal的从数据源1的结果集
        	//iRowSet = super.execute(ds);
        	return getAuditInfoRowSet(ds);
        }
        else{
        	
        	return getOtherSubRowSet(ds);
        }
        return iRowSet;
	}
	/****
	 * 如果还有其他的子元数据请实现此函数
	 * 
	 * 如果元数据没有指定名称，没有设置mainbill等信息，且只有一个元数据
	 * 可以直接覆盖此函数，并返回getMainBillRowSet()
	 * 这样可以兼容一些旧的模板
	 */
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds){
		return null;
	}
	/****
	 * 获取多级审批信息的结果
	 * 可以在工作流中设置某一个节点的信息不打印
	 * 套打中审批信息的设置，元数据名称必须是auditInfo加一个数字
	 * 另外需设置参数与主单据（mainbill）关联
	 * 
	 * @param ds
	 * @return
	 */
	public IRowSet getAuditInfoRowSet(BOSQueryDataSource ds){
		DynamicRowSet drs = null;
		try {
    		
			String[] col = FDCBillWFAuditUtil.AuditInfoCols;
			
			drs = new DynamicRowSet(col.length);
			for(int i=0;i<col.length;i++){
				ColInfo ci = new ColInfo();
				ci.colType = Types.VARCHAR;
				ci.columnName = col[i];
				ci.nullable = 1;
				drs.setColInfo(i + 1, ci);
			}
			drs.beforeFirst();
			
    		String id = ds.getID();
    		int index = Integer.parseInt(id.substring("auditInfo".length(),id.length()));
    		if(auditAllList==null)
    			auditAllList = FDCBillWFFacadeFactory.getRemoteInstance().getWFAuditResultForPrint(billId);
    		
    		List auditList = new ArrayList();
			if(index-1 >= auditAllList.size()){
				return drs;
			} else if (index-1<0) {
				for(Iterator it = auditAllList.iterator();it.hasNext();){
					List list = (List)it.next();
					auditList.addAll(list);
				}
			} else {
				auditList = (List)auditAllList.get(index-1);
			}
			
			
			Map auditMap=new HashMap();
			IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
			 ProcessInstInfo processInstInfo = null;
			 ProcessInstInfo procInsts[] = service.getProcessInstanceByHoldedObjectId(billId);
			 int i = 0;
			 for(int n = procInsts.length; i < n; i++)
				 if(procInsts[i].getState().startsWith("open"))
					 processInstInfo = procInsts[i];
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
				 FDCSQLBuilder _builder = new FDCSQLBuilder();
				 _builder.appendSql(" /*dialect*/ select a.FACTDEFNAME_L2 as AuditNodeName,TO_TIMESTAMP(TO_CHAR(a.FCREATEDTIME,'YYYY-MM-DD HH24:MI'),'YYYY-MM-DD HH24:MI') as CreateTime,b.fpersonusername_l2 as PersonName from T_WFR_ActInst a left join T_WFR_AssignDetail b on a.FACTINSTID=b.FACTINSTID where a.FPROCINSTID ='"+processInstInfo.getProcInstId()+"' ");
				 _builder.appendSql(" and b.fbizFunction='MultiApproveUIFunction' and (CAST(b.FENDTIME  AS DATE) - CAST(b.FCREATEDTIME  AS DATE))<0.00002 and (CAST(a.FCOMLETETIME AS DATE) - CAST(a.FCREATEDTIME AS DATE))<=0.00002 order by a.FCREATEDTIME ");
				IRowSet rowSet = _builder.executeQuery();
				while(rowSet.next()){
					List list=new ArrayList();
					if(auditMap.get(rowSet.getString("CreateTime"))!=null){
						list=(List) auditMap.get(rowSet.getString("CreateTime"));
					}else{
						auditMap.put(rowSet.getString("CreateTime"), list);
					}
					Map map=new HashMap();
					map.put(FDCBillWFAuditUtil.PersonName, rowSet.getString("PersonName"));
					map.put(FDCBillWFAuditUtil.CreateTime, rowSet.getString("CreateTime"));
					map.put(FDCBillWFAuditUtil.AuditNodeName, rowSet.getString("AuditNodeName"));
					map.put(FDCBillWFAuditUtil.IsPass, "同意");
					map.put(FDCBillWFAuditUtil.Opinion, "自动审批");
					list.add(map);
				}
			 }
			 List newauditList = new ArrayList();
			 newauditList.addAll(auditList);
			 
			 for(int k=newauditList.size()-1;k>=0;k--){
				 Map auditInfo=(HashMap)newauditList.get(k);
				 String input=(String) auditInfo.get(FDCBillWFAuditUtil.CreateTime);
			     SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			     Date date = inputFormat.parse(input);
			     long timeInMillis = date.getTime();
			     long minutesOnly = (timeInMillis / (60 * 1000)) * (60 * 1000);
			     Date truncatedDate = new Date(minutesOnly);
			     String time = inputFormat.format(truncatedDate);
			     
			     if(auditMap.get(time)!=null){
					List list=(List) auditMap.get(time);
					for(int j=0;j<list.size();j++){
						Map map=(Map) list.get(j);
						auditList.add(k+j+1, map);
					}
					auditMap.remove(time);
				}
		 	}
			 int ind=0;
			for(Iterator it = auditList.iterator();it.hasNext();){
				Map auditInfo = (HashMap)it.next();
				drs.moveToInsertRow();
				Set keySet = auditInfo.keySet();
				for(Iterator keys = keySet.iterator();keys.hasNext();){
					String iKey = (String)keys.next();
					drs.updateString(iKey,(String)auditInfo.get(iKey));
				}
		        
				drs.updateString(FDCBillWFAuditUtil.ID,String.valueOf(++ind));
				drs.updateString(FDCBillWFAuditUtil.BillID,billId);
				drs.insertRow();
			}
			drs.beforeFirst();
			return drs;
			
    	} catch (Exception e) {
            //ExceptionHandler.handle((CoreUI) null,e);
    		e.printStackTrace();
        }
    	return drs;
	}
	/***
	 * 在这里实现主单据的行集插入
	 * 主单据在套打设置中，其主元数据的名称，必须是mainbill
	 * @param ds
	 * @return
	 */
	public IRowSet getMainBillRowSet(BOSQueryDataSource ds){
		IRowSet iRowSet = null;
        try {
        	IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(mainQuery);
            exec.option().isAutoTranslateEnum= true;
            EntityViewInfo ev = new EntityViewInfo();
            FilterInfo filter = new FilterInfo();
            filter.getFilterItems().add(new FilterItemInfo("id", billId, CompareType.EQUALS));
            ev.setFilter(filter);            
            exec.setObjectView(ev);
            System.out.println(exec.getSQL());
            iRowSet = exec.executeQuery();	
            iRowSet.beforeFirst();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
        
		return iRowSet;
	}

}
