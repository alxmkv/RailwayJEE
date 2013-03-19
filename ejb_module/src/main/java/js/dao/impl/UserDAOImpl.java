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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import js.dao.HibernateUtil;
import js.dao.UserDAO;
import js.entity.Tickets;
import js.entity.Users;
import js.exception.UserAuthenticationFailedException;
import js.exception.UserRegistrationFailedException;

/**
 * @author Alexander Markov
 */
@Stateless
public class UserDAOImpl implements UserDAO {

	@PersistenceUnit
	private EntityManager em;

	public Users createUser(String name) {
		Users user = new Users();
		user.setName(name);
		em.persist(user);
		return user;
	}

	@Override
	public Users registerUser(String login, String password, String email,
			String name, String surname, Date birthDate)
			throws UserRegistrationFailedException {
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
			throw new UserRegistrationFailedException(e.getLocalizedMessage());
		} catch (UnsupportedEncodingException e) {
			throw new UserRegistrationFailedException(e.getLocalizedMessage());
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
			throw new UserRegistrationFailedException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return user;
	}

	@Override
	public Boolean authenticateUser(String login, String password)
			throws UserAuthenticationFailedException {
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
			throw new UserAuthenticationFailedException(e.getLocalizedMessage());
		} catch (UnsupportedEncodingException e) {
			throw new UserAuthenticationFailedException(e.getLocalizedMessage());
		}
		Long count = null;
		try {
			HibernateUtil.beginTransaction();
			count = (Long) HibernateUtil.getSession()
					.createCriteria(Users.class)
					.add(Restrictions.eq("login", login))
					.add(Restrictions.eq("password", strBuilder.toString()))
					.setProjection(Projections.rowCount()).uniqueResult();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			return Boolean.FALSE;
		} finally {
			HibernateUtil.closeSession();
		}
		return (count == 1);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Users> getAllUsers() {
		List<Users> users = new ArrayList<Users>();
		// ScrollableResults results = null;
		try {
			HibernateUtil.beginTransaction();
			users = (List<Users>) HibernateUtil.getSession()
					.createCriteria(Users.class).addOrder(Order.asc("login"))
					.list();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			return null;
		} finally {
			HibernateUtil.closeSession();
		}
		return users;
	}

	@Override
	public Set<Tickets> getTicketsByUser(Users user) {
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
			return null;
		} finally {
			HibernateUtil.closeSession();
		}
		return tickets;
	}
}