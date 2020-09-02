package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.ReadQuery;
import io.vulpine.lib.query.util.ReadResult;

/**
 * Value read query object preconfigured for using the standard JDBC
 * {@link Statement} type.
 *
 * @param <V> Type of value this query will generate.
 * @param <R> Type of result wrapper this query will return on execution.
 */
public abstract class StatementReadQuery < V, R extends ReadResult < V, ? > >
extends ReadQueryImpl < V, R, Statement >
implements ReadQuery < V, R, Statement >
{
  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public StatementReadQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public StatementReadQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public StatementReadQuery(String sql, Connection cn) {
    super(sql, cn);
  }

  @Override
  public StatementReadQuery < V, R > shareConnection(boolean flag) {
    return (StatementReadQuery < V, R >) super.shareConnection(flag);
  }

  @Override
  protected Statement getStatement(Connection cn) throws Exception {
    return cn.createStatement();
  }
}
