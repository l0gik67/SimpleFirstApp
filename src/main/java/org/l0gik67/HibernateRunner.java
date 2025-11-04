package org.l0gik67;

import org.hibernate.cfg.Configuration;
import org.l0gik67.converter.BirthDateConverter;
import org.l0gik67.entity.Birthday;
import org.l0gik67.entity.Role;
import org.l0gik67.entity.User;

import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        configuration.addAttributeConverter(BirthDateConverter.class, true);
        configuration.addAnnotatedClass(User.class);
        try (var sessionFactory = configuration.buildSessionFactory();
        var session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.persist(User.builder()
                    .username("l0gik67")
                    .firstname("vadim")
                    .lastname("matveev")
                    .birthday(new Birthday(LocalDate.of(2006, 12, 25)))
                    .role(Role.ADMIN)
                    .build());

            session.getTransaction().commit();
        }

    }
}
