package javapost;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javapost.models.Courier;
import javapost.models.Message;
import javapost.models.OfficeEmployee;
import javapost.models.User;
import javapost.repositories.MessageRepository;
import javapost.repositories.UserRepository;
import javapost.utils.MenuUtils;
import javapost.utils.MessageUtils;
import javapost.utils.UserUtils;

public class JavaPost {

    public static void main(String[] args) throws IOException, Exception {
        Timer timer = new Timer(); 
        TimerTask userRepo = new UserRepository(); 
        TimerTask msgRepo = new MessageRepository(); 
        timer.schedule(userRepo, 0, 3000);
        timer.schedule(msgRepo, 2000, 3000);

        Integer regLogInMenuInput = null;
        Integer userTypeInput = null;
        do {
            regLogInMenuInput = MenuUtils.printRegLogInMenu();
            if (regLogInMenuInput != 3) {
                userTypeInput = MenuUtils.printUserTypeMenu();

                if (userTypeInput.equals(1) || userTypeInput.equals(2)) {
                    switch (regLogInMenuInput) {
                        case 1: {
                            User user = UserUtils.createUser(userTypeInput.equals(1) ? UserType.OFFICE_EMPLOYEE : UserType.COURIER);
                            if (user != null) {
                                UserRepository.addUser(user);
                                UserRepository.save();
                                System.out.println("User created successfully!");
                            }
                        }
                        break;
                        case 2: {
                            User user = UserUtils.logInUser(userTypeInput.equals(1) ? UserType.OFFICE_EMPLOYEE : UserType.COURIER);
                            if (user == null) {
                                System.err.println("Wrong username or password!");
                            } else {
                                CurrentUser.setUser(user);
                                loggedInFlow();
                            }
                        }
                        break;
                    }
                }
            }
        } while (regLogInMenuInput != 3);

        ScannerFactory.getInstance().close();
    }

    public static void loggedInFlow() throws IOException {
        Integer input = null;
        do {
            if (CurrentUser.getUser() instanceof OfficeEmployee) {
                input = MenuUtils.printOfficeEmployeeMenu();
            } else {
                input = MenuUtils.printCourierMenu();
            }
            switch (input) {
                case 1: {
                    Message message = MessageUtils.createMessage();
                    if (message != null) {
                        MessageRepository.addMessage(message);
                        MessageRepository.save();
                        System.out.println("Sending msg successful!");
                    }else{
                        System.err.println("Sending msg failed!");
                    }
                }
                break;
                case 2: {
                    UserUtils.printAll(UserRepository.getUsers());
                }
                break;
                case 3: {
                    MessageUtils.printAll(MessageUtils.getAllMessages());
                }
                break;
                case 4: {
                    MessageUtils.printAll(MessageUtils.getAllSentMessages());
                }
                break;
                case 5: {
                    MessageUtils.printAll(MessageUtils.getAllReceivedMessages());
                }
                break;
                case 6: {
                    MessageUtils.printAll(MessageUtils.getAllUnreadMessages());
                }
                break;
                case 7: {
                    if (CurrentUser.getUser() instanceof OfficeEmployee) {
                        System.out.print("Input courier's last name: ");
                        String lastName = ScannerFactory.getInstance().nextLine();
                        List<User> users = UserUtils.findCourierByLastName(lastName);
                        UserUtils.printAll(users);
                    } else {
                        System.out.println("Traveled kilometers are: " + ((Courier) CurrentUser.getUser()).getKilometers());
                    }
                }
                break;
            }
        } while (input != 8);
    }

}
