package io.vulpine.lib.query.util;

/**
 * <code>QueryResult</code> defines the most basic response type for a
 * {@link Query} execution.
 *
 * @param <Q> The specific type of the {@link Query} implementation that created
 *            this result.
 */
public interface QueryResult < Q extends Query < ?, ? > > extends AutoCloseable
{
  /**
   * Returns the {@link Query} instance that created this result object.
   *
   * @return the parent <code>Query</code> that created this instance.
   */
  Q getQuery();

  /**
   * Sets whether or not the parent {@link Query} instance should be closed when
   * {@link #close()} is called on this <code>QueryResult</code>.
   * <p>
   * Default value is <code>false</code>.
   *
   * @param flag whether or not the parent <code>Query</code> instance should be
   *             closed along with this <code>QueryResult</code>.
   *             <p>
   *             <code>True</code> = when {@link QueryResult#close()} is called,
   *             {@link Query#close()} will also be called on the parent
   *             <code>Query</code> object.
   *             <p>
   *             <code>False</code> = when {@link QueryResult#close()} is
   *             called, only the specific statement directly relating to this
   *             result will be closed.
   *
   * @return the modified {@link QueryResult} instance.
   */
  QueryResult < Q > closeParentOnClose(boolean flag);
}
