package javapost.repositories;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import javapost.Settings;
import javapost.UserType;
import javapost.models.Courier;
import javapost.models.OfficeEmployee;
import javapost.models.User;
import javapost.security.PasswordHasher;
import javapost.utils.FileUtils;
import javapost.utils.UserUtils;

public class UserRepository extends TimerTask {

    private static List<User> users;

    public static List<User> getUsers() {
        if (users == null) {
            users = new ArrayList<User>();
        }
        return users;
    }

    public static void setUsers(List<User> userList) {
        users = userList;
    }

    public static User addUser(User user) {
        users.add(user);

        return user;
    }

    public static Boolean checkUserExist(String userName, String extraInfo, String userType) {
        User foundUser = null;
        if (userType.equals(UserType.COURIER)) {
            for (int i = 0; i < getUsers().size(); i++) {
                User currUser = getUsers().get(i);
                if (currUser instanceof Courier && currUser.getUserName().equals(userName)
                        && ((Courier) currUser).getRegNumber().equals(extraInfo)) {
                    foundUser = currUser;
                    break;
                }
            }
        } else {
            for (int i = 0; i < getUsers().size(); i++) {
                User currUser = getUsers().get(i);
                if (currUser instanceof OfficeEmployee && currUser.getUserName().equals(userName)
                        && ((OfficeEmployee) currUser).getEmail().equals(extraInfo)) {
                    foundUser = currUser;
                    break;
                }
            }
        }

        return foundUser != null;
    }

    public static List<User> getUsers(String userName, String extraInfo, String password, String userType) {
        List<User> foundUsers = new ArrayList<>();
        for (User user : getUsers()) {
            if (user.getUserName().equals(userName)
                    && user.getPassword().equals(PasswordHasher.getHashWithSalt(password, user.getSalt()))) {
                if (userType.equals(UserType.COURIER) && user instanceof Courier
                        && ((Courier) user).getRegNumber().equals(extraInfo)) {
                    foundUsers.add(user);
                } else if (userType.equals(UserType.OFFICE_EMPLOYEE) && user instanceof OfficeEmployee 
                        && ((OfficeEmployee) user).getEmail().equals(extraInfo)) {
                    foundUsers.add(user);
                }
            }
        }

        return foundUsers;
    }
    
    public static void save() throws IOException {
        Gson gson = new Gson();
        String content = gson.toJson(getUsers());
        FileUtils.writeFile(content,Settings.USER_FILE_NAME);
    }
    
    public static void loadRepo(){
         try {
            String rawData = FileUtils.readFile(Settings.USER_FILE_NAME);
            List<User> list = UserUtils.deserialize(rawData);
            setUsers(list);
        } catch (IOException ex) {
            System.err.println("User data file corrupted!");
            return;
        }
    }

    @Override
    public void run() {
       loadRepo();
    }
}
