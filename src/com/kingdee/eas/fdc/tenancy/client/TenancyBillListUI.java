/**
 * output package name
 */
package com.kingdee.eas.fdc.tenancy.client;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.export.ExportManager;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBook;
import com.kingdee.bos.ctrl.kdf.export.KDTables2KDSBookVO;
import com.kingdee.bos.ctrl.kdf.kds.KDSBook;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTMenuManager;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTDataRequestEvent;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFileChooser;
import com.kingdee.bos.ctrl.swing.KDMenuItem;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.dao.query.IQueryExecutor;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.MetaDataPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.attachment.common.AttachmentClientManager;
import com.kingdee.eas.base.attachment.common.AttachmentManagerFactory;
import com.kingdee.eas.base.commonquery.QuerySolutionInfo;
import com.kingdee.eas.base.commonquery.UserPreferenceData;
import com.kingdee.eas.base.permission.client.longtime.ILongTimeTask;
import com.kingdee.eas.base.uiframe.client.UIFactoryHelper;
import com.kingdee.eas.basedata.org.SaleOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basecrm.CRMHelper;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillCollection;
import com.kingdee.eas.fdc.basecrm.FDCReceivingBillFactory;
import com.kingdee.eas.fdc.basecrm.RevBillTypeEnum;
import com.kingdee.eas.fdc.basecrm.RevBizTypeEnum;
import com.kingdee.eas.fdc.basecrm.client.FDCReceivingBillEditUI;
import com.kingdee.eas.fdc.basedata.FDCConstants;
import com.kingdee.eas.fdc.basedata.FDCDateHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientUtils;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.FDCUtils;
import com.kingdee.eas.fdc.contract.client.ContractClientUtils;
import com.kingdee.eas.fdc.invite.InviteTypeInfo;
import com.kingdee.eas.fdc.sellhouse.BaseTransactionInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionStateEnum;
import com.kingdee.eas.fdc.sellhouse.client.PrePurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.PurchaseManageEditUI;
import com.kingdee.eas.fdc.sellhouse.client.SHEHelper;
import com.kingdee.eas.fdc.sellhouse.client.SignManageEditUI;
import com.kingdee.eas.fdc.tenancy.DepositDealBillFactory;
import com.kingdee.eas.fdc.tenancy.HandleStateEnum;
import com.kingdee.eas.fdc.tenancy.OtherBillFactory;
import com.kingdee.eas.fdc.tenancy.QuitTenancyFactory;
import com.kingdee.eas.fdc.tenancy.RentStartTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenBillOtherPayCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.TenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.TenancyBillInfo;
import com.kingdee.eas.fdc.tenancy.TenancyBillStateEnum;
import com.kingdee.eas.fdc.tenancy.TenancyContractTypeEnum;
import com.kingdee.eas.fdc.tenancy.TenancyHelper;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyRoomEntryInfo;
import com.kingdee.eas.fdc.tenancy.TenancyRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyXHRoomPayListEntryCollection;
import com.kingdee.eas.fdc.tenancy.TenancyXHRoomPayListEntryInfo;
import com.kingdee.eas.fdc.tenancy.XHTenancyBill;
import com.kingdee.eas.fdc.tenancy.XHTenancyBillCollection;
import com.kingdee.eas.fdc.tenancy.XHTenancyBillFactory;
import com.kingdee.eas.fdc.tenancy.XHTenancyBillInfo;
import com.kingdee.eas.ma.budget.client.LongTimeDialog;
import com.kingdee.eas.tools.datatask.DatataskMode;
import com.kingdee.eas.tools.datatask.DatataskParameter;
import com.kingdee.eas.tools.datatask.client.DatataskCaller;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.StringUtils;

public class TenancyBillListUI extends AbstractTenancyBillListUI
{
    private static final Logger logger = CoreUIObject.getLogger(TenancyBillListUI.class);
    
	public void actionImport_actionPerformed(ActionEvent e) throws Exception {
		String strSolutionName ="eas.fdc.tenancy.TenancyBill";
		DatataskCaller task = new DatataskCaller();
		task.setParentComponent(this);
		DatataskParameter param = new DatataskParameter();
		String solutionName = strSolutionName;
		param.solutionName = solutionName;
		ArrayList paramList = new ArrayList();
		paramList.add(param);
		task.invoke(paramList, DatataskMode.UPDATE, true);
	}
	public void actionImportSql_actionPerformed(ActionEvent e) throws Exception {
		super.actionImportSql_actionPerformed(e);
		if(TenancyImport.tenancyUpdata()){
			MsgBox.showInfo("修复导入合同操作已成功");
		}else{
			MsgBox.showInfo("没有要修复的导入合同");
		}
		super.actionRefresh_actionPerformed(e);
	}

	public TenancyBillListUI() throws Exception
    {
        super();
    }
    
    protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null) {
			return;
		}
		SaleOrgUnitInfo saleOrg = SHEHelper.getCurrentSaleOrg();
		if(!saleOrg.isIsBizUnit())
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(false);
			this.actionRemove.setEnabled(false);
			this.actionAudit.setEnabled(false);
			this.actionUnAudit.setEnabled(false);
			this.actionHandleTenancy.setEnabled(false);
			this.actionReceiveBill.setEnabled(false);
			this.actionRefundment.setEnabled(false);
			this.actionRepairStartDate.setEnabled(false);
			this.btnSpecial.setEnabled(false);
		}else
		{
			this.actionAddNew.setEnabled(false);
			this.actionEdit.setEnabled(true);
			this.actionRemove.setEnabled(true);
			this.actionAudit.setEnabled(true);
			this.actionUnAudit.setEnabled(true);
			this.actionHandleTenancy.setEnabled(true);
			this.actionReceiveBill.setEnabled(true);
			this.actionRefundment.setEnabled(true);
			this.actionRepairStartDate.setEnabled(true);
			this.btnSpecial.setEnabled(true);
			if(node.getUserObject() instanceof SellProjectInfo)
			{
				this.actionAddNew.setEnabled(true);				
			}
		}
		this.execQuery();
	}
	protected IQueryExecutor getQueryExecutor(IMetaDataPK queryPK, EntityViewInfo viewInfo) {
		try {
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			viewInfo = (EntityViewInfo) this.mainQuery.clone();

			FilterInfo filter = new FilterInfo();
			if (node != null  &&  node.getUserObject() instanceof SellProjectInfo) {
				SellProjectInfo pro = (SellProjectInfo) node.getUserObject();
				filter.getFilterItems().add(new FilterItemInfo("sellProject.id", pro.getId().toString()));
			} else {
				filter.getFilterItems().add(new FilterItemInfo("id", null));
			}

			if (viewInfo.getFilter() != null) {
				viewInfo.getFilter().mergeFilter(filter, "and");
			} else {
				viewInfo.setFilter(filter);
			}
		} catch (Exception e) {
			handleException(e);
		}

		return super.getQueryExecutor(queryPK, viewInfo);
	}
    
	protected void initTree() throws Exception {
		this.treeMain.setModel(SHEHelper.getSellProjectTree(this.actionOnLoad,MoneySysTypeEnum.TenancySys));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
		
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_ROW_SELECT);
		
		setColumnNumberFormat("leaseCount");
		this.tblMain.getColumn("leaseTime").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		setColumnNumberFormat("dealTotalRent");
		setColumnNumberFormat("standardTotalRent");
		
		setColumnNumberFormat("depositAmount");
		setColumnNumberFormat("firstPayRent");
		setColumnNumberFormat("buildingArea");
//		this.tblMain.getColumn("tenancyRoomList.dayPrice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("tenancyRoomList.dayPrice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
//		this.tblMain.getColumn("tenancyRoomList.actDayprice").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
//		this.tblMain.getColumn("tenancyRoomList.actDayprice").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	}
    
	protected void prepareUIContext(UIContext uiContext, ActionEvent e) {
		super.prepareUIContext(uiContext, e);
		uiContext.put(UIContext.ID, getSelectedKeyValue());
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node.getUserObject() instanceof SellProjectInfo) {
			SellProjectInfo sellProject = (SellProjectInfo) node.getUserObject();
			uiContext.put("sellProject", sellProject);
		}
	}
	
	/**
	 * 设置数字金额列的格式:右对齐,2位小数
	 * */
    private void setColumnNumberFormat(String colKey) {
    	this.tblMain.getColumn(colKey).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn(colKey).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
	}

   
	public void onLoad() throws Exception {
		this.menuSpecial.setIcon(EASResource.getIcon("imgTbtn_disassemble"));
		this.kDWorkButton1.setIcon(EASResource.getIcon("imgTbtn_input"));//导出模板导入数据
		this.kDWorkButton2.setIcon(EASResource.getIcon("imgTbtn_emend"));//修改导入数据
		KDMenuItem menuItem1 = new KDMenuItem();
		menuItem1.setAction(this.actionContinueTenancy);
		menuItem1.setText("续租申请");
		menuItem1.setIcon(EASResource.getIcon("imgTbtn_releasebymdanduser"));
		this.btnSpecial.addAssistMenuItem(menuItem1);

//		KDMenuItem menuItem4 = new KDMenuItem();
//		menuItem4.setAction(this.actionRejiggerTenancy);
//		menuItem4.setText("改租申请");
//		menuItem4.setIcon(EASResource.getIcon("imgTbtn_distributeuser"));
//		this.btnSpecial.addAssistMenuItem(menuItem4);
//
//		KDMenuItem menuItem2 = new KDMenuItem();
//		menuItem2.setAction(this.actionChangeName);
//		menuItem2.setText("转名申请");
//		menuItem2.setIcon(EASResource.getIcon("imgTbtn_persondistribute"));
//		this.btnSpecial.addAssistMenuItem(menuItem2);
		
//		KDMenuItem menuItem5 = new KDMenuItem();
//		menuItem5.setAction(this.actionPriceChange);
//		menuItem5.setText("价格变更");
//		menuItem5.setIcon(EASResource.getIcon("imgTbtn_assistantlistaccount"));
//		this.btnSpecial.addAssistMenuItem(menuItem5);

		KDMenuItem menuItem3 = new KDMenuItem();
		menuItem3.setAction(this.actionQuitTenancy);
		menuItem3.setText("退租申请");
		menuItem3.setIcon(EASResource.getIcon("imgTbtn_logoutuser"));
		this.btnSpecial.addAssistMenuItem(menuItem3);
    	super.onLoad();
//    	tblMain.getGroupManager().setGroup(true);
//		tblMain.getMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
//		tblMain.getMergeManager().setViewMode(KDTMergeManager.VIEW_AS_INDENTATION);
//		tblMain.getColumn("tenancyType").setMergeable(false);
//		tblMain.getColumn("tenancyState").setMergeable(false);
//		tblMain.getColumn("tenAttachesDes").setMergeable(false);
//		tblMain.getColumn("flagAtTerm").setMergeable(false);
//		tblMain.getColumn("tenancyAdviser.name").setMergeable(false);
//		
//		tblMain.getColumn("tenancyRoomList.dayprice").setMergeable(false);
//		tblMain.getColumn("tenancyRoomList.actDayprice").setMergeable(false);
    	initTree();
    	this.treeMain.setSelectionRow(0);
    	
//    	this.actionAttachment.setVisible(false);
    	this.actionAttachment.setVisible(true);
    	this.actionCreateTo.setVisible(false);
    	this.actionCopyTo.setVisible(false);
    	this.actionTraceUp.setVisible(false);
    	this.actionTraceDown.setVisible(false);
    	this.actionAuditResult.setVisible(true);
    	this.actionWorkFlowG.setVisible(true);   	
    	
    	this.actionUnAudit.setVisible(true);
    	this.actionFlagAtTerm.setVisible(false);
    	this.actionUnAudit.setEnabled(true);
    	this.actionCarryForward.setVisible(false);
    	this.actionBlankOut.setVisible(false);
    	this.actionRepairStartDate.setEnabled(true);
    	this.actionPrintBill.setVisible(true);
    	this.actionPrintBill.setEnabled(true);
    	this.actionPrintPreviewBill.setVisible(true);
    	this.actionPrintPreviewBill.setEnabled(true);
    	 
    	this.tblMain.getColumn("firstPayRent").getStyleAttributes().setHided(true);
    	this.tblMain.getColumn("leaseCount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		this.tblMain.getColumn("leaseCount").getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(0));
    	JButton tblPrintBill = this.toolBar.add(this.actionPrintBill);
		tblPrintBill.setIcon(this.btnPrint.getIcon());
		JButton tblPrintPreviewBill = this.toolBar.add(this.actionPrintPreviewBill);
		tblPrintPreviewBill.setIcon(this.btnPrintPreview.getIcon());
		this.actionImport.setVisible(true);
		this.actionImportSql.setVisible(true);
		
		
		this.actionPrintBill.setVisible(false);
		this.actionPrintPreviewBill.setVisible(false);
		this.actionReceiveBill.setVisible(false);
		this.actionRefundment.setVisible(false);
		this.actionRepairStartDate.setVisible(false);
		
		KDWorkButton btnMultiSubmit=new KDWorkButton();
		btnMultiSubmit.setText("批量提交");
		btnMultiSubmit.setIcon(EASResource.getIcon("imgTbtn_submit"));
		this.toolBar.add(btnMultiSubmit);
		btnMultiSubmit.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
	                beforeActionPerformed(e);
	                try {
	                	btnMultiSubmit_actionPerformed(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                    afterActionPerformed(e);
	                }
	            }
	        });
		if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("900002")){
			KDWorkButton btnMultiExport=new KDWorkButton();
			btnMultiExport.setText("导出excel");
			btnMultiExport.setIcon(this.kDWorkButton1.getIcon());
			this.toolBar.add(btnMultiExport);
			btnMultiExport.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnMultiExport_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
			
			
			KDWorkButton btnXHTenancyBill=new KDWorkButton();
			btnXHTenancyBill.setText("生成星瀚合同");
			btnXHTenancyBill.setIcon(this.kDWorkButton1.getIcon());
			this.toolBar.add(btnXHTenancyBill);
			btnXHTenancyBill.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
		                beforeActionPerformed(e);
		                try {
		                	btnXHTenancyBill_actionPerformed(e);
		                } catch (Exception exc) {
		                    handUIException(exc);
		                } finally {
		                    afterActionPerformed(e);
		                }
		            }
		        });
		}
	}
	public void btnXHTenancyBill_actionPerformed(ActionEvent e) throws Exception {
		for(int i=0;i<this.tblMain.getRowCount();i++){
			String id=this.tblMain.getRow(i).getCell("id").getValue().toString();
			EntityViewInfo view=new EntityViewInfo();
			FilterInfo filter=new FilterInfo();
			filter.getFilterItems().add(new FilterItemInfo("id",id));
			view.setFilter(filter);
			SelectorItemCollection sic=new SelectorItemCollection();
			sic.add("*");
			sic.add("CU.*");
			sic.add("sellProject.*");
			sic.add("tenCustomerList.*");
			sic.add("tenCustomerList.fdcCustomer.*");
			sic.add("tenancyRoomList.*");
			sic.add("tenancyRoomList.room.*");
			sic.add("tenancyRoomList.roomPayList.*");
			sic.add("tenancyRoomList.roomPayList.moneyDefine.*");
			sic.add("rentFrees.*");
			sic.add("increasedRents.*");
			sic.add("otherPayList.*");
			sic.add("otherPayList.moneyDefine.*");
			view.setSelector(sic);
			TenancyBillCollection col=TenancyBillFactory.getRemoteInstance().getTenancyBillCollection(view);
			if(col.size()>0){
				TenancyBillInfo ten=col.get(0);
				
				sic=new SelectorItemCollection();
				sic.add("*");
				sic.add("CU.*");
				sic.add("room.*");
				sic.add("customer.*");
				sic.add("xhCustomer.*");
				sic.add("xhRoomEntry.*");
				sic.add("xhRoomEntry.xhRoom.*");
				sic.add("payListEntry.*");
				sic.add("payListEntry.moneyDefine.*");
				sic.add("tenancyBill.*");
				sic.add("creator.*");
				sic.add("auditor.*");
				view=new EntityViewInfo();
				filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",id));
				view.setFilter(filter);
				view.setSelector(sic);
				
				XHTenancyBillCollection xhCol=XHTenancyBillFactory.getRemoteInstance().getXHTenancyBillCollection(view);
				XHTenancyBillInfo xhInfo=new XHTenancyBillInfo();
				if(xhCol.size()>0){
					xhInfo=xhCol.get(0);
				}
				
				xhInfo.setTenancyBill(ten);
				xhInfo.setNumber(ten.getNumber());
				xhInfo.setName(ten.getId().toString());
//				xhInfo.setTenancyName(ten.getTenancyName());
				xhInfo.setTenancyType(ten.getTenancyType());
				xhInfo.setTenancyDate(ten.getTenancyDate());
				xhInfo.setTenancyState(ten.getTenancyState());
				xhInfo.setQuitRoomDate(ten.getQuitRoomDate());
				xhInfo.setRoom(ten.getTenancyRoomList().get(0).getRoom());
				xhInfo.setCustomer(ten.getTenCustomerList().get(0).getFdcCustomer());
				xhInfo.setStartDate(ten.getStartDate());
				xhInfo.setEndDate(ten.getEndDate());
				xhInfo.setLeaseCount(ten.getLeaseCount());
				
				xhInfo.setCreateTime(ten.getCreateTime());
				xhInfo.setAuditTime(ten.getAuditTime());
				xhInfo.setCreator(ten.getCreator());
				xhInfo.setAuditor(ten.getAuditor());
				xhInfo.setDescription(ten.getDescription());
				
				xhInfo.getPayListEntry().clear();
				TenancyRoomPayListEntryCollection entryCol=ten.getTenancyRoomList().get(0).getRoomPayList();
				CRMHelper.sortCollection(entryCol, "leaseSeq", true);
				boolean ishaszj=false;
				for(int k=0;k<entryCol.size();k++){
					if(entryCol.get(k).getMoneyDefine().getName().equals("租金")||entryCol.get(k).getMoneyDefine().getName().equals("租赁保证金")
							||entryCol.get(k).getMoneyDefine().getName().indexOf("物业管理费")>=0){
						if(entryCol.get(k).getAppAmount().compareTo(FDCHelper.ZERO)<=0
								&&entryCol.get(k).getAllRemainAmount().compareTo(FDCHelper.ZERO)<=0){
							continue;
						}
						if(entryCol.get(k).getMoneyDefine().getName().equals("租金")&&entryCol.get(k).getAppAmount().compareTo(FDCHelper.ZERO)>0){
							if(xhInfo.getXhCustomer()!=null){
								xhInfo.setTenancyName(xhInfo.getXhCustomer().getName()+"租赁合同（"+ten.getTenancyDate()+"）");
							}else{
								xhInfo.setTenancyName(ten.getTenCustomerList().get(0).getFdcCustomer().getName()+"租赁合同（"+ten.getTenancyDate()+"）");
							}
							ishaszj=true;
						}
						TenancyXHRoomPayListEntryInfo entry=new TenancyXHRoomPayListEntryInfo();
						entry.setLeaseSeq(entryCol.get(k).getLeaseSeq());
						entry.setMoneyDefine(entryCol.get(k).getMoneyDefine());
						entry.setStartDate(entryCol.get(k).getStartDate());
						entry.setEndDate(entryCol.get(k).getEndDate());
						entry.setAppDate(entryCol.get(k).getAppDate());
						entry.setAppAmount(entryCol.get(k).getAppAmount());
						entry.setActRevAmount(entryCol.get(k).getActRevAmount());
						entry.setActRevDate(entryCol.get(k).getActRevDate());
						entry.setHasRefundmentAmount(entryCol.get(k).getHasRefundmentAmount());
						xhInfo.getPayListEntry().add(entry);
					}
				}
				if(!ishaszj){
					if(xhInfo.getXhCustomer()!=null){
						xhInfo.setTenancyName(xhInfo.getXhCustomer().getName()+"物业合同（"+ten.getTenancyDate()+"）");
					}else{
						xhInfo.setTenancyName(ten.getTenCustomerList().get(0).getFdcCustomer().getName()+"物业合同（"+ten.getTenancyDate()+"）");
					}
				}
				
				TenBillOtherPayCollection otherentryCol=ten.getOtherPayList();
				CRMHelper.sortCollection(otherentryCol, "leaseSeq", true);
				for(int k=0;k<otherentryCol.size();k++){
					if(otherentryCol.get(k).getMoneyDefine().getName().equals("物业费")||
							otherentryCol.get(k).getMoneyDefine().getName().equals("经营租赁")||
							otherentryCol.get(k).getMoneyDefine().getName().equals("电费")||
							otherentryCol.get(k).getMoneyDefine().getName().equals("水费")){
						if(otherentryCol.get(k).getAppAmount().compareTo(FDCHelper.ZERO)<=0
								&&otherentryCol.get(k).getAllRemainAmount().compareTo(FDCHelper.ZERO)<=0){
							continue;
						}
						TenancyXHRoomPayListEntryInfo entry=new TenancyXHRoomPayListEntryInfo();
						entry.setLeaseSeq(otherentryCol.get(k).getLeaseSeq());
						entry.setMoneyDefine(otherentryCol.get(k).getMoneyDefine());
						entry.setStartDate(otherentryCol.get(k).getStartDate());
						entry.setEndDate(otherentryCol.get(k).getEndDate());
						entry.setAppDate(otherentryCol.get(k).getAppDate());
						entry.setAppAmount(otherentryCol.get(k).getAppAmount());
						entry.setActRevAmount(otherentryCol.get(k).getActRevAmount());
						entry.setActRevDate(otherentryCol.get(k).getActRevDate());
						entry.setHasRefundmentAmount(otherentryCol.get(k).getHasRefundmentAmount());
						xhInfo.getPayListEntry().add(entry);
					}
				}
				if(ten.getRentFrees().size()>0){
					String freeremark="EAS数据迁移，";
					for(int j=0;j<ten.getRentFrees().size();j++){
						freeremark=freeremark+"免租开始日期（"+ten.getRentFrees().get(j).getFreeStartDate()+"），免租结束日期（"+ten.getRentFrees().get(j).getFreeEndDate()+"），免租类型（"+ten.getRentFrees().get(j).getFreeTenancyType().getAlias()+"）；";
					}
					xhInfo.setFreeRemark(freeremark);
				}else{
					xhInfo.setFreeRemark(null);
				}
				if(ten.getIncreasedRents().size()>0){
					String increasedremark="EAS数据迁移，";
					for(int j=0;j<ten.getIncreasedRents().size();j++){
						increasedremark=increasedremark+"递增日期（"+ten.getIncreasedRents().get(j).getIncreaseDate()+"），递增方式（"+ten.getIncreasedRents().get(j).getIncreaseType().getAlias()+"），递增类型（"+ten.getIncreasedRents().get(j).getIncreaseStyle().getAlias()+"），值（"+ten.getIncreasedRents().get(j).getValue()+"）；";
					}
					xhInfo.setIncreasedRemark(increasedremark);
				}else{
					xhInfo.setIncreasedRemark(null);
				}
				XHTenancyBillFactory.getRemoteInstance().save(xhInfo);
			}
		}
		MsgBox.showInfo("生成成功！");
	}
	public void btnMultiExport_actionPerformed(ActionEvent e) throws Exception {
		Set idSet=new HashSet();
		for(int i=0;i<this.tblMain.getRowCount();i++){
			String id=this.tblMain.getRow(i).getCell("id").getValue().toString();
			Boolean isUpdateXHTenancyBill=(Boolean) this.tblMain.getRow(i).getCell("isUpdateXHTenancyBill").getValue();
			if(isUpdateXHTenancyBill){
				idSet.add(id);
			}
		}
		SelectorItemCollection sic=new SelectorItemCollection();
		sic.add("*");
		sic.add("CU.*");
		sic.add("room.*");
		sic.add("customer.*");
		sic.add("xhCustomer.*");
		sic.add("xhRoomEntry.*");
		sic.add("xhRoomEntry.xhRoom.*");
		sic.add("payListEntry.*");
		sic.add("payListEntry.moneyDefine.*");
		sic.add("tenancyBill.*");
		sic.add("creator.*");
		sic.add("auditor.*");
		sic.add("tenancyBill.*");
		sic.add("tenancyBill.sellProject.*");
		sic.add("tenancyBill.CU.*");
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("tenancyBill.id",idSet,CompareType.INCLUDE));
		view.setFilter(filter);
		view.setSelector(sic);
		SorterItemCollection sort=new SorterItemCollection();
		sort.add(new SorterItemInfo("number"));
		view.setSorter(sort);
		XHTenancyBillCollection xhCol=XHTenancyBillFactory.getRemoteInstance().getXHTenancyBillCollection(view);
		
		Map kdtEntrys=new HashMap();
		KDTable table1=new KDTable();
		IColumn column=table1.addColumn();
		column.setKey("id");
		
		column=table1.addColumn();
		column.setKey("number");
		
		column=table1.addColumn();
		column.setKey("sellProject.name");
		
		column=table1.addColumn();
		column.setKey("name");
		
		column=table1.addColumn();
		column.setKey("field1");
		
		column=table1.addColumn();
		column.setKey("bizDate");
		
		column=table1.addColumn();
		column.setKey("field2");
		
		column=table1.addColumn();
		column.setKey("startDate");
		
		column=table1.addColumn();
		column.setKey("endDate");
		
		column=table1.addColumn();
		column.setKey("field3");
		
		column=table1.addColumn();
		column.setKey("field4");
		
		column=table1.addColumn();
		column.setKey("field5");
		
		IRow headRow=table1.addHeadRow();
		headRow.getCell("id").setValue("匹配id");
		headRow.getCell("number").setValue("合同编码");
		headRow.getCell("sellProject.name").setValue("所属项目.项目名称");
		headRow.getCell("name").setValue("合同名称.简体中文");
		headRow.getCell("field1").setValue("合同名称.English");
		headRow.getCell("bizDate").setValue("签订日期");
		headRow.getCell("field2").setValue("经营模式");
		headRow.getCell("startDate").setValue("合同开始日期");
		headRow.getCell("endDate").setValue("合同结束日期");
		headRow.getCell("field3").setValue("租赁模式");
		headRow.getCell("field4").setValue("临时编码");
		headRow.getCell("field5").setValue("备注");
		
		kdtEntrys.put("1基本信息分录", table1);
		
		KDTable table2=new KDTable();
		
		column=table2.addColumn();
		column.setKey("id");
		
		column=table2.addColumn();
		column.setKey("field1");
		
		column=table2.addColumn();
		column.setKey("field2");
		
		column=table2.addColumn();
		column.setKey("field3");
		
		column=table2.addColumn();
		column.setKey("customer.number");
		
		column=table2.addColumn();
		column.setKey("number");
		
		headRow=table2.addHeadRow();
		headRow.getCell("id").setValue("匹配id");
		headRow.getCell("field1").setValue("甲方类型");
		headRow.getCell("field2").setValue("乙方类型");
		headRow.getCell("field3").setValue("甲方.编码");
		headRow.getCell("customer.number").setValue("乙方.编码");
		headRow.getCell("number").setValue("临时编码");
		
		kdtEntrys.put("2合同主体分录", table2);
		
		KDTable table3=new KDTable();
			
		column=table3.addColumn();
		column.setKey("id");
		
		column=table3.addColumn();
		column.setKey("field1");
		
		column=table3.addColumn();
		column.setKey("sellProject.name");
		
		column=table3.addColumn();
		column.setKey("field2");
		
		column=table3.addColumn();
		column.setKey("room.number");
			
		column=table3.addColumn();
		column.setKey("number");
		
		
		headRow=table3.addHeadRow();
		headRow.getCell("id").setValue("匹配id");
		headRow.getCell("field1").setValue("资源分类.编码");
		headRow.getCell("sellProject.name").setValue("项目.项目名称");
		headRow.getCell("field2").setValue("主数据资源.编码");
		headRow.getCell("room.number").setValue("资源.资源编码");
		headRow.getCell("number").setValue("临时编码");
		
		kdtEntrys.put("3合同资源分录", table3);
		
		KDTable table4=new KDTable();
		for(int i=1;i<17;i++){
			column=table4.addColumn();
			column.setKey("field"+i);
		}
		
		headRow=table4.addHeadRow();
		headRow.getCell("field1").setValue("匹配id");
		headRow.getCell("field2").setValue("收费项目.编码");
		headRow.getCell("field3").setValue("收费方式");
		headRow.getCell("field4").setValue("周期形式");
		headRow.getCell("field5").setValue("每x一期");
		headRow.getCell("field6").setValue("收费日期");
		headRow.getCell("field7").setValue("x天");
		headRow.getCell("field8").setValue("原价格单位");
		headRow.getCell("field9").setValue("计费依据");
		headRow.getCell("field10").setValue("不足月按天");
		headRow.getCell("field11").setValue("是否租金");
		headRow.getCell("field12").setValue("是否押金");
		headRow.getCell("field13").setValue("计费开始日期");
		headRow.getCell("field14").setValue("计费结束日期");
		headRow.getCell("field15").setValue("含税价格");
		headRow.getCell("field16").setValue("临时编码");
		
		kdtEntrys.put("4商务条款分录", table4);
		
		KDTable table5=new KDTable();
		column=table5.addColumn();
		column.setKey("field1");
		
		headRow=table5.addHeadRow();
		headRow.getCell("field1").setValue("临时编码");
		
		kdtEntrys.put("5附件分录", table5);
		
		
		KDTable table6=new KDTable();
		column=table6.addColumn();
		column.setKey("id");
		
		column=table6.addColumn();
		column.setKey("appDate");
		
		column=table6.addColumn();
		column.setKey("field1");
		
		column=table6.addColumn();
		column.setKey("field2");
		
		column=table6.addColumn();
		column.setKey("field3");
		
		column=table6.addColumn();
		column.setKey("field4");
		
		column=table6.addColumn();
		column.setKey("field5");
		
		column=table6.addColumn();
		column.setKey("field6");
		
		column=table6.addColumn();
		column.setKey("number");
		
		column=table6.addColumn();
		column.setKey("name");
		
		column=table6.addColumn();
		column.setKey("field7");
		
		column=table6.addColumn();
		column.setKey("field8");
		
		column=table6.addColumn();
		column.setKey("moneyDefine.number");
		
		column=table6.addColumn();
		column.setKey("moneyDefine.name");
		
		column=table6.addColumn();
		column.setKey("field9");
		
		column=table6.addColumn();
		column.setKey("field10");
		
		column=table6.addColumn();
		column.setKey("startDate");
		
		column=table6.addColumn();
		column.setKey("endDate");
		
		column=table6.addColumn();
		column.setKey("appAmount");
		
		column=table6.addColumn();
		column.setKey("actRevAmount");
		
		column=table6.addColumn();
		column.setKey("actRevDate");
		
		column=table6.addColumn();
		column.setKey("field11");
		
		column=table6.addColumn();
		column.setKey("field12");
		
		column=table6.addColumn();
		column.setKey("field13");
		
		column=table6.addColumn();
		column.setKey("field14");
		
		column=table6.addColumn();
		column.setKey("field15");
		
		column=table6.addColumn();
		column.setKey("field16");

		headRow=table6.addHeadRow();
		headRow.getCell("id").setValue("内码");
		headRow.getCell("appDate").setValue("应收日期");
		headRow.getCell("field1").setValue("组织.编码");
		headRow.getCell("field2").setValue("组织.名称");
		headRow.getCell("field3").setValue("周期.编码");
		headRow.getCell("field4").setValue("周期.名称");
		headRow.getCell("field5").setValue("项目.项目编码");
		headRow.getCell("field6").setValue("项目.项目名称");
		
		headRow.getCell("number").setValue("合同.合同编码");
		headRow.getCell("name").setValue("合同.名称");
		headRow.getCell("field7").setValue("租赁产品.编码");
		headRow.getCell("field8").setValue("租赁产品.名称");
		headRow.getCell("moneyDefine.number").setValue("收费项目.编码");
		headRow.getCell("moneyDefine.name").setValue("收费项目.名称");
		
		headRow.getCell("field9").setValue("乙方.客户编码");
		headRow.getCell("field10").setValue("乙方.客户名称");
		
		headRow.getCell("startDate").setValue("开始日期");
		headRow.getCell("endDate").setValue("结束日期");
		headRow.getCell("appAmount").setValue("应收金额（含税）");
		headRow.getCell("actRevAmount").setValue("实收金额");
		headRow.getCell("actRevDate").setValue("实收日期");
		
		headRow.getCell("field11").setValue("收费应收明细ID");
		headRow.getCell("field12").setValue("权益方.编码");
		headRow.getCell("field13").setValue("权益方.名称");
		headRow.getCell("field14").setValue("发票号");
		headRow.getCell("field15").setValue("已开票金额");
		headRow.getCell("field16").setValue("未开票金额");
		
		kdtEntrys.put("6.1收款明细（租金）", table6);
		
		
		KDTable table7=new KDTable();
		headRow=table7.addHeadRow();
		for(int i=0;i<table6.getColumnCount();i++){
			column=table7.addColumn();
			column.setKey(table6.getColumnKey(i));
			
			headRow.getCell(table6.getColumnKey(i)).setValue(table6.getHeadRow(0).getCell(table6.getColumnKey(i)).getValue());
		}
		kdtEntrys.put("6.2收款明细（物业费）", table7);
		
		KDTable table8=new KDTable();
		headRow=table8.addHeadRow();
		for(int i=0;i<table6.getColumnCount();i++){
			column=table8.addColumn();
			column.setKey(table6.getColumnKey(i));
			
			headRow.getCell(table6.getColumnKey(i)).setValue(table6.getHeadRow(0).getCell(table6.getColumnKey(i)).getValue());
		}
		kdtEntrys.put("6.3收款明细（经营租赁）", table8);
		
		KDTable table9=new KDTable();
		headRow=table9.addHeadRow();
		for(int i=0;i<table6.getColumnCount();i++){
			column=table9.addColumn();
			column.setKey(table6.getColumnKey(i));
			
			headRow.getCell(table6.getColumnKey(i)).setValue(table6.getHeadRow(0).getCell(table6.getColumnKey(i)).getValue());
		}
		kdtEntrys.put("6.4收款明细（水费）", table9);
		
		KDTable table10=new KDTable();
		headRow=table10.addHeadRow();
		for(int i=0;i<table6.getColumnCount();i++){
			column=table10.addColumn();
			column.setKey(table6.getColumnKey(i));
			
			headRow.getCell(table6.getColumnKey(i)).setValue(table6.getHeadRow(0).getCell(table6.getColumnKey(i)).getValue());
		}
		kdtEntrys.put("6.5收款明细（电费）", table10);
		
		for(int i=0;i<xhCol.size();i++){
			IRow row=table1.addRow();
			row.getCell("number").setValue(xhCol.get(i).getNumber());
			row.getCell("field4").setValue(xhCol.get(i).getNumber());
			row.getCell("name").setValue(xhCol.get(i).getTenancyName());
			row.getCell("sellProject.name").setValue(xhCol.get(i).getTenancyBill().getSellProject().getName());
			row.getCell("bizDate").setValue(FDCDateHelper.formatDate2(xhCol.get(i).getTenancyDate()));
			row.getCell("startDate").setValue(FDCDateHelper.formatDate2(xhCol.get(i).getStartDate()));
			row.getCell("endDate").setValue(FDCDateHelper.formatDate2(xhCol.get(i).getEndDate()));
			row.getCell("field2").setValue("租赁");
			row.getCell("field3").setValue("整租");
			row.getCell("field5").setValue(xhCol.get(i).getDescription());
			if(xhCol.get(i).getXhCustomer()!=null){
				row=table2.addRow();
				row.getCell("number").setValue(xhCol.get(i).getNumber());
				row.getCell("customer.number").setValue(xhCol.get(i).getXhCustomer().getNumber());
			}
			BigDecimal totalArea=FDCHelper.ZERO;
			for(int j=0;j<xhCol.get(i).getXhRoomEntry().size();j++){
				row=table3.addRow();
				row.getCell("number").setValue(xhCol.get(i).getNumber());
				row.getCell("room.number").setValue(xhCol.get(i).getXhRoomEntry().get(j).getXhRoom().getNumber());
				row.getCell("sellProject.name").setValue(xhCol.get(i).getTenancyBill().getSellProject().getName());
				row.getCell("field2").setValue(xhCol.get(i).getXhRoomEntry().get(j).getXhRoom().getNumber().replace("ZY-", ""));
				
				totalArea=FDCHelper.add(totalArea, xhCol.get(i).getXhRoomEntry().get(j).getXhRoom().getArea());
			}
			TenancyXHRoomPayListEntryCollection entryCol=xhCol.get(i).getPayListEntry();
			CRMHelper.sortCollection(entryCol, "leaseSeq", true);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy年M期");
			
			BigDecimal rentAmount=FDCHelper.ZERO;
			BigDecimal wyAmount=FDCHelper.ZERO;
			BigDecimal bzjAmount=FDCHelper.ZERO;
			Date bzjStartDate=null;
			Date bzjEndDate=null;
			long wyLeaseTime=0;
			for(int k=0;k<entryCol.size();k++){
				if(entryCol.get(k).getMoneyDefine().getName().equals("租赁保证金")){
					bzjAmount=entryCol.get(k).getAppAmount();
					bzjStartDate=entryCol.get(k).getStartDate();
					bzjEndDate=entryCol.get(k).getEndDate();
					continue;
				}
				if(entryCol.get(k).getMoneyDefine().getName().equals("租金")){
					row=table6.addRow();
					rentAmount=FDCHelper.add(rentAmount, entryCol.get(k).getAppAmount());
				}else if(entryCol.get(k).getMoneyDefine().getName().equals("物业费")||entryCol.get(k).getMoneyDefine().getName().indexOf("物业管理费")>=0){
					row=table7.addRow();
					wyAmount=FDCHelper.add(wyAmount, entryCol.get(k).getAppAmount());
					wyLeaseTime=FDCDateHelper.dateDiff("m", entryCol.get(k).getStartDate(), entryCol.get(k).getEndDate());
				}else if(entryCol.get(k).getMoneyDefine().getName().equals("经营租赁")){
					row=table8.addRow();
				}else if(entryCol.get(k).getMoneyDefine().getName().equals("水费")){
					row=table9.addRow();
				}else if(entryCol.get(k).getMoneyDefine().getName().equals("电费")){
					row=table10.addRow();
				}
				
				row.getCell("appDate").setValue(FDCDateHelper.formatDate2(entryCol.get(k).getAppDate()));
				row.getCell("number").setValue(xhCol.get(i).getNumber());
				row.getCell("name").setValue(xhCol.get(i).getTenancyName());
				row.getCell("field1").setValue(xhCol.get(i).getTenancyBill().getCU().getNumber());
				row.getCell("field2").setValue(xhCol.get(i).getTenancyBill().getCU().getName());
				row.getCell("field3").setValue(df.format(entryCol.get(k).getStartDate()));
				row.getCell("field4").setValue(df1.format(entryCol.get(k).getStartDate()));
				row.getCell("field5").setValue(xhCol.get(i).getTenancyBill().getSellProject().getNumber());
				row.getCell("field6").setValue(xhCol.get(i).getTenancyBill().getSellProject().getName());
				row.getCell("moneyDefine.number").setValue(entryCol.get(k).getMoneyDefine().getNumber());
				row.getCell("moneyDefine.name").setValue(entryCol.get(k).getMoneyDefine().getName());
				row.getCell("field9").setValue(xhCol.get(i).getXhCustomer().getNumber());
				row.getCell("field10").setValue(xhCol.get(i).getXhCustomer().getName());
				row.getCell("startDate").setValue(FDCDateHelper.formatDate2(entryCol.get(k).getStartDate()));
				row.getCell("endDate").setValue(FDCDateHelper.formatDate2(entryCol.get(k).getEndDate()));
				row.getCell("appAmount").setValue(entryCol.get(k).getAppAmount());
				row.getCell("actRevAmount").setValue(entryCol.get(k).getAllRemainAmount());
				if(entryCol.get(k).getActRevDate()!=null)
					row.getCell("actRevDate").setValue(FDCDateHelper.formatDate2(entryCol.get(k).getActRevDate()));
			}
			if(rentAmount.compareTo(FDCHelper.ZERO)>0){
				row=table4.addRow();
				row.getCell("field2").setValue("租金");
				row.getCell("field3").setValue("循环周期");
				row.getCell("field4").setValue("月");
				row.getCell("field5").setValue(xhCol.get(i).getTenancyBill().getLeaseTime());
				row.getCell("field6").setValue("租期开始前");
				row.getCell("field8").setValue("元/月");
				row.getCell("field9").setValue("建筑面积");
				row.getCell("field10").setValue("是");
				row.getCell("field11").setValue("是");
				row.getCell("field12").setValue("否");
				row.getCell("field13").setValue(FDCDateHelper.formatDate2(xhCol.get(i).getStartDate()));
				row.getCell("field14").setValue(FDCDateHelper.formatDate2(xhCol.get(i).getEndDate()));
				BigDecimal avgRentAmount=FDCHelper.divide(rentAmount, FDCHelper.multiply(xhCol.get(i).getTenancyBill().getLeaseTime(), xhCol.get(i).getLeaseCount()), 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("field15").setValue(avgRentAmount);
				row.getCell("field16").setValue(xhCol.get(i).getNumber());
			}
			if(wyAmount.compareTo(FDCHelper.ZERO)>0){
				row=table4.addRow();
				row.getCell("field2").setValue("物业费");
				row.getCell("field3").setValue("循环周期");
				row.getCell("field4").setValue("月");
				row.getCell("field5").setValue(wyLeaseTime+1);
				row.getCell("field6").setValue("租期开始前");
				row.getCell("field8").setValue("元/月");
				row.getCell("field9").setValue("建筑面积");
				row.getCell("field10").setValue("是");
				row.getCell("field11").setValue("否");
				row.getCell("field12").setValue("否");
				row.getCell("field13").setValue(FDCDateHelper.formatDate2(xhCol.get(i).getStartDate()));
				row.getCell("field14").setValue(FDCDateHelper.formatDate2(xhCol.get(i).getEndDate()));
				BigDecimal avgRentAmount=FDCHelper.divide(wyAmount, FDCHelper.multiply(xhCol.get(i).getTenancyBill().getLeaseTime(), xhCol.get(i).getLeaseCount()), 2, BigDecimal.ROUND_HALF_UP);
				row.getCell("field15").setValue(avgRentAmount);
				row.getCell("field16").setValue(xhCol.get(i).getNumber());
			}
			if(bzjAmount.compareTo(FDCHelper.ZERO)>0){
				row=table4.addRow();
				row.getCell("field2").setValue("租赁保证金");
				row.getCell("field3").setValue("一次缴清");
				row.getCell("field4").setValue("月");
				row.getCell("field5").setValue(1);
				row.getCell("field6").setValue("租期开始前");
				row.getCell("field8").setValue("元");
				row.getCell("field9").setValue("建筑面积");
				row.getCell("field10").setValue("是");
				row.getCell("field11").setValue("否");
				row.getCell("field12").setValue("是");
				row.getCell("field13").setValue(FDCDateHelper.formatDate2(bzjStartDate));
				row.getCell("field14").setValue(FDCDateHelper.formatDate2(bzjEndDate));
				BigDecimal avgRentAmount=bzjAmount;
				row.getCell("field15").setValue(avgRentAmount);
				row.getCell("field16").setValue(xhCol.get(i).getNumber());
			}
			
			row=table5.addRow();
			row.getCell("field1").setValue(xhCol.get(i).getNumber());
		}
		ExportManager exportM = new ExportManager();
        String path = null;
        File tempFile = File.createTempFile("eastemp",".xls");
        path = tempFile.getCanonicalPath();

        KDTables2KDSBookVO[] tablesVO = new KDTables2KDSBookVO[kdtEntrys.size()];
        Object[] key = kdtEntrys.keySet().toArray(); 
        Arrays.sort(key);
        for(int i =0;i< key.length;i++){
        	KDTable table = (KDTable) kdtEntrys.get(key[i]);
            tablesVO[i] = new KDTables2KDSBookVO(table);
            String title = key[i].toString();
            title=title.replaceAll("[{\\\\}{\\*}{\\?}{\\[}{\\]}{\\/}]", "|");
			tablesVO[i].setTableName(title);
        }
        KDSBook book = null;
        book = KDTables2KDSBook.getInstance().exportKDTablesToKDSBook(tablesVO,true,true);
        exportM.exportToExcel(book, path);
        
		KDFileChooser fileChooser = new KDFileChooser();
		fileChooser.setFileSelectionMode(0);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File("租赁合同excel.xls"));
		int result = fileChooser.showSaveDialog(this);
		if (result == KDFileChooser.APPROVE_OPTION){
			File dest = fileChooser.getSelectedFile();
			try{
				File src = new File(path);
				if (dest.exists())
					dest.delete();
				src.renameTo(dest);
				FDCMsgBox.showInfo("导出成功！");
				KDTMenuManager.openFileInExcel(dest.getAbsolutePath());
			}
			catch (Exception e3)
			{
				handUIException(e3);
			}
		}
		tempFile.delete();
	}
	public void btnMultiSubmit_actionPerformed(ActionEvent e) {
		checkSelected();
		   Window win = SwingUtilities.getWindowAncestor(this);
	       LongTimeDialog dialog = null;
	       if(win instanceof Frame)
	           dialog = new LongTimeDialog((Frame)win);
	       else
	       if(win instanceof Dialog)
	           dialog = new LongTimeDialog((Dialog)win);
	       
	       dialog.setLongTimeTask(new ILongTimeTask() {
	           public Object exec()
	               throws Exception
	           {
	        	   TenancyImport.tenancyUpdata();
	        	   ArrayList id = getSelectedIdValues();
	        	   for(int i = 0; i < id.size(); i++){
        			   UIContext uiContext = new UIContext(this);
        			   uiContext.put("ID", id.get(i).toString());
        			   TenancyBillEditUI ui=(TenancyBillEditUI) UIFactoryHelper.initUIObject(TenancyBillEditUI.class.getName(), uiContext, null,OprtState.EDIT);
        			   TenancyBillStateEnum state = ((TenancyBillInfo)ui.getEditData()).getTenancyState();
        			   FDCClientUtils.checkBillInWorkflow(ui, ui.getEditData().getId().toString());
        				
        			   if(state==null||!(TenancyBillStateEnum.Saved.equals(state)||TenancyBillStateEnum.Submitted.equals(state))){
        					MsgBox.showWarning("单据不是保存或者提交状态，不能进行提交操作！");
        					SysUtil.abort();
        			   }
        			   ui.loadFields();
        			   ui.updatePayListInfo();
        			   ui.storeFields();
        			   ui.verifyInput(null);
        			   ui.runSubmit();
        			   ui.destroyWindow();
        		   }
	               return new Boolean(true);
	           }
	           public void afterExec(Object result)
	               throws Exception
	           {
	        	   FDCMsgBox.showWarning("操作成功！");
	           }
	       });
       dialog.show();
       try {
		this.refreshList();
	} catch (Exception e1) {
		e1.printStackTrace();
	}
   }
    public void actionPrintBill_actionPerformed(ActionEvent e) throws Exception {
    	ArrayList idList = new ArrayList();
    	this.checkSelected();
    	String id = this.getSelectedKeyValue();
		if (id != null && !StringUtils.isEmpty(id)) {
			idList.add(id);
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;
		}
		TenancyBillDataProvider data = new TenancyBillDataProvider(id, getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.print(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}

	public void actionPrintPreviewBill_actionPerformed(ActionEvent e)
			throws Exception {
		ArrayList idList = new ArrayList();
		String id = this.getSelectedKeyValue();
		if (id != null && !StringUtils.isEmpty(id)) {
			idList.add(id);
		}
		if (idList == null || idList.size() == 0 || getTDQueryPK() == null
				|| getTDFileName() == null) {
			MsgBox.showWarning(this, EASResource.getString(
					"com.kingdee.eas.fdc.basedata.client.FdcResource",
					"cantPrint"));
			return;

		}
		TenancyBillDataProvider data = new TenancyBillDataProvider(id, getTDQueryPK());
		com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper appHlp = new com.kingdee.bos.ctrl.report.forapp.kdnote.client.KDNoteHelper();
		appHlp.printPreview(getTDFileName(), data, javax.swing.SwingUtilities
				.getWindowAncestor(this));
	}
	protected String getTDFileName() {
		return "/bim/fdc/tenancy/TenancyBill";
	}

	protected IMetaDataPK getTDQueryPK() {
		return new MetaDataPK(
				"com.kingdee.eas.fdc.tenancy.app.TenancyBillPrintQuery");
	}

	/** 审批 */
    public void actionAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		ArrayList idList = getSelectedIdValues();
		for(int i = 0; i < idList.size(); i++){
			String id = idList.get(i).toString();
	    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
	    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
	    	if(!TenancyBillStateEnum.Submitted.equals(tenState)){
	    		MsgBox.showInfo(this, "只有已提交的合同才能审批！");
	    		this.abort();
	    	}
	    	TenancyBillFactory.getRemoteInstance().audit(BOSUuid.read(id));
		}
		FDCClientUtils.showOprtOK(this);
    	this.refresh(null);
    }
    
    /** 反审批TODO */
    public void actionUnAudit_actionPerformed(ActionEvent e) throws Exception {
    	checkSelected();
		ArrayList idList = getSelectedIdValues();
		for(int i = 0; i < idList.size(); i++){
			String id = idList.get(i).toString();
			TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
			TenancyContractTypeEnum tenType = tenBill.getTenancyType();

			if (OtherBillFactory.getRemoteInstance().exists("select id from where tenancyBill.id='"+id+"'")) {
				MsgBox.showInfo(this, "存在其他合同，禁止反审批操作！");
				this.abort();
			}
			if (DepositDealBillFactory.getRemoteInstance().exists("select id from where tenancyBill.id='"+id+"'")) {
				MsgBox.showInfo(this, "存在押金处理申请单，禁止反审批操作！");
				this.abort();
			}
			if (!tenType.equals(TenancyContractTypeEnum.NewTenancy)) {
				MsgBox.showInfo(this, "只有新租合同才允许反审批！");
				this.abort();
			}
			
			if(!TenancyBillStateEnum.Audited.equals(tenBill.getTenancyState())){
				MsgBox.showInfo(this, "只有审批状态的合同才允许反审批！");
				this.abort();
			}
			boolean isjiaojie = false;
			TenancyRoomEntryCollection list = tenBill.getTenancyRoomList();
			for (int j = 0; j < list.size(); j++) {
				TenancyRoomEntryInfo entry = list.get(j);
				if (!entry.getHandleState().equals(HandleStateEnum.NoHandleRoom)) {
					isjiaojie = true;
					break;
				}
			}
			FilterInfo info = new FilterInfo();
			info.appendFilterItem("tenancyObj.id", tenBill.getId());
			EntityViewInfo view = new EntityViewInfo();
			view.setFilter(info);
			FDCReceivingBillCollection c = FDCReceivingBillFactory.getRemoteInstance().getFDCReceivingBillCollection(view);
			if (isjiaojie = false || c.size() == 0) {
				TenancyBillFactory.getRemoteInstance().antiAudit(tenBill.getId());
			} else {
				MsgBox.showInfo(this, "只有未交接房间、未收款的新租合同才允许反审批！");
				this.abort();
			}
		}
		FDCClientUtils.showOprtOK(this);
		this.refresh(null);
	}
    
    /** 续租 */
    public void actionContinueTenancy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionContinueTenancy_actionPerformed(e);
		String id = getSelectedTenancyId();
    	commonVerify(id);
    	commonVerify2(id);
		showTenancyBillEditUI(id, TenancyContractTypeEnum.ContinueTenancy);
    }
    
    /**
     * 打开租赁合同界面
     * @param oldTenBillId 原合同ID
     * @param tenancyType 合同类型
     * @throws BOSException 
     * @throws EASBizException 
     * */
    private void showTenancyBillEditUI(String oldTenBillId, TenancyContractTypeEnum tenancyType) throws EASBizException, BOSException {
    	UIContext uiContext = new UIContext(this);
		uiContext.put(TenancyBillConstant.KEY_OLD_TENANCY_BILL_ID, oldTenBillId);
		uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE, tenancyType);

		// 创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
	}

    /**
     * 判断原合同是否为已收押金首租,部分执行或者执行中
     * 如果不是,对话框提示,并中断操作
     * */
	private void commonVerify(String tenId) throws EASBizException, BOSException {
		TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(tenId));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	/* TODO 修改为执行中的合同才能续租,需要需求确认该方式
		if(!TenancyBillStateEnum.DepositReved.equals(tenState)  &&
				!TenancyBillStateEnum.PartExecuted.equals(tenState)  &&
				!TenancyBillStateEnum.Executing.equals(tenState)){
    		MsgBox.showInfo(this, "只有已收押金首租，部分执行或执行中的合同才能执行该操作！");
    		*/
		if(!TenancyBillStateEnum.Executing.equals(tenState)){
        		MsgBox.showInfo(this, "只有执行中的合同才能执行该操作！");
    		this.abort();
    	}
	}
	
	/**
     * 判断该合同是否存在非保存状态的退租单,非保存状态的改租或续租或更名的合同
     * 如果存在,对话框提示并中断操作
     * */
	private void commonVerify2(String tenId) throws EASBizException, BOSException {
		if (TenancyHelper.existQuitTenBillByTenBill(QuitTenancyFactory.getRemoteInstance(), tenId, null)) {
			MsgBox.showInfo(this, "原合同已经有退租单！");
			this.abort();
		}

		//这步检验现在可以不要了，因为续租合同提交后，原合同为续租中状态。
		/*
		String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(tenId);
		if (targetTenId != null) {
			MsgBox.showInfo(this, "原合同已经改续租或转名！");
			this.abort();
		}
		*/
	}
    
    /** 获得选中行的租赁合同的ID,如果没有选中行给出提示,并中断操作 */
    private String getSelectedTenancyId(){
		return getSelectedId();
    }
    
    //TODO 
    private TenancyBillStateEnum getSelectedBillState(){
    	IRow row = getSelectedRow();
    	
    	return (TenancyBillStateEnum) row.getCell("tenancyState").getValue();
    }
    
    /** 改租 */
    public void actionRejiggerTenancy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionRejiggerTenancy_actionPerformed(e);
    	String id = getSelectedTenancyId();
    	commonVerify(id);
    	commonVerify2(id);
		showTenancyBillEditUI(id, TenancyContractTypeEnum.RejiggerTenancy);
    }
    
    /** 转名 */
    public void actionChangeName_actionPerformed(ActionEvent e) throws Exception {
    	super.actionChangeName_actionPerformed(e);
		String id = getSelectedTenancyId();
    	commonVerify(id);
    	commonVerify2(id);
		showTenancyBillEditUI(id, TenancyContractTypeEnum.ChangeName);
    }
    
    /** 价格变更 */
    public void actionPriceChange_actionPerformed(ActionEvent e)throws Exception {
//    	super.actionPriceChange_actionPerformed(e);
//    	String id = getSelectedTenancyId();
////    	commonVerify(id);
//		UIContext uiContext = new UIContext(this);
////		uiContext.put("ID", id);
//		uiContext.put(TenancyBillConstant.KEY_OLD_TENANCY_BILL_ID, id);
//		uiContext.put(TenancyBillConstant.KEY_TENANCY_CONTRACT_TYPE, TenancyContractTypeEnum.PriceChange);
//
//		// 创建UI对象并显示
//		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB).create(TenancyBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
//		uiWindow.show();
    }
    
    /** 作废 */
    public void actionBlankOut_actionPerformed(ActionEvent e) throws Exception {
    	super.actionBlankOut_actionPerformed(e);
    	String id = getSelectedTenancyId();
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(!TenancyBillStateEnum.Saved.equals(tenState) && !TenancyBillStateEnum.Submitted.equals(tenState) && 
				!TenancyBillStateEnum.Auditing.equals(tenState) && !TenancyBillStateEnum.Audited.equals(tenState) /*&& 
				!TenancyBillStateEnum.DepositReved.equals(tenState)*/){
    		MsgBox.showInfo("已保存,已提交,审批中,已审批,已收首租和押金状态的合同才可以作废");
			abort();
		}
    	
    	TenancyBillFactory.getRemoteInstance().blankOut(new ObjectUuidPK(id));
    	this.refresh(null);
    }
    
    /** 退租申请 */
    public void actionQuitTenancy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionQuitTenancy_actionPerformed(e);
    	String id = getSelectedTenancyId();
    	
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(!TenancyBillStateEnum.Executing.equals(tenState)
    			&& !TenancyBillStateEnum.PartExecuted.equals(tenState)
    			&& !TenancyBillStateEnum.Audited.equals(tenState)){
			MsgBox.showInfo("只有已审批，部分执行或执行中的合同才能退租！");
			abort();
		}
    	if(tenBill.getRentStartType().equals(RentStartTypeEnum.DynamicStartDate) && tenBill.getStartDate()==null)
    	{
    		MsgBox.showInfo("动态收租起始日期的合同开始日期还未补录不允许退租操作!");
    		this.abort();
    	}
    	this.commonVerify2(id);
    	
    	UIContext uiContext = new UIContext(this);
		uiContext.put(QuitTenancyEditUI.KEY_TENANCY_ID, id);
		uiContext.put("sellProject", tenBill.getSellProject());
		//创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(QuitTenancyEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
    }
    
    /** 房间交接 */
    public void actionHandleTenancy_actionPerformed(ActionEvent e) throws Exception {
    	super.actionHandleTenancy_actionPerformed(e);
    	String id = getSelectedTenancyId();
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(TenancyBillStateEnum.Saved.equals(tenState) || TenancyBillStateEnum.Submitted.equals(tenState) || TenancyBillStateEnum.Auditing.equals(tenState))
    	{
    		MsgBox.showInfo("合同还未经过审批,请先审批");
    		this.abort();
    	}else if(TenancyBillStateEnum.Expiration.equals(tenState))
    	{
    		MsgBox.showInfo("合同已经终止不能进行交房操作!");
    		this.abort();
    	}else if(TenancyBillStateEnum.Executing.equals(tenState))
    	{
    		MsgBox.showInfo("执行中的合同不能进行交房操作!");
    		this.abort();
    	}else if(TenancyBillStateEnum.BlankOut.equals(tenState))
    	{
    		MsgBox.showInfo("合同已作废!");
    		this.abort();
    	}  	
    	UIContext uiContext = new UIContext(this);
		uiContext.put("tenancyBillId", id);
		
		//创建UI对象并显示
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
				.create(HandleRoomTenancyEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		
		this.refresh(null);
    }
    
    /** 收款 */
    public void actionReceiveBill_actionPerformed(ActionEvent e) throws Exception {
    	super.actionReceiveBill_actionPerformed(e);
    	String id = getSelectedId();
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id),sels);
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(TenancyBillStateEnum.Saved.equals(tenState) || TenancyBillStateEnum.Submitted.equals(tenState) || 
    			TenancyBillStateEnum.Auditing.equals(tenState)){
    		MsgBox.showInfo("审批后的合同才能收款！");
			abort();
    	}
//    	if(TenancyBillStateEnum.ContinueTenancying.equals(tenState) || TenancyBillStateEnum.RejiggerTenancying.equals(tenState)
//    			||TenancyBillStateEnum.ChangeNaming.equals(tenState) || TenancyBillStateEnum.TenancyChanging.equals(tenState)
//    			||TenancyBillStateEnum.QuitTenancying.equals(tenState) || TenancyBillStateEnum.Expiration.equals(tenState))
//    	{
//    		MsgBox.showInfo(tenState+"的合同不能收款");
//    		abort();
//    	}
    	
    	UIContext uiContext = new UIContext(this);
    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.tenancy);
    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.gathering);
    	uiContext.put(FDCReceivingBillEditUI.KEY_SELL_PROJECT, tenBill.getSellProject());
    	uiContext.put(FDCReceivingBillEditUI.KEY_TENANCY_BILL, tenBill);
    	uiContext.put(FDCReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, Boolean.TRUE);
    	IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(TENReceivingBillEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
		
		this.refresh(null);
    }
    
    /** 退款 */
    public void actionRefundment_actionPerformed(ActionEvent e) throws Exception {
  
    	String id = getSelectedTenancyId();
    	SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("sellProject.*");
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id), sels);
//    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
//    	if(!TenancyBillStateEnum.Expiration.equals(tenState) && !TenancyBillStateEnum.BlankOut.equals(tenState)){
//    		MsgBox.showInfo("只有终止或作废状态的的合同才能退款！");
//			abort();
//    	}
    	
    	UIContext uiContext = new UIContext(this);
    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BIZ_TYPE, RevBizTypeEnum.tenancy);
    	uiContext.put(FDCReceivingBillEditUI.KEY_REV_BILL_TYPE, RevBillTypeEnum.refundment);
    	uiContext.put(FDCReceivingBillEditUI.KEY_SELL_PROJECT, tenBill.getSellProject());
    	uiContext.put(FDCReceivingBillEditUI.KEY_TENANCY_BILL, tenBill);
    	uiContext.put(FDCReceivingBillEditUI.KEY_IS_LOCK_BILL_TYPE, Boolean.TRUE);
    	IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.NEWTAB)
				.create(TENReceivingBillEditUI.class.getName(), uiContext, null,
						"ADDNEW");
		uiWindow.show();
		
		this.refresh(null);
    }
    
    /** 押金结转 */
    public void actionCarryForward_actionPerformed(ActionEvent e) throws Exception {
    	super.actionCarryForward_actionPerformed(e);
    	String id = getSelectedTenancyId();
    	
    	//改续租或更名合同存在且审批后,其原合同才能进行押金结转操作
    	String targetTenId = TenancyHelper.getTargetTenIdBySrcTenancyId(id);
    	
    	if(targetTenId == null){
    		MsgBox.showInfo("该合同没有进行改续租或更名,不能结转！");
			abort();
    	}
    	
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(targetTenId));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(TenancyBillStateEnum.Saved.equals(tenState) || TenancyBillStateEnum.Submitted.equals(tenState) ||
    			TenancyBillStateEnum.Auditing.equals(tenState)){
    		MsgBox.showInfo("改续租或者更名的合同还没有审批,不能结转！");
			abort();
    	}
    	
		UIContext uiContext = new UIContext(this);
		uiContext.put(CarryForwardBillEditUI.KEY_SRC_TENANCY_ID, id);
		
		IUIWindow uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL)
			.create(CarryForwardBillEditUI.class.getName(), uiContext, null, OprtState.ADDNEW);
		uiWindow.show();
		
		this.refresh(null);
    }
    
    /** 到期标记 */
    public void actionFlagAtTerm_actionPerformed(ActionEvent e) throws Exception {
    	super.actionFlagAtTerm_actionPerformed(e);
    }
    
    public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
    	String id = getSelectedTenancyId();
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(!TenancyBillStateEnum.Saved.equals(tenState) && !TenancyBillStateEnum.Submitted.equals(tenState)
    			&& !TenancyBillStateEnum.BlankOut.equals(tenState)){
    		MsgBox.showInfo("只有保存,提交或作废状态的的合同才能删除！");
			abort();
    	}
    	super.actionRemove_actionPerformed(e);
    }
    
    public void actionEdit_actionPerformed(ActionEvent e) throws Exception {
    	String id = getSelectedId();
    	TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance().getTenancyBillInfo(new ObjectUuidPK(id));
    	TenancyBillStateEnum tenState = tenBill.getTenancyState();
    	if(!TenancyBillStateEnum.Saved.equals(tenState) && !TenancyBillStateEnum.Submitted.equals(tenState)){
    		MsgBox.showInfo("保存或提交状态的的合同才能修改！");
			abort();
    	}
    	super.actionEdit_actionPerformed(e);
    }
    
    public void actionRepairStartDate_actionPerformed(ActionEvent e)
    		throws Exception {
    	super.actionRepairStartDate_actionPerformed(e);
    	int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		if (rowIndex == -1)
		{
			MsgBox.showInfo("请选择行!");
			this.abort();
		}
		IRow row = this.tblMain.getRow(rowIndex);
		Date startDate = (Date)row.getCell("startDate").getValue();
		if(startDate!=null)
		{
			MsgBox.showInfo("只有租赁开始日期为空的合同才能补录");
			this.abort();
		}
		String tenancyBillID = this.getSelectedKeyValue();
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		//sels.add("startDateLimit");
		TenancyBillInfo tenBill = TenancyBillFactory.getRemoteInstance()
		.getTenancyBillInfo(new ObjectUuidPK(BOSUuid.read(tenancyBillID)),
				sels);
		RepairStartDateUI.showUI(this, tenBill);
    }
    
    protected com.kingdee.eas.framework.ICoreBase getBizInterface() throws Exception
    {
        return TenancyBillFactory.getRemoteInstance();
    }
    
    protected String getKeyFieldName() {
    	return super.getKeyFieldName();
    }
    
    
	@Override
	public String getEntriesName() {
		// TODO Auto-generated method stub
		return null;
	}
	protected String getEditUIName() {
    	return TenancyBillEditUI.class.getName();
    }
    
	protected boolean isIgnoreCUFilter() {
		return true;
	}
	protected void afterTableFillData(KDTDataRequestEvent e) {
	   super.afterTableFillData(e);
	   Map valueSet=new HashMap();
		Map wyvalueSet=new HashMap();
		Map sfvalueSet=new HashMap();
		Map dfvalueSet=new HashMap();
		Map jyvalueSet=new HashMap();
	   if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("900002")){
			DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
			if(node!=null&&node.getUserObject() instanceof SellProjectInfo && node.isLeaf()){
				StringBuilder sql = new StringBuilder();
				sql.append(" select c.fid id,sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0)) amount from T_TEN_TenancyRoomPayListEntry a left join T_TEN_TENANCYROOMENTRY b on a.FTENROOMID =b.fid left join T_TEN_TENANCYBILL c on b.FTENANCYID =c.fid left join T_SHE_MONEYDEFINE d on a.FMONEYDEFINEID =d.fid where c.ftenancyState='Expiration' and c.FSELLPROJECTID ='"+((SellProjectInfo)node.getUserObject()).getId()+"' and d.fname_l2='租金' group by c.fid  having sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0))!=0 ");
				FDCSQLBuilder _builder = new FDCSQLBuilder();
				_builder.appendSql(sql.toString());
				try {
					IRowSet rowSet = _builder.executeQuery();
					while(rowSet.next()){
						valueSet.put(rowSet.getString("id"), rowSet.getBigDecimal("amount"));
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				
				sql = new StringBuilder();
				sql.append(" select t.id id,sum(t.amount) amount from (select c.fid id,sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0)) amount from T_TEN_TenancyRoomPayListEntry a left join T_TEN_TENANCYROOMENTRY b on a.FTENROOMID =b.fid left join T_TEN_TENANCYBILL c on b.FTENANCYID =c.fid left join T_SHE_MONEYDEFINE d on a.FMONEYDEFINEID =d.fid where c.ftenancyState='Expiration' and c.FSELLPROJECTID ='"+((SellProjectInfo)node.getUserObject()).getId()+"' and d.fname_l2 like '%物业管理费%' group by c.fid  having sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0))!=0 ");
				sql.append(" union all  select c.fid id,sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0)) amount from T_TEN_TenBillOtherPay a left join T_TEN_TENANCYBILL c on a.FHEADID  =c.fid left join T_SHE_MONEYDEFINE d on a.FMONEYDEFINEID =d.fid where c.ftenancyState='Expiration' and c.FSELLPROJECTID ='"+((SellProjectInfo)node.getUserObject()).getId()+"' and d.fname_l2 like '%物业费%' group by c.fid  having sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0))!=0)t group by t.id ");
				
				_builder = new FDCSQLBuilder();
				_builder.appendSql(sql.toString());
				try {
					IRowSet rowSet = _builder.executeQuery();
					while(rowSet.next()){
						wyvalueSet.put(rowSet.getString("id"), rowSet.getBigDecimal("amount"));
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				
				sql = new StringBuilder();
				sql.append(" select c.fid id,sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0)) amount from T_TEN_TenBillOtherPay a left join T_TEN_TENANCYBILL c on a.FHEADID  =c.fid left join T_SHE_MONEYDEFINE d on a.FMONEYDEFINEID =d.fid where c.ftenancyState='Expiration' and c.FSELLPROJECTID ='"+((SellProjectInfo)node.getUserObject()).getId()+"' and d.fname_l2 like '%水费%' group by c.fid  having sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0))!=0 ");
				
				_builder = new FDCSQLBuilder();
				_builder.appendSql(sql.toString());
				try {
					IRowSet rowSet = _builder.executeQuery();
					while(rowSet.next()){
						sfvalueSet.put(rowSet.getString("id"), rowSet.getBigDecimal("amount"));
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				
				sql = new StringBuilder();
				sql.append(" select c.fid id,sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0)) amount from T_TEN_TenBillOtherPay a left join T_TEN_TENANCYBILL c on a.FHEADID  =c.fid left join T_SHE_MONEYDEFINE d on a.FMONEYDEFINEID =d.fid where c.ftenancyState='Expiration' and c.FSELLPROJECTID ='"+((SellProjectInfo)node.getUserObject()).getId()+"' and d.fname_l2 like '%电费%' group by c.fid  having sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0))!=0 ");
				
				_builder = new FDCSQLBuilder();
				_builder.appendSql(sql.toString());
				try {
					IRowSet rowSet = _builder.executeQuery();
					while(rowSet.next()){
						dfvalueSet.put(rowSet.getString("id"), rowSet.getBigDecimal("amount"));
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				
				sql = new StringBuilder();
				sql.append(" select c.fid id,sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0)) amount from T_TEN_TenBillOtherPay a left join T_TEN_TENANCYBILL c on a.FHEADID  =c.fid left join T_SHE_MONEYDEFINE d on a.FMONEYDEFINEID =d.fid where c.ftenancyState='Expiration' and c.FSELLPROJECTID ='"+((SellProjectInfo)node.getUserObject()).getId()+"' and d.fname_l2 like '%经营租赁%' group by c.fid  having sum(a.FAPPAMOUNT -isnull(a.FACTREVAMOUNT ,0)+isnull(a.FHASREFUNDMENTAMOUNT ,0))!=0 ");
				
				_builder = new FDCSQLBuilder();
				_builder.appendSql(sql.toString());
				try {
					IRowSet rowSet = _builder.executeQuery();
					while(rowSet.next()){
						jyvalueSet.put(rowSet.getString("id"), rowSet.getBigDecimal("amount"));
					}
				} catch (BOSException e1) {
					e1.printStackTrace();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
	   }
	   for(int i = 0 ; i < tblMain.getRowCount();i++){
		   IRow row = tblMain.getRow(i);
		   Object state = row.getCell("tenancyState").getValue();
		   if(state!=null&&state.toString().equals("执行中")){
			   row.getStyleAttributes().setBackground(new java.awt.Color(190,255,190));
		   }
			Boolean isUpdateXHTenancyBill=(Boolean) row.getCell("isUpdateXHTenancyBill").getValue();
			if(isUpdateXHTenancyBill){
				 row.getCell("isUpdateXHTenancyBill").getStyleAttributes().setBackground(Color.PINK);
			}
			Date endDate=(Date) row.getCell("endDate").getValue();
			Date quitRoomDate=(Date) row.getCell("quitRoomDate").getValue();
			if(state!=null&&state.toString().equals("执行中")&&endDate!=null&&quitRoomDate==null&&FDCDateHelper.dateDiff(new Date(), endDate)<0){
				row.getCell("endDate").getStyleAttributes().setBackground(Color.PINK);
			}
			BigDecimal dealTotalRent=(BigDecimal) row.getCell("dealTotalRent").getValue();
			if(state!=null&&state.toString().equals("执行中")&&(dealTotalRent==null||dealTotalRent.compareTo(FDCHelper.ZERO)==0)){
				row.getCell("dealTotalRent").getStyleAttributes().setBackground(Color.PINK);
			}
			 if(SysContext.getSysContext().getCurrentUserInfo().getNumber().equals("900002")){
				 if(state!=null&&state.toString().equals("已终止")){
						String id=this.tblMain.getRow(i).getCell("id").getValue().toString();
						if(valueSet.get(id)!=null){
							row.getCell("owedAmount").setValue(valueSet.get(id));
							row.getCell("owedAmount").getStyleAttributes().setBackground(Color.PINK);
						}
						if(wyvalueSet.get(id)!=null){
							row.getCell("owedWYAmount").setValue(wyvalueSet.get(id));
							row.getCell("owedWYAmount").getStyleAttributes().setBackground(Color.PINK);
						}
						if(sfvalueSet.get(id)!=null){
							row.getCell("owedSFAmount").setValue(sfvalueSet.get(id));
							row.getCell("owedSFAmount").getStyleAttributes().setBackground(Color.PINK);
						}
						if(dfvalueSet.get(id)!=null){
							row.getCell("owedDFAmount").setValue(dfvalueSet.get(id));
							row.getCell("owedDFAmount").getStyleAttributes().setBackground(Color.PINK);
						}
						if(jyvalueSet.get(id)!=null){
							row.getCell("owedJYAmount").setValue(jyvalueSet.get(id));
							row.getCell("owedJYAmount").getStyleAttributes().setBackground(Color.PINK);
						}
					}
			 }
			this.tblMain.getColumn("owedAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    	this.tblMain.getColumn("owedAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    	
	    	this.tblMain.getColumn("owedWYAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    	this.tblMain.getColumn("owedWYAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    	
	    	this.tblMain.getColumn("owedSFAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    	this.tblMain.getColumn("owedSFAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    	
	    	this.tblMain.getColumn("owedDFAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    	this.tblMain.getColumn("owedDFAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    	
	    	this.tblMain.getColumn("owedJYAmount").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    	this.tblMain.getColumn("owedJYAmount").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	    	
	    	this.tblMain.getColumn("xhRoomArea").getStyleAttributes().setNumberFormat("#,##0.00;-#,##0.00");
	    	this.tblMain.getColumn("xhRoomArea").getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
	   }
   }
	protected void fetchInitData() throws Exception {

		super.fetchInitData();
		Map paramMap = FDCUtils.getDefaultFDCParam(null, SysContext.getSysContext().getCurrentOrgUnit().getId().toString());
		if(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL)!=null){
			canUploadForAudited = Boolean.valueOf(paramMap.get(FDCConstants.FDC_PARAM_UPLOADAUDITEDBILL).toString()).booleanValue();
		}
	}
	private boolean canUploadForAudited = false;
	 public void actionAttachment_actionPerformed(ActionEvent e) throws Exception
    {
//	    	super.actionAttachment_actionPerformed(e);
		 checkSelected();
    	boolean isEdit=false;
    	AttachmentClientManager acm = AttachmentManagerFactory.getClientManager();
    	String boID = this.getSelectedKeyValue();
    	if (boID == null)
    	{
    		return;
    	}
    	if(getBillStatePropertyName()!=null){
//    		int rowIdx=getBillListTable().getSelectManager().getActiveRowIndex();
//    		ICell cell =getBillListTable().getCell(rowIdx, getBillStatePropertyName());
//    		Object obj=cell.getValue();
//    		isEdit=ContractClientUtils.canUploadAttaForAudited(obj, canUploadForAudited);
    		isEdit=canUploadForAudited;
    	}
    	acm.showAttachmentListUIByBoID(boID,this,isEdit);
    	this.refreshList();
    }
}