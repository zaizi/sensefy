package com.zaizi.sensefy.api.dto.faceting.configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class models a list of Facet Configurations
 * @author Alessandro Benedetti
 *         17/10/2014
 *         search-api
 * @since 1.4
 */
@XmlRootElement
public class FacetConfigurationList
{
    @XmlElement
    private List<FacetConfiguration> facetConfiguration;
    
    private Map<String,FacetConfiguration> field2FacetConfiguration;

    public void initMap(){
        field2FacetConfiguration=new HashMap<String, FacetConfiguration>(  );
        for(FacetConfiguration facetConfig:facetConfiguration)
            field2FacetConfiguration.put(facetConfig.getField() ,facetConfig );
    }

    public FacetConfiguration getFacetConfig(String field){
        return field2FacetConfiguration.get( field );
    }

    public List<FacetConfiguration> getFacetConfigurations()
    {
        return facetConfiguration;
    }

    public void setFacetConfigurations( List<FacetConfiguration> facetConfigurations )
    {
        this.facetConfiguration = facetConfigurations;
    }
}
