package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import database.Database;
import util.Util;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Register() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int resultCode = Database.connect();
			JSONObject responseObj = new JSONObject(); // json response obj
			if(resultCode == Database.SERVER_ERROR) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				responseObj.put("error", "Internal server error.");
				return;
			}
			
			// Read request obj
			JSONObject input = Util.readJsonObject(request);
			String firstname = (String) input.get("firstname");
			String lastname = (String) input.get("lastname");
			String username = (String) input.get("username");
			String password = (String) input.get("password");
			String confirmPassword = (String) input.get("confirmPassword");
			String location = (String) input.get("location");
			String imageLink = (String) input.get("imageLink");
			
			resultCode = Database.register(username, password, confirmPassword, firstname, lastname, location, imageLink);
			if (resultCode == Database.SUCCESS) {
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				session.setMaxInactiveInterval(1800);
				responseObj.put("status", "SUCCESS");
				responseObj.put("username", username);
			} else if (resultCode == Database.NOT_MATCHING_PASSWORD) {
				request.getSession().invalidate();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				responseObj.put("error", "Passwords do not match.");
			} else if (resultCode == Database.USER_ALREADY_EXISTS) {
				request.getSession().invalidate();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				responseObj.put("error", "Username is already taken.");
			} else if (resultCode == Database.INVALID_FNAME) {
				request.getSession().invalidate();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				responseObj.put("error", "Invalid firstname.");
			} else if (resultCode == Database.INVALID_LNAME) {
				request.getSession().invalidate();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				responseObj.put("error", "Invalid lastname.");
			} else if (resultCode == Database.INVALID_LOCATION) {
				request.getSession().invalidate();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				responseObj.put("error", "Invalid location.");
			} else if (resultCode == Database.INVALID_USERNAME) {
				request.getSession().invalidate();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				responseObj.put("error", "Invalid username.");
			} else if (resultCode == Database.INVALID_PASSWORD) {
				request.getSession().invalidate();
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				responseObj.put("error", "Invalid password.");
			}
			else {
				request.getSession().invalidate();
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				responseObj.put("error", "Internal server error.");
			}
			Util.writeJsonObject(response, responseObj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.close();
		}
	}

}