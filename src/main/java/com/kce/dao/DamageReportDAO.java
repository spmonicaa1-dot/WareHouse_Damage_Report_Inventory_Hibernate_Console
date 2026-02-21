package com.kce.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.kce.bean.DamageReport;
import com.kce.util.HibernateUtil;

public class DamageReportDAO {

    // 🔥 Save Damage Report
    public boolean recordDamageReport(DamageReport report) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            session.persist(report);
            tx.commit();

            return true;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return false;
    }

    // 🔥 Update Status
    public boolean updateDamageStatus(int damageID, String status) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            DamageReport report = session.get(DamageReport.class, damageID);

            if (report != null) {
                report.setStatus(status);
                session.merge(report);
                tx.commit();
                return true;
            }

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return false;
    }

    // 🔥 Find By Item Code
    public List<DamageReport> findDamageReportsByItem(String itemCode) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<DamageReport> query =
                    session.createQuery("from DamageReport where itemCode = :code",
                            DamageReport.class);

            query.setParameter("code", itemCode);

            return query.list();
        }
    }

    // 🔥 Find By ID
    public DamageReport findDamageReportsByID(int damageID) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(DamageReport.class, damageID);
        }
    }

    // 🔥 Find Active Reports
    public List<DamageReport> findActiveDamageReportsByItem(String itemCode) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<DamageReport> query =
                    session.createQuery(
                            "from DamageReport where itemCode = :code and status in ('OPEN','CONFIRMED')",
                            DamageReport.class);

            query.setParameter("code", itemCode);

            return query.list();
        }
    }
}
