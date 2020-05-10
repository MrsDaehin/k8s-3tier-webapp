package webapp.tier.resource;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;

import webapp.tier.service.HazelcastCacheService;
import webapp.tier.service.HazelcastMqService;

@Path("/quarkus/hazelcast")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HazelcastResource {

	@Inject
	HazelcastCacheService cachesvc;

	@Inject
	HazelcastMqService mqsvc;

	@POST
	@Path("/setcache")
	@Counted(name = "performedChecks_setcache", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_setcache", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response putcache() {
//		HazelcastCacheService cachesvc = new HazelcastCacheService();
		try {
			return Response.ok().entity(cachesvc.setMsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/getcache")
	@Counted(name = "performedChecks_getcache", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_getcache", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response getcache() {
//		HazelcastCacheService cachesvc = new HazelcastCacheService();
		try {
			return Response.ok().entity(cachesvc.getMsgList()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/putqueue")
	@Counted(name = "performedChecks_putqueue", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_putqueue", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response putqueue() {
//		HazelcastMqService mqsvc = new HazelcastMqService();
		try {
			return Response.ok().entity(mqsvc.putMsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/getqueue")
	@Counted(name = "performedChecks_getqueue", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_getqueue", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response getqueue() {
//		HazelcastMqService mqsvc = new HazelcastMqService();
		try {
			return Response.ok().entity(mqsvc.getMsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("/publish")
	@Counted(name = "performedChecks_publish", description = "How many primality checks have been performed.")
	@Timed(name = "checksTimer_publish", description = "A measure of how long it takes to perform the primality test.", unit = MetricUnits.MILLISECONDS)
	public Response publish() {
//		HazelcastMqService mqsvc = new HazelcastMqService();
		try {
			return Response.ok().entity(mqsvc.publishMsg()).build();
		} catch (Exception e) {
			return Response.status(500).entity(e.getMessage()).build();
		}
	}
}
