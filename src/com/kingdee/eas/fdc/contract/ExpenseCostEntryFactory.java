package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpenseCostEntryFactory
{
    private ExpenseCostEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IExpenseCostEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseCostEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("13842768") ,com.kingdee.eas.fdc.contract.IExpenseCostEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IExpenseCostEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseCostEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("13842768") ,com.kingdee.eas.fdc.contract.IExpenseCostEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IExpenseCostEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseCostEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("13842768"));
    }
    public static com.kingdee.eas.fdc.contract.IExpenseCostEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseCostEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("13842768"));
    }
}