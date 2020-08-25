package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MultiReadQuery;
import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.query.StatementListReadQuery;
import io.vulpine.lib.query.util.result.ReadResultImpl;

public class BasicStatementListReadQuery< V >
extends StatementListReadQuery <
  V,
  ReadResultImpl < List < V >, MultiReadQuery < ?, ?, ?, ? >, Statement > >
{
  private final RowParser < V > parser;

  public BasicStatementListReadQuery(
    final String sql,
    final ConnectionProvider provider,
    final RowParser < V > parser
    ) {
    super(sql, provider);
    this.parser = parser;
  }

  public BasicStatementListReadQuery(
    final String sql,
    final DataSource ds,
    final RowParser < V > parser
  ) {
    super(sql, ds);
    this.parser = parser;
  }

  public BasicStatementListReadQuery(
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
  protected ReadResultImpl < List < V >, MultiReadQuery < ?, ?, ?, ? >, Statement > toResult(
    final Statement stmt,
    final List < V > value
  ) throws Exception {
    return new ReadResultImpl <>(this, stmt, value);
  }
}
