package io.vulpine.lib.query.util;

import java.sql.Statement;

public interface Query <
  R extends QueryResult < ? extends Query < ?, ? > > ,
  S extends Statement >
extends AutoCloseable
{
  String getSql();

  /**
   * Executes a statement on the given connection using the given SQL query.
   *
   * @return A {@link QueryResult} object containing details about the query
   *         execution such as result values or number of rows affected.
   */
  R execute() throws Exception;

  /**
   * Sets whether or not {@link Statement}s should be closed immediately after
   * use.  <code>Statement</code>s may also be closed using the
   * {@link QueryResult#close()} method.
   * <p>
   * Defaults to true.
   * <p>
   * <b>WARNING</b>: Setting this to false will leave dangling open statements
   * that will need to be closed via the returned {@link QueryResult} instance.
   *
   * @param flag True = close each statement immediately after use.  False = do
   *             not close statements after use.
   *
   * @return The modified Query instance.
   */
  Query < R, S > closeStatements(boolean flag);

  /**
   * Sets whether or not a single connection should be shared across multiple
   * executions.
   * <p>
   * If set to <code>true</code> only one connection will be opened, and it will
   * not be closed until the {@link #close()} method is called on this query
   * instance.
   * <p>
   * If set to <code>false</code> each call to {@link #execute()} will open a
   * new connection to perform it's job, then close it immediately after
   * execution.
   *
   * @param flag whether or not to reuse a single connection.
   *
   * @return The modified Query instance.
   */
  Query < R, S > shareConnection(boolean flag);

  /**
   * Returns whether or not {@link #close()} has been called on this Query
   * instance.
   * <p>
   * Attempting to reuse a Query instance after {@link #close()} has been called
   * will result in an {@link IllegalStateException}.
   * <p>
   * Calls to {@link #close()} will do nothing if the instance has already been
   * closed.
   *
   * @return whether or not this Query instance has been closed.
   */
  boolean isClosed();
}
