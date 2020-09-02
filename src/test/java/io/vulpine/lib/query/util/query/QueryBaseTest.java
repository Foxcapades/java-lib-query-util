package io.vulpine.lib.query.util.query;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.vulpine.lib.query.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class QueryBaseTest
{
  protected static Field fCanCloseConnection;

  protected static Field fShareConnection;

  protected static Field fClosed;

  protected static Field fCon;

  @BeforeAll
  public static void beforeAll() throws Exception {
    fCanCloseConnection = QueryBase.class.getDeclaredField("canCloseConnection");
    fCanCloseConnection.setAccessible(true);

    fShareConnection = QueryBase.class.getDeclaredField("shareConnection");
    fShareConnection.setAccessible(true);

    fClosed = QueryBase.class.getDeclaredField("closed");
    fClosed.setAccessible(true);

    fCon = QueryBase.class.getDeclaredField("con");
    fCon.setAccessible(true);
  }

  @Nested
  @DisplayName("#this(String, ConnectionProvider)")
  class Constructor1 {
    @Test
    @DisplayName("throws when sql is null")
    void throwsOnNullSql() {
      assertThrows(NullPointerException.class,
        () -> getTest(null, () -> null));
    }

    @Test
    @DisplayName("throws when provider is null")
    void throwsOnNullProvider() {
      assertThrows(NullPointerException.class,
        () -> getTest("", (ConnectionProvider) null));
    }

    @Test
    @DisplayName("sets closable flag to true")
    void setsClosableFlag() throws Exception {
      var obj = getTest("", () -> null);
      assertTrue(fCanCloseConnection.getBoolean(obj));
      assertFalse(fShareConnection.getBoolean(obj));
    }
  }

  @Nested
  @DisplayName("#this(String, Connection)")
  class Constructor2 {
    @Test
    @DisplayName("throws when sql is null")
    void throwsOnNullSql() {
      assertThrows(NullPointerException.class,
        () -> getTest(null, mockConnection()));
    }

    @Test
    @DisplayName("throws when connection is null")
    void throwsOnNullProvider() {
      assertThrows(NullPointerException.class,
        () -> getTest("", (Connection) null));
    }

    @Test
    @DisplayName("sets connection sharing flag to true")
    void setsClosableFlag() throws Exception {
      var obj = getTest("", mockConnection());
      assertFalse(fCanCloseConnection.getBoolean(obj));
      assertTrue(fShareConnection.getBoolean(obj));
    }
  }


  @Nested
  @DisplayName("#this(String, DataSource)")
  class Constructor3 {
    @Test
    @DisplayName("throws when sql is null")
    void throwsOnNullSql() {
      assertThrows(NullPointerException.class,
        () -> getTest(null, mockDataSource()));
    }

    @Test
    @DisplayName("throws when connection is null")
    void throwsOnNullProvider() {
      assertThrows(NullPointerException.class,
        () -> getTest("", (DataSource) null));
    }

    @Test
    @DisplayName("sets closable flag to true")
    void setsClosableFlag() throws Exception {
      var obj = getTest("", mockDataSource());
      assertTrue(fCanCloseConnection.getBoolean(obj));
      assertFalse(fShareConnection.getBoolean(obj));
    }
  }

  @Nested
  @DisplayName("#getSql()")
  class GetSql {
    @Test
    @DisplayName("returns sql configured from constructor 1")
    void happyConstructor1() {
      var testSql = "hello";
      var object = new Dummy <>(testSql, () -> null);

      assertEquals(testSql, object.getSql());
    }

    @Test
    @DisplayName("returns sql configured from constructor 2")
    void happyConstructor2() {
      var testSql = "hello";
      var object = new Dummy <>(testSql, mockConnection());

      assertEquals(testSql, object.getSql());
    }

    @Test
    @DisplayName("returns sql configured from constructor 3")
    void happyConstructor3() {
      var testSql = "hello";
      var object = new Dummy <>(testSql, mockDataSource());

      assertEquals(testSql, object.getSql());
    }
  }

  @Nested
  @DisplayName("#close()")
  class Close {
    @Test
    @DisplayName("marks the instance as closed when no connection is present")
    void closeWithNoCon() throws Exception {
      var obj = getTest("", () -> null);

      assertFalse(fClosed.getBoolean(obj));
      obj.close();
      assertTrue(fClosed.getBoolean(obj));
    }

    @Test
    @DisplayName("calls Connection#close when a connection is present")
    void closeWithConHappy() throws Exception {
      var obj = getTest("", () -> null);
      var con = mockConnection();

      assertFalse(fClosed.getBoolean(obj));
      fCon.set(obj, con);
      obj.close();
      Mockito.verify(con).close();
      assertTrue(fClosed.getBoolean(obj));
    }

    @Test
    @DisplayName("throws an exception when Connection#close throws")
    void closeWithConAngry() throws Exception {
      var obj = getTest("", () -> null);
      var con = mockConnection();

      Mockito.doThrow(new SQLException("test")).when(con).close();

      assertFalse(fClosed.getBoolean(obj));
      fCon.set(obj, con);
      assertThrows(SQLException.class, obj::close);
    }
  }

  @Nested
  @DisplayName("#shareConnection(bool)")
  class ShareConnection {
    @Test
    @DisplayName("updates the connection sharing flag when a connection provider is given.")
    void closeEnabled() throws Exception {
      var obj = getTest("", () -> null);

      assertFalse(fShareConnection.getBoolean(obj));
      assertSame(obj, obj.shareConnection(true));
      assertTrue(fShareConnection.getBoolean(obj));
      assertSame(obj, obj.shareConnection(false));
      assertFalse(fShareConnection.getBoolean(obj));
    }

    @Test
    @DisplayName("does not update the connection sharing flag when a single connection is given.")
    void closeDisabled() throws Exception {
      var obj = getTest("", mockConnection());

      assertTrue(fShareConnection.getBoolean(obj));
      assertSame(obj, obj.shareConnection(false));
      assertTrue(fShareConnection.getBoolean(obj));
      assertSame(obj, obj.shareConnection(false));
      assertTrue(fShareConnection.getBoolean(obj));
    }
  }

  @Nested
  @DisplayName("#isClosed()")
  class IsClosed {
    @Test
    @DisplayName("returns the current closed value")
    void isClosed() throws Exception {
      var obj = getTest("", () -> null);

      assertFalse(fClosed.getBoolean(obj));
      assertFalse(obj.isClosed());
      fClosed.setBoolean(obj, true);
      assertTrue(fClosed.getBoolean(obj));
      assertTrue(obj.isClosed());
    }
  }

  @Nested
  @DisplayName("#softCloseConnection()")
  class SoftCloseConnection {
    @Test
    @DisplayName("does nothing if shareConnection is true")
    void withShareConnection() throws Exception {
      var con = mockConnection();
      var obj = getTest("", con);

      fShareConnection.setBoolean(obj, true);
      fCon.set(obj, con);
      obj.softCloseConnection();

      Mockito.verifyZeroInteractions(con);
      assertSame(con, fCon.get(obj));
    }

    @Test
    @DisplayName("closes and nulls the connection if shareConnection is false")
    void withoutShareConnection() throws Exception {
      var con = mockConnection();
      var obj = getTest("", con);

      fShareConnection.setBoolean(obj, false);
      fCon.set(obj, con);
      obj.softCloseConnection();

      Mockito.verify(con).close();
      assertNull(fCon.get(obj));
    }
  }

  @Nested
  @DisplayName("#getConnection()")
  class GetConnection {
    @Test
    @DisplayName("sets the con field when it is null")
    void setsOnNull() throws Exception {
      var con = mockConnection();
      var obj = getTest("", () -> con);

      assertNull(fCon.get(obj));
      assertSame(con, obj.getConnection());
      assertSame(con, fCon.get(obj));
    }

    @Test
    @DisplayName("throws an exception when the con field is set but sharing is disabled")
    void throwsOnNoShare() throws Exception {
      var con1 = mockConnection();
      var obj = getTest("", () -> {
        throw new RuntimeException("shouldn't get here");
      });

      assertNull(fCon.get(obj));
      fCon.set(obj, con1);
      assertSame(con1, fCon.get(obj));

      assertFalse(fShareConnection.getBoolean(obj));
      assertThrows(IllegalStateException.class, obj::getConnection);
    }

    @Test
    @DisplayName("returns the previous connection when set and sharing is enabled")
    void returnsSameOnShare() throws Exception {
      var con1 = mockConnection();
      var obj = getTest("", () -> {
        throw new RuntimeException("shouldn't get here");
      });

      assertNull(fCon.get(obj));
      fCon.set(obj, con1);
      assertSame(con1, fCon.get(obj));

      fShareConnection.setBoolean(obj, true);
      assertTrue(fShareConnection.getBoolean(obj));
      assertSame(con1, obj.getConnection());
    }


    @Test
    @DisplayName("throws if the provider returns a null value")
    void throwsOnNullConnection() throws Exception {
      var obj = getTest("", () -> null);

      assertNull(fCon.get(obj));
      assertThrows(NullPointerException.class, obj::getConnection);
    }
  }

  protected Statement getMockStatement() {
    return mockStatement();
  }

  protected < S extends Statement > QueryBase < S > getTest(String sql, ConnectionProvider provider) {
    return new Dummy <>(sql, provider);
  }
  protected < S extends Statement > QueryBase < S > getTest(String sql, Connection con) {
    return new Dummy <>(sql, con);
  }
  protected < S extends Statement > QueryBase < S > getTest(String sql, DataSource ds) {
    return new Dummy <>(sql, ds);
  }

  @SuppressWarnings("RedundantThrows")
  protected static class Dummy < S extends Statement >
  extends QueryBase < S >
  {
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

    protected S getStatement(Connection cn) throws Exception { return null; }
  }
}
