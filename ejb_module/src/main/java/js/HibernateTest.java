package js;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import js.dao.HibernateUtil;
import js.dto.TicketDTO;
import js.dto.TimetableServiceDTO;
import js.dto.TrainServiceDTO;
import js.dto.UserDTO;
import js.exception.DataAccessException;
import js.exception.InvalidInputException;
import js.exception.TicketOrderFailedException;
import js.exception.UserRegistrationFailedException;
import js.service.StationService;
import js.service.TicketService;
import js.service.TimetableService;
import js.service.TrainService;
import js.service.UserService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit tests for all services invoking all DAOs
 * 
 * @author Alexander Markov
 */
public class HibernateTest {

	private UserService userService;
	private TimetableService timetableService;
	private TrainService trainService;
	private StationService stationService;
	private TicketService ticketService;

	@Before
	public void initHibernateTest() {
		HibernateUtil.init();
		this.userService = new UserService();
		this.timetableService = new TimetableService();
		this.trainService = new TrainService();
		this.stationService = new StationService();
		this.ticketService = new TicketService();
	}

	@Test
	public void registerUserTest() throws UserRegistrationFailedException,
			ParseException, DataAccessException {
		System.out.println(new SimpleDateFormat("yyyy-MM-dd")
				.parse("1980-19-09"));
		Assert.assertEquals(true, userService.registerUser("test", "test",
				"test@test.com", "TestName", "TestSurname",
				new SimpleDateFormat("yyyy-MM-dd").parse("1981-07-09")));
	}

	@Test(expected = UserRegistrationFailedException.class)
	public void registerUserTestWithException()
			throws UserRegistrationFailedException, ParseException,
			DataAccessException {
		Assert.assertEquals(true, userService.registerUser("test", "test",
				"test@test.com", "TestName", "TestSurname",
				new SimpleDateFormat("yyyy-MM-dd").parse("1980-19-09")));
	}

	@Test
	public void authenticateUserTest() throws DataAccessException {
		Assert.assertEquals(2, userService.authenticateUser("test", "test"));
	}

	@Test
	public void getAllUsersTest() throws DataAccessException {
		Map<String, UserDTO> users = userService.getAllUsers();
		Assert.assertEquals(true, users.keySet().iterator().hasNext());
	}

	@Test
	public void getTicketsByUserTest() throws ParseException,
			DataAccessException {
		Map<Long, TicketDTO> tickets = userService.getTicketsByUser("test");
		Set<Long> ticketNumbers = tickets.keySet();
		Assert.assertEquals(false, ticketNumbers.isEmpty());
		System.out
				.println("ticket number\ttrain number\ttrain name\tdeparture\t"
						+ "destination\tdate\tdeparture time\tarrival time");
		for (Long ticketNumber : ticketNumbers) {
			System.out.println(ticketNumber.toString() + "\t"
					+ tickets.get(ticketNumber).getTrainNumber() + "\t"
					+ tickets.get(ticketNumber).getTrainName() + "\t"
					+ tickets.get(ticketNumber).getDepartureStation() + "\t"
					+ tickets.get(ticketNumber).getArrivalStation() + "\t"
					+ tickets.get(ticketNumber).getDate() + "\t"
					+ tickets.get(ticketNumber).getDepartureTime() + "\t"
					+ tickets.get(ticketNumber).getArrivalTime());
		}
	}

	@Test
	public void getTimetableByStationTest() throws DataAccessException,
			InvalidInputException {
		Map<String, TimetableServiceDTO> timetable = timetableService
				.getTimetableByStation("Saint-Petersburg");
		Set<String> trainNumbers = timetable.keySet();
		Assert.assertEquals(false, trainNumbers.isEmpty());
		System.out.println("train number\ttrain name\tdestination\t"
				+ "departure time\tarrival time\ttickets left");
		for (String trainNumber : trainNumbers) {
			System.out.println(trainNumber + "\t"
					+ timetable.get(trainNumber).getTrainName() + "\t"
					+ timetable.get(trainNumber).getArrivalStation() + "\t"
					+ timetable.get(trainNumber).getDepartureTime() + "\t"
					+ timetable.get(trainNumber).getArrivalTime() + "\t"
					+ timetable.get(trainNumber).getTicketsLeft());
		}
	}

	@Test(expected = InvalidInputException.class)
	public void getTimetableByStationTestWithException()
			throws DataAccessException, InvalidInputException {
		timetableService.getTimetableByStation("Unexisting_station");
	}

	@Test
	public void getTimetableFromAToBInTimeIntervalTest() throws ParseException,
			DataAccessException, InvalidInputException {
		Date date = new Date();
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Map<Integer, TimetableServiceDTO> timetable = timetableService
				.getTimetableFromAToBInTimeInterval("Saint-Petersburg",
						"Moscow", date, timeFormat.parse("23:00"),
						timeFormat.parse("23:59"));
		Set<Integer> trainNumbers = timetable.keySet();
		Assert.assertEquals(false, trainNumbers.isEmpty());
		System.out.println("train number\ttrain name\tdate\t"
				+ "departure time\tarrival time\ttickets left");
		for (Integer trainNumber : trainNumbers) {
			System.out.println(trainNumber.toString() + "\t"
					+ timetable.get(trainNumber).getTrainName() + "\t" + date
					+ "\t" + timetable.get(trainNumber).getDepartureTime()
					+ "\t" + timetable.get(trainNumber).getArrivalTime() + "\t"
					+ timetable.get(trainNumber).getTicketsLeft());
		}
	}

	@Test(expected = InvalidInputException.class)
	public void getTimetableFromAToBInTimeIntervalTestWithException()
			throws ParseException, DataAccessException, InvalidInputException {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		timetableService.getTimetableFromAToBInTimeInterval("Saint-Petersburg",
				"Moscow", new Date(), timeFormat.parse("23:59"),
				timeFormat.parse("23:00"));
	}

	@Test
	public void getAllTrainsTest() throws DataAccessException {
		Map<Integer, TrainServiceDTO> timetable = trainService.getAllTrains();
		Set<Integer> trainNumbers = timetable.keySet();
		Assert.assertEquals(false, trainNumbers.isEmpty());
		System.out.println("train number\ttrain name\tdeparture\t"
				+ "destination\tdeparture time\tarrival time\tcapacity");
		for (Integer trainNumber : trainNumbers) {
			System.out.println(trainNumber.toString() + "\t"
					+ timetable.get(trainNumber).getTrainName() + "\t"
					+ timetable.get(trainNumber).getDepartureStation() + "\t"
					+ timetable.get(trainNumber).getArrivalStation() + "\t"
					+ timetable.get(trainNumber).getDepartureTime() + "\t"
					+ timetable.get(trainNumber).getArrivalTime() + "\t"
					+ timetable.get(trainNumber).getTrainCapacity());
		}
	}

	@Test(expected = DataAccessException.class)
	public void addTrainTestWithDataAccessException()
			throws DataAccessException, ParseException, InvalidInputException {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		trainService.addTrain(1, "Existing train", 400, "Saint-Petersburg",
				"Moscow", timeFormat.parse("23:00"), timeFormat.parse("8:00"));
	}

	@Test(expected = InvalidInputException.class)
	public void addTrainTestWithInvalidInputException()
			throws DataAccessException, ParseException, InvalidInputException {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		trainService.addTrain(2000, "Train", 400, "SPb", "Moscow",
				timeFormat.parse("23:00"), timeFormat.parse("8:00"));
	}

	@Test
	public void addTrainTest() throws DataAccessException, ParseException,
			InvalidInputException {
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Assert.assertEquals(true, trainService.addTrain(200, "Eiffel Tower",
				500, "Saint-Petersburg", "Paris", timeFormat.parse("23:00"),
				timeFormat.parse("8:00")));
	}

	@Test
	public void getUsersByTrainTest() throws DataAccessException,
			InvalidInputException, ParseException {
		Map<String, UserDTO> users = trainService.getUsersByTrain(1,
				new SimpleDateFormat("yyyy-MM-dd").parse("2013-04-01"));
		Assert.assertEquals(false, users.isEmpty());
		Set<String> userLogins = users.keySet();
		System.out
				.println("login\tname\tsurname\tbirthdate\temail\ttype\tstatus");
		for (String login : userLogins) {
			System.out.println(login
					+ "\t"
					+ users.get(login).getName()
					+ "\t"
					+ users.get(login).getSurname()
					+ "\t"
					+ users.get(login).getBirthdate()
					+ "\t"
					+ users.get(login).getEmail()
					+ "\t"
					+ ((Byte) users.get(login).getUserType() == Byte
							.parseByte("1") ? "admin" : "passenger")
					+ "\t"
					+ ((Byte) users.get(login).getStatus() == Byte
							.parseByte("1") ? "registered" : "logged in"));
		}
	}

	@Test(expected = InvalidInputException.class)
	public void getUsersByTrainTestWithException() throws DataAccessException,
			InvalidInputException {
		trainService.getUsersByTrain(0, new Date());
	}

	@Test
	public void getAllStationsTest() throws DataAccessException {
		Set<String> stations = stationService.getAllStations();
		Assert.assertEquals(true, stations.size() > 0);
		Iterator<String> iter = stations.iterator();
		while (iter.hasNext()) {
			String station = iter.next();
			System.out.println(station);
		}
	}

	@Test
	public void addStationTest() throws DataAccessException {
		Assert.assertEquals(true, stationService.addStation("Vladivostock"));
	}

	@Test(expected = DataAccessException.class)
	public void addStationTestWithException() throws DataAccessException {
		Assert.assertEquals(true, stationService.addStation("Vladivostock"));
	}

	@Test
	public void buyTicketTest() throws DataAccessException, ParseException,
			TicketOrderFailedException, InvalidInputException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Assert.assertEquals(true, ticketService.buyTicket("test", 25,
				"Saint-Petersburg", "Moscow", dateFormat.parse("2013-05-01"),
				timeFormat.parse("23:45"), timeFormat.parse("7:30")));
	}

	@Test(expected = TicketOrderFailedException.class)
	public void buyTicketTestWithException() throws DataAccessException,
			ParseException, TicketOrderFailedException, InvalidInputException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		Assert.assertEquals(true, ticketService.buyTicket("test", 25,
				"Saint-Petersburg", "Moscow", dateFormat.parse("2013-05-01"),
				timeFormat.parse("23:45"), timeFormat.parse("7:30")));
	}
}