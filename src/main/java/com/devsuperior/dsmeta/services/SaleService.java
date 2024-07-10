package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class SaleService {

  @Autowired
  private SaleRepository repository;

  public SaleMinDTO findById(Long id) {
    Optional<Sale> result = repository.findById(id);
    Sale entity = result.get();
    return new SaleMinDTO(entity);
  }

  @Transactional
  public Page<SaleReportDTO> getReport(String minDate, String maxDate, String name ,Pageable pageable){
    LocalDate minimumDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maximumDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

    if(minDate.isEmpty()){

      minimumDate = minimumDate.minusYears(1L);
    }
    else{
  
      DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      minimumDate = LocalDate.parse(minDate, formatador);
      maximumDate = LocalDate.parse(maxDate, formatador);
    }
  
    Page<SaleReportDTO> result = repository.getReport(minimumDate, maximumDate, name, pageable);
    
    return result;
  }


  @Transactional
  public Page<SaleSummaryDTO> getSummary(String minDate, String maxDate, Pageable pageable) {

		LocalDate minimumDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		LocalDate maximumDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

  if(minDate.isEmpty()){

		minimumDate = minimumDate.minusYears(1L);
	}
	else{

		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		minimumDate = LocalDate.parse(minDate, formatador);
		maximumDate = LocalDate.parse(maxDate, formatador);
	}

	Page<SaleSummaryDTO> result = repository.getSummary(minimumDate, maximumDate, pageable);
	return result;
  }
}
