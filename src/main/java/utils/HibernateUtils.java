package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static void main(String[] args) {
        buildTable();
    }
    public static void buildTable(){

        Session session = getSession();

        session.beginTransaction();

        session
                .getTransaction()
                .commit();
    }
    private static SessionFactory buildSessionFactory(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");

        SessionFactory sessionFactory
                = configuration.buildSessionFactory();
        return sessionFactory;
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static void close(){
        sessionFactory.close();
    }

    public static Session getSession(){
        return sessionFactory.openSession();
    }
    public static boolean merge(Object entity) {
        boolean isSucceed = true;
        Session session = getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.merge(entity);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            isSucceed = false;
        } finally {
            session.close();
        }

        return isSucceed;
    }
    public static boolean remove(Object entity) {
        boolean isSucceed = true;
        Session session = getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            session.remove(entity);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            isSucceed = false;
        } finally {
            session.close();
        }

        return isSucceed;
    }
    public static Long count(String table) {
        Session session = getSession();

        String query = "SELECT COUNT(*) FROM " + table;
        Long count = (Long) session
                .createQuery(query)
                .getSingleResult();

        return count;
    }
    public static String getRetrieveAllQuery(String tableName, String columnName, String keyword, String typeSort){
        String cmd = "from " + tableName;
        if(keyword != null && keyword != ""){
            cmd += " where " + columnName + "=" + keyword;
        }
        cmd += " order by " + columnName + " " + typeSort;

        return cmd;
    }


}
