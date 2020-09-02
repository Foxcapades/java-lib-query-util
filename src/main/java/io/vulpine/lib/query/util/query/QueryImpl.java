package io.vulpine.lib.query.util.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.sql.DataSource;

import io.vulpine.lib.query.util.ConnectionProvider;
import io.vulpine.lib.query.util.Query;
import io.vulpine.lib.query.util.QueryResult;

/**
 * Internal base implementation for the {@link Query} interface.
 *
 * @param <R> Result object type.
 * @param <S> Specific JDBC Statement type.  Generally going to be one of
 *            {@link Statement} or {@link PreparedStatement}.
 */
public abstract class QueryImpl <
  R extends QueryResult < ? extends Query < ?, ? > >,
  S extends Statement >
extends QueryBase < S >
implements Query < R, S >
{
  private S stmt;

  private boolean closeStatements;

  /**
   * @see QueryBase#QueryBase(String, ConnectionProvider)
   */
  public QueryImpl(String sql, ConnectionProvider provider) {
    super(sql, provider);
    closeStatements = true;
  }

  /**
   * @see QueryBase#QueryBase(String, DataSource)
   */
  public QueryImpl(String sql, DataSource ds) {
    super(sql, ds);
    closeStatements = true;
  }

  /**
   * @see QueryBase#QueryBase(String, Connection)
   */
  public QueryImpl(String sql, Connection cn) {
    super(sql, cn);
    closeStatements = true;
  }

  @Override
  public Query < R, S > closeStatements(boolean flag) {
    closeStatements = flag;
    return this;
  }

  @Override
  public R execute() throws Exception {
    if (isClosed())
      throw new IllegalStateException("Attempted to call #execute() on a closed Query instance");

    final var cn = getConnection();
    try {
      stmt = getStatement(cn);

      return executeStatement(stmt);
    } finally {
      softCloseStatement();
      softCloseConnection();
    }
  }

  @Override
  public void close() throws Exception {
    closeStatement();

    super.close();
  }

  @Override
  public QueryImpl < R, S > shareConnection(boolean flag) {
    return (QueryImpl < R, S >) super.shareConnection(flag);
  }

  /**
   * Closes the last statement (if one exists).
   * <p>
   * By default this method is called right after the results of the statement
   * have been processed.  This behavior can be changed using the
   * {@link #closeStatements(boolean)} method.
   */
  protected void closeStatement() throws Exception{
    if (stmt != null)
      stmt.close();
  }

  protected void softCloseStatement() throws Exception {
    if (closeStatements)
      closeStatement();
  }

  /**
   * Executes the given statement.
   *
   * @param stmt statement to execute.
   */
  protected abstract R executeStatement(S stmt) throws Exception;
}
