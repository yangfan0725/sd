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
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBase;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.ICoreBase;

public class TenancyXHRoomEntry extends CoreBase implements ITenancyXHRoomEntry
{
    public TenancyXHRoomEntry()
    {
        super();
        registerInterface(ITenancyXHRoomEntry.class, this);
    }
    public TenancyXHRoomEntry(Context ctx)
    {
        super(ctx);
        registerInterface(ITenancyXHRoomEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("804539B0");
    }
    private TenancyXHRoomEntryController getController() throws BOSException
    {
        return (TenancyXHRoomEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public TenancyXHRoomEntryInfo getTenancyXHRoomEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyXHRoomEntryInfo(getContext(), pk);
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
    public TenancyXHRoomEntryInfo getTenancyXHRoomEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyXHRoomEntryInfo(getContext(), pk, selector);
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
    public TenancyXHRoomEntryInfo getTenancyXHRoomEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getTenancyXHRoomEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public TenancyXHRoomEntryCollection getTenancyXHRoomEntryCollection() throws BOSException
    {
        try {
            return getController().getTenancyXHRoomEntryCollection(getContext());
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
    public TenancyXHRoomEntryCollection getTenancyXHRoomEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getTenancyXHRoomEntryCollection(getContext(), view);
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
    public TenancyXHRoomEntryCollection getTenancyXHRoomEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getTenancyXHRoomEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}