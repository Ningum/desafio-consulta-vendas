package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SaleRepository extends JpaRepository<Sale, Long> {
  @Query(
    "SELECT NEW com.devsuperior.dsmeta.dto.SaleReportDTO(s.id, s.date, s.amount, s.seller.name) " +
    "FROM Sale s " +
    "INNER JOIN s.seller seller " +
    "WHERE s.date >= :minDate " +
    "AND s.date <= :maxDate " +
    "AND UPPER(seller.name) LIKE CONCAT('%',UPPER(:name), '%')"
  )
  public Page<SaleReportDTO> getReport(
    LocalDate minDate,
    LocalDate maxDate,
    String name,
    Pageable pageable
  );

  @Query(
    "SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(s.seller.name, SUM(s.amount)) " +
    "FROM Sale s " +
    "WHERE s.date >= :minDate " +
    "AND s.date <= :maxDate " +
    "GROUP BY s.seller.name"
  )
  public Page<SaleSummaryDTO> getSummary(
    LocalDate minDate,
    LocalDate maxDate,
    Pageable pageable
  );
}
