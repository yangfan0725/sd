/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.math.BigDecimal;
import java.util.EventListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ICommonBOSType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessDefInfo;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.define.ProcessDef;
import com.kingdee.bos.workflow.monitor.client.BasicShowWfDefinePanel;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.kdf.servertable.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDCheckBox;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDOptionPane;
import com.kingdee.bos.ctrl.swing.KDPromptBox;
import com.kingdee.bos.ctrl.swing.KDScrollPane;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.agent.AgentUtility;
import com.kingdee.eas.base.attachment.BizobjectFacadeFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.client.CUAdminEditUI;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.assistant.ProvinceInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.master.cssp.SupplierCollection;
import com.kingdee.eas.basedata.master.cssp.SupplierFactory;
import com.kingdee.eas.basedata.master.cssp.client.CSClientUtils;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitFactory;
import com.kingdee.eas.basedata.org.PurchaseOrgUnitInfo;
import com.kingdee.eas.basedata.org.client.OrgF7PromptDialog;
import com.kingdee.eas.basedata.org.client.OrgViewF7PromptDialog;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCTableHelper;
import com.kingdee.eas.fdc.basedata.client.FDCUIWeightWorker;
import com.kingdee.eas.fdc.basedata.client.IFDCWork;
import com.kingdee.eas.fdc.contract.BankNumCollection;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.eas.fdc.contract.ContractWFTypeInfo;
import com.kingdee.eas.fdc.contract.SupplierApplyCollection;
import com.kingdee.eas.fdc.contract.SupplierApplyFactory;
import com.kingdee.eas.fdc.invite.InviteClarifyEntryFactory;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.invite.client.InviteProjectEditUI;
import com.kingdee.eas.fdc.invite.supplier.CurrencyEnum;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaFactory;
import com.kingdee.eas.fdc.invite.supplier.FDCSplAreaInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSplServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeCollection;
import com.kingdee.eas.fdc.invite.supplier.FDCSupplierServiceTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.InviteListTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.IsGradeEnum;
import com.kingdee.eas.fdc.invite.supplier.QuaLevelInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierAttachListInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierBusinessModeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierEvaluationTypeFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierFileTypeInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierInviteListTypeEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierInviteListTypeEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Collection;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Factory;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonF7Info;
import com.kingdee.eas.fdc.invite.supplier.SupplierLinkPersonInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierPersonInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierQuaLevelEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierQuaLevelEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierReviewGatherFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStateEnum;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockContractEntry;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockContractEntryCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockContractEntryInfo;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierStockInfo;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.StringUtils;

/**
 * output class name
 */
public class NewSupplierStockEditUI extends AbstractNewSupplierStockEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(NewSupplierStockEditUI.class);
    protected Map listenersMap = new HashMap();
    /**
     * output class constructor
     */
    public NewSupplierStockEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	initTable();
    	this.prmtQuaLevel.setEnabledMultiSelection(true);
    	this.prmtInviteTypeList.setEnabledMultiSelection(true);
		super.onLoad();
		initF7();
		initControl();
		btnAuditResult.setIcon(EASResource.getIcon("imgTbtn_multapproveresult"));
		btnWorkFlowG.setIcon(EASResource.getIcon("imgTbtn_flowchart"));
		this.prmtPurchaseOrgUnit.setEnabled(false);
		
		this.actionCopy.setVisible(false);
	}
    public void storeFields()
    {
    	this.storeSupplierAttachList();
    	this.storeSupplierLinkPerson();
    	this.storeSupplierPerson();
    	this.storeSupplierServiceType();
    	this.storeSupplierSplArea();
    	
    	this.storeContractEntry();
    	this.storeQuaLevel();
    	this.storeInviteListType();
        super.storeFields();
    }
    public void loadFields() {
    	detachListeners();
    	super.loadFields();
    	this.loadSupplierLinkPerson();
    	this.loadSupplierPerson();
    	this.loadSupplierServiceType();
    	this.loadSupplierSplArea();
    	this.loadSupplierAttachList();
    	
    	this.loadContractEntry();
    	this.loadQuaLevel();
    	this.loadInviteTypeList();
    	attachListeners();
    	
    	if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
    }
    protected void initControl(){
    	this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		
    	this.actionSubmit.putValue("SmallIcon", EASResource.getIcon("imgTbtn_submit"));
    	this.actionSave.putValue("SmallIcon", EASResource.getIcon("imgTbtn_save"));
    	
    	this.actionCopy.setVisible(true);
    	this.actionCancel.setVisible(false);
    	this.actionCancelCancel.setVisible(false);
    	
    	this.menuView.setVisible(false);
    	
    	this.menuItemSave.setVisible(true);
    	this.menuItemSave.setAccelerator(KeyStroke.getKeyStroke("ctrl S"));
    	this.menuItemSubmit.setVisible(true);
    	this.menuItemSubmit.setAccelerator(KeyStroke.getKeyStroke("ctrl T"));
    	this.menuItemAddNew.setAccelerator(null);
    	
		if(!SupplierStateEnum.SAVE.equals(this.editData.getState())&&!SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
		}
		if(SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
		this.actionAddNew.setVisible(false);
		
		try {
			FDCHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
		} catch (CodingRuleException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.txtName.setRequired(true);
		this.prmtProvince.setRequired(true);
		this.prmtCity.setRequired(true);
		this.txtAddress.setRequired(true);
		this.txtLinkPhone.setRequired(true);
		this.txtLinkFax.setRequired(true);
		this.txtLinkMail.setRequired(true);
		this.txtPunish.setRequired(true);
		this.comboEnterpriseKind.setRequired(true);
		this.txtRegisterMoney.setRequired(true);
		this.txtTaxRegisterNo.setRequired(true);
		this.txtBizRegisterNo.setRequired(true);
		this.txtEnterpriseMaster.setRequired(true);
		this.txtContractor.setRequired(true);
		this.txtContractorPhone.setRequired(true);
		
		this.prmtInviteType.setRequired(true);
		this.txtEnterpriseMaster.setRequired(true);
		this.prmtSupplierFileType.setRequired(true);
		this.prmtSupplierSplAreaEntry.setRequired(true);
		this.prmtSupplierBusinessMode.setRequired(true);
		this.prmtQuaLevel.setRequired(true);
		
//		this.txtAbility.setRequired(true);
//		this.txtBusinessNum.setRequired(true);
		this.txtRecommended.setRequired(true);
		
//		if(this.txtRecommended.getText()!=null&&!this.txtRecommended.getText().trim().equals("")){
//			this.txtRecommended.setEnabled(false);
//		}
		if(!SysContext.getSysContext().getCurrentOrgUnit().isIsPurchaseOrgUnit()){
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionCopy.setEnabled(false);
		}
		loadReviewGatherUI();
    }
    protected void loadReviewGatherUI(){
    	try {
			Map map=new HashMap();
			if(editData.getId()!=null){
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("supplier.id" , editData.getId()));
				view.setFilter(filter);
				view.getSelector().add(new SelectorItemInfo("id"));
				view.getSelector().add(new SelectorItemInfo("evaluationType.id"));
				
				SupplierReviewGatherCollection col= SupplierReviewGatherFactory.getRemoteInstance().getSupplierReviewGatherCollection(view);
				for(int i=0;i<col.size();i++){
					if(col.get(i).getEvaluationType()==null) continue;
					if(map.get(col.get(i).getEvaluationType().getId().toString())!=null){
						((Set)map.get(col.get(i).getEvaluationType().getId().toString())).add(col.get(i).getId().toString());
					}else{
						Set id=new HashSet();
						id.add(col.get(i).getId().toString());
						map.put(col.get(i).getEvaluationType().getId().toString(), id);
					}
				}
			}
			Iterator it=map.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry) it.next();
				String typeId=entry.getKey().toString();
				Set idSet=(HashSet)entry.getValue();
				
				KDScrollPane panel=new KDScrollPane();
				panel.setName(typeId);
				panel.setMinimumSize(new Dimension(1013,600));		
				panel.setPreferredSize(new Dimension(1013,600));
		        this.kDTabbedPane2.add(panel,SupplierEvaluationTypeFactory.getRemoteInstance().getSupplierEvaluationTypeInfo(new ObjectUuidPK(typeId)).getName());
		        
		        UIContext uiContext = new UIContext(this);
		        uiContext.put("IDSET", idSet);
				SupplierReviewGatherListUI ui = (SupplierReviewGatherListUI) UIFactoryHelper.initUIObject(SupplierReviewGatherListUI.class.getName(), uiContext, null,OprtState.VIEW);
				panel.setViewportView(ui);
				panel.setKeyBoardControl(true);
				panel.setEnabled(false);
			}
		}catch (BOSException e) {
			e.printStackTrace();
		} catch (EASBizException e) {
			e.printStackTrace();
		}
    }
    public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if(oprtType.equals(OprtState.VIEW)){
			this.actionPersonAddLine.setEnabled(false);
			this.actionPersonInsertLine.setEnabled(false);
			this.actionPersonRemoveLine.setEnabled(false);
			this.actionLinkPersonAddLine.setEnabled(false);
			this.actionLinkPersonInsertLine.setEnabled(false);
			this.actionLinkPersonRemoveLine.setEnabled(false);
			this.actionAttachListAddLine.setEnabled(false);
			this.actionAttachListInsertLine.setEnabled(false);
			this.actionAttachListRemoveLine.setEnabled(false);
			
			this.actionContractAddRow.setEnabled(false);
			this.actionContractDelRow.setEnabled(false);
			this.actionContractInsertRow.setEnabled(false);
		}else{
			this.actionPersonAddLine.setEnabled(true);
			this.actionPersonInsertLine.setEnabled(true);
			this.actionPersonRemoveLine.setEnabled(true);
			this.actionLinkPersonAddLine.setEnabled(true);
			this.actionLinkPersonInsertLine.setEnabled(true);
			this.actionLinkPersonRemoveLine.setEnabled(true);
			this.actionAttachListAddLine.setEnabled(true);
			this.actionAttachListInsertLine.setEnabled(true);
			this.actionAttachListRemoveLine.setEnabled(true);
			
			this.actionContractAddRow.setEnabled(true);
			this.actionContractDelRow.setEnabled(true);
			this.actionContractInsertRow.setEnabled(true);
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		if(SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
	}
	
	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		super.actionSave_actionPerformed(e);
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
			public void run() {
				storeFields();
				initOldData(editData);
			}
		});
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if(SupplierStateEnum.SUBMIT.equals(this.editData.getState())){
			this.actionSave.setEnabled(false);
		}
		FDCUIWeightWorker.getInstance().addWork(new IFDCWork(){
			public void run() {
				storeFields();
				initOldData(editData);
			}
		});
		
		FDCHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
	}
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sel=super.getSelectors();
    	sel.add("supplierServiceType.*");
    	sel.add("supplierServiceType.serviceType.*");
    	sel.add("supplierSplAreaEntry.*");
    	sel.add("supplierSplAreaEntry.fdcSplArea.*");
    	sel.add("supplierPersonEntry.*");
    	sel.add("supplierAttachListEntry.*");
    	sel.add("linkPerson.*");
    	sel.add("state");
    	sel.add("adminCU.*");
    	sel.add("sysSupplier.*");
    	sel.add("CU.*");
    	sel.add("*");
    	sel.add("contractEntry.*");
    	sel.add("quaLevelEntry.quaLevel.*");
    	sel.add("inviteListTypeEntry.inviteListType.*");
		return sel;
	}
    protected void storeSupplierSplArea(){
    	editData.getSupplierSplAreaEntry().clear();
    	Object[] value=(Object[]) this.prmtSupplierSplAreaEntry.getValue();
    	String splArea="";
    	if(value!=null&&value.length>0){
    		for(int i=0;i<value.length;i++){
    			FDCSplAreaInfo info = (FDCSplAreaInfo) value[i];
				SupplierSplAreaEntryInfo entry = new SupplierSplAreaEntryInfo();
				entry.setFdcSplArea(info);
				splArea=splArea+info.getName()+";";
				editData.getSupplierSplAreaEntry().add(entry);
    		}
    	}
    	editData.setSplArea(splArea);
    }
    protected void storeSupplierServiceType(){
    	editData.getSupplierServiceType().clear();
    	Object[] value=(Object[]) this.prmtServiceType.getValue();
    	if(value!=null&&value.length>0){
    		for(int i=0;i<value.length;i++){
    			FDCSplServiceTypeInfo info = (FDCSplServiceTypeInfo) value[i];
    			FDCSupplierServiceTypeInfo entry = new FDCSupplierServiceTypeInfo();
				entry.setServiceType(info);
				editData.getSupplierServiceType().add(entry);
    		}
    	}
    }
    protected void storeQuaLevel(){
    	editData.getQuaLevelEntry().clear();
    	Object[] value=(Object[]) this.prmtQuaLevel.getValue();
    	if(value!=null&&value.length>0){
    		for(int i=0;i<value.length;i++){
    			QuaLevelInfo info = (QuaLevelInfo) value[i];
    			SupplierQuaLevelEntryInfo entry = new SupplierQuaLevelEntryInfo();
				entry.setQuaLevel(info);
				editData.getQuaLevelEntry().add(entry);
    		}
    	}
    }
    protected void storeInviteListType(){
    	String inviteListTypeStr="";
    	editData.getInviteListTypeEntry().clear();
    	Object[] value=(Object[]) this.prmtInviteTypeList.getValue();
    	if(value!=null&&value.length>0){
    		for(int i=0;i<value.length;i++){
    			InviteListTypeInfo info = (InviteListTypeInfo) value[i];
    			SupplierInviteListTypeEntryInfo entry = new SupplierInviteListTypeEntryInfo();
				entry.setInviteListType(info);
				editData.getInviteListTypeEntry().add(entry);
				
				inviteListTypeStr=inviteListTypeStr+info.getName()+";";
    		}
    		this.editData.setInviteListTypeStr(inviteListTypeStr);
    	}
    }
    protected void storeSupplierLinkPerson(){
    	editData.getLinkPerson().clear();
    	for(int i=0;i<this.kdtLinkPerson.getRowCount();i++){
    		IRow row = this.kdtLinkPerson.getRow(i);
    		SupplierLinkPersonInfo entry=new SupplierLinkPersonInfo();
    		if(row.getUserObject()!=null){
    			entry=(SupplierLinkPersonInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setPersonName((String)row.getCell("personName").getValue());
    		entry.setPosition((String)row.getCell("position").getValue());
    		entry.setPhone((String)row.getCell("phone").getValue());
    		entry.setWorkPhone((String)row.getCell("workPhone").getValue());
    		entry.setPersonFax((String)row.getCell("personFax").getValue());
    		entry.setEmail((String)row.getCell("email").getValue());
			entry.setIsDefault(((Boolean)row.getCell("isDefault").getValue()).booleanValue());
			entry.setType((String)row.getCell("type").getValue());
    		editData.getLinkPerson().add(entry);
    	}
    }
    protected void storeSupplierPerson(){
    	editData.getSupplierPersonEntry().clear();
    	for(int i=0;i<this.kdtSupplierPerson.getRowCount();i++){
    		IRow row = this.kdtSupplierPerson.getRow(i);
    		SupplierPersonEntryInfo entry=new SupplierPersonEntryInfo();
    		if(row.getUserObject()!=null){
    			entry=(SupplierPersonEntryInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setNumber((String)row.getCell("number").getValue());
    		entry.setName((String)row.getCell("name").getValue());
    		if(row.getCell("amount").getValue()==null){
    			entry.setAmount(0);
    		}else{
    			entry.setAmount(Integer.parseInt(row.getCell("amount").getValue().toString()));
    		}
    		editData.getSupplierPersonEntry().add(entry);
    	}
    		
    }
    protected void storeSupplierAttachList(){
    	editData.getSupplierAttachListEntry().clear();
    	for(int i=0;i<this.kdtSupplierAttachList.getRowCount();i++){
    		IRow row = this.kdtSupplierAttachList.getRow(i);
    		SupplierAttachListEntryInfo entry=new SupplierAttachListEntryInfo();
    		if(row.getUserObject()!=null){
    			entry=(SupplierAttachListEntryInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setNumber((String)row.getCell("number").getValue());
    		entry.setName((String)row.getCell("name").getValue());
    		entry.setDescription((String)row.getCell("description").getValue());
    		editData.getSupplierAttachListEntry().add(entry);
    	}
    }
    protected void storeContractEntry(){
    	editData.getContractEntry().clear();
    	for(int i=0;i<this.kdtContractEntry.getRowCount();i++){
    		IRow row = this.kdtContractEntry.getRow(i);
    		SupplierStockContractEntryInfo entry=new SupplierStockContractEntryInfo();
    		if(row.getUserObject()!=null){
    			entry=(SupplierStockContractEntryInfo) row.getUserObject();
    		}
    		entry.setSeq(i);
    		entry.setContractName((String)row.getCell("contractName").getValue());
    		entry.setContractAmount((BigDecimal)row.getCell("contractAmount").getValue());
    		entry.setPlace((String)row.getCell("place").getValue());
    		entry.setSupplierName((String)row.getCell("supplierName").getValue());
    		
    		entry.setModel((String)row.getCell("model").getValue());
    		entry.setWorkModel((String)row.getCell("workModel").getValue());
    		entry.setManager((String)row.getCell("manager").getValue());
    		editData.getContractEntry().add(entry);
    	}
    		
    }
	protected void loadSupplierSplArea(){
    	SupplierSplAreaEntryCollection col=editData.getSupplierSplAreaEntry();
    	if(col.size()>0){
    		Object[] value=new Object[col.size()];
    		for(int i=0;i<col.size();i++) {
    			SupplierSplAreaEntryInfo entry = col.get(i);
    			value[i]=entry.getFdcSplArea();
    		}
    		this.prmtSupplierSplAreaEntry.setValue(value);
    	}else{
    		this.prmtSupplierSplAreaEntry.setValue(null);
    	}
    }
	protected void loadSupplierServiceType(){
		FDCSupplierServiceTypeCollection col=editData.getSupplierServiceType();
		if(col.size()>0){
    		Object[] value=new Object[col.size()];
    		for(int i=0;i<col.size();i++) {
    			FDCSupplierServiceTypeInfo entry = col.get(i);
    			value[i]=entry.getServiceType();
    		}
    		this.prmtServiceType.setValue(value);
    	}else{
    		this.prmtServiceType.setValue(null);
    	}
	}
	protected void loadQuaLevel(){
		SupplierQuaLevelEntryCollection col=editData.getQuaLevelEntry();
		if(col.size()>0){
    		Object[] value=new Object[col.size()];
    		for(int i=0;i<col.size();i++) {
    			SupplierQuaLevelEntryInfo entry = col.get(i);
    			value[i]=entry.getQuaLevel();
    		}
    		this.prmtQuaLevel.setValue(value);
    	}else{
    		this.prmtQuaLevel.setValue(null);
    	}
	}
	protected void loadInviteTypeList(){
		SupplierInviteListTypeEntryCollection col=editData.getInviteListTypeEntry();
		if(col.size()>0){
    		Object[] value=new Object[col.size()];
    		for(int i=0;i<col.size();i++) {
    			SupplierInviteListTypeEntryInfo entry = col.get(i);
    			value[i]=entry.getInviteListType();
    		}
    		this.prmtInviteTypeList.setValue(value);
    	}else{
    		this.prmtInviteTypeList.setValue(null);
    	}
	}
	
	protected void loadSupplierLinkPerson(){
		SupplierLinkPersonCollection col=editData.getLinkPerson();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtLinkPerson.removeRows();
		for(int i=0;i<col.size();i++){
			SupplierLinkPersonInfo entry=col.get(i);
			IRow row=this.kdtLinkPerson.addRow();
			row.setUserObject(entry);
			row.getCell("personName").setValue(entry.getPersonName());
			row.getCell("position").setValue(entry.getPosition());
			row.getCell("phone").setValue(entry.getPhone());
			row.getCell("workPhone").setValue(entry.getWorkPhone());
			row.getCell("personFax").setValue(entry.getPersonFax());
			row.getCell("email").setValue(entry.getEmail());
			row.getCell("isDefault").setValue(Boolean.valueOf(entry.isIsDefault()));
			row.getCell("type").setValue(entry.getType());
//			if(row.getCell("position").getValue()!=null
//        			&&(row.getCell("position").getValue().toString().equals("法人代表/董事长")
//        					||row.getCell("position").getValue().toString().equals("总经理"))){
//				row.getCell("position").getStyleAttributes().setLocked(true);
//        	}
		}
	}
	protected void loadSupplierPerson(){
		SupplierPersonEntryCollection col=editData.getSupplierPersonEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtSupplierPerson.removeRows();
		for(int i=0;i<col.size();i++){
			SupplierPersonEntryInfo entry=col.get(i);
			IRow row=this.kdtSupplierPerson.addRow();
			row.setUserObject(entry);
			row.getCell("number").setValue(entry.getNumber());
			row.getCell("name").setValue(entry.getName());
			row.getCell("amount").setValue(Integer.valueOf(entry.getAmount()));
		}
	}
	protected void loadSupplierAttachList(){
		SupplierAttachListEntryCollection col=editData.getSupplierAttachListEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtSupplierAttachList.removeRows();
		for(int i=0;i<col.size();i++){
			SupplierAttachListEntryInfo entry=col.get(i);
			IRow row=this.kdtSupplierAttachList.addRow();
			row.setUserObject(entry);
			row.getCell("number").setValue(entry.getNumber());
			row.getCell("name").setValue(entry.getName());
			row.getCell("description").setValue(entry.getDescription());
			try {
				if(entry.getId()!=null){
					row.getCell("attachment").setValue(loadAttachment(entry.getId().toString()));
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}	

	protected void loadContractEntry(){
		SupplierStockContractEntryCollection col=editData.getContractEntry();
		CRMHelper.sortCollection(col, "seq", true);
		this.kdtContractEntry.removeRows();
		for(int i=0;i<col.size();i++){
			SupplierStockContractEntryInfo entry=col.get(i);
			IRow row=this.kdtContractEntry.addRow();
			row.setUserObject(entry);
			row.getCell("contractName").setValue(entry.getContractName());
			row.getCell("contractAmount").setValue(entry.getContractAmount());
			row.getCell("place").setValue(entry.getPlace());
			row.getCell("supplierName").setValue(entry.getSupplierName());
			
			row.getCell("model").setValue(entry.getModel());
			row.getCell("workModel").setValue(entry.getWorkModel());
			row.getCell("manager").setValue(entry.getManager());
		}
    }
    protected void prmtProvince_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		ProvinceInfo pinfo = (ProvinceInfo) prmtProvince.getValue();
		if(pinfo!=null){
			filter.getFilterItems().add(new FilterItemInfo("province.id" , pinfo.getId().toString()));
		}
		view.setFilter(filter);
		this.prmtCity.setEntityViewInfo(view);
    }

    protected void prmtSupplierFileType_dataChanged(com.kingdee.bos.ctrl.swing.event.DataChangeEvent e) throws Exception
    {
    	 boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged){
        	 return;
         }
         SupplierFileTypeInfo fileType=(SupplierFileTypeInfo)this.prmtSupplierFileType.getValue();
         boolean isShowWarn=false;
         boolean isUpdate=false;
         if(this.kdtSupplierPerson.getRowCount()>0||this.kdtSupplierAttachList.getRowCount()>0){
        	 isShowWarn=true;
         }
         if(isShowWarn){
        	 if(FDCMsgBox.showConfirm2(this, "档案分类改变会覆盖职员构成,附件清单,联系人数据，是否继续？")== FDCMsgBox.YES){
        		 isUpdate=true;
             }
         }else{
        	 isUpdate=true;
         }
         if(isUpdate){
        	 this.kdtSupplierPerson.removeRows();
        	 this.kdtSupplierAttachList.removeRows();
        	 this.kdtLinkPerson.removeRows();
        	 
        	 if(fileType!=null){
        		 SorterItemCollection sort=new SorterItemCollection();
        		 sort.add(new SorterItemInfo("number"));
        		 EntityViewInfo view=new EntityViewInfo();
            	 FilterInfo filter = new FilterInfo();
                 filter.getFilterItems().add(new FilterItemInfo("supplierFileType.id" , fileType.getId().toString()));
                 filter.getFilterItems().add(new FilterItemInfo("isEnabled" , Boolean.TRUE));
                 view.setFilter(filter);
                 view.setSorter(sort);
                 SupplierPersonCollection col=SupplierPersonFactory.getRemoteInstance().getSupplierPersonCollection(view);
                 for(int i=0;i<col.size();i++){
                	 SupplierPersonInfo sp=col.get(i);
                	 IRow row=this.kdtSupplierPerson.addRow();
                	 row.getCell("number").setValue(sp.getNumber());
                	 row.getCell("name").setValue(sp.getName());
                 }
                 SupplierAttachListCollection alCol=SupplierAttachListFactory.getRemoteInstance().getSupplierAttachListCollection(view);
                 for(int i=0;i<alCol.size();i++){
                	 SupplierAttachListInfo at=alCol.get(i);
                	 IRow row=this.kdtSupplierAttachList.addRow();
                	 SupplierAttachListEntryInfo info=new SupplierAttachListEntryInfo();
     				 info.setId(BOSUuid.create(info.getBOSType()));
     				 row.setUserObject(info);
                	 row.getCell("number").setValue(at.getNumber());
                	 row.getCell("name").setValue(at.getName());
                	 row.getCell("description").setValue(at.getDescription());
                 }
                 SupplierLinkPersonF7Collection f7col=SupplierLinkPersonF7Factory.getRemoteInstance().getSupplierLinkPersonF7Collection(view);
                 for(int i=0;i<f7col.size();i++){
                	 SupplierLinkPersonF7Info f7info=f7col.get(i);
                	 IRow row=this.kdtLinkPerson.addRow();
                	 
                	 SupplierLinkPersonInfo info=new SupplierLinkPersonInfo();
                	 info.setId(BOSUuid.create(info.getBOSType()));
     				 row.setUserObject(info);
     				 
     				 row.getCell("personName").setValue(f7info.getPersonName());
     				 row.getCell("position").setValue(f7info.getPosition());
     				 row.getCell("phone").setValue(f7info.getPhone());
     				 row.getCell("workPhone").setValue(f7info.getWorkPhone());
     				 row.getCell("personFax").setValue(f7info.getPersonFax());
     				 row.getCell("email").setValue(f7info.getEmail());
     				 row.getCell("isDefault").setValue(Boolean.valueOf(f7info.isIsDefault()));
     				 row.getCell("type").setValue(f7info.getType());
                 }
        	 }
         }
    }
    protected void initF7(){
    	EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf",Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtServiceType.setEntityViewInfo(view);
		
		view = new EntityViewInfo();
		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtSupplierFileType.setEntityViewInfo(view);
		this.prmtSupplierBusinessMode.setEntityViewInfo(view);
		this.prmtSupplierSplAreaEntry.setEntityViewInfo(view);
    }
    protected void initWorkButton(ItemAction add,ItemAction insert,ItemAction remove,KDContainer container){
    	KDWorkButton btnAddRowinfo=new KDWorkButton();
    	KDWorkButton btnInsertRowinfo=new KDWorkButton();
    	KDWorkButton btnDeleteRowinfo=new KDWorkButton();
    	
    	add.putValue("SmallIcon", EASResource.getIcon("imgTbtn_addline"));
		btnAddRowinfo = (KDWorkButton)container.add(add);
		btnAddRowinfo.setText(getResource("addRow"));
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		insert.putValue("SmallIcon", EASResource.getIcon("imgTbtn_insert"));
		btnInsertRowinfo = (KDWorkButton)container.add(insert);
		btnInsertRowinfo.setText(getResource("insertRow"));
		btnInsertRowinfo.setSize(new Dimension(140, 19));
		
		remove.putValue("SmallIcon", EASResource.getIcon("imgTbtn_deleteline"));
		btnDeleteRowinfo = (KDWorkButton)container.add(remove);
		btnDeleteRowinfo.setText(getResource("deleteRow"));
		btnDeleteRowinfo.setSize(new Dimension(140, 19));
    }
    protected void initTable(){
    	initWorkButton(this.actionPersonAddLine,this.actionPersonInsertLine,this.actionPersonRemoveLine,this.contSupplierPerson);
    	initWorkButton(this.actionLinkPersonAddLine,this.actionLinkPersonInsertLine,this.actionLinkPersonRemoveLine,this.contLinkPerson);
    	initWorkButton(this.actionAttachListAddLine,this.actionAttachListInsertLine,this.actionAttachListRemoveLine,this.contSupplierAttachList);
    	
    	initWorkButton(this.actionContractAddRow,this.actionContractInsertRow,this.actionContractDelRow,this.contContractEntry);
    	
    	KDTextField testField = new KDTextField();
    	testField.setMaxLength(80);
		KDTDefaultCellEditor editorSize = new KDTDefaultCellEditor(testField);
		
		KDTextField testField1 = new KDTextField();
    	testField1.setMaxLength(255);
		KDTDefaultCellEditor editorSize1 = new KDTDefaultCellEditor(testField1);
		
		KDFormattedTextField peopleC = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
		peopleC.setPrecision(0);
		peopleC.setDataVerifierType(12);
		KDTDefaultCellEditor peopleCount = new KDTDefaultCellEditor(peopleC);
		
    	this.kdtLinkPerson.checkParsed();
 	    this.kdtLinkPerson.getColumn("personName").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("position").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("phone").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("workPhone").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("personFax").setEditor(editorSize);
 		this.kdtLinkPerson.getColumn("email").setEditor(editorSize);
 		KDCheckBox isAutidor = new KDCheckBox();
 		isAutidor.setSelected(false);
 		KDTDefaultCellEditor editor = new KDTDefaultCellEditor(isAutidor);
 		this.kdtLinkPerson.getColumn("isDefault").setEditor(editor);
 		
 		this.kdtLinkPerson.getColumn("workPhone").setRequired(true);
 		
 		this.kdtSupplierPerson.checkParsed();
 		this.kdtSupplierPerson.getColumn("number").setEditor(editorSize);
  		this.kdtSupplierPerson.getColumn("name").setEditor(editorSize1);
  		this.kdtSupplierPerson.getColumn("amount").setEditor(peopleCount);
  		this.kdtSupplierPerson.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
 		
 		this.kdtSupplierAttachList.checkParsed();
 		this.kdtSupplierAttachList.getColumn("number").setEditor(editorSize);
  		this.kdtSupplierAttachList.getColumn("name").setEditor(editorSize1);
  		this.kdtSupplierAttachList.getColumn("attachment").getStyleAttributes().setLocked(true);
  		this.kdtSupplierAttachList.getColumn("attachment").getStyleAttributes().setFontColor(Color.BLUE);
  		this.kdtSupplierAttachList.getColumn("attachment").setWidth(400);
  		
  		KDTextField testField2 = new KDTextField();
  		testField2.setMaxLength(200);
		KDTDefaultCellEditor editorSize2 = new KDTDefaultCellEditor(testField2);
		
  		this.kdtContractEntry.checkParsed();
 		this.kdtContractEntry.getColumn("contractName").setEditor(editorSize2);
  		this.kdtContractEntry.getColumn("place").setEditor(editorSize2);
  		this.kdtContractEntry.getColumn("supplierName").setEditor(editorSize2);
  		
  		KDFormattedTextField amount = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
  		amount.setPrecision(2);
  		amount.setDataVerifierType(12);
		KDTDefaultCellEditor amountEditor = new KDTDefaultCellEditor(amount);
		
		this.kdtContractEntry.getColumn("contractAmount").setEditor(amountEditor);
    }
    public void actionContractAddRow_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtContractEntry);
	}
	public void actionContractInsertRow_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtContractEntry);
	}
	public void actionContractDelRow_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtContractEntry);
	}
	
	public void actionAttachListAddLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtSupplierAttachList);
	}
	public void actionAttachListInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtSupplierAttachList);
	}
	public void actionAttachListRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtSupplierAttachList);
	}
	public void actionLinkPersonAddLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtLinkPerson);
	}
	public void actionLinkPersonInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtLinkPerson);
	}
	public void actionLinkPersonRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtLinkPerson);
	}
	public void actionPersonAddLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableAddRow(this.kdtSupplierPerson);
	}
	public void actionPersonInsertLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableInsertLine(this.kdtSupplierPerson);
	}
	public void actionPersonRemoveLine_actionPerformed(ActionEvent e) throws Exception {
		kdTableDeleteRow(this.kdtSupplierPerson);
	}
	protected void kdtLinkPerson_editStopped(KDTEditEvent e) throws Exception {
		int index = e.getRowIndex();
		int size = this.kdtLinkPerson.getRowCount();
	    IRow row = this.kdtLinkPerson.getRow(index);
	    Boolean b=(Boolean) row.getCell("isDefault").getValue();
	    /*
	     * 这里是合并手机与办公电话到一列中去,序时薄上要把手机和办公电话显示到一起.暂用这种方式做
	     */
	    for(int j = 0 ; j<size ; j++){
	    	if(null != this.kdtLinkPerson.getRow(j).getCell("contact").getValue()){
	    		this.kdtLinkPerson.getRow(j).getCell("contact").setValue(null);
	    	}
	    	if(null != this.kdtLinkPerson.getRow(j).getCell("phone").getValue()){
	    		if(null != this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue()){
	    			this.kdtLinkPerson.getRow(j).getCell("contact").setValue(this.kdtLinkPerson.getRow(j).getCell("phone").getValue()+";"+this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue());
	    		}else{
	    			this.kdtLinkPerson.getRow(j).getCell("contact").setValue(this.kdtLinkPerson.getRow(j).getCell("phone").getValue().toString());
	    		}
	    	}else if(null != this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue()){
	    		this.kdtLinkPerson.getRow(j).getCell("contact").setValue(this.kdtLinkPerson.getRow(j).getCell("workPhone").getValue().toString());
	    	}else{
	    		continue;
	    	}
	    }
	    /*
	     * 处理只能勾选一个人为默认联系.新增第一行时为空 则返回
	     */
	    if(null == b ){
	    	return ;
	    }
	    if(b.booleanValue()){
			for( int i = 0 ; i< size ;i++){
				if(i ==index ){
					continue;
				}
				this.kdtLinkPerson.getRow(i).getCell("isDefault").setValue(Boolean.FALSE);
			}
	    }
	}
	protected void kdtSupplierAttachList_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2&&
				this.kdtSupplierAttachList.getColumnKey(e.getColIndex()).equals("attachment")) {
			String id=((SupplierAttachListEntryInfo)this.kdtSupplierAttachList.getRow(e.getRowIndex()).getUserObject()).getId().toString();
			AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
	        boolean isEdit = false;
	        if(OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState()))
	            isEdit = true;
	        AttachmentUIContextInfo info = new AttachmentUIContextInfo();
	        info.setBoID(id);
	        info.setEdit(isEdit);
	        String multi = (String)getUIContext().get("MultiapproveAttachment");
	        if(multi != null && multi.equals("true")){
	        	acm.showAttachmentListUIByBoIDNoAlready(this, info);
	        }else{
	        	acm.showAttachmentListUIByBoID(this, info);
	        }
	        this.kdtSupplierAttachList.getRow(e.getRowIndex()).getCell("attachment").setValue(loadAttachment(id));
		}
	}
	protected String loadAttachment(String id) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("boID", id));
		view.setFilter(filter);
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("attachment.name");
		view.setSelector(sels);
		BoAttchAssoCollection col = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		String name="";
		for(int i=0;i<col.size();i++){
			name=name+col.get(i).getAttachment().getName()+" ";
		}
		return name;
	}
	protected FDCDataBaseInfo getEditData() {
		return this.editData;
	}

	protected KDBizMultiLangBox getNameCtrl() {
		return this.txtName;
	}

	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}

	protected IObjectValue createNewData() {
		SupplierStockInfo info = new SupplierStockInfo();
		try {
			FDCSplAreaCollection col=FDCSplAreaFactory.getRemoteInstance().getFDCSplAreaCollection("select * from where number='01'");
			if(col.size()>0){
				SupplierSplAreaEntryInfo entry=new SupplierSplAreaEntryInfo();
				entry.setFdcSplArea(col.get(0));
				info.getSupplierSplAreaEntry().add(entry);
			}
			SupplierBusinessModeCollection modeCol=SupplierBusinessModeFactory.getRemoteInstance().getSupplierBusinessModeCollection("select * from where number='01'");
			if(modeCol.size()>0){
				info.setSupplierBusinessMode(modeCol.get(0));
			}
			info.setCreateTime(FDCCommonServerHelper.getServerTimeStamp());
		} catch (BOSException e) {
			e.printStackTrace();
		}
		info.setCurrency(CurrencyEnum.RMB);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		if(getUIContext().get("type")!=null){
			info.setInviteType((InviteTypeInfo)getUIContext().get("type"));
		}
		if(getUIContext().get("org")!=null){
			try {
				PurchaseOrgUnitInfo orgUnitInfo = PurchaseOrgUnitFactory.getRemoteInstance().getPurchaseOrgUnitInfo(new ObjectUuidPK(OrgConstants.DEF_CU_ID));
				info.setPurchaseOrgUnit(orgUnitInfo);
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		info.setHasCreatedSupplierBill(false);
		info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
		info.setAdminCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
//		SupplierLinkPersonInfo one=new SupplierLinkPersonInfo();
//		one.setPosition("法人代表/董事长");
//		
//		SupplierLinkPersonInfo two=new SupplierLinkPersonInfo();
//		two.setPosition("总经理");
//		
//		info.getLinkPerson().add(one);
//		info.getLinkPerson().add(two);
		return info;
	}

	protected ICoreBase getBizInterface() throws Exception {
		return SupplierStockFactory.getRemoteInstance();
	}
	protected void attachListeners() {
		addDataChangeListener(this.prmtSupplierFileType);
		addDataChangeListener(this.prmtProvince);
//		addDataChangeListener(this.prmtInviteType);
	}
	protected void detachListeners() {
		removeDataChangeListener(this.prmtSupplierFileType);
		removeDataChangeListener(this.prmtProvince);
//		removeDataChangeListener(this.prmtInviteType);
	}
    protected void addDataChangeListener(JComponent com) {
    	EventListener[] listeners = (EventListener[] )listenersMap.get(com);
    	if(listeners!=null && listeners.length>0){
	    	if(com instanceof KDPromptBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDPromptBox)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDFormattedTextField){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDFormattedTextField)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	}else if(com instanceof KDDatePicker){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDDatePicker)com).addDataChangeListener((DataChangeListener)listeners[i]);
	    		}
	    	} else if(com instanceof KDComboBox){
	    		for(int i=0;i<listeners.length;i++){
	    			((KDComboBox)com).addItemListener((ItemListener)listeners[i]);
	    		}
	    	}
    	}
    }
    protected void removeDataChangeListener(JComponent com) {
		EventListener[] listeners = null;	
		if(com instanceof KDPromptBox){
			listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDPromptBox)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDFormattedTextField){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDFormattedTextField)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDDatePicker){
    		listeners = com.getListeners(DataChangeListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDDatePicker)com).removeDataChangeListener((DataChangeListener)listeners[i]);
    		}
    	}else if(com instanceof KDComboBox){
    		listeners = com.getListeners(ItemListener.class);	
    		for(int i=0;i<listeners.length;i++){
    			((KDComboBox)com).removeItemListener((ItemListener)listeners[i]);
    		}
    	} 
		if(listeners!=null && listeners.length>0){
			listenersMap.put(com,listeners );
		}
    }
    private void kdTableAddRow(KDTable table) {
		if(!getOprtState().equals(OprtState.VIEW)){
			IRow row=table.addRow();
			if(table.equals(this.kdtSupplierAttachList)){
				SupplierAttachListEntryInfo info=new SupplierAttachListEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			}else if(table.equals(this.kdtLinkPerson)){
				SupplierLinkPersonInfo info=new SupplierLinkPersonInfo();
				row.setUserObject(info);
				if(this.kdtLinkPerson.getRowCount()==1){
					row.getCell("isDefault").setValue(Boolean.TRUE);
				}else{
					row.getCell("isDefault").setValue(Boolean.FALSE);
				}
			}
		}
			
	}
	private void kdTableInsertLine(KDTable table){
		if(!getOprtState().equals(OprtState.VIEW)){
			if(table == null)
	            return;
	        IRow row = null;
	        if(table.getSelectManager().size() > 0)
	        {
	            int top = table.getSelectManager().get().getTop();
	            if(isTableColumnSelected(table))
	                row = table.addRow();
	            else
	                row = table.addRow(top);
	        } else
	        {
	            row = table.addRow();
	        }
	        if(table.equals(this.kdtSupplierAttachList)&&row!=null){
				SupplierAttachListEntryInfo info=new SupplierAttachListEntryInfo();
				info.setId(BOSUuid.create(info.getBOSType()));
				row.setUserObject(info);
			}else if(table.equals(this.kdtLinkPerson)){
				SupplierLinkPersonInfo info=new SupplierLinkPersonInfo();
				row.setUserObject(info);
				if(this.kdtLinkPerson.getRowCount()==1){
					row.getCell("isDefault").setValue(Boolean.TRUE);
				}else{
					row.getCell("isDefault").setValue(Boolean.FALSE);
				}
			}
		}
	}
	protected final boolean isTableColumnSelected(KDTable table)
    {
        if(table.getSelectManager().size() > 0)
        {
            KDTSelectBlock block = table.getSelectManager().get();
            if(block.getMode() == 4 || block.getMode() == 8)
                return true;
        }
        return false;
    }
	private boolean confirmRemove(Component comp){
		return FDCMsgBox.isYes(FDCMsgBox.showConfirm2(comp, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Confirm_Delete")));
	}
	private void kdTableDeleteRow(KDTable table) {
		if(!getOprtState().equals(OprtState.VIEW)){
	        if(table.getSelectManager().size() == 0 || isTableColumnSelected(table)){
	            FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	            return;
	        }
	        if(confirmRemove(this)){
	            int top = table.getSelectManager().get().getBeginRow();
	            int bottom = table.getSelectManager().get().getEndRow();
	            for(int i = top; i <= bottom; i++){
	                if(table.getRow(top) == null)
	                {
	                    FDCMsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_NoneEntry"));
	                    return;
	                }
	                if(table.equals(this.kdtSupplierAttachList)){
	                	try {
	                		deleteAttachment(((SupplierAttachListEntryInfo)table.getRow(top).getUserObject()).getId().toString());
	                	} catch (BOSException e) {
							e.printStackTrace();
						} catch (EASBizException e) {
							e.printStackTrace();
						}
	                }
//	                if(table.equals(this.kdtLinkPerson)){
//	                	if(this.kdtLinkPerson.getRow(top).getCell("position").getValue()!=null
//	                			&&(this.kdtLinkPerson.getRow(top).getCell("position").getValue().toString().equals("法人代表/董事长")
//	                					||this.kdtLinkPerson.getRow(top).getCell("position").getValue().toString().equals("总经理"))){
//	                		FDCMsgBox.showWarning(this,"此行不能删除！");
//	                		return;
//	                	}
//	                }
	                table.removeRow(top);
	            }
	        }
		}
	}
	protected void deleteAttachment(String id) throws BOSException, EASBizException{
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		
		filter.getFilterItems().add(new FilterItemInfo("boID" , id));
		view.setFilter(filter);
		BoAttchAssoCollection col=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(view);
		if(col.size()>0){
			for(int i=0;i<col.size();i++){
				EntityViewInfo attview=new EntityViewInfo();
				FilterInfo attfilter = new FilterInfo();
				
				attfilter.getFilterItems().add(new FilterItemInfo("attachment.id" , col.get(i).getAttachment().getId().toString()));
				attview.setFilter(attfilter);
				BoAttchAssoCollection attcol=BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(attview);
				if(attcol.size()==1){
					BizobjectFacadeFactory.getRemoteInstance().delTempAttachment(id);
				}else if(attcol.size()>1){
					BoAttchAssoFactory.getRemoteInstance().delete(filter);
				}
			}
		}
	}
	private String getResource(String msg) {
		return EASResource.getString("com.kingdee.eas.fdc.invite.supplier.SupplierResource", msg);
	}
	protected void showSaveSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Save_OK"));
	    setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
	    setShowMessagePolicy(0);
	    setIsShowTextOnly(false);
	    showMessage();
	}
	protected void showSubmitSuccess() {
		setMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Submit_OK"));
	    if(chkMenuItemSubmitAndAddNew.isSelected())
	        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
	    else
	    if(!chkMenuItemSubmitAndPrint.isSelected() && chkMenuItemSubmitAndAddNew.isSelected())
	        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_AddNew"));
	    else
	        setNextMessageText(getClassAlise() + " " + EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_Edit"));
	    setIsShowTextOnly(false);
	    setShowMessagePolicy(0);
	    showMessage();
	}
	protected void verifyInput(ActionEvent e) throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, getNumberCtrl());
		FDCClientVerifyHelper.verifyEmpty(this, getNameCtrl());
		
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtProvince);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtCity);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtAddress);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtLinkPhone);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtLinkFax);
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtLinkMail);
		if(this.txtPunish.getText().trim().equals("")){
			FDCMsgBox.showWarning(this,"三年内是否有法律纠纷，是否被政府部门处罚不能为空！");
			SysUtil.abort();
		}
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtPunish);
		FDCClientVerifyHelper.verifyEmpty(this, this.comboEnterpriseKind);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtRegisterMoney);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtTaxRegisterNo);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBizRegisterNo);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtEnterpriseMaster);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtContractor);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtContractorPhone);
		
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtInviteType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtInviteTypeList);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtPurchaseOrgUnit);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtEnterpriseMaster);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplierFileType);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplierSplAreaEntry);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtSupplierBusinessMode);
		FDCClientVerifyHelper.verifyEmpty(this, this.prmtQuaLevel);
		
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBankName);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtBankCount);
		FDCClientVerifyHelper.verifyEmpty(this, this.cbTaxerQua);
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtBusinessNum);
//		FDCClientVerifyHelper.verifyEmpty(this, this.txtAbility);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtRecommended);
		FDCClientVerifyHelper.verifyEmpty(this, this.txtLxNumber);
		
		
		SupplierCollection col=SupplierFactory.getRemoteInstance().getSupplierCollection("select * from where taxRegisterNo='"+this.txtTaxRegisterNo.getText().trim()+"' and number!='FDC-"+this.txtNumber.getText()+"'");
		if(col.size()>0){
			FDCMsgBox.showWarning(this,"纳税人识别号重复，供应商编码："+col.get(0).getNumber());
			SysUtil.abort();
		}
		SupplierStockCollection applyCol;
		if(this.editData.getId()!=null){
			applyCol=SupplierStockFactory.getRemoteInstance().getSupplierStockCollection("select * from where taxRegisterNo='"+this.txtTaxRegisterNo.getText().trim()+"' and id!='"+this.editData.getId()+"'");
		}else{
			applyCol=SupplierStockFactory.getRemoteInstance().getSupplierStockCollection("select * from where taxRegisterNo='"+this.txtTaxRegisterNo.getText().trim()+"'");
		}
		if(applyCol.size()>0){
			FDCMsgBox.showWarning(this,"纳税人识别号重复，供应商档案登记编码："+applyCol.get(0).getNumber());
			SysUtil.abort();
		}
		
		String regex = "^[a-zA-Z0-9]+$";
        if(!this.txtTaxRegisterNo.getText().matches(regex)){
        	FDCMsgBox.showWarning(this,"纳税人识别号只支持英文字母或数字！");
			SysUtil.abort();
        }
		

		BankNumCollection bankNum=BankNumFactory.getRemoteInstance().getBankNumCollection("select number from where number='"+this.txtLxNumber.getText()+"'");
		if(bankNum.size()==0){
			FDCMsgBox.showWarning(this,"联行号不存在！");
			SysUtil.abort();
		}
		
		boolean isDefault=true;
		for(int i=0;i<this.kdtLinkPerson.getRowCount();i++){
			IRow row=this.kdtLinkPerson.getRow(i);
			if(((Boolean)row.getCell("isDefault").getValue()).booleanValue()){
				isDefault=false;
			}
			if(row.getCell("personName").getValue()==null||row.getCell("personName").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"授权联系人姓名不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("phone").getValue()==null||row.getCell("phone").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"授权联系人手机不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("workPhone").getValue()==null||row.getCell("workPhone").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"授权联系人办公电话不能为空！");
				SysUtil.abort();
			}
		}
		if(isDefault){
			FDCMsgBox.showWarning(this,"授权联系人不能为空！");
			SysUtil.abort();
		}
		
		if(!StringUtils.isEmpty(editData.getSupplierDescription())&& editData.getSupplierDescription().length()>300){
			FDCMsgBox.showWarning(this,"备注最长只可输入300个字符！");
			SysUtil.abort();
		}
		for(int i=0;i<this.kdtContractEntry.getRowCount();i++){
			IRow row=this.kdtContractEntry.getRow(i);
			if(row.getCell("contractName").getValue()==null||row.getCell("contractName").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"合同名称不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("contractAmount").getValue()==null||row.getCell("contractAmount").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"合同金额不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("model").getValue()==null||row.getCell("model").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"承包模式不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("manager").getValue()==null||row.getCell("manager").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"项目经理不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("workModel").getValue()==null||row.getCell("workModel").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"劳务分包模式不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("place").getValue()==null||row.getCell("place").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"工程地点不能为空！");
				SysUtil.abort();
			}
			if(row.getCell("supplierName").getValue()==null||row.getCell("supplierName").getValue().toString().trim().equals("")){
				FDCMsgBox.showWarning(this,"甲方名称不能为空！");
				SysUtil.abort();
			}
		}
		isExist();
	}
	public boolean checkBeforeWindowClosing(){
		if (hasWorkThreadRunning()) {
			return false;
		}
		if(getTableForPrintSetting()!=null){
			this.savePrintSetting(this.getTableForPrintSetting());
		}
		boolean b = true;
		if (!b) {
			return b;
		} else {
			if (isModify()) {
				int result = MsgBox.showConfirm3(this, EASResource.getString(FrameWorkClientUtils.strResource+ "Confirm_Save_Exit"));
				if (result == KDOptionPane.YES_OPTION) {
					try {
						if (editData.getState() == null|| editData.getState() == SupplierStateEnum.SAVE) {
							actionSave.setDaemonRun(false);
							ActionEvent event = new ActionEvent(btnSave,ActionEvent.ACTION_PERFORMED, btnSave.getActionCommand());
							actionSave.actionPerformed(event);
							return !actionSave.isInvokeFailed();
						} else {
							actionSubmit.setDaemonRun(false);
							ActionEvent event = new ActionEvent(btnSubmit,ActionEvent.ACTION_PERFORMED, btnSubmit.getActionCommand());
							actionSubmit.actionPerformed(event);
							return !actionSubmit.isInvokeFailed();
						}
					} catch (Exception exc) {
						return false;
					}
				} else if (result == KDOptionPane.NO_OPTION) {
					if(editData!=null){
						for(int i=0;i<this.editData.getSupplierAttachListEntry().size();i++){
							if(this.editData.getSupplierAttachListEntry().get(i).getId()==null) continue;
							try {
								if(!SupplierAttachListEntryFactory.getRemoteInstance().exists(new ObjectUuidPK(this.editData.getSupplierAttachListEntry().get(i).getId().toString()))){
									this.deleteAttachment(this.editData.getSupplierAttachListEntry().get(i).getId().toString());
								}
							} catch (EASBizException e) {
								e.printStackTrace();
							} catch (BOSException e) {
								e.printStackTrace();
							}
						}
					}
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionCopy_actionPerformed(e);
    	FDCHelper.handleCodingRule(this.txtNumber, this.oprtState, editData, this.getBizInterface(),null);
    	this.txtStorageNumber.setText(null);
    	this.pkStorageDate.setValue(null);
    	
    	this.cbIsPass.setSelectedItem(null);
    	this.prmtCreator.setValue(null);
    	this.prmtAuditor.setValue(null);
    	this.pkCreateTime.setValue(null);
    	this.pkAuditDate.setValue(null);
    	this.kDTabbedPane2.removeAll();
    	
    	this.editData.setLevel(null);
    	this.editData.setGrade(null);
    	this.editData.put("isPass", null);
    }
	private void isExist(){
		if(this.prmtInviteType.getValue()==null)return;
	    FilterInfo filterInfo=new FilterInfo();
	    FilterItemInfo itemInfo = new FilterItemInfo("name", txtName.getSelectedItem().toString(), CompareType.EQUALS);
	    filterInfo.getFilterItems().add(itemInfo);
	    
	    // 同级CU及下级CU不允许出现重名，如公司名称、营业执照等 By Owen_wen 2011-02-23
//	    filterInfo.getFilterItems().add((new FilterItemInfo("CU.id", SysContext.getSysContext().getCurrentCtrlUnit().getId().toString())));
//	    filterInfo.getFilterItems().add((new FilterItemInfo("inviteType.id", ((InviteTypeInfo)this.prmtInviteType.getValue()).getId().toString())));
	    if(editData!=null&&editData.getId()!=null){
	    	FilterItemInfo itemInfo_2 = new FilterItemInfo("id", editData.getId().toString(), CompareType.NOTEQUALS);
	    	filterInfo.getFilterItems().add(itemInfo_2);
		} 
	    boolean flag = false;
		try {
			flag = SupplierStockFactory.getRemoteInstance().exists(filterInfo);
		} catch (Exception e) {
			handUIException(e);
		}
		if(flag){
			MsgBox.showInfo(this,getResource("firmName")+txtName.getSelectedItem().toString()+getResource("againInput"));
			abort();
		}
    }
	protected void prmtInviteType_dataChanged(DataChangeEvent e)throws Exception {
		 boolean isChanged = true;
		 isChanged = BizCollUtil.isF7ValueChanged(e);
         if(!isChanged){
        	 return;
         }
         InviteTypeInfo info=(InviteTypeInfo) this.prmtInviteType.getValue();
         if(info!=null){
        	 info=InviteTypeFactory.getRemoteInstance().getInviteTypeInfo("select inviteListType.inviteListType.*, from where id='"+info.getId().toString()+"'");
 			 Set id = new HashSet();
 			 for (int i = 0; i < info.getInviteListType().size(); i++) {
 				if (info.getInviteListType().get(i).getInviteListType() != null) {
 					id.add(info.getInviteListType().get(i).getInviteListType().getId().toString());
 				}
 			}
 			
 			EntityViewInfo view = new EntityViewInfo();
 			FilterInfo filter = new FilterInfo();
 			if (id.size() > 0) {
 				filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
 			} else {
 				filter.getFilterItems().add(new FilterItemInfo("id", null));
 			}
 			view.setFilter(filter);
 			this.prmtInviteTypeList.setEntityViewInfo(view);
 			
 			
 			if(this.prmtInviteTypeList.getValue()!=null&&!id.contains((Object[])this.prmtInviteTypeList.getValue())){
				this.prmtInviteTypeList.setValue(null);
			}else if(info.getInviteListType().size()==1){
				Object[] value=new Object[1];
				value[0]=info.getInviteListType().get(0).getInviteListType();
				this.prmtInviteTypeList.setValue(value);
			}
			this.prmtInviteTypeList.setEnabled(true);
 			
         }else{
        	 this.prmtInviteTypeList.setValue(null);
        	 this.prmtInviteTypeList.setEnabled(false);
         }
	}
	 protected BOSUuid getAuditResultIDForWF()
     {
/* <-MISALIGNED-> */ /* 276*/        return editData.getId();
     }
	public void actionAuditResult_actionPerformed(ActionEvent e)
			throws Exception {
		 BOSUuid id = getAuditResultIDForWF();
		 /* <-MISALIGNED-> */ /* 259*/        if(editData == null || id == null)
		                 {
		 /* <-MISALIGNED-> */ /* 260*/            MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_WFHasNotInstance"));
		 /* <-MISALIGNED-> */ /* 263*/            abort();
		                 }
		 /* <-MISALIGNED-> */ /* 266*/        MultiApproveUtil.showApproveHis(id, UIModelDialogFactory.class.getName(), this);
	}
	@Override
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		String selectId = null;
		/* <-MISALIGNED-> */ /*2974*/        if(editData.getId() != null)
		/* <-MISALIGNED-> */ /*2975*/            selectId = editData.getId().toString();
		/* <-MISALIGNED-> */ /*2977*/        IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
		/* <-MISALIGNED-> */ /*2979*/        ProcessInstInfo processInstInfo = null;
		/* <-MISALIGNED-> */ /*2980*/        ProcessInstInfo procInsts[] = service2.getProcessInstanceByHoldedObjectId(selectId);
		/* <-MISALIGNED-> */ /*2982*/        int i = 0;
		/* <-MISALIGNED-> */ /*2982*/        for(int n = procInsts.length; i < n; i++)
		/* <-MISALIGNED-> */ /*2983*/            if(procInsts[i].getState().startsWith("open"))
		/* <-MISALIGNED-> */ /*2984*/                processInstInfo = procInsts[i];
		/* <-MISALIGNED-> */ /*2987*/        if(processInstInfo == null)
		                {
		/* <-MISALIGNED-> */ /*2989*/            if(!StringUtils.isEmpty(selectId))
		                    {
		/* <-MISALIGNED-> */ /*2991*/                procInsts = service2.getAllProcessInstancesByBizobjId(selectId);
		/* <-MISALIGNED-> */ /*2992*/                if(procInsts == null || procInsts.length <= 0)
		/* <-MISALIGNED-> */ /*2993*/                    actionViewSubmitProccess_actionPerformed(e);
		/* <-MISALIGNED-> */ /*2994*/                else
		/* <-MISALIGNED-> */ /*2994*/                if(procInsts.length == 1)
		/* <-MISALIGNED-> */ /*2995*/                    showWorkflowDiagram(procInsts[0]);
		/* <-MISALIGNED-> */ /*2997*/                else
		/* <-MISALIGNED-> */ /*2997*/                    showWorkflowListDiagram(procInsts);
		                    } else
		                    {
		/* <-MISALIGNED-> */ /*3000*/                actionViewSubmitProccess_actionPerformed(e);
		                    }
		                } else
		                {
		/* <-MISALIGNED-> */ /*3003*/            showWorkflowDiagram(processInstInfo);
		                }
	}
	 public void actionViewSubmitProccess_actionPerformed(ActionEvent e)
     throws Exception
 {
/* <-MISALIGNED-> */ /*2297*/        if(editData.getBOSType() == null)
/* <-MISALIGNED-> */ /*2298*/            return;
/* <-MISALIGNED-> */ /*2301*/        IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
/* <-MISALIGNED-> */ /*2303*/        IObjectValue bizObj = getBizInfo4Wf();
/* <-MISALIGNED-> */ /*2304*/        storeEditData4WfPreview();
/* <-MISALIGNED-> */ /*2305*/        String procDefID = service.findSubmitProcDef(SysContext.getSysContext().getCurrentUserInfo().getId().toString(), bizObj, getWFUIFuctionName(), getWFActionName());
/* <-MISALIGNED-> */ /*2309*/        if(procDefID != null)
     {
/* <-MISALIGNED-> */ /*2311*/            ProcessDefInfo processDefInfo = service.getProcessDefInfo(procDefID);
/* <-MISALIGNED-> */ /*2313*/            java.util.Locale currentLocale = SysContext.getSysContext().getLocale();
/* <-MISALIGNED-> */ /*2314*/            ProcessDef processDef = service.getProcessDefByDefineHashValue(processDefInfo.getMd5HashValue());
/* <-MISALIGNED-> */ /*2317*/            String procDefDiagramTitle = "";
/* <-MISALIGNED-> */ /*2318*/            if(processDef != null)
/* <-MISALIGNED-> */ /*2319*/                procDefDiagramTitle = processDef.getName(currentLocale);
/* <-MISALIGNED-> */ /*2321*/            UIContext uiContext = new UIContext(this);
/* <-MISALIGNED-> */ /*2322*/            uiContext.put("define", processDef);
/* <-MISALIGNED-> */ /*2323*/            uiContext.put("title", procDefDiagramTitle);
/* <-MISALIGNED-> */ /*2325*/            BasicShowWfDefinePanel.Show(uiContext);
     } else
     {
/* <-MISALIGNED-> */ /*2327*/            MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_WFHasNotDef"));
     }
 }
	 private void showWorkflowDiagram(ProcessInstInfo processInstInfo)
     throws Exception
 {
/* <-MISALIGNED-> */ /*3011*/        UIContext uiContext = new UIContext(this);
/* <-MISALIGNED-> */ /*3012*/        uiContext.put("id", processInstInfo.getProcInstId());
/* <-MISALIGNED-> */ /*3013*/        uiContext.put("processInstInfo", processInstInfo);
/* <-MISALIGNED-> */ /*3016*/        BasicWorkFlowMonitorPanel.Show(uiContext);
 }
	 protected String getWFUIFuctionName()
     {
/* <-MISALIGNED-> */ /*2498*/        return null;
     }
	 protected String getWFActionName()
     {
/* <-MISALIGNED-> */ /*2507*/        return null;
     }
	 private IObjectValue getBizInfo4Wf()
     {
/* <-MISALIGNED-> */ /*4051*/        IObjectValue copy = AgentUtility.getNoAgentValue(editData);
/* <-MISALIGNED-> */ /*4052*/        if(copy instanceof ICommonBOSType)
         {
/* <-MISALIGNED-> */ /*4054*/            com.kingdee.bos.metadata.IMetaDataPK pk = ((ICommonBOSType)editData).getPK();
/* <-MISALIGNED-> */ /*4055*/            ((ICommonBOSType)copy).setPK(pk);
/* <-MISALIGNED-> */ /*4056*/            ((ICommonBOSType)copy).setBOSType(editData.getBOSType());
         }
/* <-MISALIGNED-> */ /*4058*/        return copy;
     }
	 protected void storeEditData4WfPreview()
     {
/* <-MISALIGNED-> */ /*2514*/        try
         {
/* <-MISALIGNED-> */ /*2514*/            storeFields();
         }
/* <-MISALIGNED-> */ /*2515*/        catch(Throwable e)
         {
/* <-MISALIGNED-> */ /*2516*/            logger.error(e.getMessage(), e);
         }
     }
	 private void showWorkflowListDiagram(ProcessInstInfo processInstInfos[])
     throws UIException
 {
/* <-MISALIGNED-> */ /*3027*/        UIContext uiContext = new UIContext(this);
/* <-MISALIGNED-> */ /*3028*/        uiContext.put("procInsts", processInstInfos);
/* <-MISALIGNED-> */ /*3029*/        String className = ProcessRunningListUI.class.getName();
/* <-MISALIGNED-> */ /*3030*/        IUIWindow uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(className, uiContext);
/* <-MISALIGNED-> */ /*3032*/        uiWindow.show();
 }
}