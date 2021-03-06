package com.kingdee.eas.fdc.sellhouse.report;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import com.kingdee.eas.fdc.sellhouse.report.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.report.ICommRptBase;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.report.CommRptBase;

public class ContractInvoiceReportFacade extends CommRptBase implements IContractInvoiceReportFacade
{
    public ContractInvoiceReportFacade()
    {
        super();
        registerInterface(IContractInvoiceReportFacade.class, this);
    }
    public ContractInvoiceReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(IContractInvoiceReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("C465467D");
    }
    private ContractInvoiceReportFacadeController getController() throws BOSException
    {
        return (ContractInvoiceReportFacadeController)getBizController();
    }
}