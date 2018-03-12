package io.maxilog.errorHandler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.HibernateOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mossa on 04/11/2017.
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(notFoundException.class)
    public ResponseEntity<?> notFoundException(Exception e, HttpServletResponse response) throws IOException {
        //response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return ResponseEntity.status(404).body("{\"message\":\""+e.getMessage()+"\"}");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationException(ConstraintViolationException e, HttpServletResponse response) throws IOException {
        //e.getConstraintViolations().iterator().next().getMessage()
        List<String> errors = new ArrayList<>();
        for(ConstraintViolation violation : e.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getConstraintViolations().iterator().next().getMessage());
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public void InvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Error deleting");
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public void DataIntegrityViolationException(DataIntegrityViolationException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Error creation");
    }

    @ExceptionHandler(HibernateOptimisticLockingFailureException.class)
    public void HibernateOptimisticLockingFailureException(HibernateOptimisticLockingFailureException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), "Error Updationg");
    }
}
