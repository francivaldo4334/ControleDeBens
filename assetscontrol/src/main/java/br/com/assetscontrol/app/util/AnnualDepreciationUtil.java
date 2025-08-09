package br.com.assetscontrol.app.util;

public class AnnualDepreciationUtil {
  public static Integer calc(
      Integer purchasePriceCents,
      Integer residualValueCents,
      Integer useFullLiveYears) {
    if (useFullLiveYears == null || useFullLiveYears == 0)
      return 0;
    int residual = (residualValueCents != null) ? residualValueCents : 0;
    return (purchasePriceCents - residual) / useFullLiveYears;
  }
}
