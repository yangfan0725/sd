package com.kingdee.eas.fdc.invite.supplier.app;

import org.apache.log4j.Logger;
import javax.ejb.*;
import java.rmi.RemoteException;
import com.kingdee.bos.*;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.metadata.IMetaDataPK;
import com.kingdee.bos.metadata.rule.RuleExecutor;
import com.kingdee.bos.metadata.MetaDataPK;
//import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.framework.ejb.AbstractEntityControllerBean;
import com.kingdee.bos.framework.ejb.AbstractBizControllerBean;
//import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.IObjectCollection;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.bos.service.IServiceContext;

import java.lang.String;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.eas.fdc.invite.supplier.ChooseCollection;
import com.kingdee.eas.fdc.invite.supplier.SupplierAppraiseChooseEntryFactory;
import com.kingdee.eas.fdc.invite.supplier.SupplierSplAreaEntryFactory;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.eas.framework.CoreBaseCollection;
import com.kingdee.eas.fdc.basedata.FDCDataBaseCollection;
import com.kingdee.eas.fdc.basedata.app.FDCDataBaseControllerBean;
import com.kingdee.eas.framework.CoreBaseInfo;
import com.kingdee.eas.framework.ObjectBaseCollection;
import com.kingdee.eas.framework.DataBaseCollection;
import com.kingdee.eas.fdc.invite.supplier.ChooseInfo;
import com.kingdee.util.NumericExceptionSubItem;

public class ChooseControllerBean extends AbstractChooseControllerBean
{
    private static Logger logger =
        Logger.getLogger("com.kingdee.eas.fdc.invite.supplier.app.ChooseControllerBean");
    protected void _IsNdelete(Context ctx, String areaName)throws BOSException, EASBizException
    {
    }
    
    protected void _isReferenced(Context arg0, IObjectPK arg1) throws BOSException, EASBizException {
		FilterInfo filter = new FilterInfo();
		filter.getFilterItems().add(new FilterItemInfo("choose.id", arg1.toString()));
		if(SupplierAppraiseChooseEntryFactory.getLocalInstance(arg0).exists(filter)){
			throw new EASBizException(new NumericExceptionSubItem("100","已经被评审模板引用，不能进行删除操作！"));
		}
	}
}