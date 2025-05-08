// repository/UserRepository.java
package com.job_portal_backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.job_portal_backend.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email); // For checking duplicate email

    boolean existsByUsername(String username);

//     @Query("SELECT u FROM User u " +
//        "LEFT JOIN u.roles r " +  // This is the join to the ElementCollection
//        "WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :keywords, '%')) OR " +
//        "LOWER(u.email) LIKE LOWER(CONCAT('%', :keywords, '%')) OR " +
//        "LOWER(u.type) LIKE LOWER(CONCAT('%', :keywords, '%')) OR " +
//        "LOWER(r.name) LIKE LOWER(CONCAT('%', :keywords, '%')) OR " +  // Search within roles collection
//        "u.userId LIKE CONCAT('%', :keywords, '%') OR " +
//        "u.contactNumber LIKE CONCAT('%', :keywords, '%')")
// List<User> searchUsers(@Param("keywords") String keywords);

}
