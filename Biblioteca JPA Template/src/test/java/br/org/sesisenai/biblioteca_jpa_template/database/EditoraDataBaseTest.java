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
public class EditoraDataBaseTest {

    @Autowired
    DataSource dataSource;

    @Test
    @DisplayName("Teste da tabela Editora, se o atributo 'id' é gerado automaticamente")
    void testTabelaEditoraIdGeradoAutomaticamente() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO EDITORA (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "Alvinho");
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
    @DisplayName("Teste da tabela Editora, se o atributo 'nome' não podendo ser nulo")
    void testTabelaEditoraNomeNaoPodeSerNulo() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO EDITORA (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, null);
            statement.executeUpdate();
            fail();
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            assertTrue(true);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Editora, se o atributo 'nome' possui até 100 caracteres")
    void testTabelaEditoraNomePodePossuir100Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO EDITORA ( nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "a".repeat(100));
            int linhasAfetadas = statement.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Editora, se o atributo 'nome' não pode possuir mais de 100 caracteres")
    void testTabelaEditoraNomeNaoPodePossuirMaisDe100Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO EDITORA (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "a".repeat(101));
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
    @DisplayName("Teste da tabela Editora, se o atributo 'nome' não pode ser duplicado")
    void testTabelaEditoraNomeNaoPodeSerDuplicado() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO EDITORA (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "Godzila");
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
