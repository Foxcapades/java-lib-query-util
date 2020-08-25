package io.vulpine.lib.query.util.result;

import java.sql.Statement;

import io.vulpine.lib.query.util.WriteQuery;
import io.vulpine.lib.query.util.WriteResult;

public class WriteResultImpl <
  Q extends WriteQuery < ?, ? >,
  S extends Statement >
extends QueryResultImpl < Q, S >
implements WriteResult < Q >
{
  private final int updateCount;

  public WriteResultImpl(Q query, S stmt, int updateCount) {
    super(query, stmt);
    this.updateCount = updateCount;
  }

  @Override
  public int getNumRowsAffected() {
    return updateCount;
  }
}
