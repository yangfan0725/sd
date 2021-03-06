package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.ActionMap;
import javax.swing.JMenuItem;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTAction;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.util.KDTableUtil;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.framework.cache.CacheServiceFactory;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIException;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.master.cssp.CSSPGroupInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.market.MarketDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.CustomerTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerFactory;
import com.kingdee.eas.fdc.sellhouse.FDCCustomerInfo;
import com.kingdee.eas.fdc.sellhouse.IFDCCustomer;
import com.kingdee.eas.fdc.sellhouse.LinkmanEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseCustomerInfoFactory;
import com.kingdee.eas.fdc.sellhouse.SincerityPurchaseFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordFactory;
import com.kingdee.eas.fdc.sellhouse.TrackRecordInfo;
import com.kingdee.eas.fdc.tenancy.MarketingUnitInfo;
import com.kingdee.eas.fdc.tenancy.TenancyCustomerEntryFactory;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.framework.client.CoreUI;
import com.kingdee.eas.util.client.MsgBox;

public class CustomerExtListUI extends AbstractCustomerExtListUI {
	public CustomerExtListUI() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2790807549965319883L;
	private static final Logger logger = CoreUIObject.getLogger(CustomerExtListUI.class);
	// SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
	SaleOrgUnitInfo saleOrg = SysContext.getSysContext().getCurrentSaleUnit();
	UserInfo userInfo = SysContext.getSysContext().getCurrentUserInfo();
	MarketDisplaySetting setting = new MarketDisplaySetting();
	private boolean showAll = false;

	private CustomerQueryPanel filterUI;
	protected CommonQueryDialog commonQueryDialog;
	
	
	private TreeNode marketNode = null;
	/**
	 * ??????????????id list
	 */
	private List idList  = new ArrayList();

	private String saleUserIds = "null";

	public void actionClueQueryShow_actionPerformed(ActionEvent e) throws Exception {

		CommerceHelper.openTheUIWindow(this, CustomerShareObjectUI.class.getName(), null);
		this.refresh(e);
	}

	protected void execQuery() {
		super.execQuery();
	}

	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		return commonQueryDialog;
	}

	private CustomerQueryPanel getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new FDCCustomerFullFilterUI(this, this.actionOnLoad);
			} catch (Exception e) {
				e.printStackTrace();
				abort(e);
			}
		}
		return this.filterUI;
	}

	protected boolean initDefaultFilter() {
		return true;
	}



	protected void refresh(ActionEvent e) throws Exception {
		CacheServiceFactory.getInstance().discardType(new LinkmanEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		super.refresh(e);
	}

	public void onLoad() throws Exception {
		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		CacheServiceFactory.getInstance().discardType(new LinkmanEntryInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new TrackRecordInfo().getBOSType());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());

		// initCommonQueryDialog();
		super.onLoad();
		initTree();
		this.treeMain.setSelectionRow(0);
		this.MenuItemAttachment.setVisible(true);
		this.btnAttachment.setVisible(true);
		this.chkShowAll.setVisible(false);

		this.menuItemCancel.setText("????");
		this.menuItemCancelCancel.setText("????");

		this.btnCancel.setText("????");
		this.btnCancel.setToolTipText("????");
		this.btnCancelCancel.setText("????");
		this.btnCancelCancel.setToolTipText("????");

		this.btnClueQueryShow.setVisible(true);// ????????
		this.btnClueQueryShow.setEnabled(true);// ????????
		this.menuItemClueQueryShow.setVisible(true);
		this.menuItemClueQueryShow.setEnabled(true);

		this.menuEdit.remove(menuItemRemove);
		// this.btnRemove.setVisible(false);
		this.btnModifyCustomerName.setVisible(false);
		this.btnModifyCustomerPhone.setVisible(false);
		this.menuItemSwitchTo.setVisible(false);

		FDCClientHelper.addSqlMenu(this, this.menuEdit);
		this.btnAddTrackRecord.setEnabled(true);
		if (!saleOrg.isIsBizUnit()) {
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);

			this.actionCancel.setEnabled(false);
			this.actionCancelCancel.setEnabled(false);
			this.actionToSysCustomer.setEnabled(false);
			this.actionImportantTrack.setEnabled(false);
			this.actionCancelImportantTrack.setEnabled(false);
			this.actionCustomerAdapter.setEnabled(false);
			this.actionCustomerShare.setEnabled(false);
			this.actionCustomerCancelShare.setEnabled(false);
			this.btnAddTrackRecord.setEnabled(false);

		}
		
		/**
		 * ????????ctrl+c????
		 */
		ActionMap actionMap = tblMain.getActionMap();
		actionMap.remove(KDTAction.COPY);
		actionMap.remove(KDTAction.CUT);
		
		disableExport(this, tblMain);
		
		
		
}

	/**
	 * ????????????????
	 * ????????????????????
	 */
	public static void disableExport(CoreUI ui, KDTable table) {
		String menuItemExportExcel = "menuItemExportExcel";
		String menuItemExportSelected = "menuItemExportSelected";
		String menuMail = "menuMail";
		/**
		 * ????????????????????
		 */
		String menuCopy = "menuItemCopy";
		String menuPublishReport = "menuPublishReport";
		if (table == null || ui == null) {
			return;
		}
		KDTMenuManager tm = ui.getMenuManager(table);
		if (tm != null) {
			Component[] menus = tm.getMenu().getComponents();
			for (int i = 0; i < menus.length; i++) {
				// ??????????
				if (menus[i] instanceof JMenuItem) {
					JMenuItem menu = (JMenuItem) menus[i];
					if (menu != null && menu.getName() != null && 
							(menu.getName().equalsIgnoreCase(menuItemExportExcel) 
									|| menu.getName().equalsIgnoreCase(menuItemExportSelected)
									||menu.getName().equalsIgnoreCase(menuMail)
									||menu.getName().equalsIgnoreCase(menuCopy)
									||menu.getName().equalsIgnoreCase(menuPublishReport))) {
						menu.setVisible(false);
					} else if (menu!=null) {
//						System.out.println("????:" + "\t" + menu.getName() + "\t" + menu.getText());
					}

				}
			}
		}
	}
	public void loadFields() {
		super.loadFields();
	}

	/**
	 * output tblMain_tableClicked method
	 */
	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e) throws Exception {
		super.tblMain_tableClicked(e);
	}

	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		this.tblMain.removeRows();
		this.execQuery();
		
		/**
		 * ??????????????????
		 * by renliang
		 */
		idList = super.getQueryPkList();
		fetchInitData(idList);
	}

	/* ????????CU???? zhicheng_jin 090226 */
	protected boolean isIgnoreCUFilter() {
		return true;
	}

	// ????????
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			// ??????????????????????????????????
			viewInfo = (EntityViewInfo) this.mainQuery.clone();
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeMain.getLastSelectedPathComponent();
			MarketingUnitInfo marketingInfo = null;
			Set marketSet = new HashSet();
			Set orgSet = new HashSet();
			// ????????????????????????????????????????????????????????????????????
			// ????????????????????????????????????????????????????????????????????????????????????
			// ????????????????????????????????????????????????????????????????????????
			// ????????????????????????????????????????????????????????????????????????????????????????????
			// ??????????????????????????????????????????????????????????????????????????????????????????????????????

			if (node != null && node.getUserObject() instanceof MarketingUnitInfo) {
				marketingInfo = (MarketingUnitInfo) node.getUserObject();
				marketSet.add(marketingInfo.getId().toString());
				// marketSet = SHEHelper.getMarketingUnit(node, marketSet);
				marketSet = SHEHelper.getChildMarketUnit(node, marketSet);
			} else if (node != null && node.getUserObject() instanceof OrgStructureInfo) {
				orgSet.add(saleOrg.getId().toString());
			}
			FilterInfo thisFilter = new FilterInfo();
			saleUserIds = "null";
			if (node != null)
				getAllSaleIds(node);
			thisFilter.getFilterItems().add(new FilterItemInfo("salesman.id", saleUserIds, CompareType.INCLUDE));
			FDCCustomerParams para = new FDCCustomerParams(this.getFilterUI().getCustomerParams());
			if (para.getBoolean("chkShare")) {
				thisFilter.getFilterItems().add(new FilterItemInfo("shareSellerList.seller.id", saleUserIds, CompareType.INCLUDE));
				if (orgSet.size() > 0) {
					thisFilter.getFilterItems().add(new FilterItemInfo("shareSellerList.orgUnit.id", orgSet, CompareType.INCLUDE));
					thisFilter.setMaskString("#0 or #1 or #2");
				} else if (marketSet.size() > 0) {
					thisFilter.getFilterItems().add(new FilterItemInfo("shareSellerList.marketingUnit.id", marketSet,CompareType.INCLUDE));
					thisFilter.setMaskString("#0 or #1 or #2");
				} else {
					thisFilter.setMaskString("#0 or #1");
				}
			}

			//????????????????
			FilterInfo addSpFilter = addSeparateBySellProject(node);
			thisFilter.mergeFilter(addSpFilter, "and");			
			
			if (viewInfo.getFilter() != null) {
				thisFilter.mergeFilter(viewInfo.getFilter(), "AND");
			}
			viewInfo.setFilter(thisFilter);
			
		
		} catch (Exception e) {
			this.handleException(e);
		}

		// ????????tia
		CustomerTypeEnum customerType = ((FDCCustomerFullFilterUI) this.getFilterUI()).getCustomerType();
		boolean isEnterCus = CustomerTypeEnum.EnterpriceCustomer.equals(customerType);

		this.tblMain.getColumn("sex").getStyleAttributes().setHided(isEnterCus);
		this.tblMain.getColumn("famillyEarning.name").getStyleAttributes().setHided(isEnterCus);
		this.tblMain.getColumn("employment").getStyleAttributes().setHided(isEnterCus);
		this.tblMain.getColumn("workCompany").getStyleAttributes().setHided(isEnterCus);

		this.tblMain.getColumn("enterpriceProperty").getStyleAttributes().setHided(!isEnterCus);
		this.tblMain.getColumn("enterpriceSize").getStyleAttributes().setHided(!isEnterCus);
		this.tblMain.getColumn("enterpriceHomepage").getStyleAttributes().setHided(!isEnterCus);
		this.tblMain.getColumn("industry.name").getStyleAttributes().setHided(!isEnterCus);

		return super.getQueryExecutor(queryPK, viewInfo);
	}
	
	
	//??????????????????????????????????????????
	private FilterInfo addSeparateBySellProject(DefaultMutableTreeNode thisNode) throws EASBizException, BOSException{
		FilterInfo filter = new FilterInfo();	
		String spFilterString = "";
			if(thisNode==null || thisNode.getUserObject() instanceof OrgStructureInfo){
				spFilterString = CommerceHelper.getPermitProjectIdSql(userInfo);
			}else if(thisNode.getUserObject() instanceof MarketingUnitInfo){//??????????????????????
				MarketingUnitInfo marketingInfo = (MarketingUnitInfo)thisNode.getUserObject();
				spFilterString = "select FSellProjectID from T_TEN_MarketingUnitSellProject where fHeadId ='"+marketingInfo.getId().toString()+"'";				
			}else if(thisNode.getUserObject() instanceof UserInfo){
				UserInfo nodeUser = (UserInfo)thisNode.getUserObject();
				if(!nodeUser.getId().toString().equals(userInfo.getId().toString())) {
					MarketingUnitInfo marketingUnit = (MarketingUnitInfo)((DefaultKingdeeTreeNode)thisNode.getParent()).getUserObject();
					spFilterString = "select FSellProjectID from T_TEN_MarketingUnitSellProject where fHeadId ='"+marketingUnit.getId().toString()+"'";
				}
			}
			
			if(!spFilterString.equals(""))
				filter.getFilterItems().add(new FilterItemInfo("project.id",spFilterString,CompareType.INNER));
			return filter;
	}
	
	

	/**
	 * ????????????????????????
	 * 
	 * @param treeNode
	 */
	private void getAllSaleIds(DefaultMutableTreeNode treeNode) {
		if (treeNode.isLeaf()) { // DefaultMutableTreeNode
			// DefaultKingdeeTreeNode
			DefaultMutableTreeNode thisNode = (DefaultMutableTreeNode) treeNode;
			if (thisNode.getUserObject() instanceof UserInfo) {
				UserInfo user = (UserInfo) thisNode.getUserObject();
				saleUserIds += "," + user.getId().toString();
			}
		} else {
			int childCount = treeNode.getChildCount();
			while (childCount > 0) {
				getAllSaleIds((DefaultMutableTreeNode) treeNode.getChildAt(childCount - 1));
				childCount--;
			}
		}
	}

	protected String getEditUIModal() {
		return UIFactoryName.NEWTAB;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getMarketTree(this.actionOnLoad));
		// this.treeMain.setModel(SHEHelper.getMarketTree(this.actionOnLoad));

		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}

	public void actionCancel_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmDialog("??????????????????????")) {
			FDCCustomerFactory.getRemoteInstance().blankOut(getSelectedIdValues());
			CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
			refresh(e);
		}
	}

	public void actionCancelCancel_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		FDCCustomerFactory.getRemoteInstance().pickUp(getSelectedIdValues());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		refresh(e);
	}

	protected String getQueryUiName() {
		return super.getQueryUiName();
	}

	// protected void checkIsOUSealUp() throws Exception {
	// }

	protected String getEditUIName() {
		return CustomerExtEditUI.class.getName();
	}

	protected ICoreBase getBizInterface() throws Exception {
		return FDCCustomerFactory.getRemoteInstance();
	}

	/**
	 * ????????????????????????????
	 * 
	 * @deprecated ??????????????????
	 */
	protected void chkShowAll_actionPerformed(ActionEvent e) throws Exception {
		super.chkShowAll_actionPerformed(e);
		// if (this.chkShowAll.isSelected()) {
		// // MsgBox.showInfo("????");
		// showAll = true;
		// this.mainQuery = getEntityViewInfo(mainQuery);
		// refreshList();
		// } else {
		// // MsgBox.showInfo("??????");
		// showAll = false;
		// this.mainQuery = getEntityViewInfo(mainQuery);
		// refreshList();
		// }
	}

	/**
	 * ??????????????
	 */
	public void actionImportantTrack_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportantTrack_actionPerformed(e);
		this.checkSelected();
		FDCCustomerFactory.getRemoteInstance().signImportantTrack(getSelectedIdValues());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		refresh(e);
	}

	/**
	 * ??????????????
	 */
	public void actionCancelImportantTrack_actionPerformed(ActionEvent e) throws Exception {
		super.actionCancelImportantTrack_actionPerformed(e);
		this.checkSelected();
		FDCCustomerFactory.getRemoteInstance().cancelImportantTrack(getSelectedIdValues());
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		refresh(e);
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddNew_actionPerformed(e);
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
	}

	/**
	 * ??????????????????
	 */
	public void actionModifyCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.actionModifyCustomer_actionPerformed(e);

		checkSelected();

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		IRow row = this.tblMain.getRow(selectRows[0]);
		String id = (String) row.getCell("id").getValue();

		if (id == null)
			return;

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("customer.id", id));
		// ??????????????????????????????????????,??????????????
		boolean exist = PurchaseCustomerInfoFactory.getRemoteInstance().exists(filter);
		if (exist) {
			MsgBox.showInfo("??????????????????????????????????????");
			return;
		}

		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("ListUI", this);// ??????EditUi????????????ListUI????????????ListUI????????
		uiContext.put("ActionCommand", "modifyName");

		// ????UI??????????
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ModifyCustomerNameUI.class.getName(), uiContext, null, "EDIT");
		uiWindow.show();
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
	}

	public void actionModifyPhone_actionPerformed(ActionEvent e) throws Exception {
		super.actionModifyPhone_actionPerformed(e);

		String idStr = this.getSelectedKeyValue();
		if (idStr == null)
			return;

		UIContext uiContext = new UIContext(ui);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		uiContext.put("ListUI", this);// ??????EditUi????????????ListUI????????????ListUI????????
		uiContext.put("ActionCommand", "modifyPhone");

		// ????UI??????????
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(ModifyCustomerNameUI.class.getName(), uiContext, null, "EDIT");
		uiWindow.show();
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
	}

	/**
	 * ????????
	 * */
	public void actionImportData_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportData_actionPerformed(e);
		UIContext uiContext = new UIContext(ui);

		// ????UI??????????
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(ImportExcelFDCCustomerUI.class.getName(), uiContext, null);
		uiWindow.show();
	}

	public void actionToSysCustomer_actionPerformed(ActionEvent e) throws Exception {
		super.actionToSysCustomer_actionPerformed(e);
		this.checkSelected();
		// String cusId = this.getSelectedKeyValue();
		// ??????????
		// FDCCustomerInfo customer = FDCCustomerFactory.getRemoteInstance()
		// .getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(cusId)));
		// if (customer.getSysCustomer() != null) {
		// MsgBox.showInfo("??????????????????!");
		// return;
		// }

		// ????????????????????,????????????????????
		Map sortStandardMap = setting.getSortStandardMap();
		Iterator it = sortStandardMap.values().iterator();
		List sortStandardList = new ArrayList();
		while (it.hasNext()) {
			CSSPGroupInfo cssInfo = (CSSPGroupInfo) it.next();
			if (cssInfo != null) {
				// ????????
				sortStandardList.add(cssInfo.getId().toString());
			}
		}
		ArrayList selectIds = getSelectedIdValues();
		for (int i = 0; i < selectIds.size(); i++) {
			String cusId = (String) selectIds.get(i);
			FDCCustomerFactory.getRemoteInstance().addToSysCustomer(cusId, sortStandardList);
		}
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
		MsgBox.showInfo("????????!");
	}

	public void actionSwitchTo_actionPerformed(ActionEvent e) throws Exception {
		super.actionSwitchTo_actionPerformed(e);
		this.checkSelected();

		UIContext uiContext = new UIContext(ui);
		uiContext.put("ListUI", this);
		uiContext.put("SelectedIdValues", this.getSelectedIdValues());

		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(CustomerSwitchToEditUI.class.getName(), uiContext, null);
		uiWindow.show();
		CacheServiceFactory.getInstance().discardType(new FDCCustomerInfo().getBOSType());
	}

	/**
	 * ????
	 * 
	 * @throws Exception
	 * */
	public void actionCustomerAdapter_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		
		int selectedRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0;i<selectedRows.length;i++) {
			IRow row = this.tblMain.getRow(selectedRows[i]);
			String custIdStr = (String)row.getCell("id").getValue();
			if(!CustomerAdapterUI.hasAdpaterPermission(custIdStr))  {
				MsgBox.showInfo("??????"+(row.getRowIndex()+1) + "????????????????????????????!");
				return;
			}
		}
		
		List idList = this.getSelectedIdValues();
		CustomerAdapterUI.showUI(this, idList);

		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * ????
	 * 
	 * @throws Exception
	 * */
	public void actionCustomerShare_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		
		int selectedRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0;i<selectedRows.length;i++) {
			IRow row = this.tblMain.getRow(selectedRows[i]);
			String custIdStr = (String)row.getCell("id").getValue();
			if(!CustomerShareUI.hasSharePermission(custIdStr))  {
				MsgBox.showInfo("??????"+(row.getRowIndex()+1) + "????????????????????????????!");
				return;
			}
		}
		
		List idList = this.getSelectedIdValues();
		CustomerShareUI.showUI(this, idList);

		this.actionRefresh_actionPerformed(e);
	}

	/**
	 * ????????
	 * */
	public void actionCustomerCancelShare_actionPerformed(ActionEvent e) throws Exception {
		this.checkSelected();
		
		int selectedRows[] = KDTableUtil.getSelectedRows(this.tblMain);
		for(int i=0;i<selectedRows.length;i++) {
			IRow row = this.tblMain.getRow(selectedRows[i]);
			String custIdStr = (String)row.getCell("id").getValue();
			if(!CustomerShareUI.hasSharePermission(custIdStr))  {
				MsgBox.showInfo("??????"+(row.getRowIndex()+1) + "????????????????????????????????!");
				return;
			}
		}
		
		String customerID = this.getSelectedKeyValue();
		CustomerCancelShareUI.showEditUI(this, customerID);

		this.actionRefresh_actionPerformed(e);
	}

	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof UserInfo) {
			UserInfo userInfo = (UserInfo) node.getUserObject();
			uiContext.put("userInfo", userInfo);
			
			if(((DefaultKingdeeTreeNode) node.getParent()).getUserObject() instanceof MarketingUnitInfo)
			{
			MarketingUnitInfo marketingUnit = (MarketingUnitInfo) ((DefaultKingdeeTreeNode) node.getParent()).getUserObject();
			uiContext.put("marketingUnit", marketingUnit);
			}
		}
		if (node.getUserObject() instanceof MarketingUnitInfo) {
			MarketingUnitInfo marketingUnit = (MarketingUnitInfo) node.getUserObject();
			uiContext.put("marketingUnit", marketingUnit);
		}
//		marketNode = (TreeNode) this.treeMain.getLastSelectedPathComponent();
		marketNode=(TreeNode)this.treeMain.getModel().getRoot();    
     	uiContext.put("MarketingNode",marketNode);
	}

	public void actionAddTrackRecord_actionPerformed(ActionEvent e) throws Exception {
		super.actionAddTrackRecord_actionPerformed(e);
		String selectId = this.getSelectedKeyValue();
		if (selectId == null) {
			MsgBox.showInfo("????????????????????!");
			return;
		}

		int[] selectRows = KDTableUtil.getSelectedRows(this.tblMain);
		String idStr = (String) this.tblMain.getRow(selectRows[0]).getCell("id").getValue();
		if (idStr == null)
			return;

		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("*"));
		selector.add(new SelectorItemInfo("project.*"));
		FDCCustomerInfo customer = FDCCustomerFactory.getRemoteInstance().getFDCCustomerInfo(new ObjectUuidPK(BOSUuid.read(idStr)), selector);
		if (customer != null) {
			// ????????????????????
			UIContext uiContext = new UIContext(this);
			uiContext.put("FdcCustomer", customer);
			uiContext.put("SellProject", customer.getProject());

			if (customer.isIsForSHE())
				uiContext.put("CommerceSysTypeEnum", MoneySysTypeEnum.SalehouseSys);
			else if (customer.isIsForTen())
				uiContext.put("CommerceSysTypeEnum", MoneySysTypeEnum.TenancySys);

			try {
				IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(TrackRecordEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
				uiWindow.show();
			} catch (UIException ee) {
				ee.printStackTrace();
			}
		}

	}

	/**
	 * ?????????? 1.?????????????????????? 2.???????????????????? 3.?????????????????????? 4.??????????????????????
	 * 5.??????????????????????????????????
	 */
	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		List selectedIds = this.getSelectedIdValues();

		Set cusIds = FDCHelper.list2Set(selectedIds);

		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("id", cusIds, CompareType.INCLUDE));
		filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
		if (FDCCustomerFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo(this, "????????????????????????");
			return;
		}

		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("customer.id", cusIds, CompareType.INCLUDE));
		if (SincerityPurchaseFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo(this, "????????????????????????????????");
			return;
		}

		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("customer.id", cusIds, CompareType.INCLUDE));
		if (PurchaseCustomerInfoFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo(this, "????????????????????????????");
			return;
		}

		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("fdcCustomer.id", cusIds, CompareType.INCLUDE));
		if (TenancyCustomerEntryFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo(this, "????????????????????????????");
			return;
		}

		filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("head.id", cusIds, CompareType.INCLUDE));
		if (TrackRecordFactory.getRemoteInstance().exists(filter)) {
			MsgBox.showInfo(this, "??????????????????????????????");
			return;
		}

		if (confirmRemove()) {
			Remove();
		}
	}

	public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
		super.actionEdit_actionPerformed(e);
		
	}
	
	/**
	 * ?????????????? 
	 * @author liang_ren969
	 * @date 2010-6-13 
	 */
	private void fetchInitData(List idList){
		
		//????query????????id????
		//List idList = super.getQueryPkList();
		
		if(idList!=null && idList.size()>0){
			try {
				IFDCCustomer customer = FDCCustomerFactory.getRemoteInstance();
				customer.setStatus(idList);
			} catch (BOSException e) {
				logger.equals(e.getMessage());
			}finally{
				this.tblMain.removeRows();
				this.execQuery();
			}
			
		}

	}
	
	/**
	 * ??????????????refresh????
	 * @author liang_ren969
	 * @date 2010-6-21
	 */
	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		//fetchInitData(idList);	??????????????????????????????
		super.actionRefresh_actionPerformed(e);
	}
}