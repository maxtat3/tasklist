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


	public ToDoDocument() {
	}

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


	@Override
	public String toString() {
		return name;
	}

}
