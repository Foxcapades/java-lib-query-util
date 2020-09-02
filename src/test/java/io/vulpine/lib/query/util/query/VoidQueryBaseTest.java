package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.vulpine.lib.query.util.TestUtil.mockConnection;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("ResultOfMethodCallIgnored")
class VoidQueryBaseTest extends QueryBaseTest
{
  @BeforeAll
  public static void beforeAll() throws Exception {
    QueryBaseTest.beforeAll();
  }

  @Nested
  @DisplayName("#execute()")
  class Execute {
    @Test
    @DisplayName("throws if query is closed")
    void test1() throws Exception {
      var obj = spy(getTest("", () -> null));
      fClosed.setBoolean(obj, true);

      assertThrows(IllegalStateException.class, obj::execute);
      verify(obj).isClosed();
      verify(obj, times(0)).getConnection();
      verify(obj, times(0)).getStatement(any());
      verify(obj, times(0)).executeStatement(any());
      verify(obj, times(0)).softCloseConnection();
    }

    @Test
    @DisplayName("passes up #getConnection exceptions")
    void test2() throws Exception {
      var obj = spy(getTest("", () -> { throw new SQLException(); }));

      assertThrows(SQLException.class, obj::execute);
      verify(obj).isClosed();
      verify(obj).getConnection();
      verify(obj, times(0)).getStatement(any());
      verify(obj, times(0)).executeStatement(any());
      verify(obj, times(0)).softCloseConnection();
    }

    @Test
    @DisplayName("passes up #getStatement exceptions")
    void test3() throws Exception {
      var stmt = getMockStatement();
      var con = mockConnection(stmt);
      var obj = spy(getTest("", () -> con));

      doReturn(stmt).when(obj).getStatement(con);
      doThrow(SQLException.class).when(obj).getStatement(any());

      assertThrows(SQLException.class, obj::execute);
      verify(obj).isClosed();
      verify(obj).getConnection();
      verify(obj).getStatement(con);
      verify(obj, times(0)).executeStatement(any());
      verify(obj).softCloseConnection();
    }

    @Test
    @DisplayName("passes up #executeStatement exceptions")
    void test4() throws Exception {
      var stmt = getMockStatement();
      var con = mockConnection(stmt);
      var obj = spy(getTest("", () -> con));

      doReturn(stmt).when(obj).getStatement(con);
      doThrow(SQLException.class).when(obj).executeStatement(any());

      assertThrows(SQLException.class, obj::execute);
      verify(obj).isClosed();
      verify(obj).getConnection();
      verify(obj).getStatement(con);
      verify(obj).executeStatement(stmt);
      verify(obj).softCloseConnection();
    }


    @Test
    @DisplayName("passes up #softCloseConnection exceptions")
    void test5() throws Exception {
      var stmt = getMockStatement();
      var con = mockConnection(stmt);
      var obj = spy(getTest("", () -> con));

      doReturn(stmt).when(obj).getStatement(con);
      doThrow(SQLException.class).when(con).close();

      assertThrows(SQLException.class, obj::execute);
      verify(obj).isClosed();
      verify(obj).getConnection();
      verify(obj).getStatement(con);
      verify(obj).executeStatement(stmt);
      verify(obj).softCloseConnection();
    }

    @Test
    @DisplayName("happy path")
    void test6() throws Exception {
      var stmt = getMockStatement();
      var con = mockConnection(stmt);
      var obj = spy(getTest("", () -> con));

      doReturn(stmt).when(obj).getStatement(con);

      assertDoesNotThrow(obj::execute);
      verify(obj).isClosed();
      verify(obj).getConnection();
      verify(obj).getStatement(con);
      verify(obj).executeStatement(stmt);
      verify(obj).softCloseConnection();
    }
  }

  @Override
  protected < S extends Statement > VoidQueryBase < S > getTest(String sql, ConnectionProvider fn) {
    return new Dummy<>(sql, fn);
  }

  @Override
  protected  < S extends Statement > VoidQueryBase < S > getTest(String sql, Connection con) {
    return new Dummy<>(sql, con);
  }

  @Override
  protected  < S extends Statement > VoidQueryBase < S > getTest(String sql, DataSource ds) {
    return new Dummy<>(sql, ds);
  }

  private static class Dummy < S extends Statement > extends VoidQueryBase < S > {
    public Dummy(String sql, ConnectionProvider provider) {
      super(sql, provider);
    }

    public Dummy(String sql, Connection con) {
      super(sql, con);
    }

    public Dummy(String sql, DataSource ds) {
      super(sql, ds);
    }

    @Override
    public Connection getConnection() throws Exception {
      return super.getConnection();
    }

    @Override
    public void softCloseConnection() throws Exception {
      super.softCloseConnection();
    }

    @Override
    protected void executeStatement(Statement stmt) throws Exception {
      stmt.execute(getSql());
    }

    @Override
    @SuppressWarnings("unchecked")
    protected S getStatement(Connection cn) throws Exception {
      return (S) cn.createStatement();
    }
  }
}
