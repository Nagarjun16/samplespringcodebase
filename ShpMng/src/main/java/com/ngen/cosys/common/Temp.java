package com.ngen.cosys.common;

/**
 * @author NIIT Technologies Ltd.
 *
 * @param <T>
 * @param <E>
 */
import java.lang.reflect.InvocationTargetException;

import org.springframework.stereotype.Service;

@Service
public abstract class Temp<T, E> implements ServiceExecutor {

   private T t;
   protected E e;
   private String taskSchedular;

   public void run() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
         InvocationTargetException, NoSuchMethodException, SecurityException {
   }

   public Temp<T, E> request(E e, String taskSchedular) {
      this.e = e;
      this.taskSchedular = taskSchedular;
      return this;
   }

   protected void request(E e) {
      this.e = e;
   }

   public E response() {
      return this.e;
   }

   protected String getM() {
      return this.taskSchedular;
   }

   protected Class<T> get() {
      return (Class<T>) this.t.getClass();
   }
}