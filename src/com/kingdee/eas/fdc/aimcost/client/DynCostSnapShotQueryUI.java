/**
 * output package name
 */
package com.kingdee.eas.fdc.aimcost.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.data.event.RequestRowSetEvent;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.chart.ChartType;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.aimcost.CostMonthlySaveTypeEnum;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotCollection;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotFactory;
import com.kingdee.eas.fdc.aimcost.DynCostSnapShotInfo;
import com.kingdee.eas.fdc.aimcost.FDCCostRptFacadeFactory;
import com.kingdee.eas.fdc.aimcost.FullDynamicCostMap;
import com.kingdee.eas.fdc.aimcost.IDynCostSnapShot;
import com.kingdee.eas.fdc.aimcost.ProjectCostRptFacadeFactory;
import com.kingdee.eas.fdc.basedata.AcctAccreditHelper;
import com.kingdee.eas.fdc.basedata.ApportionTypeInfo;
import com.kingdee.eas.fdc.basedata.CostAccountCollection;
import com.kingdee.eas.fdc.basedata.CostAccountFactory;
import com.kingdee.eas.fdc.basedata.CostAccountInfo;
import com.kingdee.eas.fdc.basedata.CurProjectFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCNumberHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.ParamValue;
import com.kingdee.eas.fdc.basedata.ProjectHelper;
import com.kingdee.eas.fdc.basedata.ProjectStageEnum;
import com.kingdee.eas.fdc.basedata.RetValue;
import com.kingdee.eas.fdc.basedata.client.ChartData;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fm.common.FMConstants;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.DateTimeUtils;

/**
 * ????:??????????????????
 * 
 * @author jackwang date:2007-4-26
 *         <p>
 * @version EAS5.3
 */
public class DynCostSnapShotQueryUI extends AbstractDynCostSnapShotQueryUI {
	private static final Logger logger = CoreUIObject.getLogger(DynCostSnapShotQueryUI.class);

	private static final String COSTACCOUNT = "costAccount";// ????????

	private static final String AIM_COST = "aimCost";// ????????

	private static final String LAST_DYNAMIC_COST = "lastDynCost";// ????????????

	// private static final String DYNAMIC_COST = "dynamicCost";//????????

	private static final String HAS_HAPPEN = "hadCost";// ??????????

	private static final String DIFF = "differ";// ????
	
	//Add by zhiyuan_tang 2010/07/08   ??????????????
	private static final String AMT_DIFF = "amountdiffer";// ????????
	
	private static final String AMT_DIFF_AIMCOST = "amountdiffer_aimcost";//????????_??????????????

	//
	private static final String AMT = "amt";// ????

	private static final String SALABLEAMT = "salableAmt";// ????????

	private static final String RATE = "rate";// ????

	private static final String DIFF_LASTMONTH = "differ_lastmonth";// ??????????????

	private static final String DIFF_AIMCOST = "differ_aimcost";// ??????????????

	// ////////////////////////////////////////////////////////////
	private static final String MONTH_TO = "monthTo";

	private static final String MONTH_FROM = "monthFrom";

	private static final String YEAR_TO = "yearTo";

	private static final String YEAR_FROM = "yearFrom";

	private static final String PROJECT_IDS = "projectIds";

	private CommonQueryDialog commonQueryDialog = null;

	private CustomerQueryPanel filterUI = null;

	private DynCostSnapShotFilterUI filterPanel = null;

	private Date begindate = null;

	private Date endDate = null;

	private CurProjectInfo project = null;

	private Integer year_from, year_to, month_from, month_to;

	private boolean isFirstIn = true;

	private int colIndex = 0;

	private DynCostSnapShotCollection dcssc = null;

	private Map acctMap = new HashMap();

	private Map cacheMap = new HashMap();

	private Map historyCacheMap = new HashMap();

	private ArrayList al = new ArrayList();

	private BigDecimal aimSaleArea;
	
	private BigDecimal dySaleArea;

	private List rootNodeList = new ArrayList();

	private List existCostAccount = new ArrayList();
	String projectName = null;
	/**
	 * output class constructor
	 */
	public DynCostSnapShotQueryUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
//		setDefaultFilterInited(true);
//		initCommonQueryDialog();
//		this.doQuery(commonQueryDialog);
		super.onLoad();
		// initStaticTable();//??????????table????
		initControl();
		this.btnChart.setIcon(EASResource.getIcon("imgTbtn_planchart"));
		this.menuItemChart.setIcon(EASResource.getIcon("imgTbtn_planchart"));
		//????????????????
		tHelper = new TablePreferencesHelper(this);
	}

	/*
	 * table??????????????
	 */
	private void initStaticTable() {
		tblMain.getStyleAttributes().setLocked(true);
		tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		tblMain.getViewManager().setFreezeView(-1, 2);// ????
		tblMain.getColumn(AIM_COST).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(LAST_DYNAMIC_COST).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(HAS_HAPPEN).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getColumn(DIFF).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		//Add by zhiyuan_tang 2010/07/08   ??????????????
		tblMain.getColumn(AMT_DIFF).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);

		tblMain.getColumn(AIM_COST).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(LAST_DYNAMIC_COST).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(HAS_HAPPEN).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		tblMain.getColumn(DIFF).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		//Add by zhiyuan_tang 2010/07/08   ??????????????
		tblMain.getColumn(AMT_DIFF).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		// ????
		tblMain.addHeadRow(1, (IRow) tblMain.getHeadRow(0).clone());

		tblMain.getHeadMergeManager().mergeBlock(0, 0, 1, 0);// ????????
		tblMain.getHeadMergeManager().mergeBlock(0, 1, 1, 1);// ????????
		// ????????
		int colInd = 2;
		IColumn col = tblMain.getColumn(colInd++);
		String key = AIM_COST;
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("??????(????)");

		col = tblMain.addColumn(colInd++);
		key = AIM_COST + SALABLEAMT;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("????????(??/????)");
		tblMain.getHeadMergeManager().mergeBlock(0, 2, 0, colInd - 1);
		// ????????????
		col = tblMain.getColumn(colInd++);
		key = LAST_DYNAMIC_COST;
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("??????(????)");

		col = tblMain.addColumn(colInd++);
		key = LAST_DYNAMIC_COST + SALABLEAMT;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("????????(??/????)");
		tblMain.getHeadMergeManager().mergeBlock(0, 4, 0, colInd - 1);
		// ??????????
		col = tblMain.getColumn(colInd++);
		key = HAS_HAPPEN;
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("????(????)");

		col = tblMain.addColumn(colInd++);
		key = HAS_HAPPEN + SALABLEAMT;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("????????(??/????)");

		col = tblMain.addColumn(colInd++);
		key = HAS_HAPPEN + RATE;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("????(%)");
		tblMain.getHeadMergeManager().mergeBlock(0, 6, 0, colInd - 1);
		// ????????
		col = tblMain.getColumn(colInd++);
		key = DIFF;
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("??????????????");

		col = tblMain.addColumn(colInd++);
		key = DIFF_AIMCOST;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("??????????????");
		tblMain.getHeadMergeManager().mergeBlock(0, 9, 0, colInd - 1);
		
		// Add by zhiyuan_tang 2010/07/08 ??????????????
		// ????????
		col = tblMain.getColumn(colInd++);
		key = AMT_DIFF;
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("??????????????");

		col = tblMain.addColumn(colInd++);
		key = AMT_DIFF_AIMCOST;
		col.setKey(key);
		col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
		col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		tblMain.getHeadRow(1).getCell(key).setValue("??????????????");
		tblMain.getHeadMergeManager().mergeBlock(0, 11, 0, colInd - 1);
	}

	/*
	 * table????(????)
	 */
	private void refreshTable() {//
		initDynamicTable();
		try {
			//add by zhiyuan_tang 2010/07/08   ????????????
			setSaleArea();
			this.getMap();
		} catch (Exception e) {// ????????????
		}
		setProjectView();	
		this.tblMain.removeRows(false);// ??????,????????
		if (!this.project.isIsLeaf()) {
			return;
		}
		loadDatas();
		gatherDatas();
		addGatherRow();
		calRate();
			
	}
	/*
	 * ??????????????????
	 */
	private void setProjectView(){
		projectName = this.project.getDisplayName(SysContext.getSysContext().getLocale());
		projectName = projectName.replaceAll("_"," \\\\ ");
		txtProjectName.setText(projectName);
		txtProjectName.setEnabled(false);
	}
	
	/**
	 * ????????????
	 * @author zhiyuan_tang 2010/07/14
	 */
	private void setSaleArea() throws Exception {
		// ????????????????
		txtSaleArea.setEnabled(false);
		txtSaleArea.setDataType(KDFormattedTextField.BIGDECIMAL_TYPE);
		txtSaleArea.setPrecision(2);
		txtSaleArea.setRemoveingZeroInDispaly(false);
		txtSaleArea.setHorizontalAlignment(JTextField.RIGHT);
		// ??????????????
		this.txtSaleArea.setValue(null);
		String prjId = this.project.getId().toString();
		BigDecimal sellArea = null;
		Set leafPrjIDs = FDCHelper.getProjectLeafIdSet(null, prjId);
		FDCSQLBuilder builder=new FDCSQLBuilder();
		builder.appendSql("select ind_e.fapportiontypeid,sum(ind_e.findexvalue) as findexvalue from t_fdc_projectindexdata ind inner join t_fdc_projectindexdataentry ind_e on ind.fid=ind_e.fparentid");
		builder.appendSql(" where ind.fislatestver=1 and ");
		builder.appendSql(" ind.fproducttypeid is null and ");
		builder.appendParam("ind.fprojectstage",ProjectStageEnum.DYNCOST_VALUE);
		builder.appendSql(" and ");
		builder.appendParam("ind.fprojororgid",leafPrjIDs.toArray());
		builder.appendSql(" and ");
		builder.appendParam("ind_e.fapportiontypeid",ApportionTypeInfo.sellAreaType);
		builder.appendSql("  group by ind_e.fapportiontypeid ");
		
		IRowSet rowSet=builder.executeQuery();
		while(rowSet.next()){
			sellArea = rowSet.getBigDecimal("findexvalue");
		}
//		??????????SQL??????????????????????????????????????????????????????RPC????????
//		Map map;		
//		if (this.project.isIsLeaf()) {
//			//??????????????????????????????????????
//			map = ProjectHelper.getIndexValue(null, prjId, new String[] {ApportionTypeInfo.buildAreaType, ApportionTypeInfo.sellAreaType }, ProjectStageEnum.DYNCOST, false);
//			sellArea = FDCNumberHelper.add(sellArea, map.get(prjId + " " + ApportionTypeInfo.sellAreaType));
//		} else {
//			//??????????????????????????????????????????????????????????
//			//??????????????????????
//			Set projSet = FDCHelper.getProjectLeafIdSet(null, prjId);
//			//??????????????????????????????????????????????????????????????????
//			for (Iterator it = projSet.iterator(); it.hasNext();) {
//				prjId = it.next().toString();
//				map = ProjectHelper.getIndexValue(null, prjId, new String[] {ApportionTypeInfo.buildAreaType, ApportionTypeInfo.sellAreaType }, ProjectStageEnum.DYNCOST, false);
//				sellArea = FDCNumberHelper.add(sellArea, map.get(prjId + " " + ApportionTypeInfo.sellAreaType));
//			}
//		}
		this.txtSaleArea.setValue(sellArea == null ? null : FDCHelper.toBigDecimal(sellArea, 2));
	}

	/*
	 * ????????
	 */
	private void gatherDatas() {
		KDTable table = this.tblMain;
		List zeroLeverRowList = new ArrayList();
		List amountColumns = new ArrayList();
		for (int j = 2; j < table.getColumnCount(); j++) {
			amountColumns.add(tblMain.getColumn(j).getKey());
		}

		for (int i = 0; i < table.getRowCount(); i++) {

			IRow row = table.getRow(i);
			if (row.getTreeLevel() == 0) {
				zeroLeverRowList.add(row);
			}
			if (row.getUserObject() == null) {// ????????
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
					boolean hasData = false;
					/**
					 * ????????????????????????????????????????????????????????????????????????????????????????????????????????????????
					 * @author pengwei_hou Date: 2009-01-19 12:50:23
					 */
					if(colName.endsWith(SALABLEAMT)){
						
						String key = colName.substring(0, colName.length()-10);
						Object value = row.getCell(key).getValue();
						if(value != null){
							BigDecimal aimCost = FDCHelper.toBigDecimal(value);
							amount = FDCNumberHelper.divide(aimCost.multiply(FDCHelper.TEN_THOUSAND), this.aimSaleArea, 2, BigDecimal.ROUND_HALF_UP);
							hasData = true;
						}
						
					} else {
						
						for (int rowIndex = 0; rowIndex < rowList.size(); rowIndex++) {
							IRow rowAdd = (IRow) rowList.get(rowIndex);
							if (rowAdd.getCell(colName).getStyleAttributes().getFontColor().equals(Color.RED)) {
							}
							Object value = rowAdd.getCell(colName).getValue();
							if (value != null) {
								if (value instanceof BigDecimal) {
									amount = amount.add((BigDecimal) value);
								} else if (value instanceof Integer) {
									amount = amount.add(new BigDecimal(((Integer) value).toString()));
								}
								hasData = true;
							}
						}
					}
					
					if (hasData) {
						row.getCell(colName).setValue(amount);
					}
				}
			} else {
				// System.out.println(row.getCell(0).getValue());
				// System.out.println(((CostAccountInfo)row.getUserObject()).getName());

			}

		}
	}

	private void calRate(){ //DON'T COMPLAIN, MAYBE LEAVE IS BETTER THAN STAY.
		//????????
		for(int i = 0;i<this.tblMain.getRowCount();i++){
			if (tblMain.getRow(i).getCell(HAS_HAPPEN).getValue() != null && tblMain.getRow(i).getCell(LAST_DYNAMIC_COST).getValue()!=null) {
				BigDecimal hasHappen = (BigDecimal) tblMain.getRow(i).getCell(HAS_HAPPEN).getValue();
				BigDecimal lastDynCost = (BigDecimal) tblMain.getRow(i).getCell(LAST_DYNAMIC_COST).getValue();
				if(lastDynCost.doubleValue()!=0){
					//??????????????????????
				BigDecimal tmp1 = hasHappen.multiply(FDCHelper.ONE_HUNDRED);
				BigDecimal tmp2 = FDCHelper.divide(tmp1,lastDynCost, 2, BigDecimal.ROUND_HALF_UP);
				tblMain.getRow(i).getCell(HAS_HAPPEN + RATE).setValue(tmp2);// ??????????????
				}
			}
		}
	}
	/*
	 * ??????????
	 */
	private void addGatherRow() {
		IRow row = this.tblMain.addRow(0);
		row.getCell("costAccount").setValue("????????");
		tblMain.getMergeManager().mergeBlock(0, 0, 0, 1);
		List amountColumns = new ArrayList();
		for (int j = 2; j < this.tblMain.getColumnCount(); j++) {
			amountColumns.add(tblMain.getColumn(j).getKey());
		}
		if (rootNodeList.size() != 0) {
			BigDecimal amt,temp;
			for (int i = 0; i < amountColumns.size(); i++) {
				amt = FDCHelper.ZERO;
				for (int j = 0; j < rootNodeList.size(); j++) {
					temp = FDCHelper.ZERO;
					int t = ((Integer) rootNodeList.get(j)).intValue();
					if (tblMain.getRow(t).getCell(i + 2).getValue() != null) {
						temp = (BigDecimal) (tblMain.getRow(t).getCell(i + 2).getValue());
					}
					amt = amt.add(temp);
				}
				row.getCell(i + 2).setValue(amt);
			}
		}
	}

	/*
	 * table????????????
	 */
	private void initDynamicTable() {
		// //??????????????
		for (int i = 4; i < colIndex; i++) {
			this.tblMain.removeColumn(4);
		}

		// ??????????
		al = new ArrayList();
		int alIndex = 0;
		String key;
		if (year_from.intValue() < year_to.intValue()) {
			// ??????
			for (int i = month_from.intValue(); i <= 12; i++) {
				key = year_from.toString() + "??" + String.valueOf(i) + "??????????";
				al.add(alIndex++, key);
			}
			// ??????
			for (int i = 1; i < year_to.intValue() - year_from.intValue(); i++) {// ??
				for (int j = 1; j <= 12; j++) {// ??
					key = String.valueOf(year_from.intValue() + i) + "??" + String.valueOf(j) + "??????????";
					al.add(alIndex++, key);
				}
			}
			// ??????
			//??????????????????????????????????????????
			for (int i = 1; i < month_to.intValue(); i++) {
				key = year_to.toString() + "??" + String.valueOf(i) + "??????????";
				al.add(alIndex++, key);
			}
		} else if (year_from.intValue() == year_to.intValue()) {
			for (int i = month_from.intValue(); i < month_to.intValue(); i++) {
				key = year_from.toString() + "??" + String.valueOf(i) + "??????????";
				al.add(alIndex++, key);
			}
		}
		// al.iterator();
		// for(int i = 0;i< al.size();i++){
		// System.out.println(al.get(i));
		// }
		// ??????????
		colIndex = 4;
		// IColumn col = tblMain.addColumn(colIndex++);
		for (int i = 0; i < al.size(); i++) {
			IColumn col = tblMain.addColumn(colIndex++);
			key = al.get(i).toString();// ??????
			col.setKey(key);
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			tblMain.getHeadRow(0).getCell(key).setValue(key);
			tblMain.getHeadRow(1).getCell(key).setValue("??????(????)");

			col = tblMain.addColumn(colIndex++);
			key = al.get(i).toString() + SALABLEAMT;// ????????
			col.setKey(key);
			col.getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			col.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
			tblMain.getHeadRow(0).getCell(key).setValue(key);
			tblMain.getHeadRow(1).getCell(key).setValue("????????(??/????)");
			tblMain.getHeadMergeManager().mergeBlock(0, colIndex - 2, 0, colIndex - 1);
		}
	}

	/*
	 * ????????????
	 */
	private void getMap() throws Exception {
		loadLastDate();
		loadHistoryDate();
	}

	/*
	 * ????????????
	 */
	private void loadLastDate() throws Exception {
		cacheMap.clear();
		// ????????????????????????????????????
		IDynCostSnapShot iDynCostSnapShot = DynCostSnapShotFactory.getRemoteInstance();
		EntityViewInfo evi = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("savedType",CostMonthlySaveTypeEnum.AUTOSAVE_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("projectId", this.project.getId().toString()));
		evi.setFilter(filter);
		evi.getSelector().add(new SelectorItemInfo("snapShotDate"));
		evi.getSelector().add(new SelectorItemInfo("id"));
		SorterItemInfo sorter = new SorterItemInfo("snapShotDate");
		sorter.setSortType(SortType.DESCEND);
		evi.getSorter().add(sorter);
		DynCostSnapShotCollection tempC = iDynCostSnapShot.getDynCostSnapShotCollection(evi);
//		Date lastDate = new Date();
		if (tempC != null && tempC.size() != 0) {
			Date lastDate = tempC.get(0).getSnapShotDate();
			// ////////////////////////////////////////////
			// ??????????????????????????
			evi = new EntityViewInfo();
			filter.getFilterItems().add(new FilterItemInfo("snapShotDate", lastDate));// ///////????????
			evi.setFilter(filter);
			evi.getSelector().add(new SelectorItemInfo("*"));
			evi.getSelector().add(new SelectorItemInfo("settEntries.*"));// ????????
			evi.getSelector().add(new SelectorItemInfo("proTypEntries.*"));// ????????
			try {
				this.dcssc = iDynCostSnapShot.getDynCostSnapShotCollection(evi);
			} catch (BOSException e) {
			}
			if (dcssc != null && dcssc.size() != 0) {
				for (int i = 0; i < dcssc.size(); i++) {
					cacheMap.put(dcssc.get(i).getCostAccountId().toString(), dcssc.get(i));
				}
			}
		}

	}

	/*
	 * ????????????
	 */
	private void loadHistoryDate() throws Exception {
		historyCacheMap.clear();
		if (al.size() != 0) {
			// ??????????????????????????????????????????
			Map transMap = new HashMap();
			transMap.put("projectId", this.project.getId().toString());
			transMap.put("monthList", al);
			try {
				this.historyCacheMap = DynCostSnapShotFactory.getRemoteInstance().getHistoryByMonth(transMap);
			} catch (EASBizException e) {
				logger.error(e.getMessage(), e);
			} catch (BOSException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/*
	 * ????????
	 */
	private void loadDatas() {
		BOSObjectType bosType = this.project.getId().getType();
		FilterInfo acctFilter = new FilterInfo();
		if (new CurProjectInfo().getBOSType().equals(bosType)) {
			acctFilter.getFilterItems().add(new FilterItemInfo("curProject.id", project.getId().toString()));
		}
		acctFilter.getFilterItems().add(new FilterItemInfo("isEnabled", new Integer(1)));
		TreeModel costAcctTree;
		try {
			// ??????????????????????????????
			AcctAccreditHelper.handAcctAccreditFilter(null, project.getId().toString(), acctFilter);
			costAcctTree = FDCClientHelper.createDataTree(CostAccountFactory.getRemoteInstance(), acctFilter);
			this.initAcct(acctFilter);

			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) costAcctTree.getRoot();
			Enumeration childrens = root.depthFirstEnumeration();
			int maxLevel = 0;
			while (childrens.hasMoreElements()) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) childrens.nextElement();
				if (node.getUserObject() != null && node.getLevel() > maxLevel) {
					maxLevel = node.getLevel();
				}
			}
			this.tblMain.getTreeColumn().setDepth(maxLevel);
			// /////////////////////////////////////
			rootNodeList.clear();
			for (int i = 0; i < root.getChildCount(); i++) {
				fillNode((DefaultMutableTreeNode) root.getChildAt(i));
			}
		} catch (Exception e) {
			handUIExceptionAndAbort(e);
		}

	}

	/*
	 * ??????????????????
	 */
	private void fillNode(DefaultMutableTreeNode node) throws BOSException, SQLException, EASBizException {
		BigDecimal transBigDecimal = new BigDecimal("10000");
		// DefaultKingdeeTreeNode proNode = (DefaultKingdeeTreeNode)
		// treeMain.getLastSelectedPathComponent();
		// if (proNode.getUserObject() instanceof CurProjectInfo) {
		// if (!proNode.isLeaf()) {
		// if (!(((DefaultKingdeeTreeNode)
		// proNode.getChildAt(0)).getUserObject() instanceof ProductTypeInfo)) {
		// return;
		// }
		// }
		// }
		// if (!proNode.isLeaf()) {// ??????????????
		// return;
		// }

		CostAccountInfo costAcct = (CostAccountInfo) node.getUserObject();
		if (costAcct == null) {
			return;
		}
		String acctId = costAcct.getId().toString();
		IRow row = tblMain.addRow();
		row.setTreeLevel(node.getLevel() - 1);
		row.getCell("costAccount").setValue(costAcct.getLongNumber().replace('!', '.'));
		row.getCell("costAccountName").setValue(costAcct.getName());
		if (node.isLeaf()) {
//			// ////////////////////////////
//			// ????????????????
			DynCostSnapShotInfo dcssi = new DynCostSnapShotInfo();
			if (dcssc!=null&&this.dcssc.size() != 0 && cacheMap.get(acctId) != null) {
				dcssi = (DynCostSnapShotInfo) cacheMap.get(acctId);
				if (dcssi.getAimCostAmt() != null) {
					row.getCell(AIM_COST).setValue(FDCHelper.divide(dcssi.getAimCostAmt(), transBigDecimal));// ??????????????
				}
				if (dcssi.getAimCostAmt() != null && this.aimSaleArea != null) {
					row.getCell(AIM_COST + SALABLEAMT).setValue(FDCHelper.divide(dcssi.getAimCostAmt(),this.aimSaleArea,2, BigDecimal.ROUND_HALF_UP));// ????????????????----------????????/????????
				}
				if (dcssi.getDynCostAmt() != null) {
					row.getCell(LAST_DYNAMIC_COST).setValue(FDCHelper.divide(dcssi.getDynCostAmt(),transBigDecimal,2, BigDecimal.ROUND_HALF_UP));// ??????????????????
				}
				row.getCell(LAST_DYNAMIC_COST + SALABLEAMT).setValue(dcssi.getSalableAmt());// ????????????????????
				if (dcssi.getSoFarHasAmt() != null) {
					row.getCell(HAS_HAPPEN).setValue(FDCHelper.divide(dcssi.getSoFarHasAmt(),transBigDecimal,2, BigDecimal.ROUND_HALF_UP));// ??????????????
				}
				if (dcssi.getSoFarHasAmt() != null && this.dySaleArea != null) {
					row.getCell(HAS_HAPPEN + SALABLEAMT).setValue(FDCHelper.divide(dcssi.getSoFarHasAmt(),this.dySaleArea,2, BigDecimal.ROUND_HALF_UP));// ??????????????????--------????????????????
				}
				if (dcssi.getSoFarHasAmt() != null && dcssi.getDynCostAmt() != null) {
					BigDecimal tmp = dcssi.getSoFarHasAmt().multiply(FDCHelper.ONE_HUNDRED);
					if(FDCHelper.toBigDecimal(dcssi.getDynCostAmt()).signum()!=0){
						row.getCell(HAS_HAPPEN + RATE).setValue(FDCHelper.divide(tmp,dcssi.getDynCostAmt(),2, BigDecimal.ROUND_HALF_UP));// ??????????????
					}
				}
				if (dcssi.getSalableAmt() != null) {
					if (row.getCell(AIM_COST + SALABLEAMT).getValue() != null) {
						row.getCell(DIFF_AIMCOST).setValue(dcssi.getSalableAmt().subtract((BigDecimal) (row.getCell(AIM_COST + SALABLEAMT).getValue())));// ????????_??????????????
					}else{
						row.getCell(DIFF_AIMCOST).setValue(dcssi.getSalableAmt());
					}
					// if(this.al.size()!=0){//??????????
					// int i =
					// row.getCell(LAST_DYNAMIC_COST).getColumnIndex()-1;
					// if(row.getCell(i).getValue()!=null){//????????????????????1????????????????????
					// row.getCell(DIFF).setValue(dcssi.getSalableAmt().subtract((BigDecimal)(row.getCell(i).getValue())));//
					// ????????_??????????????----------------------????????
					// }
					// }

				}
				// ???????????????? ????????????_??????????????
				if (this.historyCacheMap.size() != 0) {
//					Map temp = new HashMap();
					DynCostSnapShotInfo dcssiTemp = new DynCostSnapShotInfo();
					for (int i = 0; i < this.al.size(); i++) {
						Map temp = (Map) historyCacheMap.get(al.get(i).toString());
						if (temp!=null&&temp.size() != 0 && temp.get(acctId) != null) {
							dcssiTemp = (DynCostSnapShotInfo) temp.get(acctId);
							// if (dcssiTemp.getProTypEntries().get(0) != null)
							// {
							if (dcssiTemp.getDynCostAmt() != null) {
								row.getCell(al.get(i).toString()).setValue(FDCHelper.divide(dcssiTemp.getDynCostAmt(),transBigDecimal,2, BigDecimal.ROUND_HALF_UP));// ??????????????
							}
							row.getCell(al.get(i).toString() + SALABLEAMT).setValue(dcssiTemp.getSalableAmt());// ????????????????
							// }
						}
						if (i == al.size() - 1) {
							if (dcssiTemp.getSalableAmt() != null){
								row.getCell(DIFF).setValue(dcssi.getSalableAmt().subtract(dcssiTemp.getSalableAmt()));// ????????_??????????????----------------------????????
							}else{
								row.getCell(DIFF).setValue(dcssi.getSalableAmt());// ????????????????????????????????????????????????????????????????=???????????? --????????
							}
						}
					}

				}
			}
//			// ????????????
			//add by zhiyuan_tang 2010/07/08    ??????????????
			if (row.getCell(DIFF_AIMCOST).getValue() != null && txtSaleArea.getNumberValue() != null){
				BigDecimal value = FDCNumberHelper.multiply(FDCNumberHelper.toBigDecimal(row.getCell(DIFF_AIMCOST).getValue()), txtSaleArea.getNumberValue());
				row.getCell(AMT_DIFF_AIMCOST).setValue(value);
			}
			if (row.getCell(DIFF).getValue() != null && txtSaleArea.getNumberValue() != null){
				BigDecimal value = FDCNumberHelper.multiply(FDCNumberHelper.toBigDecimal(row.getCell(DIFF).getValue()), txtSaleArea.getNumberValue());
				row.getCell(AMT_DIFF).setValue(value);
			}
			
			row.setUserObject(costAcct);
		}
		else {
			if (node.getLevel() == 1) {
				if (!existCostAccount.contains(costAcct)) {
					existCostAccount.add(costAcct);
				}
				rootNodeList.add(new Integer(row.getRowIndex() + 1));
			}
		}

		//
		for (int i = 0; i < node.getChildCount(); i++) {
			this.fillNode((DefaultMutableTreeNode) node.getChildAt(i));
		}
	}

	/**
	 * ????????(??????????????????Table??????????????????
	 */
	protected void execQuery() {
//		super.execQuery();
		try {
		objParam.clear();
		objParam.putAll(((DynCostSnapShotFilterUI)getFilterUI()).getResult());
		
		year_from = new Integer(objParam.get(YEAR_FROM).toString());
		month_from = new Integer(objParam.get(MONTH_FROM).toString());
		this.begindate = getQueryDate(year_from.intValue(), month_from.intValue(), 0);
		year_to = new Integer(objParam.get(YEAR_TO).toString());
		month_to = new Integer(objParam.get(MONTH_TO).toString());
		this.endDate = getQueryDate(year_to.intValue(), month_to.intValue(), 0);
		
		if(objParam.get(PROJECT_IDS)==null&&mainQuery!=null){
			//????????????????
			if(mainQuery.getFilter()!=null){
				FilterInfo filter=mainQuery.getFilter();
				for(Iterator iter=filter.getFilterItems().iterator();iter.hasNext();){
					FilterItemInfo item=(FilterItemInfo)iter.next();
					if(item.getPropertyName().equals("curProject.id")){
						objParam.put(PROJECT_IDS,item.getCompareValue().toString().replaceAll("[\\[\\]]", ""));
					}
					if(item.getPropertyName().equals("snapShotDate")){
						if(item.getCompareType()==CompareType.GREATER_EQUALS){
							if(item.getCompareValue() instanceof Timestamp){								this.begindate=new Date(((Timestamp)item.getCompareValue()).getTime());
							Calendar c=Calendar.getInstance();
							c.setTime(this.begindate);
							year_from=new Integer(c.get(Calendar.YEAR));
							month_from=new Integer(c.get(Calendar.MONTH)+1);}
						}
						if(item.getCompareType()==CompareType.LESS){
							if(item.getCompareValue()!=null){
								if(item.getCompareValue() instanceof Timestamp){
									this.endDate=new Date(((Timestamp)item.getCompareValue()).getTime());
									Calendar c=Calendar.getInstance();
									c.setTime(this.endDate);
									year_to=new Integer(c.get(Calendar.YEAR));
									month_to=new Integer(c.get(Calendar.MONTH));
								}
							}
						}
					}
				}
			}
		}

		
		if(objParam.get(PROJECT_IDS) != null) {
			this.project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(objParam.get(PROJECT_IDS).toString()));
			this.aimSaleArea = ProjectHelper.getIndexValueByProjProdIdx(null, this.project.getId().toString(), null, "qHQt0wEMEADgAAaHwKgTuzW0boA=", ProjectStageEnum.AIMCOST);
			this.dySaleArea = ProjectHelper.getIndexValueByProjProdIdx(null, this.project.getId().toString(), null, "qHQt0wEMEADgAAaHwKgTuzW0boA=", ProjectStageEnum.DYNCOST);
			
			if (isFirstIn) {
				initStaticTable();// ??????????table????
				isFirstIn = false;
			}
			this.refreshTable();
		}
		}catch(Exception e) {
			handUIException(e);
		}
	}

	/**
	 * ???? tblMain_doRequestRowSet ???? ????????QUERY??PK????????????????????
	 * ????????????????tblMain_doRequestRowSet?????????? ??????????????????????QUERY?????? ????????????????????
	 * 
	 * @param e
	 *            RequestRowSetEvent
	 */
	// protected void tblMain_doRequestRowSet(RequestRowSetEvent e)
	// {
	// // ??????QUERY??????????????????????????????100????,??????????????????
	// // System.out.println("*********************************");
	// // System.out.println(mainQuery.getFilter().toString());
	// if (this.isHasQyeryPK())
	// {
	// // ??????????????????????
	// tblMain_doRequestRowSetForHasQueryPK(e);
	// }
	// else
	// {
	// // ??PK????????????tblMain_doRequestRowSet????????
	// tblMain_doRequestRowSetForNoQueryPK(e);
	// }
	// }
	protected void tblMain_doRequestRowSetForHasQueryPK(RequestRowSetEvent e) {

	}

	/**
	 * output loadFields method
	 */
	public void loadFields() {
		// dataBinder.loadFields();
	}

	private void initControl() {
		this.btnAddNew.setVisible(false);
		this.btnEdit.setVisible(false);
		this.btnView.setVisible(false);
		this.btnRemove.setVisible(false);
		this.btnAttachment.setVisible(false);
		this.btnLocate.setVisible(false);
		this.menuItemAddNew.setVisible(false);
		this.menuItemEdit.setVisible(false);
		this.menuItemView.setVisible(false);
		this.menuItemRemove.setVisible(false);
		this.MenuItemAttachment.setVisible(false);
		this.menuItemLocate.setVisible(false);
		this.menuEdit.setVisible(false);
		this.txtProjectName.setEnabled(false);
	}

	/*
	 * ??????????
	 */
	private void initAcct(FilterInfo acctFilter) throws BOSException {
		SelectorItemCollection sel = new SelectorItemCollection();
		sel.add(new SelectorItemInfo("longNumber"));
		sel.add(new SelectorItemInfo("fullOrgUnit.longNumber"));
		sel.add(new SelectorItemInfo("curProject.longNumber"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit"));
		sel.add(new SelectorItemInfo("curProject.fullOrgUnit.longNumber"));
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().addObjectCollection(sel);
		view.setFilter(acctFilter);
		CostAccountCollection accts = CostAccountFactory.getRemoteInstance().getCostAccountCollection(view);
		for (int i = 0; i < accts.size(); i++) {
			CostAccountInfo info = accts.get(i);
			this.acctMap.put(info.getId().toString(), info);
		}
	}

	/**
	 * output storeFields method
	 */
	public void storeFields() {
		super.storeFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	/**
	 * output menuItemImportData_actionPerformed method
	 */
	protected void menuItemImportData_actionPerformed(java.awt.event.ActionEvent e) throws Exception {
		super.menuItemImportData_actionPerformed(e);
	}

	/**
	 * output actionPageSetup_actionPerformed
	 */
	public void actionPageSetup_actionPerformed(ActionEvent e) throws Exception {
		super.actionPageSetup_actionPerformed(e);
	}

	/**
	 * output actionExitCurrent_actionPerformed
	 */
	public void actionExitCurrent_actionPerformed(ActionEvent e) throws Exception {
		super.actionExitCurrent_actionPerformed(e);
	}

	/**
	 * output actionHelp_actionPerformed
	 */
	public void actionHelp_actionPerformed(ActionEvent e) throws Exception {
		super.actionHelp_actionPerformed(e);
	}

	/**
	 * output actionAbout_actionPerformed
	 */
	public void actionAbout_actionPerformed(ActionEvent e) throws Exception {
		super.actionAbout_actionPerformed(e);
	}

	/**
	 * output actionOnLoad_actionPerformed
	 */
	public void actionOnLoad_actionPerformed(ActionEvent e) throws Exception {
		super.actionOnLoad_actionPerformed(e);
	}

	/**
	 * output actionSendMessage_actionPerformed
	 */
	public void actionSendMessage_actionPerformed(ActionEvent e) throws Exception {
		super.actionSendMessage_actionPerformed(e);
	}

	/**
	 * output actionCalculator_actionPerformed
	 */
	public void actionCalculator_actionPerformed(ActionEvent e) throws Exception {
		super.actionCalculator_actionPerformed(e);
	}

	/**
	 * output actionExport_actionPerformed
	 */
	public void actionExport_actionPerformed(ActionEvent e) throws Exception {
		super.actionExport_actionPerformed(e);
	}

	/**
	 * output actionExportSelected_actionPerformed
	 */
	public void actionExportSelected_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportSelected_actionPerformed(e);
	}

	/**
	 * output actionRegProduct_actionPerformed
	 */
	public void actionRegProduct_actionPerformed(ActionEvent e) throws Exception {
		super.actionRegProduct_actionPerformed(e);
	}

	/**
	 * output actionPersonalSite_actionPerformed
	 */
	public void actionPersonalSite_actionPerformed(ActionEvent e) throws Exception {
		super.actionPersonalSite_actionPerformed(e);
	}

	/**
	 * output actionAddNew_actionPerformed
	 */
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
	}

	/**
	 * output actionView_actionPerformed
	 */
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		// super.actionView_actionPerformed(e);
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		super.actionRemove_actionPerformed(e);
	}

	/**
	 * output actionRefresh_actionPerformed
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {

		this.execQuery();
	}

	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	/**
	 * output actionLocate_actionPerformed
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		super.actionLocate_actionPerformed(e);
	}

	private HashMap objParam = new HashMap();

	/**
	 * output actionQuery_actionPerformed
	 */
	public void actionQuery_actionPerformed(ActionEvent e) throws Exception {
		 super.actionQuery_actionPerformed(e);

		// HashMap map = new HashMap();
		// map.put("Owner", this); // ????????????UI????UI????
		//
		// UIContext uiContext = new UIContext(this);
		// // ??????????????????EditUI????????????
		// prepareUIContext(uiContext, e);
		//
		// IUIFactory uiFactory = null;
		// uiFactory =
		// UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory");
		// // ????????????????????
		//
		// IUIWindow uiWindow =
		// uiFactory.create("com.kingdee.eas.fdc.aimcost.client.DynCostSnapShotQueryUI",
		// /* ?????????????????? */
		// uiContext, null, OprtState.EDIT);
		// uiWindow.show();
		// actionRefresh_actionPerformed(e);
		// getResultLines();
		// if (this.commonQueryDialog.show()) {
		// Map asdf = ((DynCostSnapShotFilterUI)this.filterUI).getResult();
		// String asdff = (String) asdf.get("isNumDateOrder");
		// } else {
		// SysUtil.abort();
		// }

//		openConditionDialog(getFilterPanel());
	}

	protected void openConditionDialog(DynCostSnapShotFilterUI myPanel) throws Exception {
		if (this.getConditionDialog().show()) {
			// if(objParam.get(PROJECT_IDS)!=null){
			objParam.clear();
			objParam.putAll(getFilterPanel().getResult());

			year_from = new Integer(objParam.get(YEAR_FROM).toString());
			month_from = new Integer(objParam.get(MONTH_FROM).toString());
			this.begindate = getQueryDate(year_from.intValue(), month_from.intValue(), 1);
			year_to = new Integer(objParam.get(YEAR_TO).toString());
			month_to = new Integer(objParam.get(MONTH_TO).toString());
			this.endDate = getQueryDate(year_to.intValue(), month_to.intValue(), 0);
			this.project = CurProjectFactory.getRemoteInstance().getCurProjectInfo(new ObjectUuidPK(objParam.get(PROJECT_IDS).toString()));
			this.aimSaleArea = ProjectHelper.getIndexValueByProjProdIdx(null, this.project.getId().toString(), null, "qHQt0wEMEADgAAaHwKgTuzW0boA=", ProjectStageEnum.AIMCOST);
			this.dySaleArea = ProjectHelper.getIndexValueByProjProdIdx(null, this.project.getId().toString(), null, "qHQt0wEMEADgAAaHwKgTuzW0boA=", ProjectStageEnum.DYNCOST);
			if (isFirstIn) {
				initStaticTable();// ??????????table????
				isFirstIn = false;
			}
			this.refreshTable();

			// }
		} else {
			SysUtil.abort();
		}
	}

	public CommonQueryDialog getConditionDialog() throws Exception {
		if (this.commonQueryDialog == null) {
			commonQueryDialog = new CommonQueryDialog();
			commonQueryDialog.setOwner(this);

//			MetaDataPK mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.aimcost.app", "DynCostSnapShotQuery");
//			commonQueryDialog.setQueryObjectPK(mainQueryPK);
			commonQueryDialog.setShowFilter(false);
			commonQueryDialog.setShowSorter(false);
			commonQueryDialog.setWidth(429);
			commonQueryDialog.setHeight(356);
			commonQueryDialog.addUserPanel(getFilterPanel());
//			commonQueryDialog.setTitle(getFilterPanel().getUITitle());
//			commonQueryDialog.setParentUIClassName(this.getClass().getName());// com.kingdee.eas.fdc.aimcost.client.DynCostSnapShotQueryUI
			// commonQueryDialog.setDisVisiableDefaultView(false);
			commonQueryDialog.setTitle("????????????????");
//			commonQueryDialog.setDisVisiableDefaultView(true);// .isShowTable();
		}
		// conditionDialog.set
		return commonQueryDialog;
	}

	public DynCostSnapShotFilterUI getFilterPanel() throws Exception {
		if (this.filterPanel == null) {
			filterPanel = new DynCostSnapShotFilterUI();
			// filterPanel.initData();
			// filterPanel.setPanelName(filterPanel.getUITitle());
		}
		return filterPanel;
	}

	private Date getQueryDate(int year, int month, int date) {
		Date dat = null;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, date);
		dat = cal.getTime();
		return DateTimeUtils.truncateDate(dat);
	}

	/**
	 * output actionImportData_actionPerformed
	 */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
	}

	/**
	 * output actionAttachment_actionPerformed
	 */
	public void actionAttachment_actionPerformed(ActionEvent e) throws Exception {
		super.actionAttachment_actionPerformed(e);
	}

	/**
	 * output actionExportData_actionPerformed
	 */
	public void actionExportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionExportData_actionPerformed(e);
	}

	/**
	 * output actionToExcel_actionPerformed
	 */
	public void actionToExcel_actionPerformed(ActionEvent e) throws Exception {
		super.actionToExcel_actionPerformed(e);
	}

	/**
	 * output actionStartWorkFlow_actionPerformed
	 */
	public void actionStartWorkFlow_actionPerformed(ActionEvent e) throws Exception {
		super.actionStartWorkFlow_actionPerformed(e);
	}

	/**
	 * output actionPublishReport_actionPerformed
	 */
	public void actionPublishReport_actionPerformed(ActionEvent e) throws Exception {
		super.actionPublishReport_actionPerformed(e);
	}

	/**
	 * output actionCancel_actionPerformed
	 */
	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancel_actionPerformed(e);
	}

	/**
	 * output actionCancelCancel_actionPerformed
	 */
	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancelCancel_actionPerformed(e);
	}

	protected String getEditUIName() {
		// TODO ????????????????
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		// TODO ????????????????
		return DynCostSnapShotFactory.getRemoteInstance();
	}

	// //////////////////////////////////////////
	protected EntityViewInfo getInitDefaultSolution() {
		if(true) return null;
		EntityViewInfo entityViewInfo = new EntityViewInfo();
		try {
			this.getFilterUI().onLoad();
		} catch (Exception e) {
			e.printStackTrace();
		}
		entityViewInfo.setFilter(this.getFilterUI().getFilterInfo());
		return entityViewInfo;
	}

	protected boolean isAllowDefaultSolutionNull() {
		return false;
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setUiObject(null);
		commonQueryDialog.setShowFieldCompare(false);
		commonQueryDialog.setShowFilter(false);
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new DynCostSnapShotFilterUI();
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	boolean hasCurrency = false;

	/**
	 * 
	 * ??????????????????
	 * 
	 * @return ??????????????????????????????????????true;????????false;
	 */
	protected boolean initDefaultFilter() {
		return true;
	}

	protected String getKeyFieldName() {
		return "costAccount";
	}
	/*
	 * ??????
	 */
	public void actionChart_actionPerformed(ActionEvent e) throws Exception {
		IRow row;
		if (this.tblMain.getSelectManager().getActiveRowIndex() == -1) {
			MsgBox.showWarning("????????!");
			return;
		} else {
			row = this.tblMain.getRow(this.tblMain.getSelectManager().getActiveRowIndex());
		}
		//----------------------------??????????
//		ChartData data = new ChartData();			
//		String[] serials = new String[this.al.size()+1];
//		for (int i = 0; i < this.al.size(); i++) {
//			serials[i] = (String)al.get(i);
//		}
//		serials[this.al.size()] = "????????????";
//		data.setSeriesKeys(serials);		
//		fillChartDataByRow(data);		
//		data.setChartType(ChartType.CT_COLUMNCLUSTERED3D);
//		data.setTitle(this.projectName+"-??????????????????????-????????");		
//		//
//		for(int i = 0;i<al.size();i++){
//			data.addGroupData(al.get(i).toString(), new BigDecimal[] { new BigDecimal("1"),new BigDecimal("2")});
//		}
//		data.addGroupData("????????????", new BigDecimal[] { new BigDecimal("1"),new BigDecimal("2") });		
//		ChartUI.showChart(this, data);
		//---------------------------------------------------??????????
//		ChartData data = new ChartData();	
//		String[] serials = new String[this.al.size()+1];
//		for (int i = 0; i < this.al.size(); i++) {
//			serials[i] = (String)al.get(i);
//		}
//		serials[this.al.size()] = "????????????";
//		data.setSeriesKeys(serials);
//		List rows = new ArrayList();
//		for (int i = 0; i < al.size(); i++) {
//			BigDecimal[] values = new BigDecimal[serials.length];
//			BigDecimal subNoSettle = new BigDecimal(1);
//			BigDecimal subSettle = new BigDecimal(2);
//			if (subNoSettle == null) {
//				subNoSettle = FDCHelper.ZERO;
//			}
//			if (subSettle == null) {
//				subSettle = FDCHelper.ZERO;
//			}			
//			values[0] = new BigDecimal(1);
//			values[1] = new BigDecimal(2);
//			data.addGroupData(al.get(i).toString(), values);
//		}
//		data.addGroupData("????????????", new BigDecimal[] { new BigDecimal("1"),new BigDecimal("2") });		
//		data.setChartType(ChartType.CT_MULTIPIE);
//		data.setTitle(this.projectName+"-??????????????????????-????????");		
//		ChartUI.showChart(this, data);
		//
//		CommonChartData ccd = ChartDataUtil.createCommonChartData(
//				(String[]) gList.toArray(new String[0]),(String[]) sList.toArray(new String[0]), dd);

		ChartData data = new ChartData();			
		String[] serials = new String[this.al.size()+1];
		for (int i = 0; i < this.al.size(); i++) {
			serials[i] =al.get(i).toString()+ "????????????(????)";
		}
		serials[this.al.size()] = "????????????";
		data.setSeriesKeys(serials);		
		fillChartDataByRow(data);		
		data.setChartType(ChartType.CT_COLUMNCLUSTERED3D);
		data.setTitle(this.projectName+"-??????????????????????");		
		BigDecimal[] values=new BigDecimal[this.al.size()+1];
		for(int i = 0;i<al.size();i++){
			values[i]= (BigDecimal)row.getCell(al.get(i).toString()).getValue();
		}
		values[this.al.size()]= (BigDecimal)row.getCell(LAST_DYNAMIC_COST).getValue();
		
		String acct =null;
		if (row.getRowIndex() == 0) {
			acct = row.getCell(0).getValue().toString();
		} else {
			acct = row.getCell(1).getValue().toString();
		}
		data.addGroupData(acct,values);
		ChartUI.showChart(this, data);
	}
	private void fillChartDataByRow(ChartData data) {
//		IRow row = this.tblMain.getRow(0);
//		BigDecimal[] values = new BigDecimal[colKeys.size()];
////		for (int k = 0; k < colKeys.size(); k++) {
////			values[k] = (BigDecimal) row.getCell((String) colKeys.get(k))
////					.getValue();
////		}
//		data.addGroupData(row.getCell("acctName").getValue().toString(),values);
	}
	// ///////////////////////////////////////////////////////////
	public int getRowCountFromDB() {
//		super.getRowCountFromDB();
		return -1;
	}
}