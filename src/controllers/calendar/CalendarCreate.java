package controllers.calendar;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Calendar;
import models.Employee;
import models.validators.CalendarValidator;
import utils.DBUtil;

/**
 * Servlet implementation class CalendarCreate
 */
@WebServlet("/calendar/create")
public class CalendarCreate extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CalendarCreate() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            String schedule_date = (String)request.getSession().getAttribute("scedule_date");

            Employee login_employee = (Employee)request.getSession().getAttribute("login_employee");

            Calendar cal = null;
            try{
                cal = em.createNamedQuery("getMyDateCalendar", Calendar.class)
                        .setParameter("employee", login_employee)
                        .setParameter("date", schedule_date)
                        .getSingleResult();

                cal.setEmployee(login_employee);

                cal.setSchedule_date(schedule_date);

                String content = request.getParameter("content");
                cal.setContent(content);

                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                cal.setCreated_at(currentTime);
                cal.setUpdated_at(currentTime);

                em.getTransaction().begin();
                em.persist(cal);


            }catch(NoResultException ex){
                cal = new Calendar();

                cal.setEmployee(login_employee);

                cal.setSchedule_date(schedule_date);

                String content = request.getParameter("content");
                cal.setContent(content);

                Timestamp currentTime = new Timestamp(System.currentTimeMillis());
                cal.setCreated_at(currentTime);
                cal.setUpdated_at(currentTime);

                List<String> errors = CalendarValidator.validate(cal);
                if(errors.size() > 0) {
                    em.close();

                    request.setAttribute("_token", request.getSession().getId());
                    request.setAttribute("report", cal);
                    request.setAttribute("errors", errors);

                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/calendars/new.jsp");
                    rd.forward(request, response);
                } else{
                    em.getTransaction().begin();
                    em.persist(cal);

                }
            }

            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "登録が完了しました。");
            response.sendRedirect(request.getContextPath() + "/calendar/show");
        }
    }

}
