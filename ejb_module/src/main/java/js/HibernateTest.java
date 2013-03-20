package js;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import js.entity.Tickets;
import js.entity.Users;
import js.exception.UserAuthenticationFailedException;
import js.exception.UserRegistrationFailedException;
import js.service.TimetableService;
import js.service.UserService;

/**
 * @author Alexander Markov
 */
public class HibernateTest {

	private UserService userService;
	private TimetableService timetableService;

	@Before
	public void initHibernateTest() {
		this.userService = new UserService();
		this.timetableService = new TimetableService();
	}

	@Test
	// (expected = UserRegistrationFailedException.class)
	public void registerUserTest() throws UserRegistrationFailedException,
			ParseException {
		Assert.assertEquals(
				Byte.valueOf("1"),
				userService.registerUser("test", "test", "test@test.com",
						"TestName", "TestSurname",
						new SimpleDateFormat("yyyy-MM-dd").parse("1980-19-09"))
						.getStatus());
	}

	@Test
	public void authenticateUserTest() throws UserAuthenticationFailedException {
		Assert.assertEquals(true, userService.authenticateUser("test", "test"));
	}

	@Test
	public void getAllUsersTest() {
		List<Users> users = userService.getAllUsers();
		Assert.assertEquals(true, users.iterator().hasNext());
	}

	@Test
	public void getTicketsByUserTest() throws ParseException {
		Users user = new Users();
		user.setLogin("alexm");
		Set<Tickets> tickets = userService.getTicketsByUser(user);
		Assert.assertEquals(false, tickets.isEmpty());
	}

	@Test
	public void getTimetableByStationTest() {
		Map<String, List<?>> timetable = timetableService
				.getTimetableByStation("Saint-Petersburg");
		Set<String> trainNames = timetable.keySet();
		Assert.assertEquals(false, trainNames.isEmpty());
		System.out
				.println("train number\ttrain name\tdestination\tdeparture time\tarrival time\tcapacity");
		for (String trainName : trainNames) {
			System.out.println(timetable.get(trainName).get(0) + "\t"
					+ trainName + timetable.get(trainName).get(1) + "\t"
					+ timetable.get(trainName).get(2) + "\t"
					+ timetable.get(trainName).get(3) + "\t"
					+ timetable.get(trainName).get(4));
		}
	}

	@Test
	public void getTimetableFromAToBInTimeIntervalTest() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");
		Map<String, List<?>> timetable = timetableService
				.getTimetableFromAToBInTimeInterval("Saint-Petersburg",
						"Moscow", dateFormat.parse("23:00"), dateFormat.parse("23:59"));
		Set<String> trainNames = timetable.keySet();
		Assert.assertEquals(false, trainNames.isEmpty());
		System.out
				.println("train number\ttrain name\tdestination\tdeparture time\tarrival time\tcapacity");
		for (String trainName : trainNames) {
			System.out.println(timetable.get(trainName).get(0) + "\t"
					+ trainName + timetable.get(trainName).get(1) + "\t"
					+ timetable.get(trainName).get(2) + "\t"
					+ timetable.get(trainName).get(3) + "\t"
					+ timetable.get(trainName).get(4));
		}
	}
}