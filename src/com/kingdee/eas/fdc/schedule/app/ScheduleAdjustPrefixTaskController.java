package com.kingdee.eas.fdc.schedule.app;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustPrefixTaskCollection;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.schedule.framework.app.ScheduleTaskDependController;
import com.kingdee.eas.fdc.schedule.ScheduleAdjustPrefixTaskInfo;

import java.rmi.RemoteException;
import com.kingdee.bos.framework.ejb.BizController;

public interface ScheduleAdjustPrefixTaskController extends ScheduleTaskDependController
{
    public boolean exists(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public boolean exists(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public ScheduleAdjustPrefixTaskInfo getScheduleAdjustPrefixTaskInfo(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public ScheduleAdjustPrefixTaskInfo getScheduleAdjustPrefixTaskInfo(Context ctx, IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public ScheduleAdjustPrefixTaskInfo getScheduleAdjustPrefixTaskInfo(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK addnew(Context ctx, ScheduleAdjustPrefixTaskInfo model) throws BOSException, EASBizException, RemoteException;
    public void addnew(Context ctx, IObjectPK pk, ScheduleAdjustPrefixTaskInfo model) throws BOSException, EASBizException, RemoteException;
    public void update(Context ctx, IObjectPK pk, ScheduleAdjustPrefixTaskInfo model) throws BOSException, EASBizException, RemoteException;
    public void updatePartial(Context ctx, ScheduleAdjustPrefixTaskInfo model, SelectorItemCollection selector) throws BOSException, EASBizException, RemoteException;
    public void updateBigObject(Context ctx, IObjectPK pk, ScheduleAdjustPrefixTaskInfo model) throws BOSException, RemoteException;
    public void delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] getPKList(Context ctx, FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException, RemoteException;
    public ScheduleAdjustPrefixTaskCollection getScheduleAdjustPrefixTaskCollection(Context ctx) throws BOSException, RemoteException;
    public ScheduleAdjustPrefixTaskCollection getScheduleAdjustPrefixTaskCollection(Context ctx, EntityViewInfo view) throws BOSException, RemoteException;
    public ScheduleAdjustPrefixTaskCollection getScheduleAdjustPrefixTaskCollection(Context ctx, String oql) throws BOSException, RemoteException;
    public IObjectPK[] delete(Context ctx, FilterInfo filter) throws BOSException, EASBizException, RemoteException;
    public IObjectPK[] delete(Context ctx, String oql) throws BOSException, EASBizException, RemoteException;
    public void delete(Context ctx, IObjectPK[] arrayPK) throws BOSException, EASBizException, RemoteException;
}