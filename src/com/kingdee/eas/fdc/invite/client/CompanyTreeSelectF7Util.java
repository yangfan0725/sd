package com.kingdee.eas.fdc.invite.client;


import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.TreeModel;

import com.kingdee.bos.ctrl.swing.KDDialog;
import com.kingdee.bos.ctrl.swing.KDPromptSelector;
import com.kingdee.bos.ctrl.swing.tree.DefaultKingdeeTreeNode;
import com.kingdee.bos.ctrl.swing.tree.KingdeeTreeModel;
import com.kingdee.bos.ctrl.swing.util.CtrlSwingUtilities;
import com.kingdee.bos.ui.face.IUIObject;
import com.kingdee.eas.base.commonquery.client.CustomerQueryPanel;
import com.kingdee.eas.basedata.org.FullOrgUnitInfo;
import com.kingdee.eas.basedata.org.OrgStructureInfo;
import com.kingdee.eas.fdc.basedata.FDCHelper;
import com.kingdee.eas.fdc.basedata.client.FDCClientHelper;
import com.kingdee.eas.framework.client.tree.KDTreeNode;
import com.kingdee.eas.util.client.ComponentUtil;


public class CompanyTreeSelectF7Util implements KDPromptSelector {
    
    //??????
    private Window owner=null;    
    private IUIObject uiOwner=null;
    CustomerQueryPanel queryFilterUI = null;
    CompanyTreeCommomSelectUI companySelectUI;
    //??˾IDs
    private String[] companyIds = null;
    private List orgUnitVec;
    private KDDialog companySelectDlg = null;
    
    private List f7OrgValue =  null;
    

	private TreeModel orgTreeModel;
    protected CompanyTreeSelectF7Util()
    {
        this((Frame)null);
    }
    protected CompanyTreeSelectF7Util(IUIObject _uiObject)
    {
        uiOwner=_uiObject;
    }
    protected CompanyTreeSelectF7Util(Frame _owner)
    {
        owner=_owner;
    }
    protected CompanyTreeSelectF7Util(Dialog _owner)
    {
        owner=_owner;
    }
    protected CompanyTreeSelectF7Util(CustomerQueryPanel _uiObject)
    {
    	uiOwner=_uiObject;
    	queryFilterUI = _uiObject;
    }
    
    protected CompanyTreeSelectF7Util(CustomerQueryPanel _uiObject,TreeModel treeModel)
    {
    	uiOwner=_uiObject;
    	queryFilterUI = _uiObject;
    	orgTreeModel = treeModel;
    }
    
    protected CompanyTreeSelectF7Util(CustomerQueryPanel _uiObject,Vector f7OrgValue,TreeModel treeModel)
    {
        uiOwner=_uiObject;
        queryFilterUI = _uiObject;
        this.f7OrgValue = f7OrgValue;
        orgTreeModel = treeModel;
    }
   
    
    /**
     * 
     * ??????TODO 
     * @author jxd
     * ????ʱ?䣺2006-1-4
     */
    public void show() {
        Window owner=null;
        if(uiOwner!=null) {
            owner=ComponentUtil.getOwnerWindow((Component)uiOwner);
        }
//        
//        //????UI????????ʾ
//        UIContext context = new UIContext(owner);
//        IUIWindow uiWindow = null;
//        try {
//            uiWindow = UIFactory.createUIFactory(UIFactoryName.MODEL).create(
//                    "com.kingdee.eas.ma.budget.client.CompanyTreeSelectUI", context);
//        } catch (UIException e) {
//            e.printStackTrace();
//        }
//        
//        //f7UI=(BgItemGroupInF7UI)uiWindow.getUIObject();
//        uiWindow.show();
        if(companySelectDlg == null)
        	companySelectDlg = new KDDialog(FDCClientHelper.getFrameAncestor(owner),
                true);        
        try {
            if( companySelectUI==null )
            	companySelectUI = new CompanyTreeCommomSelectUI(orgTreeModel); 
            if( queryFilterUI !=null ) {
            	if(f7OrgValue != null){
            		KDTreeNode root = new KDTreeNode("");
            		KingdeeTreeModel model = new KingdeeTreeModel(root);
                	Object[] orgs = f7OrgValue.toArray();
                	for (int i = 0; i < orgs.length; i++) {
                		OrgStructureInfo company = (OrgStructureInfo) orgs[i];
            			DefaultKingdeeTreeNode node = new DefaultKingdeeTreeNode(company,
            	                null, false, false);
            			model.insertNodeInto(node, root, model.getChildCount(root));
            		}
                	companySelectUI.setValue(model);
            	}
            	
            }
            companySelectUI.setDialog(companySelectDlg);
            companySelectUI.setPreferredSize(companySelectUI.getBounds().getSize());
            companySelectDlg.getContentPane().setLayout(new BorderLayout());
            companySelectDlg.getContentPane().add(companySelectUI,
                    BorderLayout.CENTER);
            companySelectDlg.setSize(640, 480);
            CtrlSwingUtilities.centerWindow(companySelectDlg);
            companySelectDlg.setResizable(false);
            companySelectDlg.setTitle(companySelectUI.getUITitle());
            //        companySelectUI.setValue(this.companyTree);
            companySelectUI.addPropertyChangeListener(new PropertyChangeListener() {
                public void propertyChange(PropertyChangeEvent e) {
                    if (e.getPropertyName().equals("companyChanged")) {
                        try {
                            companyValueChange(e.getNewValue());
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            //handUIException(e1);
                        }
                    }
                }
            });
        
        } catch (Exception e2) {
            // TODO ?Զ????? catch ??
            e2.printStackTrace();
        }        
        companySelectDlg.show();

    }
    
    /**
     * ??˾?ı?ʱ????
     * ????ֻ??ѯʵ?幫˾???븲?Ǵ˷????????˵????幫˾
     * ?ο?CapitalStockFilterUI.companyValueChange();
     * 
     * @param object
     */
    protected void companyValueChange(Object object) throws Exception {     
    	
        this.setCompanyIds(FDCHelper.getIds(object));
        Object[] data ;
        if (object instanceof Object[]){
             data = (Object[]) object;
            
        }
        else{
            data = new Object[]{object};
        }
        if (FDCHelper.isEmpty(data)) {
            //TODO??ֻ?е?ǰ??˾
            this.setOrgUnitVec(null);
        } else{
        	List vec = new ArrayList(data.length);
            for (int i = 0; i < data.length; i++) {
            	OrgStructureInfo info = (OrgStructureInfo) data[i];
                vec.add(info);
                //result[i] = info.getId().toString();                
            }
            this.setOrgUnitVec(vec);            
        }
    }
    
    /**
     * ?õ???˾ids
     * 
     * @param companyIds
     */
    public void setCompanyIds(String[] companyIds) {
    	if(FDCHelper.isEmpty(companyIds)){
    		companyIds = null;
    	}else{
    		this.companyIds = (String[])companyIds.clone();
        }        
    }
    
    public String[] getCompanyIds() {
    	if(companyIds==null)
    		return new String[0];
    	else
    		return (String[])this.companyIds.clone();
    }   
    
    
    /**
     * 
     * ??????TODO 
     * @author jxd
     * ????ʱ?䣺2006-1-4
     */
    public boolean isCanceled() {        
        return false;
    }
    
    /**
     * 
     * ???????õ????? 
     * @author jxd
     * ????ʱ?䣺2006-1-4
     */
    public Object getData() {       
        //return this.companyIds;
    	return this.orgUnitVec;
        
    }
    /**
     * @return ???? orgUnitVec??
     */
    public List getOrgUnitVec() {
        return orgUnitVec;
    }
    /**
     * @param orgUnitVec Ҫ???õ? orgUnitVec??
     */
    public void setOrgUnitVec(List orgUnitVec) {
        this.orgUnitVec = orgUnitVec;
    }

    public void setTreeModel(TreeModel orgTreeModel){
    	this.orgTreeModel = orgTreeModel;
    }
    
    public TreeModel getTreeModel(){
    	return orgTreeModel;
    }
    

    public List getF7OrgValue() {
		return f7OrgValue;
	}
	public void setF7OrgValue(List f7OrgValue) {
		this.f7OrgValue = f7OrgValue;
	}
	
}
