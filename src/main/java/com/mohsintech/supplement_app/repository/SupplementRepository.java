package com.mohsintech.supplement_app.repository;

import com.mohsintech.supplement_app.model.Supplement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplementRepository extends JpaRepository<Supplement,Integer> {

}
