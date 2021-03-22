package org.example.projectjspandjstl.patterns;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.projectjspandjstl.service.ApplicationsService;
import org.example.projectjspandjstl.service.UserService;
import org.example.projectjspandjstl.dto.RequestRespone;
import org.example.projectjspandjstl.entity.Applications;
import org.example.projectjspandjstl.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class UberFactory {

    ObjectMapper objectMapper = new ObjectMapper();

    private UserService userService;
    private ApplicationsService applicationsService;

    public User getUser() {
        return user;
    }

    public Applications getApplications() {
        return applications;
    }

    User user;
    Applications applications;

    public ApplicationsService getApplicationsService() {
        return applicationsService;
    }

    private static final UberFactory INSTANCE = new UberFactory();

    public static UberFactory getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public UberFactory() {
        this.userService = new UserService();
        this.applicationsService = new ApplicationsService();
    }

    public void buildParametrsPost(HttpServletRequest httpServletRequest) throws IOException {
        RequestRespone requestRespone = objectMapper.readValue(httpServletRequest.getInputStream(),RequestRespone.class);

        user = new User()
                .setFio(requestRespone.getUsername())
                .setDate(requestRespone.getDateborn())
                .setPassword(requestRespone.getUserpassword());

        applications = new Applications()
                .setName(requestRespone.getName())
                .setDescription(requestRespone.getDescription())
                .setDate(requestRespone.getDate());
    }

    public void buildParametrsGet(HttpServletRequest httpServletRequest) throws IOException {

        String username = httpServletRequest.getParameter("username");
        String userpassword = httpServletRequest.getParameter("userpassword");
        String dateborn = httpServletRequest.getParameter("dateborn");

        user = new User()
                .setFio(username)
                .setDate(dateborn)
                .setPassword(userpassword);

    }


}
