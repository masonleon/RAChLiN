package io.seeport.rachlin.aiswebserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity

@Table(name = "vessels")
public class Vessel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "mmsi")
  private int mmsi;

  @Column(name="name")
  private String name;

  @Column(name="imo")
  private int imo;

  @Column(name="call_sign")
  private String callSign;

//  @Column(name="vessel_type_id")
//  private int vesselTypeId;
  @Column(name="vessel_type")
  @ManyToOne()
  @JsonIgnore
  private VesselType vesselType;

  @Column(name="loa")
  private float loa;

  @Column(name="beam")
  private float beam;

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId(){
    return this.id;
  }

  public void setMmsi(int mmsi){
    this.mmsi = mmsi;
  }

  public int getMmsi(){
    return this.mmsi;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getName(){
    return this.name;
  }

  public void setImo(int imo){
    this.imo = imo;
  }

  public int getImo() {
    return this.imo;
  }

  public void setCallSign(String callSign){
    this.callSign = callSign;
  }

  public String getCallSign(){
    return this.callSign;
  }

  //  public void setVesselTypeId(int vesselTypeId){
  //    this.vesselTypeId = vesselTypeId;
  //  }
  public void setVesselType(VesselType vesselType){
    this.vesselType = vesselType;
  }

  //  public int getVesselTypeId(){
  //    return this.vesselTypeId;
  //  }
  public VesselType getVesselType(){
    return this.vesselType;
  }

  public void setLoa(float loa){
    this.loa = loa;
  }

  public float getLoa(){
    return this.loa;
  }

  public void setBeam(float beam){
    this.beam = beam;
  }

  public float getBeam(){
    return this.beam;
  }

  public Vessel(){
  }
}
