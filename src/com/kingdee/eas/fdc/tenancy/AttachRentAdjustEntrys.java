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
import com.kingdee.eas.framework.DataBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.IDataBase;

public class AttachRentAdjustEntrys extends DataBase implements IAttachRentAdjustEntrys
{
    public AttachRentAdjustEntrys()
    {
        super();
        registerInterface(IAttachRentAdjustEntrys.class, this);
    }
    public AttachRentAdjustEntrys(Context ctx)
    {
        super(ctx);
        registerInterface(IAttachRentAdjustEntrys.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("9C40C9D7");
    }
    private AttachRentAdjustEntrysController getController() throws BOSException
    {
        return (AttachRentAdjustEntrysController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@return
     */
    public AttachRentAdjustEntrysInfo getAttachRentAdjustEntrysInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachRentAdjustEntrysInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@param selector selector
     *@return
     */
    public AttachRentAdjustEntrysInfo getAttachRentAdjustEntrysInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachRentAdjustEntrysInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql oql
     *@return
     */
    public AttachRentAdjustEntrysInfo getAttachRentAdjustEntrysInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getAttachRentAdjustEntrysInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ????-System defined method
     *@return
     */
    public AttachRentAdjustEntrysCollection getAttachRentAdjustEntrysCollection() throws BOSException
    {
        try {
            return getController().getAttachRentAdjustEntrysCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ????-System defined method
     *@param view view
     *@return
     */
    public AttachRentAdjustEntrysCollection getAttachRentAdjustEntrysCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getAttachRentAdjustEntrysCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ????-System defined method
     *@param oql oql
     *@return
     */
    public AttachRentAdjustEntrysCollection getAttachRentAdjustEntrysCollection(String oql) throws BOSException
    {
        try {
            return getController().getAttachRentAdjustEntrysCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}