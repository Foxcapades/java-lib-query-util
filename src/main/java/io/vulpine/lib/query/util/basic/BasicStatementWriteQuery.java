package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.WriteQuery;
import io.vulpine.lib.query.util.query.StatementWriteQuery;
import io.vulpine.lib.query.util.result.WriteResultImpl;

public class BasicStatementWriteQuery
extends StatementWriteQuery <
  WriteResultImpl < WriteQuery < ?, ? >, Statement > >
{
  public BasicStatementWriteQuery(
    final String sql,
    final ConnectionProvider provider
    ) {
    super(sql, provider);
  }

  public BasicStatementWriteQuery(
    final String sql,
    final DataSource ds
  ) {
    super(sql, ds);
  }

  public BasicStatementWriteQuery(
    final String sql,
    final Connection cn
  ) {
    super(sql, cn);
  }

  @Override
  protected WriteResultImpl < WriteQuery < ?, ? >, Statement > toResult(
    final Statement st
  ) throws Exception {
    return new WriteResultImpl <>(this, st, st.getUpdateCount());
  }
}
