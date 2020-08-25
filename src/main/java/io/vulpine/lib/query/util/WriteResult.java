package io.vulpine.lib.query.util;

import java.sql.Statement;

/**
 * <code>WriteResult</code> defines the response object for queries that do not
 * return a value, but instead write changes to the database.
 *
 * @param <Q> The specific type of the {@link Query} implementation that created
 *            this result.
 */
public interface WriteResult < Q extends WriteQuery < ?, ? > >
extends QueryResult < Q >
{
  /**
   * @see Statement#getUpdateCount()
   */
  int getNumRowsAffected();
}
