package com.mladen.cikara.javacro.parser.wrapper;

import com.querydsl.core.types.dsl.BooleanExpression;

public interface OperationsInterface {

  BooleanExpression doAnd(BooleanExpression value);

  BooleanExpression doEq(Object value);

  BooleanExpression doGt(Object value);

  BooleanExpression doLt(Object value);

  BooleanExpression doNe(Object value);

  BooleanExpression doOr(BooleanExpression value);
}
