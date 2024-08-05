package com.mohsintech.supplement_app.repository;
import com.mohsintech.supplement_app.model.Supplement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class SupplementRepositoryTest {

    @Autowired
    private SupplementRepository supplementRepository;


    @Test
    public void SupplementRepository_Save_ReturnsSupplement() {
        Supplement supp = Supplement.builder().name("test").description("desc").evidence("Strong").benefits("All").build();

        Supplement returnedSupplement = supplementRepository.save(supp);

        Assertions.assertThat(returnedSupplement).isNotNull();
    }

    @Test
    public void SupplementRepository_FindById_ReturnsSupplement() {
        Supplement supp = Supplement.builder().name("test").description("desc").evidence("Strong").benefits("All").build();

        supplementRepository.save(supp);

        Supplement returnedSupplement = supplementRepository.findById(supp.getId()).orElse(null);

        Assertions.assertThat(returnedSupplement).isNotNull();
        Assertions.assertThat(returnedSupplement.getName()).isEqualTo("test");
    }

    @Test
    public void SupplementRepository_FindAll_ReturnsAllSupplement() {
        Supplement supp = Supplement.builder().name("test").description("desc").evidence("Strong").benefits("All").build();
        Supplement supp2 = Supplement.builder().name("test").description("desc").evidence("Strong").benefits("All").build();

        supplementRepository.save(supp);
        supplementRepository.save(supp2);
        List<Supplement> returnedSupplements = supplementRepository.findAll();

        Assertions.assertThat(returnedSupplements).isNotNull();
        Assertions.assertThat(returnedSupplements.size()).isEqualTo(2);
    }



}
