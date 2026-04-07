package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class XHRoomFactory
{
    private XHRoomFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.IXHRoom getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHRoom)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("09862AB4") ,com.kingdee.eas.fdc.tenancy.IXHRoom.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.IXHRoom getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHRoom)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("09862AB4") ,com.kingdee.eas.fdc.tenancy.IXHRoom.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.IXHRoom getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHRoom)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("09862AB4"));
    }
    public static com.kingdee.eas.fdc.tenancy.IXHRoom getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.IXHRoom)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("09862AB4"));
    }
}