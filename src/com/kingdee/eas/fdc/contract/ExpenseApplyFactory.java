package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ExpenseApplyFactory
{
    private ExpenseApplyFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IExpenseApply getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseApply)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("AAC743B1") ,com.kingdee.eas.fdc.contract.IExpenseApply.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IExpenseApply getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseApply)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("AAC743B1") ,com.kingdee.eas.fdc.contract.IExpenseApply.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IExpenseApply getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseApply)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("AAC743B1"));
    }
    public static com.kingdee.eas.fdc.contract.IExpenseApply getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IExpenseApply)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("AAC743B1"));
    }
}