package org.blaze.javamaster.messenger2.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.ws.rs.core.Response;

import org.blaze.javamaster.messenger2.model.Message;

public class DatabaseManager {

	static final String DBdriver = "com.mysql.jdbc.Driver"; //it is a driver class which is used to connect to the database
	static final String DBurl = "jdbc:mysql://localhost:3306/messenger"; //my database url
	static final String DBuser = "root"; // datebase username
	static final String DBpass = ""; //database password
	
	static {
		try
		{
			Class.forName(DBdriver); //used to load database driver to the jvm
		}
		catch(ClassNotFoundException e)
		{
			System.exit(0);
		}
	}
	// now create the methods to create new data in our database
	
	public static Response newMessage(String message, String created, String author)
	{
		HashMap<String,String> messageResponse = new HashMap<String,String>();
		
		try {
			Connection con = DriverManager.getConnection(DBurl,DBuser,DBpass);
			String sql = "insert into messsage (message,created,author) VALUES (?,?,?)";
			
			
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, message);
			ps.setString(2, created);
			ps.setString(3, author);
			
			int row = ps.executeUpdate();
			if (row == 0) {
				   return Response.status(404).entity(messageResponse.put("Creation","Failed")).build();
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return  Response.status(200).entity(messageResponse.put("Creation","Successful")).build();
	}
	
	
	public static ArrayList<Message> getAllMessages()
	{
		ArrayList<Message> list = new ArrayList<Message> ();
		
		try
		{
			Connection con = DriverManager.getConnection(DBurl,DBuser,DBpass);
			String sql = "select * from messsage";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				Message message = new Message();
				message.setId(rs.getInt(1));
				message.setMessage(rs.getString(2));
				message.setCreated(rs.getString(3));
				message.setAuthor(rs.getString(4));
				list.add(message);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static Message getMessage(long id)
	{
		Message m = new Message();
		try {
			Connection con = DriverManager.getConnection(DBurl,DBuser,DBpass);
			String sql = "select * from messsage where messageid =" + id;
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next())
			{
				m.setId(rs.getInt(1));
				m.setMessage(rs.getString(2));
				m.setCreated(rs.getString(3));
				m.setAuthor(rs.getString(4));
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return m;
	}
	
	public static int removeMessage(long id)
	{
		int i =0;
		try {
			Connection con = DriverManager.getConnection(DBurl,DBuser,DBpass);
			String sql = "delete from messsage where messageid ='" + id+"'";
			PreparedStatement ps = con.prepareStatement(sql);
			i = ps.executeUpdate();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return i;
	}
	
	public static Response updateMessage(long id,String message, String created, String author)
	{
		HashMap<String,String> statusMessage = new HashMap<String,String>();
		try {
			Connection con = DriverManager.getConnection(DBurl,DBuser,DBpass);
			String sql = "update messsage set message ='" + message+"'"+",created = '"+created+"'"+", author = '"+author+"'"+ " where messageid = '" +id+"'";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = ps.executeUpdate();
			if (i == 0) {
				   return Response.status(405).entity(statusMessage.put("Update", "Failed")).build();
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return Response.status(200).entity(statusMessage.put("Update", "Success")).build();
	}
	
	public static ArrayList<Message> getByDate(String date)
	{
		ArrayList<Message> list = new ArrayList<Message>();
		Message m = new Message();
		try {
			Connection con = DriverManager.getConnection(DBurl,DBuser,DBpass);
			String sql = "select * from messsage where created = '" + date +"'";
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs;
			rs = ps.executeQuery();
			
			while(rs.next())
			{
				m.setId(rs.getInt(1));
				m.setMessage(rs.getString(2));
				m.setCreated(rs.getString(3));
				m.setAuthor(rs.getString(4));
				list.add(m);
			}
		}catch(SQLException e)
		{
			e.printStackTrace();
		}
		return list;
	}
}
