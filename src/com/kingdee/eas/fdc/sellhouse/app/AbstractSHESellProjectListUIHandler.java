/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.app;

import com.kingdee.bos.Context;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.eas.framework.batchHandler.ResponseContext;


/**
 * output class name
 */
public abstract class AbstractSHESellProjectListUIHandler extends com.kingdee.eas.framework.app.TreeListUIHandler

{
	public void handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionImport(request,response,context);
	}
	protected void _handleActionImport(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionEditEndDate(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionEditEndDate(request,response,context);
	}
	protected void _handleActionEditEndDate(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionToMT(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionToMT(request,response,context);
	}
	protected void _handleActionToMT(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
	public void handleActionToYB(RequestContext request,ResponseContext response, Context context) throws Exception {
		_handleActionToYB(request,response,context);
	}
	protected void _handleActionToYB(RequestContext request,ResponseContext response, Context context) throws Exception {
	}
}