package edu.sintez.tasklist.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import edu.sintez.tasklist.R;
import edu.sintez.tasklist.model.ToDoDocument;


/**
 * Создержание конкретной заметки
 */
public class ToDoDetail extends Activity {

	public static final String LOG = ToDoDetail.class.getName();
	public static final int NAME_LEN = 30;
	public static final int RESULT_SAVE = 1;
	public static final int RESULT_DELETE = 2;

	private EditText etContent;

	private ToDoDocument doc;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);

		etContent = (EditText) findViewById(R.id.et);

		doc = (ToDoDocument) getIntent().getSerializableExtra(ToDoList.TO_DO_DOCUMENTS);
		setTitle(doc.getName());
		etContent.setText(doc.getContent());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_todo_details, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.item2_back:
				Log.d(LOG, "back");
				setResult(RESULT_CANCELED);
				finish();
				return true;

			case R.id.item3_save:
				Log.d(LOG, "save");
				saveDocument();
				finish();
				return true;

			case R.id.item4_del:
				Log.d(LOG, "del");
				alertDialogDel();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveDocument(){
		doc.setContent(etContent.getText().toString());
		doc.setName(getDocName());
		setResult(RESULT_SAVE, getIntent());
	}

	private String getDocName(){
		StringBuilder sb = new StringBuilder(etContent.getText());
		if (sb.length() > NAME_LEN){
			sb.delete(NAME_LEN, sb.length()).append("...");
		}
		String text = sb.toString().trim().split("\n")[0];
		return (text.length() > 0) ? text : doc.getName();
	}

	private void alertDialogDel(){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage("подтвержедение удалить ?");

		adb.setPositiveButton("del", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setResult(RESULT_DELETE, getIntent());
				finish();
			}
		});
		adb.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		AlertDialog alertDialog = adb.create();
		alertDialog.show();
	}
}
