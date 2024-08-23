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

@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class BibliotecarioDataBaseTest {

    @Autowired
    DataSource dataSource;

    @Test
    @DisplayName("Teste da tabela Bibliotecario, se o atributo 'id' é gerado automaticamente")
    void testTabelaBibliotecarioIdGeradoAutomaticamente() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO BIBLIOTECARIO (matricula, nome) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "matricula");
            statement.setString(2, "Hideraldo");
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
    @DisplayName("Teste da tabela Bibliotecario, se o atributo 'nome' possui até 255 caracteres")
    void testTabelaBibliotecarioNomePodePossuir255Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO BIBLIOTECARIO (matricula, nome) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "matricula2");
            statement.setString(2, "a".repeat(255));
            int linhasAfetadas = statement.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Bibliotecario, se o atributo 'nome' não pode possuir mais de 255 caracteres")
    void testTabelaBibliotecarioNomeNaoPodePossuirMaisDe255Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO BIBLIOTECARIO (matricula, nome) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "matricula2");
            statement.setString(2, "a".repeat(256));
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
    @DisplayName("Teste da tabela Biblioteca, se o atributo 'nome' não podendo ser nulo")
    void testTabelaBibliotecarioNomeNaoPodeSerNulo() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO BIBLIOTECARIO (matricula, nome) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "matricula3");
            statement.setString(2, null);
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
    @DisplayName("Teste da tabela Bibliotecario, se o atributo 'matricula' possui até 20 caracteres")
    void testTabelaBibliotecarioMatriculaPodePossuir20Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO BIBLIOTECARIO (matricula, nome) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "a".repeat(20));
            statement.setString(2, "Hideraldo");
            int linhasAfetadas = statement.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Bibliotecario, se o atributo 'matricula' não pode possuir mais de 20 caracteres")
    void testTabelaBibliotecarioMatriculaNaoPodePossuirMaisDe20Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO BIBLIOTECARIO (matricula, nome) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "b".repeat(21));
            statement.setString(2, "Hideraldo");
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
    @DisplayName("Teste da tabela Biblioteca, se o atributo 'matricula' não podendo ser nulo")
    void testTabelaBibliotecarioMatriculaNaoPodeSerNulo() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO BIBLIOTECARIO (matricula, nome) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, null);
            statement.setString(2, "Hideraldo");
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
    @DisplayName("Teste da tabela Bibliotecario, se o atributo 'matricula' não pode ser duplicado")
    void testTabelaBibliotecarioMatriculaNaoPodeSerDuplicado() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO BIBLIOTECARIO (matricula, nome) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "matricula4");
            statement.setString(2, "Hideraldo");
            statement.executeUpdate();
            statement.setString(1, "matricula4");
            statement.setString(2, "Hideraldo");
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
