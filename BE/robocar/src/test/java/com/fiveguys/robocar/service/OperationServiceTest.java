package com.fiveguys.robocar.service;



import com.fiveguys.robocar.dto.req.CarpoolRegisterReqDto;
import com.fiveguys.robocar.dto.req.CarpoolSuccessReqDto;
import com.fiveguys.robocar.dto.res.CarpoolListUpResDto;
import com.fiveguys.robocar.entity.CarpoolRequest;
import com.fiveguys.robocar.models.CarType;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class OperationServiceTest {

    @Autowired
    private OperationService service;
    @Autowired
    MapService mapService;


    CarpoolRequest carpoolRequest = CarpoolRequest.builder()
            .id(11L)
            .hostNickname("hihi")
            .departLatitude(37.5564966)
            .departLongitude(127.0803692)
            .hostDestLatitude(37.5556484)
            .hostDestLongitude(127.0825222)
            .hostDepartAddress("중곡동 140-2")
            .hostDestAddress("서울 광진구 천호대로 584 능동주유소")
            .maleCount(1)
            .femaleCount(0)
            .carType(CarType.SMALL)
            .carId(1L)
            .build();
    @Test
    @DisplayName("redis에 데이터 3개 주입")
    public void redisSave(){

        CarpoolRequest carpoolRequest2 = CarpoolRequest.builder()
                .id(12L)
                .hostNickname("hihi2")
                .departLatitude(37.5564966)
                .departLongitude(127.0803692)
                .hostDestLatitude(37.5556484)
                .hostDestLongitude(127.0825222)
                .hostDepartAddress("서울 광진구 천호대로 562")
                .hostDestAddress("서울 광진구 천호대로 584 능동주유소")
                .maleCount(1)
                .femaleCount(0)
                .carType(CarType.MEIDUM)
                .carId(2L)
                .build();

        CarpoolRequest carpoolRequest3 = CarpoolRequest.builder()
                .id(13L)
                .hostNickname("hihi3")
                .departLatitude(37.5564966)
                .departLongitude(127.0803692)
                .hostDestLatitude(37.5556484)
                .hostDestLongitude(127.0825222)
                .hostDepartAddress("서울 광진구 천호대로 562")
                .hostDestAddress("능동 236-2")
                .maleCount(1)
                .femaleCount(0)
                .carType(CarType.SMALL)
                .carId(3L)
                .build();
        service.saveCarpoolRequest(carpoolRequest);
        service.saveCarpoolRequest(carpoolRequest2);
        service.saveCarpoolRequest(carpoolRequest3);

        CarpoolRequest carpoolRequest1 = service.findCarpoolRequestById(11L);
        log.info(carpoolRequest1.getHostNickname());
        assertEquals(carpoolRequest.getDepartLatitude(),carpoolRequest1.getDepartLatitude());
    }


    @Test
    @DisplayName("carpoolSuccess 테스트")
    public void redisDelete() throws JSONException {
        //given
        service.saveCarpoolRequest(carpoolRequest);

        //when
        // 게스트가 호스트에게 동승 요청을 보냈을 떄
        Long id = 11L;
        CarpoolSuccessReqDto carpoolSuccessReqDto = new CarpoolSuccessReqDto(12L, "서울 광진구 능동 237-3");
        service.carpoolSuccess(id, carpoolSuccessReqDto);
        CarpoolRequest carpoolRequest = service.findCarpoolRequestById(id);

        //then
        assertNull(carpoolRequest);
    }

    @Test
    @DisplayName("carpoolRegister 테스트")
    public void redisRegister(){
        //given
        CarpoolRegisterReqDto carpoolRegisterReqDto = new CarpoolRegisterReqDto("서울시 강남구","서울시 동작구",1,2, CarType.SMALL);
        Long id = 2L;

        //when
        service.carpoolRegister(carpoolRegisterReqDto, id);

        CarpoolRequest carpoolRequest1 = service.findCarpoolRequestById(id);
        //then
        //check redis
    }

    @Test
    @DisplayName("carpoolListUp 테스트")
    public void redisListUp(){
        CarpoolListUpResDto response;

        response = service.carpoolListUp("서울 광진구 능동 237-3","서울 광진구 천호대로 584 능동주유소",1,1);
        System.out.println(response.getList().size());
        for(CarpoolListUpResDto.CarpoolItem itm:response.getList()){
            System.out.println(itm.getHostNickname());
        }
    }

}
