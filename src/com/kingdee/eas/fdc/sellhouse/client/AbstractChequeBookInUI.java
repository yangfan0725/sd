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
public abstract class AbstractChequeBookInUI extends com.kingdee.eas.framework.client.EditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractChequeBookInUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreator;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Creator;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCreateTime;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contDescription;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtDescription;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contChequeType;
    protected com.kingdee.bos.ctrl.swing.KDComboBox comboChequeType;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCurrency;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Currency;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contKeeper;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7Keeper;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contKeepOrgUnit;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox f7KeepOrgUnit;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contLimitAmount;
    protected com.kingdee.bos.ctrl.swing.KDFormattedTextField txtLimitAmount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCount;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinCount;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartNum;
    protected com.kingdee.bos.ctrl.swing.KDSpinner spinStartNum;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCodeRule;
    protected com.kingdee.bos.ctrl.swing.KDTextField txtCodeRule;
    protected com.kingdee.bos.ctrl.swing.KDTextArea txtRemark;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkCreateTime;
    protected com.kingdee.eas.fdc.sellhouse.ChequeInfo editData = null;
    /**
     * output class constructor
     */
    public AbstractChequeBookInUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractChequeBookInUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contCreator = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Creator = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contCreateTime = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contDescription = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtDescription = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.contChequeType = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.comboChequeType = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.contCurrency = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Currency = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contKeeper = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7Keeper = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contKeepOrgUnit = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.f7KeepOrgUnit = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contLimitAmount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtLimitAmount = new com.kingdee.bos.ctrl.swing.KDFormattedTextField();
        this.contCount = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spinCount = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contStartNum = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.spinStartNum = new com.kingdee.bos.ctrl.swing.KDSpinner();
        this.contCodeRule = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.txtCodeRule = new com.kingdee.bos.ctrl.swing.KDTextField();
        this.txtRemark = new com.kingdee.bos.ctrl.swing.KDTextArea();
        this.pkCreateTime = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.contCreator.setName("contCreator");
        this.f7Creator.setName("f7Creator");
        this.contCreateTime.setName("contCreateTime");
        this.contDescription.setName("contDescription");
        this.txtDescription.setName("txtDescription");
        this.contChequeType.setName("contChequeType");
        this.comboChequeType.setName("comboChequeType");
        this.contCurrency.setName("contCurrency");
        this.f7Currency.setName("f7Currency");
        this.contKeeper.setName("contKeeper");
        this.f7Keeper.setName("f7Keeper");
        this.contKeepOrgUnit.setName("contKeepOrgUnit");
        this.f7KeepOrgUnit.setName("f7KeepOrgUnit");
        this.contLimitAmount.setName("contLimitAmount");
        this.txtLimitAmount.setName("txtLimitAmount");
        this.contCount.setName("contCount");
        this.spinCount.setName("spinCount");
        this.contStartNum.setName("contStartNum");
        this.spinStartNum.setName("spinStartNum");
        this.contCodeRule.setName("contCodeRule");
        this.txtCodeRule.setName("txtCodeRule");
        this.txtRemark.setName("txtRemark");
        this.pkCreateTime.setName("pkCreateTime");
        // CoreUI
        // contCreator		
        this.contCreator.setBoundLabelText(resHelper.getString("contCreator.boundLabelText"));		
        this.contCreator.setBoundLabelLength(100);		
        this.contCreator.setBoundLabelUnderline(true);
        // f7Creator		
        this.f7Creator.setQueryInfo("com.kingdee.eas.base.permission.app.F7UserQuery");		
        this.f7Creator.setCommitFormat("$number$");		
        this.f7Creator.setDisplayFormat("$name$");		
        this.f7Creator.setEditFormat("$number$");
        // contCreateTime		
        this.contCreateTime.setBoundLabelText(resHelper.getString("contCreateTime.boundLabelText"));		
        this.contCreateTime.setBoundLabelLength(100);		
        this.contCreateTime.setBoundLabelUnderline(true);
        // contDescription		
        this.contDescription.setBoundLabelText(resHelper.getString("contDescription.boundLabelText"));		
        this.contDescription.setBoundLabelLength(100);		
        this.contDescription.setBoundLabelUnderline(true);
        // txtDescription		
        this.txtDescription.setMaxLength(300);
        // contChequeType		
        this.contChequeType.setBoundLabelText(resHelper.getString("contChequeType.boundLabelText"));		
        this.contChequeType.setBoundLabelLength(100);		
        this.contChequeType.setBoundLabelUnderline(true);
        // comboChequeType		
        this.comboChequeType.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum").toArray());
        // contCurrency		
        this.contCurrency.setBoundLabelText(resHelper.getString("contCurrency.boundLabelText"));		
        this.contCurrency.setBoundLabelLength(100);		
        this.contCurrency.setBoundLabelUnderline(true);
        // f7Currency		
        this.f7Currency.setQueryInfo("com.kingdee.eas.basedata.assistant.app.CurrencyQuery");		
        this.f7Currency.setCommitFormat("$number$");		
        this.f7Currency.setDisplayFormat("$name$");		
        this.f7Currency.setEditFormat("$number$");
        // contKeeper		
        this.contKeeper.setBoundLabelText(resHelper.getString("contKeeper.boundLabelText"));		
        this.contKeeper.setBoundLabelLength(100);		
        this.contKeeper.setBoundLabelUnderline(true);
        // f7Keeper		
        this.f7Keeper.setQueryInfo("com.kingdee.eas.fdc.sellhouse.app.F7UserQuery");		
        this.f7Keeper.setCommitFormat("$number$");		
        this.f7Keeper.setDisplayFormat("$name$");		
        this.f7Keeper.setEditFormat("$number$");
        // contKeepOrgUnit		
        this.contKeepOrgUnit.setBoundLabelText(resHelper.getString("contKeepOrgUnit.boundLabelText"));		
        this.contKeepOrgUnit.setBoundLabelLength(100);		
        this.contKeepOrgUnit.setBoundLabelUnderline(true);
        // f7KeepOrgUnit		
        this.f7KeepOrgUnit.setQueryInfo("com.kingdee.eas.basedata.org.app.SaleOrgUnitQuery");		
        this.f7KeepOrgUnit.setCommitFormat("$number$");		
        this.f7KeepOrgUnit.setDisplayFormat("$name$");		
        this.f7KeepOrgUnit.setEditFormat("$number$");
        // contLimitAmount		
        this.contLimitAmount.setBoundLabelText(resHelper.getString("contLimitAmount.boundLabelText"));		
        this.contLimitAmount.setBoundLabelLength(100);		
        this.contLimitAmount.setBoundLabelUnderline(true);
        // txtLimitAmount		
        this.txtLimitAmount.setDataType(1);
        // contCount		
        this.contCount.setBoundLabelText(resHelper.getString("contCount.boundLabelText"));		
        this.contCount.setBoundLabelLength(100);		
        this.contCount.setBoundLabelUnderline(true);
        // spinCount		
        this.spinCount.setRequired(true);
        // contStartNum		
        this.contStartNum.setBoundLabelText(resHelper.getString("contStartNum.boundLabelText"));		
        this.contStartNum.setBoundLabelLength(100);		
        this.contStartNum.setBoundLabelUnderline(true);
        // spinStartNum		
        this.spinStartNum.setRequired(true);
        // contCodeRule		
        this.contCodeRule.setBoundLabelText(resHelper.getString("contCodeRule.boundLabelText"));		
        this.contCodeRule.setBoundLabelLength(100);		
        this.contCodeRule.setBoundLabelUnderline(true);
        // txtCodeRule		
        this.txtCodeRule.setText(resHelper.getString("txtCodeRule.text"));		
        this.txtCodeRule.setRequired(true);		
        this.txtCodeRule.setMaxLength(80);
        this.txtCodeRule.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                try {
                    txtCodeRule_propertyChange(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        this.txtCodeRule.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) {
                try {
                    txtCodeRule_focusLost(e);
                } catch(Exception exc) {
                    handUIException(exc);
                }
            }
        });
        // txtRemark		
        this.txtRemark.setText(resHelper.getString("txtRemark.text"));		
        this.txtRemark.setLineWrap(true);		
        this.txtRemark.setWrapStyleWord(true);		
        this.txtRemark.setEditable(false);		
        this.txtRemark.setMaxLength(255);
        // pkCreateTime
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
        this.setBounds(new Rectangle(10, 10, 880, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 880, 600));
        contCreator.setBounds(new Rectangle(593, 70, 270, 19));
        this.add(contCreator, new KDLayout.Constraints(593, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contCreateTime.setBounds(new Rectangle(10, 100, 270, 19));
        this.add(contCreateTime, new KDLayout.Constraints(10, 100, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contDescription.setBounds(new Rectangle(303, 100, 562, 19));
        this.add(contDescription, new KDLayout.Constraints(303, 100, 562, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contChequeType.setBounds(new Rectangle(10, 10, 270, 19));
        this.add(contChequeType, new KDLayout.Constraints(10, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCurrency.setBounds(new Rectangle(593, 10, 270, 19));
        this.add(contCurrency, new KDLayout.Constraints(593, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contKeeper.setBounds(new Rectangle(10, 70, 270, 19));
        this.add(contKeeper, new KDLayout.Constraints(10, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contKeepOrgUnit.setBounds(new Rectangle(303, 70, 270, 19));
        this.add(contKeepOrgUnit, new KDLayout.Constraints(303, 70, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contLimitAmount.setBounds(new Rectangle(303, 10, 270, 19));
        this.add(contLimitAmount, new KDLayout.Constraints(303, 10, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCount.setBounds(new Rectangle(10, 40, 270, 19));
        this.add(contCount, new KDLayout.Constraints(10, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contStartNum.setBounds(new Rectangle(303, 40, 270, 19));
        this.add(contStartNum, new KDLayout.Constraints(303, 40, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contCodeRule.setBounds(new Rectangle(593, 40, 269, 19));
        this.add(contCodeRule, new KDLayout.Constraints(593, 40, 269, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        txtRemark.setBounds(new Rectangle(10, 130, 853, 160));
        this.add(txtRemark, new KDLayout.Constraints(10, 130, 853, 160, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreator
        contCreator.setBoundEditor(f7Creator);
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contDescription
        contDescription.setBoundEditor(txtDescription);
        //contChequeType
        contChequeType.setBoundEditor(comboChequeType);
        //contCurrency
        contCurrency.setBoundEditor(f7Currency);
        //contKeeper
        contKeeper.setBoundEditor(f7Keeper);
        //contKeepOrgUnit
        contKeepOrgUnit.setBoundEditor(f7KeepOrgUnit);
        //contLimitAmount
        contLimitAmount.setBoundEditor(txtLimitAmount);
        //contCount
        contCount.setBoundEditor(spinCount);
        //contStartNum
        contStartNum.setBoundEditor(spinStartNum);
        //contCodeRule
        contCodeRule.setBoundEditor(txtCodeRule);

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
        this.toolBar.add(btnReset);
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
		dataBinder.registerBinding("creator", com.kingdee.eas.base.permission.UserInfo.class, this.f7Creator, "data");
		dataBinder.registerBinding("description", String.class, this.txtDescription, "text");
		dataBinder.registerBinding("chequeType", com.kingdee.eas.fdc.sellhouse.ChequeTypeEnum.class, this.comboChequeType, "selectedItem");
		dataBinder.registerBinding("currency", com.kingdee.eas.basedata.assistant.CurrencyInfo.class, this.f7Currency, "data");
		dataBinder.registerBinding("keeper", com.kingdee.eas.base.permission.UserInfo.class, this.f7Keeper, "data");
		dataBinder.registerBinding("keepOrgUnit", com.kingdee.eas.basedata.org.FullOrgUnitInfo.class, this.f7KeepOrgUnit, "data");
		dataBinder.registerBinding("limitAmount", java.math.BigDecimal.class, this.txtLimitAmount, "value");		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.ChequeBookInUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.sellhouse.ChequeInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("chequeType", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("currency", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("keeper", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("keepOrgUnit", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("limitAmount", ValidateHelper.ON_SAVE);    		
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
     * output txtCodeRule_propertyChange method
     */
    protected void txtCodeRule_propertyChange(java.beans.PropertyChangeEvent e) throws Exception
    {
    }

    /**
     * output txtCodeRule_focusLost method
     */
    protected void txtCodeRule_focusLost(java.awt.event.FocusEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        sic.add(new SelectorItemInfo("creator.*"));
        sic.add(new SelectorItemInfo("description"));
        sic.add(new SelectorItemInfo("chequeType"));
        sic.add(new SelectorItemInfo("currency.*"));
        sic.add(new SelectorItemInfo("keeper.*"));
        sic.add(new SelectorItemInfo("keepOrgUnit.*"));
        sic.add(new SelectorItemInfo("limitAmount"));
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "ChequeBookInUI");
    }




}