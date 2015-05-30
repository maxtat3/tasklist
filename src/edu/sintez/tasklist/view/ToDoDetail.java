package edu.sintez.tasklist.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import edu.sintez.tasklist.R;


/**
 * Создержание конкретной заметки
 */
public class ToDoDetail extends Activity {

	public static final int RESULT_SAVE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);

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
				Toast.makeText(this, "press new back", Toast.LENGTH_SHORT).show();
				break;

			case R.id.item4_save:
				Toast.makeText(this, "press save", Toast.LENGTH_SHORT).show();
				break;
		}
		return super.onOptionsItemSelected(item);
	}
}
