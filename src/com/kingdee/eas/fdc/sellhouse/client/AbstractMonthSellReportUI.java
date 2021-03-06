/**
 * output package name
 */
package com.kingdee.eas.fdc.sellhouse.client;

import org.apache.log4j.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.event.*;
import javax.swing.KeyStroke;

import com.kingdee.bos.ctrl.swing.*;
import com.kingdee.bos.ctrl.kdf.table.*;
import com.kingdee.bos.ctrl.kdf.data.event.*;
import com.kingdee.bos.dao.*;
import com.kingdee.bos.dao.query.*;
import com.kingdee.bos.metadata.*;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.ui.face.*;
import com.kingdee.bos.ui.util.ResourceBundleHelper;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.bos.service.ServiceContext;
import com.kingdee.jdbc.rowset.IRowSet;
import com.kingdee.util.enums.EnumUtils;
import com.kingdee.bos.ui.face.UIRuleUtil;
import com.kingdee.bos.ctrl.swing.event.*;
import com.kingdee.bos.ctrl.kdf.table.event.*;
import com.kingdee.bos.ctrl.extendcontrols.*;
import com.kingdee.bos.ctrl.kdf.util.render.*;
import com.kingdee.bos.ui.face.IItemAction;
import com.kingdee.eas.framework.batchHandler.RequestContext;
import com.kingdee.bos.ui.util.IUIActionPostman;
import com.kingdee.bos.appframework.client.servicebinding.ActionProxyFactory;
import com.kingdee.bos.appframework.uistatemanage.ActionStateConst;
import com.kingdee.bos.appframework.validator.ValidateHelper;
import com.kingdee.bos.appframework.uip.UINavigator;


/**
 * output class name
 */
public abstract class AbstractMonthSellReportUI extends com.kingdee.eas.framework.client.ListUI
{
    private static final Logger logger = CoreUIObject.getLogger(AbstractMonthSellReportUI.class);
    protected com.kingdee.bos.ctrl.swing.KDSplitPane kDSplitPane1;
    protected com.kingdee.bos.ctrl.swing.KDTreeView kDTreeView1;
    protected com.kingdee.bos.ctrl.swing.KDTree kDTree1;
    /**
     * output class constructor
     */
    public AbstractMonthSellReportUI() throws Exception
    {
        super();
        this.defaultObjectName = "mainQuery";
        jbInit();
        
        initUIP();
    }

    /**
     * output jbInit method
     */
    private void jbInit() throws Exception
    {
        this.resHelper = new ResourceBundleHelper(AbstractMonthSellReportUI.class.getName());
        this.setUITitle(resHelper.getString("this.title"));
        mainQueryPK = new MetaDataPK("com.kingdee.eas.fdc.sellhouse.app", "RoomQuery");
        this.kDSplitPane1 = new com.kingdee.bos.ctrl.swing.KDSplitPane();
        this.kDTreeView1 = new com.kingdee.bos.ctrl.swing.KDTreeView();
        this.kDTree1 = new com.kingdee.bos.ctrl.swing.KDTree();
        this.kDSplitPane1.setName("kDSplitPane1");
        this.kDTreeView1.setName("kDTreeView1");
        this.kDTree1.setName("kDTree1");
        // CoreUI
		String tblMainStrXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?> <DocRoot xmlns:c=\"http://www.kingdee.com/Common\" xmlns:f=\"http://www.kingdee.com/Form\" xmlns:t=\"http://www.kingdee.com/Table\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.kingdee.com/KDF KDFSchema.xsd\" version=\"0.0\"><Styles><c:Style id=\"sCol5\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol6\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol7\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol8\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol9\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol10\"><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol11\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol12\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol13\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol14\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol15\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol16\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol17\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol18\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol19\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol20\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol21\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol22\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol23\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol24\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol25\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol26\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol27\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol28\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol29\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol30\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol31\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol32\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol33\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol34\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol35\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol36\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol37\"><c:NumberFormat>%r-[ ]0.2n</c:NumberFormat><c:Alignment horizontal=\"right\" /></c:Style><c:Style id=\"sCol38\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol39\"><c:Protection hidden=\"true\" /></c:Style><c:Style id=\"sCol40\"><c:Protection hidden=\"true\" /></c:Style></Styles><Table id=\"KDTable\"><t:Sheet name=\"sheet1\"><t:Table t:selectMode=\"15\" t:mergeMode=\"0\" t:dataRequestMode=\"0\" t:pageRowCount=\"100\"><t:ColumnGroup><t:Column t:key=\"companyName\" t:width=\"100\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"true\" t:required=\"false\" t:index=\"0\" /><t:Column t:key=\"sellProName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"1\" /><t:Column t:key=\"subarea.name\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"2\" /><t:Column t:key=\"productType\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"3\" /><t:Column t:key=\"buildingName\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"4\" /><t:Column t:key=\"totalRoomcount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"5\" t:styleID=\"sCol5\" /><t:Column t:key=\"buildingArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"6\" t:styleID=\"sCol6\" /><t:Column t:key=\"actureBuildArea\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"7\" t:styleID=\"sCol7\" /><t:Column t:key=\"monthRoomCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"8\" t:styleID=\"sCol8\" /><t:Column t:key=\"yearRoomCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"9\" t:styleID=\"sCol9\" /><t:Column t:key=\"allRoomCount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"10\" t:styleID=\"sCol10\" /><t:Column t:key=\"monthAreaSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"11\" t:styleID=\"sCol11\" /><t:Column t:key=\"yearAreaSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"12\" t:styleID=\"sCol12\" /><t:Column t:key=\"allAreaSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"13\" t:styleID=\"sCol13\" /><t:Column t:key=\"monthPriceSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"14\" t:styleID=\"sCol14\" /><t:Column t:key=\"yearPriceSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"15\" t:styleID=\"sCol15\" /><t:Column t:key=\"allPriceSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"16\" t:styleID=\"sCol16\" /><t:Column t:key=\"monthResvSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"17\" t:styleID=\"sCol17\" /><t:Column t:key=\"yearResvSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"18\" t:styleID=\"sCol18\" /><t:Column t:key=\"allResvSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"19\" t:styleID=\"sCol19\" /><t:Column t:key=\"monthPreconcertMoney\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"20\" t:styleID=\"sCol20\" /><t:Column t:key=\"yearPreconcertMoney\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"21\" t:styleID=\"sCol21\" /><t:Column t:key=\"allPreconcertMoney\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"22\" t:styleID=\"sCol22\" /><t:Column t:key=\"monthArrearage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"23\" t:styleID=\"sCol23\" /><t:Column t:key=\"yearArrearage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"24\" t:styleID=\"sCol24\" /><t:Column t:key=\"allArrearage\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"25\" t:styleID=\"sCol25\" /><t:Column t:key=\"monthLoanSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"26\" t:styleID=\"sCol26\" /><t:Column t:key=\"yearLoanSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"27\" t:styleID=\"sCol27\" /><t:Column t:key=\"allLoanSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"28\" t:styleID=\"sCol28\" /><t:Column t:key=\"monthAccFundAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"29\" t:styleID=\"sCol29\" /><t:Column t:key=\"yearAccFundAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"30\" t:styleID=\"sCol30\" /><t:Column t:key=\"allAccFundAmount\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"31\" t:styleID=\"sCol31\" /><t:Column t:key=\"monthCompensateSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"32\" t:styleID=\"sCol32\" /><t:Column t:key=\"yearCompensateSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"33\" t:styleID=\"sCol33\" /><t:Column t:key=\"allCompensateSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"34\" t:styleID=\"sCol34\" /><t:Column t:key=\"monthCompensateInSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"35\" t:styleID=\"sCol35\" /><t:Column t:key=\"yearCompensateInSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"36\" t:styleID=\"sCol36\" /><t:Column t:key=\"allCompensateInSum\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"37\" t:styleID=\"sCol37\" /><t:Column t:key=\"OID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"38\" t:styleID=\"sCol38\" /><t:Column t:key=\"SID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"39\" t:styleID=\"sCol39\" /><t:Column t:key=\"PBID\" t:width=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\" t:moveable=\"true\" t:group=\"false\" t:required=\"false\" t:index=\"40\" t:styleID=\"sCol40\" /></t:ColumnGroup><t:Head><t:Row t:name=\"header1\" t:height=\"19\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{companyName}</t:Cell><t:Cell>$Resource{sellProName}</t:Cell><t:Cell>$Resource{subarea.name}</t:Cell><t:Cell>$Resource{productType}</t:Cell><t:Cell>$Resource{buildingName}</t:Cell><t:Cell>$Resource{totalRoomcount}</t:Cell><t:Cell>$Resource{buildingArea}</t:Cell><t:Cell>$Resource{actureBuildArea}</t:Cell><t:Cell>$Resource{monthRoomCount}</t:Cell><t:Cell>$Resource{yearRoomCount}</t:Cell><t:Cell>$Resource{allRoomCount}</t:Cell><t:Cell>$Resource{monthAreaSum}</t:Cell><t:Cell>$Resource{yearAreaSum}</t:Cell><t:Cell>$Resource{allAreaSum}</t:Cell><t:Cell>$Resource{monthPriceSum}</t:Cell><t:Cell>$Resource{yearPriceSum}</t:Cell><t:Cell>$Resource{allPriceSum}</t:Cell><t:Cell>$Resource{monthResvSum}</t:Cell><t:Cell>$Resource{yearResvSum}</t:Cell><t:Cell>$Resource{allResvSum}</t:Cell><t:Cell>$Resource{monthPreconcertMoney}</t:Cell><t:Cell>$Resource{yearPreconcertMoney}</t:Cell><t:Cell>$Resource{allPreconcertMoney}</t:Cell><t:Cell>$Resource{monthArrearage}</t:Cell><t:Cell>$Resource{yearArrearage}</t:Cell><t:Cell>$Resource{allArrearage}</t:Cell><t:Cell>$Resource{monthLoanSum}</t:Cell><t:Cell>$Resource{yearLoanSum}</t:Cell><t:Cell>$Resource{allLoanSum}</t:Cell><t:Cell>$Resource{monthAccFundAmount}</t:Cell><t:Cell>$Resource{yearAccFundAmount}</t:Cell><t:Cell>$Resource{allAccFundAmount}</t:Cell><t:Cell>$Resource{monthCompensateSum}</t:Cell><t:Cell>$Resource{yearCompensateSum}</t:Cell><t:Cell>$Resource{allCompensateSum}</t:Cell><t:Cell>$Resource{monthCompensateInSum}</t:Cell><t:Cell>$Resource{yearCompensateInSum}</t:Cell><t:Cell>$Resource{allCompensateInSum}</t:Cell><t:Cell>$Resource{OID}</t:Cell><t:Cell>$Resource{SID}</t:Cell><t:Cell>$Resource{PBID}</t:Cell></t:Row><t:Row t:name=\"header2\" t:height=\"-1\" t:mergeable=\"true\" t:resizeable=\"true\"><t:Cell>$Resource{companyName_Row2}</t:Cell><t:Cell>$Resource{sellProName_Row2}</t:Cell><t:Cell>$Resource{subarea.name_Row2}</t:Cell><t:Cell>$Resource{productType_Row2}</t:Cell><t:Cell>$Resource{buildingName_Row2}</t:Cell><t:Cell>$Resource{totalRoomcount_Row2}</t:Cell><t:Cell>$Resource{buildingArea_Row2}</t:Cell><t:Cell>$Resource{actureBuildArea_Row2}</t:Cell><t:Cell>$Resource{monthRoomCount_Row2}</t:Cell><t:Cell>$Resource{yearRoomCount_Row2}</t:Cell><t:Cell>$Resource{allRoomCount_Row2}</t:Cell><t:Cell>$Resource{monthAreaSum_Row2}</t:Cell><t:Cell>$Resource{yearAreaSum_Row2}</t:Cell><t:Cell>$Resource{allAreaSum_Row2}</t:Cell><t:Cell>$Resource{monthPriceSum_Row2}</t:Cell><t:Cell>$Resource{yearPriceSum_Row2}</t:Cell><t:Cell>$Resource{allPriceSum_Row2}</t:Cell><t:Cell>$Resource{monthResvSum_Row2}</t:Cell><t:Cell>$Resource{yearResvSum_Row2}</t:Cell><t:Cell>$Resource{allResvSum_Row2}</t:Cell><t:Cell>$Resource{monthPreconcertMoney_Row2}</t:Cell><t:Cell>$Resource{yearPreconcertMoney_Row2}</t:Cell><t:Cell>$Resource{allPreconcertMoney_Row2}</t:Cell><t:Cell>$Resource{monthArrearage_Row2}</t:Cell><t:Cell>$Resource{yearArrearage_Row2}</t:Cell><t:Cell>$Resource{allArrearage_Row2}</t:Cell><t:Cell>$Resource{monthLoanSum_Row2}</t:Cell><t:Cell>$Resource{yearLoanSum_Row2}</t:Cell><t:Cell>$Resource{allLoanSum_Row2}</t:Cell><t:Cell>$Resource{monthAccFundAmount_Row2}</t:Cell><t:Cell>$Resource{yearAccFundAmount_Row2}</t:Cell><t:Cell>$Resource{allAccFundAmount_Row2}</t:Cell><t:Cell>$Resource{monthCompensateSum_Row2}</t:Cell><t:Cell>$Resource{yearCompensateSum_Row2}</t:Cell><t:Cell>$Resource{allCompensateSum_Row2}</t:Cell><t:Cell>$Resource{monthCompensateInSum_Row2}</t:Cell><t:Cell>$Resource{yearCompensateInSum_Row2}</t:Cell><t:Cell>$Resource{allCompensateInSum_Row2}</t:Cell><t:Cell>$Resource{OID_Row2}</t:Cell><t:Cell>$Resource{SID_Row2}</t:Cell><t:Cell>$Resource{PBID_Row2}</t:Cell></t:Row></t:Head></t:Table><t:SheetOptions><t:MergeBlocks><t:Head><t:Block t:top=\"0\" t:left=\"0\" t:bottom=\"1\" t:right=\"0\" /><t:Block t:top=\"0\" t:left=\"1\" t:bottom=\"1\" t:right=\"1\" /><t:Block t:top=\"0\" t:left=\"2\" t:bottom=\"1\" t:right=\"2\" /><t:Block t:top=\"0\" t:left=\"3\" t:bottom=\"1\" t:right=\"3\" /><t:Block t:top=\"0\" t:left=\"4\" t:bottom=\"1\" t:right=\"4\" /><t:Block t:top=\"0\" t:left=\"5\" t:bottom=\"1\" t:right=\"5\" /><t:Block t:top=\"0\" t:left=\"6\" t:bottom=\"1\" t:right=\"6\" /><t:Block t:top=\"0\" t:left=\"8\" t:bottom=\"0\" t:right=\"10\" /><t:Block t:top=\"0\" t:left=\"11\" t:bottom=\"0\" t:right=\"13\" /><t:Block t:top=\"0\" t:left=\"14\" t:bottom=\"0\" t:right=\"16\" /><t:Block t:top=\"0\" t:left=\"17\" t:bottom=\"0\" t:right=\"19\" /><t:Block t:top=\"0\" t:left=\"20\" t:bottom=\"0\" t:right=\"22\" /><t:Block t:top=\"0\" t:left=\"23\" t:bottom=\"0\" t:right=\"25\" /><t:Block t:top=\"0\" t:left=\"26\" t:bottom=\"0\" t:right=\"28\" /><t:Block t:top=\"0\" t:left=\"7\" t:bottom=\"1\" t:right=\"7\" /><t:Block t:top=\"0\" t:left=\"29\" t:bottom=\"0\" t:right=\"31\" /><t:Block t:top=\"0\" t:left=\"32\" t:bottom=\"0\" t:right=\"34\" /><t:Block t:top=\"0\" t:left=\"35\" t:bottom=\"0\" t:right=\"37\" /></t:Head></t:MergeBlocks></t:SheetOptions></t:Sheet></Table></DocRoot> ";
		
        this.tblMain.setFormatXml(resHelper.translateString("tblMain",tblMainStrXML));
                this.tblMain.putBindContents("mainQuery",new String[] {"","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""});


        // kDSplitPane1		
        this.kDSplitPane1.setDividerLocation(250);
        // kDTreeView1
        // kDTree1
        this.kDTree1.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent e) {
                try {
                    kDTree1_valueChanged(e);
                } catch (Exception exc) {
                    handUIException(exc);
                } finally {
                }
            }
        });
		//Register control's property binding
		registerBindings();
		registerUIState();


    }

	public com.kingdee.bos.ctrl.swing.KDToolBar[] getUIMultiToolBar(){
		java.util.List list = new java.util.ArrayList();
		com.kingdee.bos.ctrl.swing.KDToolBar[] bars = super.getUIMultiToolBar();
		if (bars != null) {
			list.addAll(java.util.Arrays.asList(bars));
		}
		return (com.kingdee.bos.ctrl.swing.KDToolBar[])list.toArray(new com.kingdee.bos.ctrl.swing.KDToolBar[list.size()]);
	}




    /**
     * output initUIContentLayout method
     */
    public void initUIContentLayout()
    {
        this.setBounds(new Rectangle(10, 10, 1013, 629));
        this.setLayout(new KDLayout());
        this.putClientProperty("OriginalBounds", new Rectangle(10, 10, 1013, 629));
        kDSplitPane1.setBounds(new Rectangle(10, 5, 996, 611));
        this.add(kDSplitPane1, new KDLayout.Constraints(10, 5, 996, 611, KDLayout.Constraints.ANCHOR_TOP | KDLayout.Constraints.ANCHOR_BOTTOM | KDLayout.Constraints.ANCHOR_LEFT | KDLayout.Constraints.ANCHOR_RIGHT));
        //kDSplitPane1
        kDSplitPane1.add(tblMain, "right");
        kDSplitPane1.add(kDTreeView1, "left");
        //kDTreeView1
        kDTreeView1.setTree(kDTree1);

    }


    /**
     * output initUIMenuBarLayout method
     */
    public void initUIMenuBarLayout()
    {
        this.menuBar.add(menuFile);
        this.menuBar.add(menuEdit);
        this.menuBar.add(MenuService);
        this.menuBar.add(menuView);
        this.menuBar.add(menuBiz);
        this.menuBar.add(menuTool);
        this.menuBar.add(menuTools);
        this.menuBar.add(menuHelp);
        //menuFile
        menuFile.add(menuItemAddNew);
        menuFile.add(menuItemImportData);
        menuFile.add(menuItemExportData);
        menuFile.add(separatorFile1);
        menuFile.add(MenuItemAttachment);
        menuFile.add(kDSeparator1);
        menuFile.add(menuItemPageSetup);
        menuFile.add(menuItemPrint);
        menuFile.add(menuItemPrintPreview);
        menuFile.add(kDSeparator2);
        menuFile.add(menuItemExitCurrent);
        //menuEdit
        menuEdit.add(menuItemEdit);
        menuEdit.add(menuItemRemove);
        //MenuService
        MenuService.add(MenuItemKnowStore);
        MenuService.add(MenuItemAnwser);
        MenuService.add(SepratorService);
        MenuService.add(MenuItemRemoteAssist);
        //menuView
        menuView.add(menuItemView);
        menuView.add(menuItemLocate);
        menuView.add(separatorView1);
        menuView.add(menuItemQuery);
        menuView.add(menuItemQueryScheme);
        menuView.add(menuItemRefresh);
        //menuBiz
        menuBiz.add(menuItemCancelCancel);
        menuBiz.add(menuItemCancel);
        //menuTool
        menuTool.add(menuItemSendMessage);
        menuTool.add(menuItemCalculator);
        //menuTools
        menuTools.add(menuMail);
        menuTools.add(menuItemStartWorkFlow);
        menuTools.add(menuItemPublishReport);
        //menuMail
        menuMail.add(menuItemToHTML);
        menuMail.add(menuItemCopyScreen);
        menuMail.add(menuItemToExcel);
        //menuHelp
        menuHelp.add(menuItemHelp);
        menuHelp.add(kDSeparator12);
        menuHelp.add(menuItemRegPro);
        menuHelp.add(menuItemPersonalSite);
        menuHelp.add(helpseparatorDiv);
        menuHelp.add(menuitemProductval);
        menuHelp.add(kDSeparatorProduct);
        menuHelp.add(menuItemAbout);

    }

    /**
     * output initUIToolBarLayout method
     */
    public void initUIToolBarLayout()
    {
        this.toolBar.add(btnAddNew);
        this.toolBar.add(btnView);
        this.toolBar.add(btnEdit);
        this.toolBar.add(btnRemove);
        this.toolBar.add(btnRefresh);
        this.toolBar.add(btnQuery);
        this.toolBar.add(btnLocate);
        this.toolBar.add(btnAttachment);
        this.toolBar.add(separatorFW1);
        this.toolBar.add(btnPageSetup);
        this.toolBar.add(btnPrint);
        this.toolBar.add(btnPrintPreview);
        this.toolBar.add(separatorFW2);
        this.toolBar.add(btnCancelCancel);
        this.toolBar.add(btnCancel);
        this.toolBar.add(btnQueryScheme);


    }

	//Regiester control's property binding.
	private void registerBindings(){		
	}
	//Regiester UI State
	private void registerUIState(){		
	}
	public String getUIHandlerClassName() {
	    return "com.kingdee.eas.fdc.sellhouse.app.MonthSellReportUIHandler";
	}
	public IUIActionPostman prepareInit() {
		IUIActionPostman clientHanlder = super.prepareInit();
		if (clientHanlder != null) {
			RequestContext request = new RequestContext();
    		request.setClassName(getUIHandlerClassName());
			clientHanlder.setRequestContext(request);
		}
		return clientHanlder;
    }
	
	public boolean isPrepareInit() {
    	return false;
    }
    protected void initUIP() {
        super.initUIP();
    }



	
	

    /**
     * output setDataObject method
     */
    public void setDataObject(IObjectValue dataObject)
    {
        IObjectValue ov = dataObject;        	    	
        super.setDataObject(ov);
    }

    /**
     * output loadFields method
     */
    public void loadFields()
    {
        dataBinder.loadFields();
    }
    /**
     * output storeFields method
     */
    public void storeFields()
    {
		dataBinder.storeFields();
    }

	/**
	 * ????????????
	 */
	protected void registerValidator() {
    	getValidateHelper().setCustomValidator( getValidator() );		
	}



    /**
     * output setOprtState method
     */
    public void setOprtState(String oprtType)
    {
        super.setOprtState(oprtType);
    }

    /**
     * output kDTree1_valueChanged method
     */
    protected void kDTree1_valueChanged(javax.swing.event.TreeSelectionEvent e) throws Exception
    {
    }

    /**
     * output getSelectors method
     */
    public SelectorItemCollection getSelectors()
    {
        SelectorItemCollection sic = new SelectorItemCollection();
        return sic;
    }        

    /**
     * output getMetaDataPK method
     */
    public IMetaDataPK getMetaDataPK()
    {
        return new MetaDataPK("com.kingdee.eas.fdc.sellhouse.client", "MonthSellReportUI");
    }




}