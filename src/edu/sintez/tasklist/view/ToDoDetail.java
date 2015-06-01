package edu.sintez.tasklist.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import edu.sintez.tasklist.R;
import edu.sintez.tasklist.model.ToDoDocument;


/**
 * Создержание конкретной заметки
 */
public class ToDoDetail extends Activity {

	public static final String LOG = ToDoDetail.class.getName();
	public static final int RESULT_SAVE = 1;
	public static final int RESULT_DELETE = 2;
	public static final int NAME_LEN = 30;

	private EditText etContent;

	private ToDoDocument doc;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);

		etContent = (EditText) findViewById(R.id.et);

		doc = (ToDoDocument) getIntent().getSerializableExtra(ToDoList.TO_DO_DOCUMENTS);
		setTitle(doc.getName());
		etContent.setText(doc.getName());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_todo_list, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.item3_back:
				setResult(RESULT_CANCELED);
				Log.d(LOG, "back");
				finish();
				break;

			case R.id.item4_save:
				setResult(RESULT_SAVE, getIntent());
				Log.d(LOG, "save");
				finish();
				break;
			case R.id.item5_del:
				setResult(RESULT_DELETE, getIntent());
				finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private String getDocName(){
		StringBuilder sb = new StringBuilder(etContent.getText());
		if (sb.length() > NAME_LEN){
			sb.delete(NAME_LEN, sb.length()).append("...");
		}
		String text = sb.toString().trim().split("\n")[0];
		return (text.length() > 0) ? text : doc.getName();
	}
}
