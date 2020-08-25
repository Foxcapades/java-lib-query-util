package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.ReadResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.vulpine.lib.query.util.TestUtil.mockResultSet;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
class ReadQueryImplTest extends QueryImplTest
{
  @BeforeAll
  public static void beforeAll() throws Exception {
    QueryImplTest.beforeAll();
  }

  @Nested
  @DisplayName("#toResult(S)")
  class ToResult {
    @Test
    @DisplayName("passes up #getResultSet(S) exceptions")
    void test1() throws Exception {
      var stmt = getMockStatement();

      var obj  = getTarget();
      doThrow(SQLException.class).when(obj).getResultSet(stmt);
      doCallRealMethod().when(obj).toResult(stmt);

      assertThrows(SQLException.class, () -> obj.toResult(stmt));
    }

    @Test
    @DisplayName("passes up #parseResult(ResultSet) exceptions")
    void test2() throws Exception {
      var res  = mockResultSet();
      var stmt = getMockStatement();

      var obj  = getTarget();
      doCallRealMethod().when(obj).toResult(stmt);
      doReturn(res).when(obj).getResultSet(stmt);
      doThrow(SQLException.class).when(obj).parseResult(res);

      assertThrows(SQLException.class, () -> obj.toResult(stmt));
    }

    @Test
    @DisplayName("passes up #toResult(S, V) exceptions")
    void test3() throws Exception {
      var res  = mockResultSet();
      var stmt = getMockStatement();
      var val  = getValue();

      var obj  = getTarget();
      doCallRealMethod().when(obj).toResult(stmt);
      doReturn(res).when(obj).getResultSet(stmt);
      doReturn(val).when(obj).parseResult(res);
      doThrow(SQLException.class).when(obj).toResult(stmt, val);

      assertThrows(SQLException.class, () -> obj.toResult(stmt));
    }

    @Test
    @DisplayName("happy path")
    void test4() throws Exception {
      var val  = getValue();

      var res  = mockResultSet();
      var stmt = getMockStatement();
      var out  = getResult();

      var obj  = getTarget();
      doCallRealMethod().when(obj).toResult(stmt);
      when(obj.getResultSet(stmt)).thenReturn(res);
      when(obj.parseResult(res)).thenReturn(val);
      when(obj.toResult(stmt, val)).thenReturn(out);

      assertSame(out, obj.toResult(stmt));
    }
  }

  @SuppressWarnings("rawtypes")
  protected ReadQueryImpl getTarget() {
    return mock(ReadQueryImpl.class, CALLS_REAL_METHODS);
  }

  @Override
  protected ReadResult getResult() {
    return mock(ReadResult.class);
  }

  @Override
  protected ReadQueryImpl getTest(String sql, ConnectionProvider provider) {
    return new Dummy(sql, provider);
  }

  @Override
  protected ReadQueryImpl getTest(String sql, Connection con) {
    return new Dummy(sql, con);
  }

  @Override
  protected ReadQueryImpl getTest(String sql, DataSource ds) {
    return new Dummy(sql, ds);
  }

  protected Object getValue() {
    return new Object();
  }

  private class Dummy extends ReadQueryImpl
  {
    public Dummy(String sql, ConnectionProvider provider) {
      super(sql, provider);
    }

    public Dummy(String sql, DataSource ds) {
      super(sql, ds);
    }

    public Dummy(String sql, Connection cn) {
      super(sql, cn);
    }

    @Override
    protected ReadResult toResult(Statement stmt, Object value)
    throws Exception {
      return null;
    }

    @Override
    protected Object parseResult(ResultSet rs) throws Exception {
      return null;
    }

    @Override
    protected void executeStatement(Statement stmt) throws Exception {

    }

    @Override
    protected Statement getStatement(Connection cn) throws Exception {
      return null;
    }
  }
}
