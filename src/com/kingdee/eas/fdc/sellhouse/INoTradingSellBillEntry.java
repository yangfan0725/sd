package com.kingdee.eas.fdc.sellhouse;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBillEntryBase;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;

public interface INoTradingSellBillEntry extends ICoreBillEntryBase
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public NoTradingSellBillEntryInfo getNoTradingSellBillEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public NoTradingSellBillEntryInfo getNoTradingSellBillEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public NoTradingSellBillEntryInfo getNoTradingSellBillEntryInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(NoTradingSellBillEntryInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, NoTradingSellBillEntryInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, NoTradingSellBillEntryInfo model) throws BOSException, EASBizException;
    public void updatePartial(NoTradingSellBillEntryInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, NoTradingSellBillEntryInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public NoTradingSellBillEntryCollection getNoTradingSellBillEntryCollection() throws BOSException;
    public NoTradingSellBillEntryCollection getNoTradingSellBillEntryCollection(EntityViewInfo view) throws BOSException;
    public NoTradingSellBillEntryCollection getNoTradingSellBillEntryCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
}