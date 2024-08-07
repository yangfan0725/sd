package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;
import com.kingdee.eas.fdc.contract.app.*;

public class ContractBillReceiveTotalReportFacade extends CommRptBase implements IContractBillReceiveTotalReportFacade
{
    public ContractBillReceiveTotalReportFacade()
    {
        super();
        registerInterface(IContractBillReceiveTotalReportFacade.class, this);
    }
    public ContractBillReceiveTotalReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillReceiveTotalReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E3268DC3");
    }
    private ContractBillReceiveTotalReportFacadeController getController() throws BOSException
    {
        return (ContractBillReceiveTotalReportFacadeController)getBizController();
    }
}