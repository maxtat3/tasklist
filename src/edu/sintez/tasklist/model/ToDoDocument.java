package edu.sintez.tasklist.model;

import java.io.Serializable;
import java.util.Date;


/**
 * Заметка
 */
public class ToDoDocument implements Serializable {

	private static final long serialVersionUID = -437658343920952013L;
	public static final int DOC_DO_NOT_EXIST = -1;

	private String name;
	private String content;
	private Date createDate;
	private Priority priority = Priority.LOW;

	/**
	 * Внутренний номер заметк.
	 * Используется для идентификации каждой создаваемое заметки.
	 */
	private int number = DOC_DO_NOT_EXIST;


	/**
	 * Заметка
	 */
	public ToDoDocument() {
	}

	/**
	 * Заметка
	 * @param name имя
	 * @param content создержимое
	 * @param createDate дата создания
	 * @param number внутренний номер заметки
	 */
	public ToDoDocument(String name, String content, Date createDate, int number) {
		super();
		this.name = name;
		this.content = content;
		this.createDate = createDate;
		this.number = number;
	}

	/**
	 * Заметка
	 * @param name имя
	 * @param content создержимое
	 * @param createDate дата создания
	 * @param priority приоритет
	 */
	public ToDoDocument(String name, String content, Date createDate, Priority priority) {
		super();
		this.name = name;
		this.content = content;
		this.createDate = createDate;
		this.priority = priority;
	}

	/**
	 * Заметка
	 * @param name имя
	 * @param content содержимое
	 * @param createDate дата создания
	 * @param priority приоритет
	 * @param number внктренний номер заметки
	 */
	public ToDoDocument(String name, String content, Date createDate, Priority priority, int number) {
		this.name = name;
		this.content = content;
		this.createDate = createDate;
		this.priority = priority;
		this.number = number;
	}


	public static long getSerialVersionUID() {
		return serialVersionUID;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return name;
	}

}
