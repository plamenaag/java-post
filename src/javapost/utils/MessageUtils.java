package javapost.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javapost.CurrentUser;
import javapost.ScannerFactory;
import javapost.models.Message;
import javapost.models.User;
import javapost.repositories.MessageRepository;
import org.bson.Document;

public class MessageUtils {

    public static List<Message> deserialize(String fileContent) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        List<Document> rawMsgs = mapper.readValue(fileContent,
                TypeFactory.defaultInstance().constructCollectionType(List.class, Document.class));

        List<Message> messages = new ArrayList<>();
        for (Document item : rawMsgs) {
            Message message = new Message(item);

            messages.add(message);
        }

        return messages;
    }

    public static void printAll(List<Message> messages) throws IOException {
        if (messages == null || messages.size() == 0) {
            System.err.println("No messages found!");
            return;
        }

        Collections.sort(messages, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s", "Sender"));
        sb.append(String.format("%-20s", "Receiver"));
        sb.append(String.format("%-20s", "Date"));
        sb.append(String.format("%-20s", "Message"));
        sb.append(String.format("%-20s", "Is read"));

        System.out.println(sb.toString());

        Boolean saveReadMessages = false;
        for (Message message : messages) {
            System.out.println(message.toString());
            if (CurrentUser.getUser().equals(message.getToUser())) {
                message.setIsRead(true);
                saveReadMessages = true;
            }
        }
        
        if(saveReadMessages){
            MessageRepository.save();
        }
    }

    public static List<Message> getAllMessages() {
        List<Message> messages = new ArrayList<>();
        messages.addAll(getMessages(CurrentUser.getUser(), null, null));
        messages.addAll(getMessages(null, CurrentUser.getUser(), null));

        return messages;
    }

    public static List<Message> getAllSentMessages() {
        return getMessages(CurrentUser.getUser(), null, null);
    }

    public static List<Message> getAllReceivedMessages() {
        return getMessages(null, CurrentUser.getUser(), null);
    }

    public static List<Message> getAllUnreadMessages() {
        return getMessages(null, CurrentUser.getUser(), false);
    }

    public static List<Message> getMessages(User fromUser, User toUser, Boolean isRead) {
        List<Message> messages = MessageRepository.getMessages();
        if (fromUser != null) {
            messages = messages.stream().filter(message -> message.getFromUser()
                    .equals(fromUser)).collect(Collectors.toList());
        }
        if (toUser != null) {
            messages = messages.stream().filter(message -> message.getToUser()
                    .equals(toUser)).collect(Collectors.toList());
        }
        if (isRead != null) {
            messages = messages.stream().filter(message -> message.getIsRead()
                    .equals(isRead)).collect(Collectors.toList());
        }

        return messages;
    }

    public static Message createMessage() {
        System.out.print("Choose message receiver (username): ");
        String userName = ScannerFactory.getInstance().nextLine();

        List<User> users = UserUtils.findByUserName(userName);

        User selectedUser = null;

        if (users != null && users.size() == 1) {
            selectedUser = users.get(0);
        } else if (users != null || users.size() > 1) {
            UserUtils.printAll(users);
            System.out.println("Press " + (users.size() + 1) + " to exit!");
            System.out.print("Select user:");
            Integer selectedOption = MenuUtils.selectOption(users.size() + 1);
            if (selectedOption == null || selectedOption.equals(users.size() + 1)) {
                return null;
            } else {
                selectedUser = users.get(selectedOption - 1);
            }
        } else {
            System.err.println("No users found with that username!");
            return null;
        }

        System.out.print("You have selected user: ");
        System.out.println(selectedUser);
        System.out.print("Input your message: ");
        String content = ScannerFactory.getInstance().nextLine();
        Message message = new Message(CurrentUser.getUser(), selectedUser, content);
        return message;
    }
}
