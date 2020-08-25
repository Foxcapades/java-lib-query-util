package io.vulpine.lib.query.util.query;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.ReadResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.vulpine.lib.query.util.TestUtil.mockResultSet;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")
class MultiReadQueryImplTest extends ReadQueryImplTest
{
  protected static Field fCollectionProvider;

  @BeforeAll
  public static void beforeAll() throws Exception {
    fCollectionProvider = MultiReadQueryImpl.class.getDeclaredField(
      "collectionProvider");
    fCollectionProvider.setAccessible(true);

    ReadQueryImplTest.beforeAll();
  }

  @Nested
  @DisplayName("#setCollectionProvider(Supplier<C>)")
  class SetCollectionProvider
  {
    @Test
    @DisplayName("throws when given a null value")
    void test1() {
      var obj = getTarget();
      doCallRealMethod().when(obj).setCollectionProvider(any());

      assertThrows(
        NullPointerException.class,
        () -> obj.setCollectionProvider(null)
      );
    }

    @Test
    @DisplayName("happy path")
    void test2() throws Exception {
      var obj = getTarget();
      doCallRealMethod().when(obj).setCollectionProvider(any());

      assertNull(fCollectionProvider.get(obj));
      assertSame(obj, obj.setCollectionProvider(ArrayList::new));
      assertNotNull(fCollectionProvider.get(obj));
    }
  }

  @Nested
  @DisplayName("#parseResult(ResultSet)")
  class ParseResult
  {
    @Test
    @DisplayName("passes up exception from #parseRow(ResultSet)")
    void test1() throws Exception {
      Supplier < Collection > val = () -> getValue();

      var rs  = mockResultSet();
      doReturn(true).when(rs).next();

      var obj = getTarget();
      doReturn(val).when(obj).getCollectionProvider();
      doCallRealMethod().when(obj).parseResult(rs);
      doThrow(SQLException.class).when(obj).parseRow(rs);

      assertThrows(SQLException.class, () -> obj.parseResult(rs));
    }

    @Test
    @DisplayName("passes up exception from ResultSet#next()")
    void test2() throws Exception {
      Supplier < Collection > val = () -> getValue();

      var rs  = mockResultSet();
      doThrow(SQLException.class).when(rs).next();

      var obj = getTarget();
      doReturn(val).when(obj).getCollectionProvider();
      doCallRealMethod().when(obj).parseResult(rs);

      assertThrows(SQLException.class, () -> obj.parseResult(rs));
    }

    @Test
    @DisplayName("happy path")
    void test3() throws Exception {
      Supplier < Collection > val = () -> getValue();

      var rs  = mockResultSet();
      doReturn(true, true, true, false).when(rs).next();

      var obj = getTarget();
      doReturn(val).when(obj).getCollectionProvider();
      doCallRealMethod().when(obj).parseResult(rs);
      doReturn(new Object()).when(obj).parseRow(rs);

      var out = obj.parseResult(rs);
      assertEquals(3, out.size());
      for (var o : out)
        assertNotNull(o);
    }
  }

  @Nested
  @DisplayName("#getCollectionProvider()")
  class GetCollectionProvider
  {
    @Test
    @DisplayName("throws when collectionProvider is null")
    void test1() {
      var obj = getTarget();
      doCallRealMethod().when(obj).getCollectionProvider();

      assertThrows(NullPointerException.class, obj::getCollectionProvider);
    }

    @Test
    @DisplayName("happy path")
    void test2() throws Exception {
      Supplier < Collection > val = ArrayList::new;

      var obj = getTarget();
      doCallRealMethod().when(obj).getCollectionProvider();
      fCollectionProvider.set(obj, val);

      assertSame(val, obj.getCollectionProvider());
    }
  }

  @Override
  protected MultiReadQueryImpl getTarget() {
    return mock(MultiReadQueryImpl.class);
  }

  @Override
  protected Collection getValue() {
    return new ArrayList();
  }

  @Override
  protected MultiReadQueryImpl getTest(
    String sql, ConnectionProvider provider
  ) {
    return new Dummy(sql, provider);
  }

  @Override
  protected MultiReadQueryImpl getTest(String sql, Connection con) {
    return new Dummy(sql, con);
  }

  @Override
  protected MultiReadQueryImpl getTest(String sql, DataSource ds) {
    return new Dummy(sql, ds);
  }

  private class Dummy extends MultiReadQueryImpl
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
    protected Object parseRow(ResultSet rs) throws Exception {
      return null;
    }

    @Override
    protected ReadResult toResult(
      Statement stmt, Object value
    ) throws Exception {
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
