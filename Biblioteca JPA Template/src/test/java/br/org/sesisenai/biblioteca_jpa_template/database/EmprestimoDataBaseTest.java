package br.org.sesisenai.biblioteca_jpa_template.database;

import br.org.sesisenai.biblioteca_jpa_template.Repositories.EmprestimoRepository;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:test.properties")
public class EmprestimoDataBaseTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    EmprestimoRepository emprestimoRepository;

    String sql = "INSERT INTO EMPRESTIMO (DATA_EMPRESTIMO, DATA_DEVOLUCAO, STATUS) VALUES (?,?,?)";

    @Test
    @DisplayName("Teste da tabela Emprestimo, se o atributo 'id' é gerado automaticamente")
    void testTabelaEmprestimoDataEmprestimoIdNaoPodeSerNulo() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setDate(2, Date.valueOf(LocalDate.now().plusDays(7)));
            statement.setString(3, "1");
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
    @DisplayName("Teste da tabela Emprestimo, se o atributo 'data emprestimo' não podendo ser nulo")
    void testTabelaEmprestimoDataEmprestimoNaoPodeSerNulo() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, null);
            statement.setDate(2, Date.valueOf(LocalDate.now()));
            statement.setString(3, "1");
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
    @DisplayName("Teste da tabela Emprestimo, se o atributo 'data devolucao' não podendo ser nulo")
    void testTabelaEmprestimoDevolucaoNaoPodeSerNulo() {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setDate(2, null);
            statement.setString(3, "1");
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
    @DisplayName("Teste da tabela Emprestimo, se o atributo 'status' possui até 20 caracteres")
    void testTabelaEmprestimoStatusPodePossuir20Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setDate(2, Date.valueOf(LocalDate.now().plusDays(7)));
            statement.setString(3, "a".repeat(20));
            int linhasAfetadas = statement.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Emprestimo, se o atributo 'status' não pode possuir mais de 20 caracteres")
    void testTabelaEmprestimoStatusNaoPodePossuirMaisDe20Caracteres() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setDate(2, Date.valueOf(LocalDate.now().plusDays(7)));
            statement.setString(3, "a".repeat(21));
            statement.executeUpdate();
            fail();
        } catch (MysqlDataTruncation e) {
            e.printStackTrace();
            assertEquals("22001", e.getSQLState());
        } catch (SQLException e) {
            fail();
        }
    }

    @Test
    @DisplayName("Teste da tabela Emprestimo, se o atributo 'status' pode ser nulo")
    void testTabelaEmprestimoStatusPodeSerNulo() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(LocalDate.now()));
            statement.setDate(2, Date.valueOf(LocalDate.now().plusDays(7)));
            statement.setString(3, null);
            int linhasAfetadas = statement.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Emprestimo, se o atributo 'exemplar' pode referenciar um livro")
    void testTabelaEmprestimoExemplarPodeReferenciarUmLivro() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statementExemplar = connection.prepareStatement("INSERT INTO  EXEMPLAR (CODIGO_BARRA, LIVRO_ID) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS);
            statementExemplar.setString(1, "10");
            statementExemplar.setLong(2, 1);
            int linhasAfetadas = statementExemplar.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Emprestimo, se o atributo 'exemplar' pode referenciar um exemplar")
    void testTabelaEmprestimoExemplarPodeReferenciarUmExemplar() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statementLivro = connection.prepareStatement("INSERT INTO LIVRO (TITULO, ISBN, ANO_PUBLICACAO) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statementLivro.setString(1, "Livro");
            statementLivro.setString(2, "20");
            statementLivro.setInt(3, 2021);
            statementLivro.executeUpdate();
            ResultSet resultSet = statementLivro.getGeneratedKeys();
            Long idLivro = null;
            if (resultSet.next()) {
                idLivro = resultSet.getLong(1);
                System.out.println("livro " + idLivro);
            }

            PreparedStatement statementExemplar = connection.prepareStatement("INSERT INTO  EXEMPLAR (CODIGO_BARRAS, LIVRO_ID, DISPONIVEL) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statementExemplar.setString(1, "10");
            statementExemplar.setLong(2, idLivro);
            statementExemplar.setBoolean(3, true);
            statementExemplar.executeUpdate();
            ResultSet resultSet2 = statementExemplar.getGeneratedKeys();
            Long idExemplar = null;
            if (resultSet2.next()) {
                idExemplar = resultSet2.getLong(1);
                System.out.println("exemplar " + idExemplar);
            }
            PreparedStatement statementEmprestimo = connection.prepareStatement("INSERT INTO EMPRESTIMO (DATA_EMPRESTIMO, DATA_DEVOLUCAO, STATUS, EXEMPLAR_ID) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statementEmprestimo.setDate(1, Date.valueOf(LocalDate.now()));
            statementEmprestimo.setDate(2, Date.valueOf(LocalDate.now().plusDays(7)));
            statementEmprestimo.setString(3, null);
            statementEmprestimo.setLong(4, idExemplar);
            int linhasAfetadas = statementEmprestimo.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getSQLState());
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Emprestimo, se o atributo 'cliente' pode referenciar um cliente")
    void testTabelaEmprestimoClientePodeReferenciarUmCliente() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO CLIENTE (cpf, email, nome) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "10");
            statement.setString(2, "emailzada@email.com");
            statement.setString(3, "Hideraldo");
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            Long idCliente = null;
            if (resultSet.next()) {
                idCliente = resultSet.getLong(1);
            }
            PreparedStatement statementEmprestimo = connection.prepareStatement("INSERT INTO EMPRESTIMO (DATA_EMPRESTIMO, DATA_DEVOLUCAO, STATUS, CLIENTE_ID) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statementEmprestimo.setDate(1, Date.valueOf(LocalDate.now()));
            statementEmprestimo.setDate(2, Date.valueOf(LocalDate.now().plusDays(7)));
            statementEmprestimo.setString(3, null);
            statementEmprestimo.setLong(4, idCliente);
            int linhasAfetadas = statementEmprestimo.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @DisplayName("Teste da tabela Emprestimo, se o atributo 'bibliotecario' pode referenciar um bibliotecario")
    void testTabelaEmprestimoBibliotecarioPodeReferenciarUmBibliotecario() {
        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO BIBLIOTECARIO (matricula, nome) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, "matriculazinha");
            statement.setString(2, "Hideraldo");
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            Long idBibliotecario = null;
            if (resultSet.next()) {
                idBibliotecario = resultSet.getLong(1);
            }
            PreparedStatement statementEmprestimo = connection.prepareStatement("INSERT INTO EMPRESTIMO (DATA_EMPRESTIMO, DATA_DEVOLUCAO, STATUS, BIBLIOTECARIO_ID) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            statementEmprestimo.setDate(1, Date.valueOf(LocalDate.now()));
            statementEmprestimo.setDate(2, Date.valueOf(LocalDate.now().plusDays(7)));
            statementEmprestimo.setString(3, null);
            statementEmprestimo.setLong(4, idBibliotecario);
            int linhasAfetadas = statementEmprestimo.executeUpdate();
            assertEquals(1, linhasAfetadas);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
