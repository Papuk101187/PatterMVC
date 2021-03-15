package org.example;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Applications;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyServlet extends JsonServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        Applications applicat1 = new Applications()
                .setName("Протокол связи")
                .setDescription("Протокол описывающий связь")
                .setDate("14.01.2020");

        Applications applicat2 = new Applications()
                .setName("Протокол коммуникации")
                .setDescription("Протокол описывающий коммуникации")
                .setDate("14.01.2021");


        Map<String, Applications> applicationsMap = new HashMap<>();
        applicationsMap.put("1", applicat1);
        applicationsMap.put("2", applicat2);


        String id = req.getParameter("id");
        if (id != null && applicationsMap.containsKey(id)) {
            Applications newapplications = applicationsMap.get(id);
            writeJson(newapplications, resp);
        }


    }


}
