package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.WriteQuery;
import io.vulpine.lib.query.util.WriteResult;

public abstract class PreparedWriteQuery < R extends WriteResult < WriteQuery < ?, ? > > >
extends WriteQueryImpl < R, PreparedStatement >
{
  public PreparedWriteQuery(
    String sql,
    ConnectionProvider provider
  ) {
    super(sql, provider);
  }

  public PreparedWriteQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  public PreparedWriteQuery(String sql, Connection cn) {
    super(sql, cn);
  }

  @Override
  protected R executeStatement(PreparedStatement stmt) throws Exception {
    stmt.execute();
    return toResult(stmt);
  }

  @Override
  protected PreparedStatement getStatement(Connection cn) throws Exception {
    return cn.prepareStatement(getSql());
  }

  protected abstract void prepareStatement(PreparedStatement ps)
  throws Exception;
}
