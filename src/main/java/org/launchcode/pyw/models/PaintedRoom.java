package org.launchcode.pyw.models;


import java.util.Date;

//import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.launchcode.pyw.models.User;

//import org.launchcode.pyw.models.AbstractEntity;

//import org.launchcode.pyw.models.User;

@Entity
@Table(name = "paintedrooms")
public class PaintedRoom extends AbstractEntity {

	private String roomname;
	private String imgururl;
	//private int author_uid;
	private User author;
	private Date created;
	private Date modified;
	
	public PaintedRoom() {}
	
	public PaintedRoom(String roomname, String imgururl, User author) {
		
		super();
		
		this.roomname = roomname;
		
		this.imgururl = imgururl;
		this.author = author;
		//this.author_uid = author.getUid();
		this.created = new Date();
		this.updated();
		
		author.addPaintedRoom(this);
	}
	
	
	@NotNull
    @Column(name = "roomname")
	public String getroomname() {
		return roomname;
	}

	public void setroomname(String roomname) {
		this.roomname = roomname;
		this.updated();
	}

	@NotNull
    @Column(name = "imgururl")
	public String getimgururl() {
		return imgururl;
	}

	public void setimgururl(String imgururl) {
		this.imgururl = imgururl;
		this.updated();
	}
	
	
	/*
	@NotNull
    @Column(name = "author_uid")
	public int getauthor_uid() {
		return author_uid;
	}

	public void setauthor_uid(int author_uid) {
		this.author_uid = author_uid;
		this.updated();
	}
	*/
	
	@ManyToOne
	public User getAuthor() {
		return author;
	}
	
	@SuppressWarnings("unused")
	private void setAuthor(User author) {
		this.author = author;
	}
	
	@NotNull
	@OrderColumn
	@Column(name = "created")
	public Date getCreated() {
		return created;
	}
	
	@SuppressWarnings("unused")
	private void setCreated(Date created) {
		this.created = created;
	}
	
	@NotNull
	@Column(name = "modified")
	public Date getModified() {
		return modified;
	}
	
	@SuppressWarnings("unused")
	private void setModified(Date modified) {
		this.modified = modified;
	}
	
	private void updated() {
		this.modified = new Date();
	}
	
}
