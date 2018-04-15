package com.mladen.cikara.javacro.parser.wrapper;

import com.mladen.cikara.javacro.parser.FilterParserException;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DateTimePathWrapper extends AbstractPathWrapper<DateTimePath<LocalDateTime>> {

  public DateTimePathWrapper(DateTimePath<LocalDateTime> path) {
    this.path = path;
  }

  private LocalDateTime convertStringToLocalDateTime(Object value) {
    LocalDateTime localDateTime;

    try {
      localDateTime = LocalDateTime.parse((String) value);
    } catch (final DateTimeParseException e) {
      throw new FilterParserException();
    }
    return localDateTime;
  }

  @Override
  public BooleanExpression doEq(Object value) {
    final LocalDateTime localDateTime = convertStringToLocalDateTime(value);

    return path.eq(localDateTime);
  }

  @Override
  public BooleanExpression doGt(Object value) {
    final LocalDateTime localDateTime = convertStringToLocalDateTime(value);

    return path.gt(localDateTime);
  }

  @Override
  public BooleanExpression doLt(Object value) {
    final LocalDateTime localDateTime = convertStringToLocalDateTime(value);

    return path.lt(localDateTime);
  }

  @Override
  public BooleanExpression doNe(Object value) {
    final LocalDateTime localDateTime = convertStringToLocalDateTime(value);

    return path.ne(localDateTime);
  }
}
