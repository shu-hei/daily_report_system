package controllers.calendar;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Calendar;
import models.Employee;
import utils.DBUtil;

/**
 * Servlet implementation class CalendarEdit
 */
@WebServlet("/calendar/edit")
public class CalendarEdit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarEdit() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Calendar c = em.find(Calendar.class, Integer.parseInt(request.getParameter("id")));

        em.close();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");
        if(c != null && login_employee.getId() == c.getEmployee().getId()) {
            request.setAttribute("calendar", c);
            request.setAttribute("_token", request.getSession().getId());
            request.getSession().setAttribute("calendar_id", c.getId());
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/calendars/edit.jsp");
        rd.forward(request, response);

    }

}
