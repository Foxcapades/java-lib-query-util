package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.WriteQuery;
import io.vulpine.lib.query.util.WriteResult;

public abstract class WriteQueryImpl <
  R extends WriteResult < WriteQuery < ?, ? > >,
  S extends Statement >
extends QueryImpl < R, S >
implements WriteQuery < R, S >
{
  public WriteQueryImpl(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  public WriteQueryImpl(String sql, DataSource ds) {
    super(sql, ds);
  }

  public WriteQueryImpl(String sql, Connection cn) {
    super(sql, cn);
  }

  protected abstract R toResult(S stmt) throws Exception;
}
