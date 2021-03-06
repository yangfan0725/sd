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
import com.kingdee.eas.framework.ITreeBase;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.framework.TreeBase;

public class NatureEnterprise extends TreeBase implements INatureEnterprise
{
    public NatureEnterprise()
    {
        super();
        registerInterface(INatureEnterprise.class, this);
    }
    public NatureEnterprise(Context ctx)
    {
        super(ctx);
        registerInterface(INatureEnterprise.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("D4516711");
    }
    private NatureEnterpriseController getController() throws BOSException
    {
        return (NatureEnterpriseController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@return
     */
    public NatureEnterpriseInfo getNatureEnterpriseInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getNatureEnterpriseInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param pk ȡֵ
     *@param selector ȡֵ
     *@return
     */
    public NatureEnterpriseInfo getNatureEnterpriseInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getNatureEnterpriseInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡֵ-System defined method
     *@param oql ȡֵ
     *@return
     */
    public NatureEnterpriseInfo getNatureEnterpriseInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getNatureEnterpriseInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ????-System defined method
     *@return
     */
    public NatureEnterpriseCollection getNatureEnterpriseCollection() throws BOSException
    {
        try {
            return getController().getNatureEnterpriseCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ????-System defined method
     *@param view ȡ????
     *@return
     */
    public NatureEnterpriseCollection getNatureEnterpriseCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getNatureEnterpriseCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ????-System defined method
     *@param oql ȡ????
     *@return
     */
    public NatureEnterpriseCollection getNatureEnterpriseCollection(String oql) throws BOSException
    {
        try {
            return getController().getNatureEnterpriseCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}