package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseApplyFactory
{
    private PurchaseApplyFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPurchaseApply getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPurchaseApply)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("6FE3BC92") ,com.kingdee.eas.fdc.contract.IPurchaseApply.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPurchaseApply getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPurchaseApply)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("6FE3BC92") ,com.kingdee.eas.fdc.contract.IPurchaseApply.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPurchaseApply getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPurchaseApply)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("6FE3BC92"));
    }
    public static com.kingdee.eas.fdc.contract.IPurchaseApply getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPurchaseApply)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("6FE3BC92"));
    }
}