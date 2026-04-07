package com.kingdee.eas.fi.cas.app;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.Context;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.dao.IObjectPK;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.bot.BOTRelationFactory;
import com.kingdee.bos.metadata.bot.IBOTRelation;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.base.permission.UserFactory;
import com.kingdee.eas.base.permission.UserInfo;
import com.kingdee.eas.basedata.assistant.AccountBankFactory;
import com.kingdee.eas.basedata.assistant.AccountBankInfo;
import com.kingdee.eas.basedata.assistant.CurrencyInfo;
import com.kingdee.eas.basedata.org.CompanyOrgUnitInfo;
import com.kingdee.eas.basedata.org.CostCenterOrgUnitInfo;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.common.client.OprtState;
import com.kingdee.eas.common.client.SysContext;
import com.kingdee.eas.cp.bc.ExpenseTypeInfo;
import com.kingdee.eas.fdc.basedata.FDCCommonServerHelper;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.FDCSQLBuilder;
import com.kingdee.eas.fdc.basedata.client.FDCMsgBox;
import com.kingdee.eas.fdc.contract.BankNumCollection;
import com.kingdee.eas.fdc.contract.BankNumFactory;
import com.kingdee.eas.fdc.contract.ContractBillInfo;
import com.kingdee.eas.fdc.contract.ContractWithoutTextInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillBgEntryInfo;
import com.kingdee.eas.fdc.contract.PayRequestBillFactory;
import com.kingdee.eas.fdc.contract.PayRequestBillInfo;
import com.kingdee.eas.fdc.contract.app.HttpClientUtil;
import com.kingdee.eas.fi.cas.CashPamentBillUtil;
import com.kingdee.eas.fi.cas.PaymentBill;
import com.kingdee.eas.fi.cas.PaymentBillCollection;
import com.kingdee.eas.fi.cas.PaymentBillEntryInfo;
import com.kingdee.eas.fi.cas.PaymentBillFactory;
import com.kingdee.eas.fi.cas.PaymentBillInfo;
import com.kingdee.eas.fi.cas.SourceTypeEnum;
import com.kingdee.eas.ma.budget.BgControlFacadeFactory;
import com.kingdee.eas.ma.budget.BgCtrlParamCollection;
import com.kingdee.eas.ma.budget.BgCtrlResultCollection;
import com.kingdee.eas.ma.budget.BgCtrlTypeEnum;
import com.kingdee.eas.ma.budget.BgItemInfo;
import com.kingdee.eas.ma.budget.IBgControlFacade;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.NumericExceptionSubItem;

public class PaymentBillControllerBeanEx extends PaymentBillControllerBean {
	
	protected Map _payForBook(Context ctx, Set idSet, boolean ifBookPayable, boolean isInvokeWF, boolean validateThrowExp)throws BOSException, EASBizException{	
		Map msg=null;
		Map map = helper.getPayValidMapByAction(ctx, idSet, 60);
		PaymentBillCollection coll = (PaymentBillCollection)map.get("validColl");
		PaymentBillInfo info = null;
        int i = 0;
        Date now=SysUtil.getAppServerTime(ctx);
        for(int size = coll.size(); i < size; i++){
            info = coll.get(i);
            if(info.getSourceType().equals(SourceTypeEnum.FDC)||(info.getSourceBillId()!=null&&BOSUuid.read(info.getSourceBillId()).getType().equals(info.getBOSType()))){
            	String payRequestBillId=null;
            	if(info.getSourceBillId()!=null){
            		PaymentBillInfo payInfo=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(info.getSourceBillId()));
            		payRequestBillId=payInfo.getFdcPayReqID();
            	}else{
            		payRequestBillId=info.getFdcPayReqID();
            	}
            	if(payRequestBillId != null){
            		PayRequestBillInfo payRequest=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(payRequestBillId),getSelectors());
            		if(payRequest.isHasClosed()){
            			throw new EASBizException(new NumericExceptionSubItem("100","付款申请单已经关闭，禁止付款！"));
            		}
//            		if(payRequest.isIsBgControl()){
//            			IBgControlFacade iBgControlFacade = BgControlFacadeFactory.getLocalInstance(ctx);
//            			info.setPayDate(now);
//            			Map bgmap=iBgControlFacade.checkBudget(info);
//            			if(!((Boolean)bgmap.get("isPass")).booleanValue()){
//            				throw new EASBizException(new NumericExceptionSubItem("100",bgmap.get("message").toString()));
//            			}
//            		}
                }
            	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
                builder.appendSql("update T_CAS_PaymentBill set fbizDate=? where fid=? ");
                builder.addParam(now);
                builder.addParam(info.getId().toString());
                builder.executeUpdate();
    		}
        }
        msg=super._payForBook(ctx, idSet, ifBookPayable,isInvokeWF,validateThrowExp);
		info = null;
        i = 0;
        
		for(int size = coll.size(); i < size; i++){
            info = coll.get(i);
            if(info.getSourceType().equals(SourceTypeEnum.FDC)||(info.getSourceBillId()!=null&&BOSUuid.read(info.getSourceBillId()).getType().equals(info.getBOSType()))){
            	if(info.getSourceBillId()!=null&&BOSUuid.read(info.getSourceBillId()).getType().equals(info.getBOSType())){
                	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
                    builder.appendSql("update T_CAS_PaymentBill set fbillstatus=?,fbizDate=? where fid=? ");
                    builder.addParam(15);
                    builder.addParam(now);
                    builder.addParam(info.getSourceBillId());
                    builder.executeUpdate();
                }
            	String payRequestBillId=null;
            	if(info.getSourceBillId()!=null){
            		PaymentBillInfo payInfo=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(info.getSourceBillId()));
            		payRequestBillId=payInfo.getFdcPayReqID();
            	}else{
            		payRequestBillId=info.getFdcPayReqID();
            	}
            	if(payRequestBillId != null){
            		PayRequestBillInfo payRequest=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(payRequestBillId),getSelectors());
//                    BgControlFacadeFactory.getLocalInstance(ctx).bgAudit(info.getId().toString(), "com.kingdee.eas.fi.cas.app.PaymentBill", null);
                    for(int k=0;k<info.getEntries().size();k++){
                    	if(info.getEntries().get(k).getSourceBillEntryId()!=null){
                    		PayRequestBillBgEntryInfo bgEntry=PayRequestBillBgEntryFactory.getLocalInstance(ctx).getPayRequestBillBgEntryInfo(new ObjectUuidPK(info.getEntries().get(k).getSourceBillEntryId()));
            				bgEntry.setActPayAmount(FDCHelper.add(bgEntry.getActPayAmount(), info.getEntries().get(k).getAmount()));
            				SelectorItemCollection sic = new SelectorItemCollection();
                			sic.add("actPayAmount");
                			PayRequestBillBgEntryFactory.getLocalInstance(ctx).updatePartial(bgEntry, sic);
                    	}
        			}
                    FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
                    builder.appendSql("select sum(factualPayAmount) payAmount from t_cas_paymentbill where fbillstatus=15 and fFdcPayReqID=? and fsourceBillId is null");
                    builder.addParam(payRequest.getId().toString());
                    IRowSet rs=builder.executeQuery();
                    BigDecimal payAmount=FDCHelper.ZERO;
                	try {
                		while(rs.next()){
                			payAmount=rs.getBigDecimal("payAmount");
                		}
                	} catch (SQLException e) {
						e.printStackTrace();
					}
                	builder.clear();
                    builder.appendSql("update T_CON_PayRequestBill set fpaydate=?,fpayAmount=? where fid=? ");
                    builder.addParam(now);
                    builder.addParam(payAmount);
                    builder.addParam(payRequest.getId().toString());
                    builder.executeUpdate();
                    
                    if(BOSUuid.read(payRequest.getContractId()).getType().equals((new ContractWithoutTextInfo()).getBOSType())){
                    	builder = new FDCSQLBuilder(ctx);
                        builder.appendSql("update T_CON_ContractWithoutText set FSignDate=? where fid=? ");
                        builder.addParam(now);
                        builder.addParam(payRequest.getContractId());
                        builder.executeUpdate();
                    }
            	}
            }
        }
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		long lt = ts.getTime();
		
		JSONObject cjo=new JSONObject();
		UserInfo u=UserFactory.getLocalInstance(ctx).getUserByID(ctx.getCaller());
		cjo.put("employeeId", u.getNumber());
		
		JSONArray formData=new JSONArray();
		i = 0;
		for(int size = coll.size(); i < size; i++){
            info = coll.get(i);
            if(info.getFdcPayReqNumber()!=null&&info.getFdcPayReqNumber().indexOf("MK")>=0){
            	JSONObject act=new JSONObject();
         		
         		act.put("formNo", info.getFdcPayReqNumber().split("-")[1]);
         		act.put("paidDate", lt);
         		act.put("type", info.getFdcPayReqNumber().split("-")[2]);
         		
         		if(info.getPayerAccountBank()==null){
         			throw new EASBizException(new NumericExceptionSubItem("100","付款账户为空！"));
         		}
         		AccountBankInfo account=AccountBankFactory.getLocalInstance(ctx).getAccountBankInfo(new ObjectUuidPK(info.getPayerAccountBank().getId()));
         		act.put("payerAccountCode", account.getBankAccountNumber());
         		
         		formData.add(act);
            }
        	if(info.getSourceBillId()!=null&&BOSUuid.read(info.getSourceBillId()).getType().equals(info.getBOSType())){
        		SelectorItemCollection srcsic = new SelectorItemCollection();
        		srcsic.add("fdcPayReqNumber");
            	PaymentBillInfo srcinfo=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(info.getSourceBillId()),srcsic);
            	if(srcinfo.getFdcPayReqNumber()!=null&&srcinfo.getFdcPayReqNumber().indexOf("MK")>=0){
                	JSONObject act=new JSONObject();
             		
             		act.put("formNo", srcinfo.getFdcPayReqNumber().split("-")[1]);
             		act.put("paidDate", lt);
             		act.put("type", srcinfo.getFdcPayReqNumber().split("-")[2]);
             		
             		if(info.getPayerAccountBank()==null){
             			throw new EASBizException(new NumericExceptionSubItem("100","付款账户为空！"));
             		}
             		AccountBankInfo account=AccountBankFactory.getLocalInstance(ctx).getAccountBankInfo(new ObjectUuidPK(info.getPayerAccountBank().getId()));
             		act.put("payerAccountCode", account.getBankAccountNumber());
             		
             		formData.add(act);
                }
        	}
		}
		if(formData.size()>0){
			JSONObject login=new JSONObject();
			String tokenId=null;
			String entCode=null;
			String appCode=null;
			String secret=null;
			String mturl=null;
			try {
				FDCSQLBuilder builder=new FDCSQLBuilder(ctx);
				builder.appendSql("select * from t_mk");
				IRowSet rs=builder.executeQuery();
				while(rs.next()){
					mturl=rs.getString("furl");
					entCode=rs.getString("fentCode");
					appCode=rs.getString("fappCode");
					secret=rs.getString("fsecret");
				}
				login.put("appCode", appCode);
				login.put("secret", SHA(secret+lt));
				login.put("timestamp", lt);
				
				String respStr = HttpClientUtil.sendRequest(mturl+"/auth/login", "POST", "application/json;charse=UTF-8", "UTF-8", null, login.toJSONString());
				
				JSONObject crso = JSONObject.parseObject(respStr);
				JSONObject d=crso.getJSONObject("data");
				if(!"true".equals(crso.getString("success"))){
					throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("message")));
				}else{
					tokenId=d.getString("tokenId");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
			}
			
			cjo.put("formData",formData);
			
			HashMap header=new HashMap();
			header.put("tokenId", tokenId);
			header.put("entCode", entCode);
			
			JSONObject json=new JSONObject();
			json.put("data",  cjo);
			json.put("timestamp", lt);
			
			try {
				String crs = HttpClientUtil.sendRequest(mturl+"/paymenttransaction/update/noSequence", "POST", "application/json;charse=UTF-8", "UTF-8", header, json.toJSONString());
				JSONObject crso = JSONObject.parseObject(crs);
				if(!"ACK".equals(crso.getString("code"))&&crso.getString("data")!=null&&crso.getString("data").indexOf("单据不处于结算状态")<0){
					throw new EASBizException(new NumericExceptionSubItem("100",crso.getString("data").replace("{", "").replace("}", "")));
				}
			} catch (Exception e) {
				throw new EASBizException(new NumericExceptionSubItem("100",e.getMessage()));
			}
		}
		return msg;
	}
	public static String SHA(final String strText) {
		MessageDigest messageDigest;
        String encodeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(strText.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encodeStr;
	}
	private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }
	protected void _delete(Context ctx, IObjectPK pk) throws BOSException, EASBizException {
		PaymentBillInfo info = getPaymentBillInfo(ctx, pk);
		String payRequestBillId=null;
    	if(info.getSourceBillId()!=null&&BOSUuid.read(info.getSourceBillId()).getType().equals(info.getBOSType())){
    		PaymentBillInfo payInfo=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(info.getSourceBillId()));
    		payRequestBillId=payInfo.getFdcPayReqID();
    	}else{
    		payRequestBillId=info.getFdcPayReqID();
    	}
		if(info.getSourceType().equals(SourceTypeEnum.FDC)){
        	if(payRequestBillId != null){
        		PayRequestBillInfo payRequest=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(payRequestBillId),getSelectors());
        		if(payRequest.isHasClosed()){
        			throw new EASBizException(new NumericExceptionSubItem("100","付款申请单已经关闭，禁止删除！"));
        		}
        	}
		}
		super._delete(ctx, pk);
		if(payRequestBillId!=null){
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select count(*) payTime from t_cas_paymentbill where fFdcPayReqID=? and fsourceBillId is null");
	        builder.addParam(payRequestBillId);
	        IRowSet rs=builder.executeQuery();
	        int  payTime=0;
	        Date payDate=null;
	    	try {
	    		while(rs.next()){
	    			payTime=rs.getInt("payTime");
	    		}
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
	    	builder.clear();
	    	
	        builder.appendSql("update T_CON_PayRequestBill set fpayTime=?,fiscreatePay=?  where fid=? ");
	        builder.addParam(payTime);
	        if(payTime>0){
	        	builder.addParam(1);
	        }else{
	        	builder.addParam(0);
	        }
	        builder.addParam(payRequestBillId);
	        builder.executeUpdate();
		}
	}
	protected Map _cancelPaySilent(Context ctx, Set idSet) throws BOSException, EASBizException {
		PaymentBillCollection coll = helper.getPayColl(ctx, idSet);
		PaymentBillInfo info = null;
		int i=0;
		for(int size = coll.size(); i < size; i++){
            info = coll.get(i);
            if(info.getSourceBillId()!=null&&BOSUuid.read(info.getSourceBillId()).getType().equals(info.getBOSType())){
            	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
                builder.appendSql("update T_CAS_PaymentBill set fbillstatus=? where fid=? ");
                builder.addParam(12);
                builder.addParam(info.getSourceBillId());
                builder.executeUpdate();
            }
        }
		Map map = helper.getPayValidMapByAction(ctx, idSet, 70);
		coll = (PaymentBillCollection)map.get("validColl");
        i = 0;
		Map reMap=super._cancelPaySilent(ctx, idSet);
		for(int size = coll.size(); i < size; i++){
			info = coll.get(i);
			PaymentBillCollection payCol=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection("select * from where sourceBillId='"+info.getId()+"'");
            if(payCol.size()>0){
            	throw new EASBizException(new NumericExceptionSubItem("100","请取消付款代理公司付款单！"));
            }
            if(info.isIsCommittoBe()){
    			throw new EASBizException(new NumericExceptionSubItem("100","已传递资金系统，禁止取消付款！"));
    		}
            if(info.getSourceType().equals(SourceTypeEnum.FDC)||(info.getSourceBillId()!=null&&BOSUuid.read(info.getSourceBillId()).getType().equals(info.getBOSType()))){
            	String payRequestBillId=null;
            	if(info.getSourceBillId()!=null){
            		PaymentBillInfo payInfo=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(info.getSourceBillId()));
            		payRequestBillId=payInfo.getFdcPayReqID();
            	}else{
            		payRequestBillId=info.getFdcPayReqID();
            	}
            	if(payRequestBillId != null){
            		PayRequestBillInfo payRequest=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(payRequestBillId),getSelectors());
            		if(payRequest.isHasClosed()){
            			throw new EASBizException(new NumericExceptionSubItem("100","付款申请单已经关闭，禁止取消付款！"));
            		}
//        			BgControlFacadeFactory.getLocalInstance(ctx).returnBudget(BOSUuid.read(info.getId().toString()), "com.kingdee.eas.fi.cas.app.PaymentBill", null);
        			for(int k=0;k<info.getEntries().size();k++){
        				if(info.getEntries().get(k).getSourceBillEntryId()!=null){
        					PayRequestBillBgEntryInfo bgEntry=PayRequestBillBgEntryFactory.getLocalInstance(ctx).getPayRequestBillBgEntryInfo(new ObjectUuidPK(info.getEntries().get(k).getSourceBillEntryId()));
            				bgEntry.setActPayAmount(FDCHelper.subtract(bgEntry.getActPayAmount(), info.getEntries().get(k).getAmount()));
            				SelectorItemCollection sic = new SelectorItemCollection();
                			sic.add("actPayAmount");
                			PayRequestBillBgEntryFactory.getLocalInstance(ctx).updatePartial(bgEntry, sic);
        				}
        			}
            		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
            		builder.appendSql("select sum(factualPayAmount) payAmount,max(fbizDate) payDate from t_cas_paymentbill where fbillstatus=15 and fFdcPayReqID=? and fsourceBillId is null");
                    builder.addParam(payRequest.getId().toString());
                    IRowSet rs=builder.executeQuery();
                    BigDecimal payAmount=FDCHelper.ZERO;
                    Date payDate=null;
                	try {
                		while(rs.next()){
                			payAmount=rs.getBigDecimal("payAmount");
                			payDate=rs.getDate("payDate");
                		}
                	} catch (SQLException e) {
						e.printStackTrace();
					}
                	builder.clear();
                	
                    builder.appendSql("update T_CON_PayRequestBill set fpaydate=?,fpayAmount=? where fid=? ");
                    builder.addParam(payDate);
                    builder.addParam(payAmount);
                    builder.addParam(payRequestBillId);
                    builder.executeUpdate();
                    
                    if(BOSUuid.read(payRequest.getContractId()).getType().equals((new ContractWithoutTextInfo()).getBOSType())){
                    	builder = new FDCSQLBuilder(ctx);
                        builder.appendSql("update T_CON_ContractWithoutText set FSignDate=? where fid=? ");
                        builder.addParam(payDate);
                        builder.addParam(payRequest.getContractId());
                        builder.executeUpdate();
                    }
                }
    		}
        }
		return reMap;
	}
	protected void _cancelPay(Context ctx, Set idSet) throws BOSException, EASBizException {
		PaymentBillCollection coll = helper.getPayColl(ctx, idSet);
		PaymentBillInfo info = null;
		int i=0;
		for(int size = coll.size(); i < size; i++){
            info = coll.get(i);
            if(info.getSourceBillId()!=null&&BOSUuid.read(info.getSourceBillId()).getType().equals(info.getBOSType())){
            	FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
                builder.appendSql("update T_CAS_PaymentBill set fbillstatus=? where fid=? ");
                builder.addParam(12);
                builder.addParam(info.getSourceBillId());
                builder.executeUpdate();
            }
        }
		Map map = helper.getPayValidMapByAction(ctx, idSet, 70);
		coll = (PaymentBillCollection)map.get("validColl");
        i = 0;
		super._cancelPay(ctx, idSet);
        for(int size = coll.size(); i < size; i++){
            info = coll.get(i);
            PaymentBillCollection payCol=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection("select * from where sourceBillId='"+info.getId()+"'");
            if(payCol.size()>0){
            	throw new EASBizException(new NumericExceptionSubItem("100","请取消付款代理公司付款单！"));
            }
            if(info.isIsCommittoBe()){
    			throw new EASBizException(new NumericExceptionSubItem("100","已传递资金系统，禁止取消付款！"));
    		}
            if(info.getSourceType().equals(SourceTypeEnum.FDC)||(info.getSourceBillId()!=null&&BOSUuid.read(info.getSourceBillId()).getType().equals(info.getBOSType()))){
            	String payRequestBillId=null;
            	if(info.getSourceBillId()!=null){
            		PaymentBillInfo payInfo=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillInfo(new ObjectUuidPK(info.getSourceBillId()));
            		payRequestBillId=payInfo.getFdcPayReqID();
            	}else{
            		payRequestBillId=info.getFdcPayReqID();
            	}
            	if(payRequestBillId != null){
            		PayRequestBillInfo payRequest=PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(payRequestBillId),getSelectors());
            		if(payRequest.isHasClosed()){
            			throw new EASBizException(new NumericExceptionSubItem("100","付款申请单已经关闭，禁止取消付款！"));
            		}
//        			BgControlFacadeFactory.getLocalInstance(ctx).returnBudget(BOSUuid.read(info.getId().toString()), "com.kingdee.eas.fi.cas.app.PaymentBill", null);
        			for(int k=0;k<info.getEntries().size();k++){
        				if(info.getEntries().get(k).getSourceBillEntryId()!=null){
        					PayRequestBillBgEntryInfo bgEntry=PayRequestBillBgEntryFactory.getLocalInstance(ctx).getPayRequestBillBgEntryInfo(new ObjectUuidPK(info.getEntries().get(k).getSourceBillEntryId()));
            				bgEntry.setActPayAmount(FDCHelper.subtract(bgEntry.getActPayAmount(), info.getEntries().get(k).getAmount()));
            				SelectorItemCollection sic = new SelectorItemCollection();
                			sic.add("actPayAmount");
                			PayRequestBillBgEntryFactory.getLocalInstance(ctx).updatePartial(bgEntry, sic);
        				}
        			}
            		FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
            		builder.appendSql("select sum(factualPayAmount) payAmount,max(fbizDate) payDate from t_cas_paymentbill where fbillstatus=15 and fFdcPayReqID=? and fsourceBillId is null");
                    builder.addParam(payRequest.getId().toString());
                    IRowSet rs=builder.executeQuery();
                    BigDecimal payAmount=FDCHelper.ZERO;
                    Date payDate=null;
                	try {
                		while(rs.next()){
                			payAmount=rs.getBigDecimal("payAmount");
                			payDate=rs.getDate("payDate");
                		}
                	} catch (SQLException e) {
						e.printStackTrace();
					}
                	builder.clear();
                	
                    builder.appendSql("update T_CON_PayRequestBill set fpaydate=?,fpayAmount=? where fid=? ");
                    builder.addParam(payDate);
                    builder.addParam(payAmount);
                    builder.addParam(payRequestBillId);
                    builder.executeUpdate();
                    
                    if(BOSUuid.read(payRequest.getContractId()).getType().equals((new ContractWithoutTextInfo()).getBOSType())){
                    	builder = new FDCSQLBuilder(ctx);
                        builder.appendSql("update T_CON_ContractWithoutText set FSignDate=? where fid=? ");
                        builder.addParam(payDate);
                        builder.addParam(payRequest.getContractId());
                        builder.executeUpdate();
                    }
                }
    		}
        }
	}
	private SelectorItemCollection getSelectors() {
		SelectorItemCollection sic = new SelectorItemCollection();
		sic.add("number");
		sic.add("state");
		sic.add("name");
		sic.add("payDate");
		sic.add("recBank");
		sic.add("recAccount");
		sic.add("moneyDesc");
		sic.add("contractNo");
		sic.add("description");
		sic.add("urgentDegree");
		sic.add("attachment");
		sic.add("bookedDate");
		sic.add("originalAmount");
		sic.add("amount");
		sic.add("exchangeRate");
		sic.add("process");
		sic.add("lastUpdateTime");

		sic.add("paymentProportion");
		sic.add("costAmount");
		sic.add("grtAmount");
		sic.add("capitalAmount");

		// 1
		sic.add("contractName");
		sic.add("changeAmt");
		sic.add("payTimes");
		// sic.add("curPlannedPayment");
		sic.add("curReqPercent");

		// 2
		sic.add("settleAmt");
		sic.add("curBackPay");
		sic.add("allReqPercent");

		//
		sic.add("guerdonOriginalAmt");
		sic.add("compensationOriginalAmt");

		// 合同内工程款
		sic.add("lstPrjAllPaidAmt");
		sic.add("lstPrjAllReqAmt");
		sic.add("projectPriceInContract");
		sic.add("projectPriceInContractOri");
		sic.add("prjAllReqAmt");

		// 预付款
		sic.add("prjPayEntry.lstAdvanceAllPaid");
		sic.add("prjPayEntry.lstAdvanceAllReq");
		sic.add("prjPayEntry.advance");
		sic.add("prjPayEntry.locAdvance");
		sic.add("prjPayEntry.advanceAllReq");
		sic.add("prjPayEntry.advanceAllPaid");

		// 甲供
		sic.add("lstAMatlAllPaidAmt");
		sic.add("lstAMatlAllReqAmt");
		sic.add("payPartAMatlAmt");
		sic.add("payPartAMatlOriAmt");
		sic.add("payPartAMatlAllReqAmt");

		// 实付
		sic.add("curPaid");

		// 5
		sic.add("contractPrice");
		sic.add("latestPrice");
		sic.add("payedAmt");
		sic.add("imageSchedule");
		sic.add("completePrjAmt");
		//
		sic.add("contractId");
		sic.add("hasPayoff");
		sic.add("hasClosed");
		sic.add("isPay");
		sic.add("auditTime");
		sic.add("createTime");
		sic.add("fivouchered");
		sic.add("sourceType");

		sic.add("isDifferPlace");
		sic.add("usage");

		// totalSettlePrice
		sic.add("totalSettlePrice");

		// 发票
		sic.add("invoiceNumber");
		sic.add("invoiceAmt");
		sic.add("allInvoiceAmt");
		sic.add("invoiceDate");
		
		sic.add(new SelectorItemInfo("entrys.amount"));
		sic.add(new SelectorItemInfo("entrys.payPartAMatlAmt"));
		sic.add(new SelectorItemInfo("entrys.projectPriceInContract"));
		sic.add(new SelectorItemInfo("entrys.parent.id"));
		sic.add(new SelectorItemInfo("entrys.paymentBill.id"));
		sic.add(new SelectorItemInfo("entrys.advance"));
		sic.add(new SelectorItemInfo("entrys.locAdvance"));

		sic.add(new SelectorItemInfo("orgUnit.name"));
		sic.add(new SelectorItemInfo("orgUnit.number"));
		sic.add(new SelectorItemInfo("orgUnit.displayName"));

		sic.add(new SelectorItemInfo("CU.name"));

		sic.add(new SelectorItemInfo("auditor.name"));
		sic.add(new SelectorItemInfo("creator.name"));

		sic.add(new SelectorItemInfo("useDepartment.number"));
		sic.add(new SelectorItemInfo("useDepartment.name"));

		sic.add(new SelectorItemInfo("curProject.name"));
		sic.add(new SelectorItemInfo("curProject.number"));
		sic.add(new SelectorItemInfo("curProject.displayName"));
		sic.add(new SelectorItemInfo("curProject.fullOrgUnit.name"));
		sic.add(new SelectorItemInfo("curProject.codingNumber"));

		sic.add(new SelectorItemInfo("currency.number"));
		sic.add(new SelectorItemInfo("currency.name"));
		// 付款申请单增加存储本位币币别，以方便预算系统能取到该字段值 by Cassiel_peng 2009-10-5
		sic.add(new SelectorItemInfo("localCurrency.id"));
		sic.add(new SelectorItemInfo("localCurrency.number"));
		sic.add(new SelectorItemInfo("localCurrency.name"));

		sic.add(new SelectorItemInfo("supplier.number"));
		sic.add(new SelectorItemInfo("supplier.name"));

		sic.add(new SelectorItemInfo("realSupplier.number"));
		sic.add(new SelectorItemInfo("realSupplier.name"));

		sic.add(new SelectorItemInfo("settlementType.number"));
		sic.add(new SelectorItemInfo("settlementType.name"));

		sic.add(new SelectorItemInfo("paymentType.number"));
		sic.add(new SelectorItemInfo("paymentType.name"));
		sic.add(new SelectorItemInfo("paymentType.payType.id"));

		sic.add(new SelectorItemInfo("period.number"));
		sic.add(new SelectorItemInfo("period.beginDate"));
		sic.add(new SelectorItemInfo("period.periodNumber"));
		sic.add(new SelectorItemInfo("period.periodYear"));
		sic.add(new SelectorItemInfo("contractBase.number"));
		sic.add(new SelectorItemInfo("contractBase.name"));

		// 计划项目
		sic.add(new SelectorItemInfo("planHasCon.contract.id"));
		sic.add(new SelectorItemInfo("planHasCon.contractName"));
		sic.add(new SelectorItemInfo("planHasCon.head.deptment.name"));
		sic.add(new SelectorItemInfo("planHasCon.head.year"));
		sic.add(new SelectorItemInfo("planHasCon.head.month"));

		sic.add(new SelectorItemInfo("planUnCon.unConName"));
		sic.add(new SelectorItemInfo("planUnCon.parent.deptment.name"));
		sic.add(new SelectorItemInfo("planUnCon.parent.year"));
		sic.add(new SelectorItemInfo("planUnCon.parent.month"));
		
		sic.add("isBgControl");
		sic.add("applier.*");
		sic.add("applierOrgUnit.*");
		sic.add("applierCompany.*");
		sic.add("costedDept.*");
		sic.add("costedCompany.id");
		sic.add("costedCompany.name");
		sic.add("costedCompany.number");
		sic.add("bgEntry.*");
		sic.add("bgEntry.accountView.*");
		sic.add("bgEntry.expenseType.*");
		sic.add("bgEntry.bgItem.*");
		sic.add("isInvoice");
		sic.add("payBillType.*");
		sic.add("payContentType.*");
		sic.add("person.*");
		
		return sic;
	}
	protected IObjectPK _save(Context ctx, IObjectValue model) throws BOSException, EASBizException {
		IObjectPK pk=super._save(ctx, model);
		PaymentBillInfo info=(PaymentBillInfo) model;
		if(info.getSourceType().equals(SourceTypeEnum.FDC)&&info.getFdcPayReqID()!=null){
			String payRequestBillId=info.getFdcPayReqID();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	        builder.appendSql("update T_CON_PayRequestBill set FHasClosed=? where fid=? ");
	        builder.addParam(0);
	        builder.addParam(info.getFdcPayReqID());
	        builder.executeUpdate();

	        if(info.getCurrency()!=null&&info.getActPayAmt()!=null){
	        	SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("bgEntry.*");
				sic.add("bgEntry.expenseType.*");
				sic.add("bgEntry.accountView.*");
				sic.add("isBgControl");
				sic.add("currency.*");
				sic.add("costedDept.*");
				sic.add("originalAmount");
				sic.add("amount");
				sic.add("exchangeRate");
				sic.add("person.*");
				sic.add("completePrjAmt");
				PayRequestBillInfo payReqBill = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(payRequestBillId),sic);
				boolean isUpdateAmount=false;
				BigDecimal updateAmount=FDCHelper.ZERO;
				if(payReqBill.isIsBgControl()){
					info.getEntries().clear();
					BigDecimal subAmount=FDCHelper.ZERO;
//					PaymentBillCollection isPay=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection("select * from where fdcPayReqID='"+payReqBill.getId()+"' and id!='"+info.getId()+"' and billstatus=15");
//    				if(isPay.size()==0
//    						&&payReqBill.getCompletePrjAmt()!=null&&payReqBill.getCompletePrjAmt().compareTo(FDCHelper.ZERO)!=0&&payReqBill.getCompletePrjAmt().compareTo(payReqBill.getOriginalAmount())!=0){
//    					subAmount=payReqBill.getCompletePrjAmt().subtract(payReqBill.getOriginalAmount());
//    					isUpdateAmount=true;
//    				}
	    			if(payReqBill.getCurrency().getId().toString().equals(info.getCurrency().getId().toString())
	    					&&payReqBill.getOriginalAmount().compareTo(info.getActPayAmt())!=0){
	    				BigDecimal total=FDCHelper.ZERO;
	    				BigDecimal rate=info.getActPayAmt().divide(FDCHelper.toBigDecimal(payReqBill.getOriginalAmount()),6,BigDecimal.ROUND_HALF_UP);	
	    				for(int i=0;i<payReqBill.getBgEntry().size();i++){
	    					BigDecimal amount=FDCHelper.ZERO;
	    					BigDecimal actAmount=FDCHelper.ZERO;
	    					if(i==payReqBill.getBgEntry().size()-1){
	    						amount=info.getActPayAmt().add(subAmount).subtract(total);
	    						actAmount=info.getActPayAmt().subtract(total);
	    						updateAmount=amount;
	    					}else{
	    						amount=payReqBill.getBgEntry().get(i).getRequestAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
	    						actAmount=payReqBill.getBgEntry().get(i).getRequestAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
	    						total=total.add(amount);
	    					}
	    					PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
	    					entry.setAmount(amount);
	    					entry.setLocalAmt(amount);
	    		            entry.setActualAmt(actAmount);
	    		            entry.setActualLocAmt(actAmount);
	    		            entry.setCurrency(payReqBill.getCurrency());
	    		            entry.setExpenseType(payReqBill.getBgEntry().get(i).getExpenseType());
	    		            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(i).getId().toString());
	    		            entry.setCostCenter(payReqBill.getCostedDept());
	    		            if(payReqBill.getPerson()==null){
	    		            	entry.setOppAccount(payReqBill.getBgEntry().get(i).getAccountView());
	    		            }
	    		            info.getEntries().add(entry);
	    				}
	    			}else{
	    				BigDecimal total=FDCHelper.ZERO;
	    				for(int i=0;i<payReqBill.getBgEntry().size();i++){
	    					BigDecimal amount=FDCHelper.ZERO;
	    					BigDecimal actAmount=FDCHelper.ZERO;
	    					if(i==payReqBill.getBgEntry().size()-1){
	    						amount=info.getActPayAmt().add(subAmount).subtract(total);
	    						actAmount=info.getLocalAmt().subtract(total);
	    						updateAmount=amount;
	    					}else{
	    						amount=payReqBill.getBgEntry().get(i).getRequestAmount();
	    						actAmount=payReqBill.getBgEntry().get(i).getRequestAmount();
	    						total=total.add(amount);
	    					}
	    					PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
	    					entry.setAmount(amount);
	    					entry.setLocalAmt(amount);
	    		            entry.setActualAmt(actAmount);
	    		            entry.setActualLocAmt(actAmount);
	    		            entry.setCurrency(payReqBill.getCurrency());
	    		            entry.setExpenseType(payReqBill.getBgEntry().get(i).getExpenseType());
	    		            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(i).getId().toString());
	    		            entry.setCostCenter(payReqBill.getCostedDept());
	    		            if(payReqBill.getPerson()==null){
	    		            	entry.setOppAccount(payReqBill.getBgEntry().get(i).getAccountView());
	    		            }
	    		            info.getEntries().add(entry);
	    				}
	    			}
	    			PaymentBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()), info);
	    			if(isUpdateAmount){
	    				builder = new FDCSQLBuilder(ctx);
	                    builder.appendSql("update T_CAS_PaymentBillentry set famount=?,fLocalAmount=? where fpaymentBillid=? ");
	                    builder.addParam(updateAmount);
	                    builder.addParam(updateAmount);
	                    builder.addParam(info.getId().toString());
	                    builder.executeUpdate();
	    			}
	    		}
			}
	        builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select count(*) payTime from t_cas_paymentbill where fFdcPayReqID=? and fsourceBillId is null");
	        builder.addParam(payRequestBillId);
	        IRowSet rs=builder.executeQuery();
	        int  payTime=0;
	        Date payDate=null;
	    	try {
	    		while(rs.next()){
	    			payTime=rs.getInt("payTime");
	    		}
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
	    	builder.clear();
	    	
	        builder.appendSql("update T_CON_PayRequestBill set fpayTime=?,fiscreatePay=?  where fid=? ");
	        builder.addParam(payTime);
	        if(payTime>0){
	        	builder.addParam(1);
	        }else{
	        	builder.addParam(0);
	        }
			builder.addParam(payRequestBillId);
	        builder.executeUpdate();
		}
		return pk; 
	}
	protected IObjectPK _submit(Context ctx, IObjectValue model)throws BOSException, EASBizException{
		IObjectPK pk=super._submit(ctx, model);
		PaymentBillInfo info=(PaymentBillInfo) model;
		if(info.getSourceType().equals(SourceTypeEnum.FDC)&&info.getFdcPayReqID()!=null){
			String payRequestBillId=info.getFdcPayReqID();
			FDCSQLBuilder builder = new FDCSQLBuilder(ctx);
	        builder.appendSql("update T_CON_PayRequestBill set FHasClosed=? where fid=? ");
	        builder.addParam(0);
	        builder.addParam(payRequestBillId);
	        builder.executeUpdate();
	        
	        if(info.getCurrency()!=null&&info.getActPayAmt()!=null){
	        	SelectorItemCollection sic = new SelectorItemCollection();
				sic.add("bgEntry.*");
				sic.add("bgEntry.expenseType.*");
				sic.add("bgEntry.accountView.*");
				sic.add("isBgControl");
				sic.add("currency.*");
				sic.add("costedDept.*");
				sic.add("originalAmount");
				sic.add("amount");
				sic.add("exchangeRate");
				sic.add("person.*");
				sic.add("completePrjAmt");
				PayRequestBillInfo payReqBill = PayRequestBillFactory.getLocalInstance(ctx).getPayRequestBillInfo(new ObjectUuidPK(payRequestBillId),sic);
				boolean isUpdateAmount=false;
				BigDecimal updateAmount=FDCHelper.ZERO;
				if(payReqBill.isIsBgControl()){
					info.getEntries().clear();
					BigDecimal subAmount=FDCHelper.ZERO;
//					PaymentBillCollection isPay=PaymentBillFactory.getLocalInstance(ctx).getPaymentBillCollection("select * from where fdcPayReqID='"+payReqBill.getId()+"' and id!='"+info.getId()+"' and billstatus=15");
//    				if(isPay.size()==0
//    						&&payReqBill.getCompletePrjAmt()!=null&&payReqBill.getCompletePrjAmt().compareTo(FDCHelper.ZERO)!=0&&payReqBill.getCompletePrjAmt().compareTo(payReqBill.getOriginalAmount())!=0){
//    					subAmount=payReqBill.getCompletePrjAmt().subtract(payReqBill.getOriginalAmount());
//    					isUpdateAmount=true;
//    				}
	    			if(payReqBill.getCurrency().getId().toString().equals(info.getCurrency().getId().toString())
	    					&&payReqBill.getOriginalAmount().compareTo(info.getActPayAmt())!=0){
	    				BigDecimal total=FDCHelper.ZERO;
	    				BigDecimal rate=info.getActPayAmt().divide(FDCHelper.toBigDecimal(payReqBill.getOriginalAmount()),6,BigDecimal.ROUND_HALF_UP);	
	    				for(int i=0;i<payReqBill.getBgEntry().size();i++){
	    					BigDecimal amount=FDCHelper.ZERO;
	    					BigDecimal actAmount=FDCHelper.ZERO;
	    					if(i==payReqBill.getBgEntry().size()-1){
	    						amount=info.getActPayAmt().add(subAmount).subtract(total);
	    						actAmount=info.getActPayAmt().subtract(total);
	    						updateAmount=amount;
	    					}else{
	    						amount=payReqBill.getBgEntry().get(i).getRequestAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
	    						actAmount=payReqBill.getBgEntry().get(i).getRequestAmount().multiply(rate).setScale(2,BigDecimal.ROUND_HALF_UP);
	    						total=total.add(amount);
	    					}
	    					PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
	    					entry.setAmount(amount);
	    					entry.setLocalAmt(amount);
	    		            entry.setActualAmt(actAmount);
	    		            entry.setActualLocAmt(actAmount);
	    		            entry.setCurrency(payReqBill.getCurrency());
	    		            entry.setExpenseType(payReqBill.getBgEntry().get(i).getExpenseType());
	    		            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(i).getId().toString());
	    		            entry.setCostCenter(payReqBill.getCostedDept());
	    		            if(payReqBill.getPerson()==null){
	    		            	entry.setOppAccount(payReqBill.getBgEntry().get(i).getAccountView());
	    		            }
	    		            info.getEntries().add(entry);
	    				}
	    			}else{
	    				BigDecimal total=FDCHelper.ZERO;
	    				for(int i=0;i<payReqBill.getBgEntry().size();i++){
	    					BigDecimal amount=FDCHelper.ZERO;
	    					BigDecimal actAmount=FDCHelper.ZERO;
	    					if(i==payReqBill.getBgEntry().size()-1){
	    						amount=info.getActPayAmt().add(subAmount).subtract(total);
	    						actAmount=info.getLocalAmt().subtract(total);
	    						updateAmount=amount;
	    					}else{
	    						amount=payReqBill.getBgEntry().get(i).getRequestAmount();
	    						actAmount=payReqBill.getBgEntry().get(i).getRequestAmount();
	    						total=total.add(amount);
	    					}
	    					PaymentBillEntryInfo entry=new PaymentBillEntryInfo();
	    					entry.setAmount(amount);
	    					entry.setLocalAmt(amount);
	    		            entry.setActualAmt(actAmount);
	    		            entry.setActualLocAmt(actAmount);
	    		            entry.setCurrency(payReqBill.getCurrency());
	    		            entry.setExpenseType(payReqBill.getBgEntry().get(i).getExpenseType());
	    		            entry.setSourceBillEntryId(payReqBill.getBgEntry().get(i).getId().toString());
	    		            entry.setCostCenter(payReqBill.getCostedDept());
	    		            if(payReqBill.getPerson()==null){
	    		            	entry.setOppAccount(payReqBill.getBgEntry().get(i).getAccountView());
	    		            }
	    		            info.getEntries().add(entry);
	    				}
	    			}
	    			PaymentBillFactory.getLocalInstance(ctx).update(new ObjectUuidPK(info.getId()),info);
	    			if(isUpdateAmount){
	    				builder = new FDCSQLBuilder(ctx);
	                    builder.appendSql("update T_CAS_PaymentBillentry set famount=?,fLocalAmount=? where fpaymentBillid=? and fsourceBillId is null");
	                    builder.addParam(updateAmount);
	                    builder.addParam(updateAmount);
	                    builder.addParam(info.getId().toString());
	                    builder.executeUpdate();
	    			}
				}
	        }
			builder = new FDCSQLBuilder(ctx);
			builder.appendSql("select count(*) payTime from t_cas_paymentbill where fFdcPayReqID=?");
	        builder.addParam(payRequestBillId);
	        IRowSet rs=builder.executeQuery();
	        int  payTime=0;
	        Date payDate=null;
	    	try {
	    		while(rs.next()){
	    			payTime=rs.getInt("payTime");
	    		}
	    	} catch (SQLException e) {
				e.printStackTrace();
			}
	    	builder.clear();
	    	
	        builder.appendSql("update T_CON_PayRequestBill set fpayTime=?,fiscreatePay=?  where fid=? ");
	        builder.addParam(payTime);
	        if(payTime>0){
	        	builder.addParam(1);
	        }else{
	        	builder.addParam(0);
	        }
	        builder.addParam(payRequestBillId);
	        builder.executeUpdate();
		}
		return pk;
	}
}
