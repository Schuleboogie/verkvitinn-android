package is.hi.verkvitinn.persistence.entities;

import java.util.Date;

/**
 * Created by sunna on 4.4.2017.
 */

public class Log {
    private Long id;

    private Date timeIn;
    private Date timeOut;
    private String username;
    private long projectId;

    public Log(Long projectId, Date timeIn, Date timeOut, String username) {
        this.projectId = projectId;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.username = username;
    }

    public Date getTimeIn() {
        return timeIn;
    }

    public Date getTimeOut() {
        return timeOut;
    }

    public long getProjectId() {
        return projectId;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

}
