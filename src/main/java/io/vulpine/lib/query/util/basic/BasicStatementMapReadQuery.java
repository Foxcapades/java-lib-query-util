package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.Statement;
import java.util.Map;

import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MapReadQuery;
import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.query.StatementMapReadQuery;
import io.vulpine.lib.query.util.result.ReadResultImpl;

public class BasicStatementMapReadQuery< K, V >
  extends StatementMapReadQuery <
  K, V,
  ReadResultImpl < Map < K, V >, MapReadQuery < ?, ?, ?, ? >, Statement > >
{
  public BasicStatementMapReadQuery(
    final String sql,
    final ConnectionProvider provider,
    final RowParser < K > keyParser,
    final RowParser < V > valParser
  ) {
    super(sql, provider);
    setKeyParser(keyParser);
    setValueParser(valParser);
  }

  public BasicStatementMapReadQuery(
    final String sql,
    final DataSource ds,
    final RowParser < K > keyParser,
    final RowParser < V > valParser
  ) {
    super(sql, ds);
    setKeyParser(keyParser);
    setValueParser(valParser);
  }

  public BasicStatementMapReadQuery(
    final String sql,
    final Connection cn,
    final RowParser < K > keyParser,
    final RowParser < V > valParser
  ) {
    super(sql, cn);
    setKeyParser(keyParser);
    setValueParser(valParser);
  }

  @Override
  protected ReadResultImpl < Map < K, V >, MapReadQuery < ?, ?, ?, ? >, Statement > toResult(
    final Statement stmt,
    final Map < K, V > value
  ) throws Exception {
    return new ReadResultImpl <>(this, stmt, value);
  }
}
