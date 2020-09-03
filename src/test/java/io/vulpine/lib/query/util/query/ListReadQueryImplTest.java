package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.Query;
import io.vulpine.lib.query.util.QueryResult;
import io.vulpine.lib.query.util.ReadResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.vulpine.lib.query.util.TestUtil.mockConnection;
import static io.vulpine.lib.query.util.TestUtil.mockDataSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class ListReadQueryImplTest extends MultiReadQueryImplTest {

  @BeforeAll
  public static void beforeAll() throws Exception {
    MultiReadQueryImplTest.beforeAll();
  }

  @Nested
  @DisplayName("#this(...)")
  @SuppressWarnings("rawtypes")
  class Constructors {
    @Test
    @DisplayName("sets default list constructor")
    void test1() {
      var obj1 = getTest("", () -> null);
      assertEquals(new ArrayList(), obj1.getCollectionProvider().get());

      var obj2 = getTest("", mockConnection());
      assertEquals(new ArrayList(), obj2.getCollectionProvider().get());

      var obj3 = getTest("", mockDataSource());
      assertEquals(new ArrayList(), obj3.getCollectionProvider().get());
    }
  }

  @Override
  @SuppressWarnings("rawtypes")
  protected ListReadQueryImpl getTarget() {
    return mock(ListReadQueryImpl.class);
  }

  @Override
  @SuppressWarnings("rawtypes")
  protected List getValue() {
    return new ArrayList();
  }

  @Override
  @SuppressWarnings("rawtypes")
  protected ListReadQueryImpl getTest(String sql, ConnectionProvider fn) {
    return new Dummy(sql, fn);
  }

  @Override
  @SuppressWarnings("rawtypes")
  protected ListReadQueryImpl getTest(String sql, Connection con) {
    return new Dummy(sql, con);
  }

  @Override
  @SuppressWarnings("rawtypes")
  protected ListReadQueryImpl getTest(String sql, DataSource ds) {
    return new Dummy(sql, ds);
  }

  @Override
  @SuppressWarnings("rawtypes")
  protected ReadResult getResult() {
    return super.getResult();
  }

  @Override
  protected Statement getMockStatement() {
    return super.getMockStatement();
  }

  @SuppressWarnings({"rawtypes", "RedundantThrows"})
  private static class Dummy extends ListReadQueryImpl
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
    protected QueryResult < ? extends Query < ?, ? > > executeStatement(Statement stmt)
    throws Exception {
      return null;
    }

    @Override
    protected Object parseRow(ResultSet rs) throws Exception {
      return null;
    }

    @Override
    protected ReadResult toResult(Statement stmt, Object value) throws Exception {
      return null;
    }

    @Override
    protected Statement getStatement(Connection cn) throws Exception {
      return null;
    }
  }
}
