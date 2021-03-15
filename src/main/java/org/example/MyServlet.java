package org.example;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Applications;

import java.io.IOException;

public class MyServlet extends JsonServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        Applications applications =new Applications()
                .setName("Протокол связи")
                .setDescription("Протокол описывающий связь")
                .setDate("14.01.2020");

        writeJson(applications,resp);


    }



}
