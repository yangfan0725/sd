/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.tenancy.XHCustomerFactory;
import com.kingdee.eas.fdc.tenancy.XHCustomerInfo;
import com.kingdee.eas.fdc.tenancy.XHRoomFactory;
import com.kingdee.eas.fdc.tenancy.XHRoomInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class XHRoomEditUI extends AbstractXHRoomEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(XHRoomEditUI.class);
    
    /**
     * output class constructor
     */
    public XHRoomEditUI() throws Exception
    {
        super();
    }

    public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic= super.getSelectors();
    	sic.add("isEnabled");
    	sic.add("sellProject.*");
    	return sic;
	}
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}
	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public void loadFields() {
		super.loadFields();
	}
	protected IObjectValue createNewData() {
		XHRoomInfo info=new XHRoomInfo();
		info.setIsEnabled(true);
		info.setSellProject((SellProjectInfo) getUIContext().get(
		"sellProject"));
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return XHRoomFactory.getRemoteInstance();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtArea);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbIsSale);
		super.verifyInput(e);
	}
	public void onLoad() throws Exception {
		super.onLoad();
		
		this.actionCopy.setVisible(true);
		this.txtNumber.setRequired(true);
		this.txtName.setRequired(true);
		this.txtArea.setRequired(true);
		this.cbIsSale.setRequired(true);
	}

}