package com.alliance.louisa.louisa2.louisa1;

import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alliance.louisa.louisa2.domain.Brand;
import com.alliance.louisa.louisa2.domain.Country;
import com.alliance.louisa.louisa2.domain.Source;
import com.alliance.louisa.louisa2.service.HiveService;
import com.alliance.louisa.louisa2.service.tools.AuthenticationTools;
import com.alliance.louisa.louisa2.util.CryptoUtil;

@Component
public class Louisa1Client {
	

    private static final Logger log = LoggerFactory.getLogger(Louisa1Client.class);

    public static final String RENAULT_PLANTS_CACHE_NAME = "renaultPlants";
    public static final String LOUISA1_PLANTS_CACHE_NAME = "louisa1Plants";
    public static final String LOUISA1_PLANTS_SOURCE_CACHE_NAME = "louisa1PlantsSource";
    public static final String RENAULT_MODELS_CACHE_NAME = "renaultModels";
    public static final String NISSAN_PLANTS_CACHE_NAME = "nissanPlants";
    public static final String NISSAN_MODELS_CACHE_NAME = "nissanModels";
    public static final String LOUISA1_MODELS_CACHE_NAME = "louisa1Models";
    public static final String NISSAN_COUNTRIES_CACHE_NAME = "nissanCountries";
    public static final String RENAULT_MARKETS_CACHE_NAME = "renaultMarkets";
    public static final String LOUISA1_COUNTRIES_CACHE_NAME = "louisa1Countries";
    public static final String LOUISA1_RBU_CACHE_NAME = "louisa1Rbu";
    public static final String LOUISA1_RBU_COUNTRY_CACHE_NAME = "louisa1RbuCountry";
    private static final String BSD_SYSTEM_SOURCE_CODE = "01";
    private static final String NISSAN_SYSTEM_SOURCE_CODE = "02";
    private static final String TRIENNAL_SYSTEM_SOURCE_CODE = "03";
    private static final String LOU_API_PLANTS = "/lou/api/louisa2/plants";
    private static final String LOU_API_MODELS = "/lou/api/louisa2/models";
    private static final String LOU_API_PLANT_MODELS = "/lou/api/louisa2/plantModels";
    private static final String LOU_API_COUNTRYS = "/lou/api/louisa2/countries";
    private static final String LOU_API_MARKETS = "/lou/api/louisa2/markets";
    private static final String LOU_API_RBU = "/lou/api/louisa2/RBU";
    public static final String NISSAN_COUNTRIES_CACHE_NAME_ISO = "nissanCountriesiso";
    
	public static final String PLANT_FIRST_TRANSIT_NODE_CACHE_NAME = "plantFirstTrnasitNode";
	public static final String PLANT_FIRST_TRANSIT_NODE_EXCEPTION_CACHE_NAME = "plantFirstTrnasitNodeException";

	public static final String RENAULT_PFMT_MARKETS_CACHE_NAME = "renaultPfmtMarkets";
	public static final String RENAULT_PFMT_MARKETS_COUNTRIES_CACHE_NAME = "renaultPfmtCountriesMarkets";
	private static final String LOU_PFMT_API_MARKETS = "/api/v1/getPfmtMarketFromHive";
	private static final String LOU_PFMT_API_MARKETS_COUNTRIES_RATIO = "/api/v1/getPfmtMarketCountyRatioFromHive";
	private static final String LOU_ISO_COUNTRIES = "api/v1/getLouIsoCountriesFromHive";
	public static final String RENAULT_CARNET_COUNTRIES = "renaultCarnetCountries";
	public static final String NISSAN_POSTAL_CODES = "nissanPostalCodes";
	private static final String LOU_POSTAL_CODES = "api/v1/getLouPostalCodesFromHive";
	
	private static final String LOU_API_PLANT_FIRST_NODE_EXCEPTION = "/lou/api/louisa2/plantFirstTransitNodeException";
	
	public static final String RENAULT_SHORT_TERM_MARKETS_CACHE_NAME = "renaultShortTermMarkets";
	public static final String FIRST_PLAN_NODE = "firstplannode";
	public static final String LOU_FIRST_PLAN_NODE = "api/v1/getLouFirstPlanNodeFromHive";
	public static final String RENAULT_EXCEPTION_PLANTS_CACHE_NAME = "renaultExceptionPlants";
 
    @Value("${louisa2.louisa1.base-url:http://127.0.0.1:8089}")
    private String baseUrl;
      
	@Value("${louisa2.hive.status:false}")
	private boolean hiveStatus;
	
    @Autowired
    private AuthenticationTools authenticationTools;
    
    @Autowired
	private HiveService hiveService;
    
    private RestTemplate restTemplate;

  
    final String louCertificate; 
    
    
    public Louisa1Client(@Value("${louisa2.louisa1.louCertificate:/loure701intrarenaultfr.crt}") final String louCertificate) throws CertificateException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
    	this.louCertificate = louCertificate;
    	log.info("louCertificate {}",louCertificate);
        SSLContext sslContext = CryptoUtil.buildSslContext(this.getClass().getResourceAsStream(louCertificate));
     
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

        CloseableHttpClient httpClient = HttpClientBuilder.create().useSystemProperties().setSSLSocketFactory(csf).build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        restTemplate = new RestTemplate(requestFactory);

    }

    private HttpEntity<Object> getHttpEntity() {
        String accessToken = authenticationTools.getTechnicalToken().getAccessToken();
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(AuthenticationTools.NO_CACHE);
        headers.add("Authorization", "Bearer " +accessToken);
        return new HttpEntity<>(headers);

    }

    @Cacheable(value = RENAULT_PLANTS_CACHE_NAME)
    public Map<String, Plant> getRenaultPlants() {
        ResponseEntity<List<Plant>> response = restTemplate.exchange(baseUrl + LOU_API_PLANTS + "/" + TRIENNAL_SYSTEM_SOURCE_CODE,
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Plant>>() {
                });
        List<Plant> plants = response.getBody();
        Map<String, Plant> plantsMap = new HashMap<>();
        plants.forEach(plant -> {
            for (PlantMapping plantMapping: plant.getPlantMappings()) {
                plantsMap.put(plantMapping.getHostSystemPlant().toUpperCase(), plant);
            }
        });
        return plantsMap;
    }

    @Cacheable(value = NISSAN_PLANTS_CACHE_NAME)
    public Map<String, Plant> getNissanPlants() {
        ResponseEntity<PlantModel> response = restTemplate.exchange(baseUrl + LOU_API_PLANT_MODELS + "/" + NISSAN_SYSTEM_SOURCE_CODE,
                HttpMethod.GET, getHttpEntity(), PlantModel.class);
        PlantModel plantModel = response.getBody();
        return plantModel.getPlantModels().stream().filter(p -> p.getPlant() != null).collect(toMap(m -> m.getModel().getModel().toUpperCase(), PlantModels::getPlant, (m1, m2) -> m1));
    }

    @Cacheable(value = RENAULT_PLANTS_CACHE_NAME ,key = "#sourceCode")
    public Map<String, Plant> getRenaultPlantsBySource(String sourceCode) {
        ResponseEntity<List<Plant>> response = restTemplate.exchange(baseUrl + LOU_API_PLANTS + "/" + getSystemSourceCodeFromBrand(Brand.RENAULT,sourceCode),
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Plant>>() {
                });
        List<Plant> plants = response.getBody();
        Map<String, Plant> plantsMap = new HashMap<>();
        plants.forEach(plant -> {
            for (PlantMapping plantMapping: plant.getPlantMappings()) {
                plantsMap.put(plantMapping.getHostSystemPlant().toUpperCase(), plant);
            }
        });
        return plantsMap;
    }

    @Cacheable(value = NISSAN_PLANTS_CACHE_NAME ,key = "#sourceCode")
    public Map<String, Plant> getNissanPlantsBySource(String sourceCode) {
        ResponseEntity<PlantModel> response = restTemplate.exchange(baseUrl + LOU_API_PLANT_MODELS + "/" + getSystemSourceCodeFromBrand(Brand.NISSAN,sourceCode),
                HttpMethod.GET, getHttpEntity(), PlantModel.class);
        PlantModel plantModel = response.getBody();
        return plantModel.getPlantModels().stream().filter(p -> p.getPlant() != null).collect(toMap(m -> m.getModel().getModel().toUpperCase(), PlantModels::getPlant, (m1, m2) -> m1));
    }

    @Cacheable(value = RENAULT_MODELS_CACHE_NAME ,key = "#sourceCode")
    public Map<String, Model> getRenaultModels(String sourceCode) {
        return getModel("renault",sourceCode);
    }

    private Map<String, Model> getModel(String brand, String sourceCode) {
        ResponseEntity<List<Model>> response = restTemplate.exchange(baseUrl + LOU_API_MODELS + "/" + getSystemSourceCodeFromBrand(brand,sourceCode),
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Model>>() {
                });
        List<Model> models = response.getBody();
        Map<String, Model> modelsMap = new HashMap<>();
        models.forEach(model -> {
            for (ModelLink modelLink: model.getModelLink()) {
                modelsMap.put(modelLink.getHostModelCode().toUpperCase(), model);
            }
        });
        return modelsMap;
    }

    @Cacheable(value = NISSAN_MODELS_CACHE_NAME,key = "#sourceCode")
    public Map<String, Model> getNissanModels(String sourceCode) {
        return getModel("nissan",sourceCode);
    }

    @Cacheable(value = NISSAN_COUNTRIES_CACHE_NAME)
    public Map<String, Country> getNissanCountries() {
        ResponseEntity<List<Country>> response = restTemplate.exchange(baseUrl + LOU_API_COUNTRYS,
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Country>>() {
                });
        List<Country> countries = response.getBody();
        return countries.stream().collect(toMap(country -> country.getLabel().toUpperCase(), country -> country,(m1, m2) -> m1));
    }

    @Cacheable(value = RENAULT_MARKETS_CACHE_NAME)
    public Map<String, Market> getRenaultMarkets() {
        ResponseEntity<List<Market>> response = restTemplate.exchange(baseUrl + LOU_API_MARKETS,
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Market>>() {
                });
        List<Market> markets = response.getBody();
        return markets.stream().collect(toMap(market -> market.getMarket().toUpperCase(), market -> market, (m1, m2) -> m1));
    }


    @Cacheable(value = LOUISA1_PLANTS_CACHE_NAME,key = "#brand + #source")
    public Map<String, Plant> getLouisa1Plants(String brand,String source)  {

        ResponseEntity<List<Plant>> response = restTemplate.exchange(baseUrl + LOU_API_PLANTS + "/" + getSystemSourceCodeFromBrand(brand,source),
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Plant>>() {
                });
        List<Plant> plants = response.getBody();
        return plants.stream().collect(toMap(plant -> plant.getCode().toUpperCase(), plant -> plant, (m1, m2) -> m1));
    }

    @Cacheable(value = LOUISA1_PLANTS_SOURCE_CACHE_NAME)
    public Map<String, Plant> getLouisa1PlantsMapping(String brand)  {

        ResponseEntity<List<Plant>> response = restTemplate.exchange(baseUrl + LOU_API_PLANTS + "/" + getSystemSourceCodeFromBrand(brand),
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Plant>>() {
                });
        List<Plant> plants = response.getBody();
        
        Map<String, Plant> plantMap = new HashMap<>();
        plants.forEach(plant -> {
            for (PlantMapping plantMapping: plant.getPlantMappings()) {
            	plantMap.put(plantMapping.getHostSystemPlant().toUpperCase(), plant);
            }
        });
        return plantMap;
    }

    @Cacheable(value = LOUISA1_MODELS_CACHE_NAME,key = "#brand + #source")
    public Map<String, Model> getLouisa1Models(String brand,String source) {

        ResponseEntity<List<Model>> response = restTemplate.exchange(baseUrl + LOU_API_MODELS+ "/" + getSystemSourceCodeFromBrand(brand,source),
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Model>>() {
                });
        List<Model> models = response.getBody();
        return models.stream().collect(toMap(model -> model.getModel().toUpperCase(), model -> model, (m1, m2) -> m1));
    }

    @Cacheable(value = LOUISA1_COUNTRIES_CACHE_NAME)
    public Map<String, Country> getLouisa1Countries() {

        ResponseEntity<List<Country>> response = restTemplate.exchange(baseUrl + LOU_API_COUNTRYS,
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Country>>() {
                });
        List<Country> countries = response.getBody();
        return countries.stream().collect(toMap(country -> country.getIsoCode().toUpperCase(), country -> country,(m1, m2) -> m1));
    }
    
	/**
	 * Rbu from louisa1
	 * 
	 * @return
	 */
	@Cacheable(value = LOUISA1_RBU_CACHE_NAME)
	public Map<String, RBUMapping> getLouisa1RbuMapping() {

		ResponseEntity<List<RBUMapping>> response = restTemplate.exchange(baseUrl + LOU_API_RBU, HttpMethod.GET,
				getHttpEntity(), new ParameterizedTypeReference<List<RBUMapping>>() {
				});
		List<RBUMapping> rbuMapping = response.getBody();
		return rbuMapping.stream().collect(toMap(RBUMapping::getRbu, rbu -> rbu,(m1, m2) -> m1));
	}
	
	@Cacheable(value = LOUISA1_RBU_COUNTRY_CACHE_NAME)
	public Map<String, Set<RBUMapping>> getNissanRbuCountryMapping() {

		Map<String, Set<RBUMapping>> rbuCountryMapping = new HashMap<>();

		ResponseEntity<List<RBUMapping>> response = restTemplate.exchange(baseUrl + LOU_API_RBU, HttpMethod.GET,
				getHttpEntity(), new ParameterizedTypeReference<List<RBUMapping>>() {
				});
		List<RBUMapping> rbuMapping = response.getBody();

		rbuMapping.forEach(rbu -> {
			for (RbuCountry rbuCountry : rbu.getRbuCountry()) {
				rbuCountryMapping.computeIfAbsent(rbuCountry.getCountries().getIsoCode(), rbuLst -> new HashSet<>()).add(rbu);
			}
		});

		return rbuCountryMapping;
	}
    
	@Cacheable(value = NISSAN_COUNTRIES_CACHE_NAME)
    public Map<String, Country> getNissanCountriesByIso() {
        ResponseEntity<List<Country>> response = restTemplate.exchange(baseUrl + LOU_API_COUNTRYS,
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Country>>() {
                });
        List<Country> countries = response.getBody();
        return countries.stream().collect(toMap(country -> country.getIsoCode().toUpperCase(), country -> country,(m1, m2) -> m1));
    }

    private String getSystemSourceCodeFromBrand(String brand) {
        log.info("Method : getSystemSourceCodeFromBrand is called ..");
        return "renault".equalsIgnoreCase(brand) ? TRIENNAL_SYSTEM_SOURCE_CODE : NISSAN_SYSTEM_SOURCE_CODE;
    }
    private String getSystemSourceCodeFromBrand(String brand,String sourceCode) {
    	log.info("Method : getSystemSourceCodeFromBrand is called ..");
        if(Brand.RENAULT.equalsIgnoreCase(brand) && sourceCode.equalsIgnoreCase(Source.TRIENNAL)) {
            return TRIENNAL_SYSTEM_SOURCE_CODE;
        } else if(Brand.RENAULT.equalsIgnoreCase(brand) && sourceCode.equalsIgnoreCase(Source.PFMT)) {
            return BSD_SYSTEM_SOURCE_CODE;
        } else if(Brand.NISSAN.equalsIgnoreCase(brand)) {
            return NISSAN_SYSTEM_SOURCE_CODE;
        } else if(Brand.RENAULT.equalsIgnoreCase(brand) && sourceCode.equalsIgnoreCase(Source.CARNET)) {
            return BSD_SYSTEM_SOURCE_CODE;
        }
        return null;
    }
    
    /**
	 * This method is used to get the data from hive
	 * 
	 * @return this will return market
	 */
	@Cacheable(value = RENAULT_PFMT_MARKETS_CACHE_NAME)
	public Map<String, Market> getRenaultPfmtMarkets() {
		List<Market> markets;
		if(hiveStatus) {
			markets=hiveService.getPfmtMarket();
		}else {
			log.info("Hive Or Local url {}",baseUrl);
			ResponseEntity<List<Market>> response = restTemplate.exchange(baseUrl+LOU_PFMT_API_MARKETS, HttpMethod.GET,
					getHttpEntity(), new ParameterizedTypeReference<List<Market>>() {
					});
			 markets = response.getBody();
		}
		
		return markets.stream()
				.collect(toMap(market -> market.getMarket().toUpperCase(), market -> market, (m1, m2) -> m1));
	}
	
	@Cacheable(value = RENAULT_PFMT_MARKETS_COUNTRIES_CACHE_NAME)
	public Map<String, List<MarketCountryRatio>> getRenaultPfmtCountriesMarkets() {
		log.info("Load Renault pfmt countries market ");
		List<MarketCountryRatio> marketsCountries;
		if(hiveStatus) {
			marketsCountries=hiveService.getPfmtMarketCountryRatio();
		}else {
			ResponseEntity<List<MarketCountryRatio>> response = restTemplate.exchange(
					baseUrl + LOU_PFMT_API_MARKETS_COUNTRIES_RATIO, HttpMethod.GET, getHttpEntity(),
					new ParameterizedTypeReference<List<MarketCountryRatio>>() {
					});
			 marketsCountries = response.getBody();
		}
		
		return marketsCountries.stream().filter(market -> Objects.nonNull(market.getCountryCode()))
				.collect(Collectors.groupingBy(MarketCountryRatio::getCountryCode,
						Collectors.mapping(market -> market, Collectors.toList())));
	}
	
	@Cacheable(value = RENAULT_CARNET_COUNTRIES)
	public Map<String, CountryDestinationCode> updateCarnetDetails( List<String> lstvin ) {			
		Map<String, CountryDestinationCode> isoCountries;		
		if (hiveStatus) {
			isoCountries = hiveService.getCarnetIsoCountry(lstvin);
		} else {
			ResponseEntity<Map<String, CountryDestinationCode>> response = restTemplate.exchange(
					baseUrl + LOU_ISO_COUNTRIES, HttpMethod.GET, getHttpEntity(),
					new ParameterizedTypeReference<Map<String, CountryDestinationCode>>() {
					});
			isoCountries = response.getBody();
		}		
		return isoCountries;
	}
	
	@Cacheable(value = NISSAN_POSTAL_CODES)
	public Map<String, String> getPostalCode( List<String> lstTransitNodes ) {			
		Map<String, String> postalCodes = null;		
		if (hiveStatus) {
			postalCodes = hiveService.getPostalCode(lstTransitNodes);
		} else {
			ResponseEntity<Map<String, String>> response = restTemplate.exchange(
					baseUrl + LOU_POSTAL_CODES, HttpMethod.GET, getHttpEntity(),
					new ParameterizedTypeReference<Map<String, String>>() {
					});
			postalCodes = response.getBody();
		}		
		return postalCodes;
	}
	
	@Cacheable(value = PLANT_FIRST_TRANSIT_NODE_CACHE_NAME, key = "#brandCode")
	public Map<String, PlantFirstTransitNode> getPlantsFirstTransitNode(String brandCode) {
		ResponseEntity<List<Plant>> response = restTemplate.exchange(
				baseUrl + LOU_API_PLANTS + "/" + getSystemSourceCodeFromBrand(brandCode), HttpMethod.GET,
				getHttpEntity(), new ParameterizedTypeReference<List<Plant>>() {
				});
		List<Plant> plants = response.getBody();
		List<PlantFirstTransitNode> plantsFirstTransNodes = new ArrayList<>();
		for (Plant plant : plants) {
			if (plant!=null && plant.getTransitNode()!=null) {
			PlantFirstTransitNode plantFirstTransitNode = new PlantFirstTransitNode();
			plantFirstTransitNode.setSource(getSystemSourceCodeFromBrand(brandCode));
			plantFirstTransitNode.setLouisaPlant(plant.getCode());
			plantFirstTransitNode.setDescription(plant.getDescription());
			plantFirstTransitNode.setLouisaCode(plant.getTransitNode().getTransitNode());
			plantsFirstTransNodes.add(plantFirstTransitNode);
			}
		}
		return plantsFirstTransNodes.stream().filter(Objects::nonNull)
				.collect(toMap(PlantFirstTransitNode::getLouisaPlant, transNode -> transNode, (m1, m2) -> m1));
	}
	
	@Cacheable(value = PLANT_FIRST_TRANSIT_NODE_EXCEPTION_CACHE_NAME, key = "#brandCode")
	public Map<String, List<PlantFirstTransitNode>> getPlantsFirstTransitNodeException(String brandCode) {
		ResponseEntity<List<PlantTransitNode>> response = restTemplate.exchange(
				baseUrl + LOU_API_PLANT_FIRST_NODE_EXCEPTION + "/" + getSystemSourceCodeFromBrandForException(brandCode), HttpMethod.GET,
				getHttpEntity(), new ParameterizedTypeReference<List<PlantTransitNode>>() {
				});
		List<PlantTransitNode> plantsNodes = response.getBody();
		List<PlantFirstTransitNode> plantsFirstTransNodes = new ArrayList<>();
		for (PlantTransitNode plantNode : plantsNodes) {
			if (plantNode!=null && plantNode.getTransitNode()!=null) {
			PlantFirstTransitNode plantFirstTransitNode = new PlantFirstTransitNode();
			plantFirstTransitNode.setSource(getSystemSourceCodeFromBrand(brandCode));
			plantFirstTransitNode.setLouisaPlant(plantNode.getPlantNode().getPlant());
			plantFirstTransitNode.setHostModelCode(plantNode.getModelNode().getModel());
			plantFirstTransitNode.setCountryCode(plantNode.getCountryNode().getIsoCountry());
			plantFirstTransitNode.setLouisaCode(plantNode.getTransitNode().getTransitNode());
			plantsFirstTransNodes.add(plantFirstTransitNode);
			}
		}
		return plantsFirstTransNodes.stream().filter(Objects::nonNull)
				.collect(Collectors.groupingBy(PlantFirstTransitNode::getLouisaPlant,
						Collectors.mapping(plantFirstNode -> plantFirstNode, Collectors.toList())));

	}
	
	/**
	 * This method is used to get the data from hive
	 * 
	 * @return this will return market
	 */
	@Cacheable(value = RENAULT_SHORT_TERM_MARKETS_CACHE_NAME)
	public List<Market> getRenaultShortTermMarkets() {
		List<Market> markets;
		if(hiveStatus) {
			markets=hiveService.getPfmtMarket();
		}else {
			log.info("Hive Or Local url {}",baseUrl);
			ResponseEntity<List<Market>> response = restTemplate.exchange(baseUrl+LOU_PFMT_API_MARKETS, HttpMethod.GET,
					getHttpEntity(), new ParameterizedTypeReference<List<Market>>() {
					});
			 markets = response.getBody();
		}
		
		return markets;
	}

	/**
	 * This method is used to get first plan node data from Hive based on VIN
	 * @param lstvin
	 * @return
	 */
	@Cacheable(value = FIRST_PLAN_NODE )
	public Map<String, String> getFirstplanNode( List<String> lstvin ) {			
		Map<String, String> firstPlanNode;
		if (hiveStatus) {
			firstPlanNode = hiveService.getFirstPlanNode(lstvin);
		} else {
			ResponseEntity<Map<String, String>> response = restTemplate.exchange(
					baseUrl + LOU_FIRST_PLAN_NODE, HttpMethod.GET, getHttpEntity(),
					new ParameterizedTypeReference<Map<String, String>>() {
					});
			firstPlanNode = response.getBody();
		}		
		return firstPlanNode;
	}
	
	private String getSystemSourceCodeFromBrandForException(String brand) {
		log.info("Brand {}", brand);
		return "renault".equalsIgnoreCase(brand) ? BSD_SYSTEM_SOURCE_CODE : NISSAN_SYSTEM_SOURCE_CODE;
	}
	
    @Cacheable(value = RENAULT_EXCEPTION_PLANTS_CACHE_NAME ,key = "#sourceCode")
    public List<Plant> getRenaultPlantsByBrandSource(String sourceCode) {
    	ResponseEntity<List<Plant>> response = restTemplate.exchange(baseUrl + LOU_API_PLANTS + "/" + getSystemSourceCodeFromBrand(Brand.RENAULT,sourceCode),
                HttpMethod.GET, getHttpEntity(), new ParameterizedTypeReference<List<Plant>>() {
                });
        return response.getBody();
    }
}
