package js.dao.impl;

import javax.ejb.Stateless;

import org.hibernate.HibernateException;

import js.dao.HibernateUtil;
import js.dao.StationDAO;
import js.entity.Stations;
import js.exception.DataAccessException;

/**
 * @author Alexander Markov
 */
@Stateless
// @Local(StationDAO.class)
// @LocalBean
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
}