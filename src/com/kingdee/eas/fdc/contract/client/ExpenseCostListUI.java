/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.data.SortType;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.workflow.ProcessInstInfo;
import com.kingdee.bos.workflow.monitor.client.BasicWorkFlowMonitorPanel;
import com.kingdee.bos.workflow.monitor.client.ProcessRunningListUI;
import com.kingdee.bos.workflow.service.ormrpc.EnactmentServiceFactory;
import com.kingdee.bos.workflow.service.ormrpc.IEnactmentService;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataFillListener;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ExpenseApplyInfo;
import com.kingdee.eas.fdc.contract.ExpenseCostCollection;
import com.kingdee.eas.fdc.contract.ExpenseCostFactory;
import com.kingdee.eas.fdc.contract.ExpenseCostInfo;
import com.kingdee.eas.fdc.contract.TripCostInfo;
import com.kingdee.eas.fdc.merch.common.KDTableHelper;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.client.CasPaymentBillUI;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.framework.report.util.KDTableUtil;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;

/**
 * output class name
 */
public class ExpenseCostListUI extends AbstractExpenseCostListUI
{
    private static final Logger logger = CoreUIObject.getLogger(ExpenseCostListUI.class);
    
    /**
     * output class constructor
     */
    public ExpenseCostListUI() throws Exception
    {
        super();
    }

    protected OrgUnitInfo currentOrg = SysContext.getSysContext().getCurrentCostUnit();
    protected static final String CANTEDIT = "cantEdit";
	protected static final String CANTREMOVE = "cantRemove";
	protected Set authorizedOrgs = null;
   
    public void buildProjectTree() throws Exception {

		ProjectTreeBuilder projectTreeBuilder = new ProjectTreeBuilder();

		projectTreeBuilder.build(this, treeProject, actionOnLoad);
		
		authorizedOrgs = (Set)ActionCache.get("FDCBillListUIHandler.authorizedOrgs");
		if(authorizedOrgs==null){
			authorizedOrgs = new HashSet();
			Map orgs = PermissionFactory.getRemoteInstance().getAuthorizedOrgs(
					 new ObjectUuidPK(SysContext.getSysContext().getCurrentUserInfo().getId()),
			            OrgType.CostCenter, 
			            null,  null, null);
			if(orgs!=null){
				Set orgSet = orgs.keySet();
				Iterator it = orgSet.iterator();
				while(it.hasNext()){
					authorizedOrgs.add(it.next());
				}
			}		
		}
	}
    public void onLoad() throws Exception {
    	this.tblMain.getDataRequestManager().addDataFillListener(new KDTDataFillListener(){
			public void afterDataFill(KDTDataRequestEvent e) {
				String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
	 	        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
				boolean hasFunctionPermission=false;
				try {
					hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
							new ObjectUuidPK(userId),
							new ObjectUuidPK(orgId),
							new MetaDataPK(ExpenseApplyListUI.class.getName()),
							new MetaDataPK("ActionIsHide") );
				} catch (EASBizException e1) {
					e1.printStackTrace();
				} catch (BOSException e1) {
					e1.printStackTrace();
				}
	        	if(!hasFunctionPermission){
	        		for (int i = e.getFirstRow(); i <= e.getLastRow(); i++) {
						IRow row = tblMain.getRow(i);
						Boolean isHide=(Boolean) row.getCell("isHide").getValue();
						Boolean isPay=(Boolean) row.getCell("isPay").getValue();
						Date payBizDate= (Date) row.getCell("payBizDate").getValue();
						Integer hideDay=(Integer) row.getCell("hideDay").getValue();
						if(isHide&&isPay&&FDCDateHelper.getDiffDays(payBizDate,new Date())>hideDay){
							row.getStyleAttributes().setHided(true);
						}
					}
	        	}
			}
		});
		super.onLoad();
	
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		buildProjectTree();
		buildContractTypeTree();
		
		treeProject.setShowsRootHandles(true);	
		
		treeProject.setSelectionRow(0);
		treeProject.expandRow(0);
		
		treeContractType.setSelectionRow(0);
		treeContractType.expandRow(0);
		
		TreeSelectionListener projTreeSelectionListener = null; 
		TreeSelectionListener[] listeners = treeProject
		.getTreeSelectionListeners();
		if (listeners.length > 0) {
			projTreeSelectionListener = listeners[0];
			treeProject.removeTreeSelectionListener(projTreeSelectionListener);
		}
		treeProject.addTreeSelectionListener(projTreeSelectionListener);
		treeSelectChange();
		
		this.tblMain.setColumnMoveable(true);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblMain.setAutoscrolls(true);
		
		this.tblExpenseCost.getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		this.tblExpenseCost.setEnabled(false);
		
    	this.actionTraceUp.setVisible(false);
    	this.actionCreateTo.setVisible(false);
    	
    	this.tblExpenseCost.getColumn("amount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	this.tblExpenseCost.getColumn("amount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	public boolean isIgnoreRowCount() {
		return false;
	}
	protected void checkBeforeEditOrRemove(String warning,String id) throws EASBizException, BOSException, Exception {
    	//检查是否在工作流中
		FDCClientUtils.checkBillInWorkflow(this, id);
		
		SelectorItemCollection sels = super.getSelectors();
		sels.add("state");
		
		FDCBillInfo info=(FDCBillInfo)getBizInterface().getValue(new ObjectUuidPK(id),sels);
		
		FDCBillStateEnum state = info.getState();
		
		if (state != null&& (state == FDCBillStateEnum.AUDITTING || state == FDCBillStateEnum.AUDITTED)) {
			if(warning.equals(CANTEDIT)){
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
				SysUtil.abort();
			}else{
				FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
				SysUtil.abort();
			}
		}
	}
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblExpenseCost);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int rowIndex = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
		IRow row = this.tblExpenseCost.getRow(rowIndex);
		ExpenseCostInfo info=(ExpenseCostInfo) row.getUserObject();
		
		FDCClientUtils.checkBillInWorkflow(this, info.getId().toString());
		
		FDCBillStateEnum bizState = info.getState();
		
		if (!FDCBillStateEnum.SUBMITTED.equals(bizState)&&!FDCBillStateEnum.SAVED.equals(bizState)) {
			FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行修改操作！");
			SysUtil.abort();
		}
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("ID", info.getId().toString());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ExpenseCostEditUI.class.getName(), uiContext, null, OprtState.EDIT);
		uiWindow.show();
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblExpenseCost);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		
		int rowIndex = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
		IRow row = this.tblExpenseCost.getRow(rowIndex);
		ExpenseCostInfo info=(ExpenseCostInfo) row.getUserObject();
		
		FDCClientUtils.checkBillInWorkflow(this, info.getId().toString());
		
		FDCBillStateEnum bizState = info.getState();
		
		if (!FDCBillStateEnum.SUBMITTED.equals(bizState)&&!FDCBillStateEnum.SAVED.equals(bizState)) {
			FDCMsgBox.showWarning("单据不是保存或者提交状态，不能进行删除操作！");
			SysUtil.abort();
		}
		if(confirmRemove()){
			ExpenseCostFactory.getRemoteInstance().delete(new ObjectUuidPK(info.getId()));
			FDCClientUtils.showOprtOK(this);
			queryList();
		}
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblExpenseCost);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		
		int rowIndex = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
		IRow row = this.tblExpenseCost.getRow(rowIndex);
		ExpenseCostInfo info=(ExpenseCostInfo) row.getUserObject();
		
		FDCClientUtils.checkBillInWorkflow(this, info.getId().toString());
		
		FDCBillStateEnum bizState = info.getState();
		
		if (!FDCBillStateEnum.SUBMITTED.equals(bizState)) {
			FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
			SysUtil.abort();
		}
		ExpenseCostFactory.getRemoteInstance().audit(info.getId());
		FDCClientUtils.showOprtOK(this);
		queryList();
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblExpenseCost);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		
		int rowIndex = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
		IRow row = this.tblExpenseCost.getRow(rowIndex);
		ExpenseCostInfo info=(ExpenseCostInfo) row.getUserObject();
		
		FDCClientUtils.checkBillInWorkflow(this, info.getId().toString());
		
		FDCBillStateEnum bizState = info.getState();
		
		if (!FDCBillStateEnum.AUDITTED.equals(bizState)) {
			FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
			SysUtil.abort();
		}
		ExpenseCostFactory.getRemoteInstance().unAudit(info.getId());
		FDCClientUtils.showOprtOK(this);
		queryList();
	}
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeContractType
				.getLastSelectedPathComponent();
	}
	protected void treeSelectChange() throws Exception {

//		DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
//		
//		Object project  = null;
//		if(projectNode!=null){
//			project = projectNode.getUserObject();
//		}
//		FilterInfo filter=getTreeSelectFilter(project);
//		FilterInfo auditFilter=new FilterInfo();
//		auditFilter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
//		
//		filter.mergeFilter(auditFilter, "and");
//		mainQuery.setFilter(filter);

		execQuery();
		this.tblExpenseCost.removeRows();
		
	}
	protected void treeProject_valueChanged(TreeSelectionEvent e)throws Exception {

		super.treeProject_valueChanged(e);
		treeSelectChange();
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try	{
			DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
			DefaultKingdeeTreeNode  typeNode  =	getTypeSelectedTreeNode() ;
			
			Object project  = null;
			if(projectNode!=null){
				project = projectNode.getUserObject();
			}
			Object type  = null;
			if(typeNode!=null){
				type = typeNode.getUserObject();
			}
			FilterInfo filter =getTreeSelectFilter(project,type);
			
			FilterInfo auditFilter=new FilterInfo();
			auditFilter.getFilterItems().add(new FilterItemInfo("state",FDCBillStateEnum.AUDITTED_VALUE));
			
			filter.mergeFilter(auditFilter, "and");
			
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			if (viewInfo.getFilter() != null)
			{
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else
			{
				viewInfo.setFilter(filter);
			}
			SorterItemCollection sort=new SorterItemCollection();
			SorterItemInfo itme = new SorterItemInfo("createTime");
			itme.setSortType(SortType.DESCEND);
			sort.add(itme);
			viewInfo.setSorter(sort);
		}catch (Exception e)
		{
			handleException(e);
		}
		return super.getQueryExecutor(queryPK, viewInfo);
	}
	protected FilterInfo getTreeSelectFilter(Object projectNode,Object  typeNode) throws Exception {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		
		/*
		 * 工程项目树
		 */
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// 选择的是成本中心，取该成本中心及下级成本中心（如果有）下的所有合同
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo
					||projTreeNodeInfo instanceof CurProjectInfo) {
				
				DefaultKingdeeTreeNode node  = getProjSelectedTreeNode();
				Map sellProMap = getAllObjectIdMap(node,"CurProject");
				Iterator iter = sellProMap.keySet().iterator();
				Set sellProIdSet = new HashSet();
				while (iter.hasNext())
					sellProIdSet.add(iter.next());
				if (sellProIdSet.size() > 0) {
					filter.getFilterItems().add(
							new FilterItemInfo("curProject.id", sellProIdSet,
									CompareType.INCLUDE));
				} else {
					filter.getFilterItems().add(
							new FilterItemInfo("curProject.id", null));
				}
			}
		}
		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		/*
		 * 合同类型树
		 */
		if (typeNode != null&& typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet,CompareType.INCLUDE));
		}else if(typeNode != null &&typeNode.equals("allContract")){
			//如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}
		if(filter!=null && typefilter!=null){
			filter.mergeFilter(typefilter,"and");
		}
		return filter;
	}
	public Map getAllObjectIdMap(TreeNode treeNode, String treeType) {
		Map idMap = new HashMap();
		if (treeNode != null) {
			fillTreeNodeIdMap(idMap, treeNode, treeType);
		}
		return idMap;
	}
	private void fillTreeNodeIdMap(Map idMap, TreeNode treeNode,
			String treeType) {
		DefaultKingdeeTreeNode thisNode = (DefaultKingdeeTreeNode) treeNode;
		if (treeType.equals("CurProject")) { // 存储的是组织单元id
			if (thisNode.getUserObject() instanceof CurProjectInfo) {
				CurProjectInfo objectInfo = (CurProjectInfo) thisNode
						.getUserObject();
				idMap.put(objectInfo.getId().toString(), thisNode);
			}
		} 
		int childCount = treeNode.getChildCount();

		while (childCount > 0) {
			fillTreeNodeIdMap(idMap, treeNode.getChildAt(childCount - 1),
					treeType);
			childCount--;
		}

	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		Object userObject2 = getProjSelectedTreeNode().getUserObject();
		if(userObject2 instanceof CurProjectInfo){
			BOSUuid projId = ((CurProjectInfo) userObject2).getId();
			uiContext.put("projectId", projId);
		}
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex>=0){
			String id  = this.tblMain.getRow(rowIndex).getCell("id").getValue().toString();
			uiContext.put("expenseId", id);
		}
	}
	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeProject
				.getLastSelectedPathComponent();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return ExpenseCostFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return ExpenseCostEditUI.class.getName();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex>=0){
			String id  = this.tblMain.getRow(rowIndex).getCell("id").getValue().toString();
			if(ExpenseCostFactory.getRemoteInstance().exists("select * from where expenseApply.id='"+id+"'")){
				MsgBox.showWarning("一张申请只能一次报销！");
				SysUtil.abort();
			}
		}
		super.actionAddNew_actionPerformed(e);
	}
	protected void tblExpenseCost_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
			int rowIndex = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
			IRow row = this.tblExpenseCost.getRow(rowIndex);
			ExpenseCostInfo info=(ExpenseCostInfo) row.getUserObject();
			
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			uiContext.put("ID", info.getId().toString());
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ExpenseCostEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	private void queryList() throws BOSException{
		this.tblExpenseCost.removeRows();
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if(rowIndex<0)return;
		String id  = this.tblMain.getRow(rowIndex).getCell("id").getValue().toString();
		
		filter.getFilterItems().add(new FilterItemInfo("expenseApply.id",id));
		view.setFilter(filter);
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("creator.name");
		sic.add("auditor.name");
		sic.add("contractType.name");
		sic.add("dept.name");
		sic.add("person.name");
		sic.add("supplier.name");
		sic.add("contractWFType.name");
		view.setSelector(sic);
		ExpenseCostCollection col=ExpenseCostFactory.getRemoteInstance().getExpenseCostCollection(view);
		for(int i=0;i<col.size();i++){
			IRow row=this.tblExpenseCost.addRow();
			row.setUserObject(col.get(i));
			row.getCell("id").setValue(col.get(i).getId().toString());
			row.getCell("number").setValue(col.get(i).getNumber());
			row.getCell("state").setValue(col.get(i).getState().getAlias());
			row.getCell("bizDate").setValue(col.get(i).getBizDate());
			row.getCell("amount").setValue(col.get(i).getAmount());
			row.getCell("expenseType").setValue(col.get(i).getExpenseType().getAlias());
			row.getCell("contractType.name").setValue(col.get(i).getContractType().getName());
			if(col.get(i).getContractWFType()!=null)
				row.getCell("contractWFType.name").setValue(col.get(i).getContractWFType().getName());
			
			row.getCell("purpose").setValue(col.get(i).getPurpose());
		
			row.getCell("creator.name").setValue(col.get(i).getCreator().getName());
			if(col.get(i).getAuditor()!=null)
				row.getCell("auditor.name").setValue(col.get(i).getAuditor().getName());
			row.getCell("createTime").setValue(col.get(i).getCreateTime());
			row.getCell("auditTime").setValue(col.get(i).getAuditTime());
			PaymentBillCollection payCol=PaymentBillFactory.getRemoteInstance().getPaymentBillCollection("select billStatus from where sourceBillId='"+col.get(i).getId().toString()+"'");
			boolean isPay=false;
			for(int j=0;j<payCol.size();j++){
				if(payCol.get(j).getBillStatus().equals(BillStatusEnum.PAYED)){
					isPay=true;
				}
			}
			row.getCell("isPay").setValue(isPay);
			
			row.getCell("person.name").setValue(col.get(i).getPerson().getName());
			row.getCell("dept.name").setValue(col.get(i).getDept().getName());
			if(col.get(i).getSupplier()!=null)
				row.getCell("supplier.name").setValue(col.get(i).getSupplier().getName());
//			if(col.get(i).getBankNum()!=null)
//				row.getCell("bankNum.name").setValue(col.get(i).getBankNum().getName());
			row.getCell("accountNumber").setValue(col.get(i).getAccountNumber());
			row.getCell("receiveBank").setValue(col.get(i).getReceiveBank());
			
			row.getCell("name").setValue(col.get(i).getName());
		}
	}
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.tblMain_tableSelectChanged(e);
		queryList();
	}
	public void actionAuditResult_actionPerformed(ActionEvent e)throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblExpenseCost);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		
		int rowIndex = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
		IRow row = this.tblExpenseCost.getRow(rowIndex);
		ExpenseCostInfo info=(ExpenseCostInfo) row.getUserObject();
		String id=info.getId().toString();
		MultiApproveUtil.showApproveHis(BOSUuid.read(id), UIModelDialogFactory.class.getName(), this);
	}
	public void actionWorkFlowG_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblExpenseCost);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		
		int rowIndex = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
		IRow row = this.tblExpenseCost.getRow(rowIndex);
		ExpenseCostInfo info=(ExpenseCostInfo) row.getUserObject();
		String id=info.getId().toString();
		IEnactmentService service = EnactmentServiceFactory.createRemoteEnactService();
		ProcessInstInfo processInstInfo = null;
		ProcessInstInfo procInsts[] = service.getProcessInstanceByHoldedObjectId(id);
		int i = 0;
		for(int n = procInsts.length; i < n; i++)
			if(procInsts[i].getState().startsWith("open"))
				processInstInfo = procInsts[i];
		if(processInstInfo == null){
			procInsts = service.getAllProcessInstancesByBizobjId(id);
			if(procInsts == null || procInsts.length <= 0)
				MsgBox.showInfo(this, EASResource.getString("com.kingdee.eas.framework.FrameWorkResource.Msg_WFHasNotInstance"));
			else
				if(procInsts.length == 1){
					showWorkflowDiagram(procInsts[0]);
				} else{
					UIContext uiContext = new UIContext(this);
					uiContext.put("procInsts", procInsts);
					String className = ProcessRunningListUI.class.getName();
					IUIWindow uiWindow = UIFactory.createUIFactory("com.kingdee.eas.base.uiframe.client.UIModelDialogFactory").create(className, uiContext);
					uiWindow.show();
				}
		} else{
			showWorkflowDiagram(processInstInfo);
		}
	}
	private void showWorkflowDiagram(ProcessInstInfo processInstInfo)throws Exception{
		UIContext uiContext = new UIContext(this);
		uiContext.put("id", processInstInfo.getProcInstId());
		uiContext.put("processInstInfo", processInstInfo);
		BasicWorkFlowMonitorPanel.Show(uiContext);
	}
	public void actionTraceDown_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblExpenseCost);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int rowIndex = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
		IRow row = this.tblExpenseCost.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
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
	public void actionAttachment_actionPerformed(ActionEvent e)throws Exception {
		boolean isEdit = false;
		AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblExpenseCost);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int rowIndex = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
		IRow row = this.tblExpenseCost.getRow(rowIndex);
		String boID = (String) row.getCell(this.getKeyFieldName()).getValue();
		if(boID == null)return;
		if(getBillStatePropertyName() != null){
			int rowIdx = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
			ICell cell = this.tblExpenseCost.getCell(rowIdx, getBillStatePropertyName());
			Object obj = cell.getValue();
			if(obj != null && (obj.toString().equals(FDCBillStateEnum.SAVED.toString()) || obj.toString().equals(FDCBillStateEnum.SUBMITTED.toString()) || obj.toString().equals(FDCBillStateEnum.AUDITTING.toString()) || obj.toString().equals(BillStatusEnum.SAVE.toString()) || obj.toString().equals(BillStatusEnum.SUBMIT.toString()) || obj.toString().equals(BillStatusEnum.AUDITING.toString())))
				isEdit = true;
			else
				isEdit = false;
		}
		acm.showAttachmentListUIByBoID(boID, this, isEdit);
	}
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
		if (e.getType() == KDTStyleConstants.BODY_ROW && e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2){
			int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
			IRow row = this.tblMain.getRow(rowIndex);
			String id=row.getCell("id").getValue().toString();
			
			UIContext uiContext = new UIContext(this);
			uiContext.put(UIContext.OWNER, this);
			uiContext.put("ID", id);
			IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ExpenseApplyEditUI.class.getName(), uiContext, null, OprtState.VIEW);
			uiWindow.show();
		}
	}
	public void actionView_actionPerformed(ActionEvent e) throws Exception {
		int[] selectRows = KDTableUtil.getSelectedRows(this.tblExpenseCost);
		if(selectRows==null || selectRows.length==0){
			FDCMsgBox.showWarning(this, EASResource.getString(FrameWorkClientUtils.strResource + "Msg_MustSelected"));
			return;
		}
		int rowIndex = this.tblExpenseCost.getSelectManager().getActiveRowIndex();
		IRow row = this.tblExpenseCost.getRow(rowIndex);
		ExpenseCostInfo info=(ExpenseCostInfo) row.getUserObject();
		
		UIContext uiContext = new UIContext(this);
		uiContext.put(UIContext.OWNER, this);
		uiContext.put("ID", info.getId().toString());
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ExpenseCostEditUI.class.getName(), uiContext, null, OprtState.VIEW);
		uiWindow.show();
	}
	private TreeSelectionListener treeSelectionListener;
	private ITreeBuilder treeBuilder;
	protected void buildContractTypeTree() throws Exception {
		KDTree treeMain = this.treeContractType;
		TreeSelectionListener[] listeners = treeMain
				.getTreeSelectionListeners();
		if (listeners.length > 0) {
			treeSelectionListener = listeners[0];
			treeMain.removeTreeSelectionListener(treeSelectionListener);
		}
		treeBuilder = TreeBuilderFactory.createTreeBuilder(getLNTreeNodeCtrl(),
				getTreeInitialLevel(), getTreeExpandLevel(), this
						.getDefaultFilterForTree());

		if (getRootName() != null) {
			KDTreeNode rootNode = new KDTreeNode(getRootObject());
			((DefaultTreeModel) treeMain.getModel()).setRoot(rootNode);
			
		} else {
			((DefaultTreeModel) treeMain.getModel()).setRoot(null);
		}
		
		treeBuilder.buildTree(treeMain);
		
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);
	}
	protected Object getRootObject() {
		return getRootName();
	}
	protected ILNTreeNodeCtrl getLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getTreeInterface());
	}
	protected String getRootName() {
		return ContractClientUtils.getRes("allContractType");
	}
	private ITreeBase getTreeInterface() {

		ITreeBase treeBase = null;
		try {
			treeBase = ContractTypeFactory.getRemoteInstance();
		} catch (BOSException e) {
			abort(e);
		}

		return treeBase;
	}
	protected int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	protected int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		filter.getFilterItems().add(
				new FilterItemInfo("isExpense", Boolean.TRUE));
		return filter;
	}
	protected void treeContractType_valueChanged(TreeSelectionEvent e)
			throws Exception {
		// TODO Auto-generated method stub
		super.treeContractType_valueChanged(e);
		treeSelectChange();
	}
}