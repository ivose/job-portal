package com.zosh.job.service.impl;

import com.zosh.job.domain.CompanyStatus;
import com.zosh.job.domain.CompanyType;
import com.zosh.job.domain.IndustryType;
import com.zosh.job.dto.CompanyRequest;
import com.zosh.job.dto.CompanyResponse;
import com.zosh.job.dto.SocialLinkResponse;
import com.zosh.job.mapper.CompanyMapper;
import com.zosh.job.model.Company;
import com.zosh.job.model.SocialLink;
import com.zosh.job.repository.CompanyRepository;
import com.zosh.job.service.CompanyService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;

  @Override
  public CompanyResponse createCompany(Long ownerId, CompanyRequest req) throws Exception {
    if (companyRepository.existsByOwnerId(ownerId)) {
      throw new Exception(
          "You already have a company registered. Only one company per account is allowed");
    }
    if (companyRepository.existsByName(req.getName())) {
      throw new Exception("Company already exists. Please choose a different name.");
    }
    if (req.getRegistrationNumber() != null
        && companyRepository.existsByRegistrationNumber(req.getRegistrationNumber())) {
      throw new Exception("Company already exists. Please choose a different registration number.");
    }
    String slug = generateUniqueSlug(req.getName());
    Company company =
        Company.builder()
            .name(req.getName())
            .slug(slug)
            .tagline(req.getTagline())
            .description(req.getDescription())
            .logoUrl(req.getLogoUrl())
            .coverImageUrl(req.getCoverImageUrl())
            .email(req.getEmail())
            .phone(req.getPhone())
            .foundedYear(req.getFoundedYear())
            .companySize(req.getCompanySize())
            .companyType(req.getCompanyType())
            .industryType(req.getIndustryType())
            .registrationNumber(req.getRegistrationNumber())
            .ownerId(ownerId)
            .socialLinks(mapSocialLinks(req.getSocialLinks()))
            .build();
    Company saved = companyRepository.save(company);

    return CompanyMapper.toResponse(saved);
  }

  private List<SocialLink> mapSocialLinks(List<SocialLinkResponse> socialLinks) {
    if (socialLinks == null || socialLinks.isEmpty()) {
      return new ArrayList<>();
    }
    return socialLinks.stream()
        .map(e -> SocialLink.builder().platform(e.getPlatform()).url(e.getUrl()).build())
        .collect(Collectors.toList());
  }

  private String generateUniqueSlug(String name) {
    String base =
        name.toLowerCase().replaceAll("[^a-z0-9\\s-]", "").trim().replaceAll("[\\s-]", "-");
    if (!companyRepository.existsBySlug(base)) {
      return base;
    }
    int counter = 1;
    while (companyRepository.existsBySlug(base + "-" + counter)) {
      counter++;
    }
    return base + "-" + counter;
  }

  @Override
  public CompanyResponse getCompanyById(Long id) throws Exception {
    Company company = getCompanyEntityById(id);
    return CompanyMapper.toResponse(company);
  }

  @Override
  public CompanyResponse getMyCompany(Long ownerID) throws Exception {
    Company company =
        companyRepository
            .findByOwnerId(ownerID)
            .orElseThrow(() -> new Exception("Company not found"));
    return CompanyMapper.toResponse(company);
  }

  @Override
  public List<CompanyResponse> getAllCompanies(
      CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus)
      throws Exception {
    return companyRepository.findByFilters(companyType, industryType, companyStatus).stream()
        .map(CompanyMapper::toResponse)
        .collect(Collectors.toList());
  }

  @Override
  public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest req)
      throws Exception {
    Company company = getCompanyEntityById(companyId);
    if (req.getRegistrationNumber() != null
        && !company.getName().equals(req.getName())
        && companyRepository.existsByName(req.getName())) {
      throw new Exception("Company already exists. Please choose a different name.");
    }
    // Update company details
    company.setName(req.getName());
    company.setSlug(req.getSlug());
    company.setTagline(req.getTagline());
    company.setDescription(req.getDescription());
    company.setLogoUrl(req.getLogoUrl());
    company.setCoverImageUrl(req.getCoverImageUrl());
    company.setWebsite(req.getWebsite());
    company.setEmail(req.getEmail());
    company.setPhone(req.getPhone());
    company.setFoundedYear(req.getFoundedYear());

    // Update company information
    company.setCompanySize(req.getCompanySize());
    company.setCompanyType(req.getCompanyType());
    company.setIndustryType(req.getIndustryType());

    // Update registration number
    company.setRegistrationNumber(req.getRegistrationNumber());

    // Update social links
    if (req.getSocialLinks() != null) {
      List<SocialLink> socialLinks =
          req.getSocialLinks().stream()
              .map(
                  link ->
                      SocialLink.builder().platform(link.getPlatform()).url(link.getUrl()).build())
              .toList();

      company.getSocialLinks().clear();
      company.getSocialLinks().addAll(socialLinks);
    }
    Company updatedCompany = companyRepository.save(company);
    return CompanyMapper.toResponse(updatedCompany);
  }

  @Override
  public CompanyResponse verifyCompany(Long ownerId) throws Exception {
    Company company = getCompanyEntityById(ownerId);
    company.setStatus(CompanyStatus.ACTIVE);
    company.setVerified(true);
    return CompanyMapper.toResponse(companyRepository.save(company));
  }

  @Override
  public void deleteCompany(Long companyId, Long ownerId) throws Exception {
    Company company = getCompanyEntityById(companyId);
    assetOwner(company, ownerId);
    companyRepository.delete(company);
  }

  private void assetOwner(Company company, Long ownerId) throws Exception {
    if (!company.getOwnerId().equals(ownerId)) {
      throw new Exception("You are not the owner of this company");
    }
  }

  @Override
  public CompanyResponse deactivateCompany(Long ownerId) throws Exception {
    Company company = getCompanyEntityById(ownerId);
    company.setStatus(CompanyStatus.SUSPENDED);
    company.setVerified(false);
    return CompanyMapper.toResponse(companyRepository.save(company));
  }

  @Override
  public Company getCompanyEntityById(Long id) throws Exception {
    return companyRepository
        .findById(id)
        .orElseThrow(() -> new Exception("Company not found with id"));
  }
}
