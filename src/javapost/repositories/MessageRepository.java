package javapost.repositories;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import javapost.Settings;
import javapost.models.Message;
import javapost.utils.FileUtils;
import javapost.utils.MessageUtils;

public class MessageRepository extends TimerTask {

    private static List<Message> messages;

    public static List<Message> getMessages() {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        return messages;
    }

    public static void setMessages(List<Message> aMessages) {
        messages = aMessages;
    }

    public static Message addMessage(Message message) {
        messages.add(message);

        return message;
    }

    public static void save() throws IOException {
        Gson gson = new Gson();
        String content = gson.toJson(getMessages());
        FileUtils.writeFile(content, Settings.MESSAGES_FILE_NAME);
    }

    public static void loadRepo() {
        try {
            String rawData = FileUtils.readFile(Settings.MESSAGES_FILE_NAME);
            List<Message> list = MessageUtils.deserialize(rawData);
            setMessages(list);
        } catch (IOException ex) {
            System.err.println("Message data file corrupted!");
            return;
        }
    }

    @Override
    public void run() {
        this.loadRepo();
    }

}
