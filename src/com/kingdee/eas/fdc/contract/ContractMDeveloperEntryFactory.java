package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class ContractMDeveloperEntryFactory
{
    private ContractMDeveloperEntryFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IContractMDeveloperEntry getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMDeveloperEntry)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("C745D488") ,com.kingdee.eas.fdc.contract.IContractMDeveloperEntry.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IContractMDeveloperEntry getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMDeveloperEntry)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("C745D488") ,com.kingdee.eas.fdc.contract.IContractMDeveloperEntry.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IContractMDeveloperEntry getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMDeveloperEntry)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("C745D488"));
    }
    public static com.kingdee.eas.fdc.contract.IContractMDeveloperEntry getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IContractMDeveloperEntry)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("C745D488"));
    }
}