package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.framework.ejb.EJBRemoteException;
import com.kingdee.bos.util.BOSObjectType;
import java.rmi.RemoteException;
import com.kingdee.bos.framework.AbstractBizCtrl;
import com.kingdee.bos.orm.template.ORMObject;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import java.util.Map;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import java.util.Set;
import com.kingdee.eas.fdc.contract.programming.ProgrammingContractInfo;
import com.kingdee.eas.fdc.basedata.FDCBill;
import com.kingdee.bos.util.*;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.eas.fdc.contract.app.*;
import java.util.List;

public class ContractBillReceive extends FDCBill implements IContractBillReceive
{
    public ContractBillReceive()
    {
        super();
        registerInterface(IContractBillReceive.class, this);
    }
    public ContractBillReceive(Context ctx)
    {
        super(ctx);
        registerInterface(IContractBillReceive.class, this);
    }
    public BOSObjectType getType()
    {
        return new BOSObjectType("E9EEDE4F");
    }
    private ContractBillReceiveController getController() throws BOSException
    {
        return (ContractBillReceiveController)getBizController();
    }
    /**
     *取集合-System defined method
     *@return
     */
    public ContractBillReceiveCollection getContractBillReceiveCollection() throws BOSException
    {
        try {
            return getController().getContractBillReceiveCollection(getContext());
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
    public ContractBillReceiveCollection getContractBillReceiveCollection(EntityViewInfo view) throws BOSException
    {
        try {
            return getController().getContractBillReceiveCollection(getContext(), view);
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
    public ContractBillReceiveCollection getContractBillReceiveCollection(String oql) throws BOSException
    {
        try {
            return getController().getContractBillReceiveCollection(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *取值-System defined method
     *@param pk 取值
     *@return
     */
    public ContractBillReceiveInfo getContractBillReceiveInfo(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReceiveInfo(getContext(), pk);
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
    public ContractBillReceiveInfo getContractBillReceiveInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReceiveInfo(getContext(), pk, selector);
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
    public ContractBillReceiveInfo getContractBillReceiveInfo(String oql) throws BOSException, EASBizException
    {
        try {
            return getController().getContractBillReceiveInfo(getContext(), oql);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合同归档-User defined method
     *@param cbInfo cbInfo
     *@param storeNumber 界面归档编号
     *@return
     */
    public boolean contractBillStore(ContractBillReceiveInfo cbInfo, String storeNumber) throws BOSException, EASBizException
    {
        try {
            return getController().contractBillStore(getContext(), cbInfo, storeNumber);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *选择不计成本的金额为否,金额录在分录上,显示的时候要从分录上取-User defined method
     *@param idMap idMap
     *@return
     */
    public Map getAmtByAmtWithoutCost(Map idMap) throws BOSException, EASBizException
    {
        try {
            return getController().getAmtByAmtWithoutCost(getContext(), idMap);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *合同反归档-User defined method
     *@param idList idList
     *@return
     */
    public boolean contractBillAntiStore(List idList) throws BOSException, EASBizException
    {
        try {
            return getController().contractBillAntiStore(getContext(), idList);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *是否已经拆分-User defined method
     *@param pk pk
     *@return
     */
    public boolean isContractSplit(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().isContractSplit(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *拆分-User defined method
     *@param pk pk
     */
    public void split(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().split(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *自动删除拆分-User defined method
     *@param pk pk
     *@return
     */
    public boolean autoDelSplit(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().autoDelSplit(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取合同类型编码-User defined method
     *@param pk pk
     *@return
     */
    public String getContractTypeNumber(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            return getController().getContractTypeNumber(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取合同相关信息:附件,正文,最后审批人等-User defined method
     *@param contractIds 合同ID集合
     *@return
     */
    public Map getOtherInfo(Set contractIds) throws BOSException, EASBizException
    {
        try {
            return getController().getOtherInfo(getContext(), contractIds);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *历史合同关联合约规划，以及全期合同关联合约规划-User defined method
     *@param billId billId
     *@param programming programming
     */
    public void synUpdateProgramming(String billId, ProgrammingContractInfo programming) throws BOSException, EASBizException
    {
        try {
            getController().synUpdateProgramming(getContext(), billId, programming);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *同步返回合约规划-User defined method
     *@param billId billId
     *@param programming programming
     */
    public void synReUpdateProgramming(String billId, IObjectValue programming) throws BOSException, EASBizException
    {
        try {
            getController().synReUpdateProgramming(getContext(), billId, programming);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *造价指标库录入-User defined method
     *@param pk pk
     */
    public void costIndex(IObjectPK pk) throws BOSException, EASBizException
    {
        try {
            getController().costIndex(getContext(), pk);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取OA职位-User defined method
     *@param number number
     *@return
     */
    public Map getOAPosition(String number) throws BOSException, EASBizException
    {
        try {
            return getController().getOAPosition(getContext(), number);
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
    /**
     *获取群杰印章-User defined method
     *@return
     */
    public Map getQJYZ() throws BOSException, EASBizException
    {
        try {
            return getController().getQJYZ(getContext());
        }
        catch(RemoteException err) {
            throw new EJBRemoteException(err);
        }
    }
}