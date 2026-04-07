/*jadclipse*/package com.kingdee.eas.fdc.basedata.client;
import com.kingdee.bos.ctrl.extendcontrols.KDBizMultiLangBox;
import com.kingdee.bos.ctrl.swing.KDTextField;
import com.kingdee.bos.dao.IObjectValue;
import com.kingdee.bos.metadata.entity.EntityViewInfo;
import com.kingdee.bos.metadata.entity.FilterInfo;
import com.kingdee.bos.metadata.entity.FilterItemInfo;
import com.kingdee.bos.metadata.entity.SelectorItemCollection;
import com.kingdee.bos.metadata.entity.SelectorItemInfo;
import com.kingdee.bos.ui.face.CoreUIObject;
import com.kingdee.eas.fdc.basedata.*;
import com.kingdee.eas.framework.ICoreBase;
import com.kingdee.eas.util.client.EASResource;
import java.awt.event.ActionEvent;
import org.apache.log4j.Logger;
public class ContractDetailDefEditUI extends AbstractContractDetailDefEditUI
{
            public ContractDetailDefEditUI()
                throws Exception
            {
            }
            public void onLoad()
                throws Exception
            {

























/*  47*/        super.onLoad();
/*  48*/        setTitle();
				EntityViewInfo view=new EntityViewInfo();
				FilterInfo filter=new FilterInfo();
				filter.getFilterItems().add(new FilterItemInfo("isEnabled",Boolean.TRUE));
				view.setFilter(filter);
				this.prmtContractType.setQueryInfo("com.kingdee.eas.fdc.basedata.app.ContractTypeQuery");
				this.prmtContractType.setEntityViewInfo(view);
            }
            private void setTitle()
            {
/*  52*/        FDCBaseDataClientUtils.setupUITitle(this, EASResource.getString("com.kingdee.eas.fdc.basedata.FDCBaseDataResource", "ContractDetailDef"));
            }
            public SelectorItemCollection getSelectors()
            {



















































/* 107*/        SelectorItemCollection sic = new SelectorItemCollection();
/* 108*/        sic.add(new SelectorItemInfo("number"));
/* 109*/        sic.add(new SelectorItemInfo("contractType.*"));
/* 110*/        sic.add(new SelectorItemInfo("dataTypeEnum"));
/* 111*/        sic.add(new SelectorItemInfo("description"));
/* 112*/        sic.add(new SelectorItemInfo("name"));
/* 113*/        sic.add(new SelectorItemInfo("isEnabled"));
/* 114*/        sic.add(new SelectorItemInfo("isMustInput"));
/* 115*/        return sic;
            }
            protected IObjectValue createNewData()
            {
















































































/* 199*/        ContractDetailDefInfo contractDetailDefInfo = new ContractDetailDefInfo();
/* 200*/        contractDetailDefInfo.setIsEnabled(isEnabled);
/* 201*/        return contractDetailDefInfo;
            }
            protected ICoreBase getBizInterface()
                throws Exception
            {/* 205*/        return ContractDetailDefFactory.getRemoteInstance();
            }
            protected FDCDataBaseInfo getEditData()
            {/* 208*/        return editData;
            }
            protected KDBizMultiLangBox getNameCtrl()
            {/* 211*/        return bizName;
            }
            protected KDTextField getNumberCtrl()
            {/* 214*/        return txtNumber;
            }
            protected void verifyInput(ActionEvent e)
                throws Exception
            {
/* <-MISALIGNED-> */ /* 217*/        FDCClientVerifyHelper.verifyEmpty(this, prmtContractType);
/* 220*/        FDCClientVerifyHelper.verifyEmpty(this, txtNumber);
/* 221*/        FDCClientVerifyHelper.verifyEmpty(this, bizName);
/* 222*/        FDCClientVerifyHelper.verifyEmpty(this, comboDataTypeEnum);
            }
            private static final Logger logger = CoreUIObject.getLogger(ContractDetailDefEditUI.class.getName());
}

/*
	DECOMPILATION REPORT

	Decompiled from: D:\ws75\sd\lib\patch\sp-fdc_basedata-client.jar
	Total time: 64 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/