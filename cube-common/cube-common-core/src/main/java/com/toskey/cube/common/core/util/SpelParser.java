package com.toskey.cube.common.core.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * SpelParser
 *
 * @author toskey
 * @version 1.0
 * @since 2024/6/5
 */
public final class SpelParser {

    private final static SpelExpressionParser parser = new SpelExpressionParser();
    
    public static Object parse(ProceedingJoinPoint point, String expression) {
        ApplicationContext applicationContext = SpringContextHolder.getApplicationContext();
        if (applicationContext == null) {
            throw new ApplicationContextException("[SpelParser]: Not found application context.");
        }
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        for (int i = 0, size = point.getArgs().length; i < size; i++) {
            String pName = ((MethodSignature) point.getSignature()).getParameterNames()[i];
            context.setVariable(pName, point.getArgs()[i]);
        }
        return parser.parseExpression(expression).getValue(context);
    }
}
