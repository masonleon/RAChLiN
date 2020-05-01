package io.seeport.rachlin.aiswebserver.repositories;

import io.seeport.rachlin.aiswebserver.models.Vessel;
import io.seeport.rachlin.aiswebserver.models.VesselType;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface VesselRepository extends CrudRepository<Vessel, Integer> {

	@Query(value = "SELECT * FROM vessels",
				 nativeQuery = true)
	//	List<Vessel> findAllVessels();
	Object findAllVessels();

	@Query(value = "SELECT * FROM vessels WHERE vessels.id=:vesselId",
				 nativeQuery = true)
	//	List<Vessel> findVesselById(
	Object findVesselById(
			@Param("vesselId") Integer vesselId);

	@Query(value = "SELECT * FROM vessels WHERE vessels.mmsi=:mmsi",
			nativeQuery = true)
	//	List<Vessel> findVesselByMmsi(
	Object findVesselByMmsi(
			@Param("mmsi") Integer mmsi);

	@Query(value = "SELECT * FROM vessels WHERE vessels.name=:name OR vessels.name LIKE name",
			nativeQuery = true)
		//	List<Vessel> findVesselByName(
	Object findVesselByName(
			@Param("name") String name);

	@Query(value = "SELECT * FROM vessels WHERE vessels.imo=:imo",
			nativeQuery = true)
	//	List<Vessel> findVesselByImo(
	Object findVesselByImo(
			@Param("imo") Integer imo);

	@Query(value = "SELECT * FROM vessels WHERE vessels.call_sign=:callSign OR vessels.call_sign LIKE callSign",
			nativeQuery = true)
	//	List<Vessel> findVesselByCallSign(
	Object findVesselByCallSign(
			@Param("callSign") String callSign);

	@Query(value = "SELECT * FROM vessels WHERE vessels.vessel_type=:vesselType",
			nativeQuery = true)
	//	List<Vessel> findVesselByVesselType(
	Object findVesselByVesselType(
			@Param("vesselType") VesselType vesselType);

	@Query(value = "SELECT * FROM vessels WHERE vessels.vessel_type.id=:vesselTypeId",
			nativeQuery = true)
	//	List<Vessel> findVesselByVesselTypeId(
	Object findVesselByVesselTypeId(
			@Param("vesselTypeId") Integer vesselTypeId);

	@Query(value = "SELECT * FROM vessels WHERE vessels.loa=:loa",
			nativeQuery = true)
	//	List<Vessel> findVesselByLoa(
	Object findVesselByLoa(
			@Param("loa") Float loa);

	@Query(value = "SELECT * FROM vessels WHERE vessels.beam=:beam",
			nativeQuery = true)
	//	List<Vessel> findVesselByBeam(
	Object findVesselByBeam(
			@Param("beam") Float beam);
}
