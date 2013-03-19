package js.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Creates session factory and manages sessions + logging
 * 
 * @author Alexander Markov
 */
public class HibernateUtil {

	private static final Logger logger = LogManager
			.getLogger(HibernateUtil.class);
	private static final SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			Configuration configuration = new Configuration().configure();
			sessionFactory = configuration
					.buildSessionFactory(new ServiceRegistryBuilder()
							.applySettings(configuration.getProperties())
							.buildServiceRegistry());
		} catch (Throwable ex) {
			logger.error("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * @return Session factory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * @return Session with a started transaction
	 */
	public static Session beginTransaction() {
		Session session = HibernateUtil.getSession();
		session.beginTransaction();
		return session;
	}

	/**
	 * Commit a transaction
	 */
	public static void commitTransaction() {
		HibernateUtil.getSession().getTransaction().commit();
	}

	/**
	 * Rollback a transaction
	 */
	public static void rollbackTransaction() {
		HibernateUtil.getSession().getTransaction().rollback();
	}

	/**
	 * End a session by releasing the JDBC connection and cleaning up
	 */
	public static void closeSession() {
		HibernateUtil.getSession().close();
	}

	/**
	 * @return Current session
	 */
	public static Session getSession() {
		return sessionFactory.getCurrentSession();
	}
}