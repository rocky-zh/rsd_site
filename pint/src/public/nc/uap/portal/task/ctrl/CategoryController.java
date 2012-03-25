package nc.uap.portal.task.ctrl;
import nc.uap.lfw.core.event.TextEvent;
import nc.uap.lfw.core.cmd.CmdInvoker;
import nc.uap.lfw.core.cmd.UifPlugoutCmd;
import nc.uap.lfw.core.ctx.AppLifeCycleContext;
import nc.uap.lfw.core.event.DataLoadEvent;
import nc.uap.lfw.core.ctrl.IController;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.Dataset;
/** 
 * @author chouhl
 */
public class CategoryController implements IController {
  private static final long serialVersionUID=1L;
  /** 
 * ��������ѡ��
 * @param dataLoadEvent
 */
  public void onDataLoad_cateds(  DataLoadEvent dataLoadEvent){
    Dataset ds = dataLoadEvent.getSource();
		addCategory(ds, "�ҵİ��", "c001");
		addCategory(ds, "�ҵ��ļ�", "c002");
		ds.setRowSelectIndex(0);
		CmdInvoker.invoke(new UifPlugoutCmd("category", "doFilterTaskStatus"));
  }
  /** 
 * ���ӷ���
 * @param ds
 * @param title
 * @param id
 */
  private void addCategory(  Dataset ds,  String title,  String id){
    Row row = ds.getEmptyRow();
	row.setValue(ds.nameToIndex("id"), id);
	row.setValue(ds.nameToIndex("title"), title);
	row.setValue(ds.nameToIndex("taskstatus"), "UnRead");
	ds.addRow(row);
  }
  
  /** 
 * ��ѡ��ֵ�ı��¼�.
 * @param textEvent
 */
  public void valueChanged_cbc(  TextEvent textEvent){
	  Dataset ds = AppLifeCycleContext.current().getViewContext().getView().getViewModels().getDataset("cateds");
	  Row row = ds.getSelectedRow();
	  row.setString(ds.nameToIndex("taskstatus"), textEvent.getSource().getValue()) ;
  }
}
