/*jadclipse*/package com.kingdee.eas.fdc.basedata.client;
import com.kingdee.bos.BOSException;
import com.kingdee.bos.ctrl.kdf.table.KDTSelectManager;
import com.kingdee.bos.ctrl.kdf.table.KDTable;
import com.kingdee.bos.ctrl.kdf.table.event.KDTMouseEvent;
import com.kingdee.bos.ctrl.kdf.table.event.KDTSelectEvent;
import com.kingdee.bos.ctrl.swing.KDTree;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.dao.ormapping.ObjectUuidPK;
import com.kingdee.bos.metadata.entity.*;
import com.kingdee.bos.metadata.query.util.CompareType;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.bos.util.BOSUuid;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.framework.TreeBaseCollection;
import com.kingdee.eas.framework.TreeBaseInfo;
import com.kingdee.eas.framework.client.tree.DefaultLNTreeNodeCtrl;
import com.kingdee.eas.framework.client.tree.ILNTreeNodeCtrl;
import com.kingdee.eas.util.SysUtil;
import com.kingdee.eas.util.client.MsgBox;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Map;
import javax.swing.event.TreeSelectionEvent;
import org.apache.log4j.Logger;
public class ContractTypeF7UI extends AbstractContractTypeF7UI
{
            public ContractTypeF7UI()
                throws Exception
            {















/*  46*/        isCancel = false;
            }
            protected ILNTreeNodeCtrl getLNTreeNodeCtrl()
                throws Exception
            {





/*  56*/        if(getUIContext().get("ALL") != null && ((Boolean)getUIContext().get("ALL")).booleanValue())
                {/*  57*/            getUIContext().put("IGNORE_DATAPERMISSION_CHECK", Boolean.TRUE);
/*  58*/            return new NPContractTypeLNTreeNodeCtrl(getTreeInterface());
                } else
                {/*  60*/            return new DefaultLNTreeNodeCtrl(getTreeInterface());
                }
            }
            protected void btnCancel2_actionPerformed(ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /*  64*/        disposeUIWindow();
/* <-MISALIGNED-> */ /*  65*/        setCancel(true);
            }
            protected void btnConfirm_actionPerformed(ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /*  69*/        confirm();
            }
            private void confirm()
                throws Exception
            {
/* <-MISALIGNED-> */ /*  73*/        checkSelected();
/* <-MISALIGNED-> */ /*  74*/        getData();
/* <-MISALIGNED-> */ /*  75*/        setCancel(false);
            }
            public ContractTypeInfo getData()
                throws Exception
            {
/* <-MISALIGNED-> */ /*  79*/        String id = getSelectedKeyValue();
/* <-MISALIGNED-> */ /*  80*/        ContractTypeInfo contractTypeInfo = null;
/* <-MISALIGNED-> */ /*  81*/        if(getUIContext().get("ALL") != null && ((Boolean)getUIContext().get("ALL")).booleanValue())
/* <-MISALIGNED-> */ /*  82*/            contractTypeInfo = (ContractTypeInfo)ContractTypeFactory.getRemoteInstance().getNoPValue(new ObjectUuidPK(id));
/* <-MISALIGNED-> */ /*  84*/        else
/* <-MISALIGNED-> */ /*  84*/            contractTypeInfo = ContractTypeFactory.getRemoteInstance().getContractTypeInfo(new ObjectUuidPK(id));
/* <-MISALIGNED-> */ /*  86*/        if(!contractTypeInfo.isIsLeaf())
                {
/* <-MISALIGNED-> */ /*  87*/            MsgBox.showWarning(this, FDCClientUtils.getRes("selectLeaf"));
/* <-MISALIGNED-> */ /*  88*/            SysUtil.abort();
                }
/* <-MISALIGNED-> */ /*  90*/        disposeUIWindow();
/* <-MISALIGNED-> */ /*  92*/        return contractTypeInfo;
            }
            public boolean isCancel()
            {
/* <-MISALIGNED-> */ /*  96*/        return isCancel;
            }
            protected void menuItemImportData_actionPerformed(ActionEvent e)
                throws Exception
            {/* 104*/        super.menuItemImportData_actionPerformed(e);
            }
            public void setCancel(boolean isCancel)
            {
/* 108*/        this.isCancel = isCancel;
            }
            protected void tblMain_tableClicked(KDTMouseEvent e)
                throws Exception
            {



/* 116*/        if(e.getClickCount() == 2)
                {

/* 119*/            if(e.getType() == 0)
/* 120*/                return;


/* 123*/            confirm();
                }
            }
            protected void tblMain_tableSelectChanged(KDTSelectEvent kdtselectevent)
                throws Exception
            {
            }
            protected void treeMain_valueChanged(TreeSelectionEvent e)
                throws Exception
            {






/* 139*/        DefaultKingdeeTreeNode node = (DefaultKingdeeTreeNode)treeMain.getLastSelectedPathComponent();
/* 140*/        if(node == null)
/* 141*/            return;

/* 143*/        if(node.getUserObject() instanceof ContractTypeInfo)
                {/* 144*/            ContractTypeInfo cti = (ContractTypeInfo)node.getUserObject();
/* 145*/            HashSet lnUps = new HashSet();

/* 147*/            try
                    {
/* <-MISALIGNED-> */ /* 147*/                if(getUIContext().get("ALL") != null && ((Boolean)getUIContext().get("ALL")).booleanValue())
                        {
/* <-MISALIGNED-> */ /* 148*/                    TreeBaseCollection ctc = ContractTypeFactory.getRemoteInstance().getAllChildren((TreeBaseInfo)node.getUserObject());
/* <-MISALIGNED-> */ /* 149*/                    lnUps.add(cti.getId().toString());
/* <-MISALIGNED-> */ /* 150*/                    for(int i = 0; i < ctc.size(); i++)
/* <-MISALIGNED-> */ /* 151*/                        lnUps.add(ctc.get(i).getId().toString());
                        } else
                        {
/* <-MISALIGNED-> */ /* 154*/                    ContractTypeCollection ctc = ContractTypeFactory.getRemoteInstance().getContractTypeCollection((new StringBuilder("select id where longNumber like '")).append(cti.getLongNumber()).append("!%'").toString());
/* <-MISALIGNED-> */ /* 156*/                    lnUps.add(cti.getId().toString());
/* <-MISALIGNED-> */ /* 157*/                    for(int i = 0; i < ctc.size(); i++)
/* <-MISALIGNED-> */ /* 158*/                        lnUps.add(ctc.get(i).getId().toString());
                        }
                    }
/* <-MISALIGNED-> */ /* 161*/            catch(BOSException e1)
                    {
/* <-MISALIGNED-> */ /* 162*/                handUIException(e1);
                    }
/* <-MISALIGNED-> */ /* 164*/            FilterInfo filterInfo = new FilterInfo();
/* <-MISALIGNED-> */ /* 165*/            if(lnUps.size() != 0)
                    {
/* <-MISALIGNED-> */ /* 166*/                filterInfo.getFilterItems().add(new FilterItemInfo("id", lnUps, CompareType.INCLUDE));
/* <-MISALIGNED-> */ /* 168*/                mainQuery.setFilter(filterInfo);
                    }
                } else
                {
/* <-MISALIGNED-> */ /* 173*/            mainQuery.setFilter(new FilterInfo());
                }/* 176*/        FilterInfo filter = (FilterInfo)getUIContext().get("F7Filter");
/* 177*/        if(filter != null)
/* 178*/            mainQuery.getFilter().mergeFilter(filter, "and");

/* 180*/        tblMain.removeRows();
            }
            public boolean destroyWindow()
            {
/* 184*/        setCancel(true);
/* 185*/        return super.destroyWindow();
            }
            public void onLoad()
                throws Exception
            {/* 189*/        super.onLoad();

/* 191*/        getMainTable().getSelectManager().setSelectMode(2);
            }
            protected void beforeExcutQuery(EntityViewInfo ev)
            {


/* 197*/        FilterInfo filter = getEnableFilter();

/* 199*/        getUIContext().put("F7Filter", filter);

/* 201*/        try
                {
/* <-MISALIGNED-> */ /* 201*/            ev.getFilter().mergeFilter(filter, "and");
                }
/* <-MISALIGNED-> */ /* 202*/        catch(BOSException e)
                {
/* <-MISALIGNED-> */ /* 203*/            handUIException(e);
                }
            }
            private FilterInfo getEnableFilter()
            {
/* <-MISALIGNED-> */ /* 209*/        FilterInfo filter = new FilterInfo();
/* <-MISALIGNED-> */ /* 210*/        filter.getFilterItems().add(new FilterItemInfo("isEnabled", Boolean.TRUE));
if(getUIContext().get("isReceive") != null){
	if(((Boolean)getUIContext().get("isReceive")).booleanValue()){
		filter.getFilterItems().add(
				new FilterItemInfo("isReceive", Boolean.TRUE));
	}else{
		filter.getFilterItems().add(
				new FilterItemInfo("isReceive", Boolean.FALSE));
	}
}
if(getUIContext().get("isPurchase") != null){
	if(((Boolean)getUIContext().get("isPurchase")).booleanValue()){
		filter.getFilterItems().add(
				new FilterItemInfo("isPurchase", Boolean.TRUE));
	}else{
		filter.getFilterItems().add(
				new FilterItemInfo("isPurchase", Boolean.FALSE));
	}
}
if(getUIContext().get("isFund") != null){
	if(((Boolean)getUIContext().get("isFund")).booleanValue()){
		filter.getFilterItems().add(
				new FilterItemInfo("isFund", Boolean.TRUE));
	}else{
		filter.getFilterItems().add(
				new FilterItemInfo("isFund", Boolean.FALSE));
	}
}
if(getUIContext().get("isTrip") != null){
	if(((Boolean)getUIContext().get("isTrip")).booleanValue()){
		filter.getFilterItems().add(
				new FilterItemInfo("isTrip", Boolean.TRUE));
	}else{
		filter.getFilterItems().add(
				new FilterItemInfo("isTrip", Boolean.FALSE));
	}
}
if(getUIContext().get("isExpense") != null){
	if(((Boolean)getUIContext().get("isExpense")).booleanValue()){
		filter.getFilterItems().add(
				new FilterItemInfo("isExpense", Boolean.TRUE));
	}else{
		filter.getFilterItems().add(
				new FilterItemInfo("isExpense", Boolean.FALSE));
	}
}
/* <-MISALIGNED-> */ /* 211*/        return filter;
            }
            protected FilterInfo getDefaultFilterForQuery()
            {
/* <-MISALIGNED-> */ /* 215*/        FilterInfo filter = getEnableFilter();
/* <-MISALIGNED-> */ /* 216*/        return filter;
            }
            protected FilterInfo getDefaultFilterForTree()
            {/* 222*/        FilterInfo filter = super.getDefaultFilterForTree();
/* 223*/        FilterInfo enableFilter = getEnableFilter();
/* 224*/        if(filter == null || filter.getFilterItems().isEmpty())/* 224*/            return enableFilter;

/* 226*/        try
                {
/* <-MISALIGNED-> */ /* 226*/            filter.mergeFilter(enableFilter, "and");
                }
/* <-MISALIGNED-> */ /* 227*/        catch(BOSException e)
                {
/* <-MISALIGNED-> */ /* 228*/            handUIException(e);
                }
/* <-MISALIGNED-> */ /* 230*/        return filter;
            }
            protected boolean isIgnoreTreeCUFilter()
            {
/* <-MISALIGNED-> */ /* 235*/        return true;
            }
            private static final Logger logger = CoreUIObject.getLogger(ContractTypeF7UI.class.getName());
            private boolean isCancel;
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\sp\sp-fdc_basedata_client.jar
	Total time: 59 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/