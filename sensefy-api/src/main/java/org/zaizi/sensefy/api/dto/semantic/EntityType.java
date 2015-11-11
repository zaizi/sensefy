/**
 * (C) Copyright 2015 Zaizi Limited (http://www.zaizi.com).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 3.0 which accompanies this distribution, and is available at 
 * http://www.gnu.org/licenses/lgpl-3.0.en.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 **/
package org.zaizi.sensefy.api.dto.semantic;

import java.util.List;

import javax.ws.rs.QueryParam;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * It models the Entity Type concept
 * @Author: Alessandro Benedetti
 * Date: 11/02/2014
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = { "id", "langAwareType","type" , "attributes", "hierarchy"} )
public class EntityType
{
    @QueryParam("id")
    @org.apache.solr.client.solrj.beans.Field
    private String id;

    @QueryParam("langAwareType")
    private String langAwareType;  // the label of the entity type in the language the user has selected

    @QueryParam("attributes")
    @org.apache.solr.client.solrj.beans.Field
    private List<String> attributes; // the attributes of the entity type



    @QueryParam("hierarchy")
    @org.apache.solr.client.solrj.beans.Field

    private List<String> hierarchy; // the hierarchy of the entity type

    @QueryParam("type")
    @org.apache.solr.client.solrj.beans.Field
    private List<String> type;   // the multi valued field for suggestion of the entity type, this field is multi language

    public List<String> getType()
    {
        return type;
    }

    public void setType( List<String> type )
    {
        this.type = type;
    }

    public List<String> getAttributes()
    {
        return attributes;
    }

    public void setAttributes( List<String> attributes )
    {
        this.attributes = attributes;
    }

    public String getLangAwareType()

    {
        return langAwareType;
    }

    public void setLangAwareType( String type )
    {
        this.langAwareType = type;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public List<String> getHierarchy()
    {
        return hierarchy;
    }

    public void setHierarchy( List<String> hierarchy )
    {
        this.hierarchy = hierarchy;
    }
}
