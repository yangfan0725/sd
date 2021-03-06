/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTDataRequestManager;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectBlock;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.base.codingrule.CodingRuleException;
import com.kingdee.eas.base.codingrule.CodingRuleManagerFactory;
import com.kingdee.eas.base.codingrule.ICodingRuleManager;
import com.kingdee.eas.base.codingrule.client.CodingRuleIntermilNOBox;
import com.kingdee.eas.basedata.org.OrgConstants;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.fdc.basecrm.client.CRMTreeHelper;
import com.kingdee.eas.fdc.basedata.MoneySysTypeEnum;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedApproachEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryCollection;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedDataEntryInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedFactory;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedInfo;
import com.kingdee.eas.fdc.sellhouse.AFMortgagedStateEnum;
import com.kingdee.eas.fdc.sellhouse.BizListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.BizListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.BizNewFlowEnum;
import com.kingdee.eas.fdc.sellhouse.MoneyDefineInfo;
import com.kingdee.eas.fdc.sellhouse.MoneyTypeEnum;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.PurPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageFactory;
import com.kingdee.eas.fdc.sellhouse.PurchaseManageInfo;
import com.kingdee.eas.fdc.sellhouse.RoomDisplaySetting;
import com.kingdee.eas.fdc.sellhouse.RoomInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanAFMEntrysInfo;
import com.kingdee.eas.fdc.sellhouse.RoomLoanCollection;
import com.kingdee.eas.fdc.sellhouse.RoomLoanFactory;
import com.kingdee.eas.fdc.sellhouse.RoomLoanInfo;
import com.kingdee.eas.fdc.sellhouse.RoomSellStateEnum;
import com.kingdee.eas.fdc.sellhouse.SHEManageHelper;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeFactory;
import com.kingdee.eas.fdc.sellhouse.SHEPayTypeInfo;
import com.kingdee.eas.fdc.sellhouse.SellProjectInfo;
import com.kingdee.eas.fdc.sellhouse.SignManageFactory;
import com.kingdee.eas.fdc.sellhouse.SignManageInfo;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryCollection;
import com.kingdee.eas.fdc.sellhouse.SignPayListEntryInfo;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewCollection;
import com.kingdee.eas.fdc.sellhouse.TranBusinessOverViewInfo;
import com.kingdee.eas.fdc.sellhouse.TransactionCollection;
import com.kingdee.eas.fdc.sellhouse.TransactionFactory;
import com.kingdee.eas.fdc.sellhouse.TransactionInfo;
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.util.DateTimeUtils;
import com.kingdee.util.UuidException;

/**
 * output class name
 */
public class RoomLoanProcessUI extends AbstractRoomLoanProcessUI {
	private static final Logger logger = CoreUIObject.getLogger(RoomLoanProcessUI.class);
	
	RoomDisplaySetting setting = new RoomDisplaySetting();
	
	/**
	 * ????????????????????????
	 */
	private Color colorOfReady = new Color(255, 128, 128);
	
	/**
	 * ????????????????????????
	 */
	private Color colorOfFinish =  new Color(255, 255, 128);
	
	/**
	 * ?????????????????????????? 
	 */
	private Color colorOfNoNeed =  new Color(128, 255, 128);
	
	/**
	 * ??????????
	 */
	private final int moneyTypeOfLoan = 0;
	
	/**
	 * ????????????
	 */
	private final int moneyTypeOfAfm = 1;
	
	/**
	 * ??????????????????????
	 */
	private final int moneyTypeOfLoanAndAfm = 2;
	
	/**
	 * ????????????????????????????????????????
	 */
	private HashMap moneyDefineMap = new HashMap();
	
	/**
	 * ????????????????????????????????????????
	 */
	private HashMap billObjectMap = new HashMap();

	public RoomLoanProcessUI() throws Exception {
		super();
	}

	public void storeFields() {
		super.storeFields();
	}
	
	public void onLoad() throws Exception {
		initControl();
		super.onLoad();

		moneyDefineMap.clear();
		billObjectMap.clear();
		
		this.actionBillAdjust.setVisible(false);
	}

	protected void tblMain_tableClicked(com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent e)
			throws Exception {
		if (e.getButton() == 1) {
			RoomInfo room = this.getSelectRoom();
			if(room == null){
				return ;
			}
		}
	}

	protected void tblMain_tableSelectChanged(
			com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent e)
			throws Exception {
		RoomInfo room = this.getSelectRoom();
		if(room == null){
			return ;
		}

		if (room != null) {
			this.fillRoomDetail(room);
		}
	}
	
	protected void initTree() throws Exception {
		this.tblMain.getDataRequestManager().setDataRequestMode(KDTDataRequestManager.REAL_MODE);
		//this.treeMain.setModel(FDCTreeHelper.getPlanisphereForSHE(this.actionOnLoad, MoneySysTypeEnum.SalehouseSys));
		SellProjectInfo info = new SellProjectInfo();
		if(this.getUIContext().get("selectProject")!=null){
			info = (SellProjectInfo)this.getUIContext().get("selectProject");
		}
		this.treeMain.setModel(CRMTreeHelper.getBuildingTree(info, true));
		this.treeMain.expandOnLevel(3);
		this.tblMain.getSelectManager().setSelectMode(KDTSelectManager.MULTIPLE_CELL_SELECT);
	}

	protected void treeMain_valueChanged(javax.swing.event.TreeSelectionEvent e)
			throws Exception {
		refresh(null);
	}
	
	protected void refresh(ActionEvent e) throws Exception {
		DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode) treeMain.getLastSelectedPathComponent();
		if (node == null || node.getUserObject() instanceof OrgStructureInfo) {
			return;
		}
		DefaultKingdeeTreeNode newNode = (DefaultKingdeeTreeNode) node.clone();
		CommerceHelper.cloneTree(newNode, node);
		newNode.setParent((DefaultKingdeeTreeNode) node.getParent()); // ??????????????????????????????
		CommerceHelper.removePlanisphereNode(newNode);
		SHEHelper.fillRoomTableByNode(this.tblMain, newNode, MoneySysTypeEnum.SalehouseSys, null, setting);
		
		//????????????
		this.setRoomColor();
	}
	
	protected String getLongNumberFieldName() {
		return "number";
	}
	
	protected void btnYes_actionPerformed(ActionEvent e) throws Exception {
		ArrayList roomList = new ArrayList();
		
		ArrayList blockList = this.tblMain.getSelectManager().getBlocks();
		if(blockList==null || blockList.isEmpty()){
			return;
		}
		else if(blockList.size() > 1){  //ctrl+??????????block
			for(int i=0; i<blockList.size(); i++){
				ArrayList selectRoomList = this.getSelectRoomListByBlock(((KDTSelectBlock)blockList.get(i)));
				if(selectRoomList!=null && !selectRoomList.isEmpty()){
					for(int r=0; r<selectRoomList.size(); r++){
						roomList.add(selectRoomList.get(r));
					}
				}
			}
		}
		else{  //????????????
			KDTSelectBlock block = (KDTSelectBlock)blockList.get(0);
			ArrayList selectRoomList = this.getSelectRoomListByBlock(block);
			if(selectRoomList!=null && !selectRoomList.isEmpty()){
				for(int r=0; r<selectRoomList.size(); r++){
					roomList.add(selectRoomList.get(r));
				}
			}
		}
		
		if(roomList!=null && !roomList.isEmpty()){  //????????
			for(int i=0; i<roomList.size(); i++){
				RoomInfo room = (RoomInfo)roomList.get(i);
				TransactionInfo transactionInfo = this.getTransactionInfo(room);  //????????????????
				
				int moneyType = this.checkRoomMoneyType(room);
				if(moneyType < 0){
					FDCMsgBox.showInfo("??????????????????????????????????????????");
					SysUtil.abort();
				}
				
				if(this.moneyTypeOfLoanAndAfm == moneyType){  //??????????????????????????????????????????
					//??????????????
					RoomLoanInfo roomAfmLoan = new RoomLoanInfo();
					roomAfmLoan.setRoom(room);
					roomAfmLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
					roomAfmLoan.setMmType((MoneyDefineInfo)this.moneyDefineMap.get(new Integer(this.moneyTypeOfAfm)));
					roomAfmLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
					//??????????????????????
					String number ="";
					number = createNumber();
					if(number!=null && !"".equals(number)){
						roomAfmLoan.setNumber(number);
					}
					else{
						FDCMsgBox.showInfo("??????????????");
						SysUtil.abort();
					}
					if(this.billObjectMap.get("sign") != null){
						roomAfmLoan.setSign((SignManageInfo)this.billObjectMap.get("sign"));
						roomAfmLoan.setLoanBank(((SignManageInfo)this.billObjectMap.get("sign")).getAcfBank());
					}
					else if(this.billObjectMap.get("purchase") != null){
						roomAfmLoan.setPurchase((PurchaseManageInfo)this.billObjectMap.get("purchase"));
					}
					//??????????????
					this.initLoanData(roomAfmLoan);
					
					//??????????????????????
					SHEManageHelper.updateTransactionOverView(null, roomAfmLoan.getRoom(), SHEManageHelper.ACCFUND,
							roomAfmLoan.getPromiseDate(), null, false);
					
					RoomLoanFactory.getRemoteInstance().save(roomAfmLoan);
					
					//????????????
					RoomLoanInfo roomLoan = new RoomLoanInfo();
					roomLoan.setRoom(room);
					//??????????????????????
					number ="";
					number = createNumber();
					if(number!=null && !"".equals(number)){
						roomLoan.setNumber(number);
					}
					else{
						FDCMsgBox.showInfo("??????????????");
						SysUtil.abort();
					}
					roomLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
					roomLoan.setMmType((MoneyDefineInfo)this.moneyDefineMap.get(new Integer(this.moneyTypeOfLoan)));
					roomLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
					if(this.billObjectMap.get("sign") != null){
						roomLoan.setSign((SignManageInfo)this.billObjectMap.get("sign"));
						roomAfmLoan.setLoanBank(((SignManageInfo)this.billObjectMap.get("sign")).getLoanBank());
					}
					else if(this.billObjectMap.get("purchase") != null){
						roomLoan.setPurchase((PurchaseManageInfo)this.billObjectMap.get("purchase"));
					}
					
					//??????????????
					this.initLoanData(roomLoan);
					
					//??????????????????????
					SHEManageHelper.updateTransactionOverView(null, roomLoan.getRoom(), SHEManageHelper.MORTGAGE,
							roomLoan.getPromiseDate(), null, false);
					
					RoomLoanFactory.getRemoteInstance().save(roomLoan);
				}
				else if(this.moneyTypeOfAfm == moneyType){  //??????
					RoomLoanInfo roomLoan = new RoomLoanInfo();
					roomLoan.setRoom(room);
					//??????????????????????
					String number ="";
					number = createNumber();
					if(number!=null && !"".equals(number)){
						roomLoan.setNumber(number);
					}
					else{
						FDCMsgBox.showInfo("??????????????");
						SysUtil.abort();
					}
					roomLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
					roomLoan.setMmType((MoneyDefineInfo)this.moneyDefineMap.get(new Integer(this.moneyTypeOfAfm)));
					roomLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
					if(this.billObjectMap.get("sign") != null){
						roomLoan.setSign((SignManageInfo)this.billObjectMap.get("sign"));
						roomLoan.setLoanBank(((SignManageInfo)this.billObjectMap.get("sign")).getAcfBank());
					}
					else if(this.billObjectMap.get("purchase") != null){
						roomLoan.setPurchase((PurchaseManageInfo)this.billObjectMap.get("purchase"));
					}
					
					//??????????????
					this.initLoanData(roomLoan);
					
					//??????????????????????
					SHEManageHelper.updateTransactionOverView(null, roomLoan.getRoom(), SHEManageHelper.ACCFUND,
							roomLoan.getPromiseDate(), null, false);
					
					RoomLoanFactory.getRemoteInstance().save(roomLoan);
				}
				else if(this.moneyTypeOfLoan == moneyType){  //????
					RoomLoanInfo roomLoan = new RoomLoanInfo();
					roomLoan.setRoom(room);
					//??????????????????????
					String number ="";
					number = createNumber();
					if(number!=null && !"".equals(number)){
						roomLoan.setNumber(number);
					}
					else{
						FDCMsgBox.showInfo("??????????????");
						SysUtil.abort();
					}
					roomLoan.setAFMortgagedState(AFMortgagedStateEnum.UNTRANSACT);
					roomLoan.setMmType((MoneyDefineInfo)this.moneyDefineMap.get(new Integer(this.moneyTypeOfLoan)));
					roomLoan.setCreator(SysContext.getSysContext().getCurrentUserInfo());
					if(this.billObjectMap.get("sign") != null){
						roomLoan.setSign((SignManageInfo)this.billObjectMap.get("sign"));
						roomLoan.setLoanBank(((SignManageInfo)this.billObjectMap.get("sign")).getLoanBank());
					}
					else if(this.billObjectMap.get("purchase") != null){
						roomLoan.setPurchase((PurchaseManageInfo)this.billObjectMap.get("purchase"));
					}
					
					//??????????????
					this.initLoanData(roomLoan);
					
					//??????????????????????
					SHEManageHelper.updateTransactionOverView(null, roomLoan.getRoom(), SHEManageHelper.MORTGAGE,
							roomLoan.getPromiseDate(), null, false);
					
					RoomLoanFactory.getRemoteInstance().save(roomLoan);
				}
			}
			
			FDCMsgBox.showInfo("????????");
			
			this.billObjectMap.clear();
			this.actionRefresh_actionPerformed(null);
			
			//??????????
			try{
				RoomLoanListUI parentListUI = (RoomLoanListUI)getUIContext().get("Owner");
				parentListUI.refresh(null);
			}catch(Exception ex){
				logger.error(ex);
			}
		}
	}
	
	private String createNumber() throws BOSException, CodingRuleException,
			EASBizException {
		String number = "";
		String currentOrgId = SysContext.getSysContext().getCurrentOrgUnit()
				.getId().toString();
		ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory
				.getRemoteInstance();
		//if (!"ADDNEW".equals(this.oprtState)) {
			//return number;
		//}
		boolean isExist = true;
		RoomLoanInfo objValue = new RoomLoanInfo();
		objValue.setCU(SysContext.getSysContext().getCurrentCtrlUnit());
		if (currentOrgId.length() == 0
				|| !iCodingRuleManager.isExist(objValue, currentOrgId)) {
			currentOrgId = FDCClientHelper.getCurrentOrgId();
			if (!iCodingRuleManager.isExist(objValue, currentOrgId)) {
				isExist = false;
			}
		}

		if (isExist) {
			boolean isAddView = FDCClientHelper.isCodingRuleAddView(objValue,
					currentOrgId);
			if (isAddView) {
				number = getRuleNumber(objValue, currentOrgId);
			} else {
				// String number = null;
				if (iCodingRuleManager.isUseIntermitNumber(objValue,
						currentOrgId)) {
					if (iCodingRuleManager.isUserSelect(objValue, currentOrgId)) {
						CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
								objValue, currentOrgId, null, null);
						Object object = null;
						if (iCodingRuleManager
								.isDHExist(objValue, currentOrgId)) {
							intermilNOF7.show();
							object = intermilNOF7.getData();
						}
						if (object != null) {
							number = object.toString();
						}
					} else {
						// ??????????????????????????????????????????????????????????????????
						number = iCodingRuleManager.getNumber(objValue,
								currentOrgId);
					}
				}else{
					number = iCodingRuleManager.getNumber(objValue,
							currentOrgId);
				}
			}
		}
		return number;
	}
	
	private String getRuleNumber(IObjectValue caller, String orgId){
		
		 String number = "";
		 
		 try {
	            ICodingRuleManager iCodingRuleManager = CodingRuleManagerFactory.getRemoteInstance();
	            if (orgId == null || orgId.trim().length() == 0)
	            {
	            	//????????????????????????????????????????????
	                 orgId = OrgConstants.DEF_CU_ID;
	            }
	            if (iCodingRuleManager.isExist(caller, orgId))
	            {
	               
	                if (iCodingRuleManager.isUseIntermitNumber(caller, orgId))
	                { // ??????orgId??????1????orgId??????????????????????????????????????
	                    if (iCodingRuleManager.isUserSelect(caller, orgId))
	                    {
	                        // ??????????????????,??????????????????????????
	                        // KDBizPromptBox pb = new KDBizPromptBox();
	                        CodingRuleIntermilNOBox intermilNOF7 = new CodingRuleIntermilNOBox(
	                                caller, orgId, null, null);
	                        // pb.setSelector(intermilNOF7);
	                        // ??????????????????,????????,????????//////////////////////////////////////////
	                        Object object = null;
	                        if (iCodingRuleManager.isDHExist(caller, orgId))
	                        {
	                            intermilNOF7.show();
	                            object = intermilNOF7.getData();
	                        }
	                        if (object != null)
	                        {
	                            number = object.toString();
	                        }
	                        else
	                        {
	                            // ????????????????????????,????getNumber????????,??????????read???????????????????????????get!
	                            number = iCodingRuleManager
	                                    .getNumber(caller, orgId);
	                        }
	                    }
	                    else
	                    {
	                        // ??????????????????????????????????????????????????????????????????
	                        number = iCodingRuleManager.getNumber(caller, orgId);
	                    }
	                }
	                else
	                {
	                    // ??????????????????????????????????????????????????
	                    number = iCodingRuleManager.getNumber(caller, orgId);
	                }
	            }
      
	        } catch (Exception err) {
	            //??????????????????????????????????????
	           //handleCodingRuleError(err);
	        }
	        
	      return number;
	}
	
	/**
	 * ????????????????????????????????????????????
	 * @param roomLoan
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void initLoanData(RoomLoanInfo roomLoan) throws EASBizException, BOSException{
		String payTypeId = null;
		if(roomLoan.getSign() != null && roomLoan.getSign().getPayType().getId() != null){  //????
			payTypeId = roomLoan.getSign().getPayType().getId().toString();
		}
		else if(roomLoan.getPurchase() != null && roomLoan.getPurchase().getPayType().getId() != null){  //????
			payTypeId = roomLoan.getPurchase().getPayType().getId().toString();
		}
		
		if(payTypeId != null){
			//????????????
			SelectorItemCollection selector = new SelectorItemCollection();
			selector.add(new SelectorItemInfo("isLoan"));
			selector.add(new SelectorItemInfo("loanBank.*"));
			selector.add(new SelectorItemInfo("acfBank.*"));
			selector.add(new SelectorItemInfo("loanScheme.*"));
			selector.add(new SelectorItemInfo("afScheme.*"));
			selector.add(new SelectorItemInfo("bizLists.*"));
			
			SHEPayTypeInfo payType = SHEPayTypeFactory.getRemoteInstance()
				.getSHEPayTypeInfo(new ObjectUuidPK(payTypeId), selector);
			
			if(payType != null ){
				if(payType.isIsLoan()){  //??????????????????????????????
					if(roomLoan.getMmType().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){  //????
						roomLoan.setLoanBank(payType.getLoanBank());
						roomLoan.setORSOMortgaged(payType.getLoanScheme());
						this.setLoanEntry(roomLoan, payType.getLoanScheme());
					}
					else{  //??????
						roomLoan.setLoanBank(payType.getAcfBank());
						roomLoan.setORSOMortgaged(payType.getAfScheme());
						this.setLoanEntry(roomLoan, payType.getAfScheme());
					}
				}
				//??????????????????????????????????
				if(payType.getBizLists()!=null && !payType.getBizLists().isEmpty()){
					for(int i=0; i<payType.getBizLists().size(); i++){
						BizListEntryInfo bizEntry = payType.getBizLists().get(i);
						if(bizEntry.getPayTypeBizFlow().equals(BizNewFlowEnum.LOAN)){
							Date promDate = this.getBizPromiseDate(payType.getBizLists(), bizEntry);
							roomLoan.setPromiseDate(promDate);
							break;
						}
					}
				}
			}
		}
	}
	
	/**
	 * ????????????????????????
	 * @param bizEntryCol
	 * @param currentBizInfo
	 * @return
	 */
	private Date getBizPromiseDate(BizListEntryCollection bizEntryCol, 
			BizListEntryInfo currentBizInfo){
		if(currentBizInfo.getPayTypeBizTime() == null){
			return null;
		}
		else if(currentBizInfo.getPayTypeBizTime().equals("????????")){  //??????????????????
			return currentBizInfo.getAppDate();
		}
		else{
			for(int i=0; i<bizEntryCol.size(); i++){
				BizListEntryInfo bizInfo = bizEntryCol.get(i);
				if(bizInfo.getPayTypeBizFlow().getAlias().equals(currentBizInfo.getPayTypeBizTime())){
					Date tempDate = getBizPromiseDate(bizEntryCol, bizInfo);
					if(tempDate != null){
						//??????????????????????
						int mon = currentBizInfo.getMonthLimit();
						int day = currentBizInfo.getDayLimit();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}
	
	/**
	 * ????????????????????????????????????????????
	 * @param roomLoan
	 * @param afmScheme
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private void setLoanEntry(RoomLoanInfo roomLoan, AFMortgagedInfo afmScheme) throws EASBizException, BOSException{
		if(afmScheme == null){
			return;
		}
		SelectorItemCollection selector = new SelectorItemCollection();
		selector.add(new SelectorItemInfo("ApproachEntrys.*"));
		selector.add(new SelectorItemInfo("DataEntrys.*"));
		
		afmScheme = AFMortgagedFactory.getRemoteInstance()
			.getAFMortgagedInfo(new ObjectUuidPK(afmScheme.getId().toString()), selector);
		
		roomLoan.getAFMortgaged().clear();
		
		//????????????
		RoomLoanAFMEntrysCollection afmEntryCol = new RoomLoanAFMEntrysCollection();
		AFMortgagedApproachEntryCollection appCol = afmScheme.getApproachEntrys();
		if(appCol!=null && !appCol.isEmpty()){
			for(int i=0; i<appCol.size(); i++){
				RoomLoanAFMEntrysInfo roomLoanEntry = new RoomLoanAFMEntrysInfo();
				AFMortgagedApproachEntryInfo appEntry = appCol.get(i);
				
				roomLoanEntry.setApproach(appEntry.getName());
				roomLoanEntry.setPromiseFinishDate(getApproachPromiseDate(roomLoan, appCol, appEntry));
				roomLoanEntry.setIsFinish(false);
				roomLoanEntry.setRemark(appEntry.getRemark());
				roomLoanEntry.setIsFinishFlag(appEntry.isIsFinish());
				roomLoanEntry.setIsAOrB(true);
				
				afmEntryCol.add(roomLoanEntry);
			}
		}
		
		//????????????
		AFMortgagedDataEntryCollection dataCol = afmScheme.getDataEntrys();
		if(dataCol!=null && !dataCol.isEmpty()){
			for(int i=0; i<dataCol.size(); i++){
				RoomLoanAFMEntrysInfo roomLoanEntry = new RoomLoanAFMEntrysInfo();
				AFMortgagedDataEntryInfo dataEntry = dataCol.get(i);
				
				roomLoanEntry.setApproach(dataEntry.getName());
				roomLoanEntry.setIsFinish(false);
				roomLoanEntry.setRemark(dataEntry.getRemark());
				roomLoanEntry.setIsAOrB(false);
				
				afmEntryCol.add(roomLoanEntry);
			}
		}
		
		if(!afmEntryCol.isEmpty()){
			roomLoan.getAFMortgaged().addCollection(afmEntryCol);
		}
	}
	
	private Date getApproachPromiseDate(RoomLoanInfo roomLoan, AFMortgagedApproachEntryCollection afmAppCol, 
			AFMortgagedApproachEntryInfo currentAppInfo){
		if(currentAppInfo.getReferenceTime() == null){
			return null;
		}
		else if(currentAppInfo.getReferenceTime().equals("????????")){  //??????????????????
			return currentAppInfo.getScheduledDate();
		}
		else if(currentAppInfo.getReferenceTime().equals("????????")){  //??????????????????
			if(roomLoan.getSign()==null){
				return null;
			}
			else{
				return roomLoan.getSign().getBizDate();
			}
		}
		else{
			for(int i=0; i<afmAppCol.size(); i++){
				AFMortgagedApproachEntryInfo appInfo = afmAppCol.get(i);
				if(appInfo.getName().equals(currentAppInfo.getReferenceTime())){
					Date tempDate = getApproachPromiseDate(roomLoan, afmAppCol, appInfo);
					if(tempDate != null){
						//??????????????????????
						int mon = currentAppInfo.getIntervalMonth();
						int day = currentAppInfo.getIntervalDays();
						return DateTimeUtils.addDuration(tempDate, 0, mon, day);
					}
				}
			}
			return null;
		}
	}

	public void actionAddNew_actionPerformed(ActionEvent e) throws Exception {
		
	}

	public void actionRemove_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		if (confirmRemove()) {
			Remove();
			refresh(e);
		}
	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		super.actionRefresh_actionPerformed(e);
	}

	public void actionPrint_actionPerformed(ActionEvent e) throws Exception {
		super.actionPrint_actionPerformed(e);
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e)
			throws Exception {
		super.actionPrintPreview_actionPerformed(e);
	}

	protected ITreeBase getTreeInterface() throws Exception {
		return null;
	}
	
	protected String getSelectedKeyValue() {
		return null;
	}

	public void initControl() {
		this.menuItemImportData.setVisible(false);
		this.menuEdit.setVisible(false);
		this.actionImportData.setVisible(true);
		this.menuBiz.setVisible(true);
		this.menuBiz.setEnabled(true);
		this.menuItemCancel.setVisible(false);
		this.menuItemCancelCancel.setVisible(false);
		this.actionEdit.setVisible(false);
		this.actionEdit.setEnabled(false);
		this.actionRemove.setVisible(false);
		this.actionRemove.setEnabled(false);
		this.actionView.setVisible(false);
		this.actionView.setEnabled(false);
		this.actionQuery.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionLocate.setVisible(false);
		this.actionQuery.setEnabled(false);
		this.actionImportData.setVisible(false);
		this.treeView.setShowControlPanel(true);
		
		//this.tblApproach.getStyleAttributes().setLocked(true);
		//this.tblData.getStyleAttributes().setLocked(true);
	}

	/**
	 * ??????????????
	 * 
	 * @param reQuery
	 *            ??????????????????ID????????,????????????????
	 * @throws UuidException
	 * @throws BOSException
	 * @throws EASBizException
	 * */
	private RoomInfo getSelectRoom() throws EASBizException, BOSException, UuidException {
		KDTSelectBlock block = this.tblMain.getSelectManager().get();
		if (block == null) {
			return null;
		}
		int left = block.getLeft();
		int top = block.getTop();
		RoomInfo room = this.getSelectRoomByIndex(top, left);
		return room;
	}
	
	/**
	 * ????????????????????????????????????????????????
	 * @param block
	 * @return
	 * @throws UuidException 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private ArrayList getSelectRoomListByBlock(KDTSelectBlock block) throws EASBizException, BOSException, UuidException{
		if(block == null){
			return null;
		}
		
		ArrayList roomList = new ArrayList();
		int top = block.getTop();
		int bottom = block.getBottom();
		int left = block.getLeft();
		int right = block.getRight();
		
		if(bottom > top){  //????
			if(right > left){  //????
				for(int i=top; i<=bottom; i++){
					for(int j=left; j<=right; j++){
						RoomInfo room = this.getSelectRoomByIndex(i, j);
						if(room != null){
							//????????????????????????
							ICell cell = this.tblMain.getCell(i, j);
							if(!cell.getStyleAttributes().getBackground().equals(this.colorOfReady)){
								FDCMsgBox.showInfo("??????????????????????????????????????????");
								SysUtil.abort();
							}
							roomList.add(room);
						}
					}
				}
			}
			else{  //????
				for(int i=top; i<=bottom; i++){
					RoomInfo room = this.getSelectRoomByIndex(i, left);
					if(room != null){
						//????????????????????????
						ICell cell = this.tblMain.getCell(i, left);
						if(!cell.getStyleAttributes().getBackground().equals(this.colorOfReady)){
							FDCMsgBox.showInfo("??????????????????????????????????????????");
							SysUtil.abort();
						}
						roomList.add(room);
					}
				}
			}
		}
		else if(right > left){  //??????????
			for(int i=left; i<=right; i++){
				RoomInfo room = this.getSelectRoomByIndex(top, i);
				if(room != null){
					//????????????????????????
					ICell cell = this.tblMain.getCell(top, i);
					if(!cell.getStyleAttributes().getBackground().equals(this.colorOfReady)){
						FDCMsgBox.showInfo("??????????????????????????????????????????");
						SysUtil.abort();
					}
					roomList.add(room);
				}
			}
		}
		else{  //??????????
			RoomInfo room = this.getSelectRoomByIndex(top, left);
			if(room != null){
				//????????????????????????
				ICell cell = this.tblMain.getCell(top, left);
				if(!cell.getStyleAttributes().getBackground().equals(this.colorOfReady)){
					FDCMsgBox.showInfo("??????????????????????????????????????????");
					SysUtil.abort();
				}
				roomList.add(room);
			}
		}
		
		return roomList;
	}
	
	/**
	 * ??????????????????
	 * @param top
	 * @param left
	 * @return
	 * @throws Exception 
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private RoomInfo getSelectRoomByIndex(int top, int left) throws EASBizException, BOSException, UuidException{
		ICell cell = this.tblMain.getCell(top, left);
		if (cell == null) {
			return null;
		}
		RoomInfo room = null;
		if (cell.getUserObject() != null && cell.getUserObject() instanceof RoomInfo)
			room = (RoomInfo) cell.getUserObject();
		if (room == null) {
			return null;
		}

		// ??????????userObject????????????ID??????????????????
		room = SHEHelper.queryRoomInfo(room.getId().toString());
		cell.setUserObject(room);
		return room;
	}
	
	/**
	 *??????????????????
	 */
	private void setRoomColor() throws Exception {
		for (int i = 0; i < this.tblMain.getRowCount(); i++) {
			for (int j = 0; j < this.tblMain.getColumnCount(); j++) {
				ICell cell = this.tblMain.getRow(i).getCell(j);
				if (cell == null) {
					continue;
				}
				RoomInfo roomInfo = (RoomInfo) cell.getUserObject();
				if (roomInfo == null){
					continue;
				}
				
				if(this.isRoomLoaned(roomInfo)){  //??????????????????????????????????????????????
					cell.getStyleAttributes().setBackground(this.colorOfFinish);
				}
				//????????????????????????????????
				else if(!roomInfo.getSellState().equals(RoomSellStateEnum.Purchase) 
					    && !roomInfo.getSellState().equals(RoomSellStateEnum.Sign)){  //??????????????????????????????????????
					cell.getStyleAttributes().setBackground(this.colorOfNoNeed);
				}
				else {  //??????????????
					if(this.isRoomNeedLoan(roomInfo)){  //??????????????????????????????????????
						cell.getStyleAttributes().setBackground(this.colorOfReady);
					}
					else{  //????????
						cell.getStyleAttributes().setBackground(this.colorOfNoNeed);
					}
				}
			}
		}
	}
	
	/**
	 * ??????????????????????
	 * @param room
	 * @return
	 * @throws BOSException 
	 */
	private boolean isRoomLoaned(RoomInfo room) throws BOSException{
		boolean isLoaned = false;
		if(room == null){
			return false;
		}
		
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("id");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		
		HashSet afmStateSet = new HashSet();
		afmStateSet.add(new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTED_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.BANKFUND_VALUE));
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState", afmStateSet, CompareType.INCLUDE));

		view.setFilter(filter);

		RoomLoanCollection roomLoanCol = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(view);
		if(roomLoanCol!=null && !roomLoanCol.isEmpty()){
			isLoaned = true;
		}
		
		return isLoaned;
	}
	
	/**
	 * ????????????????????????????????????
	 * @param room
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private boolean isRoomNeedLoan(RoomInfo room) throws BOSException, EASBizException{
		boolean isNeedLoan = false;
		TransactionInfo transactionInfo = this.getTransactionInfo(room);
		if(transactionInfo != null){
			if(transactionInfo.getCurrentLink()!=null && ( transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Purchase)
					|| transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Sign))){  //??????????????
				TranBusinessOverViewCollection overViewCol = transactionInfo.getTranBusinessOverView();
				if(overViewCol!=null && !overViewCol.isEmpty()){  //??????????????????????????????????????
					for(int i=0; i<overViewCol.size(); i++){
						TranBusinessOverViewInfo overViewInfo = overViewCol.get(i);
						if(overViewInfo.getMoneyDefine()!=null){
							if (overViewInfo.getMoneyDefine().getMoneyType()!=null && overViewInfo.getMoneyDefine().getMoneyType()
									.equals(MoneyTypeEnum.LoanAmount)
									|| overViewInfo.getMoneyDefine().getMoneyType()!=null && overViewInfo
											.getMoneyDefine()
											.getMoneyType()
											.equals(MoneyTypeEnum.AccFundAmount)) {
								isNeedLoan = true;
							}
						}
					}
				}
			}
		}
		return isNeedLoan;
	}
	
	/**
	 * ??????????????????????????????????????????????????????
	 * @param room
	 * @return 0 - ??????1 - ???????? 2 - ????
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private int checkRoomMoneyType(RoomInfo room) throws BOSException, EASBizException{
		this.moneyDefineMap.clear();
		int retMoneyType = -1;
		
		TransactionInfo transactionInfo = this.getTransactionInfo(room);
		if(transactionInfo != null){
			if(transactionInfo.getCurrentLink()!=null && (transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Purchase)
					|| transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Sign))){  //??????????????
				//??????????????????
				if(transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Sign)){  //??????????
					SignManageInfo signInfo = SignManageFactory.getRemoteInstance()
						.getSignManageInfo(new ObjectUuidPK(transactionInfo.getBillId().toString()));
					this.billObjectMap.put("sign", signInfo);
					
					SignPayListEntryCollection payListEntry = this.getSignPayEntry(transactionInfo.getBillId().toString());
					if(payListEntry!=null && !payListEntry.isEmpty()){  //??????????????????????????????????????
						for(int i=0; i<payListEntry.size(); i++){
							SignPayListEntryInfo payListInfo = payListEntry.get(i);
							if(payListInfo.getMoneyDefine()!=null) {
								if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
									moneyDefineMap.put(new Integer(this.moneyTypeOfLoan), payListInfo.getMoneyDefine());
									if(retMoneyType == this.moneyTypeOfAfm){  //????????????????????
										return this.moneyTypeOfLoanAndAfm;
									}
									else{  //????
										retMoneyType = this.moneyTypeOfLoan;
									}
								}
								else if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
									moneyDefineMap.put(new Integer(this.moneyTypeOfAfm), payListInfo.getMoneyDefine());
									if(retMoneyType == this.moneyTypeOfLoan){  //????????????????????
										return this.moneyTypeOfLoanAndAfm;
									}
									else{  //??????
										retMoneyType = this.moneyTypeOfAfm;
									}
								}
							}
						}
					}
				}
				else if(transactionInfo.getCurrentLink().equals(RoomSellStateEnum.Purchase)){  //??????????
					PurchaseManageInfo purInfo = PurchaseManageFactory.getRemoteInstance()
						.getPurchaseManageInfo(new ObjectUuidPK(transactionInfo.getBillId().toString()));
					this.billObjectMap.put("purchase", purInfo);
					
					PurPayListEntryCollection payListEntry = this.getPurchasePayEntry(transactionInfo.getBillId().toString());
					if(payListEntry!=null && !payListEntry.isEmpty()){  //??????????????????????????????????????
						for(int i=0; i<payListEntry.size(); i++){
							PurPayListEntryInfo payListInfo = payListEntry.get(i);
							if(payListInfo.getMoneyDefine()!=null) {
								if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
									moneyDefineMap.put(new Integer(this.moneyTypeOfLoan), payListInfo.getMoneyDefine());
									if(retMoneyType == this.moneyTypeOfAfm){  //????????????????????
										return this.moneyTypeOfLoanAndAfm;
									}
									else{  //????
										retMoneyType = this.moneyTypeOfLoan;
									}
								}
								else if(payListInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
									moneyDefineMap.put(new Integer(this.moneyTypeOfAfm), payListInfo.getMoneyDefine());
									if(retMoneyType == this.moneyTypeOfLoan){  //????????????????????
										return this.moneyTypeOfLoanAndAfm;
									}
									else{  //??????
										retMoneyType = this.moneyTypeOfAfm;
									}
								}
							}
						}
					}
				}
				
				//????????????????????????????????
				//??????????????????????
				/*TranBusinessOverViewCollection overViewCol = transactionInfo.getTranBusinessOverView();
				if(overViewCol!=null && !overViewCol.isEmpty()){  //??????????????????????????????????????
					for(int i=0; i<overViewCol.size(); i++){
			 			TranBusinessOverViewInfo OverViewInfo = overViewCol.get(i);
						if(OverViewInfo.getMoneyDefine()!=null) {
							if(OverViewInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.LoanAmount)){
								moneyDefineMap.put(new Integer(this.moneyTypeOfLoan), OverViewInfo.getMoneyDefine());
								if(retMoneyType == this.moneyTypeOfAfm){  //????????????????????
									return this.moneyTypeOfLoanAndAfm;
								}
								else{  //????
									retMoneyType = this.moneyTypeOfLoan;
								}
							}
							else if(OverViewInfo.getMoneyDefine().getMoneyType().equals(MoneyTypeEnum.AccFundAmount)){
								moneyDefineMap.put(new Integer(this.moneyTypeOfAfm), OverViewInfo.getMoneyDefine());
								if(retMoneyType == this.moneyTypeOfLoan){  //????????????????????
									return this.moneyTypeOfLoanAndAfm;
								}
								else{  //??????
									retMoneyType = this.moneyTypeOfAfm;
								}
							}
						}
					}
				}*/
			}
		}
		
		return retMoneyType;
	}
	
	/**
	 * ??????????????????????
	 * @param room
	 * @return
	 * @throws BOSException 
	 */
	private TransactionInfo getTransactionInfo(RoomInfo room) throws BOSException{
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("tranBusinessOverView.*");
		view.getSelector().add("tranBusinessOverView.moneyDefine.*");
		view.getSelector().add("currentLink");
		view.getSelector().add("billId");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("isValid", new Boolean(false)));

		view.setFilter(filter);
		
		TransactionCollection transactionCol = TransactionFactory.getRemoteInstance().getTransactionCollection(view);
		if(transactionCol!=null && !transactionCol.isEmpty()){
			return transactionCol.get(0);
		}
		else{
			return null;
		}
	}

	/**
	 * ??????????id??????????????
	 * @param billId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 * @throws BOSException 
	 */
	private PurPayListEntryCollection getPurchasePayEntry(String billId) throws EASBizException, BOSException{
		PurPayListEntryCollection entryCol = null;
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("purPayListEntry.moneyDefine.moneyType");
		
		PurchaseManageInfo purInfo = PurchaseManageFactory.getRemoteInstance()
			.getPurchaseManageInfo(new ObjectUuidPK(billId), selCol);
		if(purInfo.getId() != null){
			entryCol = purInfo.getPurPayListEntry();
		}
		
		return entryCol;
	}
	
	/**
	 * ??????????id??????????????
	 * @param billId
	 * @return
	 * @throws BOSException 
	 * @throws EASBizException 
	 */
	private SignPayListEntryCollection getSignPayEntry(String billId) throws EASBizException, BOSException{
		SignPayListEntryCollection entryCol = null;
		
		SelectorItemCollection selCol = new SelectorItemCollection();
		selCol.add("signPayListEntry.moneyDefine.moneyType");
		
		SignManageInfo signInfo = SignManageFactory.getRemoteInstance().getSignManageInfo(new ObjectUuidPK(billId), selCol);
		if(signInfo != null){
			entryCol = signInfo.getSignPayListEntry();
		}
		
		return entryCol;
	}

	/**
	 * ????????????????????????????????????????????????
	 * @param room
	 * @return
	 * @throws BOSException 
	 */
	private RoomLoanInfo getRoomLoanInfo(RoomInfo room) throws BOSException{
		RoomLoanInfo roomLoanInfo = null;
		EntityViewInfo view = new EntityViewInfo();
		view.getSelector().add("aFMortgaged.*");
		view.getSelector().add("aFMortgaged.transactor.*");
		
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("room.id", room.getId()));
		
		HashSet afmStateSet = new HashSet();
		afmStateSet.add(new Integer(AFMortgagedStateEnum.UNTRANSACT_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTING_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.TRANSACTED_VALUE));
		afmStateSet.add(new Integer(AFMortgagedStateEnum.BANKFUND_VALUE));
		
		filter.getFilterItems().add(new FilterItemInfo("aFMortgagedState", afmStateSet, CompareType.INCLUDE));

		view.setFilter(filter);
		
		RoomLoanCollection roomLoanCol = RoomLoanFactory.getRemoteInstance().getRoomLoanCollection(view);
		if(roomLoanCol!=null && !roomLoanCol.isEmpty()){
			roomLoanInfo = roomLoanCol.get(0);
		}
		
		return roomLoanInfo;
	}
	
	/**
	 * ??????????????????????????????????
	 * @throws BOSException 
	 */
	private void fillRoomDetail(RoomInfo room) throws BOSException{
		this.fillRoomBaseInfo(room);
		
		this.fillRoomLoanInfo(room);
	}
	
	/**
	 * ????????????????
	 * @param room
	 */
	private void fillRoomBaseInfo(RoomInfo room){
	
	}
	
	/**
	 * ????????????????
	 * @param room
	 * @throws BOSException 
	 */
	private void fillRoomLoanInfo(RoomInfo room) throws BOSException{
		//????????????????
		//this.tblApproach.removeRows();
		//this.tblData.removeRows();
		
		//????????????
//		KDTSelectBlock block = this.tblMain.getSelectManager().get();
//		int left = block.getLeft();
//		int top = block.getTop();
//		ICell cell = this.tblMain.getCell(top, left);
//		//????????????????????????????????????????????
//		if(cell.getStyleAttributes().getBackground().equals(this.colorOfFinish)){
//			//this.AfmPane.setVisible(true);
//			
//			//??????????????????
//			RoomLoanInfo loanInfo = this.getRoomLoanInfo(room);
//			
//			//????????????
//			RoomLoanAFMEntrysCollection appEntry = loanInfo.getAFMortgaged();
//			if(appEntry!=null && !appEntry.isEmpty()){
//				for(int i=0; i<appEntry.size(); i++){
//					RoomLoanAFMEntrysInfo entry = appEntry.get(i);
//					if(entry.isIsAOrB()){  //????????
//						/*IRow row = this.tblApproach.addRow();
//						row.getCell("approachName").setValue(entry.getApproach());
//						row.getCell("promiseDate").setValue(entry.getPromiseFinishDate());
//						row.getCell("isFinish").setValue(new Boolean(entry.isIsFinish()));
//						row.getCell("actFinishDate").setValue(entry.getActualFinishDate());
//						row.getCell("operator").setValue(entry.getTransactor());
//						row.getCell("remark").setValue(entry.getRemark());
//					}*/
//					else{  //????????
//						/*IRow row = this.tblData.addRow();
//						row.getCell("dataName").setValue(entry.getApproach());
//						row.getCell("isReady").setValue(new Boolean(entry.isIsFinish()));
//						row.getCell("bizDate").setValue(entry.getActualFinishDate());
//						row.getCell("remark").setValue(entry.getRemark());*/
//					}
//				}
//			}
//		}
//		else{
//			//this.AfmPane.setVisible(false);
//		}
	}
}