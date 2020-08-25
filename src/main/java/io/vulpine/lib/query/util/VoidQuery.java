package io.vulpine.lib.query.util;

import java.sql.Statement;

/**
 * <code>VoidQuery</code> represents a simple SQL statement from which no
 * results or feedback are required.
 *
 * @param <S>
 */
public interface VoidQuery < S extends Statement > extends AutoCloseable
{
  /**
   * Returns the configured SQL query for this <code>VoidQuery</code> instance.
   *
   * @return the SQL this instance is using.
   */
  String getSql();

  /**
   * Executes the configured SQL query.
   */
  void execute() throws Exception;

  /**
   * Sets whether or not a single connection should be shared across multiple
   * executions.
   * <p>
   * If set to <code>true</code> only one connection will be opened, and it will
   * not be closed until the {@link #close()} method is called on this VoidQuery
   * instance.
   * <p>
   * If set to <code>false</code> each call to {@link #execute()} will open a
   * new connection to perform it's job, then close it immediately after
   * execution.
   *
   * @param flag whether or not to reuse a single connection.
   *
   * @return The modified VoidQuery instance.
   */
  VoidQuery < S > shareConnection(boolean flag);

  /**
   * Returns whether or not {@link #close()} has been called on this VoidQuery
   * instance.
   * <p>
   * Attempting to reuse a <code>VoidQuery</code> instance after
   * {@link #close()} has been called will result in an
   * {@link IllegalStateException}.
   * <p>
   * Calls to {@link #close()} will do nothing if the instance has already been
   * closed.
   *
   * @return whether or not this Query instance has been closed.
   */
  boolean isClosed();

}
