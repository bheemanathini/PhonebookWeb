package com.resources.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.resources.dao.ContactDao;
import com.resources.dao.GroupDao;
import com.resources.model.Group;
import com.resources.model.GroupInfo;

@Path("/groups")
public class GroupsService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Group> getAllGroups(){
		GroupDao gDao = new GroupDao();
		List<Group> allGroups = null;
		try {
			allGroups = gDao.getAllGroups();
		} catch(SQLException e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		catch (Exception  e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		return allGroups;
	}
	@Path("/addGroup")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addGroup(Group group) {
		GroupDao gDao = new GroupDao();
		try {
			gDao.addGroup(group);
		} catch (Exception e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		System.out.println("add group service"+group.getName());
	}

	@Path("{id}")
	@DELETE
	public void deleteGroup(@PathParam("id") int id) {
		System.out.println("web service delete group called" + id);
		GroupDao gDao = new GroupDao();
		try {
			gDao.deleteGroup(id);
		} catch (Exception e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}

	}

	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public GroupInfo getGroupInfo(@PathParam("id") int id) {
		System.out.println("web service info group called" + id);
		GroupDao gDao = new GroupDao();
		GroupInfo groupInfo = null;
		try {
			groupInfo = gDao.getGroupInfo(id);
		} catch (Exception e) {
			throw new WebApplicationException(Status.NOT_FOUND);
			
		}
		return groupInfo;

	}

	@Path("/{id}/setMembers")
	@GET
	public void setMembers(@PathParam("id") int gid, @QueryParam("cid") String cidListString
			) {
		System.out.println("cccc");
		System.out.println("cidListString"+cidListString);
		GroupDao gDao = new GroupDao();
		List<Integer> cidList = new ArrayList<Integer>();
		if(!cidListString.isEmpty()) {
			
		for(String s : cidListString.split(",")) {
			cidList.add(Integer.parseInt(s));
		}
		}
		

		try {
			gDao.setMembers(gid, cidList);
		} catch (Exception e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}



}
