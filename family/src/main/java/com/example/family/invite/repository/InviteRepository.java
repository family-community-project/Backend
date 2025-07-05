package com.example.family.invite.repository;


import java.util.Optional;

public interface InviteRepository {
    void saveInviteCode(String code, Long familyLd, int maxUses);

    Optional<String> findExistingCodeByFamilyId(Long familyId);

    String findFamilyIdByCode(String code);

    Integer findRemainingUsesByCode(String code);

    void decreaseRemainingUses(String code);

    void deleteByCode(String code);

    boolean existsCode(String code);

}
