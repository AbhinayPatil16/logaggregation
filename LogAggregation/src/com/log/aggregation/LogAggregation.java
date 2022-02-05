package com.log.aggregation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.google.gson.Gson;
import com.log.aggregation.entity.OutputLog;
import com.log.aggregation.util.HibernateUtil;

public class LogAggregation {

	public static void main(String[] args) {
		try {
			Gson gson = new Gson();
			String logFilePath = args[0];
			Files.lines(Paths.get(logFilePath)).parallel().forEach(lines -> {
				InputLog inputLog = gson.fromJson(lines, InputLog.class);
				System.out.println("Input Log -" + inputLog.toString());
				List<OutputLog> result = exist(inputLog.getId());

				if (result.size() > 0) {
					OutputLog exisitingLog = result.get(0);
					Long duration = inputLog.getTimestamp() - exisitingLog.getDuration();
					Transaction transaction = null;
					try (Session session = HibernateUtil.getSessionFactory().openSession()) {
						transaction = session.beginTransaction();
						if (duration > 4) {
							exisitingLog.setAlert(true);
						}
						exisitingLog.setDuration(duration);
						session.saveOrUpdate(exisitingLog);
						transaction.commit();
					} catch (Exception e) {
						if (transaction != null) {
							transaction.rollback();
						}
						e.printStackTrace();
					}		
				} else {

					Transaction transaction = null;
					try (Session session = HibernateUtil.getSessionFactory().openSession()) {
						OutputLog outputLog = new OutputLog(inputLog.getId(), inputLog.getTimestamp(),
								inputLog.getType(), false);
						transaction = session.beginTransaction();
						session.save(outputLog);
						transaction.commit();
					} catch (Exception e) {
						if (transaction != null) {
							transaction.rollback();
						}
						e.printStackTrace();
					}
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<OutputLog> exist(String eventId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		String queryString = "select * from log_aggregations e where e.event_id= :eventId";
		Query query = session.createQuery(queryString);
		query.setParameter("eventId", eventId);
		List<OutputLog> result = query.list();
		System.out.println(result.size());
		return result;
	}

}
