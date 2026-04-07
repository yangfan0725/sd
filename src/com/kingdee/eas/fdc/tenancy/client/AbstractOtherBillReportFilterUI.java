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
public abstract class AbstractOtherBillReportFilterUI extends com.kingdee.eas.framework.report.client.CommRptBaseConditionUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractOtherBillReportFilterUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contRoom;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contCustomer;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contStartToDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndFromDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contEndToDate;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer contMoneyDefine;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtRoom;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtCustomer;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkStartToDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndFromDate;
    protected com.kingdee.bos.ctrl.swing.KDDatePicker pkEndToDate;
    protected com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox prmtMoneyDefine;
    /**
     * output class constructor
     */
    public AbstractOtherBillReportFilterUI() throws Exception
    {
        super();
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractOtherBillReportFilterUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        this.contRoom = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contCustomer = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contStartToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndFromDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contEndToDate = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.contMoneyDefine = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.prmtRoom = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.prmtCustomer = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.pkStartFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkStartToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkEndFromDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.pkEndToDate = new com.kingdee.bos.ctrl.swing.KDDatePicker();
        this.prmtMoneyDefine = new com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox();
        this.contRoom.setName("contRoom");
        this.contCustomer.setName("contCustomer");
        this.contStartFromDate.setName("contStartFromDate");
        this.contStartToDate.setName("contStartToDate");
        this.contEndFromDate.setName("contEndFromDate");
        this.contEndToDate.setName("contEndToDate");
        this.contMoneyDefine.setName("contMoneyDefine");
        this.prmtRoom.setName("prmtRoom");
        this.prmtCustomer.setName("prmtCustomer");
        this.pkStartFromDate.setName("pkStartFromDate");
        this.pkStartToDate.setName("pkStartToDate");
        this.pkEndFromDate.setName("pkEndFromDate");
        this.pkEndToDate.setName("pkEndToDate");
        this.prmtMoneyDefine.setName("prmtMoneyDefine");
        // CustomerQueryPanel
        // contRoom		
        this.contRoom.setBoundLabelText(resHelper.getString("contRoom.boundLabelText"));		
        this.contRoom.setBoundLabelLength(100);		
        this.contRoom.setBoundLabelUnderline(true);
        // contCustomer		
        this.contCustomer.setBoundLabelText(resHelper.getString("contCustomer.boundLabelText"));		
        this.contCustomer.setBoundLabelLength(100);		
        this.contCustomer.setBoundLabelUnderline(true);
        // contStartFromDate		
        this.contStartFromDate.setBoundLabelText(resHelper.getString("contStartFromDate.boundLabelText"));		
        this.contStartFromDate.setBoundLabelLength(100);		
        this.contStartFromDate.setBoundLabelUnderline(true);
        // contStartToDate		
        this.contStartToDate.setBoundLabelText(resHelper.getString("contStartToDate.boundLabelText"));		
        this.contStartToDate.setBoundLabelLength(100);		
        this.contStartToDate.setBoundLabelUnderline(true);
        // contEndFromDate		
        this.contEndFromDate.setBoundLabelText(resHelper.getString("contEndFromDate.boundLabelText"));		
        this.contEndFromDate.setBoundLabelLength(100);		
        this.contEndFromDate.setBoundLabelUnderline(true);
        // contEndToDate		
        this.contEndToDate.setBoundLabelText(resHelper.getString("contEndToDate.boundLabelText"));		
        this.contEndToDate.setBoundLabelLength(100);		
        this.contEndToDate.setBoundLabelUnderline(true);
        // contMoneyDefine		
        this.contMoneyDefine.setBoundLabelText(resHelper.getString("contMoneyDefine.boundLabelText"));		
        this.contMoneyDefine.setBoundLabelLength(100);		
        this.contMoneyDefine.setBoundLabelUnderline(true);
        // prmtRoom		
        this.prmtRoom.setEditFormat("$name$");		
        this.prmtRoom.setDisplayFormat("$name$");		
        this.prmtRoom.setCommitFormat("$name$");		
        this.prmtRoom.setEnabledMultiSelection(true);
        // prmtCustomer
        // pkStartFromDate
        // pkStartToDate
        // pkEndFromDate
        // pkEndToDate
        // prmtMoneyDefine
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
        this.setBounds(new Rectangle(10, 10, 600, 180));
        this.setLayout(null);
        contRoom.setBounds(new Rectangle(27, 18, 552, 19));
        this.add(contRoom, null);
        contCustomer.setBounds(new Rectangle(27, 44, 552, 19));
        this.add(contCustomer, null);
        contStartFromDate.setBounds(new Rectangle(27, 96, 252, 19));
        this.add(contStartFromDate, null);
        contStartToDate.setBounds(new Rectangle(327, 96, 252, 19));
        this.add(contStartToDate, null);
        contEndFromDate.setBounds(new Rectangle(27, 125, 252, 19));
        this.add(contEndFromDate, null);
        contEndToDate.setBounds(new Rectangle(327, 125, 252, 19));
        this.add(contEndToDate, null);
        contMoneyDefine.setBounds(new Rectangle(27, 70, 552, 19));
        this.add(contMoneyDefine, null);
        //contRoom
        contRoom.setBoundEditor(prmtRoom);
        //contCustomer
        contCustomer.setBoundEditor(prmtCustomer);
        //contStartFromDate
        contStartFromDate.setBoundEditor(pkStartFromDate);
        //contStartToDate
        contStartToDate.setBoundEditor(pkStartToDate);
        //contEndFromDate
        contEndFromDate.setBoundEditor(pkEndFromDate);
        //contEndToDate
        contEndToDate.setBoundEditor(pkEndToDate);
        //contMoneyDefine
        contMoneyDefine.setBoundEditor(prmtMoneyDefine);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.tenancy.app.OtherBillReportFilterUIHandler";
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
	 * ????????§µ??
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
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.tenancy.client", "OtherBillReportFilterUI");
    }




}