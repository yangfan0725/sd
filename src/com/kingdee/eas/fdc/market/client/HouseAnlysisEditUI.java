/**
 * output package name
 */
package com.kingdee.eas.fdc.market.client;

import java.awt.event.*;
import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class HouseAnlysisEditUI extends AbstractHouseAnlysisEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(HouseAnlysisEditUI.class);
    
    /**
     * output class constructor
     */
    public HouseAnlysisEditUI() throws Exception
    {
        super();
    }
    /**
     * output loadFields method
     */
    public void loadFields()
    {
        super.loadFields();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output actionPageSetup_actionPerformed
     */
    public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPageSetup_actionPerformed(e);
    }

    /**
     * output actionExitCurrent_actionPerformed
     */
    public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExitCurrent_actionPerformed(e);
    }

    /**
     * output actionHelp_actionPerformed
     */
    public void actionHelp_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionHelp_actionPerformed(e);
    }

    /**
     * output actionAbout_actionPerformed
     */
    public void actionAbout_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAbout_actionPerformed(e);
    }

    /**
     * output actionOnLoad_actionPerformed
     */
    public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionOnLoad_actionPerformed(e);
    }

    /**
     * output actionSendMessage_actionPerformed
     */
    public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSendMessage_actionPerformed(e);
    }

    /**
     * output actionCalculator_actionPerformed
     */
    public void actionCalculator_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCalculator_actionPerformed(e);
    }

    /**
     * output actionExport_actionPerformed
     */
    public void actionExport_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExport_actionPerformed(e);
    }

    /**
     * output actionExportSelected_actionPerformed
     */
    public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionExportSelected_actionPerformed(e);
    }

    /**
     * output actionRegProduct_actionPerformed
     */
    public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionRegProduct_actionPerformed(e);
    }

    /**
     * output actionPersonalSite_actionPerformed
     */
    public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPersonalSite_actionPerformed(e);
    }

    /**
     * output actionProcductVal_actionPerformed
     */
    public void actionProcductVal_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionProcductVal_actionPerformed(e);
    }

    /**
     * output actionSave_actionPerformed
     */
    public void actionSave_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSave_actionPerformed(e);
    }

    /**
     * output actionSubmit_actionPerformed
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
    	String simpleName = this.txtSimpleName.getText();
    	if(simpleName==null || "".equals(simpleName)){
    		FDCMsgBox.showError("??????????????");
    		abort();
    	}
        super.actionSubmit_actionPerformed(e);
    }

    /**
     * output actionCancel_actionPerformed
     */
    public void actionCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionCancel_actionPerformed(e);
    }

    /**
     * output actionCancelCancel_actionPerformed
     */
    public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionCancelCancel_actionPerformed(e);
    }

    /**
     * output actionFirst_actionPerformed
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }

    /**
     * output actionPre_actionPerformed
     */
    public void actionPre_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPre_actionPerformed(e);
    }

    /**
     * output actionNext_actionPerformed
     */
    public void actionNext_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionNext_actionPerformed(e);
    }

    /**
     * output actionLast_actionPerformed
     */
    public void actionLast_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionLast_actionPerformed(e);
    }

    /**
     * output actionPrint_actionPerformed
     */
    public void actionPrint_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrint_actionPerformed(e);
    }

    /**
     * output actionPrintPreview_actionPerformed
     */
    public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionPrintPreview_actionPerformed(e);
    }

    /**
     * output actionCopy_actionPerformed
     */
    public void actionCopy_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionCopy_actionPerformed(e);
    }

    /**
     * output actionAddNew_actionPerformed
     */
    public void actionAddNew_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionAddNew_actionPerformed(e);
    }

    /**
     * output actionEdit_actionPerformed
     */
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionEdit_actionPerformed(e);
    }

    /**
     * output actionRemove_actionPerformed
     */
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception
    {
    	setAddCtrl();
        super.actionRemove_actionPerformed(e);
    }

    /**
     * output actionAttachment_actionPerformed
     */
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionAttachment_actionPerformed(e);
    }

    /**
     * output actionSubmitOption_actionPerformed
     */
    public void actionSubmitOption_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmitOption_actionPerformed(e);
    }

    /**
     * output actionReset_actionPerformed
     */
    public void actionReset_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionReset_actionPerformed(e);
    }

    /**
     * output actionMsgFormat_actionPerformed
     */
    public void actionMsgFormat_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionMsgFormat_actionPerformed(e);
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.market.HouseAnlysisFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected com.kingdee.bos.dao.IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.market.HouseAnlysisInfo objectValue = new com.kingdee.eas.fdc.market.HouseAnlysisInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));
		
        return objectValue;
    }
	protected FDCDataBaseInfo getEditData() {
		// TODO Auto-generated method stub
		return this.editData;
	}
	protected KDBizMultiLangBox getNameCtrl() {
		// TODO Auto-generated method stub
		return this.txtName;
	}
	protected KDTextField getNumberCtrl() {
		// TODO Auto-generated method stub
		return this.txtNumber;
	}
	 /**
	   * ????????????????????????????
	   */
	  private void setAddCtrl() {
	    	String ctrlOrg=com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentAdminUnit().getId().toString();
	    	String cuid=OrgConstants.DEF_CU_ID;
	    	if(ctrlOrg.compareTo(cuid)!=0) {
	    		MsgBox.showError("????????????????????????????");
	    		SysUtil.abort();
	    	}
	    }
	  public void onLoad() throws Exception {
		  super.onLoad();
		  this.txtName.setMaxLength(255);
		  this.txtRemark.setMaxLength(100);
	  }
}