package by.jpdm.controller;

import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("users")
@Controller
public class AppController {

    @GET
    public String sayHello() {
        return "hello.jsp";
    }
}
