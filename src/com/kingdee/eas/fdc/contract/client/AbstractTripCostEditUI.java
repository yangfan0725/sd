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
public abstract class AbstractTripCostEditUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractTripCostEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurpose;
    protected com.kingdee.bos.ctrl.swing.KDContainer contAttachment;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contProj;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOrg;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contToDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDays;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTransport;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAmount;
    protected com.kingdee.bos.ctrl.swing.KDContainer contTripApply;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPerson;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractWFType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCreator;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtAuditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtPurpose;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblAttachement;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtProj;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOrg;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkToDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDays;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtPlace;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTransport;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplyBizDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplyFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplyToDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplyDays;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplyPlace;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplyTransport;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplyAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplyPurpose;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplyDept;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contApplyPerson;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkApplyBizDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkApplyFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkApplyToDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApplyDays;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtApplyPlace;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtApplyTransport;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtApplyAmount;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtApplyPurpose;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplyDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtApplyPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtDept;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtPerson;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractType;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtContractWFType;
    protected com.kingdee.eas.fdc.contract.TripCostInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractTripCostEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractTripCostEditUI.class.getName());
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
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurpose = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAttachment = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contProj = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOrg = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTransport = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTripApply = new com.kingdee.bos.ctrl.swing.KDContainer();
        this.contDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractWFType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtCreator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.prmtAuditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtPurpose = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.tblAttachement = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.txtProj = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtOrg = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.pkBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtDays = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPlace = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTransport = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contApplyBizDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplyFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplyToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplyDays = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplyPlace = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplyTransport = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplyAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplyPurpose = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplyDept = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contApplyPerson = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.pkApplyBizDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkApplyFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkApplyToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtApplyDays = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtApplyPlace = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtApplyTransport = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtApplyAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.txtApplyPurpose = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.prmtApplyDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtApplyPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtDept = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtPerson = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtContractType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtContractWFType = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreator.setName("contCreator");
        this.contCreateTime.setName("contCreateTime");
        this.contNumber.setName("contNumber");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contPurpose.setName("contPurpose");
        this.contAttachment.setName("contAttachment");
        this.contProj.setName("contProj");
        this.contOrg.setName("contOrg");
        this.contBizDate.setName("contBizDate");
        this.contFromDate.setName("contFromDate");
        this.contToDate.setName("contToDate");
        this.contDays.setName("contDays");
        this.contPlace.setName("contPlace");
        this.contTransport.setName("contTransport");
        this.contAmount.setName("contAmount");
        this.contTripApply.setName("contTripApply");
        this.contDept.setName("contDept");
        this.contPerson.setName("contPerson");
        this.contContractType.setName("contContractType");
        this.contContractWFType.setName("contContractWFType");
        this.prmtCreator.setName("prmtCreator");
        this.pkCreateTime.setName("pkCreateTime");
        this.txtNumber.setName("txtNumber");
        this.prmtAuditor.setName("prmtAuditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.txtPurpose.setName("txtPurpose");
        this.tblAttachement.setName("tblAttachement");
        this.txtProj.setName("txtProj");
        this.txtOrg.setName("txtOrg");
        this.pkBizDate.setName("pkBizDate");
        this.pkFromDate.setName("pkFromDate");
        this.pkToDate.setName("pkToDate");
        this.txtDays.setName("txtDays");
        this.txtPlace.setName("txtPlace");
        this.txtTransport.setName("txtTransport");
        this.txtAmount.setName("txtAmount");
        this.contApplyBizDate.setName("contApplyBizDate");
        this.contApplyFromDate.setName("contApplyFromDate");
        this.contApplyToDate.setName("contApplyToDate");
        this.contApplyDays.setName("contApplyDays");
        this.contApplyPlace.setName("contApplyPlace");
        this.contApplyTransport.setName("contApplyTransport");
        this.contApplyAmount.setName("contApplyAmount");
        this.contApplyPurpose.setName("contApplyPurpose");
        this.contApplyDept.setName("contApplyDept");
        this.contApplyPerson.setName("contApplyPerson");
        this.pkApplyBizDate.setName("pkApplyBizDate");
        this.pkApplyFromDate.setName("pkApplyFromDate");
        this.pkApplyToDate.setName("pkApplyToDate");
        this.txtApplyDays.setName("txtApplyDays");
        this.txtApplyPlace.setName("txtApplyPlace");
        this.txtApplyTransport.setName("txtApplyTransport");
        this.txtApplyAmount.setName("txtApplyAmount");
        this.kDScrollPane2.setName("kDScrollPane2");
        this.txtApplyPurpose.setName("txtApplyPurpose");
        this.prmtApplyDept.setName("prmtApplyDept");
        this.prmtApplyPerson.setName("prmtApplyPerson");
        this.prmtDept.setName("prmtDept");
        this.prmtPerson.setName("prmtPerson");
        this.prmtContractType.setName("prmtContractType");
        this.prmtContractWFType.setName("prmtContractWFType");
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
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contPurpose		
        this.contPurpose.setBoundLabelText(resHelper.getString("contPurpose.boundLabelText"));		
        this.contPurpose.setBoundLabelLength(100);		
        this.contPurpose.setBoundLabelUnderline(true);
        // contAttachment		
        this.contAttachment.setTitle(resHelper.getString("contAttachment.title"));
        // contProj		
        this.contProj.setBoundLabelText(resHelper.getString("contProj.boundLabelText"));		
        this.contProj.setBoundLabelLength(100);		
        this.contProj.setBoundLabelUnderline(true);
        // contOrg		
        this.contOrg.setBoundLabelText(resHelper.getString("contOrg.boundLabelText"));		
        this.contOrg.setBoundLabelLength(100);		
        this.contOrg.setBoundLabelUnderline(true);
        // contBizDate		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contBizDate.setBoundLabelLength(100);		
        this.contBizDate.setBoundLabelUnderline(true);
        // contFromDate		
        this.contFromDate.setBoundLabelText(resHelper.getString("contFromDate.boundLabelText"));		
        this.contFromDate.setBoundLabelUnderline(true);		
        this.contFromDate.setBoundLabelLength(100);
        // contToDate		
        this.contToDate.setBoundLabelText(resHelper.getString("contToDate.boundLabelText"));		
        this.contToDate.setBoundLabelLength(100);		
        this.contToDate.setBoundLabelUnderline(true);
        // contDays		
        this.contDays.setBoundLabelText(resHelper.getString("contDays.boundLabelText"));		
        this.contDays.setBoundLabelLength(100);		
        this.contDays.setBoundLabelUnderline(true);
        // contPlace		
        this.contPlace.setBoundLabelText(resHelper.getString("contPlace.boundLabelText"));		
        this.contPlace.setBoundLabelUnderline(true);		
        this.contPlace.setBoundLabelLength(100);
        // contTransport		
        this.contTransport.setBoundLabelText(resHelper.getString("contTransport.boundLabelText"));		
        this.contTransport.setBoundLabelUnderline(true);		
        this.contTransport.setBoundLabelLength(100);
        // contAmount		
        this.contAmount.setBoundLabelText(resHelper.getString("contAmount.boundLabelText"));		
        this.contAmount.setBoundLabelLength(100);		
        this.contAmount.setBoundLabelUnderline(true);
        // contTripApply		
        this.contTripApply.setTitle(resHelper.getString("contTripApply.title"));
        // contDept		
        this.contDept.setBoundLabelText(resHelper.getString("contDept.boundLabelText"));		
        this.contDept.setBoundLabelLength(100);		
        this.contDept.setBoundLabelUnderline(true);
        // contPerson		
        this.contPerson.setBoundLabelText(resHelper.getString("contPerson.boundLabelText"));		
        this.contPerson.setBoundLabelLength(100);		
        this.contPerson.setBoundLabelUnderline(true);
        // contContractType		
        this.contContractType.setBoundLabelText(resHelper.getString("contContractType.boundLabelText"));		
        this.contContractType.setBoundLabelLength(100);		
        this.contContractType.setBoundLabelUnderline(true);		
        this.contContractType.setVisible(true);		
        this.contContractType.setBoundLabelAlignment(7);
        // contContractWFType		
        this.contContractWFType.setBoundLabelText(resHelper.getString("contContractWFType.boundLabelText"));		
        this.contContractWFType.setBoundLabelLength(100);		
        this.contContractWFType.setBoundLabelUnderline(true);
        // prmtCreator		
        this.prmtCreator.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // txtNumber		
        this.txtNumber.setMaxLength(200);
        // prmtAuditor		
        this.prmtAuditor.setEnabled(false);
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // kDScrollPane1
        // txtPurpose		
        this.txtPurpose.setMaxLength(2000);		
        this.txtPurpose.setRequired(true);
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

        

        // txtProj		
        this.txtProj.setEditable(false);
        // txtOrg		
        this.txtOrg.setEditable(false);
        // pkBizDate		
        this.pkBizDate.setRequired(true);
        this.pkBizDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkBizDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkFromDate		
        this.pkFromDate.setRequired(true);
        this.pkFromDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkFromDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // pkToDate		
        this.pkToDate.setRequired(true);
        this.pkToDate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    pkToDate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtDays		
        this.txtDays.setEnabled(false);
        // txtPlace		
        this.txtPlace.setRequired(true);
        // txtTransport		
        this.txtTransport.setRequired(true);
        // txtAmount		
        this.txtAmount.setRequired(true);		
        this.txtAmount.setDataType(1);		
        this.txtAmount.setPrecision(2);
        // contApplyBizDate		
        this.contApplyBizDate.setBoundLabelText(resHelper.getString("contApplyBizDate.boundLabelText"));		
        this.contApplyBizDate.setBoundLabelLength(100);		
        this.contApplyBizDate.setBoundLabelUnderline(true);
        // contApplyFromDate		
        this.contApplyFromDate.setBoundLabelText(resHelper.getString("contApplyFromDate.boundLabelText"));		
        this.contApplyFromDate.setBoundLabelLength(100);		
        this.contApplyFromDate.setBoundLabelUnderline(true);
        // contApplyToDate		
        this.contApplyToDate.setBoundLabelText(resHelper.getString("contApplyToDate.boundLabelText"));		
        this.contApplyToDate.setBoundLabelLength(100);		
        this.contApplyToDate.setBoundLabelUnderline(true);
        // contApplyDays		
        this.contApplyDays.setBoundLabelText(resHelper.getString("contApplyDays.boundLabelText"));		
        this.contApplyDays.setBoundLabelLength(100);		
        this.contApplyDays.setBoundLabelUnderline(true);
        // contApplyPlace		
        this.contApplyPlace.setBoundLabelText(resHelper.getString("contApplyPlace.boundLabelText"));		
        this.contApplyPlace.setBoundLabelUnderline(true);		
        this.contApplyPlace.setBoundLabelLength(100);
        // contApplyTransport		
        this.contApplyTransport.setBoundLabelText(resHelper.getString("contApplyTransport.boundLabelText"));		
        this.contApplyTransport.setBoundLabelLength(100);		
        this.contApplyTransport.setBoundLabelUnderline(true);
        // contApplyAmount		
        this.contApplyAmount.setBoundLabelText(resHelper.getString("contApplyAmount.boundLabelText"));		
        this.contApplyAmount.setBoundLabelUnderline(true);		
        this.contApplyAmount.setBoundLabelLength(100);
        // contApplyPurpose		
        this.contApplyPurpose.setBoundLabelText(resHelper.getString("contApplyPurpose.boundLabelText"));		
        this.contApplyPurpose.setBoundLabelLength(100);		
        this.contApplyPurpose.setBoundLabelUnderline(true);
        // contApplyDept		
        this.contApplyDept.setBoundLabelText(resHelper.getString("contApplyDept.boundLabelText"));		
        this.contApplyDept.setBoundLabelLength(100);		
        this.contApplyDept.setBoundLabelUnderline(true);
        // contApplyPerson		
        this.contApplyPerson.setBoundLabelText(resHelper.getString("contApplyPerson.boundLabelText"));		
        this.contApplyPerson.setBoundLabelLength(100);		
        this.contApplyPerson.setBoundLabelUnderline(true);
        // pkApplyBizDate		
        this.pkApplyBizDate.setEnabled(false);
        // pkApplyFromDate		
        this.pkApplyFromDate.setEnabled(false);
        // pkApplyToDate		
        this.pkApplyToDate.setEnabled(false);
        // txtApplyDays		
        this.txtApplyDays.setEnabled(false);
        // txtApplyPlace		
        this.txtApplyPlace.setEnabled(false);
        // txtApplyTransport		
        this.txtApplyTransport.setEnabled(false);
        // txtApplyAmount		
        this.txtApplyAmount.setEnabled(false);		
        this.txtApplyAmount.setPrecision(2);		
        this.txtApplyAmount.setDataType(1);
        // kDScrollPane2
        // txtApplyPurpose		
        this.txtApplyPurpose.setMaxLength(2000);		
        this.txtApplyPurpose.setEnabled(false);
        // prmtApplyDept		
        this.prmtApplyDept.setDisplayFormat("$name$");		
        this.prmtApplyDept.setEditFormat("$number");		
        this.prmtApplyDept.setDefaultF7UIName("com.kingdee.eas.basedata.org.client.f7.AdminF7");		
        this.prmtApplyDept.setCommitFormat("$number$");		
        this.prmtApplyDept.setRequired(true);		
        this.prmtApplyDept.setEnabled(false);
        // prmtApplyPerson		
        this.prmtApplyPerson.setDisplayFormat("$name$");		
        this.prmtApplyPerson.setEditFormat("$number$");		
        this.prmtApplyPerson.setCommitFormat("$number$");		
        this.prmtApplyPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtApplyPerson.setRequired(true);		
        this.prmtApplyPerson.setEnabled(false);
        // prmtDept		
        this.prmtDept.setDisplayFormat("$name$");		
        this.prmtDept.setEditFormat("$number");		
        this.prmtDept.setDefaultF7UIName("com.kingdee.eas.basedata.org.client.f7.AdminF7");		
        this.prmtDept.setCommitFormat("$number$");		
        this.prmtDept.setRequired(true);
        // prmtPerson		
        this.prmtPerson.setDisplayFormat("$name$");		
        this.prmtPerson.setEditFormat("$number$");		
        this.prmtPerson.setCommitFormat("$number$");		
        this.prmtPerson.setQueryInfo("com.kingdee.eas.basedata.person.app.PersonQuery");		
        this.prmtPerson.setRequired(true);
        // prmtContractType		
        this.prmtContractType.setVisible(true);		
        this.prmtContractType.setEditable(true);		
        this.prmtContractType.setDisplayFormat("$number$ $name$");		
        this.prmtContractType.setEditFormat("$number$");		
        this.prmtContractType.setCommitFormat("$number$");		
        this.prmtContractType.setRequired(true);		
        this.prmtContractType.setDefaultF7UIName("com.kingdee.eas.fdc.basedata.client.ContractTypeF7UI");		
        this.prmtContractType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ContractTypeQuery");		
        this.prmtContractType.setEnabled(false);
        this.prmtContractType.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    prmtcontractType_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.prmtContractType.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    prmtcontractType_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // prmtContractWFType		
        this.prmtContractWFType.setQueryInfo("com.kingdee.eas.fdc.contract.app.ContractWFQuery");		
        this.prmtContractWFType.setCommitFormat("$longNumber$");		
        this.prmtContractWFType.setEditFormat("$longNumber$");		
        this.prmtContractWFType.setDisplayFormat("$name$");		
        this.prmtContractWFType.setRequired(true);
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
        contCreator.setBounds(new Rectangle(374, 519, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(374, 519, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCreateTime.setBounds(new Rectangle(22, 519, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(22, 519, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(726, 10, 270, 19));
        this.add(contNumber, new KDLayout.Constraints(726, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(374, 540, 270, 19));
        this.add(contAuditor, new KDLayout.Constraints(374, 540, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditTime.setBounds(new Rectangle(22, 540, 270, 19));
        this.add(contAuditTime, new KDLayout.Constraints(22, 540, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPurpose.setBounds(new Rectangle(22, 120, 974, 55));
        this.add(contPurpose, new KDLayout.Constraints(22, 120, 974, 55, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contAttachment.setBounds(new Rectangle(22, 183, 971, 131));
        this.add(contAttachment, new KDLayout.Constraints(22, 183, 971, 131, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contProj.setBounds(new Rectangle(374, 10, 270, 19));
        this.add(contProj, new KDLayout.Constraints(374, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contOrg.setBounds(new Rectangle(22, 10, 270, 19));
        this.add(contOrg, new KDLayout.Constraints(22, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(22, 31, 270, 19));
        this.add(contBizDate, new KDLayout.Constraints(22, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFromDate.setBounds(new Rectangle(22, 52, 270, 19));
        this.add(contFromDate, new KDLayout.Constraints(22, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contToDate.setBounds(new Rectangle(374, 52, 270, 19));
        this.add(contToDate, new KDLayout.Constraints(374, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDays.setBounds(new Rectangle(726, 52, 270, 19));
        this.add(contDays, new KDLayout.Constraints(726, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPlace.setBounds(new Rectangle(374, 74, 270, 19));
        this.add(contPlace, new KDLayout.Constraints(374, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTransport.setBounds(new Rectangle(726, 74, 270, 19));
        this.add(contTransport, new KDLayout.Constraints(726, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAmount.setBounds(new Rectangle(22, 74, 270, 19));
        this.add(contAmount, new KDLayout.Constraints(22, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTripApply.setBounds(new Rectangle(23, 317, 971, 198));
        this.add(contTripApply, new KDLayout.Constraints(23, 317, 971, 198, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contDept.setBounds(new Rectangle(374, 31, 270, 19));
        this.add(contDept, new KDLayout.Constraints(374, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPerson.setBounds(new Rectangle(726, 31, 270, 19));
        this.add(contPerson, new KDLayout.Constraints(726, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractType.setBounds(new Rectangle(22, 97, 270, 19));
        this.add(contContractType, new KDLayout.Constraints(22, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractWFType.setBounds(new Rectangle(374, 97, 270, 19));
        this.add(contContractWFType, new KDLayout.Constraints(374, 97, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //contPurpose
        contPurpose.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtPurpose, null);
        //contAttachment
contAttachment.getContentPane().setLayout(new BorderLayout(0, 0));        contAttachment.getContentPane().add(tblAttachement, BorderLayout.CENTER);
        //contProj
        contProj.setBoundEditor(txtProj);
        //contOrg
        contOrg.setBoundEditor(txtOrg);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contFromDate
        contFromDate.setBoundEditor(pkFromDate);
        //contToDate
        contToDate.setBoundEditor(pkToDate);
        //contDays
        contDays.setBoundEditor(txtDays);
        //contPlace
        contPlace.setBoundEditor(txtPlace);
        //contTransport
        contTransport.setBoundEditor(txtTransport);
        //contAmount
        contAmount.setBoundEditor(txtAmount);
        //contTripApply
        contTripApply.getContentPane().setLayout(new KDLayout());
        contTripApply.getContentPane().putClientProperty("OriginalBounds", new Rectangle(23, 317, 971, 198));        contApplyBizDate.setBounds(new Rectangle(5, 6, 270, 19));
        contTripApply.getContentPane().add(contApplyBizDate, new KDLayout.Constraints(5, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplyFromDate.setBounds(new Rectangle(5, 27, 270, 19));
        contTripApply.getContentPane().add(contApplyFromDate, new KDLayout.Constraints(5, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplyToDate.setBounds(new Rectangle(347, 27, 270, 19));
        contTripApply.getContentPane().add(contApplyToDate, new KDLayout.Constraints(347, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplyDays.setBounds(new Rectangle(689, 27, 270, 19));
        contTripApply.getContentPane().add(contApplyDays, new KDLayout.Constraints(689, 27, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contApplyPlace.setBounds(new Rectangle(347, 49, 270, 19));
        contTripApply.getContentPane().add(contApplyPlace, new KDLayout.Constraints(347, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplyTransport.setBounds(new Rectangle(689, 49, 270, 19));
        contTripApply.getContentPane().add(contApplyTransport, new KDLayout.Constraints(689, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contApplyAmount.setBounds(new Rectangle(5, 49, 270, 19));
        contTripApply.getContentPane().add(contApplyAmount, new KDLayout.Constraints(5, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplyPurpose.setBounds(new Rectangle(5, 72, 953, 85));
        contTripApply.getContentPane().add(contApplyPurpose, new KDLayout.Constraints(5, 72, 953, 85, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contApplyDept.setBounds(new Rectangle(347, 6, 270, 19));
        contTripApply.getContentPane().add(contApplyDept, new KDLayout.Constraints(347, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contApplyPerson.setBounds(new Rectangle(689, 6, 270, 19));
        contTripApply.getContentPane().add(contApplyPerson, new KDLayout.Constraints(689, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contApplyBizDate
        contApplyBizDate.setBoundEditor(pkApplyBizDate);
        //contApplyFromDate
        contApplyFromDate.setBoundEditor(pkApplyFromDate);
        //contApplyToDate
        contApplyToDate.setBoundEditor(pkApplyToDate);
        //contApplyDays
        contApplyDays.setBoundEditor(txtApplyDays);
        //contApplyPlace
        contApplyPlace.setBoundEditor(txtApplyPlace);
        //contApplyTransport
        contApplyTransport.setBoundEditor(txtApplyTransport);
        //contApplyAmount
        contApplyAmount.setBoundEditor(txtApplyAmount);
        //contApplyPurpose
        contApplyPurpose.setBoundEditor(kDScrollPane2);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(txtApplyPurpose, null);
        //contApplyDept
        contApplyDept.setBoundEditor(prmtApplyDept);
        //contApplyPerson
        contApplyPerson.setBoundEditor(prmtApplyPerson);
        //contDept
        contDept.setBoundEditor(prmtDept);
        //contPerson
        contPerson.setBoundEditor(prmtPerson);
        //contContractType
        contContractType.setBoundEditor(prmtContractType);
        //contContractWFType
        contContractWFType.setBoundEditor(prmtContractWFType);

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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditTime, "value");
		dataBinder.registerBinding("purpose", String.class, this.txtPurpose, "text");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("fromDate", java.util.Date.class, this.pkFromDate, "value");
		dataBinder.registerBinding("toDate", java.util.Date.class, this.pkToDate, "value");
		dataBinder.registerBinding("day", int.class, this.txtDays, "value");
		dataBinder.registerBinding("place", String.class, this.txtPlace, "text");
		dataBinder.registerBinding("transport", String.class, this.txtTransport, "text");
		dataBinder.registerBinding("amount", java.math.BigDecimal.class, this.txtAmount, "value");
		dataBinder.registerBinding("tripApply.bizDate", java.util.Date.class, this.pkApplyBizDate, "value");
		dataBinder.registerBinding("tripApply.fromDate", java.util.Date.class, this.pkApplyFromDate, "value");
		dataBinder.registerBinding("tripApply.toDate", java.util.Date.class, this.pkApplyToDate, "value");
		dataBinder.registerBinding("tripApply.day", int.class, this.txtApplyDays, "value");
		dataBinder.registerBinding("tripApply.place", String.class, this.txtApplyPlace, "text");
		dataBinder.registerBinding("tripApply.transport", String.class, this.txtApplyTransport, "text");
		dataBinder.registerBinding("tripApply.amount", java.math.BigDecimal.class, this.txtApplyAmount, "value");
		dataBinder.registerBinding("tripApply.purpose", String.class, this.txtApplyPurpose, "text");
		dataBinder.registerBinding("tripApply.dept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtApplyDept, "data");
		dataBinder.registerBinding("tripApply.person", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtApplyPerson, "data");
		dataBinder.registerBinding("dept", com.kingdee.eas.basedata.org.AdminOrgUnitInfo.class, this.prmtDept, "data");
		dataBinder.registerBinding("person", com.kingdee.eas.basedata.person.PersonInfo.class, this.prmtPerson, "data");
		dataBinder.registerBinding("tripApply.contractType", com.kingdee.eas.fdc.basedata.ContractTypeInfo.class, this.prmtContractType, "data");
		dataBinder.registerBinding("contractWFType", com.kingdee.eas.fdc.contract.ContractWFTypeInfo.class, this.prmtContractWFType, "data");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.contract.app.TripCostEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.contract.TripCostInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("purpose", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fromDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("toDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("day", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("place", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("transport", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.fromDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.toDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.day", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.place", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.transport", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.amount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.purpose", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.dept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.person", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("person", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tripApply.contractType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractWFType", ValidateHelper.ON_SAVE);    		
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
     * output tblAttachement_tableClicked method
     */
    protected void tblAttachement_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output pkBizDate_dataChanged method
     */
    protected void pkBizDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkFromDate_dataChanged method
     */
    protected void pkFromDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output pkToDate_dataChanged method
     */
    protected void pkToDate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtcontractType_dataChanged method
     */
    protected void prmtcontractType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output prmtcontractType_willShow method
     */
    protected void prmtcontractType_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("creator.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("creator.id"));
        	sic.add(new SelectorItemInfo("creator.number"));
        	sic.add(new SelectorItemInfo("creator.name"));
		}
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("number"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("auditor.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("auditor.id"));
        	sic.add(new SelectorItemInfo("auditor.number"));
        	sic.add(new SelectorItemInfo("auditor.name"));
		}
        sic.add(new SelectorItemInfo("auditTime"));
        sic.add(new SelectorItemInfo("purpose"));
        sic.add(new SelectorItemInfo("bizDate"));
        sic.add(new SelectorItemInfo("fromDate"));
        sic.add(new SelectorItemInfo("toDate"));
        sic.add(new SelectorItemInfo("day"));
        sic.add(new SelectorItemInfo("place"));
        sic.add(new SelectorItemInfo("transport"));
        sic.add(new SelectorItemInfo("amount"));
        sic.add(new SelectorItemInfo("tripApply.bizDate"));
        sic.add(new SelectorItemInfo("tripApply.fromDate"));
        sic.add(new SelectorItemInfo("tripApply.toDate"));
        sic.add(new SelectorItemInfo("tripApply.day"));
        sic.add(new SelectorItemInfo("tripApply.place"));
        sic.add(new SelectorItemInfo("tripApply.transport"));
        sic.add(new SelectorItemInfo("tripApply.amount"));
        sic.add(new SelectorItemInfo("tripApply.purpose"));
        sic.add(new SelectorItemInfo("tripApply.dept"));
        sic.add(new SelectorItemInfo("tripApply.person"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("dept.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("dept.id"));
        	sic.add(new SelectorItemInfo("dept.number"));
        	sic.add(new SelectorItemInfo("dept.name"));
		}
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("person.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("person.id"));
        	sic.add(new SelectorItemInfo("person.number"));
        	sic.add(new SelectorItemInfo("person.name"));
		}
        sic.add(new SelectorItemInfo("tripApply.contractType"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("contractWFType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("contractWFType.id"));
        	sic.add(new SelectorItemInfo("contractWFType.number"));
        	sic.add(new SelectorItemInfo("contractWFType.name"));
        	sic.add(new SelectorItemInfo("contractWFType.longNumber"));
		}
        return sic;
    }        
    	

    /**
     * output actionSubmit_actionPerformed method
     */
    public void actionSubmit_actionPerformed(ActionEvent e) throws Exception
    {
        super.actionSubmit_actionPerformed(e);
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

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.contract.client", "TripCostEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}