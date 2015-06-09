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
import edu.sintez.tasklist.model.ListComparator;
import edu.sintez.tasklist.model.ToDoDocument;
import java.util.*;


/**
 * Основная активность для отображения заметок
 */
public class ToDoList extends Activity {

	private static final String LOG = ToDoList.class.getName();

	/* по умолчанию, выполнять сортировку по дате */
	private static Comparator<ToDoDocument> comparator = ListComparator.getCompByDate();

	private ListView lvTasks;
	private EditText etFilterTasks;

	private List<ToDoDocument> listDocs;
	private ArrayAdapter<ToDoDocument> arrayAdapter;

	private Intent intentDetail;


	@Override
	public void onCreate(Bundle savedInstanceState) {
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
		super.onStart();
		sort();
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
				Bundle bundle = new Bundle();
				bundle.putInt(AppContext.KEY_TYPE_ACTION, AppContext.VAL_ACTION_NEWTASK);
				intentDetail.putExtras(bundle);
				startActivity(intentDetail);
				return true;
			}

			case R.id.menu_sort_name:
				comparator = ListComparator.getCompByName();
				sort();
				return true;

			case R.id.menu_sort_date:
				comparator = ListComparator.getCompByDate();
				sort();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * Выполнение сортировки по критериям, определенным в компараторе
	 * {@link edu.sintez.tasklist.model.ListComparator}
	 */
	private void sort() {
		Collections.sort(listDocs, comparator);
		updateIndices();

		arrayAdapter = new ArrayAdapter<ToDoDocument>(this, R.layout.pattern_lw_row, listDocs);
		lvTasks.setAdapter(arrayAdapter);
		arrayAdapter.getFilter().filter(etFilterTasks.getText().toString());
	}

	/**
	 * Обновление индексов списка документов.
	 * Нужно для правильной работы алгоритма фильтрации документов.
	 */
	private void updateIndices(){
		ToDoDocument doc;
		for (int i = 0; i < listDocs.size(); i++) {
			doc = listDocs.get(i);
			doc.setNumber(i);
		}
	}

	/**
	 * Включение возможности фильтрации документов только
	 * при при наличии хотябы одного документа в списке
	 */
	private void checkFilterEnable() {
		if (listDocs.size() != 0) {
			etFilterTasks.setEnabled(true);
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

	/**
	 * При нажатии на любой элемент списка заметок, выполняется
	 * метод {@link edu.sintez.tasklist.view.ToDoList.ListViewClickListener
	 * #onItemClick(android.widget.AdapterView, android.view.View, int, long)} .
	 * В данном случае выполняется переход к нажатой заметке - открытие этой заметки в детальной активности.
	 */
	private class ListViewClickListener implements android.widget.AdapterView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			Bundle bundle = new Bundle();
			bundle.putInt(AppContext.KEY_TYPE_ACTION, AppContext.VAL_ACTION_UPDATE);
			bundle.putInt(AppContext.KEY_DOCINDEX, ((ToDoDocument)parent.getAdapter().getItem(position)).getNumber());
			startActivity(intentDetail.putExtras(bundle));
		}
	}

	/**
	 * Выполнение фильтрации при получении символа.
	 */
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
