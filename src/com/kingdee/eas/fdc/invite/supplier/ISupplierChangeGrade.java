package com.kingdee.eas.fdc.invite.supplier;

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
import com.kingdee.eas.fdc.basedata.IFDCBill;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;

public interface ISupplierChangeGrade extends IFDCBill
{
    public SupplierChangeGradeCollection getSupplierChangeGradeCollection() throws BOSException;
    public SupplierChangeGradeCollection getSupplierChangeGradeCollection(EntityViewInfo view) throws BOSException;
    public SupplierChangeGradeCollection getSupplierChangeGradeCollection(String oql) throws BOSException;
    public SupplierChangeGradeInfo getSupplierChangeGradeInfo(IObjectPK pk) throws BOSException, EASBizException;
    public SupplierChangeGradeInfo getSupplierChangeGradeInfo(IObjectPK pk, SelectorItemCollection selector) throws BOSException, EASBizException;
    public SupplierChangeGradeInfo getSupplierChangeGradeInfo(String oql) throws BOSException, EASBizException;
}