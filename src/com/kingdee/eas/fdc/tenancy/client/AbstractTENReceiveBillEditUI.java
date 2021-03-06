/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractTENReceiveBillEditUI extends com.kingdee.eas.fdc.sellhouse.client.ReceiveBillEidtUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTENReceiveBillEditUI.class);
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractTENReceiveBillEditUI() throws Exception
    {
        super();
        this.defaultObjectName = "editData";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractTENReceiveBillEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        // CoreUI		
        this.kDTable1.setFormatXml(resHelper.getString("kDTable1.formatXml"));
        this.kDTable1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

        


        

		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 960, 500));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 960, 500));
        contPurchase.setBounds(new Rectangle(344, 79, 270, 19));
        this.add(contPurchase, new KDLayout.Constraints(344, 79, 270, 19, 0));
        contReceiveAmount.setBounds(new Rectangle(12, 108, 270, 19));
        this.add(contReceiveAmount, new KDLayout.Constraints(12, 108, 270, 19, 0));
        contCurrency.setBounds(new Rectangle(344, 109, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(344, 109, 270, 19, 0));
        contNumber.setBounds(new Rectangle(12, 12, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(12, 12, 270, 19, 0));
        contCustomer.setBounds(new Rectangle(675, 79, 270, 19));
        this.add(contCustomer, new KDLayout.Constraints(675, 79, 270, 19, 0));
        contAuditor.setBounds(new Rectangle(343, 204, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(343, 204, 270, 19, 0));
        contAuditTime.setBounds(new Rectangle(12, 204, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(12, 204, 270, 19, 0));
        contCreateTime.setBounds(new Rectangle(11, 237, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(11, 237, 270, 19, 0));
        contBillDate.setBounds(new Rectangle(344, 12, 270, 19));
        this.add(contBillDate, new KDLayout.Constraints(344, 12, 270, 19, 0));
        contCreator.setBounds(new Rectangle(342, 237, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(342, 237, 270, 19, 0));
        contSellProject.setBounds(new Rectangle(12, 44, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(12, 44, 270, 19, 0));
        contSellOrder.setBounds(new Rectangle(675, 45, 270, 19));
        this.add(contSellOrder, new KDLayout.Constraints(675, 45, 270, 19, 0));
        contSalesman.setBounds(new Rectangle(675, 204, 270, 19));
        this.add(contSalesman, new KDLayout.Constraints(675, 204, 270, 19, 0));
        contDes.setBounds(new Rectangle(11, 172, 933, 19));
        this.add(contDes, new KDLayout.Constraints(11, 172, 933, 19, 0));
        contCheque.setBounds(new Rectangle(12, 140, 270, 19));
        this.add(contCheque, new KDLayout.Constraints(12, 140, 270, 19, 0));
        contReceiptNumber.setBounds(new Rectangle(12, 140, 270, 19));
        this.add(contReceiptNumber, new KDLayout.Constraints(12, 140, 270, 19, 0));
        btnChoose.setBounds(new Rectangle(781, 236, 70, 19));
        this.add(btnChoose, new KDLayout.Constraints(781, 236, 70, 19, 0));
        kDLabelContainer1.setBounds(new Rectangle(675, 12, 270, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(675, 12, 270, 19, 0));
        lcTenancyContract.setBounds(new Rectangle(344, 79, 270, 19));
        this.add(lcTenancyContract, new KDLayout.Constraints(344, 79, 270, 19, 0));
        lcRoom.setBounds(new Rectangle(11, 75, 270, 19));
        this.add(lcRoom, new KDLayout.Constraints(11, 75, 270, 19, 0));
        kDLabelContainer2.setBounds(new Rectangle(675, 109, 270, 19));
        this.add(kDLabelContainer2, new KDLayout.Constraints(675, 109, 270, 19, 0));
        lcAccessorialResource.setBounds(new Rectangle(11, 75, 270, 19));
        this.add(lcAccessorialResource, new KDLayout.Constraints(11, 75, 270, 19, 0));
        cbIsSettlement.setBounds(new Rectangle(677, 238, 92, 19));
        this.add(cbIsSettlement, new KDLayout.Constraints(677, 238, 92, 19, 0));
        btnDelete.setBounds(new Rectangle(877, 237, 70, 19));
        this.add(btnDelete, new KDLayout.Constraints(877, 237, 70, 19, 0));
        kDLabelContainer3.setBounds(new Rectangle(343, 141, 270, 19));
        this.add(kDLabelContainer3, new KDLayout.Constraints(343, 141, 270, 19, 0));
        kDLabelContainer4.setBounds(new Rectangle(675, 139, 270, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(675, 139, 270, 19, 0));
        contGathering.setBounds(new Rectangle(347, 45, 266, 19));
        this.add(contGathering, new KDLayout.Constraints(347, 45, 266, 19, 0));
        contSinPurchase.setBounds(new Rectangle(344, 79, 268, 19));
        this.add(contSinPurchase, new KDLayout.Constraints(344, 79, 268, 19, 0));
        contSinObligate.setBounds(new Rectangle(344, 79, 270, 19));
        this.add(contSinObligate, new KDLayout.Constraints(344, 79, 270, 19, 0));
        kDTabbedPane1.setBounds(new Rectangle(10, 265, 940, 216));
        this.add(kDTabbedPane1, new KDLayout.Constraints(10, 265, 940, 216, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contPurchase
        contPurchase.setBoundEditor(f7Purchase);
        //contReceiveAmount
        contReceiveAmount.setBoundEditor(txtReceiveAmount);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contCustomer
        contCustomer.setBoundEditor(f7Customer);
        //contAuditor
        contAuditor.setBoundEditor(f7Auditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contBillDate
        contBillDate.setBoundEditor(pkBillDate);
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contSellProject
        contSellProject.setBoundEditor(f7SellProject);
        //contSellOrder
        contSellOrder.setBoundEditor(f7SellOrder);
        //contSalesman
        contSalesman.setBoundEditor(f7Salesman);
        //contDes
        contDes.setBoundEditor(txtDes);
        //contCheque
        contCheque.setBoundEditor(f7Cheque);
        //contReceiptNumber
        contReceiptNumber.setBoundEditor(txtReceiptNumber);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(comboBelongSys);
        //lcTenancyContract
        lcTenancyContract.setBoundEditor(f7TenancyContract);
        //lcRoom
        lcRoom.setBoundEditor(f7Room);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(ComboGatheringOjbect);
        //lcAccessorialResource
        lcAccessorialResource.setBoundEditor(f7Accessorial);
        //kDLabelContainer3
        kDLabelContainer3.setBoundEditor(txtInvoice);
        //kDLabelContainer4
        kDLabelContainer4.setBoundEditor(txtInvoiceAmount);
        //contGathering
        contGathering.setBoundEditor(comboGathering);
        //contSinPurchase
        contSinPurchase.setBoundEditor(f7SinPurchase);
        //contSinObligate
        contSinObligate.setBoundEditor(f7SinObligate);
        //kDTabbedPane1
        kDTabbedPane1.add(kDPanel1, resHelper.getString("kDPanel1.constraints"));
        kDTabbedPane1.add(kDPanel2, resHelper.getString("kDPanel2.constraints"));
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(kDTable1, BorderLayout.CENTER);
        //kDPanel2
kDPanel2.setLayout(new BorderLayout(0, 0));        kDPanel2.add(tblRecord, BorderLayout.CENTER);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTable1);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuWorkflow);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuSubmitOption);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator6);
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator3);
        menuFile.add(menuItemExitCurrent);
        //menuSubmitOption
        menuSubmitOption.add(chkMenuItemSubmitAndAddNew);
        menuSubmitOption.add(chkMenuItemSubmitAndPrint);
        //menuEdit
        menuEdit.add(menuItemCopy);
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator4);
        menuEdit.add(menuItemReset);
        menuEdit.add(separator1);
        menuEdit.add(menuItemCreateFrom);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyFrom);
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemEnterToNextRow);
        menuEdit.add(separator2);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemFirst);
        menuView.add(menuItemPre);
        menuView.add(menuItemNext);
        menuView.add(menuItemLast);
        menuView.add(separator3);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator7);
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        //menuTable1
        menuTable1.add(menuItemAddLine);
        menuTable1.add(menuItemCopyLine);
        menuTable1.add(menuItemInsertLine);
        menuTable1.add(menuItemRemoveLine);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemCalculator);
        //menuWorkflow
        menuWorkflow.add(menuItemStartWorkFlow);
        menuWorkflow.add(separatorWF1);
        menuWorkflow.add(menuItemViewSubmitProccess);
        menuWorkflow.add(menuItemViewDoProccess);
        menuWorkflow.add(MenuItemWFG);
        menuWorkflow.add(menuItemWorkFlowList);
        menuWorkflow.add(separatorWF2);
        menuWorkflow.add(menuItemMultiapprove);
        menuWorkflow.add(menuItemNextPerson);
        menuWorkflow.add(menuItemAuditResult);
        menuWorkflow.add(kDSeparator5);
        menuWorkflow.add(kDMenuItemSendMessage);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnRec);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.TENReceiveBillEditUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
        this.editData = (com.kingdee.eas.framework.CoreBillBaseInfo)ov;
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????????
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
        if (STATUS_ADDNEW.equals(this.oprtState)) {
        } else if (STATUS_EDIT.equals(this.oprtState)) {
        } else if (STATUS_VIEW.equals(this.oprtState)) {
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
        }
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "TENReceiveBillEditUI");
    }




}