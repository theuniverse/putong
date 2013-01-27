package com.adweb.putong.core.beans;

import java.util.Date;
import java.util.List;

import com.iiiss.template.ssh.common.core.IBean;

public interface IWeibo extends IBean {

	public String getContent();

	public void setContent(String content);

	public IUser getAuthor();

	public void setAuthor(IUser author);

	public Date getTime();

	public void setTime(Date time);

	public IVideo getVideo();

	public void setVideo(IVideo video);

	public List<IImage> getImages();

	public void setImages(List<IImage> images);

	public boolean isDeleted();

	public void setDeleted(boolean deleted);

	public int getReferences();

	public void setReferences(int references);

	public IWeibo getPredecessor();

	public void setPredecessor(IWeibo predecessor);

	public IWeibo getOrigin();

	public void setOrigin(IWeibo origin);

	public List<IComment> getComments();

	public void setComments(List<IComment> comments);

	public boolean isForwarding();

}