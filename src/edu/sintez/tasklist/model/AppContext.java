package edu.sintez.tasklist.model;

import android.app.Application;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Контекст приложения
 */
public class AppContext extends Application{

	/**
	 * Константы
	 */
	public static final String LOG = AppContext.class.getName();

	public static final String KEY_TYPE_ACTION = LOG + "typeactionkey";
	public static final String KEY_DOC_INDEX = LOG + "docindexkey";
	public static final String KEY_DOC_INDEXES = LOG + "docindexeskey";
	public static final int VAL_ACTION_NEWTASK = 0;
	public static final int VAL_ACTION_UPDATE = 1;

	public static final String KEY_NAME = "name";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_DATE = "date";
	public static final String KEY_PRIORITY = "priority";

	public static final String RECEIVER_LV_REFRESH = LOG + "refresh";
	public static final String RECEIVER_LV_ITEM_DELETE = LOG + "itemDelete";


	/**
	 * Хранилище всех документов
	 */
	private List<ToDoDocument> listDocs = new ArrayList<ToDoDocument>();

	public List<ToDoDocument> getListDocs() {
		return listDocs;
	}
}
