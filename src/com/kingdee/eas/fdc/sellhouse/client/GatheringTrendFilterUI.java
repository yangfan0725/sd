/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.SpinnerNumberModel;

import org.apache.log4j.Logger;

import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.ui.face.ItemAction;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.eas.base.commonquery.client.CustomerParams;
import com.kingdee.eas.fdc.basedata.FDCCustomerParams;
import com.kingdee.eas.framework.*;
import com.kingdee.eas.framework.client.ListUI;
import com.kingdee.eas.util.client.MsgBox;
import com.kingdee.util.DateTimeUtils;

/**
 * output class name
 */
public class GatheringTrendFilterUI extends AbstractGatheringTrendFilterUI
{
    private static final Logger logger = CoreUIObject.getLogger(GatheringTrendFilterUI.class);
    
    private static final String MONTH_TO = "monthTo";

	private static final String MONTH_FROM = "monthFrom";

	private static final String YEAR_TO = "yearTo";

	private static final String YEAR_FROM = "yearFrom";

	private static final String DATE_TO = "dateTo";

	private static final String DATE_FROM = "dateFrom";

	private static final String IS_SHOW_ALL = "isShowAll";

	private static final String DATE_TYPE = "dateType";

	private static final String ROOM_SELL_TYPE_PREPURCHASE = "roomSellTypePrepurchase";

	private static final String ROOM_SELL_TYPE_PURCHASE = "roomSellTypePurchase";

	private static final String ROOM_SELL_TYPE_SIGN = "roomSellTypeSign";

	protected ItemAction actionListOnLoad;

	private boolean isLoaded;

	protected ListUI listUI;
    
    /**
     * output class constructor
     */
    public GatheringTrendFilterUI() throws Exception
    {
        super();
    }
    public GatheringTrendFilterUI(ListUI listUI,ItemAction actionListOnLoad) throws Exception
	{
		super();
		this.listUI = listUI;
		this.actionListOnLoad = actionListOnLoad;
	}


    /**
     * output storeFields method
     */
    public void storeFields()
    {
        super.storeFields();
    }

    public void clear()
	{
		this.pkDateFrom.setValue(new Date());
		this.pkDateTo.setValue(new Date());
		this.radioByDay.setSelected(true);
		initControlByDateType();
	}

	public FilterInfo getFilterInfo()
	{
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
	    FilterInfo filter = new FilterInfo();
		/*filter.getFilterItems().add(new FilterItemInfo("id", null, CompareType.NOTEQUALS));
		if (para.isNotNull(IS_SHOW_ALL) && !para.getBoolean(IS_SHOW_ALL)&& para.isNotNull(DATE_TYPE)) {
		if (getBeginQueryDate(para) != null) {
		filter.getFilterItems().add(
		new FilterItemInfo("date", getBeginQueryDate(para),
		 CompareType.GREATER_EQUALS));
		 }
		 if (getEndQueryDate(para) != null) {
		 filter.getFilterItems().add(
		 new FilterItemInfo("date", FDCHelper
		 .getNextDay(getEndQueryDate(para)),
		 CompareType.LESS));
		
		 }
		 }
		if (this.checkPrePurchase.isSelected()
		 || this.checkPurchase.isSelected()
		 || this.checkSign.isSelected()) {
		
		 if (this.checkPrePurchase.isSelected()) {
		 filter.getFilterItems().add(
		 new FilterItemInfo("sellState", "PrePurchase",
		 CompareType.EQUALS));
		 }
		 if (this.checkPurchase.isSelected()) {
		 filter.getFilterItems().add(
		 new FilterItemInfo("sellState", "Purchase",
		 CompareType.EQUALS));
		 }
		 if (this.checkSign.isSelected()) {
		 filter.getFilterItems().add(
		 new FilterItemInfo("sellState", "Sign",
		 CompareType.EQUALS));
		 }
		
		 }*/
	    filter.getFilterItems().add(new FilterItemInfo("sellState","1"));
		return filter;
	}

	public Date getBeginQueryDate(FDCCustomerParams para)
	{
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0)
		{
			date = para.getDate(DATE_FROM);
		} else if (dateType == 1)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, para.getInt(MONTH_FROM) - 1);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 2)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, (para.getInt(MONTH_FROM) - 1) * 3);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		} else if (dateType == 3)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_FROM));
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 1);
			date = cal.getTime();
		}
		return this.radioByDay.isSelected()?DateTimeUtils.truncateDate(date):truncateDate(date);
	}

	public Date getEndQueryDate(FDCCustomerParams para)
	{
		Date date = null;
		int dateType = para.getInt(DATE_TYPE);
		if (dateType == 0)
		{
			date = para.getDate(DATE_TO);
		} else if (dateType == 1)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO));
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 2)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO));
			cal.set(Calendar.MONTH, para.getInt(MONTH_TO) * 3);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		} else if (dateType == 3)
		{
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.YEAR, para.getInt(YEAR_TO) + 1);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			date = cal.getTime();
		}
		return this.radioByDay.isSelected()?DateTimeUtils.truncateDate(date):truncateDate(date);
	}
	
	 public static Date truncateDate(Date dt)
	    {
	        if (dt==null)
	            return null;
	        
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dt);
	        cal.set(cal.get(Calendar.YEAR), 
	                cal.get(Calendar.MONTH), 
	                1,
	                0, 
	                0, 
	                0);        
	        return new Date((cal.getTimeInMillis() / 1000) * 1000);
	    }
	public CustomerParams getCustomerParams()
	{
		FDCCustomerParams param = new FDCCustomerParams();

		if (this.radioByDay.isSelected())
		{
			param.add(DATE_FROM, (Date) this.pkDateFrom.getValue());
			param.add(DATE_TO, (Date) this.pkDateTo.getValue());
			param.add(DATE_TYPE, 0);
		} else if (this.radioByMonth.isSelected())
		{
			param.add(YEAR_FROM, ((Integer) this.spiYearFrom.getValue())
					.intValue());
			param
					.add(YEAR_TO, ((Integer) this.spiYearTo.getValue())
							.intValue());
			param.add(MONTH_FROM, ((Integer) this.spiMonthFrom.getValue())
					.intValue());
			param.add(MONTH_TO, ((Integer) this.spiMonthTo.getValue())
					.intValue());
			param.add(DATE_TYPE, 1);
		} 
		return param.getCustomerParams();
	}

	private void initControlByDateType()
	{
		SpinnerNumberModel yearMo1 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearFrom.setModel(yearMo1);
		SpinnerNumberModel yearMo2 = new SpinnerNumberModel(Calendar
				.getInstance().get(Calendar.YEAR), 1900, 2100, 1);
		spiYearTo.setModel(yearMo2);
		if (this.radioByDay.isSelected())
		{
			this.contYearFrom.setVisible(false);
			this.contYearTo.setVisible(false);
			this.lblMonthFrom.setVisible(false);
			this.lblMonthTo.setVisible(false);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(false);
			this.spiMonthTo.setVisible(false);
			this.contDateFrom.setVisible(true);
			this.contDateTo.setVisible(true);

		} else if (this.radioByMonth.isSelected())
		{
			this.contYearFrom.setVisible(true);
			this.contYearTo.setVisible(true);
			this.lblMonthFrom.setVisible(true);
			this.lblMonthTo.setVisible(true);
			this.lblQuarterFrom.setVisible(false);
			this.lblQuarterTo.setVisible(false);
			this.spiMonthFrom.setVisible(true);
			this.spiMonthTo.setVisible(true);
			this.contDateFrom.setVisible(false);
			this.contDateTo.setVisible(false);
			SpinnerNumberModel monthMo1 = new SpinnerNumberModel(Calendar
					.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthFrom.setModel(monthMo1);
			SpinnerNumberModel monthMo2 = new SpinnerNumberModel(Calendar
					.getInstance().get(Calendar.MONTH) + 1, 1, 12, 1);
			spiMonthTo.setModel(monthMo2);
		} 
	}

	public void onLoad() throws Exception {
		super.onLoad();
		if (!isLoaded) {
			this.clear();
		}
		isLoaded = true;
	}

	protected void radioByDay_actionPerformed(ActionEvent e) throws Exception
	{
		super.radioByDay_actionPerformed(e);
		this.initControlByDateType();
	}

	protected void radioByMonth_actionPerformed(ActionEvent e) throws Exception
	{
		super.radioByMonth_actionPerformed(e);
		this.initControlByDateType();
	}



	public void setCustomerParams(CustomerParams cp)
	{
		if (cp == null)
			return;

		FDCCustomerParams para = new FDCCustomerParams(cp);
		if (para.getInt(DATE_TYPE) == 0)
		{
			this.radioByDay.setSelected(true);
		} else if (para.getInt(DATE_TYPE) == 1)
		{
			this.radioByMonth.setSelected(true);
		} 
		this.initControlByDateType();

		if (para.getInt(DATE_TYPE) == 0)
		{
			this.pkDateFrom.setValue(para.getDate(DATE_FROM));
			this.pkDateTo.setValue(para.getDate(DATE_TO));
		} else if (para.getInt(DATE_TYPE) == 1)
		{
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 2)
		{
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
			this.spiMonthFrom.setValue(new Integer(para.getInt(MONTH_FROM)));
			this.spiMonthTo.setValue(new Integer(para.getInt(MONTH_TO)));
		} else if (para.getInt(DATE_TYPE) == 3)
		{
			this.spiYearFrom.setValue(new Integer(para.getInt(YEAR_FROM)));
			this.spiYearTo.setValue(new Integer(para.getInt(YEAR_TO)));
		}
		super.setCustomerParams(para.getCustomerParams());
	}

	public boolean verify()
	{
		FDCCustomerParams para = new FDCCustomerParams(getCustomerParams());
		Date beginQueryDate = this.getBeginQueryDate(para);
		Date endQueryDate = this.getEndQueryDate(para);
		if(beginQueryDate == null)
		{
			MsgBox.showInfo("??????????????????");
			return false;
		}
		if(endQueryDate == null)
		{
			MsgBox.showInfo("??????????????????");
			return false;
		}
		if (beginQueryDate != null && endQueryDate != null)
		{
			if (endQueryDate.before(beginQueryDate))
			{
				MsgBox.showWarning(this, "????????????????????????!");
				return false;
			}
			else if(this.isExceed(endQueryDate,beginQueryDate)&&this.radioByDay.isSelected())
			{
				MsgBox.showInfo("????????????????90????");
				return false;
			}else if(this.isMonthExceed(endQueryDate, beginQueryDate) &&this.radioByMonth.isSelected()){
				MsgBox.showInfo("????????????????90????");
				return false;
			}
		}
		return true;
	}
	
    private Date addDays(Date date,int day)
    {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.add(Calendar.DATE,day);
    	return calendar.getTime();
    }
    private boolean isExceed(Date end,Date begin)
    {
    	boolean debug = false;
    	Date  temp = this.addDays(begin,90);
    	if(temp.before(end))
    	{
    		debug =true;
    	}
    	return debug;
    }
    
    private Date addMonths(Date date,int day)
    {
    	Calendar calendar = new GregorianCalendar();
    	calendar.setTime(date);
    	calendar.add(Calendar.MONTH,day);
    	return calendar.getTime();
    }
    private boolean isMonthExceed(Date end,Date begin)
    {
    	boolean debug = false;
    	Date  temp = this.addMonths(begin,90);
    	if(temp.before(end))
    	{
    		debug =true;
    	}
    	return debug;
    }


	boolean hasCurrency = false;

	public boolean hasCurrency()
	{
		return hasCurrency;
	}


}