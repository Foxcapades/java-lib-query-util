package io.vulpine.lib.query.util;

import java.sql.Statement;
import java.util.Collection;
import java.util.function.Supplier;

public interface MultiReadQuery<
  V,
  C extends Collection < V >,
  R extends ReadResult < C, ? extends MultiReadQuery < ?, ?, ?, ? > >,
  S extends Statement >
  extends ReadQuery < C, R, S >
{
  MultiReadQuery < V, C, R, S > setCollectionProvider(Supplier < C > con);
}
