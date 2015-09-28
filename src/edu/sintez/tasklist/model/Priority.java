package edu.sintez.tasklist.model;

/**
 * Возможные варианты приоритетов для заметки
 */
public enum Priority {

	LOW(0),
	NORMAL(1),
	HIGH(2);


	private int index;


	Priority(int index) {
		this.index = index;
	}


	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
