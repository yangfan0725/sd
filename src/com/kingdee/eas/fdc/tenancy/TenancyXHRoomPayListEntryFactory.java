package com.kingdee.eas.fdc.tenancy;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TenancyXHRoomPayListEntryFactory
{
    private TenancyXHRoomPayListEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyXHRoomPayListEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyXHRoomPayListEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("BD7F53AE") ,com.kingdee.eas.fdc.tenancy.ITenancyXHRoomPayListEntry.class);
    }
    
    public static com.kingdee.eas.fdc.tenancy.ITenancyXHRoomPayListEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyXHRoomPayListEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("BD7F53AE") ,com.kingdee.eas.fdc.tenancy.ITenancyXHRoomPayListEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyXHRoomPayListEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyXHRoomPayListEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("BD7F53AE"));
    }
    public static com.kingdee.eas.fdc.tenancy.ITenancyXHRoomPayListEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.tenancy.ITenancyXHRoomPayListEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("BD7F53AE"));
    }
}