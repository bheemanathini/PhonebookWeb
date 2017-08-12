package com.resources.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mysql.jdbc.Connection;
import com.resources.model.Contact;
import com.resources.model.Group;
import com.resources.model.GroupInfo;

public class GroupDao {

	public List<Group> getAllGroups() throws Exception{
		List<Group> groups = new ArrayList<Group>();
		Database db = new Database();
		Connection con = null;
		try {
			con = db.getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT * FROM phonegroup");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Group g = new Group();
				g.setName(rs.getString("name"));
				g.setId(rs.getInt("id"));

				groups.add(g);
			}

		} catch (Exception e) {
			/*// TODO Auto-generated catch block
			e.printStackTrace();*/
			throw e;
		}finally {
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					throw e;
				}
		}
		return groups;
	}

	public void addGroup(Group g) throws Exception {
		Database db = new Database();
		Connection con = null;
		try {
			con = db.getConnection();
			String insert = "INSERT INTO phonegroup"
					+"(name) VALUES"
					+"(?)";
			PreparedStatement ps = con.prepareStatement(insert);
			ps.setString(1, g.getName());
			ps.executeUpdate();


		} catch (Exception e) {
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

	public void deleteGroup(int id) throws Exception {
		Database db = new Database();
		Connection con = null;
		try {
			con = db.getConnection();
			con.setAutoCommit(false);
			String insert = "DELETE FROM phonegroup"
					+" WHERE id= ?";

			String delete = "DELETE FROM groupcontact"
					+" WHERE gid= ?";
			
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
			throw e;
		}finally {
			if(con != null)
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}

	}


	public GroupInfo getGroupInfo(int id) throws Exception {
		Database db = new Database();
		Connection con = null;
		try {
			con = db.getConnection();
			return executeGetGroupInfo(id, con);
		} catch (Exception e) {
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

	private GroupInfo executeGetGroupInfo(int id, Connection con) throws SQLException {
		String insert = "SELECT g.id, g.name, c.id, c.firstname, c.lastname"
				+ " FROM phonegroup as g, contact as c, groupcontact as gc"
				+" WHERE g.id= ? and g.id=gc.gid and gc.cid=c.id";

		PreparedStatement ps = con.prepareStatement(insert);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		String groupName = null;
		List<Contact> members = new ArrayList<Contact>();
		while(rs.next()) {

			groupName = rs.getString("g.name");
			Contact c = new Contact();
			c.setFirstName(rs.getString("c.firstname"));
			c.setLastName(rs.getString("c.lastname"));
			c.setId(rs.getInt("c.id"));
			members.add(c);
		}

		GroupInfo g = new GroupInfo();
		g.setGroupName(groupName);
		g.setId(id);
		g.setMembers(members);
		return g;
	}

	public void setMembers(int gid, List<Integer> cidList) throws Exception {

		Database db = new Database();
		Connection con = null;
		try {
			con = db.getConnection();
			con.setAutoCommit(false);
			GroupInfo g = executeGetGroupInfo(gid, con);
			Set<Integer> existingMembers = new HashSet<Integer>();

			for(Contact c : g.getMembers()) {
				existingMembers.add(c.getId());
			}

			// Generate a list of members to add.
			Set<Integer> toAdd = new HashSet<Integer>(cidList);
			toAdd.removeAll(existingMembers);

			// Generate a list of members to delete
			Set<Integer> toDelete = new HashSet<Integer>(existingMembers);
			toDelete.removeAll(cidList);
			addMembers(con, gid, toAdd);
			deleteMembers(con, gid, toDelete);
			con.commit();




		} catch (Exception e) {
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

	private void deleteMembers(Connection con, int gid, Set<Integer> toDelete) throws SQLException {
		String insert = "DELETE FROM groupcontact"
				+" WHERE cid= ? and gid=?" ;
		PreparedStatement ps = con.prepareStatement(insert);

		for(int cid: toDelete) {
			ps.setInt(1, cid);
			ps.setInt(2, gid);
			ps.addBatch();
		}
		ps.executeBatch();


	}

	private void addMembers(Connection con, int gid, Set<Integer> toAdd) throws SQLException {
		// TODO Auto-generated method stub
		String insert = "INSERT INTO groupcontact"
				+"(cid, gid) VALUES"
				+"(?, ?)";
		PreparedStatement ps = con.prepareStatement(insert);

		for(int cid: toAdd) {
			ps.setInt(1, cid);
			ps.setInt(2, gid);
			ps.addBatch();
		}
		ps.executeBatch();


	}
}
