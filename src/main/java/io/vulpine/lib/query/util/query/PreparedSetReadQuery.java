package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Set;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.ReadResult;
import io.vulpine.lib.query.util.SetReadQuery;

public abstract class PreparedSetReadQuery <
  V,
  R extends ReadResult < Set < V >, ? extends SetReadQuery < ?, ?, ? > > >
extends SetReadQueryImpl < V, R, PreparedStatement >
implements SetReadQuery < V, R, PreparedStatement >
{
  public boolean doConflictChecks;

  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public PreparedSetReadQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public PreparedSetReadQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public PreparedSetReadQuery(String sql, Connection cn) {
    super(sql, cn);
  }


  @Override
  protected void executeStatement(PreparedStatement stmt) throws Exception {
    stmt.execute();
  }

  @Override
  protected PreparedStatement getStatement(Connection cn) throws Exception {
    return cn.prepareStatement(getSql());
  }

  protected abstract void prepareStatement(PreparedStatement ps)
  throws Exception;
}
