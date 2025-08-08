package br.com.assetscontrol.app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "asset")
class Asset implements Serializable{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false)
  private String name;
  @ManyToOne
  @JoinColumn(name = "asset_type_id", nullable = false)
  private AssetType assetType;
  @Column(name = " purchase_price_cents", nullable = false)
  private Integer purchasePriceCents;
  @Column(name = "purchase_date", nullable = false)
  private Date purchaseDate;
  @Column(name = "use_full_live_years", nullable = false)
  private Integer useFullLiveYears;
  @Column(name = "residual_value_cents")
  private Integer residualValueCents;
}
