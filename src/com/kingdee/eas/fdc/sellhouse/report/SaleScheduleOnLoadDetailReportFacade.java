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

public class SaleScheduleOnLoadDetailReportFacade extends CommRptBase implements ISaleScheduleOnLoadDetailReportFacade
{
    public SaleScheduleOnLoadDetailReportFacade()
    {
        super();
        registerInterface(ISaleScheduleOnLoadDetailReportFacade.class, this);
    }
    public SaleScheduleOnLoadDetailReportFacade(Context ctx)
    {
        super(ctx);
        registerInterface(ISaleScheduleOnLoadDetailReportFacade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("92F0A08E");
    }
    private SaleScheduleOnLoadDetailReportFacadeController getController() throws BOSException
    {
        return (SaleScheduleOnLoadDetailReportFacadeController)getBizController();
    }
}