package webapp.tier.resource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;

import org.junit.jupiter.api.Test;

import com.datastax.oss.quarkus.test.CassandraTestResource;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.bean.MsgBean;

@QuarkusTest
@QuarkusTestResource(CassandraTestResource.class)
class CassandraResourceTest {

	@Test
	void testInsert() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/cassandra/insert")
				.then()
				.statusCode(200)
				.body(containsString("Hello k8s-3tier-webapp with quarkus"))
				.extract()
				.body()
				.as(MsgBean.class);
	}

	@Test
	void testSelect() {
		given()
				.when()
				.get("/quarkus/cassandra/select")
				.then()
				.statusCode(200)
				.body(containsString("No Data."))
				.extract()
				.body()
				.as(MsgBean[].class);
	}

	@Test
	void testDelete() {
		given()
				.when()
				.contentType("application/json")
				.post("/quarkus/cassandra/delete")
				.then()
				.statusCode(200)
				.body(is("Delete Msg Records"));
	}
}
