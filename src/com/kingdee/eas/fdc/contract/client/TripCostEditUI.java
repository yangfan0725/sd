/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Dimension;
import java.awt.event.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
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
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
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
import com.kingdee.eas.basedata.assistant.PeriodInfo;
import com.kingdee.eas.basedata.assistant.PeriodUtils;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.ContractTypePromptSelector;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.TripApplyFactory;
import com.kingdee.eas.fdc.contract.TripApplyInfo;
import com.kingdee.eas.fdc.contract.TripCostCollection;
import com.kingdee.eas.fdc.contract.TripCostFactory;
import com.kingdee.eas.fdc.contract.TripCostInfo;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.client.CasPaymentBillUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class TripCostEditUI extends AbstractTripCostEditUI
{
    private static final Logger logger = CoreUIObject.getLogger(TripCostEditUI.class);
    
    /**
     * output class constructor
     */
    public TripCostEditUI() throws Exception
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
		FDCClientUtils.setPersonF7(prmtApplyPerson, this,
				canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setRespDeptF7(prmtDept, this,
				canSelectOtherOrgPerson ? null : cuId);
		FDCClientUtils.setRespDeptF7(prmtApplyDept, this,
				canSelectOtherOrgPerson ? null : cuId);
		
		this.actionPrint.setVisible(true);
		this.actionPrintPreview.setVisible(true);
		this.actionPrint.setEnabled(true);
		this.actionPrintPreview.setEnabled(true);
		
		this.actionAddNew.setVisible(false);
		
		this.prmtCreator.setDisplayFormat("$name$");
		this.prmtAuditor.setDisplayFormat("$name$");
		
		this.txtDays.setDataType(Integer.class);
		this.txtDays.setHorizontalAlignment(JTextField.RIGHT);
		
		this.txtApplyAmount.setDataType(BigDecimal.class);
		this.txtApplyAmount.setHorizontalAlignment(JTextField.RIGHT);
		this.txtApplyAmount.setPrecision(2);
		NumberFormatter formatter = new NumberFormatter(new DecimalFormat("0.00"));
		this.txtApplyAmount.setDisplayFormatter(formatter);
		
		this.txtApplyDays.setDataType(Integer.class);
		this.txtApplyDays.setHorizontalAlignment(JTextField.RIGHT);
		
		ContractTypeInfo info = (ContractTypeInfo) this.prmtContractType.getValue();
		if(info!=null){
			info=ContractTypeFactory.getRemoteInstance().getContractTypeInfo("select *,contractWFTypeEntry.contractWFType.*,inviteTypeEntry.inviteType.* from where id='"+info.getId().toString()+"'");
			Set id = new HashSet();
			for (int i = 0; i < info.getContractWFTypeEntry().size(); i++) {
				if (info.getContractWFTypeEntry().get(i).getContractWFType() != null) {
					id.add(info.getContractWFTypeEntry().get(i).getContractWFType().getId().toString());
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
			this.prmtContractWFType.setEntityViewInfo(view);
			this.prmtContractWFType.setEnabled(true);
		}else{
			this.prmtContractWFType.setEnabled(false);
		}
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
		addDataChangeListener(pkFromDate);
		addDataChangeListener(pkToDate);
	}
	protected void detachListeners() {
		removeDataChangeListener(pkBizDate);
		removeDataChangeListener(pkFromDate);
		removeDataChangeListener(pkToDate);
	}
	protected ICoreBase getBizInterface() throws Exception {
		return TripCostFactory.getRemoteInstance();
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
    	sic.add("tripApply.bizDate");
    	sic.add("tripApply.contractType.*");
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
		
		attachListeners();
		isOnload=false;
	}
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
		fillAttachmnetTable();
	}
	protected IObjectValue createNewData() {
		TripCostInfo info=new TripCostInfo();
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
		
		String tripId = (String) getUIContext().get("tripId");
		try {
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("person.*");
			sic.add("dept.*");
			sic.add("curProject.*");
	    	sic.add("curProject.CU.*");
	    	sic.add("curProject.fullOrgUnit.*");
	    	sic.add("contractWFType.*");
	    	sic.add("contractType.*");
			TripApplyInfo tripApplyInfo=TripApplyFactory.getRemoteInstance().getTripApplyInfo(new ObjectUuidPK(tripId), sic);
			info.setTripApply(tripApplyInfo);
			info.setCurProject(tripApplyInfo.getCurProject());
			info.setPerson(tripApplyInfo.getPerson());
			info.setDept(tripApplyInfo.getDept());
			info.setContractWFType(tripApplyInfo.getContractWFType());
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return info;
	}
	private void dateChange(){
		Date fromDate=(Date) this.pkFromDate.getValue();
		Date toDate=(Date) this.pkToDate.getValue();
		if(fromDate!=null&&toDate!=null){
			this.txtDays.setValue(FDCDateHelper.getDiffDays(fromDate, toDate));
		}else{
			this.txtDays.setValue(null);
		}
	}
	@Override
	protected void pkFromDate_dataChanged(DataChangeEvent e) throws Exception {
		super.pkFromDate_dataChanged(e);
		dateChange();
	}
	@Override
	protected void pkToDate_dataChanged(DataChangeEvent e) throws Exception {
		super.pkToDate_dataChanged(e);
		dateChange();
	}
	@Override
	protected void verifyInputForSubmint() throws Exception {
		super.verifyInputForSubmint();
		FDCClientVerifyHelper.verifyEmpty(this, this.txtPurpose);
		if(this.txtDays.getIntegerValue()<=0){
			MsgBox.showWarning("出差天数必须大于0！");
			SysUtil.abort();
		}
		BigDecimal appAmount=this.editData.getTripApply().getAmount();
		TripCostCollection col=TripCostFactory.getRemoteInstance().getTripCostCollection("select amount from where tripApply.id='"+this.editData.getTripApply().getId().toString()+"' and id!='"+this.editData.getId().toString()+"' and state!='1SAVED'");
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
	}
	protected String getTDFileName() {
		return "/bim/fdc/contract/TripCost";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.TripCostQuery");
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
		TripCostEditDataProvider dataPvd = new TripCostEditDataProvider(
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
		TripCostEditDataProvider dataPvd = new TripCostEditDataProvider(
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
		if(date!=null&&FDCDateHelper.getDiffDays(this.editData.getTripApply().getBizDate(),date)<1){
			MsgBox.showWarning(this,"报销日期必须大于等于申请日期！");
			this.pkBizDate.setValue(null);
			SysUtil.abort();
		}
	}
	public void storeFields() {
		this.editData.setOriginalAmount(this.txtAmount.getBigDecimalValue());
		super.storeFields();
	}
}