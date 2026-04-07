package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TripApplyFactory
{
    private TripApplyFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ITripApply getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITripApply)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AAF0BE6E") ,com.kingdee.eas.fdc.contract.ITripApply.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ITripApply getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITripApply)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AAF0BE6E") ,com.kingdee.eas.fdc.contract.ITripApply.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ITripApply getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITripApply)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AAF0BE6E"));
    }
    public static com.kingdee.eas.fdc.contract.ITripApply getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITripApply)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AAF0BE6E"));
    }
}