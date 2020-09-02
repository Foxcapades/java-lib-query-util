package io.vulpine.lib.query.util.query;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.Query;
import io.vulpine.lib.query.util.QueryResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.vulpine.lib.query.util.TestUtil.mockConnection;
import static io.vulpine.lib.query.util.TestUtil.mockDataSource;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
class QueryImplTest extends QueryBaseTest {
  protected static Field fStmt;
  protected static Field fCloseStatements;

  @BeforeAll
  public static void beforeAll() throws Exception {
    fStmt = QueryImpl.class.getDeclaredField("stmt");
    fStmt.setAccessible(true);

    fCloseStatements = QueryImpl.class.getDeclaredField("closeStatements");
    fCloseStatements.setAccessible(true);

    QueryBaseTest.beforeAll();
  }

  @Nested
  @DisplayName("#this(...)")
  class Constructors {
    @Test
    @DisplayName("set the close statements flag to true")
    void test() throws Exception {
      var obj1 = getTest("", () -> null);
      assertTrue(fCloseStatements.getBoolean(obj1));

      var obj2 = getTest("", mockConnection());
      assertTrue(fCloseStatements.getBoolean(obj2));

      var obj3 = getTest("", mockDataSource());
      assertTrue(fCloseStatements.getBoolean(obj3));
    }
  }

  @Nested
  @DisplayName("#closeStatments(bool)")
  class CloseStatements {
    @Test
    @DisplayName("Sets the close statements flag")
    void doesTheThing() throws Exception {
      var obj = getTarget();
      doCallRealMethod().when(obj).closeStatements(anyBoolean());

      assertSame(obj, obj.closeStatements(false));
      assertFalse(fCloseStatements.getBoolean(obj));
      assertSame(obj, obj.closeStatements(true));
      assertTrue(fCloseStatements.getBoolean(obj));
    }
  }

  @Nested
  @DisplayName("#softCloseStatement()")
  class SoftCloseStatement {
    @Test
    @DisplayName("does not attempt to close a statement if statement closing is disabled")
    void test1() throws Exception {
      var obj = getTarget();
      doCallRealMethod().when(obj).softCloseStatement();

      fCloseStatements.setBoolean(obj, false);
      assertNull(fStmt.get(obj));
      assertDoesNotThrow(obj::softCloseStatement);

      verify(obj, times(0)).closeStatement();
    }

    @Test
    @DisplayName("closes the available statement if statement closing is enabled")
    void test2() throws Exception {
      var obj = getTarget();
      doCallRealMethod().when(obj).softCloseStatement();

      assertNull(fStmt.get(obj));
      fCloseStatements.setBoolean(obj, true);
      assertDoesNotThrow(obj::softCloseStatement);

      verify(obj).closeStatement();
    }

    @Test
    @DisplayName("passes up thrown exception")
    void test3() throws Exception {
      var obj = getTarget();
      doCallRealMethod().when(obj).softCloseStatement();
      doThrow(SQLException.class).when(obj).closeStatement();

      fCloseStatements.setBoolean(obj, true);
      assertThrows(SQLException.class, obj::softCloseStatement);

      verify(obj).closeStatement();
    }
  }

  @Nested
  @DisplayName("#closeStatement()")
  class CloseStatement {
    @Test
    @DisplayName("does not attempt to close a statement if no statement is set")
    void test1() throws Exception {
      var obj = getTarget();
      doCallRealMethod().when(obj).closeStatement();

      assertNull(fStmt.get(obj));
      assertDoesNotThrow(obj::closeStatement);
    }

    @Test
    @DisplayName("closes the available statement if one is set")
    void test2() throws Exception {
      var obj = getTarget();
      doCallRealMethod().when(obj).closeStatement();

      var stm = getMockStatement();

      fStmt.set(obj, stm);
      assertDoesNotThrow(obj::closeStatement);
      verify(stm).close();
    }

    @Test
    @DisplayName("passes up thrown exception")
    void test3() throws Exception {
      var obj = getTarget();
      doCallRealMethod().when(obj).closeStatement();

      var stm = getMockStatement();

      doThrow(new SQLException("test")).when(stm).close();

      fStmt.set(obj, stm);
      assertThrows(SQLException.class, obj::closeStatement);
    }
  }

  @Nested
  @DisplayName("#execute()")
  class Execute {
    @Test
    @DisplayName("throws when #isClosed() returns true")
    void test1() throws Exception {
      var obj = getTarget();
      when(obj.isClosed()).thenReturn(true);
      doCallRealMethod().when(obj).execute();

      assertThrows(IllegalStateException.class, obj::execute);
    }

    @Test
    @DisplayName("passes up exception from #getConnection()")
    void test2() throws Exception  {
      var obj = getTarget();
      doThrow(SQLException.class).when(obj).getConnection();
      doCallRealMethod().when(obj).execute();

      assertThrows(SQLException.class, obj::execute);
    }

    @Test
    @DisplayName("passes up exception from #getStatement(Connection)")
    void test3() throws Exception  {
      var con = mockConnection();

      var obj = getTarget();
      doReturn(con).when(obj).getConnection();
      when(obj.getStatement(con)).thenThrow(SQLException.class);
      doCallRealMethod().when(obj).execute();
      fCon.set(obj, con);
      fShareConnection.setBoolean(obj, true);

      assertThrows(SQLException.class, obj::execute);
    }

    @Test
    @DisplayName("passes up exception from #softCloseConnection()")
    void test5() throws Exception  {
      var stmt = getMockStatement();
      var con  = mockConnection(stmt);

      var obj = getTarget();
      doReturn(con).when(obj).getConnection();
      when(obj.getStatement(con)).thenReturn(stmt);
      doThrow(SQLException.class).when(obj).softCloseConnection();
      doCallRealMethod().when(obj).execute();

      assertThrows(SQLException.class, obj::execute);
    }

    @Test
    @DisplayName("passes up exception from #softCloseStatement()")
    void test6() throws Exception  {
      var stmt = getMockStatement();
      var con = mockConnection(stmt);

      var obj = spy(getTest("", () -> con));
      when(obj.getConnection()).thenReturn(con);
      when(obj.getStatement(con)).thenReturn(stmt);
      doThrow(SQLException.class).when(obj).softCloseStatement();
      doCallRealMethod().when(obj).execute();
      fCon.set(obj, con);
      fShareConnection.setBoolean(obj, true);

      assertThrows(SQLException.class, obj::execute);
    }

    @Test
    @DisplayName("passes up exception from #executeStatement(S)")
    void test7() throws Exception  {
      var stmt = getMockStatement();
      var con = mockConnection(stmt);

      var obj = spy(getTest("", () -> con));
      when(obj.getConnection()).thenReturn(con);
      doReturn(stmt).when(obj).getStatement(con);
      doThrow(Exception.class).when(obj).executeStatement(stmt);
      doCallRealMethod().when(obj).execute();

      assertThrows(Exception.class, obj::execute);
    }

    @Test
    @DisplayName("happy path")
    void test11() throws Exception  {
      var stmt = getMockStatement();
      var con = mockConnection(stmt);
      var res = getResult();

      var obj = getTarget();
      fCon.set(obj, con);
      fShareConnection.setBoolean(obj, true);
      when(obj.getConnection()).thenReturn(con);
      when(obj.getStatement(con)).thenReturn(stmt);
      doReturn(res).when(obj).executeStatement(stmt);
      doCallRealMethod().when(obj).execute();

      assertSame(res, obj.execute());
    }
  }

  @SuppressWarnings("rawtypes")
  protected QueryImpl getTarget() {
    return mock(QueryImpl.class);
  }

  @SuppressWarnings("rawtypes")
  protected QueryResult getResult() {
    return mock(QueryResult.class);
  }

  protected < S extends Statement > QueryImpl < QueryResult < Query < ?, ? > >, S >
  getTest(String sql, ConnectionProvider provider) {
    return new Dummy<>(sql, provider);
  }
  protected < S extends Statement > QueryImpl < QueryResult < Query < ?, ? > >, S >
  getTest(String sql, Connection con) {
    return new Dummy<>(sql, con);
  }
  protected < S extends Statement > QueryImpl < QueryResult < Query < ?, ? > >, S >
  getTest(String sql, DataSource ds) {
    return new Dummy<>(sql, ds);
  }

  @SuppressWarnings("RedundantThrows")
  protected static class Dummy < S extends Statement >
  extends QueryImpl < QueryResult < Query < ?, ? > >, S > {
    public Dummy(String sql, ConnectionProvider fn) { super(sql, fn); }
    public Dummy(String sql, DataSource ds) { super(sql, ds); }
    public Dummy(String sql, Connection cn) { super(sql, cn); }

    protected QueryResult < Query < ?, ? > > executeStatement(Statement stmt) throws Exception { return null; }

    protected S getStatement(Connection cn) throws Exception { return null; }
  }
}
