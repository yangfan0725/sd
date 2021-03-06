/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.market.MediaFactory;
import com.kingdee.eas.fdc.market.MediaInfo;
import com.kingdee.eas.fdc.market.PlanTypeFactory;
import com.kingdee.eas.fdc.market.PlanTypeInfo;
import com.kingdee.eas.fdc.sellhouse.RenameRoomReasonFactory;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class PlanTypeListUI extends AbstractPlanTypeListUI {
	public void onLoad() throws Exception {
		super.onLoad();
		this.actionCancel.setVisible(true);
		this.actionCancel.setEnabled(true);
		this.actionCancelCancel.setVisible(true);
		this.actionCancelCancel.setEnabled(true);
	}

	private static final Logger logger = CoreUIObject.getLogger(PlanTypeListUI.class);

	/**
	 * output class constructor
	 */
	public PlanTypeListUI() throws Exception {
		super();
	}

	protected String getEditUIModal() {
		return UIFactoryName.MODEL;
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) throws Exception {
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output treeMain_valueChanged method
	 */
	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception {
		super.treeMain_valueChanged(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionProcductVal_actionPerformed
	 */
	public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception {
		super.actionProcductVal_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		//by tim_gao 2010-11-2
    	String selectID = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"id").getValue().toString();
    	String warnings= "????";
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID(warnings,selectID)){//????????????????
    		this.abort();
    	}
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		//by tim_gao 2010-11-2
    	String selectID = this.tblMain.getCell(this.tblMain.getSelectManager().getActiveRowIndex(),"id").getValue().toString();
    	String warnings= "????";
    	if(outPutWarningSentanceAndVerifyCancelorCancelCancelByID(warnings,selectID)){//????????????????
    		this.abort();
    	}
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		super.actionQuery_actionPerformed(e);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = this.getSelectedKeyValue();
		PlanTypeInfo info = PlanTypeFactory.getRemoteInstance().getPlanTypeInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if (!info.isIsEnabled()) {
			MsgBox.showInfo("??????????");
			return;
		}
		String cancelMsg = EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_Cancel");
		if (confirmDialog(cancelMsg)) {

			
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("isEnabled");
				info.setIsEnabled(false);
				PlanTypeFactory.getRemoteInstance().updatePartial(info, sel);
			
		}
		cancel();
		this.tblMain.removeRows();
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		String id = this.getSelectedKeyValue();
		PlanTypeInfo info = PlanTypeFactory.getRemoteInstance().getPlanTypeInfo(new ObjectUuidPK(BOSUuid.read(id)));
		if (info.isIsEnabled()) {
			MsgBox.showInfo("??????????");
			return;
		}
		String cancelMsg = EASResource.getString(FrameWorkClientUtils.strResource + "Confirm_CancelCancel");
		if (confirmDialog(cancelMsg)) {
			
				SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("isEnabled");
				info.setIsEnabled(true);
				PlanTypeFactory.getRemoteInstance().updatePartial(info, sel);
			
		}
		cancelCancel();
		this.tblMain.removeRows();
		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * output actionQueryScheme_actionPerformed
	 */
	public void actionQueryScheme_actionPerformed(ActionEvent e) throws Exception {
		super.actionQueryScheme_actionPerformed(e);
	}

	/**
	 * output actionMoveTree_actionPerformed
	 */
	public void actionMoveTree_actionPerformed(ActionEvent e) throws Exception {
		super.actionMoveTree_actionPerformed(e);
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception {
		return com.kingdee.eas.fdc.market.PlanTypeFactory.getRemoteInstance();
	}

	/**
	 * output getTreeInterface method
	 */
	protected ITreeBase getTreeInterface() throws Exception {
		return com.kingdee.eas.fdc.market.PlanTypeFactory.getRemoteInstance();
	}

	/**
	 * output getLongNumberFieldName method
	 */
	protected String getLongNumberFieldName() {
		return "longNumber";
	}

	/**
	 * output getRootName method
	 */
	protected String getRootName() {
		return "????????";
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.market.PlanTypeInfo objectValue = new com.kingdee.eas.fdc.market.PlanTypeInfo();

		return objectValue;
	}
	 /**
     * @author tim_gao
	 * @throws Exception 
     * @date 2010-11-2
     * @description ????ID??????????????????????
     */
    public boolean outPutWarningSentanceAndVerifyCancelorCancelCancelByID(String words,String selectID) throws Exception{
    	boolean flag=false;
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("id",selectID));
    	filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.valueOf(true)));//????????????
    	if(this.getBizInterface().exists(filter)){//????????????????
    		MsgBox.showWarning("????????????????????"+words+"!");
    		flag=true;
    	}
		return flag;
    }
}