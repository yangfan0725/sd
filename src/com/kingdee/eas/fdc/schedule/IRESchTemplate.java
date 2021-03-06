package com.kingdee.eas.fdc.schedule;

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
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.bos.metadata.entity.SorterItemCollection;
import com.kingdee.eas.fdc.basedata.IFDCTreeBaseData;
import com.kingdee.bos.util.*;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.bos.framework.*;
import com.kingdee.bos.util.BOSUuid;

public interface IRESchTemplate extends IFDCTreeBaseData
{
    public boolean exists(IObjectPK pk) throws BOSException, EASBizException;
    public boolean exists(FilterInfo filter) throws BOSException, EASBizException;
    public boolean exists(String oql) throws BOSException, EASBizException;
    public RESchTemplateInfo getRESchTemplateInfo(IObjectPK pk) throws BOSException, EASBizException;
    public RESchTemplateInfo getRESchTemplateInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public RESchTemplateInfo getRESchTemplateInfo(String oql) throws BOSException, EASBizException;
    public IObjectPK addnew(RESchTemplateInfo model) throws BOSException, EASBizException;
    public void addnew(IObjectPK pk, RESchTemplateInfo model) throws BOSException, EASBizException;
    public void update(IObjectPK pk, RESchTemplateInfo model) throws BOSException, EASBizException;
    public void updatePartial(RESchTemplateInfo model, SelectorItemCollection selector) throws BOSException, EASBizException;
    public void updateBigObject(IObjectPK pk, RESchTemplateInfo model) throws BOSException;
    public void delete(IObjectPK pk) throws BOSException, EASBizException;
    public IObjectPK[] getPKList() throws BOSException, EASBizException;
    public IObjectPK[] getPKList(String oql) throws BOSException, EASBizException;
    public IObjectPK[] getPKList(FilterInfo filter, SorterItemCollection sorter) throws BOSException, EASBizException;
    public RESchTemplateCollection getRESchTemplateCollection() throws BOSException;
    public RESchTemplateCollection getRESchTemplateCollection(EntityViewInfo view) throws BOSException;
    public RESchTemplateCollection getRESchTemplateCollection(String oql) throws BOSException;
    public IObjectPK[] delete(FilterInfo filter) throws BOSException, EASBizException;
    public IObjectPK[] delete(String oql) throws BOSException, EASBizException;
    public void delete(IObjectPK[] arrayPK) throws BOSException, EASBizException;
    public void importTasks(String templateID, RESchTemplateTaskCollection tasks) throws BOSException, EASBizException;
    public void audit(BOSUuid billID) throws BOSException, EASBizException;
    public void setAudittingStatus(BOSUuid billID) throws BOSException, EASBizException;
    public void setSubmitStatus(BOSUuid billID) throws BOSException, EASBizException;
    public void unAudit(BOSUuid billID) throws BOSException, EASBizException;
}