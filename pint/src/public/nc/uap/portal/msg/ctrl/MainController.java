package nc.uap.portal.msg.ctrl;
import java.util.List;

import nc.uap.lfw.core.comp.MenuItem;
import nc.uap.lfw.core.comp.MenubarComp;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.PaginationInfo;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.event.DatasetEvent;
import nc.uap.lfw.core.event.GridRowEvent;
import nc.uap.lfw.core.event.MouseEvent;
import nc.uap.lfw.core.event.TabEvent;
import nc.uap.lfw.core.exception.LfwRuntimeException;
import nc.uap.lfw.core.page.LfwWidget;
import nc.uap.lfw.jsp.uimeta.UICardLayout;
import nc.uap.lfw.jsp.uimeta.UIElement;
import nc.uap.lfw.jsp.uimeta.UILayoutPanel;
import nc.uap.lfw.jsp.uimeta.UIMenubarComp;
import nc.uap.lfw.jsp.uimeta.UIMeta;
import nc.uap.lfw.jsp.uimeta.UITabComp;
import nc.uap.lfw.jsp.uimeta.UITabItem;
import nc.uap.portal.msg.model.Msg;
import nc.uap.portal.msg.model.MsgBox;
import nc.uap.portal.msg.model.MsgCategory;
import nc.uap.portal.msg.model.MsgCmd;
import nc.uap.portal.msg.provide.IMsgCmd;
import nc.uap.portal.msg.provide.IMsgProvide;
import nc.uap.portal.msg.ui.MsgDataTranslator;
import nc.uap.portal.msg.ui.MsgOperHelper;
import nc.uap.portal.plugins.PluginManager;
import nc.uap.portal.plugins.model.PtExtension;
/** 
 * ��Ϣ���Ľ���MainWidget������
 * @author chouhl
 */
public class MainController implements IController {
  private static final long serialVersionUID=1L;
  private static final String UNDERLINE="_";
  /** 
 * �˵���id
 */
  private static final String MENU_MSG="menu_msg";
  /** 
 * ���ص�����������
 * @param dataLoadEvent
 */
  public void onDataLoad(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		if (ds.getCurrentKey() == null)
			ds.setCurrentKey(Dataset.MASTER_KEY);
		PluginManager pm = PluginManager.newIns();
		/**
		 * ����������Ϣ���
		 */
		List<PtExtension> plugins = pm.getExtensions(IMsgProvide.PID);

		if (plugins != null && !plugins.isEmpty()) {
			for (PtExtension plugin : plugins) {
				/**
				 * ��ȡ��Ϣ���ʵ��
				 */
				IMsgProvide provide = plugin.newInstance(IMsgProvide.class);

				/**
				 * ���ݻ�ȡ����ķ��ഴ��������
				 */
				MsgDataTranslator.buildNavTree(ds, provide.getCategory(), null);
			}
		}
  }
  /** 
 * �������ݼ�ѡ���¼�
 * @param datasetEvent
 */
  public void onAfterNavSelect(  DatasetEvent datasetEvent){
    Dataset ds = datasetEvent.getSource();
		Row currentRow = ds.getSelectedRow();

		int pluginidIdx = ds.nameToIndex(MsgCategory.PLUGINID);
		int idIdx = ds.nameToIndex(MsgCategory.ID);

		String id = currentRow.getString(idIdx);
		String pluginid = currentRow.getString(pluginidIdx);
		//��ʼ���Ҳ�"msgbox_card"
		
		UICardLayout uiCard = (UICardLayout)getUIMenu("msgbox_card");
		if("0".equals(uiCard.getCurrentItem())){
			uiCard.setCurrentItem("1");
		}
		
		/**
		 * ���ݲ��ID��õ�ǰ��Ϣ���
		 */
		IMsgProvide provide = MsgOperHelper.getMsgProvide(pluginid);

		/**
		 * ���ҵ�ǰ��Ϣ����
		 */
		MsgCategory currentCategory = MsgDataTranslator.findCategoryById(id,
				provide.getCategory());
		/**
		 * ���ҵ�ǰ����������Ϣ��
		 */
		List<MsgBox> msgBoxs = currentCategory.getMsgbox();

		UITabComp boxTab = MsgOperHelper.getBoxTab();
		List<UILayoutPanel> boxPanel = boxTab.getPanelList();
		if (!msgBoxs.isEmpty()) {

			for (int i = 0; i < boxPanel.size(); i++) {
				UITabItem ti = (UITabItem) boxPanel.get(i);
				if (i < msgBoxs.size()) {
					ti.setText(msgBoxs.get(i).getTitle());
					ti.setVisible(true);
				} else {
					ti.setVisible(false);
				}
			}

		}
		/**
		 * ����Ϣ�����ʼ��֮ǰִ�в���
		 */
		provide.beforeCategoryInit(currentCategory);
		/**
		 * Ĭ�ϻ�ȡ��һ����Ϣ��������
		 */
		MsgBox box = msgBoxs.get(0);

		Dataset currentDs = MsgOperHelper.getCurrentDataset(0);
		
		/**
		 * ��ʼ����ǰ��Ϣ������
		 */
		List<MsgCmd> cmds = box.getMsgcmd();
		
		MenubarComp menubar = this.getMenuComp(MENU_MSG);
		UIMenubarComp uimenu = (UIMenubarComp) this.getUIMenu(MENU_MSG);
		uimenu.setVisible(true);
		adapterCmd2Menu(cmds, menubar);
		
		
		/**
		 * ��ʼ����ǰ���ݼ�
		 */
		currentDs.setCurrentKey(id + UNDERLINE + box.getId());

		/**
		 * ��ȡ��һ�����ӵ�����
		 */
		PaginationInfo pi = currentDs.getCurrentRowSet().getPaginationInfo();
		Msg[] msgs = provide.query(id, null, null, box, pi);

		/**
		 * �������ݼ�
		 */


		MsgDataTranslator.buildMsgGrid(currentDs, msgs);

		/**
		 * ����Ϣ�����ʼ��֮ǰִ�в���
		 */
		provide.afterCategoryInit(currentCategory);
  }
  /** 
 * �����������menubar������
 * @param cmds
 * @param menubar
 */
  private void adapterCmd2Menu(  List<MsgCmd> cmds,  MenubarComp menubar){
    int cmdSize = cmds.size();
	  List<MenuItem> menuList = menubar.getMenuList();
	  int menubarSize = menuList.size();
	  
	  if(cmdSize > menubarSize)
		  throw new LfwRuntimeException("����˵�ѡ��̫��");
	  else{
		  
		  for(int i = 0; i < cmdSize; i++){
			  MsgCmd cmd = cmds.get(i);
			  MenuItem item = menuList.get(i);
//			  item.setId(cmd.getId());
			  item.setText(cmd.getTitle());
			  item.setVisible(true);
//			  item.setEnabled(true);
		  }
		  
		  for(int k = cmdSize; k < menubarSize; k++){
			  MenuItem item = menuList.get(k);
			  item.setVisible(false);
//			  item.setEnabled(true);
		  }
	  }
  }
  /** 
 * TabItem�ı������menubar������
 * @param tabEvent
 */
  public void afterActivedTabItemChange(  TabEvent tabEvent){
    int index = MsgOperHelper.getCurrentMsgBoxIndex();
	  
	  Dataset ds = MsgOperHelper.getCurrentDataset(2);
	  Row currentRow = ds.getSelectedRow();
	  UIMenubarComp menu = (UIMenubarComp) this.getUIMenu(MENU_MSG);
	  if(currentRow == null){
		  
		  menu.setVisible(false);
		  return;
	  }
	  else{
		  menu.setVisible(true);
		   List<MsgCmd> cmds = getCmdsByNavDs(index, ds, currentRow);
		   MenubarComp menubar = this.getMenuComp(MENU_MSG);
		   
		  adapterCmd2Menu(cmds, menubar);
		   
	  }
  }
  /** 
 * ���ݵ���������ѡtab�õ����
 * @param index
 * @param ds
 * @param currentRow
 * @return
 */
  private List<MsgCmd> getCmdsByNavDs(  int index,  Dataset ds,  Row currentRow){
    int pluginidIdx = ds.nameToIndex(MsgCategory.PLUGINID);
	  int idIdx = ds.nameToIndex(MsgCategory.ID);
	  
	  String id = currentRow.getString(idIdx);
	  String pluginid = currentRow.getString(pluginidIdx);
	  
	  /**
	   * ���ݲ��ID��õ�ǰ��Ϣ���
	   */
	  IMsgProvide provide = MsgOperHelper.getMsgProvide(pluginid);
	  
	  /**
	   * ���ҵ�ǰ��Ϣ����
	   */
	  MsgCategory currentCategory = MsgDataTranslator.findCategoryById(id,
			  provide.getCategory());
	  List<MsgCmd> cmds = currentCategory.getMsgbox().get(index).getMsgcmd();
	return cmds;
  }
  private UIElement getUIMenu(  String id){
    UIMeta uimeta = AppLifeCycleContext.current().getViewContext().getUIMeta();
    UIElement menu = uimeta.findChildById(id);
		return menu;
  }
  private MenubarComp getMenuComp(  String id){
    LfwWidget widget = AppLifeCycleContext.current().getViewContext().getView();
		MenubarComp menu = widget.getViewMenus().getMenuBar(id);
		return menu;
  }
  public void cmdEvent(  MouseEvent<MenuItem> mouseEvent){
    String itemId = mouseEvent.getSource().getId();
	  String itemName = mouseEvent.getSource().getText();
	  int index = MsgOperHelper.getCurrentMsgBoxIndex();
	  Dataset ds = MsgOperHelper.getCurrentDataset(2);
	  Row currentRow = ds.getSelectedRow();
	  List<MsgCmd> cmds = this.getCmdsByNavDs(index, ds, currentRow);
	  
	  int pluginidIdx = ds.nameToIndex(MsgCategory.PLUGINID);
	  String pluginid = currentRow.getString(pluginidIdx);
	  IMsgProvide provide = MsgOperHelper.getMsgProvide(pluginid);
	  
	  for(int i = 0; i < cmds.size(); i ++){
		  MsgCmd cmd = cmds.get(i);
		  if(itemName.equals(cmd.getTitle())){
			  provide.getCmd(cmd.getId()).execute();
			  break;
		  }
	  }
  }
  /**
   * ˫����,���Ͷ�ȡ����
   * @param gridRowEvent
   */
  public void grid_onRowDbClick(  GridRowEvent gridRowEvent){
	  	LfwWidget view = AppLifeCycleContext.current().getViewContext().getView();
		Dataset ds = view.getViewModels().getDataset("cateds");
		Row currentRow = ds.getSelectedRow();
		int pluginidIdx = ds.nameToIndex(MsgCategory.PLUGINID);
		String pluginid = currentRow.getString(pluginidIdx);
		IMsgProvide provide = MsgOperHelper.getMsgProvide(pluginid);
		IMsgCmd cmd = provide.getCmd("DO_READ");
		cmd.execute();
		  
  }
}
