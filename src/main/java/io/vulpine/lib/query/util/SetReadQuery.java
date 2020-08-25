package io.vulpine.lib.query.util;

import java.sql.Statement;
import java.util.Collection;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;

public interface SetReadQuery <
  V,
  R extends ReadResult < Set < V >, ? extends SetReadQuery < ?, ?, ? > >,
  S extends Statement >
extends MultiReadQuery < V, Set < V >, R, S >
{
  /**
   * Sets whether or not the result parsing should check for conflicts.
   * <p>
   * Default value is <code>false</code>.
   *
   * @param flag <code>True</code> = check for conflicts when appending new
   *             values to the <code>Set</code> being built.
   *             <p>
   *             <code>False</code> = do not check for conflicts when appending
   *             new values to the <code>Set</code> being built.
   *
   * @return the modified <code>SetReadQuery</code> instance.
   */
  SetReadQuery < V, R, S > enableConflictHandling(boolean flag);

  @Override
  SetReadQuery < V, R, S > setCollectionProvider(Supplier < Set < V > > fn);

  SetReadQuery < V, R, S > setConflictResolver(BinaryOperator < V > fn);
}
