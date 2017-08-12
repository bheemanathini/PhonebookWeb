package com.resources.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.resources.model.Contact;

public class ContactDao {

	public List<Contact> getAllContacts() throws Exception{
		List<Contact> contacts = new ArrayList<Contact>();
		Database db = new Database();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM contact");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Contact c = new Contact();
				c.setFirstName(rs.getString("firstname"));
				c.setLastName(rs.getString("lastname"));
				c.setId(rs.getInt("id"));
				c.setHomePhone(rs.getString("homephone"));
				c.setMobilePhone(rs.getString("mobile"));
				contacts.add(c);
			}

		} catch (Exception e) {
			//e.printStackTrace();
			throw e;
		}finally {
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return contacts;
	}

	public void addContact(Contact c) throws Exception {
		Database db = new Database();
		Connection con = null;
		try {
			con = db.getConnection();
			String insert = "INSERT INTO contact"
					+"(firstname, lastname, mobile, homephone) VALUES"
					+"(?,?,?,?)";
			PreparedStatement ps = con.prepareStatement(insert);
			ps.setString(1, c.getFirstName());
			ps.setString(2, c.getLastName());
			ps.setString(3, c.getHomePhone());
			ps.setString(4, c.getMobilePhone());
			ps.executeUpdate();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw e;
		}finally {
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}


	}

	public void deleteContact(int id) throws Exception {
		Database db = new Database();
		Connection con = null;
		try {
			con = db.getConnection();
			con.setAutoCommit(false);
			String insert = "DELETE FROM contact"
					+" WHERE id= ?";

			String delete = "DELETE FROM groupcontact"
					+" WHERE cid= ?";


			PreparedStatement ps = con.prepareStatement(delete);
			ps.setInt(1, id);
			ps.executeUpdate();

			ps = con.prepareStatement(insert);
			ps.setInt(1, id);
			int executeUpdate = ps.executeUpdate();
			if(executeUpdate == 0)
				throw new RuntimeException();
			con.commit();


		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw e;
		}finally {
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}
}
