/**
 * output package name
 */
package com.kingdee.eas.fdc.basedata.client;

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
public abstract class AbstractContractTypeEditUI extends com.kingdee.eas.framework.client.TreeEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractContractTypeEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLongNumber;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsEnabled;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea bizDescription;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox chkIsCost;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStampTaxRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractWFTypeEntry;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInviteType;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsAccountView;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbSinglePayment;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsTA;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsMarket;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsWebPC;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsReceive;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsRelateReceive;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsFund;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsTrip;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsExpense;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsPurchase;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsHide;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtHideDay;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel2;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel3;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsPurchaseApply;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox bizName;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtLongNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox bizDutyOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField kdftxtPayScale;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtStampTaxRate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtEntry;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbOrgType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractWFTypeEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtInviteType;
    protected com.kingdee.eas.fdc.basedata.ContractTypeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractContractTypeEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractContractTypeEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionFirst
        String _tempStr = null;
        actionFirst.setEnabled(true);
        actionFirst.setDaemonRun(false);

        actionFirst.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("shift ctrl <"));
        _tempStr = resHelper.getString("ActionFirst.SHORT_DESCRIPTION");
        actionFirst.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFirst.LONG_DESCRIPTION");
        actionFirst.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionFirst.NAME");
        actionFirst.putValue(ItemAction.NAME, _tempStr);
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionFirst.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contLongNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.chkIsEnabled = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.bizDescription = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangArea();
        this.chkIsCost = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStampTaxRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractWFTypeEntry = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInviteType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsAccountView = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbSinglePayment = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsTA = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsMarket = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsWebPC = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsReceive = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsRelateReceive = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsFund = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsTrip = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsExpense = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsPurchase = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.cbIsHide = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtHideDay = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDLabel2 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabel3 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cbIsPurchaseApply = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.bizName = new com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox();
        this.txtLongNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.bizDutyOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kdftxtPayScale = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtStampTaxRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.prmtEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.cbOrgType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.prmtContractWFTypeEntry = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtInviteType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contName.setName("contName");
        this.contLongNumber.setName("contLongNumber");
        this.chkIsEnabled.setName("chkIsEnabled");
        this.kDLabel1.setName("kDLabel1");
        this.bizDescription.setName("bizDescription");
        this.chkIsCost.setName("chkIsCost");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.contStampTaxRate.setName("contStampTaxRate");
        this.contEntry.setName("contEntry");
        this.contOrgType.setName("contOrgType");
        this.contContractWFTypeEntry.setName("contContractWFTypeEntry");
        this.contInviteType.setName("contInviteType");
        this.cbIsAccountView.setName("cbIsAccountView");
        this.cbSinglePayment.setName("cbSinglePayment");
        this.cbIsTA.setName("cbIsTA");
        this.cbIsMarket.setName("cbIsMarket");
        this.cbIsWebPC.setName("cbIsWebPC");
        this.cbIsReceive.setName("cbIsReceive");
        this.cbIsRelateReceive.setName("cbIsRelateReceive");
        this.cbIsFund.setName("cbIsFund");
        this.cbIsTrip.setName("cbIsTrip");
        this.cbIsExpense.setName("cbIsExpense");
        this.cbIsPurchase.setName("cbIsPurchase");
        this.cbIsHide.setName("cbIsHide");
        this.txtHideDay.setName("txtHideDay");
        this.kDLabel2.setName("kDLabel2");
        this.kDLabel3.setName("kDLabel3");
        this.cbIsPurchaseApply.setName("cbIsPurchaseApply");
        this.bizName.setName("bizName");
        this.txtLongNumber.setName("txtLongNumber");
        this.bizDutyOrgUnit.setName("bizDutyOrgUnit");
        this.kdftxtPayScale.setName("kdftxtPayScale");
        this.txtStampTaxRate.setName("txtStampTaxRate");
        this.prmtEntry.setName("prmtEntry");
        this.cbOrgType.setName("cbOrgType");
        this.prmtContractWFTypeEntry.setName("prmtContractWFTypeEntry");
        this.prmtInviteType.setName("prmtInviteType");
        // CoreUI		
        this.btnSave.setVisible(false);		
        this.btnCopy.setVisible(false);		
        this.btnFirst.setVisible(false);		
        this.btnPre.setVisible(false);		
        this.btnNext.setVisible(false);		
        this.btnLast.setVisible(false);		
        this.btnPrint.setVisible(false);		
        this.btnPrintPreview.setVisible(false);		
        this.menuItemCopy.setVisible(false);		
        this.menuView.setVisible(false);		
        this.btnAttachment.setVisible(false);		
        this.menuSubmitOption.setVisible(false);		
        this.menuBiz.setVisible(false);		
        this.separatorFW1.setVisible(false);		
        this.separatorFW2.setVisible(false);		
        this.separatorFW3.setVisible(false);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);
        // contLongNumber		
        this.contLongNumber.setBoundLabelText(resHelper.getString("contLongNumber.boundLabelText"));		
        this.contLongNumber.setBoundLabelLength(100);		
        this.contLongNumber.setBoundLabelUnderline(true);
        // chkIsEnabled		
        this.chkIsEnabled.setText(resHelper.getString("chkIsEnabled.text"));		
        this.chkIsEnabled.setVisible(false);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // bizDescription		
        this.bizDescription.setMaxLength(200);
        // chkIsCost		
        this.chkIsCost.setText(resHelper.getString("chkIsCost.text"));		
        this.chkIsCost.setToolTipText(resHelper.getString("chkIsCost.toolTipText"));
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // contStampTaxRate		
        this.contStampTaxRate.setBoundLabelText(resHelper.getString("contStampTaxRate.boundLabelText"));		
        this.contStampTaxRate.setBoundLabelLength(100);		
        this.contStampTaxRate.setBoundLabelUnderline(true);
        // contEntry		
        this.contEntry.setBoundLabelText(resHelper.getString("contEntry.boundLabelText"));		
        this.contEntry.setBoundLabelLength(100);		
        this.contEntry.setBoundLabelUnderline(true);
        // contOrgType		
        this.contOrgType.setBoundLabelText(resHelper.getString("contOrgType.boundLabelText"));		
        this.contOrgType.setBoundLabelUnderline(true);		
        this.contOrgType.setBoundLabelLength(100);
        // contContractWFTypeEntry		
        this.contContractWFTypeEntry.setBoundLabelText(resHelper.getString("contContractWFTypeEntry.boundLabelText"));		
        this.contContractWFTypeEntry.setBoundLabelLength(100);		
        this.contContractWFTypeEntry.setBoundLabelUnderline(true);
        // contInviteType		
        this.contInviteType.setBoundLabelText(resHelper.getString("contInviteType.boundLabelText"));		
        this.contInviteType.setBoundLabelLength(100);		
        this.contInviteType.setBoundLabelUnderline(true);
        // cbIsAccountView		
        this.cbIsAccountView.setText(resHelper.getString("cbIsAccountView.text"));		
        this.cbIsAccountView.setToolTipText(resHelper.getString("cbIsAccountView.toolTipText"));
        // cbSinglePayment		
        this.cbSinglePayment.setText(resHelper.getString("cbSinglePayment.text"));
        // cbIsTA		
        this.cbIsTA.setText(resHelper.getString("cbIsTA.text"));
        // cbIsMarket		
        this.cbIsMarket.setText(resHelper.getString("cbIsMarket.text"));
        // cbIsWebPC		
        this.cbIsWebPC.setText(resHelper.getString("cbIsWebPC.text"));
        // cbIsReceive		
        this.cbIsReceive.setText(resHelper.getString("cbIsReceive.text"));
        // cbIsRelateReceive		
        this.cbIsRelateReceive.setText(resHelper.getString("cbIsRelateReceive.text"));
        // cbIsFund		
        this.cbIsFund.setText(resHelper.getString("cbIsFund.text"));
        // cbIsTrip		
        this.cbIsTrip.setText(resHelper.getString("cbIsTrip.text"));
        // cbIsExpense		
        this.cbIsExpense.setText(resHelper.getString("cbIsExpense.text"));
        // cbIsPurchase		
        this.cbIsPurchase.setText(resHelper.getString("cbIsPurchase.text"));
        // cbIsHide		
        this.cbIsHide.setText(resHelper.getString("cbIsHide.text"));
        // txtHideDay
        // kDLabel2		
        this.kDLabel2.setText(resHelper.getString("kDLabel2.text"));
        // kDLabel3		
        this.kDLabel3.setText(resHelper.getString("kDLabel3.text"));
        // cbIsPurchaseApply		
        this.cbIsPurchaseApply.setText(resHelper.getString("cbIsPurchaseApply.text"));
        // bizName		
        this.bizName.setRequired(true);		
        this.bizName.setMaxLength(80);
        // txtLongNumber		
        this.txtLongNumber.setRequired(true);		
        this.txtLongNumber.setMaxLength(80);
        // bizDutyOrgUnit		
        this.bizDutyOrgUnit.setDisplayFormat("$name$");		
        this.bizDutyOrgUnit.setEditFormat("$number$");		
        this.bizDutyOrgUnit.setCommitFormat("$number$");		
        this.bizDutyOrgUnit.setEditable(true);
        this.bizDutyOrgUnit.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    bizPromptCompany_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kdftxtPayScale		
        this.kdftxtPayScale.setDataType(1);		
        this.kdftxtPayScale.setPrecision(10);		
        this.kdftxtPayScale.setMultiplier(3);
        // txtStampTaxRate		
        this.txtStampTaxRate.setDataType(1);		
        this.txtStampTaxRate.setPrecision(2);
        // prmtEntry		
        this.prmtEntry.setDisplayFormat("$name$");		
        this.prmtEntry.setEditFormat("$longNumber$");		
        this.prmtEntry.setCommitFormat("$longNumber$");		
        this.prmtEntry.setQueryInfo("com.kingdee.eas.fdc.contract.app.PayContentTypeQuery");		
        this.prmtEntry.setEnabledMultiSelection(true);
        this.prmtEntry.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtEntry_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // cbOrgType		
        this.cbOrgType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum").toArray());
        // prmtContractWFTypeEntry		
        this.prmtContractWFTypeEntry.setDisplayFormat("$name$");		
        this.prmtContractWFTypeEntry.setEditFormat("$longNumber$");		
        this.prmtContractWFTypeEntry.setCommitFormat("$longNumber$");		
        this.prmtContractWFTypeEntry.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractWFQuery");		
        this.prmtContractWFTypeEntry.setEnabledMultiSelection(true);
        this.prmtContractWFTypeEntry.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtContractWFTypeEntry_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtInviteType		
        this.prmtInviteType.setQueryInfo("com.kingdee.eas.fdc.invite.app.F7InviteTypeQuery");		
        this.prmtInviteType.setEditFormat("$number$");		
        this.prmtInviteType.setDisplayFormat("$name$");		
        this.prmtInviteType.setCommitFormat("$number$");		
        this.prmtInviteType.setEnabledMultiSelection(true);
        this.prmtInviteType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtInviteType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
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
        this.setBounds(new Rectangle(10, 10, 290, 570));
        this.setLayout(null);
        contName.setBounds(new Rectangle(10, 30, 270, 19));
        this.add(contName, null);
        contLongNumber.setBounds(new Rectangle(10, 8, 270, 19));
        this.add(contLongNumber, null);
        chkIsEnabled.setBounds(new Rectangle(282, 278, 140, 19));
        this.add(chkIsEnabled, null);
        kDLabel1.setBounds(new Rectangle(11, 505, 100, 19));
        this.add(kDLabel1, null);
        bizDescription.setBounds(new Rectangle(11, 525, 270, 34));
        this.add(bizDescription, null);
        chkIsCost.setBounds(new Rectangle(185, 207, 102, 19));
        this.add(chkIsCost, null);
        kDLabelContainer1.setBounds(new Rectangle(10, 52, 270, 19));
        this.add(kDLabelContainer1, null);
        kDLabelContainer2.setBounds(new Rectangle(10, 74, 270, 19));
        this.add(kDLabelContainer2, null);
        contStampTaxRate.setBounds(new Rectangle(10, 96, 270, 19));
        this.add(contStampTaxRate, null);
        contEntry.setBounds(new Rectangle(10, 118, 270, 19));
        this.add(contEntry, null);
        contOrgType.setBounds(new Rectangle(10, 162, 270, 19));
        this.add(contOrgType, null);
        contContractWFTypeEntry.setBounds(new Rectangle(10, 140, 270, 19));
        this.add(contContractWFTypeEntry, null);
        contInviteType.setBounds(new Rectangle(10, 184, 270, 19));
        this.add(contInviteType, null);
        cbIsAccountView.setBounds(new Rectangle(10, 207, 172, 19));
        this.add(cbIsAccountView, null);
        cbSinglePayment.setBounds(new Rectangle(10, 229, 140, 19));
        this.add(cbSinglePayment, null);
        cbIsTA.setBounds(new Rectangle(10, 251, 246, 19));
        this.add(cbIsTA, null);
        cbIsMarket.setBounds(new Rectangle(10, 295, 254, 19));
        this.add(cbIsMarket, null);
        cbIsWebPC.setBounds(new Rectangle(10, 317, 274, 19));
        this.add(cbIsWebPC, null);
        cbIsReceive.setBounds(new Rectangle(10, 361, 140, 19));
        this.add(cbIsReceive, null);
        cbIsRelateReceive.setBounds(new Rectangle(10, 340, 198, 19));
        this.add(cbIsRelateReceive, null);
        cbIsFund.setBounds(new Rectangle(10, 382, 140, 19));
        this.add(cbIsFund, null);
        cbIsTrip.setBounds(new Rectangle(10, 403, 115, 19));
        this.add(cbIsTrip, null);
        cbIsExpense.setBounds(new Rectangle(10, 424, 140, 19));
        this.add(cbIsExpense, null);
        cbIsPurchase.setBounds(new Rectangle(10, 445, 140, 19));
        this.add(cbIsPurchase, null);
        cbIsHide.setBounds(new Rectangle(10, 466, 205, 19));
        this.add(cbIsHide, null);
        txtHideDay.setBounds(new Rectangle(134, 486, 42, 19));
        this.add(txtHideDay, null);
        kDLabel2.setBounds(new Rectangle(13, 486, 131, 19));
        this.add(kDLabel2, null);
        kDLabel3.setBounds(new Rectangle(178, 486, 44, 19));
        this.add(kDLabel3, null);
        cbIsPurchaseApply.setBounds(new Rectangle(10, 273, 140, 19));
        this.add(cbIsPurchaseApply, null);
        //contName
        contName.setBoundEditor(bizName);
        //contLongNumber
        contLongNumber.setBoundEditor(txtLongNumber);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(bizDutyOrgUnit);
        //kDLabelContainer2
        kDLabelContainer2.setBoundEditor(kdftxtPayScale);
        //contStampTaxRate
        contStampTaxRate.setBoundEditor(txtStampTaxRate);
        //contEntry
        contEntry.setBoundEditor(prmtEntry);
        //contOrgType
        contOrgType.setBoundEditor(cbOrgType);
        //contContractWFTypeEntry
        contContractWFTypeEntry.setBoundEditor(prmtContractWFTypeEntry);
        //contInviteType
        contInviteType.setBoundEditor(prmtInviteType);

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
        this.menuBar.add(menuTool);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(menuItemSubmit);
        menuFile.add(menuItemCloudShare);
        menuFile.add(menuSubmitOption);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(rMenuItemSubmit);
        menuFile.add(rMenuItemSubmitAndAddNew);
        menuFile.add(rMenuItemSubmitAndPrint);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemMsgFormat);
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnReset);
        this.toolBar.add(kDSeparatorCloud);
        this.toolBar.add(btnSave);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isEnabled", boolean.class, this.chkIsEnabled, "selected");
		dataBinder.registerBinding("description", String.class, this.bizDescription, "_multiLangItem");
		dataBinder.registerBinding("isCost", boolean.class, this.chkIsCost, "selected");
		dataBinder.registerBinding("isAccountView", boolean.class, this.cbIsAccountView, "selected");
		dataBinder.registerBinding("singlePayment", boolean.class, this.cbSinglePayment, "selected");
		dataBinder.registerBinding("isTA", boolean.class, this.cbIsTA, "selected");
		dataBinder.registerBinding("isMarket", boolean.class, this.cbIsMarket, "selected");
		dataBinder.registerBinding("isWebPC", boolean.class, this.cbIsWebPC, "selected");
		dataBinder.registerBinding("isReceive", boolean.class, this.cbIsReceive, "selected");
		dataBinder.registerBinding("isRelateReceive", boolean.class, this.cbIsRelateReceive, "selected");
		dataBinder.registerBinding("isFund", boolean.class, this.cbIsFund, "selected");
		dataBinder.registerBinding("isTrip", boolean.class, this.cbIsTrip, "selected");
		dataBinder.registerBinding("isExpense", boolean.class, this.cbIsExpense, "selected");
		dataBinder.registerBinding("isPurchase", boolean.class, this.cbIsPurchase, "selected");
		dataBinder.registerBinding("isHide", boolean.class, this.cbIsHide, "selected");
		dataBinder.registerBinding("hideDay", int.class, this.txtHideDay, "value");
		dataBinder.registerBinding("isPurchaseApply", boolean.class, this.cbIsPurchaseApply, "selected");
		dataBinder.registerBinding("name", String.class, this.bizName, "_multiLangItem");
		dataBinder.registerBinding("longNumber", String.class, this.txtLongNumber, "text");
		dataBinder.registerBinding("dutyOrgUnit", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.bizDutyOrgUnit, "data");
		dataBinder.registerBinding("payScale", java.math.BigDecimal.class, this.kdftxtPayScale, "value");
		dataBinder.registerBinding("stampTaxRate", java.math.BigDecimal.class, this.txtStampTaxRate, "value");
		dataBinder.registerBinding("orgType", com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.class, this.cbOrgType, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.basedata.app.ContractTypeEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.basedata.ContractTypeInfo)ov;
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
	 * ????????§µ??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("isEnabled", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isCost", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isAccountView", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("singlePayment", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isTA", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isMarket", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isWebPC", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isReceive", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isRelateReceive", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isFund", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isTrip", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isExpense", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isPurchase", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isHide", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("hideDay", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isPurchaseApply", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("longNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dutyOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payScale", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("stampTaxRate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgType", ValidateHelper.ON_SAVE);    		
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
        }
    }

    /**
     * output bizPromptCompany_dataChanged method
     */
    protected void bizPromptCompany_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtEntry_dataChanged method
     */
    protected void prmtEntry_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtContractWFTypeEntry_dataChanged method
     */
    protected void prmtContractWFTypeEntry_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtInviteType_dataChanged method
     */
    protected void prmtInviteType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
		String selectorAll = System.getProperty("selector.all");
		if(StringUtils.isEmpty(selectorAll)){
			selectorAll = "true";
		}
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("isCost"));
        sic.add(new SelectorItemInfo("isAccountView"));
        sic.add(new SelectorItemInfo("singlePayment"));
        sic.add(new SelectorItemInfo("isTA"));
        sic.add(new SelectorItemInfo("isMarket"));
        sic.add(new SelectorItemInfo("isWebPC"));
        sic.add(new SelectorItemInfo("isReceive"));
        sic.add(new SelectorItemInfo("isRelateReceive"));
        sic.add(new SelectorItemInfo("isFund"));
        sic.add(new SelectorItemInfo("isTrip"));
        sic.add(new SelectorItemInfo("isExpense"));
        sic.add(new SelectorItemInfo("isPurchase"));
        sic.add(new SelectorItemInfo("isHide"));
        sic.add(new SelectorItemInfo("hideDay"));
        sic.add(new SelectorItemInfo("isPurchaseApply"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("longNumber"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("dutyOrgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("dutyOrgUnit.id"));
        	sic.add(new SelectorItemInfo("dutyOrgUnit.number"));
        	sic.add(new SelectorItemInfo("dutyOrgUnit.name"));
		}
        sic.add(new SelectorItemInfo("payScale"));
        sic.add(new SelectorItemInfo("stampTaxRate"));
        sic.add(new SelectorItemInfo("orgType"));
        return sic;
    }        
    	

    /**
     * output actionFirst_actionPerformed method
     */
    public void actionFirst_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionFirst_actionPerformed(e);
    }
	public RequestContext prepareActionFirst(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionFirst(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionFirst() {
    	return false;
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.basedata.client", "ContractTypeEditUI");
    }




}