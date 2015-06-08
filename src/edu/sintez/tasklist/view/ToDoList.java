package edu.sintez.tasklist.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import edu.sintez.tasklist.R;
import edu.sintez.tasklist.model.AppContext;
import edu.sintez.tasklist.model.ToDoDocument;
import java.util.*;


/**
 * Основная активность для отображения заметок
 */
public class ToDoList extends Activity {

	private static final String LOG = ToDoList.class.getName();

	public static final int TO_DO_DETAILS_REQUEST = 1000;
	public static final String TO_DO_DOCUMENTS = "edu.sintez.model.ToDoDocument";

	private ListView lvTasks;
	private EditText etFilterTasks;

	private List<ToDoDocument> listDocs;
	private ArrayAdapter<ToDoDocument> arrayAdapter;

	private Intent intentDetail;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(LOG, "@onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);

		listDocs = ((AppContext) getApplicationContext()).getListDocs();

		etFilterTasks = (EditText) findViewById(R.id.et_filter_task);
		etFilterTasks.addTextChangedListener(new FilterTaskChangeListener());

		lvTasks = (ListView) findViewById(R.id.lw_tasks);
		lvTasks.setOnItemClickListener(new ListViewClickListener());


		intentDetail = new Intent(this, ToDoDetail.class);

		fillListTasks();
	}

	@Override
	protected void onStart() {
		Log.d(LOG, "@onStart");
		super.onStart();
		arrayAdapter = new ArrayAdapter<ToDoDocument>(this, R.layout.pattern_lw_row, listDocs);
		lvTasks.setAdapter(arrayAdapter);
		checkFilterEnable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_todo_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.item1_add_task:{
				Log.d(LOG, "add task");

				Bundle bundle = new Bundle();
				bundle.putInt(AppContext.KEY_TYPE_ACTION, AppContext.VAL_ACTION_NEWTASK);
				intentDetail.putExtras(bundle);
				startActivity(intentDetail);

				return true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	private void checkFilterEnable() {
		if (listDocs.size() != 0) {
			etFilterTasks.setEnabled(true);
			arrayAdapter.getFilter().filter(etFilterTasks.getText().toString());
		} else {
			etFilterTasks.setEnabled(false);
		}
	}

	/**
	 * Имитация заполнение тасков
	 */
	private void fillListTasks() {
		ToDoDocument doc1 = new ToDoDocument("Name1", "Context1", new Date(), 0);
		ToDoDocument doc2 = new ToDoDocument("Name2", "Context2", new Date(), 1);
		ToDoDocument doc3 = new ToDoDocument("Name3", "Context3", new Date(), 2);

		listDocs.add(doc1);
		listDocs.add(doc2);
		listDocs.add(doc3);
	}





	private void showDocument(ToDoDocument toDoDocument) {
		Intent intentToDoDetails = new Intent(this, ToDoDetail.class);
		intentToDoDetails.putExtra(TO_DO_DOCUMENTS, toDoDocument);
		startActivityForResult(intentToDoDetails, TO_DO_DETAILS_REQUEST);
	}



	private class ListViewClickListener implements android.widget.AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Bundle bundle = new Bundle();
			bundle.putInt(AppContext.KEY_TYPE_ACTION, AppContext.VAL_ACTION_UPDATE);
			bundle.putInt(AppContext.KEY_DOCINDEX, ((ToDoDocument)parent.getAdapter().getItem(position)).getNumber());
			startActivity(intentDetail.putExtras(bundle));
		}
	}

	private class FilterTaskChangeListener implements TextWatcher {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			arrayAdapter.getFilter().filter(s);
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	}
}
