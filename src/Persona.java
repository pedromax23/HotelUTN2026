public abstract class Persona {
    private String nombre;
    private String apellido;
    private char sexo;
    private String dni;
    private int edad;
    private String email;
    private String domicilio;
    private int password;

    public Persona(String nombre, String apellido, char sexo, String dni, int edad, String email,String domicilio,int password) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.sexo = sexo;
        this.dni = dni;
        this.edad = edad;
        this.email = email;
        this.domicilio=domicilio;
        this.password=password;
    }
    public Persona (String dni,String email, int contrasenia){
        this.password = contrasenia;
        this.email = email;
        this.dni = dni;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }
}

