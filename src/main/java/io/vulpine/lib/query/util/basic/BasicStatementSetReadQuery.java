package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Set;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.SetReadQuery;
import io.vulpine.lib.query.util.query.StatementSetReadQuery;
import io.vulpine.lib.query.util.result.ReadResultImpl;

public class BasicStatementSetReadQuery < V >
extends StatementSetReadQuery <
  V,
  ReadResultImpl < Set < V >, SetReadQuery < ?, ?, ? >, Statement > >
{
  private final RowParser < V > parser;

  public BasicStatementSetReadQuery(
    final String sql,
    final ConnectionProvider provider,
    final RowParser < V > parser
  ) {
    super(sql, provider);
    this.parser = parser;
  }

  public BasicStatementSetReadQuery(
    final String sql,
    final DataSource ds,
    final RowParser < V > parser
  ) {
    super(sql, ds);
    this.parser = parser;
  }

  public BasicStatementSetReadQuery(
    final String sql,
    final Connection cn,
    final RowParser < V > parser
  ) {
    super(sql, cn);
    this.parser = parser;
  }

  @Override
  protected V parseRow(ResultSet rs) throws Exception {
    return parser.parse(rs);
  }

  @Override
  protected ReadResultImpl < Set < V >, SetReadQuery < ?, ?, ? >, Statement > toResult(
    final Statement stmt,
    final Set < V > value
  ) throws Exception {
    return new ReadResultImpl <>(this, stmt, value);
  }
}
