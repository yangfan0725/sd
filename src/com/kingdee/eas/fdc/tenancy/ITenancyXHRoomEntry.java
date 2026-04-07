package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
//import com.kingdee.bos.metadata.*;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.*;
import com.kingdee.bos.Context;

import java.lang.String;
import com.kingdee.bos.util.*;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.Context;
import com.kingdee.bos.BOSException;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.framework.ICoreBase;

public interface ITenancyXHRoomEntry extends ICoreBase
{
    public TenancyXHRoomEntryInfo getTenancyXHRoomEntryInfo(IObjectPK pk) throws BOSException, EASBizException;
    public TenancyXHRoomEntryInfo getTenancyXHRoomEntryInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public TenancyXHRoomEntryInfo getTenancyXHRoomEntryInfo(String oql) throws BOSException, EASBizException;
    public TenancyXHRoomEntryCollection getTenancyXHRoomEntryCollection() throws BOSException;
    public TenancyXHRoomEntryCollection getTenancyXHRoomEntryCollection(EntityViewInfo view) throws BOSException;
    public TenancyXHRoomEntryCollection getTenancyXHRoomEntryCollection(String oql) throws BOSException;
}