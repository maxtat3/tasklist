package edu.sintez.tasklist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import edu.sintez.tasklist.model.AppContext;
import edu.sintez.tasklist.model.ToDoDocument;
import edu.sintez.tasklist.view.ToDoList;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Широковещательный приемник для удаления выбранных заметки/заметок
 */
public class DeleteDocReceiver extends BroadcastReceiver {

	private static final String LOG = DeleteDocReceiver.class.getName();

	private Context context;
	private List<ToDoDocument> docsList;


	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		docsList = ((AppContext) context).getListDocs();

		ArrayList<Integer> docsIndicesToRemove = intent.getIntegerArrayListExtra(AppContext.KEY_DOC_INDEXES);
		Collections.reverse(docsIndicesToRemove);

		/* for debug */
//		for (ToDoDocument toDoDocument : docsList) {
//			Log.d(LOG, "toDoDocument number= " + toDoDocument.getNumber());
//		}
//		for (Integer doc : docsIndicesToRemove) {
//			Log.d(LOG, "doc number= " + doc);
//		}

		for (Integer index : docsIndicesToRemove) {
			ToDoDocument remDoc = docsList.remove(index.intValue());
			String shpDirPath = context.getApplicationInfo().dataDir + "/" + ToDoList.SHARED_PREFS_DIR;
			new File(shpDirPath, remDoc.getCreateDate().getTime() + ".xml").delete();
		}

		Intent intentLVRefresh = new Intent(AppContext.RECEIVER_LV_REFRESH);
		LocalBroadcastManager.getInstance(context).sendBroadcast(intentLVRefresh);
	}

}
