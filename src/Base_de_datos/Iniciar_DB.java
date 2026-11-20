package Base_de_datos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Iniciar_DB {

    private static final String RUTA_SCRIPT = "./schema.sql";

    public static void inicializar() {
        System.out.println("Verificando e inicializando la base de datos...");

        try (Connection conexion = ConexionDB.obtenerConexion();
             Statement statement = conexion.createStatement();
             BufferedReader br = new BufferedReader(new FileReader(RUTA_SCRIPT))) {

            StringBuilder sqlBuilder = new StringBuilder();
            String linea;

            while ((linea = br.readLine()) != null) {
                // Ignorar comentarios SQL y líneas vacías
                linea = linea.trim();
                if (linea.startsWith("--") || linea.startsWith("//") || linea.isEmpty()) {
                    continue;
                }

                sqlBuilder.append(linea);

                // Si encontramos un punto y coma, ejecutamos la sentencia acumulada
                if (linea.endsWith(";")) {
                    String sql = sqlBuilder.toString().replace(";", "").trim();
                    if (!sql.isEmpty()) {
                        statement.execute(sql);
                    }
                    sqlBuilder.setLength(0); // Limpiar buffer para la siguiente sentencia
                }
            }

            System.out.println("Base de datos inicializada correctamente.");

        } catch (IOException e) {
            System.err.println("Error al leer el archivo de script SQL: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error de SQL durante la inicialización: " + e.getMessage());
        }
    }
}