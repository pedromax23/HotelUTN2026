import java.sql.Connection;
import java.sql.DriverManager;

public class JDBC {
    static void main() {

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306-"
        );

    }
}
