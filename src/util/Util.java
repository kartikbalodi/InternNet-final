package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Util {
  public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException {
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    PrintWriter out = response.getWriter();
    out.print(array);
    out.close();

  }

  public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException {
    response.setContentType("application/json");
    response.setHeader("Access-Control-Allow-Origin", "*");
    PrintWriter out = response.getWriter();
    out.print(obj);
    out.close();
  }

  public static JSONObject readJsonObject(HttpServletRequest request) {
    StringBuilder sBuilder = new StringBuilder();
    try (BufferedReader reader = request.getReader()) {
      String line = null;
      while ((line = reader.readLine()) != null) {
        sBuilder.append(line);
      }
      JSONParser parse = new JSONParser();
      JSONObject jobj = (JSONObject) parse.parse(sBuilder.toString());
      return jobj;

    } catch (Exception e) {
      e.printStackTrace();
    }

    return new JSONObject();
  }

}
