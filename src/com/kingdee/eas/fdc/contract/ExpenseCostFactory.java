package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpenseCostFactory
{
    private ExpenseCostFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IExpenseCost getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseCost)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("9A285B2A") ,com.kingdee.eas.fdc.contract.IExpenseCost.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IExpenseCost getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseCost)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("9A285B2A") ,com.kingdee.eas.fdc.contract.IExpenseCost.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IExpenseCost getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseCost)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("9A285B2A"));
    }
    public static com.kingdee.eas.fdc.contract.IExpenseCost getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseCost)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("9A285B2A"));
    }
}