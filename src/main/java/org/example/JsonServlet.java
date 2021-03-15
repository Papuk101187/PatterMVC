package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JsonServlet extends HttpServlet {

    ObjectMapper objectMapper = new ObjectMapper();



    public void writeJson(Object object, HttpServletResponse httpServletResponse) throws IOException {

        httpServletResponse.setHeader("Content-Type","text/plain");
        String strResponce = objectMapper.writeValueAsString(object);
        httpServletResponse.getWriter().print(strResponce);
        httpServletResponse.getWriter().flush();

    }


    public void readJson(HttpServletResponse response) throws IOException {





    }







}
