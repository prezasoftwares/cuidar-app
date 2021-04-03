package com.cuidar.util;

import java.util.Calendar;

import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberLinkType;
import com.cuidar.repository.DependentFamilyMemberRepo;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  @Profile("test")
  CommandLineRunner initDatabase(MainFamilyMemberRepo mainFMrepo, DependentFamilyMemberRepo dependentFMrepo) {
    // Init Database records - for tests purposes

    Calendar calendar = Calendar.getInstance();

    var m1 = mainFMrepo.save(new MainFamilyMember("Principal Um", calendar.getTime(), FamilyMemberGender.Male, "11-11111", FamilyMemberCivilStatus.Single, "teste1@test.com"));
    m1.setAddressCity("Cidade 1");
    m1.setAddressPostalCode("111111-111");
    m1.setAddressState("UF1");
    m1.setAddressStreetComplement("Complement 1");
    m1.setAddressStreetName("Rua 2");
    m1.setAddressStreetNumber("111");
    m1.setContactPhoneNumber("11-1111-1111");
  
    var d1 = dependentFMrepo.save(new DependentFamilyMember("Dependente Um do Membro Principal Um", calendar.getTime(), FamilyMemberGender.Female, FamilyMemberLinkType.Child, m1));
    var d2 = dependentFMrepo.save(new DependentFamilyMember("Dependente Dois do Membro Principal Dois", calendar.getTime(), FamilyMemberGender.Male, FamilyMemberLinkType.Child, m1));

    return args -> {
      log.info("Preloading " + m1);
      log.info("Preloading " + d1);
      log.info("Preloading " + d2);
    };
  }
}