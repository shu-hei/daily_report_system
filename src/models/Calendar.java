package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "calendars")
@NamedQueries({
    @NamedQuery(
        name = "getAllCalendars",
        query = "SELECT c FROM Calendar AS c ORDER BY c.id DESC"
    ),
    @NamedQuery(
        name = "getCalendarsCount",
        query = "SELECT COUNT(c) FROM Employee AS c"
    ),
    @NamedQuery(
            name = "getMyAllCalendars",
            query = "SELECT c FROM Calendar AS c WHERE c.employee = :employee ORDER BY c.id DESC"
        ),
    @NamedQuery(
        name = "getMyCalendarCount",
        query = "SELECT COUNT(c) FROM Report AS c WHERE c.employee = :employee"
    ),
    @NamedQuery(
        name = "getMyDateCalendar",
        query = "SELECT c FROM Calendar  AS c WHERE c.employee  = :employee AND c.schedule_date = :date "
    )
})
@Entity
public class Calendar {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "schedule_date", nullable = false)
    private String schedule_date;

    @Column(name  = "content", nullable = false)
    private  String content;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getSchedule_date() {
        return schedule_date;
    }

    public void setSchedule_date(String schedule_date) {
        this.schedule_date = schedule_date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }
}
