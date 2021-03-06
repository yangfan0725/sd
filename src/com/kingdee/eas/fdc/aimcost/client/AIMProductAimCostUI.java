/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.Action;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.permission.client.longtime.LongTimeDialog;
import com.kingdee.eas.base.permission.client.util.UITools;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.client.OrgViewUtils;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryCollection;
import com.kingdee.eas.fdc.aimcost.AIMAimCostProductSplitEntryInfo;
import com.kingdee.eas.fdc.aimcost.AIMAimCostSplitDataGetter;
import com.kingdee.eas.fdc.aimcost.AimProductTypeGetter;
import com.kingdee.eas.fdc.aimcost.CostEntryCollection;
import com.kingdee.eas.fdc.aimcost.CostEntryInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.ProductTypeCollection;
import com.kingdee.eas.fdc.basedata.ProductTypeInfo;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.TimeTools;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ??????????????????????????
 * 
 * ????????????????????ProductDynamicCostUI
 */
public class AIMProductAimCostUI extends AbstractAIMProductAimCostUI
{
	private CostCenterOrgUnitInfo currentOrg = SysContext.getSysContext()
			.getCurrentCostUnit();

	private AIMAimCostSplitDataGetter aimGetter;

	private AimProductTypeGetter productTypeGetter;

	private RetValue retValueNotLeaf;
	
	// ????????????????????????????
	protected void setActionState() {
	}

	protected void tblMain_doRequestRowSet(RequestRowSetEvent e) {
	}

	/**
	 * ??????????
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {

	}

	public void onLoad() throws Exception {
		initControl();
		super.onLoad();
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
				tblMain.getViewManager().freeze(0, acctNameIndex);
				setApporAction();
		}});
		final Object obj = getUIContext().get("MainMenuName");
		if (!FDCHelper.isEmpty(obj)) {
			setUITitle(obj.toString());
		}
	}

	private void initControl() {
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionAddNew.setVisible(false);
		this.actionAddNew.setEnabled(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionLocate.setEnabled(false);
	}

	private void setRowColor(IRow row, Color color) {
		List amountColumns = new ArrayList();
		amountColumns.add("amount");
		String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
		for (int i = 0; i < productTypeIds.length; i++) {
			String productTypeId = productTypeIds[i];
			amountColumns.add(productTypeId + "sell");
			amountColumns.add(productTypeId + "all");
		}
		for (int k = 0; k < amountColumns.size(); k++) {
			String colName = (String) amountColumns.get(k);
			row.getCell(colName).getStyleAttributes().setFontColor(color);
		}
	}
	/**
	 * ??????????????????????<p>
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionDataNotLeaf() throws BOSException{
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add("amount");
		ProductTypeCollection prodcutMap = (ProductTypeCollection)this.retValueNotLeaf.get("ProductTypeCollection");
		RetValue productSellAreaValue = (RetValue)retValueNotLeaf.get("ProductSellAreaValue");
		String projectStage = ProjectStageEnum.AIMCOST_VALUE;
		Map productApproMap = new HashMap();
		for (Iterator iter = prodcutMap.iterator(); iter.hasNext();) {
			ProductTypeInfo product = (ProductTypeInfo) iter.next();
			String productTypeId = product.getId().toString();
			amountColumns.add(productTypeId + "all");
			amountColumns.add(productTypeId + "sell");
			BigDecimal apporValue = FDCHelper.toBigDecimal(productSellAreaValue.getBigDecimal(productTypeId+projectStage));
			productApproMap.put(productTypeId+"sell", apporValue);
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// ??????????
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean isRed = false;
					boolean hasData = false;
					boolean isSellColumn = false;
					if(colName.endsWith("sell")){
						BigDecimal apporValue = FDCHelper.toBigDecimal(productApproMap.get(colName));
						String key = colName.substring(0,colName.length()-4)+"all";
						Object allCost = row.getCell(key).getValue();
						if(allCost != null && FDCHelper.toBigDecimal(apporValue).compareTo(FDCHelper.ZERO) != 0){
							amount = FDCHelper.toBigDecimal(allCost).divide(apporValue, 2, BigDecimal.ROUND_HALF_UP);
							hasData = true;
						}
						isSellColumn = true;
					}
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null
								&& rowAdd.getCell(colName).getStyleAttributes()
										.getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						if (value != null && !isSellColumn) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.BLACK);
					}
					if (hasData) {
						row.getCell(colName).setValue(amount);
					} else {
						row.getCell(colName).setValue(null);
					}
				}
			}
		}
		if(amountColumns.size()>0){
			String[] columns=new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
			}catch(Exception e){
				handUIException(e);
			}
		}
	}
	/**
	 * ??????????????????????<p>
	 * @param table
	 * @param amountColumns
	 */
	public void setUnionData() throws BOSException{
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		amountColumns.add("amount");
//		amountColumns.add("isEnterDB");//true=1,false=0;
		String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
		String apportionId = ApportionTypeInfo.sellAreaType;
		Map productMap = new HashMap();
		for (int i = 0; i < productTypeIds.length; i++) {
			String productTypeId = productTypeIds[i];
			/**
			 * ????:??????????????all??sell????????????????????????????????????????????????????????????
			 * ????????=??????/????????????	 
			 */
			amountColumns.add(productTypeId + "all");
			amountColumns.add(productTypeId + "sell");
			BigDecimal apporValue = this.productTypeGetter.getProductApprotion(
					productTypeId, apportionId);
			productMap.put(productTypeId + "sell", apporValue);
		}
		for (int i = 0; i < table.getRowCount(); i++) {
			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {
				// ??????????
				int level = row.getTreeLevel();
				List rowList = new ArrayList();
				for (int j = i + 1; j < table.getRowCount(); j++) {
					IRow rowAfter = table.getRow(j);
					if (rowAfter.getTreeLevel() <= level) {
						break;
					}
					if (rowAfter.getUserObject() != null) {
						rowList.add(rowAfter);
					}
				}
				for (int k = 0; k < amountColumns.size(); k++) {
					String colName = (String) amountColumns.get(k);
					BigDecimal amount = FMConstants.ZERO;
					boolean isRed = false;
					boolean hasData = false;
					boolean isSellColumn = false;
					if(colName.endsWith("sell")){
						BigDecimal apporValue = FDCHelper.toBigDecimal(productMap.get(colName));
						String key = colName.substring(0,colName.length()-4)+"all";
						Object allCost = row.getCell(key).getValue();
						if(allCost != null && FDCHelper.toBigDecimal(apporValue).compareTo(FDCHelper.ZERO) != 0){
							amount = FDCHelper.toBigDecimal(allCost).divide(apporValue, 2, BigDecimal.ROUND_HALF_UP);
							hasData = true;
						}
						isSellColumn = true;
					}
					for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
						IRow rowAdd = (IRow) rowList.get(rowIndex);
						Object value = rowAdd.getCell(colName).getValue();
						if (value != null
								&& rowAdd.getCell(colName).getStyleAttributes()
										.getFontColor().equals(Color.RED)) {
							isRed = true;
						}
						if (value != null && !isSellColumn) {
							if (value instanceof BigDecimal) {
								amount = amount.add((BigDecimal) value);
							} else if (value instanceof Integer) {
								amount = amount.add(new BigDecimal(
										((Integer) value).toString()));
							}
							hasData = true;
						}
					}
					if (isRed) {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.RED);
					} else {
						row.getCell(colName).getStyleAttributes().setFontColor(
								Color.BLACK);
					}
					if (hasData) {
						row.getCell(colName).setValue(amount);
					} else {
						row.getCell(colName).setValue(null);
					}
				}
			}
		}
		if(amountColumns.size()>0){
			String[] columns=new String[amountColumns.size()];
			amountColumns.toArray(columns);
			try{
				AimCostClientHelper.setTotalCostRow(table, columns);
			}catch(Exception e){
				handUIException(e);
			}
		}
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(
			com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		int rowIndex = e.getRowIndex();
		int columnIndex = e.getColIndex();
		IRow row = tblMain.getRow(rowIndex);

		if (tblMain.getColumnKey(columnIndex)!=null&&tblMain.getColumnKey(columnIndex).equals("isChoose")) {
			List rowList = new ArrayList();
			Boolean isChoose = (Boolean) row.getCell("isChoose").getValue();
			if (row.getCell("isChoose").getUserObject() == null) {
				//????Boolean.valueOf 
				isChoose = Boolean.valueOf(!isChoose.booleanValue());
				row.getCell("isChoose").setValue(isChoose);
			}
			if (rowIndex >= tblMain.getRowCount() - 1) {
				return;
			}
			int level = row.getTreeLevel();
			for (int i = rowIndex + 1; i < tblMain.getRowCount(); i++) {
				if (tblMain.getRow(i).getTreeLevel() > level) {
					rowList.add(tblMain.getRow(i));
				} else {
					break;
				}
			}
			for (int i = 0; i < rowList.size(); i++) {
				IRow childRow = (IRow) rowList.get(i);
				if (childRow.getCell("isChoose").getUserObject() == null) {
					childRow.getCell("isChoose").setValue(isChoose);
				}
			}
		}
	}
	public void fillTableNotLeaf() throws Exception{
		if(retValueNotLeaf==null)
			return;
		CostAccountCollection costAccounts = (CostAccountCollection)retValueNotLeaf.get("CostAccountCollection");
		ProductTypeCollection prodcutMap = (ProductTypeCollection)retValueNotLeaf.get("ProductTypeCollection");
		RetValue costValues = (RetValue)retValueNotLeaf.get("costValues");
		RetValue productCostValues = (RetValue)retValueNotLeaf.get("productCostValues");
		RetValue productSellAreaValue = (RetValue)retValueNotLeaf.get("ProductSellAreaValue");
		
		this.tblMain.removeRows();
		tblMain.getTreeColumn().setDepth(retValueNotLeaf.getInt("maxLevel"));
		if(costAccounts!=null){
			for(Iterator it=costAccounts.iterator();it.hasNext();){
				CostAccountInfo costAccountInfo = (CostAccountInfo)it.next();
				IRow row = tblMain.addRow();
				row.setTreeLevel(costAccountInfo.getLevel() - 1);
				String longNumber = costAccountInfo.getLongNumber();
				longNumber = longNumber.replace('!', '.');
				row.getCell("acctNumber").setValue(longNumber);
				row.getCell("acctName").setValue(costAccountInfo.getName());
				if(costAccountInfo.isIsLeaf()){
					row.setUserObject(costAccountInfo);
					RetValue costValue = (RetValue)costValues.get(costAccountInfo.getLongNumber());
					RetValue productCostValue = (RetValue)productCostValues.get(costAccountInfo.getLongNumber());
					if(costValue !=null ){ 
						BigDecimal aimCostAmt = costValue.getBigDecimal("aimCostAmt");
						row.getCell("amount").setValue(aimCostAmt);
					}
					if(productCostValue==null) continue;
					for (Iterator iter = prodcutMap.iterator(); iter.hasNext();) {
						ProductTypeInfo product = (ProductTypeInfo) iter.next();
						String productTypeID = product.getId().toString();
						String key = productTypeID;
						BigDecimal aimAimCostAmt = productCostValue.getBigDecimal(key+"aimAimCostAmt");
						BigDecimal aimAimSaleUnitAmt = productCostValue.getBigDecimal(key+"aimAimSaleUnitAmt");
						row.getCell(productTypeID+"all").setValue(aimAimCostAmt);
						//??????????????????
//						BigDecimal approValue = FDCHelper.toBigDecimal(productSellAreaValue.getBigDecimal(key+ProjectStageEnum.AIMCOST_VALUE));
//						if(FDCHelper.toBigDecimal(aimAimCostAmt).signum()!=0&&FDCHelper.toBigDecimal(approValue).signum()!=0){
//							BigDecimal aimAimSaleUnitAmt = aimAimCostAmt.divide(approValue, BigDecimal.ROUND_HALF_UP);
							row.getCell(productTypeID+"sell").setValue(aimAimSaleUnitAmt);
//						}
					}
				}else{
					row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
				}
			}
		}
		setUnionDataNotLeaf();
	}
	
	public void fillTable() throws Exception {
		KDTable table = this.tblMain;
		table.removeRows();
		String objectId = this.getSelectObjId();
		BOSObjectType bosType = BOSUuid.read(objectId).getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("curProject.id", objectId));
		} else {
			acctFilter.getFilterItems().add(
					new FilterItemInfo("fullOrgUnit.id", objectId));
		}
		acctFilter.getFilterItems().add(
				new FilterItemInfo("isEnabled", new Integer(1)));
		AcctAccreditHelper.handAcctAccreditFilter(null, objectId, acctFilter);
		TreeModel costAcctTree = FDCClientHelper.createDataTree(
				CostAccountFactory.getRemoteInstance(), acctFilter);

		// EntityViewInfo view = new EntityViewInfo();
		// FilterInfo filter = new FilterInfo();
		// view.setFilter(filter);
		// filter.getFilterItems().add(new FilterItemInfo("orgOrProId",
		// objectId));
		// filter.getFilterItems().add(
		// new FilterItemInfo("isLastVersion", new Integer(1)));
		// view.getSorter().add(new SorterItemInfo("id"));
		// view.getSelector().add("*");
		// view.getSelector().add("creator.name");
		// view.getSelector().add("auditor.name");
		// view.getSelector().add("costEntry.*");
		// view.getSelector().add("costEntry.product.*");
		// AimCostCollection aimCostCollection = AimCostFactory
		// .getRemoteInstance().getAimCostCollection(view);
		// Set costEntryIdSet = new HashSet();
		// if (aimCostCollection.size() > 0) {
		// AimCostInfo aimCost = aimCostCollection.get(0);
		// CostEntryCollection costEntryCollection = aimCost.getCostEntry();
		// this.aimGetter=new
		// for (int i = 0; i < costEntryCollection.size(); i++) {
		// CostEntryInfo info = costEntryCollection.get(i);
		// costEntryIdSet.add(info.getId().toString());
		// CostAccountInfo costAccount = info.getCostAccount();
		// if (aimCostEntryMap.containsKey(costAccount.getId().toString())) {
		// CostEntryCollection coll = (CostEntryCollection) aimCostEntryMap
		// .get(costAccount.getId().toString());
		// coll.add(info);
		// } else {
		// CostEntryCollection newColl = new CostEntryCollection();
		// newColl.add(info);
		// aimCostEntryMap
		// .put(costAccount.getId().toString(), newColl);
		// }
		// }
		// }
		this.aimGetter = new AIMAimCostSplitDataGetter(objectId,productTypeGetter);
		this.aimGetter.initProductSplitData();
		// if (costEntryIdSet.size() > 0) {
		// view.getSelector().clear();
		// view.getSelector().add("*");
		// view.getSelector().add("apportionType.*");
		// view.getFilter().getFilterItems().clear();
		// view.getFilter().getFilterItems().add(
		// new FilterItemInfo("costEntryId", costEntryIdSet,
		// CompareType.INCLUDE));
		// AIMAimCostProductSplitEntryCollection splits =
		// AIMAimCostProductSplitEntryFactory
		// .getRemoteInstance().getAIMAimCostProductSplitEntryCollection(
		// view);
		// splitEntryMap.clear();
		// for (int i = 0; i < splits.size(); i++) {
		// AIMAimCostProductSplitEntryInfo info = splits.get(i);
		// String costEntryId = info.getCostEntryId();
		// if (splitEntryMap.containsKey(costEntryId)) {
		// AIMAimCostProductSplitEntryCollection coll =
		// (AIMAimCostProductSplitEntryCollection) splitEntryMap
		// .get(costEntryId);
		// coll.add(info);
		// } else {
		// AIMAimCostProductSplitEntryCollection newColl = new
		// AIMAimCostProductSplitEntryCollection();
		// newColl.add(info);
		// splitEntryMap.put(costEntryId, newColl);
		// }
		// }
		// }
		DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree
				.getRoot();
		Enumeration childrens = root.depthFirstEnumeration();
		int maxLevel = 0;
		while (childrens.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens
					.nextElement();
			if (node.getUserObject() != null && node.getLevel() > maxLevel) {
				maxLevel = node.getLevel();
			}
		}
		table.getTreeColumn().setDepth(maxLevel);
		for (int i = 0; i < root.getChildCount(); i++) {
			fillNode(table, (DefaultMutableTreeNode) root.getChildAt(i));
		}
		this.setUnionData();
		this.setEnterDB(-1);
	}

	private void fillNode(KDTable table, DefaultMutableTreeNode node)
			throws BOSException, SQLException, EASBizException {
		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			MsgBox.showError("too many costAccount level!");
			return;
		}
		IRow row = table.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getStyleAttributes().setLocked(true);
		row.getStyleAttributes().setBackground(new Color(0xF0EDD9));
		String longNumber = costAcct.getLongNumber();
		longNumber = longNumber.replace('!', '.');
		row.getCell("acctNumber").setValue(longNumber);
		row.getCell("acctName").setValue(costAcct.getName());
		row.getCell("isChoose").setValue(Boolean.FALSE);
//		row.getCell("isEnterDB").setValue(Boolean.TRUE);
		if (node.isLeaf()) {
			CostEntryCollection coll = this.aimGetter.getCostEntrys(costAcct
					.getId().toString());
			if (coll != null && coll.size() > 0) {
				for (int i = 0; i < coll.size(); i++) {
					CostEntryInfo info = coll.get(i);
					if(info==null) continue;
					IRow entryRow = table.addRow();
					entryRow.setUserObject(info);
					entryRow.setTreeLevel(node.getLevel());
					entryRow.getCell("acctName").setValue(info.getEntryName());
					entryRow.getCell("amount").setValue(info.getCostAmount());
					entryRow.getCell("isChoose").setValue(Boolean.FALSE);
					entryRow.getCell("isEnterDB").setValue(Boolean.valueOf(info.isIsEnterDB()));
					AIMAimCostProductSplitEntryCollection cashe = this.aimGetter
							.getProductSplitEntry(info.getId().toString());
					Map databaseData = new HashMap();
					if (cashe != null) {
						for (int j = 0; j < cashe.size(); j++) {
							AIMAimCostProductSplitEntryInfo split = cashe.get(j);
							databaseData.put(split.getProduct().getId()
									.toString(), split.getSplitAmount());
						}
					}
					if (info.getProduct() != null) {
						databaseData.put(info.getProduct().getId().toString(),
								info.getCostAmount());
					}
					entryRow.getCell("acctNumber").setUserObject(databaseData);
					AIMAimCostProductSplitEntryCollection splits = getRowSetting(entryRow);
					calculateRowData(entryRow, splits);
				}
			}
		} else {
			for (int i = 0; i < node.getChildCount(); i++) {
				this.fillNode(table, (DefaultMutableTreeNode) node
						.getChildAt(i));
			}
		}
	}

	private AIMAimCostProductSplitEntryCollection getRowSetting(IRow entryRow) {
		CostEntryInfo entry = (CostEntryInfo) entryRow.getUserObject();
		AIMAimCostProductSplitEntryCollection splits = this.aimGetter
				.createSetting(entry);
		return splits;
	}

	private void calculateRowData(IRow entryRow,
			AIMAimCostProductSplitEntryCollection splits) throws BOSException {
		// ??????????????
		String[] productTypeIds = this.productTypeGetter.getProductTypeIds();
		for (int i = 0; i < productTypeIds.length; i++) {
			String productTypeId = productTypeIds[i];
			entryRow.getCell(productTypeId + "sell").setValue(null);
			entryRow.getCell(productTypeId + "all").setValue(null);
		}
		if (splits.size() <= 0) {
			entryRow.getCell("acctName").setUserObject(new HashMap());
			entryRow.getCell("apportionType").setUserObject(new HashMap());
			this.setRowColor(entryRow, Color.BLUE);
			return;
		}
		CostEntryInfo entry = (CostEntryInfo) entryRow.getUserObject();
		AIMAimCostProductSplitEntryInfo split = splits.get(0);
		ApportionTypeInfo apportionType = split.getApportionType();
		if (apportionType != null) {
			entryRow.getCell("apportionType").setValue(apportionType.getName());
		} else {
			entryRow.getCell("apportionType").setValue(
					AimCostHandler.getResource("DirectOwn"));
		}

		if (entryRow.getCell("apportionType").getUserObject() != null) {
			Map map = (Map) entryRow.getCell("apportionType").getUserObject();
			map.put("newId", apportionType.getId().toString());
			String apporId = (String) map.get("oldId");
			if (!apportionType.getId().toString().equals(apporId)) {
				entryRow.getCell("apportionType").getStyleAttributes()
						.setFontColor(Color.RED);
			} else {
				entryRow.getCell("apportionType").getStyleAttributes()
						.setFontColor(Color.BLACK);
			}
		} else {
			Map map = new HashMap();
			if (apportionType != null) {
				map.put("oldId", apportionType.getId().toString());
			} else {
				map.put("oldId", "direct");
				AIMAimCostProductSplitEntryCollection cashe = this.aimGetter
						.getProductSplitEntry(entry.getId().toString());
				if (cashe != null) {
					entryRow.getCell("apportionType").getStyleAttributes()
							.setFontColor(Color.RED);
				}
				// if (splits.size() > 0) {
				// entryRow.getCell("apportionType").getStyleAttributes()
				// .setFontColor(Color.RED);
				// }
			}
			entryRow.getCell("apportionType").setUserObject(map);

		}

		Map calculateData = this.aimGetter.getAimProductData(entry);
		Set keySet = calculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String prodId = (String) iter.next();
			BigDecimal splitAmount = (BigDecimal) calculateData.get(prodId);
			if (entryRow.getCell(prodId + "all") != null) {
				entryRow.getCell(prodId + "all").setValue(splitAmount);
			}
		}
		// if (splits.size() > 1) {
		// } else {
		// // ????????????????
		// String productSplitId = splits.get(0).getProduct().getId()
		// .toString();
		// calculateData.put(productSplitId, entry.getCostAmount());
		// if (entryRow.getCell(productSplitId + "all") != null) {
		// entryRow.getCell(productSplitId + "all").setValue(
		// entry.getCostAmount());
		// }
		// }
		if (splits.get(0).getApportionType() == null) {
			// calculateData = new HashMap();
			entryRow.getCell("isChoose").setUserObject("NotChoose");
			entryRow.getCell("isChoose").setValue(null);
			entryRow.getCell("isChoose").getStyleAttributes().setBackground(
					Color.cyan);
		}
		// ??????????????
		setSellAmountbySplit(entryRow, calculateData);
		entryRow.getCell("acctName").setUserObject(calculateData);
		if (isEaquelDatabase(entryRow)) {
			this.setRowColor(entryRow, Color.BLACK);
		} else {
			this.setRowColor(entryRow, Color.RED);
		}
	}

	private void setSellAmountbySplit(IRow entryRow, Map calculateData) throws BOSException {
		Set keySet = calculateData.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			String apportionId = ApportionTypeInfo.sellAreaType;
			BigDecimal apporValue = this.productTypeGetter.getProductApprotion(
					productId, apportionId);
			BigDecimal value = null;
			if (entryRow.getCell(productId + "all") != null) {
				value = (BigDecimal) entryRow.getCell(productId + "all")
						.getValue();
			}
			if (value != null && apporValue.compareTo(FDCHelper.ZERO) != 0) {
				entryRow.getCell(productId + "sell").setValue(
						value.divide(apporValue, 2, BigDecimal.ROUND_HALF_UP));
			}
		}
	}

	private boolean isEaquelDatabase(IRow entryRow) {
		Map databaseData = (Map) entryRow.getCell("acctNumber").getUserObject();
		Map splitData = (Map) entryRow.getCell("acctName").getUserObject();
//		Map verifyData = new HashMap();
		if (splitData == null) {
			splitData = new HashMap();
		}
		for (Iterator iter = splitData.keySet().iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			BigDecimal amount1 = (BigDecimal) splitData.get(productId);
			BigDecimal amount2 = (BigDecimal) databaseData.get(productId);
			if(FDCHelper.toBigDecimal(amount1).compareTo(FDCHelper.toBigDecimal(amount2))!=0){
				return false;
			}
		}
		
/*		Set splitSet = splitData.keySet();
		for (Iterator iter = splitSet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			verifyData.put(productId, splitData.get(productId));
		}

		Set dataSet = databaseData.keySet();
		for (Iterator iter = dataSet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			BigDecimal amount1 = (BigDecimal) verifyData.get(productId);
			if (amount1 == null) {
				amount1 = FDCHelper.ZERO;
			}
			BigDecimal amount2 = (BigDecimal) databaseData.get(productId);
			if (amount2 == null) {
				amount2 = FDCHelper.ZERO;
			}
			verifyData.put(productId, amount1.subtract(amount2));
		}
		splitSet = verifyData.keySet();
		for (Iterator iter = splitSet.iterator(); iter.hasNext();) {
			String productId = (String) iter.next();
			BigDecimal amount = (BigDecimal) verifyData.get(productId);
			if (amount.compareTo(FDCHelper.ZERO) != 0) {
				return false;
			}
		}*/
		return true;
	}

	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		// super.tblMain_tableSelectChanged(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return CurProjectFactory.getRemoteInstance();
	}

	protected String getEditUIName() {
		return null;
	}

	protected void initTree() throws Exception {
		this.initTable();
		ProjectTreeBuilder treeBuilder = new ProjectTreeBuilder();
		treeBuilder.build(this, this.treeMain, this.actionOnLoad);
		this.treeMain.expandAllNodes(true,
				(TreeNode) ((TreeModel) this.treeMain.getModel()).getRoot());
	}

	private String getSelectObjId() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node.getUserObject() == null
				|| OrgViewUtils.isTreeNodeDisable(node)) {
			return null;
		}
		if (node.getUserObject() instanceof CurProjectInfo) {
			CurProjectInfo projectInfo = (CurProjectInfo) node.getUserObject();
			return projectInfo.getId().toString();
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			OrgStructureInfo oui = (OrgStructureInfo) node.getUserObject();
			if (oui.getUnit() == null) {

			}
			FullOrgUnitInfo info = oui.getUnit();
			return info.getId().toString();
		}
		return null;
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		this.verifySaved();
		
		fetchAndFill(selectObjId);
		initUserConfig();
//		int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
//		tblMain.getViewManager().freeze(0, acctNameIndex);
	}
	
	private void fetchAndFill(String selectObjId) throws EASBizException,
			BOSException, Exception {
		LongTimeDialog dialog = UITools.getDialog(this);
		if (dialog == null)
			return;
		dialog.setLongTimeTask(new ILongTimeTask() {
			public Object exec() throws Exception {
				String selectObjId = getSelectObjId();
				DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
						.getLastSelectedPathComponent();
				if (node.isLeaf()
						&& node.getUserObject() instanceof CurProjectInfo) {
					productTypeGetter = new AimProductTypeGetter(
							selectObjId, ProjectStageEnum.AIMCOST);
					resetProductCol();
					setApporAction();
					fillTable();
				} else {
					Set leafPrjIds = FDCClientHelper
							.getProjectLeafsOfNode(node);
					boolean isLeafPrj = node.getUserObject() instanceof CurProjectInfo;
					fetchDataNotLeaf(selectObjId, leafPrjIds, isLeafPrj);
					resetProductColNotLeaf();
					setApporAction();
					fillTableNotLeaf();
				}
				return null;
			}

			public void afterExec(Object result) throws Exception {

			}
		});
		if(dialog!=null)
		dialog.show();
	}

	
	private void setApporAction() {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (currentOrg == null || !currentOrg.isIsBizUnit()) {
//			this.actionSubmit.setEnabled(false);
//			this.actionApportion.setEnabled(false);
//			this.actionRevert.setEnabled(false);
//			this.tblMain.getColumn("isChoose").getStyleAttributes().setHided(
//					true);
		}
		if (!node.isLeaf()) {
			this.actionSubmit.setEnabled(false);
			this.actionApportion.setEnabled(false);
			this.actionRevert.setEnabled(false);
		} else {
			this.actionSubmit.setEnabled(true);
			this.actionApportion.setEnabled(true);
			this.actionRevert.setEnabled(true);
		}
		if (node.isLeaf() && node.getUserObject() instanceof CurProjectInfo) {
			tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(false);
			tblMain.getColumn("isChoose").getStyleAttributes().setHided(false);
			tblMain.getColumn("apportionType").getStyleAttributes().setHided(false);
		}else{
			tblMain.getColumn("isEnterDB").getStyleAttributes().setHided(true);
			tblMain.getColumn("isChoose").getStyleAttributes().setHided(true);
			tblMain.getColumn("apportionType").getStyleAttributes().setHided(true);
		}
	}

	private void resetProductCol() {
		int columnCount = tblMain.getColumnCount();
		for (int i = 0; i < columnCount - 6; i++) {
			tblMain.removeColumn(6);
		}
		Map prodcutMap = this.productTypeGetter.getSortedProductMap();
		int i = 0;
		Set keySet = prodcutMap.keySet();
		for (Iterator iter = keySet.iterator(); iter.hasNext();) {
			String number = (String) iter.next();
			ProductTypeInfo product = (ProductTypeInfo) prodcutMap.get(number);
			IColumn column = tblMain.addColumn();
			String key = product.getId().toString() + "sell";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue(
					AimCostHandler.getResource("SellPart"));
			column = tblMain.addColumn();
			key = product.getId().toString() + "all";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue(
					AimCostHandler.getResource("AllCost"));
			tblMain.getHeadMergeManager().mergeBlock(0, 6 + i * 2, 0,
					6 + 1 + i * 2);
			i++;
		}
	}

	private void resetProductColNotLeaf() {
		int columnCount = tblMain.getColumnCount();
		for (int i = 0; i < columnCount - 6; i++) {
			tblMain.removeColumn(6);
		}
		ProductTypeCollection prodcutMap = (ProductTypeCollection)this.retValueNotLeaf.get("ProductTypeCollection");
		int i = 0;
		for (Iterator iter = prodcutMap.iterator(); iter.hasNext();) {
			ProductTypeInfo product = (ProductTypeInfo) iter.next();
			IColumn column = tblMain.addColumn();
			String key = product.getId().toString() + "sell";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue(
					AimCostHandler.getResource("SellPart"));
			column = tblMain.addColumn();
			key = product.getId().toString() + "all";
			column.setKey(key);
			column.getStyleAttributes().setHorizontalAlign(
					HorizontalAlignment.RIGHT);
			column.getStyleAttributes().setNumberFormat(
					FDCHelper.getNumberFtm(2));
			tblMain.getHeadRow(0).getCell(key).setValue(product.getName());
			tblMain.getHeadRow(1).getCell(key).setValue(
					AimCostHandler.getResource("AllCost"));
			tblMain.getHeadMergeManager().mergeBlock(0, 6 + i * 2, 0,
					6 + 1 + i * 2);
			i++;
		}
	}
	
	private void verifySaved() throws EASBizException, BOSException {
		boolean isEdited = false;
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				isEdited = !this.isEaquelDatabase(row);
				if (row.getCell("apportionType").getStyleAttributes()
						.getFontColor().equals(Color.RED)) {
					isEdited = true;
				}
				if (isEdited) {
					break;
				}
			}
		}
		if (isEdited) {
			if (MsgBox.showConfirm2New(this, AimCostHandler.getResource("NoSave")) == MsgBox.YES) {
//				submitData();
				this.btnSubmit.doClick();
			}
		}
	}

	/**
	 * ????????????
	 */
	private void initTable() {
		KDTable table = this.tblMain;
		table.checkParsed();
		table.getDataRequestManager().setDataRequestMode(
				KDTDataRequestManager.REAL_MODE);
		tblMain.getViewManager().setFreezeView(-1, 2);
		table.setRefresh(false);
		table.getColumn("apportionType").getStyleAttributes()
				.setHorizontalAlign(HorizontalAlignment.CENTER);
		table.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		table.getColumn("acctNumber").getStyleAttributes().setLocked(true);
//		find bugs EXP ????????????????????????????????????
//		Color lockColor = new Color(0xF0AAD9);
		table.getColumn("amount").getStyleAttributes().setNumberFormat(
				FDCHelper.getNumberFtm(2));
		table.getColumn("amount").getStyleAttributes().setHorizontalAlign(
				HorizontalAlignment.RIGHT);
		tblMain.addHeadRow(1, (IRow) tblMain.getHeadRow(0).clone());
		for (int i = 0; i < tblMain.getColumnCount(); i++) {
			tblMain.getHeadMergeManager().mergeBlock(0, i, 1, i);
		}
		tblMain.setColumnMoveable(true);
	}

	/**
	 * output class constructor
	 */
	public AIMProductAimCostUI() throws Exception {
		super();
	}

	/**
	 * output actionSubmit_actionPerformed
	 */
	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		submitData();
		this.setMessageText(EASResource
				.getString(FrameWorkClientUtils.strResource + "Msg_Save_OK"));
		this.showMessage();
		fillTable();
	}

	private void submitData() throws BOSException, EASBizException {
		/*
		 *key=costEntryid value=AimCostProductSplitEntryCollection
		 */
		Map costEntryMap=new HashMap();
		String selectObjId = getSelectObjId();
		if(selectObjId!=null){
			costEntryMap.put("prjId", selectObjId);
		}
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				boolean isEdited = !this.isEaquelDatabase(row);
				if (row.getCell("apportionType").getStyleAttributes()
						.getFontColor().equals(Color.RED)) {
					isEdited = true;
				}
				if (isEdited) {
					CostEntryInfo entry = (CostEntryInfo) row.getUserObject();
					String entryId = entry.getId().toString();
					AIMAimCostProductSplitEntryCollection rowSetting = this
							.getRowSetting(row);
					Map calData = (Map) row.getCell("acctName").getUserObject();
					AIMAimCostProductSplitEntryCollection newColl=new AIMAimCostProductSplitEntryCollection();
					for (int j = 0; j < rowSetting.size(); j++) {
						AIMAimCostProductSplitEntryInfo info = rowSetting.get(j);
						info.setCostEntryId(entryId);
						info.setSplitAmount((BigDecimal) calData.get(info
								.getProduct().getId().toString()));
						if (info.getApportionType() != null) {
							newColl.add((AIMAimCostProductSplitEntryInfo)info.clone());
						}
					}
					if(newColl.size()>0){
						costEntryMap.put(entryId, newColl);
					}

				}
			}
		}
		FDCCostRptFacadeFactory.getRemoteInstance().submitAIMAimProductCost(costEntryMap);
		//????????
		
		CacheServiceFactory.getInstance().discardType(new AIMAimCostProductSplitEntryInfo().getBOSType());
	}

	/**
	 * output actionApportion_actionPerformed
	 */
	public void actionApportion_actionPerformed(ActionEvent e) throws Exception {
		List rows = new ArrayList();
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			IRow row = this.tblMain.getRow(i);
			if (row.getUserObject() != null) {
				if (row.getCell("isChoose").getValue() != null
						&& ((Boolean) row.getCell("isChoose").getValue())
								.booleanValue()) {
					rows.add(row);
				}
			}
		}
		if (rows.size() == 0) {
			this.setMessageText(AimCostHandler.getResource("NoDetailRow"));
			this.showMessage();
			return;
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put("projectStage", ProjectStageEnum.AIMCOST);
		if (rows.size() == 1) {
			IRow selectRow = (IRow) rows.get(0);
			// ????????
			AIMAimCostProductSplitEntryCollection entrys = getRowSetting(selectRow);
			uiContext.put("splits", entrys);
		} else {
			uiContext.put("splits", new AIMAimCostProductSplitEntryCollection());
		}
		String selectObjId = this.getSelectObjId();
		BOSObjectType bosType = BOSUuid.read(selectObjId).getType();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			uiContext.put("projectId", selectObjId);
			IUIWindow uiWin = UIFactory.createUIFactory(UIFactoryName.MODEL)
					.create(AIMAimCostProductSplitSettingUI.class.getName(),
							uiContext, null, "ADDNEW");
			uiWin.show();
			Map context = ((AIMAimCostProductSplitSettingUI) uiWin.getUIObject())
					.getUIContext();
			if (context.get("isOK") == null) {
				return;
			}
			for (int i = 0; i < rows.size(); i++) {
				IRow row = (IRow) rows.get(i);
				CostEntryInfo entry = (CostEntryInfo) row.getUserObject();
				AIMAimCostProductSplitEntryCollection col = (AIMAimCostProductSplitEntryCollection) context
						.get("splits");
				this.aimGetter.updateProductMap(entry.getId().toString(), col);
				this.calculateRowData(row, col);
				this.setUnionData();
			}
		}
	}

	/**
	 * output actionRevert_actionPerformed
	 */
	public void actionRevert_actionPerformed(ActionEvent e) throws Exception {
		this.fillTable();
	}

	protected void initWorkButton() {
		super.initWorkButton();
		this.btnSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.btnApportion.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.btnRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		setButtonDefaultStyl(this.btnSubmit);
		this.menuItemSubmit.setIcon(EASResource.getIcon("imgTbtn_save"));
		this.menuItemApportion.setIcon(EASResource.getIcon("imgTbtn_split"));
		this.menuItemRevert.setIcon(EASResource.getIcon("imgTbtn_restore"));
		
		actionEnterDB.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_staruse"));
		actionCancelEnterDB.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_forbid"));
		actionEnterDB.setEnabled(true);
		actionCancelEnterDB.setEnabled(true);
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		this.verifySaved();
		String selectObjId = getSelectObjId();
		if (selectObjId == null) {
			return;
		}
		TimeTools.getInstance().reset();
		fetchAndFill(selectObjId); 
		initUserConfig();
	}

	public boolean destroyWindow() {
		try {
			this.verifySaved();
		} catch (EASBizException e) {
			// TODO ???????? catch ??
			e.printStackTrace();
		} catch (BOSException e) {
			// TODO ???????? catch ??
			e.printStackTrace();
		}
		return super.destroyWindow();
	}
	public void onShow() throws Exception {
		super.onShow();
/*		int acctNameIndex=tblMain.getColumn("acctName").getColumnIndex()+1;
//		tblMain.getViewManager().setFreezeView(-1, acctNameIndex);
		tblMain.getViewManager().freeze(0, acctNameIndex);*/
		
	}
	
	public void actionEnterDB_actionPerformed(ActionEvent e) throws Exception {
		//????????
		checkSelected();
		int index=tblMain.getSelectManager().getActiveRowIndex();
		
		Object obj=tblMain.getCell(index, "isEnterDB").getValue();
		if(obj==null||obj.equals(Boolean.TRUE)){
			return;
		}
		boolean hasChange=false;
		obj=tblMain.getRow(index).getUserObject();
		FDCSQLBuilder builder=new FDCSQLBuilder();
		if(obj instanceof CostEntryInfo){
			CostEntryInfo info=(CostEntryInfo)obj;
			info.setIsEnterDB(true);
			builder.appendSql("update T_AIM_CostEntry set FIsEnterDB=? where fid=?");
			builder.addParam(new Integer(1));
			builder.addParam(info.getId().toString());
			builder.executeUpdate();
			tblMain.getCell(index, "isEnterDB").setValue(Boolean.TRUE);
			hasChange=true;
		}else{
			//??????????????????????
			List params=new ArrayList();
			int level=tblMain.getRow(index).getTreeLevel();
			for(int i=index+1;i<tblMain.getRowCount();i++){
				int mylevel=tblMain.getRow(i).getTreeLevel();
				if(mylevel<=level){
					break;
				}
				
				obj=tblMain.getRow(i).getUserObject();
				if(obj instanceof CostEntryInfo){
					CostEntryInfo info=(CostEntryInfo)obj;
					info.setIsEnterDB(true);
//					builder.appendSql("update T_AIM_CostEntry set FIsEnterDB=? where fid=?");
//					builder.addParam(new Integer(1));
//					builder.addParam(info.getId().toString());
//					builder.executeUpdate();
					params.add(Arrays.asList(new Object[]{Boolean.TRUE,info.getId().toString()}));
					tblMain.getCell(i, "isEnterDB").setValue(Boolean.TRUE);
					hasChange=true;
				}
			}
			if(params.size()>0){
				String sql="update T_AIM_CostEntry set FIsEnterDB=? where fid=?";
				builder.executeBatch(sql, params);
			}
			
		}
		
		if(hasChange){
			setEnterDB(-1);
		}
		
	}
	public void actionCancelEnterDB_actionPerformed(ActionEvent e)
			throws Exception {
		//????????
		checkSelected();
		int index=tblMain.getSelectManager().getActiveRowIndex();
		
		Object obj=tblMain.getCell(index, "isEnterDB").getValue();
		if(obj==null||obj.equals(Boolean.FALSE)){
			return;
		}
		boolean hasChange=false;
		obj=tblMain.getRow(index).getUserObject();
		FDCSQLBuilder builder=new FDCSQLBuilder();
		if(obj instanceof CostEntryInfo){
			CostEntryInfo info=(CostEntryInfo)obj;
			info.setIsEnterDB(false);
			builder.appendSql("update T_AIM_CostEntry set FIsEnterDB=? where fid=?");
			builder.addParam(new Integer(0));
			builder.addParam(info.getId().toString());
			builder.executeUpdate();
			tblMain.getCell(index, "isEnterDB").setValue(Boolean.FALSE);
			hasChange=true;
		}else{
			//??????????????????????
			List params=new ArrayList();
			int level=tblMain.getRow(index).getTreeLevel();
			for(int i=index+1;i<tblMain.getRowCount();i++){
				int mylevel=tblMain.getRow(i).getTreeLevel();
				if(mylevel<=level){
					break;
				}
				
				obj=tblMain.getRow(i).getUserObject();
				if(obj instanceof CostEntryInfo){
					CostEntryInfo info=(CostEntryInfo)obj;
					info.setIsEnterDB(false);
//					builder.appendSql("update T_AIM_CostEntry set FIsEnterDB=? where fid=?");
//					builder.addParam(new Integer(0));
//					builder.addParam(info.getId().toString());
//					builder.executeUpdate();
					params.add(Arrays.asList(new Object[]{Boolean.FALSE,info.getId().toString()}));
					tblMain.getCell(i, "isEnterDB").setValue(Boolean.FALSE);
					hasChange=true;
				}
			}
			
			if(params.size()>0){
				String sql="update T_AIM_CostEntry set FIsEnterDB=? where fid=?";
				builder.executeBatch(sql, params);
			}
		}
		
		if(hasChange){
			setEnterDB(-1);
		}
	}
	
	/**
	 * ??????????????????????????????
	 * @param rowIndex
	 * @return
	 */
	private boolean setEnterDB(int rowIndex){
		if(rowIndex<0){
			for(int i=0;i<tblMain.getRowCount();i++){
				if(tblMain.getRow(i).getTreeLevel()==0){
					setEnterDB(i);
				}
			}
			return false;
		}
		int level=tblMain.getRow(rowIndex).getTreeLevel();
		if(tblMain.getRow(rowIndex).getUserObject() instanceof CostEntryInfo){
			CostEntryInfo info = (CostEntryInfo)tblMain.getRow(rowIndex).getUserObject();
			if(info.isIsEnterDB()){
				return true;
			}
		}
		boolean isTrue=false;
		for(int i=rowIndex+1;i<tblMain.getRowCount();i++){
			int level2=tblMain.getRow(i).getTreeLevel();
			if(level2==level+1){
				//????
				boolean flag=setEnterDB(i);
				if(flag){
					isTrue=true;
				}
				
			}else if(level2<=level){
				break;
			}
		}
		
		tblMain.getRow(rowIndex).getCell("isEnterDB").setValue(Boolean.valueOf(isTrue));
		return isTrue;
		
	}
	
	private void fetchDataNotLeaf(String objId,Set leafPrjIds,boolean selectObjIsPrj) throws EASBizException, BOSException{
		TimeTools.getInstance().msValuePrintln("start fetchData");
		ParamValue paramValue = new ParamValue();
		paramValue.put("selectObjID", objId);
		paramValue.put("leafPrjIDs", leafPrjIds);
		paramValue.put("selectObjIsPrj", Boolean.valueOf(selectObjIsPrj));
		retValueNotLeaf = ProjectCostRptFacadeFactory.getRemoteInstance().getCollectionProductAimCost(paramValue);
		TimeTools.getInstance().msValuePrintln("end fetchData");
	}
	//??????????????????????????????query
	public int getRowCountFromDB() {
//		super.getRowCountFromDB();
		return -1;
	}
}