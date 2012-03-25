package nc.uap.portal.msg.ui;

import java.util.List;

import nc.uap.lfw.core.LfwRuntimeEnvironment;
import nc.uap.lfw.core.data.Dataset;
import nc.uap.lfw.core.data.Field;
import nc.uap.lfw.core.data.Row;
import nc.uap.lfw.core.data.RowData;
import nc.uap.lfw.core.data.RowSet;
import nc.uap.portal.msg.model.Msg;
import nc.uap.portal.msg.model.MsgCategory;
import nc.uap.portal.util.ToolKit;
import nc.vo.pub.BeanHelper;

/**
 * ��Ϣ���ݽ����ߣ�����������UIģ��֮���ת��
 * 
 * @author licza
 * 
 */
public class MsgDataTranslator {

	/**
	 * �������������ݼ�����
	 * 
	 * @param ds
	 * @param category
	 */
	public static void buildNavTree(Dataset ds, List<MsgCategory> categorys, String parent) {

		if (!ToolKit.notNull(categorys))
			return;

		int idIndex = ds.nameToIndex(MsgCategory.ID);
		int parentidIndex = ds.nameToIndex(MsgCategory.PARENTID);
		int titleIndex = ds.nameToIndex(MsgCategory.TITLE);
		int i18nnameIndex = ds.nameToIndex(MsgCategory.I18NNAME);
		int pluginidIndex = ds.nameToIndex(MsgCategory.PLUGINID);
		/**
		 * ��ӷ���
		 */
		for (MsgCategory category : categorys) {
			
			if(category == null)
				continue;
			
			Row row = ds.getEmptyRow();
			String id = category.getId();
			row.setValue(idIndex, id);
			row.setValue(parentidIndex, parent);
			row.setValue(titleIndex, category.getTitle());
			row.setValue(i18nnameIndex, category.getI18nname());
			row.setValue(pluginidIndex, category.getPluginid());
			ds.addRow(row);
			
			String selectid = LfwRuntimeEnvironment.getWebContext().getAppSession().getOriginalParameter("category"); 
			if(selectid != null && selectid.length() > 0 && selectid.equals(id)){
				ds.setRowSelectIndex(ds.getRowIndex(row));
			}
			/**
			 * �ݹ�����ӷ���
			 */
			List<MsgCategory> childs = category.getChild();
			if (ToolKit.notNull(childs)) {
				buildNavTree(ds, childs, id);
			}
		}

	}
	/**
	 * ������Ϣ����ID�ݹ��ѯ����
	 * @param id
	 * @param categorys
	 * @return
	 */
	public static MsgCategory findCategoryById(String id, List<MsgCategory> categorys){
		if (!ToolKit.notNull(categorys))
			return null;
		if(id == null)
			return null;
		
		for (MsgCategory category : categorys) {
			if(category == null)
				continue;
			
			if(id.equals(category.getId())){
				return category;
			}
			
			/**
			 * ��ѯ�ӷ���
			 */
			MsgCategory mc = findCategoryById(id, category.getChild());
			if(mc != null)
				return mc;
		}
		
		return null;
	}
	
	/**
	 * ������Ϣ�������
	 * @param ds
	 * @param msgs
	 */
	public static void buildMsgGrid(Dataset ds, Msg[] msgs){
		if(msgs == null || msgs.length == 0){
			return;
		}
		RowSet rowSet = ds.getRowSet(ds.getCurrentKey());
		if (rowSet == null) {
		   rowSet = new RowSet(ds.getCurrentKey());
		   ds.addRowSet(ds.getCurrentKey(), rowSet);
		}
		rowSet.setRowSetChanged(true);
		RowData rowData = rowSet.getCurrentRowData(true);
		rowData.clear();
		
		for(Msg msg : msgs){
			setMsgRowData(ds, msg);
		}
	}
	
	/**
	 * ������Ϣ������ݼ�������
	 * @param ds
	 * @param msg
	 */
	protected static void setMsgRowData(Dataset ds, Msg msg) {
		Row row = ds.getEmptyRow();
		List<Field> fields = ds.getFieldSet().getFieldList();
		if(fields != null){
			for(Field field : fields){
				String fieldName = field.getId();
				row.setValue(ds.nameToIndex(fieldName), BeanHelper.getProperty(msg, fieldName));
			}
		}
		ds.addRow(row);
	}
	
}
