package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Collection;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MultiReadQuery;
import io.vulpine.lib.query.util.ReadResult;

public abstract class StatementMultiReadQuery <
  V,
  C extends Collection < V >,
  R extends ReadResult < C, MultiReadQuery < ?, ?, ?, ? > > >
extends MultiReadQueryImpl < V, C, R, Statement >
implements MultiReadQuery < V, C, R, Statement >
{
  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public StatementMultiReadQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public StatementMultiReadQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public StatementMultiReadQuery(String sql, Connection cn) {
    super(sql, cn);
  }

  @Override
  public StatementMultiReadQuery < V, C, R > shareConnection(boolean flag) {
    return (StatementMultiReadQuery < V, C, R >) super.shareConnection(flag);
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
