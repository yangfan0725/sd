/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.*;
import java.math.BigDecimal;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.basedata.org.NewOrgUtils;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgViewType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMClientHelper;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.contract.MarketProjectFKReportFacadeFactory;
import com.kingdee.eas.fdc.contract.MarketProjectReportFacadeFactory;
import com.kingdee.eas.fdc.contract.MarketYearProjectEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.eas.framework.report.client.CommRptBaseConditionUI;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.framework.report.util.RptParams;
import com.kingdee.eas.framework.report.util.RptRowSet;
import com.kingdee.eas.framework.report.util.RptTableHeader;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;

/**
 * output class name
 */
public class MarketProjectFKReportUI extends AbstractMarketProjectFKReportUI
{
    private static final Logger logger = CoreUIObject.getLogger(MarketProjectFKReportUI.class);
    private boolean isQuery=false;
    private boolean isOnLoad=false;
    public MarketProjectFKReportUI() throws Exception
    {
        super();
        tblMain.checkParsed();
        tblMain.getDataRequestManager().addDataRequestListener(this);
        tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
        enableExportExcel(tblMain);
    }

    protected RptParams getParamsForInit() {
		return null;
	}

	protected CommRptBaseConditionUI getQueryDialogUserPanel() throws Exception {
		return new MarketProjectFKReportFilterUI();
	}

	protected ICommRptBase getRemoteInstance() throws BOSException {
		return MarketProjectFKReportFacadeFactory.getRemoteInstance();
	}

	protected KDTable getTableForPrintSetting() {
		return tblMain;
	}
	public void onLoad() throws Exception {
		isOnLoad=true;
    	setShowDialogOnLoad(true);
    	tblMain.getStyleAttributes().setLocked(true);
		super.onLoad();
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		
		TreeModel orgTreeModel = NewOrgUtils.getTreeModel(OrgViewType.COMPANY,"", SysContext.getSysContext().getCurrentFIUnit().getId().toString(), null, FDCHelper.getActionPK(this.actionOnLoad));
		this.treeMain.setModel(orgTreeModel);
		DefaultKingdeeTreeNode orgRoot = (DefaultKingdeeTreeNode) ((TreeModel) this.treeMain.getModel()).getRoot();
		this.treeMain.setSelectionNode(orgRoot);
		this.treeMain.expandAllNodes(true, orgRoot);
		isOnLoad=false;
    }
	
	protected void query() {
		if(isOnLoad) return;
		tblMain.removeColumns();
		tblMain.removeRows();
	}
//	protected void mergerTable(KDTable table,String coloum[],String mergeColoum[]){
//		int merger=0;
//		for(int i=0;i<table.getRowCount();i++){
//			if(i>0){
//				boolean isMerge=true;
//				for(int j=0;j<coloum.length;j++){
//					Object curRow=table.getRow(i).getCell(coloum[j]).getValue();
//					Object lastRow=table.getRow(i-1).getCell(coloum[j]).getValue();
//					if(getString(curRow).equals("")||getString(lastRow).equals("")||!getString(curRow).equals(getString(lastRow))){
//						isMerge=false;
//						merger=i;
//					}
//				}
//				if(isMerge){
//					for(int j=0;j<mergeColoum.length;j++){
//						table.getMergeManager().mergeBlock(merger, table.getColumnIndex(mergeColoum[j]), i, table.getColumnIndex(mergeColoum[j]));
//					}
//					table.getRow(i).getCell("tel").setValue(null);
//					table.getRow(i).getCell("visit").setValue(null);
//				}
//			}
//		}
//	}
//	private static String getString(Object value){
//		if(value==null) return "";
//		if(value!=null&&value.toString().trim().equals("")){
//			return "";
//		}else{
//			return value.toString();
//		}
//	}
	public void tableDataRequest(KDTDataRequestEvent kdtdatarequestevent) {
		if(isQuery) return;
		isQuery=true;
		Window win = SwingUtilities.getWindowAncestor(this);
        LongTimeDialog dialog = null;
        if(win instanceof Frame){
        	dialog = new LongTimeDialog((Frame)win);
        }else if(win instanceof Dialog){
        	dialog = new LongTimeDialog((Dialog)win);
        }
        if(dialog==null){
        	dialog = new LongTimeDialog(new Frame());
        }
        dialog.setLongTimeTask(new ILongTimeTask() {
            public Object exec()throws Exception{
            	RptParams resultRpt= getRemoteInstance().query(params);
            	return resultRpt;
            }
            public void afterExec(Object result)throws Exception{
            	tblMain.setRefresh(false);
            	
            	RptParams rpt = getRemoteInstance().createTempTable(params);
                RptTableHeader header = (RptTableHeader)rpt.getObject("header");
                KDTableUtil.setHeader(header, tblMain);
                
    	        RptRowSet rs = (RptRowSet)((RptParams)result).getObject("rowset");
    	        tblMain.setRowCount(rs.getRowCount());
    	        KDTableUtil.insertRows(rs, 0, tblMain);
    	        if(rs.getRowCount() > 0){
    	        	tblMain.reLayoutAndPaint();
    	        }else{
    	        	tblMain.repaint();
    	        }
    	        tblMain.setRefresh(true);
    	        
    	        CRMClientHelper.changeTableNumberFormat(tblMain, new String[]{"FYearLXAmount","FYearHTAmount","FYearAmount","FYearFSAmount","FMonthLJFSAmount","FMonthAmount","FMonthFSAmount","FNextMonthAmount","ZMonthAmount","ZPayAmount","ZYearPayAmount","ZUnPayAmount","ZNextMonthAmount"});
//    			CRMClientHelper.fmtDate(tblMain, "auditTime");
//    			CRMClientHelper.fmtDate(tblMain, "conAuditTime");
//    			CRMClientHelper.fmtDate(tblMain, "conBizDate");
//    			String[] fields=new String[tblMain.getColumnCount()];
//    			for(int i=0;i<tblMain.getColumnCount();i++){
//    				fields[i]=tblMain.getColumnKey(i);
//    			}
//    			KDTableHelper.setSortedColumn(tblMain,fields);
    			
//    			CRMClientHelper.getFootRow(tblMain, new String[]{"conAmount","payAmount","unPayAmount"});
//    			BigDecimal roomArea=tblMain.getFootRow(0).getCell("roomArea").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("roomArea").getValue();
//    			BigDecimal buildArea=tblMain.getFootRow(0).getCell("buildArea").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("buildArea").getValue();
//    			BigDecimal account=tblMain.getFootRow(0).getCell("account").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("account").getValue();
//    			BigDecimal revAccount=tblMain.getFootRow(0).getCell("revAccount").getValue()==null?FDCHelper.ZERO:(BigDecimal)tblMain.getFootRow(0).getCell("revAccount").getValue();
//    			tblMain.getFootRow(0).getCell("buildPrice").setValue(buildArea.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:account.divide(buildArea, 2, BigDecimal.ROUND_HALF_UP));
//    			tblMain.getFootRow(0).getCell("roomPrice").setValue(roomArea.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:account.divide(roomArea, 2, BigDecimal.ROUND_HALF_UP));
//    			tblMain.getFootRow(0).getCell("revAccountRate").setValue(account.compareTo(FDCHelper.ZERO)==0?FDCHelper.ZERO:revAccount.divide(account, 2, BigDecimal.ROUND_HALF_UP));
//    			
//    			tblMain.getColumn("amount").getStyleAttributes().setFontColor(Color.BLUE);
    			
    			
//    			this.tblMain.getMergeManager().setMergeMode(KDTMergeManager.GROUP_MERGE);
//    	    	this.tblMain.getGroupManager().setGroup(true);
//    	    	this.tblMain.getColumn("number").setGroup(true);
//    	    	this.tblMain.getColumn("auditTime").setGroup(true);
//    	    	this.tblMain.getColumn("name").setGroup(true);
//    	    	this.tblMain.getColumn("costAccount").setGroup(true);
//    	    	this.tblMain.getColumn("amount").setGroup(true);
//    	    	
//    	    	this.tblMain.getColumn("conAuditTime").setGroup(true);
//    	    	this.tblMain.getColumn("conNumber").setGroup(true);
//    	    	this.tblMain.getColumn("conName").setGroup(true);
//    	    	this.tblMain.getColumn("conPartB").setGroup(true);
//    	    	this.tblMain.getColumn("conAmount").setGroup(true);
//    	    	this.tblMain.getColumn("payAmount").setGroup(true);
//    	    	this.tblMain.getColumn("unPayAmount").setGroup(true);
//    	    	this.tblMain.getGroupManager().group();
    			
    			for(int i=tblMain.getRowCount()-1;i>=0;i--){
    				if(tblMain.getRow(i).getCell("isLeaf").getValue()!=null&&tblMain.getRow(i).getCell("isLeaf").getValue().toString().equals("1")){
    					continue;
    				}else{
    					tblMain.getRow(i).getStyleAttributes().setBackground(new java.awt.Color(246, 246, 191));
    				}
    				String totalNumber=tblMain.getRow(i).getCell("number").getValue().toString();
    				for(int k=4;k<tblMain.getColumnCount();k++){
    					BigDecimal amount=FDCHelper.ZERO;
    					for(int j=i+1;j<tblMain.getRowCount();j++){
    						String number=tblMain.getRow(j).getCell("number").getValue().toString();
    						if(number.substring(0, number.lastIndexOf(".")).equals(totalNumber)){
    							amount=FDCHelper.add(amount,tblMain.getRow(j).getCell(k).getValue());
    						}
    					}
//    					if(amount.compareTo(FDCHelper.ZERO)>0){
    						tblMain.getRow(i).getCell(k).setValue(amount);
//    					}
    				}
    			}
            }
        }
        );
        dialog.show();
        isQuery=false;
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode treeNode = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
		if(treeNode!=null){
			Object obj = treeNode.getUserObject();
			if(obj!=null){
				params.setObject("org", ((OrgStructureInfo)obj).getUnit().getId().toString());
			}else{
				params.setObject("org", null);
			}
			query();
		}
	}
}