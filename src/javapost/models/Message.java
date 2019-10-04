package javapost.models;

import javapost.utils.DateUtils;
import javapost.repositories.UserRepository;
import org.bson.Document;

public class Message {

    private static Integer lastId = 0;
    private Integer id;
    private transient User fromUser;
    private transient User toUser;
    private Integer fromUserId;
    private Integer toUserId;
    private Long date;
    private String content;
    private Boolean isRead;

    public Message() {
        lastId++;
        this.id = lastId;

    }
    
    public Message(User fromUser, User toUser, String content){
        this();
        this.setFromUser(fromUser);
        this.setToUser(toUser);
        this.content = content;
        this.isRead = false;
        this.date = System.currentTimeMillis();
        
        
    }

    public Message(Document jsonObj) {
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

        this.date = jsonObj.get("date", Long.class);
        this.content = jsonObj.get("content", String.class);
        this.isRead = jsonObj.get("isRead", Boolean.class);

        Integer fromUserId = jsonObj.get("fromUserId", Integer.class);
        this.setFromUser(UserRepository.getUsers().stream().filter(user -> user.getId().equals(fromUserId)).findFirst().orElse(null));

        Integer toUserId = jsonObj.get("toUserId", Integer.class);
        this.setToUser(UserRepository.getUsers().stream().filter(user -> user.getId().equals(toUserId)).findFirst().orElse(null));

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        if(fromUser!=null){
            fromUserId = fromUser.getId();
        }else{
            fromUserId = null;
        }
        this.fromUser = fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
          if(toUser!=null){
            toUserId = toUser.getId();
        }else{
            toUserId = null;
        }
        this.toUser = toUser;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s", this.getFromUser().getUserName()));
        sb.append(String.format("%-20s", this.getToUser().getUserName()));
        sb.append(String.format("%-20s", DateUtils.getDateTime(this.getDate())));
        sb.append(String.format("%-20s", this.getContent()));
        sb.append(String.format("%-20s", this.getIsRead()));

        return sb.toString();
    }

}
