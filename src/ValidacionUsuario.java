import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class ValidacionUsuario {

    Base_De_Datos db;

    public ValidacionUsuario(Base_De_Datos db) {
        this.db = db;
    }


    public boolean validarEmail(String email) {

        // Validar si es un email
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        boolean flag = matcher.matches();

        // Validar si el email ya existe
        boolean flag1 = db.NoExisteEmail(email);

        try {
            if ((!flag) || (!flag1)) {
                throw new EmailNoValido("El email no es valido");
            }
        } catch (EmailNoValido e) {
            System.out.println(e.getMessage());
        }

        return (flag1 && flag);

    }

    public boolean validarDni(String dni) {

        // Validar si es un dni
        boolean esDni = dni.matches("^\\d{7,8}$");

        // Validar si el dni ya existe
        boolean estaEnDb = db.NoExisteDni(dni);

        try {
            if ((!esDni) || (!estaEnDb)) {
                throw new DniExiste("El dni no es valido");
            }
        } catch (DniExiste e) {
            System.out.println(e.getMessage());
        }

        return (esDni && estaEnDb);
    }

    public boolean validarPass(String password, String confirmPass) {

        // Validar si la contraseña cumple las condiciones
        String PASS_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern PASS_PATTERN = Pattern.compile(PASS_REGEX);

        Matcher matcher = PASS_PATTERN.matcher(password);
        boolean isValid = matcher.matches();

        // Validar si las contraseñas son iguales
        boolean passMatch = password.equals(confirmPass);

        try {
            if((!isValid) || (!passMatch)) {
                throw new ContraseñaNoCoincide("La contraseña no es valida");
            }
        }catch (ContraseñaNoCoincide e){
            System.out.println(e.getMessage());
        }

        return (passMatch && isValid);
    }


}
