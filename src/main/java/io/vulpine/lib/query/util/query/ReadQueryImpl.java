package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.ReadQuery;
import io.vulpine.lib.query.util.ReadResult;

/**
 * Basic implementation for a query returning a value.
 *
 * @param <V> Type of the value this query will generate.
 * @param <R> Type of result wrapper this query will return on execution.
 * @param <S> Specific JDBC Statement type.  Generally going to be one of
 *            {@link Statement} or {@link PreparedStatement}.
 */
public abstract class ReadQueryImpl<
  V,
  R extends ReadResult < V, ? extends ReadQuery < ?, ?, ? > >,
  S extends Statement >
extends QueryImpl < R, S >
{
  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public ReadQueryImpl(String sql, ConnectionProvider provider) {
    super(sql, provider);
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public ReadQueryImpl(String sql, DataSource ds) {
    super(sql, ds);
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public ReadQueryImpl(String sql, Connection cn) {
    super(sql, cn);
  }


  /**
   * Constructs a {@link ReadResult} instance implementing type {@link R}
   * with the given <code>Statement</code> and constructed {@link V} value.
   *
   * @param stmt  Executed statement
   * @param value Constructed value from the results of the statement execution.
   *
   * @return A new instance of {@link R}.
   */
  protected abstract R toResult(S stmt, V value) throws Exception;

  /**
   * Parses the given result set into an instance of {@link V}.
   * <p>
   * The <code>ResultSet</code> will be closed immediately after this function
   * is called.
   *
   * @param rs <code>ResultSet</code> instance to use when creating an instance
   *           of {@link V}
   *
   * @return An instance of {@link V}.
   */
  protected abstract V parseResult(ResultSet rs) throws Exception;
}
