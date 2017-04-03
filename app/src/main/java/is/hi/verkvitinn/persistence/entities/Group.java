package is.hi.verkvitinn.persistence.entities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunna on 20.3.2017.
 */

public class Group {
    private Long id;
    private String name;
    private ArrayList<User> workers;

    public Group(String name, ArrayList<User> workers){
        this.name=name;
        this.workers=workers;
    }

    public Long getId(){return id;}

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){return name;}

    public ArrayList<User> getWorkers(){return workers;}

}
