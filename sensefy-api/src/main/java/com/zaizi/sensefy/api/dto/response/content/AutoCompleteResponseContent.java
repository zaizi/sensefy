/*******************************************************************************
 * Sensefy
 *
 * Copyright (c) Zaizi Limited, All rights reserved.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 *******************************************************************************/
package com.zaizi.sensefy.api.dto.response.content;

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
