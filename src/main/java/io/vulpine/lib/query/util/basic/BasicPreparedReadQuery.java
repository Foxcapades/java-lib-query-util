package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.StatementPreparer;
import io.vulpine.lib.query.util.query.QueryBase;
import io.vulpine.lib.query.util.query.PreparedReadQuery;
import io.vulpine.lib.query.util.result.ReadResultImpl;

public class BasicPreparedReadQuery< V >
extends PreparedReadQuery < V, ReadResultImpl < V, ?, PreparedStatement > >
{
  private final RowParser < V > parser;

  private final StatementPreparer prep;

  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public BasicPreparedReadQuery(
    final String sql,
    final ConnectionProvider provider,
    final RowParser < V > parser,
    final StatementPreparer prep
    ) {
    super(sql, provider);
    this.parser = parser;
    this.prep = prep;
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public BasicPreparedReadQuery(
    final String sql,
    final DataSource ds,
    final RowParser < V > parser,
    final StatementPreparer prep
  ) {
    super(sql, ds);
    this.parser = parser;
    this.prep = prep;
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public BasicPreparedReadQuery(
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
  protected void prepareStatement(PreparedStatement ps) throws Exception {
    prep.prepare(ps);
  }

  @Override
  protected V parseResult(final ResultSet rs) throws Exception {
    return parser.parse(rs);
  }

  @Override
  protected ReadResultImpl < V, ?, PreparedStatement > toResult(
    final PreparedStatement stmt,
    final V value
  ) throws Exception {
    return new ReadResultImpl <>(this, stmt, value);
  }
}
