package edu.sintez.tasklist.model;

import java.util.Comparator;

/**
 * Сортировка заметок по: имени, дате, приоритету
 */
public class ListComparator {

	private static ComparatorByName compByName;
	private static ComparatorByDate compByDate;
	private static ComparatorByPriority compByPriority;


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

	public static Comparator<ToDoDocument> getCompByPriority() {
		if (compByPriority == null) {
			compByPriority = new ComparatorByPriority();
		}
		return compByPriority;
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

	private static class ComparatorByPriority implements Comparator<ToDoDocument> {
		@Override
		public int compare(ToDoDocument lhs, ToDoDocument rhs) {
			return lhs.getPriority().compareTo(rhs.getPriority());
		}
	}

}
