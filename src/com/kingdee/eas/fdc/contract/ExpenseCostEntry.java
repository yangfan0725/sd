package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.framework.CoreBillEntryBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;

public class ExpenseCostEntry extends CoreBillEntryBase implements IExpenseCostEntry
{
    public ExpenseCostEntry()
    {
        super();
        registerInterface(IExpenseCostEntry.class, this);
    }
    public ExpenseCostEntry(Context ctx)
    {
        super(ctx);
        registerInterface(IExpenseCostEntry.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("13842768");
    }
    private ExpenseCostEntryController getController() throws BOSException
    {
        return (ExpenseCostEntryController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ExpenseCostEntryInfo getExpenseCostEntryInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getExpenseCostEntryInfo(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@param selector 取值
     *@return
     */
    public ExpenseCostEntryInfo getExpenseCostEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getExpenseCostEntryInfo(getContext(), pk, selector);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param oql 取值
     *@return
     */
    public ExpenseCostEntryInfo getExpenseCostEntryInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getExpenseCostEntryInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ExpenseCostEntryCollection getExpenseCostEntryCollection() throws BOSException
    {
        try {
            return getController().getExpenseCostEntryCollection(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param view 取集合
     *@return
     */
    public ExpenseCostEntryCollection getExpenseCostEntryCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getExpenseCostEntryCollection(getContext(), view);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@param oql 取集合
     *@return
     */
    public ExpenseCostEntryCollection getExpenseCostEntryCollection(String oql) throws BOSException
    {
        try {
            return getController().getExpenseCostEntryCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}