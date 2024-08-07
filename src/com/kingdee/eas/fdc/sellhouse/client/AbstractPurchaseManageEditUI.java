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
public abstract class AbstractPurchaseManageEditUI extends com.kingdee.eas.fdc.sellhouse.client.BaseTransactionEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPurchaseManageEditUI.class);
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane2;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contSaleRate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChangeState;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtSaleRate;
    protected com.kingdee.bos.ctrl.swing.KDComboBox cbChangeState;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDes;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanSignDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contActualArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPreArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanningCompensate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPurAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCashSalesCompensate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contPlanningArea;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRecommend;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractTotalAmountNT;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealTotalAmountNT;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealBuildPriceNT;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractBuildPriceNT;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contContractRoomPriceNT;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDealRoomPriceNT;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contOneQd;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contTwoQd;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtDes;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkPlanSignDate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtActualArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPreArea;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlanningCompensate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPurAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtCashSalesCompensate;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtPlanningArea;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtRecommend;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractTotalAmountNT;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealTotalAmountNT;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealBuildPriceNT;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractBuildPriceNT;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtContractRoomPriceNT;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtDealRoomPriceNT;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtOneQd;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtTwoQd;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRelatePrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRelateSign;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRelatePrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRelateSign;
    protected com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo editData = null;
    protected ActionRelateSign actionRelateSign = null;
    protected ActionRelatePrePurchase actionRelatePrePurchase = null;
    /**
     * output class constructor
     */
    public AbstractPurchaseManageEditUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractPurchaseManageEditUI.class.getName());
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
        //actionRelateSign
        this.actionRelateSign = new ActionRelateSign(this);
        getActionManager().registerAction("actionRelateSign", actionRelateSign);
         this.actionRelateSign.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRelatePrePurchase
        this.actionRelatePrePurchase = new ActionRelatePrePurchase(this);
        getActionManager().registerAction("actionRelatePrePurchase", actionRelatePrePurchase);
         this.actionRelatePrePurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDScrollPane2 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.contSaleRate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contChangeState = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtSaleRate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.cbChangeState = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contDes = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanSignDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contActualArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPreArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanningCompensate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPurAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCashSalesCompensate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contPlanningArea = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contRecommend = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractTotalAmountNT = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealTotalAmountNT = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealBuildPriceNT = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractBuildPriceNT = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contContractRoomPriceNT = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDealRoomPriceNT = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contOneQd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contTwoQd = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDes = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkPlanSignDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.txtActualArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPreArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPlanningCompensate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPurAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtCashSalesCompensate = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtPlanningArea = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtRecommend = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtContractTotalAmountNT = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealTotalAmountNT = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealBuildPriceNT = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractBuildPriceNT = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtContractRoomPriceNT = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtDealRoomPriceNT = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtOneQd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtTwoQd = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.btnRelatePrePurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRelateSign = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemRelatePrePurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRelateSign = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDScrollPane2.setName("kDScrollPane2");
        this.kDPanel1.setName("kDPanel1");
        this.contSaleRate.setName("contSaleRate");
        this.contChangeState.setName("contChangeState");
        this.txtSaleRate.setName("txtSaleRate");
        this.cbChangeState.setName("cbChangeState");
        this.contDes.setName("contDes");
        this.contPlanSignDate.setName("contPlanSignDate");
        this.contActualArea.setName("contActualArea");
        this.contPreArea.setName("contPreArea");
        this.contPlanningCompensate.setName("contPlanningCompensate");
        this.contPurAmount.setName("contPurAmount");
        this.contCashSalesCompensate.setName("contCashSalesCompensate");
        this.contPlanningArea.setName("contPlanningArea");
        this.contRecommend.setName("contRecommend");
        this.contContractTotalAmountNT.setName("contContractTotalAmountNT");
        this.contDealTotalAmountNT.setName("contDealTotalAmountNT");
        this.contDealBuildPriceNT.setName("contDealBuildPriceNT");
        this.contContractBuildPriceNT.setName("contContractBuildPriceNT");
        this.contContractRoomPriceNT.setName("contContractRoomPriceNT");
        this.contDealRoomPriceNT.setName("contDealRoomPriceNT");
        this.contOneQd.setName("contOneQd");
        this.contTwoQd.setName("contTwoQd");
        this.txtDes.setName("txtDes");
        this.pkPlanSignDate.setName("pkPlanSignDate");
        this.txtActualArea.setName("txtActualArea");
        this.txtPreArea.setName("txtPreArea");
        this.txtPlanningCompensate.setName("txtPlanningCompensate");
        this.txtPurAmount.setName("txtPurAmount");
        this.txtCashSalesCompensate.setName("txtCashSalesCompensate");
        this.txtPlanningArea.setName("txtPlanningArea");
        this.txtRecommend.setName("txtRecommend");
        this.txtContractTotalAmountNT.setName("txtContractTotalAmountNT");
        this.txtDealTotalAmountNT.setName("txtDealTotalAmountNT");
        this.txtDealBuildPriceNT.setName("txtDealBuildPriceNT");
        this.txtContractBuildPriceNT.setName("txtContractBuildPriceNT");
        this.txtContractRoomPriceNT.setName("txtContractRoomPriceNT");
        this.txtDealRoomPriceNT.setName("txtDealRoomPriceNT");
        this.txtOneQd.setName("txtOneQd");
        this.txtTwoQd.setName("txtTwoQd");
        this.btnRelatePrePurchase.setName("btnRelatePrePurchase");
        this.btnRelateSign.setName("btnRelateSign");
        this.menuItemRelatePrePurchase.setName("menuItemRelatePrePurchase");
        this.menuItemRelateSign.setName("menuItemRelateSign");
        // CoreUI		
        this.conPayList.setTitle(resHelper.getString("conPayList.title"));		
        this.contPhoneNumber.setBoundLabelLength(60);		
        this.contRoomModel.setVisible(false);		
        this.contStandardTotalAmount.setBoundLabelText(resHelper.getString("contStandardTotalAmount.boundLabelText"));		
        this.contAttachPropertyTotalAmount.setVisible(false);		
        this.contFitmentAmount1.setVisible(false);
        this.labelCustomer1.addMouseListener(new java.awt.event.MouseAdapter() {
        });
        this.labelCustomer2.addMouseListener(new java.awt.event.MouseAdapter() {
        });
        this.labelCustomer3.addMouseListener(new java.awt.event.MouseAdapter() {
        });
        this.labelCustomer4.addMouseListener(new java.awt.event.MouseAdapter() {
        });
        this.labelCustomer5.addMouseListener(new java.awt.event.MouseAdapter() {
        });		
        this.txtPhoneNumber.setEnabled(false);		
        this.txtRoomModel.setEnabled(false);		
        this.txtBuildingArea.setEnabled(false);		
        this.txtRoomArea.setEnabled(false);		
        this.txtStandardTotalAmount.setEnabled(false);		
        this.txtBuildingPrice.setEnabled(false);		
        this.txtRoomPrice.setEnabled(false);		
        this.txtAttachPropertyTotalAmount.setEnabled(false);		
        this.txtFitmentAmount1.setEnabled(false);
		String tblAttachPropertyStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"roomNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"roomArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"buildingPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"roomPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"standardTotalAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"agio\" t:width=\"350\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"isMergeTocontract\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"mergeAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{roomNumber}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{roomArea}</t:Cell><t:Cell>$Resource{buildingPrice}</t:Cell><t:Cell>$Resource{roomPrice}</t:Cell><t:Cell>$Resource{standardTotalAmount}</t:Cell><t:Cell>$Resource{agio}</t:Cell><t:Cell>$Resource{isMergeTocontract}</t:Cell><t:Cell>$Resource{mergeAmount}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";

        this.tblAttachProperty.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

        

        this.tblAttachProperty.checkParsed();		
        this.txtFitmentPrice.setEnabled(false);
		String tblReceiveBillStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"revDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"billType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"amount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"revPerson\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{revDate}</t:Cell><t:Cell>$Resource{billType}</t:Cell><t:Cell>$Resource{amount}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{revPerson}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        

        this.tblReceiveBill.checkParsed();
		String tblBizReviewStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" t:styleID=\"sCol0\" /><t:Column t:key=\"bizName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"finishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"isFinish\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"actualFinishDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{bizName}</t:Cell><t:Cell>$Resource{finishDate}</t:Cell><t:Cell>$Resource{isFinish}</t:Cell><t:Cell>$Resource{actualFinishDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";

        this.tblBizReview.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

        

        this.tblBizReview.checkParsed();		
        this.conRecommendCard.setBoundLabelText(resHelper.getString("conRecommendCard.boundLabelText"));		
        this.contContractNumber.setBoundLabelText(resHelper.getString("contContractNumber.boundLabelText"));		
        this.contBizDate.setBoundLabelText(resHelper.getString("contBizDate.boundLabelText"));		
        this.contAgioScheme.setVisible(false);		
        this.contAgioDes.setVisible(false);		
        this.contDealTotalAmount.setBoundLabelText(resHelper.getString("contDealTotalAmount.boundLabelText"));		
        this.contDealBuildPrice.setBoundLabelText(resHelper.getString("contDealBuildPrice.boundLabelText"));		
        this.contDealRoomPrice.setBoundLabelText(resHelper.getString("contDealRoomPrice.boundLabelText"));		
        this.contContractTotalAmount.setBoundLabelText(resHelper.getString("contContractTotalAmount.boundLabelText"));		
        this.contContractTotalAmount.setVisible(false);		
        this.contContractBuildPrice.setBoundLabelText(resHelper.getString("contContractBuildPrice.boundLabelText"));		
        this.contContractBuildPrice.setBoundLabelLength(120);		
        this.contContractBuildPrice.setVisible(false);		
        this.contContractRoomPrice.setBoundLabelText(resHelper.getString("contContractRoomPrice.boundLabelText"));		
        this.contContractRoomPrice.setBoundLabelLength(120);		
        this.contContractRoomPrice.setVisible(false);		
        this.contAgio.setVisible(false);		
        this.contRecommendPerson.setVisible(false);		
        this.btnChooseAgio.setVisible(false);		
        this.txtRecommendCard.setEnabled(false);		
        this.comboValuationType.setRequired(true);		
        this.f7PayType.setRequired(true);		
        this.txtDealTotalAmount.setRequired(true);		
        this.txtDealTotalAmount.setEnabled(false);		
        this.txtDealBuildPrice.setEnabled(false);		
        this.txtDealBuildPrice.setRequired(true);		
        this.txtDealRoomPrice.setRequired(true);		
        this.txtDealRoomPrice.setEnabled(false);		
        this.txtContractTotalAmount.setEnabled(false);		
        this.txtContractTotalAmount.setRequired(true);		
        this.txtContractBuildPrice.setRequired(true);		
        this.txtContractBuildPrice.setEnabled(false);		
        this.txtContractRoomPrice.setEnabled(false);		
        this.txtContractRoomPrice.setRequired(true);		
        this.txtAgio.setEnabled(false);		
        this.f7RecommendPerson.setCommitFormat("$insiderName$");		
        this.f7RecommendPerson.setEditFormat("$insiderName$");		
        this.f7RecommendPerson.setDisplayFormat("$insiderName$");		
        this.contAFundAmount.setVisible(false);		
        this.contLoanAmount.setVisible(false);
		String tblPayListStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"state\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"moneyName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"currency\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"appAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"actAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"balance\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"des\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /></t:ColumnGroup><t:Head><t:Row t:name=\"ʵ������\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{state}</t:Cell><t:Cell>$Resource{moneyName}</t:Cell><t:Cell>$Resource{appDate}</t:Cell><t:Cell>$Resource{currency}</t:Cell><t:Cell>$Resource{appAmount}</t:Cell><t:Cell>$Resource{actAmount}</t:Cell><t:Cell>$Resource{balance}</t:Cell><t:Cell>$Resource{des}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";

        this.tblPayList.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });

        

        this.tblPayList.checkParsed();
        // kDScrollPane2
        // kDPanel1		
        this.kDPanel1.setPreferredSize(new Dimension(1013,700));
        // contSaleRate		
        this.contSaleRate.setBoundLabelLength(100);		
        this.contSaleRate.setBoundLabelUnderline(true);		
        this.contSaleRate.setBoundLabelText(resHelper.getString("contSaleRate.boundLabelText"));		
        this.contSaleRate.setVisible(false);
        // contChangeState		
        this.contChangeState.setBoundLabelText(resHelper.getString("contChangeState.boundLabelText"));		
        this.contChangeState.setBoundLabelLength(100);		
        this.contChangeState.setBoundLabelUnderline(true);
        // txtSaleRate		
        this.txtSaleRate.setEnabled(false);
        // cbChangeState		
        this.cbChangeState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.PurChangeStateEnum").toArray());		
        this.cbChangeState.setRequired(true);
        // contDes		
        this.contDes.setBoundLabelText(resHelper.getString("contDes.boundLabelText"));		
        this.contDes.setBoundLabelLength(100);		
        this.contDes.setBoundLabelUnderline(true);
        // contPlanSignDate		
        this.contPlanSignDate.setBoundLabelText(resHelper.getString("contPlanSignDate.boundLabelText"));		
        this.contPlanSignDate.setBoundLabelLength(100);		
        this.contPlanSignDate.setBoundLabelUnderline(true);
        // contActualArea		
        this.contActualArea.setBoundLabelText(resHelper.getString("contActualArea.boundLabelText"));		
        this.contActualArea.setBoundLabelLength(100);		
        this.contActualArea.setBoundLabelUnderline(true);		
        this.contActualArea.setVisible(false);
        // contPreArea		
        this.contPreArea.setBoundLabelText(resHelper.getString("contPreArea.boundLabelText"));		
        this.contPreArea.setBoundLabelLength(100);		
        this.contPreArea.setBoundLabelUnderline(true);		
        this.contPreArea.setVisible(false);
        // contPlanningCompensate		
        this.contPlanningCompensate.setBoundLabelText(resHelper.getString("contPlanningCompensate.boundLabelText"));		
        this.contPlanningCompensate.setBoundLabelLength(100);		
        this.contPlanningCompensate.setBoundLabelUnderline(true);		
        this.contPlanningCompensate.setVisible(false);
        // contPurAmount		
        this.contPurAmount.setBoundLabelText(resHelper.getString("contPurAmount.boundLabelText"));		
        this.contPurAmount.setBoundLabelLength(100);		
        this.contPurAmount.setBoundLabelUnderline(true);		
        this.contPurAmount.setVisible(false);
        // contCashSalesCompensate		
        this.contCashSalesCompensate.setBoundLabelText(resHelper.getString("contCashSalesCompensate.boundLabelText"));		
        this.contCashSalesCompensate.setBoundLabelLength(100);		
        this.contCashSalesCompensate.setBoundLabelUnderline(true);		
        this.contCashSalesCompensate.setVisible(false);
        // contPlanningArea		
        this.contPlanningArea.setBoundLabelText(resHelper.getString("contPlanningArea.boundLabelText"));		
        this.contPlanningArea.setBoundLabelLength(100);		
        this.contPlanningArea.setBoundLabelUnderline(true);		
        this.contPlanningArea.setVisible(false);
        // contRecommend		
        this.contRecommend.setBoundLabelText(resHelper.getString("contRecommend.boundLabelText"));		
        this.contRecommend.setBoundLabelUnderline(true);		
        this.contRecommend.setBoundLabelLength(100);
        // contContractTotalAmountNT		
        this.contContractTotalAmountNT.setBoundLabelText(resHelper.getString("contContractTotalAmountNT.boundLabelText"));		
        this.contContractTotalAmountNT.setBoundLabelLength(120);		
        this.contContractTotalAmountNT.setBoundLabelUnderline(true);		
        this.contContractTotalAmountNT.setEnabled(false);		
        this.contContractTotalAmountNT.setVisible(false);
        // contDealTotalAmountNT		
        this.contDealTotalAmountNT.setBoundLabelText(resHelper.getString("contDealTotalAmountNT.boundLabelText"));		
        this.contDealTotalAmountNT.setBoundLabelLength(120);		
        this.contDealTotalAmountNT.setBoundLabelUnderline(true);		
        this.contDealTotalAmountNT.setEnabled(false);		
        this.contDealTotalAmountNT.setVisible(false);
        // contDealBuildPriceNT		
        this.contDealBuildPriceNT.setBoundLabelText(resHelper.getString("contDealBuildPriceNT.boundLabelText"));		
        this.contDealBuildPriceNT.setBoundLabelLength(140);		
        this.contDealBuildPriceNT.setBoundLabelUnderline(true);		
        this.contDealBuildPriceNT.setEnabled(false);		
        this.contDealBuildPriceNT.setVisible(false);
        // contContractBuildPriceNT		
        this.contContractBuildPriceNT.setBoundLabelText(resHelper.getString("contContractBuildPriceNT.boundLabelText"));		
        this.contContractBuildPriceNT.setBoundLabelLength(140);		
        this.contContractBuildPriceNT.setBoundLabelUnderline(true);		
        this.contContractBuildPriceNT.setEnabled(false);		
        this.contContractBuildPriceNT.setVisible(false);
        // contContractRoomPriceNT		
        this.contContractRoomPriceNT.setBoundLabelText(resHelper.getString("contContractRoomPriceNT.boundLabelText"));		
        this.contContractRoomPriceNT.setBoundLabelLength(140);		
        this.contContractRoomPriceNT.setBoundLabelUnderline(true);		
        this.contContractRoomPriceNT.setEnabled(false);		
        this.contContractRoomPriceNT.setVisible(false);
        // contDealRoomPriceNT		
        this.contDealRoomPriceNT.setBoundLabelText(resHelper.getString("contDealRoomPriceNT.boundLabelText"));		
        this.contDealRoomPriceNT.setBoundLabelLength(140);		
        this.contDealRoomPriceNT.setBoundLabelUnderline(true);		
        this.contDealRoomPriceNT.setEnabled(false);		
        this.contDealRoomPriceNT.setVisible(false);
        // contOneQd		
        this.contOneQd.setBoundLabelText(resHelper.getString("contOneQd.boundLabelText"));		
        this.contOneQd.setBoundLabelLength(100);		
        this.contOneQd.setBoundLabelUnderline(true);
        // contTwoQd		
        this.contTwoQd.setBoundLabelText(resHelper.getString("contTwoQd.boundLabelText"));		
        this.contTwoQd.setBoundLabelLength(100);		
        this.contTwoQd.setBoundLabelUnderline(true);
        // txtDes		
        this.txtDes.setMaxLength(255);
        // pkPlanSignDate		
        this.pkPlanSignDate.setRequired(true);
        // txtActualArea		
        this.txtActualArea.setEnabled(false);
        // txtPreArea		
        this.txtPreArea.setEnabled(false);
        // txtPlanningCompensate		
        this.txtPlanningCompensate.setEnabled(false);
        // txtPurAmount
        // txtCashSalesCompensate		
        this.txtCashSalesCompensate.setEnabled(false);
        // txtPlanningArea		
        this.txtPlanningArea.setEnabled(false);
        // txtRecommend
        // txtContractTotalAmountNT		
        this.txtContractTotalAmountNT.setDataType(1);		
        this.txtContractTotalAmountNT.setEnabled(false);		
        this.txtContractTotalAmountNT.setRequired(true);
        // txtDealTotalAmountNT		
        this.txtDealTotalAmountNT.setDataType(1);		
        this.txtDealTotalAmountNT.setRequired(true);		
        this.txtDealTotalAmountNT.setEnabled(false);
        // txtDealBuildPriceNT		
        this.txtDealBuildPriceNT.setDataType(1);		
        this.txtDealBuildPriceNT.setSupportedEmpty(true);		
        this.txtDealBuildPriceNT.setEnabled(false);		
        this.txtDealBuildPriceNT.setRequired(true);
        // txtContractBuildPriceNT		
        this.txtContractBuildPriceNT.setDataType(1);		
        this.txtContractBuildPriceNT.setRequired(true);		
        this.txtContractBuildPriceNT.setEnabled(false);
        // txtContractRoomPriceNT		
        this.txtContractRoomPriceNT.setDataType(1);		
        this.txtContractRoomPriceNT.setRequired(true);		
        this.txtContractRoomPriceNT.setEnabled(false);
        // txtDealRoomPriceNT		
        this.txtDealRoomPriceNT.setDataType(1);		
        this.txtDealRoomPriceNT.setEnabled(false);		
        this.txtDealRoomPriceNT.setRequired(true);
        // txtOneQd		
        this.txtOneQd.setEnabled(false);
        // txtTwoQd		
        this.txtTwoQd.setEnabled(false);
        // btnRelatePrePurchase
        this.btnRelatePrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionRelatePrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRelatePrePurchase.setText(resHelper.getString("btnRelatePrePurchase.text"));
        // btnRelateSign
        this.btnRelateSign.setAction((IItemAction)ActionProxyFactory.getProxy(actionRelateSign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRelateSign.setText(resHelper.getString("btnRelateSign.text"));
        // menuItemRelatePrePurchase
        this.menuItemRelatePrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionRelatePrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRelatePrePurchase.setText(resHelper.getString("menuItemRelatePrePurchase.text"));
        // menuItemRelateSign
        this.menuItemRelateSign.setAction((IItemAction)ActionProxyFactory.getProxy(actionRelateSign, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRelateSign.setText(resHelper.getString("menuItemRelateSign.text"));
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
        this.setBounds(new Rectangle(10, 10, 1013, 700));
this.setLayout(new BorderLayout(0, 0));
        this.add(kDScrollPane2, BorderLayout.CENTER);
        //kDScrollPane2
        kDScrollPane2.getViewport().add(kDPanel1, null);
        //kDPanel1
        kDPanel1.setLayout(new KDLayout());
        kDPanel1.putClientProperty("OriginalBounds", new Rectangle(0, 0, 1013, 700));        contCreateTime.setBounds(new Rectangle(7, 668, 270, 19));
        kDPanel1.add(contCreateTime, new KDLayout.Constraints(7, 668, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contModifyDate.setBounds(new Rectangle(726, 668, 270, 19));
        kDPanel1.add(contModifyDate, new KDLayout.Constraints(726, 668, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditor.setBounds(new Rectangle(364, 646, 270, 19));
        kDPanel1.add(contAuditor, new KDLayout.Constraints(364, 646, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditDate.setBounds(new Rectangle(364, 668, 270, 19));
        kDPanel1.add(contAuditDate, new KDLayout.Constraints(364, 668, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contModifier.setBounds(new Rectangle(726, 646, 270, 19));
        kDPanel1.add(contModifier, new KDLayout.Constraints(726, 646, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreator.setBounds(new Rectangle(7, 646, 270, 19));
        kDPanel1.add(contCreator, new KDLayout.Constraints(7, 646, 270, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        tabInformation.setBounds(new Rectangle(7, 6, 989, 152));
        kDPanel1.add(tabInformation, new KDLayout.Constraints(7, 6, 989, 152, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        tabBiz.setBounds(new Rectangle(7, 162, 989, 182));
        kDPanel1.add(tabBiz, new KDLayout.Constraints(7, 162, 989, 182, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        conPayList.setBounds(new Rectangle(7, 349, 989, 280));
        kDPanel1.add(conPayList, new KDLayout.Constraints(7, 349, 989, 280, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contModifyDate
        contModifyDate.setBoundEditor(pkModifyDate);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditDate
        contAuditDate.setBoundEditor(pkAuditDate);
        //contModifier
        contModifier.setBoundEditor(prmtModifier);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //tabInformation
        tabInformation.add(panelCommonInfo, resHelper.getString("panelCommonInfo.constraints"));
        tabInformation.add(panelAttachPropertyInfo, resHelper.getString("panelAttachPropertyInfo.constraints"));
        tabInformation.add(panelAdditionInfo, resHelper.getString("panelAdditionInfo.constraints"));
        tabInformation.add(panelFitInfo, resHelper.getString("panelFitInfo.constraints"));
        tabInformation.add(panelReceiveInfo, resHelper.getString("panelReceiveInfo.constraints"));
        tabInformation.add(panelBizReview, resHelper.getString("panelBizReview.constraints"));
        //panelCommonInfo
        panelCommonInfo.setLayout(new KDLayout());
        panelCommonInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 988, 119));        kDSeparator6.setBounds(new Rectangle(46, 25, 56, 10));
        panelCommonInfo.add(kDSeparator6, new KDLayout.Constraints(46, 25, 56, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator7.setBounds(new Rectangle(103, 25, 56, 10));
        panelCommonInfo.add(kDSeparator7, new KDLayout.Constraints(103, 25, 56, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labelCustomer.setBounds(new Rectangle(12, 7, 30, 19));
        panelCommonInfo.add(labelCustomer, new KDLayout.Constraints(12, 7, 30, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnSelectCustomer.setBounds(new Rectangle(346, 5, 85, 19));
        panelCommonInfo.add(btnSelectCustomer, new KDLayout.Constraints(346, 5, 85, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPhoneNumber.setBounds(new Rectangle(456, 5, 170, 19));
        panelCommonInfo.add(contPhoneNumber, new KDLayout.Constraints(456, 5, 170, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoom.setBounds(new Rectangle(12, 30, 615, 19));
        panelCommonInfo.add(contRoom, new KDLayout.Constraints(12, 30, 615, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSellType.setBounds(new Rectangle(12, 52, 270, 19));
        panelCommonInfo.add(contSellType, new KDLayout.Constraints(12, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomModel.setBounds(new Rectangle(922, 31, 270, 19));
        panelCommonInfo.add(contRoomModel, new KDLayout.Constraints(922, 31, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildingArea.setBounds(new Rectangle(12, 74, 270, 19));
        panelCommonInfo.add(contBuildingArea, new KDLayout.Constraints(12, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomArea.setBounds(new Rectangle(357, 74, 270, 19));
        panelCommonInfo.add(contRoomArea, new KDLayout.Constraints(357, 74, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStandardTotalAmount.setBounds(new Rectangle(357, 52, 270, 19));
        panelCommonInfo.add(contStandardTotalAmount, new KDLayout.Constraints(357, 52, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBuildingPrice.setBounds(new Rectangle(12, 96, 270, 19));
        panelCommonInfo.add(contBuildingPrice, new KDLayout.Constraints(12, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRoomPrice.setBounds(new Rectangle(357, 96, 270, 19));
        panelCommonInfo.add(contRoomPrice, new KDLayout.Constraints(357, 96, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAttachPropertyTotalAmount.setBounds(new Rectangle(933, 80, 270, 19));
        panelCommonInfo.add(contAttachPropertyTotalAmount, new KDLayout.Constraints(933, 80, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contFitmentAmount1.setBounds(new Rectangle(917, 68, 270, 19));
        panelCommonInfo.add(contFitmentAmount1, new KDLayout.Constraints(917, 68, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        labelCustomer1.setBounds(new Rectangle(46, 5, 56, 19));
        panelCommonInfo.add(labelCustomer1, new KDLayout.Constraints(46, 5, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labelCustomer2.setBounds(new Rectangle(103, 5, 56, 19));
        panelCommonInfo.add(labelCustomer2, new KDLayout.Constraints(103, 5, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labelCustomer3.setBounds(new Rectangle(160, 5, 56, 19));
        panelCommonInfo.add(labelCustomer3, new KDLayout.Constraints(160, 5, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator8.setBounds(new Rectangle(160, 25, 56, 19));
        panelCommonInfo.add(kDSeparator8, new KDLayout.Constraints(160, 25, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labelCustomer4.setBounds(new Rectangle(217, 5, 56, 19));
        panelCommonInfo.add(labelCustomer4, new KDLayout.Constraints(217, 5, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator9.setBounds(new Rectangle(217, 25, 56, 10));
        panelCommonInfo.add(kDSeparator9, new KDLayout.Constraints(217, 25, 56, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        labelCustomer5.setBounds(new Rectangle(274, 5, 56, 19));
        panelCommonInfo.add(labelCustomer5, new KDLayout.Constraints(274, 5, 56, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        kDSeparator10.setBounds(new Rectangle(274, 25, 56, 10));
        panelCommonInfo.add(kDSeparator10, new KDLayout.Constraints(274, 25, 56, 10, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contValuationType.setBounds(new Rectangle(697, 30, 270, 19));
        panelCommonInfo.add(contValuationType, new KDLayout.Constraints(697, 30, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contSaleRate.setBounds(new Rectangle(940, 93, 270, 19));
        panelCommonInfo.add(contSaleRate, new KDLayout.Constraints(940, 93, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contChangeState.setBounds(new Rectangle(697, 5, 270, 19));
        panelCommonInfo.add(contChangeState, new KDLayout.Constraints(697, 5, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contPhoneNumber
        contPhoneNumber.setBoundEditor(txtPhoneNumber);
        //contRoom
        contRoom.setBoundEditor(f7Room);
        //contSellType
        contSellType.setBoundEditor(comboSellType);
        //contRoomModel
        contRoomModel.setBoundEditor(txtRoomModel);
        //contBuildingArea
        contBuildingArea.setBoundEditor(txtBuildingArea);
        //contRoomArea
        contRoomArea.setBoundEditor(txtRoomArea);
        //contStandardTotalAmount
        contStandardTotalAmount.setBoundEditor(txtStandardTotalAmount);
        //contBuildingPrice
        contBuildingPrice.setBoundEditor(txtBuildingPrice);
        //contRoomPrice
        contRoomPrice.setBoundEditor(txtRoomPrice);
        //contAttachPropertyTotalAmount
        contAttachPropertyTotalAmount.setBoundEditor(txtAttachPropertyTotalAmount);
        //contFitmentAmount1
        contFitmentAmount1.setBoundEditor(txtFitmentAmount1);
        //contValuationType
        contValuationType.setBoundEditor(comboValuationType);
        //contSaleRate
        contSaleRate.setBoundEditor(txtSaleRate);
        //contChangeState
        contChangeState.setBoundEditor(cbChangeState);
        //panelAttachPropertyInfo
        panelAttachPropertyInfo.setLayout(new KDLayout());
        panelAttachPropertyInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 988, 119));        tblAttachProperty.setBounds(new Rectangle(4, 28, 975, 90));
        panelAttachPropertyInfo.add(tblAttachProperty, new KDLayout.Constraints(4, 28, 975, 90, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddAPLine.setBounds(new Rectangle(803, 4, 80, 19));
        panelAttachPropertyInfo.add(btnAddAPLine, new KDLayout.Constraints(803, 4, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemoveAPLine.setBounds(new Rectangle(899, 4, 80, 19));
        panelAttachPropertyInfo.add(btnRemoveAPLine, new KDLayout.Constraints(899, 4, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //panelAdditionInfo
panelAdditionInfo.setLayout(new BorderLayout(0, 0));        panelAdditionInfo.add(kDScrollPane1, BorderLayout.CENTER);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtTackDesc, null);
        //panelFitInfo
        panelFitInfo.setLayout(new KDLayout());
        panelFitInfo.putClientProperty("OriginalBounds", new Rectangle(0, 0, 988, 119));        contFitmentStandard.setBounds(new Rectangle(12, 16, 270, 19));
        panelFitInfo.add(contFitmentStandard, new KDLayout.Constraints(12, 16, 270, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFitmentPrice.setBounds(new Rectangle(354, 16, 270, 19));
        panelFitInfo.add(contFitmentPrice, new KDLayout.Constraints(354, 16, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contFitmentAmount.setBounds(new Rectangle(696, 16, 270, 19));
        panelFitInfo.add(contFitmentAmount, new KDLayout.Constraints(696, 16, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        chkIsFitmentToContract.setBounds(new Rectangle(12, 54, 169, 19));
        panelFitInfo.add(chkIsFitmentToContract, new KDLayout.Constraints(12, 54, 169, 19, KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contFitmentStandard
        contFitmentStandard.setBoundEditor(f7FitmentStandard);
        //contFitmentPrice
        contFitmentPrice.setBoundEditor(txtFitmentPrice);
        //contFitmentAmount
        contFitmentAmount.setBoundEditor(txtFitmentAmount);
        //panelReceiveInfo
panelReceiveInfo.setLayout(new BorderLayout(0, 0));        panelReceiveInfo.add(tblReceiveBill, BorderLayout.CENTER);
        //panelBizReview
panelBizReview.setLayout(new BorderLayout(0, 0));        panelBizReview.add(tblBizReview, BorderLayout.CENTER);
        //tabBiz
        tabBiz.add(panelBiz, resHelper.getString("panelBiz.constraints"));
        //panelBiz
        panelBiz.setLayout(new KDLayout());
        panelBiz.putClientProperty("OriginalBounds", new Rectangle(0, 0, 988, 149));        conRecommendCard.setBounds(new Rectangle(353, 92, 270, 19));
        panelBiz.add(conRecommendCard, new KDLayout.Constraints(353, 92, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contSeller.setBounds(new Rectangle(698, 92, 270, 19));
        panelBiz.add(contSeller, new KDLayout.Constraints(698, 92, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contBizAdscriptionDate.setBounds(new Rectangle(353, 28, 270, 19));
        panelBiz.add(contBizAdscriptionDate, new KDLayout.Constraints(353, 28, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contNumber.setBounds(new Rectangle(9, 6, 270, 19));
        panelBiz.add(contNumber, new KDLayout.Constraints(9, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractNumber.setBounds(new Rectangle(353, 6, 270, 19));
        panelBiz.add(contContractNumber, new KDLayout.Constraints(353, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contBizDate.setBounds(new Rectangle(698, 28, 270, 19));
        panelBiz.add(contBizDate, new KDLayout.Constraints(698, 28, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPayType.setBounds(new Rectangle(9, 28, 270, 19));
        panelBiz.add(contPayType, new KDLayout.Constraints(9, 28, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAgioScheme.setBounds(new Rectangle(661, 245, 270, 19));
        panelBiz.add(contAgioScheme, new KDLayout.Constraints(661, 245, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAgioDes.setBounds(new Rectangle(388, 259, 507, 19));
        panelBiz.add(contAgioDes, new KDLayout.Constraints(388, 259, 507, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealTotalAmount.setBounds(new Rectangle(9, 49, 270, 19));
        panelBiz.add(contDealTotalAmount, new KDLayout.Constraints(9, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealBuildPrice.setBounds(new Rectangle(353, 49, 270, 19));
        panelBiz.add(contDealBuildPrice, new KDLayout.Constraints(353, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealRoomPrice.setBounds(new Rectangle(698, 49, 270, 19));
        panelBiz.add(contDealRoomPrice, new KDLayout.Constraints(698, 49, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contContractTotalAmount.setBounds(new Rectangle(22, 262, 270, 19));
        panelBiz.add(contContractTotalAmount, new KDLayout.Constraints(22, 262, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractBuildPrice.setBounds(new Rectangle(366, 262, 270, 19));
        panelBiz.add(contContractBuildPrice, new KDLayout.Constraints(366, 262, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractRoomPrice.setBounds(new Rectangle(711, 262, 270, 19));
        panelBiz.add(contContractRoomPrice, new KDLayout.Constraints(711, 262, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAgio.setBounds(new Rectangle(285, 256, 270, 19));
        panelBiz.add(contAgio, new KDLayout.Constraints(285, 256, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecommendPerson.setBounds(new Rectangle(704, 248, 270, 19));
        panelBiz.add(contRecommendPerson, new KDLayout.Constraints(704, 248, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnChooseAgio.setBounds(new Rectangle(906, 259, 97, 19));
        panelBiz.add(btnChooseAgio, new KDLayout.Constraints(906, 259, 97, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDes.setBounds(new Rectangle(9, 71, 962, 19));
        panelBiz.add(contDes, new KDLayout.Constraints(9, 71, 962, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlanSignDate.setBounds(new Rectangle(698, 6, 270, 19));
        panelBiz.add(contPlanSignDate, new KDLayout.Constraints(698, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contActualArea.setBounds(new Rectangle(704, 270, 270, 19));
        panelBiz.add(contActualArea, new KDLayout.Constraints(704, 270, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contPreArea.setBounds(new Rectangle(359, 270, 270, 19));
        panelBiz.add(contPreArea, new KDLayout.Constraints(359, 270, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlanningCompensate.setBounds(new Rectangle(15, 248, 270, 19));
        panelBiz.add(contPlanningCompensate, new KDLayout.Constraints(15, 248, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPurAmount.setBounds(new Rectangle(704, 226, 270, 19));
        panelBiz.add(contPurAmount, new KDLayout.Constraints(704, 226, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCashSalesCompensate.setBounds(new Rectangle(359, 248, 270, 19));
        panelBiz.add(contCashSalesCompensate, new KDLayout.Constraints(359, 248, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contPlanningArea.setBounds(new Rectangle(15, 270, 270, 19));
        panelBiz.add(contPlanningArea, new KDLayout.Constraints(15, 270, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contRecommend.setBounds(new Rectangle(9, 92, 270, 19));
        panelBiz.add(contRecommend, new KDLayout.Constraints(9, 92, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractTotalAmountNT.setBounds(new Rectangle(-1, 276, 270, 19));
        panelBiz.add(contContractTotalAmountNT, new KDLayout.Constraints(-1, 276, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealTotalAmountNT.setBounds(new Rectangle(321, 282, 270, 19));
        panelBiz.add(contDealTotalAmountNT, new KDLayout.Constraints(321, 282, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDealBuildPriceNT.setBounds(new Rectangle(67, 277, 270, 19));
        panelBiz.add(contDealBuildPriceNT, new KDLayout.Constraints(67, 277, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractBuildPriceNT.setBounds(new Rectangle(579, 282, 270, 19));
        panelBiz.add(contContractBuildPriceNT, new KDLayout.Constraints(579, 282, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contContractRoomPriceNT.setBounds(new Rectangle(630, 270, 270, 19));
        panelBiz.add(contContractRoomPriceNT, new KDLayout.Constraints(630, 270, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contDealRoomPriceNT.setBounds(new Rectangle(861, 258, 270, 19));
        panelBiz.add(contDealRoomPriceNT, new KDLayout.Constraints(861, 258, 270, 19, KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contOneQd.setBounds(new Rectangle(9, 114, 270, 19));
        panelBiz.add(contOneQd, new KDLayout.Constraints(9, 114, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contTwoQd.setBounds(new Rectangle(353, 114, 270, 19));
        panelBiz.add(contTwoQd, new KDLayout.Constraints(353, 114, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //conRecommendCard
        conRecommendCard.setBoundEditor(txtRecommendCard);
        //contSeller
        contSeller.setBoundEditor(f7Seller);
        //contBizAdscriptionDate
        contBizAdscriptionDate.setBoundEditor(pkBizAdscriptionDate);
        //contNumber
        contNumber.setBoundEditor(txtNumber);
        //contContractNumber
        contContractNumber.setBoundEditor(txtContractNumber);
        //contBizDate
        contBizDate.setBoundEditor(pkBizDate);
        //contPayType
        contPayType.setBoundEditor(f7PayType);
        //contAgioScheme
        contAgioScheme.setBoundEditor(f7AgioScheme);
        //contAgioDes
        contAgioDes.setBoundEditor(txtAgioDes);
        //contDealTotalAmount
        contDealTotalAmount.setBoundEditor(txtDealTotalAmount);
        //contDealBuildPrice
        contDealBuildPrice.setBoundEditor(txtDealBuildPrice);
        //contDealRoomPrice
        contDealRoomPrice.setBoundEditor(txtDealRoomPrice);
        //contContractTotalAmount
        contContractTotalAmount.setBoundEditor(txtContractTotalAmount);
        //contContractBuildPrice
        contContractBuildPrice.setBoundEditor(txtContractBuildPrice);
        //contContractRoomPrice
        contContractRoomPrice.setBoundEditor(txtContractRoomPrice);
        //contAgio
        contAgio.setBoundEditor(txtAgio);
        //contRecommendPerson
        contRecommendPerson.setBoundEditor(f7RecommendPerson);
        //contDes
        contDes.setBoundEditor(txtDes);
        //contPlanSignDate
        contPlanSignDate.setBoundEditor(pkPlanSignDate);
        //contActualArea
        contActualArea.setBoundEditor(txtActualArea);
        //contPreArea
        contPreArea.setBoundEditor(txtPreArea);
        //contPlanningCompensate
        contPlanningCompensate.setBoundEditor(txtPlanningCompensate);
        //contPurAmount
        contPurAmount.setBoundEditor(txtPurAmount);
        //contCashSalesCompensate
        contCashSalesCompensate.setBoundEditor(txtCashSalesCompensate);
        //contPlanningArea
        contPlanningArea.setBoundEditor(txtPlanningArea);
        //contRecommend
        contRecommend.setBoundEditor(txtRecommend);
        //contContractTotalAmountNT
        contContractTotalAmountNT.setBoundEditor(txtContractTotalAmountNT);
        //contDealTotalAmountNT
        contDealTotalAmountNT.setBoundEditor(txtDealTotalAmountNT);
        //contDealBuildPriceNT
        contDealBuildPriceNT.setBoundEditor(txtDealBuildPriceNT);
        //contContractBuildPriceNT
        contContractBuildPriceNT.setBoundEditor(txtContractBuildPriceNT);
        //contContractRoomPriceNT
        contContractRoomPriceNT.setBoundEditor(txtContractRoomPriceNT);
        //contDealRoomPriceNT
        contDealRoomPriceNT.setBoundEditor(txtDealRoomPriceNT);
        //contOneQd
        contOneQd.setBoundEditor(txtOneQd);
        //contTwoQd
        contTwoQd.setBoundEditor(txtTwoQd);
        //conPayList
        conPayList.getContentPane().setLayout(new KDLayout());
        conPayList.getContentPane().putClientProperty("OriginalBounds", new Rectangle(7, 349, 989, 280));        contAFundAmount.setBounds(new Rectangle(305, 4, 270, 19));
        conPayList.getContentPane().add(contAFundAmount, new KDLayout.Constraints(305, 4, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLoanAmount.setBounds(new Rectangle(9, 6, 270, 19));
        conPayList.getContentPane().add(contLoanAmount, new KDLayout.Constraints(9, 6, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnAddPayline.setBounds(new Rectangle(694, 4, 80, 19));
        conPayList.getContentPane().add(btnAddPayline, new KDLayout.Constraints(694, 4, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnInsertPayLine.setBounds(new Rectangle(793, 4, 80, 19));
        conPayList.getContentPane().add(btnInsertPayLine, new KDLayout.Constraints(793, 4, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        btnRemovePayLine.setBounds(new Rectangle(892, 4, 80, 19));
        conPayList.getContentPane().add(btnRemovePayLine, new KDLayout.Constraints(892, 4, 80, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        tblPayList.setBounds(new Rectangle(7, 31, 965, 215));
        conPayList.getContentPane().add(tblPayList, new KDLayout.Constraints(7, 31, 965, 215, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM_SCALE | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        //contAFundAmount
        contAFundAmount.setBoundEditor(txtAFundAmount);
        //contLoanAmount
        contLoanAmount.setBoundEditor(txtLoanAmount);

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
        menuView.add(menuItemLocate);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemAddCustomer);
        menuBiz.add(menuItemMemberApply);
        menuBiz.add(menuItemPointPresent);
        menuBiz.add(menuItemReceiveBill);
        menuBiz.add(menuItemRelatePrePurchase);
        menuBiz.add(menuItemRelateSign);
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
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnSubmitAudit);
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
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnAddCustomer);
        this.toolBar.add(btnMemberApply);
        this.toolBar.add(btnPointPresent);
        this.toolBar.add(btnReceiveBill);
        this.toolBar.add(btnRelatePrePurchase);
        this.toolBar.add(btnRelateSign);
        this.toolBar.add(btnCalculator);


    }

	//Regiester control's property binding.
	private void registerBindings(){
		dataBinder.registerBinding("createTime", java.sql.Timestamp.class, this.pkCreateTime, "value");
		dataBinder.registerBinding("lastUpdateTime", java.sql.Timestamp.class, this.pkModifyDate, "value");
		dataBinder.registerBinding("auditor", com.kingdee.eas.base.permission.UserInfo.class, this.prmtAuditor, "data");
		dataBinder.registerBinding("auditTime", java.util.Date.class, this.pkAuditDate, "value");
		dataBinder.registerBinding("lastUpdateUser", com.kingdee.eas.base.permission.UserInfo.class, this.prmtModifier, "data");
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.prmtCreator, "data");
		dataBinder.registerBinding("customerPhone", String.class, this.txtPhoneNumber, "text");
		dataBinder.registerBinding("room", com.kingdee.eas.fdc.sellhouse.RoomInfo.class, this.f7Room, "data");
		dataBinder.registerBinding("sellType", com.kingdee.eas.fdc.sellhouse.SellTypeEnum.class, this.comboSellType, "selectedItem");
		dataBinder.registerBinding("room.roomModel.name", String.class, this.txtRoomModel, "text");
		dataBinder.registerBinding("strdTotalAmount", java.math.BigDecimal.class, this.txtStandardTotalAmount, "value");
		dataBinder.registerBinding("strdBuildingPrice", java.math.BigDecimal.class, this.txtBuildingPrice, "value");
		dataBinder.registerBinding("strdRoomPrice", java.math.BigDecimal.class, this.txtRoomPrice, "value");
		dataBinder.registerBinding("attachmentAmount", java.math.BigDecimal.class, this.txtAttachPropertyTotalAmount, "value");
		dataBinder.registerBinding("fitmentTotalAmount", java.math.BigDecimal.class, this.txtFitmentAmount1, "value");
		dataBinder.registerBinding("tackDesc", String.class, this.txtTackDesc, "text");
		dataBinder.registerBinding("isFitmentToContract", boolean.class, this.chkIsFitmentToContract, "selected");
		dataBinder.registerBinding("fitmentStandard", com.kingdee.eas.fdc.sellhouse.DecorationStandardInfo.class, this.f7FitmentStandard, "data");
		dataBinder.registerBinding("fitmentPrice", java.math.BigDecimal.class, this.txtFitmentPrice, "value");
		dataBinder.registerBinding("fitmentTotalAmount", java.math.BigDecimal.class, this.txtFitmentAmount, "value");
		dataBinder.registerBinding("qdPerson", String.class, this.txtRecommendCard, "text");
		dataBinder.registerBinding("busAdscriptionDate", java.sql.Timestamp.class, this.pkBizAdscriptionDate, "value");
		dataBinder.registerBinding("number", String.class, this.txtNumber, "text");
		dataBinder.registerBinding("bizNumber", String.class, this.txtContractNumber, "text");
		dataBinder.registerBinding("valuationType", com.kingdee.eas.fdc.sellhouse.CalcTypeEnum.class, this.comboValuationType, "selectedItem");
		dataBinder.registerBinding("bizDate", java.util.Date.class, this.pkBizDate, "value");
		dataBinder.registerBinding("payType", com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo.class, this.f7PayType, "data");
		dataBinder.registerBinding("agioDesc", String.class, this.txtAgioDes, "text");
		dataBinder.registerBinding("dealTotalAmount", java.math.BigDecimal.class, this.txtDealTotalAmount, "value");
		dataBinder.registerBinding("dealBuildPrice", java.math.BigDecimal.class, this.txtDealBuildPrice, "value");
		dataBinder.registerBinding("dealRoomPrice", java.math.BigDecimal.class, this.txtDealRoomPrice, "value");
		dataBinder.registerBinding("contractTotalAmount", java.math.BigDecimal.class, this.txtContractTotalAmount, "value");
		dataBinder.registerBinding("contractBuildPrice", java.math.BigDecimal.class, this.txtContractBuildPrice, "value");
		dataBinder.registerBinding("contractRoomPrice", java.math.BigDecimal.class, this.txtContractRoomPrice, "value");
		dataBinder.registerBinding("lastAgio", java.math.BigDecimal.class, this.txtAgio, "value");
		dataBinder.registerBinding("insider", com.kingdee.eas.fdc.insider.InsiderInfo.class, this.f7RecommendPerson, "data");
		dataBinder.registerBinding("accFundAmount", java.math.BigDecimal.class, this.txtAFundAmount, "value");
		dataBinder.registerBinding("loanAmount", java.math.BigDecimal.class, this.txtLoanAmount, "value");
		dataBinder.registerBinding("changeState", com.kingdee.eas.fdc.sellhouse.PurChangeStateEnum.class, this.cbChangeState, "selectedItem");
		dataBinder.registerBinding("description", String.class, this.txtDes, "text");
		dataBinder.registerBinding("planSignDate", java.util.Date.class, this.pkPlanSignDate, "value");
		dataBinder.registerBinding("actualArea", java.math.BigDecimal.class, this.txtActualArea, "value");
		dataBinder.registerBinding("preArea", java.math.BigDecimal.class, this.txtPreArea, "value");
		dataBinder.registerBinding("planningCompensate", java.math.BigDecimal.class, this.txtPlanningCompensate, "value");
		dataBinder.registerBinding("cashSalesCompensate", java.math.BigDecimal.class, this.txtCashSalesCompensate, "value");
		dataBinder.registerBinding("planningArea", java.math.BigDecimal.class, this.txtPlanningArea, "value");
		dataBinder.registerBinding("recommended", String.class, this.txtRecommend, "text");
		dataBinder.registerBinding("oneQd", String.class, this.txtOneQd, "text");
		dataBinder.registerBinding("twoQd", String.class, this.txtTwoQd, "text");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.PurchaseManageEditUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo)ov;
    }
    protected void removeByPK(IObjectPK pk) throws Exception {
    	IObjectValue editData = this.editData;
    	super.removeByPK(pk);
    	recycleNumberByOrg(editData,"Sale",editData.getString("number"));
    }
    
    protected void recycleNumberByOrg(IObjectValue editData,String orgType,String number) {
        if (!StringUtils.isEmpty(number))
        {
            try {
            	String companyID = null;            
            	com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID =com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}				
				if (!StringUtils.isEmpty(companyID) && iCodingRuleManager.isExist(editData, companyID) && iCodingRuleManager.isUseIntermitNumber(editData, companyID)) {
					iCodingRuleManager.recycleNumber(editData,companyID,number);					
				}
            }
            catch (Exception e)
            {
                handUIException(e);
            }
        }
    }
    protected void setAutoNumberByOrg(String orgType) {
    	if (editData == null) return;
		if (editData.getNumber() == null) {
            try {
            	String companyID = null;
				if(!com.kingdee.util.StringUtils.isEmpty(orgType) && !"NONE".equalsIgnoreCase(orgType) && com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType))!=null) {
					companyID = com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit(com.kingdee.eas.basedata.org.OrgType.getEnum(orgType)).getString("id");
				}
				else if (com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit() != null) {
					companyID = ((com.kingdee.eas.basedata.org.OrgUnitInfo)com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentOrgUnit()).getString("id");
            	}
				com.kingdee.eas.base.codingrule.ICodingRuleManager iCodingRuleManager = com.kingdee.eas.base.codingrule.CodingRuleManagerFactory.getRemoteInstance();
		        if (iCodingRuleManager.isExist(editData, companyID)) {
		            if (iCodingRuleManager.isAddView(editData, companyID)) {
		            	editData.setNumber(iCodingRuleManager.getNumber(editData,companyID));
		            }
	                txtNumber.setEnabled(false);
		        }
            }
            catch (Exception e) {
                handUIException(e);
                this.oldData = editData;
                com.kingdee.eas.util.SysUtil.abort();
            } 
        } 
        else {
            if (editData.getNumber().trim().length() > 0) {
                txtNumber.setText(editData.getNumber());
            }
        }
    }
			protected com.kingdee.eas.basedata.org.OrgType getMainBizOrgType() {
			return com.kingdee.eas.basedata.org.OrgType.getEnum("Sale");
		}


    /**
     * output loadFields method
     */
    public void loadFields()
    {
        		setAutoNumberByOrg("Sale");
        dataBinder.loadFields();
    }
		protected void setOrgF7(KDBizPromptBox f7,com.kingdee.eas.basedata.org.OrgType orgType) throws Exception
		{
			com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer oufip = new com.kingdee.eas.basedata.org.client.f7.NewOrgUnitFilterInfoProducer(orgType);
			oufip.getModel().setIsCUFilter(true);
			f7.setFilterInfoProducer(oufip);
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
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastUpdateUser", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("customerPhone", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("sellType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("room.roomModel.name", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("strdTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("strdBuildingPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("strdRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("attachmentAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fitmentTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("tackDesc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("isFitmentToContract", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fitmentStandard", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fitmentPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("fitmentTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("qdPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("busAdscriptionDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("number", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizNumber", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("valuationType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("bizDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("payType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("agioDesc", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dealTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dealBuildPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("dealRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractTotalAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractBuildPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("contractRoomPrice", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("lastAgio", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("insider", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("accFundAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("loanAmount", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("changeState", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planSignDate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("actualArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("preArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planningCompensate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("cashSalesCompensate", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("planningArea", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("recommended", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("oneQd", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("twoQd", ValidateHelper.ON_SAVE);    		
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
     * output txtContractTotalAmount_dataChanged method
     */
    protected void txtContractTotalAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtDealTotalAmount_dataChanged method
     */
    protected void txtDealTotalAmount_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    }

    /**
     * output txtDealBuildPrice_actionPerformed method
     */
    protected void txtDealBuildPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
    {
    }

    /**
     * output txtContractBuildPrice_actionPerformed method
     */
    protected void txtContractBuildPrice_actionPerformed(java.awt.event.ActionEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("lastUpdateTime"));
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
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("lastUpdateUser.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("lastUpdateUser.id"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.number"));
        	sic.add(new SelectorItemInfo("lastUpdateUser.name"));
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
        sic.add(new SelectorItemInfo("customerPhone"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("room.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("room.id"));
        	sic.add(new SelectorItemInfo("room.number"));
        	sic.add(new SelectorItemInfo("room.name"));
		}
        sic.add(new SelectorItemInfo("sellType"));
        sic.add(new SelectorItemInfo("room.roomModel.name"));
        sic.add(new SelectorItemInfo("strdTotalAmount"));
        sic.add(new SelectorItemInfo("strdBuildingPrice"));
        sic.add(new SelectorItemInfo("strdRoomPrice"));
        sic.add(new SelectorItemInfo("attachmentAmount"));
        sic.add(new SelectorItemInfo("fitmentTotalAmount"));
        sic.add(new SelectorItemInfo("tackDesc"));
        sic.add(new SelectorItemInfo("isFitmentToContract"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("fitmentStandard.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("fitmentStandard.id"));
        	sic.add(new SelectorItemInfo("fitmentStandard.number"));
        	sic.add(new SelectorItemInfo("fitmentStandard.name"));
		}
        sic.add(new SelectorItemInfo("fitmentPrice"));
        sic.add(new SelectorItemInfo("qdPerson"));
        sic.add(new SelectorItemInfo("busAdscriptionDate"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("bizNumber"));
        sic.add(new SelectorItemInfo("valuationType"));
        sic.add(new SelectorItemInfo("bizDate"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("payType.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("payType.id"));
        	sic.add(new SelectorItemInfo("payType.number"));
        	sic.add(new SelectorItemInfo("payType.name"));
		}
        sic.add(new SelectorItemInfo("agioDesc"));
        sic.add(new SelectorItemInfo("dealTotalAmount"));
        sic.add(new SelectorItemInfo("dealBuildPrice"));
        sic.add(new SelectorItemInfo("dealRoomPrice"));
        sic.add(new SelectorItemInfo("contractTotalAmount"));
        sic.add(new SelectorItemInfo("contractBuildPrice"));
        sic.add(new SelectorItemInfo("contractRoomPrice"));
        sic.add(new SelectorItemInfo("lastAgio"));
		if(selectorAll.equalsIgnoreCase("true"))
		{
			sic.add(new SelectorItemInfo("insider.*"));
		}
		else{
        	sic.add(new SelectorItemInfo("insider.id"));
        	sic.add(new SelectorItemInfo("insider.number"));
        	sic.add(new SelectorItemInfo("insider.insiderName"));
		}
        sic.add(new SelectorItemInfo("accFundAmount"));
        sic.add(new SelectorItemInfo("loanAmount"));
        sic.add(new SelectorItemInfo("changeState"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("planSignDate"));
        sic.add(new SelectorItemInfo("actualArea"));
        sic.add(new SelectorItemInfo("preArea"));
        sic.add(new SelectorItemInfo("planningCompensate"));
        sic.add(new SelectorItemInfo("cashSalesCompensate"));
        sic.add(new SelectorItemInfo("planningArea"));
        sic.add(new SelectorItemInfo("recommended"));
        sic.add(new SelectorItemInfo("oneQd"));
        sic.add(new SelectorItemInfo("twoQd"));
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
     * output actionRelateSign_actionPerformed method
     */
    public void actionRelateSign_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRelatePrePurchase_actionPerformed method
     */
    public void actionRelatePrePurchase_actionPerformed(ActionEvent e) throws Exception
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
	public RequestContext prepareActionRelateSign(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRelateSign() {
    	return false;
    }
	public RequestContext prepareActionRelatePrePurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRelatePrePurchase() {
    	return false;
    }

    /**
     * output ActionRelateSign class
     */     
    protected class ActionRelateSign extends ItemAction {     
    
        public ActionRelateSign()
        {
            this(null);
        }

        public ActionRelateSign(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRelateSign.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelateSign.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelateSign.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseManageEditUI.this, "ActionRelateSign", "actionRelateSign_actionPerformed", e);
        }
    }

    /**
     * output ActionRelatePrePurchase class
     */     
    protected class ActionRelatePrePurchase extends ItemAction {     
    
        public ActionRelatePrePurchase()
        {
            this(null);
        }

        public ActionRelatePrePurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionRelatePrePurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelatePrePurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRelatePrePurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseManageEditUI.this, "ActionRelatePrePurchase", "actionRelatePrePurchase_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PurchaseManageEditUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }

    /**
     * output getEditUIName method
     */
    protected String getEditUIName()
    {
        return com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI.class.getName();
    }

    /**
     * output getBizInterface method
     */
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory.getRemoteInstance();
    }

    /**
     * output createNewData method
     */
    protected IObjectValue createNewData()
    {
        com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo objectValue = new com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo();
        objectValue.setCreator((com.kingdee.eas.base.permission.UserInfo)(com.kingdee.eas.common.client.SysContext.getSysContext().getCurrentUser()));		
        return objectValue;
    }


    	protected String getTDFileName() {
    	return "/bim/fdc/sellhouse/PurchaseManage";
	}
    protected IMetaDataPK getTDQueryPK() {
    	return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app.PurchaseManageQuery");
	}
    

    /**
     * output getDetailTable method
     */
    protected KDTable getDetailTable() {
        return tblAttachProperty;
	}
    /**
     * output applyDefaultValue method
     */
    protected void applyDefaultValue(IObjectValue vo) {        
    }        
	protected void setFieldsNull(com.kingdee.bos.dao.AbstractObjectValue arg0) {
		super.setFieldsNull(arg0);
		arg0.put("number",null);
	}

}