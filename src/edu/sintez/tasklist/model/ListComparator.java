package edu.sintez.tasklist.model;

import java.util.Comparator;

/**
 * Created by max on 09.06.15.
 */
public class ListComparator {

	private static ComparatorByName compByName;
	private static ComparatorByDate compByDate;


	public static Comparator<ToDoDocument> getCompByName() {
		if (compByName == null){
			compByName = new ComparatorByName();
		}
		return compByName;
	}

	public static Comparator<ToDoDocument> getCompByDate() {
		if (compByDate == null) {
			compByDate = new ComparatorByDate();
		}
		return compByDate;
	}


	private static class ComparatorByName implements Comparator<ToDoDocument>{
		@Override
		public int compare(ToDoDocument lhs, ToDoDocument rhs) {
			return lhs.getName().compareTo(rhs.getName());
		}
	}

	private static class ComparatorByDate implements Comparator<ToDoDocument>{

		@Override
		public int compare(ToDoDocument lhs, ToDoDocument rhs) {
			return lhs.getCreateDate().compareTo(rhs.getCreateDate());
		}

	}

}