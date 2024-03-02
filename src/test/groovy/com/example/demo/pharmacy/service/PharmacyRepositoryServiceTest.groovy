package com.example.demo.pharmacy.service

import com.example.demo.AbstractractIntegrationContainerBaseTest
import com.example.demo.pharmacy.entity.Pharmacy
import com.example.demo.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryServiceTest extends AbstractractIntegrationContainerBaseTest {
    @Autowired
    PharmacyRepositoryService pharmacyRepositoryService

    @Autowired
    PharmacyRepository pharmacyRepository

    def setup() {
        pharmacyRepository.deleteAll()
    }

    def "PharmacyRepository update - dirty checking success"() {
        given:
        String address = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()
        when:
        def entity = pharmacyRepository.save(pharmacy)
        pharmacyRepositoryService.updateAddress(entity.getId(), modifiedAddress)

        def result = pharmacyRepository.findAll()

        then:
        result.get(0).getPharmacyAddress() == modifiedAddress
    }

    def "PharmacyRepository update - dirty checking fail"() {
        given:
        String address = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()
        when:
            def entity = pharmacyRepository.save(pharmacy)
            pharmacyRepositoryService.updateAddressWithoutTransaction(entity.getId(), modifiedAddress)

            def result = pharmacyRepository.findAll()

        then:
            result.get(0).getPharmacyAddress() == address
    }
}
