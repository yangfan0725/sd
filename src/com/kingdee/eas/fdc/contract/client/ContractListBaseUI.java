/**
 * output package name
 */
package com.kingdee.eas.fdc.contract.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.KDTreeView;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.ActionCache;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemCollection;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.multiapprove.client.MultiApproveUtil;
import com.kingdee.eas.base.permission.PermissionFactory;
import com.kingdee.eas.base.uiframe.client.UIModelDialogFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitFactory;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.OrgType;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.ContractTypeFactory;
import com.kingdee.eas.fdc.basedata.ContractTypeInfo;
import com.kingdee.eas.fdc.basedata.CurProjectInfo;
import com.kingdee.eas.fdc.basedata.FDCBillStateEnum;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.ProjectTreeBuilder;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.CoreBillBaseCollection;
import com.kingdee.eas.framework.CoreBillBaseInfo;
import com.kingdee.eas.framework.ICoreBillBase;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.client.UIActionPostman;
import com.kingdee.eas.framework.client.FrameWorkClientUtils;
import com.kingdee.eas.framework.client.IIDList;
import com.kingdee.eas.framework.client.RealModeIDList;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ITreeBuilder;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.framework.client.tree.TreeBuilderFactory;
import com.kingdee.eas.framework.config.TablePreferencesHelper;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

/**
 * 
 * ????:????????????????
 * 
 * @author liupd date:2006-8-1
 *         <p>
 * @version EAS5.1.3
 */
public abstract class ContractListBaseUI extends AbstractContractListBaseUI {
	private static final Logger logger = CoreUIObject
			.getLogger(ContractListBaseUI.class);
	
	//????????????????
	protected Set authorizedOrgs = null;

	private CommonQueryDialog commonQueryDialog = null;
	private ContractBillFilterUI filterUI = null;
	/**
	 * output class constructor
	 */
	public ContractListBaseUI() throws Exception {
		super();
	}

	
	/**
	 * output tblMain_tableSelectChanged method
	 */
	protected void tblMain_tableSelectChanged(
			KDTSelectEvent e)
			throws Exception {
		if(!isHasBillTable()) {
			super.tblMain_tableSelectChanged(e);
			return;
		}
		if(e.getSelectBlock()==null) return;
		getBillListTable().removeRows(false);
		
		EntityViewInfo view = genBillQueryView(e);
		if(!displayBillByContract(e, view) ){
			displayBillByContract(view);
		}
		if(getBillListTable()!=null&&getBillListTable().getRowCount()>0){
			getBillListTable().getSelectManager().select(0,0);
		}
		super.tblMain_tableSelectChanged(e);
	}

	/**
	 * ??????????????????????????????????????????onLoad()??????
	 * @author sxhong  		Date 2006-10-20
	 * @param e
	 * @throws Exception
	 */
	protected void tblBill_tableClicked(KDTMouseEvent e) throws Exception
	{
    	/*
    	 * ??????????????????????????tblMain????????????????????
    	 */
		if(e.getType()==KDTStyleConstants.HEAD_ROW) {
			return;
		}
		//TODO ????????tblMain_tableClicked ?????????? by sxhong
		if(tblMain.getColumn(e.getColIndex())==null){
			return;
		}
		super.tblMain_tableClicked(e);
	}

	/**
	 * ??????????????????????????????????????????????????
	 * @author sxhong  		Date 2006-10-19
	 * @param e
	 * @throws Exception
	 * @see com.kingdee.eas.fdc.basedata.client.FDCBillListUI#tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent)
	 */
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception
	{
		if(isHasBillTable()){
			/*
			 * ????????
			 */
			if(e.getType()==KDTStyleConstants.HEAD_ROW){
				super.tblMain_tableClicked(e);
			}else 
			//????????????/??????????
			if(e.getClickCount()==2){
				checkSelected(getMainTable());
				this.setCursorOfWair();
		        UIContext uiContext = new UIContext(this);
		        String contractId = getSelectedKeyValue(getMainTable());
		        if(contractId==null) return;
		        
				uiContext.put(UIContext.ID, contractId);
				uiContext.put("source", "listBase");	//??????????????????????
		        IUIWindow contractUiWindow = null;
		        String editUIName=null;
		        BOSObjectType contractType = BOSUuid.read(contractId).getType();
		        if(contractType.equals(new ContractBillInfo().getBOSType())){
		        	editUIName=com.kingdee.eas.fdc.contract.client.ContractBillEditUI.class.getName();
		        }else if(contractType.equals(new ContractWithoutTextInfo().getBOSType())){
		        	editUIName=com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI.class.getName();
		        }else{
		        	return;
		        }
		        
		        if (SwingUtilities.getWindowAncestor(this) != null
		                && SwingUtilities.getWindowAncestor(this) instanceof JDialog) {
		        	contractUiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(
		                    editUIName, uiContext, null, "FINDVIEW");

		        } else {
		            // ????UI??????????
		        	contractUiWindow = UIFactory.createUIFactory(getEditUIModal()).create(
		            		editUIName, uiContext, null, "FINDVIEW");
		        }
		        if(contractUiWindow!=null) {
//		        	EditUI ui=(EditUI)contractUiWindow.getUIObject();
//		        	ui.
		        	contractUiWindow.show();
		        }
				this.setCursorOfDefault();
			}
		}else{
			super.tblMain_tableClicked(e);
		}
	}

	/**
	 * output actionEdit_actionPerformed
	 */
	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		
		checkBeforeEdit();
		
		super.actionEdit_actionPerformed(e);
	}

	/**
	 * output actionRemove_actionPerformed
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		
		checkBeforeRemove();
		
		super.actionRemove_actionPerformed(e);
	}


	/**
	 * output actionPrint_actionPerformed
	 */
	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		if(isHasBillTable()){
			preparePrintPage(getBillListTable());
	        getBillListTable().getPrintManager().print();
		}else{
			super.actionPrint_actionPerformed(e);
		}
	}

	/**
	 * output actionPrintPreview_actionPerformed
	 */
	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		if(isHasBillTable()){
			preparePrintPage(getBillListTable());
	        getBillListTable().getPrintManager().printPreview();
		}else{
			super.actionPrintPreview_actionPerformed(e);
		}
	}


	public void actionCreateTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionCreateTo_actionPerformed(e);
	}

	public void actionView_actionPerformed(ActionEvent e) throws Exception
	{
		super.actionView_actionPerformed(e);
	}
	
    public void actionQuery_actionPerformed(ActionEvent e) throws Exception
    {
    	if (this.actionQuery.isVisible()) {
			getFilterUI();
			super.actionQuery_actionPerformed(e);
		}
    }
    
	/**
	 * output getEditUIName method
	 */
	protected abstract String getEditUIName();

	/**
	 * output getBizInterface method
	 */
	protected abstract com.kingdee.eas.framework.ICoreBase getBizInterface()
			throws Exception;

	/**
	 * output createNewData method
	 */
	protected com.kingdee.bos.dao.IObjectValue createNewData() {
		com.kingdee.eas.fdc.contract.ContractBillInfo objectValue = new com.kingdee.eas.fdc.contract.ContractBillInfo();
		return objectValue;
	}

	
	/**
	 * private??????????public??????????????????????????????????????
	 * ??????????????????????????????????????????????????????????????????????????????????????????????????
	 * @throws Exception
	 */
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

	private TreeSelectionListener treeSelectionListener;

	private ITreeBuilder treeBuilder;

	/**
	 * ??????????????
	 */
	protected void buildContractTypeTree() throws Exception {
		KDTree treeMain = getContractTypeTree();
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
		if(containConWithoutTxt()){
			DefaultKingdeeTreeNode root = (DefaultKingdeeTreeNode) treeMain.getModel().getRoot();
			KDTreeNode node = new KDTreeNode("allContract");
			node.setUserObject("allContract");
			node.setText(getRootName());
			root.setText("????");
			node.add(root);
			((DefaultTreeModel) treeMain.getModel()).setRoot(node);
			
		}
		
		treeMain.addTreeSelectionListener(treeSelectionListener);
		treeMain.setShowPopMenuDefaultItem(false);

	}
	
	/**
	 * ??????????????????????????
	 * @return
	 */
	protected boolean containConWithoutTxt(){
		return false;
	}
	/**
	 * ??????ILNTreeNodeCtrl???????????????? ??????????????????????????????????????????????????????????????
	 * ??????????????????ILNTreeNodeCtrl??????????????????????????????DefaultLNTreeNodeCtrl)??
	 * ??????????????????????????????????????????????
	 */
	protected ILNTreeNodeCtrl getLNTreeNodeCtrl() throws Exception {
		return new DefaultLNTreeNodeCtrl(getTreeInterface());
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

	private KDTree getContractTypeTree() {
		return treeContractType;
	}

	protected int getTreeInitialLevel() {
		return TreeBuilderFactory.DEFAULT_INITIAL_LEVEL;
	}

	protected int getTreeExpandLevel() {
		return TreeBuilderFactory.DEFAULT_EXPAND_LEVEL;
	}

	// ??????CU??????????
	protected FilterInfo getDefaultFilterForTree() {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("isEnabled", Boolean.TRUE));
		return filter;
	}

	/**
	 * ????????????????????????????null???????????????????????????????????? ????????????????????????????
	 */
	protected String getRootName() {
		return ContractClientUtils.getRes("allContractType");
	}

	protected Object getRootObject() {
		return getRootName();
	}

	
	public void onLoad() throws Exception {
		
		super.onLoad();
		//????????????????
		buildProjectTree();
		//????????????????
		buildContractTypeTree();	
		
		
	   //????KDtreeView
		KDTreeView treeView=new KDTreeView();
		treeView.setTree(treeProject);
		treeView.setShowButton(false);
		treeView.setTitle("????????");
        kDSplitPane1.add(treeView, "left");
		treeView.setShowControlPanel(true);
		
		
		treeProject.setShowsRootHandles(true);
		updateButtonStatus();
		
		// ????????????
		actionQuery.setEnabled(false);
		actionQuery.setVisible(false);
		// ????????????
//		actionMultiapprove.setEnabled(false);
//		actionMultiapprove.setVisible(false);
		// ????????????????
//		actionNextPerson.setEnabled(false);
//		actionNextPerson.setVisible(false);
		
		actionAuditResult.setEnabled(true);
//		actionAuditResult.setVisible(false);

//		treeProject.expandAllNodes(true, (TreeNode) treeProject.getModel().getRoot());
		
		TreeSelectionListener projTreeSelectionListener = null; 
		TreeSelectionListener[] listeners = treeProject
		.getTreeSelectionListeners();
		if (listeners.length > 0) {
			projTreeSelectionListener = listeners[0];
			treeProject.removeTreeSelectionListener(projTreeSelectionListener);
		}
		
		TreeSelectionListener conTypeTreeSelectionListener = null;
			TreeSelectionListener[] listeners2 = treeContractType
			.getTreeSelectionListeners();
			if (listeners2.length > 0) {
				conTypeTreeSelectionListener = listeners2[0];
				treeContractType.removeTreeSelectionListener(conTypeTreeSelectionListener);
		}
		
		/*
		 * ??????????????
		 */
		treeProject.setSelectionRow(0);
		treeProject.expandRow(0);
		if(containConWithoutTxt()){
			treeContractType.setSelectionRow(1);
		}else{
			treeContractType.setSelectionRow(0);
		}
		treeContractType.expandRow(1);
		treeProject.addTreeSelectionListener(projTreeSelectionListener);
		treeContractType.addTreeSelectionListener(conTypeTreeSelectionListener);
		
		treeSelectChange();
		
		/*
		 * ????????????????????????
		 */
		if(isHasBillTable()){
			getBillListTable().addKDTMouseListener(
					new com.kingdee.bos.ctrl.kdf.table.event.KDTMouseListener() {
	            public void tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) {
	                try {
	                    tblBill_tableClicked(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                }
	            }
	        });
			getBillListTable().setColumnMoveable(true);
		}
		
		String key = "F8";
		JComponent c = this.pnlSplit;
		InputMap inputMap = c.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
		if(inputMap.get(KeyStroke.getKeyStroke(key))!=null){
			if(c.getActionMap().getParent()!=null)
				c.getActionMap().getParent().remove(KeyStroke.getKeyStroke(key));
			c.getActionMap().remove(inputMap.get(KeyStroke.getKeyStroke(key)));
			inputMap.remove(KeyStroke.getKeyStroke(key));
			if(inputMap.getParent()!=null)
				inputMap.getParent().remove(KeyStroke.getKeyStroke(key));
		}
		//????????????????
		tHelper = new TablePreferencesHelper(this);
//		btnSetRespite.setIcon();
	}
	/**
	 * 
	 * ????????????????
	 * @author:liupd
	 * ??????????2006-8-3 <p>
	 */
	protected void initTable() {
		
		freezeMainTableColumn();
		if(getBillListTable()!=null){
			freezeBillTableColumn();
			getBillListTable().getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
			if(getBillListTable().getColumn("createTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createTime");
			}
			if(getBillListTable().getColumn("auditTime")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "auditTime");
			}
			if(getBillListTable().getColumn("createDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "createDate");
			}
			
			//revDate
			if(getBillListTable().getColumn("revDate")!=null){
				FDCHelper.formatTableDate(getBillListTable(), "revDate");
			}
			
			if(getBillListTable().getColumn("amount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "amount");
			}
			
			if(getBillListTable().getColumn("originalAmount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "originalAmount");
			}
			
			
			if(getBillListTable().getColumn("totalSettlePrice")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "totalSettlePrice");
			}
			
			
			if(getBillListTable().getColumn("totalOriginalAmount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "totalOriginalAmount");
			}
			
			if(getBillListTable().getColumn("actPaidAmount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "actPaidAmount");
			}		
			
			if(getBillListTable().getColumn("oldOriginalAmount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "oldOriginalAmount");
			}
			
			if(getBillListTable().getColumn("oldAmount")!=null){
				FDCHelper.formatTableNumber(getBillListTable(), "oldAmount");
			}
		}
		getMainTable().getSelectManager().setSelectMode(KDTSelectManager.ROW_SELECT);
		
		if(getMainTable().getColumn("amount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "amount");
		}
		if(getMainTable().getColumn("originalAmount")!=null){
			FDCHelper.formatTableNumber(getMainTable(), "originalAmount");
		}
		
		if(getMainTable().getColumn("signDate")!=null){
			FDCHelper.formatTableDate(getMainTable(), "signDate");
		}
		
		super.initTable();
	}
	
	
	protected void refresh(ActionEvent e) throws Exception {
		if(e!=null&&e.getActionCommand()!=null&&
				e.getActionCommand().equals("com.kingdee.eas.framework.client.AbstractListUI$ActionView")){
			//????????????????????????
			return;
		}
		super.refresh(e);
//		initTable();
//		FDCHelper.formatTableNumber(getMainTable(), "amount");
	}
	
	/**
	 * 
	 * ??????????????????
	 * 
	 * @author:liupd ??????????2006-7-25
	 *               <p>
	 */
	protected void freezeMainTableColumn() {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				/*
				 * ????????????????????????????1????
				 */
				// ????????
				int number_col_index = getMainTable().getColumn("number")
						.getColumnIndex();
				getMainTable().getViewManager().setFreezeView(-1, number_col_index+1);
			}});
		
	}

	/**
	 * 
	 * ??????????????????
	 * 
	 * @author:liupd ??????????2006-7-25
	 *               <p>
	 */
	protected void freezeBillTableColumn() {
		getBillListTable().checkParsed();
		getBillListTable().getStyleAttributes().setLocked(true);
		
	}
	
	
	protected void updateButtonStatus() {

		super.updateButtonStatus();

		// ????????????????????????????????????
//		if (!SysContext.getSysContext().getCurrentCostUnit().isIsBizUnit()) {
//			actionAddNew.setEnabled(false);
//			actionEdit.setEnabled(false);
//			actionRemove.setEnabled(false);
//			actionAddNew.setVisible(false);
//			actionEdit.setVisible(false);
//			actionRemove.setVisible(false);
//			actionAttachment.setEnabled(false);
//			actionAttachment.setVisible(false);
//			menuEdit.setVisible(false);
//		}
		
		actionRemove.setEnabled(true);
		actionAudit.setEnabled(true);
		actionUnAudit.setEnabled(true);
		actionRespite.setVisible(false);
		actionCancelRespite.setVisible(false);
	}

	/**
	 * 
	 * ????????????????????????????????????????????????????????????
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#beforeExcutQuery(com.kingdee.bos.metadata.entity.EntityViewInfo)
	 */
	protected void beforeExcutQuery(EntityViewInfo ev) {

		ev.setTopCount(2000);
		ev.getSorter().remove(new SorterItemInfo("id"));
		ev.getSorter().add(new SorterItemInfo("state"));
		ev.getSorter().add(new SorterItemInfo("number"));

		super.beforeExcutQuery(ev);
	}

	protected void treeProject_valueChanged(TreeSelectionEvent e)
			throws Exception {

		super.treeProject_valueChanged(e);
		treeSelectChange();
	}

	protected void treeContractType_valueChanged(TreeSelectionEvent e)
			throws Exception {

		super.treeContractType_valueChanged(e);
		treeSelectChange();
	}

	public DefaultKingdeeTreeNode getProjSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeProject
				.getLastSelectedPathComponent();
	}

	public DefaultKingdeeTreeNode getTypeSelectedTreeNode() {
		return (DefaultKingdeeTreeNode) treeContractType
				.getLastSelectedPathComponent();
	}

	/**
	 * 
	 * ??????????????????????????????????????????
	 * 
	 * @author:liupd ??????????2006-7-25
	 *               <p>
	 */
	protected void treeSelectChange() throws Exception {

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
		
		mainQuery.setFilter(getTreeSelectFilter(project,type,containConWithoutTxt()));

		execQuery();

		if(isHasBillTable()) {
			getBillListTable().removeRows(false);
		}	
		
		if (getMainTable().getRowCount() > 0) {
			getMainTable().getSelectManager().select(0, 0);
			btnAddNew.setEnabled(true);
		}else if(isHasBillTable()){
			/*
			 * ?????????????????????????? sxhong
			 */
			btnAddNew.setEnabled(false);
		}
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK arg0,
			EntityViewInfo arg1) {
		// TODO Auto-generated method stub
		
		
		FilterInfo filter = getTreeSelectChangeFilter();
		EntityViewInfo viewInfo = (EntityViewInfo) this.mainQuery.clone();
		if (viewInfo.getFilter() != null)
		{
			try {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} catch (BOSException e) {
				e.printStackTrace();
			}
		} else
		{
			viewInfo.setFilter(filter);
		}
		return super.getQueryExecutor(arg0, viewInfo);
	}


	/**
	 * ??????????????????????????????????????????
	 * @return
	 * @throws Exception
	 */
	protected FilterInfo getTreeSelectFilter(Object projectNode,Object  typeNode,boolean containConWithoutTxt) throws Exception {
		FilterInfo filter = getTreeSelectChangeFilter();
		FilterItemCollection filterItems = filter.getFilterItems();
		
		/*
		 * ??????????
		 */
		if (projectNode != null 	&& projectNode instanceof CoreBaseInfo) {

			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode;
			BOSUuid id = null;
			// ??????????????????????????????????????????????????????????????????
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					id = ((OrgStructureInfo)projTreeNodeInfo).getUnit().getId();	
				}else{
					id = ((FullOrgUnitInfo)projTreeNodeInfo).getId();
				}				
				
				String orgUnitLongNumber = null;
				if(orgUnit!=null && id.toString().equals(orgUnit.getId().toString())){					
					orgUnitLongNumber = orgUnit.getLongNumber();
				}else{
					FullOrgUnitInfo orgUnitInfo = FullOrgUnitFactory.getRemoteInstance()
					.getFullOrgUnitInfo(new ObjectUuidPK(id));
					orgUnitLongNumber = orgUnitInfo.getLongNumber();
				}
				
				FilterInfo f = new FilterInfo();
				f.getFilterItems().add(
						new FilterItemInfo("orgUnit.longNumber", orgUnitLongNumber + "%",CompareType.LIKE));

				f.getFilterItems().add(new FilterItemInfo("orgUnit.isCostOrgUnit", Boolean.TRUE));
				f.getFilterItems().add(new FilterItemInfo("orgUnit.id", authorizedOrgs,CompareType.INCLUDE));
				
				f.setMaskString("#0 and #1 and #2");
				
				if(filter!=null){
					filter.mergeFilter(f,"and");
				}
			}
			// ??????????????????????????????????????????????????????
			else if (projTreeNodeInfo instanceof CurProjectInfo) {
				id = projTreeNodeInfo.getId();
				Set idSet = FDCClientUtils.genProjectIdSet(id);
				filterItems.add(new FilterItemInfo("curProject.id", idSet,
						CompareType.INCLUDE));
			}

		}

		FilterInfo typefilter =  new FilterInfo();
		FilterItemCollection typefilterItems = typefilter.getFilterItems();	
		/*
		 * ??????????
		 */
		if (typeNode != null&& typeNode instanceof TreeBaseInfo) {
			TreeBaseInfo typeTreeNodeInfo = (TreeBaseInfo)typeNode;
			BOSUuid id = typeTreeNodeInfo.getId();
			Set idSet = FDCClientUtils.genContractTypeIdSet(id);
			typefilterItems.add(new FilterItemInfo("contractType.id", idSet,CompareType.INCLUDE));
		}else if(containConWithoutTxt && typeNode != null &&typeNode.equals("allContract")){
			//??????????????????????????????????????????????
			typefilterItems.add(new FilterItemInfo("contractType.id", "allContract"));
		}
		
		//????????
		//R110412-116??????????-????????-??????????????????????????????????????????????????????
		if(!(this instanceof ContractBillListUI) && !(this instanceof ContractBillReviseListUI)){
			typefilter.appendFilterItem("isAmtWithoutCost", String.valueOf(0));
		}
		
		if(filter!=null && typefilter!=null){
			filter.mergeFilter(typefilter,"and");
		}
		
		return filter;
	}

	/**
	 * 
	 * ????????????????????????????????????????????????????????????????????????????????????????????????????????????new FilterInfo()??????????????null??
	 * @return
	 * @author:liupd
	 * ??????????2006-9-5 <p>
	 */
	protected FilterInfo getTreeSelectChangeFilter() {
		FilterInfo filter = new FilterInfo();
		FilterItemCollection filterItems = filter.getFilterItems();
		Set set = getContractBillStateSet();
//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.AUDITTED_VALUE));
//		filterItems.add(new FilterItemInfo("state", FDCBillStateEnum.STORED_VALUE));
		filterItems.add(new FilterItemInfo("state",set,CompareType.INCLUDE));
		filterItems.add(new FilterItemInfo("contractType.isEnabled", Boolean.TRUE));
		filterItems.add(new FilterItemInfo("curProject.isEnabled", Boolean.TRUE));
		
/*		String maskString="(#0 or #1) and #2 and #3";
		filter.setMaskString(maskString);*/
		return filter;
	}


	/**
	 * ??????????????????????,????????????
	 * @return
	 */
	protected Set getContractBillStateSet() {
		Set set=new HashSet();
		set.add(FDCBillStateEnum.AUDITTED_VALUE);
		return set;
	}
	
	/**
	 * 
	 * ??????????????
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillListUI#actionAudit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();

		checkBillState(new String[]{getStateForAudit()}, "selectRightRowForAudit");

		audit(ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName()));

		super.actionAudit_actionPerformed(e);
		
		showOprtOKMsgAndRefresh();
	}
	
	//????BillListTable ??????????
	public void actionAuditResult_actionPerformed(ActionEvent e)throws Exception
	{
    	checkSelected();    	
        String id = getSelectedKeyValue(getBillListTable());
        if (!StringUtils.isEmpty(id)) {
            MultiApproveUtil.showApproveHis(
            		BOSUuid.read(id) ,UIModelDialogFactory.class.getName() , this);
        }
	}


	
	/**
	 * 
	 * ????????????
	 * 
	 * @author:liupd
	 * @see com.kingdee.eas.fdc.contract.client.AbstractContractBillListUI#actionUnAudit_actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {

		checkSelected();

		checkBillState(new String[]{getStateForUnAudit()}, "selectRightRowForUnAudit");

		List selectedIdValues = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());
		
		//????????
		for (Iterator iter = selectedIdValues.iterator(); iter.hasNext();) {
			String id = (String) iter.next();
			checkRef(id);
		}
		
		unAudit(selectedIdValues);
		
		super.actionUnAudit_actionPerformed(e);
		
		showOprtOKMsgAndRefresh();
	}

	

    
    /**
	 * 
	 * ??????????????????????????????????
	 * @return
	 * @throws BOSException
	 * @author:liupd
	 * ??????????2006-8-1 <p>
	 */
	protected abstract ICoreBillBase getRemoteInterface() throws BOSException;

	/**
	 * 
	 * ????????????????????????????????????????????????????????????????
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * ??????????2006-8-1 <p>
	 */
	protected abstract void audit(List ids) throws Exception;

	/**
	 * 
	 * ????????????????????????????????????????????????????????????????
	 * @param ids
	 * @throws Exception
	 * @author:liupd
	 * ??????????2006-8-1 <p>
	 */
	protected abstract void unAudit(List ids) throws Exception;
	
	/**
	 * 
	 * ????????????????????????????
	 * @return
	 * @author:liupd
	 * ??????????2006-8-1 <p>
	 */
	protected String getStateForAudit() {
		return FDCBillStateEnum.SUBMITTED_VALUE;
	}
	
	/**
	 * 
	 * ??????????????????????????????
	 * @return
	 * @author:liupd
	 * ??????????2006-8-1 <p>
	 */
	protected String getStateForUnAudit() {
		return FDCBillStateEnum.AUDITTED_VALUE;
	}
	
	/**
	 * 
	 * ??????????????????????????????????????Context
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.CoreBillListUI#prepareUIContext(com.kingdee.eas.common.client.UIContext, java.awt.event.ActionEvent)
	 */
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew)) {
			
			//????????BillTable????????????????????????????ID??????????????????????????????F7????????
			if(!isHasBillTable()) {
				Set leafNodesIdSet = new HashSet();
				TreeNode projRoot = (TreeNode) treeProject.getModel().getRoot();
				FDCClientUtils.genProjLeafNodesIdSet(projRoot, leafNodesIdSet);
				uiContext.put("projLeafNodesIdSet", leafNodesIdSet);
			}
		}
		else if(act.equals(actionEdit) || act.equals(actionView)) {
			uiContext.put(UIContext.ID, getSelectedKeyValue());
		}
		
		uiContext.put("contractBillId", getSelectedKeyValue(getMainTable()));
		uiContext.put("contractBillNumber", getSelectedContractNumber());
		
		Object userObject2 = getProjSelectedTreeNode().getUserObject();
		if(userObject2 instanceof CurProjectInfo){
			BOSUuid projId = ((CurProjectInfo) userObject2).getId();
			uiContext.put("projectId", projId);
		}
	}

	protected String getSelectedContractNumber() {
		KDTSelectBlock selectBlock = getMainTable().getSelectManager().get();
		
	    if (selectBlock != null) {
	        int rowIndex = selectBlock.getTop();
	        IRow row = getMainTable().getRow(rowIndex);
	        if (row == null) 
	        {
	            return null;
	        }
	        
	        ICell cell = row.getCell("number");
	
	        if (cell == null) {
	            MsgBox.showError(EASResource
	                    .getString(FrameWorkClientUtils.strResource
	                            + "Error_KeyField_Fail"));
	            SysUtil.abort();
	        }
	
	        Object keyValue = cell.getValue();
	
	        if (keyValue != null) {
	            return keyValue.toString();
	        }
	    }
	
	    return null;
	}
	
	/**
	 * ????????????????????
	 * 
	 * @return ??????????????????????????????????????????????????????????????????????????null
	 */
	protected String getSelectedKeyValue(KDTable table) {
		//String value = super.getSelectedKeyValue();
		
        int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
        selectIndex=-1;
        if (selectRows.length > 0)
        {
        	selectIndex=selectRows[0];
        }
		
	    KDTSelectBlock selectBlock = table.getSelectManager().get();
	    selectKeyValue = null;
	
	    if (selectBlock != null) {
	        int rowIndex = selectBlock.getTop();
	        IRow row = table.getRow(rowIndex);
	        if (row == null) 
	        {
	            return null;
	        }
	        
	        ICell cell = row.getCell(getKeyFieldName());
	
	        if (cell == null) {
	            MsgBox.showError(EASResource
	                    .getString(FrameWorkClientUtils.strResource
	                            + "Error_KeyField_Fail"));
	            SysUtil.abort();
	        }
	
	        Object keyValue = cell.getValue();
	
	        if (keyValue != null) {
	        	selectKeyValue = keyValue.toString();
	        }
	    }   
	    	
	    return selectKeyValue;
	}
	
	protected String getSelectedKeyValue() {
		return getSelectedKeyValue(getBillListTable());
	}

	/**
	 * 
	 * ????????????????????Table????????????????
	 * @return
	 * @author:liupd
	 * ??????????2006-8-2 <p>
	 */
	protected abstract KDTable getBillListTable();

	/**
	 * 
	 * ????????????????????Table??????????
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected() {
		checkSelected(getBillListTable());
	}

	/**
	 * 
	 * ????????????????????Table??????????
	 * @author:liupd
	 * @see com.kingdee.eas.framework.client.ListUI#checkSelected()
	 */
	public void checkSelected(KDTable table) {
		if (table.getRowCount()==0 || table.getSelectManager().size() == 0) {
	        MsgBox.showWarning(this, EASResource
	                .getString(FrameWorkClientUtils.strResource
	                        + "Msg_MustSelected"));
	        SysUtil.abort();
	    }
	}
	
	/**
	 * ????????
	 * @throws Exception
	 * @throws EASBizException
	 */
	protected void removeBill() throws Exception {
		int [] selectRows = KDTableUtil.getSelectedRows(getBillListTable());
		IObjectPK[] arrayPK = new ObjectUuidPK[selectRows.length];
		
		boolean canRemove = true ;
		//????????
        try
        {
			for (int i = 0; i < selectRows.length; i++) {
				String id = (String)getBillListTable().getCell(selectRows[i], getKeyFieldName()).getValue();
				checkRef(id);
				arrayPK[i] = new ObjectUuidPK(id);
			
                this.setOprtState("REMOVE");
                this.pubFireVOChangeListener(arrayPK[i].toString());
            }
            
		}catch (Throwable ex)
        {
        	this.handUIException(ex);
        	canRemove = false;
            SysUtil.abort();
        }
		
		if(canRemove){
			getRemoteInterface().delete(arrayPK);		
			showOprtOKMsgAndRefresh();
		}
	}
	
	/**
     * 
     * ????????????????????????????????
     * @param e
     * @throws BOSException
     * @author:liupd
     * ??????????2006-8-2 <p>
     */
	protected void displayBillByContract(EntityViewInfo view) throws BOSException {
		if(view==null){
			return ;
		}
	}
	
	protected boolean  displayBillByContract(KDTSelectEvent e,EntityViewInfo view) throws BOSException {
		return false;
	}
	
	//??????????????????
	protected int getPre(KDTSelectEvent e){
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	int pre = 2;
    	
    	if(getMainTable().getCell(top,"currency.precision")!=null){
    		pre = ((Integer)getMainTable().getCell(top, "currency.precision").getValue()).intValue();
    	}
    	
    	return pre;
	}
	
	/**
	 * 
	 * ????????????????????EntityViewInfo
	 * @param e
	 * @return
	 * @author:liupd
	 * ??????????2006-8-2 <p>
	 */
	protected EntityViewInfo genBillQueryView(com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e) {
		KDTSelectBlock selectBlock = e.getSelectBlock();
    	int top = selectBlock.getTop();
    	if(getMainTable().getCell(top, getKeyFieldName())==null){
    		return null;
    	}
    	
    	String contractId = (String)getMainTable().getCell(top, getKeyFieldName()).getValue();
    	EntityViewInfo view = new EntityViewInfo();
    	FilterInfo filter = new FilterInfo();
    	filter.getFilterItems().add(new FilterItemInfo("contractBill.id", contractId));
    	view.setFilter(filter);
    	view.getSorter().add(new SorterItemInfo(getBillStatePropertyName()));
    	SelectorItemCollection selectors = genBillQuerySelector();
    	if(selectors != null && selectors.size() > 0) {
    		for (Iterator iter = selectors.iterator(); iter.hasNext();) {
				SelectorItemInfo element = (SelectorItemInfo) iter.next();
				view.getSelector().add(element);
				
			}
    	}
		return view;
	}
	
	/**
	 * 
	 * ????????????????????Selector
	 * @return
	 * @author:liupd
	 * ??????????2006-8-3 <p>
	 */
	protected abstract SelectorItemCollection genBillQuerySelector();
	
	/**
     * ??????????????????????????????????
     * @return
     */
    protected String getEditUIModal()
    {
        return UIFactoryName.NEWTAB;
    }
    
//  ????????????????????????????
    protected void setActionState(){
    	super.setActionState();
    	updateButtonStatus();
    }
    
    /**
     * 
     * ??????????????????Table????????Table??????????Table
     * @return
     * @author:liupd
     * ??????????2006-8-26 <p>
     */
    protected boolean isHasBillTable() {
    	return true;
    }
    
    /**
     * 
     * ????????????????????????(??????????)
     * @author:liupd
     * ??????????2006-8-26 <p>
     */
    protected void checkRef(String id) throws Exception {
    	
    }
    
    /**
     * ??????????????????????
     * @throws Exception
     * @throws EASBizException
     */
    protected void Remove() throws Exception
    {
    	removeBill();
    }
    
    /**
     * 
     * ????????????????
     * @author:liupd
     * ??????????2006-8-26 <p>
     * @throws Exception 
     */
    protected void checkBeforeEdit() throws Exception {
	    checkSelected();
		
		CoreBillBaseInfo billInfo = getRemoteInterface().getCoreBillBaseInfo(new ObjectUuidPK(getSelectedKeyValue(getBillListTable())));
		String billState = billInfo.getString(getBillStatePropertyName());
		String[] states = getBillStateForEditOrRemove();
		boolean pass = false;
		for (int i = 0; i < states.length; i++) {
			if(billState.equals(states[i])) {
				pass = true;
			}
		}
		if(!pass) {
			MsgBox.showWarning(this, ContractClientUtils.getRes("cantEdit"));
			SysUtil.abort();
		}
    }
    
    /**
     * 
     * ????????????????????????
     * @author:liupd
     * ??????????2006-8-26 <p>
     * @throws Exception 
     */
    protected boolean checkBeforeRemove() throws Exception {
    	checkSelected();
    	
    	List idList = ContractClientUtils.getSelectedIdValues(getBillListTable(), getKeyFieldName());

    	if(idList==null || idList.size()==0){
    		return false;
    	}
    	
		Set idSet = ContractClientUtils.listToSet(idList);

		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(
				new FilterItemInfo("id", idSet, CompareType.INCLUDE));
		view.setFilter(filter);
		view.getSelector().add("id");
		view.getSelector().add(getBillStatePropertyName());
		CoreBillBaseCollection coll = getRemoteInterface().getCoreBillBaseCollection(view);

		
		String[] states = getBillStateForEditOrRemove();
		
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			CoreBillBaseInfo element = (CoreBillBaseInfo) iter.next();
			String billState = element.getString(getBillStatePropertyName());
			boolean pass = false;
			for (int i = 0; i < states.length; i++) {
				if(billState.equals(states[i])) {
					pass = true;
				}
			}
			if(!pass) {
				MsgBox.showWarning(this, ContractClientUtils.getRes("cantRemove"));
				SysUtil.abort();
			}
			
			ContractClientUtils.checkContractBillRefForRemove(this, element.getId().toString());
		}
		
		return true;
    }
    
    
  


	/** 
	 * ??????????????KDLayout????????????"OriginalBounds"??????????KDLayout????????????????????????????????????Java??????????????????????
	 * 			??????UI????????????????????public void initUIContentLayout()
     * @return
     * @author:jelon
     * ??????????2006-8-28 <p>
	 */
	private void setContainerLayout() {
		//pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(pnlSplit.getX(), pnlSplit.getY(), pnlSplit.getDividerLocation(), pnlSplit.HEIGHT));
		pnlLeftTree.putClientProperty("OriginalBounds", new Rectangle(10, 10, 250, 609));
		pnlRight.putClientProperty("OriginalBounds", new Rectangle(270, 10, 733, 609));
	}
	
	/* setContainerLayout????????????????????????????(??????????????????????????????????,????outofMem ????????????????),
	 * ????????????????,????????????????
	 * @see com.kingdee.bos.ui.face.CoreUIObject#initLayout()
	 */
	public void initLayout() {
		super.initLayout();
		setContainerLayout();
	}
	/**
	 * ??????????????????????????????
	 * @author sxhong  		Date 2006-9-14
	 * @return
	 * @see com.kingdee.eas.framework.client.ListUI#getTableForPrintSetting()
	 */
	protected KDTable getTableForPrintSetting()
	{
		if(isHasBillTable()) return getBillListTable();
		else return super.getTableForPrintSetting();
	}

	/**
	 * 
	 * ????????????????????????????????????????????????ListUI??allIdList??????Bill??IdList
	 * ????????????????????????????????????????id????????id??????????????????????????????????
	 * ????????????????????????????????ListUI??processAllIdList????????
	 * ??getBillIDList()??????
	 * @return
	 * @author:liupd
	 * ??????????2006-9-29 <p>
	 */
	protected List getAllIdListForBill() {
		List idList = new ArrayList();
		int count = getBillListTable().getRowCount();

        if (count == 0)
        {
            return null;
        }
        String id = null;
        Object[]keyValue = null;
		for (int i = 0; i < count; i++) {
			id = (String)getBillListTable().getCell(i, getKeyFieldName()).getValue();
			keyValue = new Object[1];
			keyValue[0] = id;
			idList.add(keyValue);
		}
		
		return idList;
	}
	
	protected void initWorkButton() {
		super.initWorkButton();
		btnAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		btnUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setIcon(FDCClientHelper.ICON_AUDIT);
		menuItemUnAudit.setIcon(FDCClientHelper.ICON_UNAUDIT);
		menuItemAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl U"));
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl U"));
		menuItemAudit.setText(menuItemAudit.getText().replaceAll("\\(A\\)", "")+"(A)");
		menuItemAudit.setMnemonic('A');
		
		actionAudit.putValue(ItemAction.ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setText(menuItemUnAudit.getText().replaceAll("\\(U\\)", "")+"(U)");
		menuItemUnAudit.setAccelerator(KeyStroke.getKeyStroke("ctrl shift U"));
		menuItemUnAudit.setMnemonic('U');
		actionRespite.setVisible(false);
		actionRespite.setEnabled(false);
		actionCancelRespite.setVisible(false);
		actionCancelRespite.setEnabled(false);
//		menuItemTraceUp.setAccelerator(KeyStroke.getKeyStroke("F5"));
	}
	
    /**
     * 
     * ????????????????????????????
     * @return
     * @author:liupd
     * ??????????2006-8-26 <p>
     */
    protected String[] getBillStateForEditOrRemove() {
    	return new String[]{FDCBillStateEnum.SAVED_VALUE, FDCBillStateEnum.SUBMITTED_VALUE};
    }
      
    /**
     * ??????????????????????????????????????????id??????????????getSelectedKeyValues()????
     * ????????????????????????????????????????????????????????????????????
     * ??????tblMain??????????????????????????
     * @author sxhong  		Date 2006-10-28
     * @return	IIDList ??????????????????????????????????????????????????????????????????????????null
     * @see com.kingdee.eas.framework.client.ListUI#getSelectedKeyValuesForHasQueryPK()
     */
    protected IIDList getSelectedKeyValuesForHasQueryPK()
    {
    	if(isHasBillTable()){
    		return getBillIDList();
    	}else{
    		return super.getSelectedKeyValuesForHasQueryPK();
    	}
    }
    
    private IIDList getBillIDList()
    {
    	if(getBillListTable()==null){
    		return RealModeIDList.getEmptyIDList();//????????????
    	}
    	KDTable billTable=getBillListTable();
        // ????????????????????????????????
        int [] selectRows = KDTableUtil.getSelectedRows(billTable);
        
//        selectList = new ArrayList();
        int size = selectRows.length; // ??????????????????
        int maxReturnRowCount = 10000;//????????
        if (size == 0)
        {
            return null;
        }

        if ((size == 1)
                && (billTable.getSelectManager().get().getTop() == billTable
                        .getSelectManager().get().getBottom()))
        {
            // ????????????????????????????100??????
            //?????????????????????????????????????????????????????????????????????????? by psu_s 2005-8-24
            RealModeIDList idList = new RealModeIDList();
            int rowNum = billTable.getSelectManager().get().getTop();
            ICell cell = billTable.getRow(rowNum).getCell(getKeyFieldName());
             
            if (cell == null)
            {
                MsgBox.showError(EASResource
                        .getString(FrameWorkClientUtils.strResource
                                + "Error_KeyField_Fail"));
                SysUtil.abort();
            }

            String curId = cell.getValue().toString();
            //allIdList
            Object[] currentIds = null;
            //TODO ??????
            List allIdList = getAllIdListForBill();
            if(rowNum<allIdList.size())
            {
                currentIds = (Object[]) allIdList.get(rowNum);
            }
            else
            {
                for(int i=0;i<allIdList.size();i++)
                {
                    Object [] objs = (Object[]) allIdList.get(i);
                    if(curId!=null && curId.equals(objs[0].toString()))
                    {
                        rowNum = i;
                        currentIds = (Object[]) allIdList.get(rowNum);
                        break;
                    }
                }
                 
            }
            String currentId = null;
            if(currentIds == null)
            {
            	//??????????????????????
                RealModeIDList idList2 = new RealModeIDList();
                if(cell != null && cell.getValue() != null)
                {
                    idList2.add(cell.getValue().toString());
                }
                return idList2;
            }
            currentId = currentIds[0].toString();
            
            Object[] ids = null;

            if (currentIds.length > 1) // ????????bill
            {
                String theId = null;
                int j = 0;

                for (int i = 0; i < allIdList.size(); i++)
                {
                    ids = (Object[]) allIdList.get(i);

                    if (theId == null || !theId.equals(ids[0].toString()))
                    {
                        j++;
                        theId = ids[0].toString();

                        if (currentId.equals(theId))
                        {
                            rowNum = j - 1;
                        }

                        idList.add(theId);
                        // selectList.add(new Integer(i));
                    }
                }
            }
            else
            // ?????? 
            {
                for (int i = 0; i < allIdList.size(); i++)
                {//??????????????id??????idList????????????????????Id????????????????id
                    ids = (Object[]) allIdList.get(i);
                    idList.add(ids[0].toString());
                    // selectList.add(new Integer(i));
                }
            }

            idList.setCurrentIndex(rowNum);
//            idList.setQuery(this.mainQueryPK, this.mainQuery);
//             idList.setCount(allIdList.size()); //??????????
            idList.setMaxRowCount(10000);
//            getSelectid
//            selectList.add(new Integer(rowNum));

            return idList;
        }
        else
        {
            RealModeIDList idList = new RealModeIDList();

            for(int i=0;i<size;i++)
            {
                if(selectRows[i]<0)
                {
                    return idList;
                }
                ICell cell = billTable.getRow(selectRows[i]).getCell(getKeyFieldName());

                if (cell == null)
                {
                    MsgBox.showError(EASResource
                            .getString(FrameWorkClientUtils.strResource
                                    + "Error_KeyField_Fail"));
                    SysUtil.abort();
                }

                if(cell.getValue() == null)
                {
                    return idList;
                }
                String id = cell.getValue().toString();
                idList.add(id);
//                selectList.add(new Integer(selectRows[i]));

            }
            idList.setCurrentIndex(0);
//            idList.setQuery(this.mainQueryPK, this.mainQuery);
            //idList.setCount(this.rowCount);
//            idList.setMaxRowCount(this.maxReturnRowCount);
            idList.setMaxRowCount(maxReturnRowCount);
            return idList;
        }
    }
    
    /**
     * 
     * ??????????????????????????????????????????????????????????????????????????ID????
     * @author sxhong
     * ????????????????????????????
     * 
     * @return ??????????????????????????????????????????????????????????????????????????null
     */
    protected IIDList getSelectedKeyValues() {
    	return super.getSelectedKeyValues();
    }
    
    /**
     * ????????????????????????????????????????????tblMain????????????????????????????????????
     * @author sxhong  		Date 2006-10-30
     * @return
     * @throws Exception
     * @see com.kingdee.eas.framework.client.CoreBillListUI#getBillList()
     */
    protected CoreBillBaseCollection getBillList() throws Exception
    {
    	CoreBillBaseCollection billList;
		if(isHasBillTable()){
			KDTable bak = getMainTable();
			tblMain = getBillListTable();
			try{
				billList=super.getBillList();
			}finally{
				tblMain = bak;
			}
		}else{
			billList=super.getBillList();
		}
    	return billList;
    }
    
    public void onGetRowSet(IRowSet rowSet) {
    	
    	super.onGetRowSet(rowSet);
/*	??????query??????????,?????????????????? by sxhong 2009-05-07 15:26:29
		
		 * ??????????????????????,??????????????,??????????????????????
		 
		try {
			rowSet.beforeFirst();
			
			Set contractIds = new HashSet() ;
			Map idMap  = new HashMap();
			while (rowSet.next()) {
				String id  = ContractClientUtils.getUpdateAmtByAmtWithoutCost(rowSet);
				if(id!=null){
					idMap.put(id,id);					
				}
				contractIds.add(rowSet.getString("id"));
			}		
//			RPC??????????,??????????,??????????????????????????????????
			IUIActionPostman actionPost = prepareDataAfterOnRowSet();
			((RequestContext)actionPost.getRequestContext()).put("ContractListBaseUIHandler.contractIds",contractIds);
			((RequestContext)actionPost.getRequestContext()).put("ContractListBaseUIHandler.idMap",idMap);
			actionPost.callHandler();
			
			Map amountMap  = (Map) ActionCache.get("ContractListBaseUIHandler.amountMap");
			if(amountMap==null){
				amountMap = ((IContractBill)ContractBillFactory.getRemoteInstance()).getAmtByAmtWithoutCost(idMap);
			}
//			Map	amountMap = ((IContractBill)ContractBillFactory.getRemoteInstance()).getAmtByAmtWithoutCost(idMap);
			rowSet.beforeFirst();
			while (rowSet.next()) {
				ContractClientUtils.updateAmtByAmtWithoutCost( rowSet, amountMap);
			}			
			
			rowSet.beforeFirst();
		} catch (Exception e) {
			handUIException(e);
		}*/

    }
    
    public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
    	KDTable table=getBillListTable();
    	if(!isHasBillTable()||table==null){
    		table=getMainTable();
    	}
    	
    	boolean isEdit=false;
        AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
        String boID = this.getSelectedKeyValue();
        checkSelected(table);
        if (boID == null)
        {
            return;
        }
        if(getBillStatePropertyName()!=null){
        	int rowIdx=table.getSelectManager().getActiveRowIndex();
        	ICell cell =table.getCell(rowIdx, getBillStatePropertyName());
        	Object obj=cell.getValue();
        	if(obj!=null&&
        			(obj.toString().equals(FDCBillStateEnum.SAVED.toString())
        			||obj.toString().equals(FDCBillStateEnum.SUBMITTED.toString())
        			||obj.toString().equals(FDCBillStateEnum.AUDITTING.toString())
        			||obj.toString().equals(BillStatusEnum.SAVE.toString())
        			||obj.toString().equals(BillStatusEnum.SUBMIT.toString())
        			||obj.toString().equals(BillStatusEnum.AUDITING.toString()))){
        		isEdit=true;
        	}else{
        		isEdit=false;
        	}
			
        }
        
        
        //????????????????????????????
        if(isEdit){
        	//????
	        String orgId = SysContext.getSysContext().getCurrentOrgUnit().getId().toString();
	        String userId = SysContext.getSysContext().getCurrentUserInfo().getId().toString();
			if(this.getClass().getName().equals("com.kingdee.eas.fdc.contract.client.ContractBillListUI")){
				String uiName = "com.kingdee.eas.fdc.contract.client.ContractBillEditUI";
				boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
	    				new ObjectUuidPK(userId),
	    				new ObjectUuidPK(orgId),
	    				new MetaDataPK(uiName),
	    				new MetaDataPK("ActionAttamentCtrl") );
				//????????????????????????????????????????????,?????????????????????????????????????????????????????? ????
	        	if(hasFunctionPermission){
	        		boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
	        		if(creatorCtrl){
	        			//????????????????????????
	        			FDCSQLBuilder builder=new FDCSQLBuilder();
	        			builder.appendSql("select 1 from T_Con_ContractBill where fid=? and fcreatorId=?");
	        			builder.addParam(boID);
	        			builder.addParam(userId);
	        			if(!builder.isExist()){
	        				isEdit=false;
	        			}
	        		}
	        	}else{
	        		isEdit=false;
	        	}
	        }
	        //??????????
	        if(this.getClass().getName().equals("com.kingdee.eas.fdc.contract.client.PayRequestBillListUI")){
	        		String uiName = "com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI";
					boolean hasFunctionPermission = PermissionFactory.getRemoteInstance().hasFunctionPermission(
		    				new ObjectUuidPK(userId),
		    				new ObjectUuidPK(orgId),
		    				new MetaDataPK(uiName),
		    				new MetaDataPK("ActionAttamentCtrl") );
					//????????????????????????????????????????????,?????????????????????????????????????????????????????? ????
		        	if(hasFunctionPermission){
		        		boolean creatorCtrl=FDCUtils.getDefaultFDCParamByKey(null, null, FDCConstants.FDC_PARAM_CREATORATTACHMENT);
		        		if(creatorCtrl){
		        			//????????????????????????
		        			FDCSQLBuilder builder=new FDCSQLBuilder();
		        			builder.appendSql("select 1 from T_Con_PayRequestBill where fid=? and fcreatorId=?");
		        			builder.addParam(boID);
		        			builder.addParam(userId);
		        			if(!builder.isExist()){
		        				isEdit=false;
		        			}
		        		}
		        	}else{
		        		isEdit=false;
		        	}
		        }
	    }
        acm.showAttachmentListUIByBoID(boID, this,isEdit); // boID ?? ???????????? ?????????? ID
    }
    
	protected abstract boolean isFootVisible();
	
	
    private String selectKeyValue = null;
    private int selectIndex = -1;
    protected void setPreSelecteRow()
    {
        if (isDoRefreshLocate())
        {
        	selectKeyValue = selectPreRow(tblMain,this.getAllIdList(),selectKeyValue,selectIndex,getKeyFieldName());
        }
    }
    
    
    /**
     * ????listui??,????????????????
     */
    public String selectPreRow(KDTable table,List allIdList,String selectKeyValue,int selectIndex,String keyFieldName)
    {
    	int index = selectIndex;
        if (table.getRowCount() <= 0)
            return null;
        if (index >= 0 && selectKeyValue != null)
        {
            if (index>=table.getRowCount())
                index=table.getRowCount()-1;
            //????????index??
            String selectValue=getTableCellValue(table,index,keyFieldName);
            if (selectValue!=null&&selectKeyValue.equals(selectValue))
            {
            	table.getSelectManager().select(index, 0);
                return selectValue;
            }

            //????????????????????????????????
            int size=allIdList==null?0:allIdList.size();
            for (int r=0;r<size;r++)
            {
                selectValue=getTableCellValue(table,r,keyFieldName);
                if (selectValue!=null&&selectKeyValue.equals(selectValue))
                {
                	table.getSelectManager().select(index, 0);
                    return selectValue;
                }
            }
            if (table.getRowCount() > 0)
            {
            	table.getSelectManager().select(0, 0);
            }
        }
        return null;
    }
    
    public String getTableCellValue(KDTable table,int rowIndex,String  FieldName){
        String selectValue="";
        if (rowIndex<0)
            return selectValue;
        IRow row = table.getRow(rowIndex);
        if (row != null)
        {
            ICell cell = row.getCell(FieldName);
            if (cell!=null&&cell.getValue()!=null)
                selectValue=cell.getValue().toString();
        }
        return selectValue;
    }
    
	/**
	 * RPC??????????????????????????RPC
	 */
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();

		return clientHanlder;
    }
	
	/**
	 * OnRowSet
	 * @return
	 */
	public IUIActionPostman prepareDataAfterOnRowSet() {
		IUIActionPostman clientHanlder = UIActionPostman.getInstance(this);
		RequestContext request=new RequestContext();
		if (clientHanlder != null) {
			
			clientHanlder.setAvailabe(true);
//			request.setItemActionHandler(getUIHandlerClassName());
//			request.setItemAction("afterOnRowSet");
			request.setClassName(getUIHandlerClassName());
			request.setMethodName("afterOnRowSet");
//	    	request.setMetaDataPK(this.getMetaDataPK());
//	    	
//	    	request.setUIName(this.getClass().getName());
//	    	request.setOrgExt(((Boolean)getUIContext().get("ORGEXT"))==null?
//	    			           true:((Boolean)getUIContext().get("ORGEXT")).booleanValue());
//	    	request.setPerMenuItem(this.getOnloadPermItemName());
//	    	request.setActionPK(new MetaDataPK(getActionName(actionOnLoad)));
//	    	request.setConfigPK(this.buildPK());
//	        request.setMainOrgType(this.getMainBizOrgType());
//	        request.setMainOrgContext(this.getMainOrgContext());
//	        
//	        request.put("License.UserInfo",
//	        		(LicenseUserInfo) SysContext.getSysContext().getProperty("License.UserInfo"));
//	        request.put("License.Ckeck", 
//	        		(getUIContext().get(UIContext.CHECK_LICENSE)==null ? 
//	        				"":getUIContext().get(UIContext.CHECK_LICENSE).toString()));
//	        
	        clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	
    //??????????????????????????????????????????????
	/**
     *??????????id??????id
     */
    protected CoreBillBaseCollection getNewBillList() throws Exception
    {
        checkSelected();

        ArrayList idList = new ArrayList();
        idList.add(getSelectedKeyValue());

        Object[] filterObj=new Object[idList.size()];
        FilterInfo filterInfo = new FilterInfo();
        Iterator idIter = idList.iterator();
        int index = 0;
        StringBuffer sbMaskString = new StringBuffer();

        while (idIter.hasNext())
        {
              String id = idIter.next().toString();
              filterObj[index]=id;
              index++;
         }

         String strIdLists =StringUtils.arrayToString(idList.toArray(),",");
         filterInfo.getFilterItems().add(new FilterItemInfo("id", strIdLists, com.kingdee.bos.metadata.query.util.CompareType.INCLUDE));
         //????????????????????VOUCHERFLAG??????????????????????????????????????????
         if(isCanVoucher())
         {
            filterInfo.getFilterItems().add(new FilterItemInfo(VOUCHERFLAG,Boolean.FALSE,CompareType.EQUALS));
            filterInfo.getFilterItems().add(new FilterItemInfo(VOUCHERFLAG,null,CompareType.EQUALS));
            sbMaskString.append("#0 and (#1 or #2)");
         }else
         {
            sbMaskString.append("#0");
         }
         filterInfo.setMaskString(sbMaskString.toString());

         EntityViewInfo entityViewInfo = new EntityViewInfo();
         entityViewInfo.getSelector().add(new SelectorItemInfo("id"));
         entityViewInfo.setFilter(filterInfo);

         CoreBillBaseCollection sourceBillCollection = ( (ICoreBillBase)getCoreBaseInterface())
                .getCoreBillBaseCollection(entityViewInfo);

         return sourceBillCollection;
    }
    
    
    private static final String VOUCHERFLAG = "fiVouchered";
    //????????????????????
	private boolean canVoucher=false;
    private boolean isCanVoucher()
    {
    	return this.canVoucher;
    }
    
    public void beforeActionPerformed(ActionEvent e) {
    	ItemAction act = getActionFromActionEvent(e);
    	if(act!=null&&act.equals(actionVoucher)){
    		this.canVoucher=true;
    	}else{
    		this.canVoucher=false;
    	}
    	super.beforeActionPerformed(e);
    }
    
	protected ArrayList getSelectedIdValues() {
		// TODO ????????????????
		ArrayList idList;
		if(isHasBillTable()){
			KDTable bak = getMainTable();
			tblMain = getBillListTable();
			try{
				idList=super.getSelectedIdValues();
			}finally{
				tblMain = bak;
			}
		}else{
			idList=super.getSelectedIdValues();
		}
    	return idList;
	}
	
	public void actionRespite_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(getBillListTable(), getKeyFieldName(),false);
			if(idList.size()>0 && idList.get(0) != null){
				((IFDCBill)getRemoteInterface()).setRespite(idList, true);
				MsgBox.showWarning("??????????????????????????????????????");
				refreshList();
			}
	}
	
	public void actionCancelRespite_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		List idList = ContractClientUtils.getSelectedUnAuditedId(getBillListTable(), getKeyFieldName(),true);
		if(idList.size()>0 && idList.get(0) != null){
			((IFDCBill)getRemoteInterface()).setRespite(idList, false);
			showOprtOKMsgAndRefresh();
		}
	}
	
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog == null) {
			commonQueryDialog = super.initCommonQueryDialog();
			commonQueryDialog.setWidth(400);
			commonQueryDialog.setTitle("????????");
		}
		if(this.getClass().getName().equals(ContractBillListUI.class.getName())){
			commonQueryDialog.addUserPanel(this.getFilterUI());
		}
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new ContractBillFilterUI(this, this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		filterUI.setCompany(null);
		filterUI.setProject(null);
		filterUI.setContractType(containConWithoutTxt(), null);
		filterUI.setAuthorizedOrgs(this.authorizedOrgs);
		if (isOnlyQueryAudited()) {
			filterUI.setAuditedState();
		}
		filterUI.setOtherFilter(getOtherFilter4Query());
		DefaultKingdeeTreeNode projectNode = this.getProjSelectedTreeNode();
		if (projectNode != null && projectNode.getUserObject() != null && projectNode.getUserObject() instanceof CoreBaseInfo) {
			CoreBaseInfo projTreeNodeInfo = (CoreBaseInfo) projectNode.getUserObject();
			if (projTreeNodeInfo instanceof OrgStructureInfo || projTreeNodeInfo instanceof FullOrgUnitInfo) {
				FullOrgUnitInfo company = null;
				if (projTreeNodeInfo instanceof OrgStructureInfo) {
					company = ((OrgStructureInfo) projTreeNodeInfo).getUnit();
				} else {
					company = (FullOrgUnitInfo) projTreeNodeInfo;
				}
				filterUI.setCompany(company);
			} else if (projTreeNodeInfo instanceof CurProjectInfo) {
				filterUI.setProject((CurProjectInfo) projTreeNodeInfo);
			}
		}
		DefaultKingdeeTreeNode contractTypeNode = this.getTypeSelectedTreeNode();
		if (contractTypeNode != null && contractTypeNode.getUserObject() != null) {
			Object typeNode = contractTypeNode.getUserObject();
			if (typeNode instanceof ContractTypeInfo) {
				filterUI.setContractType(containConWithoutTxt(), (ContractTypeInfo) typeNode);
			}
		}

		return this.filterUI;
	}
	
	protected FilterInfo getOtherFilter4Query() {
		return null;
	}
	protected boolean isOnlyQueryAudited() {
		return false;
	}

    /**
     * ????????CU????????????????????????????CU????????????????????????????????????????????????????????
     * @author owen_wen 2010-12-13
     */
    protected boolean isIgnoreCUFilter() {
    	if (OrgConstants.DEF_CU_ID.equals(SysContext.getSysContext().getCurrentOrgUnit().getId().toString()))
    		return true;
    	else 
    		return false;
    }
}