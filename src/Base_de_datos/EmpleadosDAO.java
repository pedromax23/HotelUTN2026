package Base_de_datos;

import Clases.Cliente;
import Clases.Empleado;
import Interfaces.IPersonaDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmpleadosDAO implements IPersonaDAO {

    public static void registrarEmpleado(String dni, String email, int contrasenia) {

        String sql = "INSERT INTO empleado (dni, email, contrasenia) VALUES (?, ?, ?)";

        try (Connection conexion = Base_De_Datos.CreaConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)){

            ps.setString(1, dni);
            ps.setString(2, email);
            ps.setInt(3, contrasenia);
            ps.executeUpdate();
            System.out.println("Empleado Registrado");

        } catch (SQLException ex) {

            Logger.getLogger(Base_De_Datos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al registrar el Empleado");

        }
    }

    public static void registrarDatosEmpleado(String dni, String nombre, String apellido, char sexo, int edad, String domicilio, double sueldo) {

        String sql = "UPDATE empleado SET nombre = ?, apellido = ?, sexo = ?, edad = ?, domicilio = ?, sueldo = ? WHERE dni = ?";

        try (Connection conexion = Base_De_Datos.CreaConexion();
             PreparedStatement ps = conexion.prepareStatement(sql);){


            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, String.valueOf(sexo));
            ps.setInt(4, edad);
            ps.setString(5, domicilio);
            ps.setDouble(6, sueldo);
            ps.setString(7, dni);

            int filasActualizadas = ps.executeUpdate();

            if (filasActualizadas > 0) {
                System.out.println("Se guardaron los datos");

            } else {
                System.out.println("El dni no coincide.");

            }

        } catch (SQLException ex) {

            Logger.getLogger(Base_De_Datos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al actualizar los datos.");

        }
    }

    public static Empleado buscarPorEmail(String email) {
        char sexo;

        String sql = "SELECT * FROM cliente WHERE email = ?";

        try (Connection conexion = Base_De_Datos.CreaConexion();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    if (rs.getString("sexo") != null) {
                        sexo = rs.getString("sexo").charAt(0);
                    } else {
                        sexo = '\0';
                    }

                    return new Empleado(
                            rs.getString("nombre"),
                            rs.getString("apellido"),
                            sexo,
                            rs.getString("dni"),
                            rs.getInt("edad"),
                            rs.getString("email"),
                            rs.getString("domicilio"),
                            rs.getInt("contrasenia"),
                            rs.getDouble("sueldo")
                    ) {
                    };

                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuariosDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

        return null;
    }

    @Override
    public boolean noExisteDni(String dniIngresado) {

        String sql = ("SELECT dni FROM cliente WHERE dni=?");

        try (Connection conexion = Base_De_Datos.CreaConexion();

             PreparedStatement ps = conexion.prepareStatement(sql);) {
             ps.setString(1, dniIngresado);

             try (ResultSet rs = ps.executeQuery()) {
                 return !rs.next();
             }

        } catch (SQLException ex) {

            Logger.getLogger(Base_De_Datos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error al verificar el DNI ");

        }
        return false;

    }

}
