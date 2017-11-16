package com.sencha.gxt.examples.resources.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sencha.gxt.examples.resources.server.data.Folder;
import com.sencha.gxt.examples.resources.server.data.Music;

public class JsonTreeServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    super.doPost(req, resp);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    try {
      String id = req.getParameter("id");

      Folder folder = Folder.findFolder(id == null ? 1 : Integer.valueOf(id));

      String xml = generateJson(folder);

      resp.setContentType("application/json");
      PrintWriter out = resp.getWriter();
      out.println(xml);
      out.flush();
    } catch (Exception e) {
      throw new ServletException(e);
    }
  }

  private String generateJson(Folder folder) throws Exception {

    JSONObject root = new JSONObject();

    JSONArray records = new JSONArray();
    root.put("records", records);

    for (int i = 0, len = folder.getSubFolders().size(); i < len; i++) {
      Folder sub = folder.getSubFolders().get(i);
      JSONObject f = new JSONObject();
      f.put("folder", true);
      f.put("name", sub.getName());
      f.put("id", sub.getId());
      records.put(f);
    }

    for (int i = 0, len = folder.getChildren().size(); i < len; i++) {
      Music m = folder.getChildren().get(i);
      JSONObject f = new JSONObject();
      f.put("folder", false);
      f.put("name", m.getName());
      f.put("id", m.getId());
      records.put(f);
    }

    return root.toString();
  }
}
