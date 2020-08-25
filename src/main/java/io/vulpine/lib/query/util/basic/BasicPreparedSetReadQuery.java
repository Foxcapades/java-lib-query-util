package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Set;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.SetReadQuery;
import io.vulpine.lib.query.util.StatementPreparer;
import io.vulpine.lib.query.util.query.PreparedSetReadQuery;
import io.vulpine.lib.query.util.result.ReadResultImpl;

public class BasicPreparedSetReadQuery< V >
extends PreparedSetReadQuery <
  V,
  ReadResultImpl < Set < V >, SetReadQuery < ?, ?, ? >, PreparedStatement > >
{
  private final RowParser < V > parser;

  private final StatementPreparer prep;

  public BasicPreparedSetReadQuery(
    final String sql,
    final ConnectionProvider provider,
    final RowParser < V > parser,
    final StatementPreparer prep
    ) {
    super(sql, provider);
    this.parser = parser;
    this.prep = prep;
  }

  public BasicPreparedSetReadQuery(
    final String sql,
    final DataSource ds,
    final RowParser < V > parser,
    final StatementPreparer prep
  ) {
    super(sql, ds);
    this.parser = parser;
    this.prep = prep;
  }

  public BasicPreparedSetReadQuery(
    final String sql,
    final Connection cn,
    final RowParser < V > parser,
    final StatementPreparer prep
  ) {
    super(sql, cn);
    this.parser = parser;
    this.prep = prep;
  }

  @Override
  protected V parseRow(ResultSet rs) throws Exception {
    return parser.parse(rs);
  }

  @Override
  protected void prepareStatement(PreparedStatement ps) throws Exception {
    prep.prepare(ps);
  }

  @Override
  protected ReadResultImpl < Set < V >, SetReadQuery < ?, ?, ? >, PreparedStatement > toResult(
    final PreparedStatement stmt,
    final Set < V > value
  ) throws Exception {
    return new ReadResultImpl <>(this, stmt, value);
  }
}
