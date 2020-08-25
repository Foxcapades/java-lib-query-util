package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.WriteQuery;
import io.vulpine.lib.query.util.WriteResult;

public abstract class StatementWriteQuery< R extends WriteResult < WriteQuery < ?, ? > > >
extends WriteQueryImpl < R, Statement >
{
  public StatementWriteQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  public StatementWriteQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  public StatementWriteQuery(String sql, Connection cn) {
    super(sql, cn);
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
