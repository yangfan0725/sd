/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
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
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.basedata.org.OrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.PurchaseDocumentFactory;
import com.kingdee.eas.fdc.contract.PurchaseDocumentInfo;
import com.kingdee.eas.fdc.contract.PurchaseDocumentFactory;
import com.kingdee.eas.fdc.invite.InviteTypeCollection;
import com.kingdee.eas.fdc.invite.InviteTypeFactory;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class PurchaseDocumentListUI extends AbstractPurchaseDocumentListUI
{
    private static final Logger logger = CoreUIObject.getLogger(PurchaseDocumentListUI.class);
    
    /**
     * output class constructor
     */
    public PurchaseDocumentListUI() throws Exception
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
		super.onLoad();
	
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		buildProjectTree();
		buildContractTypeTree();
		
		treeProject.setShowsRootHandles(true);	
		
		treeProject.setSelectionRow(0);
		treeProject.expandRow(0);
		
		treeInviteType.setSelectionRow(0);
		treeInviteType.expandRow(0);
		
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
		
		this.actionTraceDown.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionCreateTo.setVisible(false);
	}
    protected void treeInviteType_valueChanged(TreeSelectionEvent e)
		throws Exception {
	// TODO Auto-generated method stub
		super.treeInviteType_valueChanged(e);
		treeSelectChange();
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
		checkSelected();
		String id = getSelectedKeyValue();
    	checkBeforeEditOrRemove(CANTEDIT,id);
    	
		super.actionEdit_actionPerformed(e);
	}
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
	    	checkBeforeEditOrRemove(CANTREMOVE,id.get(i).toString());
		}
		super.actionRemove_actionPerformed(e);
	}
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
	    	
			if (!FDCBillStateEnum.SUBMITTED.equals(PurchaseDocumentFactory.getRemoteInstance().getPurchaseDocumentInfo(new ObjectUuidPK(id.get(i).toString())).getState())) {
				FDCMsgBox.showWarning("单据不是提交状态，不能进行审批操作！");
				return;
			}
			((IFDCBill)getBizInterface()).audit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		ArrayList id = getSelectedIdValues();
		for(int i = 0; i < id.size(); i++){
			FDCClientUtils.checkBillInWorkflow(this, id.get(i).toString());
			PurchaseDocumentInfo info=PurchaseDocumentFactory.getRemoteInstance().getPurchaseDocumentInfo(new ObjectUuidPK(id.get(i).toString()));
			if (!FDCBillStateEnum.AUDITTED.equals(info.getState())) {
				FDCMsgBox.showWarning("单据不是审批状态，不能进行反审批操作！");
				return;
			}
			((IFDCBill)getBizInterface()).unAudit(BOSUuid.read(id.get(i).toString()));
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeInviteType
				.getLastSelectedPathComponent();
	}
	protected void treeSelectChange() throws Exception {

//		DefaultKingdeeTreeNode projectNode  = getProjSelectedTreeNode();
//		DefaultKingdeeTreeNode  typeNode  =	getTypeSelectedTreeNode() ;
//		
//		Object project  = null;
//		if(projectNode!=null){
//			project = projectNode.getUserObject();
//		}
//		Object type  = null;
//		if(typeNode!=null){
//			type = typeNode.getUserObject();
//		}
//		mainQuery.setFilter(getTreeSelectFilter(project,type));

		execQuery();
		
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
			typefilterItems.add(new FilterItemInfo("inviteType.id", idSet,CompareType.INCLUDE));
		}else if(typeNode != null &&typeNode.equals("allContract")){
			//如果包含无文本合同，查询所有时，让它查不到合同
			typefilterItems.add(new FilterItemInfo("inviteType.id", "allContract"));
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
		Object contractType = ((DefaultKingdeeTreeNode) treeInviteType.getLastSelectedPathComponent()).getUserObject();
		if(contractType instanceof ContractTypeInfo){
			BOSUuid contractTypeId = ((ContractTypeInfo) contractType).getId();
			uiContext.put("contractTypeId", contractTypeId);
		}else{
			uiContext.put("contractTypeId", null);
		}
	}
	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeProject
				.getLastSelectedPathComponent();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return PurchaseDocumentFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return PurchaseDocumentEditUI.class.getName();
	}
	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}
	@Override
	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		Object userObject = getProjSelectedTreeNode().getUserObject();
		if(userObject==null||!(userObject instanceof CurProjectInfo)||!getProjSelectedTreeNode().isLeaf()){
			FDCMsgBox.showWarning(this, "请选择明细工程项目！");
			SysUtil.abort();
		}
		Object userObject2 = getTypeSelectedTreeNode().getUserObject();
		if(userObject2==null||!(userObject2 instanceof ContractTypeInfo)||!getTypeSelectedTreeNode().isLeaf()){
			FDCMsgBox.showWarning(this, "请选择明细采购类别！");
			SysUtil.abort();
		}
		super.actionAddNew_actionPerformed(e);
	}
	private TreeSelectionListener treeSelectionListener;
	private ITreeBuilder treeBuilder;
	protected void buildContractTypeTree() throws Exception {
		KDTree treeMain = this.treeInviteType;
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
				new FilterItemInfo("isPurchase", Boolean.TRUE));
		return filter;
	}
}