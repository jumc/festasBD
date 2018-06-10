import jdk.internal.util.xml.impl.Input;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class Keyboard {

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm-dd/MM/yyyy");

    private static Scanner sc = new Scanner(System.in);

    public static int readInt(){
        try {
            int i = sc.nextInt();
            sc.nextLine();
            return i;
        } catch (InputMismatchException e){
            System.out.println("Digite um numero inteiro");
            System.out.print("> ");
            sc.nextLine();
            return readInt();
        }
    }

    public static float readFloat(){
        try {
            float i = sc.nextFloat();
            sc.nextLine();
            return i;
        } catch (InputMismatchException e){
            System.out.println("Digite um numero (casas quebradas separadas com ponto)");
            System.out.print("> ");
            sc.nextLine();
            return readInt();
        }
    }

    public static String readTimestamp(){
        String i = readLine();
        try {
            LocalDateTime date = LocalDateTime.parse(i, formatter);
            i = formatter.format(date);
            return i;
        } catch (DateTimeParseException e){
            System.out.println("Digite no formato horas:minutos-dia/mes/ano (hh:mm-dd/MM/AAAA)");
            System.out.print("> ");
            return readTimestamp();
        }
    }

    public static String readLine(){
        try {
            return sc.nextLine();
        } catch (Exception e){
            System.out.println("Erro");
            return readLine();
        }
    }
}
