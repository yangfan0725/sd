/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.supplier.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractWebUserListUIHandler extends com.kingdee.eas.fdc.basedata.app.FDCBillListUIHandler

{
	public void handleActionInvalid(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionInvalid(request,response,context);
	}
	protected void _handleActionInvalid(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAudit(request,response,context);
	}
	protected void _handleActionAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionUnAudit(request,response,context);
	}
	protected void _handleActionUnAudit(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionAssoSupplier(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionAssoSupplier(request,response,context);
	}
	protected void _handleActionAssoSupplier(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionChangePasswd(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionChangePasswd(request,response,context);
	}
	protected void _handleActionChangePasswd(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}