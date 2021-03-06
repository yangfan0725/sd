package com.kingdee.eas.fdc.tenancy;

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

public interface ITenPriceBaseLine extends IFDCBill
{
    public TenPriceBaseLineInfo getTenPriceBaseLineInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenPriceBaseLineInfo getTenPriceBaseLineInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenPriceBaseLineInfo getTenPriceBaseLineInfo(String oql) throws BOSException, EASBizException;
    public TenPriceBaseLineCollection getTenPriceBaseLineCollection() throws BOSException;
    public TenPriceBaseLineCollection getTenPriceBaseLineCollection(EntityViewInfo view) throws BOSException;
    public TenPriceBaseLineCollection getTenPriceBaseLineCollection(String oql) throws BOSException;
    public void useTenPrice(BOSUuid billId) throws BOSException, EASBizException;
}