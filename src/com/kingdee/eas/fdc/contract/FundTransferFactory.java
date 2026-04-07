package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FundTransferFactory
{
    private FundTransferFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IFundTransfer getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFundTransfer)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("B0D6AB8B") ,com.kingdee.eas.fdc.contract.IFundTransfer.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IFundTransfer getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFundTransfer)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("B0D6AB8B") ,com.kingdee.eas.fdc.contract.IFundTransfer.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IFundTransfer getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFundTransfer)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("B0D6AB8B"));
    }
    public static com.kingdee.eas.fdc.contract.IFundTransfer getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFundTransfer)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("B0D6AB8B"));
    }
}