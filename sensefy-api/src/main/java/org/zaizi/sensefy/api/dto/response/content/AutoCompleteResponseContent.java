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
package org.zaizi.sensefy.api.dto.response.content;

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
