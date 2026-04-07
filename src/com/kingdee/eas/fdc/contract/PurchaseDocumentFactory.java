package com.kingdee.eas.fdc.contract;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.BOSObjectFactory;
import com.kingdee.bos.util.BOSObjectType;
import com.kingdee.bos.Context;

public class PurchaseDocumentFactory
{
    private PurchaseDocumentFactory()
    {
    }
    public static com.kingdee.eas.fdc.contract.IPurchaseDocument getRemoteInstance() throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPurchaseDocument)BOSObjectFactory.createRemoteBOSObject(new BOSObjectType("A3FE5577") ,com.kingdee.eas.fdc.contract.IPurchaseDocument.class);
    }
    
    public static com.kingdee.eas.fdc.contract.IPurchaseDocument getRemoteInstanceWithObjectContext(Context objectCtx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPurchaseDocument)BOSObjectFactory.createRemoteBOSObjectWithObjectContext(new BOSObjectType("A3FE5577") ,com.kingdee.eas.fdc.contract.IPurchaseDocument.class, objectCtx);
    }
    public static com.kingdee.eas.fdc.contract.IPurchaseDocument getLocalInstance(Context ctx) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPurchaseDocument)BOSObjectFactory.createBOSObject(ctx, new BOSObjectType("A3FE5577"));
    }
    public static com.kingdee.eas.fdc.contract.IPurchaseDocument getLocalInstance(String sessionID) throws BOSException
    {
        return (com.kingdee.eas.fdc.contract.IPurchaseDocument)BOSObjectFactory.createBOSObject(sessionID, new BOSObjectType("A3FE5577"));
    }
}