package com.example.demo.pharmacy.service


import com.example.demo.pharmacy.cache.PharmacyRedisTemplateService
import com.example.demo.pharmacy.dto.PharmacyDto
import com.example.demo.pharmacy.entity.Pharmacy
import spock.lang.Specification
import spock.lang.Subject

class PharmacySearchServiceTest extends Specification {
    private PharmacySearchService pharmacySearchService

    private PharmacyRepositoryService pharmacyRepositoryService = Mock()
    private PharmacyRedisTemplateService pharmacyRedisTemplateService = Mock()

    private List<PharmacyDto> pharmacyList;

    def setup() {
        pharmacySearchService = new PharmacySearchService(pharmacyRepositoryService, pharmacyRedisTemplateService)
        pharmacyList = new ArrayList<>()
        pharmacyList.addAll(
                Pharmacy.builder()
                        .id(1L)
                        .pharmacyName("호수온누리약국")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build(),
                Pharmacy.builder()
                        .id(2L)
                        .pharmacyName("돌곶이온누리약국")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build())
    }

    def "레디스 장애시 DB를 이용하여 약국 데이터 조회"() {
        when:
        pharmacyRedisTemplateService.findAll() >> []
        pharmacyRepositoryService.findAll() >> pharmacyList

        def result = pharmacySearchService.searchPharmacyDtoList()

        then:
        result.size() == 2
    }

}
