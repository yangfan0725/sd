/*jadclipse*/package com.kingdee.eas.fdc.basedata.client;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ui.face.*;
import com.kingdee.eas.common.client.*;
import com.kingdee.eas.fdc.contract.client.ContractBillEditUI;
import com.kingdee.eas.fdc.contract.client.ContractBillReceiveEditUI;
import com.kingdee.eas.fdc.contract.client.ExpenseApplyEditUI;
import com.kingdee.eas.fdc.contract.client.ExpenseCostEditUI;
import com.kingdee.eas.fdc.contract.client.FundContractEditUI;
import com.kingdee.eas.fdc.contract.client.PurchaseApplyEditUI;
import com.kingdee.eas.fdc.contract.client.PurchaseDocumentEditUI;
import com.kingdee.eas.fdc.contract.client.TripApplyEditUI;
import com.kingdee.eas.fdc.contract.client.TripCostEditUI;
import com.kingdee.eas.fdc.contract.programming.client.ProgrammingEditUI;
import com.kingdee.eas.util.client.ExceptionHandler;
public class ContractTypePromptSelector
    implements KDPromptSelector
{
            public ContractTypePromptSelector(CoreUIObject ui)
            {






















/*  35*/        this.ui = ui;
            }
            public void show()
            {

/*  40*/        UIContext context = new UIContext(ui);
/*  41*/        if(ui instanceof ProgrammingEditUI)
/*  42*/            context.put("ALL", Boolean.TRUE);

				if(ui instanceof ContractBillReceiveEditUI){
					context.put("isReceive", Boolean.TRUE);
				}else{
					context.put("isReceive", Boolean.FALSE);
				}
				if(ui instanceof PurchaseApplyEditUI || ui instanceof PurchaseDocumentEditUI){
					context.put("isPurchase", Boolean.TRUE);
				}else{
					context.put("isPurchase", Boolean.FALSE);
				}
				if(ui instanceof FundContractEditUI){
					context.put("isFund", Boolean.TRUE);
				}else{
					context.put("isFund", Boolean.FALSE);
				}
				if(ui instanceof TripApplyEditUI || ui instanceof TripCostEditUI){
					context.put("isTrip", Boolean.TRUE);
				}else{
					context.put("isTrip", Boolean.FALSE);
				}
				if(ui instanceof ExpenseApplyEditUI || ui instanceof ExpenseCostEditUI){
					context.put("isExpense", Boolean.TRUE);
				}else{
					context.put("isExpense", Boolean.FALSE);
				}
					/*  42*/            

/*  44*/        context.put("CANRESIZE", Boolean.FALSE);

/*  46*/        IUIFactory uiFactory = null;

/*  48*/        try
                {
/* <-MISALIGNED-> */ /*  48*/            uiFactory = UIFactory.createUIFactory(UIFactoryName.MODEL);
/* <-MISALIGNED-> */ /*  49*/            f7UI = uiFactory.create(ContractTypeF7UI.class.getName(), context, null, OprtState.VIEW);
/* <-MISALIGNED-> */ /*  50*/            f7UI.show();
                }
/* <-MISALIGNED-> */ /*  52*/        catch(BOSException ex)
                {
/* <-MISALIGNED-> */ /*  53*/            ExceptionHandler.handle(ui, ex);
                }
            }
            public boolean isCanceled()
            {
/* <-MISALIGNED-> */ /*  59*/        ContractTypeF7UI f7UIObject = (ContractTypeF7UI)f7UI.getUIObject();/*  61*/        return f7UIObject.isCancel();
            }
            public Object getData()
            {



/*  68*/        try
                {
/* <-MISALIGNED-> */ /*  68*/            ContractTypeF7UI f7UIObject = (ContractTypeF7UI)f7UI.getUIObject();/*  70*/            return f7UIObject.getData();
                }/*  71*/        catch(Exception e)
                {/*  72*/            e.printStackTrace();
                }

/*  75*/        return null;
            }
            protected IUIWindow f7UI;
            protected CoreUIObject ui;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_basedata_client.jar
	Total time: 47 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/