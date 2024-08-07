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
public abstract class AbstractImportExcelFDCCustomerUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractImportExcelFDCCustomerUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contFilePath;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contImportType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contExcelTotalNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contInvaluidCount;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtFilePath;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboImportType;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtExcelCount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtInvaluidCount;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnImportExcel;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnSave;
    protected ActionImportFromExcel actionImportFromExcel = null;
    protected ActionSaveImport actionSaveImport = null;
    /**
     * output class constructor
     */
    public AbstractImportExcelFDCCustomerUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractImportExcelFDCCustomerUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "CustomerQuery");
        //actionImportFromExcel
        this.actionImportFromExcel = new ActionImportFromExcel(this);
        getActionManager().registerAction("actionImportFromExcel", actionImportFromExcel);
         this.actionImportFromExcel.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionSaveImport
        this.actionSaveImport = new ActionSaveImport(this);
        getActionManager().registerAction("actionSaveImport", actionSaveImport);
         this.actionSaveImport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.contFilePath = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contImportType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contExcelTotalNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contInvaluidCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtFilePath = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.comboImportType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.txtExcelCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.txtInvaluidCount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.btnImportExcel = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnSave = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.contFilePath.setName("contFilePath");
        this.contImportType.setName("contImportType");
        this.contExcelTotalNum.setName("contExcelTotalNum");
        this.contInvaluidCount.setName("contInvaluidCount");
        this.txtFilePath.setName("txtFilePath");
        this.comboImportType.setName("comboImportType");
        this.txtExcelCount.setName("txtExcelCount");
        this.txtInvaluidCount.setName("txtInvaluidCount");
        this.btnImportExcel.setName("btnImportExcel");
        this.btnSave.setName("btnSave");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles /><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"id\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"importStatus\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"number\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"salesman.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"project.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" /><t:Column t:key=\"classify\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" /><t:Column t:key=\"province.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" /><t:Column t:key=\"city.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" /><t:Column t:key=\"level.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" /><t:Column t:key=\"receptionType.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" /><t:Column t:key=\"firstDate\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" /><t:Column t:key=\"phone\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" /><t:Column t:key=\"fax\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" /><t:Column t:key=\"email\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" /><t:Column t:key=\"postalcode\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" /><t:Column t:key=\"address\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" /><t:Column t:key=\"mailAddress\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" /><t:Column t:key=\"businessNature\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" /><t:Column t:key=\"zz\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" /><t:Column t:key=\"area\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" /><t:Column t:key=\"zb\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" /><t:Column t:key=\"description\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" /><t:Column t:key=\"fr\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" /><t:Column t:key=\"clrq\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" /><t:Column t:key=\"zczj\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" /><t:Column t:key=\"bb\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" /><t:Column t:key=\"gyzs\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" /><t:Column t:key=\"yyzz\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" /><t:Column t:key=\"taxNo\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" /><t:Column t:key=\"gszch\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" /><t:Column t:key=\"remark\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{id}</t:Cell><t:Cell>$Resource{importStatus}</t:Cell><t:Cell>$Resource{number}</t:Cell><t:Cell>$Resource{name}</t:Cell><t:Cell>$Resource{salesman.name}</t:Cell><t:Cell>$Resource{project.name}</t:Cell><t:Cell>$Resource{classify}</t:Cell><t:Cell>$Resource{province.name}</t:Cell><t:Cell>$Resource{city.name}</t:Cell><t:Cell>$Resource{level.name}</t:Cell><t:Cell>$Resource{receptionType.name}</t:Cell><t:Cell>$Resource{firstDate}</t:Cell><t:Cell>$Resource{phone}</t:Cell><t:Cell>$Resource{fax}</t:Cell><t:Cell>$Resource{email}</t:Cell><t:Cell>$Resource{postalcode}</t:Cell><t:Cell>$Resource{address}</t:Cell><t:Cell>$Resource{mailAddress}</t:Cell><t:Cell>$Resource{businessNature}</t:Cell><t:Cell>$Resource{zz}</t:Cell><t:Cell>$Resource{area}</t:Cell><t:Cell>$Resource{zb}</t:Cell><t:Cell>$Resource{description}</t:Cell><t:Cell>$Resource{fr}</t:Cell><t:Cell>$Resource{clrq}</t:Cell><t:Cell>$Resource{zczj}</t:Cell><t:Cell>$Resource{bb}</t:Cell><t:Cell>$Resource{gyzs}</t:Cell><t:Cell>$Resource{yyzz}</t:Cell><t:Cell>$Resource{taxNo}</t:Cell><t:Cell>$Resource{gszch}</t:Cell><t:Cell>$Resource{remark}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head /></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
        this.tblMain.addKDTEditListener(new com.kingdee.bos.ctrl.kdf.table.event.KDTEditAdapter() {
        });
                this.tblMain.putBindContents("mainQuery",new String[] {"id","","number","name","salesman.name","project.name","","province.name","","","receptionType.name","","phone","fax","email","postalcode","","mailAddress","businessNature.name","","","","description","","","","","","","","",""});

		
        this.btnCancelCancel.setVisible(true);
        // contFilePath		
        this.contFilePath.setBoundLabelText(resHelper.getString("contFilePath.boundLabelText"));		
        this.contFilePath.setBoundLabelLength(100);		
        this.contFilePath.setBoundLabelUnderline(true);
        // contImportType		
        this.contImportType.setBoundLabelText(resHelper.getString("contImportType.boundLabelText"));		
        this.contImportType.setBoundLabelLength(100);		
        this.contImportType.setBoundLabelUnderline(true);
        // contExcelTotalNum		
        this.contExcelTotalNum.setBoundLabelText(resHelper.getString("contExcelTotalNum.boundLabelText"));		
        this.contExcelTotalNum.setBoundLabelUnderline(true);		
        this.contExcelTotalNum.setBoundLabelLength(100);
        // contInvaluidCount		
        this.contInvaluidCount.setBoundLabelText(resHelper.getString("contInvaluidCount.boundLabelText"));		
        this.contInvaluidCount.setBoundLabelLength(100);		
        this.contInvaluidCount.setBoundLabelUnderline(true);
        // txtFilePath
        // comboImportType		
        this.comboImportType.addItems(resHelper.getArray("comboImportType.items"));		
        this.comboImportType.setEnabled(false);
        // txtExcelCount		
        this.txtExcelCount.setPrecision(0);
        // txtInvaluidCount		
        this.txtInvaluidCount.setPrecision(0);
        // btnImportExcel
        this.btnImportExcel.setAction((IItemAction)ActionProxyFactory.getProxy(actionImportFromExcel, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnImportExcel.setText(resHelper.getString("btnImportExcel.text"));		
        this.btnImportExcel.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_importexcel"));
        // btnSave
        this.btnSave.setAction((IItemAction)ActionProxyFactory.getProxy(actionSaveImport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnSave.setText(resHelper.getString("btnSave.text"));		
        this.btnSave.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_save"));
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
        tblMain.setBounds(new Rectangle(10, 65, 996, 554));
        this.add(tblMain, new KDLayout.Constraints(10, 65, 996, 554, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contFilePath.setBounds(new Rectangle(10, 8, 480, 19));
        this.add(contFilePath, new KDLayout.Constraints(10, 8, 480, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contImportType.setBounds(new Rectangle(10, 37, 270, 19));
        this.add(contImportType, new KDLayout.Constraints(10, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contExcelTotalNum.setBounds(new Rectangle(526, 8, 270, 19));
        this.add(contExcelTotalNum, new KDLayout.Constraints(526, 8, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        contInvaluidCount.setBounds(new Rectangle(526, 37, 270, 19));
        this.add(contInvaluidCount, new KDLayout.Constraints(526, 37, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT));
        //contFilePath
        contFilePath.setBoundEditor(txtFilePath);
        //contImportType
        contImportType.setBoundEditor(comboImportType);
        //contExcelTotalNum
        contExcelTotalNum.setBoundEditor(txtExcelCount);
        //contInvaluidCount
        contInvaluidCount.setBoundEditor(txtInvaluidCount);

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
        menuFile.add(menuItemCloudFeed);
        menuFile.add(menuItemExportData);
        menuFile.add(menuItemCloudScreen);
        menuFile.add(separatorFile1);
        menuFile.add(menuItemCloudShare);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(kdSeparatorFWFile1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
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
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        menuTool.add(menuItemToolBarCustom);
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
        this.toolBar.add(btnCloud);
        this.toolBar.add(btnView);
        this.toolBar.add(btnXunTong);
        this.toolBar.add(btnEdit);
        this.toolBar.add(kDSeparatorCloud);
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
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);
        this.toolBar.add(btnImportExcel);
        this.toolBar.add(btnSave);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.ImportExcelFDCCustomerUIHandler";
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
	 * ????????��??
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
     * output tblMain_editStopped method
     */
    protected void tblMain_editStopped(com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent e) throws Exception
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
        sic.add(new SelectorItemInfo("number"));
        sic.add(new SelectorItemInfo("name"));
        sic.add(new SelectorItemInfo("sex"));
        sic.add(new SelectorItemInfo("isImportantTrack"));
        sic.add(new SelectorItemInfo("phone"));
        sic.add(new SelectorItemInfo("lastTrackDate"));
        sic.add(new SelectorItemInfo("email"));
        sic.add(new SelectorItemInfo("customerOrigin.name"));
        sic.add(new SelectorItemInfo("receptionType.name"));
        sic.add(new SelectorItemInfo("customerGrade.name"));
        sic.add(new SelectorItemInfo("workCompany"));
        sic.add(new SelectorItemInfo("employment"));
        sic.add(new SelectorItemInfo("bookedPlace"));
        sic.add(new SelectorItemInfo("certificateName"));
        sic.add(new SelectorItemInfo("certificateNumber"));
        sic.add(new SelectorItemInfo("mailAddress"));
        sic.add(new SelectorItemInfo("postalcode"));
        sic.add(new SelectorItemInfo("province.name"));
        sic.add(new SelectorItemInfo("id"));
        sic.add(new SelectorItemInfo("famillyEarning.name"));
        sic.add(new SelectorItemInfo("project.name"));
        sic.add(new SelectorItemInfo("customerType"));
        sic.add(new SelectorItemInfo("salesman.name"));
        sic.add(new SelectorItemInfo("isEnabled"));
        sic.add(new SelectorItemInfo("tradeTime"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("createTime"));
        sic.add(new SelectorItemInfo("habitationArea.name"));
        sic.add(new SelectorItemInfo("creator.name"));
        sic.add(new SelectorItemInfo("tenTradeTime"));
        sic.add(new SelectorItemInfo("isForSHE"));
        sic.add(new SelectorItemInfo("isForPPM"));
        sic.add(new SelectorItemInfo("isForTen"));
        sic.add(new SelectorItemInfo("saleTrackPhase"));
        sic.add(new SelectorItemInfo("tenancyTrackPhase"));
        sic.add(new SelectorItemInfo("enterpriceSize"));
        sic.add(new SelectorItemInfo("enterpriceHomepage"));
        sic.add(new SelectorItemInfo("industry.name"));
        sic.add(new SelectorItemInfo("tel"));
        sic.add(new SelectorItemInfo("phone2"));
        sic.add(new SelectorItemInfo("officeTel"));
        sic.add(new SelectorItemInfo("QQ"));
        sic.add(new SelectorItemInfo("fax"));
        sic.add(new SelectorItemInfo("cooperateMode.name"));
        sic.add(new SelectorItemInfo("country.name"));
        sic.add(new SelectorItemInfo("customerManager.name"));
        sic.add(new SelectorItemInfo("businessNature.name"));
        return sic;
    }        
    	

    /**
     * output actionImportFromExcel_actionPerformed method
     */
    public void actionImportFromExcel_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionSaveImport_actionPerformed method
     */
    public void actionSaveImport_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionImportFromExcel(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionImportFromExcel() {
    	return false;
    }
	public RequestContext prepareActionSaveImport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionSaveImport() {
    	return false;
    }

    /**
     * output ActionImportFromExcel class
     */     
    protected class ActionImportFromExcel extends ItemAction {     
    
        public ActionImportFromExcel()
        {
            this(null);
        }

        public ActionImportFromExcel(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionImportFromExcel.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportFromExcel.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionImportFromExcel.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportExcelFDCCustomerUI.this, "ActionImportFromExcel", "actionImportFromExcel_actionPerformed", e);
        }
    }

    /**
     * output ActionSaveImport class
     */     
    protected class ActionSaveImport extends ItemAction {     
    
        public ActionSaveImport()
        {
            this(null);
        }

        public ActionSaveImport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionSaveImport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveImport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionSaveImport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractImportExcelFDCCustomerUI.this, "ActionSaveImport", "actionSaveImport_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "ImportExcelFDCCustomerUI");
    }




}