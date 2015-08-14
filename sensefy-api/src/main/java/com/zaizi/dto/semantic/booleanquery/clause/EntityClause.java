package com.zaizi.dto.semantic.booleanquery.clause;

/**
 * @author Alessandro Benedetti
 *         27/10/2014
 *         search-api
 * @since 1.4
 */
public class EntityClause implements BooleanClause
{
    public static final String ENTITY_ID = "id";

    private String id;

    public EntityClause( String id )
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getClause(){
        String query = "(" + ENTITY_ID + ":\"" + id + "\")";
        return query;
    }
}
