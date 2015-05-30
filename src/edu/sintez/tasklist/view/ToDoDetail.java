package edu.sintez.tasklist.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import edu.sintez.tasklist.R;


/**
 * Создержание конкретной заметки
 */
public class ToDoDetail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);
	}

	@Override
	public boolean onCreatePanelMenu(int featureId, Menu menu) {
		return super.onCreatePanelMenu(featureId, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
}
