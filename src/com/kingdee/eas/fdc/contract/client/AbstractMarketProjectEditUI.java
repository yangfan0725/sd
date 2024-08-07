/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

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
public abstract class AbstractMarketProjectEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMarketProjectEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contName;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsSupplier;
    protected com.kingdee.bos.ctrl.swing.KDContainer contEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer contCostEntry;
    protected com.kingdee.bos.ctrl.swing.KDContainer contUnitEntry;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsSub;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMp;
    protected com.kingdee.bos.ctrl.swing.KDContainer contAttachment;
    protected com.kingdee.bos.ctrl.swing.KDTextArea kDTextArea1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNw;
    protected com.kingdee.bos.ctrl.swing.KDLabel kDLabel1;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsJT;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer conSource;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsKJ;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtName;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtCostEntry;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kdtUnitEntry;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMp;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttachement;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtSellProject;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbNw;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbSource;
    protected com.kingdee.eas.fdc.contract.MarketProjectInfo editData = null;
    protected ActionALine actionALine = null;
    protected ActionRLine actionRLine = null;
    protected ActionACLine actionACLine = null;
    protected ActionRCLine actionRCLine = null;
    protected ActionAULine actionAULine = null;
    protected ActionRULine actionRULine = null;
    /**
     * output class constructor
     */
    public AbstractMarketProjectEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMarketProjectEditUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionSubmit
        String _tempStr = null;
        actionSubmit.setEnabled(true);
        actionSubmit.setDaemonRun(false);

        actionSubmit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
        _tempStr = resHelper.getString("ActionSubmit.SHORT_DESCRIPTION");
        actionSubmit.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.LONG_DESCRIPTION");
        actionSubmit.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
        _tempStr = resHelper.getString("ActionSubmit.NAME");
        actionSubmit.putValue(ItemAction.NAME, _tempStr);
        this.actionSubmit.setBindWorkFlow(true);
        this.actionSubmit.setExtendProperty("canForewarn", "true");
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.NetFunctionService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.UserMonitorService());
         this.actionSubmit.addService(new com.kingdee.eas.framework.client.service.ForewarnService());
        //actionALine
        this.actionALine = new ActionALine(this);
        getActionManager().registerAction("actionALine", actionALine);
         this.actionALine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRLine
        this.actionRLine = new ActionRLine(this);
        getActionManager().registerAction("actionRLine", actionRLine);
         this.actionRLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionACLine
        this.actionACLine = new ActionACLine(this);
        getActionManager().registerAction("actionACLine", actionACLine);
         this.actionACLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRCLine
        this.actionRCLine = new ActionRCLine(this);
        getActionManager().registerAction("actionRCLine", actionRCLine);
         this.actionRCLine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionAULine
        this.actionAULine = new ActionAULine(this);
        getActionManager().registerAction("actionAULine", actionAULine);
         this.actionAULine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRULine
        this.actionRULine = new ActionRULine(this);
        getActionManager().registerAction("actionRULine", actionRULine);
         this.actionRULine.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contName = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsSupplier = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contCostEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contUnitEntry = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.cbIsSub = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.contMp = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachment = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.kDTextArea1 = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNw = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabel1 = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.cbIsJT = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.conSource = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsKJ = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtName = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kdtEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtCostEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.kdtUnitEntry = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.prmtMp = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.tblAttachement = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtSellProject = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.cbNw = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.cbSource = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contNumber.setName("contNumber");
        this.contName.setName("contName");
        this.contOrgUnit.setName("contOrgUnit");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.contBizDate.setName("contBizDate");
        this.contAmount.setName("contAmount");
        this.cbIsSupplier.setName("cbIsSupplier");
        this.contEntry.setName("contEntry");
        this.contCostEntry.setName("contCostEntry");
        this.contUnitEntry.setName("contUnitEntry");
        this.cbIsSub.setName("cbIsSub");
        this.contMp.setName("contMp");
        this.contAttachment.setName("contAttachment");
        this.kDTextArea1.setName("kDTextArea1");
        this.contSellProject.setName("contSellProject");
        this.contNw.setName("contNw");
        this.kDLabel1.setName("kDLabel1");
        this.cbIsJT.setName("cbIsJT");
        this.conSource.setName("conSource");
        this.cbIsKJ.setName("cbIsKJ");
        this.txtNumber.setName("txtNumber");
        this.txtName.setName("txtName");
        this.prmtOrgUnit.setName("prmtOrgUnit");
        this.txtDescription.setName("txtDescription");
        this.pkBizDate.setName("pkBizDate");
        this.txtAmount.setName("txtAmount");
        this.kdtEntry.setName("kdtEntry");
        this.kdtCostEntry.setName("kdtCostEntry");
        this.kdtUnitEntry.setName("kdtUnitEntry");
        this.prmtMp.setName("prmtMp");
        this.tblAttachement.setName("tblAttachement");
        this.txtSellProject.setName("txtSellProject");
        this.cbNw.setName("cbNw");
        this.cbSource.setName("cbSource");
        // CoreUI		
        this.setPreferredSize(new Dimension(1013,629));		
        this.btnPageSetup.setVisible(false);		
        this.btnCloud.setVisible(false);		
        this.btnXunTong.setVisible(false);		
        this.kDSeparatorCloud.setVisible(false);		
        this.menuItemPageSetup.setVisible(false);		
        this.menuItemCloudFeed.setVisible(false);		
        this.menuItemCloudScreen.setEnabled(false);		
        this.menuItemCloudScreen.setVisible(false);		
        this.menuItemCloudShare.setVisible(false);		
        this.kdSeparatorFWFile1.setVisible(false);		
        this.menuItemCalculator.setVisible(true);		
        this.btnCancelCancel.setVisible(false);		
        this.btnCancelCancel.setEnabled(false);		
        this.btnCancel.setEnabled(false);		
        this.btnCancel.setVisible(false);		
        this.kDSeparator2.setVisible(false);		
        this.menuItemPrint.setVisible(true);		
        this.menuItemPrintPreview.setVisible(true);		
        this.kDSeparator4.setVisible(false);		
        this.kDSeparator4.setEnabled(false);		
        this.rMenuItemSubmit.setVisible(false);		
        this.rMenuItemSubmit.setEnabled(false);		
        this.rMenuItemSubmitAndAddNew.setVisible(false);		
        this.rMenuItemSubmitAndAddNew.setEnabled(false);		
        this.rMenuItemSubmitAndPrint.setVisible(false);		
        this.rMenuItemSubmitAndPrint.setEnabled(false);		
        this.menuItemCancelCancel.setVisible(false);		
        this.menuItemCancelCancel.setEnabled(false);		
        this.menuItemCancel.setEnabled(false);		
        this.menuItemCancel.setVisible(false);		
        this.btnReset.setEnabled(false);		
        this.btnReset.setVisible(false);		
        this.menuItemReset.setEnabled(false);		
        this.menuItemReset.setVisible(false);		
        this.btnSignature.setVisible(false);		
        this.btnSignature.setEnabled(false);		
        this.btnViewSignature.setEnabled(false);		
        this.btnViewSignature.setVisible(false);		
        this.separatorFW4.setVisible(false);		
        this.separatorFW4.setEnabled(false);		
        this.btnNumberSign.setEnabled(false);		
        this.btnNumberSign.setVisible(false);		
        this.btnCopyFrom.setVisible(false);		
        this.btnCopyFrom.setEnabled(false);		
        this.btnCreateTo.setVisible(false);		
        this.separatorFW5.setVisible(false);		
        this.separatorFW5.setEnabled(false);		
        this.btnCopyLine.setVisible(false);		
        this.separatorFW6.setVisible(false);		
        this.separatorFW6.setEnabled(false);		
        this.btnVoucher.setVisible(false);		
        this.btnDelVoucher.setVisible(false);		
        this.btnWFViewdoProccess.setEnabled(false);		
        this.btnWFViewdoProccess.setVisible(false);		
        this.btnWFViewSubmitProccess.setEnabled(false);		
        this.btnWFViewSubmitProccess.setVisible(false);		
        this.menuItemCreateTo.setVisible(false);		
        this.separatorEdit1.setVisible(false);		
        this.menuItemEnterToNextRow.setVisible(false);		
        this.separator2.setVisible(false);		
        this.menuItemLocate.setVisible(false);		
        this.MenuItemVoucher.setVisible(false);		
        this.menuItemDelVoucher.setVisible(false);		
        this.menuItemStartWorkFlow.setVisible(false);		
        this.separatorWF1.setVisible(false);		
        this.menuItemViewSubmitProccess.setVisible(false);		
        this.menuItemViewSubmitProccess.setEnabled(false);		
        this.menuItemViewDoProccess.setEnabled(false);		
        this.menuItemViewDoProccess.setVisible(false);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contName		
        this.contName.setBoundLabelText(resHelper.getString("contName.boundLabelText"));		
        this.contName.setBoundLabelLength(100);		
        this.contName.setBoundLabelUnderline(true);		
        this.contName.setEnabled(false);
        // contOrgUnit		
        this.contOrgUnit.setBoundLabelText(resHelper.getString("contOrgUnit.boundLabelText"));		
        this.contOrgUnit.setBoundLabelLength(100);		
        this.contOrgUnit.setBoundLabelUnderline(true);		
        this.contOrgUnit.setEnabled(false);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));
        // kDScrollPane1
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);
        // cbIsSupplier		
        this.cbIsSupplier.setText(resHelper.getString("cbIsSupplier.text"));
        this.cbIsSupplier.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    cbIsSupplier_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contEntry		
        this.contEntry.setTitle(resHelper.getString("contEntry.title"));
        // contCostEntry		
        this.contCostEntry.setTitle(resHelper.getString("contCostEntry.title"));
        // contUnitEntry		
        this.contUnitEntry.setTitle(resHelper.getString("contUnitEntry.title"));
        // cbIsSub		
        this.cbIsSub.setText(resHelper.getString("cbIsSub.text"));
        this.cbIsSub.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent e) {
                try {
                    cbIsSub_stateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // contMp		
        this.contMp.setBoundLabelText(resHelper.getString("contMp.boundLabelText"));		
        this.contMp.setBoundLabelLength(100);		
        this.contMp.setBoundLabelUnderline(true);
        // contAttachment		
        this.contAttachment.setTitle(resHelper.getString("contAttachment.title"));
        // kDTextArea1
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelLength(100);		
        this.contSellProject.setBoundLabelUnderline(true);
        // contNw		
        this.contNw.setBoundLabelText(resHelper.getString("contNw.boundLabelText"));		
        this.contNw.setBoundLabelLength(100);		
        this.contNw.setBoundLabelUnderline(true);
        // kDLabel1		
        this.kDLabel1.setText(resHelper.getString("kDLabel1.text"));
        // cbIsJT		
        this.cbIsJT.setText(resHelper.getString("cbIsJT.text"));
        // conSource		
        this.conSource.setBoundLabelText(resHelper.getString("conSource.boundLabelText"));		
        this.conSource.setBoundLabelLength(100);		
        this.conSource.setBoundLabelUnderline(true);
        // cbIsKJ		
        this.cbIsKJ.setText(resHelper.getString("cbIsKJ.text"));
        // txtNumber
        // txtName		
        this.txtName.setRequired(true);
        // prmtOrgUnit		
        this.prmtOrgUnit.setEnabled(false);
        // txtDescription		
        this.txtDescription.setText(resHelper.getString("txtDescription.text"));
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        // txtAmount		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setRequired(true);		
        this.txtAmount.setEnabled(false);
        // kdtEntry
		String kdtEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"supplier\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{supplier}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtEntry.setFormatXml(resHelper.translateString("kdtEntry",kdtEntryStrXML));

                this.kdtEntry.putBindContents("editData",new String[] {"supplier","amount","remark"});


        // kdtCostEntry
		String kdtCostEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"costAccount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"type\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"canAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{costAccount}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{canAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtCostEntry.setFormatXml(resHelper.translateString("kdtCostEntry",kdtCostEntryStrXML));
        this.kdtCostEntry.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kdtCostEntry_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

                this.kdtCostEntry.putBindContents("editData",new String[] {"costAccount","amount","type",""});


        // kdtUnitEntry
		String kdtUnitEntryStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"unit\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"remark\" t:width=\"200\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{unit}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.kdtUnitEntry.setFormatXml(resHelper.translateString("kdtUnitEntry",kdtUnitEntryStrXML));

                this.kdtUnitEntry.putBindContents("editData",new String[] {"unit","amount","remark"});


        // prmtMp		
        this.prmtMp.setQueryInfo("com.kingdee.eas.fdc.contract.app.MarketProjectQuery");		
        this.prmtMp.setDisplayFormat("$name$");		
        this.prmtMp.setEditFormat("$number$");		
        this.prmtMp.setCommitFormat("$number$");
        this.prmtMp.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtMp_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // tblAttachement
		String tblAttachementStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sTable\"><c:Alignment horizontal=\"left\" /><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol2\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol3\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"2\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\" t:styleID=\"sTable\"><t:ColumnGroup><t:Column t:key=\"seq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"name\" t:width=\"400\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"type\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"date\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{seq}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{type}</t:Cell><t:Cell>$Resource{date}</t:Cell><t:Cell>$Resource{id}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblAttachement.setFormatXml(resHelper.translateString("tblAttachement",tblAttachementStrXML));
        this.tblAttachement.addKDTMouseListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
                try {
                    tblAttachement_tableClicked(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });

        

        // txtSellProject		
        this.txtSellProject.setRequired(true);
        // cbNw		
        this.cbNw.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum").toArray());		
        this.cbNw.setRequired(true);
        // cbSource		
        this.cbSource.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.contract.MarketProjectSourceEnum").toArray());		
        this.cbSource.setEnabled(false);
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
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        contNumber.setBounds(new Rectangle(30, 18, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(30, 18, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contName.setBounds(new Rectangle(376, 18, 270, 19));
        this.add(contName, new KDLayout.Constraints(376, 18, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrgUnit.setBounds(new Rectangle(704, 18, 270, 19));
        this.add(contOrgUnit, new KDLayout.Constraints(704, 18, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabelContainer4.setBounds(new Rectangle(30, 122, 73, 19));
        this.add(kDLabelContainer4, new KDLayout.Constraints(30, 122, 73, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDScrollPane1.setBounds(new Rectangle(30, 142, 947, 65));
        this.add(kDScrollPane1, new KDLayout.Constraints(30, 142, 947, 65, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizDate.setBounds(new Rectangle(30, 45, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(30, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAmount.setBounds(new Rectangle(376, 45, 270, 19));
        this.add(contAmount, new KDLayout.Constraints(376, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbIsSupplier.setBounds(new Rectangle(511, 318, 140, 19));
        this.add(cbIsSupplier, new KDLayout.Constraints(511, 318, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contEntry.setBounds(new Rectangle(511, 334, 465, 155));
        this.add(contEntry, new KDLayout.Constraints(511, 334, 465, 155, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCostEntry.setBounds(new Rectangle(30, 210, 466, 106));
        this.add(contCostEntry, new KDLayout.Constraints(30, 210, 466, 106, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contUnitEntry.setBounds(new Rectangle(30, 334, 465, 157));
        this.add(contUnitEntry, new KDLayout.Constraints(30, 334, 465, 157, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbIsSub.setBounds(new Rectangle(30, 75, 140, 19));
        this.add(cbIsSub, new KDLayout.Constraints(30, 75, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contMp.setBounds(new Rectangle(376, 75, 270, 19));
        this.add(contMp, new KDLayout.Constraints(376, 75, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAttachment.setBounds(new Rectangle(31, 493, 942, 124));
        this.add(contAttachment, new KDLayout.Constraints(31, 493, 942, 124, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        kDTextArea1.setBounds(new Rectangle(516, 217, 460, 96));
        this.add(kDTextArea1, new KDLayout.Constraints(516, 217, 460, 96, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSellProject.setBounds(new Rectangle(704, 45, 270, 19));
        this.add(contSellProject, new KDLayout.Constraints(704, 45, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contNw.setBounds(new Rectangle(704, 75, 270, 19));
        this.add(contNw, new KDLayout.Constraints(704, 75, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDLabel1.setBounds(new Rectangle(704, 97, 294, 19));
        this.add(kDLabel1, new KDLayout.Constraints(704, 97, 294, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        cbIsJT.setBounds(new Rectangle(30, 98, 278, 19));
        this.add(cbIsJT, new KDLayout.Constraints(30, 98, 278, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conSource.setBounds(new Rectangle(376, 98, 270, 19));
        this.add(conSource, new KDLayout.Constraints(376, 98, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        cbIsKJ.setBounds(new Rectangle(186, 75, 140, 19));
        this.add(cbIsKJ, new KDLayout.Constraints(186, 75, 140, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contName
        contName.setBoundEditor(txtName);
        //contOrgUnit
        contOrgUnit.setBoundEditor(prmtOrgUnit);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contEntry
contEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contEntry.getContentPane().add(kdtEntry, BorderLayout.CENTER);
        //contCostEntry
contCostEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contCostEntry.getContentPane().add(kdtCostEntry, BorderLayout.CENTER);
        //contUnitEntry
contUnitEntry.getContentPane().setLayout(new BorderLayout(0, 0));        contUnitEntry.getContentPane().add(kdtUnitEntry, BorderLayout.CENTER);
        //contMp
        contMp.setBoundEditor(prmtMp);
        //contAttachment
contAttachment.getContentPane().setLayout(new BorderLayout(0, 0));        contAttachment.getContentPane().add(tblAttachement, BorderLayout.CENTER);
        //contSellProject
        contSellProject.setBoundEditor(txtSellProject);
        //contNw
        contNw.setBoundEditor(cbNw);
        //conSource
        conSource.setBoundEditor(cbSource);

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
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnSave);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnSignature);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("isSupplier", boolean.class, this.cbIsSupplier, "selected");
		dataBinder.registerBinding("isSub", boolean.class, this.cbIsSub, "selected");
		dataBinder.registerBinding("isJT", boolean.class, this.cbIsJT, "selected");
		dataBinder.registerBinding("isKJ", boolean.class, this.cbIsKJ, "selected");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("name", String.class, this.txtName, "text");
		dataBinder.registerBinding("orgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.prmtOrgUnit, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("entry", com.kingdee.eas.fdc.contract.MarketProjectEntryInfo.class, this.kdtEntry, "userObject");
		dataBinder.registerBinding("entry.supplier", com.kingdee.eas.basedata.master.cssp.SupplierInfo.class, this.kdtEntry, "supplier.text");
		dataBinder.registerBinding("entry.amount", java.math.BigDecimal.class, this.kdtEntry, "amount.text");
		dataBinder.registerBinding("entry.remark", String.class, this.kdtEntry, "remark.text");
		dataBinder.registerBinding("costEntry", com.kingdee.eas.fdc.contract.MarketProjectCostEntryInfo.class, this.kdtCostEntry, "userObject");
		dataBinder.registerBinding("costEntry.costAccount", com.kingdee.eas.fdc.basedata.CostAccountInfo.class, this.kdtCostEntry, "costAccount.text");
		dataBinder.registerBinding("costEntry.amount", java.math.BigDecimal.class, this.kdtCostEntry, "amount.text");
		dataBinder.registerBinding("costEntry.type", com.kingdee.eas.fdc.contract.app.MarketCostTypeEnum.class, this.kdtCostEntry, "type.text");
		dataBinder.registerBinding("unitEntry", com.kingdee.eas.fdc.contract.MarketProjectUnitEntryInfo.class, this.kdtUnitEntry, "userObject");
		dataBinder.registerBinding("unitEntry.unit", String.class, this.kdtUnitEntry, "unit.text");
		dataBinder.registerBinding("unitEntry.amount", java.math.BigDecimal.class, this.kdtUnitEntry, "amount.text");
		dataBinder.registerBinding("unitEntry.remark", String.class, this.kdtUnitEntry, "remark.text");
		dataBinder.registerBinding("mp", com.kingdee.eas.fdc.contract.MarketProjectInfo.class, this.prmtMp, "data");
		dataBinder.registerBinding("sellProjecttxt", String.class, this.txtSellProject, "text");
		dataBinder.registerBinding("nw", com.kingdee.eas.fdc.basedata.ContractTypeOrgTypeEnum.class, this.cbNw, "selectedItem");
		dataBinder.registerBinding("source", com.kingdee.eas.fdc.contract.MarketProjectSourceEnum.class, this.cbSource, "selectedItem");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.MarketProjectEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.MarketProjectInfo)ov;
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
	 * ????????��??
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );
		getValidateHelper().registerBindProperty("isSupplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isSub", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isJT", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isKJ", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("orgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.supplier", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("entry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costEntry.costAccount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costEntry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("costEntry.type", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unitEntry", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unitEntry.unit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unitEntry.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("unitEntry.remark", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("mp", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellProjecttxt", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("nw", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("source", ValidateHelper.ON_SAVE);    		
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
     * output cbIsSupplier_stateChanged method
     */
    protected void cbIsSupplier_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output cbIsSub_stateChanged method
     */
    protected void cbIsSub_stateChanged(javax.swing.event.ChangeEvent e) throws Exception
    {
    }

    /**
     * output kdtCostEntry_editStopped method
     */
    protected void kdtCostEntry_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
    }

    /**
     * output prmtMp_dataChanged method
     */
    protected void prmtMp_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output tblAttachement_tableClicked method
     */
    protected void tblAttachement_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
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
        sic.add(new SelectorItemInfo("isSupplier"));
        sic.add(new SelectorItemInfo("isSub"));
        sic.add(new SelectorItemInfo("isJT"));
        sic.add(new SelectorItemInfo("isKJ"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("orgUnit.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("orgUnit.id"));
        	sic.add(new SelectorItemInfo("orgUnit.number"));
        	sic.add(new SelectorItemInfo("orgUnit.name"));
		}
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("amount"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("entry.supplier.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("entry.supplier.id"));
			sic.add(new SelectorItemInfo("entry.supplier.name"));
        	sic.add(new SelectorItemInfo("entry.supplier.number"));
		}
    	sic.add(new SelectorItemInfo("entry.amount"));
    	sic.add(new SelectorItemInfo("entry.remark"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costEntry.*"));
		}
		else{
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("costEntry.costAccount.*"));
		}
		else{
	    	sic.add(new SelectorItemInfo("costEntry.costAccount.id"));
			sic.add(new SelectorItemInfo("costEntry.costAccount.name"));
        	sic.add(new SelectorItemInfo("costEntry.costAccount.number"));
		}
    	sic.add(new SelectorItemInfo("costEntry.amount"));
    	sic.add(new SelectorItemInfo("costEntry.type"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("unitEntry.*"));
		}
		else{
		}
    	sic.add(new SelectorItemInfo("unitEntry.unit"));
    	sic.add(new SelectorItemInfo("unitEntry.amount"));
    	sic.add(new SelectorItemInfo("unitEntry.remark"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("mp.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("mp.id"));
        	sic.add(new SelectorItemInfo("mp.number"));
        	sic.add(new SelectorItemInfo("mp.name"));
		}
        sic.add(new SelectorItemInfo("sellProjecttxt"));
        sic.add(new SelectorItemInfo("nw"));
        sic.add(new SelectorItemInfo("source"));
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
    }
    	

    /**
     * output actionALine_actionPerformed method
     */
    public void actionALine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRLine_actionPerformed method
     */
    public void actionRLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionACLine_actionPerformed method
     */
    public void actionACLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRCLine_actionPerformed method
     */
    public void actionRCLine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionAULine_actionPerformed method
     */
    public void actionAULine_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRULine_actionPerformed method
     */
    public void actionRULine_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionSubmit(IItemAction itemAction) throws Exception {
			RequestContext request = super.prepareActionSubmit(itemAction);		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSubmit() {
    	return false;
    }
	public RequestContext prepareActionALine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionALine() {
    	return false;
    }
	public RequestContext prepareActionRLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRLine() {
    	return false;
    }
	public RequestContext prepareActionACLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionACLine() {
    	return false;
    }
	public RequestContext prepareActionRCLine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRCLine() {
    	return false;
    }
	public RequestContext prepareActionAULine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAULine() {
    	return false;
    }
	public RequestContext prepareActionRULine(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRULine() {
    	return false;
    }

    /**
     * output ActionALine class
     */     
    protected class ActionALine extends ItemAction {     
    
        public ActionALine()
        {
            this(null);
        }

        public ActionALine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionALine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionALine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionALine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketProjectEditUI.this, "ActionALine", "actionALine_actionPerformed", e);
        }
    }

    /**
     * output ActionRLine class
     */     
    protected class ActionRLine extends ItemAction {     
    
        public ActionRLine()
        {
            this(null);
        }

        public ActionRLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketProjectEditUI.this, "ActionRLine", "actionRLine_actionPerformed", e);
        }
    }

    /**
     * output ActionACLine class
     */     
    protected class ActionACLine extends ItemAction {     
    
        public ActionACLine()
        {
            this(null);
        }

        public ActionACLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionACLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionACLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionACLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketProjectEditUI.this, "ActionACLine", "actionACLine_actionPerformed", e);
        }
    }

    /**
     * output ActionRCLine class
     */     
    protected class ActionRCLine extends ItemAction {     
    
        public ActionRCLine()
        {
            this(null);
        }

        public ActionRCLine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRCLine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRCLine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRCLine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketProjectEditUI.this, "ActionRCLine", "actionRCLine_actionPerformed", e);
        }
    }

    /**
     * output ActionAULine class
     */     
    protected class ActionAULine extends ItemAction {     
    
        public ActionAULine()
        {
            this(null);
        }

        public ActionAULine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAULine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAULine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAULine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketProjectEditUI.this, "ActionAULine", "actionAULine_actionPerformed", e);
        }
    }

    /**
     * output ActionRULine class
     */     
    protected class ActionRULine extends ItemAction {     
    
        public ActionRULine()
        {
            this(null);
        }

        public ActionRULine(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRULine.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRULine.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRULine.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMarketProjectEditUI.this, "ActionRULine", "actionRULine_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "MarketProjectEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}