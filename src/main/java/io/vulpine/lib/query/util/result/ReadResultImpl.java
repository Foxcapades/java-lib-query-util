package io.vulpine.lib.query.util.result;

import java.sql.Statement;

import io.vulpine.lib.query.util.ReadQuery;
import io.vulpine.lib.query.util.ReadResult;

public class ReadResultImpl <
  V,
  Q extends ReadQuery < ?, ?, ? >,
  S extends Statement >
extends QueryResultImpl < Q, S >
implements ReadResult < V, Q >
{
  private final V value;

  public ReadResultImpl(Q query, S stmt, V value) {
    super(query, stmt);
    this.value = value;
  }

  @Override
  public V getValue() {
    return value;
  }
}
