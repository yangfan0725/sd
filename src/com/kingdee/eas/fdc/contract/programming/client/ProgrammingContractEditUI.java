/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.programming.client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.extendcontrols.BizDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.IDataFormat;
import com.kingdee.bos.ctrl.extendcontrols.KDBizPromptBox;
import com.kingdee.bos.ctrl.extendcontrols.ext.FilterInfoProducerFactory;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTEditEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.render.ObjectValueRender;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDContainer;
import com.kingdee.bos.ctrl.swing.KDDatePicker;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.LimitedLengthDocument;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.ctrl.swing.event.DataChangeListener;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.face.WinStyle;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.client.AttachmentUIContextInfo;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AimCostInfo;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.PaymentTypeFactory;
import com.kingdee.eas.fdc.basedata.PaymentTypeInfo;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContracCostInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyCollection;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractEconomyInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractFactory;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.contract.programming.ProgrammingFactory;
import com.kingdee.eas.fdc.invite.InviteFormEnum;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.ExceptionHandler;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class ProgrammingContractEditUI extends AbstractProgrammingContractEditUI
{
	private static final Logger logger = CoreUIObject.getLogger(ProgrammingContractEditUI.class);

	protected KDWorkButton btnAddnewLine_cost;
	protected KDWorkButton btnRemoveLines_cost;
	protected KDWorkButton btnAddnewLine_economy;
	protected KDWorkButton btnRemoveLines_economy;

	private ProgrammingContractInfo pcInfo;
	private ProgrammingContractInfo oldPcInfo;
	private ProgrammingContractCollection pcCollection;

	private BigDecimal oldAmount;
	private BigDecimal oldControlAmount;
	private BigDecimal oldbalance;
	private BigDecimal oldcontrolBalance;

	// ????????????????????
	private static final String COST_ID = "id";// ID
	private static final String PROJECT = "project";// ????????(F7)
	private static final String COSTACCOUNT_NUMBER = "number";// ????????????
	private static final String COSTACCOUNT = "name";// ????????????(F7)
	private static final String GOALCOST = "goalCost";// ????????
	private static final String ASSIGNED = "assigned";// ??????
	private static final String ASSIGNING = "assigning";// ??????
	private static final String CONTRACTASSIGN = "contractAssign";// ??????????
	private static final String COST_DES = "description";// ????

	// ????????????????????
	private static final String ECONOMY_ID = "id";// ID
	private static final String PAYMENTTYPE = "paymentType";// ????????
	private static final String SCALE = "scale";// ????????
	private static final String AMOUNT = "amount";// ????????
	private static final String PAYMENTDATE = "paymentDate";// ????????
	private static final String CONDITION = "condition";// ????????
	private static final String ECONOMY_DES = "description";// ????

	public ProgrammingContractEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		initFormat();
		setSmallButton();
		if (this.oprtState.equals(OprtState.VIEW)) {
			isEditable(false);
			ctrButtonEnable(false);
		} else if (this.oprtState.equals(OprtState.EDIT) || this.oprtState.equals(OprtState.ADDNEW)) {
			isEditable(true);
			ctrButtonEnable(true);
		}

		txtAmount.addDataChangeListener(new DataChangeListener() {
			/*
			 * ??????????????????????????????????????????
			 * 1. ??????????????????????????????0
			 * 2. ????????????????????????????????????????
			 * 3.
			 */
			public void dataChanged(DataChangeEvent e) {
				if (e.getOldValue() != e.getNewValue()) {
					/**
					 * ????????????????????????
					 */
					int economyRowCount = kdtEconomy.getRowCount();
					BigDecimal planAmount = null;// ????????
					if (!FDCHelper.isEmpty(e.getNewValue())) {
						planAmount = new BigDecimal(e.getNewValue().toString());
						/* 1.??????????????????????????????0 */
						// if (planAmount.compareTo(new BigDecimal(0)) <= 0) {
						// FDCMsgBox.showInfo("\"????????\"????????0");
						// txtAmount.setValue(e.getOldValue());
						// SysUtil.abort();
						// }
						/* 2.???????????????????????????????????????? */
						// 2.1 ?????????????????????????????????? = ??????????*??????????
						for (int i = 0; i < economyRowCount; i++) {
							// BigDecimal amount = null;
							Object obj = kdtEconomy.getCell(i, SCALE).getValue();
							if (obj != null) {
								BigDecimal scale = new BigDecimal(obj.toString());
								scale = FDCHelper.divide(scale, new BigDecimal(100), 8, BigDecimal.ROUND_HALF_UP);
								kdtEconomy.getCell(i, AMOUNT).setValue(scale.multiply(planAmount));
							}
						}
					} else {
						// 2.2 ??????????????????????????????????????null
						for (int i = 0; i < economyRowCount; i++) {
							kdtEconomy.getCell(i, AMOUNT).setValue(null);
						}
					}

					/**
					 * ????????????????????????
					 */
				}
			}
		});
		KDWorkButton btnAddRowinfo = new KDWorkButton();
		btnAddRowinfo = (KDWorkButton)kdContainerCost.add(this.actionSelect);
		btnAddRowinfo.setText("????????????");
		btnAddRowinfo.setSize(new Dimension(140, 19));
		
		this.actionEdit.setVisible(false);
	}
	public void actionSelect_actionPerformed(ActionEvent e) throws Exception {
		Map ctx = new HashMap();
		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
//		ctx.put("query", this.getQueryInfo());
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (project != null) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", "error", CompareType.EQUALS));
		}
		entityView.setFilter(filter);
		ctx.put("view", entityView);
		ctx.put("col", this.pcCollection);
		ctx.put("aimCost", (AimCostInfo) this.getUIContext().get("aimCostInfo"));
		try  {
			IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
			IUIWindow costAccountDialog = uiFactory.create(CostAccountF7UI.class.getName(),ctx,null,OprtState.VIEW,WinStyle.SHOW_ONLYLEFTSTATUSBAR);
	        costAccountDialog.show();
	        CostAccountF7UI caF7UI = (CostAccountF7UI) costAccountDialog.getUIObject();
	        CostAccountInfo [] newCostAccount=null;
	        if(!caF7UI.isCancel()){
	        	newCostAccount=caF7UI.getData();
	        }
	        if(newCostAccount!=null&&newCostAccount.length>0){
	        	
	        	List account=new ArrayList();
	        	for(int j=0;j<newCostAccount.length;j++){
	        		boolean isAdd=true;
	        		CostAccountInfo newCostAccountInfo=newCostAccount[j];
	        		for(int i=0;i<kdtCost.getRowCount();i++){
		        		CostAccountInfo costAccountObj = (CostAccountInfo)kdtCost.getCell(i, COSTACCOUNT).getValue();
	        			if(costAccountObj!=null&&newCostAccountInfo.getId().equals(costAccountObj.getId())){
	        				isAdd=false;
	        				break;
	        			}
	        		}
	        		if(isAdd){
	        			account.add(newCostAccountInfo);
	        		}
        		}
	        	BigDecimal allAssigned = FDCHelper.ZERO;
				for(int i=0;i<account.size();i++){
					CostAccountInfo newCostAccountInfo=(CostAccountInfo) account.get(i);
//					for(int j=0;j<kdtCost.getRowCount();j++){
//						if (isCostAccountDup(newCostAccountInfo, project, j)) {
//							continue;
//						}
//					}
					IRow row=kdtCost.addRow();
					int rowIndex=row.getRowIndex();
					kdtCost.getCell(rowIndex, COSTACCOUNT).setValue(newCostAccountInfo);
					row.getCell(COST_ID).setValue(BOSUuid.create("9E6FDD26"));
					row.getCell(PROJECT).setValue(project);
					//?????? ????????????????????????????????????
					row.getCell(GOALCOST).setValue(FDCHelper.ZERO);
					row.getCell(ASSIGNED).setValue(FDCHelper.ZERO);
					row.getCell(ASSIGNING).setValue(FDCHelper.ZERO);
					row.getCell(CONTRACTASSIGN).setValue(FDCHelper.ZERO);
					projectF7();
					costAccountCellF7(project, rowIndex, kdtCost.getColumnIndex(COSTACCOUNT),this.pcCollection);
					
					kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(newCostAccountInfo.getLongNumber().replace('!', '.'));
					// 2.
					AimCostInfo aimCostInfo = (AimCostInfo) this.getUIContext().get("aimCostInfo");// ????????
					if (aimCostInfo == null) {
						// 2.1
//						ProgrammingContractUtil.clearCell(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
						ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
					} else {
						// 2.2????????????
						if (!isAimCostAudit(aimCostInfo)) {
							// 2.2.1
							ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
						} else {
							// 2.2.2 ????????????????
							// CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex, PROJECT).getValue();
							if (project != null) {
								BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(newCostAccountInfo,
										aimCostInfo);
								if (goalCost.compareTo(FDCHelper.ZERO) == 0) {
									ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
									afterContractAssignChange();
									afterPlanAmountChange();
								} else {
									allAssigned = getAllContractAssign(newCostAccountInfo, false);// ??????
									// ????"??????" == "????????" - "??????"
									BigDecimal assigning = goalCost.subtract(allAssigned);// ??????
									// ????"??????????"="??????"
									BigDecimal contractAssign = assigning;// ??????????
									// ??????????????
									kdtCost.getCell(rowIndex, GOALCOST).setValue(goalCost);// ????????
									kdtCost.getCell(rowIndex, ASSIGNED).setValue(allAssigned);// ??????
									kdtCost.getCell(rowIndex, ASSIGNING).setValue(assigning);// ??????
									kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(contractAssign);// ??????????

									// ??????????????????????????"????????"
									afterContractAssignChange();
									// ????????"????????"??????????????????????"????????"??"????????"
									// ??????"????????"????????????"????????"
									afterPlanAmountChange();
								}
							}
						}
					}
				}
	        }
		}catch (BOSException ex) {
	        ExceptionHandler.handle(ex);
	        SysUtil.abort();
		}
	}

	public void onShow() throws Exception {
		super.onShow();
		initTabalFormat();
		this.btnAttachment.setIcon(EASResource.getIcon("imgTbtn_affixmanage"));
		this.txtParentLongName.setEditable(false);// ??????????????????????????
		this.txtAttachment.setEditable(false);// ????????????????????????????????????????????????????????????????????????
		Map uiContext = this.getUIContext();
		Object object = uiContext.get("inviteProject");
		Object contractBillProg = uiContext.get("programmingContractTemp");
		if (object != null || contractBillProg != null) {
			this.btnSave.setVisible(false);// ??????????????????????????????????????
		}
		pcInfo = (ProgrammingContractInfo) uiContext.get("programmingContract");
		if (pcInfo.getId() == null) {
			pcInfo.setId(BOSUuid.create(pcInfo.getBOSType()));
		}
		oldPcInfo = pcInfo;

		oldAmount = pcInfo.getAmount();
		if(oldAmount==null){
			oldAmount = FDCHelper.ZERO;
		}
		oldControlAmount = pcInfo.getControlAmount();
		if(oldControlAmount == null){
			oldControlAmount = FDCHelper.ZERO;
		}
		oldbalance = pcInfo.getBalance();
		if (oldbalance == null) {
			oldbalance = FDCHelper.ZERO;
		}
		oldcontrolBalance = pcInfo.getControlBalance();
		if (oldcontrolBalance == null) {
			oldcontrolBalance = FDCHelper.ZERO;
		}
		pcCollection = (ProgrammingContractCollection) uiContext.get("pcCollection");
		preparePCData();
		
		txtName.setDocument(new LimitedLengthDocument() {
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
				if (str.matches("^\\.+$")) {
					return;
				}
				super.insertString(offs, str, a);
			}

			public void remove(int offs, int len) throws BadLocationException {
				super.remove(offs, len);
			}
		});
		txtName.setText(pcInfo.getName() == null ? null : pcInfo.getName().trim());
		
		if(pcInfo.getId()==null||!this.oprtState.equals(OprtState.VIEW)){
			this.btnEdit.setEnabled(false);
			this.btnAddnewLine_economy.setEnabled(false);
			this.btnRemoveLines_economy.setEnabled(false);
		}
		if(pcInfo.getId()!=null&&this.oprtState.equals(OprtState.VIEW)){
			this.btnEdit.setEnabled(true);
			this.btnAddnewLine_economy.setEnabled(true);
			this.btnRemoveLines_economy.setEnabled(true);
		}
		kDTabbedPane1.remove(kDContainerEconomy);
	}

	/**
	 * ????????????????????????????????????????????
	 */
	private void setSmallButton() {
		btnAddnewLine_cost = new KDWorkButton();
		btnRemoveLines_cost = new KDWorkButton();
		btnAddnewLine_economy = new KDWorkButton();
		btnRemoveLines_economy = new KDWorkButton();

		btnAddnewLine_cost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_cost_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		btnRemoveLines_cost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_cost_actionPerformed(e);
					if (kdtCost.getRowCount() == 0) {
						btnRemoveLines_cost.setEnabled(false);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAddnewLine_economy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionAddnewLine_enocomy_actionPerformed(e);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnRemoveLines_economy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					actionRemoveLine_enocomy_actionPerformed(e);
					if (kdtEconomy.getRowCount() == 0) {
						btnRemoveLines_economy.setEnabled(false);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		setButtonStyle(kdContainerCost, btnAddnewLine_cost, "??????", "imgTbtn_addline");
		setButtonStyle(kdContainerCost, btnRemoveLines_cost, "??????", "imgTbtn_deleteline");
		setButtonStyle(kDContainerEconomy, btnAddnewLine_economy, "??????", "imgTbtn_addline");
		setButtonStyle(kDContainerEconomy, btnRemoveLines_economy, "??????", "imgTbtn_deleteline");

		if (OprtState.VIEW.equals(getOprtState())) {
			setButtionEnable(false);
		} else {
			setButtionEnable(true);
		}
	}

	/**
	 * ????????????
	 * 
	 * @param kdContainer
	 * @param button
	 * @param text
	 * @param icon
	 */
	private void setButtonStyle(KDContainer kdContainer, KDWorkButton button, String text, String icon) {
		button.setText(text);
		button.setToolTipText(text);
		button.setVisible(true);
		button.setIcon(EASResource.getIcon(icon));
		kdContainer.addButton(button);
	}

	/**
	 * ????????????????
	 * 
	 * @param isEnable
	 */
	private void setButtionEnable(boolean isEnable) {
		btnAddnewLine_cost.setEnabled(isEnable);
		btnRemoveLines_cost.setEnabled(isEnable);
		btnAddnewLine_economy.setEnabled(isEnable);
		btnRemoveLines_economy.setEnabled(isEnable);
		this.actionSelect.setEnabled(isEnable);
	}
	protected void actionAddnewLine_cost_actionPerformed(ActionEvent e) {
		IRow row = kdtCost.addRow();
		int rowIndex = row.getRowIndex();

		CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
		row.getCell(COST_ID).setValue(BOSUuid.create("9E6FDD26"));
		row.getCell(PROJECT).setValue(project);
		//?????? ????????????????????????????????????
		row.getCell(GOALCOST).setValue(FDCHelper.ZERO);
		row.getCell(ASSIGNED).setValue(FDCHelper.ZERO);
		row.getCell(ASSIGNING).setValue(FDCHelper.ZERO);
		row.getCell(CONTRACTASSIGN).setValue(FDCHelper.ZERO);
		projectF7();
		costAccountCellF7(project, rowIndex, kdtCost.getColumnIndex(COSTACCOUNT),this.pcCollection);
	}

	protected void actionRemoveLine_cost_actionPerformed(ActionEvent e) throws Exception {
		if (kdtCost.getSelectManager().getActiveRowIndex() < 0) {
			FDCMsgBox.showInfo("????????");
			SysUtil.abort();
		}
		if (FDCMsgBox.OK == FDCMsgBox.showConfirm2("??????????????????")) {
//			if(pcInfo.isIsCiting()||pcInfo.isIsWTCiting()){
//	    		MsgBox.showInfo("????????????????????,??????????");
//	    		SysUtil.abort();
//	    	}
			int rowIndex = this.kdtCost.getSelectManager().getActiveRowIndex();
			Object contractAssignObj = kdtCost.getCell(rowIndex, CONTRACTASSIGN).getValue();
			if (contractAssignObj != null) {
				BigDecimal contractAssign = new BigDecimal(contractAssignObj.toString());
				Object amountObj = this.txtAmount.getValue();
				if (amountObj != null) {
					BigDecimal amount = new BigDecimal(amountObj.toString());
					txtAmount.setValue(amount.subtract(contractAssign));
					afterPlanAmountChange();
				}
			}
			removeLine(kdtCost, rowIndex);
		}
	}

	protected void actionAddnewLine_enocomy_actionPerformed(ActionEvent e) {
		IRow row = kdtEconomy.addRow();
		row.getCell(ECONOMY_ID).setValue(BOSUuid.create("144467E3"));
	}

	protected void actionRemoveLine_enocomy_actionPerformed(ActionEvent e) throws Exception {
		if (kdtEconomy.getSelectManager().getActiveRowIndex() < 0) {
			FDCMsgBox.showInfo("????????");
			SysUtil.abort();
		}
		if (FDCMsgBox.OK == FDCMsgBox.showConfirm2("??????????????????")) {
			int rowIndex = this.kdtEconomy.getSelectManager().getActiveRowIndex();
			removeLine(kdtEconomy, rowIndex);
		}
	}

	/**
	 * ??????????
	 */
	protected void loadLineFields(KDTable table, IRow row, IObjectValue obj) {
		dataBinder.loadLineFields(table, row, obj);
	}

	/**
	 * ??????????????????????????????????????
	 */
	protected IObjectValue createNewDetailData(KDTable table, Object obj) {
		if (table == null) {
			return null;
		}
		if (obj instanceof ProgrammingContracCostInfo) {
			ProgrammingContracCostInfo newDetailInfo = (ProgrammingContracCostInfo) obj;
			newDetailInfo.setId(BOSUuid.create("9E6FDD26"));
			return (IObjectValue) newDetailInfo;
		}
		if (obj instanceof ProgrammingContractEconomyInfo) {
			ProgrammingContractEconomyInfo newDetailInfo = new ProgrammingContractEconomyInfo();
			newDetailInfo.setId(BOSUuid.create("144467E3"));
			return (IObjectValue) newDetailInfo;
		}
		return null;
	}

	/**
	 * ????????????????????????
	 * 
	 * @param table
	 * @param rowIndex
	 * @throws Exception
	 */
	protected void removeLine(KDTable table, int rowIndex) throws Exception {
		IObjectValue detailData = (IObjectValue) table.getRow(rowIndex).getUserObject();
		table.removeRow(rowIndex);
		IObjectCollection collection = (IObjectCollection) table.getUserObject();
		if (collection != null) {
			if (detailData != null) {
				collection.removeObject(rowIndex);
			}
		}
	}

	/**
	 * ??????KDFormattedTextField??????
	 */
	private void initFormat(){
		setTextFormat(txtAmount);
		setTextFormat(txtControlAmount);
		setTextFormat(txtEstimateAmount);
		filterORG();
	}

	private void filterORG() {
		EntityViewInfo view = prmtInviteOrg.getEntityViewInfo();
		if (view == null) {
			view = new EntityViewInfo();
		}
		if (view.getFilter() == null) {
			view.setFilter(new FilterInfo());
		}
		Set idSet = null;
		try {
			idSet = FDCUtils.getAuthorizedOrgs(null);
		} catch (Exception e) {
			logger.error(e);
		}
		view.getFilter().getFilterItems().add(new FilterItemInfo("COSTCENTERORGUNIT.id", idSet, CompareType.INCLUDE));
		prmtInviteOrg.setEntityViewInfo(view);
	}

	private void initTabalFormat(){
		// kdtCost.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		// kdtCost.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		// kdtEconomy.getSelectManager().setSelectMode(KDTSelectManager.CELL_SELECT);
		// kdtEconomy.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
		/* ???????? */
		this.kdtCost.checkParsed();
		// ????????
		FDCHelper.formatTableNumber(kdtCost, GOALCOST);
		// ??????
		FDCHelper.formatTableNumber(kdtCost, ASSIGNED);
		// ??????
		FDCHelper.formatTableNumber(kdtCost, ASSIGNING);
		// ??????????
		FDCHelper.formatTableNumber(kdtCost, CONTRACTASSIGN);
		KDTDefaultCellEditor cellEditor0 = new KDTDefaultCellEditor(new KDFormattedTextField());
		KDFormattedTextField kdftf = (KDFormattedTextField) cellEditor0.getComponent();
		kdftf.setDataType(1);
		kdftf.setSupportedEmpty(true);
		kdftf.setMinimumValue(FDCHelper.ZERO);
		kdftf.setPrecision(2);
		kdtCost.getColumn(CONTRACTASSIGN).setEditor(cellEditor0);
		
		// ????
		KDTDefaultCellEditor cellEditorDes = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtfDes = (KDTextField) cellEditorDes.getComponent();
		kdtfDes.setMaxLength(80);
		this.kdtCost.getColumn(COST_DES).setEditor(cellEditorDes);

		KDFormattedTextField txtWeight = new KDFormattedTextField();
		txtWeight.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtWeight.setDataVerifierType(KDFormattedTextField.NO_VERIFIER);
		txtWeight.setPrecision(2);
		txtWeight.setMaximumValue(new BigDecimal(100));
		txtWeight.setMinimumValue(FDCHelper.ZERO);
		KDTDefaultCellEditor weight = new KDTDefaultCellEditor(txtWeight);
		this.kdtCost.getColumn("scale").setEditor(weight);
		this.kdtCost.getColumn("scale").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtCost.getColumn("scale").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		/* ???????? */

		this.kdtEconomy.checkParsed();
		// ????????
		this.kdtEconomy.getColumn(PAYMENTDATE).getStyleAttributes().setNumberFormat("yyyy-MM");
		// ???????? -----????????????80??????
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(new KDTextField());
		KDTextField kdtf = (KDTextField) cellEditor.getComponent();
		kdtf.setMaxLength(80);
		this.kdtEconomy.getColumn(CONDITION).setEditor(cellEditor);
		
		this.kdtEconomy.getColumn("scale").setEditor(weight);
		this.kdtEconomy.getColumn("scale").getStyleAttributes().setNumberFormat("#0.00");
		this.kdtEconomy.getColumn("scale").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		
		KDTDefaultCellEditor description = new KDTDefaultCellEditor(new KDTextField());
		KDTextField des = (KDTextField) description.getComponent();
		des.setMaxLength(255);
		this.kdtEconomy.getColumn("description").setEditor(description);

	}
	/**
	 * ??????KDFormattedTextField??????????????
	 * */
	private static void setTextFormat(KDFormattedTextField textField) {
		textField.setRemoveingZeroInDispaly(false);
		textField.setRemoveingZeroInEdit(false);
		textField.setPrecision(2);
		textField.setHorizontalAlignment(JTextField.RIGHT);
		textField.setSupportedEmpty(true);
	}
	/**
	 * ????????
	 * 
	 * @param b
	 */
	private void ctrButtonEnable(boolean isEnable) {
		btnAttachment.setEnabled(true);
		btnSave.setEnabled(isEnable);
		btnAddnewLine_cost.setEnabled(isEnable);
		btnRemoveLines_cost.setEnabled(false);
		btnAddnewLine_economy.setEnabled(isEnable);
		btnRemoveLines_economy.setEnabled(false);
		this.actionSelect.setEnabled(isEnable);
	}

	/**
	 * ????????
	 * 
	 * @param b
	 */
	private void isEditable(boolean isEditable) {
		// ??????????
		txtParentLongName.setEditable(isEditable);
		txtNumber.setEditable(isEditable);
		txtName.setEditable(isEditable);
		txtAmount.setEditable(isEditable);
		txtControlAmount.setEditable(isEditable);
		txtEstimateAmount.setEditable(isEditable);

		txtWorkContent.setEditable(isEditable);
		txtSupMaterial.setEditable(isEditable);
		txtDescription.setEditable(isEditable);
		kdcInviteWay.setEditable(isEditable);
		kdcInviteWay.setEnabled(isEditable);
		prmtInviteOrg.setEditable(isEditable);
		prmtInviteOrg.setEnabled(isEditable);
	    
		// ????
		kdtCost.setEditable(isEditable);
//		kdtEconomy.setEditable(isEditable);
	}

	/**
	 * ??????????????
	 */
	private boolean directExit = false;
	protected boolean checkBeforeWindowClosing() {
		this.kdtCost.getEditManager().editingStopped();
		this.kdtEconomy.getEditManager().editingStopped();
		try {
			this.txtControlAmount.commitEdit();
			this.txtAmount.commitEdit();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		if (this.oprtState.equals(OprtState.ADDNEW) || this.oprtState.equals(OprtState.EDIT)) {
			if (verifyIsModify()) {
				int i = FDCMsgBox.showConfirm3("???????????????????????????");
				if (i == FDCMsgBox.OK) {
					try {
						directExit = true;
						actionSubmit_actionPerformed(null);
					} catch (Exception e) {
						SysUtil.abort();
						e.printStackTrace();
					}
				} else if (i == FDCMsgBox.NO) {
					return super.checkBeforeWindowClosing();
				} else if (i == FDCMsgBox.CANCEL) {
					return false;
				}
			}
		}
		return super.checkBeforeWindowClosing();
	}

	/**
	 * ????????????????????
	 * 
	 * @return
	 * 
	 *         true????????????
	 * 
	 *         false????????????
	 */
	private boolean verifyIsModify() {
		if (oprtState.equals(OprtState.EDIT) || oprtState.equals(OprtState.ADDNEW)) {
			// ??????
			if (FDCHelper.isEmpty(txtNumber.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getLongNumber())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtNumber.getText()) & !FDCHelper.isEmpty(oldPcInfo.getLongNumber())) {
				if (!txtNumber.getText().equals(oldPcInfo.getLongNumber())) {
					return true;
				}
			}
			// ????
			if (FDCHelper.isEmpty(txtName.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getName())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtName.getText()) & !FDCHelper.isEmpty(oldPcInfo.getName())) {
				if (!txtName.getText().equals(oldPcInfo.getName().trim())) {
					return true;
				}
			}
			// ????????
			if (FDCHelper.isEmpty(txtControlAmount.getNumberValue()) ^ FDCHelper.isEmpty(oldPcInfo.getControlAmount())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtControlAmount.getNumberValue()) & !FDCHelper.isEmpty(oldPcInfo.getControlAmount())) {
				if (new BigDecimal(txtControlAmount.getNumberValue().toString()).compareTo(oldPcInfo.getControlAmount()) != 0) {
					return true;
				}
			}

			// ????????
			if (FDCHelper.isEmpty(txtAmount.getNumberValue()) ^ FDCHelper.isEmpty(oldPcInfo.getAmount())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtAmount.getNumberValue()) & !FDCHelper.isEmpty(oldPcInfo.getAmount())) {
				if (new BigDecimal(txtAmount.getNumberValue().toString()).compareTo(oldPcInfo.getAmount()) != 0) {
					return true;
				}
			}

			// ????????
			if (FDCHelper.isEmpty(kdcInviteWay.getSelectedItem()) ^ FDCHelper.isEmpty(oldPcInfo.getInviteWay())) {
				return true;
			}
			if (!FDCHelper.isEmpty(kdcInviteWay.getSelectedItem()) & !FDCHelper.isEmpty(oldPcInfo.getInviteWay())) {
				if (!kdcInviteWay.getSelectedItem().equals(oldPcInfo.getInviteWay())) {
					return true;
				}
			}

			// ????????
			if (FDCHelper.isEmpty(prmtInviteOrg.getData()) ^ FDCHelper.isEmpty(oldPcInfo.getInviteOrg())) {
				return true;
			}
			if (!FDCHelper.isEmpty(prmtInviteOrg.getData()) & !FDCHelper.isEmpty(oldPcInfo.getInviteOrg())) {
				if (!prmtInviteOrg.getData().equals(oldPcInfo.getInviteOrg())) {
					return true;
				}
			}

			// ????????
			if (FDCHelper.isEmpty(txtWorkContent.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getWorkContent())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtWorkContent.getText()) & !FDCHelper.isEmpty(oldPcInfo.getWorkContent())) {
				if (!txtWorkContent.getText().equals(oldPcInfo.getWorkContent())) {
					return true;
				}
			}
			// ??????????????
			if (FDCHelper.isEmpty(txtSupMaterial.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getSupMaterial())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtSupMaterial.getText()) & !FDCHelper.isEmpty(oldPcInfo.getSupMaterial())) {
				if (!txtSupMaterial.getText().equals(oldPcInfo.getSupMaterial())) {
					return true;
				}
			}
			
			// ????
			if (FDCHelper.isEmpty(txtDescription.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getDescription())) {
				return true;
			}
			if (!FDCHelper.isEmpty(txtDescription.getText()) & !FDCHelper.isEmpty(oldPcInfo.getDescription())) {
				if (!txtDescription.getText().equals(oldPcInfo.getDescription())) {
					return true;
				}
			}
			// ????
			// if (FDCHelper.isEmpty(txtAttachment.getText()) ^ FDCHelper.isEmpty(oldPcInfo.getAttachment())) {
			// return true;
			// }
			// if (!FDCHelper.isEmpty(txtAttachment.getText()) & !FDCHelper.isEmpty(oldPcInfo.getAttachment())) {
			// if (!txtAttachment.getText().equals(oldPcInfo.getAttachment())) {
			// return true;
			// }
			// }

			if (kdtCost.getRowCount() == oldPcInfo.getCostEntries().size()) {
				for (int i = 0; i < kdtCost.getRowCount(); i++) {
					for (int j = 0; j < oldPcInfo.getCostEntries().size(); j++) {
						if (oldPcInfo.getCostEntries().get(j).getId() == kdtCost.getCell(i, COST_ID).getValue()) {
							CurProjectInfo project = (CurProjectInfo) this.getUIContext().get("project");
							CostAccountInfo infoCA = oldPcInfo.getCostEntries().get(j).getCostAccount();
							BigDecimal infoContractAssign = oldPcInfo.getCostEntries().get(j).getContractAssign();
							String infoDescription = oldPcInfo.getCostEntries().get(j).getDescription();

							CurProjectInfo tablePro = (CurProjectInfo) kdtCost.getCell(j, PROJECT).getValue();
							CostAccountInfo tableCA = (CostAccountInfo) kdtCost.getCell(j, COSTACCOUNT).getValue();
							Object contractAssign = kdtCost.getCell(j, CONTRACTASSIGN).getValue();
							Object description = kdtCost.getCell(j, COST_DES).getValue();

							// ????????
							if (FDCHelper.isEmpty(project) ^ FDCHelper.isEmpty(tablePro)) {
								return true;
							}
							if (!FDCHelper.isEmpty(project) & !FDCHelper.isEmpty(tablePro)) {
								if (!project.getName().equals(tablePro.getName())) {
									return true;
								}
							}
							// ????????F7
							if (FDCHelper.isEmpty(infoCA) ^ FDCHelper.isEmpty(tableCA)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoCA) & !FDCHelper.isEmpty(tableCA)) {
								if (!infoCA.getName().equals(tableCA.getName())) {
									return true;
								}
							}
							// ??????????
							if (FDCHelper.isEmpty(infoContractAssign) ^ FDCHelper.isEmpty(contractAssign)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoContractAssign) & !FDCHelper.isEmpty(contractAssign)) {
								BigDecimal temp = new BigDecimal(contractAssign.toString());
								if (infoContractAssign.compareTo(temp) != 0) {
									return true;
								}
							}
							// ????
							if (FDCHelper.isEmpty(infoDescription) ^ FDCHelper.isEmpty(description)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoDescription) & !FDCHelper.isEmpty(description)) {
								if (!infoDescription.equals(description.toString())) {
									return true;
								}
							}
						}
					}
				}
			} else {
				return true;
			}
			if (kdtEconomy.getRowCount() == oldPcInfo.getEconomyEntries().size()) {
				for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
					for (int j = 0; j < oldPcInfo.getEconomyEntries().size(); j++) {
						if (oldPcInfo.getEconomyEntries().get(j).getId() == kdtEconomy.getCell(i, ECONOMY_ID).getValue()) {

							PaymentTypeInfo infoPT = oldPcInfo.getEconomyEntries().get(j).getPaymentType();
							BigDecimal infoScale = oldPcInfo.getEconomyEntries().get(j).getScale();
							BigDecimal infoAmount = oldPcInfo.getEconomyEntries().get(j).getAmount();
							String infoCondition = oldPcInfo.getEconomyEntries().get(j).getCondition();
							String infoDescription = oldPcInfo.getEconomyEntries().get(j).getDescription();

							PaymentTypeInfo tablePT = (PaymentTypeInfo) kdtEconomy.getCell(j, PAYMENTTYPE).getValue();
							Object scale = kdtEconomy.getCell(j, SCALE).getValue();
							Object amount = kdtEconomy.getCell(j, AMOUNT).getValue();
							Object condition = kdtEconomy.getCell(j, CONDITION).getValue();
							Object description = kdtEconomy.getCell(j, ECONOMY_DES).getValue();

							// ????????
							if (FDCHelper.isEmpty(infoPT) ^ FDCHelper.isEmpty(tablePT)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoPT) & !FDCHelper.isEmpty(tablePT)) {
								if (!infoPT.getName().equals(tablePT.getName())) {
									return true;
								}
							}

							// ????????
							if (FDCHelper.isEmpty(infoScale) ^ FDCHelper.isEmpty(scale)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoScale) & !FDCHelper.isEmpty(scale)) {
								BigDecimal temp = new BigDecimal(scale.toString());
								if (infoScale.compareTo(temp) != 0) {
									return true;
								}
							}
							// ????????
							if (FDCHelper.isEmpty(infoAmount) ^ FDCHelper.isEmpty(amount)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoAmount) & !FDCHelper.isEmpty(amount)) {
								BigDecimal temp = new BigDecimal(amount.toString());
								if (infoAmount.compareTo(temp) != 0) {
									return true;
								}
							}
							// ????????
							if (FDCHelper.isEmpty(infoCondition) ^ FDCHelper.isEmpty(condition)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoCondition) & !FDCHelper.isEmpty(condition)) {
								if (!infoCondition.equals(condition.toString())) {
									return true;
								}
							}
							// ????
							if (FDCHelper.isEmpty(infoDescription) ^ FDCHelper.isEmpty(description)) {
								return true;
							}
							if (!FDCHelper.isEmpty(infoDescription) & !FDCHelper.isEmpty(description)) {
								if (!infoDescription.equals(description.toString())) {
									return true;
								}
							}
						}
					}
				}
			} else {
				return true;
			}
		}
		if (oprtState.equals(OprtState.VIEW)) {
			return false;
		}
		return false;
	}

	/**
	 * ????
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyIsEmpty();
		verifyAllData();
		/* ?????????????? */
		pcInfo.setLongNumber(txtNumber.getText());// ??????
		if (FDCHelper.isEmpty(txtParentLongName.getText())) {
			pcInfo.setDisplayName(txtParentLongName.getText());// ??????????????
		} else {
			pcInfo.setDisplayName(txtParentLongName.getText() + "." + txtName.getText());
		}
		pcInfo.setNumber(txtNumber.getText());
		pcInfo.setName(setNameIndent(pcInfo.getLevel()) + txtName.getText());// ????
		pcInfo.setInviteWay((InviteFormEnum) kdcInviteWay.getSelectedItem());// ????????
		pcInfo.setInviteOrg((CostCenterOrgUnitInfo) prmtInviteOrg.getData());		
		pcInfo.setAmount((BigDecimal) txtAmount.getValue());// ????????
		pcInfo.setControlAmount((BigDecimal) txtControlAmount.getValue());// ????????
		pcInfo.setWorkContent(txtWorkContent.getText());// ????????
		pcInfo.setSupMaterial(txtSupMaterial.getText());// ??????????????	
		pcInfo.setDescription(txtDescription.getText());// ????
		if (FDCHelper.isEmpty(txtAttachment.getText())) {
			pcInfo.setAttachment(null);// ????
		} else {
			pcInfo.setAttachment(txtAttachment.getText());// ????
		}
		
		updateBalance(pcInfo);
		/* ???????????????? */
		/* ???????? */
		pcInfo.getCostEntries().clear();
		int cost_rowCount = kdtCost.getRowCount();
		StringBuffer costAccountNames = new StringBuffer(); 
		for (int i = 0; i < cost_rowCount; i++) {
			ProgrammingContracCostInfo pccInfo = new ProgrammingContracCostInfo();
			Object project = kdtCost.getCell(i, PROJECT).getValue();// ????????
			Object costAccount = kdtCost.getCell(i, COSTACCOUNT).getValue();// ????????
			Object goalCost = kdtCost.getCell(i, GOALCOST).getValue();// ????????
			Object assigned = kdtCost.getCell(i, ASSIGNED).getValue();// ??????
			Object assigning = kdtCost.getCell(i, ASSIGNING).getValue();// ??????
			Object contractAssign = kdtCost.getCell(i, CONTRACTASSIGN).getValue();// ??????????
			String description = (String) kdtCost.getCell(i, COST_DES).getValue();// ????
			CurProjectInfo projectInfo = (CurProjectInfo) project;
			CostAccountInfo costAccountInfo = (CostAccountInfo) costAccount;
			if(i > 0){
				costAccountNames.append(";");
			}
			costAccountNames.append(costAccountInfo.getName());
			costAccountInfo.setCurProject(projectInfo);
			pccInfo.setCostAccount(costAccountInfo);
			if (!FDCHelper.isEmpty(goalCost)) {
				pccInfo.setGoalCost(new BigDecimal(goalCost.toString()));
			}
			if (!FDCHelper.isEmpty(assigned)) {
				pccInfo.setAssigned(new BigDecimal(assigned.toString()));
			}
			if (!FDCHelper.isEmpty(assigning)) {
				pccInfo.setAssigning(new BigDecimal(assigning.toString()));
			}
			if (!FDCHelper.isEmpty(contractAssign)) {
				pccInfo.setContractAssign(new BigDecimal(contractAssign.toString()));
			}
			pccInfo.setDescription(description);
			pcInfo.getCostEntries().add(pccInfo);
		}
		pcInfo.setCostAccountNames(costAccountNames.toString());
		// ????????????????"??????"????
		int pteSize = pcCollection.size();
		for (int i = 0; i < pteSize; i++) {
			ProgrammingContracCostCollection pccCollection = pcCollection.get(i).getCostEntries();// ????????????
			for (int j = 0; j < pccCollection.size(); j++) {
				ProgrammingContracCostInfo pccInfo = pccCollection.get(j);// ????????
				BigDecimal oldContractAssign = pccInfo.getContractAssign();// ??????????
				CostAccountInfo costAccountInfo = pccInfo.getCostAccount();// ????????
				BigDecimal goalCost = pccInfo.getGoalCost();// ????????
				if (costAccountInfo != null) {
					BigDecimal allCostAccountAssign = getAllContractAssign(costAccountInfo, true);// "??????????"????????
					if (oldContractAssign != null) {
						// ????"??????":"????????"-"??????????"????????+????"??????????"
						BigDecimal newAssigning = goalCost.subtract(allCostAccountAssign).add(oldContractAssign);
						// ????"??????"??"??????????"????????-????"??????????"
						BigDecimal newAssigned = allCostAccountAssign.subtract(oldContractAssign);
						pccInfo.setAssigning(newAssigning);
						pccInfo.setAssigned(newAssigned);
					}
				}
			}

		}
		

		/* ???????? */
		pcInfo.getEconomyEntries().clear();
		int enonomy_rowCount = kdtEconomy.getRowCount();
		for(int i=0;i<enonomy_rowCount;i++){
			ProgrammingContractEconomyInfo pciInfo = new ProgrammingContractEconomyInfo();
			PaymentTypeInfo currentInfo = (PaymentTypeInfo) kdtEconomy.getCell(i, PAYMENTTYPE).getValue();// ????????
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();//????????
			Object amountObj = kdtEconomy.getCell(i, AMOUNT).getValue();//????????
			Object conditionObj = kdtEconomy.getCell(i, CONDITION).getValue();//????????
			Object paymentDateObj = kdtEconomy.getCell(i, PAYMENTDATE).getValue();//????????
			Object descriptionObj = kdtEconomy.getCell(i, ECONOMY_DES).getValue();// ????
			pciInfo.setPaymentType(currentInfo);// ????????????
			if(!FDCHelper.isEmpty(scaleObj)){
				pciInfo.setScale(new BigDecimal(scaleObj.toString()));// ????????????
			}
			if(!FDCHelper.isEmpty(amountObj)){
				pciInfo.setAmount(new BigDecimal(amountObj.toString()));// ????????????
			}
			pciInfo.setCondition((String) conditionObj);// ????????????
			if(!FDCHelper.isEmpty(paymentDateObj)){
				Date paymentDate = (Date) paymentDateObj;
				pciInfo.setPaymentDate(new Timestamp(paymentDate.getTime()));// ????????????
			}
			pciInfo.setDescription((String) descriptionObj);// ????????????
			pcInfo.getEconomyEntries().add(pciInfo);
		}

		if (directExit) {
			// ??????????????????????
			// verifyIsEmpty();
			// verifyAllData();
		} else {
			FDCMsgBox.showInfo("??????????????????");
		}
		oldPcInfo = pcInfo;
		// destroyWindow();
	}

	/**
	 * ??????????????????????
	 */
	private void updateBalance(ProgrammingContractInfo pcInfo) {
		BigDecimal newAmount = pcInfo.getAmount();// ????????
		if (newAmount == null) {
			newAmount = FDCHelper.ZERO;
		}
		BigDecimal newControlAmount = pcInfo.getControlAmount();// ????????
		if (newControlAmount == null) {
			newControlAmount = FDCHelper.ZERO;
		}
		pcInfo.setControlBalance(newAmount);
		pcInfo.setBalance(oldbalance.add(newAmount.subtract(oldAmount)));
		//????????????????????
		if(!(pcInfo.isIsCiting()||pcInfo.isIsWTCiting())){
			pcInfo.setBudgetAmount(newAmount);
		}else{
			pcInfo.setBudgetAmount(FDCHelper.ZERO);
		}
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param level
	 * @return
	 */
	private String setNameIndent(int level) {
		StringBuffer blank = new StringBuffer("");
		for (int i = level; i > 1; i--) {
			blank.append("        ");
		}
		return blank.toString();
	}

	/**
	 * ??????????????
	 */
	private void verifyIsEmpty() {
		ProgrammingContractInfo head = pcInfo.getParent();
		if (head != null) {
			String longNumber = head.getLongNumber();
			if (txtNumber.getText().equals(longNumber + ".")) {
				FDCMsgBox.showInfo("??????????????????????");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
			for (int i = 0; i < pcCollection.size(); i++) {
				ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
				if (programmingContractInfo.getLongNumber().equals(txtNumber.getText())
						&& !pcInfo.getId().toString().equals(programmingContractInfo.getId().toString())) {
					FDCMsgBox.showInfo("????????????????????????????????");
					txtNumber.requestFocus();
					SysUtil.abort();
				}
			}
		} else {
			if (FDCHelper.isEmpty(txtNumber.getText())) {
				FDCMsgBox.showInfo("??????????????????????");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
		}
		if (FDCHelper.isEmpty(txtName.getText())) {
			FDCMsgBox.showInfo("??????????????????????");
			txtName.requestFocus();
			SysUtil.abort();
		} else {
			for (int i = 0; i < pcCollection.size(); i++) {
				ProgrammingContractInfo rowObject = pcCollection.get(i);
				String name = rowObject.getName();
				if (!FDCHelper.isEmpty(name)) {
					if (txtName.getText().equals(name.trim()) && !pcInfo.getId().toString().equals(rowObject.getId().toString())) {
						FDCMsgBox.showInfo("????????????????????????????????");
						txtName.requestFocus();
						SysUtil.abort();
					}
				}
			}
		}
		// if (this.oprtState.equals(OprtState.ADDNEW)) {
		// isNameDup(txtName.getText(), pcInfo.getId().toString());
		// isNumberDup(txtNumber.getText(), null);
		// } else if (this.oprtState.equals(OprtState.EDIT)) {
		// isNameDup(txtName.getText(), pcInfo.getId().toString());
		// isNumberDup(txtNumber.getText(), pcInfo.getId().toString());
		// }
		// ??????????????????????
		for (int i = 0; i < kdtCost.getRowCount(); i++) {
			IRow row = kdtCost.getRow(i);
			ICell projectCell = row.getCell(PROJECT);
			ICell costAccountCell_number = row.getCell(COSTACCOUNT_NUMBER);
			ICell costAccountCell_name = row.getCell(COSTACCOUNT);
			ICell goalCost = row.getCell(GOALCOST);
			ICell assigned = row.getCell(ASSIGNED);
			ICell assigning = row.getCell(ASSIGNING);
			ICell contractAssign = row.getCell(CONTRACTASSIGN);
			if (FDCHelper.isEmpty(projectCell.getValue())) {
				FDCMsgBox.showInfo("??????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(costAccountCell_number.getValue())) {
				FDCMsgBox.showInfo("??????????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(costAccountCell_name.getValue())) {
				FDCMsgBox.showInfo("??????????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(goalCost.getValue())) {
				FDCMsgBox.showInfo("??????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(assigned.getValue())) {
				FDCMsgBox.showInfo("????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(assigning.getValue())) {
				FDCMsgBox.showInfo("????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(contractAssign.getValue())) {
				FDCMsgBox.showInfo("????????????????????");
				SysUtil.abort();
			}
		}
		// ??????????????????????
		for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
			IRow row = kdtEconomy.getRow(i);
			ICell costAccountCell = row.getCell(PAYMENTTYPE);
			ICell scale = row.getCell(SCALE);
			
			if (FDCHelper.isEmpty(row.getCell(PAYMENTDATE))) {
				FDCMsgBox.showInfo("??????????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(costAccountCell.getValue())) {
				FDCMsgBox.showInfo("??????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(scale.getValue())) {
				FDCMsgBox.showInfo("??????????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(row.getCell(AMOUNT))) {
				FDCMsgBox.showInfo("??????????????????????");
				SysUtil.abort();
			}
		}
	}

	private void verifyAllData() {


		BigDecimal amount = (BigDecimal) txtAmount.getValue();// ????????
		BigDecimal controlAmount = (BigDecimal) txtControlAmount.getValue();// ????????
		if (amount != null) {
			// ????????????????0
			if (amount.compareTo(FDCHelper.ZERO) < 0) {
				FDCMsgBox.showInfo("????????????????0");
				SysUtil.abort();
			}
		}
//		if (controlAmount != null) {
//			// ????????????????0
//			if (controlAmount.compareTo(FDCHelper.ZERO) < 0) {
//				FDCMsgBox.showInfo("????????????????0");
//				SysUtil.abort();
//			}
//		}
		if (amount != null && controlAmount != null) {
			// ????????????????????????
//			if (controlAmount.compareTo(amount) > 0) {
//				FDCMsgBox.showInfo("????????????????????????");
//				SysUtil.abort();
//			}
			// ??????????????????????????????????????
			if (amount.compareTo(getHappenedAmount()) < 0) {
				FDCMsgBox.showInfo("??????????????????????????");
				SysUtil.abort();
			}
		}

		int costRowCount = kdtCost.getRowCount();
		for (int i = 0; i < costRowCount; i++) {
			Object assigningObj = kdtCost.getCell(i, ASSIGNING).getValue();
			Object contractAssignObj = kdtCost.getCell(i, CONTRACTASSIGN).getValue();
			if (assigningObj != null && contractAssignObj != null) {
				BigDecimal assigning = new BigDecimal(assigningObj.toString());
				if(assigning.compareTo(FDCHelper.ZERO)<0) continue;
				
				BigDecimal contractAssign = new BigDecimal(contractAssignObj.toString());
				if (assigning.compareTo(contractAssign) < 0) {
					FDCMsgBox.showInfo("????????????????????????????????");
					SysUtil.abort();
				}
			}
		}

	}

	/**
	 * ??????????
	 * 
	 * @return
	 */
	private BigDecimal getHappenedAmount() {
		BigDecimal happenedAmount = FDCHelper.ZERO;
		if(pcInfo.getSrcId()!=null&&pcInfo.getId()!=null&&!pcInfo.getSrcId().equals(pcInfo.getId().toString())){
			
		}else{
			BigDecimal signUpAmount = pcInfo.getSignUpAmount();// ????????
			BigDecimal changeAmount = pcInfo.getChangeAmount();// ????????
			BigDecimal settleAmount = pcInfo.getSettleAmount();// ????????
			BigDecimal estimateAmount=pcInfo.getEstimateAmount();
			
			if (FDCHelper.isEmpty(signUpAmount)) {
				signUpAmount = new BigDecimal(0);
			}
			if (FDCHelper.isEmpty(changeAmount)) {
				changeAmount = new BigDecimal(0);
			}
			if (FDCHelper.isEmpty(settleAmount)) {
				settleAmount = new BigDecimal(0);
			}
			if (FDCHelper.isEmpty(settleAmount)) {
				settleAmount = new BigDecimal(0);
			}
			if (FDCHelper.isEmpty(estimateAmount)) {
				estimateAmount = new BigDecimal(0);
			}
			if(settleAmount.compareTo(FDCHelper.ZERO)>0){
				happenedAmount=settleAmount;
			}else{
				happenedAmount=signUpAmount.add(changeAmount).add(estimateAmount);
			}
		}
		return happenedAmount;
	}



	/**
	 * ????????????????????
	 */
	protected void kdtCost_tableClicked(KDTMouseEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		// ??????????????????????????
		if (e.getRowIndex() > -1 && !oprtState.equals(OprtState.VIEW) && e.getType() != KDTStyleConstants.HEAD_ROW) {
			btnRemoveLines_cost.setEnabled(true);
		}
		if (e.getColIndex() == kdtCost.getColumnIndex(COSTACCOUNT) && e.getType() != KDTStyleConstants.HEAD_ROW) {
			CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex, PROJECT).getValue();
			if (project == null) {
				FDCMsgBox.showInfo("????????????????");
			} else {
				kdtCost.getColumn(COSTACCOUNT).getStyleAttributes().setLocked(false);
			}
		}
	}

	/**
	 * ????????????????????????
	 */
	protected void kdtCost_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		Object oldValue = e.getOldValue();
		// ????"????????"
		if (colIndex == kdtCost.getColumnIndex(PROJECT)) {
			Object projectObj = kdtCost.getCell(rowIndex, colIndex).getValue();
			if (projectObj != null) {
				CurProjectInfo newProject = (CurProjectInfo) projectObj;
				costAccountCellF7(newProject, rowIndex, kdtCost.getColumnIndex(COSTACCOUNT),this.pcCollection);// ??????????????????????????????????F7
				// ????????????????????????
				if (oldValue != null) {
					CurProjectInfo oldProject = (CurProjectInfo) oldValue;
					if (newProject.getNumber().equals(oldProject.getNumber())) {
						return;
					}
				}

				// ????????????????????????????????????????????????
				AimCostInfo aimCostInfo = null;
				Object aimCostObj = this.getUIContext().get("aimCostInfo");
				if (aimCostObj == null) {
					ProgrammingContractUtil.clearCell(kdtCost, rowIndex, COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED, ASSIGNING,
							CONTRACTASSIGN, COST_DES);
				} else {
					aimCostInfo = (AimCostInfo) aimCostObj;
				}
				/*
				 * ????????????????
				 * 
				 * 1.
				 * 
				 * 2.????????????????????????????
				 * 
				 * 2.1:??????????????????????????????????????
				 * 
				 * 2.2:??????????????????????????????????????????
				 * 
				 * 2.2.1:??????????????????????????????????????
				 * 
				 * 2.2.2:??????
				 * 
				 * 2.2.2.1:??????????????????????????????????????????
				 * 
				 * 2.2.2.2??????"????????"
				 * 
				 * 2.2.2.2.1????????????????????0
				 * 
				 * 2.2.2.2.1.1:??0????"????????","??????","??????","??????????"????0,????????
				 * 
				 * 2.2.2.2.1.2:????0??????"??????","??????","??????????"????
				 * 
				 * ??????????????????????????????????????"????????"??"????????"???? ????????
				 */
				// 1

				Object costAccountObj = kdtCost.getCell(rowIndex, COSTACCOUNT).getValue();
				// 2
				if (costAccountObj == null) {
					// 2.1
					ProgrammingContractUtil.clearCell(kdtCost, rowIndex, COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED, ASSIGNING,
							CONTRACTASSIGN, COST_DES);
				} else {
					// 2.2
					CostAccountInfo costAccount = (CostAccountInfo) costAccountObj;
					String newCostAccountID = ProgrammingContractUtil.isExitCostAccount(newProject, costAccount);
					if (newCostAccountID == null) {
						// 2.2.1
						ProgrammingContractUtil.clearCell(kdtCost, rowIndex, COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED,
								ASSIGNING, CONTRACTASSIGN, COST_DES);
					} else {
						// 2.2.2
						// 2.2.2.1??????????????????????????????????????????
						CostAccountInfo newCostAccountInfo = ProgrammingContractUtil.getCostAccountByNewID(newCostAccountID);
						kdtCost.getCell(rowIndex, COSTACCOUNT).setValue(newCostAccountInfo);
						kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(newCostAccountInfo.getLongNumber().replace('!', '.'));
						// 2.2.2.2??????"????????"
						BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(newCostAccountInfo,
								aimCostInfo);
						// 2.2.2.2.1
						if (goalCost.compareTo(FDCHelper.ZERO) == 0) {
							// 2.2.2.2.1.1
							ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
							afterContractAssignChange();
							afterPlanAmountChange();
						} else {
							// 2.2.2.2.1.2 ????"??????","??????","??????????"????
							BigDecimal allAssigned = FDCHelper.ZERO;// ??????
							// ????"??????" == "????????" - "??????"
							BigDecimal assigning = goalCost.subtract(allAssigned);
							// ????"??????????"="??????"
							BigDecimal contractAssign = assigning;
							// ??????????????
							kdtCost.getCell(rowIndex, GOALCOST).setValue(goalCost);// ????????
							kdtCost.getCell(rowIndex, ASSIGNED).setValue(allAssigned);// ??????
							kdtCost.getCell(rowIndex, ASSIGNING).setValue(assigning);// ??????
							kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(contractAssign);// ??????????

							// ??????????????????????????"????????"
							afterContractAssignChange();
							// ????????"????????"??????????????????????"????????"??"????????"
							// ??????"????????"????????????"????????"
							afterPlanAmountChange();
						}
					}
				}
			} else {
				//????????????????????????????????
				ProgrammingContractUtil.clearCell(kdtCost, rowIndex, PROJECT, COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED,
						ASSIGNING, CONTRACTASSIGN, COST_DES);
				kdtCost.getColumn(COSTACCOUNT).getStyleAttributes().setLocked(true);
			}

		}
		// ????"??????????"
		if (colIndex == kdtCost.getColumnIndex(CONTRACTASSIGN)) {
			afterContractAssignChange();
			
//			ObjectValueRender render_scale = new ObjectValueRender();
//			render_scale.setFormat(new IDataFormat() {
//				public String format(Object o) {
//					String str = o.toString();
//					if (!FDCHelper.isEmpty(str)) {
//						return str + "%";
//					}
//					return str;
//				}
//			});
//			kdtCost.getColumn("scale").setRenderer(render_scale);
			BigDecimal amount=(BigDecimal) kdtCost.getRow(e.getRowIndex()).getCell(CONTRACTASSIGN).getValue();
			BigDecimal goalCost=(BigDecimal) kdtCost.getRow(e.getRowIndex()).getCell(GOALCOST).getValue();
			if(amount!=null&&goalCost!=null&&goalCost.compareTo(FDCHelper.ZERO)!=0){
				amount=goalCost==null?FDCHelper.ZERO:amount.multiply(new BigDecimal(100)).divide(goalCost,2,BigDecimal.ROUND_HALF_UP);
				kdtCost.getRow(e.getRowIndex()).getCell("scale").setValue(amount);
			}else{
				kdtCost.getRow(e.getRowIndex()).getCell("scale").setValue(FDCHelper.ZERO);
			}
		}

		// ????"????????F7"
		if (colIndex == kdtCost.getColumnIndex(COSTACCOUNT)) {
			/*
			 * 1.????????????????
			 * 
			 * 1.1????????????????????
			 * 
			 * 1.1.1????????????????????????????????????????
			 * 
			 * 1.1.2????????????
			 * 
			 * 1.2????????????????????????????????????????,??????????
			 * 
			 * 2.????????????????
			 * 
			 * 2.1??????????"????????","??????","??????","??????????","????"??????
			 * 
			 * 2.2?????????? ????????????????????????????????
			 * 
			 * 2.2.1????????????????????????????????????0???????????? ??
			 * 
			 * 2.2.2????????????????????????????????????????????????????????????????????????????????????
			 * 
			 * 3.????"??????","??????","??????????"????
			 * 
			 * ??????????????????????????????????????"????????"??"????????"???? ????????
			 */
			BigDecimal allAssigned = FDCHelper.ZERO;// "??????"
			CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex, PROJECT).getValue();// ????????
			// 1.
			Object newValue = kdtCost.getCell(rowIndex, COSTACCOUNT).getValue();
			// 1.1
			if(newValue == null){
				// 1.1.1
				ProgrammingContractUtil.clearCell(kdtCost, rowIndex, COSTACCOUNT_NUMBER, COSTACCOUNT, GOALCOST, ASSIGNED, ASSIGNING,
						CONTRACTASSIGN, COST_DES);
			}else{
				// 1.1.2
				CostAccountInfo newCostAccountInfo = (CostAccountInfo) newValue;// ????????
				kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER).setValue(newCostAccountInfo.getLongNumber().replace('!', '.'));
				// 1.2
				if (isCostAccountDup(newCostAccountInfo, project, rowIndex)) {
					return;
				}
				// 2.
				AimCostInfo aimCostInfo = (AimCostInfo) this.getUIContext().get("aimCostInfo");// ????????
				if (aimCostInfo == null) {
					// 2.1
//					ProgrammingContractUtil.clearCell(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
					ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
				} else {
					// 2.2????????????
					if (!isAimCostAudit(aimCostInfo)) {
						// 2.2.1
						ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
					} else {
						// 2.2.2 ????????????????
						// CurProjectInfo project = (CurProjectInfo) kdtCost.getCell(rowIndex, PROJECT).getValue();
						if (project != null) {
							BigDecimal goalCost = ProgrammingContractUtil.getGoalCostBy_costAcc_aimCost(newCostAccountInfo,
									aimCostInfo);
							if (goalCost.compareTo(FDCHelper.ZERO) == 0) {
								ProgrammingContractUtil.setZero(kdtCost, rowIndex, GOALCOST, ASSIGNED, ASSIGNING, CONTRACTASSIGN, COST_DES);
								afterContractAssignChange();
								afterPlanAmountChange();
							} else {
								allAssigned = getAllContractAssign(newCostAccountInfo, false);// ??????
								// ????"??????" == "????????" - "??????"
								BigDecimal assigning = goalCost.subtract(allAssigned);// ??????
								// ????"??????????"="??????"
								BigDecimal contractAssign = assigning;// ??????????
								// ??????????????
								kdtCost.getCell(rowIndex, GOALCOST).setValue(goalCost);// ????????
								kdtCost.getCell(rowIndex, ASSIGNED).setValue(allAssigned);// ??????
								kdtCost.getCell(rowIndex, ASSIGNING).setValue(assigning);// ??????
								kdtCost.getCell(rowIndex, CONTRACTASSIGN).setValue(contractAssign);// ??????????

								// ??????????????????????????"????????"
								afterContractAssignChange();
								// ????????"????????"??????????????????????"????????"??"????????"
								// ??????"????????"????????????"????????"
								afterPlanAmountChange();
							}
						}
					}
				}
			}
		}
		if(colIndex == kdtCost.getColumnIndex("scale")){
			// ???????????????? ????
			ObjectValueRender render_scale = new ObjectValueRender();
			render_scale.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str + "%";
					}
					return str;
				}
			});
			kdtCost.getColumn("scale").setRenderer(render_scale);
			BigDecimal amount=(BigDecimal) kdtCost.getRow(e.getRowIndex()).getCell("scale").getValue();
			BigDecimal goalCost=(BigDecimal) kdtCost.getRow(e.getRowIndex()).getCell(GOALCOST).getValue();
			if(amount!=null){
				amount=goalCost==null?FDCHelper.ZERO:goalCost.multiply(amount).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP);
				kdtCost.getRow(e.getRowIndex()).getCell(CONTRACTASSIGN).setValue(amount);
			}else{
				kdtCost.getRow(e.getRowIndex()).getCell(CONTRACTASSIGN).setValue(FDCHelper.ZERO);
			}
			afterContractAssignChange();
		}
	}

	/**
	 * "??????????"??????????????????????
	 */
	private void afterContractAssignChange() {
		BigDecimal allContractAssign = FDCHelper.ZERO;
		for (int i = 0; i < kdtCost.getRowCount(); i++) {
			Object contractAssignObj = kdtCost.getCell(i, CONTRACTASSIGN).getValue();// ??????????
			if (contractAssignObj == null) {
				allContractAssign = allContractAssign.add(FDCHelper.ZERO);
			} else {
				allContractAssign = allContractAssign.add(new BigDecimal(contractAssignObj.toString()));
			}
		}
		txtAmount.setValue(allContractAssign);
		txtControlAmount.setValue(allContractAssign);
	}

	/**
	 * "????????"????????????????????????????????????????
	 */
	private void afterPlanAmountChange() {
		BigDecimal planAmount = (BigDecimal) txtAmount.getValue();
		for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();
			if (scaleObj != null) {
				BigDecimal scale = new BigDecimal(scaleObj.toString());
				BigDecimal hundren = FDCHelper.ONE_HUNDRED;
				scale = FDCHelper.divide(scale, hundren);
				BigDecimal amount = planAmount.multiply(scale);
				kdtEconomy.getCell(i, AMOUNT).setValue(amount);
			}
		}
	}

	/**
	 * ????????????????????????????????????????????????
	 * 
	 * @param rowIndex
	 */
	private boolean isCostAccountDup(CostAccountInfo currentInfo, CurProjectInfo project, int rowIndex) {
		ICell costAccountNameCell = kdtCost.getCell(rowIndex, COSTACCOUNT);
		ICell costAccountNumberCell = kdtCost.getCell(rowIndex, COSTACCOUNT_NUMBER);
		if (FDCHelper.isEmpty(currentInfo)) {
			return false;
		}
		int rowCount = kdtCost.getRowCount();
		if (!FDCHelper.isEmpty(currentInfo.getLongNumber())) {
			int flag = 0;
			for (int i = 0; i < rowCount; i++) {
				CostAccountInfo forInfo = (CostAccountInfo) kdtCost.getCell(i, COSTACCOUNT).getValue();
				CurProjectInfo forProjectInfo = (CurProjectInfo) kdtCost.getCell(i, PROJECT).getValue();
				if (forInfo == null) {
					break;
				}
				if (!FDCHelper.isEmpty(forInfo.getLongNumber())) {
					if (currentInfo.getLongNumber().equals(forInfo.getLongNumber()) && project.getNumber().equals(forProjectInfo.getNumber())) {
						flag++;
						if (flag >= 2) {
							FDCMsgBox.showInfo("??????????????????????????\"" + currentInfo.getName() + "\"??????????????????????????????????????");
							costAccountNameCell.setValue(null);
							costAccountNumberCell.setValue(null);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * ????????????????????????"??????????"????????
	 * 
	 * @param caInfo
	 * 
	 * @param flag
	 * 
	 *            true??????????????????????"??????????"????
	 * 
	 *            false ??????????????????????????????????"??????????"????
	 * @return
	 */
	private BigDecimal getAllContractAssign(CostAccountInfo caInfo, boolean flag) {
		BigDecimal allContractAssign = FDCHelper.ZERO;
		for (int i = 0; i < pcCollection.size(); i++) {
			ProgrammingContractInfo programmingContractInfo = pcCollection.get(i);
			if (flag) {
				ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
				for (int j = 0; j < costEntries.size(); j++) {
					ProgrammingContracCostInfo pccInfo = costEntries.get(j);
					CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
					if (costAccountInfo != null) {
						if(costAccountInfo.getLongNumber()!=null){
							if (costAccountInfo.getLongNumber().equals(caInfo.getLongNumber())) {
								BigDecimal contractAssign = pccInfo.getContractAssign();
								if (contractAssign == null) {
									contractAssign = FDCHelper.ZERO;
								}
								allContractAssign = allContractAssign.add(contractAssign);
							}
						}
					}
				}
			} else {
				if (!programmingContractInfo.getId().toString().equals(pcInfo.getId().toString())) {
					ProgrammingContracCostCollection costEntries = programmingContractInfo.getCostEntries();
					for (int j = 0; j < costEntries.size(); j++) {
						ProgrammingContracCostInfo pccInfo = costEntries.get(j);
						CostAccountInfo costAccountInfo = pccInfo.getCostAccount();
						if (costAccountInfo != null) {
							if(costAccountInfo.getLongNumber()!=null){
								if (costAccountInfo.getLongNumber().equals(caInfo.getLongNumber())) {
									BigDecimal contractAssign = pccInfo.getContractAssign();
									if (contractAssign == null) {
										contractAssign = FDCHelper.ZERO;
									}
									allContractAssign = allContractAssign.add(contractAssign);
								}
							}
						}
					}
				}
			}
		}
		return allContractAssign;
	}

	/**
	 * ????????????????????
	 */
	protected void kdtEconomy_tableClicked(KDTMouseEvent e) throws Exception {
		// ??????????????????????????
		if (e.getRowIndex() > -1 && !oprtState.equals(OprtState.VIEW) && e.getType() != KDTStyleConstants.HEAD_ROW) {
			btnRemoveLines_economy.setEnabled(true);
		}
	}

	/**
	 * ????????????????????????
	 */
	protected void kdtEconomy_editStopped(KDTEditEvent e) throws Exception {
		int rowIndex = e.getRowIndex();
		int colIndex = e.getColIndex();
		Object oldValue = e.getOldValue();
		/* ???????????? */
		// if (colIndex == kdtEconomy.getColumnIndex(PAYMENTTYPE)) {
		// isEnonomDup();
		// }

		/* ???????????? */
		if (colIndex == kdtEconomy.getColumnIndex(SCALE)) {
			ICell scaleCell = kdtEconomy.getCell(rowIndex, SCALE);
			ICell amountCell = kdtEconomy.getCell(rowIndex, AMOUNT);
			if (scaleCell.getValue() != null && oldValue != null) {
				BigDecimal newScale = FDCHelper.toBigDecimal(scaleCell.getValue().toString());
				BigDecimal oldScale = FDCHelper.toBigDecimal(oldValue.toString());
				if ((newScale).compareTo(oldScale) == 0) {
					return;
				}
			}
			// ???????????????? ????
			ObjectValueRender render_scale = new ObjectValueRender();
			render_scale.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str + "%";
					}
					return str;
				}
			});
			kdtEconomy.getColumn(SCALE).setRenderer(render_scale);

			// ???????????????? ????
			ObjectValueRender render_amount = new ObjectValueRender();
			render_amount.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str;
					}
					return str;
				}
			});
			kdtEconomy.getColumn(AMOUNT).setRenderer(render_amount);

			BigDecimal scale = (BigDecimal) scaleCell.getValue();
			if (!FDCHelper.isEmpty(scale)) {
				// Object newScaleValue = e.getValue();
				Object oldScaleValue = e.getOldValue();
				Object oldAmountValue = kdtEconomy.getCell(rowIndex, AMOUNT).getValue();
				// editStopedCheckIsChange(oldScaleValue, newScaleValue);
				BigDecimal planAmount = txtAmount.getBigDecimalValue();
				if (!FDCHelper.isEmpty(planAmount)) {
					BigDecimal amount = FDCHelper.divide(planAmount.multiply(scale), FDCHelper.ONE_HUNDRED);
					amountCell.setValue(amount.toString());
				}

				// ????
				if (getPayScaleAll().compareTo(new BigDecimal(100)) > 0) {
					FDCMsgBox.showInfo("????????????????????????100%");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				} else if (getPayAmountAll().compareTo(
						txtAmount.getValue() == null ? new BigDecimal(0) : new BigDecimal(txtAmount.getValue().toString())) > 0) {
					FDCMsgBox.showInfo("????????????????????????????????");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				}
			} else {
				amountCell.setValue(null);
			}
		}

		/* ???????????? */
		if (colIndex == kdtEconomy.getColumnIndex(AMOUNT)) {
			ICell scaleCell = kdtEconomy.getCell(rowIndex, SCALE);
			ICell amountCell = kdtEconomy.getCell(rowIndex, AMOUNT);
			if (amountCell.getValue() != null && oldValue != null) {
				BigDecimal newAmount = FDCHelper.toBigDecimal(amountCell.getValue().toString());
				BigDecimal oldAmount = FDCHelper.toBigDecimal(oldValue.toString());
				if ((newAmount).compareTo(oldAmount) == 0) {
					return;
				}
			}
			String amountStr = (String) amountCell.getValue();
			// ???????????????? ????
			ObjectValueRender render_scale = new ObjectValueRender();
			render_scale.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str + "%";
					}
					return str;
				}
			});
			kdtEconomy.getColumn(SCALE).setRenderer(render_scale);

			// ???????????? ????????
			ObjectValueRender render_amount = new ObjectValueRender();
			render_amount.setFormat(new IDataFormat() {
				public String format(Object o) {
					String str = o.toString();
					if (!FDCHelper.isEmpty(str)) {
						return str;
					}
					return str;
				}
			});
			kdtEconomy.getColumn(AMOUNT).setRenderer(render_amount);

			if (!FDCHelper.isEmpty(amountStr)) {
				// Object newAmountValue = e.getValue();
				Object oldAmountValue = e.getOldValue();
				Object oldScaleValue = kdtEconomy.getCell(rowIndex, SCALE).getValue();
				// editStopedCheckIsChange(oldAmountValue, newAmountValue);

				if (amountStr.matches("^\\d*$")) {
					// ????????????????????????????????
					BigDecimal amount = new BigDecimal(amountStr);
					BigDecimal hundrenBig = FDCHelper.ONE_HUNDRED;
					amount = amount.multiply(hundrenBig);
					BigDecimal planAmount = (BigDecimal) txtAmount.getValue();
					if (!FDCHelper.isEmpty(planAmount)) {
						BigDecimal scale = FDCHelper.divide(amount, planAmount, 10, BigDecimal.ROUND_HALF_UP);
						scaleCell.setValue(scale);
					}
				} else {
					// scaleCell.setValue(null);
					amountCell.setValue(null);
					FDCMsgBox.showInfo("????????????");
				}

				// ????
				if (getPayScaleAll().compareTo(FDCHelper.ONE_HUNDRED) > 0) {
					FDCMsgBox.showInfo("??????????????????????????????????????????????100%");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				} else if (getPayAmountAll().compareTo(
						txtAmount.getValue() == null ? new BigDecimal(0) : new BigDecimal(txtAmount.getValue().toString())) > 0) {
					FDCMsgBox.showInfo("????????????????????????????????");
					scaleCell.setValue(oldScaleValue);
					amountCell.setValue(oldAmountValue);
				}
			} else {
				scaleCell.setValue(null);
			}

		}

	}

	/**
	 * ??????????????????????
	 * 
	 * @return true:??????????????; false:??????
	 */
	private boolean isAimCostAudit(AimCostInfo aimCostInfo) {
		if (aimCostInfo != null) {
			FDCBillStateEnum state = aimCostInfo.getState();
			if (state.equals(FDCBillStateEnum.SAVED) || state.equals(FDCBillStateEnum.SUBMITTED)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	/**
	 * ????????????????????????????????
	 * 
	 * @param oldValue
	 * @param newValue
	 */
	private void editStopedCheckIsChange(Object oldValue, Object newValue) {
		if (newValue != null && oldValue != null) {
			if (new BigDecimal(newValue.toString()).compareTo(new BigDecimal(oldValue.toString())) == 0) {
				SysUtil.abort();
			}
		}
	}

	/**
	 * ????????????
	 * 
	 * @return
	 */
	private BigDecimal getPayScaleAll() {
		int rowCount = kdtEconomy.getRowCount();
		BigDecimal scale = new BigDecimal(0);
		for (int i = 0; i < rowCount; i++) {
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();
			if (!FDCHelper.isEmpty(scaleObj)) {
				BigDecimal temp = new BigDecimal(scaleObj.toString());
				scale = scale.add(temp);
			}
		}
		return scale;
	}

	/**
	 * ????????????
	 * 
	 * @return
	 */
	private BigDecimal getPayAmountAll() {
		int rowCount = kdtEconomy.getRowCount();
		BigDecimal amount = new BigDecimal(0);
		for (int i = 0; i < rowCount; i++) {
			Object amountObj = kdtEconomy.getCell(i, AMOUNT).getValue();
			if (!FDCHelper.isEmpty(amountObj)) {
				BigDecimal temp = new BigDecimal(amountObj.toString());
				amount = amount.add(temp);
			}
		}
		return amount;
	}

	/**
	 * ????????????????????????????????????????
	 */
	// protected void kdtEconomy_activeCellChanged(KDTActiveCellEvent e) throws Exception {
	// if (e.getPrevColumnIndex() == kdtEconomy.getColumnIndex(SCALE)) {
	// if (kdtEconomy.getCell(e.getPrevRowIndex(), e.getPrevColumnIndex()).getValue() == null) {
	// kdtEconomy.getCell(e.getPrevRowIndex(), AMOUNT).setValue(null);
	// }
	// }
	// if (e.getPrevColumnIndex() == kdtEconomy.getColumnIndex(AMOUNT)) {
	// if (kdtEconomy.getCell(e.getPrevRowIndex(), e.getPrevColumnIndex()).getValue() == null) {
	// kdtEconomy.getCell(e.getPrevRowIndex(), SCALE).setValue(null);
	// }
	// }
	// }

	/**
	 * ????????????????????
	 * 
	 * @param enonomy_rowCount
	 */
	private void isEnonomDup() {
		int rowIndex = kdtEconomy.getSelectManager().getActiveRowIndex();
		ICell economyCell = kdtEconomy.getCell(rowIndex, PAYMENTTYPE);
		PaymentTypeInfo currentInfo = (PaymentTypeInfo) economyCell.getValue();
		if (FDCHelper.isEmpty(currentInfo)) {
			return;
		}
		int columnCount = kdtEconomy.getRowCount();
		if (!FDCHelper.isEmpty(currentInfo.getName())) {
			int flag = 0;
			for (int i = 0; i < columnCount; i++) {
				PaymentTypeInfo forInfo = (PaymentTypeInfo) kdtEconomy.getCell(i, PAYMENTTYPE).getValue();
				if (forInfo == null) {
					break;
				}
				if (!FDCHelper.isEmpty(forInfo.getName())) {
					if (currentInfo.getName().equals(forInfo.getName())) {
						flag++;
						if (flag >= 2) {
							FDCMsgBox.showInfo("??????????????????????????\"" + currentInfo.getName() + "\"??????????????????????????????????????");
							economyCell.setValue(null);
							SysUtil.abort();
						}
					}
				}
			}
		}
	}

	private void preparePCData() {
		// ??????????
		if (pcInfo.getParent() != null) {
			String longName = pcInfo.getDisplayName();
			if(longName!=null){
				String headName = longName.substring(0, longName.lastIndexOf('.'));
				this.txtParentLongName.setText(headName);// ??????????????????
			}
		}
		txtNumber.setText(pcInfo.getLongNumber());// ??????????????
		txtName.setText(pcInfo.getName() == null ? null : pcInfo.getName().trim());// ????
		txtAmount.setValue(pcInfo.getAmount());// ????????
		txtControlAmount.setValue(pcInfo.getControlAmount());// ????????
		kdcInviteWay.setSelectedItem(pcInfo.getInviteWay());// ????????
		txtEstimateAmount.setValue(pcInfo.getEstimateAmount());// ????????
		if(this.oprtState.equals(OprtState.VIEW)){
			if(pcInfo.getInviteOrg()!=null){
				CostCenterOrgUnitInfo orgUnitInfo = null;
				try {
					orgUnitInfo = CostCenterOrgUnitFactory.getRemoteInstance().getCostCenterOrgUnitInfo(new ObjectUuidPK(pcInfo.getInviteOrg().getId()));
				} catch (EASBizException e) {
					e.printStackTrace();
				} catch (BOSException e) {
					e.printStackTrace();
				}
				prmtInviteOrg.setData(orgUnitInfo);
			}
		}else{
			prmtInviteOrg.setData(pcInfo.getInviteOrg());// ????????
		}
		txtWorkContent.setText(pcInfo.getWorkContent());// ????????
		txtSupMaterial.setText(pcInfo.getSupMaterial());// ??????????????		
		txtDescription.setText(pcInfo.getDescription());// ????
		txtDescription.setToolTipText(pcInfo.getDescription());
		txtAttachment.setText(getAllAttachmentName(pcInfo.getId().toString()).toString());// ????

		ObjectValueRender render_scale = new ObjectValueRender();
		render_scale.setFormat(new IDataFormat() {
			public String format(Object o) {
				String str = o.toString();
				if (!FDCHelper.isEmpty(str)) {
					return str + "%";
				}
				return str;
			}
		});
		kdtEconomy.getColumn(SCALE).setRenderer(render_scale);

		// ????????
		addCostLine(kdtCost);
		addEconomyLine(kdtEconomy);

		int level = pcInfo.getLevel();
		if (level > 1) {
			String longNumber = pcInfo.getLongNumber();
			if (!FDCHelper.isEmpty(longNumber)) {
				LimitedTextDocument document = new LimitedTextDocument(longNumber);
				String text = txtNumber.getText();
				txtNumber.setDocument(document);
				document.setIsAutoUpdate(true);
				document.setIsOnload(true);
				txtNumber.setText(text);
				document.setIsAutoUpdate(false);
				document.setIsOnload(false);
			}
		}
		if (level == 1) {
			LimitedTextDocument document = new LimitedTextDocument("");
			String text = txtNumber.getText();
			txtNumber.setDocument(document);
			document.setIsAutoUpdate(true);
			document.setIsOnload(true);
			txtNumber.setText(text);
			document.setIsAutoUpdate(false);
			document.setIsOnload(false);
		}
	}

	/**
	 * ??????????????????????????????
	 * 
	 * @param table
	 */
	private void addCostLine(KDTable table) {
		projectF7();
		
		if (pcInfo.getCostEntries().size() > 0) {
			sortCollection(pcInfo.getCostEntries(), "costAccount.longNumber", true);
			IRow row;
			for (int i = 0; i < pcInfo.getCostEntries().size(); i++) {
				ProgrammingContracCostInfo pccInfo = pcInfo.getCostEntries().get(i);
				row = table.addRow();
				if (pccInfo.getId() == null) {
					pccInfo.setId(BOSUuid.create(pccInfo.getBOSType()));
				}
				row.getCell(COST_ID).setValue(pccInfo.getId());
				CurProjectInfo project = null;
				if (pccInfo.getCostAccount() != null) {
					project = pccInfo.getCostAccount().getCurProject();
				}
				row.getCell(PROJECT).setValue(project);
				costAccountCellF7(project, i, kdtCost.getColumnIndex(COSTACCOUNT),this.pcCollection);// ??????????????????????F7????????
				row.getCell(COSTACCOUNT_NUMBER).setValue(pccInfo.getCostAccount().getLongNumber().replace('!', '.'));
				row.getCell(COSTACCOUNT).setValue(pccInfo.getCostAccount());
				row.getCell(GOALCOST).setValue(pccInfo.getGoalCost());
				row.getCell(ASSIGNED).setValue(pccInfo.getAssigned());
				row.getCell(ASSIGNING).setValue(pccInfo.getAssigning());
				row.getCell(CONTRACTASSIGN).setValue(pccInfo.getContractAssign());
				row.getCell(COST_DES).setValue(pccInfo.getDescription());
			}
		}
	}
	
	/**
	 * ??????????????????????,????????????????Comparable??????.
	 * @param cols ????????????
	 * @param sortColName ????????????
	 * @param sortType ????????
	 * */
	public  void sortCollection(IObjectCollection cols, final String sortColName, final boolean sortType) {
		Object[] toSortData = cols.toArray();
		
		Arrays.sort(toSortData, new Comparator(){
			public int compare(Object arg0, Object arg1) {
				IObjectValue obj0 = (IObjectValue)arg0;
				IObjectValue obj1 = (IObjectValue)arg1;
				if(obj0 == null  ||  obj1 == null){
					return 0;
				}
				
				Comparable tmp0 = (Comparable)getValue(obj0,sortColName);
				Comparable tmp1 = (Comparable)getValue(obj1,sortColName);
				if(tmp0 == null  ||  tmp1 == null){
					return 0;
				}
				
				return sortType ? tmp0.compareTo(tmp1) : -tmp0.compareTo(tmp1);
			}
		});
		
		cols.clear();
		for(int j=0; j<toSortData.length; j++){
			cols.addObject((IObjectValue) toSortData[j]);
		}
	}
	
	public  Object getValue(IObjectValue value, String key){
		int in = key.indexOf(".");
		if(in == -1){
			return value.get(key);
		}else{
			Object tmp = value.get(key.substring(0, in));
			if(tmp != null  &&  tmp instanceof IObjectValue){
				return getValue((IObjectValue) tmp, key.substring(in + 1, key.length()));
			}
		}
		return null;
	}
	/**
	 * ??????????????????????????????
	 * 
	 * @param table
	 */
	private void addEconomyLine(KDTable table) {
		ProgrammingContractEconomyCollection pcEnonomyCollection = pcInfo.getEconomyEntries();
		IRow row;
		for (int i = 0; i < pcEnonomyCollection.size(); i++) {
			ProgrammingContractEconomyInfo pcEnonomyInfo = pcEnonomyCollection.get(i);
			row = table.addRow();
			if (pcEnonomyInfo.getId() == null) {
				pcEnonomyInfo.setId(BOSUuid.create(pcEnonomyInfo.getBOSType()));
			}
			row.getCell(ECONOMY_ID).setValue(pcEnonomyInfo.getId());
			row.getCell(PAYMENTTYPE).setValue(pcEnonomyInfo.getPaymentType());
			if (pcEnonomyInfo.getScale() != null) {
				row.getCell(SCALE).setValue(pcEnonomyInfo.getScale());
			}
			row.getCell(AMOUNT).setValue(pcEnonomyInfo.getAmount());
			row.getCell(CONDITION).setValue(pcEnonomyInfo.getCondition());
			row.getCell(PAYMENTDATE).setValue(pcEnonomyInfo.getPaymentDate());
			row.getCell(ECONOMY_DES).setValue(pcEnonomyInfo.getDescription());
		}
		paymentTypeF7();
		paymentDate();
	}

	/**
	 * ????????ID????????????????????
	 * 
	 * @param pteID
	 * @return
	 */
	private ProgrammingContractEconomyCollection getPCEconomy(String pcID) {
		ProgrammingContractEconomyInfo pcEnonomyInfo = null;
		PaymentTypeInfo paymentInfo = null;
		ProgrammingContractEconomyCollection pceCollection = new ProgrammingContractEconomyCollection();
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select FID,FPAYMENTTYPEID  from T_CON_ProgContEconomy ");
		fdcBuilder.appendSql(" where FContractID = '" + pcID + "'");
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			for (int i = 0; i < rs.size(); i++) {
				if (rs.next()) {
					pcEnonomyInfo = ProgrammingContractEconomyFactory.getRemoteInstance().getProgrammingContractEconomyInfo(
							new ObjectUuidPK(rs.getString("FID")));
					paymentInfo = PaymentTypeFactory.getRemoteInstance().getPaymentTypeInfo(
							new ObjectUuidPK(rs.getString("FPAYMENTTYPEID")));
					pcEnonomyInfo.setPaymentType(paymentInfo);
					pceCollection.add(pcEnonomyInfo);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return pceCollection;
	}

	/**
	 * ??????????????????????
	 * 
	 * @param name
	 */
	private void isNameDup(String name, String id) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_CON_ProgrammingContract where FName_l2 = '" + name + "' ");
		fdcBuilder.appendSql(" and FID <> '" + id + "' ");
		try {
			IRowSet iRowSet = fdcBuilder.executeQuery();
			if (iRowSet.next()) {
				FDCMsgBox.showInfo("??????????????????????????????");
				txtName.requestFocus();
				SysUtil.abort();
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * ??????????????????????
	 * 
	 * @param number
	 */
	private void isNumberDup(String longNumber, String id) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_CON_ProgrammingContract where FLongNumber = '" + longNumber + "' ");
		fdcBuilder.appendSql(" and FID <> '" + id + "' ");
		try {
			IRowSet iRowSet = fdcBuilder.executeQuery();
			if (iRowSet.next()) {
				FDCMsgBox.showInfo("??????????????????????????????");
				txtNumber.requestFocus();
				SysUtil.abort();
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}

	// ????????????????????????????????????????????????id
	private String attachMentTempID = null;

	/**
	 * ????????
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		boolean isEdit = false;// ??????????????
		if (OprtState.EDIT.equals(getOprtState()) || OprtState.ADDNEW.equals(getOprtState())) {
			isEdit = true;
		}
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		AttachmentUIContextInfo info = getAttacheInfo();
		if (info == null) {
			info = new AttachmentUIContextInfo();
		}
		if (FDCHelper.isEmpty(info.getBoID())) {
			String boID = getSelectBOID();
			if (boID == null) {
				if (!isEdit) {
					if (attachMentTempID == null) {
						boID = acm.getAttID().toString();
						attachMentTempID = boID;
					} else {
						boID = attachMentTempID;
					}
				} else {
					return;
				}
			}
			info.setBoID(boID);
			acm.showAttachmentListUIByBoID(boID, this, isEdit);
		}
		info.setEdit(isEdit);
		if (getSelectBOID() != null) {
			StringBuffer allAttachmentName = getAllAttachmentName(getSelectBOID());
			if (!FDCHelper.isEmpty(allAttachmentName.toString())) {
				pcInfo.setAttachment(allAttachmentName.toString());
				this.txtAttachment.setText(allAttachmentName.toString());
				this.txtAttachment.setToolTipText(allAttachmentName.toString());
			} else {
				pcInfo.setAttachment(null);
				this.txtAttachment.setText(null);
			}
		}
	}

	protected AttachmentUIContextInfo getAttacheInfo() {
		return null;
	}

	protected final String getSelectBOID() {
		if (pcInfo == null)
			return null;
		String boID = pcInfo.getId() != null ? pcInfo.getId().toString() : null;
		return boID;
	}

	/**
	 * ????????????????????????????????????????????";"????
	 * 
	 * @param boID
	 * @return
	 */
	private StringBuffer getAllAttachmentName(String boID) {
		FDCSQLBuilder fdcBuilder = new FDCSQLBuilder();
		fdcBuilder.appendSql(" select * from T_BAS_Attachment at");
		fdcBuilder.appendSql(" join T_BAS_BoAttchAsso boAt on at.FID=boAt.FAttachmentID");
		fdcBuilder.appendSql(" where boAt.FBoID = '" + boID + "'");
		System.out.println("sql:" + fdcBuilder.getSql().toString());
		StringBuffer sb = new StringBuffer();
		IRowSet rs = null;
		try {
			rs = fdcBuilder.executeQuery();
			while (rs.next()) {
				if (rs.isLast()) {
					sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName"));
				} else {
					sb.append(rs.getString("FName_l2") + "." + rs.getString("FSimpleName") + ",");
				}
			}
		} catch (BOSException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return sb;
	}

	/**
	 * ????????F7
	 * 
	 */
	private void projectF7() {
		KDBizPromptBox kdtEconomyEntriese_costAccount_PromptBox = new KDBizPromptBox();
		kdtEconomyEntriese_costAccount_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7ProjectQuery");
		kdtEconomyEntriese_costAccount_PromptBox.setVisible(true);
		kdtEconomyEntriese_costAccount_PromptBox.setEditable(true);
		kdtEconomyEntriese_costAccount_PromptBox.setDisplayFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setEditFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtEconomyEntriese_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEconomyEntriese_costAccount_PromptBox);
		this.kdtCost.getColumn(PROJECT).setEditor(kdtEconomyEntriese_costAccount_CellEditor);
		ObjectValueRender kdtCostEntries_paymentType_OVR = new ObjectValueRender();
		kdtCostEntries_paymentType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtCost.getColumn(PROJECT).setRenderer(kdtCostEntries_paymentType_OVR);
	}

	/**
	 * ????????F7 ????????????????
	 */
	private void costAccountF7(CurProjectInfo project,ProgrammingContractCollection pcCol) {
		CostAccountPromptBox selector = new CostAccountPromptBox(this,pcCol,(AimCostInfo) this.getUIContext().get("aimCostInfo"));
		KDBizPromptBox prmtCostAccount = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				String str = null;
				if (o != null && o instanceof CostAccountInfo) {
					str = ((CostAccountInfo) o).getLongNumber().replace('!', '.');
				}
				return str;
			}
		};
		prmtCostAccount.setSelector(selector);
		prmtCostAccount.setEnabledMultiSelection(false);
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");
		prmtCostAccount.setCommitFormat("$longNumber$");

		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		// filter.getFilterItems().add(new FilterItemInfo("fullOrgUnit.id",OrgConstants.DEF_CU_ID,CompareType.EQUALS));
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
		entityView.setFilter(filter);
		prmtCostAccount.setEntityViewInfo(entityView);
		kdtCost.getColumn(COSTACCOUNT).setEditor(caEditor);
		ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
		kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtCost.getColumn(COSTACCOUNT).setRenderer(kdtCostEntries_costAccount_OVR);
	}

	/**
	 * ????????F7????????????
	 */
	private void costAccountCellF7(CurProjectInfo project,int rowIndex,int colIndex,ProgrammingContractCollection pcCol) {
		CostAccountPromptBox selector = new CostAccountPromptBox(this,pcCol,(AimCostInfo) this.getUIContext().get("aimCostInfo"));
		KDBizPromptBox prmtCostAccount = new KDBizPromptBox() {
			protected String valueToString(Object o) {
				String str = null;
				if (o != null && o instanceof CostAccountInfo) {
					str = ((CostAccountInfo) o).getLongNumber().replace('!', '.');
				}
				return str;
			}
		};
		prmtCostAccount.setSelector(selector);
		prmtCostAccount.setEnabledMultiSelection(false);
		prmtCostAccount.setDisplayFormat("$longNumber$");
		prmtCostAccount.setEditFormat("$longNumber$");
		prmtCostAccount.setCommitFormat("$longNumber$");

		KDTDefaultCellEditor caEditor = new KDTDefaultCellEditor(prmtCostAccount);
		EntityViewInfo entityView = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		if (project != null) {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString(), CompareType.EQUALS));
		} else {
			filter.getFilterItems().add(new FilterItemInfo("curProject.id", "error", CompareType.EQUALS));
		}
		entityView.setFilter(filter);
		prmtCostAccount.setEntityViewInfo(entityView);
		caEditor.setValue(prmtCostAccount);
		kdtCost.getCell(rowIndex, colIndex).setEditor(caEditor);
		ObjectValueRender kdtCostEntries_costAccount_OVR = new ObjectValueRender();
		kdtCostEntries_costAccount_OVR.setFormat(new BizDataFormat("$name$"));
		kdtCost.getCell(rowIndex, colIndex).setRenderer(kdtCostEntries_costAccount_OVR);
	}

	/**
	 * ????????F7
	 */
	private void paymentTypeF7() {
		KDBizPromptBox kdtEconomyEntriese_costAccount_PromptBox = new KDBizPromptBox();
		kdtEconomyEntriese_costAccount_PromptBox.setQueryInfo("com.kingdee.eas.fdc.basedata.app.F7PaymentTypeQuery");
		kdtEconomyEntriese_costAccount_PromptBox.setVisible(true);
		kdtEconomyEntriese_costAccount_PromptBox.setEditable(true);
		kdtEconomyEntriese_costAccount_PromptBox.setDisplayFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setEditFormat("$number$");
		kdtEconomyEntriese_costAccount_PromptBox.setCommitFormat("$number$");
		KDTDefaultCellEditor kdtEconomyEntriese_costAccount_CellEditor = new KDTDefaultCellEditor(kdtEconomyEntriese_costAccount_PromptBox);
		this.kdtEconomy.getColumn(PAYMENTTYPE).setEditor(kdtEconomyEntriese_costAccount_CellEditor);
		ObjectValueRender kdtCostEntries_paymentType_OVR = new ObjectValueRender();
		kdtCostEntries_paymentType_OVR.setFormat(new BizDataFormat("$name$"));
		this.kdtEconomy.getColumn(PAYMENTTYPE).setRenderer(kdtCostEntries_paymentType_OVR);
	}

	/**
	 * ????????
	 */
	private void paymentDate() {
		KDDatePicker kdDataPicker = new KDDatePicker();
		KDTDefaultCellEditor cellEditor = new KDTDefaultCellEditor(kdDataPicker);
		kdtEconomy.getColumn(PAYMENTDATE).setEditor(cellEditor);
		ObjectValueRender ovr = new ObjectValueRender();
		kdtEconomy.getColumn(PAYMENTDATE).setRenderer(ovr);
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		if (MsgBox.showConfirm2("??????????????") == MsgBox.CANCEL) {
			return;
		}
		for (int i = 0; i < kdtEconomy.getRowCount(); i++) {
			IRow row = kdtEconomy.getRow(i);
			ICell costAccountCell = row.getCell(PAYMENTTYPE);
			ICell scale = row.getCell(SCALE);
			
			if (FDCHelper.isEmpty(row.getCell(PAYMENTDATE))) {
				FDCMsgBox.showInfo("??????????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(costAccountCell.getValue())) {
				FDCMsgBox.showInfo("??????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(scale.getValue())) {
				FDCMsgBox.showInfo("??????????????????????");
				SysUtil.abort();
			}
			if (FDCHelper.isEmpty(row.getCell(AMOUNT))) {
				FDCMsgBox.showInfo("??????????????????????");
				SysUtil.abort();
			}
		}
		pcInfo.getEconomyEntries().clear();
		int enonomy_rowCount = kdtEconomy.getRowCount();
		for(int i=0;i<enonomy_rowCount;i++){
			ProgrammingContractEconomyInfo pciInfo = new ProgrammingContractEconomyInfo();
			PaymentTypeInfo currentInfo = (PaymentTypeInfo) kdtEconomy.getCell(i, PAYMENTTYPE).getValue();// ????????
			Object scaleObj = kdtEconomy.getCell(i, SCALE).getValue();//????????
			Object amountObj = kdtEconomy.getCell(i, AMOUNT).getValue();//????????
			Object conditionObj = kdtEconomy.getCell(i, CONDITION).getValue();//????????
			Object paymentDateObj = kdtEconomy.getCell(i, PAYMENTDATE).getValue();//????????
			Object descriptionObj = kdtEconomy.getCell(i, ECONOMY_DES).getValue();// ????
			pciInfo.setPaymentType(currentInfo);// ????????????
			if(!FDCHelper.isEmpty(scaleObj)){
				pciInfo.setScale(new BigDecimal(scaleObj.toString()));// ????????????
			}
			if(!FDCHelper.isEmpty(amountObj)){
				pciInfo.setAmount(new BigDecimal(amountObj.toString()));// ????????????
			}
			pciInfo.setCondition((String) conditionObj);// ????????????
			if(!FDCHelper.isEmpty(paymentDateObj)){
				Date paymentDate = (Date) paymentDateObj;
				pciInfo.setPaymentDate(new Timestamp(paymentDate.getTime()));// ????????????
			}
			pciInfo.setDescription((String) descriptionObj);// ????????????
			pcInfo.getEconomyEntries().add(pciInfo);
		}
		ProgrammingContractFactory.getRemoteInstance().submit(pcInfo);
	}
}