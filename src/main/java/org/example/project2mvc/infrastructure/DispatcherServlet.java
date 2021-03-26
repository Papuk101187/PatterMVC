package org.example.project2mvc.infrastructure;
import org.example.PathMatcher;
import org.example.project2mvc.MainController;
import org.example.project2mvc.annotations.*;
import org.example.project2mvc.refction.PackageScanner;
import org.example.projectjspandjstl.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

public class DispatcherServlet extends HttpServlet {


    private final PackageScanner packageScanner = new PackageScanner();
    private final ApplicationContext context = new ApplicationContext();
    private final List<Class<?>> controllers;
    PathMatcher pathMatcher = new PathMatcher();  // Тот самый класс PathMatcher


    public DispatcherServlet() throws IllegalAccessException {
        this.controllers = packageScanner
                .findClassesWithAnnotation(
                        Controller.class,
                        "org.example");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        Date date = new Date();

        String address = null;


        for (Class<?> controller : controllers) {

            for (Method m : controller.getDeclaredMethods()) {


                if (request.getMethod().equalsIgnoreCase("GET")
                        && m.isAnnotationPresent(GetMapping.class)) {
                    address = m.getAnnotation(GetMapping.class).value();}  // _____________ ГЛАВНАЯ СТРАНИЦА


                if (request.getMethod().equalsIgnoreCase("POST")
                        && m.isAnnotationPresent(DeleteMapping.class)) {
                    address = m.getAnnotation(DeleteMapping.class).value(); // _______________УДАЛЕНИЕ ЮЗЕРА
                }

                if (request.getMethod().equalsIgnoreCase("POST")  //______________ДОБАВЛЕНИЕ ЮЗЕРА
                        && m.isAnnotationPresent(PostMapping.class)) {
                    address = m.getAnnotation(PostMapping.class).value();
                    System.out.println("PostMapping");}

                if (request.getMethod().equalsIgnoreCase("GET")   // _________ПОКАЗЫВАЕМ ЗАЯВКИ ПО ЮЗЕРУ
                        && m.isAnnotationPresent(PutMapping.class)) {
                    String originalPath = request.getRequestURI().substring(request.getContextPath().length());
                    pathMatcher.match(originalPath,m.getAnnotation(PutMapping.class).value());
                    String id = pathMatcher.getAdress().get("id");
                    request.setAttribute("id",id);
                    address = originalPath.substring(1);
                    System.out.println("PutMapping"); }


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
}
