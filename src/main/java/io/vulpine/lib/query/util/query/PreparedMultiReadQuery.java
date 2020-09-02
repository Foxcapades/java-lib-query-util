package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Collection;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MultiReadQuery;
import io.vulpine.lib.query.util.ReadResult;

public abstract class PreparedMultiReadQuery <
  V,
  C extends Collection < V >,
  R extends ReadResult < C, ? extends MultiReadQuery < ?, ?, ?, ? > > >
extends MultiReadQueryImpl < V, C, R, PreparedStatement >
implements MultiReadQuery < V, C, R, PreparedStatement >
{
  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public PreparedMultiReadQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public PreparedMultiReadQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public PreparedMultiReadQuery(String sql, Connection cn) {
    super(sql, cn);
  }

  @Override
  protected void executeStatement(PreparedStatement stmt) throws Exception {
    stmt.execute();
  }

  @Override
  protected PreparedStatement getStatement(Connection cn) throws Exception {
    var out = cn.prepareStatement(getSql());
    prepareStatement(out);
    return out;
  }

  protected abstract void prepareStatement(PreparedStatement ps)
  throws Exception;
}
