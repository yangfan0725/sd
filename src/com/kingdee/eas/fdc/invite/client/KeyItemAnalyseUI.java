/**
 * output package name
 */
package com.kingdee.eas.fdc.invite.client;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.common.variant.SyntaxErrorException;
import com.kingdee.bos.ctrl.kdf.table.ICell;
import com.kingdee.bos.ctrl.kdf.table.IColumn;
import com.kingdee.bos.ctrl.kdf.table.IRow;
import com.kingdee.bos.ctrl.kdf.table.KDTStyleConstants;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.util.style.StyleAttributes;
import com.kingdee.bos.ctrl.kdf.util.style.Styles;
import com.kingdee.bos.ctrl.kdf.util.style.Styles.HorizontalAlignment;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SorterItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.common.EASBizException;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.invite.DescriptionEnum;
import com.kingdee.eas.fdc.invite.HeadColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingColumnCollection;
import com.kingdee.eas.fdc.invite.NewListingColumnFactory;
import com.kingdee.eas.fdc.invite.NewListingColumnInfo;
import com.kingdee.eas.fdc.invite.NewListingEntryCollection;
import com.kingdee.eas.fdc.invite.NewListingEntryFactory;
import com.kingdee.eas.fdc.invite.NewListingEntryInfo;
import com.kingdee.eas.fdc.invite.NewListingFactory;
import com.kingdee.eas.fdc.invite.NewListingInfo;
import com.kingdee.eas.fdc.invite.NewListingPageCollection;
import com.kingdee.eas.fdc.invite.NewListingPageInfo;
import com.kingdee.eas.fdc.invite.NewListingValueCollection;
import com.kingdee.eas.fdc.invite.NewListingValueFactory;
import com.kingdee.eas.fdc.invite.NewListingValueInfo;
import com.kingdee.eas.fdc.invite.NewQuotingPriceCollection;
import com.kingdee.eas.fdc.invite.NewQuotingPriceFactory;
import com.kingdee.eas.fdc.invite.NewQuotingPriceInfo;
import com.kingdee.eas.fdc.invite.QuotingPriceTypeEnum;
import com.kingdee.eas.util.client.MsgBox;

/**
 * ??????????????
 * @author ??????
 */
public class KeyItemAnalyseUI extends AbstractKeyItemAnalyseUI {

	private static final long serialVersionUID = -8634462040215179456L;

	private static final Logger logger = CoreUIObject.getLogger(KeyItemAnalyseUI.class);

	private static BigDecimal zero = FDCHelper.ZERO;

//	private KDTabbedPane tablePane = kDTabbedPane1;

	private List tables = new ArrayList(10);

	private NewListingInfo listingInfo;

	private NewQuotingPriceCollection priceContentCollection;// ????

	// private Map sheetsMap = new HashMap();

	private Map projectAndUnit = new HashMap(100);// ??????????????????????

	private Map priceWidth = new HashMap(9); // ????????????????????

	private BigDecimal refTotalPrice = zero;// ????????

	// ????????????????????
	private Color splitColor = KDTStyleConstants.HEAD_BACKGROUND;// FDCTableHelper.KDTABLE_SUBTOTAL_BG_COLOR;

	private static StyleAttributes numberSA;
	static {
		numberSA = Styles.getEmptySA();
		numberSA.setHorizontalAlign(HorizontalAlignment.RIGHT);
		numberSA.setNumberFormat("#,#0.00;-#,#0.00");
	}

	/**
	 * output class constructor
	 */
	public KeyItemAnalyseUI() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		

		btnPrintPreview.getAction().setEnabled(true);
		btnPrint.getAction().setEnabled(true);
		btnPrintPreview.setEnabled(true);
		btnPrint.setEnabled(true);
		
		init();

		for(int i=0,count=this.tabbedPane.getTabCount();i<count;i++){
			((KDTable)tabbedPane.getComponentAt(i)).setColumnMoveable(false);
		}

	}

	private void init() throws Exception {
		tables.clear();
		this.tabbedPane.removeAll();
		projectAndUnit.clear();
		priceWidth.clear();
		initPrivateData();

		NewListingPageCollection pages = listingInfo.getPages();
		for (int i = 0; i < pages.size(); i++) {
			NewListingPageInfo pageInfo = pages.get(i);
			if (pageInfo.getHeadType() != null) {
				KDTable table = new KDTable();
				tables.add(table);
				table.setName(pageInfo.getPageHead().getName());
				table.setColumnMoveable(false);
				table.addHeadRow();
				priceWidth.put(pageInfo, new Integer(initTableColumn(table, pageInfo)));
				fillKeyItem(table, pageInfo);
				tabbedPane.add(table);
				// sheetsMap.put(table.getName(), table);
				table.getStyleAttributes().setLocked(true);
			}

		}
		
		InviteHelper.setAotuHeight(this.tabbedPane);
		
	}

	/**
	 * ????????????????
	 *
	 * @throws EASBizException
	 * @throws BOSException
	 */

	public void initPrivateData() throws EASBizException, BOSException {
		String inviteListingId = (String) getUIContext().get("inviteListingId");
		SelectorItemCollection sels = new SelectorItemCollection();
		sels.add("*");
		sels.add("pages.*");
		// sels.add("pages.headType.*");
		sels.add("pages.pageHead.name");
		// sels.add("pages.columns.*");
		// sels.add("pages.columns.headColumn.*");
		listingInfo = NewListingFactory.getRemoteInstance().getNewListingInfo(new ObjectUuidPK(inviteListingId), sels);

		for (int i = 0; i < listingInfo.getPages().size(); i++) {
			EntityViewInfo view = new EntityViewInfo();
			FilterInfo filter = new FilterInfo();
//			SelectorItemCollection sic = new SelectorItemCollection();
			NewListingPageInfo sheet = listingInfo.getPages().get(i);
//			view = new EntityViewInfo();
//			filter = new FilterInfo();
			view.setFilter(filter);
			filter.getFilterItems().add(new FilterItemInfo("page.id", sheet.getId().toString()));
			SelectorItemCollection sic = view.getSelector();
			sic.add("*");
			sic.add("headColumn.*");
			sic.add("headColumn.parent.*");
			view.getSorter().add(new SorterItemInfo("seq"));
			NewListingColumnCollection columns = NewListingColumnFactory.getRemoteInstance().getNewListingColumnCollection(view);
			sheet.getColumns().clear();
			sheet.getColumns().addCollection(columns);
		}

		// ????????????????????????
		EntityViewInfo view = new EntityViewInfo();
		FilterInfo filter = new FilterInfo();
		view.setFilter(filter);
		view.getSelector().add("*");
		view.getSelector().add("supplier.*");
		filter.getFilterItems().add(new FilterItemInfo("listing", listingInfo.getId().toString()));
		filter.getFilterItems().add(new FilterItemInfo("hasEffected", Boolean.TRUE));
		// filter.getFilterItems().add(new
		// FilterItemInfo("status",QuotingPriceStatusEnum.IMPORTEDPRICE_VALUE));
		priceContentCollection = NewQuotingPriceFactory.getRemoteInstance().getNewQuotingPriceCollection(view);

		if (this.priceContentCollection.size() == 0) {
			MsgBox.showInfo("????????????!");
			this.abort();
		}

		refTotalPrice = listingInfo.getTotalAmount() != null ? listingInfo.getTotalAmount() : zero;
	}

	/**
	 * ????????????
	 *
	 * @param table
	 * @param page
	 * @return ??????????????
	 */
	private int initTableColumn(final KDTable table, NewListingPageInfo page) {
		int colCount = 0;
		NewListingColumnCollection columns = page.getColumns();
		int size = columns.size();
		if (size > 0) {
			IColumn col = table.addColumn();
			// col.setWidth(200);
			if (col.getKey() == null) {
				col.setKey("??????");
				table.getHeadRow(0).getCell(0).setValue("??????");
			}

			// ????????
			for (int j = 0; j < size; j++) {
				NewListingColumnInfo columnInfo = columns.get(j);
				HeadColumnInfo headColumn = columnInfo.getHeadColumn();
				if (headColumn.getProperty().equals(DescriptionEnum.TotalPrice) || headColumn.getProperty().equals(DescriptionEnum.TotalPriceSum)) {
					IColumn column = table.addColumn();
					if (column.getKey() == null) {
						column.setKey(headColumn.getName());
						table.getHeadRow(0).getCell(colCount + 1).setValue(headColumn.getName());
					}
					// column.setWidth(120);
					colCount++;
				}
			}

			col = table.addColumn();
			if (col.getKey() == null) {
				col.setKey("??????????????");
				table.getHeadRow(0).getCell(colCount + 1).setValue("??????????????");
			}
			// col.setWidth(120);

			col = table.addColumn();
			if (col.getKey() == null) {
				col.setKey("????????????");
				table.getHeadRow(0).getCell(colCount + 2).setValue("????????????");
			}
			// col.setWidth(120);

			col = table.addColumn();
			if (col.getKey() == null) {
				col.setKey("??????????");
				table.getHeadRow(0).getCell(colCount + 3).setValue("??????????");
			}
			// col.setWidth(120);
		}
		if (colCount < 2) {
			table.addColumn();
		}

//		StyleAttributes sa = Styles.getEmptySA();
//		sa.setFontColor(KDTStyleConstants.);
//		table.getHeadRow(0).setStyleAttributes(sa);

		return colCount;
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param table
	 * @param pageInfo
	 * @throws BOSException
	 * @throws SyntaxErrorException
	 */
	private void fillKeyItem(KDTable table, NewListingPageInfo pageInfo) throws BOSException, SyntaxErrorException {

		// ????????????????????
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("head", pageInfo.getId().toString());
		filterInfo.appendFilterItem("isKey", Boolean.TRUE);
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add("*");
		// viewInfo.getSelector().add("item.id");
		// viewInfo.getSelector().add("item.number");
		viewInfo.getSorter().add(new SorterItemInfo("seq"));
		NewListingEntryCollection entryCollection = NewListingEntryFactory.getRemoteInstance().getNewListingEntryCollection(viewInfo);

		// ??????????????????????????????????
		initProjectAndUnit(pageInfo, entryCollection);

		// ????????????????????????????
		for (int i = 0; i < entryCollection.size(); i++) {
			NewListingEntryInfo entryInfo = entryCollection.get(i);

			fillKeyItemByEntry(pageInfo, entryInfo, table);
			IRow row = table.addRow();
			int rowIndex = row.getRowIndex();

			// ????????????????
			int col = 0;

			ICell cell = null;
			cell = row.getCell(col);
			cell.setValue("????????????");

			Map map = (Map) projectAndUnit.get(entryInfo);

			// ??????????
			for (int j = 1; j < table.getColumnCount(); j++) {
				IColumn column = table.getColumn(j);
				Object columnKey = column.getKey();
				if (columnKey != null && map.containsKey(columnKey)) {
					cell = table.getCell(rowIndex, j);
					cell.setValue(map.get(columnKey));
					cell.setStyleAttributes(numberSA);
				}
			}

			// ????????????
			cell = table.getCell(rowIndex - 3, 1);
			table.getRow(rowIndex - 3).getStyleAttributes().setBackground(splitColor);
			;
			cell.setValue(entryInfo.getItemName());
			cell.getStyleAttributes().setWrapText(true);

			// ????????
			table.getCell(rowIndex - 3, 3).setValue(map.get("????"));

			// ??????????
			cell = table.getCell(rowIndex - 3, 5);
			cell.setValue(map.get("??????"));
			cell.getStyleAttributes().setNumberFormat("#,#0.00");
			table.getMergeManager().mergeBlock(rowIndex - 3, 5, rowIndex - 3, table.getColumnCount() - 1);

			// ????????????????
			col += ((Integer) priceWidth.get(pageInfo)).intValue();
			col++;
			cell = row.getCell(col);
			cell.setValue(map.get("????????"));
			cell.setStyleAttributes(numberSA);

			// ????????????
			col++;
			cell = table.getCell(rowIndex, col);
			cell.setValue(refTotalPrice);
			cell.setStyleAttributes(numberSA);

			// ????????
			col++;
			cell = table.getCell(rowIndex, col);
			if (refTotalPrice.intValue() != 0) {
				if ((BigDecimal) map.get("????????") != null) {
					cell.setValue(((BigDecimal) map.get("????????")).divide(
							refTotalPrice, BigDecimal.ROUND_HALF_UP, 4));
				}
				cell.getStyleAttributes().setNumberFormat("#,#0.00%;-#,#0.00%");
				cell.getStyleAttributes().setHorizontalAlign(
						HorizontalAlignment.RIGHT);
			} else {
				cell.setValue("??????????");
			}
			if (cell.getColumnIndex() < table.getColumnCount() - 1) {
				table.getMergeManager().mergeBlock(cell.getRowIndex(), cell.getColumnIndex(), cell.getRowIndex(), table.getColumnCount() - 1);
			}

			// ???????????????? ????

			// ????????????????
			for (int m = 0; m < priceContentCollection.size(); m++) {
				fillKeyItemDataByBidder(entryInfo, priceContentCollection.get(m), table, ((Integer) priceWidth.get(pageInfo)).intValue());
			}
			// ??????????
			row = table.addRow();
			table.getMergeManager().mergeBlock(row.getRowIndex(), 0, row.getRowIndex(), table.getColumnCount() - 1);
		}

	}

	/**
	 * ????????????????????????????????
	 *
	 * @param entryCollection
	 * @param pageInfo
	 * @throws BOSException
	 */
	private void initProjectAndUnit(NewListingPageInfo pageInfo, NewListingEntryCollection entryCollection) throws BOSException {

		projectAndUnit.clear();

		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("entry.head.id", pageInfo.getId().toString());
		filterInfo.appendFilterItem("entry.isKey", Boolean.TRUE);
		filterInfo.appendFilterItem("quotingPrice", null);// ??????
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add("*");
		viewInfo.getSelector().add("entry.id");
		viewInfo.getSelector().add("column.*");
		viewInfo.getSelector().add("column.headColumn.*");
		NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(viewInfo);
		for (int i = 0; i < entryCollection.size(); i++) {
			Map map = new HashMap(10);
			NewListingEntryInfo entry = entryCollection.get(i);
			for (int j = 0; j < valueCollection.size(); j++) {
				NewListingValueInfo value = valueCollection.get(j);
				if (value.getEntry().getId().toString().equals(entry.getId().toString())) {
					HeadColumnInfo headColumn = value.getColumn().getHeadColumn();
					if ("????".equals(headColumn.getName())) {
						map.put("????", value.getText());
					} else if (DescriptionEnum.ProjectAmtSum.equals(headColumn.getProperty())) {
						map.put("??????", value.getAmount());
					} else if (DescriptionEnum.AmountSum.equals(headColumn.getProperty())) {
						map.put("????????", value.getAmount());
					} else {
						map.put(headColumn.getName(), value.getAmount());
					}
				}
			}
			projectAndUnit.put(entry, map);
		}

	}

	/**
	 * ??????????????????????????????????????????
	 *
	 * @param entryInfo
	 * @param table
	 * @param rowIndex
	 * @param root
	 * @return int ??????????????
	 */
	private int fillKeyItemByEntry(NewListingPageInfo pageInfo, NewListingEntryInfo entryInfo, KDTable table) {
		IRow row = table.addRow();
		ICell cell = null;

		cell = row.getCell(0);
		cell.setValue("????????");

		cell = row.getCell(1);
		cell.setValue(entryInfo.getName());

		cell = row.getCell(2);
		cell.setValue("????");

		cell = row.getCell(3);

		cell = row.getCell(4);
		cell.setValue("??????");

		cell = row.getCell(5);

		// ????
		row = table.addRow();
		row.getCell(0).setValue("??????");
		int rowIndex = row.getRowIndex();

		row.getCell(1).setValue("??????????????");
		StyleAttributes sa = Styles.getEmptySA();
		sa.setHorizontalAlign(HorizontalAlignment.CENTER);
		row.getCell(1).setStyleAttributes(sa);

		// ????
		row = table.addRow();
		rowIndex = row.getRowIndex();

		// ??????????
		table.getMergeManager().mergeBlock(rowIndex - 1, 0, rowIndex, 0);

		int col = 1;// ??????????????
		// ????????
		NewListingColumnCollection columns = pageInfo.getColumns();
		for (int i = 0; i < columns.size(); i++) {
			NewListingColumnInfo columnInfo = columns.get(i);
			HeadColumnInfo headColumn = columnInfo.getHeadColumn();
			if (headColumn.getProperty().equals(DescriptionEnum.TotalPrice) || headColumn.getProperty().equals(DescriptionEnum.TotalPriceSum)) {
				row.getCell(col++).setValue(headColumn.getName());
			}
		}
		// ????????????
		table.getMergeManager().mergeBlock(rowIndex - 1, 1, rowIndex - 1, col - 1);

		// col += ((Integer) priceWidth.get(pageInfo)).intValue() - 1;
		cell = row.getCell(col++);
		cell.setValue("??????????????");
		// ??????
		table.getMergeManager().mergeBlock(rowIndex - 1, col - 1, rowIndex, col - 1);

		cell = row.getCell(col++);
		cell.setValue("????????????");
		// ????????????
		table.getMergeManager().mergeBlock(rowIndex - 1, col - 1, rowIndex, col - 1);
		// cell = table.getCell(row, col++);
		// while ((cell = table.getCell(row, col)) == null)
		// table.addColumn();
		cell = row.getCell(col++);
		cell.setValue("????????????");
		table.getMergeManager().mergeBlock(rowIndex - 1, col - 1, rowIndex, table.getColumnCount() - 1);
		// StyleAttributes sa = Styles.getEmptySA();
		// sa.setHorizontalAlign(HorizontalAlignment.CENTER);
		// cell.setStyleAttributes(sa);

		return col - 1;
	}

	/**
	 * ????????????????????????????????????????????
	 *
	 * @param sheetInfo
	 * @param priceContentInfo
	 * @param table
	 * @param rowIndex
	 * @throws BOSException
	 * @throws SyntaxErrorException
	 */
	public void fillKeyItemDataByBidder(NewListingEntryInfo entryInfo, NewQuotingPriceInfo priceContentInfo, KDTable table, int feeColCount)
			throws BOSException, SyntaxErrorException {
		EntityViewInfo viewInfo = new EntityViewInfo();
		FilterInfo filterInfo = new FilterInfo();
		filterInfo.appendFilterItem("entry", entryInfo.getId().toString());
		filterInfo.appendFilterItem("quotingPrice", priceContentInfo.getId().toString());
		filterInfo.appendFilterItem("type", QuotingPriceTypeEnum.modify);
		viewInfo.setFilter(filterInfo);
		viewInfo.getSelector().add("*");
		viewInfo.getSelector().add("entry.*");
		viewInfo.getSelector().add("column.*");
		viewInfo.getSelector().add("column.headColumn.*");
		viewInfo.getSorter().add(new SorterItemInfo("seq"));
		NewListingValueCollection valueCollection = NewListingValueFactory.getRemoteInstance().getNewListingValueCollection(viewInfo);
		HashMap map = new HashMap();

		// ????????
		BigDecimal amount = zero;
		for (int i = 0; i < valueCollection.size(); i++) {
			NewListingValueInfo valueInfo = valueCollection.get(i);
			NewListingColumnInfo columnInfo = valueInfo.getColumn();
			// if (valueInfo.getAmount() == null)
			// continue;
			if (columnInfo.getHeadColumn().getProperty().equals(DescriptionEnum.AmountSum)) {
				amount = valueInfo.getAmount();
			} else if (columnInfo.getHeadColumn().getProperty().equals(DescriptionEnum.ProjectAmtSum)) {
			} else {
				map.put(columnInfo.getHeadColumn().getName(), valueInfo.getAmount());
			}
		}

		int col = 0;
		ICell cell = null;
		IRow row = table.addRow();
		int rowIndex = row.getRowIndex();

		cell = row.getCell(col++);
		// ??????
		cell.setValue(priceContentInfo.getSupplier().getName());

		int maxCol = table.getColumnCount() - 1;
		// ??????????
		for (int i = 1; i <= maxCol; i++) {
			IColumn column = table.getColumn(i);
			Object columnKey = column.getKey();
			if (columnKey != null && map.containsKey(columnKey)) {
				cell = row.getCell(i);
				cell.setValue(map.get(columnKey));
				cell.setStyleAttributes(numberSA);
			}
		}

		col += feeColCount;

		// ????????
		cell = row.getCell(col++);
		cell.setValue(amount);
		cell.setStyleAttributes(numberSA);

		// ??????
		cell = table.getCell(rowIndex, col++);
		BigDecimal totalPrice = priceContentInfo.getTotoalPrice();
		cell.setValue(totalPrice);
		cell.setStyleAttributes(numberSA);

		// ????????
		cell = table.getCell(rowIndex, col++);
		if (totalPrice.signum() > 0) {
			cell.setValue(amount.divide(totalPrice, BigDecimal.ROUND_HALF_UP, 4));
			cell.getStyleAttributes().setNumberFormat("#,#0.00%;-#,#0.00%");
			cell.getStyleAttributes().setHorizontalAlign(HorizontalAlignment.RIGHT);
		} else {
			cell.setValue("??????????");
		}
		if (cell.getColumnIndex() < table.getColumnCount() - 1) {
			table.getMergeManager().mergeBlock(cell.getRowIndex(), cell.getColumnIndex(), cell.getRowIndex(), table.getColumnCount() - 1);
		}
	}

	public void actionPrint_actionPerformed(ActionEvent e) {
		KDTable table = (KDTable) tabbedPane.getSelectedComponent();
		InviteHelper.setPrintTitle(table, "??????????????", 0);
		table.getPrintManager().print();
	}

	public void actionPrintPreview_actionPerformed(ActionEvent e) throws Exception {
		KDTable table = (KDTable) tabbedPane.getSelectedComponent();
		InviteHelper.setPrintTitle(table, "??????????????", 0);
		table.getPrintManager().printPreview();
	}

	public void actionExportExcel_actionPerformed(ActionEvent e) throws Exception {
		InviteHelper.exportToExcel(tables, false);

	}

	public void actionRefresh_actionPerformed(ActionEvent e) throws Exception {
		init();
	}
}
