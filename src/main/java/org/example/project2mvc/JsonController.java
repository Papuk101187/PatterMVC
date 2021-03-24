package org.example.project2mvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JsonController {

    private ObjectMapper mapper = new ObjectMapper();


    protected void writeJson(Object object, HttpServletResponse response) {

        try {
            response.setHeader("Content-Type", "application/json");
            String strResponce = mapper.writeValueAsString(object);
            response.getWriter().print(strResponce);
            response.getWriter().flush();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected <T> T readJson(Class<T> clazz, HttpServletRequest request) throws IOException {
        return mapper.readValue(request.getInputStream(), clazz);
    }


}
