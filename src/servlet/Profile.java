package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import database.Database;
import model.InternshipObject;
import model.ProfileObject;
import util.Util;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int resultCode = Database.connect();
		JSONArray array = new JSONArray();
		if (resultCode == Database.SERVER_ERROR) {
			Util.writeJsonArray(response, array);
			return;
		}
		try {
			String query = request.getParameter("query");
			System.out.println("GET PROFILE: " + query);
			ArrayList<ProfileObject> profiles = Database.getProfilesByName(query);
			for (ProfileObject profile : profiles) {
				JSONObject obj = profile.toJSONObject();
				array.add(obj);
			}
			Util.writeJsonArray(response, array);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int resultCode = Database.connect();
		JSONObject responseObj = new JSONObject(); // json response obj
		if (resultCode == Database.SERVER_ERROR) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			responseObj.put("error", "Internal server error.");
			return;
		}
	  	 try {
	  		 JSONObject input = Util.readJsonObject(request);
	  		 String userId = (String) input.get("username");
	  		 ProfileObject user  = Database.GetProfile(userId);
	  		 JSONObject out1 = user.toJSONObject();
	  		 JSONArray arr = new JSONArray();
	  		 arr.add(out1);
	  		 ArrayList<InternshipObject> interns = Database.GetInternship(userId);
	  		 for(int i=0; i<interns.size(); i++) {
	  			 JSONObject out2 =  interns.get(i).toJSONObject();
	  			 arr.add(out2);
	  		 }
	  		 Util.writeJsonArray(response,arr);
	  	 } catch (Exception e) {
	  		 e.printStackTrace();
	  	 } finally {
	  		 Database.close();
	  	 }
	}

}
