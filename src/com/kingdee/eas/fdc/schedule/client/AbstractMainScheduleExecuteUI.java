/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

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
public abstract class AbstractMainScheduleExecuteUI extends com.kingdee.eas.fdc.schedule.client.FDCScheduleBaseEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMainScheduleExecuteUI.class);
    protected com.kingdee.bos.ctrl.swing.KDLabel paySubway;
    protected com.kingdee.bos.ctrl.swing.KDLabelContainer kDLabelContainer1;
    protected com.kingdee.bos.ctrl.swing.KDComboBox kDComTaskFilter;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnScheduleReport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnTaskApprise;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnWeekReport;
    protected com.kingdee.bos.ctrl.swing.KDWorkButton btnMonthReport;
    protected com.kingdee.eas.fdc.schedule.FDCScheduleInfo editData = null;
    protected ActionWeekReport actionWeekReport = null;
    protected ActionMonthReport actionMonthReport = null;
    protected ActionTaskApprise actionTaskApprise = null;
    protected ActionScheduleReport actionScheduleReport = null;
    /**
     * output class constructor
     */
    public AbstractMainScheduleExecuteUI() throws Exception
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
        this.resHelper = new ResourceBundleHelper(AbstractMainScheduleExecuteUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        //actionWeekReport
        this.actionWeekReport = new ActionWeekReport(this);
        getActionManager().registerAction("actionWeekReport", actionWeekReport);
         this.actionWeekReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionMonthReport
        this.actionMonthReport = new ActionMonthReport(this);
        getActionManager().registerAction("actionMonthReport", actionMonthReport);
         this.actionMonthReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionTaskApprise
        this.actionTaskApprise = new ActionTaskApprise(this);
        getActionManager().registerAction("actionTaskApprise", actionTaskApprise);
         this.actionTaskApprise.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        //actionScheduleReport
        this.actionScheduleReport = new ActionScheduleReport(this);
        getActionManager().registerAction("actionScheduleReport", actionScheduleReport);
         this.actionScheduleReport.addService(new com.kingdee.eas.framework.client.service.PermissionService());
        this.paySubway = new com.kingdee.bos.ctrl.swing.KDLabel();
        this.kDLabelContainer1 = new com.kingdee.bos.ctrl.swing.KDLabelContainer();
        this.kDComTaskFilter = new com.kingdee.bos.ctrl.swing.KDComboBox();
        this.btnScheduleReport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnTaskApprise = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnWeekReport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.btnMonthReport = new com.kingdee.bos.ctrl.swing.KDWorkButton();
        this.paySubway.setName("paySubway");
        this.kDLabelContainer1.setName("kDLabelContainer1");
        this.kDComTaskFilter.setName("kDComTaskFilter");
        this.btnScheduleReport.setName("btnScheduleReport");
        this.btnTaskApprise.setName("btnTaskApprise");
        this.btnWeekReport.setName("btnWeekReport");
        this.btnMonthReport.setName("btnMonthReport");
        // CoreUI		
        this.btnPageSetup.setEnabled(false);		
        this.btnAddNew.setEnabled(false);		
        this.btnEdit.setEnabled(false);		
        this.btnSave.setEnabled(false);		
        this.btnSave.setVisible(false);		
        this.btnSubmit.setEnabled(false);		
        this.btnSubmit.setVisible(false);		
        this.btnCopy.setEnabled(false);		
        this.btnRemove.setEnabled(false);		
        this.btnFirst.setEnabled(false);		
        this.btnPre.setEnabled(false);		
        this.btnNext.setEnabled(false);		
        this.btnLast.setEnabled(false);		
        this.menuView.setText(resHelper.getString("menuView.text"));		
        this.btnTraceUp.setEnabled(false);		
        this.btnTraceDown.setEnabled(false);		
        this.btnWorkFlowG.setEnabled(false);		
        this.btnViewSignature.setEnabled(true);		
        this.btnCreateFrom.setEnabled(false);		
        this.btnCreateTo.setEnabled(false);		
        this.btnAddLine.setEnabled(false);		
        this.btnInsertLine.setEnabled(false);		
        this.btnRemoveLine.setEnabled(false);		
        this.btnZoomLeft.setEnabled(false);		
        this.btnZoomLeft.setVisible(false);		
        this.btnZoomCenter.setEnabled(false);		
        this.btnZoomCenter.setVisible(false);		
        this.btnZoomRight.setEnabled(false);		
        this.btnZoomRight.setVisible(false);		
        this.btnZoomOut.setEnabled(false);		
        this.btnZoomOut.setVisible(false);		
        this.btnZoomIn.setEnabled(false);		
        this.btnZoomIn.setVisible(false);		
        this.btnProperty.setText(resHelper.getString("btnProperty.text"));		
        this.btnProperty.setEnabled(false);		
        this.btnShowByLevel.setVisible(false);		
        this.btnCritical.setEnabled(false);		
        this.btnCritical.setVisible(false);		
        this.btnPert.setEnabled(false);		
        this.btnPert.setVisible(false);		
        this.contCurProject.setBoundLabelLength(70);		
        this.contVersion.setBoundLabelLength(70);		
        this.contState.setBoundLabelText(resHelper.getString("contState.boundLabelText"));		
        this.contDescription.setBoundLabelLength(70);
		String kDTable1StrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol1\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol2\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol3\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol4\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol5\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol8\"><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol9\"><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Protection hidden=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol11\"><c:Protection hidden=\"true\" /><c:NumberFormat>yyyy-MM-dd</c:NumberFormat></c:Style><c:Style id=\"sCol12\"><c:Protection hidden=\"true\" /><c:NumberFormat>0</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol14\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol15\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"taskName\" t:width=\"250\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" /><t:Column t:key=\"workDayPre\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol1\" /><t:Column t:key=\"workDayDep\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" t:styleID=\"sCol2\" /><t:Column t:key=\"workDayDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" t:styleID=\"sCol3\" /><t:Column t:key=\"planStartPre\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" t:styleID=\"sCol4\" /><t:Column t:key=\"planStartDep\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"planStartDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"planEndPre\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"planEndDep\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"planEndDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"checkDatePre\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"checkDateDep\" t:width=\"80\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol11\" /><t:Column t:key=\"checkDateDiff\" t:width=\"60\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol12\" /><t:Column t:key=\"longNumber\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol13\" /><t:Column t:key=\"isLeaf\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol14\" /><t:Column t:key=\"level\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"-1\" t:styleID=\"sCol15\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskName}</t:Cell><t:Cell>$Resource{workDayPre}</t:Cell><t:Cell>$Resource{workDayDep}</t:Cell><t:Cell>$Resource{workDayDiff}</t:Cell><t:Cell>$Resource{planStartPre}</t:Cell><t:Cell>$Resource{planStartDep}</t:Cell><t:Cell>$Resource{planStartDiff}</t:Cell><t:Cell>$Resource{planEndPre}</t:Cell><t:Cell>$Resource{planEndDep}</t:Cell><t:Cell>$Resource{planEndDiff}</t:Cell><t:Cell>$Resource{checkDatePre}</t:Cell><t:Cell>$Resource{checkDateDep}</t:Cell><t:Cell>$Resource{checkDateDiff}</t:Cell><t:Cell>$Resource{longNumber}</t:Cell><t:Cell>$Resource{isLeaf}</t:Cell><t:Cell>$Resource{level}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{taskName_Row2}</t:Cell><t:Cell>$Resource{workDayPre_Row2}</t:Cell><t:Cell>$Resource{workDayDep_Row2}</t:Cell><t:Cell>$Resource{workDayDiff_Row2}</t:Cell><t:Cell>$Resource{planStartPre_Row2}</t:Cell><t:Cell>$Resource{planStartDep_Row2}</t:Cell><t:Cell>$Resource{planStartDiff_Row2}</t:Cell><t:Cell>$Resource{planEndPre_Row2}</t:Cell><t:Cell>$Resource{planEndDep_Row2}</t:Cell><t:Cell>$Resource{planEndDiff_Row2}</t:Cell><t:Cell>$Resource{checkDatePre_Row2}</t:Cell><t:Cell>$Resource{checkDateDep_Row2}</t:Cell><t:Cell>$Resource{checkDateDiff_Row2}</t:Cell><t:Cell>$Resource{longNumber_Row2}</t:Cell><t:Cell>$Resource{isLeaf_Row2}</t:Cell><t:Cell>$Resource{level_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"0\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"0\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"0\" t:right=\"9\" /><t:Block t:top=\"0\" t:left=\"10\" t:bottom=\"0\" t:right=\"12\" /><t:Block t:top=\"0\" t:left=\"13\" t:bottom=\"1\" t:right=\"13\" /><t:Block t:top=\"0\" t:left=\"14\" t:bottom=\"1\" t:right=\"14\" /><t:Block t:top=\"0\" t:left=\"15\" t:bottom=\"1\" t:right=\"15\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot>";


        
		
        this.btnAdjust.setEnabled(false);		
        this.btnAdjust.setVisible(false);		
        this.btnSaveNewTask.setEnabled(false);		
        this.btnRestore.setEnabled(false);		
        this.btnRestore.setVisible(false);		
        this.btnBatChangeRespDept.setEnabled(false);		
        this.btnBatChangeRespDept.setVisible(false);		
        this.btnAudit.setEnabled(false);		
        this.btnAudit.setVisible(false);		
        this.btnUnClose.setEnabled(false);		
        this.btnUnClose.setVisible(false);		
        this.btnDisplayAll.setEnabled(false);		
        this.btnDisplayAll.setVisible(false);		
        this.btnHideOther.setEnabled(false);		
        this.btnHideOther.setVisible(false);		
        this.btnImportProject.setEnabled(false);		
        this.btnImportProject.setVisible(false);		
        this.btnExportProject.setEnabled(false);		
        this.btnExportProject.setVisible(false);		
        this.btnImportPlanTemplate.setEnabled(false);		
        this.btnImportPlanTemplate.setVisible(false);		
        this.btnExportPlanTemplate.setEnabled(false);		
        this.btnExportPlanTemplate.setVisible(false);		
        this.btnHisVerion.setVisible(false);
        // paySubway
        // kDLabelContainer1		
        this.kDLabelContainer1.setBoundLabelText(resHelper.getString("kDLabelContainer1.boundLabelText"));		
        this.kDLabelContainer1.setBoundLabelLength(70);		
        this.kDLabelContainer1.setBoundLabelUnderline(true);
        // kDComTaskFilter		
        this.kDComTaskFilter.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.FDCScheduleExecuteEnum").toArray());
        this.kDComTaskFilter.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent e) {
                try {
                    kDComTaskFilter_itemStateChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
        // btnScheduleReport
        this.btnScheduleReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionScheduleReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnScheduleReport.setText(resHelper.getString("btnScheduleReport.text"));
        // btnTaskApprise
        this.btnTaskApprise.setAction((IItemAction)ActionProxyFactory.getProxy(actionTaskApprise, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnTaskApprise.setText(resHelper.getString("btnTaskApprise.text"));
        // btnWeekReport
        this.btnWeekReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionWeekReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnWeekReport.setText(resHelper.getString("btnWeekReport.text"));		
        this.btnWeekReport.setToolTipText(resHelper.getString("btnWeekReport.toolTipText"));
        // btnMonthReport
        this.btnMonthReport.setAction((IItemAction)ActionProxyFactory.getProxy(actionMonthReport, new Class[] { IItemAction.class }, getServiceContext()));		
        this.btnMonthReport.setText(resHelper.getString("btnMonthReport.text"));		
        this.btnMonthReport.setToolTipText(resHelper.getString("btnMonthReport.toolTipText"));
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
        this.setBounds(new Rectangle(10, 10, 900, 600));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 900, 600));
        contCreateTime.setBounds(new Rectangle(554, 534, 371, 19));
        this.add(contCreateTime, new KDLayout.Constraints(554, 534, 371, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAuditor.setBounds(new Rectangle(519, 549, 371, 19));
        this.add(contAuditor, new KDLayout.Constraints(519, 549, 371, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAuditTime.setBounds(new Rectangle(519, 573, 371, 19));
        this.add(contAuditTime, new KDLayout.Constraints(519, 573, 371, 19, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        mainPanel.setBounds(new Rectangle(10, 89, 880, 471));
        this.add(mainPanel, new KDLayout.Constraints(10, 89, 880, 471, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        contCurProject.setBounds(new Rectangle(10, 10, 250, 19));
        this.add(contCurProject, new KDLayout.Constraints(10, 10, 250, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contVersion.setBounds(new Rectangle(325, 10, 250, 19));
        this.add(contVersion, new KDLayout.Constraints(325, 10, 250, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contProject.setBounds(new Rectangle(295, 551, 270, 19));
        this.add(contProject, new KDLayout.Constraints(295, 551, 270, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        contAdminDept.setBounds(new Rectangle(620, 526, 270, 19));
        this.add(contAdminDept, new KDLayout.Constraints(620, 526, 270, 19, KDLayout.Constraints.ANCHOR_CENTRE | KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT_SCALE));
        contAdminPerson.setBounds(new Rectangle(772, -1, 8, 8));
        this.add(contAdminPerson, new KDLayout.Constraints(772, -1, 8, 8, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        kDPanel1.setBounds(new Rectangle(213, 155, 680, 222));
        this.add(kDPanel1, new KDLayout.Constraints(213, 155, 680, 222, 0));
        contDescription.setBounds(new Rectangle(11, 34, 880, 47));
        this.add(contDescription, new KDLayout.Constraints(11, 34, 880, 47, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        paySubway.setBounds(new Rectangle(10, 569, 386, 21));
        this.add(paySubway, new KDLayout.Constraints(10, 569, 386, 21, KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT));
        kDLabelContainer1.setBounds(new Rectangle(640, 10, 250, 19));
        this.add(kDLabelContainer1, new KDLayout.Constraints(640, 10, 250, 19, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_LEFT_SCALE | KDLayout.Constraints.ANCHOR_RIGHT));
        //contCreateTime
        contCreateTime.setBoundEditor(pkCreateTime);
        //contAuditor
        contAuditor.setBoundEditor(prmtAuditor);
        //contAuditTime
        contAuditTime.setBoundEditor(pkAuditTime);
        //mainPanel
mainPanel.setLayout(new BorderLayout(0, 0));        mainPanel.add(contCreator, BorderLayout.SOUTH);
        mainPanel.add(contState, BorderLayout.NORTH);
        //contCreator
        contCreator.setBoundEditor(prmtCreator);
        //contState
        contState.setBoundEditor(cbState);
        //contCurProject
        contCurProject.setBoundEditor(prmtCurproject);
        //contVersion
        contVersion.setBoundEditor(txtVersion);
        //contProject
        contProject.setBoundEditor(prmtProject);
        //contAdminDept
        contAdminDept.setBoundEditor(prmtAdminDept);
        //contAdminPerson
        contAdminPerson.setBoundEditor(prmtAdminPerson);
        //kDPanel1
kDPanel1.setLayout(new BorderLayout(0, 0));        kDPanel1.add(kDTable1, BorderLayout.CENTER);
        //contDescription
        contDescription.setBoundEditor(kDScrollPane1);
        //kDScrollPane1
        kDScrollPane1.getViewport().add(txtDescription, null);
        //kDLabelContainer1
        kDLabelContainer1.setBoundEditor(kDComTaskFilter);

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
        menuFile.add(menuItemSaveNewTask);
        menuFile.add(kdSeparatorFWFile1);
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
        menuFile.add(menuItemSendMail);
        menuFile.add(kDSeparator9);
        menuFile.add(menuImportProject);
        menuFile.add(menuExportProject);
        menuFile.add(menuITemBatChangeRespDept);
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
        menuView.add(menuItemZoomLeft);
        menuView.add(menuItemLocate);
        menuView.add(menuItemZoomCenter);
        menuView.add(menuItemZoomRight);
        menuView.add(menuItemZoomOut);
        menuView.add(menuItemZoomIn);
        menuView.add(kDSeparator6);
        menuView.add(menuItemDisplayAll);
        menuView.add(menuItemHideOther);
        menuView.add(kDSeparator10);
        menuView.add(menuViewHis);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        menuBiz.add(MenuItemVoucher);
        menuBiz.add(menuItemDelVoucher);
        menuBiz.add(menuItemProperty);
        menuBiz.add(menuItemGraphOption);
        menuBiz.add(menuItemCritical);
        menuBiz.add(menuItemPERT);
        menuBiz.add(kDSeparator7);
        menuBiz.add(menuItemAudit);
        menuBiz.add(menuItemUnAduit);
        menuBiz.add(kDSeparator8);
        menuBiz.add(menuItemClose);
        menuBiz.add(menuItemUnClose);
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
        this.toolBar.add(btnSaveNewTask);
        this.toolBar.add(btnReset);
        this.toolBar.add(btnSubmit);
        this.toolBar.add(btnCopy);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(btnLocate);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(btnBatChangeRespDept);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnFirst);
        this.toolBar.add(btnPre);
        this.toolBar.add(btnNext);
        this.toolBar.add(btnLast);
        this.toolBar.add(btnZoomLeft);
        this.toolBar.add(btnZoomCenter);
        this.toolBar.add(btnZoomRight);
        this.toolBar.add(btnNumberSign);
        this.toolBar.add(btnZoomOut);
        this.toolBar.add(btnZoomIn);
        this.toolBar.add(separatorFW3);
        this.toolBar.add(btnTraceUp);
        this.toolBar.add(btnTraceDown);
        this.toolBar.add(btnWorkFlowG);
        this.toolBar.add(btnSignature);
        this.toolBar.add(btnViewSignature);
        this.toolBar.add(btnCopyLine);
        this.toolBar.add(separatorFW4);
        this.toolBar.add(separatorFW7);
        this.toolBar.add(btnCreateFrom);
        this.toolBar.add(btnCopyFrom);
        this.toolBar.add(btnCreateTo);
        this.toolBar.add(separatorFW5);
        this.toolBar.add(separatorFW8);
        this.toolBar.add(btnAddLine);
        this.toolBar.add(btnInsertLine);
        this.toolBar.add(btnRemoveLine);
        this.toolBar.add(separatorFW6);
        this.toolBar.add(separatorFW9);
        this.toolBar.add(btnVoucher);
        this.toolBar.add(btnDelVoucher);
        this.toolBar.add(btnAuditResult);
        this.toolBar.add(btnMultiapprove);
        this.toolBar.add(btnWFViewdoProccess);
        this.toolBar.add(btnShowByLevel);
        this.toolBar.add(btnWFViewSubmitProccess);
        this.toolBar.add(btnNextPerson);
        this.toolBar.add(btnBatchModifyTaskType);
        this.toolBar.add(btnProperty);
        this.toolBar.add(btnCritical);
        this.toolBar.add(btnPert);
        this.toolBar.add(btnAudit);
        this.toolBar.add(btnUnAudit);
        this.toolBar.add(btnClose);
        this.toolBar.add(btnUnClose);
        this.toolBar.add(btnDisplayAll);
        this.toolBar.add(btnHideOther);
        this.toolBar.add(btnAdjust);
        this.toolBar.add(btnRestore);
        this.toolBar.add(btnImportProject);
        this.toolBar.add(btnExportProject);
        this.toolBar.add(btnImportPlanTemplate);
        this.toolBar.add(btnExportPlanTemplate);
        this.toolBar.add(btnHisVerion);
        this.toolBar.add(btnScheduleReport);
        this.toolBar.add(btnTaskApprise);
        this.toolBar.add(btnWeekReport);
        this.toolBar.add(btnCompareVer);
        this.toolBar.add(btnMonthReport);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.schedule.app.MainScheduleExecuteUIHandler";
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
        this.editData = (com.kingdee.eas.fdc.schedule.FDCScheduleInfo)ov;
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
		getValidateHelper().registerBindProperty("creator", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("createTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditor", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("auditTime", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("versionName", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("state", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("project", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminDept", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("adminPerson", ValidateHelper.ON_SAVE);    
		getValidateHelper().registerBindProperty("description", ValidateHelper.ON_SAVE);    		
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
     * output kDComTaskFilter_itemStateChanged method
     */
    protected void kDComTaskFilter_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
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
        return sic;
    }        
    	

    /**
     * output actionWeekReport_actionPerformed method
     */
    public void actionWeekReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionMonthReport_actionPerformed method
     */
    public void actionMonthReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionTaskApprise_actionPerformed method
     */
    public void actionTaskApprise_actionPerformed(ActionEvent e) throws Exception
    {
    }
    	

    /**
     * output actionScheduleReport_actionPerformed method
     */
    public void actionScheduleReport_actionPerformed(ActionEvent e) throws Exception
    {
    }
	public RequestContext prepareActionWeekReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionWeekReport() {
    	return false;
    }
	public RequestContext prepareActionMonthReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionMonthReport() {
    	return false;
    }
	public RequestContext prepareActionTaskApprise(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionTaskApprise() {
    	return false;
    }
	public RequestContext prepareActionScheduleReport(IItemAction itemAction) throws Exception {
			RequestContext request = new RequestContext();		
		if (request != null) {
    		request.setClassName(getUIHandlerClassName());
		}
		return request;
    }
	
	public boolean isPrepareActionScheduleReport() {
    	return false;
    }

    /**
     * output ActionWeekReport class
     */     
    protected class ActionWeekReport extends ItemAction {     
    
        public ActionWeekReport()
        {
            this(null);
        }

        public ActionWeekReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionWeekReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWeekReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionWeekReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMainScheduleExecuteUI.this, "ActionWeekReport", "actionWeekReport_actionPerformed", e);
        }
    }

    /**
     * output ActionMonthReport class
     */     
    protected class ActionMonthReport extends ItemAction {     
    
        public ActionMonthReport()
        {
            this(null);
        }

        public ActionMonthReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionMonthReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMonthReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionMonthReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMainScheduleExecuteUI.this, "ActionMonthReport", "actionMonthReport_actionPerformed", e);
        }
    }

    /**
     * output ActionTaskApprise class
     */     
    protected class ActionTaskApprise extends ItemAction {     
    
        public ActionTaskApprise()
        {
            this(null);
        }

        public ActionTaskApprise(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            _tempStr = resHelper.getString("ActionTaskApprise.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTaskApprise.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionTaskApprise.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMainScheduleExecuteUI.this, "ActionTaskApprise", "actionTaskApprise_actionPerformed", e);
        }
    }

    /**
     * output ActionScheduleReport class
     */     
    protected class ActionScheduleReport extends ItemAction {     
    
        public ActionScheduleReport()
        {
            this(null);
        }

        public ActionScheduleReport(IUIObject uiObject)
        {     
		super(uiObject);     
        
            String _tempStr = null;
            this.setEnabled(false);
            _tempStr = resHelper.getString("ActionScheduleReport.SHORT_DESCRIPTION");
            this.putValue(ItemAction.SHORT_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionScheduleReport.LONG_DESCRIPTION");
            this.putValue(ItemAction.LONG_DESCRIPTION, _tempStr);
            _tempStr = resHelper.getString("ActionScheduleReport.NAME");
            this.putValue(ItemAction.NAME, _tempStr);
        }

        public void actionPerformed(ActionEvent e)
        {
        	getUIContext().put("ORG.PK", getOrgPK(this));
            innerActionPerformed("eas", AbstractMainScheduleExecuteUI.this, "ActionScheduleReport", "actionScheduleReport_actionPerformed", e);
        }
    }

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.schedule.client", "MainScheduleExecuteUI");
    }




}