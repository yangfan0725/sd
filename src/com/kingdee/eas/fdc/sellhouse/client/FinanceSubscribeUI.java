/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;


import java.awt.Color;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDefaultCellEditor;
import com.kingdee.bos.ctrl.kdf.table.KDTIndexColumn;
import com.kingdee.bos.ctrl.kdf.table.KDTMergeManager;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.kdf.table.foot.KDTFootManager;
import com.kingdee.bos.ctrl.kdf.util.editor.ICellEditor;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.ctrl.swing.KDFormattedTextField;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.eas.base.commonquery.client.CommonQueryDialog;
import com.kingdee.eas.basedata.assistant.ISettlementType;
import com.kingdee.eas.basedata.assistant.SettlementTypeCollection;
import com.kingdee.eas.basedata.assistant.SettlementTypeFactory;
import com.kingdee.eas.basedata.assistant.SettlementTypeInfo;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.sellhouse.FinanceSubscribeUIFacadeFactory;
import com.kingdee.eas.fdc.sellhouse.IFinanceSubscribeUIFacade;
import com.kingdee.eas.fdc.sellhouse.IMoneyDefine;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineCollection;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineFactory;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;

/**
 * output class name
 */
public class FinanceSubscribeUI extends AbstractFinanceSubscribeUI
{
    private static final Logger logger = CoreUIObject.getLogger(FinanceSubscribeUI.class);

    public FinanceSubscribeUI() throws Exception
    {
        super();
    }

    private CommonQueryDialog commonQueryDialog = null;
	protected CommonQueryDialog initCommonQueryDialog() {
		if (commonQueryDialog != null) {
			return commonQueryDialog;
		}
		commonQueryDialog = super.initCommonQueryDialog();
		commonQueryDialog.setWidth(400);
		commonQueryDialog.addUserPanel(this.getFilterUI());
		commonQueryDialog.setShowSorter(false);
		commonQueryDialog.setShowFilter(false);
		commonQueryDialog.setUiObject(null);
		return commonQueryDialog;
	}
    
	private FinanceSubscribeFilterUI filterUI = null;
	private FinanceSubscribeFilterUI getFilterUI() {
		if (this.filterUI == null) {
			try {
				this.filterUI = new FinanceSubscribeFilterUI();
				this.filterUI.onLoad();
			} catch (Exception e) {
				e.printStackTrace();
				SysUtil.abort(e);
			}
		}
		return this.filterUI;
	}
    
    public void storeFields()
    {
        super.storeFields();
    }

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}

	protected void initTree() throws Exception {
		this.treeMain.setModel(FDCTreeHelper.getSellProjectTree(this.actionOnLoad, this.getSystemType()));
		this.treeMain.expandAllNodes(true, (TreeNode) this.treeMain.getModel().getRoot());
	}
	
	protected MoneySysTypeEnum getSystemType() {
		return MoneySysTypeEnum.SalehouseSys;
	}
	
	protected void treeMain_valueChanged(TreeSelectionEvent e) throws Exception {
		execQuery();
	}
	
	public void onLoad() throws Exception {
		super.onLoad();
		actionAddNew.setEnabled(false);
		actionAddNew.setVisible(false);
		actionView.setEnabled(false);
		actionView.setVisible(false);
		actionEdit.setEnabled(false);
		actionEdit.setVisible(false);
		actionRemove.setEnabled(false);
		actionRemove.setVisible(false);
		actionRefresh.setEnabled(false);
		actionRefresh.setVisible(false);
		actionLocate.setEnabled(false);
		actionLocate.setVisible(false);
		actionPrint.setEnabled(false);
		actionPrint.setVisible(false);
		actionPrintPreview.setEnabled(false);
		actionPrintPreview.setVisible(false);
	}
	
	private void initTblMain() throws BOSException, EASBizException, SQLException {
		this.tblMain.removeRows(false);
		this.tblMain.removeColumns();
		this.tblMain.addHeadRow(0);
		this.tblMain.addHeadRow(1);
		EntityViewInfo view=new EntityViewInfo();
		FilterInfo filter=new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("sysType",MoneySysTypeEnum.SALEHOUSESYS_VALUE));
		view.setFilter(filter);
		IMoneyDefine iMoneyDefine=MoneyDefineFactory.getRemoteInstance();
		MoneyDefineCollection moneyDefineCollection=iMoneyDefine.getMoneyDefineCollection(view);
		if(moneyDefineCollection!=null && moneyDefineCollection.size()>0){
			//??????table
			//????
			IColumn subareaColumn= tblMain.addColumn();
			subareaColumn.setKey("subareaName");
			tblMain.getHeadRow(0).getCell("subareaName").setValue("????");
			tblMain.getHeadRow(1).getCell("subareaName").setValue("????");
			//????????
			IColumn proTypeColumn= tblMain.addColumn();
			proTypeColumn.setKey("proTypeName");
			tblMain.getHeadRow(0).getCell("proTypeName").setValue("????????");
			tblMain.getHeadRow(1).getCell("proTypeName").setValue("????????");
			//????????
			IColumn builProColumn= tblMain.addColumn();
			builProColumn.setKey("builProName");
			tblMain.getHeadRow(0).getCell("builProName").setValue("????????");
			tblMain.getHeadRow(1).getCell("builProName").setValue("????????");
			//????????
			IColumn customerColumn= tblMain.addColumn();
			customerColumn.setKey("customerNames");
			tblMain.getHeadRow(0).getCell("customerNames").setValue("????????");
			tblMain.getHeadRow(1).getCell("customerNames").setValue("????????");
			//????????
			IColumn sellProjectColumn= tblMain.addColumn();
			sellProjectColumn.setKey("sellProject");
			tblMain.getHeadRow(0).getCell("sellProject").setValue("????????");
			tblMain.getHeadRow(1).getCell("sellProject").setValue("????????");
			//????
			IColumn buildingColumn= tblMain.addColumn();
			buildingColumn.setKey("building");
			tblMain.getHeadRow(0).getCell("building").setValue("????");
			tblMain.getHeadRow(1).getCell("building").setValue("????");
			//????
			IColumn buildingUnitColumn= tblMain.addColumn();
			buildingUnitColumn.setKey("buildingUnit");
			tblMain.getHeadRow(0).getCell("buildingUnit").setValue("????");
			tblMain.getHeadRow(1).getCell("buildingUnit").setValue("????");
			//????ID
			IColumn roomIDColumn= tblMain.addColumn();
			roomIDColumn.setKey("roomID");
			roomIDColumn.getStyleAttributes().setHided(true);
			tblMain.getHeadRow(0).getCell("roomID").setValue("????ID");
			tblMain.getHeadRow(1).getCell("roomID").setValue("????ID");
			//????
			IColumn roomNumberColumn= tblMain.addColumn();
			roomNumberColumn.setKey("roomNumber");
			tblMain.getHeadRow(0).getCell("roomNumber").setValue("????");
			tblMain.getHeadRow(1).getCell("roomNumber").setValue("????");
			
			//??????????????????????????????
			List list=new ArrayList();
			//????????????????
			int ColumnIndex =0;
			
			//?????????????? ??????
			boolean hasPreMoney=false;
			for(int i =0;i<moneyDefineCollection.size();i++){
				MoneyDefineInfo info=moneyDefineCollection.get(i);
				if(info.getMoneyType().equals(MoneyTypeEnum.PreMoney)){
					hasPreMoney=true;
					IColumn column= tblMain.addColumn();
					column.setKey("moneyDefine"+info.getNumber());
					tblMain.getHeadRow(0).getCell("moneyDefine"+info.getNumber()).setValue("??????");
					tblMain.getHeadRow(1).getCell("moneyDefine"+info.getNumber()).setValue(info.getName());
					ColumnIndex++;
				}
			}
			list.add(new Integer(ColumnIndex));
			
			//?????????????? ??????????????????????????????????????
			ColumnIndex=0;
			for(int i =0;i<moneyDefineCollection.size();i++){
				MoneyDefineInfo info=moneyDefineCollection.get(i);
				if(info.getMoneyType().equals(MoneyTypeEnum.EarnestMoney)
						|| info.getMoneyType().equals(MoneyTypeEnum.FisrtAmount)
						|| info.getMoneyType().equals(MoneyTypeEnum.HouseAmount)
						|| info.getMoneyType().equals(MoneyTypeEnum.LoanAmount)
						|| info.getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
					hasPreMoney=true;
					IColumn column= tblMain.addColumn();
					column.setKey("moneyDefine"+info.getNumber());
					tblMain.getHeadRow(0).getCell("moneyDefine"+info.getNumber()).setValue("????");
					tblMain.getHeadRow(1).getCell("moneyDefine"+info.getNumber()).setValue(info.getName());
					ColumnIndex++;
				}
			}
			list.add(new Integer(ColumnIndex));
			
			//?????????????? ??????
			ColumnIndex=0;
			for(int i =0;i<moneyDefineCollection.size();i++){
				MoneyDefineInfo info=moneyDefineCollection.get(i);
				if(info.getMoneyType().equals(MoneyTypeEnum.PreconcertMoney)){
					hasPreMoney=true;
					IColumn column= tblMain.addColumn();
					column.setKey("moneyDefine"+info.getNumber());
					tblMain.getHeadRow(0).getCell("moneyDefine"+info.getNumber()).setValue("??????");
					tblMain.getHeadRow(1).getCell("moneyDefine"+info.getNumber()).setValue(info.getName());
					ColumnIndex++;
				}
			}
			list.add(new Integer(ColumnIndex));
			
			ColumnIndex=0;
			if(hasPreMoney){
				IColumn column= tblMain.addColumn();
				column.setKey("PreMoney");
				tblMain.getHeadRow(0).getCell("PreMoney").setValue("????????");
				tblMain.getHeadRow(1).getCell("PreMoney").setValue("????????");
				ColumnIndex=1001;
			}
			list.add(new Integer(ColumnIndex));
			
			//?????????????? ????????
			boolean hasReplaceFee=false;
			ColumnIndex=0;
			for(int i =0;i<moneyDefineCollection.size();i++){
				MoneyDefineInfo info=moneyDefineCollection.get(i);
				if(info.getMoneyType().equals(MoneyTypeEnum.ReplaceFee)){
					hasReplaceFee=true;
					IColumn column= tblMain.addColumn();
					column.setKey("moneyDefine"+info.getNumber());
					tblMain.getHeadRow(0).getCell("moneyDefine"+info.getNumber()).setValue("????????");
					tblMain.getHeadRow(1).getCell("moneyDefine"+info.getNumber()).setValue(info.getName());
					ColumnIndex++;
				}
			}
			list.add(new Integer(ColumnIndex));
			
			ColumnIndex=0;
			if(hasReplaceFee){
				IColumn column= tblMain.addColumn();
				column.setKey("ReplaceFee");
				tblMain.getHeadRow(0).getCell("ReplaceFee").setValue("????????????????");
				tblMain.getHeadRow(1).getCell("ReplaceFee").setValue("????????????????");
				ColumnIndex=1001;
			}
			list.add(new Integer(ColumnIndex));
			
			//?????????????? ??????
			boolean hasFitmentAmount=false;
			ColumnIndex=0;
			for(int i =0;i<moneyDefineCollection.size();i++){
				MoneyDefineInfo info=moneyDefineCollection.get(i);
				if(info.getMoneyType().equals(MoneyTypeEnum.FitmentAmount)){
					hasFitmentAmount=true;
					IColumn column= tblMain.addColumn();
					column.setKey("moneyDefine"+info.getNumber());
					tblMain.getHeadRow(0).getCell("moneyDefine"+info.getNumber()).setValue("??????");
					tblMain.getHeadRow(1).getCell("moneyDefine"+info.getNumber()).setValue(info.getName());
					ColumnIndex++;
				}
			}
			list.add(new Integer(ColumnIndex));
			
			ColumnIndex=0;
			if(hasFitmentAmount){
				IColumn column= tblMain.addColumn();
				column.setKey("FitmentAmount");
				tblMain.getHeadRow(0).getCell("FitmentAmount").setValue("??????????");
				tblMain.getHeadRow(1).getCell("FitmentAmount").setValue("??????????");
				ColumnIndex=1001;
			}
			list.add(new Integer(ColumnIndex));
			
			//?????????????? ??????
			boolean hasLateFee=false;
			ColumnIndex=0;
			for(int i =0;i<moneyDefineCollection.size();i++){
				MoneyDefineInfo info=moneyDefineCollection.get(i);
				if(info.getMoneyType().equals(MoneyTypeEnum.LateFee)){
					hasLateFee=true;
					IColumn column= tblMain.addColumn();
					column.setKey("moneyDefine"+info.getNumber());
					tblMain.getHeadRow(0).getCell("moneyDefine"+info.getNumber()).setValue("??????");
					tblMain.getHeadRow(1).getCell("moneyDefine"+info.getNumber()).setValue(info.getName());
					ColumnIndex++;
				}
			}
			list.add(new Integer(ColumnIndex));
			
			ColumnIndex=0;
			if(hasLateFee){
				IColumn column= tblMain.addColumn();
				column.setKey("LateFee");
				tblMain.getHeadRow(0).getCell("LateFee").setValue("??????????");
				tblMain.getHeadRow(1).getCell("LateFee").setValue("??????????");
				ColumnIndex=1001;
			}
			list.add(new Integer(ColumnIndex));
			
			//?????????????? ??????
			boolean hasCompensateAmount=false;
			ColumnIndex=0;
			for(int i =0;i<moneyDefineCollection.size();i++){
				MoneyDefineInfo info=moneyDefineCollection.get(i);
				if(info.getMoneyType().equals(MoneyTypeEnum.CompensateAmount)){
					hasCompensateAmount=true;
					IColumn column= tblMain.addColumn();
					column.setKey("moneyDefine"+info.getNumber());
					tblMain.getHeadRow(0).getCell("moneyDefine"+info.getNumber()).setValue("??????");
					tblMain.getHeadRow(1).getCell("moneyDefine"+info.getNumber()).setValue(info.getName());
					ColumnIndex++;
				}
			}
			list.add(new Integer(ColumnIndex));
			
			ColumnIndex=0;
			if(hasCompensateAmount){
				IColumn column= tblMain.addColumn();
				column.setKey("CompensateAmount");
				tblMain.getHeadRow(0).getCell("CompensateAmount").setValue("??????????");
				tblMain.getHeadRow(1).getCell("CompensateAmount").setValue("??????????");
				ColumnIndex=1001;
			}
			list.add(new Integer(ColumnIndex));
			
			//?????????????? ??????
			boolean hasCommissionCharge=false;
			ColumnIndex=0;
			for(int i =0;i<moneyDefineCollection.size();i++){
				MoneyDefineInfo info=moneyDefineCollection.get(i);
				if(info.getMoneyType().equals(MoneyTypeEnum.CommissionCharge)){
					hasCommissionCharge=true;
					IColumn column= tblMain.addColumn();
					column.setKey("moneyDefine"+info.getNumber());
					tblMain.getHeadRow(0).getCell("moneyDefine"+info.getNumber()).setValue("??????");
					tblMain.getHeadRow(1).getCell("moneyDefine"+info.getNumber()).setValue(info.getName());
					ColumnIndex++;
				}
			}
			list.add(new Integer(ColumnIndex));
			
			ColumnIndex=0;
			if(hasCommissionCharge){
				IColumn column= tblMain.addColumn();
				column.setKey("CommissionCharge");
				tblMain.getHeadRow(0).getCell("CommissionCharge").setValue("??????????");
				tblMain.getHeadRow(1).getCell("CommissionCharge").setValue("??????????");
				ColumnIndex=1001;
			}
			list.add(new Integer(ColumnIndex));
			
			//?????????????? ??????
			boolean hasElseAmount=false;
			ColumnIndex=0;
			for(int i =0;i<moneyDefineCollection.size();i++){
				MoneyDefineInfo info=moneyDefineCollection.get(i);
				if(info.getMoneyType().equals(MoneyTypeEnum.ElseAmount)){
					hasElseAmount=true;
					IColumn column= tblMain.addColumn();
					column.setKey("moneyDefine"+info.getNumber());
					tblMain.getHeadRow(0).getCell("moneyDefine"+info.getNumber()).setValue("????");
					tblMain.getHeadRow(1).getCell("moneyDefine"+info.getNumber()).setValue(info.getName());
					ColumnIndex++;
				}
			}
			list.add(new Integer(ColumnIndex));
			
			ColumnIndex=0;
			if(hasElseAmount){
				IColumn column= tblMain.addColumn();
				column.setKey("ElseAmount");
				tblMain.getHeadRow(0).getCell("ElseAmount").setValue("??????????");
				tblMain.getHeadRow(1).getCell("ElseAmount").setValue("??????????");
				ColumnIndex=1001;
			}
			list.add(new Integer(ColumnIndex));
			
			//?????????????? ????
//			boolean hasRefundment=false;
//			ColumnIndex=0;
//			for(int i =0;i<moneyDefineCollection.size();i++){
//				MoneyDefineInfo info=moneyDefineCollection.get(i);
//				if(info.getMoneyType().equals(MoneyTypeEnum.Refundment)){
//					hasRefundment=true;
//					IColumn column= tblMain.addColumn();
//					column.setKey("moneyDefine"+info.getNumber());
//					tblMain.getHeadRow(0).getCell("moneyDefine"+info.getNumber()).setValue("????");
//					tblMain.getHeadRow(1).getCell("moneyDefine"+info.getNumber()).setValue(info.getName());
//					ColumnIndex++;
//				}
//			}
//			list.add(new Integer(ColumnIndex));
//			
//			ColumnIndex=0;
//			if(hasRefundment){
//				IColumn column= tblMain.addColumn();
//				column.setKey("Refundment");
//				tblMain.getHeadRow(0).getCell("Refundment").setValue("????????");
//				tblMain.getHeadRow(1).getCell("Refundment").setValue("????????");
//				ColumnIndex=1001;
//			}
//			list.add(new Integer(ColumnIndex));
			
			//????????
			IColumn sumColumn= tblMain.addColumn();
			sumColumn.setKey("sum");
			tblMain.getHeadRow(0).getCell("sum").setValue("????????");
			tblMain.getHeadRow(1).getCell("sum").setValue("????????");
			list.add(new Integer(1001));
			
			//????????????
			boolean hasSettlementType=false;
			ColumnIndex=0;
			ISettlementType is=SettlementTypeFactory.getRemoteInstance();
			SettlementTypeCollection seTypeColl=is.getSettlementTypeCollection();
			if(seTypeColl!=null && seTypeColl.size()>0){
				hasSettlementType=true;
				for(int i =0;i<seTypeColl.size();i++){
					SettlementTypeInfo info=seTypeColl.get(i);
					IColumn column= tblMain.addColumn();
					column.setKey("settlementType"+info.getNumber());
					tblMain.getHeadRow(0).getCell("settlementType"+info.getNumber()).setValue("????????");
					tblMain.getHeadRow(1).getCell("settlementType"+info.getNumber()).setValue(info.getName());
					ColumnIndex++;
				}
			}
			list.add(new Integer(ColumnIndex));
			
			ColumnIndex=0;
			if(hasSettlementType){
				IColumn column= tblMain.addColumn();
				column.setKey("SettlementType");
				tblMain.getHeadRow(0).getCell("SettlementType").setValue("????????");
				tblMain.getHeadRow(1).getCell("SettlementType").setValue("????????");
				ColumnIndex=1001;
			}
			list.add(new Integer(ColumnIndex));
			
			// ????????????
			tblMain.getHeadMergeManager().setMergeMode(KDTMergeManager.FREE_ROW_MERGE);
			// ??????????????????
			KDTMergeManager mm = tblMain.getHeadMergeManager();
			// ????????????
			for(int i = 0 ; i <9;i++){
				mm.mergeBlock(0, i, 1, i, KDTMergeManager.SPECIFY_MERGE);
			}
			int sumIndex=0;
			for(int i = 0 ; i <list.size();i++){
				Integer index=(Integer) list.get(i);
				if(index.intValue()>1000){
					int newIndex=index.intValue()-1000;
					mm.mergeBlock(0, 9+sumIndex, 1, 8+sumIndex+newIndex, KDTMergeManager.SPECIFY_MERGE);
					sumIndex+=newIndex;
				}else{
					mm.mergeBlock(0, 9+sumIndex, 0, 8+sumIndex+index.intValue(), KDTMergeManager.SPECIFY_MERGE);
					sumIndex+=index.intValue();
				}
			}
			
			//????????????????
			KDFormattedTextField formattedTextField = new KDFormattedTextField(KDFormattedTextField.DECIMAL);
			formattedTextField.setPrecision(2);
			formattedTextField.setSupportedEmpty(true);
			formattedTextField.setNegatived(true);
			ICellEditor numberEditor = new KDTDefaultCellEditor(formattedTextField);
			for(int i =9;i<tblMain.getColumnCount();i++){
				this.tblMain.getColumn(i).setEditor(numberEditor);
				this.tblMain.getColumn(i).getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
				this.tblMain.getColumn(i).getStyleAttributes().setNumberFormat(FDCHelper.getNumberFtm(2));
			}
			
			
			//???????????????? ?? ????????
			DefaultKingdeeTreeNode orgNode = (DefaultKingdeeTreeNode) treeMain
					.getLastSelectedPathComponent();
			if (orgNode == null) {
				return;
			}
			String saleLongNumber =null;
			String sellProjectID=null;
			if (orgNode.getUserObject() instanceof OrgStructureInfo){
				final OrgStructureInfo info = (OrgStructureInfo)orgNode.getUserObject();
				FullOrgUnitInfo fullOrgUnitInfo = info.getUnit();
				saleLongNumber=fullOrgUnitInfo.getLongNumber();
			}
			if (orgNode.getUserObject() instanceof SellProjectInfo){
				final SellProjectInfo sellProjectInfo = (SellProjectInfo)orgNode.getUserObject();
				sellProjectID=sellProjectInfo.getId().toString();
			}
			
			//TODO ?????????????????????? bizDate ????????,??????????????????
			Map filterMap = CommerceHelper.convertFilterItemCollToMap(this.mainQuery.getFilter());
			String beginTime="";
			String endTime="";
			if(filterMap.get("BeginQueryDate")!=null){
				Timestamp BeginQueryDate = (Timestamp) filterMap.get("BeginQueryDate");
				beginTime=BeginQueryDate.toString().substring(0, 10);
			}
			if(filterMap.get("EndQueryDate")!=null){
				Timestamp EndQueryDate = (Timestamp) filterMap.get("EndQueryDate");
				endTime=EndQueryDate.toString().substring(0, 10);
			}
			
			//????????????????????????
			IFinanceSubscribeUIFacade iFinance=FinanceSubscribeUIFacadeFactory.getRemoteInstance();
			IRowSet rowSet= iFinance.getSubscribe(saleLongNumber, sellProjectID, beginTime, endTime);
			while(rowSet.next()){
				String moneyType =rowSet.getString("moneyType");
				String moneyDefNumber=rowSet.getString("moneyDefNumber");
				String settTypeNumber=rowSet.getString("settTypeNumber");
				if(tblMain.getRowCount()==0){
					IRow row= tblMain.addRow();
					tblMain.setRowCount(tblMain.getRowCount()+1);
					row.getCell("subareaName").setValue(rowSet.getString("subareaName"));
					row.getCell("proTypeName").setValue(rowSet.getString("proTypeName"));
					row.getCell("builProName").setValue(rowSet.getString("builProName"));
					row.getCell("customerNames").setValue(rowSet.getString("customerNames"));
					row.getCell("sellProject").setValue(rowSet.getString("sellProject"));
					row.getCell("building").setValue(rowSet.getString("building"));
					row.getCell("buildingUnit").setValue(rowSet.getString("buildingUnit"));
					row.getCell("roomID").setValue(rowSet.getString("roomID"));
					row.getCell("roomNumber").setValue(rowSet.getString("roomNumber"));
					//??????????????????
					if(row.getCell("moneyDefine"+moneyDefNumber)!=null){
						row.getCell("moneyDefine"+moneyDefNumber).setValue(rowSet.getString("amount"));
					}
					
					//????????????????
					if(moneyType!=null){
						String sumCell=getColumnMoneyType(moneyType);
						if(!sumCell.equals("")){
							row.getCell(sumCell).setValue(rowSet.getString("amount"));
						}
					}
					
					//????????????
					if(rowSet.getString("amount")!=null){
						if(row.getCell("sum")!=null){
							row.getCell("sum").setValue(rowSet.getString("amount"));
						}
					}
					
					//??????????????????
					if(row.getCell("settlementType"+settTypeNumber)!=null){
						row.getCell("settlementType"+settTypeNumber).setValue(rowSet.getString("amount"));
						if(rowSet.getString("amount")!=null){
							BigDecimal setAmount=new BigDecimal(rowSet.getString("amount"));
							if(row.getCell("SettlementType")!=null){
								BigDecimal setOldAmount=new BigDecimal(0);
								if(row.getCell("SettlementType").getValue()!=null){
									setOldAmount=new BigDecimal(row.getCell("SettlementType").getValue().toString());
								}
								row.getCell("SettlementType").setValue(setOldAmount.add(setAmount));
							}
						}
					}
				}else{
					boolean has=false;
					IRow oldRow=null;
					String newRoomID=rowSet.getString("roomID");
					for(int i=0;i<tblMain.getRowCount();i++){
						IRow row=tblMain.getRow(i);
						String oldRoomID="";
						if(row.getCell("roomID").getValue()!=null){
							oldRoomID=row.getCell("roomID").getValue().toString();
						}
						if(newRoomID!=null && !newRoomID.equals("") && oldRoomID.equals(newRoomID)){
							has=true;
							oldRow=row;
						}
					}
					if(has){
						//??????????????????
						BigDecimal oldAmount=new BigDecimal(0);
						BigDecimal amount=new BigDecimal(0);
						if(rowSet.getString("amount")!=null){
							amount=new BigDecimal(rowSet.getString("amount"));
						}
						if(oldRow.getCell("moneyDefine"+moneyDefNumber)!=null){
							if(oldRow.getCell("moneyDefine"+moneyDefNumber).getValue()!=null){
								oldAmount=new BigDecimal(oldRow.getCell("moneyDefine"+moneyDefNumber).getValue().toString());
							}
							oldRow.getCell("moneyDefine"+moneyDefNumber).setValue(oldAmount.add(amount));
						}
						
						//????????????????
						if(moneyType!=null){
							BigDecimal oldSumAmount=new BigDecimal(0);
							String sumCell=getColumnMoneyType(moneyType);
							if(!sumCell.equals("")){
								if(oldRow.getCell(sumCell).getValue()!=null){
									oldSumAmount =new BigDecimal(oldRow.getCell(sumCell).getValue().toString());
								}
								oldRow.getCell(sumCell).setValue(oldSumAmount.add(amount));
							}
						}
						
						//????????????
						if(rowSet.getString("amount")!=null){
							BigDecimal setAmount=new BigDecimal(rowSet.getString("amount"));
							if(oldRow.getCell("sum")!=null){
								BigDecimal setOldAmount=new BigDecimal(0);
								if(oldRow.getCell("sum").getValue()!=null){
									setOldAmount=new BigDecimal(oldRow.getCell("sum").getValue().toString());
								}
								oldRow.getCell("sum").setValue(setOldAmount.add(setAmount));
							}
						}
						
						//??????????????????
						BigDecimal settlementTypeAmount=new BigDecimal(0);
						if(oldRow.getCell("settlementType"+settTypeNumber)!=null){
							if(oldRow.getCell("settlementType"+settTypeNumber).getValue()!=null){
								settlementTypeAmount=new BigDecimal(oldRow.getCell("settlementType"+settTypeNumber).getValue().toString());
							}
							oldRow.getCell("settlementType"+settTypeNumber).setValue(settlementTypeAmount.add(amount));
							if(rowSet.getString("amount")!=null){
								BigDecimal setAmount=new BigDecimal(rowSet.getString("amount"));
								if(oldRow.getCell("SettlementType")!=null){
									BigDecimal setOldAmount=new BigDecimal(0);
									if(oldRow.getCell("SettlementType").getValue()!=null){
										setOldAmount=new BigDecimal(oldRow.getCell("SettlementType").getValue().toString());
									}
									oldRow.getCell("SettlementType").setValue(setOldAmount.add(setAmount));
								}
							}
						}
						
						//????????????????????????????????????????????????????
//						BigDecimal tranAmount=new BigDecimal(0);
//						BigDecimal tranoldAmount=new BigDecimal(0);
//						if(rowSet.getString("tranAmount")!=null){
//							tranAmount=new BigDecimal(rowSet.getString("tranAmount"));
//						}
//						if(tranAmount.compareTo(new BigDecimal(0))!=0){
//							String tranMoneyDefNumber =rowSet.getString("tranMoneyDefNumber");
//							if(oldRow.getCell("moneyDefine"+tranMoneyDefNumber)!=null){
//								if(oldRow.getCell("moneyDefine"+tranMoneyDefNumber).getValue()!=null){
//									tranoldAmount=new BigDecimal(oldRow.getCell("moneyDefine"+tranMoneyDefNumber).getValue().toString());
//								}
//								oldRow.getCell("moneyDefine"+tranMoneyDefNumber).setValue(tranoldAmount.subtract(tranAmount));
//							}
//						}
//						String tranMoneyType =rowSet.getString("tranMoneyType");
//						if(tranMoneyType!=null){
//							BigDecimal oldSumAmount=new BigDecimal(0);
//							String sumCell=getColumnMoneyType(tranMoneyType);
//							if(!sumCell.equals("")){
//								if(oldRow.getCell(sumCell).getValue()!=null){
//									oldSumAmount =new BigDecimal(oldRow.getCell(sumCell).getValue().toString());
//								}
//								oldRow.getCell(sumCell).setValue(oldSumAmount.subtract(tranAmount));
//							}
//						}
					}else{
						IRow row= tblMain.addRow();
						tblMain.setRowCount(tblMain.getRowCount()+1);
						row.getCell("subareaName").setValue(rowSet.getString("subareaName"));
						row.getCell("proTypeName").setValue(rowSet.getString("proTypeName"));
						row.getCell("builProName").setValue(rowSet.getString("builProName"));
						row.getCell("customerNames").setValue(rowSet.getString("customerNames"));
						row.getCell("sellProject").setValue(rowSet.getString("sellProject"));
						row.getCell("building").setValue(rowSet.getString("building"));
						row.getCell("buildingUnit").setValue(rowSet.getString("buildingUnit"));
						row.getCell("roomID").setValue(rowSet.getString("roomID"));
						row.getCell("roomNumber").setValue(rowSet.getString("roomNumber"));
						//??????????????????
						if(row.getCell("moneyDefine"+moneyDefNumber)!=null){
							row.getCell("moneyDefine"+moneyDefNumber).setValue(rowSet.getString("amount"));
						}
						
						//????????????????
						if(moneyType!=null){
							String sumCell=getColumnMoneyType(moneyType);
							if(!sumCell.equals("")){
								row.getCell(sumCell).setValue(rowSet.getString("amount"));
							}
						}
						
						//????????????
						if(rowSet.getString("amount")!=null){
							if(row.getCell("sum")!=null){
								row.getCell("sum").setValue(rowSet.getString("amount"));
							}
						}
						
						//??????????????????
						if(row.getCell("settlementType"+settTypeNumber)!=null){
							row.getCell("settlementType"+settTypeNumber).setValue(rowSet.getString("amount"));
							if(rowSet.getString("amount")!=null){
								BigDecimal setAmount=new BigDecimal(rowSet.getString("amount"));
								if(row.getCell("SettlementType")!=null){
									BigDecimal setOldAmount=new BigDecimal(0);
									if(row.getCell("SettlementType").getValue()!=null){
										setOldAmount=new BigDecimal(row.getCell("SettlementType").getValue().toString());
									}
									row.getCell("SettlementType").setValue(setOldAmount.add(setAmount));
								}
							}
						}
					}
				}
			}
			
			//??????????
            IRow footRow=null;
			KDTFootManager footRowManager= tblMain.getFootManager();
			footRowManager = new KDTFootManager(this.tblMain);
	        footRowManager.addFootView();
	        this.tblMain.setFootManager(footRowManager);
	        footRow= footRowManager.addFootRow(0);
	        footRow.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.getAlignment("right"));
	        this.tblMain.getIndexColumn().setWidthAdjustMode(KDTIndexColumn.WIDTH_MANUAL);
	        this.tblMain.getIndexColumn().setWidth(30);
	        footRowManager.addIndexText(0, "????");
	        footRow.getStyleAttributes().setBackground(new Color(0xf6, 0xf6, 0xbf));
	        for(int i =0;i<tblMain.getRowCount();i++){
	        	IRow row =tblMain.getRow(i);
	        	for(int j=9;j<tblMain.getColumnCount();j++){
	        		BigDecimal oldAmount=new BigDecimal(0);
	        		if(footRow.getCell(j).getValue()!=null){
	        			oldAmount=new BigDecimal(footRow.getCell(j).getValue().toString());
	        		}
	        		BigDecimal newAmount=new BigDecimal(0);
	        		if(row.getCell(j).getValue()!=null){
	        			newAmount=new BigDecimal(row.getCell(j).getValue().toString());
	        		}
	        		footRow.getCell(j).setValue(oldAmount.add(newAmount));
	        	}
	        }
		}
	}
	
	/**
	 * ???????????? ????????????????key
	 * @param moneyType
	 * @return
	 */
	public String getColumnMoneyType(String moneyType){
		if(moneyType.equals(MoneyTypeEnum.PREMONEY_VALUE)
				|| moneyType.equals(MoneyTypeEnum.EARNESTMONEY_VALUE)
				|| moneyType.equals(MoneyTypeEnum.FISRTAMOUNT_VALUE)
				|| moneyType.equals(MoneyTypeEnum.HOUSEAMOUNT_VALUE)
				|| moneyType.equals(MoneyTypeEnum.LOANAMOUNT_VALUE)
				|| moneyType.equals(MoneyTypeEnum.ACCFUNDAMOUNT_VALUE)
				|| moneyType.equals(MoneyTypeEnum.PRECONCERTMONEY_VALUE)){	
			return "PreMoney";//????????
		}else if(moneyType.equals(MoneyTypeEnum.REPLACEFEE_VALUE)){
			return "ReplaceFee";//????????????????
		}else if(moneyType.equals(MoneyTypeEnum.FITMENTAMOUNT_VALUE)){
			return "FitmentAmount";//??????????
		}else if(moneyType.equals(MoneyTypeEnum.LATEFEE_VALUE)){
			return "LateFee";//??????????
		}else if(moneyType.equals(MoneyTypeEnum.COMPENSATEAMOUNT_VALUE)){
			return "CompensateAmount";//??????????
		}else if(moneyType.equals(MoneyTypeEnum.COMMISSIONCHARGE_VALUE)){
			return "CommissionCharge";//??????????
		}else if(moneyType.equals(MoneyTypeEnum.ELSEAMOUNT_VALUE)){
			return "ElseAmount";//??????????
		}else if(moneyType.equals(MoneyTypeEnum.REFUNDMENT_VALUE)){
			return "Refundment";//????????
		}
		return "";
	}
	
	protected void execQuery() {
		try {
			initTblMain();
		} catch (EASBizException e) {
			e.printStackTrace();
		} catch (BOSException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	protected boolean isIgnoreCUFilter() {
		return true;
	}
	
	protected String getKeyFieldName() {
		return "subareaName";
	}
	
	protected void tblMain_tableClicked(KDTMouseEvent e) throws Exception {
	}
	
	protected void tblMain_tableSelectChanged(KDTSelectEvent e)
			throws Exception {
	}
}