package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.VoidQuery;

public abstract class PreparedVoidQuery
extends VoidQueryBase < PreparedStatement >
implements VoidQuery < PreparedStatement >
{
  public PreparedVoidQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  public PreparedVoidQuery(String sql, Connection con) {
    super(sql, con);
  }

  public PreparedVoidQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  @Override
  protected PreparedStatement getStatement(Connection cn) throws Exception {
    final var stmt = cn.prepareStatement(getSql());
    prepareStatement(stmt);
    return stmt;
  }

  @Override
  protected void executeStatement(PreparedStatement stmt) throws Exception {
    stmt.execute();
  }

  protected abstract void prepareStatement(PreparedStatement st)
  throws Exception;
}
