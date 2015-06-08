package edu.sintez.tasklist.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import edu.sintez.tasklist.R;
import edu.sintez.tasklist.model.AppContext;
import edu.sintez.tasklist.model.ToDoDocument;

import java.util.List;


/**
 * Создержание конкретной заметки
 */
public class ToDoDetail extends Activity {

	public static final String LOG = ToDoDetail.class.getName();
	public static final int NAME_LEN = 30;
	public static final int RESULT_SAVE = 1;
	public static final int RESULT_DELETE = 2;
	public static final String MSG_DEL_THIS_DOC = "Вы действительно хотите удалить эту заметку ?";
	public static final String MSG_DOC_IS_CHANGE_CONFIRM_SAVE = "Заметка была изменена, сохранить ?";
	public static final String YES = "Да";
	public static final String CANCEL = "Отмена";
	public static final String NO = "Нет";

	private List<ToDoDocument> listDocs;

	private int typeAction;
	private int docIndex;

	private EditText etContent;

	private ToDoDocument doc;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo_detail);

		etContent = (EditText) findViewById(R.id.et);

		listDocs = ((AppContext) getApplicationContext()).getListDocs();
		for (ToDoDocument listDoc : listDocs) {
			Log.d(LOG, "doc name = " + listDoc.getName());
		}

//		doc = (ToDoDocument) getIntent().getSerializableExtra(ToDoList.TO_DO_DOCUMENTS);
//		setTitle(doc.getName());
//		etContent.setText(doc.getContent());
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
				Log.d(LOG, "is change ? " + isChangeDoc());
				if (isChangeDoc()) {
					dialogConfirmSave();
				} else {
					setResult(RESULT_CANCELED, getIntent());
					finish();
				}
				return true;

			case R.id.item3_save:
				Log.d(LOG, "save");
				saveDocument();
				finish();
				return true;

			case R.id.item4_del:
				Log.d(LOG, "del");
				dialogConfirmDel();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void saveDocument(){
		if (isChangeDoc()) {
			doc.setContent(etContent.getText().toString());
			doc.setName(getDocName());
			setResult(RESULT_SAVE, getIntent());
		} else {
			setResult(RESULT_CANCELED, getIntent());
			Toast.makeText(this, "Документ " + doc.getName() + " не был изменен.", Toast.LENGTH_SHORT).show();
		}
	}

	private boolean isChangeDoc() {
		if (doc.getContent() != null && etContent.getText().toString().equals(doc.getContent())) {
			return false;
		} else {
			return true;
		}
	}

	private String getDocName(){
		StringBuilder sb = new StringBuilder(etContent.getText());
		if (sb.length() > NAME_LEN){
			sb.delete(NAME_LEN, sb.length()).append("...");
		}
		String text = sb.toString().trim().split("\n")[0];
		return (text.length() > 0) ? text : doc.getName();
	}

	private void dialogConfirmDel(){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(MSG_DEL_THIS_DOC);

		adb.setPositiveButton(YES, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setResult(RESULT_DELETE, getIntent());
				finish();
			}
		});
		adb.setNegativeButton(CANCEL, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		AlertDialog alertDialog = adb.create();
		alertDialog.show();

	}

	private void dialogConfirmSave(){
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		adb.setMessage(MSG_DOC_IS_CHANGE_CONFIRM_SAVE);

		adb.setPositiveButton(YES, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveDocument();
				setResult(RESULT_SAVE, getIntent());
				finish();
			}
		});
		adb.setNegativeButton(NO, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				setResult(RESULT_CANCELED, getIntent());
				finish();
			}
		});

		AlertDialog alertDialog = adb.create();
		alertDialog.show();
	}

}
