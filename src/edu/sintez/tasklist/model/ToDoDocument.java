package edu.sintez.tasklist.model;

import java.io.Serializable;
import java.util.Date;


/**
 * Заметка
 */
public class ToDoDocument implements Serializable {

	private static final long serialVersionUID = -437658343920952013L;

	private String name;
	private String content;
	private Date createDate;

	/**
	 * Внутренний номер заметк.
	 * Используется для идентификации каждой создаваемое заметки.
	 */
	private int number = -1;


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
	public boolean equals(Object obj) {
		if (!(obj instanceof ToDoDocument)){
			return false;
		}
		ToDoDocument doc = (ToDoDocument) obj;
		return doc.getNumber() == this.number;
	}
}
