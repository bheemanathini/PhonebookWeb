package com.resources.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.resources.dao.ContactDao;
import com.resources.model.Contact;

@Path("/contacts")
public class ContactsService {

	/*	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getContacts() {
		return "test service";
	}*/

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllContacts(){
		//throw new WebApplicationException(Status.NOT_FOUND);
		ContactDao cDao = new ContactDao();
		List<Contact> contacts;
		try {
			contacts = cDao.getAllContacts();
		} catch (Exception e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		
		Response res = Response.status(Status.OK).type(MediaType.APPLICATION_JSON)
        .entity(contacts).build();
		return res;

	}

	@POST
	@Path("/add")
	@Consumes("application/x-www-form-urlencoded")
	public Response addContact(@FormParam("firstname") String firstName, @FormParam("lastname") String lastName, 
			@FormParam("mobile") String mobile, @FormParam("homephone") String homePhone) {
		Contact c = new Contact();
		c.setFirstName(firstName);
		c.setLastName(lastName);
		c.setHomePhone(homePhone);
		c.setMobilePhone(mobile);
		ContactDao cDao = new ContactDao();
		Response res;
		try {
			cDao.addContact(c);
			res = Response.status(Status.OK).type(MediaType.APPLICATION_JSON)
			        .build();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		
		return res;
		
	}
	
	@Path("{id}")
	@DELETE
	public void deleteContact(@PathParam("id") int id) {
		System.out.println("web service delete called" + id);
		ContactDao cDao = new ContactDao();
		try {
			cDao.deleteContact(id);
		}catch(RuntimeException e) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		catch (Exception e) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		
	}
}
