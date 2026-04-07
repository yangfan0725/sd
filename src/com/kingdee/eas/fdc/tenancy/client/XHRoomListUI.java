/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.event.*;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.fdc.basecrm.client.FDCSysContext;
import com.kingdee.eas.fdc.basedata.FDCDataBaseInfo;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.client.FDCTreeHelper;
import com.kingdee.eas.fdc.tenancy.XHCustomerFactory;
import com.kingdee.eas.fdc.tenancy.XHCustomerInfo;
import com.kingdee.eas.fdc.tenancy.XHRoomFactory;
import com.kingdee.eas.fdc.tenancy.XHRoomInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.util.FilterUtility;

/**
 * output class name
 */
public class XHRoomListUI extends AbstractXHRoomListUI
{
    private static final Logger logger = CoreUIObject.getLogger(XHRoomListUI.class);
    
    /**
     * output class constructor
     */
    public XHRoomListUI() throws Exception
    {
        super();
    }
    protected FDCDataBaseInfo getBaseDataInfo() {
		return new XHRoomInfo();
	}
	protected ICoreBase getBizInterface() throws Exception {
		return XHRoomFactory.getRemoteInstance();
	}
	protected String getEditUIName() {
		return XHRoomEditUI.class.getName();
	}
    protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void refresh(ActionEvent e) throws Exception {
		this.tblMain.removeRows();
	}
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		ItemAction act = getActionFromActionEvent(e);
		if (act.equals(actionAddNew) || act.equals(actionEdit)
				|| (act.equals(actionView))) {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (node != null) {
				if (node.getUserObject() instanceof SellProjectInfo) {
					uiContext.put("sellProject", node.getUserObject());
				}
			}
		}
	}
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
			if(node.isLeaf()){
				this.actionAddNew.setEnabled(true);
			}else{
				this.actionAddNew.setEnabled(false);
			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			this.actionAddNew.setEnabled(false);
		} else {
			this.actionAddNew.setEnabled(false);
		}
		this.refreshList();
	}
	public void onLoad() throws Exception {
		super.onLoad();
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTreeForSHE(this.actionOnLoad,MoneySysTypeEnum.SalehouseSys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		this.treeMain.setSelectionRow(0);
		
		this.tblMain.getColumn("area").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
    	this.tblMain.getColumn("area").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	}
    protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
    	FilterInfo filter = new FilterInfo();
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain
				.getLastSelectedPathComponent();
		if (node == null) {
			return super.getQueryExecutor(queryPK, viewInfo);
		}
		if (node.getUserObject() instanceof SellProjectInfo) {
//			if (FDCSysContext.getInstance().checkIsSHEOrg()) {
			Map sellProMap = FDCTreeHelper.getAllObjectIdMap(node,"SellProject");
			Iterator iter = sellProMap.keySet().iterator();
			Set sellProIdSet = new HashSet();
			while (iter.hasNext())
				sellProIdSet.add(iter.next());
			if (sellProIdSet.size() > 0) {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProIdSet,
								CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null));
			}
//			}
		} else if (node.getUserObject() instanceof OrgStructureInfo) {
			Map sellProMap = FDCTreeHelper.getAllObjectIdMap(node,
					"SellProject");
			Iterator iter = sellProMap.keySet().iterator();
			Set sellProIdSet = new HashSet();
			while (iter.hasNext())
				sellProIdSet.add(iter.next());
			if (sellProIdSet.size() > 0) {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", sellProIdSet,
								CompareType.INCLUDE));
			} else {
				filter.getFilterItems().add(
						new FilterItemInfo("sellProject.id", null));
			}
		}

		if (FilterUtility.hasFilterItem(filter)) {
			try {
				filter.mergeFilter(viewInfo.getFilter(), "and");
				EntityViewInfo evi = (EntityViewInfo) viewInfo.clone();
				evi.setFilter(filter);
				return super.getQueryExecutor(queryPK, evi);
			} catch (BOSException e) {
				e.printStackTrace();
				return super.getQueryExecutor(queryPK, viewInfo);
			}
		} else {
			return super.getQueryExecutor(queryPK, viewInfo);
		}
	}

}