package br.org.sesisenai.biblioteca_jpa_template.database;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class ClienteDataBaseTest {

    @Autowired
    DataSource dataSource;

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'id' é gerado automaticamente")
    void testTabelaClienteIdGeradoAutomaticamente() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "111.111.111-11");
            statement.setString(2, "email@email.com");
            statement.setString(3, "Hideraldo");
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                assertNotNull(id);
                System.out.println("ID: " + id);
            } else {
                fail();
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'nome' possui até 255 caracteres")
    void testTabelaClienteNomePodePossuir255Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "222.222.222-22");
            statement.setString(2, "email2@email.com");
            statement.setString(3, "a".repeat(255));
            int linhasAfetadas = statement.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'nome' não pode possuir mais de 255 caracteres")
    void testTabelaClienteNomeNaoPodePossuirMaisDe255Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "333.333.333-33");
            statement.setString(2, "email3@email.com");
            statement.setString(3, "b".repeat(256));
            statement.executeUpdate();
            fail();
        } catch (MysqlDataTruncation e) {
            e.printStackTrace();
            assertTrue(true);
        } catch (SQLException e) {
            assertEquals("22001", e.getSQLState());
            fail();
        }
    }

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'nome' não podendo ser nulo")
    void testTabelaClienteNomeNaoPodeSerNulo() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "444.444.444-44");
            statement.setString(2, "email4@email.com");
            statement.setString(3, null);
            statement.executeUpdate();
            fail();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            assertEquals("23000", e.getSQLState());
        } catch (SQLException e) {
            fail();

        }
    }

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'cpf' possui até 14 caracteres")
    void testTabelaCLienteCpfPodePossuir14Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "a".repeat(14));
            statement.setString(2, "email5@email.com");
            statement.setString(3, "Hideraldo");
            int linhasAfetadas = statement.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'cpf' não pode possuir mais de 14 caracteres")
    void testTabelaClienteCpfNaoPodePossuirMaisDe14Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "a".repeat(15));
            statement.setString(2, "email6@email.com");
            statement.setString(3, "Hideraldo");
            statement.executeUpdate();
            fail();
        } catch (MysqlDataTruncation e) {
            e.printStackTrace();
            assertTrue(true);
        } catch (SQLException e) {
            assertEquals("22001", e.getSQLState());
            fail();
        }
    }

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'cpf' não podendo ser nulo")
    void testTabelaClienteCpfNaoPodeSerNulo() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, null);
            statement.setString(2, "email7@email.com");
            statement.setString(3, "Hideraldo");
            statement.executeUpdate();
            fail();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            assertEquals("23000", e.getSQLState());
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'cpf' não pode ser duplicado")
    void testTabelaClienteCpfNaoPodeSerDuplicado() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "555.555.555-55");
            statement.setString(2, "email8@email.com");
            statement.setString(3, "Hideraldo");
            statement.executeUpdate();
            statement.executeUpdate();
            fail();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            assertEquals("23000", e.getSQLState());
        } catch (SQLException e) {
            fail(e.getMessage());
        }

    }
    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'email' possui até 255 caracteres")
    void testTabelaCLienteEmailPodePossuir255Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "666.666.666-66");
            statement.setString(2, "a".repeat(255));
            statement.setString(3, "Hideraldo");
            int linhasAfetadas = statement.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'email' não pode possuir mais de 255 caracteres")
    void testTabelaClienteEmailNaoPodePossuirMaisDe255Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "777.777.777-77");
            statement.setString(2, "a".repeat(256));
            statement.setString(3, "Hideraldo");
            statement.executeUpdate();
            fail();
        } catch (MysqlDataTruncation e) {
            e.printStackTrace();
            assertTrue(true);
        } catch (SQLException e) {
            assertEquals("22001", e.getSQLState());
            fail();
        }
    }

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'email' não podendo ser nulo")
    void testTabelaClienteEmailNaoPodeSerNulo() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "888.888.888-88");
            statement.setString(2, null);
            statement.setString(3, "Hideraldo");
            statement.executeUpdate();
            fail();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            assertEquals("23000", e.getSQLState());
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    @DisplayName("Teste da tabela Cliente, se o atributo 'email' não pode ser duplicado")
    void testTabelaClienteEmailNaoPodeSerDuplicado() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "999.999.999-99");
            statement.setString(2, "email9@email.com");
            statement.setString(3, "Hideraldo");
            statement.executeUpdate();
            statement.executeUpdate();
            fail();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            assertEquals("23000", e.getSQLState());
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }
}
