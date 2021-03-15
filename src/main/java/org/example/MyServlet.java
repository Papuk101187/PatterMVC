package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Applications;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyServlet extends JsonServlet {

    Map<String, Applications> applicationsMap = new HashMap<>();

    int i=0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String id = req.getParameter("id");
        if (id != null && applicationsMap.containsKey(id)) {
            Applications newapplications = applicationsMap.get(id);
            writeJson(newapplications, resp);
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Applications applications = readJson(Applications.class,req);
        String id = String.valueOf(++i);
        applicationsMap.put(id,applications);

    }
}
