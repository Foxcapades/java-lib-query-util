package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Set;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.ReadQuery;
import io.vulpine.lib.query.util.ReadResult;
import io.vulpine.lib.query.util.SetReadQuery;

public abstract class StatementSetReadQuery <
  V,
  R extends ReadResult < Set < V >, SetReadQuery < ?, ?, ? > > >
extends SetReadQueryImpl < V, R, Statement >
implements ReadQuery < Set < V >, R, Statement >
{
  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public StatementSetReadQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public StatementSetReadQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public StatementSetReadQuery(String sql, Connection cn) {
    super(sql, cn);
  }

  @Override
  public StatementSetReadQuery < V, R > shareConnection(boolean flag) {
    return (StatementSetReadQuery < V, R >) super.shareConnection(flag);
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
