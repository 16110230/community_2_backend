package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "activity_details")
public class ActivityDetails extends BaseEntity	{
	
	private static final long serialVersionUID = -5196455701225322056L;
	
	@ManyToOne
	@JoinColumn(name = "activity_id")
	private Activity activity;
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private Users user;
	
	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
