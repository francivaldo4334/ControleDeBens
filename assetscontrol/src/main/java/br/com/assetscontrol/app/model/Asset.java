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

import br.com.assetscontrol.app.util.AnnualDepreciationUtil;

@Entity
@Table(name = "asset")
public class Asset implements Serializable {
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public AssetType getAssetType() {
    return assetType;
  }

  public void setAssetType(AssetType assetType) {
    this.assetType = assetType;
  }

  public Integer getPurchasePriceCents() {
    return purchasePriceCents;
  }

  public void setPurchasePriceCents(Integer purchasePriceCents) {
    this.purchasePriceCents = purchasePriceCents;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public Integer getUseFullLiveYears() {
    return useFullLiveYears;
  }

  public void setUseFullLiveYears(Integer useFullLiveYears) {
    this.useFullLiveYears = useFullLiveYears;
  }

  public Integer getResidualValueCents() {
    return residualValueCents;
  }

  public void setResidualValueCents(Integer residualValueCents) {
    this.residualValueCents = residualValueCents;
  }

  public Integer getAnnualDepreciation() {
    return AnnualDepreciationUtil.calc(purchasePriceCents, residualValueCents, useFullLiveYears);
  }
}
