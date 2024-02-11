package br.com.giannatech.giannaschool.exceptions;

public class CourseNotFoundException extends RuntimeException {

  public CourseNotFoundException(String message) {
    super(message);
  }
}
