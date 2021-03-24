package org.example.project2mvc.infrastructure;

import org.example.project2mvc.annotations.*;
import org.example.project2mvc.refction.PackageScanner;
import org.example.projectjspandjstl.entity.User;
import org.example.projectjspandjstl.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class DispatcherServlet extends HttpServlet {

    private final PackageScanner packageScanner = new PackageScanner();
    private final ApplicationContext context = new ApplicationContext();
    private final List<Class<?>> controllers;


    public DispatcherServlet() throws IllegalAccessException {
        this.controllers = packageScanner
                .findClassesWithAnnotation(
                        Controller.class,
                        "org.example");
        ;

    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

        UserService userService = new UserService();

        for (Class<?> controller : controllers) {

            for (Method m : controller.getDeclaredMethods()) {

                String address = null;
                String id = null;
                User user = null;
                int count = request.getRequestURI().length();

                address = getString(request, userService, m, address, id, count);

                if (address == null) continue;

                String addr = request.getContextPath() + "/" + address;
                if (extracted(request, resp, controller, m, addr)) return;
            }
        }


        resp.setStatus(404);
        resp.getWriter().write("NOT FOUND");
    }

    private boolean extracted(HttpServletRequest request, HttpServletResponse resp, Class<?> controller, Method m, String addr) {
        if (addr.equalsIgnoreCase(request.getRequestURI())) {

            Object instance = context.getBeanByType(controller);
            try {
                m.invoke(instance, request, resp);
                return true;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private String getString(HttpServletRequest request, UserService userService, Method m, String address, String id, int count) {
        User user;
        if (request.getMethod().equalsIgnoreCase("GET")
                && m.isAnnotationPresent(GetMapping.class)) {
            System.out.println("GetMapping.class");
            address = m.getAnnotation(GetMapping.class).value();
        }

        if (request.getMethod().equalsIgnoreCase("POST")
                && m.isAnnotationPresent(DeleteMapping.class)) {
            System.out.println("DeleteMapping.class");
            address = m.getAnnotation(DeleteMapping.class).value();
        }

        if (request.getMethod().equalsIgnoreCase("POST")
                && m.isAnnotationPresent(PostMapping.class)) {
            System.out.println("PostMapping.class");
            address = m.getAnnotation(PostMapping.class).value();
        }

        if (request.getMethod().equalsIgnoreCase("GET")
                && m.isAnnotationPresent(PutMapping.class)) {
            if (count > 22) {
                id = request.getRequestURI().substring(28);
                user = userService.getAllUsersId(id);
            }
            address = m.getAnnotation(PutMapping.class).value() + id;
        }
        return address;
    }
}
