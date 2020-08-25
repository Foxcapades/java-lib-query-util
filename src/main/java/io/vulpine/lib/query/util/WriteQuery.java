package io.vulpine.lib.query.util;

import java.sql.Statement;

public interface WriteQuery <
  R extends WriteResult < ? extends WriteQuery < ?, ? > >,
  S extends Statement >
extends Query < R, S >
{
}
