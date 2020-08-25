package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MapReadQuery;
import io.vulpine.lib.query.util.ReadResult;

public abstract class PreparedMapReadQuery <
  K, V,
  R extends ReadResult < Map < K, V >, ? extends MapReadQuery < ?, ?, ?, ? > > >
extends MapReadQueryImpl < K, V, R, PreparedStatement >
implements MapReadQuery < K, V, R, PreparedStatement >
{
  public PreparedMapReadQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  public PreparedMapReadQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  public PreparedMapReadQuery(String sql, Connection cn) {
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
