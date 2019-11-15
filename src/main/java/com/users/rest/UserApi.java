package com.users.rest;


import com.generic.exception.InvalidDateException;
import com.generic.exception.UserAlreadyExistException;
import com.rest.BaseApi;
import com.users.dto.PersonDto;
import com.users.ejb.PersonBean;
import com.users.model.Person;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.util.DateParam;
import org.apache.log4j.Logger;

/**
 * The com.users.rest UserApi.java
 *
 * @author Daniel Mboya
 * @version 1.0.0
 * @since Sep 27, 2017 12:40:55 PM
 */

@Path("/")
public class UserApi  extends BaseApi {
  Logger logger = Logger.getLogger(getClass());
  @EJB
  private PersonBean userBean;
  @GET
  @Path("/addPerson")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response add(@QueryParam("surname")String surname, @QueryParam("firstname")String firstname,
                      @QueryParam("dob") String dob, @QueryParam("email")String email, @QueryParam("mobile_no")String mobile_no) {
    try {

      logger.info("Data="+surname+","+firstname+","+dob+","+email+","+mobile_no+"");
      Person person=new Person();
      person.setSurname(surname);
      person.setFirstName(firstname);
      person.setEmail(email);
      person.setMobileNo(mobile_no);
      person.setDob(new DateParam(dob).getDate());
       userBean.save(person);
      return success();
    }
    catch (InvalidDateException | UserAlreadyExistException e) {
      return invalidInput(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return invalidInput(e.getMessage());
    }

  }

  @GET
  @Path("/updatePerson")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response update(@QueryParam("id")Long id, @QueryParam("surname")String surname, @QueryParam("firstname")String firstname,
                         @QueryParam("dob") String dob, @QueryParam("email")String email, @QueryParam("mobile_no")String mobile_no) {
    try {
      Person person=new Person();
      person.setId(id);
      person.setSurname(surname);
      person.setFirstName(firstname);
      person.setEmail(email);
      person.setMobileNo(mobile_no);
      person.setDob(new DateParam(dob).getDate());

       userBean.update(person);
      return success();
    }  catch (InvalidDateException e) {
      return invalidInput(e.getMessage());
    }
    catch (UserAlreadyExistException e) {
      return invalidInput(e.getMessage());
    }
    catch (Exception e) {
      e.printStackTrace();
      return serverFailure();
    }

  }


  @GET
  @Path("/getPerson")
  @Produces(MediaType.APPLICATION_JSON)
  public Response show(@QueryParam("id") Long id) {
    try {
      Person person = userBean.findById(id);
      if (person == null)
        return notFound();
      else {
       return entity(new PersonDto().fromPerson(person));
      }
    } catch (Exception e) {
      e.printStackTrace();
      return serverFailure();
    }

  }

}
