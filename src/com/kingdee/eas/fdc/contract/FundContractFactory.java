package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class FundContractFactory
{
    private FundContractFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IFundContract getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFundContract)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("42B304B2") ,com.kingdee.eas.fdc.contract.IFundContract.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IFundContract getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFundContract)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("42B304B2") ,com.kingdee.eas.fdc.contract.IFundContract.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IFundContract getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFundContract)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("42B304B2"));
    }
    public static com.kingdee.eas.fdc.contract.IFundContract getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IFundContract)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("42B304B2"));
    }
}