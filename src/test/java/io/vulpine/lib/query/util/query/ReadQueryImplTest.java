package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.ReadResult;
import org.junit.jupiter.api.BeforeAll;

import static org.mockito.Mockito.mock;

@SuppressWarnings({"unchecked", "rawtypes"})
class ReadQueryImplTest extends QueryImplTest
{
  @BeforeAll
  public static void beforeAll() throws Exception {
    QueryImplTest.beforeAll();
  }

  @SuppressWarnings("rawtypes")
  protected ReadQueryImpl getTarget() {
    return mock(ReadQueryImpl.class);
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

  @SuppressWarnings("RedundantThrows")
  private static class Dummy extends ReadQueryImpl
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
    protected ReadResult toResult(Statement stmt, Object value) throws Exception {
      return null;
    }

    @Override
    protected Object parseResult(ResultSet rs) throws Exception {
      return null;
    }

    @Override
    protected Statement getStatement(Connection cn) throws Exception {
      return null;
    }
  }
}
