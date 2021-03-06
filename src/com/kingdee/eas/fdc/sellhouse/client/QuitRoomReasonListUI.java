/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomInfo;
import com.kingdee.eas.fdc.sellhouse.QuitRoomReasonFactory;
import com.kingdee.eas.fdc.sellhouse.QuitRoomReasonInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class QuitRoomReasonListUI extends AbstractQuitRoomReasonListUI
{
    private static final Logger logger = CoreUIObject.getLogger(QuitRoomReasonListUI.class);
    
    /**
     * output class constructor
     */
    public QuitRoomReasonListUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    /**
     * output tblMain_tableClicked method
     */
    protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        super.tblMain_tableClicked(e);
    }

    /**
     * output menuItemImportData_actionPerformed method
     */
    protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
        super.menuItemImportData_actionPerformed(e);
    }

	protected FDCDataBaseInfo getBaseDataInfo() {
		return new QuitRoomReasonInfo();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return QuitRoomReasonFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return QuitRoomReasonEditUI.class.getName();
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		String idStr = this.getSelectedKeyValue();
		if(idStr == null || "".equals(idStr)){
			return;
		}
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("quitRoomReason.id",idStr));
		if(QuitRoomFactory.getRemoteInstance().exists(filter)){
			MsgBox.showInfo("??????????????????,????????!");
			return;
		}
		super.actionRemove_actionPerformed(e);
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		
this.actionQuery.setVisible(false);
		
		this.menuBar.remove(menuBiz);
		this.actionPrint.setVisible(false);
		this.actionPrintPreview.setVisible(false);
		this.toolBar.remove(btnPrint);
		this.toolBar.remove(btnPrintPreview);		
		this.toolBar.remove(btnCancelCancel);
        this.toolBar.remove(btnCancel);
        
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if (!saleOrg.getId().toString().equals(OrgConstants.DEF_CU_ID))
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
	}
}