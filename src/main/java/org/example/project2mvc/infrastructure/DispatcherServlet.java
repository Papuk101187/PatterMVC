package org.example.project2mvc.infrastructure;

import org.example.project2mvc.annotations.*;
import org.example.project2mvc.refction.PackageScanner;

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
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        for (Class<?> controller : controllers) {

            for (Method m : controller.getDeclaredMethods()) {

                String address = null;

                if (req.getMethod().equalsIgnoreCase("get")
                        && m.isAnnotationPresent(GetMapping.class)) {
                    address = m.getAnnotation(GetMapping.class).value();
                }

                if (req.getMethod().equalsIgnoreCase("post")
                        && m.isAnnotationPresent(PostMapping.class)) {
                    address = m.getAnnotation(PostMapping.class).value();
                }

                if (req.getMethod().equalsIgnoreCase("delete")
                        && m.isAnnotationPresent(DeleteMapping.class)) {
                    address = m.getAnnotation(DeleteMapping.class).value();
                }

                if (req.getMethod().equalsIgnoreCase("put")
                        && m.isAnnotationPresent(PutMapping.class)) {
                    address = m.getAnnotation(PutMapping.class).value();
                }

                if (address == null) continue;


                String addr = req.getContextPath() + "/" + address;
                if (addr.equalsIgnoreCase(req.getRequestURI())) {

                    Object instance = context.getBeanByType(controller);
                    try {
                        m.invoke(instance, req, resp);
                        return;
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }

            }

        }


        resp.setStatus(404);
        resp.getWriter().write("NOT FOUND");
    }
}
