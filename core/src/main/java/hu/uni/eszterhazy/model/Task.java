package hu.uni.eszterhazy.model;


import hu.uni.eszterhazy.exceptions.DateNotOK;
import hu.uni.eszterhazy.exceptions.NotMatchingID;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Task {

    private String title;
    private String id;
    private String caption;
    private LocalDate startDate;
    private LocalDate dueDate;
    private Prio prio;
    private String contact;
    private String comment;
    private Pattern idPattern;

    public Task() {
        idPattern = Pattern.compile("^[0-9]{2}$");
    }

    public Task(String title, String id, LocalDate startDate,
                LocalDate dueDate, Prio prio, String contact,
                String comment )
            throws DateNotOK, NotMatchingID {
        this();
        this.title = title;
        setId(id);
        setStartDate(startDate);
        setDueDate(dueDate);
        this.prio = prio;
        this.contact = contact;
        this.comment = comment;
    }

    public Task(String taskId) throws NotMatchingID, DateNotOK {
        this( "dummy", taskId, LocalDate.now(), LocalDate.now(), Prio.FIVE, "","");
    }

    private boolean validDueDate(LocalDate dueDate) {
        if (this.startDate == null) {
            this.startDate =  LocalDate.now();
        }
        if (dueDate.isAfter(this.startDate) || dueDate.isEqual(this.startDate)) {
            return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public Prio getPrio() {
        return prio;
    }

    public String getContact() {
        return contact;
    }

    public String getComment() {
        return comment;
    }

    public String getCaption() {
        this.caption = this.id + " - " + this.title;
        return this.caption;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(String id) throws NotMatchingID {
        if (idPattern.matcher(id).find()) {
            this.id = id;
        } else {
            throw new NotMatchingID(id);
        }
    }

    public void setPrio(Prio prio) {
        this.prio = prio;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
    }

    public void setDueDate(LocalDate dueDate) throws DateNotOK {
        if (validDueDate(dueDate)) {
            this.dueDate = dueDate;
        } else {
            throw new DateNotOK(dueDate);
        }
    }
}
