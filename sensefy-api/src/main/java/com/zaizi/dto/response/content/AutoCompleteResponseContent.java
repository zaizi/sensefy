package com.zaizi.dto.response.content;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Auto Complete AbstractSensefyResponse Content
 * It contains the list of the suggestions and the collation query
 *
 * @author Alessandro Benedetti
 * @since 1.4
 */
@XmlRootElement
@XmlType( propOrder = { "numberOfSuggestions","suggestions"} )
public class AutoCompleteResponseContent
{
    private List<String> suggestions;

    private int numberOfSuggestions;

    public AutoCompleteResponseContent()
    {
        suggestions = new ArrayList<String>();
    }

    public List<String> getSuggestions()
    {
        return suggestions;
    }

    public void setSuggestions( List<String> suggestions )
    {
        this.suggestions = suggestions;
    }

    public void addSuggestion( String suggestion )
    {
        this.suggestions.add( suggestion );
    }

    public int getNumberOfSuggestions()
    {
        return numberOfSuggestions;
    }

    public void setNumberOfSuggestions( int numberOfSuggestions )
    {
        this.numberOfSuggestions = numberOfSuggestions;
    }
}
