public class Main {
    public static void main(String[] args) {
        if (Database.open()) {
            System.out.println("conectou!");
            Menu.MenuPrincipal();
            Database.close();
        }
    }
}