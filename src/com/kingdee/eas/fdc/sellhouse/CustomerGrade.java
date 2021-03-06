package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.eas.fdc.basedata.IFDCDataBase;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.sellhouse.app.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.basedata.FDCDataBase;

public class CustomerGrade extends FDCDataBase implements ICustomerGrade
{
    public CustomerGrade()
    {
        super();
        registerInterface(ICustomerGrade.class, this);
    }
    public CustomerGrade(Context ctx)
    {
        super(ctx);
        registerInterface(ICustomerGrade.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("4FFB5F9E");
    }
    private CustomerGradeController getController() throws BOSException
    {
        return (CustomerGradeController)getBizController();
    }
    /**
     *ȡֵ-System defined method
     *@param pk pk
     *@return
     */
    public CustomerGradeInfo getCustomerGradeInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerGradeInfo(getContext(), pk);
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
    public CustomerGradeInfo getCustomerGradeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerGradeInfo(getContext(), pk, selector);
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
    public CustomerGradeInfo getCustomerGradeInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getCustomerGradeInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *ȡ????-System defined method
     *@return
     */
    public CustomerGradeCollection getCustomerGradeCollection() throws BOSException
    {
        try {
            return getController().getCustomerGradeCollection(getContext());
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
    public CustomerGradeCollection getCustomerGradeCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getCustomerGradeCollection(getContext(), view);
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
    public CustomerGradeCollection getCustomerGradeCollection(String oql) throws BOSException
    {
        try {
            return getController().getCustomerGradeCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}