package database;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.Vector;

import javax.servlet.Servlet;

import model.InternshipObject;
import model.ProfileObject;

public class Database {
	private static Connection conn;
	private static String connStr = "jdbc:mysql://google/InternNET?cloudSqlInstance=balodics201:us-central1:internet&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=root"
			+ "&password=";

	// Result codes
	public static final int SUCCESS = 0;
	public static final int SERVER_ERROR = 1;
	// Register & login
	public static final int NO_USER_EXIST = 2;
	public static final int INCORRECT_PASSWORD = 3;
	public static final int INVALID_USERNAME = 4;
	public static final int INVALID_PASSWORD = 5;
	public static final int USER_ALREADY_EXISTS = 6;
	public static final int NOT_MATCHING_PASSWORD = 7;
	public static final int INVALID_FNAME = 8;
	public static final int INVALID_LNAME = 9;
	public static final int INVALID_LOCATION = 10;

	public static int connect() {
		try {
			System.out.print("Trying to connect to database...");
			// Connect to DB
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(connStr);
			System.out.println("Connected!");
			return SUCCESS;
		} catch (Exception e) {
			System.out.println("Unabled to connect to database " + connStr + ".");
			e.printStackTrace();
			return SERVER_ERROR;
		}
	}

	public static void close() {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static int GetUserID(String username) {
		try {
	    int userID;
		String sql = "SELECT userID FROM users WHERE username = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		if(!rs.next()) {
			return -1; //returns null string if userID doesn't exist from username
		} 
		else{
			userID  = rs.getInt("userID");
		}
		
		return userID;
		
		}catch(SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static int login(String username, String password) {
		if (conn == null) {
			return SERVER_ERROR;
		}

		try {
			// Check if user exists
			String sql = "SELECT username FROM users WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (!rs.next()) {
				return NO_USER_EXIST;
			}

			sql = "SELECT username FROM users WHERE username = ? AND password = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, password);
			rs = ps.executeQuery();
			if (!rs.next()) {
				return INCORRECT_PASSWORD;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return SERVER_ERROR;
		}

		return SUCCESS;
	}

	public static int register(String username, String password, String confirm, String firstname, String lastname,
			String location, String imageLink) {
		// TODO Auto-generated method stub
		if (conn == null) {
			return SERVER_ERROR;
		}

		try {
			if (firstname == null || firstname.equals(""))
				return INVALID_FNAME;
			if (lastname == null || lastname.equals(""))
				return INVALID_LNAME;
			if (location == null || location.equals(""))
				return INVALID_LOCATION;
			if (username == null || username.equals(""))
				return INVALID_USERNAME;
			if (password == null || password.equals(""))
				return INVALID_PASSWORD;

			// Check if user exists
			String sql = "SELECT username FROM users WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return USER_ALREADY_EXISTS;
			}
			// Check for password
			if (!password.equals(confirm))
				return NOT_MATCHING_PASSWORD;

			// Insert into db
			sql = "INSERT INTO users(username, password) VALUES (?, ?)";
			ps = conn.prepareStatement(sql, new String[] { "userID" });
			ps.setString(1, username);
			ps.setString(2, password);
			ps.executeUpdate();
			// Get inserted userID
			rs = ps.getGeneratedKeys();
			int key = -1;
			if (rs.next()) {
				key = rs.getInt(1);
			}

			// Create new profile that references current userID
			sql = "INSERT INTO profile(userID, imageLink, firstName, lastName, location) VALUES (?, ?, ?, ?, ?)";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, key);
			ps.setString(2, imageLink);
			ps.setString(3, firstname);
			ps.setString(4, lastname);
			ps.setString(5, location);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return SERVER_ERROR;
		}
		return SUCCESS;
	}

	public static ProfileObject GetProfile(String userId) {
		if (conn == null) {
			return null;
		}
		try {
			int userID = 0;
			String sql = "SELECT userID FROM users WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				return null; // returns null string if userID doesn't exist from username
			} else {
				userID = rs.getInt("userID");
				sql = "SELECT * FROM profile WHERE userID = ?";
				ps.clearParameters();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, userID);
				rs = ps.executeQuery();
			}

			// get data information corresponding to the input username and return String
			// array
			ProfileObject user = new ProfileObject();
			while (rs.next()) {
				System.out.println(rs.getString("imageLink"));
				user.setUserID(rs.getInt("userID"));
				user.setImageLink(rs.getString("imageLink"));
				user.setFirstName(rs.getString("firstName"));
				user.setLastName(rs.getString("lastName"));
				user.setLocation(rs.getString("location"));
			}
			return user;
		}
		catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
	
	public static int setProfile(String username, String imageLink, String firstName, String lastName, String location){
		if(conn == null) {
			return SERVER_ERROR;
		}
		try {
			int userID;
			String sql = "SELECT userID FROM users WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			if (!rs.next()) {
				return NO_USER_EXIST; // returns null string if userID doesn't exist from username
			} else {
				userID = rs.getInt("userID");
			}

			// update respective category in profile. attribute being null or empty means no
			// change required
			if (imageLink != null || imageLink.trim() != "") {
				sql = "UPDATE profile SET imageLink = ? WHERE userID = ?";
				ps.clearParameters();
				ps = conn.prepareStatement(sql);
				ps.setString(1, imageLink);
				ps.setInt(2, userID);
				ps.executeUpdate();
			}
			if (firstName != null || firstName.trim() != "") {
				sql = "UPDATE profile SET firstName = ? WHERE userID = ?";
				ps.clearParameters();
				ps = conn.prepareStatement(sql);
				ps.setString(1, firstName);
				ps.setInt(2, userID);
				ps.executeUpdate();
			}
			if (lastName != null || lastName.trim() != "") {
				sql = "UPDATE profile SET lastName = ? WHERE userID = ?";
				ps.clearParameters();
				ps = conn.prepareStatement(sql);
				ps.setString(1, lastName);
				ps.setInt(2, userID);
				ps.executeUpdate();
			}
			if (location != null || location.trim() != "") {
				sql = "UPDATE profile SET location = ? WHERE userID = ?";
				ps.clearParameters();
				ps = conn.prepareStatement(sql);
				ps.setString(1, location);
				ps.setInt(2, userID);
				ps.executeUpdate();
			}
			return SUCCESS;
		} catch (SQLException e) {
			e.printStackTrace();
			return SERVER_ERROR;
		}
	}

	public static ArrayList<ProfileObject> getProfilesByName(String query) {
		ArrayList<ProfileObject> profiles = new ArrayList<>();
		if (conn == null) {
			return profiles;
		}
		try {
			// Split query into fname and lname
			String[] queryArr = query.split("\\s+"); // split by any number of consecutive space
			String fname = queryArr[0].trim();
			String lname = queryArr.length > 1 ? queryArr[1].trim() : "";

			// Search query
			String sql = "SELECT * FROM profile WHERE firstName = ? OR firstName = ? OR lastName = ? OR lastName = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, fname);
			ps.setString(4, lname);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				System.out.println(rs.getString("userID"));
				profiles.add(new ProfileObject(rs.getInt("userID"), rs.getString("firstName"), rs.getString("lastName"),
						rs.getString("imageLink"), rs.getString("location")));
			}
			return profiles;
		} catch (SQLException e) {
			e.printStackTrace();
			return profiles;
		}
	}

	public static ArrayList<ProfileObject> getProfilesOfFriends(String root) {
		ArrayList<ProfileObject> profiles = new ArrayList<>();
		if (conn == null) {
			return profiles;
		}
		try {
			int userID = 0;
			String sql = "SELECT * FROM users WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, root);
			ResultSet rs = ps.executeQuery();
			if(rs.next()){
				userID = rs.getInt("userID");
			}

			sql = "SELECT * FROM friends WHERE friend1 = ? OR friend2 = ?";
			ps.clearParameters();
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userID);
			ps.setInt(2, userID);
			rs = ps.executeQuery();

			Vector<Integer> friendIDs = new Vector<>();
			while(rs.next()){
				int ID1 = rs.getInt("friend1");
				int ID2 = rs.getInt("friend2");
				if(ID1 != userID){
					friendIDs.add(ID1);
				}
				if(ID2 != userID){
					friendIDs.add(ID2);
				}
			}

			for(int i=0; i<friendIDs.size(); i++){
				int addID = friendIDs.get(i);
				String addUsername = "";
				sql = "SELECT * FROM users WHERE userID = ?";
				ps.clearParameters();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, addID);
				rs = ps.executeQuery();
				if(rs.next()){
					addUsername = rs.getString("username");
				}
				profiles.add(GetProfile(addUsername));
			}
			return profiles;
		} catch (SQLException e) {
			e.printStackTrace();
			return profiles;
		}
	}
	
	public static ArrayList<InternshipObject> GetInternship(String username) {
		if(conn == null) {
			return null;
		}
		try {
			int userID;
			String sql = "SELECT userID FROM users WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			if(!rs.next()) {
				return null; //returns null string if userID doesn't exist from username
			} 
			else{
				userID  = rs.getInt("userID");
				sql = "SELECT * FROM internship WHERE userID = ?";
				ps.clearParameters();
				ps = conn.prepareStatement(sql);
				ps.setInt(1, userID);
				rs = ps.executeQuery();
			}

			//get data information corresponding to the input username and return String array
			ArrayList<InternshipObject> interns = new ArrayList<InternshipObject>();
			while(rs.next()){
				InternshipObject intern = new InternshipObject();
				intern.setAppDeadline(rs.getDate("appDeadline"));
				intern.setAppStatus(rs.getString("appStatus"));
				intern.setCompany(rs.getString("company "));
				intern.setDateApplied(rs.getDate("dateApplied"));
				intern.setLocation(rs.getString("location"));
				intern.setPosition(rs.getString("position"));	
			}
			return interns;
		} catch (SQLException e){
			e.printStackTrace();
			return null;
		}	
	}
	
	public void UpdateProfile(String username, String firstName, String lastName, String location, String imgLink) {
		if(conn == null) {
			return;
		}
		
		try {
			int userID;
			String sql = "SELECT userID FROM users WHERE username = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();

			if(!rs.next()) {
				return; //returns null string if userID doesn't exist from username
			} 
			else{
				userID  = rs.getInt("userID");
				sql = "UPDATE profile SET firstName = ?, lastName = ?, location = ?, imageLink = ? WHERE userID = ?";
				ps.clearParameters();
				ps = conn.prepareStatement(sql);
				ps.setString(1,firstName);
				ps.setString(2, lastName);
				ps.setString(3, location);
				ps.setString(4, imgLink);
				ps.setInt(5, userID);
				ps.executeUpdate();
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	//adds a column to the notification column with type set to 1 i.e. friendrequest notification
	public static int sendFriendRequest(int sendingUserID, String sendingUserImageLink, int receivingUserID){
		if(conn == null) {
			return SERVER_ERROR;
		}
		try{
			String sql = "INSERT INTO notifications(sendingUserID, receivingUserID, type, imageLink) VALUES (? , ?, 1, ? )";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, sendingUserID);
			ps.setInt(2, receivingUserID);
			ps.setString(3, sendingUserImageLink);
			ps.executeUpdate();
		}catch (SQLException e){
			e.printStackTrace();
			return SERVER_ERROR;
		}	
		return SUCCESS;
	}
	
}
