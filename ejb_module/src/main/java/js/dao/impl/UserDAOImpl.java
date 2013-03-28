package js.dao.impl;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import js.dao.HibernateUtil;
import js.dao.UserDAO;
import js.entity.Tickets;
import js.entity.Users;
import js.exception.DataAccessException;
import js.exception.UserRegistrationFailedException;

/**
 * @author Alexander Markov
 */
@Stateless
public class UserDAOImpl implements UserDAO {
	@Override
	public Boolean registerUser(String login, String password, String email,
			String name, String surname, Date birthDate)
			throws UserRegistrationFailedException, DataAccessException {
		MessageDigest md5 = null;
		MessageDigest sha256 = null;
		byte[] sha256Bytes = null;
		StringBuilder strBuilder = new StringBuilder();
		try {
			md5 = MessageDigest.getInstance("MD5");
			sha256 = MessageDigest.getInstance("SHA-256");
			sha256Bytes = sha256.digest(md5.digest(password.getBytes("UTF-8")));
			for (byte b : sha256Bytes) {
				strBuilder.append((char) (b + 128));
			}
		} catch (NoSuchAlgorithmException e) {
			throw new DataAccessException(e.getLocalizedMessage());
		} catch (UnsupportedEncodingException e) {
			throw new DataAccessException(e.getLocalizedMessage());
		}
		Users user = new Users(login, strBuilder.toString(), email, name,
				surname, birthDate, Byte.parseByte("2"), Byte.parseByte("1"));
		try {
			HibernateUtil.beginTransaction();
			Long count = (Long) HibernateUtil.getSession()
					.createCriteria(Users.class)
					.add(Restrictions.eq("login", login))
					.setProjection(Projections.rowCount()).uniqueResult();
			if (count > 0) {
				throw new UserRegistrationFailedException(
						"The user with the login = " + login
								+ " already exists");
			}
			count = (Long) HibernateUtil.getSession()
					.createCriteria(Users.class)
					.add(Restrictions.eq("email", email))
					.setProjection(Projections.rowCount()).uniqueResult();
			if (count > 0) {
				throw new UserRegistrationFailedException(
						"The user with the email = " + email
								+ " already exists");
			}
			HibernateUtil.getSession().saveOrUpdate("Users", user);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return (user.getStatus() == 1);
	}

	@Override
	public int authenticateUser(String login, String password)
			throws DataAccessException {
		MessageDigest md5 = null;
		MessageDigest sha256 = null;
		byte[] sha256Bytes = null;
		StringBuilder strBuilder = new StringBuilder();
		try {
			md5 = MessageDigest.getInstance("MD5");
			sha256 = MessageDigest.getInstance("SHA-256");
			sha256Bytes = sha256.digest(md5.digest(password.getBytes("UTF-8")));
			for (byte b : sha256Bytes) {
				strBuilder.append((char) (b + 128));
			}
		} catch (NoSuchAlgorithmException e) {
			throw new DataAccessException(e.getLocalizedMessage());
		} catch (UnsupportedEncodingException e) {
			throw new DataAccessException(e.getLocalizedMessage());
		}
		Users user = null;
		try {
			HibernateUtil.beginTransaction();
			user = (Users) HibernateUtil.getSession()
					.createCriteria(Users.class)
					.add(Restrictions.eq("login", login))
					.add(Restrictions.eq("password", strBuilder.toString()))
					.uniqueResult();//.setProjection(Projections.rowCount())
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		if(user != null) {
			return user.getUserType();
		}
		return -1;//(count == 1)
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getAllUsers() throws DataAccessException {
		List<Users> users = new ArrayList<Users>();
		try {
			HibernateUtil.beginTransaction();
			users = (List<Users>) HibernateUtil.getSession()
					.createCriteria(Users.class).addOrder(Order.asc("login"))
					.list();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return users;
	}

	@Override
	public Set<Tickets> getTicketsByUser(Users user) throws DataAccessException {
		Set<Tickets> tickets = new HashSet<Tickets>();
		try {
			HibernateUtil.beginTransaction();
			Users userT = (Users) HibernateUtil.getSession()
					.createCriteria(Users.class)
					.add(Restrictions.eq("login", user.getLogin()))
					.uniqueResult();
			tickets = userT.getTickets();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return tickets;
	}

	// @PersistenceUnit
	// private EntityManager em;
	// public Users createUser(String name) {
	// Users user = new Users();
	// user.setName(name);
	// em.persist(user);
	// return user;
	// }
}