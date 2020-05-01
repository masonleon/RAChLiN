package io.seeport.rachlin.aiswebserver.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vessel_types")
public class VesselType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String vesselGroup;
	private Integer aisVesselCode;
	private String aisShipCargoClassification;
	private String note;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVesselGroup() {
		return vesselGroup;
	}

	public void setVesselGroup(String vesselGroup) {
		this.vesselGroup = vesselGroup;
	}

	public Integer getAisVesselCode() {
		return aisVesselCode;
	}

	public void setAisVesselCode(Integer aisVesselCode) {
		this.aisVesselCode = aisVesselCode;
	}

	public String getAisShipCargoClassification() {
		return aisShipCargoClassification;
	}

	public void setAisShipCargoClassification(String aisShipCargoClassification) {
		this.aisShipCargoClassification = aisShipCargoClassification;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}
