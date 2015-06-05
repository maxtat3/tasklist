package edu.sintez.tasklist.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import edu.sintez.tasklist.R;
import edu.sintez.tasklist.model.ToDoDocument;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * Основная активность для отображения заметок
 */
public class ToDoList extends Activity {

	private static final String LOG = ToDoList.class.getName();
	public static final int TO_DO_DETAILS_REQUEST = 1000;
	public static final String TO_DO_DOCUMENTS = "edu.sintez.model.ToDoDocument";

	private ListView lvTasks;

	private List<ToDoDocument> listDocs;
	private ArrayAdapter<ToDoDocument> arrayAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);

		lvTasks = (ListView) findViewById(R.id.lw_tasks);
		lvTasks.setOnItemClickListener(new ListViewClickListener());

		listDocs = new ArrayList<ToDoDocument>();
//		listDocs = new CopyOnWriteArrayList<ToDoDocument>();
		arrayAdapter = new ArrayAdapter<ToDoDocument>(this, R.layout.pattern_lw_row, listDocs);
		lvTasks.setAdapter(arrayAdapter);

		fillListTasks();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_todo_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.item1_new_doc:
				Log.d(LOG, "new document");
				break;

			case R.id.item2_add_task:{
				ToDoDocument doc = new ToDoDocument();
				doc.setName("doc name");
				showDocument(doc);
				Log.d(LOG, "add task");
				return true;
			}

			case R.id.item3_back:
				Log.d(LOG, "back");
				break;
			case R.id.item4_save:
				Log.d(LOG, "save");
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Имитация заполнение тасков
	 */
	private void fillListTasks() {
		ToDoDocument doc1 = new ToDoDocument("Name1", "Context1", null, 0);
		ToDoDocument doc2 = new ToDoDocument("Name2", "Context2", null, 1);
		ToDoDocument doc3 = new ToDoDocument("Name3", "Context3", null, 2);

		listDocs.add(doc1);
		listDocs.add(doc2);
		listDocs.add(doc3);
	}

	private void addDocument(ToDoDocument doc) {
		if (doc.getNumber() == -1) { /*это новый документ - сохраняем его*/
			listDocs.add(doc);
		} else {
			listDocs.set(doc.getNumber(), doc); /*такой локумент уже есть - редактируем и сохраняем*/
		}
		arrayAdapter.notifyDataSetChanged();
	}

	private void showDocument(ToDoDocument toDoDocument) {
		Intent intentToDoDetails = new Intent(this, ToDoDetail.class);
		intentToDoDetails.putExtra(TO_DO_DOCUMENTS, toDoDocument);
		startActivityForResult(intentToDoDetails, TO_DO_DETAILS_REQUEST);
	}

	private void deleteDocument(ToDoDocument doc){
		listDocs.remove(doc);
		arrayAdapter.notifyDataSetChanged();
	}

	private class ListViewClickListener implements android.widget.AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ToDoDocument doc = (ToDoDocument) parent.getAdapter().getItem(position);
			showDocument(doc);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == TO_DO_DETAILS_REQUEST)
			switch (resultCode){
				case RESULT_CANCELED:
					Log.d(LOG, "back");
					break;

				case ToDoDetail.RESULT_SAVE:
					Log.d(LOG, "save");
					ToDoDocument receiveDoc = (ToDoDocument) data.getSerializableExtra(TO_DO_DOCUMENTS);
					addDocument(receiveDoc);
					break;

				case ToDoDetail.RESULT_DELETE:
					ToDoDocument doc = (ToDoDocument) data.getSerializableExtra(TO_DO_DOCUMENTS);
					deleteDocument(doc);
			}
	}

	/**
	 * Тестирование сравнения двух документов между собой
	 */
	private void testDocEquals(){
		ToDoDocument doc1 = new ToDoDocument("Name1", "Context1", null);
		doc1.setNumber(1);
		ToDoDocument doc2 = new ToDoDocument("Name2", "Context2", null);
		doc2.setNumber(2);
		ToDoDocument doc3 = new ToDoDocument("Name3", "Context3", null);
		doc3.setNumber(3);
		ToDoDocument doc4 = new ToDoDocument("Name4", "Context4", null);
		doc4.setNumber(3);

		if (doc1.equals(doc2)){
			Log.d(LOG, "doc 1 equals doc 2");
		} else {
			Log.d(LOG, "doc 1 NOT equals doc 2");
		}

		if (doc3.equals(doc4)){
			Log.d(LOG, "doc 3 equals doc 4");
		} else {
			Log.d(LOG, "doc 3 NOT equals doc 4");
		}
	}
}
