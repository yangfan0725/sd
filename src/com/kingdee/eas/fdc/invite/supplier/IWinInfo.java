package com.kingdee.eas.fdc.invite.supplier;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.util.*;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IWinInfo extends IFDCBill
{
    public WinInfoInfo getWinInfoInfo(IObjectPK pk) throws BOSException, EASBizException;
    public WinInfoInfo getWinInfoInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public WinInfoInfo getWinInfoInfo(String oql) throws BOSException, EASBizException;
    public WinInfoCollection getWinInfoCollection() throws BOSException;
    public WinInfoCollection getWinInfoCollection(EntityViewInfo view) throws BOSException;
    public WinInfoCollection getWinInfoCollection(String oql) throws BOSException;
    public void publish(BOSUuid billId) throws BOSException, EASBizException;
    public void unPublish(BOSUuid billId) throws BOSException, EASBizException;
}