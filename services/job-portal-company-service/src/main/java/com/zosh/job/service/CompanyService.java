package com.zosh.job.service;

import com.zosh.job.domain.CompanyStatus;
import com.zosh.job.domain.CompanyType;
import com.zosh.job.domain.IndustryType;
import com.zosh.job.dto.CompanyRequest;
import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.model.Company;
import java.util.List;

public interface CompanyService {
  CompanyResponse createCompany(Long ownerId, CompanyRequest request) throws Exception;

  CompanyResponse getCompanyById(Long id) throws Exception;

  CompanyResponse getMyCompany(Long ownerID) throws Exception;

  List<CompanyResponse> getAllCompanies(
      CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus)
      throws Exception;

  CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest request)
      throws Exception;

  CompanyResponse verifyCompany(Long ownerId) throws Exception;

  void deleteCompany(Long companyId, Long ownerId) throws Exception;

  CompanyResponse deactivateCompany(Long ownerId) throws Exception;

  Company getCompanyEntityById(Long id) throws Exception;
}
