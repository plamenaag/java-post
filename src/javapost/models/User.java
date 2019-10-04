package javapost.models;

import javapost.models.Message;
import java.util.ArrayList;
import java.util.List;
import javapost.security.PasswordHasher;
import org.bson.Document;

public abstract class User {

    private static Integer lastId = 0;
    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String salt;
    private String type;

    private transient List<Message> messages;

    public User() {
        lastId++;
        this.id = lastId;

    }

    public User(String firstName, String lastName, String userName, String password){
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.salt = PasswordHasher.getSalt();
        this.password = PasswordHasher.getHashWithSalt(password, this.salt);
    }
    
    
    public User(Document jsonObj) {
        Integer id = jsonObj.get("id", Integer.class);

        if (id != null && id > 0) {
            this.id = id;
            if (id > lastId) {
                lastId = id;
            }
        } else {
            lastId++;
            this.id = lastId;
        }

        this.firstName = jsonObj.get("firstName", String.class);
        this.lastName = jsonObj.get("lastName", String.class);
        this.userName = jsonObj.get("userName", String.class);
        this.password = jsonObj.get("password", String.class);
        this.salt = jsonObj.get("salt", String.class);

    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<Message> getMessages() {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        int hash = this.getId() != null ? this.getId().hashCode() : 0;
        return  hash;
    }

    @Override
    public boolean equals(Object object) {

        if (object == this) return true;
        if (object == null) return false;
        if (object.getClass() != this.getClass()) return false;

        User other = (User) object;
        if (this.getId() == null || other.getId()==null) return false;

        return this.getId().equals(other.getId());
    }
}
