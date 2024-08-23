package br.org.sesisenai.biblioteca_jpa_template.database;

import br.org.sesisenai.biblioteca_jpa_template.Repositories.AutorRepository;
import br.org.sesisenai.biblioteca_jpa_template.model.Autor;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest
@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class AutorDatabaseTest {

    @Autowired
    private DataSource dataSource;

    @Test
    @DisplayName("Teste da tabela Autor, se o atributo 'id' é gerado automaticamente")
    void testTabelaAutorIdGeradoAutomaticamente() {
//        Prática de testes de banco de dados com ORM (Object Relational Mapping) com objetos.
//        Autor autor = new Autor();
//        autor.setNome("Hideraldo");
//        assertNull(autor.getId());
//        autor = repository.save(autor);
//        assertNotNull(autor.getId());

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO AUTOR (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "Hideraldo");
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
    @DisplayName("Teste da tabela Autor, se o atributo 'nome' não podendo ser nulo")
    void testTabelaAutorNomeNaoPodeSerNulo() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO AUTOR (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
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
    @DisplayName("Teste da tabela Autor, se o atributo 'nome' possui até 255 caracteres")
    void testTabelaAutorNomePodePossuir255Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO AUTOR (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "a".repeat(255));
            int linhasAfetadas = statement.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Autor, se o atributo 'nome' não pode possuir mais de 255 caracteres")
    void testTabelaAutorNomeNaoPodePossuirMaisDe255Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO AUTOR (nome) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "a".repeat(256));
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

}
