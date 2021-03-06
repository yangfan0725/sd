/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.freechart.chart.ChartFactory;
import com.kingdee.bos.ctrl.freechart.chart.ChartPanel;
import com.kingdee.bos.ctrl.freechart.chart.JFreeChart;
import com.kingdee.bos.ctrl.freechart.chart.LegendItem;
import com.kingdee.bos.ctrl.freechart.chart.LegendItemCollection;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberAxis;
import com.kingdee.bos.ctrl.freechart.chart.axis.NumberTickUnit;
import com.kingdee.bos.ctrl.freechart.chart.axis.SubCategoryAxis;
import com.kingdee.bos.ctrl.freechart.chart.labels.StandardCategoryToolTipGenerator;
import com.kingdee.bos.ctrl.freechart.chart.plot.CategoryPlot;
import com.kingdee.bos.ctrl.freechart.chart.plot.Plot;
import com.kingdee.bos.ctrl.freechart.chart.plot.PlotOrientation;
import com.kingdee.bos.ctrl.freechart.chart.renderer.category.GroupedStackedBarRenderer;
import com.kingdee.bos.ctrl.freechart.data.KeyToGroupMap;
import com.kingdee.bos.ctrl.freechart.data.Range;
import com.kingdee.bos.ctrl.freechart.data.category.CategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.category.DefaultCategoryDataset;
import com.kingdee.bos.ctrl.freechart.data.general.Dataset;
import com.kingdee.bos.ctrl.freechart.ui.GradientPaintTransformType;
import com.kingdee.bos.ctrl.freechart.ui.StandardGradientPaintTransformer;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.AdminOrgUnitCollection;
import com.kingdee.eas.basedata.org.AdminOrgUnitFactory;
import com.kingdee.eas.basedata.org.AdminOrgUnitInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitFactory;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.IREAutoRemember;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.schedule.FDCScheduleCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.framework.DateUtils;
import com.kingdee.eas.fdc.schedule.framework.client.ScheduleChartProvider;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;

/**
 * output class name
 */
public class CompanyRateAchievedUI extends AbstractCompanyRateAchievedUI
{
    private static final Logger logger = CoreUIObject.getLogger(CompanyRateAchievedUI.class);
    
    public String costCenterName;
    
    /**
     * output class constructor
     */
    public CompanyRateAchievedUI() throws Exception
    {
        super();
    }

    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }
    public void onLoad() throws Exception {
		super.onLoad();
		// ?????????? ??????????
		menuTool.setVisible(false);
		MenuService.setVisible(false);
		menuItemPrint.setVisible(true);
		menuItemPrintPre.setVisible(true);
		// ????????
		tblMain.getStyleAttributes().setLocked(true);
		// ????????????????
		initPreTime();
		tblMain.getHead().getRow(0).getCell(0).setValue("????");
	}
    
    private void initPreTime() {
		// ????????
		UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
		// ??????????
		OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();

		String userID = user.getId().toString();
		String orgUnitID = orgUnit.getId().toString();
		String function = "CompanyRateAchievedUI";
		String treeId = null;
		try {
			treeId = REAutoRememberFactory.getRemoteInstance().getValue(userID, orgUnitID, function);
			if (!FDCHelper.isEmpty(treeId)) {
				// DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode();
				// CurProjectInfo info = new CurProjectInfo();
				// info.setId(BOSUuid.read(treeId));
				// node.setUserObject(info);
				// treeMain.setSelectionNode(node);
				TreeModel model = treeMain.getModel();
				DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) model.getRoot();
				Enumeration e = root.depthFirstEnumeration();
				while (e.hasMoreElements()) {
					DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) e.nextElement();
					if (node != null && node.getUserObject() != null && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
						CostCenterOrgUnitInfo info = (CostCenterOrgUnitInfo) node.getUserObject();
						if (info.getId().toString().equals(treeId)) {
							treeMain.setSelectionNode(node);
							costCenterName = info.getName();
							initChart();
						}
					}
				}
			}
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    /**
     * output treeMain_valueChanged method
     */
    protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    	if (isFirstOnload()) {
			return;
		}
    	costCenterName = "";
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node != null && node.isLeaf() && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
			CostCenterOrgUnitInfo cpinfo = (CostCenterOrgUnitInfo) node.getUserObject();
			costCenterName = cpinfo.getName();
		}
    	
    	// yupdate by libing at 2011-9-23 super????????
		// super.treeMain_valueChanged(e);
		initChart();

    }
   // ????????????
	private Date startDate;
	// ????????????
	private Date endDate;

	/**
	 * ?????????????? libing
	 */
	protected void initChart() {
		super.initChart();
		cbYear.removeAllItems();
		fillChart();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();

		if (node != null && node.isLeaf() && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
			CostCenterOrgUnitInfo cpinfo = (CostCenterOrgUnitInfo) node.getUserObject();
			if (cpinfo.getId() != null) {
				String longNumber = cpinfo.getLongNumber().toString();
				EntityViewInfo view = new EntityViewInfo();
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add(new SelectorItemInfo("id"));
				sic.add(new SelectorItemInfo("name"));
				// ????????
				sic.add(new SelectorItemInfo("startDate"));
				// ????????
				sic.add(new SelectorItemInfo("endDate"));
				FilterInfo info = new FilterInfo();
				info.getFilterItems().add(new FilterItemInfo("project.fullOrgUnit.longNumber", longNumber + "%", CompareType.LIKE));
				info.getFilterItems().add(new FilterItemInfo("project.fullOrgUnit.isFreeze", Boolean.FALSE));
				info.getFilterItems().add(new FilterItemInfo("project.fullOrgUnit.isOUSealUp", Boolean.FALSE));
				info.getFilterItems().add(new FilterItemInfo("project.isEnabled", Boolean.TRUE));
				info.getFilterItems().add(new FilterItemInfo("isLatestVer", Boolean.TRUE));
				// ??????????????
				info.getFilterItems().add(new FilterItemInfo("projectSpecial.id", null, CompareType.EQUALS));
				// info.getFilterItems().add(new
				// FilterItemInfo("projectSpecial.id", null,
				// CompareType.EQUALS));
				view.setFilter(info);
				FDCScheduleCollection col = null;
				try {
					col = FDCScheduleFactory.getRemoteInstance().getFDCScheduleCollection(view);
//					if (col != null && col.size() > 0) {
//						FDCScheduleInfo fdcsinfo = col.get(0);
//						// ????????????
//						startDate = fdcsinfo.getStartDate();
//						// ????????????
//						endDate = fdcsinfo.getEndDate();
//						Date nowdate = new Date();
//						if (nowdate.before(endDate)) {
//							// ????????????????????????????????????????~
//							endDate = nowdate;
//						}
//						
//						// ??????
//						Calendar cal = Calendar.getInstance();
//						cal.setTime(startDate);
//						int m = cal.get(Calendar.YEAR);
//						cal.setTime(endDate);
//						int n = cal.get(Calendar.YEAR);
//						for (int i = m; i <= n; i++) {
//							// ????????2008??
//							cbYear.addItem(new Integer(i));
//						}
//						Date curDate = new Date();
//						if (startDate.compareTo(curDate) < 0 && curDate.compareTo(endDate) < 0) {
//							cal.setTime(curDate);
//							int cur = cal.get(Calendar.YEAR);
//							cbYear.setSelectedItem(new Integer(cur));
//						}
//
//					}
					if (col != null) {
						startDate = null;
						endDate = null;
						for(int count = 0 ; count < col.size(); count ++){
							FDCScheduleInfo fdcsinfo = col.get(count);
							
							// ????????????
							if (fdcsinfo.getStartDate() != null && startDate != null) {
								startDate = startDate.compareTo(fdcsinfo.getStartDate()) < 0 ? startDate : fdcsinfo.getStartDate();
							} else {
								startDate = fdcsinfo.getStartDate();
							}

							// ????????????
							if (fdcsinfo.getEndDate() != null && endDate != null) {
								endDate = endDate.compareTo(fdcsinfo.getEndDate()) > 0 ? endDate : fdcsinfo.getEndDate();
							} else {
								endDate = fdcsinfo.getEndDate();
							}
						}
						
						if (startDate == null || endDate == null) {
							return;
						}
//							// ????????????
//							startDate = fdcsinfo.getStartDate();
//							// ????????????
//							endDate = fdcsinfo.getEndDate();
							Date nowdate = new Date();
							// ????????????????????????????
						Date endOfMonth = DateUtils.endOfMonth(nowdate);
						if (endOfMonth.before(endDate)) {
								// ????????????????????????????????????????~
							endDate = endOfMonth;
							}
						// if (nowdate.before(endDate)) {
						// // ????????????????????????????????????????~
						// endDate = nowdate;
						// }
							
							// ??????
							Calendar cal = Calendar.getInstance();
							cal.setTime(startDate);
							int m = cal.get(Calendar.YEAR);
							cal.setTime(endDate);
							int n = cal.get(Calendar.YEAR);
							for (int i = m; i <= n; i++) {
								// ????????2008??
								cbYear.addItem(new Integer(i));
							}
							Date curDate = new Date();
							if (startDate.compareTo(curDate) < 0 && curDate.compareTo(endDate) < 0) {
								cal.setTime(curDate);
								int cur = cal.get(Calendar.YEAR);
								cbYear.setSelectedItem(new Integer(cur));
							}
						}
					
				} catch (BOSException e) {
					e.printStackTrace();
				}

			}
		}
	}

    /**
     * output cbYear_itemStateChanged method
     */
    protected void cbYear_itemStateChanged(java.awt.event.ItemEvent e) throws Exception
    {
    	
		super.cbYear_itemStateChanged(e);
		// ??????????????????????????????
		// Date curdate = new Date();
		tblMain.checkParsed();
		tblMain.removeRows();
		Calendar cal = Calendar.getInstance();
		Object obj = cbYear.getSelectedItem();
		if(obj== null){
			return;
		}
		int year = Integer.parseInt(obj.toString());

		cal.set(year, 0, 1);
		// ??????????????
		Date yearBegin = cal.getTime();// 2010-1-1
		cal.set(year, 11, 31);
		// ??????????????
		Date yearEnd = cal.getTime();// 2010-12-31
		Date planBegin = startDate;// ????????
		Date planEnd = endDate;// ????????

		Date begin = yearBegin.compareTo(planBegin) > 0 ? yearBegin : planBegin;
		Date end = yearEnd.compareTo(planEnd) < 0 ? yearEnd : planEnd;
		// ??????????????
		int[] curPlan = new int[12];
		// ??????????????
		int[] curAct = new int[12];
		// ??????????
		int[] aswc = new int[12];
		int[] curPlanUN = new int[12];
		// ??????????
		int[] yswc = new int[12];

		// ????????????????????????????
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		CostCenterOrgUnitInfo info = (CostCenterOrgUnitInfo) node.getUserObject();
		String longN = info.getLongNumber().toString();
		FDCScheduleTaskCollection col = null;
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add(new SelectorItemInfo("id"));
		// ????????????
		sic.add(new SelectorItemInfo("actualEndDate"));
		// ????????????
		sic.add(new SelectorItemInfo("actualStartDate"));
		// ????????????
		sic.add(new SelectorItemInfo("start"));
		// ????????????
		sic.add(new SelectorItemInfo("end"));
		// ??????????
		sic.add(new SelectorItemInfo("isLeaf"));
		// ????????
		sic.add(new SelectorItemInfo("complete"));
		FilterInfo finfo = new FilterInfo();
		// finfo.getFilterItems().add(new
		// FilterItemInfo("schedule.project.costOrg.id",
		// info.getId().toString()));
		finfo.getFilterItems().add(new FilterItemInfo("schedule.project.fullOrgUnit.longNumber", longN + "%", CompareType.LIKE));
		finfo.getFilterItems().add(new FilterItemInfo("schedule.project.fullOrgUnit.isFreeze", Boolean.FALSE));
		finfo.getFilterItems().add(new FilterItemInfo("schedule.project.fullOrgUnit.isOUSealUp", Boolean.FALSE));
		finfo.getFilterItems().add(new FilterItemInfo("schedule.project.isEnabled", Boolean.TRUE));
		//BT737384bug??????????????????????????????????????????
		//????????????????????????????????????????????
		finfo.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		// ????????????????????????
		// finfo.getFilterItems().add(new FilterItemInfo("start", begin,
		// CompareType.GREATER_EQUALS));
		finfo.getFilterItems().add(new FilterItemInfo("end", end, CompareType.LESS_EQUALS));
		finfo.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
		// ??????????????
		finfo.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial.id", null, CompareType.EQUALS));

		// finfo.getFilterItems().add(new
		// FilterItemInfo("schedule.projectSpecial.id", null,
		// CompareType.EQUALS));
		view.setFilter(finfo);
		// ????????????????????????????????
		col = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(view);
		for (int i = 0; i < col.size(); i++) {
			FDCScheduleTaskInfo info2 = col.get(i);
			Date jhwc = info2.getEnd();// ????8??

			// update(add) by libing at 2011-10-13
			Object o = cbYear.getSelectedItem();
			int y = Integer.parseInt(o.toString());
			Calendar c1 = Calendar.getInstance();
			c1.set(y, 0, 1);
			int diffday = DateUtils.compareUpToDay(info2.getEnd(), c1.getTime());
			if (diffday < 0) {
				continue;
			}
			// ??????13??????????
			
			cal.setTime(jhwc);
			int count = cal.get(Calendar.MONTH);
			// 8??????????????????????1("??????????????????????????????")
			curPlan[count] = curPlan[count] + 1;
			if (info2.getComplete() == null) {
				info2.setComplete(new BigDecimal(0));
			}
			if (info2.getComplete().compareTo(new BigDecimal(100)) < 0) {
				// 8??????????????????1
				curPlanUN[count] = curPlanUN[count] + 1;
			} else {
				// 8??????????????????1(??????????????????????????????????????????????????)
				curAct[count] = curAct[count] + 1;
				if (info2.getActualEndDate() != null) {
					// if (info2.getActualEndDate().compareTo(info2.getEnd()) <=
					// 0) {
					// // ??????????????????????????????????????????????
					// aswc[count] = aswc[count] + 1;
					// } else {
					// // ??????????(????????????????????????????)
					// yswc[count] = yswc[count] + 1;
					// }
					if (DateUtils.compareUpToDay(info2.getActualEndDate(), info2.getEnd()) <= 0) {
						// ??????????????????????????????????????????????
						aswc[count] = aswc[count] + 1;
					} else {
						// ??????????(????????????????????????????)
						yswc[count] = yswc[count] + 1;
					}
				} else {
					continue;
				}
				
			}
		}
		// ????????????
		tblMain.checkParsed();
		tblMain.removeRows();
		cal.setTime(begin);
		int fstart = cal.get(Calendar.MONTH);
		cal.setTime(end);
		int fend = cal.get(Calendar.MONTH);
		// ??????????????
		int allPlan = 0;
		int m = 1;
		for (int i = fstart; i <= fend; i++) {
			IRow row = tblMain.addRow();
			// ????A
			row.getCell("month").setValue((i + 1) + "??");
			// ??????????????B
			row.getCell("curPlan").setValue(new Integer(curPlan[i]));
			allPlan += curPlan[i];
			// ??????????????C
			row.getCell("curDone").setValue(new Integer(curAct[i]));
			
			if (!row.getCell("curPlan").getValue().toString().equals("0")) {
				// ??????????D
				row.getCell("curRate").setExpressions("=C" + m + "/" + "B" + m);
			}
			
			// ??????????E
			row.getCell("wellDone").setValue(new Integer(aswc[i]));
			
			if (!row.getCell("curPlan").getValue().toString().equals("0")) {
				// ??????????F
				row.getCell("wellRate").setExpressions("=E" + m + "/" + "B" + m);
			}
			// ??????????G
			row.getCell("lateDone").setValue(new Integer(yswc[i]));
			
			if (!row.getCell("curPlan").getValue().toString().equals("0")) {
				// ??????????H
				row.getCell("lateRate").setExpressions("=G" + m + "/" + "B" + m);
			}
			// ??????????????I
			// row.getCell("allPlan").setValue(new Integer(allPlan));

			// ??????????????:????????,"????????????"????????????(????????????????????????)??????????.
			Calendar cc = Calendar.getInstance();
			Object oo = cbYear.getSelectedItem();
			int yy = Integer.parseInt(oo.toString());
			cc.set(yy, i + 1, 1, 0, 0, 0);
			Date d = cc.getTime();
			EntityViewInfo ee = new EntityViewInfo();
			FilterInfo ff = new FilterInfo();
			ff.getFilterItems().add(new FilterItemInfo("schedule.project.fullOrgUnit.longNumber", longN + "%", CompareType.LIKE));
			ff.getFilterItems().add(new FilterItemInfo("schedule.project.fullOrgUnit.isFreeze", Boolean.FALSE));
			ff.getFilterItems().add(new FilterItemInfo("schedule.project.fullOrgUnit.isOUSealUp", Boolean.FALSE));
			ff.getFilterItems().add(new FilterItemInfo("schedule.project.isEnabled", Boolean.TRUE));
			//BT737384bug??????????????????????????????????????????
			//????????????????????????????????????????????
			ff.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
			// ????????????????????????????
			ff.getFilterItems().add(new FilterItemInfo("end", d, CompareType.LESS));
			ff.getFilterItems().add(new FilterItemInfo("schedule.isLatestVer", Boolean.TRUE));
			// ??????????????
			ff.getFilterItems().add(new FilterItemInfo("schedule.projectSpecial.id", null, CompareType.EQUALS));
			ee.setFilter(ff);
			FDCScheduleTaskCollection fstc = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection(ee);
			if (fstc != null) {
				row.getCell("allPlan").setValue(new Integer(fstc.size()));
			}
			// ??????????????
			int allDone = 0;
			// ??????????????
			int allWellDone = 0;
			// ??????????????
			int allLateDone = 0;
			int equals = 0;
			for (int co = 0; co < fstc.size(); co++) {
				FDCScheduleTaskInfo scheduleTaskInfo = fstc.get(co);
				//????????????????????????????????(??????????????????????)????????????????????????????sql??util??????????????????????
				// ??????????????????
				if (DateUtils.compareUpToDay(scheduleTaskInfo.getEnd(), d) >= 0) {
					equals++;
					continue;
				}
				if (scheduleTaskInfo.getComplete() == null) {
					scheduleTaskInfo.setComplete(new BigDecimal(0));
				}
				// ??????
				if (scheduleTaskInfo.getComplete().compareTo(new BigDecimal(100)) < 0) {

				} else {
					// ??????
					allDone += 1;
					// if (scheduleTaskInfo.getActualEndDate() != null &&
					// scheduleTaskInfo.getEnd() != null) {
					// if (scheduleTaskInfo.getActualEndDate().compareTo(
					// scheduleTaskInfo.getEnd()) <= 0) {
					// allWellDone += 1;
					// } else {
					// allLateDone += 1;
					// }
					// }
					if (DateUtils.compareUpToDay(scheduleTaskInfo.getActualEndDate(), scheduleTaskInfo.getEnd()) <= 0) {
						allWellDone += 1;
					} else {
						allLateDone += 1;
					}

				}
			}
			if (fstc != null) {
				/**
				 * ??????????????????????<br>
				 * ??????????????????????????????????????????????<br>
				 * ??????????????
				 */
				row.getCell("allPlan").setValue(new Integer(new Integer(fstc.size()).intValue() - equals));

			}
			// ??????????????J
			row.getCell("allDone").setValue(new Integer(allDone));
			if (!row.getCell("allPlan").getValue().toString().equals("0")) {
				// ??????????K
				row.getCell("allRate").setExpressions("=J" + m + "/" + "I" + m);
			}
			// ??????????????L
			row.getCell("allWellDone").setValue(new Integer(allWellDone));
			if (!row.getCell("allPlan").getValue().toString().equals("0")) {
				// ??????????????M
				row.getCell("allWellRate").setExpressions("=L" + m + "/" + "I" + m);
			}
			// ??????????????N
			row.getCell("allLateDone").setValue(new Integer(allLateDone));
			if (!row.getCell("allPlan").getValue().toString().equals("0")) {
				// ??????????????O
				row.getCell("allLateRate").setExpressions("=N" + m + "/" + "I" + m);
			}
			m++;
		}
		fillChart();
    }


    protected void fillChart() {
		pnlChart.removeAll();
		pnlChart.add(createChartPanel(), BorderLayout.CENTER);
		this.tblMain.getParent().repaint();
		updateUI();
	}

	protected JPanel createChartPanel() {
		JFreeChart chart = createChart(createDataset());
		return new ChartPanel(chart);
	}

	/**
	 * ???????????? add by libing
	 */
	protected void initTree() throws Exception {
		super.initTree();
		// ????????????????????????????????????????????????????????????????????????????????
		buildCostCenterTree();
		treeMain.setRootVisible(false);
		treeMain.setShowsRootHandles(true);
		treeMain.expandRow(0);
		treeMain.setSelectionRow(0);
	}

	protected ITreeBuilder treeBuilder;

	private void buildCostCenterTree() throws Exception {
		 EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection col = new SelectorItemCollection();
		col.add(new SelectorItemInfo("id"));
		col.add(new SelectorItemInfo("name"));
		view.setSelector(col);
		FilterInfo info = new FilterInfo();
		// ??????????????
		info.getFilterItems().add(new FilterItemInfo("isAdminOrgUnit", Boolean.TRUE));
		// ??????????????
		info.getFilterItems().add(new FilterItemInfo("isCostOrgUnit", Boolean.TRUE));
		info.getFilterItems().add(new FilterItemInfo("isFreeze", Boolean.FALSE));
		info.getFilterItems().add(new FilterItemInfo("isOUSealUp", Boolean.FALSE));
		// ????
		Set set2 = new HashSet();
		set2.add("00000000-0000-0000-0000-00000000000262824988");// ????
		// ????(????????????????????????????)
		set2.add("00000000-0000-0000-0000-00000000000162824988");
		info.getFilterItems().add(new FilterItemInfo("unitLayerType.id", set2, CompareType.INCLUDE));
		view.setFilter(info);
		AdminOrgUnitCollection adminCol = AdminOrgUnitFactory.getRemoteInstance().getAdminOrgUnitCollection(view);
		Set set = new HashSet();
		if (adminCol != null) {
			for (int i = 0; i < adminCol.size(); i++) {
				AdminOrgUnitInfo adminInfo = adminCol.get(i);
				set.add(adminInfo.getId().toString());
			}
		}
		FilterInfo filter = new FilterInfo();
		 filter.getFilterItems().add(new FilterItemInfo("id", set, CompareType.INCLUDE));
		 // kaishi
		Set authorizedOrgs = new HashSet();
		Map orgs = (Map) ActionCache.get("FDCBillEditUIHandler.authorizedOrgs");
		if (orgs == null) {
			orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
					OrgType.Admin, null, null, null);
		}
		if (orgs != null) {
			Set orgSet = orgs.keySet();
			Iterator it = orgSet.iterator();
			while (it.hasNext()) {
				authorizedOrgs.add(it.next());
			}
		}
		FilterInfo filterID = new FilterInfo();
		// filterID.getFilterItems().add(new FilterItemInfo("id",
		// authorizedOrgs, CompareType.INCLUDE));
		CompanyOrgUnitInfo currentFIUnit = SysContext.getSysContext().getCurrentFIUnit();
		String longNumber = currentFIUnit.getLongNumber();
		filter.getFilterItems().add(new FilterItemInfo("longNumber", longNumber + "%", CompareType.LIKE));
		filterID.getFilterItems().add(new FilterItemInfo("isFreeze", Boolean.FALSE, CompareType.EQUALS));
		filterID.getFilterItems().add(new FilterItemInfo("isOUSealUp", Boolean.FALSE));
		filter.mergeFilter(filterID, "and");
		// jiesu
		 
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getTempLNTreeNodeCtrl(), 50, 5, filter, new SelectorItemCollection());
		treeBuilder.buildTree(treeMain);
	}

	private ILNTreeNodeCtrl getTempLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(CostCenterOrgUnitFactory.getRemoteInstance());
	}

	 
    /**
	 * @discription <??????????????????????????????????????????>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/09/09>
	 *             <p>
	 * @param <??>
	 * @return <??????????>
	 * 
	 *         modifier <??>
	 *         <p>
	 *         modifyDate <??>
	 *         <p>
	 *         modifyInfo <??>
	 *         <p>
	 * @throws Exception
	 * @see <????????>
	 */
	public void removeDept(DefaultKingdeeTreeNode rootNode) throws Exception {
		
	}
	
	
	
	/**
	 * ??????????
	 */
	protected void initTable() {
		super.initTable();
	}

	/**
	 * ????????????????????<br>
	 * ??????????????????????????????????????????
	 * <p>
	 * ??????????????????????<br>
	 * ??????????????????????????????????????????????????????????<br>
	 * ????????????????
	 */
	protected Dataset createDataset() {
		// return super.createDataset();
		DefaultCategoryDataset result = new DefaultCategoryDataset();
		int count = tblMain.getRowCount();
		for (int i = 0; i < count; i++) {
			 if (tblMain.getRow(i).getCell("wellRate").getValue() == null) {
				result.addValue(0, "?????????? (????????)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("wellRate").getValue().toString()) * 100, "?????????? (????????)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
			if (tblMain.getRow(i).getCell("lateRate").getValue() == null) {
				result.addValue(0, "?????????? (????????)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("lateRate").getValue().toString()) * 100, "?????????? (????????)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
			if (tblMain.getRow(i).getCell("allWellRate").getValue() == null) {
				result.addValue(0, "?????????? (????????)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("allWellRate").getValue().toString()) * 100, "?????????? (????????)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
			if (tblMain.getRow(i).getCell("allLateRate").getValue() == null) {
				result.addValue(0, "?????????? (????????)", tblMain.getRow(i).getCell("month").getValue().toString());
			} else {
				result.addValue(Double.parseDouble(tblMain.getRow(i).getCell("allLateRate").getValue().toString()) * 100, "?????????? (????????)", tblMain
						.getRow(i).getCell("month").getValue().toString());
			}
		}
		return result;
	}

	protected int getChartHeight() {
		return getHeight() - 200;
	}

	/**
	 * ????????
	 */
	protected JFreeChart createChart(Dataset dataset) {
		// ????+????????+??????????????
		String title = "??????????????";
		if (!FDCHelper.isEmpty(costCenterName)) {
			title = costCenterName + " " + title;
		}
		if (!FDCHelper.isEmpty(cbYear.getSelectedItem())) {
			title = cbYear.getSelectedItem().toString() + " " + title;
		}
		JFreeChart chart = ChartFactory.createStackedBarChart(title, // ????
				"????", // ????
				"??????", // ????
				(CategoryDataset) dataset, // ??????
				PlotOrientation.VERTICAL, // ????????
				true, // ????????
				true, // ????????????
				false // urls
				);
		// ????????
		chart.getTitle().setFont(new Font("????", Font.PLAIN, 20));
		// ????????????????????????????????????????????????????????????
		// ????????????????????????????
		GroupedStackedBarRenderer renderer = new GroupedStackedBarRenderer();
		KeyToGroupMap map = new KeyToGroupMap("G1");
		map.mapKeyToGroup("?????????? (????????)", "G1");
		map.mapKeyToGroup("?????????? (????????)", "G1");
		map.mapKeyToGroup("?????????? (????????)", "G2");
		map.mapKeyToGroup("?????????? (????????)", "G2");
		renderer.setSeriesToGroupMap(map);
		// ????????
		renderer.setItemMargin(0.02);
		// ????????
		renderer.setDrawBarOutline(false);
		
		// ????????????????????
		// setSeriesPaint????????????????????????????????????????????????????????????????
		// ??????0????????????????????????????????????????????????
		// ??????????????????????2??????????????????2????????????0??2????????????????????????
		// 1,3????????????
		Paint p1 = new GradientPaint(0.0f, 0.0f, new Color(149, 255, 149), 0.0f, 0.0f, new Color(89, 183, 89));
		renderer.setSeriesPaint(0, p1);

		Paint p2 = new GradientPaint(0.0f, 0.0f, new Color(255, 200, 200), 20.0f, 0.0f, new Color(255, 145, 145));
		renderer.setSeriesPaint(1, p2);

		Paint p3 = new GradientPaint(0.0f, 0.0f, new Color(109, 142, 255), 0.0f, 0.0f, new Color(49, 82, 123));
		renderer.setSeriesPaint(2, p3);

		Paint p4 = new GradientPaint(0.0f, 0.0f, new Color(255, 170, 170), 20.0f, 0.0f, new Color(255, 45, 45));
		renderer.setSeriesPaint(3, p4);
		// ????????????????????????????
		renderer.setGradientPaintTransformer(new StandardGradientPaintTransformer(GradientPaintTransformType.CENTER_HORIZONTAL));

		// ??????????????????????????????????
		renderer.setBaseToolTipGenerator(new StandardCategoryToolTipGenerator("({0}, {1}) = {2}%", NumberFormat.getInstance()));
		// ??????????????????????????????????????????????????
		// renderer.setItemLabelGenerator(new
		// StandardCategoryItemLabelGenerator(
		// "{2}", NumberFormat.getInstance()));
		// renderer.setItemLabelsVisible(true);
		// renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
		// ItemLabelAnchor.OUTSIDE12, TextAnchor.TOP_CENTER,
		// TextAnchor.TOP_CENTER, 0.0));
		// renderer.setItemLabelFont(new Font("????", Font.PLAIN, 9));
		chart.getLegend().setBackgroundPaint(new Color(255, 255, 255, 0));
		chart.getLegend().setItemFont(new Font("????", Font.PLAIN, 12));
		CategoryPlot plot = (CategoryPlot) chart.getPlot();
		plot.setRenderer(renderer);

		// ??????????????????????????????
		SubCategoryAxis domainAxis = new SubCategoryAxis("");
		domainAxis.setCategoryMargin(0.2);
		// domainAxis.addSubCategory("????");
		// domainAxis.addSubCategory("????");
		plot.setDomainAxis(domainAxis);
		// ??????????????
		NumberAxis rangeAxis = new NumberAxis("??????(%)") {
			public void setRange(Range range, boolean turnOffAutoRange, boolean notify) {
				range = new Range(0, 110);
				super.setRange(range, turnOffAutoRange, notify);
			}
		};
		rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		rangeAxis.setUpperMargin(0.10);
		
		rangeAxis.setLowerBound(0.0);
		rangeAxis.setUpperBound(110.0);
		rangeAxis.setTickUnit(new NumberTickUnit(5.0));
		// rangeAxis.setAutoTickUnitSelection(false);
		// rangeAxis.setAutoRange(false);
		// rangeAxis.setAutoRangeIncludesZero(false);
		// rangeAxis.setAutoRangeStickyZero(false);
		// rangeAxis.set

		// rangeAxis.setTickUnit(new NumberTickUnit(1));// 0.5??????????????
		// rangeAxis.setTickUnit(new NumberTickUnit(5));// 1??????????????
		
		
		// ????????P
		// rangeAxis.setInverted(true);
		// ??????90??????
		// rangeAxis.setVerticalTickLabels(true);
		plot.setRangeAxis(rangeAxis);
		// ????????????
		plot.setFixedLegendItems(createLegendItems());
		// chart.set
		return chart;
	}

	private static LegendItemCollection createLegendItems() {
		LegendItemCollection result = new LegendItemCollection();
		LegendItem item1 = new LegendItem("????????????", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(89, 183, 89));
		LegendItem item2 = new LegendItem("????????????", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 105, 105));
		LegendItem item3 = new LegendItem("????????????", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(49, 82, 123));
		LegendItem item4 = new LegendItem("????????????", "-", null, null, Plot.DEFAULT_LEGEND_ITEM_BOX, new Color(255, 45, 45));
		result.add(item1);
		result.add(item2);
		result.add(item3);
		result.add(item4);
		return result;
	}


		/**
	 * ????????????(????????)
	 * <p>
	 * ??????????????<br>
	 * 1??????????????????img1<br>
	 * 2??????????????????????????????????<br>
	 * 3??????????????img2<br>
	 * 4??????img????img1????????????????img2????????????img??????<br>
	 * 5????img2????img??????<br>
	 * 6??????????????????????????????????????????<br>
	 * 7??????img
	 */
	protected BufferedImage getUIIMG() {
		// 1??????????????????img1
		BufferedImage img1 = getChartIMG();
		int w1 = img1.getWidth();
		int h1 = img1.getHeight();
		// BufferedImage img1 = new BufferedImage(w1, h1,
		// BufferedImage.TYPE_INT_RGB);
		// Graphics g = img1.getGraphics();
		// pnlChart.paintAll(g);
		// g.dispose();

		// 2??????????????????????????????????
		// ????????????????
		// int curDivLeft = sptLeft.getDividerLocation();
		// int curDivTop = sptTop.getDividerLocation();
		KDTSelectBlock curSelect = tblMain.getSelectManager().get();
		// ??????????????????(2????????????+????????+??????????)
		int fitWidth = 2 + tblMain.getIndexColumn().getWidth() + tblMain.getColumns().getWidth();
		// sptLeft
		// .setDividerLocation(curDivLeft
		// + (tblMain.getWidth() - fitWidth));
		// ??????????????????(2????????????+??????+??????????)
		int fitHeight = 2 + tblMain.getHead().getHeight() + tblMain.getBody().getHeight();
		// sptTop
		// .setDividerLocation(curDivTop
		// + (tblMain.getHeight() - fitHeight));
		// ????????
		tblMain.getSelectManager().remove();
		// ??????????????????????????????????????????????
		tblMain.setSize(fitWidth, fitHeight);

		// 3??????????????img2
		int w2 = tblMain.getWidth();
		int h2 = tblMain.getHeight();
		BufferedImage img2 = new BufferedImage(w2, h2, BufferedImage.TYPE_INT_RGB);
		Graphics g = img2.getGraphics();
		tblMain.paintAll(g);
		g.dispose();

		// 4??????img????img1????????????????img2????????????img??????
		float scale = (float) w2 / w1;
		w1 = w2;
		h1 = (int) (h1 * scale);
		BufferedImage img = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);
		Image scaleImage = img1.getScaledInstance(w1, h1, Image.SCALE_AREA_AVERAGING);
		g = img.createGraphics();
		g.drawImage(scaleImage, 0, 0, w1, h1, null);

		// 5????img2????img??????
		g.drawImage(img2, 0, h1, w2, h2, null);
		g.dispose();

		// 6??????????????????????????????????????????
		// sptLeft.setDividerLocation(curDivLeft);
		// sptTop.setDividerLocation(curDivTop);
		if (curSelect != null) {
			tblMain.getSelectManager().select(curSelect);
		}

		// 7??????img
		return img;
	}

	// ????
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		ScheduleChartProvider dataPvd = new ScheduleChartProvider(getTDName(), getUIIMG());
		KDNoteHelper appHlp = new KDNoteHelper();
		appHlp.print(getTDPath(), dataPvd, SwingUtilities.getWindowAncestor(this));
	}

	// ????????
	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		super.actionPrintPreview_actionPerformed(e);
	}

	protected String getTDPath() {
		// TODO Auto-generated method stub
		return super.getTDPath();
	}

	protected BufferedImage getChartIMG() {
		// TODO Auto-generated method stub
		return super.getChartIMG();
	}
	public void actionExportIMG_actionPerformed(ActionEvent e) throws Exception {
		tblMain.checkParsed();
		if (tblMain.getRowCount() <= 0) {
			FDCMsgBox.showInfo("????????????????????????");
			abort();
		}
		super.actionExportIMG_actionPerformed(e);
	}
	
	// add by libing at 2011-09-14
	public boolean destroyWindow() {
		try {
			IREAutoRemember autoRemember = REAutoRememberFactory.getRemoteInstance();
			// ????????
			UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
			// ??????????
			OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			CostCenterOrgUnitInfo companyOrgUnitInfo = null;
			if (node != null && node.isLeaf() && node.getUserObject() instanceof CostCenterOrgUnitInfo) {
				companyOrgUnitInfo = (CostCenterOrgUnitInfo) node.getUserObject();
			}
			// ????????
			Object[] objs = new Object[] { user, orgUnit, companyOrgUnitInfo };
			if (exits(objs)) {
				String userID = user.getId().toString();
				String orgUnitID = orgUnit.getId().toString();
				String curProjectID = companyOrgUnitInfo.getId().toString();
				autoRemember.save(userID, orgUnitID, "CompanyRateAchievedUI", curProjectID);
			}
		} catch (BOSException e) {
			e.printStackTrace();
		}

		return super.destroyWindow();
	}

	/**
	 * @description ??????????????????
	 * @author ??????
	 * @createDate 2011-8-25 void
	 * @version EAS7.0
	 * @see
	 */

	private boolean exits(Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			if (FDCHelper.isEmpty(objs[i])) {
				return false;
			}
		}
		return true;
	}
	
}