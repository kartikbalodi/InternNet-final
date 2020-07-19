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

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
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
			String username = (String) input.get("username");
			String password = (String) input.get("password");

			resultCode = Database.login(username, password);
			if (resultCode == Database.SUCCESS) {
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
				session.setMaxInactiveInterval(1800);
				responseObj.put("status", "SUCCESS");
				responseObj.put("username", username);
			} 
			else if(resultCode == Database.NO_USER_EXIST) {
				responseObj.put("status", "FAIL1");
				responseObj.put("error", "User does not exist.");
			}
			else if(resultCode == Database.INCORRECT_PASSWORD) {
				responseObj.put("status", "FAIL2");
				responseObj.put("error", "Incorrect Password");
			}
			
			Util.writeJsonObject(response, responseObj);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Database.close();
		}
	}

}