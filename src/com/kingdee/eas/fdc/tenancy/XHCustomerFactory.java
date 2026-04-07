package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class XHCustomerFactory
{
    private XHCustomerFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IXHCustomer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHCustomer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("5EE79537") ,com.kingdee.eas.fdc.tenancy.IXHCustomer.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IXHCustomer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHCustomer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("5EE79537") ,com.kingdee.eas.fdc.tenancy.IXHCustomer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IXHCustomer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHCustomer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("5EE79537"));
    }
    public static com.kingdee.eas.fdc.tenancy.IXHCustomer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHCustomer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("5EE79537"));
    }
}