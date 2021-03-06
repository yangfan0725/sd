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
public abstract class AbstractPurchaseListUI extends com.kingdee.eas.fdc.basedata.client.FDCBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractPurchaseListUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane pnlMain;
    protected com.kingdee.bos.ctrl.swing.KDTreeView treeView;
    protected com.kingdee.bos.ctrl.swing.KDTree treeMain;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnCheckPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUncheckPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBlankOut;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWebMark;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurchasePrintView;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurchasePrint;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnProofOfPayment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemUnAudit;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBlankOut;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuWebMark;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPurchasePrint;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPurchasePrintView;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemProofOfPayment;
    protected ActionAudit actionAudit = null;
    protected ActionUnAudit actionUnAudit = null;
    protected ActionBlankOut actionBlankOut = null;
    protected ActionWebMark actionWebMark = null;
    protected ActionCheckPrePurchase actionCheckPrePurchase = null;
    protected ActionUncheckPrePurchase actionUncheckPrePurchase = null;
    protected ActionPurchasePrint actionPurchasePrint = null;
    protected ActionPurchasePrintView actionPurchasePrintView = null;
    protected ActionPrePurchase actionPrePurchase = null;
    protected ActionProofOfPayment actionProofOfPayment = null;
    /**
     * output class constructor
     */
    public AbstractPurchaseListUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractPurchaseListUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "PurchaseQuery");
        //actionAudit
        this.actionAudit = new ActionAudit(this);
        getActionManager().registerAction("actionAudit", actionAudit);
         this.actionAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUnAudit
        this.actionUnAudit = new ActionUnAudit(this);
        getActionManager().registerAction("actionUnAudit", actionUnAudit);
        this.actionUnAudit.setBindWorkFlow(true);
         this.actionUnAudit.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBlankOut
        this.actionBlankOut = new ActionBlankOut(this);
        getActionManager().registerAction("actionBlankOut", actionBlankOut);
         this.actionBlankOut.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionWebMark
        this.actionWebMark = new ActionWebMark(this);
        getActionManager().registerAction("actionWebMark", actionWebMark);
         this.actionWebMark.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionCheckPrePurchase
        this.actionCheckPrePurchase = new ActionCheckPrePurchase(this);
        getActionManager().registerAction("actionCheckPrePurchase", actionCheckPrePurchase);
         this.actionCheckPrePurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionUncheckPrePurchase
        this.actionUncheckPrePurchase = new ActionUncheckPrePurchase(this);
        getActionManager().registerAction("actionUncheckPrePurchase", actionUncheckPrePurchase);
         this.actionUncheckPrePurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPurchasePrint
        this.actionPurchasePrint = new ActionPurchasePrint(this);
        getActionManager().registerAction("actionPurchasePrint", actionPurchasePrint);
         this.actionPurchasePrint.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPurchasePrintView
        this.actionPurchasePrintView = new ActionPurchasePrintView(this);
        getActionManager().registerAction("actionPurchasePrintView", actionPurchasePrintView);
         this.actionPurchasePrintView.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionPrePurchase
        this.actionPrePurchase = new ActionPrePurchase(this);
        getActionManager().registerAction("actionPrePurchase", actionPrePurchase);
         this.actionPrePurchase.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionProofOfPayment
        this.actionProofOfPayment = new ActionProofOfPayment(this);
        getActionManager().registerAction("actionProofOfPayment", actionProofOfPayment);
         this.actionProofOfPayment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.pnlMain = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.treeView = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.treeMain = new com.kingdee.bos.ctrl.swing.KDTree();
        this.btnPrePurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnCheckPrePurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUncheckPrePurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnUnAudit = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBlankOut = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnWebMark = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPurchasePrintView = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPurchasePrint = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnProofOfPayment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemUnAudit = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBlankOut = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuWebMark = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPurchasePrint = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPurchasePrintView = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemProofOfPayment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.pnlMain.setName("pnlMain");
        this.treeView.setName("treeView");
        this.treeMain.setName("treeMain");
        this.btnPrePurchase.setName("btnPrePurchase");
        this.btnCheckPrePurchase.setName("btnCheckPrePurchase");
        this.btnUncheckPrePurchase.setName("btnUncheckPrePurchase");
        this.btnAudit.setName("btnAudit");
        this.btnUnAudit.setName("btnUnAudit");
        this.btnBlankOut.setName("btnBlankOut");
        this.btnWebMark.setName("btnWebMark");
        this.btnPurchasePrintView.setName("btnPurchasePrintView");
        this.btnPurchasePrint.setName("btnPurchasePrint");
        this.btnProofOfPayment.setName("btnProofOfPayment");
        this.menuItemAudit.setName("menuItemAudit");
        this.menuItemUnAudit.setName("menuItemUnAudit");
        this.menuItemBlankOut.setName("menuItemBlankOut");
        this.menuWebMark.setName("menuWebMark");
        this.menuItemPurchasePrint.setName("menuItemPurchasePrint");
        this.menuItemPurchasePrintView.setName("menuItemPurchasePrintView");
        this.menuItemProofOfPayment.setName("menuItemProofOfPayment");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol0\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol4\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol26\"><c:NumberFormat>####-##-##</c:NumberFormat></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol0\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"purchaseState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"subarea.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"gatheringState\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"building.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"floor\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"room.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"roomNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"customer\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"sincerityPurchase.number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"prePurchaseAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"prePurchaseCurrency.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"prePurchaseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"purchaseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"planSignDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"planSignTimeLimit\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"dealAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"dealPrice\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"payType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"salesman.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"prePurchaseAuditor.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"prePurchaseAuditDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"prePurchaseValidDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"creator\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"createTime\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"sellType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"specilAgio\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"desc\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"toPrePurchaseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"toPurchaseDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"toSaleDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{purchaseState}</t:Cell><t:Cell>$Resource{subarea.name}</t:Cell><t:Cell>$Resource{gatheringState}</t:Cell><t:Cell>$Resource{building.name}</t:Cell><t:Cell>$Resource{floor}</t:Cell><t:Cell>$Resource{room.number}</t:Cell><t:Cell>$Resource{roomNo}</t:Cell><t:Cell>$Resource{customer}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{sincerityPurchase.number}</t:Cell><t:Cell>$Resource{prePurchaseAmount}</t:Cell><t:Cell>$Resource{prePurchaseCurrency.name}</t:Cell><t:Cell>$Resource{prePurchaseDate}</t:Cell><t:Cell>$Resource{purchaseDate}</t:Cell><t:Cell>$Resource{planSignDate}</t:Cell><t:Cell>$Resource{planSignTimeLimit}</t:Cell><t:Cell>$Resource{dealAmount}</t:Cell><t:Cell>$Resource{dealPrice}</t:Cell><t:Cell>$Resource{payType.name}</t:Cell><t:Cell>$Resource{salesman.name}</t:Cell><t:Cell>$Resource{prePurchaseAuditor.name}</t:Cell><t:Cell>$Resource{prePurchaseAuditDate}</t:Cell><t:Cell>$Resource{prePurchaseValidDate}</t:Cell><t:Cell>$Resource{creator}</t:Cell><t:Cell>$Resource{createTime}</t:Cell><t:Cell>$Resource{sellType}</t:Cell><t:Cell>$Resource{specilAgio}</t:Cell><t:Cell>$Resource{desc}</t:Cell><t:Cell>$Resource{toPrePurchaseDate}</t:Cell><t:Cell>$Resource{toPurchaseDate}</t:Cell><t:Cell>$Resource{toSaleDate}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"id","number","purchaseState","subarea.name","gatheringState","building.name","buildingFloor.floorAlias","room.displayName","room.roomNo","customerNames","customerPhones","sincerityPurchase.number","prePurchaseAmount","prePurchaseCurrency.name","prePurchaseDate","purchaseDate","planSignDate","planSignTimeLimit","dealAmount","dealPrice","payType.name","salesman.name","prePurchaseAuditor.name","prePurchaseAuditDate","prePurchaseValidDate","creator.name","createTime","sellType","specialAgio","description","toPrePurchaseDate","toPurchaseDate","toSaleDate"});

		
        this.btnCreateTo.setVisible(false);		
        this.btnTraceUp.setVisible(false);		
        this.btnTraceDown.setVisible(false);		
        this.separatorFW4.setVisible(false);
        // pnlMain		
        this.pnlMain.setDividerLocation(200);
        // treeView
        // treeMain
        this.treeMain.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    treeMain_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnPrePurchase
        this.btnPrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionPrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPrePurchase.setText(resHelper.getString("btnPrePurchase.text"));		
        this.btnPrePurchase.setToolTipText(resHelper.getString("btnPrePurchase.toolTipText"));		
        this.btnPrePurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_balancecheck"));
        // btnCheckPrePurchase
        this.btnCheckPrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionCheckPrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnCheckPrePurchase.setText(resHelper.getString("btnCheckPrePurchase.text"));		
        this.btnCheckPrePurchase.setToolTipText(resHelper.getString("btnCheckPrePurchase.toolTipText"));		
        this.btnCheckPrePurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // btnUncheckPrePurchase
        this.btnUncheckPrePurchase.setAction((IItemAction)ActionProxyFactory.getProxy(actionUncheckPrePurchase, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUncheckPrePurchase.setText(resHelper.getString("btnUncheckPrePurchase.text"));		
        this.btnUncheckPrePurchase.setToolTipText(resHelper.getString("btnUncheckPrePurchase.toolTipText"));		
        this.btnUncheckPrePurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // btnAudit
        this.btnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnAudit.setText(resHelper.getString("btnAudit.text"));		
        this.btnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));		
        this.btnAudit.setToolTipText(resHelper.getString("btnAudit.toolTipText"));
        // btnUnAudit
        this.btnUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnUnAudit.setText(resHelper.getString("btnUnAudit.text"));		
        this.btnUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));		
        this.btnUnAudit.setToolTipText(resHelper.getString("btnUnAudit.toolTipText"));
        // btnBlankOut
        this.btnBlankOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionBlankOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBlankOut.setText(resHelper.getString("btnBlankOut.text"));		
        this.btnBlankOut.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
        // btnWebMark
        this.btnWebMark.setAction((IItemAction)ActionProxyFactory.getProxy(actionWebMark, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWebMark.setText(resHelper.getString("btnWebMark.text"));
        // btnPurchasePrintView
        this.btnPurchasePrintView.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchasePrintView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPurchasePrintView.setText(resHelper.getString("btnPurchasePrintView.text"));		
        this.btnPurchasePrintView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // btnPurchasePrint
        this.btnPurchasePrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchasePrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnPurchasePrint.setText(resHelper.getString("btnPurchasePrint.text"));		
        this.btnPurchasePrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // btnProofOfPayment
        this.btnProofOfPayment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProofOfPayment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnProofOfPayment.setText(resHelper.getString("btnProofOfPayment.text"));		
        this.btnProofOfPayment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_assistantcount"));
        // menuItemAudit
        this.menuItemAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemAudit.setText(resHelper.getString("menuItemAudit.text"));		
        this.menuItemAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_audit"));
        // menuItemUnAudit
        this.menuItemUnAudit.setAction((IItemAction)ActionProxyFactory.getProxy(actionUnAudit, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemUnAudit.setText(resHelper.getString("menuItemUnAudit.text"));		
        this.menuItemUnAudit.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_unaudit"));
        // menuItemBlankOut
        this.menuItemBlankOut.setAction((IItemAction)ActionProxyFactory.getProxy(actionBlankOut, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBlankOut.setText(resHelper.getString("menuItemBlankOut.text"));		
        this.menuItemBlankOut.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_blankout"));
        // menuWebMark
        this.menuWebMark.setAction((IItemAction)ActionProxyFactory.getProxy(actionWebMark, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuWebMark.setText(resHelper.getString("menuWebMark.text"));
        // menuItemPurchasePrint
        this.menuItemPurchasePrint.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchasePrint, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPurchasePrint.setText(resHelper.getString("menuItemPurchasePrint.text"));		
        this.menuItemPurchasePrint.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_print"));
        // menuItemPurchasePrintView
        this.menuItemPurchasePrintView.setAction((IItemAction)ActionProxyFactory.getProxy(actionPurchasePrintView, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemPurchasePrintView.setText(resHelper.getString("menuItemPurchasePrintView.text"));		
        this.menuItemPurchasePrintView.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_preview"));
        // menuItemProofOfPayment
        this.menuItemProofOfPayment.setAction((IItemAction)ActionProxyFactory.getProxy(actionProofOfPayment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemProofOfPayment.setText(resHelper.getString("menuItemProofOfPayment.text"));		
        this.menuItemProofOfPayment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_assistantlistaccount"));
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
        pnlMain.setBounds(new Rectangle(16, 5, 979, 611));
        this.add(pnlMain, new KDLayout.Constraints(16, 5, 979, 611, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(tblMain, "right");
        pnlMain.add(treeView, "left");
        //treeView
        treeView.setTree(treeMain);

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
        this.menuBar.add(menuWorkFlow);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        menuEdit.add(kDSeparator3);
        menuEdit.add(menuItemCreateTo);
        menuEdit.add(menuItemCopyTo);
        menuEdit.add(kDSeparator4);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(kDSeparator5);
        menuView.add(menuItemQuery);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemSwitchView);
        menuView.add(separatorView1);
        menuView.add(menuItemTraceUp);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemTraceDown);
        menuView.add(kDSeparator6);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemVoucher);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAudit);
        menuBiz.add(menuItemBlankOut);
        menuBiz.add(menuWebMark);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemPurchasePrint);
        menuBiz.add(menuItemPurchasePrintView);
        menuBiz.add(menuItemProofOfPayment);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuWorkFlow
        menuWorkFlow.add(menuItemViewDoProccess);
        menuWorkFlow.add(menuItemMultiapprove);
        menuWorkFlow.add(menuItemWorkFlowG);
        menuWorkFlow.add(menuItemWorkFlowList);
        menuWorkFlow.add(separatorWF1);
        menuWorkFlow.add(menuItemNextPerson);
        menuWorkFlow.add(menuItemAuditResult);
        menuWorkFlow.add(kDSeparator7);
        menuWorkFlow.add(menuItemSendSmsMessage);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
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
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnCopyTo);
        this.toolBar.add(btnCancel);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnWorkFlowList);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnPrePurchase);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCheckPrePurchase);
        this.toolBar.add(btnUncheckPrePurchase);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnBlankOut);
        this.toolBar.add(btnWebMark);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnPurchasePrintView);
        this.toolBar.add(btnPurchasePrint);
        this.toolBar.add(btnProofOfPayment);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.PurchaseListUIHandler";
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
    }

    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
        //write your code here
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("sincerityPurchase.number"));
        sic.add(new SelectorItemInfo("prePurchaseAmount"));
        sic.add(new SelectorItemInfo("prePurchaseCurrency.name"));
        sic.add(new SelectorItemInfo("prePurchaseDate"));
        sic.add(new SelectorItemInfo("dealAmount"));
        sic.add(new SelectorItemInfo("dealPrice"));
        sic.add(new SelectorItemInfo("payType.name"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("customerNames"));
        sic.add(new SelectorItemInfo("purchaseState"));
        sic.add(new SelectorItemInfo("prePurchaseAuditDate"));
        sic.add(new SelectorItemInfo("prePurchaseValidDate"));
        sic.add(new SelectorItemInfo("prePurchaseAuditor.name"));
        sic.add(new SelectorItemInfo("sellType"));
        sic.add(new SelectorItemInfo("specialAgio"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("subarea.name"));
        sic.add(new SelectorItemInfo("building.name"));
        sic.add(new SelectorItemInfo("buildingFloor.floorAlias"));
        sic.add(new SelectorItemInfo("gatheringState"));
        sic.add(new SelectorItemInfo("room.roomNo"));
        sic.add(new SelectorItemInfo("customerPhones"));
        sic.add(new SelectorItemInfo("purchaseDate"));
        sic.add(new SelectorItemInfo("planSignDate"));
        sic.add(new SelectorItemInfo("planSignTimeLimit"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("toPrePurchaseDate"));
        sic.add(new SelectorItemInfo("toPurchaseDate"));
        sic.add(new SelectorItemInfo("toSaleDate"));
        sic.add(new SelectorItemInfo("room.displayName"));
        return sic;
    }        
    	

    /**
     * output actionAudit_actionPerformed method
     */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUnAudit_actionPerformed method
     */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBlankOut_actionPerformed method
     */
    public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionWebMark_actionPerformed method
     */
    public void actionWebMark_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionCheckPrePurchase_actionPerformed method
     */
    public void actionCheckPrePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionUncheckPrePurchase_actionPerformed method
     */
    public void actionUncheckPrePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPurchasePrint_actionPerformed method
     */
    public void actionPurchasePrint_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPurchasePrintView_actionPerformed method
     */
    public void actionPurchasePrintView_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionPrePurchase_actionPerformed method
     */
    public void actionPrePurchase_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionProofOfPayment_actionPerformed method
     */
    public void actionProofOfPayment_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionAudit() {
    	return false;
    }
	public RequestContext prepareActionUnAudit(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUnAudit() {
    	return false;
    }
	public RequestContext prepareActionBlankOut(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBlankOut() {
    	return false;
    }
	public RequestContext prepareActionWebMark(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionWebMark() {
    	return false;
    }
	public RequestContext prepareActionCheckPrePurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionCheckPrePurchase() {
    	return false;
    }
	public RequestContext prepareActionUncheckPrePurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionUncheckPrePurchase() {
    	return false;
    }
	public RequestContext prepareActionPurchasePrint(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPurchasePrint() {
    	return false;
    }
	public RequestContext prepareActionPurchasePrintView(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPurchasePrintView() {
    	return false;
    }
	public RequestContext prepareActionPrePurchase(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionPrePurchase() {
    	return false;
    }
	public RequestContext prepareActionProofOfPayment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionProofOfPayment() {
    	return false;
    }

    /**
     * output ActionAudit class
     */     
    protected class ActionAudit extends ItemAction {     
    
        public ActionAudit()
        {
            this(null);
        }

        public ActionAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseListUI.this, "ActionAudit", "actionAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionUnAudit class
     */     
    protected class ActionUnAudit extends ItemAction {     
    
        public ActionUnAudit()
        {
            this(null);
        }

        public ActionUnAudit(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUnAudit.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUnAudit.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseListUI.this, "ActionUnAudit", "actionUnAudit_actionPerformed", e);
        }
    }

    /**
     * output ActionBlankOut class
     */     
    protected class ActionBlankOut extends ItemAction {     
    
        public ActionBlankOut()
        {
            this(null);
        }

        public ActionBlankOut(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBlankOut.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBlankOut.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseListUI.this, "ActionBlankOut", "actionBlankOut_actionPerformed", e);
        }
    }

    /**
     * output ActionWebMark class
     */     
    protected class ActionWebMark extends ItemAction {     
    
        public ActionWebMark()
        {
            this(null);
        }

        public ActionWebMark(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.putValue(ItemAction.SMALL_ICON, com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cycattemper"));
            _tempStr = resHelper.getString("ActionWebMark.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWebMark.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWebMark.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseListUI.this, "ActionWebMark", "actionWebMark_actionPerformed", e);
        }
    }

    /**
     * output ActionCheckPrePurchase class
     */     
    protected class ActionCheckPrePurchase extends ItemAction {     
    
        public ActionCheckPrePurchase()
        {
            this(null);
        }

        public ActionCheckPrePurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionCheckPrePurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCheckPrePurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionCheckPrePurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseListUI.this, "ActionCheckPrePurchase", "actionCheckPrePurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionUncheckPrePurchase class
     */     
    protected class ActionUncheckPrePurchase extends ItemAction {     
    
        public ActionUncheckPrePurchase()
        {
            this(null);
        }

        public ActionUncheckPrePurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionUncheckPrePurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUncheckPrePurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionUncheckPrePurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseListUI.this, "ActionUncheckPrePurchase", "actionUncheckPrePurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionPurchasePrint class
     */     
    protected class ActionPurchasePrint extends ItemAction {     
    
        public ActionPurchasePrint()
        {
            this(null);
        }

        public ActionPurchasePrint(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPurchasePrint.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchasePrint.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchasePrint.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseListUI.this, "ActionPurchasePrint", "actionPurchasePrint_actionPerformed", e);
        }
    }

    /**
     * output ActionPurchasePrintView class
     */     
    protected class ActionPurchasePrintView extends ItemAction {     
    
        public ActionPurchasePrintView()
        {
            this(null);
        }

        public ActionPurchasePrintView(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionPurchasePrintView.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchasePrintView.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPurchasePrintView.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseListUI.this, "ActionPurchasePrintView", "actionPurchasePrintView_actionPerformed", e);
        }
    }

    /**
     * output ActionPrePurchase class
     */     
    protected class ActionPrePurchase extends ItemAction {     
    
        public ActionPrePurchase()
        {
            this(null);
        }

        public ActionPrePurchase(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionPrePurchase.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrePurchase.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionPrePurchase.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseListUI.this, "ActionPrePurchase", "actionPrePurchase_actionPerformed", e);
        }
    }

    /**
     * output ActionProofOfPayment class
     */     
    protected class ActionProofOfPayment extends ItemAction {     
    
        public ActionProofOfPayment()
        {
            this(null);
        }

        public ActionProofOfPayment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionProofOfPayment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProofOfPayment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionProofOfPayment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractPurchaseListUI.this, "ActionProofOfPayment", "actionProofOfPayment_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "PurchaseListUI");
    }
    /**
     * output isBindWorkFlow method
     */
    public boolean isBindWorkFlow()
    {
        return true;
    }




}