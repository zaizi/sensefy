package com.zaizi.dto.semantic.booleanquery.clause;

/**
 * This class has the responsability to model an EntityType boolean clause
 * @author Alessandro Benedetti
 *         27/10/2014
 *         search-api
 * @since 1.4
 */
public class EntityTypeClause implements BooleanClause
{
    public static final String SMLT_ENTITY_TYPES = "smlt_entity_types";


    /* Entity Type Index */
    public static final String ENTITY_TYPE_ID_FIELD = "type";


    private String id;
    private String attribute;
    private String value;

    public EntityTypeClause( String id, String attribute, String value )
    {
        this.id = id;
        this.attribute = attribute;
        this.value = value;
    }

    public String getClause(){
        String query;
        String baseQuery="";

            if ( attribute == null )
            {
                baseQuery = ENTITY_TYPE_ID_FIELD + ":\"" + id+ "\"";
            }
            else
            {
                baseQuery = ENTITY_TYPE_ID_FIELD + ":\"" + id + "\" AND " + attribute + ":\""
                    + value + "\"";
            }
        return "("+baseQuery+")";
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getAttribute()
    {
        return attribute;
    }

    public void setAttribute( String attribute )
    {
        this.attribute = attribute;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue( String value )
    {
        this.value = value;
    }
}
