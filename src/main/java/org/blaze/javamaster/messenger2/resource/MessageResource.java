package org.blaze.javamaster.messenger2.resource;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import java.util.ArrayList;

import org.blaze.javamaster.messenger2.database.DatabaseManager;
import org.blaze.javamaster.messenger2.model.Message;


@Path("/messages")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageResource {

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) //this provides information that all parameters will be needed
	public Response newMessage(@FormParam("message") String message, @FormParam("created") String created, @FormParam("author") String author) throws SQLException
	{
		if(message == null & created == null & author == null)
		{
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
		
		return DatabaseManager.newMessage(message, created, author);
	}
	
	@PUT
	@Path("/update/{messageid}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) //this provides information that all parameters will be needed
	public Response newMessage(@PathParam("messageid") Integer id,@FormParam("message") String message, @FormParam("created") String created, @FormParam("author") String author) throws SQLException
	{
		if(id == null  & message == null & created == null)
		{
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
		return DatabaseManager.updateMessage(id, message, created, author);
	}
	
	@GET
	public ArrayList<Message> getAllMessages()
	{
		ArrayList<Message> list = new ArrayList<Message>();
		list = DatabaseManager.getAllMessages();
		return list;
	}
	
	@GET
	@Path("/{messageid}")
	public Message getMessage(@PathParam("messageid") Integer id)
	{
		if(id == null)
		{
			Response.status(Response.Status.NOT_ACCEPTABLE).build();
			return null;
		}
		return DatabaseManager.getMessage(id);
	}
	
	@DELETE
	@Path("/{messageid}")
	public static Response deleteMessage(@PathParam("messageid") Integer id)
	{
		if(id == null)
		{
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		return Response.status(Response.Status.OK).build();
	}
	
	@GET
	@Path("/byDate")
	public ArrayList<Message> getMessagesByDate(@QueryParam("created") String date)
	{
		ArrayList<Message> newMessage = new ArrayList<Message>();
		newMessage = DatabaseManager.getByDate(date);
		return newMessage;
	}

}
