package ai.ecma.appeticketserver.repository;

import ai.ecma.appeticketserver.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository(value = "verificationCode")
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, UUID> {

//    Optional<VerificationCode> findByPhoneNumber(String phoneNumber);

    List<VerificationCode> findAllByCreatedAtAfterOrderByCreatedAt(Timestamp createdAt);

    @Query("select v from VerificationCode v where v.phoneNumber = ?1 and v.verificationCode = ?2 and v.expireTime > ?3 and v.used=false")
    Optional<VerificationCode> checkVerificationCode(String phoneNumber, String verificationCode, Timestamp expireTime);


}
