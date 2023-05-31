package by.jpdm.model.beans.scheme;

import java.sql.Timestamp;

public class SchemeInstalled {
    private String name;
    private Timestamp date;
    private String userName;
    
    public SchemeInstalled() {

    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Timestamp getDate() {
        return date;
    }
    
    public void setDate(Timestamp date) {
        this.date = date;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
