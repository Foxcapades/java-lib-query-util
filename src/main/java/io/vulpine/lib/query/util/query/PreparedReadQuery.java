package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.ReadQuery;
import io.vulpine.lib.query.util.ReadResult;

public abstract class PreparedReadQuery < V, R extends ReadResult < V, ? > >
extends ReadQueryImpl < V, R, PreparedStatement >
implements ReadQuery < V, R, PreparedStatement >
{
  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public PreparedReadQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public PreparedReadQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public PreparedReadQuery(String sql, Connection cn) {
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
