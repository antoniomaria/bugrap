/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vaadin.bugrap.presentation;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vaadin.bugrap.business.projects.boundary.ProjectRepository;
import com.vaadin.bugrap.business.projects.entity.Project;
import com.vaadin.bugrap.business.projects.entity.ProjectVersion;

/**
 * 
 * @author adam-bien.com
 */
@WebServlet(name = "Ping", urlPatterns = { "/Ping" })
public class Ping extends HttpServlet {

    @Inject
    ProjectRepository pr;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * 
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     * @throws ServletException
     *             if a servlet-specific error occurs
     * @throws IOException
     *             if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            ProjectVersion pv = new ProjectVersion();
            pv.setClosed(false);
            Project project = new Project();
            project.setName("ping test");
            pv.setProject(project);
            pr.store(project);
            pr.store(pv);
            List<Project> activeProjects = pr.getActiveProjects();
            out.println("active:" + activeProjects);
            out.println("All: " + pr.getProjects());
        } finally {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed"
    // desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     * @throws ServletException
     *             if a servlet-specific error occurs
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * @param request
     *            servlet request
     * @param response
     *            servlet response
     * @throws ServletException
     *             if a servlet-specific error occurs
     * @throws IOException
     *             if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     * 
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Ping Servlet";
    }// </editor-fold>
}
