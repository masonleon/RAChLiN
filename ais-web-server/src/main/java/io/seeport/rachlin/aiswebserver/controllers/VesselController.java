package io.seeport.rachlin.aiswebserver.controllers;

import io.seeport.rachlin.aiswebserver.models.Message;
import io.seeport.rachlin.aiswebserver.models.Vessel;
import io.seeport.rachlin.aiswebserver.models.VesselType;
import io.seeport.rachlin.aiswebserver.services.VesselService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowCredentials = "true")
public class VesselController {

  @Autowired
  VesselService vesselService;

  @GetMapping("/api/vessels")
  public List<Vessel> findAllVessels(){
    return vesselService.findAllVessels();
  }

  @GetMapping("/api/vessels/{vesselId}")
  public List<Vessel> findVesselById(
      @PathVariable("vesselId") Integer vesselId) {
    return vesselService.findVesselById(vesselId);
  }

  @GetMapping("/api/vessels/mmsi/{mmsi}")
  public List<Vessel> findVesselByMmsi(
      @PathVariable("mmsi") Integer mmsi) {
    return vesselService.findVesselByMmsi(mmsi);
  }

  @GetMapping("/api/vessels/name/{name}")
  public List<Vessel> findVesselByName(
      @PathVariable("name") String name) {
    return vesselService.findVesselByName(name);
  }

  @GetMapping("/api/vessels/imo/{imo}")
  public List<Vessel> findVesselByImo(
      @PathVariable("imo") Integer imo) {
    return vesselService.findVesselByImo(imo);
  }

  @GetMapping("/api/vessels/call-sign/{callSign}")
  public List<Vessel> findVesselByCallSign(
      @PathVariable("callSign") String callSign) {
    return vesselService.findVesselByCallSign(callSign);
  }

  @GetMapping("/api/vessels/type/{vesselType}")
  public List<Vessel> findVesselByType(
      @PathVariable("vesselType") VesselType vesselType) {
    return vesselService.findVesselByType(vesselType);
  }

  @GetMapping("/api/vessels/type/{vesselTypeId}")
  public List<Vessel> findVesselByTypeId(
      @PathVariable("vesselTypeId") Integer vesselTypeId) {
    return vesselService.findVesselByTypeId(vesselTypeId);
  }

  @GetMapping("/api/vessels/loa/{loa}")
  public List<Vessel> findVesselByLoa(
      @PathVariable("loa") Float loa) {
    return vesselService.findVesselByLoa(loa);
  }

  @GetMapping("/api/vessels/beam/{beam}")
  public List<Vessel> findVesselByBeam(
      @PathVariable("beam") Float beam) {
    return vesselService.findVesselByBeam(beam);
  }
}
