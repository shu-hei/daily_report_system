package controllers.calendar;

import java.io.IOException;
import java.util.List;

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
 * Servlet implementation class CalendarNewServlet
 */
@WebServlet("/CalendarNew")
public class CalendarNewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarNewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("_token", request.getSession().getId());

        EntityManager em = DBUtil.createEntityManager();

        Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

        String schedule_date = request.getParameter("YEAR") + "年" + request.getParameter("MONTH") + "月" + request.getParameter("DAY") + "日";


        List<Calendar> calendars = em.createNamedQuery("getMyDateCalendar", Calendar.class)
                .setParameter("employee", login_employee)
                .setParameter("date", schedule_date)
                .getResultList();

        Calendar c = null;

        if(calendars.size() > 0){
            c = calendars.get(0);
        }else{
            c = new Calendar();
        }


        request.getSession().setAttribute("scedule_date",  schedule_date);
        request.setAttribute("calendar", c);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/calendars/new.jsp");
        rd.forward(request, response);
    }

}
