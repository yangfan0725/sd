package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class TripCostFactory
{
    private TripCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.ITripCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITripCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("CBB614CD") ,com.kingdee.eas.fdc.contract.ITripCost.class);
    }
    
    public static com.kingdee.eas.fdc.contract.ITripCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITripCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("CBB614CD") ,com.kingdee.eas.fdc.contract.ITripCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.ITripCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITripCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("CBB614CD"));
    }
    public static com.kingdee.eas.fdc.contract.ITripCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.ITripCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("CBB614CD"));
    }
}