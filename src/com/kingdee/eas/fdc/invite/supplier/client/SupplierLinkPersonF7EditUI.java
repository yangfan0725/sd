/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.event.*;

import org.apache.log4j.Logger;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Factory;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Info;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.framework.*;

/**
 * output class name
 */
public class SupplierLinkPersonF7EditUI extends AbstractSupplierLinkPersonF7EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(SupplierLinkPersonF7EditUI.class);
    
    public SupplierLinkPersonF7EditUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}
	protected KDBizMultiLangBox getNameCtrl() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return null;
	}
	protected IObjectValue createNewData() {
		SupplierLinkPersonF7Info info=new SupplierLinkPersonF7Info();
		info.setIsEnabled(true);
		info.setSupplierFileType((SupplierFileTypeInfo)this.getUIContext().get("supplierFileType"));
		return info;
	}
	protected ICoreBase getBizInterface() throws Exception {
		return SupplierLinkPersonF7Factory.getRemoteInstance();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtNumber);
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtName);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplierFileType);
//		super.verifyInput(e);
	}
	public void onLoad() throws Exception {
		super.onLoad();
//		this.txtDescription.setMaxLength(255);
//		this.txtNumber.setRequired(true);
//		this.txtName.setRequired(true);
		this.prmtSupplierFileType.setRequired(true);
	}
}