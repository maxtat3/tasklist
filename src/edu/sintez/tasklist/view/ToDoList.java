package edu.sintez.tasklist.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import edu.sintez.tasklist.R;
import edu.sintez.tasklist.model.ToDoDocument;

import java.util.ArrayList;
import java.util.List;


/**
 * Основная активность для отображения заметок
 */
public class ToDoList extends Activity {

	public static final int TO_DO_DETAILS_REQUEST = 1000;
	public static final String TO_DO_DOCUMENTS = "edu.sintez.model.ToDoDocument";

	private ListView listTasks;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);

		listTasks = (ListView) findViewById(R.id.lw_tasks);
		listTasks.setOnItemClickListener(new ListViewClickListener());

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
				Toast.makeText(this, "press new document", Toast.LENGTH_SHORT).show();
				break;

			case R.id.item2_add_task:{
				ToDoDocument doc = new ToDoDocument();
				doc.setName("doc name");
				showDocument(doc);
				Toast.makeText(this, "press new document", Toast.LENGTH_SHORT).show();
				return true;
			}

			case R.id.item3_back:
				Toast.makeText(this, "pressed Back", Toast.LENGTH_SHORT).show();
				break;
			case R.id.item4_save:
				Toast.makeText(this, "press SAVE", Toast.LENGTH_SHORT).show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Имитация заполнение тасков
	 */
	private void fillListTasks() {
		ToDoDocument doc1 = new ToDoDocument("Name1", "Context1", null);
		ToDoDocument doc2 = new ToDoDocument("Name2", "Context2", null);
		ToDoDocument doc3 = new ToDoDocument("Name3", "Context3", null);

		List<ToDoDocument> listDocument = new ArrayList<ToDoDocument>();
		listDocument.add(doc1);
		listDocument.add(doc2);
		listDocument.add(doc3);

		ArrayAdapter<ToDoDocument> arrayAdapter = new ArrayAdapter<ToDoDocument>(this, R.layout.pattern_lw_row, listDocument);
		listTasks.setAdapter(arrayAdapter);
	}

	private void showDocument(ToDoDocument toDoDocument) {
		Intent intentToDoDetails = new Intent(this, ToDoDetail.class);
		intentToDoDetails.putExtra(TO_DO_DOCUMENTS, toDoDocument);
		startActivityForResult(intentToDoDetails, TO_DO_DETAILS_REQUEST);
	}

	private class ListViewClickListener implements android.widget.AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			ToDoDocument doc = (ToDoDocument) parent.getAdapter().getItem(position);
			showDocument(doc);
		}
	}
}
