package io.vulpine.lib.query.util.basic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.RowParser;
import io.vulpine.lib.query.util.query.QueryBase;
import io.vulpine.lib.query.util.query.StatementReadQuery;
import io.vulpine.lib.query.util.result.ReadResultImpl;

public class BasicStatementReadQuery< V >
extends StatementReadQuery < V, ReadResultImpl < V, ?, Statement > >
{
  private final RowParser < V > parser;

  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public BasicStatementReadQuery(
    String sql,
    ConnectionProvider provider,
    RowParser < V > parser
  ) {
    super(sql, provider);
    this.parser = parser;
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public BasicStatementReadQuery(
    String sql,
    DataSource ds,
    RowParser < V > parser
  ) {
    super(sql, ds);
    this.parser = parser;
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public BasicStatementReadQuery(
    String sql,
    Connection cn,
    RowParser < V > parser
  ) {
    super(sql, cn);
    this.parser = parser;
  }

  @Override
  protected ReadResultImpl < V, ?, Statement > toResult(Statement stmt, V value)
  throws Exception {
    return new ReadResultImpl <>(this, stmt, value);
  }

  @Override
  protected V parseResult(ResultSet rs) throws Exception {
    return parser.parse(rs);
  }
}
