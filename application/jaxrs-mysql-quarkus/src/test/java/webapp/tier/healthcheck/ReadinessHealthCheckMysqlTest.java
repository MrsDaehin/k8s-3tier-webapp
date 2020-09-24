package webapp.tier.healthcheck;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.eclipse.microprofile.health.HealthCheckResponse.State;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.junit.QuarkusTest;
import webapp.tier.service.MysqlService;

@QuarkusTest
class ReadinessHealthCheckMysqlTest {

	@Test
	void testCallDown() {
		ReadinessHealthCheckMysql hc = new ReadinessHealthCheckMysql();
		assertEquals(State.DOWN, hc.call().getState(), "Unexpected status");
	}

	@Test
	void testCallUp() throws IOException, TimeoutException {
		ReadinessHealthCheckMysql hc = new ReadinessHealthCheckMysql() {
			protected MysqlService createMysqlService() {
				MysqlService mock = Mockito.mock(MysqlService.class);
				Mockito.when(mock.connectionStatus()).thenReturn(true);
				return mock;
			}
		};
		assertEquals(State.UP, hc.call().getState(), "Unexpected status");
	}
}
