package com.mladen.cikara.javacro.parser.wrapper;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringPath;

public class StringPathWrapper extends AbstractPathWrapper<StringPath> {

  public StringPathWrapper(StringPath path) {
    this.path = path;
  }

  @Override
  public BooleanExpression doEq(Object value) {
    return path.eq((String) value);
  }

  @Override
  public BooleanExpression doNe(Object value) {
    return path.ne((String) value);
  }

}
