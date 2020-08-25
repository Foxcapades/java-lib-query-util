package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.MapReadQuery;
import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.StatementPreparer;
import io.vulpine.lib.query.util.query.PreparedMapReadQuery;
import io.vulpine.lib.query.util.result.ReadResultImpl;

public class BasicPreparedMapReadQuery< K, V >
  extends PreparedMapReadQuery <
    K, V,
    ReadResultImpl < Map < K, V >, MapReadQuery < ?, ?, ?, ? >, PreparedStatement > >
{
  private final StatementPreparer prep;

  public BasicPreparedMapReadQuery(
    final String sql,
    final ConnectionProvider provider,
    final RowParser < K > keyParser,
    final RowParser < V > valParser,
    final StatementPreparer prep
    ) {
    super(sql, provider);
    setKeyParser(keyParser);
    setValueParser(valParser);

    this.prep = prep;
  }

  public BasicPreparedMapReadQuery(
    final String sql,
    final DataSource ds,
    final RowParser < K > keyParser,
    final RowParser < V > valParser,
    final StatementPreparer prep
  ) {
    super(sql, ds);
    setKeyParser(keyParser);
    setValueParser(valParser);

    this.prep = prep;
  }

  public BasicPreparedMapReadQuery(
    final String sql,
    final Connection cn,
    final RowParser < K > keyParser,
    final RowParser < V > valParser,
    final StatementPreparer prep
  ) {
    super(sql, cn);
    setKeyParser(keyParser);
    setValueParser(valParser);

    this.prep = prep;
  }

  @Override
  protected void prepareStatement(PreparedStatement ps) throws Exception {
    prep.prepare(ps);
  }

  @Override
  protected ReadResultImpl < Map < K, V >, MapReadQuery < ?, ?, ?, ? >, PreparedStatement > toResult(
    final PreparedStatement stmt,
    final Map < K, V > value
  ) throws Exception {
    return new ReadResultImpl <>(this, stmt, value);
  }
}
