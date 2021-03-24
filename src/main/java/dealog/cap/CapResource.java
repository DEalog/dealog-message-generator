package dealog.cap;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/bbk-mock")
public class CapResource {

    @GET
    @Path("/bbk.katwarn/warnmeldung.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOne() throws Exception{
        return buildResponse("one.json");
    }

    @GET
    @Path("/bbk.biwapp/warnmeldung.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTwo() throws Exception{
        return buildResponse("two.json");
    }

    @GET
    @Path("/bbk.dwd/warnmeldung.json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getThree() throws Exception{
        return buildResponse("three.json");
    }

    private Response buildResponse(final String filename) throws Exception {
        String caps = Resources.toString(Resources.getResource(filename), Charsets.UTF_8);

        final SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy hh:mm:ss z");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return Response.ok(caps).header("last-modified", sdf.format(new Date())).build();
    }
}
