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
import java.util.Date;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.fdc.tenancy.app.*;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fi.cas.ReceivingBillCollection;

public class XHTenancyBill extends TenBillBase implements IXHTenancyBill
{
    public XHTenancyBill()
    {
        super();
        registerInterface(IXHTenancyBill.class, this);
    }
    public XHTenancyBill(Context ctx)
    {
        super(ctx);
        registerInterface(IXHTenancyBill.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E628832E");
    }
    private XHTenancyBillController getController() throws BOSException
    {
        return (XHTenancyBillController)getBizController();
    }
    /**
     *取值-System defined method
     *@param pk pk
     *@return
     */
    public XHTenancyBillInfo getXHTenancyBillInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getXHTenancyBillInfo(getContext(), pk);
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
    public XHTenancyBillInfo getXHTenancyBillInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getXHTenancyBillInfo(getContext(), pk, selector);
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
    public XHTenancyBillInfo getXHTenancyBillInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getXHTenancyBillInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取集合-System defined method
     *@return
     */
    public XHTenancyBillCollection getXHTenancyBillCollection() throws BOSException
    {
        try {
            return getController().getXHTenancyBillCollection(getContext());
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
    public XHTenancyBillCollection getXHTenancyBillCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getXHTenancyBillCollection(getContext(), view);
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
    public XHTenancyBillCollection getXHTenancyBillCollection(String oql) throws BOSException
    {
        try {
            return getController().getXHTenancyBillCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *租赁交接-User defined method
     *@param tenAttachEntryColl tenAttachEntryColl
     *@param tenancyRoomEntryColl tenancyRoomEntryColl
     *@param tenancyBillInfo tenancyBillInfo
     *@param handleRoomEntryColl handleRoomEntryColl
     */
    public void handleTenancyRoom(IObjectCollection tenAttachEntryColl, TenancyRoomEntryCollection tenancyRoomEntryColl, XHTenancyBillInfo tenancyBillInfo, HandleRoomEntrysCollection handleRoomEntryColl) throws BOSException
    {
        try {
            getController().handleTenancyRoom(getContext(), tenAttachEntryColl, tenancyRoomEntryColl, tenancyBillInfo, handleRoomEntryColl);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *资金结转（其实就是事物的新增多张收款单）-User defined method
     *@param receivingBills 收款单
     */
    public void carryForward(ReceivingBillCollection receivingBills) throws BOSException, EASBizException
    {
        try {
            getController().carryForward(getContext(), receivingBills);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *作废-User defined method
     *@param pk pk
     */
    public void blankOut(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().blankOut(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *补录租赁开始日期-User defined method
     *@param tenancyBillInfo tenancyBillInfo
     *@param repairStartDate 补录的租赁开始日期
     *@param firstLease 首期类型
     *@param firstLeaseDate 首期结束日期
     */
    public void repairStartDate(XHTenancyBillInfo tenancyBillInfo, Date repairStartDate, FirstLeaseTypeEnum firstLease, Date firstLeaseDate) throws BOSException, EASBizException
    {
        try {
            getController().repairStartDate(getContext(), tenancyBillInfo, repairStartDate, firstLease, firstLeaseDate);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *反审批-User defined method
     *@param billID 单据编号
     */
    public void antiAudit(BOSUuid billID) throws BOSException, EASBizException
    {
        try {
            getController().antiAudit(getContext(), billID);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}