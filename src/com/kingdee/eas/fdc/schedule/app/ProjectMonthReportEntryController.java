package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.schedule.ProjectMonthReportEntryCollection;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ProjectMonthReportEntryController extends OpReportEntryBaseController
{
    public ProjectMonthReportEntryInfo getProjectMonthReportEntryInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ProjectMonthReportEntryInfo getProjectMonthReportEntryInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ProjectMonthReportEntryInfo getProjectMonthReportEntryInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ProjectMonthReportEntryCollection getProjectMonthReportEntryCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public ProjectMonthReportEntryCollection getProjectMonthReportEntryCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ProjectMonthReportEntryCollection getProjectMonthReportEntryCollection(Context ctx) throws BOSException, RemoteException;
}