package io.vulpine.lib.query.util;

import java.sql.PreparedStatement;

@FunctionalInterface
public interface StatementPreparer
{
  void prepare(PreparedStatement ps) throws Exception;
}
