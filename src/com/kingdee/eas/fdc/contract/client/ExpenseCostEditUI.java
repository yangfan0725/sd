/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.ctrl.extendcontrols.ExtendParser;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDComboBox;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDNumberTextField;
import com.kingdee.bos.ctrl.swing.KDTextArea;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.attachment.AttachmentFactory;
import com.kingdee.eas.base.attachment.AttachmentFtpFacadeFactory;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.IAttachment;
import com.kingdee.eas.base.attachment.util.FileGetter;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.master.cssp.SupplierInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.cp.bc.BizCollUtil;
import com.kingdee.eas.fdc.basedata.ContractDetailDefCollection;
import com.kingdee.eas.fdc.basedata.ContractDetailDefFactory;
import com.kingdee.eas.fdc.basedata.ContractDetailDefInfo;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.DataTypeEnum;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.BankNumCollection;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.eas.fdc.contract.BusinessTypeEnum;
import com.kingdee.eas.fdc.contract.ExpenseApplyFactory;
import com.kingdee.eas.fdc.contract.ExpenseApplyInfo;
import com.kingdee.eas.fdc.contract.ExpenseCostEntryInfo;
import com.kingdee.eas.fdc.contract.ExpenseTypeEnum;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.TripApplyFactory;
import com.kingdee.eas.fdc.contract.TripApplyInfo;
import com.kingdee.eas.fdc.contract.ExpenseCostCollection;
import com.kingdee.eas.fdc.contract.ExpenseCostFactory;
import com.kingdee.eas.fdc.contract.ExpenseCostInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCFormattedTextField;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.client.CasPaymentBillUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ExpenseCostEditUI extends AbstractExpenseCostEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(ExpenseCostEditUI.class);
    
    /**
     * output class constructor
     */
    public ExpenseCostEditUI() throws Exception
    {
        super();
    }
    public void onLoad() throws Exception {
    	super.onLoad();
    	
    	this.actionRemove.setVisible(false);
    	this.actionAddLine.setVisible(false);
    	this.actionInsertLine.setVisible(false);
    	this.actionRemoveLine.setVisible(false);
    	this.actionCopy.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	this.actionCreateFrom.setVisible(false);
    	this.actionNext.setVisible(false);
    	this.actionPre.setVisible(false);
    	this.actionTraceDown.setVisible(true);
    	this.actionTraceUp.setVisible(false);
    	this.actionFirst.setVisible(false);
    	this.actionLast.setVisible(false);
    	
    	this.actionAudit.setEnabled(true);
    	this.actionUnAudit.setEnabled(true);
    	
    	this.actionAudit.setVisible(true);
    	this.actionUnAudit.setVisible(true);
    	
    	this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		
		this.tblAttachement.checkParsed();
		this.actionAttachment.putValue("SmallIcon", EASResource.getIcon("imgTbtn_affixmanage"));
		btnAttachment = (KDWorkButton) this.contAttachment.add(this.actionAttachment);
		btnAttachment.setText("附件管理");
		btnAttachment.setSize(new Dimension(140, 19));
		
		String cuId = editData.getCU().getId().toString();
		FDCClientUtils.setPersonF7(prmtPerson, this,
				canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setRespDeptF7(prmtDept, this,
				canSelectOtherOrgPerson ? null : cuId);
		

		FDCClientUtils.initSupplierF7(this, prmtSupplier, cuId);
		
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isExpense",Boolean.TRUE));
		view.setFilter(filter);
		this.prmtContractType.setEntityViewInfo(view);
		
		ContractTypeInfo info = (ContractTypeInfo) this.prmtContractType.getValue();
		if(info!=null){
			info=ContractTypeFactory.getRemoteInstance().getContractTypeInfo("select *,contractWFTypeEntry.contractWFType.*,inviteTypeEntry.inviteType.* from where id='"+info.getId().toString()+"'");
			Set id = new HashSet();
			for (int i = 0; i < info.getContractWFTypeEntry().size(); i++) {
				if (info.getContractWFTypeEntry().get(i).getContractWFType() != null) {
					id.add(info.getContractWFTypeEntry().get(i).getContractWFType().getId().toString());
				}
			}
			view = new EntityViewInfo();
			filter = new FilterInfo();
			if (id.size() > 0) {
				filter.getFilterItems().add(new FilterItemInfo("id", id, CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}
			view.setFilter(filter);
			this.prmtContractWFType.setEntityViewInfo(view);
			this.prmtContractWFType.setEnabled(true);
		}else{
			this.prmtContractWFType.setEnabled(false);
		}
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.actionAddNew.setVisible(false);
		
		this.prmtCreator.setDisplayFormat("$name$");
		this.prmtAuditor.setDisplayFormat("$name$");
		
		this.prmtContractType.setSelector(new ContractTypePromptSelector(this));
		
		ExtendParser bankParser = new ExtendParser(this.prmtBankNum);
		this.prmtBankNum.setCommitParser(bankParser);
		this.prmtBankNum.setCommitFormat("$number$;$name$");
		this.prmtBankNum.setDisplayFormat("$name$");
    }
    private boolean canSelectOtherOrgPerson = false;
    protected void fetchInitParam() throws Exception {
		super.fetchInitParam();
		Map param = (Map) ActionCache.get("FDCBillEditUIHandler.orgParamItem");
		if (param == null) {
			param = FDCUtils.getDefaultFDCParam(null, orgUnitInfo.getId()
					.toString());
		}
		if (param.get(FDCConstants.FDC_PARAM_SELECTPERSON) != null) {
			canSelectOtherOrgPerson = Boolean.valueOf(
					param.get(FDCConstants.FDC_PARAM_SELECTPERSON).toString())
					.booleanValue();
		}
	}
    public void fillAttachmnetTable() throws EASBizException, BOSException {
		this.tblAttachement.removeRows();
		String boId = null;
		if (this.editData.getId() == null) {
			return;
		} else {
			boId = this.editData.getId().toString();
		}

		if (boId != null) {
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			sic.add(new SelectorItemInfo("attachment.createTime"));
			sic.add(new SelectorItemInfo("attachment.attachID"));
			sic.add(new SelectorItemInfo("attachment.beizhu"));
			sic.add(new SelectorItemInfo("assoType"));
			sic.add(new SelectorItemInfo("boID"));

			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID", boId));
			EntityViewInfo evi = new EntityViewInfo();
			evi.getSorter().add(new SorterItemInfo("boID"));
			evi.getSorter().add(new SorterItemInfo("attachment.name"));
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			try {
				cols = BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				e.printStackTrace();
			}
			boolean flag = false;
			if (cols != null && cols.size() > 0) {
				tblAttachement.checkParsed();
				for (Iterator it = cols.iterator(); it.hasNext();) {
					BoAttchAssoInfo boaInfo = (BoAttchAssoInfo)it.next();
					AttachmentInfo attachment = boaInfo.getAttachment();
					IRow row = tblAttachement.addRow();
					row.getCell("id").setValue(attachment.getId().toString());
					row.getCell("seq").setValue(attachment.getAttachID());
					row.getCell("name").setValue(attachment.getName());
					row.getCell("date").setValue(attachment.getCreateTime());
					row.getCell("type").setValue(boaInfo.getAssoType());
				}
			}
		}
	}
	protected void attachListeners() {
		addDataChangeListener(pkBizDate);
		addDataChangeListener(cbExpenseType);
		addDataChangeListener(prmtContractType);
		addDataChangeListener(prmtSupplier);
	}
	protected void detachListeners() {
		removeDataChangeListener(pkBizDate);
		removeDataChangeListener(cbExpenseType);
		removeDataChangeListener(prmtContractType);
		removeDataChangeListener(prmtSupplier);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ExpenseCostFactory.getRemoteInstance();
	}
	protected KDTable getDetailTable() {
		return null;
	}
	protected KDTextField getNumberCtrl() {
		return this.txtNumber;
	}
	public SelectorItemCollection getSelectors() {
    	SelectorItemCollection sic = super.getSelectors();
    	sic.add("CU.*");
    	sic.add("*");
    	sic.add("curProject.*");
    	sic.add("curProject.CU.*");
    	sic.add("curProject.fullOrgUnit.name");
    	sic.add("expenseApply.bizDate");
    	sic.add("contractType.contractWFTypeEntry.contractWFType.*");
    	return sic;
    }
	
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
		handleCodingRule();
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSubmit_actionPerformed(e);
		if (editData.getState() == FDCBillStateEnum.AUDITTING) {
			btnSave.setEnabled(false);
			btnSubmit.setEnabled(false);
			btnEdit.setEnabled(false);
			btnRemove.setEnabled(false);
		}
		this.setOprtState("VIEW");
	}
	
	public boolean isBillInWorkflow(String id) throws BOSException{
		ProcessInstInfo instInfo = null;
		ProcessInstInfo procInsts[] = null;

		IEnactmentService service2 = EnactmentServiceFactory.createRemoteEnactService();
		procInsts = service2.getProcessInstanceByHoldedObjectId(id);
		int i = 0;
		for(int n = procInsts.length; i < n; i++){
			if("open.running".equals(procInsts[i].getState()) || "open.not_running.suspended".equals(procInsts[i].getState())){
				instInfo = procInsts[i];
			}
		}
		if(instInfo != null){
			return true;
		}else{
			return false;
		}
    }
	
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(true);
		this.actionUnAudit.setEnabled(true);
		this.actionAudit.setVisible(false);
		this.actionAudit.setEnabled(false);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		super.actionUnAudit_actionPerformed(e);
		this.actionUnAudit.setVisible(false);
		this.actionUnAudit.setEnabled(false);
		this.actionAudit.setVisible(true);
		this.actionAudit.setEnabled(true);
	}
	public void setOprtState(String oprtType) {
		super.setOprtState(oprtType);
		if (oprtType.equals(OprtState.VIEW)) {
			this.lockUIForViewStatus();
		} else {
			this.unLockUI();
		}
	}
	public void loadFields() {
		isOnload=true;
		detachListeners();
		this.tblEntry.checkParsed();
		super.loadFields();
		try {
			fillAttachmnetTable();
		} catch (EASBizException e) {
			handleException(e);
		} catch (BOSException e) {
			handleException(e);
		}
		if (editData != null && editData.getCurProject() != null) {
			txtProj.setText(editData.getCurProject().getDisplayName());

			FullOrgUnitInfo costOrg = this.orgUnitInfo;
			
			txtOrg.setText(editData.getCurProject().getFullOrgUnit().getName());
			editData.setOrgUnit(costOrg);
			editData.setCU(editData.getCurProject().getCU());
		}
		try {
			cbExpenseType_itemStateChanged(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			KDTDefaultCellEditor editorString = getEditorByDataType(DataTypeEnum.STRING);
			for(int i=0;i<this.tblEntry.getRowCount();i++){
				IRow row=this.tblEntry.getRow(i);
				String detailDefId=row.getCell("detailDef.id").getValue().toString();
				ContractDetailDefInfo info = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefInfo(new ObjectUuidPK(detailDefId));
				if (info.isIsMustInput()){
					this.setRequiredBGColor(row);
				}
				KDTDefaultCellEditor editor = getEditorByDataType(info.getDataTypeEnum());
				if (editor != null) {
					row.getCell(CONTENT_COL).setEditor(editor);
				}
				if(info.getName().equals("招待类型")){
					KDComboBox cb = new KDComboBox();
					cb.addItems(BusinessTypeEnum.getEnumList().toArray());
					
					KDTDefaultCellEditor cbEditor=new KDTDefaultCellEditor(cb);
					row.getCell(CONTENT_COL).setEditor(cbEditor);
				}
				if (info.getDataTypeEnum() == DataTypeEnum.DATE) {
					row.getCell(CONTENT_COL).getStyleAttributes().setNumberFormat(
							"%r{yyyy-M-d}t");
				} else if (info.getDataTypeEnum() == DataTypeEnum.NUMBER) {
					row.getCell(CONTENT_COL).getStyleAttributes()
							.setHorizontalAlign(HorizontalAlignment.RIGHT);
				}
				row.getCell(DESC_COL).setEditor(editorString);
			}
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attachListeners();
		isOnload=false;
	}
	private KDComboBox isLonelyCalCombo = null;
	private KDComboBox getBooleanCombo() {
		isLonelyCalCombo = new KDComboBox();
		isLonelyCalCombo.addItems(BooleanEnum.getEnumList().toArray());
		return isLonelyCalCombo;
	}
	public KDTDefaultCellEditor getEditorByDataType(DataTypeEnum dataType) {
		if (dataType == DataTypeEnum.DATE) {
			//KDDatePicker datePicker = new KDDatePicker();
			return FDCClientHelper.getDateCellEditor();
		} else if (dataType == DataTypeEnum.BOOL) {
			KDComboBox booleanCombo = getBooleanCombo();
			return new KDTDefaultCellEditor(booleanCombo);
		} else if (dataType == DataTypeEnum.NUMBER) {
			return FDCClientHelper.getNumberCellEditor();
		} else if (dataType == DataTypeEnum.STRING) {

			KDTextArea indexValue_TextField = new KDTextArea();
			indexValue_TextField.setName("indexValue_TextField");
			indexValue_TextField.setVisible(true);
			indexValue_TextField.setEditable(true);
			indexValue_TextField.setMaxLength(1000);
			indexValue_TextField.setColumns(10);
			indexValue_TextField.setWrapStyleWord(true);
			indexValue_TextField.setLineWrap(true);
			indexValue_TextField.setAutoscrolls(true);

			KDTDefaultCellEditor indexValue_CellEditor = new KDTDefaultCellEditor(
					indexValue_TextField);
			indexValue_CellEditor.setClickCountToStart(1);
			return indexValue_CellEditor;
		}
		return null;
	}
	private static final String CONTENT_COL = ContractClientUtils.CON_DETIAL_CONTENT_COL;

	private static final String DESC_COL = ContractClientUtils.CON_DETIAL_DESC_COL;
	private void setRequiredBGColor(IRow row){
		row.getCell(CONTENT_COL).getStyleAttributes().setBackground(FDCColorConstants.requiredColor);
	}
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
		fillAttachmnetTable();
	}
	protected IObjectValue createNewData() {
		ExpenseCostInfo info=new ExpenseCostInfo();
		info.setId(BOSUuid.create(info.getBOSType()));
		Date now=new Date();
		try {
			now=FDCCommonServerHelper.getServerTimeStamp();
		} catch (BOSException e) {
			logger.error(e.getMessage());
		}
		try {
			PeriodInfo bookedPeriod = PeriodUtils.getPeriodInfo(new Date(), new ObjectUuidPK(SysContext.getSysContext().getCurrentCtrlUnit().getId()));
			info.setPeriod(bookedPeriod);
		} catch (EASBizException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
		info.setBizDate(now);
		info.setState(FDCBillStateEnum.SAVED);
		info.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		
		String tripId = (String) getUIContext().get("expenseId");
		try {
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("person.*");
			sic.add("dept.*");
			sic.add("curProject.*");
	    	sic.add("curProject.CU.*");
	    	sic.add("curProject.fullOrgUnit.*");
	    	sic.add("contractType.*");
	    	sic.add("contractWFType.*");
	    	sic.add("entry.*");
			ExpenseApplyInfo tripApplyInfo=ExpenseApplyFactory.getRemoteInstance().getExpenseApplyInfo(new ObjectUuidPK(tripId), sic);
			info.setExpenseApply(tripApplyInfo);
			info.setCurProject(tripApplyInfo.getCurProject());
			info.setContractType(tripApplyInfo.getContractType());
			info.setContractWFType(tripApplyInfo.getContractWFType());
			info.setPerson(tripApplyInfo.getPerson());
			info.setDept(tripApplyInfo.getDept());
			info.setExpenseType(tripApplyInfo.getExpenseType());
			
			info.setPurpose(tripApplyInfo.getPurpose());
			info.setName(tripApplyInfo.getName());
			for(int i=0;i<tripApplyInfo.getEntry().size();i++){
				ExpenseCostEntryInfo entry=new ExpenseCostEntryInfo();
				entry.setSeq(tripApplyInfo.getEntry().get(i).getSeq());
				entry.setDetail(tripApplyInfo.getEntry().get(i).getDetail());
				entry.setContent(tripApplyInfo.getEntry().get(i).getContent());
				entry.setDesc(tripApplyInfo.getEntry().get(i).getDesc());
				entry.setRowKey(tripApplyInfo.getEntry().get(i).getRowKey());
				entry.setDataType(tripApplyInfo.getEntry().get(i).getDataType());
				entry.setDetailDefID(tripApplyInfo.getEntry().get(i).getDetailDefID());
				
				info.getEntry().add(entry);
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	@Override
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		FDCClientVerifyHelper.verifyEmpty(this, this.txtPurpose);
		
		BigDecimal appAmount=this.editData.getExpenseApply().getAmount();
		ExpenseCostCollection col=ExpenseCostFactory.getRemoteInstance().getExpenseCostCollection("select amount from where expenseApply.id='"+this.editData.getExpenseApply().getId().toString()+"' and id!='"+this.editData.getId().toString()+"' and state!='1SAVED'");
		BigDecimal totalAmount=FDCHelper.ZERO;
		for(int i=0;i<col.size();i++){
			totalAmount=FDCHelper.add(totalAmount,col.get(i).getAmount());
		}
		if(this.txtAmount.getBigDecimalValue().add(totalAmount).compareTo(appAmount)>0){
			MsgBox.showWarning("报销总金额大于申请金额！");
			SysUtil.abort();
		}
		if(this.tblAttachement.getRowCount()==0){
			MsgBox.showWarning("请先上传附件！");
			SysUtil.abort();
		}
		for(int i=0;i<this.tblEntry.getRowCount();i++){
			IRow row=this.tblEntry.getRow(i);
			if(row.getCell(CONTENT_COL).getStyleAttributes().getBackground().equals(FDCColorConstants.requiredColor)){
				if(row.getCell(CONTENT_COL).getValue()==null||row.getCell(CONTENT_COL).toString().trim().equals("")){
					FDCMsgBox.showWarning(this,"内容不能为空！");
					this.tblEntry.getEditManager().editCellAt(row.getRowIndex(), this.tblEntry.getColumnIndex(CONTENT_COL));
					SysUtil.abort();
				}
			}
		}
	}
	protected String getTDFileName() {
		return "/bim/fdc/contract/ExpenseCost";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.ExpenseCostQuery");
	}
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		ExpenseCostEditDataProvider dataPvd = new ExpenseCostEditDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		ExpenseCostEditDataProvider dataPvd = new ExpenseCostEditDataProvider(
				editData.getString("id"), getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), dataPvd, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		String id = this.editData.getId().toString();
		PaymentBillCollection col=PaymentBillFactory.getRemoteInstance().getPaymentBillCollection("select id from where sourceBillId='"+id+"'");
		if(col.size()>0){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", col.get(0).getId().toString());
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(CasPaymentBillUI.class.getName(), uiContext,null,OprtState.VIEW);
	        uiWindow.show();
	        return;
		}
		FDCMsgBox.showInfo(this,"目标单据为空！");
	}
	private  FileGetter fileGetter;
	private  FileGetter getFileGetter() throws Exception {
        if (fileGetter == null)
            fileGetter = new FileGetter((IAttachment) AttachmentFactory.getRemoteInstance(), AttachmentFtpFacadeFactory.getRemoteInstance());
        return fileGetter;
    }
	protected void tblAttachement_tableClicked(KDTMouseEvent e)throws Exception {
		if(e.getType() == 1 && e.getButton() == 1 && e.getClickCount() == 2)
		{
			IRow row  =  tblAttachement.getRow(e.getRowIndex());
			getFileGetter();
			Object selectObj= row.getCell("id").getValue();
			if(selectObj!=null){
				String attachId=selectObj.toString();
				fileGetter.viewAttachment(attachId);
			}
			
		}
	}
	private boolean isOnload=false;
	protected void pkBizDate_dataChanged(DataChangeEvent e) throws Exception {
		if(isOnload){
			return;
		}
		Date date=(Date) this.pkBizDate.getValue();
		if(date!=null&&FDCDateHelper.getDiffDays(this.editData.getExpenseApply().getBizDate(),date)<1){
			MsgBox.showWarning(this,"报销日期必须大于等于申请日期！");
			this.pkBizDate.setValue(null);
			SysUtil.abort();
		}
	}
	public void storeFields() {
		try {
			for(int i=0;i<this.tblEntry.getRowCount();i++){
				IRow row=this.tblEntry.getRow(i);
				ContractDetailDefInfo info = ContractDetailDefFactory.getRemoteInstance().getContractDetailDefInfo(new ObjectUuidPK(row.getCell("detailDef.id").getValue().toString()));
				if(info.getDataTypeEnum().equals(DataTypeEnum.DATE)){
					if(row.getCell("content").getValue()!=null){
						if(row.getCell("content").getValue() instanceof Date){
							row.getCell("content").setValue(FDCDateHelper.formatDate2((Date)row.getCell("content").getValue()));
						}
					}
				}
			}
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		if(this.prmtBankNum.getText()!=null){
			try {
				BankNumCollection col=BankNumFactory.getRemoteInstance().getBankNumCollection("select * from where name='"+this.prmtBankNum.getText()+"'");
				if(col.size()>0){
					this.editData.setBankNum(col.get(0));
				}else{
					this.editData.setBankNum(null);
				}
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
		this.editData.setOriginalAmount(this.txtAmount.getBigDecimalValue());
		super.storeFields();
	}
	protected void btnViewApply_actionPerformed(ActionEvent e) throws Exception {
		if(this.editData.getExpenseApply()!=null){
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", this.editData.getExpenseApply().getId());
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
	        IUIWindow uiWindow = uiFactory.create(ExpenseApplyEditUI.class.getName(), uiContext,null,OprtState.VIEW);
	        uiWindow.show();
		}
	}
	@Override
	protected void supplier_dataChanged(DataChangeEvent e) throws Exception {
		SupplierInfo info=(SupplierInfo) this.prmtSupplier.getValue();
		if(info!=null){
			ExpenseCostCollection col=ExpenseCostFactory.getRemoteInstance().getExpenseCostCollection("select receiveBank,accountNumber from where state='4AUDITTED' and id!='"+this.editData.getId()+"' and supplier.id='"+info.getId()+"' order by createTime desc");
			if(col.size()>0){
				this.prmtBankNum.setValue(col.get(0).getReceiveBank());
				this.txtAccountNumber.setText(col.get(0).getAccountNumber());
			}
		}
	}
}