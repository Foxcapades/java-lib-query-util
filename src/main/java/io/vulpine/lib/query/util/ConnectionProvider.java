package io.vulpine.lib.query.util;

import java.sql.Connection;

@FunctionalInterface
public interface ConnectionProvider
{
  Connection get() throws Exception;
}
