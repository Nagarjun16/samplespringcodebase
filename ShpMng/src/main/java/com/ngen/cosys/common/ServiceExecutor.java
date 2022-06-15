package com.ngen.cosys.common;

import java.lang.reflect.InvocationTargetException;

import org.springframework.stereotype.Service;

/**
 * @author NIIT Technologies Ltd.
 *
 */
@Service
@FunctionalInterface
public interface ServiceExecutor {
   void run() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
         InvocationTargetException, NoSuchMethodException, SecurityException;
}