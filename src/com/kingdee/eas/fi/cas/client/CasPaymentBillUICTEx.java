package com.kingdee.eas.fi.cas.client;

import java.awt.event.ActionEvent;
import java.net.URLEncoder;

import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.ui.face.IUIFactory;
import com.kingdee.bos.ui.face.IUIWindow;
import com.kingdee.bos.ui.face.UIFactory;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.common.client.UIContext;
import com.kingdee.eas.common.client.UIFactoryName;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.ContractBillFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ExpenseCostInfo;
import com.kingdee.eas.fdc.contract.FundTransferInfo;
import com.kingdee.eas.fdc.contract.PayReqUtils;
import com.kingdee.eas.fdc.contract.TripCostInfo;
import com.kingdee.eas.fdc.contract.app.OaUtil;
import com.kingdee.eas.fdc.contract.client.ContractWithoutTextEditUI;
import com.kingdee.eas.fdc.contract.client.ExpenseCostEditUI;
import com.kingdee.eas.fdc.contract.client.FundTransferEditUI;
import com.kingdee.eas.fdc.contract.client.PayRequestBillEditUI;
import com.kingdee.eas.fdc.contract.client.TripCostEditUI;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.util.SysUtil;

public class CasPaymentBillUICTEx extends CasPaymentBillUI{

	public CasPaymentBillUICTEx() throws Exception {
		super();
	}

	protected void verifyInput(ActionEvent arg0) throws Exception {
		super.verifyInput(arg0);
		if(SysContext.getSysContext().getDcAlias().equals("SUNDY-ERP")||SysContext.getSysContext().getDcAlias().equals("SUNDY-WY")){
			for(int i=0;i<this.kdtEntries.getRowCount();i++){
				if(this.kdtEntries.getRow(i).getCell("costCenter").getValue()==null){
					FDCMsgBox.showWarning(this,"分录成本中心不能为空！");
					SysUtil.abort();
				}
				if(this.kdtEntries.getRow(i).getCell("expenseType").getValue()==null){
					FDCMsgBox.showWarning(this,"分录费用类型不能为空！");
					SysUtil.abort();
				}
			}
		}
	}
	public void actionTraceUp_actionPerformed(ActionEvent e) throws Exception {
		String id = this.editData.getId().toString();
		PaymentBillInfo info=PaymentBillFactory.getRemoteInstance().getPaymentBillInfo(new ObjectUuidPK(id));
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
