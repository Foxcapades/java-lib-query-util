package io.vulpine.lib.query.util;

import java.sql.ResultSet;

@FunctionalInterface
public interface RowParser < V >
{
  V parse(ResultSet rs) throws Exception;
}
