package js;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import js.dao.impl.UserDAOImpl;
import js.entity.Stations;
import js.entity.Tickets;
import js.entity.Users;
import js.exception.UserAuthenticationFailedException;
import js.exception.UserRegistrationFailedException;
import js.service.TimetableService;

/**
 * @author Alexander Markov
 */
public class HibernateTest {

	@Test
	// (expected = UserRegistrationFailedException.class)
	public void registerUserTest() throws UserRegistrationFailedException,
			ParseException {
		Assert.assertEquals(
				Byte.valueOf("1"),
				new UserDAOImpl().registerUser("test", "test", "test@test.com",
						"TestName", "TestSurname",
						new SimpleDateFormat("yyyy-MM-dd").parse("1980-19-09"))
						.getStatus());
	}

	@Test
	public void authenticateUserTest() throws UserAuthenticationFailedException {
		Assert.assertEquals(true,
				new UserDAOImpl().authenticateUser("test", "test"));
	}

	@Test
	public void getAllUsersTest() {
		List<Users> users = new UserDAOImpl().getAllUsers();
		Assert.assertEquals(true, users.iterator().hasNext());
	}

	@Test
	public void getTicketsByUserTest() throws ParseException {
		Users user = new Users();
		user.setLogin("alexm");
		Set<Tickets> tickets = new UserDAOImpl().getTicketsByUser(user);
		Assert.assertEquals(false, tickets.isEmpty());
	}

	@Test
	public void getTimetableByStationTest() {
		Stations station = new Stations("Saint-Petersburg");
		Map<String, List<?>> timetable = new TimetableService()
				.getTimetableByStation(station);
		Assert.assertEquals(false, timetable.isEmpty());
		Set<String> stationNames = timetable.keySet();
		for (String name : stationNames) {
			System.out.println("departure time: " + timetable.get(name).get(0)
					+ "\narrival station: " + name);
		}
	}
}