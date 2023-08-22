package com.kingdee.eas.fdc.contract.client;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.kingdee.bos.ctrl.kdf.data.datasource.BOSQueryDataSource;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.dao.query.QueryExecutorFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.fdc.basedata.client.FDCBillDataProvider;
import com.kingdee.eas.fdc.contract.client.ContractBillEditDataProvider;
import com.kingdee.jdbc.rowset.IRowSet;

public class ContractBillReceiveEditDataProvider extends FDCBillDataProvider {
	public ContractBillReceiveEditDataProvider(String billId,
			IMetaDataPK mainQuery) {
		super(billId, mainQuery);
	}

	private static final Logger logger = Logger.getLogger(ContractBillReceiveEditDataProvider.class);
	
	public IRowSet getOtherSubRowSet(BOSQueryDataSource ds) {
		if (ds.getID().equalsIgnoreCase("ContractBillReceiveRateEntryPrintQuery")) {
			// 合同付款事项
			IRowSet iRowSet = null;
			try {
				IQueryExecutor exec = QueryExecutorFactory.getRemoteInstance(new MetaDataPK(
						"com.kingdee.eas.fdc.contract.app.ContractBillReceiveRateEntryPrintQuery"));
				exec.option().isAutoTranslateEnum = true;
				EntityViewInfo ev = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("parent.id", billId));
				ev.setFilter(filter);
				exec.setObjectView(ev);
				iRowSet = exec.executeQuery();
				iRowSet.beforeFirst();
			} catch (Exception e) {
				logger.debug(e.getMessage(), e);
				e.printStackTrace();
			}

			return iRowSet;
		}
		
		return getMainBillRowSet(ds);
	}
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
            iRowSet = exec.executeQuery();	
//            if(iRowSet.next()){
//            	BigDecimal roomArea=(BigDecimal)map.get("roomArea");
//                BigDecimal buildingArea=(BigDecimal)map.get("buildingArea");
//                iRowSet.updateBigDecimal("roomArea", roomArea);
//                iRowSet.updateBigDecimal("bulidingArea", buildingArea);
//                
//                String mainAddress=(String)map.get("mainAddress");
//                String mainPhone=(String)map.get("mainPhone");
//                String mainTel=(String)map.get("mainTel");
//                String mainPostalCode=(String)map.get("mainPostalCode");
//                iRowSet.updateString("mainAddress", mainAddress);
//                iRowSet.updateString("mainPhone", mainPhone);
//                iRowSet.updateString("mainTel", mainTel);
//                iRowSet.updateString("mainPostalCode", mainPostalCode);
//			}
            iRowSet.beforeFirst();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return iRowSet;
	}
}
