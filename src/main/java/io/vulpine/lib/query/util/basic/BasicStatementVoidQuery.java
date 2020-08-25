package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.VoidQuery;
import io.vulpine.lib.query.util.query.VoidQueryBase;

public class BasicStatementVoidQuery
extends VoidQueryBase < Statement >
implements VoidQuery < Statement >
{
  public BasicStatementVoidQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  public BasicStatementVoidQuery(String sql, Connection con) {
    super(sql, con);
  }

  public BasicStatementVoidQuery(String sql, DataSource ds) {
    super(sql, ds);
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
