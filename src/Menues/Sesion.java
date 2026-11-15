package Menues;

import java.io.*;

public class Sesion {

    private static final String ARCHIVO = "sesion.txt";

    public static void guardarSesion(String email) {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(ARCHIVO))) {

            bw.write(email);

        } catch (IOException e) {
            System.out.println("Error al guardar la sesion");
        }
    }

    public static String recuperarSesion() {

        try (BufferedReader br = new BufferedReader(
                new FileReader(ARCHIVO))) {

            return br.readLine();

        } catch (IOException e) {
            return null;
        }
    }

    public static void cerrarSesion() {

        try (FileWriter fw = new FileWriter(ARCHIVO)) {
            fw.write("");

        } catch (IOException e) {
            System.out.println("Error al cerrar la sesion");
        }
    }
}
