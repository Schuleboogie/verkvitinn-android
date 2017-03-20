package is.hi.verkvitinn.persistence.entities;

import java.util.List;

/**
 * Created by sunna on 20.3.2017.
 */

public class Group {
    private Long id;
    private String name;
    private List<User> workers;

    public Group(String name, List<User> workers){
        this.name=name;
        this.workers=workers;
    }

    public Long getId(){return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){return name;}

    public List<User> getWorkers(){return workers;}

}
