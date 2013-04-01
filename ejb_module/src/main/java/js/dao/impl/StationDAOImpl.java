package js.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;

import js.dao.HibernateUtil;
import js.dao.StationDAO;
import js.entity.Stations;
import js.exception.DataAccessException;

/**
 * @author Alexander Markov
 */
@Stateless
public class StationDAOImpl implements StationDAO {
	@Override
	public Boolean addStation(Stations station) throws DataAccessException {
		try {
			HibernateUtil.beginTransaction();
			HibernateUtil.getSession().saveOrUpdate("Stations", station);
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return Boolean.TRUE;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Stations> getAllStations() throws DataAccessException {
		List<Stations> stations = new ArrayList<Stations>();
		try {
			HibernateUtil.beginTransaction();
			stations = (List<Stations>) HibernateUtil.getSession()
					.createCriteria(Stations.class).addOrder(Order.asc("name"))
					.list();
			HibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			HibernateUtil.rollbackTransaction();
			throw new DataAccessException(e.getLocalizedMessage());
		} finally {
			HibernateUtil.closeSession();
		}
		return stations;
	}
}