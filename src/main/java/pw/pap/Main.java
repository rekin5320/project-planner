package pw.pap;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class Main {
    private static final EntityManagerFactory
        ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("PW.PAP");
    public static void main(String[] args) {
        deleteUsers();
        createUsers();
        updateUser();
        deleteUser();

        List<User> users = readAll();
        if (users != null) {
            for (User u : users)
                System.out.println(u);
        }

        ENTITY_MANAGER_FACTORY.close();
    }

    public static void createUsers() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();

            User jeremy = new User();
            jeremy.setId(1);
            jeremy.setName("Jeremy");
            manager.persist(jeremy);

            User james = new User();
            james.setId(2);
            james.setName("James");
            manager.persist(james);

            User richard = new User();
            james.setId(3);
            james.setName("Richard");
            manager.persist(richard);

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null)
                transaction.rollback();
            ex.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public static List<User> readAll() {
        List<User> result = null;
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();

            transaction.begin();
            result = manager.createQuery("SELECT u FROM User u", User.class).getResultList();

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null)
                transaction.rollback();
            ex.printStackTrace();
        } finally {
            manager.close();
        }
        return result;
    }

    public static void deleteUser() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            User u = manager.find(User.class, 3);
            manager.remove(u);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null)
                transaction.rollback();
            ex.printStackTrace();
        } finally {
            manager.close();
        }
    }

    public static void deleteUsers() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            Query query = manager.createQuery("DELETE FROM User u");
            int deleteRecords = query.executeUpdate();
            transaction.commit();
            System.out.println("All records were deleted.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            manager.close();
        }
    }

    public static void updateUser() {
        EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = manager.getTransaction();
            transaction.begin();
            User u = manager.find(User.class, 2);
            u.setName("JAMES");
            manager.persist(u);
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null)
                transaction.rollback();
            ex.printStackTrace();
        } finally {
            manager.close();
            }
        }
}
