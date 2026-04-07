/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.ProductTypePropertyEnum;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListFactory;
import com.kingdee.eas.fdc.sellhouse.SHEAttachListInfo;
import com.kingdee.eas.fdc.sellhouse.client.SHEAttachListEditUI;
import com.kingdee.eas.fdc.tenancy.XHCustomerFactory;
import com.kingdee.eas.fdc.tenancy.XHCustomerInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.KDTreeNode;

/**
 * output class name
 */
public class XHCustomerListUI extends AbstractXHCustomerListUI
{
    private static final Logger logger = CoreUIObject.getLogger(XHCustomerListUI.class);
    
    /**
     * output class constructor
     */
    public XHCustomerListUI() throws Exception
    {
        super();
    }

    protected FDCDataBaseInfo getBaseDataInfo() {
		return new XHCustomerInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return XHCustomerFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return XHCustomerEditUI.class.getName();
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
	}
	public void onLoad() throws Exception {
		super.onLoad();
	}
	public void onShow()throws Exception{
		super.onShow();
		this.actionCancel.setVisible(false);
		this.actionCancelCancel.setVisible(false);
		this.btnCancel.setVisible(false);
		this.btnCancelCancel.setVisible(false);
	}
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		return super.getQueryExecutor(queryPK, viewInfo);
	}

    public boolean outPutWarningSentanceAndVerifyCancelorCancelCancelByID(String words, String selectID)
    throws Exception
{
    	return false;
}
    
}