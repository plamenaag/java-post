
package javapost;

import java.util.Scanner;


public class ScannerFactory {
    
    private static Scanner sc;

    public static Scanner getInstance() {
        if (sc == null) {
            sc = new Scanner(System.in);
        }
        return sc;
    }

    public static void closeInstance() {
        if (sc != null) {
            sc.close();
        }
    }
    
}
