package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MultiReadQuery;
import io.vulpine.lib.query.util.ReadResult;

public abstract class StatementListReadQuery <
  V,
  R extends ReadResult < List < V >, ? extends MultiReadQuery < ?, ?, ?, ? > > >
extends ListReadQueryImpl < V, R, Statement >
implements MultiReadQuery < V, List < V >, R, Statement >
{
  public StatementListReadQuery(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  public StatementListReadQuery(String sql, DataSource ds) {
    super(sql, ds);
  }

  public StatementListReadQuery(String sql, Connection cn) {
    super(sql, cn);
  }

  @Override
  public StatementListReadQuery < V, R > shareConnection(boolean flag) {
    return (StatementListReadQuery < V, R >) super.shareConnection(flag);
  }

  @Override
  protected Statement getStatement(Connection cn) throws Exception {
    return cn.createStatement();
  }
}
