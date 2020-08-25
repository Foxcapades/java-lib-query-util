package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MultiReadQuery;
import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.StatementPreparer;
import io.vulpine.lib.query.util.query.PreparedListReadQuery;
import io.vulpine.lib.query.util.result.ReadResultImpl;

public class BasicPreparedListReadQuery < V >
extends PreparedListReadQuery <
  V,
  ReadResultImpl < List < V >, MultiReadQuery < ?, ?, ?, ? >, PreparedStatement > >
{
  private final RowParser < V > parser;

  private final StatementPreparer prep;

  public BasicPreparedListReadQuery(
    final String sql,
    final ConnectionProvider provider,
    final RowParser < V > parser,
    final StatementPreparer prep
    ) {
    super(sql, provider);
    this.parser = parser;
    this.prep = prep;
  }

  public BasicPreparedListReadQuery(
    final String sql,
    final DataSource ds,
    final RowParser < V > parser,
    final StatementPreparer prep
  ) {
    super(sql, ds);
    this.parser = parser;
    this.prep = prep;
  }

  public BasicPreparedListReadQuery(
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
  protected ReadResultImpl < List < V >, MultiReadQuery < ?, ?, ?, ? >, PreparedStatement > toResult(
    final PreparedStatement stmt,
    final List < V > value
  ) throws Exception {
    return new ReadResultImpl <>(this, stmt, value);
  }
}
