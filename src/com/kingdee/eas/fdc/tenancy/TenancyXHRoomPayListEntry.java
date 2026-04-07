package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.basecrm.IRevList;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.basecrm.RevList;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public class TenancyXHRoomPayListEntry extends RevList implements ITenancyXHRoomPayListEntry
{
    public TenancyXHRoomPayListEntry()
    {
        super();
        registerInterface(ITenancyXHRoomPayListEntry.class, this);
    }
    public TenancyXHRoomPayListEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyXHRoomPayListEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("BD7F53AE");
    }
    private TenancyXHRoomPayListEntryController getController() throws BOSException
    {
        return (TenancyXHRoomPayListEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenancyXHRoomPayListEntryInfo getTenancyXHRoomPayListEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyXHRoomPayListEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public TenancyXHRoomPayListEntryInfo getTenancyXHRoomPayListEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyXHRoomPayListEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql oql
     *@return
     */
    public TenancyXHRoomPayListEntryInfo getTenancyXHRoomPayListEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyXHRoomPayListEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenancyXHRoomPayListEntryCollection getTenancyXHRoomPayListEntryCollection() throws BOSException
    {
        try {
            return getController().getTenancyXHRoomPayListEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view view
     *@return
     */
    public TenancyXHRoomPayListEntryCollection getTenancyXHRoomPayListEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyXHRoomPayListEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql oql
     *@return
     */
    public TenancyXHRoomPayListEntryCollection getTenancyXHRoomPayListEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyXHRoomPayListEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}