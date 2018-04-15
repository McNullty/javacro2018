package com.mladen.cikara.javacro.parser.wrapper;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.BooleanExpression;

public abstract class AbstractPathWrapper<T> implements PathWrapper<Path<T>>, OperationsInterface {
  protected T path;

  @Override
  public BooleanExpression doAnd(BooleanExpression value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanExpression doEq(Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanExpression doGt(Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanExpression doLt(Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanExpression doNe(Object value) {
    throw new UnsupportedOperationException();
  }

  @Override
  public BooleanExpression doOr(BooleanExpression value) {
    throw new UnsupportedOperationException();
  }

}
