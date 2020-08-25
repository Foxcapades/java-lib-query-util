package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.StatementPreparer;
import io.vulpine.lib.query.util.WriteQuery;
import io.vulpine.lib.query.util.query.PreparedWriteQuery;
import io.vulpine.lib.query.util.result.WriteResultImpl;

public class BasicPreparedWriteQuery
extends PreparedWriteQuery <
  WriteResultImpl < WriteQuery < ?, ? >, PreparedStatement > >
{
  private final StatementPreparer prep;

  public BasicPreparedWriteQuery(
    final String sql,
    final ConnectionProvider provider,
    final StatementPreparer prep
    ) {
    super(sql, provider);
    this.prep = prep;
  }

  public BasicPreparedWriteQuery(
    final String sql,
    final DataSource ds,
    final StatementPreparer prep
  ) {
    super(sql, ds);
    this.prep = prep;
  }

  public BasicPreparedWriteQuery(
    final String sql,
    final Connection cn,
    final StatementPreparer prep
  ) {
    super(sql, cn);
    this.prep = prep;
  }

  @Override
  protected void prepareStatement(PreparedStatement ps) throws Exception {
    prep.prepare(ps);
  }

  @Override
  protected WriteResultImpl < WriteQuery < ?, ? >, PreparedStatement > toResult(
    final PreparedStatement st
  ) throws Exception {
    return new WriteResultImpl <>(this, st, st.getUpdateCount());
  }
}
