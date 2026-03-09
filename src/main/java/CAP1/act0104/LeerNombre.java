package CAP1.act0104;

public class LeerNombre {
    public static void main(String[] args) {
        if (args[0].chars().allMatch(Character::isLetter)) {
            System.out.println("En efecto, \"" + args[0] + "\" es un nombre.");
            System.exit(1);
        } else {
            System.out.println("La cadena \"" + args[0] + "\" es incorrecta, no es un nombre.");
            System.exit(-1);
        }
    }
}
