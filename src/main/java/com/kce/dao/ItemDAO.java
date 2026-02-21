package com.kce.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.kce.bean.Item;
import com.kce.util.HibernateUtil;

public class ItemDAO {

    // 🔥 Find Item by ID
    public Item findItem(String itemCode) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(Item.class, itemCode);
        }
    }

    // 🔥 View All Items
    public List<Item> viewAllItems() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query<Item> query =
                    session.createQuery("from Item", Item.class);

            return query.list();
        }
    }

    // 🔥 Insert Item
    public boolean insertItem(Item item) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            session.persist(item);
            tx.commit();

            return true;

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return false;
    }

    // 🔥 Update Quantities
    public boolean updateQuantities(String itemCode,
                                    double newOnHand,
                                    double newTotalDamaged) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Item item = session.get(Item.class, itemCode);

            if (item != null) {
                item.setOnHandQuantity(newOnHand);
                item.setTotalDamagedQuantity(newTotalDamaged);

                session.merge(item);
                tx.commit();
                return true;
            }

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return false;
    }

    // 🔥 Delete Item
    public boolean deleteItem(String itemCode) {

        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            Item item = session.get(Item.class, itemCode);

            if (item != null) {
                session.remove(item);
                tx.commit();
                return true;
            }

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }

        return false;
    }
}
