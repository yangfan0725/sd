package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyXHRoomEntryFactory
{
    private TenancyXHRoomEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyXHRoomEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyXHRoomEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("804539B0") ,com.kingdee.eas.fdc.tenancy.ITenancyXHRoomEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyXHRoomEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyXHRoomEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("804539B0") ,com.kingdee.eas.fdc.tenancy.ITenancyXHRoomEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyXHRoomEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyXHRoomEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("804539B0"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyXHRoomEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyXHRoomEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("804539B0"));
    }
}