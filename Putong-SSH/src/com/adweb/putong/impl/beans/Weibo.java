package com.adweb.putong.impl.beans;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.adweb.putong.core.beans.IComment;
import com.adweb.putong.core.beans.IImage;
import com.adweb.putong.core.beans.IUser;
import com.adweb.putong.core.beans.IVideo;
import com.adweb.putong.core.beans.IWeibo;
import com.iiiss.template.ssh.common.impl.BaseBean;

@Entity
public class Weibo extends BaseBean implements IWeibo {
	@Basic
	private String content;

	@OneToOne(targetEntity = User.class)
	private IUser author;

	@Temporal(TemporalType.TIMESTAMP)
	private Calendar time;

	@OneToOne(targetEntity = Video.class, cascade = { CascadeType.ALL })
	private IVideo video;

	@OneToMany(targetEntity = Image.class, cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<IImage> images = new ArrayList<IImage>();

	@Basic
	private boolean deleted;

	@Basic
	private int references;

	@ManyToOne(targetEntity = Weibo.class)
	private IWeibo predecessor;

	@ManyToOne(targetEntity = Weibo.class)
	private IWeibo origin;

	@OneToMany(targetEntity = Comment.class, mappedBy = "weibo", cascade = { CascadeType.ALL })
	private List<IComment> comments = new ArrayList<IComment>();

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public IUser getAuthor() {
		return author;
	}

	public void setAuthor(IUser author) {
		this.author = author;
	}

	public Date getTime() {
		if (time != null)
			return time.getTime();
		else
			return null;
	}

	public void setTime(Date time) {
		this.time = Calendar.getInstance();
		this.time.setTime(time);
	}

	public IVideo getVideo() {
		return video;
	}

	public void setVideo(IVideo video) {
		this.video = video;
	}

	public List<IImage> getImages() {
		return images;
	}

	public void setImages(List<IImage> images) {
		this.images = images;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getReferences() {
		return references;
	}

	public void setReferences(int references) {
		this.references = references;
	}

	public IWeibo getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(IWeibo predecessor) {
		this.predecessor = predecessor;
	}

	public IWeibo getOrigin() {
		return origin;
	}

	public void setOrigin(IWeibo origin) {
		this.origin = origin;
	}

	public List<IComment> getComments() {
		return comments;
	}

	public void setComments(List<IComment> comments) {
		this.comments = comments;
	}

	public boolean isForwarding() {
		return origin != null;
	}
}