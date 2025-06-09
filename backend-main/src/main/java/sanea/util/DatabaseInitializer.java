package sanea.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;
import java.util.stream.Collectors;
import sanea.dao.MySqlConnection;

public class DatabaseInitializer {
    public static void initialize() {
        try {
            // Lê o arquivo schema.sql
            String schema = new BufferedReader(
                new InputStreamReader(DatabaseInitializer.class.getResourceAsStream("/schema.sql"))
            ).lines().collect(Collectors.joining("\n"));

            // Divide o schema em comandos individuais
            String[] commands = schema.split(";");

            // Executa cada comando
            try (Connection conn = MySqlConnection.conectar();
                 Statement stmt = conn.createStatement()) {
                
                for (String command : commands) {
                    if (!command.trim().isEmpty()) {
                        stmt.execute(command);
                    }
                }
                
                System.out.println("Banco de dados inicializado com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao inicializar banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
} 