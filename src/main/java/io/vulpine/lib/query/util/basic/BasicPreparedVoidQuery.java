package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.StatementPreparer;
import io.vulpine.lib.query.util.query.PreparedVoidQuery;

public class BasicPreparedVoidQuery extends PreparedVoidQuery
{
  private final StatementPreparer prep;

  public BasicPreparedVoidQuery(
    final String sql,
    final ConnectionProvider provider,
    final StatementPreparer prep
    ) {
    super(sql, provider);
    this.prep = prep;
  }

  public BasicPreparedVoidQuery(
    final String sql,
    final Connection con,
    final StatementPreparer prep
  ) {
    super(sql, con);
    this.prep = prep;
  }

  public BasicPreparedVoidQuery(
    final String sql,
    final DataSource ds,
    final StatementPreparer prep
  ) {
    super(sql, ds);
    this.prep = prep;
  }

  @Override
  protected void prepareStatement(PreparedStatement st) throws Exception {
    prep.prepare(st);
  }
}
