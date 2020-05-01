package io.seeport.rachlin.aiswebserver.services;

import io.seeport.rachlin.aiswebserver.models.Vessel;
import io.seeport.rachlin.aiswebserver.models.VesselType;
import io.seeport.rachlin.aiswebserver.repositories.VesselRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VesselService {

  @Autowired
  VesselRepository vesselRepository;

  public List<Vessel> findAllVessels(){
//    return (List<Vessel>) vesselRepository.findAll();
    return (List<Vessel>) vesselRepository.findAllVessels();
  }

  public List<Vessel> findVesselById(Integer vesselId) {
    return (List<Vessel>) vesselRepository.findVesselById(vesselId);
  }

  public List<Vessel> findVesselByMmsi(Integer mmsi) {
    return (List<Vessel>) vesselRepository.findVesselByMmsi(mmsi);
  }

  public List<Vessel> findVesselByName(String name) {
    return (List<Vessel>) vesselRepository.findVesselByName(name);
  }

  public List<Vessel> findVesselByImo(Integer imo) {
    return (List<Vessel>) vesselRepository.findVesselByImo(imo);
  }

  public List<Vessel> findVesselByCallSign(String callSign) {
    return (List<Vessel>) vesselRepository.findVesselByCallSign(callSign);
  }

  public List<Vessel> findVesselByType(VesselType vesselType) {
    return (List<Vessel>) vesselRepository.findVesselByVesselType(vesselType);
  }

  public List<Vessel> findVesselByTypeId(Integer vesselTypeId) {
    return (List<Vessel>) vesselRepository.findVesselByVesselTypeId(vesselTypeId);
  }

  public List<Vessel> findVesselByLoa(Float loa) {
    return (List<Vessel>) vesselRepository.findVesselByLoa(loa);
  }

  public List<Vessel> findVesselByBeam(Float beam) {
    return (List<Vessel>) vesselRepository.findVesselByBeam(beam);
  }
}
