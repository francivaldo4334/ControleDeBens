# Documentação
## Estrutura do projeto.

```
 ├╴  assetscontrol
 │ ├╴  src
 │ │ └╴  main
 │ │   ├╴  java
 │ │   │ └╴  br
 │ │   │   └╴  com
 │ │   │     └╴  assetscontrol
 │ │   │       └╴  app
 │ │   │         ├╴  model
 │ │   │         │ ├╴  Asset.java
 │ │   │         │ └╴  AssetType.java
 │ │   │         ├╴  service
 │ │   │         │ ├╴  AssetService.java
 │ │   │         │ └╴  AssetTypeService.java
 │ │   │         ├╴  util
 │ │   │         │ └╴  AnnualDepreciationUtil.java
 │ │   │         └╴  web
 │ │   │           ├╴  AssetBean.java
 │ │   │           └╴  AssetTypeBean.java
 │ │   ├╴  resources
 │ │   │ └╴  META-INF
 │ │   │   └╴  persistence.xml
 │ │   └╴  webapp
 │ │     ├╴  WEB-INF
 │ │     │ └╴  web.xml
 │ │     ├╴  asset_type.xhtml
 │ │     └╴  index.xhtml
 │ └╴  pom.xml
 ├╴  README.md
 └╴  docker-compose.yml
```
## Atribuições das Pastas
* "model"
  Contém as classes que representam as entidades do banco de dados (JPA Entities).
* "service"
  Contém os serviços da aplicação, responsáveis pela lógica de negócio e operações de persistência (repositórios).
* "util"
  Contém classes utilitárias para pequenas tarefas auxiliares, como cálculos e funções de apoio.
* "web"
  Contém os Managed Beans (ViewModels) responsáveis por fornecer dados e funcionalidades para as páginas web JSF.
---
## Passos para configuração e execução.
### Requisitos
* **Java 8**
* **GlassFish 5.1.0**
* **Docker/Docker Compose** ou serviço **PostgreSQL** local
* **Maven**
---
### Passos para Configuração e Execução
1. Inicie o banco de dados localmente ou utilize o **Dockerfile** para iniciar um serviço PostgreSQL.
2. Certifique-se de iniciar o GlassFish com:
   ```bash
   asadmin start-domain
   ```
3. Acesse o painel de administração do GlassFish:
   ```
   http://localhost:4848/
   ```
4. Configure o **Connection Pool** em:
   **JDBC → JDBC Connection Pools → New**
   * **Pool Name**: `clinicplusPool`
   * **Resource Type**: `javax.sql.DataSource`
   * **Database Driver Vendor**: `PostgreSQL`
5. Clique em **Next** para proceguir.
6. Na segunda etapa da configuração do Connect Pool adicione as seguintes propriedade adicionais:
   * **password**: `postgresql`
   * **databaseName**: `db`
   * **serverName**: `localhost`
   * **user**: `postgresql`
   * **portNumber**: `5432`
7. Clique em **Save** para salvar o novo JDBC Connect Pool
8. Configure um novo **JDBC Resource** em:
   **JDBC → JDBC Resources → New**
   * **JNDI Name**: `jdbc/ClinicPlusDS` *(exatamente assim)*
   * **Pool Name**: `clinicplusPool`
9. Clique em **OK** para confirmar a criação do JDBC Resource.
10. Limpe pacotes gerados anteriormente (se necessário):
   ```bash
   mvn clean
   ```
11. Na pasta `assetscontrol/`, gere o arquivo `.war`:
   ```bash
   mvn package
   ```
11. Faça o deploy da aplicação no GlassFish:
    ```bash
    asadmin deploy ./target/assetscontrol.war
    ```
12. Acesse a aplicação no navegador:
    ```
    http://localhost:8080/assetscontrol
    ```
---
## Explicação do cálculo de depreciação implementado.
O sistema utiliza o **método linear de depreciação**, que **distribui a perda de valor de um bem igualmente ao longo de sua vida útil estimada**, com o intuito de descobrir **quanto o valor diminui a cada ano**.

### Para realizar essa estimativa:
1. **Calcular a perda total do bem**
   ```
   perdaTotal = valorInicial - valorFinal
   ```
   * **valorInicial** → preço de compra do bem.
   * **valorFinal** → valor residual estimado ao final da vida útil.
2. **Dividir a perda total pela vida útil em anos**
   ```
   perdaAnual = perdaTotal / anosVidaUtil
   ```
   Esse valor representa **quanto o bem perde em valor por ano**.<br/>
### Implementação no código:.
.../util/AnnualDepreciationUtil.java<br/>
```java
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
```
