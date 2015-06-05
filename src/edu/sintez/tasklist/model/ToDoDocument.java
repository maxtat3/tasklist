package edu.sintez.tasklist.model;

import java.io.Serializable;
import java.util.Date;


/**
 * Заметка
 */
public class ToDoDocument implements Serializable, Comparable<ToDoDocument> {

	private static final long serialVersionUID = -437658343920952013L;
	public static final int DOC_DO_NOT_EXIST = -1;

	private String name;
	private String content;
	private Date createDate;

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
	 */
	public ToDoDocument(String name, String content, Date createDate) {
		super();
		this.name = name;
		this.content = content;
		this.createDate = createDate;
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


	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(ToDoDocument anotherDoc) {
		return anotherDoc.getCreateDate().compareTo(createDate);
	}

//	@Override
//	public boolean equals(Object obj) {
//		if (!(obj instanceof ToDoDocument)){
//			return false;
//		}
//		ToDoDocument doc = (ToDoDocument) obj;
//		return doc.getNumber() == this.number;
//	}


}
