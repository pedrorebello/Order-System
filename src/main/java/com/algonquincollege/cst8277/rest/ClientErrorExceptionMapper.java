/**
 * File: ClientErrorExceptionMapper.java
 * Course materials (20F) CST 8277
 * @author Mike Norman
 * 
 * This isn't 100% necessary, I just like to have a simple JSON response instead of Payara's
 * default error page which is in HTML
 */
package com.algonquincollege.cst8277.rest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ClientErrorExceptionMapper implements ExceptionMapper<ClientErrorException> {
    
    @Override
    public Response toResponse(ClientErrorException exception) {
      Response response = exception.getResponse();
      Response.StatusType statusType = response.getStatusInfo();
      int statusCode = statusType.getStatusCode();
      String reasonPhrase = statusType.getReasonPhrase();
      HttpErrorResponse entity = new HttpErrorResponse(statusCode, reasonPhrase);
      return Response.status(statusCode).entity(entity).build();
    }
}