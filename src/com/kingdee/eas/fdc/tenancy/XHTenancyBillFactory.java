package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class XHTenancyBillFactory
{
    private XHTenancyBillFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IXHTenancyBill getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHTenancyBill)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("E628832E") ,com.kingdee.eas.fdc.tenancy.IXHTenancyBill.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IXHTenancyBill getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHTenancyBill)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("E628832E") ,com.kingdee.eas.fdc.tenancy.IXHTenancyBill.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IXHTenancyBill getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHTenancyBill)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("E628832E"));
    }
    public static com.kingdee.eas.fdc.tenancy.IXHTenancyBill getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHTenancyBill)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("E628832E"));
    }
}