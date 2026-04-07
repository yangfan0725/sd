package com.kingdee.eas.base.attachment.client;

public class AttachmentByBoListUICTEx extends AttachmentByBoListUI {

	public AttachmentByBoListUICTEx() throws Exception {
		super();
	}

	public void onLoad() throws Exception {
		super.onLoad();
		this.tblMain.getColumn("creatorName").getStyleAttributes().setHided(true);
		this.tblMain.getColumn("createTime").getStyleAttributes().setHided(true);
	}

}
