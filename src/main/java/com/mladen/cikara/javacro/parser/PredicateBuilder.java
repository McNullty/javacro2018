package com.mladen.cikara.javacro.parser;

import com.mladen.cikara.javacro.parser.wrapper.DateTimePathWrapper;
import com.mladen.cikara.javacro.parser.wrapper.NumberPathWrapper;
import com.mladen.cikara.javacro.parser.wrapper.OperationsInterface;
import com.mladen.cikara.javacro.parser.wrapper.PredicateWrapper;
import com.mladen.cikara.javacro.parser.wrapper.StringPathWrapper;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PredicateBuilder<T> extends FilterParserBaseListener {

  private static final Logger logger = LoggerFactory.getLogger(PredicateBuilder.class);

  private final Map<String, Path<?>> paths = new HashMap<>();
  private final Stack<Object> stack = new Stack<>();
  private Predicate filter = null;
  private boolean firstFlage = true;

  public PredicateBuilder(T obj) {

    putFieldsInMap(obj, "");

    logger.trace("Done creating builder object");
  }

  @SuppressWarnings("unchecked")
  private Predicate buldExpression() {
    final Object rightHandSideObject = stack.pop();

    if (stack.isEmpty()) {
      return (Predicate) rightHandSideObject;
    }

    final Object operator = stack.pop();

    final Object leftHandSideObject;
    if (operator instanceof LogicalOperator) {
      leftHandSideObject = buldExpression();
    } else {
      leftHandSideObject = stack.pop();
    }

    OperationsInterface wrappedObject = null;

    Predicate predicate = null;
    if (leftHandSideObject instanceof StringPath) {
      wrappedObject = new StringPathWrapper((StringPath) leftHandSideObject);
    } else if (leftHandSideObject instanceof BooleanExpression) {
      wrappedObject = new PredicateWrapper((BooleanExpression) leftHandSideObject);
    } else if (leftHandSideObject instanceof NumberPath<?>) {
      wrappedObject = new NumberPathWrapper((NumberPath<?>) leftHandSideObject);
    } else if (leftHandSideObject instanceof DateTimePath<?>) {
      wrappedObject = new DateTimePathWrapper((DateTimePath<LocalDateTime>) leftHandSideObject);
    }

    if (operator instanceof RelationalOperator) {
      switch ((RelationalOperator) operator) {
      case EQ:
        predicate = wrappedObject.doEq(rightHandSideObject);
        break;
      case NE:
        predicate = wrappedObject.doNe(rightHandSideObject);
        break;
      case LT:
        predicate = wrappedObject.doLt(rightHandSideObject);
        break;
      case GT:
        predicate = wrappedObject.doGt(rightHandSideObject);
        break;
      default:
        throw new FilterParserException();
      }
    } else if (operator instanceof LogicalOperator) {
      switch ((LogicalOperator) operator) {
      case AND:
        predicate = wrappedObject.doAnd((BooleanExpression) rightHandSideObject);
        break;
      case OR:
        predicate = wrappedObject.doOr((BooleanExpression) rightHandSideObject);
        break;
      default:
        throw new FilterParserException();
      }
    }
    return predicate;
  }

  @Override
  public void exitExpression(@NotNull FilterParser.ExpressionContext ctx) {
    logger.debug("Expression, context: {}", ctx.getText());

    Boolean stackIsEmptyFlag = Boolean.FALSE;

    while (!stackIsEmptyFlag) {
      final Predicate predicate = buldExpression();

      if (stack.isEmpty()) {
        stackIsEmptyFlag = Boolean.TRUE;
      }
      stack.push(predicate);

      logger.debug("Pushed predicate {} on stack", predicate.toString());
    }
  }

  @Override
  public void exitExpression_list(@NotNull FilterParser.Expression_listContext ctx) {
    logger.debug("Expression list, context: {}", ctx.getText());

    filter = (Predicate) stack.pop();

    logger.debug("Created final predicate: {}", filter.toString());
  }

  @Override
  public void exitField(@NotNull FilterParser.FieldContext ctx) {
    logger.debug("Field, context: {}", ctx.getText());

    final Path<?> path = paths.get(ctx.getText());

    stack.push(path);

    logger.debug("Pushed path object {} on stack", ctx.getText());
  }

  @Override
  public void exitLogical_operator(@NotNull FilterParser.Logical_operatorContext ctx) {
    logger.debug("Logical operator, context: {}", ctx.getText());

    stack.push(LogicalOperator.valueOf(ctx.getText().toUpperCase()));

    logger.debug("Pushed logical operator {} on stack.", ctx.getText());
  }

  @Override
  public void exitNumber(@NotNull FilterParser.NumberContext ctx) {
    logger.debug("Number value, context: {}", ctx.getText());

    final String numberString = ctx.getText();

    Number number = null;
    try {
      number = Long.valueOf(numberString);
    } catch (final NumberFormatException e) {
      logger.debug("Conversion to Long failed, context: {}", ctx.getText());
    }

    if (number == null) {
      try {
        number = Double.valueOf(numberString);
      } catch (final NumberFormatException e) {
        logger.debug("Conversion to Double failed, context: {}", ctx.getText());
      }
    }

    if (number == null) {
      throw new FilterParserException();
    }

    stack.push(number);

    logger.debug("Pushed number {} on stack", number);
  }

  @Override
  public void exitRelational_operator(@NotNull FilterParser.Relational_operatorContext ctx) {
    logger.debug("Relational operator, context: {}", ctx.getText());

    stack.push(RelationalOperator.valueOf(ctx.getText().toUpperCase()));

    logger.debug("Pushed relational operator {} on stack.", ctx.getText());
  }

  @Override
  public void exitString(@NotNull FilterParser.StringContext ctx) {
    logger.debug("String, context: {}", ctx.getText());

    final String stringValue = sanitizeString(ctx.getText());

    stack.push(stringValue);

    logger.debug("Pushed String {} on stack", stringValue);
  }

  public Predicate getFilter(String filter) {

    final ANTLRInputStream antlrInputStream = new ANTLRInputStream(filter);

    final FilterLexer lexer = new FilterLexer(antlrInputStream);

    final CommonTokenStream tokens = new CommonTokenStream(lexer);

    final FilterParser parser = new FilterParser(tokens);

    final ParseTree tree = parser.expression_list();

    final ParseTreeWalker walker = new ParseTreeWalker();
    walker.walk(this, tree);

    return this.filter;
  }

  private void putFieldsInMap(Object obj, String prefix) {
    final Field[] fieldsArray = obj.getClass().getFields();

    for (final Field field : fieldsArray) {
      try {
        if (EntityPathBase.class.isAssignableFrom(field.getType()) && !firstFlage) {
          firstFlage = true;
          putFieldsInMap(field.get(obj), prefix + field.getName() + ".");

          logger.debug("Field {} is Entity", field);
        } else {
          if (firstFlage) {
            firstFlage = false;
          } else {
            paths.put(prefix + field.getName(), (Path<?>) field.get(obj));
          }
        }

      } catch (IllegalArgumentException | IllegalAccessException e) {
        logger.error(e.getMessage());

        throw new RuntimeException("Error while creating PredicateBuilder");
      }
    }
  }

  private String sanitizeString(String text) {
    return text.substring(1, text.length() - 1);
  }

}
