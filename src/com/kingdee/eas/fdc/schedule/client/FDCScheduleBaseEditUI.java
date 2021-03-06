/**
 * output package name
 */
package com.kingdee.eas.fdc.schedule.client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.Box.Filler;
import javax.swing.filechooser.FileFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import net.sf.mpxj.MPXJException;
import net.sourceforge.ganttproject.GanttTreeTable;
import net.sourceforge.ganttproject.GanttTreeTable.DisplayedColumnsList;
import net.sourceforge.ganttproject.cache.ActivityCache;
import net.sourceforge.ganttproject.task.Task;
import net.sourceforge.ganttproject.task.dependency.TaskDependencyCollectionMutator;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.event.DataChangeEvent;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.framework.cache.ActionCache;
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
import com.kingdee.eas.base.message.MsgType;
import com.kingdee.eas.base.param.ParamCollection;
import com.kingdee.eas.base.param.ParamFactory;
import com.kingdee.eas.base.param.ParamInfo;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.basedata.person.PersonInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IREAutoRemember;
import com.kingdee.eas.fdc.basedata.REAutoRememberFactory;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCClientVerifyHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.util.FdcManagementUtil;
import com.kingdee.eas.fdc.schedule.CheckNodeCollection;
import com.kingdee.eas.fdc.schedule.CheckNodeFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleConstant;
import com.kingdee.eas.fdc.schedule.FDCScheduleFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependCollection;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskDependInfo;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskFactory;
import com.kingdee.eas.fdc.schedule.FDCScheduleTaskInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSInfo;
import com.kingdee.eas.fdc.schedule.FDCWBSTree;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeCollection;
import com.kingdee.eas.fdc.schedule.GlobalTaskNodeInfo;
import com.kingdee.eas.fdc.schedule.IFDCSchedule;
import com.kingdee.eas.fdc.schedule.ProjectSpecialInfo;
import com.kingdee.eas.fdc.schedule.RESchTaskTypeEnum;
import com.kingdee.eas.fdc.schedule.RESchTemplateInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskFactory;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskInfo;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorCollection;
import com.kingdee.eas.fdc.schedule.RESchTemplateTaskPredecessorInfo;
import com.kingdee.eas.fdc.schedule.ScheduleBizTypeInfo;
import com.kingdee.eas.fdc.schedule.ScheduleCalendarHelper;
import com.kingdee.eas.fdc.schedule.ScheduleHelper;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeCollection;
import com.kingdee.eas.fdc.schedule.ScheduleTaskBizTypeInfo;
import com.kingdee.eas.fdc.schedule.TaskTypeInfo;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchCalendar;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskCreator;
import com.kingdee.eas.fdc.schedule.client.RESchMSProjectReader.IRESchTaskPredecessorCreator;
import com.kingdee.eas.fdc.schedule.framework.DependHardnessEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarFactory;
import com.kingdee.eas.fdc.schedule.framework.ScheduleCalendarInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskBaseInfo;
import com.kingdee.eas.fdc.schedule.framework.ScheduleTaskPropertyCollection;
import com.kingdee.eas.fdc.schedule.framework.client.FindTaskUI;
import com.kingdee.eas.fdc.schedule.framework.client.IScheduleUIFacade;
import com.kingdee.eas.fdc.schedule.framework.ext.KDTask;
import com.kingdee.eas.fdc.schedule.framework.ext.ScheduleGanttProject;
import com.kingdee.eas.fdc.schedule.framework.parser.ScheduleParserHelper;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTask;
import com.kingdee.eas.fdc.schedule.framework.util.IRESchTaskPredecessor;
import com.kingdee.eas.fdc.schedule.framework.util.KDProjectFileReader;
import com.kingdee.eas.fdc.schedule.framework.util.KDProjectWriter;
import com.kingdee.eas.fdc.schedule.framework.util.ScheduleTaskPropertyHelper;
import com.kingdee.eas.fdc.schedule.framework.util.StopWatch;
import com.kingdee.eas.fdc.schedule.param.ScheduleParamHelper;
import com.kingdee.eas.fm.common.FMHelper;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.batchHandler.ResponseConstant;
import com.kingdee.eas.framework.batchHandler.UtilRequest;
import com.kingdee.eas.util.AbortException;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;

/**
 * output class name
 */
public  class FDCScheduleBaseEditUI extends AbstractFDCScheduleBaseEditUI implements IScheduleUIFacade {
	/**
	 * ????????????????Project????????????????????????????????
	 */
	private static String preProjectFileName = System.getProperty("user.home");
	private static final Logger logger = CoreUIObject.getLogger(FDCScheduleBaseEditUI.class);
	private static boolean isCanSaveAddNewTask = false;
	private CoreBaseCollection wbsCoreBaseCollection;
	private ScheduleTaskPropertyCollection columnsProperty = null;
	private boolean isNeedRemeber = true;

	/**
	 * output class constructoro
	 */
	public FDCScheduleBaseEditUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		//??????????????????????KD??????
		 ClassLoaderUtil.modifyClassLoader(true);
		try {
			beforeOnload();
			isCanSaveAddNewTask = ScheduleParamHelper.getSchParamByKey(null, null, ScheduleParamHelper.FDCSCH_PARAM_CANSAVEADDNEWTASK);
			super.onLoad();
			cbState.addItems(EnumUtils.getEnumList("com.kingdee.eas.fdc.schedule.framework.ScheduleStateEnum").toArray());
			if (editData != null && editData.getProject() != null) {
				loadProjectState();
				setVersion();
			}
		
			afterOnload();
			loadCurProject();
			if (null == editData || null == editData.getProject()) {
				txtVersion.setText("");
				cbState.setSelectedItem(null);
			}
			moveBtnToGantt();
			actionHistoryVersion();
			
			addExport();
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			e.printStackTrace();
			isNeedRemeber = false;
			abort();
		}
		this.actionBatchModifyTaskType.setVisible(false);
	}
	public void addExport(){
		KDWorkButton exportXls = new KDWorkButton();
		exportXls.setIcon(EASResource.getIcon("imgTbtn_importcyclostyle"));
		exportXls.setText("????????excel");
		this.toolBar.add(exportXls, 10);
		exportXls.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ExportManager exportM = new ExportManager();
			    String path = null;
			    File tempFile;
			    KDTable exportTable = new KDTable();
			    initExportTable(exportTable);
			    FDCScheduleTaskCollection taskColl = editData.getTaskEntrys();
			    SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd");
			    for(int i = 0; i < taskColl.size(); i++) {
			    	FDCScheduleTaskInfo scheTaskInfo = taskColl.get(i);
			    	int seq = scheTaskInfo.getSeq();//????
			    	String qualityState = scheTaskInfo.getString("qualityEvaluation");
			    	String scheduleState = scheTaskInfo.getString("scheduleEvaluation");
			    	String TaskName = addSpaceToTaskName(scheTaskInfo.getName(), scheTaskInfo.getLevel());//????????
			    	String type=scheTaskInfo.getTaskType().getAlias();
			    	BigDecimal NatureTimes = scheTaskInfo.getEffectTimes();//????
			    	Date PlanStart = scheTaskInfo.getPlanStart();//????????????
			    	Date PlanEnd = scheTaskInfo.getPlanEnd();//????????????
			    	Date ActualStartDate = scheTaskInfo.getActualStartDate();//????????????
			    	Date ActualEndDate = scheTaskInfo.getActualEndDate();//????????????
			    	Date CheckDate = scheTaskInfo.getCheckDate();//????????
			    	PersonInfo AdminPerson = scheTaskInfo.getAdminPerson();//??????
			    	FullOrgUnitInfo AdminDept = scheTaskInfo.getAdminDept();//????????
			    	
			    	String planStart = (PlanStart == null ? "" : simpleFormat.format(PlanStart));
			    	String planEnd = (PlanEnd == null ? "" : simpleFormat.format(PlanEnd));
			    	String actualStartDate = (ActualStartDate == null ? "" : simpleFormat.format(ActualStartDate));
			    	String actualEndDate = (ActualEndDate == null ? "" : simpleFormat.format(ActualEndDate));
			    	String checkDate = (CheckDate == null ? "" : simpleFormat.format(CheckDate));
			    	
			    	
			    	int state = -1;
			    	if(scheTaskInfo.get("state") != null)
			    		state = scheTaskInfo.getInt("state");
			    	String strState = null;
			    	switch (state) {
					case FDCScheduleConstant.ONTIME:
						strState = "????????";
						break;
					case FDCScheduleConstant.DELAY:
						strState = "????????";
						break;
					case FDCScheduleConstant.CONFIRMED:
						strState = "??????";
						break;
					case FDCScheduleConstant.DELAYANDNOTCOMPLETE:
						strState = "??????????";
						break;
					}
			    	

			    	IRow rowAdd = exportTable.addRow();
			    	rowAdd.getCell(0).setValue(seq);
			    	rowAdd.getCell(1).setValue(strState);
			    	rowAdd.getCell(2).setValue(qualityState);
			    	rowAdd.getCell(3).setValue(scheduleState);
			    	rowAdd.getCell(4).setValue(type);
			    	rowAdd.getCell(5).setValue(TaskName);
			    	rowAdd.getCell(6).setValue(NatureTimes);
			    	rowAdd.getCell(7).setValue(planStart);
			    	rowAdd.getCell(8).setValue(planEnd);
			    	rowAdd.getCell(9).setValue(actualStartDate);
			    	rowAdd.getCell(10).setValue(actualEndDate);
			    	rowAdd.getCell(11).setValue(checkDate);
			    	rowAdd.getCell(12).setValue(AdminPerson);
			    	rowAdd.getCell(13).setValue(AdminDept);
			    	rowAdd.getCell(14).setValue(scheTaskInfo.getString("qualityResult"));
			    	rowAdd.getCell(15).setValue(scheTaskInfo.getString("scheduleResult"));
			    	rowAdd.getCell(16).setValue(scheTaskInfo.getString("reportDesc"));
			    }
				try {
					tempFile = File.createTempFile("eastemp",".xls");
					path = tempFile.getCanonicalPath();
					KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[1];
					tablesVO[0]=new KDTables2KDSBookVO(exportTable);
					tablesVO[0].setTableName("??????????");
					KDSBook book = null;
				    book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
				    exportM.exportToExcel(book, path);
				    
				    KDFileChooser fileChooser = new KDFileChooser();
					fileChooser.setFileSelectionMode(0);
					fileChooser.setMultiSelectionEnabled(false);
					fileChooser.setSelectedFile(new File("??????????.xls"));
					int result = fileChooser.showSaveDialog(null);
					
					if (result == KDFileChooser.APPROVE_OPTION){
						File dest = fileChooser.getSelectedFile();
						File src = new File(path);
						if (dest.exists())
							dest.delete();
						src.renameTo(dest);
						MsgBox.showInfo("??????????");
						KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
					}
					tempFile.delete();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	private void initExportTable(KDTable table) {
		table.addHeadRow(0);
		String[] head = new String[] {"????","????","????????????","????????????","????????","????????", "????????", "????????????", "????????????",
									  "????????????", "????????????", "????????", "??????", "????????","????????????","????????????","????????????"};
		for(int i = 0; i < head.length; i++) {
			IColumn col = table.addColumn();
			col.setKey("" + i);
			table.getHeadRow(0).getCell("" + i).setValue(head[i]);
		}
	}
	private String addSpaceToTaskName(String taskName, int level) {
		StringBuilder sb = new StringBuilder("");
		for(int i = 0; i < level-1; i++)
			sb.append("    ");
		sb.append(taskName);
		return sb.toString();
	}
	/**
	 * ????UI??????????????
	 * 
	 * @return
	 */
	public boolean isFromExecuteUI() {
		Object metaPK = getUIContext().get("METADATA.PK");
		if (FDCHelper.isEmpty(metaPK)) {
			return false;
		} else if (metaPK.toString().endsWith("MainScheduleExecuteUI") || metaPK.toString().endsWith("SpecialScheduleExecuteUI")) {
			return true;
		}
		return false;
	}

	/**
	 * ????UI????????????????
	 * 
	 * @return
	 */
	public boolean isFromTotalUI() {
		Object metaPK = getUIContext().get("METADATA.PK");
		if (FDCHelper.isEmpty(metaPK)) {
			return false;
		} else if (metaPK.toString().endsWith("TotalScheduleEditUI")) {
			return true;
		}
		return false;
	}

	/**
	 * ??????????????????????
	 * <p>
	 * ????????????????????????????????<br>
	 * ??????????????????????????????????????
	 */
	protected void moveBtnToGantt() {
		toolBar.remove(btnProperty);
		btnProperty = new KDWorkButton(btnProperty.getAction());
		btnProperty.setFactType(KDWorkButton.BLUE_FACE);
		ganttProject.getToolbarBox().add(btnProperty);

		Dimension dms = new Dimension(3, 0);
		Filler filler = new Box.Filler(dms, dms, dms);
		ganttProject.getToolbarBox().add(filler);

		toolBar.remove(btnBatChangeRespDept);
		btnBatChangeRespDept = new KDWorkButton(btnBatChangeRespDept.getAction());
		// btnBatChangeRespDept.setBorder(btnShowByLevel.getBorder());
		btnBatChangeRespDept.setText("??????????????");
		btnBatChangeRespDept.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
		btnBatChangeRespDept.setToolTipText("??????????????");
		btnBatChangeRespDept.setFactType(KDWorkButton.BLUE_FACE);
		ganttProject.getToolbarBox().add(btnBatChangeRespDept);

		toolBar.remove(btnBatchModifyTaskType);
		btnBatChangeRespDept = new KDWorkButton(btnBatchModifyTaskType.getAction());
		// btnBatChangeRespDept.setBorder(btnShowByLevel.getBorder());
		btnBatchModifyTaskType.setText("????????????????");
		btnBatchModifyTaskType.setIcon(com.kingdee.eas.util.client.EASResource.getIcon("imgTbtn_setting"));
		btnBatchModifyTaskType.setToolTipText("????????????????");
		btnBatchModifyTaskType.setFactType(KDWorkButton.BLUE_FACE);
		ganttProject.getToolbarBox().add(btnBatchModifyTaskType);

		filler = new Box.Filler(dms, dms, dms);
		ganttProject.getToolbarBox().add(filler);

		toolBar.remove(btnLocate);
		btnLocate = new KDWorkButton(btnLocate.getAction());
		btnLocate.setIcon(EASResource.getIcon("imgTbtn_speedgoto"));
		this.btnLocate.setText("????");
		this.btnLocate.setToolTipText("????");
		btnLocate.setFactType(KDWorkButton.BLUE_FACE);
		ganttProject.getToolbarBox().add(btnLocate);

		filler = new Box.Filler(dms, dms, dms);
		ganttProject.getToolbarBox().add(filler);

		ganttProject.getToolbarBox().add(btnShowByLevel);

	}

	private void loadProjectState() {
		cbState.setSelectedItem(editData.getState());
		cbState.setEnabled(false);
	}

	private void setVersion() {
		txtVersion.setText(editData.getVersionName());
	}

	protected void afterOnload() {
		FDCClientUtils.setRespDeptF7(prmtAdminDept, this);
		prmtAuditor.setEnabled(false);
		pkAuditTime.setEnabled(false);
		pkCreateTime.setEnabled(false);
		prmtAdminDept.setEnabled(false);
		prmtAdminDept.setRequired(true);
		// ????????????????
		this.chkMenuItemSubmitAndPrint.setSelected(false);
		this.chkMenuItemSubmitAndPrint.setVisible(false);
		this.chkMenuItemSubmitAndAddNew.setSelected(false);
		this.chkMenuItemSubmitAndAddNew.setVisible(false);
		this.btnAdjust.setEnabled(true);
		this.btnRestore.setEnabled(true);
		txtVersion.setEnabled(false);
		cbState.setEnabled(false);
	}

	/**
	 * 
	 * 
	 * ?????? ????????F7????????-????????????????
	 * 
	 * @author ??????
	 * @version EAS7.0
	 * @modifyDate 2011-10-5
	 * 
	 */
	private void loadCurProject() {
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("isLeaf", Boolean.TRUE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		String sql = "select fid from t_fdc_curproject where FCostCenterId in (select fid from t_org_baseunit where flongnumber like (select flongnumber||'%' from t_org_baseunit where fid ='"
				+ SysContext.getSysContext().getCurrentCostUnit().getId().toString()
				+ "') and fisfreeze = 0 and fisOuSealUp = 0  and fid in (select forgid from T_PM_OrgRange where fuserid = '"
				+ SysContext.getSysContext().getCurrentUserInfo().getId().toString() + "'))";
		filter.getFilterItems().add(new FilterItemInfo("curProject.id", sql, CompareType.INNER));
		logger.error(sql);
		
		/* modified by zhaoqin on 2014/06/10 */
		getAuthorizedCostOrgs();
		filter.getFilterItems().add(new FilterItemInfo("costCenter.id", authorizedOrgs, CompareType.INCLUDE));
		
		view.setFilter(filter);

		prmtCurproject.setEntityViewInfo(view);
		prmtCurproject.setDataNoNotify(editData.getProject());
		prmtCurproject.setEditable(false);
		logger.error(view);
	}

	/**
	 * ????project
	 */
	public void actionImportProject_actionPerformed(ActionEvent e) throws Exception {
		
		String oldState = getOprtState(); //modified by zhaoqin on 2014/06/17
		beforeCheck();

		/* ???????????? */
		CoreBaseCollection currentAllTaskCollection = new CoreBaseCollection();
		CoreBaseCollection currentWbsCollection = new CoreBaseCollection();
		CoreBaseCollection currentDependTaskCollection = new CoreBaseCollection();
		for (int k = 0; k < this.editData.getTaskEntrys().size(); k++) {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = this.editData.getTaskEntrys().get(k);
			currentAllTaskCollection.add(fDCScheduleTaskInfo);
			if (null != fDCScheduleTaskInfo.getWbs()) {
				currentWbsCollection.add(fDCScheduleTaskInfo.getWbs());
			}
			FDCScheduleTaskDependCollection fDCScheduleTaskDependCollection = fDCScheduleTaskInfo.getDependEntrys();
			for (int m = 0; m < fDCScheduleTaskDependCollection.size(); m++) {
				currentDependTaskCollection.add(fDCScheduleTaskDependCollection.get(m));
			}
		}

		if (this.editData.getTaskEntrys().size() > 0) {
			int result = FDCMsgBox.showConfirm2("??????????????????????????????");
			/* result==0 ?????????????? result==2 ?????????????? */
			if (result == 2) {
				return;
			}
		}

		/*
		 * ????????project????
		 */
		JFileChooser chooser = new JFileChooser(new File("c:\\"));
		/* ?????????? */
		chooser.setFileFilter(new FilterType());
		/* ?????????? */
		chooser.showDialog(null, "????Project");
		/* ???????????????? */
		if (chooser.getSelectedFile() == null) {
			return;
		}
		/* ???????????????? */
		String filePath = chooser.getSelectedFile().getPath();
		File file = new File(filePath);

		/* ???????????? */
		this.editData.getTaskEntrys().clear();

		/* ?????? */
		List projectList = RESchMSProjectReader.pasreMSProject(file, new IRESchTaskCreator() {
			public IRESchTask createSchTask() {
				return (IRESchTask) new FDCScheduleTaskInfo();
			}
		}, new IRESchTaskPredecessorCreator() {
			public IRESchTaskPredecessor createSchTaskPredecessor() {
				return new FDCScheduleTaskDependInfo();
			}
		}, new IRESchCalendar() {
			public ScheduleCalendarInfo getSchedule() {
				return editData.getCalendar();
			}
		});

		/* ??????????????id???????????????????????????????????????? */
		Map projectMap = new HashMap();
		for (int k = 0; k < projectList.size(); k++) {
			if (null != projectList.get(k)) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) projectList.get(k);
				if (null != fDCScheduleTaskInfo.getMsProjectId()) {
					projectMap.put(fDCScheduleTaskInfo.getMsProjectId().toString(), fDCScheduleTaskInfo);
				}
			}
		}

		wbsCoreBaseCollection = new CoreBaseCollection();
		ScheduleCalendarInfo calendar = editData.getCalendar();

		/* ???????????????????????????????? */
		CoreBaseCollection newTaskCollection = new CoreBaseCollection();
		for (int k = 0; k < projectList.size(); k++) {
			if (null != projectList.get(k)) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = (FDCScheduleTaskInfo) projectList.get(k);
				Object projectObj = projectMap.get(String.valueOf(null == fDCScheduleTaskInfo.getMsParentPrjId() ? "" : fDCScheduleTaskInfo.getMsParentPrjId()));
				if (null != projectObj && !"".equals(projectObj.toString().trim())) {
					if (projectObj instanceof FDCScheduleTaskInfo) {
						fDCScheduleTaskInfo.setParent((FDCScheduleTaskInfo) projectObj);
					}
				}
				/* ????WBS */
				// fDCScheduleTaskInfo.setWbs(createWBS(fDCScheduleTaskInfo,
				// fDCScheduleTaskInfo.getParent()));
				Date closestStart = ScheduleCalendarHelper.getClosestWorkDay(fDCScheduleTaskInfo.getPlanStart(), calendar);
				Date closestEnd = ScheduleCalendarHelper.getClosestWorkDay(fDCScheduleTaskInfo.getPlanEnd(), calendar);
				fDCScheduleTaskInfo.setStart(closestStart);
				fDCScheduleTaskInfo.setEnd(closestEnd);
				/* ???????????? */
				BigDecimal effDay = ScheduleCalendarHelper.getEffectTimes(closestStart, closestEnd, calendar);
				fDCScheduleTaskInfo.setDuration(effDay.intValue());
				fDCScheduleTaskInfo.setEffectTimes(effDay);
				fDCScheduleTaskInfo.setSchedule(this.editData);
				fDCScheduleTaskInfo.setComplete(FDCHelper.ZERO);
				fDCScheduleTaskInfo.setCalendar(this.editData.getCalendar());
				fDCScheduleTaskInfo.setNatureTimes(BigDecimal.valueOf(0));
				fDCScheduleTaskInfo.setIsScheduleTask(true);
				fDCScheduleTaskInfo.setPriority(1);
				fDCScheduleTaskInfo.setIsAdd(true);
				// ??????????????????????????????????????
				fDCScheduleTaskInfo.setSeq(k + 1);
				fDCScheduleTaskInfo.setSrcID(fDCScheduleTaskInfo.getId().toString());
				if (fDCScheduleTaskInfo.getParent() != null) {
					fDCScheduleTaskInfo.setLongNumber(fDCScheduleTaskInfo.getParent().getLongNumber() + "!" + fDCScheduleTaskInfo.getNumber());
					fDCScheduleTaskInfo.setLevel(fDCScheduleTaskInfo.getLevel() + 1);
				} else {
					fDCScheduleTaskInfo.setLongNumber(fDCScheduleTaskInfo.getNumber());
					fDCScheduleTaskInfo.setLevel(0);
				}
				// if (null != fDCScheduleTaskInfo.getWbs()) {
				// fDCScheduleTaskInfo.setLocked(fDCScheduleTaskInfo.getWbs().
				// isIsLocked());
				// }
				newTaskCollection.add((CoreBaseInfo) fDCScheduleTaskInfo);
				this.editData.getTaskEntrys().add(fDCScheduleTaskInfo);
			}
		}
		verifyIncludeGlobalTask(this.editData.getTaskEntrys());
		/* ?????? */
		/* ???????????? */
		try {
			setDataObject(this.editData);
			load2Gantt(this.editData);
			this.setInitEnd(false);
			setMessageText("??" + newTaskCollection.size() + "??????????????????");
			showMessage();
			weakOnShow();
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		this.oprtState = oldState; //modified by zhaoqin on 2014/06/17
		getScheduleGanttProject().setOprtState(this.getOprtState());
		changePageState();
	}
	
	private void verifyIncludeGlobalTask(FDCScheduleTaskCollection scheduleTaskCollection) {
		GlobalTaskNodeCollection cols = FDCScheduleBaseHelper.getGlobalTaskNode();
		Map newMap = new HashMap();
		for (int i = 0; i < cols.size(); i++) {
			GlobalTaskNodeInfo taskNode = cols.get(i);
			newMap.put(taskNode.getName().toString(), taskNode);
		}
		if (newMap.isEmpty()) {
			return;
		}
		FDCScheduleTaskInfo task = null;
		GlobalTaskNodeInfo nodeInfo = null;
		int size = scheduleTaskCollection.size();
		for (int i = 0; i < size; i++) {
			task = scheduleTaskCollection.get(i);
//			if (newMap.containsKey(task.getName())) {
//				nodeInfo = (GlobalTaskNodeInfo) newMap.get(task.getName());
//				task.setSrcGroupNode(nodeInfo.getId().toString());
//				newMap.remove(task.getName());
//			}
            if(newMap.containsKey(task.getName().toString())){
 				newMap.remove(task.getName().toString());
             }
		}

		if (!newMap.isEmpty()) {
			StringBuffer errMsg = new StringBuffer();
			Set set = newMap.entrySet();
			for (Iterator it = set.iterator(); it.hasNext();) {
				errMsg.append(((GlobalTaskNodeInfo) ((Entry) it.next()).getValue()).getName());
				errMsg.append("\n");
			}

//			FDCMsgBox.showDetailAndOK(this, "????project????????????????????????????!????????????", errMsg.toString(), MsgBox.ICONERROR);
			int  flag = FDCMsgBox.showConfirm3a(this, "????project????????????????????????????!????????????", errMsg.toString());
			if(flag != 0){				
				abort();
			}
		}
		newMap.clear();
	}
	/**
	 * ????Project
	 * <p>
	 * 
	 * @author Xiaolong_Luo
	 * @createDate 2011-08-20
	 */
	public void actionImportPlanTemplate_actionPerformed(ActionEvent e) throws Exception {
		String oState = getOprtState();
		beforeCheck();
		wbsCoreBaseCollection = new CoreBaseCollection();
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(REScheduleImportPlanTemplateListUI.class.getName(), uiContext, null, OprtState.ADDNEW);
//		IUIWindow uiWindow = uiFactory.create(F7RESchTemplateUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		getScheduleGanttProject().setOprtState(oState);
		oprtState = oState;
		changePageState();
	}

	/**
	 * @discription <????????????>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/20>
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
	 * @see <????????>
	 */
	public void importPlanTemplate(RESchTemplateInfo schTemplateInfo, Date planStateDate) {
		if (null == schTemplateInfo || null == schTemplateInfo.getId() || "".equals(schTemplateInfo.getId().toString().trim())) {
			return;
		}
		ScheduleCalendarInfo calendar = null;
		try {
			calendar = ScheduleCalendarFactory.getRemoteInstance().getDefaultCal(editData.getProject().getId().toString());
		} catch (EASBizException e1) {
			handUIException(e1);
		} catch (BOSException e1) {
			handUIException(e1);
		}
		editData.setCalendar(calendar);
		
		planStateDate = ScheduleCalendarHelper.getClosestWorkDay(planStateDate, calendar);
		/* ???????????? */
		CoreBaseCollection currentAllTaskCollection = new CoreBaseCollection();
		CoreBaseCollection currentWbsCollection = new CoreBaseCollection();
		CoreBaseCollection currentDependTaskCollection = new CoreBaseCollection();
		for (int k = 0; k < this.editData.getTaskEntrys().size(); k++) {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = this.editData.getTaskEntrys().get(k);
			currentAllTaskCollection.add(fDCScheduleTaskInfo);
			if (null != fDCScheduleTaskInfo.getWbs()) {
				currentWbsCollection.add(fDCScheduleTaskInfo.getWbs());
			}
			FDCScheduleTaskDependCollection fDCScheduleTaskDependCollection = fDCScheduleTaskInfo.getDependEntrys();
			for (int m = 0; m < fDCScheduleTaskDependCollection.size(); m++) {
				currentDependTaskCollection.add(fDCScheduleTaskDependCollection.get(m));
			}
		}

		/* ???????????? */
		editData.getTaskEntrys().clear();

		/* ????????id?????????????????????????????????????????? */
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("template.id", schTemplateInfo.getId());
		view.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		view.getSorter().add(sii);
		view.getSelector().add(new SelectorItemInfo("*"));
		view.getSelector().add(new SelectorItemInfo("template.*"));
		view.getSelector().add(new SelectorItemInfo("parent.*"));
		view.getSelector().add(new SelectorItemInfo("standardTask.*"));
		view.getSelector().add(new SelectorItemInfo("businessType.*"));
		view.getSelector().add(new SelectorItemInfo("businessType.bizType.number"));
		view.getSelector().add(new SelectorItemInfo("businessType.bizType.id"));
		view.getSelector().add(new SelectorItemInfo("businessType.bizType.name"));
		view.getSelector().add(new SelectorItemInfo("predecessors.*"));
		view.getSelector().add(new SelectorItemInfo("achievementType.*"));
		try {
			RESchTemplateTaskCollection rESchTemplateTaskCollection = RESchTemplateTaskFactory.getRemoteInstance().getRESchTemplateTaskCollection(view);

			/* ???? */
			Map sMap = new HashMap();
			Map pMap = new HashMap();
			Map handlerProcessorMap = new HashMap();
			Set needProcessSet = new HashSet();
			FDCScheduleTaskCollection fDCScheduleTaskCollection = new FDCScheduleTaskCollection();
			for (int k = 0; k < rESchTemplateTaskCollection.size(); k++) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = new FDCScheduleTaskInfo();
				RESchTemplateTaskInfo rESchTemplateTaskInfo = rESchTemplateTaskCollection.get(k);
				/* ?????????? */
				fDCScheduleTaskInfo.setId(BOSUuid.create((new FDCScheduleTaskInfo()).getBOSType()));
				fDCScheduleTaskInfo.setSchTemplateTask(rESchTemplateTaskInfo);
				/* ???????????? */
				fDCScheduleTaskInfo.setName(rESchTemplateTaskInfo.getName());
				// ??????????????????????????????????????
				fDCScheduleTaskInfo.setSeq(k + 1);
				/* ???? ???????? */
				fDCScheduleTaskInfo.setTaskType(rESchTemplateTaskInfo.getTaskType());
				/* ???????????? */
				for (int s = 0; s < rESchTemplateTaskInfo.getBusinessType().size(); s++) {
					RESchTemplateTaskBizTypeInfo rESchTemplateTaskBizTypeInfo = rESchTemplateTaskInfo.getBusinessType().get(s);
					ScheduleTaskBizTypeInfo scheduleTaskBizTypeInfo = new ScheduleTaskBizTypeInfo();
					scheduleTaskBizTypeInfo.setId(BOSUuid.create((new ScheduleTaskBizTypeInfo()).getBOSType()));
					scheduleTaskBizTypeInfo.setBizType(rESchTemplateTaskBizTypeInfo.getBizType());
					fDCScheduleTaskInfo.getBizType().add(scheduleTaskBizTypeInfo);
				}

				/* ???????? */
				fDCScheduleTaskInfo.setEffectTimes(BigDecimal.valueOf(rESchTemplateTaskInfo.getReferenceDay()));
				fDCScheduleTaskInfo.setDuration(rESchTemplateTaskInfo.getReferenceDay());
				/* ???????????? */
				fDCScheduleTaskInfo.setAchievementType(rESchTemplateTaskInfo.getAchievementType());
				/* ???????????? */
				fDCScheduleTaskInfo.setStandardTask(rESchTemplateTaskInfo.getStandardTask());
				/* ???????? */
				fDCScheduleTaskInfo.setSimpleName(rESchTemplateTaskInfo.getDescription());
				fDCScheduleTaskInfo.setComplete(FDCHelper.ZERO);
				/* ???????????? */
				fDCScheduleTaskInfo.setSchedule(this.editData);
				/* ???????? */
				fDCScheduleTaskInfo.setNumber(rESchTemplateTaskInfo.getNumber());
				/* ?????????? */
				fDCScheduleTaskInfo.setLongNumber(rESchTemplateTaskInfo.getLongNumber());
				/* ???????? */
				fDCScheduleTaskInfo.setLevel(rESchTemplateTaskInfo.getLevel() - 1);
				/* ???????????????? */
				fDCScheduleTaskInfo.setIsLeaf(rESchTemplateTaskInfo.isIsLeaf());
				fDCScheduleTaskInfo.setSrcID(fDCScheduleTaskInfo.getId().toString());
				fDCScheduleTaskCollection.add(fDCScheduleTaskInfo);

				sMap.put(rESchTemplateTaskInfo.getId().toString(), fDCScheduleTaskInfo);
				if (null != rESchTemplateTaskInfo.getParent() && null != rESchTemplateTaskInfo.getParent().getId() && !"".equals(rESchTemplateTaskInfo.getParent().getId().toString().trim())) {
					pMap.put(fDCScheduleTaskInfo.getId().toString(), rESchTemplateTaskInfo.getParent().getId());
				}
				handlerProcessorMap.put(rESchTemplateTaskInfo.getId().toString(), fDCScheduleTaskInfo);
			}

			CoreBaseCollection newDependTaskCollection = new CoreBaseCollection();
			CoreBaseCollection newTaskCollection = new CoreBaseCollection();
			/* ???? */
			for (int j = 0; j < fDCScheduleTaskCollection.size(); j++) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(j);

				Object pObject = pMap.get(fDCScheduleTaskInfo.getId().toString());
				/* ?????????????????? */
				if (null != pObject && !"".equals(pObject.toString().trim())) {
					Object sObject = sMap.get(pObject.toString().trim());
					if (null != sObject && sObject instanceof FDCScheduleTaskInfo) {
						FDCScheduleTaskInfo dCScheduleTaskInfo = (FDCScheduleTaskInfo) sObject;
						fDCScheduleTaskInfo.setParent(dCScheduleTaskInfo);
					}
				}
				
				fDCScheduleTaskInfo.setWbs(createWBS(fDCScheduleTaskInfo, fDCScheduleTaskInfo.getParent()));
				fDCScheduleTaskInfo.setExpand(true);
				fDCScheduleTaskInfo.setCalendar(this.editData.getCalendar());
				// fDCScheduleTaskInfo.setEffectTimes(BigDecimal.valueOf(0));
				// fDCScheduleTaskInfo.setNatureTimes(BigDecimal.valueOf(0));
				fDCScheduleTaskInfo.setIsScheduleTask(true);
				fDCScheduleTaskInfo.setPriority(1);
				fDCScheduleTaskInfo.setIsAdd(true);

				// ????????????????????????????
				// ??????????????????????????????????????????????????????????????????????????????????--lanyuanjun

				fDCScheduleTaskInfo.setStart(planStateDate);
				Date endDate = ScheduleCalendarHelper.getEndDate(planStateDate, fDCScheduleTaskInfo.getEffectTimes()
						.subtract(FDCHelper.ONE), calendar);
				// Date planEndDate = new Date();
				// if(fDCScheduleTaskInfo.getDuration() > 0){
				// planEndDate =
				// FDCDateHelper.addDays(planStateDate,fDCScheduleTaskInfo
				// .getDuration()-1);
				// }else{
				// planEndDate =
				// FDCDateHelper.addDays(planStateDate,fDCScheduleTaskInfo
				// .getDuration());
				// }

				fDCScheduleTaskInfo.setEnd(endDate);
				
				if ((fDCScheduleTaskInfo.getTaskType().equals(RESchTaskTypeEnum.KEY)) || 
					      (fDCScheduleTaskInfo.getTaskType().equals(RESchTaskTypeEnum.MILESTONE))){
					fDCScheduleTaskInfo.setIsCheckNode(true);
					CheckNodeCollection cols =CheckNodeFactory.getRemoteInstance().getCheckNodeCollection("select * from where name='"+fDCScheduleTaskInfo.getName()+"'");
					if(cols.size()>0){
						fDCScheduleTaskInfo.setCheckNode(cols.get(0));
					}else{
						FDCMsgBox.showWarning(this,"???????? '"+fDCScheduleTaskInfo.getName()+"' ????????????????????????????????????????????");
						SysUtil.abort();
					}
					fDCScheduleTaskInfo.setCheckDate(endDate);
				}
				
				if (null != fDCScheduleTaskInfo.getWbs()) {
					fDCScheduleTaskInfo.setLocked(fDCScheduleTaskInfo.getWbs().isIsLocked());
					wbsCoreBaseCollection.add(fDCScheduleTaskInfo.getWbs());
				}

				/* ???????????? */
				processDepends(fDCScheduleTaskInfo, rESchTemplateTaskCollection.get(j), handlerProcessorMap, newDependTaskCollection);
				FDCScheduleTaskDependCollection cols = fDCScheduleTaskInfo.getDependEntrys();
				FDCScheduleTaskDependInfo depend = null;
				for(Iterator it = cols.iterator();it.hasNext();){
					 depend = (FDCScheduleTaskDependInfo) it.next();
					 FDCScheduleTaskInfo beforeTask = depend.getTask();
					 if (beforeTask.getPlanStart() != null || beforeTask.getPlanEnd() != null) {
						fDCScheduleTaskInfo = ScheduleHelper.computeTaskDate(fDCScheduleTaskInfo, beforeTask, depend.getPredecessorType(),
								depend.getDifference(), needProcessSet);
					} else {
						needProcessSet.add(fDCScheduleTaskInfo.getId().toString());
					}
					 
					 
				}
				
				
				newTaskCollection.add(fDCScheduleTaskInfo);
				this.editData.getTaskEntrys().add(fDCScheduleTaskInfo);
			}
			
			
			for (int i = 0; i < editData.getTaskEntrys().size(); i++) {
				FDCScheduleTaskInfo myTask = editData.getTaskEntrys().get(i);
				if (needProcessSet.contains(myTask.getId().toString())) {
					FDCScheduleTaskDependCollection cols = myTask.getDependEntrys();
					FDCScheduleTaskDependInfo depend = null;
					for (Iterator it = cols.iterator(); it.hasNext();) {
						depend = (FDCScheduleTaskDependInfo) it.next();
						FDCScheduleTaskInfo beforeTask = depend.getTask();
						if (beforeTask.getPlanStart() != null || beforeTask.getPlanEnd() != null) {
							myTask = ScheduleHelper
									.computeTaskDate(myTask, beforeTask, depend.getPredecessorType(), depend.getDifference());
						}

					}
				}
			}
			
			

			/* ?????? */
			/* ???????????? */
			try {
				setDataObject(this.editData);
				load2Gantt(this.editData);
				this.setInitEnd(false);
				setMessageText("??" + newTaskCollection.size() + "??????????????????");
				GanttTreeTable table = ganttProject.getGanttTreeTable(); 
				TaskDependencyCollectionMutator myMutator;
				Task[] tasks = ganttProject.getTaskManager().getTasks();
				Task task = null;
				for(int i=0;i<tasks.length;i++){
					task = tasks[i];
					if(task.getManager().getDependencyCollection()!=null){
						myMutator = task.getManager().getDependencyCollection().createMutator();
						myMutator.commit();
					}
					
					/* modified by zhaoqin for R140617-0259 on 2014/06/23 start */
					if(task instanceof KDTask) {
						KDTask t = (KDTask)task;
						ScheduleTaskBaseInfo info = t.getScheduleTaskInfo();
						if(null != info && !info.isIsLeaf()) {
							info.setStart(ScheduleParserHelper.parseGanttCalendarToDate(t.getStart()));
							info.setEnd(ScheduleParserHelper.parseGanttCalendarToDate(t.getEnd()));
						}
					}
					/* modified by zhaoqin for R140617-0259 on 2014/06/23 end */
					
				}
				ganttProject.getTree().getTable().repaint();
				showMessage();
				weakOnShow();

			} catch (Exception ex) {
				handUIException(ex);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
	}

	/**
	 * @discription <????????????>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/31>
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
	 * @see <????????>
	 */
	private void processDepends(FDCScheduleTaskInfo fDCScheduleTaskInfo, RESchTemplateTaskInfo rESchTemplateTaskInfo, Map handlerProcessorMap, CoreBaseCollection newDependTaskCollection) {
		FDCScheduleTaskDependCollection depends = fDCScheduleTaskInfo.getDependEntrys();

		RESchTemplateTaskPredecessorCollection rESchTemplateTaskPredecessorCollection = rESchTemplateTaskInfo.getPredecessors();
		for (int k = 0; k < rESchTemplateTaskPredecessorCollection.size(); k++) {
			RESchTemplateTaskPredecessorInfo rESchTemplateTaskPredecessorInfo = rESchTemplateTaskPredecessorCollection.get(k);

			RESchTemplateTaskInfo schTemplateTaskInfo = rESchTemplateTaskPredecessorInfo.getPredecessorTask();
			if (null == schTemplateTaskInfo) {
				continue;
			}
			Object proceObj = handlerProcessorMap.get(schTemplateTaskInfo.getId().toString());
			if (null == proceObj) {
				continue;
			}

			FDCScheduleTaskDependInfo depend = new FDCScheduleTaskDependInfo();
			depends.add(depend);
			// ????????????
			depend.setDependTask(fDCScheduleTaskInfo);
			// ????????????
			depend.setTask((FDCScheduleTaskInfo) handlerProcessorMap.get(schTemplateTaskInfo.getId().toString()));
			// ????????????
			depend.setType(rESchTemplateTaskPredecessorInfo.getPredecessorType());
			// ????????????
			depend.setDifference(rESchTemplateTaskPredecessorInfo.getDifferenceDay());
			depend.setHardness(DependHardnessEnum.Rubber);
			newDependTaskCollection.add(depend);
		}
	}

	// ????WBS
	private FDCWBSInfo createWBS(FDCScheduleTaskInfo task, FDCScheduleTaskInfo parentTask) {
		if (null == parentTask) {
			parentTask = new FDCScheduleTaskInfo();
		}
		FDCWBSInfo wbs = new FDCWBSInfo();
		wbs.setId(BOSUuid.create(wbs.getBOSType()));
		wbs.setParent(parentTask.getWbs());
		wbs.setIsEnabled(true);
		wbs.setBoolean("isNew", true);
		wbs.setIsFromTemplate(false);
		wbs.setCurProject(this.editData.getProject());
		wbs.setName(task.getName());
		wbs.setTaskType(this.editData.getScheduleType());
		if (isSpecialSchedule()) {
			// ????????????????????????????????????
			if (null != parentTask.getWbs()) {
				wbs.setAdminPerson(parentTask.getWbs().getAdminPerson());
				wbs.setRespDept(parentTask.getWbs().getRespDept());
			}
		} else {
			// ??????????????????????????????????????
			wbs.setAdminDept(SysContext.getSysContext().getCurrentCostUnit().castToFullOrgUnitInfo());
		}
		wbs.setIsUnVisible(false);
		BigDecimal effectTimes = ScheduleCalendarHelper.getEffectTimes(task.getStart(), task.getEnd(), this.editData.getCalendar());
		wbs.setEstimateDays(effectTimes.intValue());
		wbsCoreBaseCollection.add(wbs);
		return wbs;
	}

	boolean isSpecialSchedule() {
		if (null != this.editData.getScheduleType()) {
			return TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(this.editData.getScheduleType().getId().toString());
		} else {
			return false;
		}
	}

	/**
	 * @discription <????????????>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/20>
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
	 * @see <????????>
	 */
	public void actionExportPlanTemplate_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		uiContext.put("isCloseWindow", "true");
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(RESchTemplateEditOldUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

	/**
	 * @discription <????????????>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/20>
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
	 * @see <????????>
	 */
	public void exportPlanTemplate(RESchTemplateInfo schTemplateInfo) {
		if (null == schTemplateInfo.getName() || "".equals(schTemplateInfo.getName().trim())) {
			return;
		}
		EntityViewInfo view = new EntityViewInfo();
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("dependEntrys.*");
		view.setSelector(sic);
		FilterInfo filter = new FilterInfo();
		filter.appendFilterItem("schedule.id", this.editData.getId());
		view.setFilter(filter);
		SorterItemInfo sii = new SorterItemInfo();
		sii.setPropertyName("longNumber");
		sii.setSortType(SortType.ASCEND);
		view.getSorter().add(sii);
		try {
			FDCScheduleTaskCollection fDCScheduleTaskCollection = editData.getTaskEntrys();

			Map ttMap = new HashMap();
			Map tpMap = new HashMap();
			RESchTemplateTaskCollection rESchTemplateTaskCollection = new RESchTemplateTaskCollection();

			/* ?????? */
			for (int i = 0; i < fDCScheduleTaskCollection.size(); i++) {
				RESchTemplateTaskInfo rESchTemplateTaskInfo = new RESchTemplateTaskInfo();
				FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(i);
				/* ???????? */
				rESchTemplateTaskInfo.setId(BOSUuid.create((new RESchTemplateTaskInfo()).getBOSType()));
				/* ???? */
				rESchTemplateTaskInfo.setSeq(i + 1);
				/* ???????????? */
				rESchTemplateTaskInfo.setName(fDCScheduleTaskInfo.getName());
				/* ???? ???????? */
				rESchTemplateTaskInfo.setTaskType(fDCScheduleTaskInfo.getTaskType());
				/* ???????????? */
				for (int s = 0; s < fDCScheduleTaskInfo.getBizType().size(); s++) {
					ScheduleTaskBizTypeInfo scheduleTaskBizTypeInfo = fDCScheduleTaskInfo.getBizType().get(s);
					RESchTemplateTaskBizTypeInfo rESchTemplateTaskBizTypeInfo = new RESchTemplateTaskBizTypeInfo();
					rESchTemplateTaskBizTypeInfo.setId(BOSUuid.create((new RESchTemplateTaskBizTypeInfo()).getBOSType()));
					rESchTemplateTaskBizTypeInfo.setBizType(scheduleTaskBizTypeInfo.getBizType());
					rESchTemplateTaskInfo.getBusinessType().add(rESchTemplateTaskBizTypeInfo);
				}
				/* ???????? */
				rESchTemplateTaskInfo.setReferenceDay(fDCScheduleTaskInfo.getDuration());
				/* ???????????? */
				rESchTemplateTaskInfo.setAchievementType(fDCScheduleTaskInfo.getAchievementType());
				/* ???????????? */
				rESchTemplateTaskInfo.setStandardTask(fDCScheduleTaskInfo.getStandardTask());
				/* ???????? */
				rESchTemplateTaskInfo.setSimpleName(fDCScheduleTaskInfo.getDescription());
				/* ???????? */
				rESchTemplateTaskInfo.setTemplate(schTemplateInfo);
				/* ???????? */
				rESchTemplateTaskInfo.setNumber(fDCScheduleTaskInfo.getNumber());
				ttMap.put(fDCScheduleTaskInfo.getId().toString(), rESchTemplateTaskInfo);
				if (null != fDCScheduleTaskInfo.getParent() && null != fDCScheduleTaskInfo.getParent().getId()) {
					tpMap.put(rESchTemplateTaskInfo.getId().toString(), fDCScheduleTaskInfo.getParent().getId());
				}
				/* ???????? */
				FDCScheduleTaskDependCollection dependEntrys = fDCScheduleTaskInfo.getDependEntrys();
				if (dependEntrys != null && dependEntrys.size() > 0) {
					for (int j = 0; j < dependEntrys.size(); j++) {
						RESchTemplateTaskPredecessorInfo preInfo = new RESchTemplateTaskPredecessorInfo();
						preInfo.setParent(rESchTemplateTaskInfo);
						preInfo.setTask(rESchTemplateTaskInfo);
						preInfo.setPredecessorType(dependEntrys.get(j).getPredecessorType());
						preInfo.setDifferenceDay(dependEntrys.get(j).getDifference());
						preInfo.put("depTaskID", dependEntrys.get(j).getDependTask().getId().toString());
						rESchTemplateTaskInfo.getPredecessors().add(preInfo);
					}
				}
				/* ?????????? */
				String longNumber = fDCScheduleTaskInfo.getLongNumber();
				rESchTemplateTaskInfo.setLongNumber(longNumber.replaceAll("\\.", "!"));
				/* ???????? */
				rESchTemplateTaskInfo.setLevel(fDCScheduleTaskInfo.getLevel());
				/* ???????????????? */
				rESchTemplateTaskInfo.setIsLeaf(fDCScheduleTaskInfo.isIsLeaf());
				rESchTemplateTaskCollection.add(rESchTemplateTaskInfo);
			}

			CoreBaseCollection coreBaseCollection = new CoreBaseCollection();
			/* ?????? */
			Set ids = new HashSet();
			for (int j = 0; j < rESchTemplateTaskCollection.size(); j++) {
				RESchTemplateTaskInfo rESchTemplateTaskInfo = rESchTemplateTaskCollection.get(j);
				Object pObject = tpMap.get(rESchTemplateTaskInfo.getId().toString());
				/* ?????????????????? */
				if (null != pObject && !"".equals(pObject.toString().trim())) {
					Object sObject = ttMap.get(pObject.toString().trim());
					if (null != sObject && sObject instanceof RESchTemplateTaskInfo) {
						RESchTemplateTaskInfo chTemplateTaskInfo = (RESchTemplateTaskInfo) sObject;
						rESchTemplateTaskInfo.setParent(chTemplateTaskInfo);
						coreBaseCollection.add(chTemplateTaskInfo);
					}
				}
				/* ???????? */
				if (rESchTemplateTaskInfo.getPredecessors() != null && rESchTemplateTaskInfo.getPredecessors().size() > 0) {
					for (int k = 0; k < rESchTemplateTaskInfo.getPredecessors().size(); k++) {
						RESchTemplateTaskPredecessorInfo preInfo = rESchTemplateTaskInfo.getPredecessors().get(k);
						preInfo.setPredecessorTask(preInfo.getTask());
						String depTaskID = (String) preInfo.get("depTaskID");
						RESchTemplateTaskInfo depInfo = (RESchTemplateTaskInfo) ttMap.get(depTaskID);
						ids.add(depInfo.getId().toString());
						preInfo.setTask(depInfo);
						preInfo.setParent(depInfo);
					}
				}
				RESchTemplateTaskFactory.getRemoteInstance().addnew(rESchTemplateTaskInfo);
			}
			FDCSQLBuilder sql = new FDCSQLBuilder();
			sql.appendSql(" update T_SCH_RESchTplTaskPredecessor set FParentID = FTaskID where FTaskID in ");
			sql.appendSql(FMHelper.setTran2String(ids));
			sql.executeUpdate();
			// RESchTemplateTaskFactory.getRemoteInstance().updateBatchData(
			// coreBaseCollection);
		} catch (BOSException e) {
			handUIException(e);
		} catch (Exception e) {
			logger.error(e.getMessage());
			handUIException(e);
		}
	}

	protected IObjectValue createNewData() {
		FDCScheduleInfo info = null;
		try {
			info = getNewData();
			info.setCreator(SysContext.getSysContext().getCurrentUserInfo());
			info.setViewDate(new Date());
		} catch (Exception e) {
			// ????????????????????????????,??????????
			if (null == tempException) {
				tempException = e;
			}
		}
		info.setCreateTime(new Timestamp(System.currentTimeMillis()));
		return info;
	}

	protected IObjectValue createNewDetailData(KDTable table) {
		return null;
	}

	protected ICoreBase getBizInterface() throws Exception {
		IFDCSchedule schedule = null;
		try {
			schedule = FDCScheduleFactory.getRemoteInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return schedule;
	}

	private void addEffectTable() {
		if (null != getScheduleGanttProject()) {
			EffectDegreePanel effectDegreePanel = new EffectDegreePanel();
			effectDegreePanel.getCBEffect().addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JComboBox cb = (JComboBox) e.getSource();
					filterEffectTableData((String) cb.getSelectedItem());
				}
			});
			if (kDPanel1.getComponentCount() < 2) {
				//??getComponentCount()==2??????????????????kdTable??effectDegreePanel??????????????effectDegreePanel??
				kDPanel1.add(effectDegreePanel, BorderLayout.NORTH);
			}
			getScheduleGanttProject().getTabs().add(kDPanel1, "????????");
		}
	}

	/**
	 * 
	 * ??????????????????????????????????????????????
	 * @Author??owen_wen
	 * @param effectDegree ??????????comboBox??????????
	 * @CreateTime??2013-11-28
	 */
	private void filterEffectTableData(String effectDegree) {
		for (int i = 0; i < kDTable1.getRowCount(); i++) {
			FDCScheduleTaskInfo scheduleTask = (FDCScheduleTaskInfo) kDTable1.getRow(i).getUserObject();
			if ("????????".equals(effectDegree)) {
				kDTable1.getRow(i).getStyleAttributes().setHided(false);
			} else {
				kDTable1.getRow(i).getStyleAttributes().setHided(!scheduleTask.getTaskType().toString().equals(effectDegree));
			}
		}
	}

	/**
	 * ????????????????
	 */
	private void removeEffectTable() {
		if (null != getScheduleGanttProject()) {
			getScheduleGanttProject().getTabs().remove(kDPanel1);
		}
	}


	protected void initWorkButton() {
		super.initWorkButton();
		actionAudit.setVisible(false);
		actionUnAudit.setVisible(false);
		actionClose.setVisible(false);
		actionUnClose.setVisible(false);
		actionAddNew.setVisible(false);
		actionAddNew.setEnabled(false);
		this.actionCritical.setVisible(false);
		this.actionCritical.setEnabled(false);
		this.actionDisplayAll.setVisible(false);
		this.actionHideOther.setVisible(false);

		actionAudit.putValue(Action.SMALL_ICON, FDCClientHelper.ICON_AUDIT);
		actionSaveNewTask.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_saveas"));
		actionPert.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_estatechart"));

		actionDisplayAll.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_entirely"));
		actionHideOther.putValue(Action.SMALL_ICON, EASResource.getIcon("imgTbtn_partshow"));
		btnRestore.setIcon(EASResource.getIcon("imgTbtn_restore"));
		btnEdit.setIcon(EASResource.getIcon("imgTbtn_bluepencil"));
		btnAdjust.setIcon(EASResource.getIcon("imgTbtn_edit"));
		btnClose.setVisible(false);
		btnUnClose.setVisible(false);
		actionCompareVer.setEnabled(true);
		// btnImportPlanTemplate.setVisible(false);
		// btnImportProject.setVisible(false);
		// btnExportPlanTemplate.setVisible(false);
		// btnExportProject.setVisible(false);
		actionLocate.setEnabled(true);

		btnImportPlanTemplate.setIcon(EASResource.getIcon("imgTbtn_upenumnew"));
		btnExportPlanTemplate.setIcon(EASResource.getIcon("imgTbtn_upenumdel"));
		btnImportProject.setIcon(EASResource.getIcon("imgTbtn_checklose"));
		btnExportProject.setIcon(EASResource.getIcon("imgTbtn_checkprofit"));
		btnShowByLevel.setVisible(true);
		btnShowByLevel.setText("????????");
		btnHisVerion.setIcon(EASResource.getIcon("imgTbtn_sealup"));
		btnCompareVer.setIcon(EASResource.getIcon("imgTbtn_differentia"));

		toolBar.remove(btnZoomLeft);
		toolBar.remove(btnZoomRight);
		KDMenuItem pre = new KDMenuItem(actionZoomLeft);
		pre.setText("??");
		pre.setToolTipText("??????????????");
		btnZoomCenter.addAssistMenuItem(pre);
		KDMenuItem back = new KDMenuItem(actionZoomRight);
		back.setText("??");
		back.setToolTipText("??????????????");
		btnZoomCenter.addAssistMenuItem(back);
	}

	public void onShow() throws Exception {
		if (this.getUIWindow() instanceof Window) {
			Window window = (Window) this.getUIWindow();
			// ??????????????????window??null
			if (window != null) {
				if (window instanceof JFrame) {
					((JFrame) window).setExtendedState(JFrame.MAXIMIZED_BOTH);
				} else {
					window.setSize(Toolkit.getDefaultToolkit().getScreenSize());
					window.requestFocus();
				}
			}
		}
		super.onShow();
		if (getUIContext().get("isFromWorkflow") != null && Boolean.valueOf(getUIContext().get("isFromWorkflow").toString()).booleanValue()) {
			this.contCurProject.setEnabled(false);
		}
		btnAttachment.setVisible(false);
		this.btnDisplayAll.setVisible(false);
		this.btnHideOther.setVisible(false);
	}

	public void loadFields() {
		super.loadFields();
	}

	protected FDCScheduleInfo getNewData() throws EASBizException, BOSException {
		/*
		 * FDCScheduleInfo info=new FDCScheduleInfo(); ScheduleDemo demo=new
		 * ScheduleDemo(); info=demo.getTestData();
		 */
		Map param = new HashMap();
		CurProjectInfo prj = (CurProjectInfo) getUIContext().get("CurProject");
		if (prj == null) {
			FDCMsgBox.showWarning(this, "??????????????");
			SysUtil.abort();
		}
		param.put("adminDeptId", SysContext.getSysContext().getCurrentAdminUnit());// ????????
		param.put("prjId", prj.getId().toString());// ????????
		param.put("taskTypeId", TaskTypeInfo.TASKTYPE_MAINTASK);// ????????????
		FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getNewData(param);
		return info;
	}

	protected IObjectValue getValue(IObjectPK pk) throws Exception {
		
		if(getUIContext().get("isFromWorkflow") != null 
				&&	Boolean.valueOf(getUIContext().get("isFromWorkflow").toString()).booleanValue() 
				&& getUIContext().get("DATAOBJECTS") != null){
			editData = (FDCScheduleInfo) ((Map)(getUIContext().get("DATAOBJECTS"))).get("billInfo");
			BOSUuid billId = null;
			if (editData == null) {
				billId = BOSUuid.read(getUIContext().get("ID").toString());
			} else {
				billId = editData.getId();
				if (editData.getProjectSpecial() != null) {

				}
			}
			FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(new ObjectUuidPK(billId));
			return info;
		}else{
			// ????????????getValue??????????????????????
			if (this.editData != null) {
				SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("number");
				sic.add("state");
				FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getFDCScheduleInfo(pk, sic);
				if (info.getNumber() != null && info.getNumber().length() > 0) {
					editData.setNumber(info.getNumber());
				}
				return this.editData;
			}

			Map param = new HashMap();
			param.put("isFormExecuteUI", Boolean.valueOf(isFromExecuteUI()));
			param.put("isFormTotalUI", Boolean.valueOf(isFromTotalUI()));
			FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(isMainSchedule(), false, null, null, param);
			info.setEditable(true);
			return info;
			
		}
	}

	protected boolean isMainSchedule() {
		return true;
	}

	public void setOprtState(String oprtType) {
		if (oprtType == null) {
			oprtType = STATUS_VIEW;
		}
		super.setOprtState(oprtType);
		try {
			changePageState();
		} catch (BOSException e) {
			handleException(e);
		} catch (SQLException e) {
			handleException(e);
		}
	}

	/***
	 * ???????????????????????????????????? ??????????????????????????????????????????????????????????????????????????????????????
	 * ??????????????????????????????????????????????
	 * 
	 */
	private List canEditState() {
		List arrayList = new ArrayList();
		arrayList.add(FDCBillStateEnum.SAVED_VALUE);
		arrayList.add(FDCBillStateEnum.SUBMITTED_VALUE);
		return arrayList;
	}

	private List canAuditState() {
		List arrayList = new ArrayList();
		arrayList.add(FDCBillStateEnum.SUBMITTED_VALUE);
		return arrayList;
	}

	private float getMaxVersion(FDCScheduleInfo editData) throws BOSException, SQLException {
		float ver = 1.0f;
		FDCSQLBuilder builder = new FDCSQLBuilder();
		builder.appendSql("select max(fversion) from t_sch_fdcschedule where fprojectid = ?");
		if (editData == null || editData.getProject() == null) {
			return ver;
		} else {
			builder.addParam(editData.getProject().getId().toString());
		}
		if (editData.getProjectSpecial() != null) {
			builder.appendSql("and fprojectspecialid = ? ");
			builder.addParam(editData.getProjectSpecial().getId().toString());
		} else {
			// ????SqlServer??????????????????????????
			builder.appendSql("and fprojectspecialid is null or (fprojectspecialid = '' and fprojectid =  ? )");
			builder.addParam(editData.getProject().getId().toString());
		}
		IRowSet rs = builder.executeQuery();
		while (rs.next()) {
			ver = rs.getFloat(1);
		}
		return ver;
	}

	/**
	 * ??????????????????????????????????????????????
	 * 
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected void changePageState() throws BOSException, SQLException {
		if (editData == null) {
			return;
		}
		boolean isTotalUI = isFromTotalUI();
		boolean isFromExUI = isFromExecuteUI();
		boolean isEdit = oprtState.equals(STATUS_EDIT);
		boolean isNewest = editData.getVersion() == 0 ? true : editData.getVersion() == getMaxVersion(editData);
		boolean isAudited = editData.getState() == ScheduleStateEnum.AUDITTED;
		// ????????????
		if (isEdit) {
			addEffectTable();
		} else {
			removeEffectTable();
		}
		// ????????????????
		initBaseAction(isFromExUI, isTotalUI, isEdit, isNewest, isAudited);
		// ????????????????
		initImportExportAction(isTotalUI, isFromExUI, isEdit);
		// ??????????????
		initGanttActions(isEdit,isTotalUI,isFromExUI);
		
		if (isFromWf()) {
			this.btnSave.setEnabled(true);
			this.btnSave.setVisible(true);
			addEffectTable();
		}
	}

	private void initGanttActions(boolean isEdit, boolean isTotalUI, boolean isFromExUI) {
	    initGanttActions(isEdit);
	    boolean isShow = true;
	    if(isTotalUI){
	    	isShow = false;
	    }
	    
	    if(isFromExUI){
	    	isShow = false;
	    }

	    /* modified by zhaoqin for R140624-0238 on 2015/01/19 */
	   	actionBatChangeRespDept.setVisible(isShow);
	   	actionBatChangeRespDept.setEnabled(isEdit);
	   	
//	    this.actionZoomCenter.setVisible(true);
//	    this.menuItemZoomCenter.setEnabled(true);
//		this.btnZoomCenter.setEnabled(true);
//	    this.actionZoomCenter.setEnabled(true);
//		this.actionZoomIn.setVisible(true);
//		this.actionZoomOut.setVisible(true);
//		this.actionZoomLeft.setVisible(true);
//		this.actionZoomRight.setVisible(true);
	   	
		this.actionZoomCenter.setVisible(false);
		this.btnZoomCenter.setVisible(false);
		this.actionZoomIn.setVisible(false);
		this.btnZoomIn.setVisible(false);
		this.actionZoomOut.setVisible(false);
		this.btnZoomOut.setVisible(false);
		this.actionGraphOption.setVisible(false);
		this.menuView.setVisible(false);
	}

	/**
	 * ????????action????
	 * 
	 * @param isFromExUI
	 *            ????????UI
	 * @param isTotalUI
	 *            ??????????????UI
	 * @param isEdit
	 *            ??????????
	 * @param isNewest
	 *            ????????????
	 * @param isAudited
	 *            ??????????
	 * @throws BOSException
	 * @throws SQLException
	 */
	protected void initBaseAction(boolean isFromExUI, boolean isTotalUI, boolean isEdit, boolean isNewest, boolean isAudited) throws BOSException, SQLException {
		editData.setEditable(isEdit);
		if (isFromExUI || isTotalUI) {
			// ????????????????
			actionEdit.setVisible(false);
			actionAdjust.setVisible(false);
			actionRestore.setVisible(false);
			actionSave.setVisible(false);
			actionSubmit.setVisible(false);
			btnHisVerion.setVisible(false);
			actionCompareVer.setVisible(false);
		} else {
			float maxVer = getMaxVersion(editData);
			boolean hasHisVer = maxVer > 0;
			if (hasHisVer && !isNewest) {
				// ??????????????????????????????
				actionEdit.setVisible(false);
				actionAdjust.setVisible(false);
				actionRestore.setVisible(false);
				actionSave.setVisible(false);
				actionSubmit.setVisible(false);
				actionCompareVer.setVisible(false);
			} else {
				// ????????
				actionAdjust.setVisible(isAudited);
				actionEdit.setVisible(!isAudited);

				// ????????
				actionSubmit.setVisible(isEdit);
				actionSubmit.setEnabled(isEdit);
				actionRestore.setVisible(isEdit);
				actionRestore.setEnabled(isEdit);
				// ????????
				if (editData.getState() != null && canAuditState().contains(editData.getState().getValue())) {
					actionSave.setVisible(false);
					actionSave.setEnabled(false);
					actionAudit.setVisible(true);
					actionAudit.setEnabled(true);
				} else {
					actionSave.setVisible(isEdit);
					actionSave.setEnabled(isEdit);
					actionAudit.setVisible(false);
					actionAudit.setEnabled(false);
				}
				// ????????
				btnHisVerion.setVisible(hasHisVer);
				btnHisVerion.setEnabled(hasHisVer);
				actionCompareVer.setVisible(hasHisVer);
				actionCompareVer.setEnabled(hasHisVer);
			}
		}
	}

	/**
	 * ??????????????
	 * 
	 * @param isEdit
	 */
	protected void initGanttActions(boolean isEdit) {
		actionLocate.setEnabled(true);
		getScheduleGanttProject().isOnlyViewer = !isEdit;
		actionBatchModifyTaskType.setEnabled(isEdit);
	}

	/**
	 * ????????action????????
	 * 
	 * @param isTotalUI
	 * @param isEdit
	 * @param isFromExUI
	 */
	protected void initImportExportAction(boolean isTotalUI, boolean isFromExUI, boolean isEdit) {
		if (isTotalUI) {
			actionExportPlanTemplate.setVisible(false);
			actionExportPlanTemplate.setEnabled(false);
			actionExportProject.setVisible(false);
			actionExportProject.setEnabled(false);
			actionImportPlanTemplate.setVisible(false);
			actionImportPlanTemplate.setEnabled(false);
			actionImportProject.setVisible(false);
			actionImportProject.setEnabled(false);
		} else {
			actionExportPlanTemplate.setVisible(!isFromExUI);
			actionExportPlanTemplate.setEnabled(!isFromExUI);
			actionExportProject.setVisible(!isFromExUI);
			actionExportProject.setEnabled(!isFromExUI);

			boolean canImport = !isFromExUI && isEdit;
			actionImportPlanTemplate.setVisible(canImport);
			actionImportPlanTemplate.setEnabled(canImport);
			actionImportProject.setVisible(canImport);
			actionImportProject.setEnabled(canImport);
		}
	}


	public boolean haveHistory(float version) {
		return version > 1f;
	}

	public boolean haveTaskEntrys(FDCScheduleInfo info) {
		return null != info && null != info.getTaskEntrys() && info.getTaskEntrys().size() > 0;
	}


	/**
	 * ????????EditUI??????
	 * 
	 * @throws Exception
	 * @return IObjectPK
	 */
	public IObjectPK runSave() throws Exception {
		// ????????????????????BOTP??????????
		if (UtilRequest.isPrepare("ActionSave", this)) {// TODO ??????????????
			Object pk = ActionCache.get(ResponseConstant.FRAMEWORK_PK);
			if (pk != null) {
				return (IObjectPK) pk;
			}
		}
		return getBizInterface().save(getSaveSchedule());
	}

	/**
	 * ????????EditUI??????
	 * 
	 * @throws Exception
	 * @return IObjectPK
	 */
	public IObjectPK runSubmit() throws Exception {
		if (getBizInterface() == null) {
			throw new Exception("don't implement getBizInterface()  !");
		}
		if (UtilRequest.isPrepare("ActionSubmit", this)) {// TODO ??????????????
			Object pk = ActionCache.get(ResponseConstant.FRAMEWORK_PK);
			if (pk != null) {
				return (IObjectPK) pk;
			}
		}
		return getBizInterface().submit(getSaveSchedule());
	}

	protected void initCtrlListner() {

	}

	/** ?????????????????????????????? */
	protected void beforeOnload() {
		if (!SysContext.getSysContext().getCurrentOrgUnit().isIsCostOrgUnit()) {
			FDCMsgBox.showError("????????????????????????????????????");
			abort();
		}

		// ????????????????????????????????????????????????
		ActivityCache cache = ActivityCache.getInstance();
		cache.clearScheduleCalendar();

		if (getColumnsProperty() == null) {
			try {
				columnsProperty = ScheduleTaskPropertyHelper.getAllColumns(ScheduleClientHelper.getUIMark(this));
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}

	/** ??????????????????????,???????????????????????????? 'FDCScheduleBaseHelper' modify by duhongming **/
	protected FDCScheduleInfo getSaveSchedule() {
		editData.setProject((CurProjectInfo) prmtCurproject.getValue());
		return new FDCScheduleBaseHelper(editData, this).getSaveSchedule();
	}

	protected void verifyInput(ActionEvent e) throws Exception {
		super.verifyInput(e);
		// if (cbState != null || cbState.getSelectedItem() == null ||
		// cbState.getSelectedItem() == ScheduleStateEnum.SAVED) {
		// this.verifyInputForSave();
		// } else {
		// this.verifyInputForSubmit();
		// }
	}

	protected void verifyInputForSave() throws Exception {
		if (!haveTaskEntrys(editData)) {
			FDCMsgBox.showWarning(this, "??????????????????????????");
			abort();
		}
	}

	protected void verifyInputForSubmit() throws Exception {
		if (!haveTaskEntrys(editData)) {
			FDCMsgBox.showWarning(this, "??????????????????????????");
			abort();
		}
		int size = editData.getTaskEntrys().size();
		FDCScheduleTaskInfo taskInfo = null;
		StringBuffer str = new StringBuffer("????\t????????\t????????\t\t\n");
		int err = 0;
		if (editData.getProjectSpecial() == null) {
			for (int i = 0; i < size; i++) {
				taskInfo = editData.getTaskEntrys().get(i);
				if (null == taskInfo.getBizType() || taskInfo.getBizType().isEmpty()) {
					continue;
				}
				for (int j = 0; j < taskInfo.getBizType().size(); j++) {
					ScheduleBizTypeInfo taskBizType = taskInfo.getBizType().get(j).getBizType();
					if (taskBizType.getId().toString().equals("Rz+dS7ECSfqM4kEJqGawYWLF6cA=") && taskInfo.getAchievementType() == null) {
						err++;
						str.append(i + 1 + "\t" + taskInfo.getName() + "\t????????????\n");
					}

				}
				
			}
			if (err > 0) {
				FDCMsgBox.showDetailAndOK(this, "????????????????????????????????", str.toString(), MsgType.UPDATE_VALUE);
				abort();
			}
		}
		
		// ??????
		// ????????????????????????????????????????????????????
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("valueAlias");
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("name", new String("??????????????????????????")));
		view.setFilter(filter);
		ParamCollection paramCollection = ParamFactory.getRemoteInstance().getParamCollection(view);
		if (paramCollection != null && paramCollection.size() > 0) {
			ParamInfo paramInfo = paramCollection.get(0);
			if (paramInfo.getValueAlias().equals("??????")) {
				FDCScheduleTaskCollection taskEntrys = editData.getTaskEntrys();
				for (int i = 0; i < taskEntrys.size(); i++) {
					FDCScheduleTaskInfo spcailTask = taskEntrys.get(i);
					FDCScheduleTaskInfo mainTask = spcailTask.getDependMainTaskID();
					if (mainTask != null) {
						Date mainEnd = mainTask.getEnd();
						Date specailEnd = spcailTask.getEnd();
						if (specailEnd.after(mainEnd)) {
							FDCMsgBox.showWarning(this, spcailTask.getName() + " ??????????????????????????????????????????????????????");
							SysUtil.abort();
						}

					}
				}
			}
		}

		FDCClientVerifyHelper.verifyRequire(this);
	}

	private boolean isSaveAction = false;

	public boolean isSaveAction() {
		return isSaveAction;
	}

	public void setSaveAction(boolean isSaveAction) {
		this.isSaveAction = isSaveAction;
	}

	private void importVerify(String taskType) {
		// if(TaskTypeInfo.TASKTYPE_MAINTASK.equals(taskType)){
		// if(FDCMsgBox.CANCEL == FDCMsgBox.showConfirm2(this,
		// "??????????????????????????????????????????????????????????????????????????????")){
		// SysUtil.abort();
		// }
		// }
		if (TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(taskType)) {
			ScheduleGanttProject ganttProject = this.getScheduleGanttProject();
			DefaultMutableTreeNode[] selectedNodes = ganttProject.getTree().getSelectedNodes();
			boolean isSelectedOne = selectedNodes != null && selectedNodes.length == 1 && !selectedNodes[0].equals(ganttProject.getTree().getRoot());
			if (!isSelectedOne || !TaskTypeInfo.TASKTYPE_MAINTASK.equals(getSelectedTask().getWbs().getTaskType().getId().toString())) {
				FDCMsgBox.showWarning(this, "????????????????????????????????????????");
				SysUtil.abort();
			}
		}
	}

	private FDCScheduleTaskInfo getSelectedTask() {
		DefaultMutableTreeNode selectedTreeNode = getScheduleGanttProject().getSelectedNode();
		if (selectedTreeNode != null) {
			return (FDCScheduleTaskInfo) ((KDTask) selectedTreeNode.getUserObject()).getScheduleTaskInfo();
		}
		return null;
	}

	public void importProject(String taskType) {
		importVerify(taskType);
		KDFileChooser chooser = new KDFileChooser(new File(preProjectFileName));
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".mpp");
			}

			public String getDescription() {
				return "Project????(*.mpp)";
			}
		});
		if (chooser.showOpenDialog(this) == KDFileChooser.APPROVE_OPTION) {
			StopWatch sw = new StopWatch();
			sw.start();
			File file = chooser.getSelectedFile();
			preProjectFileName = file.getParent();
			try {
				FDCWBSTree wbsTree = getScheduleGanttProject().getWbsTree();
				KDProjectFileReader reader = new KDProjectFileReader(editData, file, getSelectedTask(), wbsTree);
				if (reader.isEmptyTask()) {
					FDCMsgBox.showWarning(this, "??????????????????");
					SysUtil.abort();
				}
				if (reader.existEmptyName()) {
					FDCMsgBox.showWarning(this, "???????????????????? ??????????????????????");
					SysUtil.abort();
				}
				boolean isRemoveRoot = false;
				if (TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(taskType)) {
					FDCScheduleTaskInfo selectedTask = getSelectedTask();
					if (!reader.verifyDateRange(selectedTask.getStart(), selectedTask.getEnd())) {
						FDCMsgBox.showWarning(this, "??????????????/????????????????????????????");
						SysUtil.abort();
					}
					String onlyRootName = reader.getOnlyRootName();
					if (onlyRootName != null) {
						int confirm = FDCMsgBox.showConfirm3(this, "????????????" + onlyRootName + "??????????" + "??????????????");
						if (confirm == FDCMsgBox.NO) {
							isRemoveRoot = true;
						} else if (confirm == FDCMsgBox.CANCEL) {
							return;
						}
					}
				}

				logger.info("Prepare import Time: " + sw.getLastTime());
				reader.getFDCScheduleTask(isRemoveRoot);
				reader = null;
				logger.info("Parse import Time: " + sw.getLastTime());
				this.editData.setEditable(true);
				setDataObject(this.editData);
				logger.info("setDate import Time: " + sw.getLastTime());
				load2Gantt(this.editData);
				logger.info("Load2Gantt import Time: " + sw.getLastTime());
				this.setInitEnd(false);
				setMessageText("??????????????...");
				showMessage();
				weakOnShow();
				getScheduleGanttProject().setOprtState(this.getOprtState());
			} catch (MPXJException e) {
				logger.error(e.getMessage(), e);
				FDCMsgBox.showError(this, "????????" + file.getPath() + "??????????????????Project????");
			} catch (BOSException e) {
				handleException(e);
			} catch (Exception e) {
				handleException(e);
			}
		}

	}

	/**
	 * @discription <????project>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/22>
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
	 * @see <????????>
	 */
	public List importProjectNew(String taskType, KDFileChooser chooser) {
		List resultList = new ArrayList();
		importVerify(taskType);
		StopWatch sw = new StopWatch();
		sw.start();
		File file = chooser.getSelectedFile();
		preProjectFileName = file.getParent();
		try {
			FDCWBSTree wbsTree = getScheduleGanttProject().getWbsTree();
			KDProjectFileReader reader = new KDProjectFileReader(editData, file, getSelectedTask(), wbsTree);
			if (reader.isEmptyTask()) {
				FDCMsgBox.showWarning(this, "??????????????????");
				SysUtil.abort();
			}
			if (reader.existEmptyName()) {
				FDCMsgBox.showWarning(this, "???????????????????? ??????????????????????");
				SysUtil.abort();
			}
			boolean isRemoveRoot = false;
			if (TaskTypeInfo.TASKTYPE_SPECIALTASK.equals(taskType)) {
				FDCScheduleTaskInfo selectedTask = getSelectedTask();
				if (!reader.verifyDateRange(selectedTask.getStart(), selectedTask.getEnd())) {
					FDCMsgBox.showWarning(this, "??????????????/????????????????????????????");
					SysUtil.abort();
				}
				String onlyRootName = reader.getOnlyRootName();
				if (onlyRootName != null) {
					int confirm = FDCMsgBox.showConfirm3(this, "????????????" + onlyRootName + "??????????" + "??????????????");
					if (confirm == FDCMsgBox.NO) {
						isRemoveRoot = true;
					} else if (confirm == FDCMsgBox.CANCEL) {
						return resultList;
					}
				}
			}

			logger.info("Prepare import Time: " + sw.getLastTime());
			FDCScheduleTaskCollection fDCScheduleTaskCollection = reader.getFDCScheduleTask(isRemoveRoot);
			resultList.add(reader.isPersonNulls);
			resultList.add(reader.isDeptNulls);
			resultList.add(reader.isPersonRepeats);
			resultList.add(reader.isDeptRepeats);
			reader = null;
			logger.info("Parse import Time: " + sw.getLastTime());
			this.editData.setEditable(true);
			setDataObject(this.editData);
			logger.info("setDate import Time: " + sw.getLastTime());
			load2Gantt(this.editData);
			logger.info("Load2Gantt import Time: " + sw.getLastTime());
			this.setInitEnd(false);
			setMessageText("??????????????...");
			showMessage();
			weakOnShow();
			getScheduleGanttProject().setOprtState(this.getOprtState());
			setMessageText("????????" + fDCScheduleTaskCollection.size() + "????????");
			showMessage();

		} catch (MPXJException e) {
			logger.error(e.getMessage(), e);
			FDCMsgBox.showError(this, "????????" + file.getPath() + "??????????????????Project????");
		} catch (BOSException e) {
			handleException(e);
		} catch (Exception e) {
			handleException(e);
		}
		return resultList;
	}

	/**
	 * @discription <????????????????????>
	 * @author <Xiaolong Luo>
	 * @createDate <2011/08/30>
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
	public void saveDepAndPerson(FDCScheduleTaskCollection fDCScheduleTaskCollection) throws Exception {
		FDCScheduleTaskCollection scheduleTaskCollection = this.editData.getTaskEntrys();
		for (int k = 0; k < scheduleTaskCollection.size(); k++) {
			FDCScheduleTaskInfo scheduleTaskInfo = scheduleTaskCollection.get(k);
			for (int j = 0; j < fDCScheduleTaskCollection.size(); j++) {
				FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(j);
				if (scheduleTaskInfo.getId().toString().equals(fDCScheduleTaskInfo.getId().toString())) {
					scheduleTaskInfo.setAdminPerson(fDCScheduleTaskInfo.getAdminPerson());
					scheduleTaskInfo.setAdminDept(fDCScheduleTaskInfo.getAdminDept());
				}
			}
		}

		/* ???????????? */
		setDataObject(this.editData);
		load2Gantt(this.editData);
		this.setInitEnd(false);
		setMessageText("??????????????...");
		showMessage();
		weakOnShow();
	}

	private FDCScheduleTaskCollection getSelectedTasks4Export() {
		FDCScheduleTaskCollection tasks = new FDCScheduleTaskCollection();
		DefaultMutableTreeNode[] selectedNodes = getScheduleGanttProject().getTree().getSelectedNodes();
		if (selectedNodes != null) {
			for (int i = 0; i < selectedNodes.length; ++i) {
				Object userObject = selectedNodes[i].getUserObject();
				if (userObject != null && userObject instanceof KDTask) {
					tasks.add((FDCScheduleTaskInfo) ((KDTask) userObject).getScheduleTaskInfo());
				}
			}
		}
		Set parentTasks = new HashSet();
		for (int i = 0; i < tasks.size(); ++i) {
			FDCScheduleTaskInfo parent = tasks.get(i).getParent();
			if (parent != null) {
				parentTasks.add(parent.getId().toString());
			} else {
				parentTasks.add(null);
			}
		}
		if (parentTasks.size() > 1) {
			FDCMsgBox.showWarning(this, "?????????????????????????? ????????");
			SysUtil.abort();
		}
		return tasks;
	}

	public void actionExportProject_actionPerformed(ActionEvent e) throws Exception {
		beforeCheck();
		FDCScheduleTaskCollection selectedTasks = getSelectedTasks4Export();
		if ((selectedTasks == null || selectedTasks.isEmpty()) && (editData.getTaskEntrys() == null || editData.getTaskEntrys().isEmpty())) {
			FDCMsgBox.showWarning(this, "??????????????????");
			return;
		}
		KDFileChooser chooser = new KDFileChooser(new File(preProjectFileName));
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
			}

			public String getDescription() {
				return "XML ????(*.xml)";
			}
		});
		String defaultName = editData.getName();
		if (FDCHelper.isEmpty(defaultName)) {
			defaultName = editData.getProject().getName();
		}
		chooser.setSelectedFile(new File(preProjectFileName + defaultName + ".xml"));
		if (chooser.showSaveDialog(this) == KDFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			KDProjectWriter projectWriter = new KDProjectWriter(this.editData.getTaskEntrys(), null);
			projectWriter.write(file);
		}
	}

	public void actionSave_actionPerformed(ActionEvent e) throws Exception {
		verifyInputForSave();
		logger.info("actionSave_actionPerformed begin...");
		if (FDCHelper.isEmpty(prmtCurproject.getValue())) {
			FDCMsgBox.showError(this, "????????,??????????????????");
			SysUtil.abort();
		}
		try {
			this.setSaveAction(true);
			editData.setEffectDegree(ganttProject.getTaskEffectDegree());
    		super.actionSave_actionPerformed(e);
			cbState.setSelectedItem(ScheduleStateEnum.SAVED);
			editData.setState(ScheduleStateEnum.SAVED);
			txtVersion.setText(editData.getVersionName());
		} catch (EASBizException ex) {
			throw ex;
		} catch (BOSException ex) {
			throw ex;
		} catch (AbortException ex) {
			throw ex;
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex.getCause(), ex);
			FDCMsgBox.showError("??????????Project????????????");
		}
		logger.info("actionSave_actionPerformed end...");
	}

	public void actionSubmit_actionPerformed(ActionEvent e) throws Exception {
		verifyInputForSubmit();
		logger.info("actionSubmit_actionPerformed begin...");
		try {
			btnAudit.setVisible(true);
			btnAudit.setEnabled(true);
			this.setSaveAction(false);
			editData.setState(ScheduleStateEnum.SUBMITTED);
			cbState.setSelectedItem(ScheduleStateEnum.SUBMITTED);
			if(!isFromExecuteUI() && isMainSchedule()){				
				verifyIncludeGlobalTask(editData.getTaskEntrys());
			}
			editData.setEffectDegree(ganttProject.getTaskEffectDegree());
			super.actionSubmit_actionPerformed(e);
			this.actionSave.setEnabled(false);
			setOprtState(oprtState);
		} catch (EASBizException ex) {
			throw ex;
		} catch (BOSException ex) {
			throw ex;
		} catch (AbortException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error(ex.getCause(), ex);
			FDCMsgBox.showError("??????????Project????????????");
		}
		logger.info("actionSubmit_actionPerformed end...");
	}

	/**
	 * @description ????
	 * @author ??????
	 * @createDate 2011-9-19
	 * @version EAS7.0
	 * @see
	 */

	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		if (!editData.getState().equals(ScheduleStateEnum.SUBMITTED)) {
			FDCMsgBox.showError("??????????????" + editData.getState() + ",????????????????");
			abort();
		}
		cbState.setSelectedItem(ScheduleStateEnum.AUDITTED);
		editData.setState(ScheduleStateEnum.AUDITTED);
		/** ?????? ???????????? **/
		runSave();
		FDCScheduleFactory.getRemoteInstance().audit(editData.getId());
		FDCMsgBox.showInfo(this, "????????!");
		setOprtState(STATUS_VIEW);
		ganttProject.setOprtState(STATUS_VIEW);
	}

	public void actionSaveNewTask_actionPerformed(ActionEvent e) throws Exception {
		super.actionSaveNewTask_actionPerformed(e);
		FDCScheduleFactory.getRemoteInstance().saveNewTask(getNewTaskScheduleInfo());
	}

	public FDCScheduleInfo getNewTaskScheduleInfo() {
		FDCScheduleInfo info = new FDCScheduleInfo();
		info.setId(this.editData.getId());
		info.setProject(this.editData.getProject());
		info.setCalendar(this.editData.getCalendar());
		for (Iterator iter = this.editData.getTaskEntrys().iterator(); iter.hasNext();) {
			FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) iter.next();
			if (task.isAdd()) {
				// verifyTaskRequired(task);
				info.getTaskEntrys().add(task);
			}
		}
		return info;
	}

	protected UIContext getPropertyUIContext() {
		UIContext uiContext = super.getPropertyUIContext();
		// ????????????????????????
		uiContext.put("OprtState", getOprtState());

		if (getOprtState() != null && getOprtState().equals(OprtState.VIEW)) {
			List selectedTasks = this.getScheduleGanttProject().getTaskSelectionContext().getSelectedTasks();
			if (selectedTasks.size() > 0) {
				KDTask task = (KDTask) selectedTasks.get(0);
				if (task.isEditable()) {
					uiContext.put("OprtState", OprtState.EDIT);
				}
			}
		}

		// ??????????????????????????????????????
		if (getOprtState() != null && getOprtState().equals(OprtState.EDIT)) {
			List selectedTasks = this.getScheduleGanttProject().getTaskSelectionContext().getSelectedTasks();
			if (selectedTasks.size() > 0) {
				KDTask task = (KDTask) selectedTasks.get(0);
				if (!task.isEditable()) {
					uiContext.put("OprtState", OprtState.VIEW);
				}
			}
		}
		uiContext.put("isScheduleExec", getUIContext().get("isScheduleExec"));
		return uiContext;
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		/* modified by zhaoqin for R20150105-0160 on 2015/01/15 */
		if(null == prmtCurproject.getValue()) {
			FDCMsgBox.showWarning("????????????????,????????????????!");
			abort();
		}
		
		if (this.editData != null && this.editData.getState() == ScheduleStateEnum.EXETING ) {
			FDCMsgBox.showInfo(this, "??????????,????????????????????????????");
			getUIContext().put("isScheduleExec", Boolean.TRUE);
			this.actionEdit.setVisible(true);
			this.actionEdit.setEnabled(false);
		} else if(this.editData != null && this.editData.getState() == ScheduleStateEnum.AUDITTING ){
			FDCMsgBox.showInfo(this, "??????????????????????????????!");
			abort();
		}else{
			super.actionEdit_actionPerformed(e);
			this.setSaveAction(true);
			/*
			 * Added by Owen_wen 2013-12-16
			 * ????????super.actionSave_actionPerformed(e)????????????????
			 *  1. ??????????????????????????????????bug
			 *  2. ??doAfterSave??????editData.setEditable(true), ????????????????????????
			 */
			super.actionSave_actionPerformed(e);
			editData.setState(ScheduleStateEnum.SAVED);
			cbState.setSelectedItem(ScheduleStateEnum.SAVED);
			txtVersion.setText(editData.getVersionName()); 
		}
	}

	public SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = super.getSelectors();
		sic.add("name");
		sic.add("number");
		sic.add("id");
		sic.add("*");
		sic.add("taskEntrys.dependMainTaskID.id");
		sic.add("taskEntrys.dependMainTaskID.name");
		sic.add("taskEntrys.dependMainTaskID.number");
		return sic;
	}

	/** ?????????????? **/
	public FDCScheduleInfo scheduleInfo;
	/** ???????????????? **/
	private Map tasksMap;

	/**
	 * @description ????????????
	 * @author ??????
	 * @createDate 2011-9-9
	 * @version EAS7.0
	 * @see
	 */
	public void getAllTask() throws BOSException {
		tasksMap = new HashMap();
		FDCScheduleTaskCollection fDCScheduleTaskCollection = FDCScheduleTaskFactory.getRemoteInstance().getFDCScheduleTaskCollection();
		for (int k = 0; k < fDCScheduleTaskCollection.size(); k++) {
			FDCScheduleTaskInfo fDCScheduleTaskInfo = fDCScheduleTaskCollection.get(k);
			tasksMap.put(fDCScheduleTaskInfo.getId().toString(), fDCScheduleTaskInfo);
		}
	}

	/***
	 * ????????????????
	 * 
	 * isWbsOrSchedule=True:Wbs???????????????? False:??????????????????????????????????????
	 * 
	 * add by warship at 2010/08/05
	 */
	public void actionBatChangeRespDept_actionPerformed(ActionEvent e) throws Exception {
        String oState = getOprtState();
        /* ??????????????????????????????????????????????????????????????Bug????????????????Added by Owen_wen 2013.11.1
		if (!(ScheduleStateEnum.SAVED.equals(editData.getState()) || ScheduleStateEnum.SUBMITTED.equals(editData.getState()))) {
			FDCMsgBox.showInfo(this, "??????????????????????????????????????????????");
			SysUtil.abort();
		}*/
		if (prmtCurproject.getValue() == null) {
			FDCMsgBox.showWarning("??????????????");
			SysUtil.abort();
		}
		if (editData == null || editData.getTaskEntrys() == null || editData.getTaskEntrys().size() <= 0) {
			FDCMsgBox.showWarning("????????????????");
			SysUtil.abort();
		}
		this.scheduleInfo = editData;

		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.ID, null);
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = uiFactory.create(BatchModifyResPersonUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
		oprtState = oState;
		changePageState();
	}

	/**
	 * 
	 * @description ????????????.????????????
	 * @author ??????
	 * @createDate 2011-8-25
	 * @version EAS7.0
	 * @see
	 */
	protected void prmtCurproject_dataChanged(DataChangeEvent e) throws Exception {
		// TODO ?????????? ???????????? ????????????
		Object newValue = e.getNewValue();
		Object oldValue = e.getOldValue();
//		if ((newValue == null && oldValue == null) || (newValue != null && newValue.equals(oldValue))) {
//			return;
//		}

		Map param = new HashMap();
		param.put("isFormExecuteUI", Boolean.valueOf(isFromExecuteUI()));
		if (oprtState == STATUS_EDIT && editData != null) {
			int showConfirm2 = FDCMsgBox.showConfirm2(this, "?????????????????????");
			if (showConfirm2 == 0) {
				/* modified by zhaoqin for R140325-0293 on 2014/04/29 start */
				// ????????????????????????????????????????????????????
				//if(e.getOldValue() != null)
				//prmtCurproject.setDataNoNotify(e.getOldValue());
				prmtCurproject.setDataNoNotify(oldValue);
				if (ScheduleStateEnum.SUBMITTED.equals(editData.getState())) {
					actionSubmit_actionPerformed(null);
				}
				if (ScheduleStateEnum.SAVED.equals(editData.getState())) {
					actionSave_actionPerformed(null);
				}
				//prmtCurproject.setDataNoNotify(e.getNewValue());
				prmtCurproject.setDataNoNotify(newValue);
			}
			setOprtState(STATUS_VIEW);
			/* modified by zhaoqin for R140325-0293 on 2014/04/29 end */
		}
		CurProjectInfo curProject = null;
		if (prmtCurproject.getValue() instanceof CurProjectInfo)

			curProject = (CurProjectInfo) prmtCurproject.getValue();
		if (curProject == null) {
			editData = null;
			getScheduleGanttProject().close();
			return;
		}
		ProjectSpecialInfo projectSpecial = null;
		if (this instanceof SpecialScheduleEditUI) {
			projectSpecial = (ProjectSpecialInfo) ((SpecialScheduleEditUI) this).prmtPrjSpecial.getValue();
		}
		// if (editData != null) {
		// projectSpecial = editData.getProjectSpecial();
		// }

		editData = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(isMainSchedule(), false, curProject, projectSpecial, param);
		editData.setProject(curProject);
		loadData2Gantt(editData);
		this.setUserObject(editData);
		setOprtState(STATUS_VIEW);
		if (editData.getProject() != null && editData.getTaskEntrys() != null && editData.getTaskEntrys().size() > 0) {
			String versionName = editData.getVersionName();
			if (FDCHelper.isEmpty(versionName)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				if (projectSpecial != null) {
					txtVersion.setText(editData.getProject().getName() + projectSpecial + sdf.format(new Date()) + "_" + new Float(editData.getVersion()) + "??");
				} else {
					txtVersion.setText(editData.getProject().getName() + sdf.format(new Date()) + "_" + new Float(editData.getVersion()) + "??");
				}
			} else {
				txtVersion.setText(versionName);
			}
			// if (editData.getVersion() > 1f) {
			// btnHisVerion.setEnabled(true);
			// btnHisVerion.setVisible(true);
			// } else {
			// btnHisVerion.setVisible(false);
			// btnHisVerion.setEnabled(false);
			// }
		}
	}

	protected void loadData2Gantt(FDCScheduleInfo editData) throws Exception {
		// StopWatch sw = new StopWatch();
		// sw.start();
		// logger.info("Prepare import Time: " + sw.getLastTime());
		// logger.info("Parse import Time: " + sw.getLastTime());
		setDataObject(editData);
		// logger.info("setDate import Time: " + sw.getLastTime());
		load2Gantt(editData);
		// logger.info("Load2Gantt import Time: " + sw.getLastTime());
		this.setInitEnd(false);
		setMessageText("??????????????...");
		showMessage();
		weakOnShow();
		if (haveTaskEntrys(editData)) {
			txtVersion.setText(editData.getVersionName());
			cbState.setSelectedItem(editData.getState());
		} else {
			txtVersion.setText(null);
			cbState.setSelectedItem(null);
		}
		setUserObject(editData);
		centerAndShowAll();
	}
	
	public boolean isFromWf() {
		if (getUIContext().get("isFromWorkflow") != null && Boolean.valueOf(getUIContext().get("isFromWorkflow").toString()).booleanValue()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @description ????????????,????????????
	 * @author ??????
	 * @createDate 2011-8-24
	 * @version EAS7.0
	 * @see
	 */

	public boolean destroyWindow() {
		
		
		if (isNeedRemeber && !isFromWf()) {
			try {
				IREAutoRemember autoRemember = REAutoRememberFactory.getRemoteInstance();
				UserInfo user = SysContext.getSysContext().getCurrentUserInfo();
				OrgUnitInfo orgUnit = SysContext.getSysContext().getCurrentOrgUnit();
				CurProjectInfo curProject = ((CurProjectInfo) prmtCurproject.getValue());
				Object[] objs = new Object[] { user, orgUnit, curProject };
				if (exits(objs)) {
					String userID = user.getId().toString();
					String orgUnitID = orgUnit.getId().toString();
					String curProjectID = curProject.getId().toString();
					if (isMainSchedule()) {
						autoRemember.save(userID, orgUnitID, "MainScheduleProject", curProjectID);
					} else {
						if (editData != null && editData.getProjectSpecial() != null) {
							String projectSpecialId = editData.getProjectSpecial().getId().toString();
							autoRemember.save(userID, orgUnitID, "SpecialScheduleProject", curProjectID + "!" + projectSpecialId);
						} else {
							autoRemember.save(userID, orgUnitID, "SpecialScheduleProject", curProjectID);
						}

						// if (editData != null && editData.getProjectSpecial()
						// != null) {
						// autoRemember.save(userID, orgUnitID,
						// "SpecialSheduleProjectSpecial",
						// editData.getProjectSpecial().getId().toString());
						// }
					}
				}
			} catch (BOSException e) {
				handUIException(e);
			}
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

	/**
	 * @description ????
	 * @author ??????
	 * @createDate 2011-8-26
	 * @version EAS7.0
	 * @see
	 */

	public void actionAdjust_actionPerformed(ActionEvent e) throws Exception {
		// if(editData.getVersion()<getMaxVersion(editData)){
		// FDCMsgBox.showError("??????????????????????????");
		// abort();
		// }
		if (editData == null) {
			FDCMsgBox.showError("??????????????");
			abort();
		}
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
		IUIWindow uiWindow = null;
		Map uiContext = getUIContext();
		uiContext.put("editUI", this);
		uiContext.put("editData", editData);
		uiWindow = uiFactory.create(SavePlanVersionUI.class.getName(), uiContext, null, oprtState);
		uiWindow.show();
		if (((SavePlanVersionUI) uiWindow.getUIObject()).isSave) {
			this.txtVersion.setText(editData.getVersionName());
			cbState.setSelectedItem(editData.getState());
			oprtState = STATUS_EDIT;
			loadData2Gantt(editData);
			this.editData.setId(BOSUuid.read(runSave().toString()));
			
			/* modified by zhaoqin for BT829954 on 2014/06/09 */
			this.editData.setIsCheckVersion(false);
		}
	}

	/**
	 * @description ????
	 * @author ??????
	 * @createDate 2011-8-26
	 * @version EAS7.0
	 * @see ??????????????????????????1.0????????????????????????????????????????????????????????????????????
	 */
	public void actionRestore_actionPerformed(ActionEvent e) throws Exception {
		int showConfirm2 = FDCMsgBox.showConfirm2(this, "????????????????????????????????????????????????????????");
		if (showConfirm2 == 0 && editData != null && editData.getId() != null) {
			FDCScheduleInfo otherInfo = null;
			if (editData.getVersion() == 1) {
				// int cfm = FDCMsgBox.showConfirm2(this,
				// "??????????????????????????????????????????????");
				// if (cfm == FDCMsgBox.OK) {
				if (prmtCurproject.getValue() == null) {
					FDCMsgBox.showWarning(this, "????????????!");
					return;
				}
				getUIContext().put("CurProject", prmtCurproject.getValue());
				editData.getTaskEntrys().clear();
				// ganttProject.close();
				// }
				// return;
				// FDCMsgBox.showWarning(this, "????????????????????????????????????????");
				// return;
			} else {
				String scheduleid = null;
				CurProjectInfo curProject = editData.getProject();
				ProjectSpecialInfo projectSpecial = editData.getProjectSpecial();
				FDCSQLBuilder builder = new FDCSQLBuilder();
				builder.appendSql("select fid from t_sch_fdcschedule where fprojectid = ?  and fislatestver = 1");
				if (projectSpecial != null) {
					builder.appendSql(" and fprojectspecialid is not null");
				} else {
					builder.appendSql(" and fprojectspecialid is null or FProjectSpecialID = ' ' ");
				}
				builder.addParam(editData.getProject().getId().toString());
				IRowSet rs = builder.executeQuery();
				while (rs.next()) {
					scheduleid = rs.getString(1);
				}
				
				
				
// try {
				// // FDCScheduleFactory.getRemoteInstance().delete(
				// // new ObjectStringPK(editData.getId().toString()));
				// // ???????????? by duhongming
				// if (editData.getVersion() > 1f) {
				// FDCSQLBuilder builder = new FDCSQLBuilder();
				// builder.appendSql(
				// "update T_SCH_FDCSchedule set FIsLatestVer=1 where ");
				// if (editData.getProjectSpecial() == null) {
				// builder.appendSql(
				// "(FProjectSpecialID is null or FProjectSpecialID = '') and "
				// );
				// } else {
				// builder.appendParam("FProjectSpecialID",
				// editData.getProjectSpecial().getId().toString());
				// builder.appendSql(" and ");
				// }
				// builder.appendSql("  FProjectID=? and fversion=?");
				// builder.addParam(curProject.getId().toString());
				// builder.addParam(new Integer(new
				// Float(editData.getVersion()).intValue() - 1));
				// builder.executeUpdate();
				// }
				// } catch (Exception e1) {
				// FDCMsgBox.showWarning(this, "????????????????????????");
				// SysUtil.abort();
				// }
				// 3. ????getScheduleInfo??????????????????isAdjust??????true??,??????????
				FDCScheduleInfo info = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(new ObjectUuidPK(BOSUuid.read(scheduleid)));
				// info.setId(editData.getId());
				// BOSObjectType type = new FDCScheduleTaskInfo().getBOSType();
				// // for (int i = 0; i < info.getTaskEntrys().size(); i++) {
				// // info.getTaskEntrys().get(i).setId(BOSUuid.create(type));
				// // }
				// info.setVersion(editData.getVersion());
				// info.setVersionName(editData.getVersionName());
				// info.setState(editData.getState());
				// editData = info;
				
				
				FDCScheduleTaskCollection tasks = (FDCScheduleTaskCollection) info.getTaskEntrys().clone();
				
				Map temp = new HashMap();
				ScheduleTaskBizTypeInfo bizType = null;
				for (int i = 0; i < tasks.size(); i++) {
					FDCScheduleTaskInfo scheduleTaskBaseInfo = (FDCScheduleTaskInfo) tasks.get(i);
					scheduleTaskBaseInfo.setSchedule(editData);
					String oldID = scheduleTaskBaseInfo.getSrcID();
					BOSUuid newID = BOSUuid.create(scheduleTaskBaseInfo.getBOSType());
					scheduleTaskBaseInfo.setId(newID);
					temp.put(oldID, scheduleTaskBaseInfo);
					// temp.put(newID.toString(), oldID);
					scheduleTaskBaseInfo.put("myOldStartDate", scheduleTaskBaseInfo.getStart());
					scheduleTaskBaseInfo.put("myOldEndDate", scheduleTaskBaseInfo.getEnd());
//					scheduleTaskBaseInfo.put("myOldCheckDate", ((FDCScheduleTaskInfo) scheduleTaskBaseInfo).getCheckDate());
//					scheduleTaskBaseInfo.put("myOldCheckDate", ((FDCScheduleTaskInfo) scheduleTaskBaseInfo).getCheckDate());

					if(((FDCScheduleTaskInfo) scheduleTaskBaseInfo).isIsCheckNode()){						
						scheduleTaskBaseInfo.put("myOldCheckDate", ((FDCScheduleTaskInfo) scheduleTaskBaseInfo).getCheckDate());
					}else{
						scheduleTaskBaseInfo.put("myOldCheckDate", null);
					}

				}

				for (int i = 0; i < tasks.size(); i++) {
					FDCScheduleTaskInfo task = (FDCScheduleTaskInfo) tasks.get(i);

					ScheduleTaskBizTypeCollection bizTypeCollection = task.getBizType();
					ScheduleTaskBizTypeCollection newBizTypeCollection = new ScheduleTaskBizTypeCollection();
					for (Iterator it = bizTypeCollection.iterator(); it.hasNext();) {
						bizType = (ScheduleTaskBizTypeInfo) it.next();
						bizType.setId(BOSUuid.create(bizType.getBOSType()));
						newBizTypeCollection.add(bizType);
					}

					task.getBizType().clear();
					task.getBizType().addCollection(newBizTypeCollection);

					if (null != task.getParent() && temp.containsKey(task.getParent().getSrcID().toString())) {
						task.setParent((FDCScheduleTaskInfo) temp.get(task.getParent().getSrcID()));
					}

					FDCScheduleTaskDependCollection depends = task.getDependEntrys();
					if (depends == null || depends.size() < 1) {
						continue;
					}

					for (int j = 0; j < depends.size(); j++) {
						depends.get(j).setId(null);
						FDCScheduleTaskDependInfo depend = depends.get(j);
						String taskID = ((FDCScheduleTaskInfo) depend.getTaskBase()).getSrcID();
						String dependTaskId = ((FDCScheduleTaskInfo) depend.getDependTaskBase()).getSrcID();
						if (temp.containsKey(taskID)) {
							depend.put("task", temp.get(taskID));
							depend.put("dependTask", temp.get(dependTaskId));
						}
					}
				}
				
				
				
				editData.getTaskEntrys().clear();
				editData.getTaskEntrys().addCollection(tasks);
			}
			setDataObject(editData);
			loadData2Gantt(editData);
		}
	}
	
	


	/**
	 * @description ????????????
	 * @author ??????
	 * @createDate 2011-8-29
	 * @version EAS7.0
	 * @see
	 */
	public boolean isAdjustSchedule() {
		return false;
	}

	/**
	 * @description ??????????F7????????????????????
	 * @author ??????
	 * @createDate 2011-9-3
	 * @version EAS7.0
	 * @see
	 */

	protected void lockContainer(Container container) {
		if (container != null && container.getName() != null) {
			if (container.getName().equals("contCurProject") || container.getName().equals("contSpecialProject")) {
				logger.info(container.getName());
			}
		} else {
			super.lockContainer(container);
		}
	}

	private void beforeCheck() {
		if (editData == null || editData.getProject() == null) {
			FDCMsgBox.showError("????????????????");
			abort();
		}
	}

	/**
	 * @description ????????
	 * @author ??????
	 * @createDate 2011-9-15
	 * @version EAS7.0
	 * @see
	 */

	public void actionCompareVer_actionPerformed(ActionEvent e) throws Exception {
		beforeCheck();
		if (editData.getVersion() == 1.0) {
			FDCMsgBox.showError("????????????????????????????????????!");
			abort();
		}
		IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
		IUIWindow uiWindow = null;
		Map uiContext = getUIContext();
		uiContext.put("editUI", this);
		uiContext.put("editData", editData);
		uiWindow = uiFactory.create(RESchCompareUI.class.getName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
	}

	/**
	 * ????????????????????????UI??????????????????????
	 * 
	 * @author ??????
	 * @createDate 2011-11-9
	 * @version EAS7.0
	 * @see
	 */
	public void actionLocate_actionPerformed(ActionEvent e) throws Exception {
		String destBillEditUIClassName = FindTaskUI.class.getName();
		Map ctx = new UIContext(this);
		IUIWindow uiWindow = null;
		// UIFactoryName.MODEL ??????????
		uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(destBillEditUIClassName, ctx, null, OprtState.VIEW);
		// ????????UI
		uiWindow.show();
	}

	protected void refresh() throws Exception {
		// ////////////////////////////////////////////////////////////////////////
		// ???????????? by skyiter_wang 2014-06-11
		Map refreshMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "refresh");
		
		DisplayedColumnsList disCols = getScheduleGanttProject().getGanttTreeTable().getDisplayColumns();
		Map param = new HashMap();
		param.put("isFormExecuteUI", Boolean.valueOf(isFromExecuteUI()));
		ProjectSpecialInfo projectSpecial = null;
		if (this instanceof SpecialScheduleEditUI) {
			projectSpecial = (ProjectSpecialInfo) ((SpecialScheduleEditUI) this).prmtPrjSpecial.getValue();
		}
		editData = FDCScheduleFactory.getRemoteInstance().getScheduleInfo(isMainSchedule(), false, editData.getProject(), projectSpecial, param);
		load2Gantt(editData);
		getScheduleGanttProject().getGanttTreeTable().setDisplayedColumns(disCols);
		ganttProject.getGanttTreeTable().expandAll();
		if (getCurrentSelectLocation() != null && getCurrentSelectLocation().length > 0) {
			getScheduleGanttProject().getTree().selectTreeRow(getCurrentSelectLocation()[0]);
		}
		
		// ////////////////////////////////////////////////////////////////////////
		// ???????????? by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "refresh", refreshMap);
		// ////////////////////////////////////////////////////////////////////////
	}

	protected void refresh(FDCScheduleInfo editData) throws Exception {
		// ////////////////////////////////////////////////////////////////////////
		// ???????????? by skyiter_wang 2014-06-11
		Map refreshMap = FdcManagementUtil.recodeExeTimeBefore(this.getClass(), "refresh");
		// ////////////////////////////////////////////////////////////////////////
		
		DisplayedColumnsList disCols = getScheduleGanttProject().getGanttTreeTable().getDisplayColumns();
		load2Gantt(editData);
		getScheduleGanttProject().getGanttTreeTable().setDisplayedColumns(disCols);
		ganttProject.getGanttTreeTable().expandAll();
		
		// ////////////////////////////////////////////////////////////////////////
		// ???????????? by skyiter_wang 2014-06-11
		FdcManagementUtil.recodeExeTimeAfter(this.getClass(), "refresh", refreshMap);
		// ////////////////////////////////////////////////////////////////////////
	}

	public ScheduleTaskPropertyCollection getColumnsProperty() {
		return columnsProperty;
	}

	public void actionBatchModifyTaskType_actionPerformed(ActionEvent e) throws Exception {

		if (prmtCurproject.getValue() == null) {
			FDCMsgBox.showWarning("??????????????");
			SysUtil.abort();
		}
		if (editData == null || editData.getTaskEntrys() == null || editData.getTaskEntrys().size() <= 0) {
			FDCMsgBox.showWarning("????????????????");
			SysUtil.abort();
		}
		this.scheduleInfo = editData;
		UIContext uiContext = new UIContext(this);
		uiContext.put("schdule", editData);
		UIFactory.createUIFactory(UIFactoryName.MODEL).create(BatchModifyTaskType.class.getName(), uiContext).show();
	}
	
	public void actionCopy_actionPerformed(ActionEvent e) throws Exception {
		UIContext uiContext = new UIContext();
		uiContext.put("value", editData);
		uiContext.put("editUI", this);
		UIFactory.createUIFactory().create(CopyScheduleUI.class.getName(), uiContext).show();
	}

	
	// ????????????????
	protected Set authorizedOrgs = null;

	/**
	 * ??????????????????????????????
	 * @Author??zhaoqin
	 * @CreateTime??2014-6-10
	 */
	private void getAuthorizedCostOrgs() {
		authorizedOrgs = (Set) ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if (authorizedOrgs == null) {
			authorizedOrgs = new HashSet();
			try {
				Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
						new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()), OrgType.CostCenter, null, null, null);
				if (orgs != null) {
					authorizedOrgs = orgs.keySet();
				}
			} catch (EASBizException e) {
				e.printStackTrace();
			} catch (BOSException e) {
				e.printStackTrace();
			}
		}
	}
}