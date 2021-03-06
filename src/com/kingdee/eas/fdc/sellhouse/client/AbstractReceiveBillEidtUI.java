/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

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
public abstract class AbstractReceiveBillEidtUI extends com.kingdee.eas.fdc.basedata.client.FDCBillEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractReceiveBillEidtUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceiveAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contNumber;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditor;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contBillDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellProject;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSellOrder;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSalesman;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCheque;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contReceiptNumber;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChoose;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcTenancyContract;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer2;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer lcAccessorialResource;
    protected com.kingdee.bos.ctrl.swing.KDCheckBox cbIsSettlement;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnDelete;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer3;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer4;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contGathering;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSinPurchase;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSinObligate;
    protected com.kingdee.bos.ctrl.swing.KDTabbedPane kDTabbedPane1;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Purchase;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtReceiveAmount;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Currency;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtNumber;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Customer;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Auditor;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkAuditTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkBillDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellProject;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SellOrder;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Salesman;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDes;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Cheque;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtReceiptNumber;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboBelongSys;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7TenancyContract;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Room;
    protected com.kingdee.bos.ctrl.swing.KDComboBox ComboGatheringOjbect;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Accessorial;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtInvoice;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvoiceAmount;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboGathering;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SinPurchase;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7SinObligate;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel2;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable kDTable1;
    protected com.kingdee.bos.ctrl.kdf.table.KDTable tblRecord;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRec;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnViewBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemViewBill;
    protected com.kingdee.eas.framework.CoreBillBaseInfo editData = null;
    protected ActionRec actionRec = null;
    protected ActionViewBill actionViewBill = null;
    /**
     * output class constructor
     */
    public AbstractReceiveBillEidtUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractReceiveBillEidtUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionRec
        this.actionRec = new ActionRec(this);
        getActionManager().registerAction("actionRec", actionRec);
         this.actionRec.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionViewBill
        this.actionViewBill = new ActionViewBill(this);
        getActionManager().registerAction("actionViewBill", actionViewBill);
         this.actionViewBill.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contPurchase = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReceiveAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditor = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contAuditTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contBillDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellProject = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSellOrder = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSalesman = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCheque = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contReceiptNumber = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.btnChoose = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcTenancyContract = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer2 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.lcAccessorialResource = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.cbIsSettlement = new com.kingdee.bos.ctrl.swing.KDCheckBox();
        this.btnDelete = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.kDLabelContainer3 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDLabelContainer4 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contGathering = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSinPurchase = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contSinObligate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDTabbedPane1 = new com.kingdee.bos.ctrl.swing.KDTabbedPane();
        this.f7Purchase = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtReceiveAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.f7Currency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Customer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Auditor = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkAuditTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkBillDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellProject = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SellOrder = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Salesman = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtDes = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.f7Cheque = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtReceiptNumber = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboBelongSys = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7TenancyContract = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7Room = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.ComboGatheringOjbect = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7Accessorial = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.txtInvoice = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtInvoiceAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.comboGathering = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.f7SinPurchase = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.f7SinObligate = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDPanel2 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDTable1 = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.tblRecord = new com.kingdee.bos.ctrl.kdf.table.KDTable();
        this.btnRec = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnViewBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemViewBill = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.contPurchase.setName("contPurchase");
        this.contReceiveAmount.setName("contReceiveAmount");
        this.contCurrency.setName("contCurrency");
        this.contNumber.setName("contNumber");
        this.contCustomer.setName("contCustomer");
        this.contAuditor.setName("contAuditor");
        this.contAuditTime.setName("contAuditTime");
        this.contCreateTime.setName("contCreateTime");
        this.contBillDate.setName("contBillDate");
        this.contCreator.setName("contCreator");
        this.contSellProject.setName("contSellProject");
        this.contSellOrder.setName("contSellOrder");
        this.contSalesman.setName("contSalesman");
        this.contDes.setName("contDes");
        this.contCheque.setName("contCheque");
        this.contReceiptNumber.setName("contReceiptNumber");
        this.btnChoose.setName("btnChoose");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.lcTenancyContract.setName("lcTenancyContract");
        this.lcRoom.setName("lcRoom");
        this.kDLabelContainer2.setName("kDLabelContainer2");
        this.lcAccessorialResource.setName("lcAccessorialResource");
        this.cbIsSettlement.setName("cbIsSettlement");
        this.btnDelete.setName("btnDelete");
        this.kDLabelContainer3.setName("kDLabelContainer3");
        this.kDLabelContainer4.setName("kDLabelContainer4");
        this.contGathering.setName("contGathering");
        this.contSinPurchase.setName("contSinPurchase");
        this.contSinObligate.setName("contSinObligate");
        this.kDTabbedPane1.setName("kDTabbedPane1");
        this.f7Purchase.setName("f7Purchase");
        this.txtReceiveAmount.setName("txtReceiveAmount");
        this.f7Currency.setName("f7Currency");
        this.txtNumber.setName("txtNumber");
        this.f7Customer.setName("f7Customer");
        this.f7Auditor.setName("f7Auditor");
        this.pkAuditTime.setName("pkAuditTime");
        this.pkCreateTime.setName("pkCreateTime");
        this.pkBillDate.setName("pkBillDate");
        this.f7Creator.setName("f7Creator");
        this.f7SellProject.setName("f7SellProject");
        this.f7SellOrder.setName("f7SellOrder");
        this.f7Salesman.setName("f7Salesman");
        this.txtDes.setName("txtDes");
        this.f7Cheque.setName("f7Cheque");
        this.txtReceiptNumber.setName("txtReceiptNumber");
        this.comboBelongSys.setName("comboBelongSys");
        this.f7TenancyContract.setName("f7TenancyContract");
        this.f7Room.setName("f7Room");
        this.ComboGatheringOjbect.setName("ComboGatheringOjbect");
        this.f7Accessorial.setName("f7Accessorial");
        this.txtInvoice.setName("txtInvoice");
        this.txtInvoiceAmount.setName("txtInvoiceAmount");
        this.comboGathering.setName("comboGathering");
        this.f7SinPurchase.setName("f7SinPurchase");
        this.f7SinObligate.setName("f7SinObligate");
        this.kDPanel1.setName("kDPanel1");
        this.kDPanel2.setName("kDPanel2");
        this.kDTable1.setName("kDTable1");
        this.tblRecord.setName("tblRecord");
        this.btnRec.setName("btnRec");
        this.btnViewBill.setName("btnViewBill");
        this.menuItemViewBill.setName("menuItemViewBill");
        // CoreUI		
        this.setPreferredSize(new Dimension(960,500));		
        this.btnVoucher.setVisible(true);		
        this.MenuItemVoucher.setVisible(true);		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // contPurchase		
        this.contPurchase.setBoundLabelText(resHelper.getString("contPurchase.boundLabelText"));		
        this.contPurchase.setBoundLabelLength(100);		
        this.contPurchase.setBoundLabelUnderline(true);
        // contReceiveAmount		
        this.contReceiveAmount.setBoundLabelText(resHelper.getString("contReceiveAmount.boundLabelText"));		
        this.contReceiveAmount.setBoundLabelLength(100);		
        this.contReceiveAmount.setBoundLabelUnderline(true);
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // contNumber		
        this.contNumber.setBoundLabelText(resHelper.getString("contNumber.boundLabelText"));		
        this.contNumber.setBoundLabelLength(100);		
        this.contNumber.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contAuditor		
        this.contAuditor.setBoundLabelText(resHelper.getString("contAuditor.boundLabelText"));		
        this.contAuditor.setBoundLabelLength(100);		
        this.contAuditor.setBoundLabelUnderline(true);
        // contAuditTime		
        this.contAuditTime.setBoundLabelText(resHelper.getString("contAuditTime.boundLabelText"));		
        this.contAuditTime.setBoundLabelLength(100);		
        this.contAuditTime.setBoundLabelUnderline(true);
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contBillDate		
        this.contBillDate.setBoundLabelText(resHelper.getString("contBillDate.boundLabelText"));		
        this.contBillDate.setBoundLabelLength(100);		
        this.contBillDate.setBoundLabelUnderline(true);
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // contSellProject		
        this.contSellProject.setBoundLabelText(resHelper.getString("contSellProject.boundLabelText"));		
        this.contSellProject.setBoundLabelUnderline(true);		
        this.contSellProject.setBoundLabelLength(100);
        // contSellOrder		
        this.contSellOrder.setBoundLabelText(resHelper.getString("contSellOrder.boundLabelText"));		
        this.contSellOrder.setBoundLabelLength(100);		
        this.contSellOrder.setBoundLabelUnderline(true);
        // contSalesman		
        this.contSalesman.setBoundLabelText(resHelper.getString("contSalesman.boundLabelText"));		
        this.contSalesman.setBoundLabelLength(100);		
        this.contSalesman.setBoundLabelUnderline(true);
        // contDes		
        this.contDes.setBoundLabelText(resHelper.getString("contDes.boundLabelText"));		
        this.contDes.setBoundLabelUnderline(true);		
        this.contDes.setBoundLabelLength(100);
        // contCheque		
        this.contCheque.setBoundLabelText(resHelper.getString("contCheque.boundLabelText"));		
        this.contCheque.setBoundLabelUnderline(true);		
        this.contCheque.setBoundLabelLength(100);
        // contReceiptNumber		
        this.contReceiptNumber.setBoundLabelText(resHelper.getString("contReceiptNumber.boundLabelText"));		
        this.contReceiptNumber.setBoundLabelLength(100);		
        this.contReceiptNumber.setBoundLabelUnderline(true);
        // btnChoose		
        this.btnChoose.setText(resHelper.getString("btnChoose.text"));
        this.btnChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnChoose_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(100);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // lcTenancyContract		
        this.lcTenancyContract.setBoundLabelText(resHelper.getString("lcTenancyContract.boundLabelText"));		
        this.lcTenancyContract.setBoundLabelLength(100);		
        this.lcTenancyContract.setBoundLabelUnderline(true);
        // lcRoom		
        this.lcRoom.setBoundLabelText(resHelper.getString("lcRoom.boundLabelText"));		
        this.lcRoom.setBoundLabelLength(100);		
        this.lcRoom.setBoundLabelUnderline(true);
        // kDLabelContainer2		
        this.kDLabelContainer2.setBoundLabelText(resHelper.getString("kDLabelContainer2.boundLabelText"));		
        this.kDLabelContainer2.setBoundLabelLength(100);		
        this.kDLabelContainer2.setBoundLabelUnderline(true);
        // lcAccessorialResource		
        this.lcAccessorialResource.setBoundLabelText(resHelper.getString("lcAccessorialResource.boundLabelText"));		
        this.lcAccessorialResource.setBoundLabelLength(100);		
        this.lcAccessorialResource.setBoundLabelUnderline(true);
        // cbIsSettlement		
        this.cbIsSettlement.setText(resHelper.getString("cbIsSettlement.text"));		
        this.cbIsSettlement.setEnabled(false);		
        this.cbIsSettlement.setBackground(new java.awt.Color(255,0,0));		
        this.cbIsSettlement.setForeground(new java.awt.Color(255,0,0));
        // btnDelete		
        this.btnDelete.setText(resHelper.getString("btnDelete.text"));
        this.btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    btnDelete_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        // kDLabelContainer3		
        this.kDLabelContainer3.setBoundLabelText(resHelper.getString("kDLabelContainer3.boundLabelText"));		
        this.kDLabelContainer3.setBoundLabelLength(100);		
        this.kDLabelContainer3.setBoundLabelUnderline(true);
        // kDLabelContainer4		
        this.kDLabelContainer4.setBoundLabelText(resHelper.getString("kDLabelContainer4.boundLabelText"));		
        this.kDLabelContainer4.setBoundLabelLength(100);		
        this.kDLabelContainer4.setBoundLabelUnderline(true);
        // contGathering		
        this.contGathering.setBoundLabelText(resHelper.getString("contGathering.boundLabelText"));		
        this.contGathering.setBoundLabelLength(100);		
        this.contGathering.setBoundLabelUnderline(true);
        // contSinPurchase		
        this.contSinPurchase.setBoundLabelText(resHelper.getString("contSinPurchase.boundLabelText"));		
        this.contSinPurchase.setBoundLabelLength(100);		
        this.contSinPurchase.setBoundLabelUnderline(true);
        // contSinObligate		
        this.contSinObligate.setBoundLabelText(resHelper.getString("contSinObligate.boundLabelText"));		
        this.contSinObligate.setBoundLabelLength(100);		
        this.contSinObligate.setBoundLabelUnderline(true);
        // kDTabbedPane1
        // f7Purchase		
        this.f7Purchase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.PurchaseQuery");		
        this.f7Purchase.setDisplayFormat("$number$");		
        this.f7Purchase.setEditFormat("$number$");		
        this.f7Purchase.setCommitFormat("$number$");
        this.f7Purchase.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Purchase_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // txtReceiveAmount		
        this.txtReceiveAmount.setDataType(1);		
        this.txtReceiveAmount.setEnabled(false);
        // f7Currency		
        this.f7Currency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.f7Currency.setDisplayFormat("$name$");		
        this.f7Currency.setEditFormat("$number$");		
        this.f7Currency.setCommitFormat("$number$");
        // txtNumber
        // f7Customer		
        this.f7Customer.setDisplayFormat("$name$");		
        this.f7Customer.setEditFormat("$number$");		
        this.f7Customer.setQueryInfo("com.kingdee.eas.basedata.master.cssp.app.F7CustomerQuery");		
        this.f7Customer.setCommitFormat("$number$");		
        this.f7Customer.setEnabledMultiSelection(true);
        this.f7Customer.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Customer_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Auditor		
        this.f7Auditor.setEnabled(false);		
        this.f7Auditor.setDisplayFormat("$name$");
        // pkAuditTime		
        this.pkAuditTime.setEnabled(false);
        // pkCreateTime		
        this.pkCreateTime.setEnabled(false);
        // pkBillDate
        // f7Creator		
        this.f7Creator.setEnabled(false);		
        this.f7Creator.setDisplayFormat("$name$");
        // f7SellProject		
        this.f7SellProject.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellProjectQuery");		
        this.f7SellProject.setEnabled(false);
        this.f7SellProject.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SellProject_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7SellOrder		
        this.f7SellOrder.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SellOrderQuery");
        // f7Salesman		
        this.f7Salesman.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Salesman.setDisplayFormat("$name$");		
        this.f7Salesman.setEditFormat("$number$");		
        this.f7Salesman.setCommitFormat("$number$");
        // txtDes		
        this.txtDes.setMaxLength(80);
        // f7Cheque		
        this.f7Cheque.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.ChequeQuery");		
        this.f7Cheque.setCommitFormat("$number$");		
        this.f7Cheque.setDisplayFormat("$number$");		
        this.f7Cheque.setEditFormat("$number$");
        // txtReceiptNumber		
        this.txtReceiptNumber.setMaxLength(80);
        // comboBelongSys		
        this.comboBelongSys.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.basedata.MoneySysTypeEnum").toArray());
        this.comboBelongSys.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                beforeActionPerformed(e);
                try {
                    comboBelongSys_actionPerformed(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                    afterActionPerformed(e);
                }
            }
        });
        this.comboBelongSys.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboBelongSys_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7TenancyContract		
        this.f7TenancyContract.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenancyBillQuery");		
        this.f7TenancyContract.setDisplayFormat("$name$");		
        this.f7TenancyContract.setEditFormat("$number$");		
        this.f7TenancyContract.setCommitFormat("$number$");		
        this.f7TenancyContract.setRequired(true);
        this.f7TenancyContract.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7TenancyContract_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Room		
        this.f7Room.setRequired(true);
        this.f7Room.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7Room_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.f7Room.addCommitListener(new com.kingdee.bos.ctrl.swing.event.CommitListener() {
            public void willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) {
                try {
                    f7Room_willCommit(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.f7Room.addSelectorListener(new com.kingdee.bos.ctrl.swing.event.SelectorListener() {
            public void willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) {
                try {
                    f7Room_willShow(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // ComboGatheringOjbect		
        this.ComboGatheringOjbect.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.GatheringObjectEnum").toArray());		
        this.ComboGatheringOjbect.setRequired(true);
        this.ComboGatheringOjbect.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    ComboGatheringOjbect_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7Accessorial		
        this.f7Accessorial.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.TenAttachResourceEntryQuery");		
        this.f7Accessorial.setDisplayFormat("$attachLongNum$");		
        this.f7Accessorial.setCommitFormat("$attachLongNum$");		
        this.f7Accessorial.setEditFormat("$attachLongNum$");
        // txtInvoice		
        this.txtInvoice.setEnabled(false);
        // txtInvoiceAmount		
        this.txtInvoiceAmount.setEnabled(false);
        // comboGathering		
        this.comboGathering.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.GatheringEnum").toArray());
        this.comboGathering.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    comboGathering_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7SinPurchase		
        this.f7SinPurchase.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.SincerityPurchaseQuery");		
        this.f7SinPurchase.setCommitFormat("$number$");		
        this.f7SinPurchase.setEditFormat("$number$");		
        this.f7SinPurchase.setDisplayFormat("$number$");
        this.f7SinPurchase.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SinPurchase_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // f7SinObligate		
        this.f7SinObligate.setQueryInfo("com.kingdee.eas.fdc.tenancy.app.SincerObligateQuery");		
        this.f7SinObligate.setCommitFormat("$number$");		
        this.f7SinObligate.setEditFormat("$number$");		
        this.f7SinObligate.setDisplayFormat("$name$");
        this.f7SinObligate.addDataChangeListener(new com.kingdee.bos.ctrl.swing.event.DataChangeListener() {
            public void dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) {
                try {
                    f7SinObligate_dataChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // kDPanel1
        // kDPanel2
        // kDTable1
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol4\"><c:NumberFormat>#,##0.00</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:Protection locked=\"true\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>#,###</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>#,###</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>#,###</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:NumberFormat>#,###</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>#,###</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>#,###</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol16\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"moneyType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"settlementType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"settlementNumber\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"gatheringSubject\" t:width=\"160\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"gatheringAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"gatheringNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"paymentAccount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"gatheringAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"refundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"canRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"hasRefundmentAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"appAmount\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"derateAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"oppSubject\" t:width=\"150\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"counteractAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{moneyType}</t:Cell><t:Cell>$Resource{settlementType}</t:Cell><t:Cell>$Resource{settlementNumber}</t:Cell><t:Cell>$Resource{gatheringSubject}</t:Cell><t:Cell>$Resource{gatheringAccount}</t:Cell><t:Cell>$Resource{gatheringNumber}</t:Cell><t:Cell>$Resource{paymentAccount}</t:Cell><t:Cell>$Resource{gatheringAmount}</t:Cell><t:Cell>$Resource{refundmentAmount}</t:Cell><t:Cell>$Resource{canRefundmentAmount}</t:Cell><t:Cell>$Resource{hasRefundmentAmount}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{derateAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{oppSubject}</t:Cell><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{counteractAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.kDTable1.setFormatXml(resHelper.translateString("kDTable1",kDTable1StrXML));
        this.kDTable1.addKDTActiveCellListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellListener() {
            public void activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) {
                try {
                    kDTable1_activeCellChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.kDTable1.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
            public void editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) {
                try {
                    kDTable1_editStopped(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });

        

        // tblRecord
		String tblRecordStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"recordType\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"content\" t:width=\"240\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operatingUser\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"operatingDate\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol4\" /><t:Column t:key=\"description\" t:width=\"240\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{recordType}</t:Cell><t:Cell>$Resource{content}</t:Cell><t:Cell>$Resource{operatingUser}</t:Cell><t:Cell>$Resource{operatingDate}</t:Cell><t:Cell>$Resource{description}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblRecord.setFormatXml(resHelper.translateString("tblRecord",tblRecordStrXML));

        

        // btnRec
        this.btnRec.setAction((IItemAction)ActionProxyFactory.getProxy(actionRec, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRec.setText(resHelper.getString("btnRec.text"));		
        this.btnRec.setToolTipText(resHelper.getString("btnRec.toolTipText"));		
        this.btnRec.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_recieversetting"));
        // btnViewBill
        this.btnViewBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnViewBill.setText(resHelper.getString("btnViewBill.text"));		
        this.btnViewBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_demandcollateresult"));
        // menuItemViewBill
        this.menuItemViewBill.setAction((IItemAction)ActionProxyFactory.getProxy(actionViewBill, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemViewBill.setText(resHelper.getString("menuItemViewBill.text"));		
        this.menuItemViewBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_demandcollateresult"));
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
        menuBiz.add(menuItemViewBill);
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
        this.toolBar.add(btnViewBill);
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
	        getActionManager().registerUIState(STATUS_ADDNEW, this.btnChoose, ActionStateConst.ENABLED);
	        getActionManager().registerUIState(STATUS_EDIT, this.btnChoose, ActionStateConst.ENABLED);					 	        		
	        getActionManager().registerUIState(STATUS_VIEW, this.btnChoose, ActionStateConst.DISABLED);					 	        		
	        getActionManager().registerUIState(STATUS_FINDVIEW, this.btnChoose, ActionStateConst.DISABLED);		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ReceiveBillEidtUIHandler";
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
		            this.btnChoose.setEnabled(true);
        } else if (STATUS_EDIT.equals(this.oprtState)) {
		            this.btnChoose.setEnabled(true);
        } else if (STATUS_VIEW.equals(this.oprtState)) {
		            this.btnChoose.setEnabled(false);
        } else if (STATUS_FINDVIEW.equals(this.oprtState)) {
		            this.btnChoose.setEnabled(false);
        }
    }

    /**
     * output btnChoose_actionPerformed method
     */
    protected void btnChoose_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output btnDelete_actionPerformed method
     */
    protected void btnDelete_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output f7Purchase_dataChanged method
     */
    protected void f7Purchase_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7Customer_dataChanged method
     */
    protected void f7Customer_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output f7SellProject_dataChanged method
     */
    protected void f7SellProject_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output comboBelongSys_actionPerformed method
     */
    protected void comboBelongSys_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output comboBelongSys_itemStateChanged method
     */
    protected void comboBelongSys_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output f7TenancyContract_dataChanged method
     */
    protected void f7TenancyContract_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Room_dataChanged method
     */
    protected void f7Room_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7Room_willCommit method
     */
    protected void f7Room_willCommit(com.kingdee.bos.ctrl.swing.event.CommitEvent e) throws Exception
    {
    }

    /**
     * output f7Room_willShow method
     */
    protected void f7Room_willShow(com.kingdee.bos.ctrl.swing.event.SelectorEvent e) throws Exception
    {
    }

    /**
     * output ComboGatheringOjbect_itemStateChanged method
     */
    protected void ComboGatheringOjbect_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output comboGathering_itemStateChanged method
     */
    protected void comboGathering_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    }

    /**
     * output f7SinPurchase_dataChanged method
     */
    protected void f7SinPurchase_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output f7SinObligate_dataChanged method
     */
    protected void f7SinObligate_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output kDTable1_activeCellChanged method
     */
    protected void kDTable1_activeCellChanged(com.kingdee.bos.ctrl.kdf.table.event.KDTActiveCellEvent e) throws Exception
    {
    }

    /**
     * output kDTable1_editStopped method
     */
    protected void kDTable1_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
    {
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
     * output actionRec_actionPerformed method
     */
    public void actionRec_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionViewBill_actionPerformed method
     */
    public void actionViewBill_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionRec(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRec() {
    	return false;
    }
	public RequestContext prepareActionViewBill(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionViewBill() {
    	return false;
    }

    /**
     * output ActionRec class
     */     
    protected class ActionRec extends ItemAction {     
    
        public ActionRec()
        {
            this(null);
        }

        public ActionRec(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRec.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRec.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRec.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillEidtUI.this, "ActionRec", "actionRec_actionPerformed", e);
        }
    }

    /**
     * output ActionViewBill class
     */     
    protected class ActionViewBill extends ItemAction {     
    
        public ActionViewBill()
        {
            this(null);
        }

        public ActionViewBill(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionViewBill.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBill.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionViewBill.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractReceiveBillEidtUI.this, "ActionViewBill", "actionViewBill_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ReceiveBillEidtUI");
    }




}