package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.vulpine.lib.query.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PreparedVoidQueryTest extends VoidQueryBaseTest
{
  @BeforeAll
  public static void beforeAll() throws Exception {
    VoidQueryBaseTest.beforeAll();
  }

  @Nested
  @DisplayName("#getStatement(Connection)")
  class GetStatement {
    @Test
    @DisplayName("passes up Connection#prepareStatement exceptions")
    void test1() throws Exception {
      var con = mockConnection();
      var obj = spy(getTest("", () -> con));

      doThrow(SQLException.class).when(con).prepareStatement(anyString());

      assertThrows(SQLException.class, () -> obj.getStatement(con));
      verify(con).prepareStatement(anyString());
      verify(obj, times(0)).prepareStatement(any());
    }

    @Test
    @DisplayName("passes up #prepareStatement exceptions")
    void test2() throws Exception {
      var stmt = getMockStatement();
      var con = mockConnection(stmt);
      var obj = spy(getTest("", () -> con));

      doThrow(SQLException.class).when(obj).prepareStatement(any());

      assertThrows(SQLException.class, () -> obj.getStatement(con));
      verify(con).prepareStatement(anyString());
      verify(obj).prepareStatement(stmt);
    }

    @Test
    @DisplayName("returns created statement")
    void test3() throws Exception {
      var stmt = getMockStatement();
      var con = mockConnection(stmt);
      var obj = spy(getTest("", () -> con));

      assertSame(stmt, obj.getStatement(con));
      verify(con).prepareStatement(anyString());
      verify(obj).prepareStatement(stmt);
    }
  }

  @Nested
  @DisplayName("#executeStatement(PreparedStatement)")
  class ExecuteStatement {
    @Test
    @DisplayName("passes up PreparedStatement#execute exceptions")
    void test1() throws Exception {
      var stmt = getMockStatement();
      var obj = spy(getTest("", () -> null));

      doThrow(SQLException.class).when(stmt).execute();

      assertThrows(SQLException.class, () -> obj.executeStatement(stmt));
      verify(stmt).execute();
    }

    @Test
    @DisplayName("happy path")
    void test2() throws Exception {
      var stmt = getMockStatement();
      var obj = spy(getTest("", () -> null));

      assertDoesNotThrow(() -> obj.executeStatement(stmt));
      verify(stmt).execute();
    }
  }

  @Override
  protected PreparedStatement getMockStatement() {
    return mockPreparedStatement();
  }

  @Override
  protected PreparedVoidQuery getTest(String sql, ConnectionProvider fn) {
    return new Dummy(sql, fn);
  }

  @Override
  protected PreparedVoidQuery getTest(String sql, Connection con) {
    return new Dummy(sql, con);
  }

  @Override
  protected PreparedVoidQuery getTest(String sql, DataSource ds) {
    return new Dummy(sql, ds);
  }

  private static class Dummy extends PreparedVoidQuery {
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
    public PreparedStatement getStatement(Connection cn) throws Exception {
      return super.getStatement(cn);
    }

    @Override
    public void executeStatement(PreparedStatement stmt) throws Exception {
      super.executeStatement(stmt);
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
    public void prepareStatement(PreparedStatement st) throws Exception {}
  }
}
