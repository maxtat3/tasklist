package edu.sintez.tasklist.model;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by max on 08.06.15.
 */
public class AppContext extends Application{

	public static final String KEY_TYPE_ACTION = "keyaction";
	public static final String KEY_DOCINDEX = "keyindex";
	public static final int VAL_ACTION_NEWTASK = 0;
	public static final int VAL_ACTION_UPDATE = 1;

	private List<ToDoDocument> listDocs = new ArrayList<ToDoDocument>();

	public List<ToDoDocument> getListDocs() {
		return listDocs;
	}
}
