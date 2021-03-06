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
public abstract class AbstractSellControlUI extends com.kingdee.eas.framework.client.TreeListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractSellControlUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane sclPanel;
    protected com.kingdee.bos.ctrl.swing.KDPanel kDPanel1;
    protected com.kingdee.bos.ctrl.swing.KDScrollPane kDScrollPane1;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSimulateSell;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSinPurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurchase;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReserve;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnKeepDown;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuitRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeRoom;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSignContract;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnReceiveBill;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnChangeName;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnPurchaseChange;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnQuitOrder;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnRefundment;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnBillAdjust;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnZhuanKuan;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemShowRoomDetail;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSinPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPrePurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPurchase;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReserve;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemKeepDown;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuitRoom;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeRoom;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemSignContract;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReceiveBill;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemChangeName;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemPurchaseChange;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemReclaimRoom;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemQuitOrder;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemRefundment;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemBillAdjust;
    protected com.kingdee.bos.ctrl.swing.KDMenuItem menuItemZhuanKuan;
    protected ActionReserve actionReserve = null;
    protected ActionReclaimRoom actionReclaimRoom = null;
    protected ActionQuitOrder actionQuitOrder = null;
    protected ActionRefundment actionRefundment = null;
    protected ActionBillAdjust actionBillAdjust = null;
    protected ActionZhuanKuan actionZhuanKuan = null;
    protected ActionShowRoomDetail actionShowRoomDetail = null;
    protected ActionSimulate actionSimulate = null;
    /**
     * output class constructor
     */
    public AbstractSellControlUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractSellControlUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.base.message", "MsgQuery");
        //actionReserve
        this.actionReserve = new ActionReserve(this);
        getActionManager().registerAction("actionReserve", actionReserve);
         this.actionReserve.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionReclaimRoom
        this.actionReclaimRoom = new ActionReclaimRoom(this);
        getActionManager().registerAction("actionReclaimRoom", actionReclaimRoom);
         this.actionReclaimRoom.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionQuitOrder
        this.actionQuitOrder = new ActionQuitOrder(this);
        getActionManager().registerAction("actionQuitOrder", actionQuitOrder);
         this.actionQuitOrder.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionRefundment
        this.actionRefundment = new ActionRefundment(this);
        getActionManager().registerAction("actionRefundment", actionRefundment);
         this.actionRefundment.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionBillAdjust
        this.actionBillAdjust = new ActionBillAdjust(this);
        getActionManager().registerAction("actionBillAdjust", actionBillAdjust);
         this.actionBillAdjust.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionZhuanKuan
        this.actionZhuanKuan = new ActionZhuanKuan(this);
        getActionManager().registerAction("actionZhuanKuan", actionZhuanKuan);
         this.actionZhuanKuan.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionShowRoomDetail
        this.actionShowRoomDetail = new ActionShowRoomDetail(this);
        getActionManager().registerAction("actionShowRoomDetail", actionShowRoomDetail);
         this.actionShowRoomDetail.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSimulate
        this.actionSimulate = new ActionSimulate(this);
        getActionManager().registerAction("actionSimulate", actionSimulate);
         this.actionSimulate.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.sclPanel = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.kDPanel1 = new com.kingdee.bos.ctrl.swing.KDPanel();
        this.kDScrollPane1 = new com.kingdee.bos.ctrl.swing.KDScrollPane();
        this.btnSimulateSell = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSinPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPrePurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPurchase = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReserve = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnKeepDown = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnQuitRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChangeRoom = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSignContract = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnReceiveBill = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnChangeName = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnPurchaseChange = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnQuitOrder = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnRefundment = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnBillAdjust = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnZhuanKuan = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.menuItemShowRoomDetail = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSinPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPrePurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPurchase = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReserve = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemKeepDown = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuitRoom = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChangeRoom = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemSignContract = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReceiveBill = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemChangeName = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemPurchaseChange = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemReclaimRoom = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemQuitOrder = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemRefundment = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemBillAdjust = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.menuItemZhuanKuan = new com.kingdee.bos.ctrl.swing.KDMenuItem();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.sclPanel.setName("sclPanel");
        this.kDPanel1.setName("kDPanel1");
        this.kDScrollPane1.setName("kDScrollPane1");
        this.btnSimulateSell.setName("btnSimulateSell");
        this.btnSinPurchase.setName("btnSinPurchase");
        this.btnPrePurchase.setName("btnPrePurchase");
        this.btnPurchase.setName("btnPurchase");
        this.btnReserve.setName("btnReserve");
        this.btnKeepDown.setName("btnKeepDown");
        this.btnQuitRoom.setName("btnQuitRoom");
        this.btnChangeRoom.setName("btnChangeRoom");
        this.btnSignContract.setName("btnSignContract");
        this.btnReceiveBill.setName("btnReceiveBill");
        this.btnChangeName.setName("btnChangeName");
        this.btnPurchaseChange.setName("btnPurchaseChange");
        this.btnQuitOrder.setName("btnQuitOrder");
        this.btnRefundment.setName("btnRefundment");
        this.btnBillAdjust.setName("btnBillAdjust");
        this.btnZhuanKuan.setName("btnZhuanKuan");
        this.menuItemShowRoomDetail.setName("menuItemShowRoomDetail");
        this.menuItemSinPurchase.setName("menuItemSinPurchase");
        this.menuItemPrePurchase.setName("menuItemPrePurchase");
        this.menuItemPurchase.setName("menuItemPurchase");
        this.menuItemReserve.setName("menuItemReserve");
        this.menuItemKeepDown.setName("menuItemKeepDown");
        this.menuItemQuitRoom.setName("menuItemQuitRoom");
        this.menuItemChangeRoom.setName("menuItemChangeRoom");
        this.menuItemSignContract.setName("menuItemSignContract");
        this.menuItemReceiveBill.setName("menuItemReceiveBill");
        this.menuItemChangeName.setName("menuItemChangeName");
        this.menuItemPurchaseChange.setName("menuItemPurchaseChange");
        this.menuItemReclaimRoom.setName("menuItemReclaimRoom");
        this.menuItemQuitOrder.setName("menuItemQuitOrder");
        this.menuItemRefundment.setName("menuItemRefundment");
        this.menuItemBillAdjust.setName("menuItemBillAdjust");
        this.menuItemZhuanKuan.setName("menuItemZhuanKuan");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"column1\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\" /></t:Head></t:Table></t:Sheet></Table></DocRoot> ";

                this.tblMain.putBindContents("mainQuery",new String[] {"BMCMessage.id"});

		
        this.pnlMain.setDividerLocation(160);		
        this.pnlMain.setOneTouchExpandable(true);
        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(550);		
        this.kDSplitPane1.setOneTouchExpandable(true);
        // sclPanel
        // kDPanel1		
        this.kDPanel1.setBorder(BorderFactory.createLineBorder(new Color(0,0,0),1));
        // kDScrollPane1		
        this.kDScrollPane1.setPreferredSize(new Dimension(3,75));
        // btnSimulateSell
        this.btnSimulateSell.setAction((IItemAction)ActionProxyFactory.getProxy(actionSimulate, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSimulateSell.setText(resHelper.getString("btnSimulateSell.text"));		
        this.btnSimulateSell.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_cost"));
        // btnSinPurchase		
        this.btnSinPurchase.setText(resHelper.getString("btnSinPurchase.text"));		
        this.btnSinPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTree_businessgroup"));		
        this.btnSinPurchase.setToolTipText(resHelper.getString("btnSinPurchase.toolTipText"));
        // btnPrePurchase		
        this.btnPrePurchase.setText(resHelper.getString("btnPrePurchase.text"));		
        this.btnPrePurchase.setToolTipText(resHelper.getString("btnPrePurchase.toolTipText"));		
        this.btnPrePurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_balancecheck"));
        // btnPurchase		
        this.btnPurchase.setText(resHelper.getString("btnPurchase.text"));		
        this.btnPurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_scatterpurview"));		
        this.btnPurchase.setToolTipText(resHelper.getString("btnPurchase.toolTipText"));
        // btnReserve
        this.btnReserve.setAction((IItemAction)ActionProxyFactory.getProxy(actionReserve, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnReserve.setText(resHelper.getString("btnReserve.text"));		
        this.btnReserve.setToolTipText(resHelper.getString("btnReserve.toolTipText"));
        // btnKeepDown		
        this.btnKeepDown.setText(resHelper.getString("btnKeepDown.text"));		
        this.btnKeepDown.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_downview"));		
        this.btnKeepDown.setToolTipText(resHelper.getString("btnKeepDown.toolTipText"));
        // btnQuitRoom		
        this.btnQuitRoom.setText(resHelper.getString("btnQuitRoom.text"));		
        this.btnQuitRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_quit"));		
        this.btnQuitRoom.setToolTipText(resHelper.getString("btnQuitRoom.toolTipText"));
        // btnChangeRoom		
        this.btnChangeRoom.setText(resHelper.getString("btnChangeRoom.text"));		
        this.btnChangeRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_assetchange"));		
        this.btnChangeRoom.setToolTipText(resHelper.getString("btnChangeRoom.toolTipText"));
        // btnSignContract		
        this.btnSignContract.setText(resHelper.getString("btnSignContract.text"));		
        this.btnSignContract.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_signup"));		
        this.btnSignContract.setToolTipText(resHelper.getString("btnSignContract.toolTipText"));
        // btnReceiveBill		
        this.btnReceiveBill.setText(resHelper.getString("btnReceiveBill.text"));		
        this.btnReceiveBill.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_monadismpostil"));		
        this.btnReceiveBill.setToolTipText(resHelper.getString("btnReceiveBill.toolTipText"));
        // btnChangeName		
        this.btnChangeName.setText(resHelper.getString("btnChangeName.text"));		
        this.btnChangeName.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_rename"));		
        this.btnChangeName.setToolTipText(resHelper.getString("btnChangeName.toolTipText"));
        // btnPurchaseChange		
        this.btnPurchaseChange.setText(resHelper.getString("btnPurchaseChange.text"));		
        this.btnPurchaseChange.setToolTipText(resHelper.getString("btnPurchaseChange.toolTipText"));		
        this.btnPurchaseChange.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // btnQuitOrder
        this.btnQuitOrder.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitOrder, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnQuitOrder.setText(resHelper.getString("btnQuitOrder.text"));		
        this.btnQuitOrder.setToolTipText(resHelper.getString("btnQuitOrder.toolTipText"));		
        this.btnQuitOrder.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_requite"));
        // btnRefundment
        this.btnRefundment.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefundment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnRefundment.setText(resHelper.getString("btnRefundment.text"));		
        this.btnRefundment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_retract"));
        // btnBillAdjust
        this.btnBillAdjust.setAction((IItemAction)ActionProxyFactory.getProxy(actionBillAdjust, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnBillAdjust.setText(resHelper.getString("btnBillAdjust.text"));		
        this.btnBillAdjust.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_checkadjust"));		
        this.btnBillAdjust.setToolTipText(resHelper.getString("btnBillAdjust.toolTipText"));
        // btnZhuanKuan
        this.btnZhuanKuan.setAction((IItemAction)ActionProxyFactory.getProxy(actionZhuanKuan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnZhuanKuan.setText(resHelper.getString("btnZhuanKuan.text"));		
        this.btnZhuanKuan.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_redclash"));
        // menuItemShowRoomDetail
        this.menuItemShowRoomDetail.setAction((IItemAction)ActionProxyFactory.getProxy(actionShowRoomDetail, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemShowRoomDetail.setVisible(false);		
        this.menuItemShowRoomDetail.setText(resHelper.getString("menuItemShowRoomDetail.text"));
        // menuItemSinPurchase		
        this.menuItemSinPurchase.setText(resHelper.getString("menuItemSinPurchase.text"));
        // menuItemPrePurchase		
        this.menuItemPrePurchase.setText(resHelper.getString("menuItemPrePurchase.text"));		
        this.menuItemPrePurchase.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_balancecheck"));
        // menuItemPurchase		
        this.menuItemPurchase.setText(resHelper.getString("menuItemPurchase.text"));
        // menuItemReserve
        this.menuItemReserve.setAction((IItemAction)ActionProxyFactory.getProxy(actionReserve, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReserve.setText(resHelper.getString("menuItemReserve.text"));
        // menuItemKeepDown		
        this.menuItemKeepDown.setText(resHelper.getString("menuItemKeepDown.text"));
        // menuItemQuitRoom		
        this.menuItemQuitRoom.setText(resHelper.getString("menuItemQuitRoom.text"));
        // menuItemChangeRoom		
        this.menuItemChangeRoom.setText(resHelper.getString("menuItemChangeRoom.text"));
        // menuItemSignContract		
        this.menuItemSignContract.setText(resHelper.getString("menuItemSignContract.text"));
        // menuItemReceiveBill		
        this.menuItemReceiveBill.setText(resHelper.getString("menuItemReceiveBill.text"));
        // menuItemChangeName		
        this.menuItemChangeName.setText(resHelper.getString("menuItemChangeName.text"));
        // menuItemPurchaseChange		
        this.menuItemPurchaseChange.setText(resHelper.getString("menuItemPurchaseChange.text"));		
        this.menuItemPurchaseChange.setToolTipText(resHelper.getString("menuItemPurchaseChange.toolTipText"));		
        this.menuItemPurchaseChange.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_edit"));
        // menuItemReclaimRoom
        this.menuItemReclaimRoom.setAction((IItemAction)ActionProxyFactory.getProxy(actionReclaimRoom, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemReclaimRoom.setText(resHelper.getString("menuItemReclaimRoom.text"));		
        this.menuItemReclaimRoom.setToolTipText(resHelper.getString("menuItemReclaimRoom.toolTipText"));		
        this.menuItemReclaimRoom.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_declarecollect"));
        // menuItemQuitOrder
        this.menuItemQuitOrder.setAction((IItemAction)ActionProxyFactory.getProxy(actionQuitOrder, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemQuitOrder.setText(resHelper.getString("menuItemQuitOrder.text"));		
        this.menuItemQuitOrder.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_requite"));		
        this.menuItemQuitOrder.setToolTipText(resHelper.getString("menuItemQuitOrder.toolTipText"));
        // menuItemRefundment
        this.menuItemRefundment.setAction((IItemAction)ActionProxyFactory.getProxy(actionRefundment, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemRefundment.setText(resHelper.getString("menuItemRefundment.text"));		
        this.menuItemRefundment.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_retract"));
        // menuItemBillAdjust
        this.menuItemBillAdjust.setAction((IItemAction)ActionProxyFactory.getProxy(actionBillAdjust, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemBillAdjust.setText(resHelper.getString("menuItemBillAdjust.text"));		
        this.menuItemBillAdjust.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_checkadjust"));		
        this.menuItemBillAdjust.setToolTipText(resHelper.getString("menuItemBillAdjust.toolTipText"));
        // menuItemZhuanKuan
        this.menuItemZhuanKuan.setAction((IItemAction)ActionProxyFactory.getProxy(actionZhuanKuan, new Class[] { IItemAction.class }, getServiceContext()));		
        this.menuItemZhuanKuan.setText(resHelper.getString("menuItemZhuanKuan.text"));		
        this.menuItemZhuanKuan.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_redclash"));
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
        this.setBounds(new Rectangle(10, 10, 800, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 800, 600));
        pnlMain.setBounds(new Rectangle(10, 9, 784, 580));
        this.add(pnlMain, new KDLayout.Constraints(10, 9, 784, 580, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //pnlMain
        pnlMain.add(treeView, "left");
        pnlMain.add(kDSplitPane1, "right");
        //treeView
        treeView.setTree(treeMain);
        //kDSplitPane1
        kDSplitPane1.add(sclPanel, "right");
        kDSplitPane1.add(kDPanel1, "left");
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(tblMain, BorderLayout.CENTER);
        kDPanel1.add(kDScrollPane1, BorderLayout.SOUTH);

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
        menuEdit.add(separatorEdit1);
        menuEdit.add(menuItemMoveTree);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemRefresh);
        menuView.add(menuItemShowRoomDetail);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(menuItemSinPurchase);
        menuBiz.add(menuItemPrePurchase);
        menuBiz.add(menuItemPurchase);
        menuBiz.add(menuItemReserve);
        menuBiz.add(menuItemKeepDown);
        menuBiz.add(menuItemQuitRoom);
        menuBiz.add(menuItemChangeRoom);
        menuBiz.add(menuItemSignContract);
        menuBiz.add(menuItemReceiveBill);
        menuBiz.add(menuItemChangeName);
        menuBiz.add(menuItemPurchaseChange);
        menuBiz.add(menuItemReclaimRoom);
        menuBiz.add(menuItemQuitOrder);
        menuBiz.add(menuItemRefundment);
        menuBiz.add(menuItemBillAdjust);
        menuBiz.add(menuItemZhuanKuan);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
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
        this.toolBar.add(btnMoveTree);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnSimulateSell);
        this.toolBar.add(btnSinPurchase);
        this.toolBar.add(btnPrePurchase);
        this.toolBar.add(btnPurchase);
        this.toolBar.add(btnReserve);
        this.toolBar.add(btnKeepDown);
        this.toolBar.add(btnQuitRoom);
        this.toolBar.add(btnChangeRoom);
        this.toolBar.add(btnSignContract);
        this.toolBar.add(btnReceiveBill);
        this.toolBar.add(btnChangeName);
        this.toolBar.add(btnPurchaseChange);
        this.toolBar.add(btnQuitOrder);
        this.toolBar.add(btnRefundment);
        this.toolBar.add(btnBillAdjust);
        this.toolBar.add(btnZhuanKuan);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.SellControlUIHandler";
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
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        
    	

    /**
     * output actionReserve_actionPerformed method
     */
    public void actionReserve_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionReclaimRoom_actionPerformed method
     */
    public void actionReclaimRoom_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionQuitOrder_actionPerformed method
     */
    public void actionQuitOrder_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionRefundment_actionPerformed method
     */
    public void actionRefundment_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionBillAdjust_actionPerformed method
     */
    public void actionBillAdjust_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionZhuanKuan_actionPerformed method
     */
    public void actionZhuanKuan_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionShowRoomDetail_actionPerformed method
     */
    public void actionShowRoomDetail_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSimulate_actionPerformed method
     */
    public void actionSimulate_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionReserve(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReserve() {
    	return false;
    }
	public RequestContext prepareActionReclaimRoom(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionReclaimRoom() {
    	return false;
    }
	public RequestContext prepareActionQuitOrder(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionQuitOrder() {
    	return false;
    }
	public RequestContext prepareActionRefundment(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionRefundment() {
    	return false;
    }
	public RequestContext prepareActionBillAdjust(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionBillAdjust() {
    	return false;
    }
	public RequestContext prepareActionZhuanKuan(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionZhuanKuan() {
    	return false;
    }
	public RequestContext prepareActionShowRoomDetail(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionShowRoomDetail() {
    	return false;
    }
	public RequestContext prepareActionSimulate(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSimulate() {
    	return false;
    }

    /**
     * output ActionReserve class
     */     
    protected class ActionReserve extends ItemAction {     
    
        public ActionReserve()
        {
            this(null);
        }

        public ActionReserve(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReserve.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReserve.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReserve.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellControlUI.this, "ActionReserve", "actionReserve_actionPerformed", e);
        }
    }

    /**
     * output ActionReclaimRoom class
     */     
    protected class ActionReclaimRoom extends ItemAction {     
    
        public ActionReclaimRoom()
        {
            this(null);
        }

        public ActionReclaimRoom(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionReclaimRoom.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReclaimRoom.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionReclaimRoom.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellControlUI.this, "ActionReclaimRoom", "actionReclaimRoom_actionPerformed", e);
        }
    }

    /**
     * output ActionQuitOrder class
     */     
    protected class ActionQuitOrder extends ItemAction {     
    
        public ActionQuitOrder()
        {
            this(null);
        }

        public ActionQuitOrder(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionQuitOrder.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitOrder.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionQuitOrder.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellControlUI.this, "ActionQuitOrder", "actionQuitOrder_actionPerformed", e);
        }
    }

    /**
     * output ActionRefundment class
     */     
    protected class ActionRefundment extends ItemAction {     
    
        public ActionRefundment()
        {
            this(null);
        }

        public ActionRefundment(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionRefundment.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefundment.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionRefundment.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellControlUI.this, "ActionRefundment", "actionRefundment_actionPerformed", e);
        }
    }

    /**
     * output ActionBillAdjust class
     */     
    protected class ActionBillAdjust extends ItemAction {     
    
        public ActionBillAdjust()
        {
            this(null);
        }

        public ActionBillAdjust(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionBillAdjust.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBillAdjust.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionBillAdjust.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellControlUI.this, "ActionBillAdjust", "actionBillAdjust_actionPerformed", e);
        }
    }

    /**
     * output ActionZhuanKuan class
     */     
    protected class ActionZhuanKuan extends ItemAction {     
    
        public ActionZhuanKuan()
        {
            this(null);
        }

        public ActionZhuanKuan(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionZhuanKuan.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZhuanKuan.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionZhuanKuan.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellControlUI.this, "ActionZhuanKuan", "actionZhuanKuan_actionPerformed", e);
        }
    }

    /**
     * output ActionShowRoomDetail class
     */     
    protected class ActionShowRoomDetail extends ItemAction {     
    
        public ActionShowRoomDetail()
        {
            this(null);
        }

        public ActionShowRoomDetail(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setDaemonRun(true);
            _tempStr = resHelper.getString("ActionShowRoomDetail.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowRoomDetail.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionShowRoomDetail.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellControlUI.this, "ActionShowRoomDetail", "actionShowRoomDetail_actionPerformed", e);
        }
    }

    /**
     * output ActionSimulate class
     */     
    protected class ActionSimulate extends ItemAction {     
    
        public ActionSimulate()
        {
            this(null);
        }

        public ActionSimulate(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionSimulate.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSimulate.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSimulate.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractSellControlUI.this, "ActionSimulate", "actionSimulate_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "SellControlUI");
    }




}