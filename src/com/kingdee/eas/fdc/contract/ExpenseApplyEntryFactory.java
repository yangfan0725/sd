package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpenseApplyEntryFactory
{
    private ExpenseApplyEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IExpenseApplyEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseApplyEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("80547B41") ,com.kingdee.eas.fdc.contract.IExpenseApplyEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IExpenseApplyEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseApplyEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("80547B41") ,com.kingdee.eas.fdc.contract.IExpenseApplyEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IExpenseApplyEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseApplyEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("80547B41"));
    }
    public static com.kingdee.eas.fdc.contract.IExpenseApplyEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseApplyEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("80547B41"));
    }
}