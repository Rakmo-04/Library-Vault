package com.LibVault.spring_boot_library.dao;

import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import com.LibVault.spring_boot_library.entity.*;

public interface HistoryRepository extends JpaRepository<History, Long> {

	Page<History> findBooksByUserEmail(@RequestParam("email") String userEmail,Pageable pageable);
}
