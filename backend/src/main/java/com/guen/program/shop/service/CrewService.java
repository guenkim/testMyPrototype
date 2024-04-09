package com.guen.program.shop.service;

import com.guen.program.shop.model.dto.request.ReqCrewDto;
import com.guen.program.shop.model.dto.response.CrewDto;
import com.guen.program.shop.model.entity.Address;
import com.guen.program.shop.repository.crew.CrewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CrewService {
    @Autowired
    private CrewRepo crewRepo;
    private Object Collectors;

    public List<CrewDto> findCrews() {
        List<CrewDto> crewDtoList = crewRepo.findAll().stream().map(
                crew ->
                    CrewDto.builder()
                            .address(Address.builder()
                                    .city(crew.getAddress().getCity())
                                    .street(crew.getAddress().getStreet())
                                    .zipcode(crew.getAddress().getZipcode())
                                    .build())
                            .name(crew.getName())
                            .id(crew.getId())
                            .build()
        ).collect(java.util.stream.Collectors.toList());
        return crewDtoList;
    }

    @Transactional
    public void saveCrew(final ReqCrewDto reqCrewDto) {
        crewRepo.save(reqCrewDto.toEntity());
    }
}
