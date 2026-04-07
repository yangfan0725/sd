package com.kingdee.eas.fi.cas.client;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.swing.KDLayout;
import com.kingdee.bos.ctrl.swing.KDWorkButton;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.basedata.org.CompanyOrgUnitFactory;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.BankNumCollection;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.eas.fdc.contract.ExpenseCostInfo;
import com.kingdee.eas.fdc.contract.FundTransfer;
import com.kingdee.eas.fdc.contract.FundTransferInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.TripCostInfo;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.contract.client.ExpenseCostEditUI;
import com.kingdee.eas.fdc.contract.client.FundTransferEditUI;
import com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI;
import com.kingdee.eas.fdc.contract.client.TripCostEditUI;
import com.kingdee.eas.fi.cas.BgCtrlPaymentBillHandler;
import com.kingdee.eas.fi.cas.BillStatusEnum;
import com.kingdee.eas.fi.cas.IPaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fm.common.client.FMClientHelper;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.ma.budget.IBgCtrlHandler;
import com.kingdee.eas.util.client.EASResource;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class CasPaymentBillListUICTEx extends CasPaymentBillListUI{
	public CasPaymentBillListUICTEx() throws Exception {
		super();
	}
	public void actionViewBgBalance_actionPerformed(ActionEvent e)
			throws Exception {
		checkSelected();
		String id = getSelectedKeyValue();
		SelectorItemCollection sic = new SelectorItemCollection();
		/* <-MISALIGNED-> */ /*2385*/        sic.add(new SelectorItemInfo("*"));
		/* <-MISALIGNED-> */ /*2386*/        sic.add(new SelectorItemInfo("entries.*"));
		/* <-MISALIGNED-> */ /*2387*/        sic.add(new SelectorItemInfo("assItems.*"));
		/* <-MISALIGNED-> */ /*2389*/        sic.add(new SelectorItemInfo("settlementType.id"));
		/* <-MISALIGNED-> */ /*2390*/        sic.add(new SelectorItemInfo("settlementType.number"));
		/* <-MISALIGNED-> */ /*2391*/        sic.add(new SelectorItemInfo("settlementType.name"));
		/* <-MISALIGNED-> */ /*2392*/        sic.add(new SelectorItemInfo("settlementType.payThroughBE"));
		/* <-MISALIGNED-> */ /*2393*/        sic.add(new SelectorItemInfo("settlementType.isDefault"));
		/* <-MISALIGNED-> */ /*2394*/        sic.add(new SelectorItemInfo("settlementType.ntType.group"));
		/* <-MISALIGNED-> */ /*2395*/        sic.add(new SelectorItemInfo("settlementType.ntType.superGroup"));
		/* <-MISALIGNED-> */ /*2396*/        sic.add(new SelectorItemInfo("settlementType.ntType.id"));
		/* <-MISALIGNED-> */ /*2397*/        sic.add(new SelectorItemInfo("settlementType.ntType.isUsed"));
		/* <-MISALIGNED-> */ /*2398*/        sic.add(new SelectorItemInfo("settlementType.ntType.isRecNote"));
		/* <-MISALIGNED-> */ /*2399*/        sic.add(new SelectorItemInfo("settlementType.ntType.name"));
		/* <-MISALIGNED-> */ /*2400*/        sic.add(new SelectorItemInfo("settlementType.ntType.number"));
		/* <-MISALIGNED-> */ /*2402*/        sic.add(new SelectorItemInfo("oppAccount.id"));
		/* <-MISALIGNED-> */ /*2403*/        sic.add(new SelectorItemInfo("oppAccount.number"));
		/* <-MISALIGNED-> */ /*2404*/        sic.add(new SelectorItemInfo("oppAccount.name"));
		/* <-MISALIGNED-> */ /*2405*/        sic.add(new SelectorItemInfo("oppAccount.CAA"));
		/* <-MISALIGNED-> */ /*2406*/        sic.add(new SelectorItemInfo("oppAccount.isCash"));
		/* <-MISALIGNED-> */ /*2407*/        sic.add(new SelectorItemInfo("oppAccount.isBank"));
		/* <-MISALIGNED-> */ /*2413*/        sic.add(new SelectorItemInfo("payerBank.name"));
		/* <-MISALIGNED-> */ /*2415*/        sic.add(new SelectorItemInfo("payerAccount.id"));
		/* <-MISALIGNED-> */ /*2416*/        sic.add(new SelectorItemInfo("payerAccount.name"));
		/* <-MISALIGNED-> */ /*2417*/        sic.add(new SelectorItemInfo("payerAccount.number"));
		/* <-MISALIGNED-> */ /*2418*/        sic.add(new SelectorItemInfo("payerAccount.isBank"));
		/* <-MISALIGNED-> */ /*2419*/        sic.add(new SelectorItemInfo("payerAccount.isCash"));
		/* <-MISALIGNED-> */ /*2420*/        sic.add(new SelectorItemInfo("payerAccountBank.company.CU.id"));
		/* <-MISALIGNED-> */ /*2421*/        sic.add(new SelectorItemInfo("payerAccountBank.company.id"));
		/* <-MISALIGNED-> */ /*2424*/        sic.add(new SelectorItemInfo("payerAccountBank.*"));
		/* <-MISALIGNED-> */ /*2426*/        sic.add(new SelectorItemInfo("project.id"));
		/* <-MISALIGNED-> */ /*2427*/        sic.add(new SelectorItemInfo("project.name"));
		/* <-MISALIGNED-> */ /*2428*/        sic.add(new SelectorItemInfo("project.number"));
		/* <-MISALIGNED-> */ /*2429*/        sic.add(new SelectorItemInfo("projectManager.id"));
		/* <-MISALIGNED-> */ /*2430*/        sic.add(new SelectorItemInfo("projectManager.name"));
		/* <-MISALIGNED-> */ /*2431*/        sic.add(new SelectorItemInfo("projectManager.number"));
		/* <-MISALIGNED-> */ /*2432*/        sic.add(new SelectorItemInfo("payeeAccountBank"));
		/* <-MISALIGNED-> */ /*2434*/        sic.add(new SelectorItemInfo("payeeAccountBankO.id"));
		/* <-MISALIGNED-> */ /*2435*/        sic.add(new SelectorItemInfo("payeeAccountBankO.acctName"));
		/* <-MISALIGNED-> */ /*2436*/        sic.add(new SelectorItemInfo("payeeAccountBankO.number"));
		/* <-MISALIGNED-> */ /*2437*/        sic.add(new SelectorItemInfo("payeeAccountBankO.name"));
		/* <-MISALIGNED-> */ /*2438*/        sic.add(new SelectorItemInfo("payeeAccountBankO.company.id"));
		/* <-MISALIGNED-> */ /*2439*/        sic.add(new SelectorItemInfo("payeeAccountBankO.company.name"));
		/* <-MISALIGNED-> */ /*2440*/        sic.add(new SelectorItemInfo("payeeAccountBankO.company.number"));
		/* <-MISALIGNED-> */ /*2442*/        sic.add(new SelectorItemInfo("bizType.id"));
		/* <-MISALIGNED-> */ /*2443*/        sic.add(new SelectorItemInfo("bizType.number"));
		/* <-MISALIGNED-> */ /*2444*/        sic.add(new SelectorItemInfo("bizType.name"));
		/* <-MISALIGNED-> */ /*2445*/        sic.add(new SelectorItemInfo("bizType.type"));
		/* <-MISALIGNED-> */ /*2446*/        sic.add(new SelectorItemInfo("bizType.notSubmitHouse"));
		/* <-MISALIGNED-> */ /*2448*/        sic.add(new SelectorItemInfo("settlementNumber"));
		/* <-MISALIGNED-> */ /*2449*/        sic.add(new SelectorItemInfo("feeType.id"));
		/* <-MISALIGNED-> */ /*2450*/        sic.add(new SelectorItemInfo("feeType.name"));
		/* <-MISALIGNED-> */ /*2451*/        sic.add(new SelectorItemInfo("feeType.number"));
		/* <-MISALIGNED-> */ /*2452*/        sic.add(new SelectorItemInfo("payBillType.*"));
		/* <-MISALIGNED-> */ /*2454*/        sic.add(new SelectorItemInfo("approver.name"));
		/* <-MISALIGNED-> */ /*2455*/        sic.add(new SelectorItemInfo("payeeBank"));
		/* <-MISALIGNED-> */ /*2456*/        sic.add(new SelectorItemInfo("FRecCountry.id"));
		/* <-MISALIGNED-> */ /*2457*/        sic.add(new SelectorItemInfo("FRecCountry.name"));
		/* <-MISALIGNED-> */ /*2458*/        sic.add(new SelectorItemInfo("FRecCountry.number"));
		/* <-MISALIGNED-> */ /*2459*/        sic.add(new SelectorItemInfo("recProvince"));
		/* <-MISALIGNED-> */ /*2460*/        sic.add(new SelectorItemInfo("recCity"));
		/* <-MISALIGNED-> */ /*2462*/        sic.add(new SelectorItemInfo("entries.currency.id"));
		/* <-MISALIGNED-> */ /*2463*/        sic.add(new SelectorItemInfo("entries.currency.name"));
		/* <-MISALIGNED-> */ /*2464*/        sic.add(new SelectorItemInfo("entries.currency.number"));
		/* <-MISALIGNED-> */ /*2465*/        sic.add(new SelectorItemInfo("entries.currency.precision"));
		/* <-MISALIGNED-> */ /*2467*/        sic.add(new SelectorItemInfo("entries.oppAccount.id"));
		/* <-MISALIGNED-> */ /*2468*/        sic.add(new SelectorItemInfo("entries.oppAccount.number"));
		/* <-MISALIGNED-> */ /*2469*/        sic.add(new SelectorItemInfo("entries.oppAccount.name"));
		/* <-MISALIGNED-> */ /*2470*/        sic.add(new SelectorItemInfo("entries.oppAccount.CAA"));
		/* <-MISALIGNED-> */ /*2472*/        sic.add(new SelectorItemInfo("entries.expenseType.id"));
		/* <-MISALIGNED-> */ /*2473*/        sic.add(new SelectorItemInfo("entries.expenseType.number"));
		/* <-MISALIGNED-> */ /*2474*/        sic.add(new SelectorItemInfo("entries.expenseType.name"));
		/* <-MISALIGNED-> */ /*2476*/        sic.add(new SelectorItemInfo("company.id"));
		/* <-MISALIGNED-> */ /*2477*/        sic.add(new SelectorItemInfo("company.number"));
		/* <-MISALIGNED-> */ /*2478*/        sic.add(new SelectorItemInfo("company.name"));
		/* <-MISALIGNED-> */ /*2479*/        sic.add(new SelectorItemInfo("company.isBizUnit"));
		/* <-MISALIGNED-> */ /*2480*/        sic.add(new SelectorItemInfo("company.CU"));
		/* <-MISALIGNED-> */ /*2481*/        sic.add(new SelectorItemInfo("company.baseCurrency.number"));
		/* <-MISALIGNED-> */ /*2482*/        sic.add(new SelectorItemInfo("company.baseCurrency.id"));
		/* <-MISALIGNED-> */ /*2483*/        sic.add(new SelectorItemInfo("company.baseExchangeTable.id"));
		/* <-MISALIGNED-> */ /*2484*/        sic.add(new SelectorItemInfo("company.baseExchangeTable.number"));
		/* <-MISALIGNED-> */ /*2485*/        sic.add(new SelectorItemInfo("agentPayCompany.id"));
		/* <-MISALIGNED-> */ /*2486*/        sic.add(new SelectorItemInfo("agentPayCompany.name"));
		/* <-MISALIGNED-> */ /*2489*/        sic.add(new SelectorItemInfo("currency.id"));
		/* <-MISALIGNED-> */ /*2490*/        sic.add(new SelectorItemInfo("currency.name"));
		/* <-MISALIGNED-> */ /*2491*/        sic.add(new SelectorItemInfo("currency.number"));
		/* <-MISALIGNED-> */ /*2492*/        sic.add(new SelectorItemInfo("currency.precision"));
		/* <-MISALIGNED-> */ /*2493*/        sic.add(new SelectorItemInfo("payerBank.id"));
		/* <-MISALIGNED-> */ /*2494*/        sic.add(new SelectorItemInfo("payerBank.inGroup"));
		/* <-MISALIGNED-> */ /*2496*/        sic.add(new SelectorItemInfo("payeeType.id"));
		/* <-MISALIGNED-> */ /*2497*/        sic.add(new SelectorItemInfo("payeeType.number"));
		/* <-MISALIGNED-> */ /*2498*/        sic.add(new SelectorItemInfo("payeeType.name"));
		/* <-MISALIGNED-> */ /*2499*/        sic.add(new SelectorItemInfo("payeeType.asstHGAttribute"));
		/* <-MISALIGNED-> */ /*2501*/        sic.add(new SelectorItemInfo("payerAccountBank.bank.id"));
		/* <-MISALIGNED-> */ /*2502*/        sic.add(new SelectorItemInfo("payerAccountBank.bank.name"));
		/* <-MISALIGNED-> */ /*2503*/        sic.add(new SelectorItemInfo("payerAccountBank.bank.number"));
		/* <-MISALIGNED-> */ /*2504*/        sic.add(new SelectorItemInfo("payerAccountBank.bank.inGroup"));
		/* <-MISALIGNED-> */ /*2505*/        sic.add(new SelectorItemInfo("payerAccountBank.ctrlStrategy.id"));
		/* <-MISALIGNED-> */ /*2508*/        sic.add(new SelectorItemInfo("entries.assItemsEntries.*"));
		/* <-MISALIGNED-> */ /*2509*/        sic.add("assItems.asstActType.id");
		/* <-MISALIGNED-> */ /*2510*/        sic.add("assItems.asstActType.number");
		/* <-MISALIGNED-> */ /*2511*/        sic.add("adminOrgUnit.id");
		/* <-MISALIGNED-> */ /*2512*/        sic.add("adminOrgUnit.number");
		/* <-MISALIGNED-> */ /*2513*/        sic.add("adminOrgUnit.name");
		/* <-MISALIGNED-> */ /*2515*/        sic.add("payerAccountBank.InnerAcct.id");
		/* <-MISALIGNED-> */ /*2516*/        sic.add("payerAccountBank.InnerAcct.number");
		/* <-MISALIGNED-> */ /*2517*/        sic.add("payerAccountBank.InnerAcct.name");
		/* <-MISALIGNED-> */ /*2518*/        sic.add("payeeArea.name");
		/* <-MISALIGNED-> */ /*2519*/        sic.add("payeeArea.id");
		/* <-MISALIGNED-> */ /*2520*/        sic.add("payeeArea.number");
		/* <-MISALIGNED-> */ /*2522*/        sic.add("outBgItemId");
		/* <-MISALIGNED-> */ /*2523*/        sic.add("outBgItemNumber");
		/* <-MISALIGNED-> */ /*2524*/        sic.add("outBgItemName");
		/* <-MISALIGNED-> */ /*2525*/        sic.add("entries.outBgItemId");
		/* <-MISALIGNED-> */ /*2526*/        sic.add("entries.outBgItemNumber");
		/* <-MISALIGNED-> */ /*2528*/        sic.add(new SelectorItemInfo("costCenter.name"));
		/* <-MISALIGNED-> */ /*2529*/        sic.add(new SelectorItemInfo("costCenter.number"));
		/* <-MISALIGNED-> */ /*2530*/        sic.add(new SelectorItemInfo("costCenter.id"));
		/* <-MISALIGNED-> */ /*2532*/        sic.add(new SelectorItemInfo("actRecAccountBank.*"));
		/* <-MISALIGNED-> */ /*2533*/        sic.add(new SelectorItemInfo("payerInAcctID.id"));
		/* <-MISALIGNED-> */ /*2534*/        sic.add(new SelectorItemInfo("payerInAcctID.name"));
		/* <-MISALIGNED-> */ /*2535*/        sic.add(new SelectorItemInfo("payerInAcctID.number"));
		/* <-MISALIGNED-> */ /*2537*/        sic.add(new SelectorItemInfo("entries.costCenter.name"));
		/* <-MISALIGNED-> */ /*2538*/        sic.add(new SelectorItemInfo("entries.costCenter.number"));
		/* <-MISALIGNED-> */ /*2539*/        sic.add(new SelectorItemInfo("entries.costCenter.id"));
		/* <-MISALIGNED-> */ /*2541*/        sic.add(new SelectorItemInfo("cheque.id"));
		/* <-MISALIGNED-> */ /*2542*/        sic.add(new SelectorItemInfo("cheque.number"));
		/* <-MISALIGNED-> */ /*2543*/        sic.add(new SelectorItemInfo("cheque.bank"));
		/* <-MISALIGNED-> */ /*2544*/        sic.add(new SelectorItemInfo("cheque.bankAcct"));
		/* <-MISALIGNED-> */ /*2545*/        sic.add(new SelectorItemInfo("cheque.state"));
		/* <-MISALIGNED-> */ /*2547*/        sic.add(new SelectorItemInfo("person.id"));
		/* <-MISALIGNED-> */ /*2548*/        sic.add(new SelectorItemInfo("person.name"));
		/* <-MISALIGNED-> */ /*2549*/        sic.add(new SelectorItemInfo("person.number"));
		/* <-MISALIGNED-> */ /*2552*/        sic.add(new SelectorItemInfo("entries.project.*"));
		/* <-MISALIGNED-> */ /*2553*/        sic.add(new SelectorItemInfo("entries.project.id"));
		/* <-MISALIGNED-> */ /*2554*/        sic.add(new SelectorItemInfo("entries.project.number"));
		/* <-MISALIGNED-> */ /*2555*/        sic.add(new SelectorItemInfo("entries.project.name"));
		/* <-MISALIGNED-> */ /*2557*/        sic.add(new SelectorItemInfo("entries.trackNumber.*"));
		/* <-MISALIGNED-> */ /*2558*/        sic.add(new SelectorItemInfo("entries.trackNumber.id"));
		/* <-MISALIGNED-> */ /*2559*/        sic.add(new SelectorItemInfo("entries.trackNumber.number"));
		/* <-MISALIGNED-> */ /*2560*/        sic.add(new SelectorItemInfo("entries.trackNumber.name"));
		/* <-MISALIGNED-> */ /*2562*/        sic.add(new SelectorItemInfo("fpItem.id"));
		/* <-MISALIGNED-> */ /*2563*/        sic.add(new SelectorItemInfo("fpItem.name"));
		/* <-MISALIGNED-> */ /*2564*/        sic.add(new SelectorItemInfo("fpItem.number"));
		/* <-MISALIGNED-> */ /*2566*/        sic.add(new SelectorItemInfo("oppFpItem.id"));
		/* <-MISALIGNED-> */ /*2567*/        sic.add(new SelectorItemInfo("oppFpItem.name"));
		/* <-MISALIGNED-> */ /*2568*/        sic.add(new SelectorItemInfo("oppFpItem.number"));
		/* <-MISALIGNED-> */ /*2570*/        sic.add(new SelectorItemInfo("entries.fpItem.id"));
		/* <-MISALIGNED-> */ /*2571*/        sic.add(new SelectorItemInfo("entries.fpItem.number"));
		/* <-MISALIGNED-> */ /*2572*/        sic.add(new SelectorItemInfo("entries.fpItem.name"));
		PaymentBillInfo info = ((IPaymentBill)getBizInterface()).getPaymentBillInfo(new ObjectUuidPK(id),sic);
		sic = new SelectorItemCollection();
		sic.add("*");
		sic.add("baseCurrency.*");
		IObjectPK pk = new ObjectUuidPK(info.getCompany().getId());
		CompanyOrgUnitInfo company = CompanyOrgUnitFactory.getRemoteInstance().getCompanyOrgUnitInfo(pk, sic);
		info.setCompany(company);
		IBgCtrlHandler handler = new BgCtrlPaymentBillHandler();
		com.kingdee.eas.ma.budget.BgCtrlParamCollection paramColl = BgCtrlPaymentBillHandler.getParamColl(info);
		String boName = "com.kingdee.eas.fi.cas.app.PaymentBill";
		 FMClientHelper.viewBgBalance(this, boName, paramColl, info);
	}
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id=row.getCell("id").getValue().toString();
		PaymentBillInfo info=PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(id));
		if(info.getContractBillId()!=null&&info.getFdcPayReqID()!=null){
			String className=null;
			if(PayReqUtils.isConWithoutTxt(info.getContractBillId())){
				id=info.getContractBillId();
				className=ContractWithoutTextEditUI.class.getName();
			}else{
				id=info.getFdcPayReqID();id=info.getFdcPayReqID();
				className=PayRequestBillEditUI.class.getName();
			}
			UIContext uiContext = new UIContext(this);
			uiContext.put("ID", id);
	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
	        IUIWindow uiWindow = uiFactory.create(className, uiContext,null,OprtState.VIEW);
	        uiWindow.show();
		}else{
			if(info.getSummary()!=null){
				String mtLoginNum = OaUtil.encrypt(SysContext.getSysContext().getCurrentUserInfo().getNumber());
				String s2 = "&MtFdLoinName=";
				StringBuffer stringBuffer = new StringBuffer();
				String oaid = info.getSummary();
	            String link = String.valueOf(stringBuffer.append(oaid).append(s2).append(mtLoginNum));
				
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+link);  
	    	}else{
	    		if(info.getSourceBillId()!=null){
	    			String className=null;
	    			if(BOSUuid.read(info.getSourceBillId()).getType().equals(new FundTransferInfo().getBOSType())){
	    				className=FundTransferEditUI.class.getName();
	    			}else if(BOSUuid.read(info.getSourceBillId()).getType().equals(new TripCostInfo().getBOSType())){
	    				className=TripCostEditUI.class.getName();
	    			}else if(BOSUuid.read(info.getSourceBillId()).getType().equals(new ExpenseCostInfo().getBOSType())){
	    				className=ExpenseCostEditUI.class.getName();
	    			}
	    			if(className!=null){
	    				UIContext uiContext = new UIContext(this);
		    			uiContext.put("ID", info.getSourceBillId());
		    	        IUIFactory uiFactory = UIFactory.createUIFactory(UIFactoryName.NEWTAB);
		    	        IUIWindow uiWindow = uiFactory.create(className, uiContext,null,OprtState.VIEW);
		    	        uiWindow.show();
	    			}else{
	    				super.actionTraceUp_actionPerformed(e);
	    			}
	    		}else{
	    			super.actionTraceUp_actionPerformed(e);
	    		}
	    	}
		}
	}
	public void onLoad() throws Exception {
		super.onLoad();
		KDWorkButton btnReCal=new KDWorkButton();
		btnReCal.setText("传递资金系统付款");
		btnReCal.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent e) {
	                beforeActionPerformed(e);
	                try {
	                	btnReCal_actionPerformed(e);
	                } catch (Exception exc) {
	                    handUIException(exc);
	                } finally {
	                    afterActionPerformed(e);
	                }
	            }
	        });
		btnReCal.setIcon(EASResource.getIcon("imgTbtn_input"));
		
		this.toolBar.add(btnReCal);
		btnReCal.setBounds(new Rectangle(658, 36, 120, 19));
	}
	protected void btnReCal_actionPerformed(ActionEvent e) throws Exception {
		checkSelected();
		int rowIndex = this.tblMain.getSelectManager().getActiveRowIndex();
		IRow row = this.tblMain.getRow(rowIndex);
		String id = (String) row.getCell(this.getKeyFieldName()).getValue();
		try {
			FDCSQLBuilder builder=new FDCSQLBuilder();
			builder.appendSql("select furl from t_zjsc");
			IRowSet rs=builder.executeQuery();
			String url=null;
	        while(rs.next()){
	        	url=rs.getString("furl");
	        }
	        if(url!=null){
	        	SelectorItemCollection sel = new SelectorItemCollection();
				sel.add("*");
				sel.add("company.*");
				sel.add("payerAccountBank.*");
				sel.add("payerAccountBank.property.*");
				sel.add("payeeType.*");
				sel.add("feeType.*");
				sel.add("entries.*");
				sel.add("entries.expenseType.*");
				sel.add("entries.costCenter.*");
				PaymentBillInfo pay=PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(id), sel);
				
//				Set idSet=new HashSet();
//				idSet.add(pay.getId().toString());
//				PaymentBillFactory.getRemoteInstance().payForBook(idSet,true);
				
//				IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getRemoteInstance();
//				pay.setPayDate(new Date());
//    			Map bgmap=iBgControlFacade.checkBudget(pay);
//    			if(!((Boolean)bgmap.get("isPass")).booleanValue()){
//    				FDCMsgBox.showWarning(this,bgmap.get("message").toString());
//    				return;
//    			}
				if(pay.isIsCommittoBe()){
					FDCMsgBox.showWarning(this,"已提交资金系统，不能传递！");
					return;
				}
				if(!pay.getBillStatus().equals(BillStatusEnum.PAYED)){
					FDCMsgBox.showWarning(this,"非已付款状态，不能传递！");
					return;
				}
				if(pay.getPayerAccountBank()==null){
					FDCMsgBox.showWarning(this,"付款账号不能为空！");
					return;
				}
				JSONObject json=new JSONObject();
				String srcId=null;
				if(pay.getSourceFunction()==null){
					srcId=pay.getId()+"-1";
					json.put("SERIAL_NO_ERP", srcId);
				}else{
					srcId=pay.getSourceFunction();
					json.put("SERIAL_NO_ERP", pay.getSourceFunction());
				}
//				else{
//					srcId=pay.getSourceFunction().split("-")[0]+"-"+(Integer.parseInt(pay.getSourceFunction().split("-")[1])+1);
//					json.put("SERIAL_NO_ERP", srcId);
//				}
				json.put("VOUCHER_NO_ERP", pay.getNumber());
				json.put("CORP_CODE", pay.getCompany().getNumber());
				if(pay.getPayerAccountBank()!=null){
					json.put("PAYER_ACC_NO", pay.getPayerAccountBank().getBankAccountNumber());
				}
				json.put("AMT", pay.getActPayAmt());
				
				json.put("PAYEE_CORP_CODE", pay.getPayeeNumber());
				json.put("PAYEE_ACC_NO", pay.getPayeeAccountBank());
				json.put("PAYEE_NAME", pay.getPayeeName());
				
				if(pay.getBankNumber()!=null&&!"".equals(pay.getBankNumber().trim())){
					json.put("PAYEE_CODE", pay.getBankNumber());
				}else{
					BankNumCollection bankNum=BankNumFactory.getRemoteInstance().getBankNumCollection("select number from where name='"+pay.getPayeeBank()+"'");
					if(bankNum.size()>0){
						json.put("PAYEE_CODE", bankNum.get(0).getNumber());
					}else{
						FDCMsgBox.showWarning(this,"收款银行："+pay.getPayeeBank()+" 维护错误，请维护正确的开户行全称名！");
						return;
					}
				}
				json.put("VOUCHER_TYPE", "34");
				
//				json.put("ABS", "EAS系统支付");
				if(pay.getFeeType()!=null){
					json.put("ABS", pay.getFeeType().getName());
				}else{
					json.put("ABS", "EAS系统支付");
				}
//				json.put("PURPOSE", pay.getUsage());
				
				json.put("DATA_SOURCE", SysContext.getSysContext().getDCNumber());
				
				if(pay.getPayeeName().length()>4){
					json.put("ISFORINDIVIDUAL", "0");
				}else{
					json.put("ISFORINDIVIDUAL", "1");
				}
				Service s=new Service();
				Call call=(Call)s.createCall();
				if(pay.getPayeeType()!=null&&pay.getPayeeType().getName().equals("公司")){
					 call.setOperationName(new QName("http://server.webservice.sdkg.erp.hibernate.byttersoft.com","erpAllocationJsonData	"));
					 call.setTargetEndpointAddress(url+"/erp/services/sdkgAllocationWebService?wsdl");
					 call.setReturnQName(new QName("http://server.webservice.sdkg.erp.hibernate.byttersoft.com","erpAllocationJsonDataResponse"));
					 
					 if(pay.getFeeType()!=null)
						 json.put("BIS_TYPE", pay.getFeeType().getNumber());
				}else{
					 call.setOperationName(new QName("http://server.webservice.sdkg.erp.hibernate.byttersoft.com","erpPaymentJsonData"));
					 call.setTargetEndpointAddress(url+"/erp/services/sdkgPaymentWebService?wsdl");
					 call.setReturnQName(new QName("http://server.webservice.sdkg.erp.hibernate.byttersoft.com","erpPaymentJsonDataResponse"));
				}
				call.setTimeout(Integer.valueOf(1000*600000*60));
		        call.setMaintainSession(true);
		        
		        
		        JSONArray arr=new JSONArray();
	        	arr.add(json);
	        	
		        String result=(String)call.invoke(new Object[]{arr.toString()} );
		         
		        JSONArray rso = JSONArray.fromObject(result);
				if(rso.getJSONObject(0).get("STATUS").equals("2")){
					SelectorItemCollection sic = new SelectorItemCollection();
					sic.add("sourceFunction");
					sic.add("isCommittoBe");
					pay.setSourceFunction(srcId);
					pay.setIsCommittoBe(true);
					PaymentBillFactory.getRemoteInstance().updatePartial(pay, sic);
					FDCMsgBox.showInfo(this,"传递成功！");
				}else{
					FDCMsgBox.showWarning(this,rso.getJSONObject(0).getString("MESSAGE"));
				}
	        }
		} catch (RemoteException e1) {
			FDCMsgBox.showWarning(this,e1.getMessage());
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (BOSException e1) {
			e1.printStackTrace();
		}
	}
	
}
