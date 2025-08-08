package br.com.assetscontrol.app.model;

import java.util.Date;

class Asset {
  private Long id;
  private String name;
  private AssetType assetType;
  private Integer purchasePriceCents;
  private Date purchaseDate;
  private Integer useFullLiveYears;
}
