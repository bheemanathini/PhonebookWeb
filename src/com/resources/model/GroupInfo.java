package com.resources.model;

import java.util.List;

import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GroupInfo {
	
	@Id
	private int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public List<Contact> getMembers() {
		return members;
	}
	public void setMembers(List<Contact> members) {
		this.members = members;
	}
	private String groupName;
	private List<Contact> members;
	
	public GroupInfo() {
		
	}

}
