package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MapReadQuery;
import io.vulpine.lib.query.util.ReadResult;

/**
 * Value read query object preconfigured for using the standard JDBC
 * {@link Statement} type.
 *
 * @param <V> Type of value this query will generate.
 * @param <R> Type of result wrapper this query will return on execution.
 */
public abstract class StatementMapReadQuery<
  K, V,
  R extends ReadResult < Map < K, V >, MapReadQuery < ?, ?, ?, ? > > >
extends MapReadQueryImpl < K, V, R, Statement >
implements MapReadQuery < K, V, R, Statement >
{
  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public StatementMapReadQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public StatementMapReadQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public StatementMapReadQuery(String sql, Connection cn) {
    super(sql, cn);
  }

  @Override
  public StatementMapReadQuery < K, V, R > shareConnection(boolean flag) {
    return (StatementMapReadQuery < K, V, R >) super.shareConnection(flag);
  }

  @Override
  protected void executeStatement(Statement stmt) throws Exception {
    stmt.execute(getSql());
  }

  @Override
  protected Statement getStatement(Connection cn) throws Exception {
    return cn.createStatement();
  }
}
