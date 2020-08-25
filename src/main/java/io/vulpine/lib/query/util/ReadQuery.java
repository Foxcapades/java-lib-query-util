package io.vulpine.lib.query.util;

import java.sql.Statement;

public interface ReadQuery <
  V,
  R extends ReadResult < V, ? extends ReadQuery < ?, ?, ? > >,
  S extends Statement >
extends Query < R, S >
{
}
