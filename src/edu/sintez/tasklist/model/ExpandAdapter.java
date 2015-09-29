package edu.sintez.tasklist.model;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import edu.sintez.tasklist.R;
import edu.sintez.tasklist.view.ToDoList;

import java.util.List;


public class ExpandAdapter extends ArrayAdapter<ToDoDocument> {

	private static final String LOG = ExpandAdapter.class.getName();
	private View.OnClickListener docSelListener;


	public ExpandAdapter(Context context, int textViewResourceId, List<ToDoDocument> listDocs, View.OnClickListener docSelListener) {
		super(context, textViewResourceId, listDocs);
		this.docSelListener = docSelListener;
	}

	public ExpandAdapter(Context context, int textViewResourceId, ToDoDocument[] objects) {
		super(context, textViewResourceId, objects);
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.pattern_lw_row, parent, false);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.name = (TextView) convertView.findViewById(R.id.tv_task_name);
			viewHolder.date = (TextView) convertView.findViewById(R.id.tv_task_date);
			viewHolder.imgPriority = (ImageView) convertView.findViewById(R.id.image_priority_task);
			viewHolder.chbItemSel = (CheckBox) convertView.findViewById(R.id.chbox_item_sel);

			viewHolder.chbItemSel.setOnClickListener(docSelListener);

			convertView.setTag(viewHolder);
		}

		ViewHolder viewHolder = (ViewHolder) convertView.getTag();
		ToDoDocument doc = getItem(position);
		viewHolder.name.setText(doc.getName());
		viewHolder.date.setText(DateFormat.format("dd MMMM, yyyy,  hh:mm", doc.getCreateDate()));
		switch (doc.getPriority()) {
			case LOW:
				viewHolder.imgPriority.setImageResource(R.mipmap.ic_priority_low);
				break;
			case NORMAL:
				viewHolder.imgPriority.setImageResource(R.mipmap.ic_priority_normal);
				break;
			case HIGH:
				viewHolder.imgPriority.setImageResource(R.mipmap.ic_priority_high);
				break;
		}

		viewHolder.chbItemSel.setTag(doc);

		return convertView;
	}

	public static class ViewHolder{
		public TextView name;
		public TextView date;
		public ImageView imgPriority;
		public CheckBox chbItemSel;
	}
}
