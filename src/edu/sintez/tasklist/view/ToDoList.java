package edu.sintez.tasklist.view;

import android.app.Activity;
import android.content.*;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import edu.sintez.tasklist.DeleteDocReceiver;
import edu.sintez.tasklist.R;
import edu.sintez.tasklist.model.*;

import java.io.File;
import java.util.*;


/**
 * Основная активность для отображения заметок
 */
public class ToDoList extends Activity {

	private static final String LOG = ToDoList.class.getName();
	public static final String SHARED_PREFS_DIR = "shared_prefs";

	/* по умолчанию, выполнять сортировку по дате */
	private static Comparator<ToDoDocument> comparator = ListComparator.getCompByDate();

	private ListView lvTasks;
	private EditText etFilterTasks;

	/**
	 * Список всех заметок
	 */
	private List<ToDoDocument> listDocs;

	/**
	 * Адаптер для заполнеия заметок в главной активности
	 */
	private ArrayAdapter<ToDoDocument> arrayAdapter;

	/**
	 * Интент для перехода в активность детального отображения заметки
	 */
	private Intent intentDetail;

	/**
	 * Слушатель для checkbox в каждом элементе списка (в каждой заметке)
	 */
	private DocumentSelectedListener docSelListener = new DocumentSelectedListener();

	/**
	 * Широковещательный приемник для обновления списка заметок
	 */
	private TasksRefreshReceiver tasksRefreshReceiver = new TasksRefreshReceiver();

	/**
	 * Широковещательный приемник для удаления выбранных заметки/заметок
	 */
	private DeleteDocReceiver deleteDocReceiver = new DeleteDocReceiver();

	/**
	 * Кнопка (элемент меню) "Добавлени новой заметки"
	 */
	private MenuItem miAddTask;

	/**
	 * Кнопка (элемент меню) "Сортровка заметок по ..."
	 */
	private MenuItem miSortTasks;

	/**
	 * Кнопка (элемент меню) "Удаление заметки/заметок"
	 */
	private MenuItem miDelTask;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_list);

		listDocs = ((AppContext) getApplicationContext()).getListDocs();

		etFilterTasks = (EditText) findViewById(R.id.et_filter_task);
		etFilterTasks.addTextChangedListener(new FilterTaskChangeListener());

		Button btnClearFilterText = (Button) findViewById(R.id.btn_clear_filter_text);
		btnClearFilterText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				etFilterTasks.setText("");
			}
		});

		lvTasks = (ListView) findViewById(R.id.lw_tasks);
		lvTasks.setOnItemClickListener(new ListViewClickListener());

		lvTasks.setEmptyView(findViewById(R.id.rl_empty_view));

		intentDetail = new Intent(this, ToDoDetail.class);

		fillListTasks();

		LocalBroadcastManager.getInstance(this).registerReceiver(
				tasksRefreshReceiver, new IntentFilter(AppContext.RECEIVER_LV_REFRESH)
		);

		LocalBroadcastManager.getInstance(this).registerReceiver(
				deleteDocReceiver, new IntentFilter(AppContext.RECEIVER_LV_ITEM_DELETE)
		);
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

		miAddTask = menu.findItem(R.id.item1_add_task);
		miSortTasks = menu.findItem(R.id.item_sorting_tasks);
		miDelTask = menu.findItem(R.id.item_del_task);

		turnOnOffControls();

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.item_del_task:
				Log.d(LOG, "item del");
				if (!docsIndicesToRemove.isEmpty()) {
					Intent intent = new Intent(AppContext.RECEIVER_LV_ITEM_DELETE);
					intent.putIntegerArrayListExtra(AppContext.KEY_DOC_INDEXES, docsIndicesToRemove);
					LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
				}
				break;

			case R.id.item1_add_task: {
				Bundle bundle = new Bundle();
				bundle.putInt(AppContext.KEY_TYPE_ACTION, AppContext.VAL_ACTION_NEWTASK);
				intentDetail.putExtras(bundle);
				startActivity(intentDetail);
				return true;
			}

			case R.id.menu_sort_name:
				comparator = ListComparator.getCompByName();
				sort();
				item.setChecked(true);
				return true;

			case R.id.menu_sort_date:
				comparator = ListComparator.getCompByDate();
				sort();
				item.setChecked(true);
				return true;

			case R.id.menu_sort_priority:
				comparator = ListComparator.getCompByPriority();
				sort();
				item.setChecked(true);
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

		arrayAdapter = new ExpandAdapter(this, R.layout.pattern_lw_row, listDocs, docSelListener);
		lvTasks.setAdapter(arrayAdapter);
		arrayAdapter.getFilter().filter(etFilterTasks.getText().toString());

		turnOnOffControls();
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
	 * Восстановление заметок из ФС при старте приложения.
	 * Заметки храняться как xml файлы во внутреннем хранилище приложения.
	 */
	private void fillListTasks() {
		File sharedPrefsDir = new File(getApplicationInfo().dataDir, SHARED_PREFS_DIR);
		if (sharedPrefsDir.exists() && sharedPrefsDir.isDirectory()) {
			String[] list = sharedPrefsDir.list();
			for (int i = 0; i < list.length; i++) {
				SharedPreferences shp = getSharedPreferences(list[i].replace(".xml", ""), MODE_PRIVATE);
				String name = shp.getString(AppContext.KEY_NAME, null);
				String content = shp.getString(AppContext.KEY_CONTENT, null);
				long dateMs = shp.getLong(AppContext.KEY_DATE, 0);
				int priority = shp.getInt(AppContext.KEY_PRIORITY, 0);

				ToDoDocument doc = new ToDoDocument(name, content, new Date(dateMs), Priority.values()[priority]);
				listDocs.add(doc);
			}
		}
	}

	/**
	 * Включение и отключение определенных кнопок в actionbar
	 */
	private void turnOnOffControls() {
		if (miAddTask != null && miSortTasks != null) {
			/* нет заметок */
			if (listDocs.isEmpty()) {
				miAddTask.setEnabled(true);
				miSortTasks.setEnabled(false);
				miDelTask.setEnabled(false);
				/* выделено >= 1 заметки (заметки есть) */
			} else if (!docsIndicesToRemove.isEmpty()) {
				miAddTask.setEnabled(false);
				miSortTasks.setEnabled(false);
				miDelTask.setEnabled(true);
				/* есть заметки */
			} else {
				miAddTask.setEnabled(true);
				miSortTasks.setEnabled(true);
				miDelTask.setEnabled(true);
			}
			checkFilterEnable();
		}
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
			bundle.putInt(AppContext.KEY_DOC_INDEX, ((ToDoDocument)parent.getAdapter().getItem(position)).getNumber());
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

	/**
	 * Коллекция содержащия позиции элементов для удаления
	 */
	private ArrayList<Integer> docsIndicesToRemove = new ArrayList<Integer>();

	/**
	 * Слушатель нажатия на checkbox каждого элемента списка
	 */
	private class DocumentSelectedListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			CheckBox chb = (CheckBox) v;
			ToDoDocument doc = (ToDoDocument) chb.getTag();

//			Log.d(LOG, "checkbox gettag doc.getNumber() = " + doc.getNumber());

			RelativeLayout container = (RelativeLayout) v.getParent();
			TextView tvTaskName = (TextView) container.findViewById(R.id.tv_task_name);
			TextView tvTaskDate = (TextView) container.findViewById(R.id.tv_task_date);

			if (chb.isChecked()) {
				docsIndicesToRemove.add(doc.getNumber());
				tvTaskName.setTypeface(null, Typeface.BOLD_ITALIC);
				tvTaskDate.setTypeface(null, Typeface.BOLD_ITALIC);
			} else {
				docsIndicesToRemove.remove((Integer) doc.getNumber()); //т.к. мы кладем объекты то и стерать мы должны объекты
				tvTaskName.setTypeface(null, Typeface.NORMAL);
				tvTaskDate.setTypeface(null, Typeface.NORMAL);
			}
			Collections.sort(docsIndicesToRemove);

			turnOnOffControls();

			/* for debug */
//			for (ToDoDocument docc : listDocs) {
//				Log.d(LOG, "docc.getNumber() = " + docc.getNumber());
//			}
//			Log.d(LOG, "---");
//
//			for (Integer num : docsIndicesToRemove) {
//				Log.d(LOG, "toDoDocument number = " + num);
//			}
//			Log.d(LOG, "===");
		}

	}

	/**
	 * Широковещательный приемник для обновления списка заметок
	 */
	private class TasksRefreshReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			docsIndicesToRemove.clear();
			sort();
		}

	}
}
