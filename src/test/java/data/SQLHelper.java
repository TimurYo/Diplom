package data;

import SQLMaps.PaymentMap;
import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class SQLHelper {

    private static QueryRunner runner = new QueryRunner();
    private static Connection conn;

    private SQLHelper() {
    }

    private static Connection getConnection() {
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/app", "app", "pass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void cleanDB() {
        var creditRequest = "DELETE FROM credit_request_entity";
        var order = "DELETE FROM order_entity";
        var payment = "DELETE FROM payment_entity";
        try (var conn = getConnection()) {
            runner.update(conn, creditRequest);
            runner.update(conn, order);
            runner.update(conn, payment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PaymentMap getPaymentEntity() {
        var amountSQL = "SELECT * FROM payment_entity";
        try(var conn = getConnection()) {
            runner.query(conn,amountSQL,new BeanHandler<>(PaymentMap.class));
            return new PaymentMap();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public static String getStatusWithPayment() {
        var amountSQL = "SELECT * FROM payment_entity";
        try(var conn = getConnection()) {
            var status = runner.query(conn,amountSQL,new BeanHandler<>(PaymentMap.class));
            return status.getStatus();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}


