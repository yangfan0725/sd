/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryFactory;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AimProductTypeGetter;
import com.kingdee.eas.fdc.basedata.ApportionTypeCollection;
import com.kingdee.eas.fdc.basedata.ApportionTypeFactory;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataCollection;
import com.kingdee.eas.fdc.basedata.CurProjProEntrApporDataInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.client.FDCColorConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class AIMAimCostProductSplitSettingUI extends
		AbstractAIMAimCostProductSplitSettingUI {
	private ApportionTypeCollection apportionColl = new ApportionTypeCollection();
	private ProjectStageEnum projectStage = ProjectStageEnum.AIMCOST;
	private Map splitMap = new HashMap();

	String projectId = null;

	/**
	 * output class constructor
	 */
	public AIMAimCostProductSplitSettingUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.actionSplit.setEnabled(true);
		projectId = (String) getUIContext().get("projectId");
		AIMAimCostProductSplitEntryCollection splits = (AIMAimCostProductSplitEntryCollection) getUIContext()
				.get("splits");
		for (int i = 0; i < splits.size(); i++) {
			AIMAimCostProductSplitEntryInfo info = splits.get(i);
			splitMap.put(info.getProduct().getId().toString(), info);
		}
		initTable();
		this.actionSelectAll.setEnabled(true);
		this.actionSelectNone.setEnabled(true);
		this.actionRemove.setVisible(false);
	}

	public void actionSplit_actionPerformed(ActionEvent e) throws Exception {
		boolean isSelected = false;
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			if (kdtEntrys.getCell(i, 1).getValue().equals(Boolean.TRUE)) {
				isSelected = true;
				break;
			}
		}
		if (!isSelected) {
			MsgBox.showWarning(this, AimCostHandler
					.getResource("NoSelectObject"));
			return;
		}
		this.getUIContext().put("isOK", Boolean.TRUE);
		getUIContext().put("splits", this.getSelectSetting());
		disposeUIWindow();
	}

	public void initTable() throws BOSException {
		kdtEntrys.checkParsed();
		kdtEntrys.setActiveCellStatus(KDTStyleConstants.ACTIVE_CELL_EDIT);
//		kdtEntrys.getColumn(3).getStyleAttributes().setNumberFormat(
//				FDCHelper.getNumberFtm(2));
//		kdtEntrys.getColumn(3).getStyleAttributes().setHorizontalAlign(
//				HorizontalAlignment.RIGHT);
		kdtEntrys.getColumn(1).getStyleAttributes().setLocked(true);

		KDFormattedTextField formattedTextField = new KDFormattedTextField(
				KDFormattedTextField.DECIMAL);
		formattedTextField.setPrecision(2);
		formattedTextField.setSupportedEmpty(true);
		ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
		kdtEntrys.getColumn(3).setEditor(numberEditor);
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("forCostApportion", Boolean.TRUE));
		/**
		 * 		filter.getFilterItems().add(
				new FilterItemInfo("id", ApportionTypeInfo.appointType,CompareType.NOTEQUALS));
		 */

		filter.mergeFilter(ApportionTypeInfo.getCUFilter(SysContext.getSysContext().getCurrentCtrlUnit()), "and");
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add("name");
		view.getSorter().add(new SorterItemInfo("number"));
		apportionColl = ApportionTypeFactory.getRemoteInstance()
				.getApportionTypeCollection(view);
		IRow selectRow = kdtEntrys.addRow();
		selectRow.getStyleAttributes().setLocked(true);
		// selectRow.getStyleAttributes().setBackground(new Color(0xFFFFFF));
		for (Iterator iter = apportionColl.iterator(); iter.hasNext();) {
			ApportionTypeInfo apportion = (ApportionTypeInfo) iter.next();
			if (!apportion.getId().toString().equals(
					ApportionTypeInfo.aimCostType)) {
				IColumn col = kdtEntrys.addColumn(kdtEntrys.getColumnCount());
				String key = apportion.getId().toString();
				col.setKey(key);
				col.getStyleAttributes().setLocked(true);
				kdtEntrys.getHeadRow(0).getCell(col.getColumnIndex()).setValue(
						apportion.getName());
				// ??????
				ICell cell = selectRow.getCell(key);
				cell.setValue(Boolean.FALSE);
				cell.getStyleAttributes().setBackground(new Color(0xFFFFFF));
			}
		}
		if (kdtEntrys.getColumnCount() > 4) {
			selectRow.getCell(4).setValue(Boolean.TRUE);
		}
		Collection collection = splitMap.values();
		Iterator iterator = collection.iterator();
		if (iterator.hasNext()) {
			AIMAimCostProductSplitEntryInfo info = (AIMAimCostProductSplitEntryInfo) iterator
					.next();
			if (info.getApportionType() != null) {
				kdtEntrys.getRow(0).getCell(4).setValue(Boolean.FALSE);
				kdtEntrys.getRow(0).getCell(
						info.getApportionType().getId().toString()).setValue(
						Boolean.TRUE);
				/**
				 * ????????????????????????????????????????????????
				 */
				if(ApportionTypeInfo.appointType.equals(
						info.getApportionType().getId().toString())){
					kdtEntrys.getColumn("directAmount").getStyleAttributes().
						setLocked(true);
					kdtEntrys.getColumn(ApportionTypeInfo.appointType).
						getStyleAttributes().setLocked(true);
					kdtEntrys.getColumn(ApportionTypeInfo.appointType).
						getStyleAttributes().setBackground(FDCColorConstants.cantEditColor);
				}
			}
		}

		AimProductTypeGetter getter = new AimProductTypeGetter(projectId,projectStage);
		Map prodcutMap = getter.getSortedProductMap();
		Set set = prodcutMap.keySet();
		for (Iterator pIter = set.iterator(); pIter.hasNext();) {
			ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(pIter
					.next());
			IRow row = kdtEntrys.addRow();
			String productId = product.getId().toString();
			row.setUserObject(product);
			row.getCell(0).setValue(productId);
			row.getCell(1).setValue(Boolean.FALSE);
			row.getCell(2).setValue(product.getName());
			AIMAimCostProductSplitEntryInfo info = (AIMAimCostProductSplitEntryInfo) splitMap
					.get(productId);
			if (info != null) {
				if (info.getDescription() != null) {
					row.getCell(1).setValue(Boolean.TRUE);
				}
				if (info.getDirectAmount() != null
						&& info.getDirectAmount().compareTo(FDCHelper.ZERO) != 0) {
					row.getCell(3).setValue(info.getDirectAmount());
				}
			}
			CurProjProEntrApporDataCollection appts = getter.getAppors(product
					.getId().toString());
			for (Iterator aIter = appts.iterator(); aIter.hasNext();) {
				CurProjProEntrApporDataInfo apptData = (CurProjProEntrApporDataInfo) aIter
						.next();
				ICell cell = row.getCell(apptData.getApportionType().getId()
						.toString());
				if (cell == null) {
					continue;
				}
				BigDecimal amount = apptData.getValue();
				if (amount == null) {
					amount = FDCHelper.ZERO;
				}
				cell.setValue(amount);
				cell.getStyleAttributes().setNumberFormat(
						FDCHelper.getNumberFtm(2));
				cell.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
			}
		}
	}

	private AIMAimCostProductSplitEntryCollection getSelectSetting() {
		AIMAimCostProductSplitEntryCollection entrys = new AIMAimCostProductSplitEntryCollection();
		ApportionTypeInfo apportion = getSelectApportion();
		// ????????
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			IRow row = kdtEntrys.getRow(i);
			AIMAimCostProductSplitEntryInfo entry = new AIMAimCostProductSplitEntryInfo();
			entry.setProduct((ProductTypeInfo) row.getUserObject());
			entry.setApportionType(apportion);
			if (row.getCell(1).getValue().equals(Boolean.TRUE)) {
				BigDecimal directAmount = (BigDecimal) row.getCell(3)
						.getValue();
				if (directAmount == null) {
					directAmount = FDCHelper.ZERO;
				}
				entry.setDirectAmount(directAmount);
				entry.setDescription("isChoose");
				entrys.add(entry);
			} else {
				if (row.getCell(3).getValue() != null) {
					BigDecimal directAmount = (BigDecimal) row.getCell(3)
							.getValue();
					entry.setDirectAmount(directAmount);
					entry.setApportionType(apportion);
					entrys.add(entry);
				}
			}
		}
		return entrys;

	}

	private ApportionTypeInfo getSelectApportion() {
		String apptId = null;
		for (int i = 4; i < kdtEntrys.getColumnCount(); i++) {
			if (kdtEntrys.getRow(0).getCell(i).getValue().equals(Boolean.TRUE)) {
				apptId = kdtEntrys.getColumnKey(i);
				break;
			}
		}
		for (Iterator iter = apportionColl.iterator(); iter.hasNext();) {
			ApportionTypeInfo apptType = (ApportionTypeInfo) iter.next();
			if (apptType.getId().toString().equals(apptId)) {
				return apptType;
			}
		}
		return null;
	}

	protected IObjectValue createNewData() {
		// TODO ????????????????
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO ????????????????
		return AIMAimCostProductSplitEntryFactory.getRemoteInstance();
	}

	/*
	 * ???? Javadoc??
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractCostSplitApportionUI#kdtEntrys_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
	 */
	protected void kdtEntrys_tableClicked(KDTMouseEvent e) throws Exception {
		// TODO ????????????????
		super.kdtEntrys_tableClicked(e);
//		Boolean isAppointType = Boolean.FALSE;
		
		if (e.getRowIndex() == 0) {
			int idx = e.getColIndex();
//			isAppointType = (Boolean)kdtEntrys.getCell(0, idx).getValue();
			if(kdtEntrys.getColumnIndex(ApportionTypeInfo.appointType) != 
						e.getColIndex()){
				if (idx >= 4 && idx < kdtEntrys.getColumnCount()) {
					IRow row = kdtEntrys.getRow(0);
					for (int i = 4; i < kdtEntrys.getColumnCount(); i++) {
						row.getCell(i).setValue(Boolean.FALSE);
					}
						row.getCell(idx).setValue(Boolean.TRUE);
				}
			}
			if(Boolean.TRUE.equals(
					kdtEntrys.getCell(0, ApportionTypeInfo.appointType))){
				kdtEntrys.getColumn("directAmount").getStyleAttributes().setLocked(true);
			}else{
				kdtEntrys.getColumn("directAmount").getStyleAttributes().setLocked(false);
			}
		}
		if (e.getColIndex() == 1 && e.getRowIndex() != 0) {
			Boolean b = (Boolean) kdtEntrys.getRow(e.getRowIndex()).getCell(1)
					.getValue();
			//find bugs DM?????? Boolean??????Boolean.valueOf
			kdtEntrys.getRow(e.getRowIndex()).getCell(1).setValue(
					Boolean.valueOf(!b.booleanValue()));
		}
	}

	public void actionSelectAll_actionPerformed(ActionEvent e) throws Exception {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			kdtEntrys.getCell(i, 1).setValue(Boolean.TRUE);
		}
	}

	/*
	 * ???? Javadoc??
	 * 
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractCostSplitApportionUI#actionSelNone_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionSelectNone_actionPerformed(ActionEvent e)
			throws Exception {
		for (int i = 1; i < kdtEntrys.getRowCount(); i++) {
			kdtEntrys.getCell(i, 1).setValue(Boolean.FALSE);
		}
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSplit.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.btnSelectNone.setIcon(EASResource.getIcon("imgTbtn_deleteall"));
		this.btnSelectAll.setIcon(EASResource.getIcon("imgTbtn_selectall"));
	}
}