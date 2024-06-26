package com.agilevolve.domain.common.model;

import java.io.Serializable;

public abstract class AbstractBaseEntity implements Serializable {
  private static final long serialVersionUID = 9756258742L;

  public abstract boolean equals(Object obj);

  public abstract int hashCode();

  public abstract String toString();

}
