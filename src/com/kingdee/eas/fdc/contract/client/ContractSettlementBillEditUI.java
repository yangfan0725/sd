/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.StringUtils;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.ctrl.swing.util.CtrlCommonConstant;
import com.kingdee.bos.dao.AbstractObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.AttachmentInfo;
import com.kingdee.eas.base.attachment.BoAttchAssoCollection;
import com.kingdee.eas.base.attachment.BoAttchAssoFactory;
import com.kingdee.eas.base.attachment.BoAttchAssoInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.BooleanEnum;
import com.kingdee.eas.base.param.ParamControlFactory;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.assistant.ExchangeRateInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AimCostCollection;
import com.kingdee.eas.fdc.aimcost.AimCostFactory;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBasedataException;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCParamInfo;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.SourceTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.FDCSplitClientHelper;
import com.kingdee.eas.fdc.contract.ChangeSupplierEntryFactory;
import com.kingdee.eas.fdc.contract.CompensationBillInfo;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractChangeBillCollection;
import com.kingdee.eas.fdc.contract.ContractChangeBillFactory;
import com.kingdee.eas.fdc.contract.ContractChangeBillInfo;
import com.kingdee.eas.fdc.contract.ContractException;
import com.kingdee.eas.fdc.contract.ContractFacadeFactory;
import com.kingdee.eas.fdc.contract.ContractSettleTypeEnum;
import com.kingdee.eas.fdc.contract.ContractSettlementBillCollection;
import com.kingdee.eas.fdc.contract.ContractSettlementBillFactory;
import com.kingdee.eas.fdc.contract.ContractSettlementBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextCollection;
import com.kingdee.eas.fdc.contract.ContractWithoutTextFactory;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.GuerdonBillInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillCollection;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.SettNoCostSplitFactory;
import com.kingdee.eas.fdc.contract.SettlementCostSplitFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.finance.DeductBillEntryInfo;
import com.kingdee.eas.framework.client.workflow.IWorkflowUIEnhancement;
import com.kingdee.eas.framework.client.workflow.IWorkflowUISupport;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.UuidException;

/**
 * 
 * ????:??????????????
 * 
 * @author liupd date:2006-10-10
 *         <p>
 * @version EAS5.1.3
 */
public class ContractSettlementBillEditUI extends
		AbstractContractSettlementBillEditUI {
	private static boolean isFirstLoad = true;
	private static final Logger logger = CoreUIObject
			.getLogger(ContractSettlementBillEditUI.class);

	// ????????????helper
	private SettlementDetailHelper settlementDetailHelper;

	// ????
	private boolean isQualityGuarante = false;
	
	private boolean isKeep6ForGuarantterate = false;//FDC_PARAM_FDC224_KEEP6FORGUARANTERATE

	// ??????????????????
	private boolean canSetterMore = false;

	private Map totalSettleMap = null;
	private Map perMap = null;
	
	//??????????????????
	private boolean isHasAttachment = false;
	//????????????????????
	//private int advancePaymentNumber = 1;
    //????????????????
	private boolean isMoreSettlement = false;
	
	/**
	 * ??????????????????????????
	 */
	private boolean selectExeRateFromContractbill = false;
	
	//????????????????????????
	private boolean isOverContractAmt(){
		boolean isOverContractAmt =false;
		String costCenterId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		try {
			isOverContractAmt = FDCUtils.getDefaultFDCParamByKey(null, costCenterId, FDCConstants.FDC_PARAM_SETTLEMENTOVERCONTRACTAMOUNT);
		} catch (EASBizException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isOverContractAmt ;
	}
	
	/**
	 * ????????????????????????????????????????
	 * @return  true <p>
	 * 				?????????????????????????????? <p>
	 * 			false <p>
	 * 				???????????????????????????????? <p>
	 * @author zhiyuan_tang 2010/07/27
	 */
	private boolean isContractChangeMustComplete(){
		boolean isContractChangeMustComplete =false;
		String costCenterId = SysContext.getSysContext().getCurrentCostUnit().getId().toString();
		try {
			isContractChangeMustComplete = FDCUtils.getDefaultFDCParamByKey(null, costCenterId, FDCConstants.FDC_PARAM_CONTRACTCHANGESETTLEMENTMUSTCOMPLETE);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		
		return isContractChangeMustComplete ;
	}
	
	
	/**
	 * output class constructor
	 */
	
	private String conSettType = "rdContractOverRate";
	
	private String costCenterConstRate = "0.00";
	
	public void formatCtrl(KDFormattedTextField ctrl){
		if(isKeep6ForGuarantterate){
			ctrl.setPrecision(6);
		}else{
			ctrl.setPrecision(2);
		}
	}
	
	
	public ContractSettlementBillEditUI() throws Exception {
		super();
		settlementDetailHelper = new SettlementDetailHelper(this);
		jbInit();
	}

	private void jbInit() {
		titleMain = getUITitle();
		cbFinalSettle.addItems(BooleanEnum.getEnumList().toArray());
	}

	// ????????????????
	protected void bookedDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e)
			throws Exception {
		if (contractBill == null) {
			return;
		}
		String projectId = this.contractBill.getCurProject().getId().toString();
		fetchPeriod(e, pkbookedDate, cbPeriod, projectId, true);
	}

	protected void cbFinalSettle_itemStateChanged(java.awt.event.ItemEvent e)
			throws Exception {

		if (STATUS_VIEW.equals(getOprtState())
				|| STATUS_FINDVIEW.equals(getOprtState()))
			return;
		if (e == null || e.getStateChange() == ItemEvent.SELECTED) {
			Object newValue = cbFinalSettle.getSelectedItem();
			if (BooleanEnum.TRUE.equals(newValue)) {
				settlementDetailHelper.unLock(true);
			} else {
				settlementDetailHelper.unLock(false);
			}
		}
	}

	private void currencyChanged(DataChangeEvent e) throws Exception {

		Object newValue = e.getNewValue();
		if (newValue instanceof CurrencyInfo) {
			if (isFirstLoad && !getOprtState().equals(OprtState.ADDNEW))
				return;
			if (e.getOldValue() != null
					&& ((CurrencyInfo) e.getOldValue()).getId().equals(
							((CurrencyInfo) newValue).getId())) {
				// ??????????????????????????????????????????
				return;
			}

			BOSUuid srcid = ((CurrencyInfo) newValue).getId();
			setExchangeRate(srcid);
		}
	}

	/**
	 * ???????????????????????????????????????????????? added by Owen_wen 2010-10-09
	 * @param srcid
	 * @throws Exception
	 */
	protected void setExchangeRate(BOSUuid srcid) throws Exception {
		if (this.selectExeRateFromContractbill){
			txtExchangeRate.setValue(contractBill.getExRate());
		}
		else {
			Date bookedDate = (Date) pkbookedDate.getValue();
			ExchangeRateInfo exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this, srcid, company, bookedDate);
	
			int curPrecision = FDCClientHelper.getPrecOfCurrency(srcid);
			BigDecimal exRate = FDCHelper.ONE;
			int exPrecision = curPrecision;
	
			if (exchangeRate != null) {
				exRate = exchangeRate.getConvertRate();
				exPrecision = exchangeRate.getPrecision();
			}
			txtExchangeRate.setValue(exRate);
			txtExchangeRate.setPrecision(exPrecision);
			txtSettlePrice.setPrecision(curPrecision);
		}
	}

	private void modifySettlePrice() {
		BigDecimal originalAmt = FDCHelper.toBigDecimal(txtOriginalAmount
				.getBigDecimalValue());
		BigDecimal exchangeRate = FDCHelper.toBigDecimal(txtExchangeRate
				.getBigDecimalValue());

		BigDecimal amount = FDCHelper.ZERO;
		if (originalAmt.compareTo(FDCHelper.ZERO) == 0
				|| exchangeRate.compareTo(FDCHelper.ZERO) == 0) {
			txtSettlePrice.setValue(FDCHelper.ZERO);
			// return; // by Cassiel_peng
		} else {
			amount = originalAmt.multiply(exchangeRate);
			txtSettlePrice.setValue(amount);
		}

		// ????????????= ??????????+??????????
		if (totalSettleMap == null) {
			txtTotalSettlePrice.setValue(amount);
			txtTotalOriginalAmount.setValue(originalAmt);
		} else {

			BigDecimal totalAmount = (BigDecimal) totalSettleMap
					.get("SettlePrice");
			txtTotalSettlePrice.setValue(FDCHelper.add(totalAmount, amount));

			totalAmount = (BigDecimal) totalSettleMap.get("OriginalAmount");
			txtTotalOriginalAmount.setValue(totalAmount.add(originalAmt));
		}
	}

	protected void controlDate_dataChanged(
			com.kingdee.bos.ctrl.swing.event.DataChangeEvent e,
			ControlDateChangeListener listener) throws Exception {
		if ("bookedDate".equals(listener.getShortCut())) {
			bookedDate_dataChanged(e);
		}
		// by Cassiel_peng
		else if ("guaranteAmt".equals(listener.getShortCut())) {
			setQualityGuarante("txtGuaranceAmt", e);

		} else if ("qualityGuarante".equals(listener.getShortCut())) {
			setQualityGuarante("txtqualityGuarante", e);
			isQualityGuarante = true;
		} else if ("qualityGuaranteRate".equals(listener.getShortCut())) {
			setQualityGuarante("txtqualityGuaranteRate", e);
			isQualityGuarante = false;
		}
		// else if("totalSettlePrice".equals(listener.getShortCut())){
		// setQualityGuarante("txtqualityGuaranteRate", e);
		// isQualityGuarante = false;
		// }
		else if ("settlePrice".equals(listener.getShortCut())) {//??????"????????"??????????
																// ????????????????????????????????
																// by
																// Cassiel_peng
			setQualityGuarante("txtqualityGuaranteRate", e);
			isQualityGuarante = false;
		} else if ("currency".equals(listener.getShortCut())) {

			// ??????????????
			currencyChanged(e);
			// ??????????
			modifySettlePrice();

		} else if ("originalAmount".equals(listener.getShortCut())) {
			// ??????????
			modifySettlePrice();
		} else if ("exchageRate".equals(listener.getShortCut())) {
			// ??????????
			modifySettlePrice();
		}
	}

	// ????????????????
	ControlDateChangeListener bookedDateChangeListener = new ControlDateChangeListener(
			"bookedDate");
	// ??????????
	ControlDateChangeListener qualityGuaranteDataChangeLs = new ControlDateChangeListener(
			"qualityGuarante");
	// ??????
	ControlDateChangeListener guaranteAmtDataChangeLs = new ControlDateChangeListener(
			"guaranteAmt");
	// ??????????
	ControlDateChangeListener qualityGuaranteRateDataChangeLs = new ControlDateChangeListener(
			"qualityGuaranteRate");
	// ??????
	ControlDateChangeListener settlePriceDataChangeLs = new ControlDateChangeListener(
			"settlePrice");
	// ????
	ControlDateChangeListener currencyDataChangeLs = new ControlDateChangeListener(
			"currency");
	// ????
	ControlDateChangeListener originalAmountDataChangeLs = new ControlDateChangeListener(
			"originalAmount");
	// ????
	ControlDateChangeListener exchageRateDataChangeLs = new ControlDateChangeListener(
			"exchageRate");

	// ??????????????????????
	protected void attachListeners() {
		// by Cassiel_peng
		txtGuaranceAmt.addDataChangeListener(guaranteAmtDataChangeLs);

		txtqualityGuarante.addDataChangeListener(qualityGuaranteDataChangeLs);
		txtqualityGuaranteRate
				.addDataChangeListener(qualityGuaranteRateDataChangeLs);
		// txtSettlePrice.addDataChangeListener(settlePriceDataChangeLs);
		txtTotalSettlePrice.addDataChangeListener(settlePriceDataChangeLs);

		pkbookedDate.addDataChangeListener(bookedDateChangeListener);

		prmtCurrency.addDataChangeListener(currencyDataChangeLs);
		txtOriginalAmount.addDataChangeListener(originalAmountDataChangeLs);
		txtExchangeRate.addDataChangeListener(exchageRateDataChangeLs);

		addDataChangeListener(txtbuildArea);

		addDataChangeListener(cbFinalSettle);
		perMap = new HashMap();
		try {
			// ??????????
			if (PermissionFactory
					.getRemoteInstance()
					.hasFunctionPermission(
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentUserInfo().getId().toString()),
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentOrgUnit().getId().toString()),
							new MetaDataPK(
									"com.kingdee.eas.fdc.contract.client.CompensationBillListUI"),
							new MetaDataPK("ActionOnLoad"))) {
				perMap.put(this.panelCompensation.getName(), Boolean.TRUE);
			}
			// ??????????
			if (PermissionFactory
					.getRemoteInstance()
					.hasFunctionPermission(
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentUserInfo().getId().toString()),
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentOrgUnit().getId().toString()),
							new MetaDataPK(
									"com.kingdee.eas.fdc.contract.client.DeductListUI"),
							new MetaDataPK("ActionOnLoad"))) {
				perMap.put(this.panelDeduct.getName(), Boolean.TRUE);
			}
			// ??????????
			if (PermissionFactory
					.getRemoteInstance()
					.hasFunctionPermission(
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentUserInfo().getId().toString()),
							new ObjectUuidPK(SysContext.getSysContext()
									.getCurrentOrgUnit().getId().toString()),
							new MetaDataPK(
									"com.kingdee.eas.fdc.contract.client.GuerdonBillListUI"),
							new MetaDataPK("ActionOnLoad"))) {
				perMap.put(this.panelGuerdon.getName(), Boolean.TRUE);
			}
		} catch (EASBizException e) {
			// TODO ???????? catch ??
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO ???????? catch ??
			e.printStackTrace();
		}
		this.tabTop.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// TODO ????????????????
				String panelName = tabTop.getSelectedComponent().getName();
				if ((panelName.equals(panelCompensation.getName())
						|| panelName.equals(panelDeduct.getName()) || panelName
						.equals(panelGuerdon.getName()))
						&& !perMap.containsKey(panelName)) {
					if (panelName.equals(panelCompensation.getName()))
						tblCompensationBill.setVisible(false);
					else if (panelName.equals(panelDeduct.getName()))
						tblDeduct.setVisible(false);
					else if (panelName.equals(panelGuerdon.getName()))
						tblGuerdon.setVisible(false);
					MsgBox.showError(ContractClientUtils.getRes("NoPermission")
							.replaceAll(
									"\\{0\\}",
									SysContext.getSysContext()
											.getCurrentOrgUnit().getName()));
				}
			}

		});
		addDataChangeListener(this.txtCostAuditCost);
	}

	// ??????????
	protected void detachListeners() {
		// by Cassiel_peng
		txtGuaranceAmt.removeDataChangeListener(guaranteAmtDataChangeLs);

		txtqualityGuarante
				.removeDataChangeListener(qualityGuaranteDataChangeLs);
		txtqualityGuaranteRate
				.removeDataChangeListener(qualityGuaranteRateDataChangeLs);
		// txtSettlePrice.removeDataChangeListener(settlePriceDataChangeLs);
		txtTotalSettlePrice.removeDataChangeListener(settlePriceDataChangeLs);

		pkbookedDate.removeDataChangeListener(bookedDateChangeListener);

		prmtCurrency.removeDataChangeListener(currencyDataChangeLs);
		txtOriginalAmount.removeDataChangeListener(originalAmountDataChangeLs);
		txtExchangeRate.removeDataChangeListener(exchageRateDataChangeLs);

		removeDataChangeListener(txtGuaranceAmt);
		removeDataChangeListener(txtbuildArea);
		removeDataChangeListener(txtqualityGuarante);
		removeDataChangeListener(txtqualityGuaranteRate);
		removeDataChangeListener(txtSettlePrice);

		removeDataChangeListener(cbFinalSettle);
		
		removeDataChangeListener(this.txtCostAuditCost);
	}
	boolean isOnload=false;
	public void loadFields() {
		isOnload=true;
		// ????????????
		detachListeners();

		super.loadFields();

		// fillCompensationBills();
		settlementDetailHelper.fill();
		setSaveActionStatus();

		CurProjectInfo curProjectInfo = editData.getCurProject();
		if (editData != null && editData.getContractBill() != null
				&& editData.getContractBill().getCurProject() != null
				&& curProjectInfo == null) {
			String projId = editData.getContractBill().getCurProject().getId()
					.toString();
			curProjectInfo = FDCClientUtils.getProjectInfoForDisp(projId);
		}

		txtProj.setText(curProjectInfo.getDisplayName());
		FullOrgUnitInfo costOrg = FDCClientUtils
				.getCostOrgByProj(curProjectInfo.getId().toString());
		txtOrgUnit.setText(costOrg.getDisplayName());
		editData.setOrgUnit(costOrg);
		editData.setCU(curProjectInfo.getCU());

		// ??????????
		if (STATUS_ADDNEW.equals(getOprtState())) {
			this.actionNextPerson.setVisible(false);
		}

		// ??????????????????,????????????????,??????????????
		CurrencyInfo currency = (CurrencyInfo) prmtCurrency.getValue();
		if (currency != null
				&& currency.getId().toString().endsWith(
						this.baseCurrency.getId().toString())) {
			this.txtExchangeRate.setEnabled(false);
			this.txtSettlePrice.setEnabled(false);
		} else {
			this.txtExchangeRate.setEnabled(true);
		}
		this.txtSettlePrice.setEnabled(false);

		// ??????????????????
		tabTop.setSelectedIndex(0);

		// ??????
		BigDecimal price = txtSettlePrice.getBigDecimalValue();
		if (price != null) {
			settlePrice = price.toString();
		}

		if (totalSettleMap == null) {
			totalSettleMap = new HashMap();
			totalSettleMap.put("OriginalAmount", editData
					.getTotalOriginalAmount().subtract(
							editData.getCurOriginalAmount()));
			totalSettleMap.put("SettlePrice", editData.getTotalSettlePrice()
					.subtract(editData.getCurSettlePrice()));
		}

		// ????????????????
		attachListeners();

		if (STATUS_VIEW.equals(getOprtState())
				|| BooleanEnum.FALSE.equals(editData.getIsFinalSettle())) {
			try {
				settlementDetailHelper.unLock(false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		ContractSettleTypeEnum  cst = this.editData.getContractSettleType();
		if(ContractSettleTypeEnum.AType.equals(cst)){
			this.btnAType.setSelected(true);
		} else if(ContractSettleTypeEnum.BType.equals(cst)){
			this.btnBType.setSelected(true);
		} else if(ContractSettleTypeEnum.CType.equals(cst)){
			this.BtnCType.setSelected(true);
		}
		
		try {
			BigDecimal latestPrice = FDCUtils.getContractLastAmt(null, this.editData.getContractBill().getId().toString());
			this.txtLastPrice.setValue(latestPrice);
			
			BigDecimal deduct=FDCHelper.ZERO;
			for(int i=0;i<tblDeduct.getRowCount();i++){
				deduct=FDCHelper.add(deduct, tblDeduct.getRow(i).getCell("amount").getValue());
			}
			this.txtDeduct.setValue(deduct);
			
			BigDecimal reward=FDCHelper.ZERO;
			for(int i=0;i<tblGuerdon.getRowCount();i++){
				reward=FDCHelper.add(reward, tblGuerdon.getRow(i).getCell("amount").getValue());
			}
			this.txtToReward.setValue(reward);
			
			BigDecimal changeAmount=FDCHelper.ZERO;
			ContractChangeBillCollection contractChangeBillCollection = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection("select hasSettled,amount,balanceAmount from where contractBill.id='"+this.editData.getContractBill().getId()+"'");
			for (int i = 0; i < contractChangeBillCollection.size(); i++) {
				ContractChangeBillInfo info = contractChangeBillCollection.get(i);
				if(info.isHasSettled()){
					changeAmount=FDCHelper.add(changeAmount, info.getBalanceAmount());
				}else{
					changeAmount=FDCHelper.add(changeAmount, info.getAmount());
				}
			}
			this.txtChangeAmount.setValue(changeAmount);
			
			BigDecimal hasPayAmt=FDCHelper.ZERO;
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select sum(FActPayLocAmt) hasPayed from T_CAS_PaymentBill \n ");
			builder.appendSql("where  FContractBillId=?  \n ");
			builder.appendSql("and FBillStatus =15 \n");
			builder.addParam(this.editData.getContractBill().getId().toString());
			IRowSet rowSet=builder.executeQuery();
			if(rowSet.next()){
				hasPayAmt=FDCHelper.toBigDecimal(rowSet.getBigDecimal("hasPayed"), 2);
			}
			this.txtPayAmount.setValue(hasPayAmt);
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		isOnload=false;
	}

	public void storeFields() {
		super.storeFields();

		ContractSettlementBillInfo objectValue = (ContractSettlementBillInfo) editData;
		if (BooleanEnum.TRUE.equals(objectValue.getIsFinalSettle())) {
			objectValue.setOriginalAmount(objectValue.getTotalOriginalAmount());
			objectValue.setSettlePrice(objectValue.getTotalSettlePrice());
		} else {
			objectValue.setOriginalAmount(objectValue.getCurOriginalAmount());
			objectValue.setSettlePrice(objectValue.getCurSettlePrice());
		}
		if(this.btnAType.isSelected()){
			this.editData.setContractSettleType(ContractSettleTypeEnum.AType);
		} else if(this.btnBType.isSelected()){
			this.editData.setContractSettleType(ContractSettleTypeEnum.BType);
		} else if(this.BtnCType.isSelected()){
			this.editData.setContractSettleType(ContractSettleTypeEnum.CType);
		}
	}
//	private boolean isNeedCheckContractType(){
//		String  contractTypeNumber = this.editData.getContractBill().getContractType().getLongNumber().toUpperCase();
//		String respDeptNumber = this.editData.getContractBill().getRespDept().getNumber().toUpperCase();
//		respDeptNumber = respDeptNumber.substring(respDeptNumber.lastIndexOf("-") + 1,respDeptNumber.length());
//		if((contractTypeNumber.startsWith("02QQ")||contractTypeNumber.startsWith("03JA")||contractTypeNumber.startsWith("04JC")||contractTypeNumber.startsWith("05GJ"))
//				&& (respDeptNumber.equals("04")||respDeptNumber.equals("05")||respDeptNumber.equals("06")||respDeptNumber.equals("10"))){
//			return true;
//		}
//		return false;
//	}
	// ????????
	protected void setPrecision() {
		CurrencyInfo currency = editData.getCurrency();
		Date bookedDate = (Date) editData.getBookedDate();

		ExchangeRateInfo exchangeRate = null;
		try {
			exchangeRate = FDCClientHelper.getLocalExRateBySrcCurcy(this,
					currency.getId(), company, bookedDate);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		int curPrecision = FDCClientHelper.getPrecOfCurrency(currency.getId());
		int exPrecision = curPrecision;

		if (exchangeRate != null) {
			exPrecision = exchangeRate.getPrecision();
		}

		txtExchangeRate.setPrecision(exPrecision);
		txtSettlePrice.setPrecision(curPrecision);
	}

	// private void setComSumAmount() {
	// BigDecimal sum = FDCHelper.ZERO;
	// for (int i = 0; i < this.tblCompensationBill.getRowCount(); i++) {
	// IRow row = this.tblCompensationBill.getRow(i);
	// Boolean select = (Boolean) row.getCell("select").getValue();
	// if (select.booleanValue()) {
	// BigDecimal amount = (BigDecimal) row.getCell("amount")
	// .getValue();
	// if (amount != null) {
	// sum = sum.add(amount);
	// }
	// }
	// }
	// this.txtCompensationAmount.setValue(sum);
	// BigDecimal settleAmount = this.txtSettlePrice.getBigDecimalValue();
	// if (settleAmount == null) {
	// settleAmount = FDCHelper.ZERO;
	// }
	// this.txtFinalAmount.setValue(settleAmount.subtract(sum));
	// }

	/*
	 * private void fillCompensationBills() { tblCompensationBill.removeRows();
	 * tblCompensationBill.getColumn("amount").getStyleAttributes()
	 * .setHorizontalAlign(HorizontalAlignment.RIGHT);
	 * tblCompensationBill.getColumn("amount").getStyleAttributes()
	 * .setNumberFormat(FDCHelper.getNumberFtm(2)); String formatString =
	 * "yyyy-MM-dd";
	 * tblCompensationBill.getColumn("createDate").getStyleAttributes()
	 * .setNumberFormat(formatString); CompensationBillCollection coll = new
	 * CompensationBillCollection(); try { String contractId = (String)
	 * getUIContext().get("contractBillId"); if (contractId == null) { String
	 * settId = getUIContext().get(UIContext.ID).toString(); if (settId != null)
	 * { ContractSettlementBillInfo sett = (ContractSettlementBillInfo) this
	 * .getValue(new ObjectUuidPK(BOSUuid.read(settId))); contractId =
	 * sett.getContractBill().getId().toString(); } } EntityViewInfo conView =
	 * new EntityViewInfo(); conView.getSelector().add(new
	 * SelectorItemInfo("*")); conView.getSelector().add(new
	 * SelectorItemInfo("currency.*")); conView.getSelector().add(new
	 * SelectorItemInfo("creator.*")); conView.getSelector().add( new
	 * SelectorItemInfo("compensationType.*")); FilterInfo filter = new
	 * FilterInfo(); conView.setFilter(filter); filter.getFilterItems().add( new
	 * FilterItemInfo("contract.Id", contractId)); coll =
	 * CompensationBillFactory.getRemoteInstance()
	 * .getCompensationBillCollection(conView); } catch (Exception e) {
	 * e.printStackTrace(); this.abort(e); }
	 * 
	 * for (int i = 0; i < coll.size(); i++) { CompensationBillInfo info =
	 * coll.get(i); if (info.getAuditor() == null) { continue; } IRow row =
	 * tblCompensationBill.addRow(); row.setUserObject(info); if
	 * (info.getDescription() != null) {
	 * row.getCell("select").setValue(Boolean.TRUE); } else {
	 * row.getCell("select").setValue(Boolean.FALSE); }
	 * row.getCell("number").setValue(info.getNumber());
	 * row.getCell("name").setValue(info.getName());
	 * row.getCell("type").setValue(info.getCompensationType());
	 * row.getCell("amount").setValue(info.getAmount());
	 * row.getCell("deductType").setValue(
	 * CompensationBillEditUI.getResource("settleDeduct")); if
	 * (info.getCreator() != null) {
	 * row.getCell("creator").setValue(info.getCreator().getName()); }
	 * row.getCell("createDate").setValue(info.getCreateTime()); }
	 * this.tblCompensationBill.getStyleAttributes().setLocked(true); }
	 */

	/**
	 * output actionCopy_actionPerformed
	 */
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		checkFinalSettleDup();

		super.actionCopy_actionPerformed(e);
		editData.setSourceType(SourceTypeEnum.ADDNEW);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		checkFinalSettleDup();

		super.actionAddNew_actionPerformed(e);
	}

	public void onShow() throws Exception {
		super.onShow();
		// ???????????????????????????????? by Cassiel_peng
		btnPrint.setVisible(true);
		btnPrint.setEnabled(true);
		btnPrintPreview.setVisible(true);
		btnPrintPreview.setEnabled(true);
		actionPrint.setVisible(true);
		actionPrint.setEnabled(true);
		actionPrintPreview.setVisible(true);
		actionPrintPreview.setEnabled(true);
		getNumberCtrl().requestFocus();
		/*
		 * SwingUtilities.invokeLater(new Runnable(){ public void run() { try {
		 * settlementDetailHelper.fillCollectPanel(); } catch (Exception e) {
		 * handUIException(e); } } });
		 */
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (txtNumber.isEnabled()) {
					txtNumber.requestFocus(true);
				} else {
					txtbillName.requestFocus(true);
				}
				editAuditProgColumn();
			}
		});
		menuView.setVisible(false);
	}
	private void editAuditProgColumn() {
		Object isFromWorkflow = getUIContext().get("isFromWorkflow");
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_FINDVIEW) && actionSave.isVisible()
				&& (editData.getState() == FDCBillStateEnum.SUBMITTED || editData.getState() == FDCBillStateEnum.AUDITTING)) {

			// ????????????????????????
			this.lockUIForViewStatus();
			this.txtCostAuditCost.setEnabled(true);
			this.actionSave.setEnabled(true);

		}
		if (isFromWorkflow != null && isFromWorkflow.toString().equals("true") && getOprtState().equals(STATUS_EDIT)) {
			actionRemove.setEnabled(false);
		}
	}
	protected void fetchInitData() throws Exception {
		// ????Id
		String contractBillId = (String) getUIContext().get("contractBillId");
		if (contractBillId == null) {
			Object obj = getUIContext().get("ID");
			String settId = null;
			// ??????????????????BOSUuid
			if (obj instanceof BOSUuid) {
				settId = ((BOSUuid) obj).toString();
			} else {
				settId = (String) obj;
			}

			if (settId != null) {
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder
						.appendSql("select fcontractBillId from T_CON_ContractSettlementBill where fid=?");
				builder.addParam(settId);
				IRowSet rowSet = builder.executeQuery();
				if (rowSet.size() == 1) {
					rowSet.next();
					getUIContext().put("contractBillId",
							rowSet.getString("fcontractBillId"));
				}
			}
		}
		super.fetchInitData();

	}

	/**
	 * by Cassiel_peng
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ArrayList idList = new ArrayList();
		if (editData != null && !StringUtils.isEmpty(editData.getString("id"))) {
			idList.add(editData.getString("id"));
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		ConSettlementPrintProvider data = new ConSettlementPrintProvider(
				editData.getString("id"), getTDQueryPK(),getATTCHQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
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
			MsgBox.showWarning(this, FDCClientUtils.getRes("cantPrint"));
			return;
		}
		ConSettlementPrintProvider data = new ConSettlementPrintProvider(
				editData.getString("id"), getTDQueryPK(),getATTCHQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	// ????????????????????????
	protected String getTDFileName() {
		return "/bim/fdc/contract/ContractSettlement";
	}

	// ??????????Query
	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.ContractSettlementBillForPrintQuery");
	}
	
	// ??????????Query
	protected IMetaDataPK getATTCHQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.contract.app.AttchmentForPrintQuery");
	}

	/**
	 * output getEditUIName method
	 */
	protected String getEditUIName() {
		return com.kingdee.eas.fdc.contract.client.ContractSettlementBillEditUI.class
				.getName();
	}

	/**
	 * output getBizInterface method
	 */
	protected com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception {
		return com.kingdee.eas.fdc.contract.ContractSettlementBillFactory
				.getRemoteInstance();
	}

	/**
	 * output getDetailTable method
	 */
	protected KDTable getDetailTable() {
		return null;
	}

	/**
	 * ????????????????????????????????????????????
	 */
	protected void setFieldsNull(AbstractObjectValue newData) {
		super.setFieldsNull(newData);
		ContractSettlementBillInfo info = (ContractSettlementBillInfo) newData;
		info.setContractBill(editData.getContractBill());
		info.setContractBillNumber(editData.getContractBillNumber());
	}

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {

		ContractSettlementBillInfo objectValue = new ContractSettlementBillInfo();
		objectValue.setCreator((UserInfo) (SysContext.getSysContext()
				.getCurrentUserInfo()));
		// objectValue.setCreateTime(new Timestamp(new Date().getTime()));
		try {
			objectValue.setCreateTime(FDCDateHelper.getServerTimeStamp());
		} catch (BOSException e1) {
			// TODO ???????? catch ??
			e1.printStackTrace();
		}
		objectValue.setSourceType(SourceTypeEnum.ADDNEW);

		ContractBillInfo contractBillInfo = contractBill;

		objectValue.setContractBill(contractBillInfo);
		objectValue.setCurProject(this.curProject);

		objectValue.setCurrency(contractBill.getCurrency());
		objectValue.setExchangeRate(contractBill.getExRate());
		objectValue.setIsCostSplit(contractBill.isIsCoseSplit());
		String contractBillNumber = (String) getUIContext().get(
				"contractBillNumber");
		objectValue.setContractBillNumber(contractBillNumber);
		objectValue.setQualityGuaranteRate(contractBillInfo.getGrtRate());
		txtcontractNumber.setText(contractBillInfo.getNumber());
		txtcontractName.setText(contractBillInfo.getName());
		txtqualityGuaranteRate.setValue(contractBillInfo.getGrtRate());
		objectValue.setContractBill(contractBillInfo);
		objectValue.setCU(contractBillInfo.getCU());
		objectValue.setOrgUnit(contractBillInfo.getOrgUnit());
		// ??????????????   ????????????????????????????????????????????????????????????????????????????????????????"??"  by cassiel_peng  2009-12-05
		objectValue.setIsFinalSettle(BooleanEnum.TRUE);

		objectValue.setBookedDate(bookedDate);
		objectValue.setPeriod(curPeriod);
		objectValue.setOrgUnit(orgUnitInfo);

		objectValue.setOriginalAmount(FDCConstants.ZERO);
		objectValue.setSettlePrice(FDCConstants.ZERO);
		objectValue.setCurOriginalAmount(FDCConstants.ZERO);
		objectValue.setCurSettlePrice(FDCConstants.ZERO);

		// ??????????????????????????????????????0????????????????????????????0????????????????????????????????????????????????
		// ????????????????????????NullPointException(??:)
		// com.kingdee.eas.fdc.contract.client
		// .SettlementCostSplitEditUI.setQuaAmt()
		objectValue.setQualityGuarante(FDCConstants.ZERO);
		objectValue.setGuaranteAmt(FDCConstants.ZERO);
		// ??????????????
		try {
			Map param = new HashMap();
			param.put("ContractBillId", contractBill.getId().toString());
			totalSettleMap = ContractFacadeFactory.getRemoteInstance()
					.getTotalSettlePrice(param);
			if (totalSettleMap != null) {
				objectValue.setTotalOriginalAmount((BigDecimal) totalSettleMap
						.get("OriginalAmount"));
				objectValue.setTotalSettlePrice((BigDecimal) totalSettleMap
						.get("SettlePrice"));
			} else {
				objectValue.setTotalOriginalAmount(FDCConstants.ZERO);
				objectValue.setTotalSettlePrice(FDCConstants.ZERO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return objectValue;
	}

	// ????????????
	protected void fetchInitParam() throws Exception {

		super.fetchInitParam();

		// ??????????????????
		if (orgUnitInfo != null) {
			HashMap param = FDCUtils.getDefaultFDCParam(null, orgUnitInfo.getId().toString());
			if (param.get(FDCConstants.FDC_PARAM_MORESETTER) != null) {
				canSetterMore = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MORESETTER).toString()).booleanValue();
			}
			if(param.get(FDCConstants.FDC_PARAM_MORESETTER) != null){
				isMoreSettlement = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_MORESETTER).toString()).booleanValue();
			}

			if(param.get(FDCConstants.FDC_PARAM_FDC224_KEEP6FORGUARANTERATE) != null){
				isKeep6ForGuarantterate = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_FDC224_KEEP6FORGUARANTERATE).toString()).booleanValue();
			}
			
			if(param.get(FDCConstants.FDC_PARAM_FDC323_SELECTEXECHANGERATE) != null){
				selectExeRateFromContractbill = Boolean.valueOf(param.get(FDCConstants.FDC_PARAM_FDC323_SELECTEXECHANGERATE).toString()).booleanValue();
			}
		}

	}

	/**
	 * ??????????????????????????????
	 * 
	 * @return
	 * @throws BOSException
	 * @throws EASBizException
	 */
	public boolean isMoreSettlement()  {
		boolean isMoreSettlement = false;
		if (orgUnitInfo != null) {
			HashMap param = null;
			try {
				param = FDCUtils.getDefaultFDCParam(null, null);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (param.get(FDCConstants.FDC_PARAM_MORESETTER) != null) {
				isMoreSettlement = Boolean
						.valueOf(
								param.get(FDCConstants.FDC_PARAM_MORESETTER)
										.toString()).booleanValue();
			}
		}
		return isMoreSettlement;
	}

	private boolean keep6ForGuaranteRate() {
		boolean keep6ForGuaranteRate = true;
		try {
			keep6ForGuaranteRate = FDCUtils.getDefaultFDCParamByKey(null, null,
					FDCConstants.FDC_PARAM_FDC224_KEEP6FORGUARANTERATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keep6ForGuaranteRate;
	}

	/*
	 * ???? Javadoc??
	 * 
	 * @see com.kingdee.eas.framework.client.CoreBillEditUI#onLoad()
	 */
	public void onLoad() throws Exception {
		// ????????????????
		
		this.setPreferredSize(new Dimension(1013, 600));
		txtOriginalAmount.setDataType(1);
		txtCostAuditCost.setDataType(1);
		txtcontractName.setMaxLength(200);
		formatCtrl(this.txtqualityGuaranteRate);
		super.onLoad();
		// FDCUIWeightWorker.getInstance().addWork(new IFDCWork() {
		// public void run() {
		// try {		
		settlementDetailHelper.fillCollectPanel();
		// } catch (Exception e) {
		// handUIException(e);
		// }
		// }
		// });
		if(this.getUIContext().get("isShowPanelCollection")!=null){
			// Boolean.parseBoolean(this.getUIContext().get("isShowPanelCollection").toString());
			boolean b=Boolean.valueOf(this.getUIContext().get("isShowPanelCollection").toString()).booleanValue();
			if(!b){
				this.tabTop.remove(panelCollection);
			}
		}

		txtOriginalAmount.setRequired(true);
		txtSettlePrice.setCommitsOnValidEdit(true);
		txtcontractName.setMaxLength(200);
		txtNumber.setMaxLength(80);
		txtbillName.setMaxLength(80);

		txtgetFeeCriteria.setMaxLength(50);
		txtinfoPrice.setMaxLength(50);
		tetQualityTime.setMaxLength(20);

		actionNext.setEnabled(false);
		actionNext.setVisible(false);
		actionPre.setEnabled(false);
		actionPre.setVisible(false);
		actionLast.setEnabled(false);
		actionLast.setVisible(false);
		actionFirst.setEnabled(false);
		actionFirst.setVisible(false);
		actionSplit.setEnabled(false);
		actionSplit.setVisible(false);

		// this.storeFields();
		txtqualityGuaranteRate.setMaximumValue(FDCHelper.ONE_HUNDRED);
		txtqualityGuaranteRate.setMinimumValue(FDCHelper.ZERO);
		// ????:????????????????????????????????????6?????? by Cassiel_peng 2009-10-6
		txtqualityGuaranteRate.setPrecision(6);
		if (!keep6ForGuaranteRate()) {
			txtqualityGuaranteRate.setPrecision(2);
		}
		txtqualityGuarante.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		// ????????????"??????????"??????????0,??????????????????????????????????????????????????????????????????????????????????0??
		// ??"??????????"????????????????????0,????????????????????????????????"??????"????????????????????????????????????"??????????????"
		// ??????????????????????"??????"????????????????????????????"????????????"??????????0???????????? by Cassiel_peng
		// 2008-9-3
		// txtqualityGuarante.setMinimumValue(FDCHelper.ZERO);

		txtExchangeRate.setMaximumValue(FDCHelper.MAX_TOTAL_VALUE2);
		txtExchangeRate.setMinimumValue(FDCHelper.ZERO);
		txtExchangeRate.setPrecision(6);
		txtConstructPrice.setMinimumValue(FDCHelper.ZERO);
		if (getOprtState().equals(OprtState.ADDNEW)) {
			txtqualityGuaranteRate.setValue(editData.getContractBill()
					.getGrtRate());

			// ??????????,??????????,??????????????
			if (!this.baseCurrency.getId().equals(editData.getCurrency().getId())) {
				setExchangeRate(editData.getCurrency().getId());
			}
		}

		// ??????????????????????????????????CTRL+C????
		if (OprtState.ADDNEW.equals(getOprtState())
				|| OprtState.EDIT.equals(getOprtState())) {
			txtcontractNumber.setEnabled(true);
			txtcontractNumber.setEditable(false);
			txtcontractName.setEnabled(true);
			txtcontractName.setEditable(false);
		}

		setPrecision();

		// FDCClientHelper.initComboCurrency(prmtCurrency, true);

		handleOldData();
		// ??????????????????????????
		if (canSetterMore) {
			cbFinalSettle.setEnabled(true);
			// cbFinalSettle.setSelectedItem(com.kingdee.eas.base.commonquery.
			// BooleanEnum.TRUE);
		} else {
			cbFinalSettle.setEnabled(false);
			kDLabelContainer16.setVisible(false);
			kDLabelContainer17.setVisible(false);
		}

		// ????????????????????????????
		if (pkbookedDate != null && pkbookedDate.isSupportedEmpty()) {
			pkbookedDate.setSupportedEmpty(false);
		}
		fillAttachmentList();
	}

	/**
	 * 
	 * ??????????????????????????????????
	 * 
	 * @return
	 * @author:liupd ??????????2006-10-13
	 *               <p>
	 */
	protected KDTextField getNumberCtrl() {
		return txtNumber;
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add(new SelectorItemInfo("id"));
		sic.add(new SelectorItemInfo("state"));
		sic.add(new SelectorItemInfo("orgUnit.id"));
		sic.add(new SelectorItemInfo("contractBill.id"));
		sic.add(new SelectorItemInfo("contractBill.isCoseSplit"));
		sic.add(new SelectorItemInfo("contractBill.programmingContract.*"));
		sic.add(new SelectorItemInfo("splitState"));
		sic.add("contractBillNumber");
		sic.add("contractBill.CurProject.id");
		sic.add("curProject.id");
		sic.add("curProject.displayName");
		sic.add("curProject.CU.number");
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));

		sic.add("currency.number");
		sic.add("currency.name");
		sic.add("amount");
		sic.add("originalAmount");
		sic.add("settlePrice");
		sic.add("buildArea");
		sic.add("getFeeCriteria");
		sic.add("unitPrice");
		sic.add("isFinalSettle");

		sic.add("totalOriginalAmount");
		sic.add("totalSettlePrice");
		sic.add("curOriginalAmount");
		sic.add("curSettlePrice");

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		sic.add(new SelectorItemInfo("period.beginDate"));

		sic.add(new SelectorItemInfo("contractSettleType"));
		sic.add(new SelectorItemInfo("contractBill.partB.*"));
		return sic;
	}

	public void checkFinalSettleDup() {
		// ????????????????????
		String billId = editData.getContractBill().getId().toString();
		ContractSettlementBillCollection billColl = null;

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("contractBill.id", billId));
		// ????????????????????
		// filter.getFilterItems().add(new FilterItemInfo("state",
		// FDCBillStateEnum.SAVED,CompareType.NOTEQUALS));
		filter.getFilterItems().add(
				new FilterItemInfo("isFinalSettle", BooleanEnum.TRUE));
		view.setFilter(filter);
		try {
			billColl = ContractSettlementBillFactory.getRemoteInstance()
					.getContractSettlementBillCollection(view);
		} catch (BOSException e) {
			handUIException(e);
		}
		if (billColl != null && billColl.size() > 0) {
			MsgBox.showWarning(this, ContractClientUtils
					.getRes("hasFinalSettle"));
			SysUtil.abort();
		}
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkBeforeRemove();
		super.actionRemove_actionPerformed(e);
	}

	// ?????????????????????????????? by Cassiel_peng
	protected void checkBeforeRemove() throws Exception {
		boolean isCostSplit = FDCSplitClientHelper.isBillSplited(
				getSelectBOID(), "T_CON_SettNoCostSplit", "FSettlementBillID");
		boolean isNoCostSplit = FDCSplitClientHelper.isBillSplited(
				getSelectBOID(), "T_CON_SettlementCostSplit",
				"FSettlementBillID");
		if (isCostSplit || isNoCostSplit) {
			MsgBox.showWarning("??????????????????????????");
			SysUtil.abort();
		}

		/*
		 * EntityViewInfo view = new EntityViewInfo(); FilterInfo filter = new
		 * FilterInfo(); filter.getFilterItems().add(new
		 * FilterItemInfo("settlementBill", editData.getId()));
		 * view.setFilter(filter); view.getSelector().add("id");
		 * CoreBillBaseCollection coll =
		 * SettlementCostSplitFactory.getRemoteInstance
		 * ().getCoreBillBaseCollection(view); Iterator iter = coll.iterator();
		 * if (iter.hasNext()) { MsgBox.showWarning(this,
		 * ContractClientUtils.getRes("noRemove")); SysUtil.abort(); }
		 */
	}

	protected void verifyInputForSave() throws Exception {
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("contractType.isMarket");
		sic.add("amount");
		ContractBillInfo cb=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(this.editData.getContractBill().getId()), sic);
		if(cb.getContractType().isIsMarket()&&txtSettlePrice.getBigDecimalValue().compareTo(cb.getAmount())>0){
			FDCMsgBox.showWarning(this,"??????????????????????????");
			SysUtil.abort();
		}
		PayRequestBillCollection col=PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection("select amount from where contractid='"+this.editData.getContractBill().getId()+"'");
		BigDecimal payAmount=FDCHelper.ZERO;
		for(int i=0;i<col.size();i++){
			payAmount=FDCHelper.add(payAmount,col.get(i).getAmount());
		}
		if(txtSettlePrice.getBigDecimalValue().compareTo(payAmount)<0){
			FDCMsgBox.showWarning(this,"????????????????????????????????????");
			SysUtil.abort();
		}
		super.verifyInputForSave();
		FDCClientVerifyHelper.verifyEmpty(this, txtbillName);
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {

		super.actionEdit_actionPerformed(e);

		// ??????????????????,????????????????,??????????????
		CurrencyInfo currency = (CurrencyInfo) prmtCurrency.getValue();
		if (currency != null
				&& currency.getId().toString().endsWith(
						this.baseCurrency.getId().toString())) {
			this.txtExchangeRate.setEnabled(false);
			this.txtSettlePrice.setEnabled(false);
		} else {
			this.txtExchangeRate.setEnabled(true);
			this.txtSettlePrice.setEnabled(false);
		}

		settlementDetailHelper.unLock(false);
	}

	protected void tblCompensationBill_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		// super.tblCompensationBill_tableClicked(e);
		if (e.getColIndex() == 0) {
			if (this.getOprtState().equals("ADDNEW")
					|| this.getOprtState().equals("EDIT")) {
				IRow row = this.tblCompensationBill.getRow(e.getRowIndex());
				if (row == null) {
					return;
				}
				Boolean b = (Boolean) row.getCell("select").getValue();
				row.getCell("select").setValue(new Boolean(!b.booleanValue()));
			}
		}
	}
	private boolean isNotLastestVersion(ProgrammingContractInfo pcInfo) {
		try{
			String progId = pcInfo.getId().toString();
			SelectorItemCollection sel= new SelectorItemCollection();
			sel.add("programming.aimCost.id");
			sel.add("programming.project.id");
			while(true){
				FilterInfo filter=new FilterInfo();
				EntityViewInfo view=new EntityViewInfo();
				filter.getFilterItems().add(new FilterItemInfo("srcId",progId));
				view.setFilter(filter);
				ProgrammingContractCollection pcol=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractCollection(view);
				if(pcol.size()>0){
					progId=pcol.get(0).getId().toString();
				}else{
					break;
				}
			}
			ProgrammingContractInfo progammingInfo = ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(progId),sel);
			AimCostInfo aimcost = progammingInfo.getProgramming().getAimCost();
			CurProjectInfo project = progammingInfo.getProgramming().getProject();
			EntityViewInfo entityView = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			filter.getFilterItems().add(new FilterItemInfo("orgOrProId",project.getId().toString()));
			filter.getFilterItems().add(new FilterItemInfo("isLastVersion",Boolean.TRUE));
			entityView.setFilter(filter);
			AimCostCollection aimCostColl= AimCostFactory.getRemoteInstance().getAimCostCollection(entityView);
			if(aimCostColl.size()>0){
				if(!aimcost.getId().toString().equals(aimCostColl.get(0).getId().toString())){
					return true;
				}else{
					return false;
				}
			}
			return true;
		}catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		}
		return true;
	}
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		// saveCompensationRel();
		if (this.editData.getContractBill().getId() != null) {
			Map paramMap = new HashMap();
			paramMap.put("contractBillId", this.editData.getContractBill()
					.getId().toString());
			paramMap.put("billState", this.editData.getState());
			paramMap.put("txtSettlePrice", txtOriginalAmount
					.getBigDecimalValue());
			paramMap.put("updateAfterSavedToSubmitBillId", this.editData
					.getId());
			ContractClientUtils.checkTotalSettlePriceSmallerThanZero(paramMap);
		}
		checkTotalSettlePrice();
		/**
		 * ???????????????????????????????????? - ????????  ????????????
		 * ???????????????????????????????????????????????????????????????????????????????? ????????????????????????????????+????????????
		 * ????????????????????????????????????????????????????????+?????????? 2009-11-12 ?????? *
		 */
		if (isMoreSettlement) {

			if (((com.kingdee.eas.base.commonquery.BooleanEnum)this.cbFinalSettle.getSelectedItem()).getValue()==BooleanEnum.TRUE_VALUE) {
				checkAmt();
				chkSettAmtOverRate();
				chkSettlementAmt();
                  
			} else {
				chkSettlementAmt();
			}
		} else {
			if(this.txtOriginalAmount.getBigDecimalValue()!=null&&this.editData.getContractBill()!=null&&this.editData.getContractBill().getProgrammingContract()!=null){
				SelectorItemCollection wtSel = new SelectorItemCollection();
				wtSel.add("amount");
				EntityViewInfo view = new EntityViewInfo();
				FilterInfo filter = new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("programmingContract.id",this.editData.getContractBill().getProgrammingContract().getId().toString()));
				view.setFilter(filter);
				view.setSelector(wtSel);
				ContractWithoutTextCollection col=ContractWithoutTextFactory.getRemoteInstance().getContractWithoutTextCollection(view);
				BigDecimal amount=FDCHelper.ZERO;
				for(int i=0;i<col.size();i++){
					amount =FDCHelper.add(amount,col.get(i).getAmount()) ;
				}
				BigDecimal pcAmount=ProgrammingContractFactory.getRemoteInstance().getProgrammingContractInfo(new ObjectUuidPK(this.editData.getContractBill().getProgrammingContract().getId())).getAmount();
				if(this.txtOriginalAmount.getBigDecimalValue().compareTo(pcAmount.subtract(amount))>0){
					FDCMsgBox.showWarning(this,"??????????????????????????????????????-??????????????????????????????");
					abort();
				}
			}
			checkAmt();
			chkSettAmtOverRate();
			chkSettlementAmt();
		}
//		 chkSettAmtOverRate();
		settlementDetailHelper.save();
		
		//Add by zhiyuan_tang 2010/07/27   ????????:??????????????????????????????????
		//????????????????????????????????????????????????????
		if (isContractChangeMustComplete()) {
			//????????????????????????????????????????????????,??????????????????????????????
			Map paramMap = new HashMap();
			paramMap.put("contractBillId", this.editData.getContractBill()
					.getId().toString());
			if (!isContractChangeSettled(paramMap)) {
				//????????
				int result = FDCMsgBox.showConfirm2New(this, "????????????????????????????????");
				if(JOptionPane.YES_OPTION != result){
					//????????????????
					abort();
				}
			}
		}
		super.actionSubmit_actionPerformed(e);
	}
	
	/**
	 * ????????????????????????????????????????????
	 * @param paramMap	??????????????ID??HashMap
	 * @return true:
	 * 				????????????????????????????????<p>
	 * 		   false:
	 * 				????????????????????????
	 * @throws BOSException
	 * @throws EASBizException
	 * @throws UuidException
	 * @author zhiyuan_tang 2010/07/28
	 */
	private boolean isContractChangeSettled(Map paramMap) throws BOSException, EASBizException, UuidException {
		
		boolean isContractChangeSettled = false;
		Object contractBillId= paramMap.get("contractBillId");//????ID
		
		//????????????????????????????????????
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		view.setFilter(filter);
		filter.getFilterItems().add(new FilterItemInfo("contractBill",contractBillId.toString()));
		filter.getFilterItems().add(new FilterItemInfo("hasSettled", Boolean.FALSE));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED, CompareType.NOTEQUALS));
		filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTING, CompareType.NOTEQUALS));
		view.getSelector().add("id");
		view.getSelector().add("number");
		ContractChangeBillCollection ContractChangeCol = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(view);
		if (ContractChangeCol != null && ContractChangeCol.size() > 0) {
			//????????????????????????
			isContractChangeSettled = false;
		} else {
			//??????????????????????
			isContractChangeSettled = true;
		}
		return isContractChangeSettled;
	}


	/**
	 * 1????????????????????????(txtqualityGuarante)????????????????????txtTotalSettlePrice??
	 * 2??????????????????????????????????????????????????????????totalPay??
	 * 3??????????????????-????????????????????????????,????????????????
	 * by ??????  2009-11-12
	 */

	public void chkSettlementAmt() throws BOSException {
		if(BooleanEnum.FALSE.equals(this.cbFinalSettle.getSelectedItem())){
				return;
		}
		BigDecimal qualityGuarante = FDCHelper.toBigDecimal(txtqualityGuarante
				.getBigDecimalValue());// ??????????
	    BigDecimal leastSettAmt = FDCHelper.toBigDecimal(txtTotalSettlePrice
				.getBigDecimalValue());// ????????????????
		BigDecimal totalPay = FDCHelper.ZERO;// ????????

		//????????????   ????????????????????????????????????????????????????????????+???????????????????????????????????? by cassiel_peng 2009-12-02
		if(this.editData.getContractBill().getId()!=null){
			totalPay=ContractClientUtils.getPayAmt(this.editData.getContractBill().getId().toString());
		}
	
		if (qualityGuarante.compareTo(FDCHelper
				.subtract(leastSettAmt, totalPay)) > 0) {
				FDCMsgBox.showError("??????????????????????????????????-?????????????????????? =?????????????????????????????????????????????? + ????????????????????????????????????");
				abort();
			
		}

	}
	
	public void chkSettAmtOverRate(){
		BigDecimal totalSettlementOrgiAmt = txtTotalOriginalAmount.getBigDecimalValue();
		BigDecimal contractOrgiAmt = FDCHelper.ZERO;
		BigDecimal canSettAmt = FDCHelper.ZERO;
		
		if(this.editData.getContractBill().getId()!=null){
			ContractBillInfo contract = null;
			try {
				contract = ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(this.editData.getContractBill().getId()));
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(contract != null ){
//				
				
//					EntityViewInfo evi = new EntityViewInfo();
//					SelectorItemCollection sic = null;
//					FilterInfo filter = null;
//				
//				    contractOrgiAmt = contract.getOriginalAmount();
//					sic = new SelectorItemCollection();
//					sic.add(new SelectorItemInfo("originalAmount"));
//					filter = new FilterInfo();
//					filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contract.getId().toString()));
//					filter.getFilterItems().add(new FilterItemInfo("state","\'"+FDCBillStateEnum.ANNOUNCE_VALUE+"\','"+FDCBillStateEnum.VISA_VALUE+"\',\'"+FDCBillStateEnum.AUDITTED_VALUE+"\'",CompareType.INCLUDE));
//					evi.setSelector(sic);
//					evi.setFilter(filter);
//					ContractChangeBillCollection collection = null;
//					 try {
//						collection = ContractChangeBillFactory.getRemoteInstance().getContractChangeBillCollection(evi);
//					} catch (BOSException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					if(collection != null && collection.size() >0 ){
//						for(Iterator it = collection.iterator();it.hasNext();){
//							contractOrgiAmt = FDCHelper.add(contractOrgiAmt, ((ContractChangeBillInfo)it.next()).getOriginalAmount());
//						}
//				    }
				//??????????????????????????????????
				
				try {
					contractOrgiAmt  = (BigDecimal) FDCUtils.getLastOriginalAmt_Batch(null, new String[]{contract.getId().toString()}).get(contract.getId().toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			/**
			 * ??????????????FDC591,FDC592??????????????????????????????????????????????????
			 */
			HashMap paramItem = null;
			FDCParamInfo paramInfo = null;
			try {
				paramItem = ParamControlFactory.getRemoteInstance().getParamHashMap(SysContext.getSysContext().getCurrentCostUnit().getId().toString(),"com.kingdee.eas.fdc.contract.contract");
				paramInfo = new FDCParamInfo(paramItem);
			} catch (EASBizException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(paramInfo != null){
				conSettType = paramInfo.getBase_ConSettType();
				costCenterConstRate = paramInfo.getBase_CostCenterConstRate();
			}
			if(contractOrgiAmt != null && contractOrgiAmt.compareTo(FDCHelper.ZERO) != 0){
				double newRate = 0.0;
				if(conSettType.equalsIgnoreCase("rdContractOverRate")){
					newRate = contract.getOverRate();
				}else{
					if(costCenterConstRate != null && !costCenterConstRate.trim().equals("")){
						newRate = new Double(costCenterConstRate).doubleValue();
					}
				}
				canSettAmt =FDCHelper.toBigDecimal(FDCHelper.multiply(contractOrgiAmt,FDCHelper.toBigDecimal(new Double(1+newRate/100.00))),2);
			}
			
			
			
			if(FDCHelper.subtract(totalSettlementOrgiAmt, canSettAmt).compareTo(FDCHelper.ZERO) > 0 ){
				if(isOverContractAmt()){
					FDCMsgBox.showError("??????????????????????????????????????????????????????????????????????????????!");
					abort();
				}else{
					int result = MsgBox.showConfirm2New(null,"??????????????????????????????????????????????");
					if(JOptionPane.NO_OPTION == result){
						 abort();
					}
				}
			}
			
			
		}
		
	}

	/*
	 * private void saveCompensationRel() throws BOSException, EASBizException {
	 * for (int i = 0; i < this.tblCompensationBill.getRowCount(); i++) { IRow
	 * row = this.tblCompensationBill.getRow(i); CompensationBillInfo info =
	 * (CompensationBillInfo) row .getUserObject(); Boolean b = (Boolean)
	 * row.getCell("select").getValue(); if (b.booleanValue()) { if
	 * (info.getDescription() == null) { info.setDescription("isSelect");
	 * SelectorItemCollection sels = new SelectorItemCollection(); sels.add(new
	 * SelectorItemInfo("description"));
	 * CompensationBillFactory.getRemoteInstance().updatePartial( info, sels); }
	 * } else { if (info.getDescription() != null) { info.setDescription(null);
	 * SelectorItemCollection sels = new SelectorItemCollection(); sels.add(new
	 * SelectorItemInfo("description"));
	 * CompensationBillFactory.getRemoteInstance().updatePartial( info, sels); }
	 * } } }
	 */

	public boolean isModify() {
		if (getOprtState() == OprtState.ADDNEW
				|| getOprtState() == OprtState.EDIT) {
			for (int i = 0; i < this.tblCompensationBill.getRowCount(); i++) {
				IRow row = this.tblCompensationBill.getRow(i);
				CompensationBillInfo info = (CompensationBillInfo) row
						.getUserObject();
				Boolean b = (Boolean) row.getCell("select").getValue();
				if (info == null || b == null)
					return false;
				if (b.booleanValue() != info.isIsCompensated()) {
					return true;
				}
			}

			for (int i = 0; i < this.tblGuerdon.getRowCount(); i++) {
				IRow row = this.tblGuerdon.getRow(i);
				GuerdonBillInfo info = (GuerdonBillInfo) row.getUserObject();
				Boolean b = (Boolean) row.getCell("select").getValue();
				if (info == null || b == null)
					return false;
				if (b.booleanValue() != info.isIsGuerdoned()) {
					return true;
				}
			}

			for (int i = 0; i < this.tblDeduct.getRowCount(); i++) {
				IRow row = this.tblDeduct.getRow(i);
				DeductBillEntryInfo info = (DeductBillEntryInfo) row
						.getUserObject();
				Boolean b = (Boolean) row.getCell("select").getValue();
				if (info == null || b == null)
					return false;
				if (b.booleanValue() != info.isHasApplied()) {
					return true;
				}
			}
		}

		return super.isModify();
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		super.actionSplit_actionPerformed(e);

		String id = getSelectBOID();

		if (id == null)
			return;

		AbstractSplitInvokeStrategy invokeStra = new ConSettleSplitInvokeStrategy(
				id, this);
		invokeStra.invokeSplit();
	}

	public void initUIToolBarLayout() {
		super.initUIToolBarLayout();

	}

	protected void initWorkButton() {
		// TODO Auto-generated method stub
		super.initWorkButton();
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		// btnSplit.setIcon(FDCConstants.ICON_SPLIT);
		// ????????????????????????????????
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
		if (orgUnit.isIsCostOrgUnit() && orgUnit.isIsLeaf()) {
			actionSplit.setEnabled(true);
			actionSplit.setVisible(true);
		} else {
			actionSplit.setEnabled(false);
			actionSplit.setVisible(false);
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		// saveCompensationRel();
		/*
		 * if(this.editData.getContractBill().getId()!=null){ Map paramMap=new
		 * HashMap(); paramMap.put("contractBillId",
		 * this.editData.getContractBill().getId().toString());
		 * paramMap.put("billState", this.editData.getState());
		 * paramMap.put("txtSettlePrice", txtSettlePrice.getBigDecimalValue());
		 * ContractClientUtils.checkTotalSettlePriceSmallerThanZero(paramMap); }
		 */
		checkAmt();
        if(isMoreSettlement){
        	if (((com.kingdee.eas.base.commonquery.BooleanEnum)this.cbFinalSettle.getSelectedItem()).getValue()==BooleanEnum.TRUE_VALUE){
        		chkSettAmtOverRate();
        	}
        }else{
		   chkSettAmtOverRate();
		}
		settlementDetailHelper.save();
		super.actionSave_actionPerformed(e);
	}

	/**
	 * by Cassiel_peng
	 */
	boolean isWarn = false;

	protected void txtOriginalAmount_focusGained(FocusEvent e) throws Exception {
		super.txtOriginalAmount_focusGained(e);
		if (STATUS_EDIT.equals(getOprtState()) && !isWarn) {
			boolean isCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_CON_SettNoCostSplit",
					"FSettlementBillID");
			boolean isNoCostSplit = FDCSplitClientHelper.isBillSplited(
					getSelectBOID(), "T_CON_SettlementCostSplit",
					"FSettlementBillID");
			if (isCostSplit || isNoCostSplit) {
				MsgBox
						.showWarning("????????????????????????????????????????????????????????????????????????????????????????????");
				isWarn = true;
			}
		}
	}

	/**
	 * ??????????????,????????????????????????????????????
	 */
	boolean amtWarned = false;
	String settlePrice = null;

	protected void txtSettlePrice_focusLost(FocusEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.txtSettlePrice_focusLost(e);

		if (settlePrice == null || txtSettlePrice.getText() == null) {
			return;
		}

		BigDecimal a = txtSettlePrice.getBigDecimalValue();

		if ((settlePrice == null && txtSettlePrice.getText() != null)
				|| (settlePrice != null && txtSettlePrice.getText() == null)
				|| new BigDecimal(settlePrice).compareTo(a) != 0) {
			if (a != null) {
				settlePrice = a.toString();
			}

			if (STATUS_EDIT.equals(getOprtState()) && !amtWarned) {
				FDCSplitClientHelper.checkSplitedForAmtChange(this,
						getSelectBOID(), "T_CON_SettlementCostSplit",
						"FSettlementBillID");
				amtWarned = true;
			}
		}
	}

	// ????????????????
	protected void txtSettlePrice_dataChanged(DataChangeEvent e)
			throws Exception {
		super.txtSettlePrice_dataChanged(e);

		calUnitPrice();
	}

	// ????????????
	protected void txtbuildArea_dataChanged(DataChangeEvent e) throws Exception {
		super.txtbuildArea_dataChanged(e);

		calUnitPrice();
	}

	// ????????????
	private void calUnitPrice() {
		BigDecimal settPrice = txtSettlePrice.getBigDecimalValue();
		BigDecimal buildArea = txtbuildArea.getBigDecimalValue();
		if (settPrice != null && buildArea != null
				&& settPrice.floatValue() != 0 && buildArea.floatValue() != 0) {
			BigDecimal unitPrice = settPrice.divide(buildArea,
					BigDecimal.ROUND_HALF_UP);
			txtunitPrice.setValue(unitPrice);
		}
	}

	/**
	 * ????????????????????????????,??????FDCBillEditUI.handCodingRule??????????
	 */
	protected void setAutoNumberByOrg(String orgType) {

	}

	private void setQualityGuarante(String flag, DataChangeEvent e) {
		// ?????????????? <= ?????? ?C ????????????????????????
		BigDecimal settleAmtAll = FDCHelper.toBigDecimal(txtTotalSettlePrice
				.getBigDecimalValue());
		// settleAmtSingle:?????????????? by Cassiel_peng
		BigDecimal settleAmtSingle = FDCHelper.toBigDecimal(txtSettlePrice
				.getBigDecimalValue());
		// if (isFirstLoad)
		// return;
		if (settleAmtAll.compareTo(FDCHelper.ZERO) == 0
				|| settleAmtSingle.compareTo(FDCHelper.ZERO) == 0) {
			txtqualityGuarante.setValue(FDCHelper.ZERO);
			txtGuaranceAmt.setValue(FDCHelper.ZERO);
			// txtqualityGuaranteRate.setValue(FDCHelper.ZERO);
			return;
		}
		if (flag.equals("txtqualityGuarante")) {// ????????????????????
												// ??????????????????????(??????"??????????"????????)
												// by Cassiel_peng
			BigDecimal qualityGuarante = FDCHelper
					.toBigDecimal(txtqualityGuarante.getBigDecimalValue());
			if (qualityGuarante.compareTo(FDCHelper.ZERO) == 0) {
				// return;
			}
			DataChangeListener[] listeners = (DataChangeListener[]) txtqualityGuaranteRate
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuaranteRate.removeDataChangeListener(listeners[i]);
			}
			// BigDecimal qualityGuaranteRate =
			// qualityGuarante.multiply(FDCHelper
			// .ONE_HUNDRED).divide(settleAmtAll, 6, BigDecimal.ROUND_HALF_UP);
//			BigDecimal qualityGuaranteRate = FDCHelper.divide(FDCHelper
//					.multiply(qualityGuarante, FDCHelper.ONE_HUNDRED),
//					settleAmtAll, 6, BigDecimal.ROUND_HALF_UP);
			
			BigDecimal qualityGuaranteRate = FDCHelper.divide(FDCHelper.multiply(qualityGuarante, FDCHelper.ONE_HUNDRED),settleAmtAll, 8, BigDecimal.ROUND_HALF_UP);
			txtqualityGuaranteRate.setValue(qualityGuaranteRate);
			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuaranteRate.addDataChangeListener(listeners[i]);
			}
		}
		/**
		 * ???????????????? by Cassiel_peng
		 */
		else if (flag.equals("txtqualityGuaranteRate")) {

			DataChangeListener[] listeners = (DataChangeListener[]) txtqualityGuarante
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuarante.removeDataChangeListener(listeners[i]);
			}

			BigDecimal qualityGuaranteRate = FDCHelper
					.toBigDecimal(txtqualityGuaranteRate.getBigDecimalValue());
			// ????????????????(?????????? * ?????????????? / 100)??????
			// BigDecimal qualityGuaranteAll =
			// qualityGuaranteRate.multiply(settleAmtAll
			// ).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal qualityGuaranteAll = FDCHelper.divide(FDCHelper.multiply(qualityGuaranteRate, settleAmtAll),	FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			txtqualityGuarante.setValue(qualityGuaranteAll);
			// ????????????????(?????????? * ?????????????? / 100)????
			// BigDecimal
			// qualityGuaranteSingle=qualityGuaranteRate.multiply(settleAmtSingle
			// ).divide(FDCHelper.ONE_HUNDRED, 2,BigDecimal.ROUND_HALF_UP);
			BigDecimal qualityGuaranteSingle = FDCHelper.divide(FDCHelper
					.multiply(qualityGuaranteRate, settleAmtSingle),
					FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			txtGuaranceAmt.setValue(qualityGuaranteSingle);

			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuarante.addDataChangeListener(listeners[i]);
			}

		}
		/**
		 * ???????????? by Cassiel_peng
		 */
		else if (flag.equals("txtGuaranceAmt")) {
			BigDecimal qualityGuaranteSingle = FDCHelper.toBigDecimal(txtGuaranceAmt.getBigDecimalValue());
			if (qualityGuaranteSingle.compareTo(FDCHelper.ZERO) == 0) {
				// return;
			}
			DataChangeListener[] listeners = (DataChangeListener[]) txtqualityGuaranteRate
					.getListeners(DataChangeListener.class);
			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuaranteRate.removeDataChangeListener(listeners[i]);
			}
			// ????????????????????(?????? * 100 / ??????????????)????
			// BigDecimal qualityGuaranteRate =
			// qualityGuaranteSingle.multiply(FDCHelper
			// .ONE_HUNDRED).divide(settleAmtSingle, 6,
			// BigDecimal.ROUND_HALF_UP);
			BigDecimal qualityGuaranteRate = FDCHelper.divide(FDCHelper.multiply(qualityGuaranteSingle, FDCHelper.ONE_HUNDRED),	settleAmtSingle, 8, BigDecimal.ROUND_HALF_UP);
			txtqualityGuaranteRate.setValue(qualityGuaranteRate);
			// ??????????????????(?????????? * ?????????????? / 100)????
			// BigDecimal
			// qualityGuaranteAll=qualityGuaranteRate.multiply(settleAmtAll
			// ).divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			BigDecimal qualityGuaranteAll = FDCHelper.divide(FDCHelper.multiply(qualityGuaranteRate, settleAmtAll),	FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			txtqualityGuarante.setValue(qualityGuaranteAll);

			for (int i = 0; i < listeners.length; i++) {
				txtqualityGuaranteRate.addDataChangeListener(listeners[i]);
			}
		}
	}

	/**
	 * @deprecated
	 */
	private void checkQualityGuarante() {
		BigDecimal settleAmt = FDCHelper.toBigDecimal(txtSettlePrice
				.getBigDecimalValue());
		BigDecimal settleTotalAmt = FDCHelper.toBigDecimal(txtTotalSettlePrice
				.getBigDecimalValue());
		BigDecimal qualityGuarante = FDCHelper.toBigDecimal(txtqualityGuarante
				.getBigDecimalValue());// ??????????
		BigDecimal qualityGuaranteRate = FDCHelper
				.toBigDecimal(txtqualityGuaranteRate.getBigDecimalValue());

		if (settleAmt.compareTo(FDCHelper.ZERO) == 0) {
			MsgBox.showError(this, "????????????????");
			SysUtil.abort();
		}

		if (qualityGuaranteRate.compareTo(FDCHelper.ONE_HUNDRED) > 0
				&& qualityGuaranteRate.compareTo(FDCHelper.ZERO) < 0) {
			MsgBox.showError(this, "??????????????????????0??????????100");
			SysUtil.abort();
		}

		if (isQualityGuarante) {
			// BigDecimal temp =
			// qualityGuarante.multiply(FDCHelper.ONE_HUNDRED).
			// divide(settleTotalAmt,6, BigDecimal.ROUND_HALF_UP);
			BigDecimal temp = FDCHelper.divide(FDCHelper.multiply(
					qualityGuarante, FDCHelper.ONE_HUNDRED), settleTotalAmt, 6,
					BigDecimal.ROUND_HALF_UP);
			if (temp.compareTo(qualityGuaranteRate) != 0) {
				MsgBox.showError(this, "????????????????????????*?????????? ??????????");
				SysUtil.abort();
			}
		} else {
			BigDecimal temp = settleTotalAmt.multiply(qualityGuaranteRate)
					.divide(FDCHelper.ONE_HUNDRED, 2, BigDecimal.ROUND_HALF_UP);
			if (temp.compareTo(qualityGuarante) != 0) {
				MsgBox.showError(this, "????????????????????????*?????????? ??????????");
				SysUtil.abort();
			}
		}
	}

	protected boolean checkCanSubmit() throws Exception {
		if (isIncorporation && ((FDCBillInfo) editData).getPeriod() == null) {
			MsgBox.showWarning(this, "??????????????????????????????????????????????????????????????????");
			SysUtil.abort();
		}
		return super.checkCanSubmit();
	}
	protected void verifyInputForSubmint() throws Exception {
		FDCClientVerifyHelper.verifyEmpty(this, this.txtConstructPrice);
		ContractSettlementBillInfo billInfo = this.editData;
		if(BooleanEnum.TRUE.equals(billInfo.getIsFinalSettle())){
			String contractId = billInfo.getContractBill().getId().toString();
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SAVED));
			filter.getFilterItems().add(new FilterItemInfo("state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("isFinalSettle", BooleanEnum.FALSE));
			filter.setMaskString("#0 and ( #1 or #2)  and  #3");
			//??????????????????????????
			if (ContractSettlementBillFactory.getRemoteInstance().exists(filter)) {
				FDCMsgBox.showWarning(this,"??????????????????????????????????????????????????????");
				SysUtil.abort();
//				throw new ContractException(ContractException.AUDITFIRST);
			}
			//??????????????????????????     by Cassiel_peng
			filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
//			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.SAVED));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.SUBMITTED));
			filter.getFilterItems().add(new FilterItemInfo("parent.state", FDCBillStateEnum.AUDITTING));//??????????????????????????????
			filter.setMaskString("#0 and ( #1 or #2)");
			if(ChangeSupplierEntryFactory.getRemoteInstance().exists(filter)){
				FDCMsgBox.showWarning(this,"????????????????????????????????????????????????????");
				SysUtil.abort();
//				throw new ContractException(ContractException.CHANGEBILLNOTAUDIT);
			}
		}
//		if(!this.btnAType.isSelected()&&!this.btnBType.isSelected()&&!this.BtnCType.isSelected()){
//			FDCMsgBox.showWarning(this, "????????????????????");
//			this.btnAType.requestFocus(true);
//			SysUtil.abort();
//		}
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("contractType.isMarket");
		sic.add("amount");
		ContractBillInfo cb=ContractBillFactory.getRemoteInstance().getContractBillInfo(new ObjectUuidPK(billInfo.getContractBill().getId()), sic);
		if(cb.getContractType().isIsMarket()&&txtSettlePrice.getBigDecimalValue().compareTo(cb.getAmount())>0){
			FDCMsgBox.showWarning(this,"??????????????????????????");
			SysUtil.abort();
		}
		PayRequestBillCollection col=PayRequestBillFactory.getRemoteInstance().getPayRequestBillCollection("select amount from where contractid='"+this.editData.getContractBill().getId()+"'");
		BigDecimal payAmount=FDCHelper.ZERO;
		for(int i=0;i<col.size();i++){
			payAmount=FDCHelper.add(payAmount,col.get(i).getAmount());
		}
		if(txtSettlePrice.getBigDecimalValue().compareTo(payAmount)<0){
			FDCMsgBox.showWarning(this,"????????????????????????????????????");
			SysUtil.abort();
		}
		super.verifyInputForSubmint();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		
		super.verifyInput(e);
		// checkQualityGuarante(); ??????????????
		checkAmt();
	}

	protected void checkAmt() throws BOSException, EASBizException {
		storeFields();
		// ????????????????????????????
//		if (!editData.getBoolean("isFinalSettle")) {
		//??????????????????????????:????????????????????????????????-?????????? ???????????????????????????????????? 
		//?? createNewData()??objectValue.setIsFinalSettle(BooleanEnum.TRUE);??????
		if(BooleanEnum.FALSE.equals(this.cbFinalSettle.getSelectedItem())){
			return;
		}
		ContractBillInfo contractBill=this.editData.getContractBill();
		boolean checkSettlePriceBiggerThanPayAmt = ContractClientUtils.checkSettlePriceBiggerThanPayAmt(this.editData,contractBill==null?"":contractBill.getId().toString());
		if (!checkSettlePriceBiggerThanPayAmt) {
			MsgBox.showError(this, "??????????????????????????????????????");
			SysUtil.abort();
		}
		
	}

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {

		if (this.editData.getContractBill().getId() != null) {
			Map paramMap = new HashMap();
			paramMap.put("contractBillId", this.editData.getContractBill()
					.getId().toString());
			paramMap.put("updateAfterSavedToSubmitBillId", this.editData
					.getId());
			paramMap.put("billState", this.editData.getState());
			paramMap.put("txtSettlePrice", txtOriginalAmount
					.getBigDecimalValue());
			ContractClientUtils.checkTotalSettlePriceSmallerThanZero(paramMap);
		}
		
		checkTotalSettlePrice();
		/**
		 * ???????????????????????????????????????????????????????????????????????????????? ????????????????????????????????+????????????
		 * ????????????????????????????????????????????????????????+?????????? 2009-11-12 ?????? *
		 */
        chkSettAmtOverRate();
		if (isMoreSettlement) {
			if (((com.kingdee.eas.base.commonquery.BooleanEnum)this.cbFinalSettle.getSelectedItem()).getValue()==BooleanEnum.TRUE_VALUE) {
				checkAmt();
				chkSettlementAmt();

			} else {
				chkSettlementAmt();
			}
		} else {
			checkAmt();
			chkSettlementAmt();
		}

		super.actionAudit_actionPerformed(e);
		// ?????????????????? ??????????????
		actionAudit.setEnabled(false);
		actionAudit.setVisible(false);
		actionUnAudit.setVisible(true);
		actionUnAudit.setEnabled(true);

		if (this.editData.getId() != null) {
			AbstractSplitInvokeStrategy strategy = SplitInvokeStrategyFactory
					.createSplitStrategyByParam(this.editData.getId()
							.toString(), this);
			strategy.invokeSplit();
		}
	}

	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {

		super.actionUnAudit_actionPerformed(e);
		// ?????????????????????? ????????????
		actionAudit.setEnabled(true);
		actionAudit.setVisible(true);
		actionUnAudit.setVisible(false);
		actionUnAudit.setEnabled(false);
	}

	protected boolean isUseMainMenuAsTitle() {
		return false;
	}

	/**
	 * 
	 * ????????????????????????
	 * 
	 * @author:liupd ??????????2006-8-26
	 *               <p>
	 */
	protected void checkRef(String id) throws Exception {
		FilterInfo filterSett = new FilterInfo();
		filterSett.getFilterItems().add(
				new FilterItemInfo("settlementBill.id", id));
		filterSett.getFilterItems().add(
				new FilterItemInfo("state", FDCBillStateEnum.INVALID_VALUE,
						CompareType.NOTEQUALS));
		boolean hasSettleSplit = false;
		if (SettlementCostSplitFactory.getRemoteInstance().exists(filterSett)
				|| SettNoCostSplitFactory.getRemoteInstance()
						.exists(filterSett)) {
			hasSettleSplit = true;
		}
		if (hasSettleSplit) {
			MsgBox.showWarning("??????????????,??????????!");
			SysUtil.abort();
			return;
		}
	}

    public void fillAttachmentList(){
    	this.cmbAttchment.removeAllItems();
		String boId = null;
		if(this.editData.getId() == null){
			return;
		}else{
			boId = this.editData.getId().toString();
		}
		
		if(boId != null){
			SelectorItemCollection sic = new SelectorItemCollection();
			sic.add(new SelectorItemInfo("id"));
			sic.add(new SelectorItemInfo("attachment.id"));
			sic.add(new SelectorItemInfo("attachment.name"));
			
			FilterInfo filter = new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("boID",boId));
			
			EntityViewInfo evi = new EntityViewInfo();
			evi.setFilter(filter);
			evi.setSelector(sic);
			BoAttchAssoCollection cols = null;
			 try {
				cols =BoAttchAssoFactory.getRemoteInstance().getBoAttchAssoCollection(evi);
			} catch (BOSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(cols != null && cols.size()>0){
				for(Iterator it = cols.iterator();it.hasNext();){
					AttachmentInfo attachment = ((BoAttchAssoInfo)it.next()).getAttachment();
					this.cmbAttchment.addItem(attachment);
				}
				this.isHasAttachment = true;
			}else{
				this.isHasAttachment =false;
			}
		}
//		this.btnv.setEnabled(this.isHasAttachment);
		this.viewAttachment.setEnabled(isHasAttachment);
    }
    
    public void actionViewAttachment_actionPerformed(ActionEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    	super.actionViewAttachment_actionPerformed(e);
    	String attachId = null;
    	if(this.cmbAttchment.getSelectedItem() != null && this.cmbAttchment.getSelectedItem() instanceof AttachmentInfo){
    		attachId = ((AttachmentInfo)this.cmbAttchment.getSelectedItem()).getId().toString();
    		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    		acm.viewAttachment(attachId);
    	}
    }
    
    public void actionAttachment_actionPerformed(ActionEvent e)
    		throws Exception {
    	// TODO Auto-generated method stub
    	super.actionAttachment_actionPerformed(e);
    	fillAttachmentList();
    }
    
    protected void lockContainer(Container container) {
    	 if(lblAttachmentContainer.getName().equals(container.getName())){
         	return;
         }
         else{
          	super.lockContainer(container);
         }
    }
    /**
     * ????????????????????????????????????????????????????????????????????????????????????????????????
     * ???????????????????????????????????????????????????????????????????????????????????????? =????
     * ?????????????????????????????????????????? + ??????????????????????????????????????????????????
     * ??????????????????????????????????????????????????
     * by jian_wen 2009.12.24
     */
    public void checkTotalSettlePrice()throws EASBizException, BOSException{   
    	//???????????? ????
    	if(BooleanEnum.FALSE.equals(this.cbFinalSettle.getSelectedItem())){
    		boolean b=isOverContractAmt();
    		//????????????
    		BigDecimal payAmt = ContractClientUtils.getPayAmt(contractBill.getId().toString());
    		if(payAmt==null){
    			payAmt=FDCHelper.ZERO;
    		}
    		if(payAmt.compareTo(txtTotalSettlePrice.getBigDecimalValue())==1){    			
    			if(b){
    				MsgBox.showError("?????????????????????????????????????????????????? =?????????????????????????????????????????????? + ????????????????????????????????????");
        			abort();
        		}
    			else{
    				MsgBox.showWarning("?????????????????????????????????????????????????? =?????????????????????????????????????????????? + ????????????????????????????????????");
    			}
    		}    		
    	}
    }
    
	/**
	 * ??????????R100806-236????????????????????????????????????????
	 * <P>
	 * ????????BT????????????????????
	 * 
	 * @author owen_wen 2011-03-08
	 */
	protected void setAuditButtonStatus(String oprtType) {
		if (STATUS_VIEW.equals(oprtType) || STATUS_EDIT.equals(oprtType)) {
			actionAudit.setVisible(true);
			actionUnAudit.setVisible(true);
			actionAudit.setEnabled(true);
			actionUnAudit.setEnabled(true);

			FDCBillInfo bill = (FDCBillInfo) editData;
			if (editData != null) {
				if (FDCBillStateEnum.AUDITTED.equals(bill.getState())) {
					actionUnAudit.setVisible(true);
					actionUnAudit.setEnabled(true);
					actionAudit.setVisible(false);
					actionAudit.setEnabled(false);
				} else {
					actionUnAudit.setVisible(false);
					actionUnAudit.setEnabled(false);
					actionAudit.setVisible(true);
					actionAudit.setEnabled(true);
				}
			}
		} else {
			actionAudit.setVisible(false);
			actionUnAudit.setVisible(false);
			actionAudit.setEnabled(false);
			actionUnAudit.setEnabled(false);
		}
	}

	/**
	 * ??????????R100806-236????????????????????????????????????????
	 * <P>
	 * ????????BT????????????????????
	 * 
	 * @author owen_wen 2011-03-08
	 */
	public void checkBeforeAuditOrUnAudit(FDCBillStateEnum state, String warning) throws Exception {

		isSameUserForUnAudit = FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_AUDITORMUSTBETHESAMEUSER);

		if (isSameUserForUnAudit && editData.getAuditor() != null) {

			if (!SysContext.getSysContext().getCurrentUserInfo().getId().equals(editData.getAuditor().getId())) {
				try {
					throw new FDCBasedataException(FDCBasedataException.AUDITORMUSTBETHESAMEUSER);
				} catch (FDCBasedataException e) {
					handUIExceptionAndAbort(e);
				}
			}
		}
		// ??????????????????????
		FDCClientUtils.checkBillInWorkflow(this, getSelectBOID());

		if (editData == null || editData.getState() == null || !editData.getState().equals(state)) {
			MsgBox.showWarning(this, FDCClientUtils.getRes(warning));
			SysUtil.abort();
		}
	}

	protected void txtCostAuditCost_dataChanged(DataChangeEvent e)
			throws Exception {
		if(isOnload)return;
		txtOriginalAmount.setValue(this.txtCostAuditCost.getBigDecimalValue());
	}

}