package com.w2m.spaceship.service.domain;

public class NotFoundException extends RuntimeException{
  public NotFoundException(String message) {
    super(message);
  }
}
